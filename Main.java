import java.awt.*; // Untuk BorderLayout
import java.awt.event.*; // Untuk ActionListener dan ActionEvent
import javax.swing.*; // Untuk JFrame, JButton, dan komponen Swing lainnya
/**
 * The main Sudoku program
 */
public class Main extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton btnNewGame = new JButton("New Game");
    JButton restart = new JButton("Restart");
    int cellsToGuess;

    // Constructor
    public Main() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        // Add a button to the south to re-start the game via board.newGame()
        // Add a "New Game" button to the south
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectDifficulty();
                board.newGame(cellsToGuess);
            }
        });

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame(cellsToGuess);
            }
        });
        cp.add(southPanel, BorderLayout.SOUTH);

        southPanel.add(btnNewGame);
        southPanel.add(restart);

        int cellsToGuess = selectDifficulty();
        board.newGame(cellsToGuess);

        pack(); // Sesuaikan ukuran komponen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
        setVisible(true);


        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
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
            case 1: cellsToGuess = 40; // Medium
            case 2: cellsToGuess =  60; // Hard
            default: cellsToGuess =  20; // Easy
                return cellsToGuess;
        }
    }


    /** The entry main() entry method */
    public static void main(String[] args) {
        // [TODO 1] Check "Swing program template" on how to run
        //  the constructor of "SudokuMain"

        Main game = new Main();
    }
}