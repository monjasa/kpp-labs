package org.monjasa;

import org.monjasa.view.DefaultFrame;

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

            JFrame frame = new DefaultFrame("Application");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationByPlatform(true);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
