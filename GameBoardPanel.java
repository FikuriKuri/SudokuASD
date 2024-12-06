import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle = new Puzzle();

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }
        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
        CellInputListener listener = new CellInputListener();
        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++){
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++){
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Generate a new puzzle; and reset the game board of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame(int cellsToGuess) {
        // Generate a new puzzle

        puzzle.newPuzzle(cellsToGuess);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isValidMove(int row, int col, int number) {
        // Periksa baris
        for (int c = 0; c < SudokuConstants.GRID_SIZE; ++c) {
            if (c != col && cells[row][c].number == number) {
                return false;
            }
        }
        // Periksa kolom
        for (int r = 0; r < SudokuConstants.GRID_SIZE; ++r) {
            if (r != row && cells[r][col].number == number) {
                return false;
            }
        }
        // Periksa sub-kisi
        int startRow = (row / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        int startCol = (col / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        for (int r = startRow; r < startRow + SudokuConstants.SUBGRID_SIZE; ++r) {
            for (int c = startCol; c < startCol + SudokuConstants.SUBGRID_SIZE; ++c) {
                if ((r != row || c != col) && cells[r][c].number == number) {
                    return false;
                }
            }
        }
        return true;
    }

    private void highlightSameNumber(int number) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].number == number) {
                    cells[row][col].setBackground(Cell.BG_CORRECT_GUESS);
                } else if (cells[row][col].status == CellStatus.TO_GUESS) {
                    cells[row][col].setBackground(Cell.BG_TO_GUESS);
                }
            }
        }
    }



    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();

            try {
                int numberIn = Integer.parseInt(sourceCell.getText());
                if (numberIn < 1 || numberIn > 9) {
                    throw new NumberFormatException("Out of range");
                }

                // Set input as the cell's number temporarily
                sourceCell.number = numberIn;

                if (isValidMove(sourceCell.row, sourceCell.col, numberIn)) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                    highlightSameNumber(numberIn); // Highlight cells with the same number
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                    sourceCell.setBackground(Cell.BG_WRONG_GUESS);
                }

                sourceCell.paint();

                if (isSolved()) {
                    JOptionPane.showMessageDialog(null, "You have finished the puzzle!", "Congratulations!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number between 1 and 9.", "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                sourceCell.setText("");
            }
        }

    }
}