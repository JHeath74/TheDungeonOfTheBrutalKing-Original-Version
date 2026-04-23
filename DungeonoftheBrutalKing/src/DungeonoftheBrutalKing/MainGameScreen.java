
// src/DungeonoftheBrutalKing/MainGameScreen.java
package DungeonoftheBrutalKing;

import DungeonoftheBrutalKing.GameEngine.Camera;
import DungeonoftheBrutalKing.GameEngine.Game;
import DungeonoftheBrutalKing.Maps.DungeonLevel;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.MusicPlayer;
import DungeonoftheBrutalKing.SharedData.SettingsAndPreferences;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class MainGameScreen extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1L;
    private static MainGameScreen instance;

    private double preCombatX, preCombatY, postCombatX, postCombatY;
    private final Character myChar = Character.getInstance();
    private final GameSettings myGameSettings = new GameSettings();
   // private final LoadSaveGame2 myGameState = new LoadSaveGame2();
    private final LoadSaveGame myGameState = new LoadSaveGame();
    private final GameMenuItems myGameMenuItems = new GameMenuItems();

    private JFrame mainFrame;
    private JPanel p1Panel, p2Panel, p3Panel, p4Panel, gameImagesAndCombatPanel;
    private static JPanel originalPanel;
    private JTextField charNameClassLevelField, charXPHPGoldField;
    private JTextPane charStatsField, charStats2Field;
    private static JTextPane messageTextPane;
    private JMenuBar menuBar;
    private static JSplitPane picturesAndTextUpdatesPane;
    private Dimension screenSize;
    private int width, height;
    private Timer timer;
    private TimeClock clock;
    private Canvas gameImagesAndCombatCanvas;
    public static JTextArea combatMessageArea = new JTextArea();
    private static JScrollPane combatMessageScrollPane;

    private JMenu gameMenu, characterMenu, settingsMenu, helpMenu;
    private JMenuItem newGameMenuItem, loadSavedGameMenuItem, saveMenuItem, exitGameMenuItem;
    private JMenuItem characterStatsMenuItem, characterInventoryMenuItem, displayActiveQuestsMenuItem;
    private JMenuItem gameSettingsMenuItem, aboutMenuItem, helpMenuItem;

    private static Camera camera;
    private static JPanel renderPanel;
    private Game game;
    private static DungeonLevel currentDungeonLevel;

    private JMenu devToolsMenu;
    private JMenuItem devToolsMenuItem;

    private static double savedPlayerX;
    private static double savedPlayerY;
    private static int savedDungeonLevel;

    public static MainGameScreen getInstance() throws IOException, InterruptedException, ParseException {
        if (instance == null) {
            instance = new MainGameScreen();
            instance.initGame();
        }
        return instance;
    }

    public MainGameScreen() throws IOException {
        setupFrame();
        setupPanels();
        setupMenuBar();
        setupMenusAndItems();
        setupDevToolsMenu();
        setupSplitPane();
        setupTimer();
        setupClock();
        updateCombatMessageArea(clock.getCurrentTimeString());
    }

    private void initGame() {
        try {
        	
        	
        	
            game = new Game();
            renderPanel = game.getRenderPanel();
            if (renderPanel != null) {
                renderPanel.addKeyListener(game.getCamera());
            } else {
                throw new IllegalStateException("Game renderPanel is null after construction.");
            }
            camera = game.getCamera();
            currentDungeonLevel = game.getCurrentDungeonLevelInstance();

            double startX = myChar.getX();
            double startY = myChar.getY();
            camera.setPosition(startX, startY);

            replaceWithAnyPanel(renderPanel);
            renderPanel.addKeyListener(this);
            renderPanel.setFocusable(true);
            renderPanel.requestFocusInWindow();

            Timer renderTimer = new Timer(16, _ -> renderPanel.repaint());
            renderTimer.start();

            mainFrame.setVisible(true);
            game.start();
            MusicPlayer.mp3Player("Dark_Dungeon_Ambience.mp3");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    public void keyTyped(KeyEvent e) {}

    public void updateCombatMessageArea(String text) {
        combatMessageArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        combatMessageArea.setBackground(Color.BLACK);
        combatMessageArea.setForeground(Color.GREEN);
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
        combatMessageArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        combatMessageArea.setBackground(Color.BLACK);
        combatMessageArea.setForeground(Color.GREEN);
        combatMessageArea.setRows(4);
        combatMessageArea.setLineWrap(true);
        combatMessageArea.setWrapStyleWord(true);

        try {
            myGameState.StartGameLoadCharacter();
            if (!myChar.getCharInfo().isEmpty()) {
                myChar.setName(myChar.getCharInfo().get(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Make the name/class/level field and stats fields very wide
        int infoWidth = 1600;
        charNameClassLevelField = createTextField(myGameSettings.getFontTimesNewRoman(), myGameSettings.getColorGreen(), myGameSettings.getColorWhite(), 3, false);
        charNameClassLevelField.setPreferredSize(new Dimension(infoWidth, 28));
        charNameClassLevelField.setMinimumSize(new Dimension(infoWidth, 28));
        charNameClassLevelField.setMaximumSize(new Dimension(Short.MAX_VALUE, 28));
        charStatsField = createTextPane(new Font("Monospaced", Font.BOLD, 16), myGameSettings.getColorBlue(), myGameSettings.getColorWhite(), 60, false);
        charStats2Field = createTextPane(new Font("Monospaced", Font.PLAIN, 16), myGameSettings.getColorBlue(), myGameSettings.getColorWhite(), 60, false);
        charXPHPGoldField = createTextField(myGameSettings.getFontTimesNewRoman(), myGameSettings.getColorPurple(), myGameSettings.getColorWhite(), 3, false);

        p1Panel.add(p2Panel, BorderLayout.NORTH);
        p1Panel.add(p3Panel, BorderLayout.CENTER);
        p1Panel.add(p4Panel, BorderLayout.SOUTH);
        p2Panel.add(charNameClassLevelField, BorderLayout.CENTER);

        JScrollPane statsScroll1 = new JScrollPane(charStatsField, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane statsScroll2 = new JScrollPane(charStats2Field, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        statsScroll1.setPreferredSize(new Dimension(infoWidth, 24));
        statsScroll2.setPreferredSize(new Dimension(infoWidth, 24));
        p3Panel.add(statsScroll1, BorderLayout.NORTH);
        p3Panel.add(statsScroll2, BorderLayout.SOUTH);
        p4Panel.add(charXPHPGoldField);
        
        ArrayList<String> charInfo = myChar.getCharInfo();
    	StringBuilder info = new StringBuilder("Character Info:\n");
    	for (int i = 0; i < charInfo.size(); i++) {
    	    info.append("[").append(i).append("]: ").append(charInfo.get(i)).append("\n");
    	}
    	JOptionPane.showMessageDialog(null, info.toString());
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

    private JTextPane createTextPane(Font font, Color bg, Color fg, int columns, boolean editable) {
        JTextPane pane = new JTextPane();
        pane.setFont(font);
        pane.setBackground(bg);
        pane.setForeground(fg);
        pane.setEditable(editable);

        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setTabSet(sas, createStatsTabSet());
        pane.setParagraphAttributes(sas, true);

        return pane;
    }

    private TabSet createStatsTabSet() {
        return new TabSet(new TabStop[] {
            new TabStop(150f, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE),
            new TabStop(300f, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE),
            new TabStop(450f, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE),
            new TabStop(600f, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE),
            new TabStop(800f, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE),
            new TabStop(1000f, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE),
            new TabStop(1200f, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE),
        });
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
                myGameState.SaveGame(null);
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

    private void setupDevToolsMenu() {
        devToolsMenu = new JMenu("Dev Tools");
        devToolsMenu.setMnemonic(KeyEvent.VK_D);

        devToolsMenuItem = new JMenuItem("Open Dev Tools");
        devToolsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        devToolsMenuItem.getAccessibleContext().setAccessibleDescription("Open developer tools");
        devToolsMenuItem.addActionListener(_ -> showDevToolsDialog());

        devToolsMenu.add(devToolsMenuItem);
        menuBar.add(devToolsMenu);
    }

    private void showDevToolsDialog() {
        JDialog devDialog = new JDialog(mainFrame, "Developer Tools", true);
        devDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton teleportButton = new JButton("Teleport Charecter");
        teleportButton.addActionListener(_ -> {
            JOptionPane.showMessageDialog(devDialog, "Teleport Charecter tool launched.");
        });

        panel.add(teleportButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(_ -> devDialog.dispose());
        panel.add(Box.createVerticalStrut(20));
        panel.add(closeButton);

        devDialog.getContentPane().add(panel);
        devDialog.pack();
        devDialog.setLocationRelativeTo(mainFrame);
        devDialog.setVisible(true);
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
                CharacterCreation characterCreation = new CharacterCreation();
                characterCreation.createCharector();
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

        InputStream stream = getClass().getResourceAsStream("/DungeonoftheBrutalKing/TextFiles/About.txt");

        if (stream == null) {
            JOptionPane.showMessageDialog(this, "About file not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
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

        InputStream stream = getClass().getResourceAsStream("/DungeonoftheBrutalKing/TextFiles/Help.txt");

        if (stream == null) {
            JOptionPane.showMessageDialog(this, "Help file not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
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
        rightPanel.setPreferredSize(new Dimension(600, 200));
        rightPanel.setMinimumSize(new Dimension(600, 200));
        rightPanel.add(new JScrollPane(messageTextPane), BorderLayout.CENTER);

        combatMessageScrollPane = new JScrollPane(combatMessageArea);
        combatMessageScrollPane.setPreferredSize(new Dimension(600, 75));
        combatMessageScrollPane.setMinimumSize(new Dimension(600, 75));
        rightPanel.add(combatMessageScrollPane, BorderLayout.SOUTH);

        picturesAndTextUpdatesPane.setRightComponent(rightPanel);

        mainFrame.add(picturesAndTextUpdatesPane, BorderLayout.CENTER);
        mainFrame.add(p1Panel, BorderLayout.NORTH);
    }

    private void setupTimer() {
        ActionListener task = _ -> {
            if (myChar.getCharInfo().size() >= 5) {
                charNameClassLevelField.setText(
                    String.format("Name: %-20s  Class: %-15s  Race: %-15s  Level: %-10s  XP: %-10s",
                        myChar.getCharInfo().get(0),
                        myChar.getCharInfo().get(1),
                        myChar.getCharInfo().get(2),
                        myChar.getCharInfo().get(3),
                        myChar.getCharInfo().get(4))
                );
            }

            String header = "Vitality\tStamina\tCharisma\tStrength\tIntelligence\tWisdom\tAgility";
            String values = String.format("%d\t%d\t%d\t%d\t%d\t%d\t%d",
                myChar.getVitality(), myChar.getStamina(), myChar.getCharisma(), myChar.getStrength(),
                myChar.getIntelligence(), myChar.getWisdom(), myChar.getAgility());

            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attr, "Monospaced");
            StyleConstants.setFontSize(attr, 16);
            StyleConstants.setBold(attr, true);
            StyleConstants.setForeground(attr, Color.GREEN);

            TabStop[] tabs = new TabStop[] {
                new TabStop(150f), new TabStop(300f), new TabStop(450f),
                new TabStop(600f), new TabStop(800f), new TabStop(1000f), new TabStop(1200f)
            };
            StyleConstants.setTabSet(attr, new TabSet(tabs));

            charStatsField.setText("");
            charStats2Field.setText("");
            charStatsField.setCharacterAttributes(attr, true);
            charStats2Field.setCharacterAttributes(attr, true);

            try {
                charStatsField.getDocument().insertString(0, header, attr);
                charStats2Field.getDocument().insertString(0, values, attr);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        };

        timer = new Timer(100, task);
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

    public static void replaceWithAnyPanel(JPanel newPanel) {
        if (newPanel != null) {
            try {
                if (picturesAndTextUpdatesPane != null) {
                    picturesAndTextUpdatesPane.setLeftComponent(newPanel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getMagicOrActionPoints() {
        String className = myChar.getClassName();
        if ("Mage".equals(className) || "Wizard".equals(className)) {
            return myChar.getMagicPoints();
        } else {
            return myChar.getActionPoints();
        }
    }

    public void savePlayerPosition() {
        savedPlayerX = myChar.getX();
        savedPlayerY = myChar.getY();
        savedDungeonLevel = currentDungeonLevel.getCurrentDungeonLevel();
    }

    public static void restorePlayerPosition() {
        if ((savedPlayerX != 0 || savedPlayerY != 0) &&
            !(Double.isNaN(savedPlayerX) || Double.isNaN(savedPlayerY))) {
            if (currentDungeonLevel.getCurrentDungeonLevel() != savedDungeonLevel) {
                 currentDungeonLevel.setCurrentDungeonLevel(savedDungeonLevel);
            }
            camera.setPosition(savedPlayerX, savedPlayerY);
        }
    }

    public void restoreOriginalPanel() {
        if (originalPanel != null && picturesAndTextUpdatesPane != null) {
            picturesAndTextUpdatesPane.setLeftComponent(renderPanel);
            restorePlayerPosition();
            if (combatMessageScrollPane != null) {
                combatMessageScrollPane.setPreferredSize(new Dimension(600, 75));
                combatMessageScrollPane.setMinimumSize(new Dimension(600, 75));
                Container parent = combatMessageScrollPane.getParent();
                if (parent instanceof JPanel) {
                    parent.setPreferredSize(new Dimension(600, 200));
                    parent.setMinimumSize(new Dimension(600, 200));
                    parent.revalidate();
                    parent.repaint();
                }
                combatMessageScrollPane.revalidate();
                combatMessageScrollPane.repaint();
            }
            picturesAndTextUpdatesPane.revalidate();
            picturesAndTextUpdatesPane.repaint();
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

    public Character getPlayer() {
        return myChar;
    }

    public void savePreCombatPosition() {
        preCombatX = camera.xPos;
        preCombatY = camera.yPos;
    }

    public void savePostCombatPosition() {
        postCombatX = camera.xPos;
        postCombatY = camera.yPos;
    }

    public String getPreCombatPosition() {
        return "(" + preCombatX + ", " + preCombatY + ")";
    }

    public String getPostCombatPosition() {
        return "(" + postCombatX + ", " + postCombatY + ")";
    }
}
