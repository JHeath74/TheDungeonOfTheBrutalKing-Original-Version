

package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

import Enemies.Enemies;
import Enemies.MonsterSelector;
import SharedData.GameSettings;

public class Combat {

    private final GameSettings myGameSettings = new GameSettings();
    private final Charecter myChar = Charecter.Singleton();
    private Enemies myEnemies;
    private String selectedSpell = null;

    private JPanel combatPanel, combatImagePanel, combatPanelButtons, combatPanelCombatAreaPanel,
            combatUpdateInfoPanel, combatNameAndHPPanel;
    private JSplitPane combatImageAndCombatUpdatesStatsSplitPane, combatCombatUpdatesAndStatsSplitPane;
    private JTextArea combatMessageArea, combatNameAndHPfield;
    private JButton combatAttackButton, castSelectedSpellButton, selectSpellButton, combatRunButton;
    private JLabel picLabel;
    private Timer timer;

    public Combat() throws IOException {
        int heroHP = (myChar.CharInfo != null && myChar.CharInfo.size() > 5)
                ? parseIntSafe(myChar.CharInfo.get(5))
                : 0;
        myChar.setHitPoints(heroHP);
    }

    public void combatEncounter() throws IOException, InterruptedException, ParseException {
        combatPanel = new JPanel(new BorderLayout());
        MainGameScreen.replaceWithAnyPanel(combatPanel);

        myEnemies = MonsterSelector.selectRandomMonster();
        if (myEnemies == null) {
            JOptionPane.showMessageDialog(combatPanel, "No monster found!");
            combatPanel.setVisible(true);
            return;
        }
        System.out.println("Selected monster: " + myEnemies.getName() + " HP: " + myEnemies.getHP());

        combatImagePanel = new JPanel();
        combatImagePanel.setPreferredSize(new Dimension(300, 400));

        combatPanelButtons = new JPanel(new FlowLayout());
        combatPanelCombatAreaPanel = new JPanel();
        combatUpdateInfoPanel = new JPanel();
        combatNameAndHPPanel = new JPanel(new FlowLayout());

        combatImageAndCombatUpdatesStatsSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        combatImageAndCombatUpdatesStatsSplitPane.setResizeWeight(0.3);

        combatCombatUpdatesAndStatsSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        combatMessageArea = new JTextArea(12, 40);
        combatNameAndHPfield = new JTextArea(2, 40);

        combatAttackButton = new JButton("Attack");
        castSelectedSpellButton = new JButton("Cast Selected Spell");
        selectSpellButton = new JButton("Select Spell to Cast");
        combatRunButton = new JButton("Run Away!");

        String imageDir = GameSettings.MonsterImagePath;
        String enemyImageFile = new File(myEnemies.getImagePath()).getName();
        File imageFile = new File(imageDir, enemyImageFile);

        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
            if (img == null) {
                picLabel = new JLabel("Image not found");
            } else {
                picLabel = new JLabel(new ImageIcon(img));
            }
        } catch (IOException e) {
            picLabel = new JLabel("Image not found");
        }

        picLabel.setPreferredSize(new Dimension(300, 400));
        combatImagePanel.add(picLabel);

        combatPanel.add(combatPanelButtons, BorderLayout.SOUTH);
        combatPanel.add(combatImageAndCombatUpdatesStatsSplitPane, BorderLayout.CENTER);
        combatPanelButtons.add(combatAttackButton);
        combatPanelButtons.add(castSelectedSpellButton);
        combatPanelButtons.add(selectSpellButton);
        combatPanelButtons.add(combatRunButton);
        combatPanelCombatAreaPanel.add(combatMessageArea);
        combatNameAndHPPanel.add(combatNameAndHPfield);
        combatUpdateInfoPanel.add(combatPanelCombatAreaPanel);

