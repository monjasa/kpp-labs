package org.monjasa.lab04.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.monjasa.lab04.DijkstraApplication;
import org.monjasa.lab04.model.Graph;
import org.monjasa.lab04.service.ParallelDijkstraAlgorithm;
import org.monjasa.lab04.util.ResourceLoader;
import org.monjasa.lab04.util.ThreadScannerTask;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class DefaultFrame extends JFrame {

    private JPanel rootPanel;
    private JTextArea textArea;
    private JSpinner threadsSpinner;
    private JButton startButton;
    private JTextArea resultArea;

    private List<Integer> result;
    private Timer timer;

    public DefaultFrame(String title) throws HeadlessException {

        super(title);

        threadsSpinner.setValue(1);

        setContentPane(rootPanel);
        startButton.addActionListener(getListenerForStartButton());
    }

    private ActionListener getListenerForStartButton() {
        return event -> {

            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
            fileChooser.setDialogTitle("Select data source");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JavaScript Object Notation (*.json)", "json"));

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                try {
                    Graph graph = ResourceLoader.loadGraph(fileChooser.getSelectedFile());

                    textArea.setText("");
                    if (timer != null) timer.cancel();
                    timer = new Timer();

                    result = new ArrayList<>();

                    ThreadScannerTask threadScannerTask = new ThreadScannerTask(textArea);
                    timer.schedule(threadScannerTask, 0, 10);
                    timer.schedule(new ResultListUpdaterTask(resultArea, result), 0, 10);

                    new Thread(() -> result.addAll(ParallelDijkstraAlgorithm.to(graph, threadScannerTask).solve((Integer) threadsSpinner.getValue())))
                            .start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @RequiredArgsConstructor
    public static class ResultListUpdaterTask extends TimerTask {

        private final JTextArea resultArea;
        private final List<Integer> result;

        @Override
        public void run() {
            resultArea.setText(result.toString());
        }
    }
}
