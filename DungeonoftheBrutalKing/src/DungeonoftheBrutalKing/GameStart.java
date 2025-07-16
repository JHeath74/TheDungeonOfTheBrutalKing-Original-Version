
package DungeonoftheBrutalKing;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class GameStart extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws IOException, InterruptedException {
        GameSettings myGameSettings = new GameSettings();
        CharacterCreation myCharacterCreation = new CharacterCreation();
        LoadSaveGame myLoadSaveGame = new LoadSaveGame();

        JFrame StartMenuFrame = new JFrame("Dungeon of the Brutal King");
        StartMenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        StartMenuFrame.setSize(800, 600);
        StartMenuFrame.setLayout(new BorderLayout());
        StartMenuFrame.getContentPane().setBackground(myGameSettings.colorLightBrown);

        // Title label
        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Dungeon<br>of the<br>Brutal King</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 46));
        titleLabel.setForeground(myGameSettings.colorWhite);
        StartMenuFrame.add(titleLabel, BorderLayout.NORTH);

       

        JButton ContinueGameButton = new JButton("Continue");
        JButton StartNewGameButton = new JButton("Start New Game");
        JButton LoadExistingGameButton = new JButton("Load Game");
        JButton ExitGameButton = new JButton("Exit");

        // Configure button sizes and styles
        Dimension buttonSize = new Dimension(200, 50);
        ContinueGameButton.setPreferredSize(buttonSize);
        StartNewGameButton.setPreferredSize(buttonSize);
        LoadExistingGameButton.setPreferredSize(buttonSize);
        ExitGameButton.setPreferredSize(buttonSize);

        ContinueGameButton.setBackground(myGameSettings.colorGrey);
        StartNewGameButton.setBackground(myGameSettings.colorGrey);
        LoadExistingGameButton.setBackground(myGameSettings.colorGrey);
        ExitGameButton.setBackground(myGameSettings.colorGrey);

        ContinueGameButton.setForeground(myGameSettings.colorWhite);
        StartNewGameButton.setForeground(myGameSettings.colorWhite);
        LoadExistingGameButton.setForeground(myGameSettings.colorWhite);
        ExitGameButton.setForeground(myGameSettings.colorWhite);

        // Check if saved games exist and adjust button visibility
        File directory = new File(GameSettings.SavedGameDirectory);
        if (directory.isDirectory()) {
            String[] files = directory.list();
            if (files != null && files.length > 0) {
                ContinueGameButton.setVisible(true);
            } else {
                ContinueGameButton.setVisible(false);
            }
        }

        
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(myGameSettings.colorLightBrown);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // All buttons in the same column
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch buttons horizontally
        gbc.weighty = 0.1; // Adjust weighty to move buttons up
        gbc.anchor = GridBagConstraints.NORTH; // Align buttons to the top

        // Adjust spacing for the first button
        gbc.insets = new Insets(50, 0, 7, 0); // Move the top button down by 0.5 inches
        gbc.gridy = 0; // First button
        buttonPanel.add(ContinueGameButton, gbc);

        // Adjust spacing for subsequent buttons
        gbc.insets = new Insets(-50, 0, 7, 0); // Reduced spacing between buttons
        gbc.gridy = 1; // Second button
        buttonPanel.add(StartNewGameButton, gbc);

        gbc.gridy = 2; // Third button
        buttonPanel.add(LoadExistingGameButton, gbc);

        gbc.gridy = 3; // Last button
        buttonPanel.add(ExitGameButton, gbc);

        StartMenuFrame.add(buttonPanel, BorderLayout.CENTER);        StartMenuFrame.setLocationRelativeTo(null);
        StartMenuFrame.setVisible(true);

        // Add action listener for the Start New Game button
        StartNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File saveFile = new File(GameSettings.SavedGameDirectory + "/InitlaCharecterSave.txt");

                if (saveFile.exists()) {
                    int response = JOptionPane.showConfirmDialog(null,
                            "InitlaCharecterSave.txt exists. Do you want to delete it and start a new game?",
                            "File Exists",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (response == JOptionPane.YES_OPTION) {
                        if (saveFile.delete()) {
                            try {
                                proceedToCreateCharacter(e);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Failed to delete the file. Please try again.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        returnToPreviousScreen();
                    }
                } else {
                    try {
                        proceedToCreateCharacter(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            private void proceedToCreateCharacter(ActionEvent e) throws IOException {
                Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                MusicPlayer.stopMidi();
                window.dispose();
                myCharacterCreation.createCharector();
            }

            private void returnToPreviousScreen() {
                // Code to return to the previous screen
            }
        });

        // Add action listener for the Continue Game button
        ContinueGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                    myLoadSaveGame.ContinueCurrentGame();
                    window.dispose();
                } catch (IOException | InterruptedException | ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Add action listener for the Load Existing Game button
        LoadExistingGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicPlayer.stopMidi();
                Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                myLoadSaveGame.LoadGame();
                window.dispose();
            }
        });

        // Add action listener for the Exit Game button
        ExitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
    }

    public GameStart() throws IOException, InterruptedException {
        // Start the background music in a separate thread
        Thread musicThread = new Thread(() -> {
            MusicPlayer soundplayer = new MusicPlayer();
            try {
                soundplayer.midiPlayer("Stones.mid");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        });
        musicThread.start();
    }
}
