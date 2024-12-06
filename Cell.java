
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;

    // Define constants for colors and fonts
    public static final Color BG_GIVEN = new Color(220, 220, 220); // Light Gray
    public static final Color FG_GIVEN = Color.BLACK;             // Black text
    public static final Color FG_NOT_GIVEN = Color.DARK_GRAY;     // Dark Gray text
    public static final Color BG_TO_GUESS = Color.WHITE;          // White for editable cells
    public static final Color BG_CORRECT_GUESS = new Color(144, 238, 144); // Light Green
    public static final Color BG_WRONG_GUESS = new Color(255, 102, 102);   // Light Red
    public static final Font FONT_NUMBERS = new Font("Arial", Font.BOLD, 26);

    int row, col;
    int number;
    CellStatus status;

    public Cell(int row, int col) {
        super();
        this.row = row;
        this.col = col;

        // Cell appearance
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
    }

    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        paint();
    }

    public void paint() {
        if (status == CellStatus.GIVEN) {
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        } else if (status == CellStatus.CORRECT_GUESS) {
            super.setBackground(BG_CORRECT_GUESS);
        } else if (status == CellStatus.WRONG_GUESS) {
            super.setBackground(BG_WRONG_GUESS);
        }
    }
}