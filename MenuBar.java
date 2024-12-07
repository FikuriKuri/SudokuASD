import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    public MenuBar(Main game) {
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(e -> game.board.newGame(game.getSelectDifficulty()));
        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> game.board.newGame(game.cellsToGuess));
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(newGameItem);
        fileMenu.add(restartItem);
        fileMenu.add(exitItem);

        // Options Menu
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem difficultyItem = new JMenuItem("Select Difficulty");
        difficultyItem.addActionListener(e -> game.getSelectDifficulty());
        optionsMenu.add(difficultyItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem howToPlayItem = new JMenuItem("How to Play");
        howToPlayItem.addActionListener(e -> JOptionPane.showMessageDialog(
                null, "Sudoku is a number puzzle game. Fill each row, column, and subgrid with numbers 1-9 without repetition.",
                "How to Play", JOptionPane.INFORMATION_MESSAGE
        ));
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
                null, "Sudoku Game developed as a Java project.\nCreated by: [Your Name]",
                "About", JOptionPane.INFORMATION_MESSAGE
        ));
        helpMenu.add(howToPlayItem);
        helpMenu.add(aboutItem);

        // Extras Menu
        JMenu extrasMenu = new JMenu("Extras");
        JMenuItem toggleMusicItem = new JMenuItem("Toggle Music");
        toggleMusicItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add logic to toggle background music
            }
        });
        extrasMenu.add(toggleMusicItem);

        // Add menus to MenuBar
        add(fileMenu);
        add(optionsMenu);
        add(helpMenu);
        add(extrasMenu);
    }
}
