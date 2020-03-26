import javax.swing.*;
import java.awt.*;

public class GameOverWindow extends JFrame {
    public GameOverWindow() {
        setTitle("Начать с начала?");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200, 200);
        setLocation(500, 200);

        JButton jButton = new JButton();
        jButton.setText("Начать сначала?");
        jButton.setBackground(Color.YELLOW);
        add(jButton);
        setVisible(true);
    }
}
