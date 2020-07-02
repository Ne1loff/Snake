package com.sergey.snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;


public class GameField extends JPanel implements ActionListener {
    public static final int SIZE = MainWindow.width;
    public static int score = 0;
    public static int score1 = score;
    public static int record;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = (int) Math.pow((SIZE / DOT_SIZE - 3), 2);
    private Image bricks;
    private Image dotHead;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS], y = new int[ALL_DOTS];
    private int dots;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    private boolean isGameOver = false;

    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame() {
        dots = 1;
        for (int i = 0; i < dots; i++) {
            x[i] = 320 - i * DOT_SIZE;
            y[i] = 320;
        }
        Timer timer = new Timer(125, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = (new Random().nextInt(38) + 1) * DOT_SIZE;
        appleY = (new Random().nextInt(38) + 1) * DOT_SIZE;
        if (appleY == SIZE - 32 || appleX == SIZE - 32 || appleX == 0 || appleY == 0) {
            createApple();
        } else {
            for (int i = 0; i < dots; i++) {
                if (appleX == x[i] && appleY == y[i]) {
                    createApple();
                }
            }
        }
    }

    ImageIcon loadImage(String filename) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = Objects.requireNonNull(classLoader.getResource(filename));
        try {
            return new ImageIcon(ImageIO.read(url));
        } catch (IOException e) {
            return null;
        }
    }

    public void loadImages() {

        ImageIcon iib = loadImage("bricks.png");
        bricks = iib.getImage();
        ImageIcon iia = loadImage("apple.png");
        apple = iia.getImage();
        ImageIcon iidh = loadImage("head.png");
        dotHead = iidh.getImage();
        ImageIcon iid = loadImage("body.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            for (int i = 0; i < SIZE - 32; i += 16) {
                g.drawImage(bricks, i,0,this);
            }
            for (int i = 0; i < SIZE - 32; i += 16) {
                g.drawImage(bricks, 0, i,this);
                g.drawImage(bricks, SIZE - 32, i, this);
            }
            for (int i = 0; i < SIZE - 16; i += 16) {
                g.drawImage(bricks, i, SIZE - 32, this);
            }
            g.drawImage(apple, appleX, appleY, this);
            g.drawImage(dotHead, x[0], y[0], this);
            for (int i = 1; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }

            try (FileReader reader = new FileReader("record.txt")) {
                int c;
                char cr;
                StringBuilder recc = new StringBuilder();
                while ((c = reader.read()) != -1) {
                    cr = (char) c;
                    recc.append(cr);
                }
                record = Integer.parseInt(recc.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (record < score) record = score;

            String sc = "Score: " + score;
            String rec = "Record: " + record;
            Font f = new Font("Arial Narrow", Font.BOLD, 16);
            g.setColor(Color.WHITE);
            g.setFont(f);
            g.drawString(sc, 0, SIZE);
            g.drawString(rec, SIZE - SIZE / 4, SIZE);

        } else if (!isGameOver) {
            String str = "Game Over";
            Font f = new Font("Arial", Font.BOLD, 48);
            g.setColor(Color.WHITE);
            g.setFont(f);
            g.drawString(str, SIZE / 2 - 148, SIZE / 2 - 24);
            isGameOver = true;
            score1 = score;
            new GameOverWindow(e -> {
                isGameOver = false;
                inGame = true;
                x[0] = 320;
                y[0] = 320;
                dots = 1;
                if (score >= record) {
                    try (FileWriter fw = new FileWriter("record.txt", false)) {
                        String rec = "" + record;
                        fw.write(rec);
                        fw.flush();
                    } catch (IOException ignored) {
                    }
                }
                score = 0;
            });
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            score++;
            createApple();
        }
    }


    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (x[i] == x[0] && y[i] == y[0] && (right || left || up || down)) {
                inGame = false;
                break;
            }
        }

        if(x[0] >= SIZE - 32) {
            inGame = false;
        }
        if(x[0] <= 0) {
            inGame = false;
        }
        if(y[0] >= SIZE - 32) {
            inGame = false;
        }
        if(y[0] <= 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right && x[0] == x[1]) {
                left = true;
                up = false;
                down = false;
            } else if (key == KeyEvent.VK_RIGHT && !left && x[0] == x[1]) {
                right = true;
                up = false;
                down = false;
            } else if (key == KeyEvent.VK_UP && !down && y[0] == y[1]) {
                up = true;
                right = false;
                left = false;
            } else if (key == KeyEvent.VK_DOWN && !up && y[0] == y[1]) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
