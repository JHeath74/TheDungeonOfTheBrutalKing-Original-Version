
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
    combatPanel = new JPanel(new GridBagLayout());
    MainGameScreen.replaceWithAnyPanel(combatPanel);

    myEnemies = MonsterSelector.selectRandomMonster();
    if (myEnemies == null) {
        JOptionPane.showMessageDialog(combatPanel, "No monster found!");
        combatPanel.setVisible(true);
        return;
    }

    JPanel playerPanel = new JPanel();
    playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
    BufferedImage playerImg = null;
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
    playerImg = ImageIO.read(new File(imagePath));
    playerPicLabel = (playerImg == null) ? new JLabel("Image not found") : new JLabel(new ImageIcon(playerImg));
} catch (IOException e) {
    playerPicLabel = new JLabel("Image not found");
}

    playerPicLabel.setPreferredSize(new Dimension(150, 200));
    playerPanel.add(playerPicLabel);

    JTextArea playerInfo = new JTextArea(
        myChar.getName() + "\nHP: " + myChar.getHitPoints() + "\nMP: " + myChar.getMagicPoints()
    );
    playerInfo.setEditable(false);
    playerInfo.setBackground(new Color(255, 255, 220));
    playerPanel.add(playerInfo);

    JPanel enemyPanel = new JPanel();
    enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
    BufferedImage enemyImg = null;
    JLabel enemyPicLabel;
    try {
        enemyImg = ImageIO.read(new File(myEnemies.getImagePath()));
        enemyPicLabel = (enemyImg == null) ? new JLabel("Image not found") : new JLabel(new ImageIcon(enemyImg));
    } catch (IOException e) {
        enemyPicLabel = new JLabel("Image not found");
    }
    enemyPicLabel.setPreferredSize(new Dimension(150, 200));
    enemyPanel.add(enemyPicLabel);

    JTextArea enemyInfo = new JTextArea(
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

    combatAttackButton.addActionListener(_ -> handleAttack());
    selectSpellButton.addActionListener(_ -> handleSelectSpell());
    castSelectedSpellButton.addActionListener(_ -> handleCastSpell());
    combatRunButton.addActionListener(_ -> handleRun());
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


    private void updateNameAndHP() {
	// TODO Auto-generated method stub
	
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
