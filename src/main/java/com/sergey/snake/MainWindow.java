package com.sergey.snake;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MainWindow extends JFrame {
    public static final int width = 640;
    public static final int height = width + 41;
    public MainWindow () {
        File file = new File("C:\\Users\\serge\\IdeaProjects\\Snake\\build\\libs");
        int counter = 0;
        for (File findRec : Objects.requireNonNull(file.listFiles())) {
            if (findRec.getName().equals("record.txt")) {
                counter = 1;
            }
        }
        if (counter == 0) {
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
