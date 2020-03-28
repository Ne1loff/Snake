package com.sergey.snake;

import com.sergey.snake.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;



public class GameOverWindow extends JFrame {
    public GameOverWindow(ActionListener actionListener) {
        setTitle("Game Over");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocation(575, 300);

        JButton jButton = new JButton();

        Font font1 = new Font("Arial Black", Font.BOLD, 26);
        Font font2 = new Font("Arial Black", Font.BOLD, 21);
        jButton.setLayout(new BorderLayout());
        JLabel jLabel1 = new JLabel("  Вы програли, ваш счет: " + GameField.score1);
        JLabel jLabel2 = new JLabel("Нажмите, чтобы начать новую игру.");
        jLabel1.setFont(font1);
        jLabel2.setFont(font2);
        jButton.add(BorderLayout.NORTH, jLabel1);
        jButton.add(BorderLayout.CENTER, jLabel2);

        jButton.setBackground(Color.ORANGE);
        jButton.addActionListener(actionListener);
        jButton.addActionListener(e -> setVisible(false));
        add(jButton);
        setVisible(true);
    }
}
