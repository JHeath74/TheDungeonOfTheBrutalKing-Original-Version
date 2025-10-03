
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
<<<<<<< Updated upstream
<<<<<<< Updated upstream

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
=======
=======
>>>>>>> Stashed changes
import javax.imageio.ImageIO;
import javax.swing.*;
>>>>>>> Stashed changes

import SharedData.GameSettings;

public class Combat extends JFrame {

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    private static final long serialVersionUID = 1L;

    Singleton myCharSingleton = new Singleton();
    GameSettings myGameSettings = new GameSettings();
    private MainGameScreen myMainGameScreen = null;
    Charecter myChar = new Charecter();
    Enemies myEnemies = new Enemies();
=======
=======
>>>>>>> Stashed changes
    private final Charecter myChar = Charecter.getInstance();
    private JTextArea playerInfo;
    private JTextArea enemyInfo;
    private final Random random = new Random();

    private Enemies myEnemies;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public Combat() throws IOException {
        HeroHPArrayList = myChar.CharInfo.size() > 4 ? myChar.CharInfo.get(4) : "0";
        HeroHP = Integer.parseInt(HeroHPArrayList);
=======
    private JPanel combatPanel, combatPanelButtons;
    private JButton combatAttackButton, castSelectedSpellButton, selectSpellButton, combatRunButton;

    public Combat() throws IOException {
        int heroHP = myChar.getHitPoints();
        myChar.setHitPoints(heroHP);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
            myPictureBufferedImage = ImageIO.read(new File(GameSettings.MonsterImagePath + myEnemies.getImagePath()));
            if (myPictureBufferedImage == null) {
                throw new IOException("Image could not be loaded: null image");
=======
            String playerClass = myChar.getClassName();
            String imagePath;
            switch (playerClass) {
                case "Bard":    imagePath = GameSettings.ClassImagesPath + "bard.png"; break;
                case "Cleric":  imagePath = GameSettings.ClassImagesPath + "cleric.png"; break;
                case "Hunter":  imagePath = GameSettings.ClassImagesPath + "hunter.png"; break;
                case "Paladin": imagePath = GameSettings.ClassImagesPath + "paladin.png"; break;
                case "Rogue":   imagePath = GameSettings.ClassImagesPath + "rogue.png"; break;
                case "Warrior": imagePath = GameSettings.ClassImagesPath + "warrior.png"; break;
                case "Wizard":  imagePath = GameSettings.ClassImagesPath + "wizard.png"; break;
                default:        imagePath = GameSettings.ClassImagesPath + "default.png"; break;
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
            }
            picLabel = new JLabel(new ImageIcon(myPictureBufferedImage));
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
            picLabel = new JLabel("Image not found");
        }

        imageSize = new Dimension();
        imageSize.setSize(768, 1024);
        picLabel.setPreferredSize(imageSize);

<<<<<<< Updated upstream
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
=======
        playerInfo = new JTextArea(
            myChar.getName() + "\nHP: " + myChar.getHitPoints() +
            "\nMP: " + myChar.getMagicPoints() +
            "\nGold: " + myChar.getGold()
        );
        playerInfo.setEditable(false);
        playerInfo.setBackground(new Color(255, 255, 220));
        playerPanel.add(playerInfo);
<<<<<<< Updated upstream
>>>>>>> Stashed changes

        CombatImageAndCombatUpdatesStatsSplitPane.setDividerLocation(0.5);
        CombatCombatUpdatesAndStatsSplitPane.setDividerLocation(0.25);
=======
>>>>>>> Stashed changes

        CombatNameAndHPfield.setLineWrap(false);
        CombatNameAndHPfield.setEditable(false);
        CombatNameAndHPfield.setBackground(myGameSettings.getColorLightYellow());
        CombatNameAndHPPanel.setBackground(myGameSettings.getColorCoral());

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
<<<<<<< Updated upstream
=======
        enemyPicLabel.setPreferredSize(new Dimension(300, 400));
        enemyPanel.add(enemyPicLabel);

        enemyInfo = new JTextArea(
            myEnemies.getName() + "\nHP: " + myEnemies.getHitPoints()
        );
        enemyInfo.setEditable(false);
        enemyInfo.setBackground(new Color(255, 255, 220));
        enemyPanel.add(enemyInfo);

        combatPanelButtons = new JPanel(new FlowLayout());
        combatAttackButton = new JButton("Attack");
        castSelectedSpellButton = new JButton("Cast Selected Spell");
        selectSpellButton = new JButton("Select Spell to Cast");
        combatRunButton = new JButton("Run Away!");
        combatPanelButtons.add(combatAttackButton);
        combatPanelButtons.add(castSelectedSpellButton);
        combatPanelButtons.add(selectSpellButton);
        combatPanelButtons.add(combatRunButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        combatPanel.add(playerPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        combatPanel.add(enemyPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        combatPanel.add(combatPanelButtons, gbc);

        combatPanel.revalidate();
        combatPanel.repaint();

        combatAttackButton.addActionListener(_ -> {
			try {
				handleAttack();
			} catch (IOException | InterruptedException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
        selectSpellButton.addActionListener(_ -> handleSelectSpell());
        castSelectedSpellButton.addActionListener(_ -> handleCastSpell());
        combatRunButton.addActionListener(_ -> {
			try {
				handleRun();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    }

    private void handleAttack() throws IOException, InterruptedException, ParseException {
        if (myEnemies != null && myChar != null) {
            int playerDamage = myChar.getAttackDamage();
            int reducedDamage = monsterDefend(playerDamage);
            monsterTakeDamage(reducedDamage);

            MainGameScreen.appendToMessageTextPane("You attack " + myEnemies.getName() +
                " for " + reducedDamage + " damage.\n");

            if (isMonsterDead()) {
                int combatCount = 0; // Replace with actual combat count tracking if available
                int goldReward = myEnemies.getGoldReward(myEnemies.getLevel(), combatCount);
                int currentGold = myChar.getGold();
                myChar.setGold(currentGold + goldReward);
                MainGameScreen.appendToMessageTextPane("Monster defeated! You win " + goldReward + " gold.\n");
                updateNameAndHP();
                endCombat();
                return;
            } else {
                int monsterDamage = myEnemies.getAttackDamage();
                int playerDefense = myChar.getDefense();
                int damageToPlayer = Math.max(0, monsterDamage - playerDefense);
                myChar.takeDamage(damageToPlayer);
                MainGameScreen.appendToMessageTextPane(myEnemies.getName() +
                    " attacks you for " + damageToPlayer + " damage.\n");
            }
            updateNameAndHP();
        }
    }

    private void handleSelectSpell() {
        MainGameScreen.appendToMessageTextPane("Select Spell button pressed.\n");
    }

    private void handleCastSpell() {
        MainGameScreen.appendToMessageTextPane("Cast Selected Spell button pressed.\n");
    }

    private void handleRun() throws IOException, InterruptedException, ParseException {
        MainGameScreen.appendToMessageTextPane("Run Away button pressed.\n");
        endCombat();
    }

    private void updateNameAndHP() {
        playerInfo.setText(
            myChar.getName() + "\nHP: " + myChar.getHitPoints() +
            "\nMP: " + myChar.getMagicPoints() +
            "\nGold: " + myChar.getGold()
        );
        enemyInfo.setText(
            myEnemies.getName() + "\nHP: " + myEnemies.getHitPoints()
        );
    }

    public Enemies getMyEnemies() {
        return myEnemies;
    }

    public void setMyEnemies(Enemies enemies) {
        this.myEnemies = enemies;
    }

    public Charecter getMyChar() {
        return myChar;
    }

    public String getSelectedSpell() {
        return selectedSpell;
    }

    public void setSelectedSpell(String selectedSpell) {
        this.selectedSpell = selectedSpell;
    }

    public void monsterTakeDamage(int damage) {
        if (myEnemies != null) {
            myEnemies.takeDamage(damage);
        }
    }

    public int monsterDefend(int damage) {
        return (myEnemies != null) ? myEnemies.defend(damage) : damage;
    }

    public boolean isMonsterDead() {
        return (myEnemies != null) && myEnemies.isDead();
>>>>>>> Stashed changes
    }

    public void randomCombat(boolean moved) throws IOException, InterruptedException, ParseException {
        if (moved && random.nextDouble() < 0.1 && myEnemies == null) {
            setMyEnemies(MonsterSelector.selectRandomMonster());
            combatEncounter();
        }
    }





public void endCombat() throws IOException, InterruptedException, ParseException {
    myEnemies = null;
    Camera.getInstance().clearActiveCombat();
    MainGameScreen.getInstance().restoreOriginalPanel();


    MainGameScreen.appendToMessageTextPane("You have exited combat.\n");
}




}
