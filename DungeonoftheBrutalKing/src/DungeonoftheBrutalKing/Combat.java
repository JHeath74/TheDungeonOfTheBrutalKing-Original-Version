
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

import SharedData.GameSettings;

public class Combat extends JFrame {

    private static final long serialVersionUID = 1L;

    Singleton myCharSingleton = new Singleton();
    GameSettings myGameSettings = new GameSettings();
    Charecter myChar = new Charecter();
    Enemies myEnemies = new Enemies();
    private String selectedSpell = null;
    String HeroHPArrayList = "";
    public JFrame CombatFrame, spellsFrame;
    public JPanel CombatPanel, CombatImagePanel, CombatPanelButtons, CombatPanelCombatAreaPanel,
            CombatUpdateInfoPanel, CombatNameAndHPPanel, spelllistbox = null;
    public JSplitPane CombatImageAndCombatUpdatesStatsSplitPane, CombatCombatUpdatesAndStatsSplitPane = null;
    public JTextArea CombatMessageArea, CombatNameAndHPfield = null;
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
        MonsterSelector.selectRandomMonster();

        CombatPanel = new JPanel(new BorderLayout());
        MainGameScreen.getInstance().replaceWithAnyPanel(CombatPanel);
        CombatImagePanel = new JPanel();
        CombatPanelButtons = new JPanel(new FlowLayout());
        CombatPanelCombatAreaPanel = new JPanel();
        CombatUpdateInfoPanel = new JPanel();
        CombatNameAndHPPanel = new JPanel(new FlowLayout());

        CombatImageAndCombatUpdatesStatsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        CombatCombatUpdatesAndStatsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        CombatMessageArea = new JTextArea(12, 40);
        CombatNameAndHPfield = new JTextArea(2, 40);

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
        imageSize.setSize(300, 400);
        picLabel.setPreferredSize(imageSize);
        CombatImagePanel.add(picLabel);

        CombatPanel.add(CombatPanelButtons, BorderLayout.SOUTH);
        CombatPanel.add(CombatImageAndCombatUpdatesStatsSplitPane, BorderLayout.CENTER);
        CombatPanelButtons.add(CombatAttackButton);
        CombatPanelButtons.add(CastSelectedSpellButton);
        CombatPanelButtons.add(SelectSpellButton);
        CombatPanelButtons.add(CombatRunButton);
        CombatPanelCombatAreaPanel.add(CombatMessageArea);
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
        CombatNameAndHPfield.setBackground(myGameSettings.getColorLightYellow());
        CombatNameAndHPPanel.setBackground(myGameSettings.getColorCoral());

        updateNameAndHP();

        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                updateNameAndHP();
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
                if (charAttackChance >= myEnemies.getAgility()) {
                    int charDamage = myChar.getWeaponDamage() + myChar.getStrength();
                    myEnemies.setHP(myEnemies.getHP() - charDamage);
                    combatLog.append("You hit the ").append(myEnemies.getName())
                            .append(" for ").append(charDamage).append(" damage!\n");
                } else {
                    combatLog.append("You missed your attack!\n");
                }

                if (myEnemies.getHP() <= 0) {
                    combatLog.append("You defeated the ").append(myEnemies.getName()).append("!\n");
                    CombatMessageArea.setText(combatLog.toString());
                    CombatAttackButton.setEnabled(false);
                    CastSelectedSpellButton.setEnabled(false);
                    SelectSpellButton.setEnabled(false);
                    CombatRunButton.setEnabled(false);
                    return;
                }

                int monsterAttackChance = random.nextInt(100);
                if (monsterAttackChance >= myChar.getAgility()) {
                    int monsterDamage = myEnemies.getWeaponDamage() + myEnemies.getStrength();
                    myChar.setHitPoints(myChar.getHitPoints() - monsterDamage);
                    combatLog.append("The ").append(myEnemies.getName())
                            .append(" hit you for ").append(monsterDamage).append(" damage!\n");
                } else {
                    combatLog.append("The ").append(myEnemies.getName()).append(" missed its attack!\n");
                }

                if (myChar.getHitPoints() <= 0) {
                    combatLog.append("You were defeated by the ").append(myEnemies.getName()).append("!\n");
                    CombatMessageArea.setText(combatLog.toString());
                    CombatAttackButton.setEnabled(false);
                    CastSelectedSpellButton.setEnabled(false);
                    SelectSpellButton.setEnabled(false);
                    CombatRunButton.setEnabled(false);
                    return;
                }

