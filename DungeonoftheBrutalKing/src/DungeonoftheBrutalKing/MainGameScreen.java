
package DungeonoftheBrutalKing;

import DungeonoftheBrutalKing.GameEngine.Camera;
import DungeonoftheBrutalKing.GameEngine.Game;
import DungeonoftheBrutalKing.Maps.DungeonLevel;
import DungeonoftheBrutalKing.Narrative.Core.QuestHooks;
import DungeonoftheBrutalKing.Narrative.Core.QuestManager;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.MusicPlayer;
import DungeonoftheBrutalKing.SharedData.SettingsAndPreferences;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;

public class MainGameScreen extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1L;
    private static MainGameScreen instance;

    private double preCombatX, preCombatY, postCombatX, postCombatY;
    private final Character myChar = Character.getInstance();
    private final GameSettings myGameSettings = GameSettings.getInstance();
    private final LoadSaveGame myGameState = new LoadSaveGame();
    private final GameMenuItems myGameMenuItems = new GameMenuItems();

    private JFrame mainFrame;
    private JPanel characterInfoPanel, characterHeaderPanel, characterStatsPanel, characterFooterPanel, gameImagesAndCombatPanel;
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

    // Quest system wiring
    private QuestManager questManager;
    private QuestHooks questHooks;

    // Quest event state
    private Integer lastKnownDungeonLevel = null;

    public static synchronized MainGameScreen getInstance() throws IOException, InterruptedException, ParseException {
        if (instance == null) {
            instance = new MainGameScreen();
            instance.initGame();
        }
        return instance;
    }

    private MainGameScreen() throws IOException {
        setupFrame();
        setupMenuBar();
        setupMenusAndItems();
        setupDevToolsMenu();
        setupPanels();
        initQuestSystem();
        setupSplitPane();
        setupTimer();
        setupClock();
        updateCombatMessageArea(clock.getCurrentTimeString());
    }

    private void initQuestSystem() {
        this.questManager = new QuestManager();
        this.questHooks = new QuestHooks(questManager);
        appendToMessageTextPane("\nQuest system initialized.\n");
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public QuestHooks getQuestHooks() {
        return questHooks;
    }

    private void initGame() {
        try {
            game = new Game();
            renderPanel = game.getRenderPanel();
            camera = game.getCamera();
            currentDungeonLevel = game.getCurrentDungeonLevelInstance();

            double startX = myChar.getX();
            double startY = myChar.getY();
            if (camera != null) {
                camera.setPosition(startX, startY);
            }

            replaceWithAnyPanel(renderPanel);
            if (renderPanel != null) {
                renderPanel.addKeyListener(this);
                focusRenderPanel();
            }

            Timer renderTimer = new Timer(16, _ -> {
                if (renderPanel != null) renderPanel.repaint();
            });
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
            if (renderPanel != null) renderPanel.repaint();
        }
        pollQuestWorldState();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (camera != null) {
            camera.keyReleased(e);
            if (renderPanel != null) renderPanel.repaint();
        }
        pollQuestWorldState();
    }

    @Override
    public void keyTyped(KeyEvent e) { }

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
        characterInfoPanel = new JPanel(new BorderLayout());
        characterHeaderPanel = new JPanel(new BorderLayout());
        characterStatsPanel = new JPanel(new BorderLayout());
        characterFooterPanel = new JPanel(new BorderLayout());
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        int infoWidth = 1600;
        charNameClassLevelField = createTextField(myGameSettings.getFontTimesNewRoman(), myGameSettings.getColorGreen(), myGameSettings.getColorWhite(), 3, false);
        charNameClassLevelField.setPreferredSize(new Dimension(infoWidth, 28));
        charNameClassLevelField.setMinimumSize(new Dimension(infoWidth, 28));
        charNameClassLevelField.setMaximumSize(new Dimension(Short.MAX_VALUE, 28));
        charStatsField = createTextPane(new Font("Monospaced", Font.BOLD, 16), myGameSettings.getColorBlue(), myGameSettings.getColorWhite(), 60, false);
        charStats2Field = createTextPane(new Font("Monospaced", Font.PLAIN, 16), myGameSettings.getColorBlue(), myGameSettings.getColorWhite(), 60, false);
        charXPHPGoldField = createTextField(myGameSettings.getFontTimesNewRoman(), myGameSettings.getColorPurple(), myGameSettings.getColorWhite(), 3, false);

        characterInfoPanel.add(characterHeaderPanel, BorderLayout.NORTH);
        characterInfoPanel.add(characterStatsPanel, BorderLayout.CENTER);
        characterInfoPanel.add(characterFooterPanel, BorderLayout.SOUTH);
        characterHeaderPanel.add(charNameClassLevelField, BorderLayout.CENTER);

        JScrollPane statsScroll1 = new JScrollPane(charStatsField, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane statsScroll2 = new JScrollPane(charStats2Field, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        statsScroll1.setPreferredSize(new Dimension(infoWidth, 24));
        statsScroll2.setPreferredSize(new Dimension(infoWidth, 24));
        characterStatsPanel.add(statsScroll1, BorderLayout.NORTH);
        characterStatsPanel.add(statsScroll2, BorderLayout.SOUTH);
        characterFooterPanel.add(charXPHPGoldField);
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
        return new TabSet(new TabStop[]{
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
            if (questHooks != null) questHooks.onUiAction("DISPLAY_ACTIVE_QUESTS");
            appendToMessageTextPane("\nActive Quests:\n");
            if (questManager != null) {
                questManager.displayActiveQuests(MainGameScreen::appendToMessageTextPane);
            } else {
                appendToMessageTextPane("Quest system not available.\n");
            }
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

        JButton teleportButton = new JButton("Teleport Character");
        teleportButton.addActionListener(_ -> JOptionPane.showMessageDialog(devDialog, "Teleport Character tool launched."));
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
                // New game initialization logic here
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(mainFrame,
            "Dungeon of the Brutal King\nVersion 1.0\n© 2024",
            "About",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void showHelpDialog() {
        JOptionPane.showMessageDialog(mainFrame,
            "Use W/A/S/D or Arrow Keys to move.\nESC to open menu.",
            "Help",
            JOptionPane.INFORMATION_MESSAGE);
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
        mainFrame.add(characterInfoPanel, BorderLayout.NORTH);
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

            charXPHPGoldField.setText(
                String.format(
                    "X:%.0f\tY:%.0f\tHP:%d\tGold:%d\t%s:%d",
                    (double) myChar.getX(),
                    (double) myChar.getY(),
                    myChar.getHitPoints(),
                    myChar.getGold(),
                    ("Mage".equals(myChar.getClassName()) || "Wizard".equals(myChar.getClassName())) ? "MP" : "AP",
                    getMagicOrActionPoints()
                )
            );

            pollQuestWorldState();
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
        if (messageTextPane == null || text == null || text.isEmpty()) return;

        String normalized = text.replace("\r\n", "\n").replace('\r', '\n').strip();
        if (normalized.isEmpty()) return;
        if (!normalized.endsWith(" ")) {
            normalized += " ";
        }
        // Enforce exactly one newline separator between appended messages.
        normalized += "\n";

        StyledDocument doc = messageTextPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), normalized, null);
            messageTextPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void replaceWithAnyPanel(JPanel newPanel) {
        if (newPanel != null) {
            try {
                if (picturesAndTextUpdatesPane != null) {
                    picturesAndTextUpdatesPane.setLeftComponent(newPanel);
                    picturesAndTextUpdatesPane.revalidate();
                    picturesAndTextUpdatesPane.repaint();
                    focusRenderPanel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void focusRenderPanel() {
        if (renderPanel == null || picturesAndTextUpdatesPane == null) return;

        Runnable focusTask = () -> {
            if (picturesAndTextUpdatesPane.getLeftComponent() == renderPanel) {
                renderPanel.revalidate();
                renderPanel.repaint();
                renderPanel.setFocusable(true);
                if (!renderPanel.requestFocusInWindow()) {
                    renderPanel.requestFocus();
                }

                // One more deferred request helps after split-pane component swaps.
                SwingUtilities.invokeLater(() -> {
                    if (picturesAndTextUpdatesPane.getLeftComponent() == renderPanel) {
                        renderPanel.requestFocusInWindow();
                    }
                });
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            focusTask.run();
        } else {
            SwingUtilities.invokeLater(focusTask);
        }
    }

    public int getMagicOrActionPoints() {
        String className = myChar.getClassName();
        if ("Mage".equals(className) || "Wizard".equals(className) || "Cleric".equals(className)) {
            return myChar.getMagicPoints();
        }

        int ap = myChar.getActionPoints();
        if (ap <= 0) {
            ap = Math.max(1, myChar.getStamina());
        }
        return ap;
    }

    public void savePlayerPosition() {
        savedPlayerX = myChar.getX();
        savedPlayerY = myChar.getY();
        if (currentDungeonLevel != null) {
            savedDungeonLevel = currentDungeonLevel.getCurrentDungeonLevel();
        }
    }

    public static void restorePlayerPosition() {
        if (camera == null || currentDungeonLevel == null) return;
        if ((savedPlayerX != 0 || savedPlayerY != 0) && !(Double.isNaN(savedPlayerX) || Double.isNaN(savedPlayerY))) {
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
            if (camera != null) {
                camera.resetMovementFlags();
            }
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
            focusRenderPanel();
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
        if (camera != null) {
            preCombatX = camera.xPos;
            preCombatY = camera.yPos;
        }
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

    private void pollQuestWorldState() {
        if (questHooks == null || currentDungeonLevel == null) return;

        int level;
        try {
            level = currentDungeonLevel.getCurrentDungeonLevel();
        } catch (Exception ignored) {
            return;
        }

        if (lastKnownDungeonLevel == null || lastKnownDungeonLevel != level) {
            lastKnownDungeonLevel = level;
            questHooks.onLocationEnter("DUNGEON_LEVEL_" + level);
        }
    }
}
