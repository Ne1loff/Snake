import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow () {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(640, 665);
        setLocation(500, 200);

        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
