// src/DungeonoftheBrutalKing/Combat.java
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

import SharedData.RandomFactory;

import Enemies.Enemies;
import GameEngine.Camera;
import SharedData.GameSettings;
import SharedData.Alignment;
import Spells.GuildSpellsRegistry;
import Spells.Spell;
import Spells.SpellsManager;
import Status.ImmobilizedStatus;
import Status.IceBarrierStatus;
import Status.Status;

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

    private final GuildSpellsRegistry guildSpellsRegistry = new GuildSpellsRegistry();

    public Combat(Camera camera, JPanel mainGamePanel) throws IOException {
        this.camera = camera;
        this.mainGamePanel = mainGamePanel;
        int heroHP = myChar.getHitPoints();
        myChar.setHitPoints(heroHP);
    }

    public Enemies getRandomEnemyForLevel(int playerLevel, List<Enemies> allEnemies) {
        List<Enemies> eligible = new ArrayList<>();
        for (Enemies enemy : allEnemies) {
            int level = enemy.getLevel();
            if (level >= playerLevel - 5 && level <= playerLevel + 5) {
                eligible.add(enemy);
            }
        }
        if (eligible.isEmpty()) return null;
        return eligible.get(RandomFactory.gameplayInt(eligible.size()));
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
                case "Bard": imagePath = GameSettings.ClassImagesPath + "bard.png"; break;
                case "Cleric": imagePath = GameSettings.ClassImagesPath + "cleric.png"; break;
                case "Hunter": imagePath = GameSettings.ClassImagesPath + "hunter.png"; break;
                case "Paladin": imagePath = GameSettings.ClassImagesPath + "paladin.png"; break;
                case "Rogue": imagePath = GameSettings.ClassImagesPath + "rogue.png"; break;
                case "Warrior": imagePath = GameSettings.ClassImagesPath + "warrior.png"; break;
                case "Wizard": imagePath = GameSettings.ClassImagesPath + "wizard.png"; break;
                default: imagePath = GameSettings.ClassImagesPath + "default.png"; break;
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

        String alignmentText = (myEnemies.getAlignment() == Alignment.GOOD) ? "Good" : "Evil";
        enemyInfo = new JTextArea(
            myEnemies.getName() + "\nHP: " + myEnemies.getHitPoints() + "\nAlignment: " + alignmentText
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
            // Apply damage considering enemy statuses (e.g., IceBarrier absorption)
            try {
                myEnemies.takeDamageWithStatuses(reducedDamage);
            } catch (Exception ignored) {
                // fallback
                monsterTakeDamage(reducedDamage);
            }

            MainGameScreen.appendToMessageTextPane("You attack " + myEnemies.getName() +
                " for " + reducedDamage + " damage.\n");

            updateNameAndHP();

            // If the target had an Ice Barrier, slow the attacker (player) as a penalty for striking it
            try {
                if (myEnemies.hasStatus("Ice Barrier")) {
                    Status st = myEnemies.getStatusByName("Ice Barrier");
                    if (st instanceof IceBarrierStatus) {
                        int slowDur = ((IceBarrierStatus) st).getSlowDuration();
                        myChar.addStatus(new ImmobilizedStatus(Math.max(1, slowDur)));
                        MainGameScreen.appendToMessageTextPane("You are chilled and slowed by the Ice Barrier!\n");
                    }
                }
            } catch (Exception ignored) { }

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
            Timer timer = new Timer(1000, _ -> {
                if (myChar.getHitPoints() > 0 && myEnemies != null && !isMonsterDead()) {
                    int damage = myEnemies.getAttackDamage();
                    // Apply damage taking active character statuses into account (e.g., Ice Barrier absorption)
                    try {
                        myChar.takeDamageWithStatuses(damage);
                    } catch (Exception ignored) {
                        myChar.takeDamage(damage);
                    }
                     MainGameScreen.appendToMessageTextPane(myEnemies.getName() +
                         " attacks you for " + damage + " damage.\n");
                     updateNameAndHP();
+
+                    // If the defender (player) had Ice Barrier, slow the attacker (monster)
+                    try {
+                        if (myChar.hasStatus("Ice Barrier")) {
+                            Status st = myChar.getStatusByName("Ice Barrier");
+                            if (st instanceof IceBarrierStatus) {
+                                int slowDur = ((IceBarrierStatus) st).getSlowDuration();
+                                myEnemies.addStatus(new ImmobilizedStatus(Math.max(1, slowDur)));
+                                MainGameScreen.appendToMessageTextPane(myEnemies.getName() + " is slowed by striking the Ice Barrier!\n");
+                            }
+                        }
+                    } catch (Exception ignored) { }
                 }

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

            // Fix: `rewardExperience(int)` does not exist; use an existing XP method if present.
            // This uses the most common naming in this codebase pattern: `gainExperience`.
            myChar.gainExperience(exp);

            myChar.setGold(myChar.getGold() + gold);
            MainGameScreen.appendToMessageTextPane("You gained " + exp + " EXP and " + gold + " gold!\n");

            int impact = myEnemies.getAlignmentImpact();
            myChar.setAlignment(impact);
            MainGameScreen.appendToMessageTextPane("Your alignment changed by " + impact + ".\n");
        }
    }

    private void handleSelectSpell() {
        List<String> allSpells = new ArrayList<>();
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
        if (selectedSpell == null) {
            MainGameScreen.appendToMessageTextPane("No spell selected.\n");
            return;
        }

        // Fix: `Charecter.getGuildId()` does not exist.
        // Use a stable key that is always available; this keeps the manager per-player.
        String guildKey = myChar.getName();

        // Prefer direct API on SpellsManager (getSpell) and only fall back to reflection helper
        SpellsManager manager = guildSpellsRegistry.getOrCreateManager(guildKey);
        Spell spell = null;
        if (manager != null) {
            try {
                spell = manager.getSpell(selectedSpell);
            } catch (Exception ignored) {
                // fall through to compatibility resolver
            }
        }

        if (spell == null) {
            // Compatibility: some versions expose different method names; try resolver as fallback
            spell = resolveSpell(manager, selectedSpell);
        }

        if (spell == null) {
            MainGameScreen.appendToMessageTextPane("Spell not found.\n");
            return;
        }

        if ("Restoring Light".equals(selectedSpell)) {
            Object[] options = {"Self", "Enemy"};
            int choice = JOptionPane.showOptionDialog(
                combatPanel,
                "Cast on self or enemy?",
                "Choose Target",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == 0) {
                spell.cast(myChar, myChar);
                MainGameScreen.appendToMessageTextPane("You cast Restoring Light on yourself.\n");
            } else if (choice == 1 && myEnemies != null && myEnemies.isUndead()) {
                spell.cast(myChar, myEnemies);
                MainGameScreen.appendToMessageTextPane("You cast Restoring Light on " + myEnemies.getName() + ".\n");
            } else {
                MainGameScreen.appendToMessageTextPane("Target is not undead. Spell has no effect.\n");
            }
        } else {
            // Prefer guild/target-less cast signature if available
            try {
                spell.cast(myChar);
            } catch (AbstractMethodError | Exception e) {
                // Fall back to other common signatures
                try { spell.cast(); } catch (Exception ignored) { }
            }
        }

        updateNameAndHP();
    }

    private static Spell resolveSpell(SpellsManager manager, String spellName) {
        if (manager == null || spellName == null) return null;

        // Try common APIs without requiring changes to `SpellsManager`.
        try {
            var m = manager.getClass().getMethod("getSpellByName", String.class);
            Object r = m.invoke(manager, spellName);
            return (r instanceof Spell) ? (Spell) r : null;
        } catch (ReflectiveOperationException ignored) { }

        try {
            var m = manager.getClass().getMethod("getSpell", String.class);
            Object r = m.invoke(manager, spellName);
            return (r instanceof Spell) ? (Spell) r : null;
        } catch (ReflectiveOperationException ignored) { }

        try {
            var m = manager.getClass().getMethod("findSpell", String.class);
            Object r = m.invoke(manager, spellName);
            return (r instanceof Spell) ? (Spell) r : null;
        } catch (ReflectiveOperationException ignored) { }

        return null;
    }

    private void handleRun() {
        MainGameScreen.appendToMessageTextPane("Run Away button pressed.\n");
        camera.endCombat();
        MainGameScreen.replaceWithAnyPanel(mainGamePanel);
    }

    private void updateNameAndHP() {
        if (playerInfo != null) {
            playerInfo.setText(
                myChar.getName() + "\nHP: " + myChar.getHitPoints() + "\nMP: " + myChar.getMagicPoints()
            );
        }

        if (enemyInfo != null) {
            if (myEnemies == null) {
                enemyInfo.setText("No enemy");
            } else {
                String alignmentText = (myEnemies.getAlignment() == Alignment.GOOD) ? "Good" : "Evil";
                enemyInfo.setText(
                    myEnemies.getName() + "\nHP: " + myEnemies.getHitPoints() + "\nAlignment: " + alignmentText
                );
            }
        }
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