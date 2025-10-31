
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.imageio.ImageIO;
import javax.swing.*;

import Enemies.Enemies;
import Enemies.MonsterSelector;
import GameEngine.Camera;
import SharedData.GameSettings;

public class Combat {

    private final Charecter myChar = Charecter.getInstance();
    private JTextArea playerInfo;
    private JTextArea enemyInfo;

    private Enemies myEnemies;
    private String selectedSpell = null;

    private JPanel combatPanel, combatPanelButtons;
    private JButton combatAttackButton, castSelectedSpellButton, selectSpellButton, combatRunButton;
    private Camera camera;
    private JPanel mainGamePanel;

    public Combat(Camera camera, JPanel mainGamePanel) throws IOException {
        this.camera = camera;
        this.mainGamePanel = mainGamePanel;
        int heroHP = myChar.getHitPoints();
        myChar.setHitPoints(heroHP);
    }

    public void combatEncounter() throws IOException, InterruptedException, ParseException {
        combatPanel = new JPanel(new GridBagLayout());
        MainGameScreen.replaceWithAnyPanel(combatPanel);

        if (myEnemies == null) {
            JOptionPane.showMessageDialog(combatPanel, "No monster found!");
            combatPanel.setVisible(true);
            return;
        }

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        JLabel playerPicLabel;

        try {
            String playerClass = myChar.getClassName();
            String imagePath;
            switch (playerClass) {
                case "Bard":
                    imagePath = GameSettings.ClassImagesPath + "bard.png";
                    break;
                case "Cleric":
                    imagePath = GameSettings.ClassImagesPath + "cleric.png";
                    break;
                case "Hunter":
                    imagePath = GameSettings.ClassImagesPath + "hunter.png";
                    break;
                case "Paladin":
                    imagePath = GameSettings.ClassImagesPath + "paladin.png";
                    break;
                case "Rogue":
                    imagePath = GameSettings.ClassImagesPath + "rogue.png";
                    break;
                case "Warrior":
                    imagePath = GameSettings.ClassImagesPath + "warrior.png";
                    break;
                case "Wizard":
                    imagePath = GameSettings.ClassImagesPath + "wizard.png";
                    break;
                default:
                    imagePath = GameSettings.ClassImagesPath + "default.png";
                    break;
            }
            BufferedImage playerImg = ImageIO.read(new File(imagePath));
            Image scaledPlayerImg = playerImg.getScaledInstance(300, 400, Image.SCALE_SMOOTH);
            playerPicLabel = new JLabel(new ImageIcon(scaledPlayerImg));
        } catch (IOException e) {
            playerPicLabel = new JLabel("Image not found");
        }

        playerPicLabel.setPreferredSize(new Dimension(300, 400));
        playerPanel.add(playerPicLabel);

        playerInfo = new JTextArea(
            myChar.getName() + "\nHP: " + myChar.getHitPoints() + "\nMP: " + myChar.getMagicPoints()
        );
        playerInfo.setMaximumSize(new Dimension(300, playerInfo.getPreferredSize().height));
        playerInfo.setPreferredSize(new Dimension(300, playerInfo.getPreferredSize().height));
        playerPanel.add(Box.createRigidArea(new Dimension(24, 0)));
        playerInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        playerPanel.add(playerInfo);
        playerInfo.setEditable(false);
        playerInfo.setBackground(new Color(255, 255, 220));

        JPanel enemyPanel = new JPanel();
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
        JLabel enemyPicLabel;
        try {
            BufferedImage enemyImg = ImageIO.read(new File(myEnemies.getImagePath()));
            Image scaledEnemyImg = enemyImg.getScaledInstance(300, 400, Image.SCALE_SMOOTH);
            enemyPicLabel = new JLabel(new ImageIcon(scaledEnemyImg));
        } catch (IOException e) {
            enemyPicLabel = new JLabel("Image not found");
        }
        enemyPicLabel.setPreferredSize(new Dimension(300, 400));
        enemyPanel.add(enemyPicLabel);

        enemyInfo = new JTextArea(
            myEnemies.getName() + "\nHP: " + myEnemies.getHitPoints()
        );
        enemyPanel.add(Box.createRigidArea(new Dimension(24, 0)));
        enemyInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        enemyPanel.add(enemyInfo);
        enemyInfo.setMaximumSize(new Dimension(300, enemyInfo.getPreferredSize().height));
        enemyInfo.setPreferredSize(new Dimension(300, enemyInfo.getPreferredSize().height));
        enemyInfo.setEditable(false);
        enemyInfo.setBackground(new Color(255, 255, 220));

        combatPanelButtons = new JPanel(new FlowLayout());
        combatAttackButton = new JButton("Attack");
        selectSpellButton = new JButton();
        castSelectedSpellButton = new JButton();
        String className = myChar.getClassName();
        if (!"Mage".equals(className) && !"Wizard".equals(className)) {
            selectSpellButton.setText("Select Action to Use");
            castSelectedSpellButton.setText("Use Selected Action");
        } else {
            selectSpellButton.setText("Select Spell to Cast");
            castSelectedSpellButton.setText("Cast Selected Spell");
        }
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

        combatAttackButton.addActionListener(_ -> handleAttack());
        selectSpellButton.addActionListener(_ -> handleSelectSpell());
        castSelectedSpellButton.addActionListener(_ -> handleCastSpell());
        combatRunButton.addActionListener(_ -> handleRun());
    }

