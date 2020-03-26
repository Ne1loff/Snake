import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameOverWindow extends JFrame {
    public GameOverWindow(ActionListener actionListener) {
        setTitle("Начать с начала?");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(200, 200);
        setLocation(500, 200);

        JButton jButton = new JButton();
        jButton.setText("Начать сначала?");
        jButton.setBackground(Color.YELLOW);
        jButton.addActionListener(actionListener);
        add(jButton);
        setVisible(true);
    }
}
