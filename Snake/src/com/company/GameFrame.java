package com.company;

import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){
        this.setTitle("Snake 🐍");
        this.add(new GamePanel());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
