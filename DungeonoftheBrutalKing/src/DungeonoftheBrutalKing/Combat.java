
// Java

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
import SharedData.GameSettings;
import Spells.Spells;

public class Combat {

    private static final int RANDOM_CHANCE_MAX = 100;
    private final GameSettings myGameSettings = new GameSettings();
    private final Charecter myChar = Charecter.Singleton();

    private Enemies myEnemies;
    private String selectedSpell = null;

    private JPanel combatPanel, combatImagePanel, combatPanelButtons, combatRightPanel,
            combatUpdateInfoPanel, combatNameAndHPPanel;
    private JTextArea combatMessageArea, combatNameAndHPfield;
    private JButton combatAttackButton, castSelectedSpellButton, selectSpellButton, combatRunButton;
    private JLabel picLabel;
    private Timer timer;

    public Combat() throws IOException {
        int heroHP = myChar.getHitPoints();
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

        // Image panel (fixed width)
        combatImagePanel = new JPanel();
        combatImagePanel.setPreferredSize(new Dimension(150, 200));
        String imageDir = GameSettings.MonsterImagePath;
        String enemyImageFile = new File(myEnemies.getImagePath()).getName();
        File imageFile = new File(imageDir, enemyImageFile);

        BufferedImage img = null;
        try {
            img = ImageIO.read(imageFile);
            picLabel = (img == null) ? new JLabel("Image not found") : new JLabel(new ImageIcon(img));
        } catch (IOException e) {
            picLabel = new JLabel("Image not found");
        }
        picLabel.setPreferredSize(new Dimension(150, 200));
        combatImagePanel.add(picLabel);

        // Right panel (vertical layout)
        combatRightPanel = new JPanel();
        combatRightPanel.setLayout(new BoxLayout(combatRightPanel, BoxLayout.Y_AXIS));

        combatNameAndHPPanel = new JPanel(new FlowLayout());
        combatNameAndHPfield = new JTextArea(2, 40);
        combatNameAndHPfield.setLineWrap(false);
        combatNameAndHPfield.setEditable(false);
        combatNameAndHPfield.setBackground(myGameSettings.getColorLightYellow());
        combatNameAndHPPanel.setBackground(myGameSettings.getColorCoral());
        combatNameAndHPPanel.add(combatNameAndHPfield);

        combatUpdateInfoPanel = new JPanel();
        combatMessageArea = new JTextArea(12, 40);
        combatUpdateInfoPanel.add(combatMessageArea);

        combatPanelButtons = new JPanel(new FlowLayout());
        combatAttackButton = new JButton("Attack");
        castSelectedSpellButton = new JButton("Cast Selected Spell");
        selectSpellButton = new JButton("Select Spell to Cast");
        combatRunButton = new JButton("Run Away!");
        combatPanelButtons.add(combatAttackButton);
        combatPanelButtons.add(castSelectedSpellButton);
        combatPanelButtons.add(selectSpellButton);
        combatPanelButtons.add(combatRunButton);

        combatRightPanel.add(combatNameAndHPPanel);
        combatRightPanel.add(combatUpdateInfoPanel);

        combatPanel.add(combatImagePanel, BorderLayout.WEST);
        combatPanel.add(combatRightPanel, BorderLayout.CENTER);
        combatPanel.add(combatPanelButtons, BorderLayout.SOUTH);

        updateNameAndHP();

        timer = new Timer(1000, _ -> updateNameAndHP());
        timer.setRepeats(true);
        timer.start();

        combatAttackButton.addActionListener(_ -> handleAttack());
        selectSpellButton.addActionListener(_ -> handleSelectSpell());
        castSelectedSpellButton.addActionListener(_ -> handleCastSpell());
        combatRunButton.addActionListener(_ -> handleRun());

        combatPanel.revalidate();
        combatPanel.repaint();
    }

    // --- Add missing methods below ---

    private void updateNameAndHP() {
        if (combatNameAndHPfield != null && myEnemies != null && myChar != null) {
            combatNameAndHPfield.setText(
                "Monster: " + myEnemies.getName() + " | HP: " + myEnemies.getHitPoints() +
                "\nHero: " + myChar.getName() + " | HP: " + myChar.getHitPoints()
            );
        }
    }


private void handleAttack() {
    if (myEnemies != null && myChar != null) {
        int damage = myChar.getAttackDamage(); // Assumes Charecter has getAttackDamage()
        int actualDamage = monsterDefend(damage);
        monsterTakeDamage(actualDamage);

        combatMessageArea.append("You attack " + myEnemies.getName() +
            " for " + actualDamage + " damage.\n");

        if (isMonsterDead()) {
            combatMessageArea.append("Monster defeated!\n");
        }
        updateNameAndHP();
    }
}


    private void handleSelectSpell() {
        combatMessageArea.append("Select Spell button pressed.\n");
    }

    private void handleCastSpell() {
        combatMessageArea.append("Cast Selected Spell button pressed.\n");
    }

    private void handleRun() {
        combatMessageArea.append("Run Away button pressed.\n");
    }

    // --- Added Getters, Setters, and Monster Logic Wrappers ---

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