        combatImageAndCombatUpdatesStatsSplitPane.setLeftComponent(combatImagePanel);
        combatImageAndCombatUpdatesStatsSplitPane.setRightComponent(combatCombatUpdatesAndStatsSplitPane);
        combatCombatUpdatesAndStatsSplitPane.setTopComponent(combatNameAndHPPanel);
        combatCombatUpdatesAndStatsSplitPane.setBottomComponent(combatUpdateInfoPanel);

        combatImageAndCombatUpdatesStatsSplitPane.setDividerLocation(0.5);
        combatCombatUpdatesAndStatsSplitPane.setDividerLocation(0.25);

        combatNameAndHPfield.setLineWrap(false);
        combatNameAndHPfield.setEditable(false);
        combatNameAndHPfield.setBackground(myGameSettings.getColorLightYellow());
        combatNameAndHPPanel.setBackground(myGameSettings.getColorCoral());

        updateNameAndHP();

        timer = new Timer(1000, _ -> updateNameAndHP());
        timer.setRepeats(true);
        timer.start();

        combatAttackButton.addActionListener(_ -> handleAttack());
        selectSpellButton.addActionListener(_ -> handleSelectSpell());
        castSelectedSpellButton.addActionListener(_ -> handleCastSpell());
        combatRunButton.addActionListener(_ -> handleRun());
    }

    private void handleAttack() {
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
            endCombat(combatLog);
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
            endCombat(combatLog);
            return;
        }

        combatMessageArea.setText(combatLog.toString());
    }

    private void handleSelectSpell() {
        String[] knownSpells = myChar.getKnownSpells();
        if (knownSpells == null || knownSpells.length == 0) {
            JOptionPane.showMessageDialog(combatPanel, "You don't know any spells.");
            return;
        }
        String selected = (String) JOptionPane.showInputDialog(
                combatPanel,
                "Select a spell to cast:",
                "Spell Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                knownSpells,
                knownSpells[0]
        );
        selectedSpell = selected;
        JOptionPane.showMessageDialog(combatPanel, selected != null ? "You selected: " + selectedSpell : "No spell selected.");
    }

    private void handleCastSpell() {
        if (selectedSpell == null) {
            JOptionPane.showMessageDialog(combatPanel, "No spell selected. Please select a spell first.");
            combatMessageArea.setText("No spell selected.\n");
            return;
        }

        Spells spell = myChar.getSpellByName(selectedSpell);
        if (spell == null) {
            JOptionPane.showMessageDialog(combatPanel, "Selected spell not found.");
            combatMessageArea.setText("Selected spell not found.\n");
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
            endCombat(combatLog);
            return;
        }

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
            endCombat(combatLog);
            return;
        }

        combatMessageArea.setText(combatLog.toString());
    }

    private void handleRun() {
        Random random = new Random();
        int runChance = random.nextInt(100);
        int agility = myChar.getAgility();

        if (runChance < agility) {
            combatMessageArea.setText("You successfully ran away!\n");
            disableCombatButtons();
        } else {
            StringBuilder combatLog = new StringBuilder();
            combatLog.append("You failed to run away!\n");
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
                disableCombatButtons();
            }
            combatMessageArea.setText(combatLog.toString());
        }
    }

    private void updateNameAndHP() {
        String charName = myChar.getName() != null ? myChar.getName() : "Unknown";
        String charHP = String.valueOf(myChar.getHitPoints());
        String monsterName = myEnemies != null && myEnemies.getName() != null ? myEnemies.getName() : "Unknown";
        String monsterHP = myEnemies != null ? String.valueOf(myEnemies.getHP()) : "0";

        System.out.println("Monster: " + monsterName + " HP: " + monsterHP);

        combatNameAndHPfield.setText(
            charName + " HP: " + charHP +
            " | " + monsterName + " HP: " + monsterHP
        );
    }

    private void endCombat(StringBuilder combatLog) {
        combatMessageArea.setText(combatLog.toString());
        disableCombatButtons();
    }

    private void disableCombatButtons() {
        combatAttackButton.setEnabled(false);
        castSelectedSpellButton.setEnabled(false);
        selectSpellButton.setEnabled(false);
        combatRunButton.setEnabled(false);
    }

    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
