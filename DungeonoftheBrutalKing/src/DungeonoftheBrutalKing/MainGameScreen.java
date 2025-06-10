
// MainGameScreen.java
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class MainGameScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    // Singleton instance of the character
    Charecter myChar = Charecter.Singleton();
    GameSettings myGameSettings = new GameSettings();
    LoadSaveGame myGameState = new LoadSaveGame();
    GameMenuItems myGameMenuItems = new GameMenuItems();
    CharacterCreation myCharacterCreation;

    // Main frame and panels for the game screen
    JFrame MainGameScreenFrame = null;
    JPanel p1Panel, p2Panel, p3Panel, p4Panel = null;
    public JPanel GameImagesAndCombatPanel = null;
    private JPanel originalPanel;

    // Text fields for displaying character stats
    JTextField CharNameClassLevelField, CharStatsField, CharStats2Field, CharXPHPGoldField = null;
    JTextPane MessageTextPane = null;

    // Menu bar and menu items
    JMenuBar menuBar = null;
    JMenu gameMenu, charecterMenu, settingsMenu, helpMenu = null;
    JMenuItem newGameMenuItem, LoadSavedGameMenuItem, saveMenuItem, exitGameMenuItem, charecterstatsMenuItem,
            charecterinventoryMenuItem, mapMenu, gameSettingsMenuItem, aboutMenuItem, helpMenuItem, mapFloor1MenuItem,
            mapFloor2MenuItem, mapFloor3MenuItem, mapFloor4MenuItem = null;

    // Split pane for dividing the game screen
    JSplitPane PicturesAndTextUpdatesPane = null;

    // Screen dimensions
    Dimension screenSize = null;
    int width, height = 0;

    // Timer for updating character stats
    Timer timer = null;
    private TimeClock clock;
    private Canvas gameImagesAndCombatCanvas;
    public static JTextArea CombatMessageArea = new JTextArea();

    // Constructor for initializing the game screen
    public MainGameScreen() throws IOException {
        // Create the main frame
        MainGameScreenFrame = new JFrame("Dungeon of the Brutal King");

        // Set screen dimensions
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) size.getWidth();
        height = (int) size.getHeight();

        // Configure the main frame
        MainGameScreenFrame.setSize(width, height);
        MainGameScreenFrame.setLayout(new BorderLayout());
        MainGameScreenFrame.setForeground(myGameSettings.colorBrown);
        MainGameScreenFrame.setUndecorated(true);
        MainGameScreenFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Initialize panels
        p1Panel = new JPanel(new BorderLayout());
        p2Panel = new JPanel(new BorderLayout());
        p3Panel = new JPanel(new BorderLayout());
        p4Panel = new JPanel(new BorderLayout());
        GameImagesAndCombatPanel = new JPanel(new BorderLayout());

        // Load character data
        try {
            myGameState.StartGameLoadCharecter();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        // Initialize text fields for character stats
        CharNameClassLevelField = new JTextField();
        CharNameClassLevelField.setFont(myGameSettings.fontTimesNewRoman);
        CharNameClassLevelField.setBackground(myGameSettings.colorGreen);
        CharNameClassLevelField.setForeground(myGameSettings.colorWhite);
        CharNameClassLevelField.setColumns(3);
        CharNameClassLevelField.setEditable(false);

        CharStatsField = new JTextField();
        CharStatsField.setLayout(new FlowLayout());
        CharStatsField.setFont(myGameSettings.fontTimesNewRoman);
        CharStatsField.setBackground(myGameSettings.colorBlue);
        CharStatsField.setForeground(myGameSettings.colorWhite);
        CharStatsField.setEditable(false);

        CharStats2Field = new JTextField();
        CharStats2Field.setLayout(new FlowLayout());
        CharStats2Field.setFont(myGameSettings.fontTimesNewRoman);
        CharStats2Field.setBackground(myGameSettings.colorBlue);
        CharStats2Field.setForeground(myGameSettings.colorWhite);
        CharStats2Field.setEditable(false);

        CharXPHPGoldField = new JTextField();
        CharXPHPGoldField.setLayout(getLayout());
        CharXPHPGoldField.setFont(myGameSettings.fontTimesNewRoman);
        CharXPHPGoldField.setBackground(myGameSettings.colorPurple);
        CharXPHPGoldField.setForeground(myGameSettings.colorWhite);
        CharXPHPGoldField.setColumns(3);
        CharXPHPGoldField.setEditable(false);

        // Add panels and text fields to the layout
        p1Panel.add(p2Panel, BorderLayout.NORTH);
        p1Panel.add(p3Panel, BorderLayout.CENTER);
        p1Panel.add(p4Panel, BorderLayout.SOUTH);
        p2Panel.add(CharNameClassLevelField);
        p3Panel.add(CharStatsField, BorderLayout.NORTH);
        p3Panel.add(CharStats2Field, BorderLayout.SOUTH);
        p4Panel.add(CharXPHPGoldField);

        // Timer for updating character stats periodically
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // Update character stats in the text fields
                CharNameClassLevelField.setText("Name: " + myChar.CharInfo.get(0) + "\t\t"
                        + "Level: " + myChar.CharInfo.get(2) + "\t\t"
                        + "Experience: " + myChar.CharInfo.get(3)
                        + "\t\t" + "Class: " + myChar.CharInfo.get(1) + "\t\t");

                CharStatsField.setText("Stamina:\t"
                        + "Charisma: \t"
                        + "Strength: \t"
                        + "Intelligence:\t "
                        + "Wisdom: \t"
                        + "Agility: \t");

                CharStats2Field.setText(myChar.CharInfo.get(6) + "\t" +
                        myChar.CharInfo.get(7) + "\t" +
                        myChar.CharInfo.get(8) + "\t" +
                        myChar.CharInfo.get(9) + "\t" +
                        myChar.CharInfo.get(10) + "\t" +
                        myChar.CharInfo.get(11));

                CharXPHPGoldField.setText("Hit Points: " + myChar.CharInfo.get(4) + "\t\t"
                        + "Magic Points: " + myChar.CharInfo.get(5) + "\t"
                        + "Gold: " + myChar.CharInfo.get(12) + "\t"
                        + "Food: " + myChar.CharInfo.get(13) + "\t"
                        + "Water: " + myChar.CharInfo.get(14) + "\t"
                        + "Torches: " + myChar.CharInfo.get(15) + "\t"
                        + "Gems: " + myChar.CharInfo.get(16) + "\t");
            }
        };
        timer = new Timer(100, task);
        timer.setRepeats(true);
        timer.start();

        // Initialize menu bar and menus
        menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(25, 35));
        menuBar.setFont(new Font("sans-serif", Font.ROMAN_BASELINE, 22));
        menuBar.setBackground(myGameSettings.colorPlum);

        MainGameScreenFrame.setJMenuBar(menuBar);

        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        charecterMenu = new JMenu("Charecter");
        charecterMenu.setMnemonic(KeyEvent.VK_C);

        settingsMenu = new JMenu("Preferences");
        settingsMenu.setMnemonic(KeyEvent.VK_P);

        helpMenu = new JMenu("About");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        menuBar.add(gameMenu);
        menuBar.add(charecterMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        // Add menu items to the game menu
        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        newGameMenuItem.getAccessibleContext().setAccessibleDescription("New Game");
        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Confirm and start a new game
                int result = JOptionPane.showConfirmDialog(rootPane,
                        "Are you sure you wish to delete your current game and start a new one?", "Start New Game?",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    dispose();
                    try {
                        Files.createDirectories(Paths.get("src/AlternateRealityTheDungeon/TextFiles/SaveGame"));
                        BufferedWriter writer = Files.newBufferedWriter(Paths
                                .get("src/AlternateRealityTheDungeon/TextFiles/SaveGame/InitialCharecterSave.txt"));
                        writer.write("");
                        writer.flush();
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    File d = new File(GameSettings.SavedGameDirectory);
                    for (File file : d.listFiles()) {
                        if (!file.isDirectory()) {
                            file.delete();
                        }
                    }
                    try {
                        myCharacterCreation = new CharacterCreation();
                    } catch (IOException | InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    myCharacterCreation.createCharector();
                }
            }
        });

        gameMenu.add(newGameMenuItem);

        // Configure split pane for game screen layout
        PicturesAndTextUpdatesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        PicturesAndTextUpdatesPane.setDividerLocation(width - 200);
        PicturesAndTextUpdatesPane.setResizeWeight(.90d);
        PicturesAndTextUpdatesPane.setLeftComponent(GameImagesAndCombatPanel);
        PicturesAndTextUpdatesPane.setRightComponent(MessageTextPane);

        MainGameScreenFrame.add(PicturesAndTextUpdatesPane, BorderLayout.CENTER);
        MainGameScreenFrame.add(p1Panel, BorderLayout.NORTH);

        // Start the game clock
        clock = new TimeClock(TimeClock.Month.REBIRTH, MessageTextPane);
        clock.startClock();

        // Make the frame visible
        MainGameScreenFrame.setVisible(true);
    }

    // Set the message text pane
    public void setMessageTextPane(JTextPane messageTextPane) {
        MessageTextPane = messageTextPane;
    }

    // Append text to the message text pane
    public void setMessageTextPane(String string) {
        appendToMessageTextPane(string);
    }

    public void appendToMessageTextPane(String text) {
        StyledDocument doc = MessageTextPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), text, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // Replace the left panel with a new panel
    public void replaceWithAnyPanel(JPanel newPanel) {
        if (newPanel != null) {
            if (originalPanel == null) {
                originalPanel = (JPanel) PicturesAndTextUpdatesPane.getLeftComponent();
            }
            PicturesAndTextUpdatesPane.setLeftComponent(newPanel);
            PicturesAndTextUpdatesPane.revalidate();
            PicturesAndTextUpdatesPane.repaint();
        } else {
            throw new IllegalArgumentException("Panel cannot be null");
        }
    }

    // Restore the original left panel
    public void restoreOriginalPanel() {
        if (originalPanel != null) {
            PicturesAndTextUpdatesPane.setLeftComponent(originalPanel);
            PicturesAndTextUpdatesPane.revalidate();
            PicturesAndTextUpdatesPane.repaint();
        } else {
            throw new IllegalStateException("No original panel to restore.");
        }
    }

    // Get the game images and combat panel
    public JPanel getGameImagesAndCombatPanel() {
        return GameImagesAndCombatPanel;
    }

    // Get the game images and combat canvas
    public Canvas getGameImagesAndCombatCanvas() {
        return gameImagesAndCombatCanvas;
    }

    // Main method to start the game screen
    public static void main(String[] args) throws IOException {
        new MainGameScreen();
    }
}