                CombatMessageArea.setText(combatLog.toString());
            }
        });

        SelectSpellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] knownSpells = myChar.getKnownSpells();
                if (knownSpells == null || knownSpells.length == 0) {
                    JOptionPane.showMessageDialog(CombatPanel, "You don't know any spells.");
                    return;
                }
                String selected = (String) JOptionPane.showInputDialog(
                        CombatPanel,
                        "Select a spell to cast:",
                        "Spell Selection",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        knownSpells,
                        knownSpells[0]
                );

                if (selected != null) {
                    selectedSpell = selected;
                    JOptionPane.showMessageDialog(CombatPanel, "You selected: " + selectedSpell);
                } else {
                    JOptionPane.showMessageDialog(CombatPanel, "No spell selected.");
                }
            }
        });

        CastSelectedSpellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedSpell == null) {
                    JOptionPane.showMessageDialog(CombatPanel, "No spell selected. Please select a spell first.");
                    CombatMessageArea.setText("No spell selected.\n");
                    return;
                }

                Spells spell = myChar.getSpellByName(selectedSpell);
                if (spell == null) {
                    JOptionPane.showMessageDialog(CombatPanel, "Selected spell not found.");
                    CombatMessageArea.setText("Selected spell not found.\n");
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
                    CombatMessageArea.setText(combatLog.toString());
                    CombatAttackButton.setEnabled(false);
                    CastSelectedSpellButton.setEnabled(false);
                    SelectSpellButton.setEnabled(false);
                    CombatRunButton.setEnabled(false);
                    return;
                }

                // Monster's turn after spell
                Random random = new Random();
                int monsterAttackChance = random.nextInt(100);
                if (monsterAttackChance >= myChar.getAgility()) {
                    int monsterDamage = myEnemies.getWeaponDamage() + myEnemies.getStrength();
                    myChar.setHitPoints(myChar.getHitPoints() - monsterDamage);
                    combatLog.append("The ").append(myEnemies.getName())
                            .append(" hit you for ").append(monsterDamage).append(" damage!\n");
                } else {
                    combatLog.append("The ").append(myEnemies.getName()).append(" missed its attack!\n");
                }

                if (myChar.getHitPoints() <= 0) {
                    combatLog.append("You were defeated by the ").append(myEnemies.getName()).append("!\n");
                    CombatMessageArea.setText(combatLog.toString());
                    CombatAttackButton.setEnabled(false);
                    CastSelectedSpellButton.setEnabled(false);
                    SelectSpellButton.setEnabled(false);
                    CombatRunButton.setEnabled(false);
                    return;
                }

                CombatMessageArea.setText(combatLog.toString());
            }
        });

        CombatRunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int runChance = random.nextInt(100);
                int agility = myChar.getAgility();

                if (runChance < agility) {
                    CombatMessageArea.setText("You successfully ran away!\n");
                    CombatAttackButton.setEnabled(false);
                    CastSelectedSpellButton.setEnabled(false);
                    SelectSpellButton.setEnabled(false);
                    CombatRunButton.setEnabled(false);
                } else {
                    StringBuilder combatLog = new StringBuilder();
                    combatLog.append("You failed to run away!\n");
                    // Monster gets a free attack
                    int monsterAttackChance = random.nextInt(100);
                    if (monsterAttackChance >= myChar.getAgility()) {
                        int monsterDamage = myEnemies.getWeaponDamage() + myEnemies.getStrength();
                        myChar.setHitPoints(myChar.getHitPoints() - monsterDamage);
                        combatLog.append("The ").append(myEnemies.getName())
                                .append(" hit you for ").append(monsterDamage).append(" damage!\n");
                    } else {
                        combatLog.append("The ").append(myEnemies.getName()).append(" missed its attack!\n");
                    }
                    if (myChar.getHitPoints() <= 0) {
                        combatLog.append("You were defeated by the ").append(myEnemies.getName()).append("!\n");
                        CombatAttackButton.setEnabled(false);
                        CastSelectedSpellButton.setEnabled(false);
                        SelectSpellButton.setEnabled(false);
                        CombatRunButton.setEnabled(false);
                    }
                    CombatMessageArea.setText(combatLog.toString());
                }
            }
        });
    }

    private void updateNameAndHP() {
        CombatNameAndHPfield.setText(
                myChar.getName() + " HP: " + myChar.getHitPoints() +
                " | " + myEnemies.getName() + " HP: " + myEnemies.getHP()
        );
    }

    public static void main(String[] args) throws InterruptedException, ParseException {
        try {
            new Combat().CombatEncouter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
