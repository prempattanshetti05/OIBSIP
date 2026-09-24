import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class NumberGuessingGame {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Number Guessing Game");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 650);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            frame.setContentPane(new GamePanel());

            frame.setVisible(true);
        });
    }
}