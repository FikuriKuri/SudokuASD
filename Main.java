import java.awt.*; // Untuk BorderLayout
import java.awt.event.*; // Untuk ActionListener dan ActionEvent
import javax.swing.*; // Untuk JFrame, JButton, dan komponen Swing lainnya
/**
 * The main Sudoku program
 */
public class Main extends JFrame {
    private static final long serialVersionUID = 1L;

    GameBoardPanel board = new GameBoardPanel();
    JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton btnNewGame = new JButton("New Game");
    JButton btnRestart = new JButton("Restart");
    int cellsToGuess;

    public Main() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        btnNewGame.addActionListener(e -> {
            selectDifficulty();
            board.newGame(cellsToGuess);
        });

        btnRestart.addActionListener(e -> board.newGame(cellsToGuess));

        southPanel.add(btnNewGame);
        southPanel.add(btnRestart);
        cp.add(southPanel, BorderLayout.SOUTH);

        selectDifficulty();
        board.newGame(cellsToGuess);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);
    }

    private int selectDifficulty() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Difficulty Level",
                "Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 1:
                cellsToGuess = 40;
                break;
            case 2:
                cellsToGuess = 60;
                break;
            default:
                cellsToGuess = 20;
                break;
        }
        return cellsToGuess;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}