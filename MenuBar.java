import javax.swing.*;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class MenuBar extends JFrame implements ActionListener, KeyListener {
    JMenuBar menuBar;
    JMenu file, options, help;
    JMenuItem newGame, resetGame, exit;

    public static void main(String[] args) {
        MenuBar menu = new MenuBar();
        menu.setVisible(true);
    }

    public MenuBar(){
        setLayout(new FlowLayout());
        setSize(300,400);
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();

        file = new JMenu("File");
        file.addMenuListener(new MenuListener() {
        });
        menuBar.add(file);

        options = new JMenu("Options");
        options.addMenuListener(new thisMenuListener());
        menuBar.add(options);

        help = new JMenu("Help");
        help.addMenuListener(new thisMenuListener());
        menuBar.add(help);

        newGame = new JMenuItem("New Game");
        newGame.addActionListener(this);
        file.add(newGame);

        resetGame = new JMenuItem("Reset Game");
        resetGame.addActionListener(this);
        file.add(resetGame);

        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        file.add(exit);

        this.setMenuBar();

    }

    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(newGame)) {

        }
    }
}
