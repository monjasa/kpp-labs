package org.monjasa.view;

import org.monjasa.FacilityHandler;
import org.monjasa.FacilityStrategy;
import org.monjasa.model.Facility;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class DefaultFrame extends JFrame {

    private JPanel rootPanel;

    private JButton selectDataSourcesButton;
    private JButton computeButton;
    private JComboBox<FacilityStrategy> strategyBox;

    private JTextArea sourceArea;
    private JTextArea resultArea;

    private Facility facility;

    public DefaultFrame(String title) throws HeadlessException {

        super(title);

        strategyBox.addItem(new FacilityStrategy("Last names by position", FacilityHandler::groupEmployeesLastNamesByPosition));
        strategyBox.addItem(new FacilityStrategy("Youngest and oldest by position", FacilityHandler::groupYoungestAndOldestEmployeeByPosition));

        strategyBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                FacilityStrategy strategy = (FacilityStrategy) value;
                return super.getListCellRendererComponent(list, strategy.getName(), index, isSelected, cellHasFocus);
            }
        });

        computeButton.addActionListener(event -> {
            var result = strategyBox.getItemAt(strategyBox.getSelectedIndex()).getStrategy().apply(facility);
            result.forEach((key, value) -> resultArea.append(String.format("%s => %s\n", key, value)));
        });

        selectDataSourcesButton.addActionListener(event -> {

            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                facility = FacilityHandler.unmarshallFacilityFromFile(selectedFile);
                facility.getEmployees().forEach(employee -> sourceArea.append(employee + "\n"));
            }
        });

        setContentPane(rootPanel);
    }
}