    private void handleAttack() {
        if (myEnemies != null && myChar != null) {
            int playerDamage = myChar.getAttackDamage();
            int reducedDamage = monsterDefend(playerDamage);
            monsterTakeDamage(reducedDamage);

            MainGameScreen.appendToMessageTextPane("You attack " + myEnemies.getName() +
                " for " + reducedDamage + " damage.\n");

            updateNameAndHP();

            if (isMonsterDead()) {
                MainGameScreen.appendToMessageTextPane("Monster defeated!\n");
                handleRewards();
                myEnemies = null;
                MainGameScreen.replaceWithAnyPanel(mainGamePanel);
                camera.endCombat();
                camera.getActiveCombat();
                return;
            }

            combatAttackButton.setEnabled(false);
            Timer timer = new Timer(1000, e -> {
                int monsterDamage = myEnemies.getAttackDamage();
                int playerDefense = myChar.getDefense();
                int damageToPlayer = Math.max(0, monsterDamage - playerDefense);
                myChar.takeDamage(damageToPlayer);
                MainGameScreen.appendToMessageTextPane(myEnemies.getName() +
                    " attacks you for " + damageToPlayer + " damage.\n");
                updateNameAndHP();

                if (myChar.getHitPoints() <= 0) {
                    int choice = JOptionPane.showOptionDialog(
                        combatPanel,
                        "You have been defeated!\nWhat would you like to do?",
                        "Game Over",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new String[]{"Exit Game", "Load Save"},
                        "Exit Game"
                    );
                    if (choice == 0) {
                        System.exit(0);
                    } else if (choice == 1) {
                        LoadSaveGame loadSaveGame = new LoadSaveGame();
                        loadSaveGame.LoadGame();
                    }
                    return;
                }

                combatAttackButton.setEnabled(true);
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            combatAttackButton.setEnabled(false);
        }
    }

    private void handleRewards() {
        if (myEnemies != null) {
            int exp = myEnemies.getExperienceReward();
            int gold = myEnemies.getGoldReward();
            myChar.rewardExperience(exp);
            myChar.setGold(myChar.getGold() + gold);
            MainGameScreen.appendToMessageTextPane("You gained " + exp + " EXP and " + gold + " gold!\n");
        }
    }

    private void handleSelectSpell() {
        java.util.List<String> allSpells = new java.util.ArrayList<>();
        allSpells.addAll(myChar.getSpellsLearned());
        allSpells.addAll(myChar.getGuildSpells());

        if (allSpells.isEmpty()) {
            MainGameScreen.appendToMessageTextPane("You don't know any spells or actions.\n");
            return;
        }

        String selected = (String) JOptionPane.showInputDialog(
            combatPanel,
            "Select a spell or action:",
            "Spell Selection",
            JOptionPane.PLAIN_MESSAGE,
            null,
            allSpells.toArray(),
            allSpells.get(0)
        );

        if (selected != null) {
            setSelectedSpell(selected);
            MainGameScreen.appendToMessageTextPane("Selected: " + selected + "\n");
        }
    }

    private void handleCastSpell() {
        MainGameScreen.appendToMessageTextPane("Cast Selected Spell button pressed.\n");
    }

    private void handleRun() {
        MainGameScreen.appendToMessageTextPane("Run Away button pressed.\n");
        camera.endCombat();
        MainGameScreen.replaceWithAnyPanel(mainGamePanel);
    }

    private void updateNameAndHP() {
        playerInfo.setText(
            myChar.getName() + "\nHP: " + myChar.getHitPoints() + "\nMP: " + myChar.getMagicPoints()
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
    }
}
