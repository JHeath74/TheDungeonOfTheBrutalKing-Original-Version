
// File: MainGameScreen.java
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.text.*;

import GameEngine.Game;
import GameEngine.Camera;
import SharedData.GameSettings;
import SharedData.SettingsAndPreferences;

public class MainGameScreen extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1L;
    private static MainGameScreen instance;

    // Core game objects
    private final Charecter myChar = Charecter.Singleton();
    private final GameSettings myGameSettings = new GameSettings();
    private final LoadSaveGame myGameState = new LoadSaveGame();
    private final GameMenuItems myGameMenuItems = new GameMenuItems();
    private CharacterCreation myCharacterCreation = new CharacterCreation();

    // UI components
    private JFrame mainFrame;
    private JPanel p1Panel, p2Panel, p3Panel, p4Panel, gameImagesAndCombatPanel, originalPanel;
    private JTextField charNameClassLevelField, charStatsField, charStats2Field, charXPHPGoldField;
    private static JTextPane messageTextPane;
    private JMenuBar menuBar;
    private JSplitPane picturesAndTextUpdatesPane;
    private Dimension screenSize;
    private int width, height;
    private Timer timer;
    private TimeClock clock;
    private Canvas gameImagesAndCombatCanvas;
    public static JTextArea combatMessageArea = new JTextArea();

    // Menus and items
    private JMenu gameMenu, characterMenu, settingsMenu, helpMenu;
    private JMenuItem newGameMenuItem, loadSavedGameMenuItem, saveMenuItem, exitGameMenuItem;
    private JMenuItem characterStatsMenuItem, characterInventoryMenuItem, displayActiveQuestsMenuItem;
    private JMenuItem gameSettingsMenuItem, aboutMenuItem, helpMenuItem;

    // Key handling
    private Camera camera;
    private JPanel renderPanel;

    public static MainGameScreen getInstance() throws IOException, InterruptedException, ParseException {
        if (instance == null) {
            instance = new MainGameScreen();
        }
        return instance;
    }

    public MainGameScreen() throws IOException, InterruptedException, ParseException {
        setupFrame();
        setupPanels();
        setupMenuBar();
        setupMenusAndItems();
        setupSplitPane();
        setupTimer();
        setupClock();
        
        updateCombatMessageArea(clock.getCurrentTimeString());
        
        Game game = new Game();
        renderPanel = game.getRenderPanel();
        camera = game.getCamera();

        replaceWithAnyPanel(renderPanel);

        renderPanel.addKeyListener(this);
        renderPanel.setFocusable(true);
        renderPanel.requestFocusInWindow();

        Timer renderTimer = new Timer(16, _ -> renderPanel.repaint());
        renderTimer.start();

        mainFrame.setVisible(true);
        game.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (camera != null) {
            camera.keyPressed(e);
            renderPanel.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (camera != null) {
            camera.keyReleased(e);
            renderPanel.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public void updateCombatMessageArea(String text) {
        combatMessageArea.setText(text);
    }

    private void setupFrame() {
        mainFrame = this;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        mainFrame.setSize(width, height);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setForeground(myGameSettings.getColorBrown());
        mainFrame.setUndecorated(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void setupPanels() throws IOException {
        p1Panel = new JPanel(new BorderLayout());
        p2Panel = new JPanel(new BorderLayout());
        p3Panel = new JPanel(new BorderLayout());
        p4Panel = new JPanel(new BorderLayout());
        gameImagesAndCombatPanel = new JPanel(new BorderLayout());
        originalPanel = gameImagesAndCombatPanel;

        messageTextPane = new JTextPane();
        messageTextPane.setEditable(false);
        messageTextPane.setFont(new Font("Arial", Font.PLAIN, 14));

        combatMessageArea.setEditable(false);
        combatMessageArea.setFont(new Font("Arial", Font.BOLD, 16));
        combatMessageArea.setBackground(Color.BLACK);
        combatMessageArea.setForeground(Color.GREEN);
        combatMessageArea.setRows(4);
        combatMessageArea.setLineWrap(true);
        combatMessageArea.setWrapStyleWord(true);

        try {
            myGameState.StartGameLoadCharecter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        charNameClassLevelField = createTextField(myGameSettings.getFontTimesNewRoman(), myGameSettings.getColorGreen(), myGameSettings.getColorWhite(), 3, false);
        charStatsField = createTextField(myGameSettings.getFontTimesNewRoman(), myGameSettings.getColorBlue(), myGameSettings.getColorWhite(), 0, false);
        charStats2Field = createTextField(myGameSettings.getFontTimesNewRoman(), myGameSettings.getColorBlue(), myGameSettings.getColorWhite(), 0, false);
        charXPHPGoldField = createTextField(myGameSettings.getFontTimesNewRoman(), myGameSettings.getColorPurple(), myGameSettings.getColorWhite(), 3, false);

        p1Panel.add(p2Panel, BorderLayout.NORTH);
        p1Panel.add(p3Panel, BorderLayout.CENTER);
        p1Panel.add(p4Panel, BorderLayout.SOUTH);
        p2Panel.add(charNameClassLevelField);
        p3Panel.add(charStatsField, BorderLayout.NORTH);
        p3Panel.add(charStats2Field, BorderLayout.SOUTH);
        p4Panel.add(charXPHPGoldField);
    }

    private JTextField createTextField(Font font, Color bg, Color fg, int columns, boolean editable) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBackground(bg);
        field.setForeground(fg);
        if (columns > 0) field.setColumns(columns);
        field.setEditable(editable);
        return field;
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(25, 35));
        menuBar.setFont(new Font("sans-serif", Font.ROMAN_BASELINE, 22));
        menuBar.setBackground(myGameSettings.getColorPlum());
        mainFrame.setJMenuBar(menuBar);
    }

    private void setupMenusAndItems() {
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);

        newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newGameMenuItem.getAccessibleContext().setAccessibleDescription("Start a new game");
        newGameMenuItem.addActionListener(_ -> handleNewGame());

        loadSavedGameMenuItem = new JMenuItem("Load Saved Game");
        loadSavedGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        loadSavedGameMenuItem.getAccessibleContext().setAccessibleDescription("Load a saved game");
        loadSavedGameMenuItem.addActionListener(_ -> myGameState.LoadGame());

        saveMenuItem = new JMenuItem("Save Game");
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveMenuItem.getAccessibleContext().setAccessibleDescription("Save the current game");
        saveMenuItem.addActionListener(_ -> {
            try {
                myGameState.SaveGame();
            } catch (IOException | ParseException ex) {
                ex.printStackTrace();
            }
        });

        exitGameMenuItem = new JMenuItem("Exit Game");
        exitGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        exitGameMenuItem.getAccessibleContext().setAccessibleDescription("Exit the game");
        exitGameMenuItem.addActionListener(_ -> System.exit(0));

        gameMenu.add(newGameMenuItem);
        gameMenu.add(loadSavedGameMenuItem);
        gameMenu.add(saveMenuItem);
        gameMenu.add(exitGameMenuItem);

        characterMenu = new JMenu("Character");
        characterMenu.setMnemonic(KeyEvent.VK_C);

        characterStatsMenuItem = new JMenuItem("Character Stats");
        characterStatsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
        characterStatsMenuItem.getAccessibleContext().setAccessibleDescription("View character stats");
        characterStatsMenuItem.addActionListener(_ -> myGameMenuItems.Stats());

        characterInventoryMenuItem = new JMenuItem("Character Inventory");
        characterInventoryMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        characterInventoryMenuItem.getAccessibleContext().setAccessibleDescription("View character inventory");
        characterInventoryMenuItem.addActionListener(_ -> myGameMenuItems.Inventory());

        displayActiveQuestsMenuItem = new JMenuItem("Display Active Quests");
        displayActiveQuestsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        displayActiveQuestsMenuItem.getAccessibleContext().setAccessibleDescription("Display Active Quests");
        displayActiveQuestsMenuItem.addActionListener(_ -> {
            System.out.print("Active Quests:\n");
        });

        characterMenu.add(characterStatsMenuItem);
        characterMenu.add(characterInventoryMenuItem);
        characterMenu.add(displayActiveQuestsMenuItem);

        settingsMenu = new JMenu("Preferences");
        settingsMenu.setMnemonic(KeyEvent.VK_P);

        gameSettingsMenuItem = new JMenuItem("Game Settings");
        gameSettingsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
        gameSettingsMenuItem.getAccessibleContext().setAccessibleDescription("Adjust game settings");
        gameSettingsMenuItem.addActionListener(_ -> new SettingsAndPreferences());

        settingsMenu.add(gameSettingsMenuItem);

        helpMenu = new JMenu("About");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        aboutMenuItem.getAccessibleContext().setAccessibleDescription("About the game");
        aboutMenuItem.addActionListener(_ -> showAboutDialog());

        helpMenuItem = new JMenuItem("Help");
        helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
        helpMenuItem.getAccessibleContext().setAccessibleDescription("Help information");
        helpMenuItem.addActionListener(_ -> showHelpDialog());

        helpMenu.add(aboutMenuItem);
        helpMenu.add(helpMenuItem);

        menuBar.add(gameMenu);
        menuBar.add(characterMenu);
        menuBar.add(settingsMenu);
        menuBar.add(helpMenu);
    }

    private void handleNewGame() {
        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                "Are you sure you wish to delete your current game and start a new one?",
                "Start New Game?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (result == JOptionPane.YES_OPTION) {
            dispose();
            try {
                File d = new File(GameSettings.SavedGameDirectory);
                for (File file : d.listFiles()) {
                    if (!file.isDirectory()) file.delete();
                }
                myCharacterCreation.createCharector();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showAboutDialog() {
        JDialog aboutDialog = new JDialog(mainFrame, "About Information", true);
        aboutDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(_ -> aboutDialog.dispose());

        JTextPane aboutTextPane = new JTextPane();
        aboutTextPane.setEditable(false);
        StyledDocument doc = aboutTextPane.getStyledDocument();

        Style headerStyle = doc.addStyle("Header", null);
        StyleConstants.setFontSize(headerStyle, 18);
        StyleConstants.setBold(headerStyle, true);
        StyleConstants.setForeground(headerStyle, Color.BLUE);

        Style bodyStyle = doc.addStyle("Body", null);
        StyleConstants.setFontSize(bodyStyle, 14);
        StyleConstants.setForeground(bodyStyle, Color.BLACK);

        Style footerStyle = doc.addStyle("Footer", null);
        StyleConstants.setFontSize(footerStyle, 12);
        StyleConstants.setItalic(footerStyle, true);
        StyleConstants.setForeground(footerStyle, Color.GRAY);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/about.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                doc.insertString(doc.getLength(), line + "\n", bodyStyle);
            }
        } catch (IOException | BadLocationException ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(aboutTextPane);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        aboutDialog.add(panel);
        aboutDialog.pack();
        aboutDialog.setLocationRelativeTo(mainFrame);
        aboutDialog.setVisible(true);
    }

    private void showHelpDialog() {
        JDialog helpDialog = new JDialog(mainFrame, "Help Information", true);
        helpDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(_ -> helpDialog.dispose());

        JTextPane helpTextPane = new JTextPane();
        helpTextPane.setEditable(false);
        StyledDocument doc = helpTextPane.getStyledDocument();

        Style headerStyle = doc.addStyle("Header", null);
        StyleConstants.setFontSize(headerStyle, 18);
        StyleConstants.setBold(headerStyle, true);
        StyleConstants.setForeground(headerStyle, Color.BLUE);

        Style bodyStyle = doc.addStyle("Body", null);
        StyleConstants.setFontSize(bodyStyle, 14);
        StyleConstants.setForeground(bodyStyle, Color.BLACK);

        Style footerStyle = doc.addStyle("Footer", null);
        StyleConstants.setFontSize(footerStyle, 12);
        StyleConstants.setItalic(footerStyle, true);
        StyleConstants.setForeground(footerStyle, Color.GRAY);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/help.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                doc.insertString(doc.getLength(), line + "\n", bodyStyle);
            }
        } catch (IOException | BadLocationException ex) {
            ex.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(helpTextPane);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(closeButton, BorderLayout.SOUTH);

        helpDialog.add(panel);
        helpDialog.pack();
        helpDialog.setLocationRelativeTo(mainFrame);
        helpDialog.setVisible(true);
    }

    private void setupSplitPane() {
        picturesAndTextUpdatesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        picturesAndTextUpdatesPane.setDividerLocation(width - 200);
        picturesAndTextUpdatesPane.setResizeWeight(.90d);
        
        picturesAndTextUpdatesPane.setLeftComponent(gameImagesAndCombatPanel);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JScrollPane(messageTextPane), BorderLayout.CENTER);

        JScrollPane combatScrollPane = new JScrollPane(combatMessageArea);
        combatScrollPane.setPreferredSize(new Dimension(0, 75));
        rightPanel.add(combatScrollPane, BorderLayout.SOUTH);

        picturesAndTextUpdatesPane.setRightComponent(rightPanel);

        mainFrame.add(picturesAndTextUpdatesPane, BorderLayout.CENTER);
        mainFrame.add(p1Panel, BorderLayout.NORTH);
    }

    private void setupTimer() {
        ActionListener task = _ -> {
            String mpOrApLabel;
            if (myChar.CharInfo.get(1).equals("Mage") || myChar.CharInfo.get(1).equals("Wizard")) {
                mpOrApLabel = "Magic Points: ";
            } else {
                mpOrApLabel = "Action Points: ";
            }

            charNameClassLevelField.setText(
                "Name: " + myChar.CharInfo.get(0) + "\t\t" +
                "Class: " + myChar.CharInfo.get(1) + "\t\t" +
                "Race: " + myChar.CharInfo.get(2) + "\t\t" +
                "Level: " + myChar.CharInfo.get(3) + "\t\t" +
                "XP: " + myChar.CharInfo.get(4)
            );
            charStatsField.setText(
                "Stamina\t\tCharisma\t\tStrength\t\tIntelligence\t\tWisdom\t\tAgility"
            );
            charStats2Field.setText(
                myChar.CharInfo.get(7) + "\t\t" +
                myChar.CharInfo.get(8) + "\t\t" +
                myChar.CharInfo.get(9) + "\t\t" +
                myChar.CharInfo.get(10) + "\t\t" +
                myChar.CharInfo.get(11) + "\t\t" +
                myChar.CharInfo.get(12)
            );
            charXPHPGoldField.setText(
                "Hit Points: " + myChar.CharInfo.get(5) + "\t\t" +
                mpOrApLabel + myChar.CharInfo.get(6) + "\t\t" +
                "Gold: " + myChar.CharInfo.get(9)
            );
        };

        timer = new Timer(1000, task);
        timer.setRepeats(true);
        timer.start();
    }

    private void setupClock() {
        clock = new TimeClock(TimeClock.Month.REBIRTH, messageTextPane, this);
        clock.startClock();
    }

    public void setMessageTextPane(JTextPane pane) {
        messageTextPane = pane;
    }

    public void setMessageTextPane(String string) {
        appendToMessageTextPane(string);
    }

    public static void appendToMessageTextPane(String text) {
        StyledDocument doc = messageTextPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), text, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void replaceWithAnyPanel(JPanel newPanel) {
        if (newPanel != null) {
            if (picturesAndTextUpdatesPane != null) {
                picturesAndTextUpdatesPane.setLeftComponent(newPanel);
            }
        }
    }

    public void restoreOriginalPanel() {
        if (originalPanel != null && picturesAndTextUpdatesPane != null) {
            picturesAndTextUpdatesPane.setLeftComponent(originalPanel);
        }
    }

    public JPanel getGameImagesAndCombatPanel() {
        return gameImagesAndCombatPanel;
    }

    public Canvas getGameImagesAndCombatCanvas() {
        return gameImagesAndCombatCanvas;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        MainGameScreen.getInstance();
    }
}
