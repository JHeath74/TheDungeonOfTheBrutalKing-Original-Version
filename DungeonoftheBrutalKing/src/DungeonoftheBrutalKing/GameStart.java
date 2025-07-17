
package DungeonoftheBrutalKing;

import SharedData.GameSettings;
import SharedData.MusicPlayer;
import SharedData.RoundedButton;
import SharedData.SettingsAndPreferences;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
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
        StartMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        StartMenuFrame.setUndecorated(true);
        StartMenuFrame.setLayout(new BorderLayout());
        StartMenuFrame.getContentPane().setBackground(myGameSettings.getColorLightBrown());

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = Toolkit.getDefaultToolkit().getImage("src\\DungeonoftheBrutalKing\\Images\\Program\\StartMenu\\StartingImage.png");
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>Dungeon<br>of the<br>Brutal King</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 92));
        titleLabel.setForeground(myGameSettings.getColorWhite());
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);

        int buttonRadius = 20;
        RoundedButton ContinueGameButton = new RoundedButton("Continue", buttonRadius);
        RoundedButton StartNewGameButton = new RoundedButton("Start New Game", buttonRadius);
        RoundedButton LoadExistingGameButton = new RoundedButton("Load Game", buttonRadius);
        RoundedButton SettingsButton = new RoundedButton("Settings", buttonRadius); // New Settings button
        RoundedButton ExitGameButton = new RoundedButton("Exit", buttonRadius);

        Dimension buttonSize = new Dimension(200, 50);
        ContinueGameButton.setPreferredSize(buttonSize);
        StartNewGameButton.setPreferredSize(buttonSize);
        LoadExistingGameButton.setPreferredSize(buttonSize);
        SettingsButton.setPreferredSize(buttonSize); // Configure Settings button size
        ExitGameButton.setPreferredSize(buttonSize);

        ContinueGameButton.setBackground(new Color(128, 128, 128));
        StartNewGameButton.setBackground(new Color(128, 128, 128));
        LoadExistingGameButton.setBackground(new Color(128, 128, 128));
        SettingsButton.setBackground(new Color(128, 128, 128)); // Configure Settings button color
        ExitGameButton.setBackground(new Color(128, 128, 128));

        ContinueGameButton.setForeground(myGameSettings.getColorWhite());
        StartNewGameButton.setForeground(myGameSettings.getColorWhite());
        LoadExistingGameButton.setForeground(myGameSettings.getColorWhite());
        SettingsButton.setForeground(myGameSettings.getColorWhite()); // Configure Settings button text color
        ExitGameButton.setForeground(myGameSettings.getColorWhite());

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
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0.25;
        gbc.insets = new Insets(20, 20, 20, 20);

        gbc.insets = new Insets(20, 0, 0, 0);
        gbc.gridy = 0;
        buttonPanel.add(ContinueGameButton, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = 1;
        buttonPanel.add(StartNewGameButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(LoadExistingGameButton, gbc);

        gbc.gridy = 3; // Position for Settings button
        buttonPanel.add(SettingsButton, gbc);

        gbc.gridy = 4; // Move Exit button down
        buttonPanel.add(ExitGameButton, gbc);

        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);
        StartMenuFrame.add(backgroundPanel);
        StartMenuFrame.setLocationRelativeTo(null);
        StartMenuFrame.setVisible(true);

        StartNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File saveFile = new File(GameSettings.SavedGameDirectory + "/InitlaCharecterSave.txt");

                if (saveFile.exists()) {
                    int response = JOptionPane.showConfirmDialog(null,
                            "InitalCharecterSave.txt exists. Do you want to delete it and start a new game?",
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

        LoadExistingGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicPlayer.stopMidi();
                Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                myLoadSaveGame.LoadGame();
                window.dispose();
            }
        });


SettingsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            try {
                new SettingsAndPreferences(); // Open the SettingsAndPreferences window
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to open settings.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
});


        ExitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public GameStart() throws IOException, InterruptedException {
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
