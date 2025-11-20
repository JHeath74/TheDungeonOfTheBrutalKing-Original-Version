
package DungeonoftheBrutalKing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import SharedData.GameSettings;
import SharedData.MusicPlayer;
import SharedData.RoundedButton;
import SharedData.SettingsAndPreferences;

public class GameStart extends JFrame {
    private static final long serialVersionUID = 1L;
    private JFrame StartMenuFrame;
    private GameSettings myGameSettings;
    private CharacterCreation myCharacterCreation;
    private LoadSaveGame myLoadSaveGame;

    public static void main(String[] args) throws IOException, InterruptedException {
        new GameStart();
    }

    public GameStart() throws IOException, InterruptedException {
        myGameSettings = new GameSettings();
        myCharacterCreation = new CharacterCreation();
        myLoadSaveGame = new LoadSaveGame();

        setupFrame();
        JPanel backgroundPanel = createBackgroundPanel();
        JPanel buttonPanel = createButtonPanel();
        backgroundPanel.add(buttonPanel, BorderLayout.CENTER);
        StartMenuFrame.add(backgroundPanel);
        StartMenuFrame.setLocationRelativeTo(null);
        StartMenuFrame.setVisible(true);

        //LoadSaveGame.readSaveFileDemo();
    }

    private void setupFrame() {
        StartMenuFrame = new JFrame("Dungeon of the Brutal King");
        StartMenuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        StartMenuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        StartMenuFrame.setUndecorated(true);
        StartMenuFrame.setLayout(new BorderLayout());
        StartMenuFrame.getContentPane().setBackground(myGameSettings.getColorLightBrown());
    }

    private JPanel createBackgroundPanel() {
        JPanel backgroundPanel = new JPanel() {
            private static final long serialVersionUID = 1L;
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

        return backgroundPanel;
    }

    private JPanel createButtonPanel() {
        int buttonRadius = 20;
        Dimension buttonSize = new Dimension(200, 50);

        RoundedButton ContinueGameButton = new RoundedButton("Continue", buttonRadius);
        RoundedButton StartNewGameButton = new RoundedButton("Start New Game", buttonRadius);
        RoundedButton LoadExistingGameButton = new RoundedButton("Load Game", buttonRadius);
        RoundedButton SettingsButton = new RoundedButton("Settings", buttonRadius);
        RoundedButton ExitGameButton = new RoundedButton("Exit", buttonRadius);

        for (RoundedButton btn : new RoundedButton[]{ContinueGameButton, StartNewGameButton, LoadExistingGameButton, SettingsButton, ExitGameButton}) {
            btn.setPreferredSize(buttonSize);
            btn.setBackground(new Color(128, 128, 128));
            btn.setForeground(myGameSettings.getColorWhite());
        }

        File directory = new File(GameSettings.SavedGameDirectory);
        ContinueGameButton.setVisible(directory.isDirectory() && directory.list() != null && directory.list().length > 0);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.weighty = 0.25;
        gbc.insets = new Insets(20, 0, 0, 0);

        int y = 0;
        for (RoundedButton btn : new RoundedButton[]{ContinueGameButton, StartNewGameButton, LoadExistingGameButton, SettingsButton, ExitGameButton}) {
            gbc.gridy = y++;
            buttonPanel.add(btn, gbc);
            if (y == 1) gbc.insets = new Insets(0, 0, 0, 0);
        }

        setupButtonActions(ContinueGameButton, StartNewGameButton, LoadExistingGameButton, SettingsButton, ExitGameButton);

        return buttonPanel;
    }

    private void setupButtonActions(RoundedButton continueBtn, RoundedButton newGameBtn, RoundedButton loadBtn, RoundedButton settingsBtn, RoundedButton exitBtn) {
        newGameBtn.addActionListener(e -> {
            MusicPlayer.stopMidi();
            File saveFile = new File(GameSettings.SavedGameDirectory + "/InitialCharecterSave.txt");
            if (saveFile.exists()) {
                int response = JOptionPane.showConfirmDialog(null,
                        "InitalCharecterSave.txt exists. Do you want to delete it and start a new game?",
                        "File Exists",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    if (saveFile.delete()) {
                        proceedToCreateCharacter(e);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete the file. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                proceedToCreateCharacter(e);
            }
        });

        continueBtn.addActionListener(e -> {
            MusicPlayer.stopMidi();
            try {
                Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
                myLoadSaveGame.ContinueCurrentGame();
                window.dispose();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
        });

        loadBtn.addActionListener(e -> {
            MusicPlayer.stopMidi();
            Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
            myLoadSaveGame.LoadGame();
            window.dispose();
        });

        settingsBtn.addActionListener(e -> {
            MusicPlayer.stopMidi();
            SwingUtilities.invokeLater(() -> {
                try {
                    new SettingsAndPreferences();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to open settings.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        exitBtn.addActionListener(e -> {
            MusicPlayer.stopMidi();
            System.exit(0);
        });
    }
    
    private void proceedToCreateCharacter(java.awt.event.ActionEvent e) {
        Window window = SwingUtilities.getWindowAncestor((Component) e.getSource());
        MusicPlayer.stopMidi();
        window.dispose();
        myCharacterCreation.createCharector();
    }

	/*
	 * private void setupMusic() { MusicPlayer.stopMidi(); Thread musicThread = new
	 * Thread(() -> { MusicPlayer soundplayer = new MusicPlayer(); try {
	 * soundplayer.midiPlayer("Stones.mid"); } catch (UnsupportedAudioFileException
	 * | IOException | LineUnavailableException e) { e.printStackTrace(); } });
	 * musicThread.start(); }
	 */

    public JFrame getStartMenuFrame() {
        return StartMenuFrame;
    }
}
