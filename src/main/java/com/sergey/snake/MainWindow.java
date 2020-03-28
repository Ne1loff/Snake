package com.sergey.snake;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static final int width = 640;
    public static final int height = width + 41;
    public MainWindow () {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocation(500, 200);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
