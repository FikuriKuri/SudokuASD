import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    // Define properties
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new BorderLayout());  // Use BorderLayout for placing components

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE)); // GridLayout for cells

        // Allocate the 2D array of Cell and add them into the gridPanel
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                gridPanel.add(cells[row][col]);   // Add cells into gridPanel
            }
        }

        // Add the grid panel to the main panel
        super.add(gridPanel, BorderLayout.CENTER);

        // Create an "Exit" button and add it to the bottom of the window
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0); // Exit the application
                }
            }
        });

        // Add the exit button to the bottom of the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);
        super.add(buttonPanel, BorderLayout.SOUTH);

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT + 50)); // Increased size to accommodate the button
    }

    public void newGame(int cellsToGuess) {
        puzzle.newPuzzle(cellsToGuess);
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

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
        // Check the row
        for (int c = 0; c < SudokuConstants.GRID_SIZE; ++c) {
            if (c != col && cells[row][c].number == number) {
                return false; // Number is already present in the row
            }
        }

        // Check the column
        for (int r = 0; r < SudokuConstants.GRID_SIZE; ++r) {
            if (r != row && cells[r][col].number == number) {
                return false; // Number is already present in the column
            }
        }

        // Check the subgrid
        int startRow = (row / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        int startCol = (col / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
        for (int r = startRow; r < startRow + SudokuConstants.SUBGRID_SIZE; ++r) {
            for (int c = startCol; c < startCol + SudokuConstants.SUBGRID_SIZE; ++c) {
                if ((r != row || c != col) && cells[r][c].number == number) {
                    return false; // Number is already present in the subgrid
                }
            }
        }

        // If all checks pass, the move is valid
        return true;
    }

    private void highlightSameNumber(int number) {
        // Loop through all cells
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                // If the cell's number matches the provided number, highlight it
                if (cells[row][col].number == number) {
                    cells[row][col].setBackground(Cell.BG_CORRECT_GUESS); // Correct guess background
                } else if (cells[row][col].status == CellStatus.TO_GUESS) {
                    // If the cell is still to guess, set the default background
                    cells[row][col].setBackground(Cell.BG_TO_GUESS);
                }
            }
        }
    }


    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();

            try {
                int numberIn = Integer.parseInt(sourceCell.getText());
                if (numberIn < 1 || numberIn > 9) {
                    throw new NumberFormatException("Out of range");
                }

                sourceCell.number = numberIn;

                if (isValidMove(sourceCell.row, sourceCell.col, numberIn)) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                    highlightSameNumber(numberIn);
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                    sourceCell.setBackground(Cell.BG_WRONG_GUESS);
                }

                sourceCell.paint();

                if (isSolved()) {
                    JOptionPane.showMessageDialog(null, "You have finished the puzzle!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number between 1 and 9.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                sourceCell.setText("");
            }
        }
    }
}