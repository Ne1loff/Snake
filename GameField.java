import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 640;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image bricks;
    private Image dotHead;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS], y = new int[ALL_DOTS];
    private int dots;
    private int score = 0;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

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
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(125,this);
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = (new Random().nextInt(38) + 1) * DOT_SIZE;
        appleY = (new Random().nextInt(38) + 1) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iib =  new ImageIcon("Bricks.png");
        bricks = iib.getImage();
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iidh = new ImageIcon("peter_griffin_football_head.png");
        dotHead = iidh.getImage();
        ImageIcon iid = new ImageIcon("SnakeBody.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            for (int i = 0; i < SIZE + 1; i += 16) {
                g.drawImage(bricks, i,0,this);
            }
            for (int i = 0; i < SIZE - 32; i += 16) {
                g.drawImage(bricks, 0, i,this);
                g.drawImage(bricks, SIZE - 32, i,this);
            }
            for (int i = 0; i < SIZE + 1; i += 16) {
                g.drawImage(bricks, i, SIZE - 32,this);
            }
            g.drawImage(apple, appleX, appleY, this);
            g.drawImage(dotHead, x[0], y[0], this);
            for (int i = 1; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "Game Over";
            Font f = new Font("Arial", Font.BOLD, 48);
            g.setColor(Color.WHITE);
            g.setFont(f);
            g.drawString(str, SIZE / 2 - 148, SIZE / 2 - 24);
            GameOverWindow gow = new GameOverWindow();

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
            if (i > 4 && x[i] == x[0] && y[i] == y[0]) {
                inGame =false;
            }
        }

        if(x[0] >= SIZE) {
            inGame = false;
        }
        if(x[0] <= 0) {
            inGame = false;
        }
        if(y[0] >= SIZE) {
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
            if (key == KeyEvent.VK_LEFT && ! right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && ! left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && ! down) {
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && ! up) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
