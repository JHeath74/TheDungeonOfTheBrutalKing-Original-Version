
// src/DungeonoftheBrutalKing/Combat.java
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The Combat class handles the combat encounters in the game.
 * It sets up the combat UI, manages combat logic, and interacts with the player and enemies.
 */
public class Combat extends JFrame {

    private static final long serialVersionUID = 1L;

    // Singleton instance for character
    Singleton myCharSingleton = new Singleton();

    // Game settings instance
    GameSettings myGameSettings = new GameSettings();

    // Main game screen reference
    private MainGameScreen myMainGameScreen = null;

    // Character and enemy instances
    Charecter myChar = new Charecter();
    Enemies myEnemies = new Enemies();

    // Variable to store the selected spell
    private String selectedSpell = null;

    // Hero's HP as a string
    String HeroHPArrayList = "";

    // UI components
    public JFrame CombatFrame, spellsFrame;
    public JPanel CombatPanel, CombatImagePanel, CombatPanelButtons, CombatPanelCombatAreaPanel,
            CombatUpdateInfoPanel, CombatNameAndHPPanel, spelllistbox = null;
    public JSplitPane CombatImageAndCombatUpdatesStatsSplitPane, CombatCombatUpdatesAndStatsSplitPane = null;
    public JTextArea CombatCombatTextArea, CombatNameAndHPfield = null;
    public JButton CombatAttackButton, CastSelectedSpellButton, CombatSpellButton, CombatRunButton, SelectSpellToCast = null;
    public JLabel picLabel = null;

    // Combat-related variables
    public int width, height, HP, HeroHP, CharrandomCombatChance, MonsterrandomCombatChance = 0;
    public Dimension imageSize = null;
    public BufferedImage myPictureBufferedImage = null;
    public Timer timer = null;
    public String[] spellList = null;

    /**
     * Constructor for the Combat class.
     * Initializes the hero's HP and sets up the combat environment.
     */
    public Combat() throws IOException {
        // Retrieve hero's HP from character info
        HeroHPArrayList = myChar.CharInfo.size() > 4 ? myChar.CharInfo.get(4) : "0";
        HeroHP = Integer.parseInt(HeroHPArrayList);
    }

    /**
     * Sets up and starts a combat encounter.
     */
    public void CombatEncouter() throws IOException {
        // Select a random monster for the encounter
        MonsterSelector monsterSelector = new MonsterSelector();
        Object randomMonster = monsterSelector.selectRandomMonster();

        // Initialize the main game screen
        MainGameScreen myMainGameScreen = new MainGameScreen();

        // Set up the combat UI components
        CombatPanel = new JPanel(new BorderLayout());
        myMainGameScreen.replaceWithAnyPanel(CombatPanel);
        CombatImagePanel = new JPanel(); // Panel for displaying the enemy image
        CombatPanelButtons = new JPanel(new FlowLayout()); // Panel for combat buttons
        CombatPanelCombatAreaPanel = new JPanel(); // Panel for combat updates
        CombatUpdateInfoPanel = new JPanel(); // Panel for player stats
        CombatNameAndHPPanel = new JPanel(new FlowLayout()); // Panel for name and HP display

        // Set up split panes for layout
        CombatImageAndCombatUpdatesStatsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        CombatCombatUpdatesAndStatsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        // Set up text areas for combat outcomes and name/HP display
        CombatCombatTextArea = new JTextArea();
        CombatNameAndHPfield = new JTextArea();

        // Set up combat buttons
        CombatAttackButton = new JButton("Attack");
        CastSelectedSpellButton = new JButton("Cast Selected Spell");
        CombatSpellButton = new JButton("Select Spell to Cast");
        CombatRunButton = new JButton("Run Away!");

        // Load and display the enemy image
        try {
            myPictureBufferedImage = ImageIO.read(new File(myGameSettings.MonsterImagePath + myEnemies.getMonsterImagePath() + ".png"));
            picLabel = new JLabel(new ImageIcon(myPictureBufferedImage));
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            picLabel = new JLabel("Image not found");
        }

        // Set image size and add components to panels
        imageSize = new Dimension();
        imageSize.setSize(768, 1024);
        picLabel.setPreferredSize(imageSize);

        CombatPanel.add(CombatPanelButtons, BorderLayout.SOUTH);
        CombatPanel.add(CombatImageAndCombatUpdatesStatsSplitPane, BorderLayout.CENTER);
        CombatPanelButtons.add(CombatAttackButton);
        CombatPanelButtons.add(CastSelectedSpellButton);
        CombatPanelButtons.add(CombatSpellButton);
        CombatPanelButtons.add(CombatRunButton);
        CombatPanelCombatAreaPanel.add(CombatCombatTextArea);
        CombatNameAndHPPanel.add(CombatNameAndHPfield);
        CombatUpdateInfoPanel.add(CombatPanelCombatAreaPanel);

        CombatImageAndCombatUpdatesStatsSplitPane.setLeftComponent(CombatImagePanel);
        CombatImageAndCombatUpdatesStatsSplitPane.setRightComponent(CombatCombatUpdatesAndStatsSplitPane);
        CombatCombatUpdatesAndStatsSplitPane.setTopComponent(CombatNameAndHPPanel);
        CombatCombatUpdatesAndStatsSplitPane.setBottomComponent(CombatUpdateInfoPanel);

        // Configure split panes
        CombatImageAndCombatUpdatesStatsSplitPane.setDividerLocation(0.5);
        CombatCombatUpdatesAndStatsSplitPane.setDividerLocation(0.25);

        // Configure text areas
        CombatNameAndHPfield.setLineWrap(false);
        CombatNameAndHPfield.setEditable(false);
        CombatNameAndHPfield.setBackground(myGameSettings.colorLightYellow);
        CombatNameAndHPPanel.setBackground(myGameSettings.colorCoral);

        // Set up a timer for periodic updates
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // TODO: Add periodic update logic
            }
        };
        timer = new Timer(1000, task);
        timer.setRepeats(true);
        timer.start();

        // Add action listeners for combat buttons
        CombatAttackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Add attack logic
            }
        });

        CombatSpellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Add spell selection logic
            }
        });

        CastSelectedSpellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Add spell casting logic
            }
        });
    }

    public static void main(String[] args) {
        try {
            // Start the combat encounter
            new Combat().CombatEncouter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
