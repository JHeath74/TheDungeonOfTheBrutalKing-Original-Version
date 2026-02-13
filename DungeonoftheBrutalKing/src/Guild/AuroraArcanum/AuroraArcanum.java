
package Guild.AuroraArcanum;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GuildType;
import SharedData.GuildMembershipStatus;

public class AuroraArcanum extends JPanel {

    private static final long serialVersionUID = 1L;
    private final String guildName = "Aurora Arcanum";
    private final String description = "The Aurora Arcanum is a guild of enlightened sorcerers who harness the power of celestial magic to bring balance and wisdom to the realm.";
    private final Alignment alignment = Alignment.GOOD;
    private final GuildType guildType = GuildType.WIZARD;

    public AuroraArcanum() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        Charecter charecter = Charecter.getInstance();
        GuildMembershipStatus status = charecter.getGuildStatus(guildType);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/AuroraArcanum.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton enchantItemButton = new JButton("Enchant Item");
        JButton removeCurseButton = new JButton("Remove Curses/Effects");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Guild Storage");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton exitRoomButton = new JButton("Exit Room");

        if (status == GuildMembershipStatus.NOT_MEMBER) {
            JButton questButton = new JButton("Start Guild Quest");
            questButton.addActionListener(_ -> {
                charecter.setGuildStatus(guildType, GuildMembershipStatus.INITIATE);
                JOptionPane.showMessageDialog(this, "Quest complete! You are now an Initiate.");
                reloadPanelSafe();
            });
            buttonPanel.add(questButton);
        } else if (status == GuildMembershipStatus.INITIATE) {
            JButton initiationButton = new JButton("Complete Initiation Task");
            initiationButton.addActionListener(_ -> {
                charecter.setGuildStatus(guildType, GuildMembershipStatus.FULL_MEMBER);
                charecter.addToInventory("Aurora Arcanum Guild Ring");
                JOptionPane.showMessageDialog(this, "You are now a full member and received the Guild Ring!");
                reloadPanelSafe();
            });
            buttonPanel.add(initiationButton);
        } else if (status == GuildMembershipStatus.FULL_MEMBER) {
            buttonPanel.add(buySpellsButton);
            buttonPanel.add(enchantItemButton);
            buttonPanel.add(removeCurseButton);
            buttonPanel.add(sellItemsButton);
            buttonPanel.add(enterStorageButton);
            buttonPanel.add(eatFoodButton);
            buttonPanel.add(sleepBedButton);
        }
        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(_ -> buyGuildSpell());
        enchantItemButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Enchanting item..."));
        removeCurseButton.addActionListener(_ -> { removeCursesAndEffects(); JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!"); });
        sellItemsButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(_ -> {
            int currentFood = charecter.getFood();
            if (currentFood > 0) {
                charecter.setFood(currentFood - 1);
                JOptionPane.showMessageDialog(this, "You eat a hearty meal. Food left: " + charecter.getFood());
            } else {
                JOptionPane.showMessageDialog(this, "You have no food to eat.");
            }
        });
        sleepBedButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You rest in a comfortable bed and recover your strength."));
        exitRoomButton.addActionListener(_ -> {
            try { MainGameScreen.getInstance().restoreOriginalPanel(); } catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    private void removeCursesAndEffects() {
        Charecter charecter = Charecter.getInstance();
        charecter.clearCurses();
        charecter.clearNegativeEffects();
    }

    private void buyGuildSpell() {
        Charecter charecter = Charecter.getInstance();
        int wisdom = charecter.getWisdom();
        int alignmentValue = charecter.getAlignment();
        int maxSpells = 6;
        int currentGuildSpells = getGuildSpellsCount();

        if (currentGuildSpells >= maxSpells) {
            JOptionPane.showMessageDialog(this, "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }
        if (wisdom <= 0) {
            JOptionPane.showMessageDialog(this, "You need sufficient wisdom to buy guild spells.");
            return;
        }
        if (alignmentValue < 100) {
            JOptionPane.showMessageDialog(this, "Your alignment is evil. You cannot buy guild spells.");
            return;
        }
        String newSpell = "New Guild Spell";
        addGuildSpell(newSpell);
        JOptionPane.showMessageDialog(this, "You have successfully bought the guild spell: " + newSpell);
    }

    private void reloadPanelSafe() {
        SwingUtilities.invokeLater(() -> {
            JPanel parent = (JPanel) getParent();
            if (parent != null) {
                parent.removeAll();
                try {
                    parent.add(new AuroraArcanum());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                parent.revalidate();
                parent.repaint();
            }
        });
    }

    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }
    public boolean removeGuildSpell(String spell) { return Charecter.getInstance().getGuildSpells().remove(spell); }
    public int getGuildSpellsCount() { return Charecter.getInstance().getGuildSpells().size(); }
    public void addGuildSpell(String spell) {
        if (Charecter.getInstance().getGuildSpells().size() < 6) {
            Charecter.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }
}
