package com.sergey.snake;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainWindow extends JFrame {
    public static final int width = 640;
    public static final int height = width + 41;

    public MainWindow() {
        Path recordFilePath = Paths.get("record.txt");

        if (!Files.exists(recordFilePath)) {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("record.txt"), StandardCharsets.UTF_8))) {
                writer.write('0');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
