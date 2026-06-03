
package DungeonoftheBrutalKing;

import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.GameEngine.Camera;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.Spells.GuildSpellsRegistry;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Spells.SpellsManager;
import DungeonoftheBrutalKing.Status.IceBarrierStatus;
import DungeonoftheBrutalKing.Status.ImmobilizedStatus;
import DungeonoftheBrutalKing.Status.Status;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Combat {

    private static final int IMAGE_WIDTH  = 300;
    private static final int IMAGE_HEIGHT = 400;

    private final Character myChar = Character.getInstance();
    private final GuildSpellsRegistry guildSpellsRegistry = new GuildSpellsRegistry();
    private final Camera camera;
    private final JPanel mainGamePanel;

    private Enemies myEnemies;
    private String selectedSpell;

    private JPanel combatPanel;
    private JButton combatAttackButton;
    private JTextArea playerInfo;
    private JTextArea enemyInfo;

    public Combat(Camera camera, JPanel mainGamePanel) {
        this.camera = camera;
        this.mainGamePanel = mainGamePanel;
    }

    // ── Public API ────────────────────────────────────────────────────────────

    public Enemies getMyEnemies()                    { return myEnemies; }
    public void    setMyEnemies(Enemies enemies)     { this.myEnemies = enemies; }
    public Character getMyChar()                     { return myChar; }
    public String  getSelectedSpell()                { return selectedSpell; }
    public void    setSelectedSpell(String spell)    { this.selectedSpell = spell; }

    public Enemies getRandomEnemyForLevel(int playerLevel, List<Enemies> allEnemies) {
        List<Enemies> eligible = new ArrayList<>();
        for (Enemies enemy : allEnemies) {
            int lvl = enemy.getLevel();
            if (lvl >= playerLevel - 5 && lvl <= playerLevel + 5) {
                eligible.add(enemy);
            }
        }
        return eligible.isEmpty() ? null : eligible.get(RandomFactory.gameplayInt(eligible.size()));
    }


    public int monsterDefend(int damage) {
        return (myEnemies != null) ? myEnemies.defend(damage) : damage;
    }

    public boolean isMonsterDead() {
        return myEnemies != null && myEnemies.isDead();
    }

    // ── Combat Encounter UI ───────────────────────────────────────────────────

    public void combatEncounter() throws IOException, InterruptedException, ParseException {
        combatPanel = new JPanel(new GridBagLayout());
        MainGameScreen.replaceWithAnyPanel(combatPanel);

        if (myEnemies == null) {
            JOptionPane.showMessageDialog(combatPanel, "No monster found!");
            return;
        }

        combatPanel.add(buildPlayerPanel(), gridCell(0, 0));
        combatPanel.add(buildEnemyPanel(),  gridCell(1, 0));
        combatPanel.add(buildButtonPanel(), gridSpan(0, 1, 2));

        combatPanel.revalidate();
        combatPanel.repaint();
    }

    // ── Panel Builders ────────────────────────────────────────────────────────

    private JPanel buildPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String playerImagePath = resolvePlayerImagePath();
    //    System.out.println("Player image path: " + playerImagePath);
        JLabel playerImage = loadScaledImage(playerImagePath, "Player image not found");
        panel.add(playerImage);

        playerInfo = makeInfoArea(
            myChar.getName() + "\nHP: " + myChar.getHitPoints() +
            "\nMP: " + myChar.getMagicPoints() + "\n" + getEquipmentInfo()
        );
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(playerInfo);
        return panel;
    }



