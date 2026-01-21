
// src/Guild/CrimsonVeilRogues/CrimsonVeilRogues.java
package Guild.CrimsonVeilRogues;

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

public class CrimsonVeilRogues extends JPanel {

    private static final long serialVersionUID = 1L;
    private final String guildName = "Crimson Veil Rogues";
    private final String description = "The Crimson Veil Rogues are a notorious guild of evil rogues, masters of deception, stealth, and ruthless ambition.";
    private final Alignment alignment = Alignment.EVIL;
    GuildType guildType = GuildType.ROGUE;

    public CrimsonVeilRogues() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        GuildMembershipStatus status = character.getGuildStatus(guildType);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/CrimsonVeilRogues.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton ambushButton = new JButton("Ambush");
        JButton sneakButton = new JButton("Sneak");
        JButton sabotageButton = new JButton("Sabotage");
        JButton sellSecretsButton = new JButton("Sell Secrets");
        JButton enterLairButton = new JButton("Enter Lair");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton exitRoomButton = new JButton("Exit Room");

        if (status == GuildMembershipStatus.NOT_MEMBER) {
            JButton questButton = new JButton("Start Rogue Trial");
            questButton.addActionListener(event -> {
                character.setGuildStatus(guildType, GuildMembershipStatus.INITIATE);
                JOptionPane.showMessageDialog(this, "Trial complete! You are now an Initiate.");
                try { reloadPanel(); } catch (Exception ex) { ex.printStackTrace(); }
            });
            buttonPanel.add(questButton);
        } else if (status == GuildMembershipStatus.INITIATE) {
            JButton initiationButton = new JButton("Complete Initiation Task");
            initiationButton.addActionListener(event -> {
                character.setGuildStatus(guildType, GuildMembershipStatus.FULL_MEMBER);
                character.addToInventory("Crimson Veil Emblem");
                JOptionPane.showMessageDialog(this, "You are now a full member and received the Crimson Veil Emblem!");
                try { reloadPanel(); } catch (Exception ex) { ex.printStackTrace(); }
            });
            buttonPanel.add(initiationButton);
        } else if (status == GuildMembershipStatus.FULL_MEMBER) {
            buttonPanel.add(ambushButton);
            buttonPanel.add(sneakButton);
            buttonPanel.add(sabotageButton);
            buttonPanel.add(sellSecretsButton);
            buttonPanel.add(enterLairButton);
            buttonPanel.add(eatFoodButton);
            buttonPanel.add(sleepBedButton);
        }
        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        ambushButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You set up a deadly ambush..."));
        sneakButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You move silently through the shadows..."));
        sabotageButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You sabotage a rival's plans..."));
        sellSecretsButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Selling stolen secrets..."));
        enterLairButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "Entering the hidden lair..."));
        eatFoodButton.addActionListener(event -> {
            int currentFood = character.getFood();
            if (currentFood > 0) {
                character.setFood(currentFood - 1);
                JOptionPane.showMessageDialog(this, "You eat a quick meal. Food left: " + character.getFood());
            } else {
                JOptionPane.showMessageDialog(this, "You have no food to eat.");
            }
        });
        sleepBedButton.addActionListener(event -> JOptionPane.showMessageDialog(this, "You rest in a secret bed and recover your strength."));
        exitRoomButton.addActionListener(event -> {
            try { MainGameScreen.getInstance().restoreOriginalPanel(); } catch (Exception ex) { ex.printStackTrace(); }
        });
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new CrimsonVeilRogues());
    }

    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }
}
