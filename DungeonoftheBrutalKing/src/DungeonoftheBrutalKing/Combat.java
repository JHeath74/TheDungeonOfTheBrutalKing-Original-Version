
package DungeonoftheBrutalKing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class Combat extends JFrame {

    private static final long serialVersionUID = 1L;

    Singleton myCharSingleton = new Singleton();
    GameSettings myGameSettings = new GameSettings();
    private MainGameScreen myMainGameScreen = null;
    Charecter myChar = new Charecter();
    Enemies myEnemies = new Enemies();
    private String selectedSpell = null;
    String HeroHPArrayList = "";
    public JFrame CombatFrame, spellsFrame;
    public JPanel CombatPanel, CombatImagePanel, CombatPanelButtons, CombatPanelCombatAreaPanel,
            CombatUpdateInfoPanel, CombatNameAndHPPanel, spelllistbox = null;
    public JSplitPane CombatImageAndCombatUpdatesStatsSplitPane, CombatCombatUpdatesAndStatsSplitPane = null;
    public JTextArea CombatCombatTextArea, CombatNameAndHPfield = null;
    public JButton CombatAttackButton, CastSelectedSpellButton, SelectSpellButton, CombatRunButton, SelectSpellToCast = null;
    public JLabel picLabel = null;
    public int width, height, HP, HeroHP, CharrandomCombatChance, MonsterrandomCombatChance = 0;
    public Dimension imageSize = null;
    public BufferedImage myPictureBufferedImage = null;
    public Timer timer = null;
    public String[] spellList = null;

    public Combat() throws IOException {
        HeroHPArrayList = myChar.CharInfo.size() > 4 ? myChar.CharInfo.get(4) : "0";
        HeroHP = Integer.parseInt(HeroHPArrayList);
    }

    public void CombatEncouter() throws IOException, InterruptedException, ParseException {
        Object randomMonster = MonsterSelector.selectRandomMonster();
        MainGameScreen myMainGameScreen = new MainGameScreen();

        CombatPanel = new JPanel(new BorderLayout());
        myMainGameScreen.replaceWithAnyPanel(CombatPanel);
        CombatImagePanel = new JPanel();
        CombatPanelButtons = new JPanel(new FlowLayout());
        CombatPanelCombatAreaPanel = new JPanel();
        CombatUpdateInfoPanel = new JPanel();
        CombatNameAndHPPanel = new JPanel(new FlowLayout());

        CombatImageAndCombatUpdatesStatsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        CombatCombatUpdatesAndStatsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        CombatCombatTextArea = new JTextArea();
        CombatNameAndHPfield = new JTextArea();

        CombatAttackButton = new JButton("Attack");
        CastSelectedSpellButton = new JButton("Cast Selected Spell");
        SelectSpellButton = new JButton("Select Spell to Cast");
        CombatRunButton = new JButton("Run Away!");

        try {
            myPictureBufferedImage = ImageIO.read(new File(GameSettings.MonsterImagePath + myEnemies.getImagePath()));
            if (myPictureBufferedImage == null) {
                throw new IOException("Image could not be loaded: null image");
            }
            picLabel = new JLabel(new ImageIcon(myPictureBufferedImage));
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            picLabel = new JLabel("Image not found");
        }

        imageSize = new Dimension();
        imageSize.setSize(768, 1024);
        picLabel.setPreferredSize(imageSize);

        CombatPanel.add(CombatPanelButtons, BorderLayout.SOUTH);
        CombatPanel.add(CombatImageAndCombatUpdatesStatsSplitPane, BorderLayout.CENTER);
        CombatPanelButtons.add(CombatAttackButton);
        CombatPanelButtons.add(CastSelectedSpellButton);
        CombatPanelButtons.add(SelectSpellButton);
        CombatPanelButtons.add(CombatRunButton);
        CombatPanelCombatAreaPanel.add(CombatCombatTextArea);
        CombatNameAndHPPanel.add(CombatNameAndHPfield);
        CombatUpdateInfoPanel.add(CombatPanelCombatAreaPanel);

        CombatImageAndCombatUpdatesStatsSplitPane.setLeftComponent(CombatImagePanel);
        CombatImageAndCombatUpdatesStatsSplitPane.setRightComponent(CombatCombatUpdatesAndStatsSplitPane);
        CombatCombatUpdatesAndStatsSplitPane.setTopComponent(CombatNameAndHPPanel);
        CombatCombatUpdatesAndStatsSplitPane.setBottomComponent(CombatUpdateInfoPanel);

        CombatImageAndCombatUpdatesStatsSplitPane.setDividerLocation(0.5);
        CombatCombatUpdatesAndStatsSplitPane.setDividerLocation(0.25);

        CombatNameAndHPfield.setLineWrap(false);
        CombatNameAndHPfield.setEditable(false);
        CombatNameAndHPfield.setBackground(myGameSettings.colorLightYellow);
        CombatNameAndHPPanel.setBackground(myGameSettings.colorCoral);

        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // TODO: Add periodic update logic
            }
        };
        timer = new Timer(1000, task);
        timer.setRepeats(true);
        timer.start();

        CombatAttackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                StringBuilder combatLog = new StringBuilder();

                int charAttackChance = random.nextInt(100);
                if (charAttackChance >= Enemies.getAgility()) {
                    int charDamage = myChar.getWeaponDamage() + myChar.getStrength();
                    myEnemies.setHP(myEnemies.getHP() - charDamage);
                    combatLog.append("You hit the ").append(myEnemies.getName())
                            .append(" for ").append(charDamage).append(" damage!\n");
                } else {
                    combatLog.append("You missed your attack!\n");
                }

                if (myEnemies.getHP() <= 0) {
                    combatLog.append("You defeated the ").append(myEnemies.getName()).append("!\n");
                    MainGameScreen.CombatMessageArea.setText(combatLog.toString());
                    return;
                }

                int monsterAttackChance = random.nextInt(100);
                if (monsterAttackChance >= myChar.getAgility()) {
                    int monsterDamage = myEnemies.getWeaponDamage() + Enemies.getStrength();
                    myChar.setHP(myChar.getHitPoints() - monsterDamage);
                    combatLog.append("The ").append(myEnemies.getName())
                            .append(" hit you for ").append(monsterDamage).append(" damage!\n");
                } else {
                    combatLog.append("The ").append(myEnemies.getName()).append(" missed its attack!\n");
                }

                if (myChar.getHitPoints() <= 0) {
                    combatLog.append("You were defeated by the ").append(myEnemies.getName()).append("!\n");
                    MainGameScreen.CombatMessageArea.setText(combatLog.toString());
                    return;
                }

                MainGameScreen.CombatMessageArea.setText(combatLog.toString());
            }
        });

        SelectSpellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] knownSpells = myChar.getKnownSpells();
                String selected = (String) JOptionPane.showInputDialog(
                        CombatFrame,
                        "Select a spell to cast:",
                        "Spell Selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        knownSpells,
                        knownSpells.length > 0 ? knownSpells[0] : null
                );

                if (selected != null) {
                    selectedSpell = selected;
                    JOptionPane.showMessageDialog(CombatFrame, "You selected: " + selectedSpell);
                } else {
                    JOptionPane.showMessageDialog(CombatFrame, "No spell selected.");
                }
            }
        });

        CastSelectedSpellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedSpell == null) {
                    JOptionPane.showMessageDialog(CombatFrame, "No spell selected. Please select a spell first.");
                    return;
                }

                Spells spell = myChar.getSpellByName(selectedSpell);
                if (spell == null) {
                    JOptionPane.showMessageDialog(CombatFrame, "Selected spell not found.");
                    return;
                }

                StringBuilder combatLog = new StringBuilder();
                int spellDamage = spell.getDamage();
                myEnemies.setHP(myEnemies.getHP() - spellDamage);
                combatLog.append("You cast ").append(spell.getName())
                        .append(" and dealt ").append(spellDamage)
                        .append(" damage to ").append(myEnemies.getName()).append("!\n");

                if (myEnemies.getHP() <= 0) {
                    combatLog.append("You defeated the ").append(myEnemies.getName()).append("!\n");
                }

                MainGameScreen.CombatMessageArea.setText(combatLog.toString());
            }
        });

        CastSelectedSpellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedSpell == null) {
                    JOptionPane.showMessageDialog(CombatFrame, "No spell selected. Please select a spell first.");
                    return;
                }

                StringBuilder combatLog = new StringBuilder();
                Random random = new Random();

                int spellDamage = SpellList.getSpells(selectedSpell).getDamage();

                myEnemies.setHP(myEnemies.getHP() - spellDamage);
                combatLog.append("You cast ").append(selectedSpell)
                        .append(" and dealt ").append(spellDamage)
                        .append(" damage to ").append(myEnemies.getName()).append("!\n");

                if (myEnemies.getHP() <= 0) {
                    combatLog.append("You defeated the ").append(myEnemies.getName()).append("!\n");
                }

                MainGameScreen.CombatMessageArea.setText(combatLog.toString());
            }
        });



CombatRunButton.addActionListener(new ActionListener() {
    @Override
	public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        int runChance = random.nextInt(100); // Generate a random number between 0 and 99
        int agility = myChar.getAgility();  // Get the character's agility

        if (runChance < agility) {
            JOptionPane.showMessageDialog(CombatFrame, "You successfully ran away!");
            // Logic to exit combat or return to the previous screen
            myMainGameScreen.replaceWithAnyPanel(new JPanel()); // Example: Replace with a blank panel
        } else {
            JOptionPane.showMessageDialog(CombatFrame, "You failed to run away!");
            // Logic for the enemy to attack or continue combat
        }
    }
});

    }
    public static void main(String[] args) throws InterruptedException, ParseException {
        try {
            new Combat().CombatEncouter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
