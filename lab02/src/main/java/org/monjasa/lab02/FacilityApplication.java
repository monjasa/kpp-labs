package org.monjasa.lab02;

import org.monjasa.lab02.view.DefaultFrame;

import javax.swing.*;

public class FacilityApplication {

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

            JFrame frame = new DefaultFrame("Facility Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationByPlatform(true);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
