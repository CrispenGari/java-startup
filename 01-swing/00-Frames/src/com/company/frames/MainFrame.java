package com.company.frames;

import javax.swing.*;
import java.awt.*;

public class MainFrame  extends JFrame {
    public MainFrame(){
        this.setSize(new Dimension(200, 200)); // alternatively frame.setSize(200, 200)
        this.setResizable(false);
        // Create an icon
        ImageIcon image = new ImageIcon("info.png");
        this.setIconImage(image.getImage());
        this.setTitle("My Frame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0, 0, 0)); // new Color(0x000000)

        this.setVisible(true); // this makes a frame visible
    }
}
