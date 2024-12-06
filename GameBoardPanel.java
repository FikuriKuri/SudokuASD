import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 60;
    public static final int BOARD_WIDTH = CELL_SIZE * 9;
    public static final int BOARD_HEIGHT = CELL_SIZE * 9;

    private Cell[][] cells = new Cell[9][9];
    private Puzzle puzzle = new Puzzle();

    public GameBoardPanel() {
        super.setLayout(new GridLayout(9, 9));

        // Create cells with proper borders
        for (int row = 0; row < 9; ++row) {
            for (int col = 0; col < 9; ++col) {
                cells[row][col] = new Cell(row, col);
                add(cells[row][col]);

                // Set thick borders for subgrids
                int top = (row % 3 == 0) ? 3 : 1;
                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = (row == 8) ? 3 : 1;
                int right = (col == 8) ? 3 : 1;

                Border border = BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);
                cells[row][col].setBorder(border);
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void newGame(int cellsToGuess) {
        puzzle.newPuzzle(cellsToGuess);

        for (int row = 0; row < 9; ++row) {
            for (int col = 0; col < 9; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    public boolean isSolved() {
        for (int row = 0; row < 9; ++row) {
            for (int col = 0; col < 9; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }
}