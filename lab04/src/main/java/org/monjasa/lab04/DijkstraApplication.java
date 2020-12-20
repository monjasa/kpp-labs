package org.monjasa.lab04;

import org.monjasa.lab04.view.DefaultFrame;

import javax.swing.*;

public class DijkstraApplication {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException
                    | InstantiationException
                    | IllegalAccessException
                    | UnsupportedLookAndFeelException exception
            ) {
                exception.printStackTrace();
            }

            JFrame frame = new DefaultFrame("Dijkstra Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationByPlatform(true);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
