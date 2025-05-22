
// Package declaration for the project
package DungeonoftheBrutalKing;

// Import statements for required libraries and classes
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Objects;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import GameEngine.Game;
import Maps.*;

// MainGameScreen class extends JFrame to create the main game window
public class MainGameScreen extends JFrame {

    // JPanel for rendering game visuals
    private JPanel renderPanel;

    // Serial version UID for serialization
    private static final long serialVersionUID = 1L;

    // Singleton instance of the character
    Charecter myChar = Charecter.Singleton();

    // Game settings instance for managing preferences
    GameSettings myGameSettings = new GameSettings();

    // Instance for managing game state (saving/loading)
    LoadSaveGame myGameState = new LoadSaveGame();

    // Instance for handling game menu items
    GameMenuItems myGameMenuItems = new GameMenuItems();

    // Instance for character creation
    CharacterCreation myCharacterCreation;

    // JFrame for the main game screen
    JFrame MainGameScreenFrame = null;

    // Panels for organizing UI components
    JPanel p1Panel, p2Panel, p3Panel, p4Panel = null;
    public JPanel GameImagesAndCombatPanel = null;
    private JPanel originalPanel;

    // Text fields for displaying character information
    JTextField CharNameClassLevelField, CharStatsField, CharStats2Field, CharXPHPGoldField = null;

    // Text pane for displaying game messages
    JTextPane MessageTextPane = null;

    // Menu bar and menu items for the game
    JMenuBar menuBar = null;
    JMenu gameMenu, charecterMenu, settingsMenu, helpMenu = null;
    JMenuItem newGameMenuItem, LoadSavedGameMenuItem, saveMenuItem, exitGameMenuItem, charecterstatsMenuItem,
            charecterinventoryMenuItem, mapMenu, gameSettingsMenuItem, aboutMenuItem, helpMenuItem, mapFloor1MenuItem,
            mapFloor2MenuItem, mapFloor3MenuItem, mapFloor4MenuItem = null;

    // Split pane for dividing game visuals and text updates
    JSplitPane PicturesAndTextUpdatesPane = null;

    // Screen dimensions
    Dimension screenSize = null;
    int width, height = 0;

    // Timer for updating character stats periodically
    Timer timer = null;

    // Instance of TimeClock for managing in-game time
    private TimeClock clock;

    // Canvas for rendering game images and combat
    private Canvas gameImagesAndCombatCanvas;

    // Text area for displaying combat messages
    public JTextArea CombatMessageArea = new JTextArea();

    // Constructor to initialize the main game screen
    public MainGameScreen() throws IOException {

        // Creating the main game frame
        MainGameScreenFrame = new JFrame("Dungeon of the Brutal King");

        // Get the screen size
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) size.getWidth(); // Store screen width
        height = (int) size.getHeight(); // Store screen height

        // Set frame preferences and settings
        MainGameScreenFrame.setSize(width, height);
        MainGameScreenFrame.setLayout(new BorderLayout());
        MainGameScreenFrame.setForeground(myGameSettings.colorBrown);
        MainGameScreenFrame.setUndecorated(true);
        MainGameScreenFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Initialize panels for organizing UI components
        p1Panel = new JPanel(new BorderLayout());
        p2Panel = new JPanel(new BorderLayout());
        p3Panel = new JPanel(new BorderLayout());
        p4Panel = new JPanel(new BorderLayout());
        GameImagesAndCombatPanel = new JPanel(new BorderLayout());

        // Load character data from the most recently saved game
        try {
            myGameState.StartGameLoadCharecter();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        // Initialize text fields for character information
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

        // Timer to periodically update character stats
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
        timer = new Timer(100, task); // Execute task every 100 milliseconds
        timer.setRepeats(true);
        timer.start();

        // Initialize the menu bar and menu items
        menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(25, 35));
        menuBar.setFont(new Font("sans-serif", Font.ROMAN_BASELINE, 22));
        menuBar.setBackground(myGameSettings.colorPlum);

        // Add menu bar to the frame
        MainGameScreenFrame.setJMenuBar(menuBar);

        // Initialize menu headers
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        charecterMenu = new JMenu("Charecter");
        charecterMenu.setMnemonic(KeyEvent.VK_C);

        settingsMenu = new JMenu("Preferences");
        settingsMenu.setMnemonic(KeyEvent.VK_P);

        helpMenu = new JMenu("About");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        // Add menu headers to the menu bar
        menuBar.add(gameMenu);
        menuBar.add(charecterMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);

        // Initialize and add menu items to the menus
        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        newGameMenuItem.getAccessibleContext().setAccessibleDescription("New Game");
        newGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle starting a new game
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

        // Add the new game menu item to the game menu
        gameMenu.add(newGameMenuItem);

        // Initialize and add other menu items (Load, Save, Exit, etc.)
        // ...

        // Initialize the split pane for game visuals and text updates
        PicturesAndTextUpdatesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        PicturesAndTextUpdatesPane.setDividerLocation(width - 200);
        PicturesAndTextUpdatesPane.setResizeWeight(.90d);
        PicturesAndTextUpdatesPane.setLeftComponent(GameImagesAndCombatPanel);
        PicturesAndTextUpdatesPane.setRightComponent(MessageTextPane);

        // Add components to the main frame
        MainGameScreenFrame.add(PicturesAndTextUpdatesPane, BorderLayout.CENTER);
        MainGameScreenFrame.add(p1Panel, BorderLayout.NORTH);

        // Initialize and start the in-game clock
        clock = new TimeClock(TimeClock.Month.REBIRTH, MessageTextPane);
        clock.startClock();

        // Make the main frame visible
        MainGameScreenFrame.setVisible(true);
    }

    // Setter for the message text pane
    public void setMessageTextPane(JTextPane messageTextPane) {
        MessageTextPane = messageTextPane;
    }

    // Overloaded setter for the message text pane with a string
    public void setMessageTextPane(String string) {
        appendToMessageTextPane(string);
    }

    // Method to append text to the message text pane
    public void appendToMessageTextPane(String text) {
        StyledDocument doc = MessageTextPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), text, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // Method to replace the left component of the split pane with a new panel
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

    // Method to restore the original panel in the split pane
    public void restoreOriginalPanel() {
        if (originalPanel != null) {
            PicturesAndTextUpdatesPane.setLeftComponent(originalPanel);
            PicturesAndTextUpdatesPane.revalidate();
            PicturesAndTextUpdatesPane.repaint();
        } else {
            throw new IllegalStateException("No original panel to restore.");
        }
    }

    // Getter for the game images and combat panel
    public JPanel getGameImagesAndCombatPanel() {
        return GameImagesAndCombatPanel;
    }

    // Getter for the game images and combat canvas
    public Canvas getGameImagesAndCombatCanvas() {
        return gameImagesAndCombatCanvas;
    }

    // Main method to launch the game screen
    public static void main(String[] args) throws IOException {
        new MainGameScreen();
    }
}
