package com.company;

import javax.swing.*;
public class Main {
    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = Main.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    public static void main(String[] args) {

        JFrame frame = new JFrame("JLabel");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("My label is awesome");
        label.setIcon(createImageIcon("info.jpg", "-"));
        label.setHorizontalAlignment(JLabel.CENTER); // CENTER, RIGHT LEFT
        label.setVerticalAlignment(JLabel.CENTER); // TOP, CENTER BOTTOM

        // Adding a label to the frame
        frame.add(label);
        frame.setVisible(true);
    }
}
