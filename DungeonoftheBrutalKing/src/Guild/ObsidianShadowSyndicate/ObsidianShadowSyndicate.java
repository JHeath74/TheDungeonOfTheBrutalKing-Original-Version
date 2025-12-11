
// src/Guild/ObsidianShadowSyndicate/ObsidianShadowSyndicate.java
package Guild.ObsidianShadowSyndicate;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GuildType;
import SharedData.GuildMembershipStatus;

public class ObsidianShadowSyndicate extends JPanel {

    private static final long serialVersionUID = 1L;
    private final String guildName = "Obsidian Shadow Syndicate";
    private final String description = "The Obsidian Shadow Syndicate is a secretive and ruthless guild of master thieves, thriving in darkness and chaos, feared for their cunning and evil deeds.";
    private final Alignment alignment = Alignment.EVIL;
    GuildType guildType = GuildType.THIEF;

    public ObsidianShadowSyndicate() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        GuildMembershipStatus status = character.getGuildStatus(guildType);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/ObsidianShadowSyndicate.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton stealButton = new JButton("Steal");
        JButton pickLockButton = new JButton("Pick Lock");
        JButton bribeButton = new JButton("Bribe");
        JButton sellLootButton = new JButton("Sell Loot");
        JButton enterHideoutButton = new JButton("Enter Hideout");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton exitRoomButton = new JButton("Exit Room");

        if (status == GuildMembershipStatus.NOT_MEMBER) {
            JButton questButton = new JButton("Start Guild Heist");
            questButton.addActionListener(event -> {
                character.setGuildStatus(guildType, GuildMembershipStatus.INITIATE);
                JOptionPane.showMessageDialog(this, "Heist complete! You are now an Initiate.");
                try { reloadPanel(); } catch (Exception ex) { ex.printStackTrace(); }
            });
            buttonPanel.add(questButton);
        } else if (status == GuildMembershipStatus.INITIATE) {
            JButton initiationButton = new JButton("Complete Initiation Task");
            initiationButton.addActionListener(event -> {
                character.setGuildStatus(guildType, GuildMembershipStatus.FULL_MEMBER);
                character.addToInventory("Obsidian Shadow Syndicate Emblem");
                JOptionPane.showMessageDialog(this, "You are now a full member and received the Syndicate Emblem!");
                try { reloadPanel(); } catch (Exception ex) { ex.printStackTrace(); }
            });
            buttonPanel.add(initiationButton);
        } else if (status == GuildMembershipStatus.FULL_MEMBER) {
            buttonPanel.add(stealButton);
            buttonPanel.add(pickLockButton);
            buttonPanel.add(bribeButton);
            buttonPanel.add(sellLootButton);
            buttonPanel.add(enterHideoutButton);
            buttonPanel.add(eatFoodButton);
            buttonPanel.add(sleepBedButton);
        }
        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        stealButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You attempt a daring theft..."));
        pickLockButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You pick a complex lock..."));
        bribeButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You bribe a guard for information..."));
        sellLootButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Selling stolen loot..."));
        enterHideoutButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Entering the secret hideout..."));
        eatFoodButton.addActionListener(event -> {
            int currentFood = character.getFood();
            if (currentFood > 0) {
                character.setFood(currentFood - 1);
                JOptionPane.showMessageDialog(this, "You eat a quick meal. Food left: " + character.getFood());
            } else {
                JOptionPane.showMessageDialog(this, "You have no food to eat.");
            }
        });
        sleepBedButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You rest in a hidden bed and recover your strength."));
        exitRoomButton.addActionListener(event -> {
            try { MainGameScreen.getInstance().restoreOriginalPanel(); } catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new ObsidianShadowSyndicate());
    }

    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }
}