private JPanel buildEnemyPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    String rawPath = myEnemies.getImagePath();
    // Convert "src/DungeonoftheBrutalKing/Images/Enemies/X.png"
    // to classpath resource "/DungeonoftheBrutalKing/Images/Enemies/X.png"
    String enemyImagePath = rawPath.contains("src/")
        ? "/" + rawPath.substring(rawPath.indexOf("src/") + 4)
        : rawPath;
    JLabel enemyImage = loadScaledImage(enemyImagePath, "Enemy image not found");
    panel.add(enemyImage);

    enemyInfo = makeInfoArea(
        myEnemies.getName() + "\nHP: " + myEnemies.getHitPoints() +
        "\nAlignment: " + alignmentLabel(myEnemies.getAlignment())
    );
    panel.add(Box.createRigidArea(new Dimension(0, 8)));
    panel.add(enemyInfo);
    return panel;
}



    private JPanel buildButtonPanel() {
        boolean isCaster = isCasterClass();
        JButton selectSpellButton = new JButton(isCaster ? "Select Spell to Cast"  : "Select Action to Use");
        JButton castSpellButton   = new JButton(isCaster ? "Cast Selected Spell"   : "Use Selected Action");
        JButton runButton         = new JButton("Run Away!");
        combatAttackButton        = new JButton("Attack");

        combatAttackButton.addActionListener(_ -> handleAttack());
        selectSpellButton .addActionListener(_ -> handleSelectSpell());
        castSpellButton   .addActionListener(_ -> handleCastSpell());
        runButton         .addActionListener(_ -> handleRun());

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(combatAttackButton);
        panel.add(castSpellButton);
        panel.add(selectSpellButton);
        panel.add(runButton);
        return panel;
    }

    // ── Action Handlers ───────────────────────────────────────────────────────

    private void handleAttack() {
        if (myEnemies == null) {
            combatAttackButton.setEnabled(false);
            return;
        }
        int rawDamage = myChar.getAttackDamage();
        int damage = monsterDefend(myChar.getAttackDamage());
        
        System.out.println("Raw damage: " + rawDamage + " | After defend: " + damage); // debug
        applyDamageToEnemy(damage);
        System.out.println("Enemy HP after attack: " + myEnemies.getHitPoints()); // debug
        
        appendMessage("You attack " + myEnemies.getName() + " for " + damage + " damage.\n");
        applyIceBarrierEffect(myEnemies, myChar, "You are chilled and slowed by the Ice Barrier!\n");
        updateNameAndHP();

        if (isMonsterDead()) {
            appendMessage("Monster defeated!\n");
            handleRewards();
            endCombat();
            return;
        }

        combatAttackButton.setEnabled(false);
        Timer timer = new Timer(1000, _ -> {
            enemyAttackPlayer();
            combatAttackButton.setEnabled(myChar.getHitPoints() > 0);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void enemyAttackPlayer() {
        if (myEnemies == null || myChar.getHitPoints() <= 0) return;

        int damage = myEnemies.getAttackDamage();
        try {
            myChar.takeDamageWithStatuses(damage);
        } catch (Exception ignored) {
            myChar.takeDamage(damage);
        }

        appendMessage(myEnemies.getName() + " attacks you for " + damage + " damage.\n");
        applyIceBarrierEffect(myChar, myEnemies, myEnemies.getName() + " is slowed by striking the Ice Barrier!\n");
        updateNameAndHP();

        if (myChar.getHitPoints() <= 0) {
            handlePlayerDeath();
        }
    }

    private void handlePlayerDeath() {
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
            new LoadSaveGame().LoadGame();
        }
    }

    private void handleRewards() {
        if (myEnemies == null) return;
        int exp  = myEnemies.getExperienceReward();
        int gold = myEnemies.getGoldReward();
        myChar.gainExperience(exp);
        myChar.setGold(myChar.getGold() + gold);
        appendMessage("You gained " + exp + " EXP and " + gold + " gold!\n");

        int impact = myEnemies.getAlignmentImpact();
        myChar.setAlignment(myChar.getAlignment() + impact);
        appendMessage("Your alignment changed by " + impact + ".\n");
    }

    private void handleSelectSpell() {
        List<String> allSpells = new ArrayList<>();
        allSpells.addAll(myChar.getSpellsLearned());
        allSpells.addAll(myChar.getGuildSpells());

        if (allSpells.isEmpty()) {
            appendMessage("You don't know any spells or actions.\n");
            return;
        }

        String selected = (String) JOptionPane.showInputDialog(
            combatPanel, "Select a spell or action:", "Spell Selection",
            JOptionPane.PLAIN_MESSAGE, null,
            allSpells.toArray(), allSpells.get(0)
        );

        if (selected != null) {
            setSelectedSpell(selected);
            appendMessage("Selected: " + selected + "\n");
        }
    }

    private void handleCastSpell() {
        if (selectedSpell == null) {
            appendMessage("No spell selected.\n");
            return;
        }

        Spell spell = findSpell(selectedSpell);
        if (spell == null) {
            appendMessage("Spell not found.\n");
            return;
        }

        if ("Restoring Light".equals(selectedSpell)) {
            castRestoringLight(spell);
        } else {
            try {
                spell.cast(myChar);
            } catch (Exception e) {
                try { spell.cast(); } catch (Exception ignored) { }
            }
        }
        updateNameAndHP();
    }

    private void castRestoringLight(Spell spell) {
        int choice = JOptionPane.showOptionDialog(
            combatPanel, "Cast on self or enemy?", "Choose Target",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, new Object[]{"Self", "Enemy"}, "Self"
        );
        if (choice == 0) {
            spell.cast(myChar, myChar);
            appendMessage("You cast Restoring Light on yourself.\n");
        } else if (choice == 1 && myEnemies != null && myEnemies.isUndead()) {
            spell.cast(myChar, myEnemies);
            appendMessage("You cast Restoring Light on " + myEnemies.getName() + ".\n");
        } else {
            appendMessage("Target is not undead. Spell has no effect.\n");
        }
    }

    private void handleRun() {
        appendMessage("You flee from combat!\n");
        endCombat();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void endCombat() {
        myEnemies = null;
        MainGameScreen.replaceWithAnyPanel(mainGamePanel);
        camera.endCombat();
    }




private void applyDamageToEnemy(int damage) {

    myEnemies.takeDamage(damage, null);

}




    private void applyIceBarrierEffect(Object bearer, Object target, String message) {
        try {
            Status st = null;
            if (bearer instanceof Character c && c.hasStatus("Ice Barrier")) {
                st = c.getStatusByName("Ice Barrier");
            } else if (bearer instanceof Enemies e && e.hasStatus("Ice Barrier")) {
                st = e.getStatusByName("Ice Barrier");
            }
            if (st instanceof IceBarrierStatus ib) {
                ImmobilizedStatus slow = new ImmobilizedStatus(Math.max(1, ib.getSlowDuration()));
                if (target instanceof Character c) c.addStatus(slow);
                else if (target instanceof Enemies e) e.addStatus(slow);
                appendMessage(message);
            }
        } catch (Exception ignored) { }
    }

    private Spell findSpell(String name) {
        SpellsManager manager = guildSpellsRegistry.getOrCreateManager(myChar.getName());
        if (manager == null) return null;
        try { return manager.getSpell(name); } catch (Exception ignored) { }
        return null;
    }

    private void updateNameAndHP() {
        if (playerInfo != null) {
            playerInfo.setText(
                myChar.getName() + "\nHP: " + myChar.getHitPoints() +
                "\nMP: " + myChar.getMagicPoints() + "\n" + getEquipmentInfo()
            );
        }
        if (enemyInfo != null) {
            enemyInfo.setText(myEnemies == null ? "No enemy" :
                myEnemies.getName() + "\nHP: " + myEnemies.getHitPoints() +
                "\nAlignment: " + alignmentLabel(myEnemies.getAlignment())
            );
        }
    }

    private String getEquipmentInfo() {
        return "Weapon: " + myChar.getEquippedWeapon() +
               System.lineSeparator() +
               "Armor: "  + myChar.getEquippedArmour();
    }



private String resolvePlayerImagePath() {
    String cls = myChar.getClassName();
    String normalized = (cls == null) ? "" : cls.trim();
    String file = switch (normalized.toLowerCase()) {
        case "bard", "cleric", "hunter", "mage", "minstrel",
             "paladin", "ranger", "rogue", "thief",
             "warrior", "wizard" -> normalized.toLowerCase() + ".png";
        default -> "default.png";
    };
return "src/DungeonoftheBrutalKing/Images/Classes/" + file;
}



    private boolean isCasterClass() {
        String cls = myChar.getClassName();
        return "Mage".equals(cls) || "Wizard".equals(cls);
    }

    private static String alignmentLabel(Alignment a) {
        return a == Alignment.GOOD ? "Good" : "Evil";
    }


private JLabel loadScaledImage(String path, String fallback) {
    BufferedImage img = null;

    // 1. Try as direct filesystem path (GameSettings provides src/ prefixed paths)
    try {
        File file = new File(path);
        if (file.exists()) {
            img = ImageIO.read(file);
        }
    } catch (IOException ignored) { }

    // 2. Try as classpath resource (with and without leading slash)
    if (img == null) {
        for (String resourcePath : new String[]{ path, "/" + path }) {
            try {
                java.net.URL url = getClass().getResource(resourcePath);
                if (url != null) { img = ImageIO.read(url); break; }
            } catch (IOException ignored) { }
        }
    }

    // 3. Try as classpath stream
    if (img == null) {
        String resourcePath = path.startsWith("/") ? path : "/" + path;
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is != null) img = ImageIO.read(is);
        } catch (IOException ignored) { }
    }

    if (img != null) {
        Image scaled = img.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaled));
        label.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        label.setMaximumSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    System.err.println("Could not load image: " + path);
    JLabel label = new JLabel(fallback);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
    return label;
}




    private static JTextArea makeInfoArea(String text) {
        JTextArea area = new JTextArea(text);
        area.setEditable(false);
        area.setBackground(new Color(255, 255, 220));
        area.setAlignmentX(Component.CENTER_ALIGNMENT);
        area.setMaximumSize(new Dimension(IMAGE_WIDTH, area.getPreferredSize().height));
        return area;
    }

    private static void appendMessage(String msg) {
        MainGameScreen.appendToMessageTextPane(msg);
    }

    private static GridBagConstraints gridCell(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; gbc.gridy = y;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    private static GridBagConstraints gridSpan(int x, int y, int width) {
        GridBagConstraints gbc = gridCell(x, y);
        gbc.gridwidth = width;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }
    
    
}
