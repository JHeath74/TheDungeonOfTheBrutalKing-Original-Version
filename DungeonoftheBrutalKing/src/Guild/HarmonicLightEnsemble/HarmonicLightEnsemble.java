
// `src/Guild/HarmonicLightEnsemble/HarmonicLightEnsemble.java`
package Guild.HarmonicLightEnsemble;

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

public class HarmonicLightEnsemble extends JPanel {

    private static final long serialVersionUID = 1L;
    private final String guildName = "Harmonic Light Ensemble";
    private final String description = "A guild of bards whose melodies heal, uplift, and rally the forces of good.";
    private final Alignment alignment = Alignment.GOOD;
    private final GuildType guildType = GuildType.MINSTREL;

    public HarmonicLightEnsemble() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();

        // Keep current guild in sync for this panel.
        character.setCurrentGuild(guildType);
        GuildMembershipStatus status = character.getCurrentGuildStatus();

        // Show description on entry for non-members (and also for EVIL characters entering a GOOD-only guild).
        if (status == GuildMembershipStatus.NOT_MEMBER || !isGood(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource(
                "/DungeonoftheBrutalKing/Images/HarmonicLightEnsemble.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton performSongButton = new JButton("Perform Song of Healing");
        JButton inspireButton = new JButton("Inspire Allies");
        JButton removeDebuffButton = new JButton("Remove Negative Effects");
        JButton sellMusicButton = new JButton("Sell Sheet Music");
        JButton enterStorageButton = new JButton("Guild Storage");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton exitRoomButton = new JButton("Exit Room");

        if (status == GuildMembershipStatus.NOT_MEMBER) {
            JButton questButton = new JButton("Start Guild Quest");
            questButton.addActionListener(evt -> {
                if (!isGood(character.getAlignment())) {
                    JOptionPane.showMessageDialog(this,
                            "You are not good (`alignment >= 0`). The Harmonic Light Ensemble refuses you.");
                    return;
                }
                character.setCurrentGuild(guildType);
                character.setCurrentGuildStatus(GuildMembershipStatus.INITIATE);
                JOptionPane.showMessageDialog(this, "Quest complete! You are now an Initiate.");
                try {
                    reloadPanel();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(questButton);
        } else if (status == GuildMembershipStatus.INITIATE) {
            JButton initiationButton = new JButton("Complete Initiation Performance");
            initiationButton.addActionListener(evt -> {
                if (!isGood(character.getAlignment())) {
                    JOptionPane.showMessageDialog(this,
                            "You are not good (`alignment >= 0`). You cannot advance in this guild.");
                    return;
                }
                character.setCurrentGuild(guildType);
                character.setCurrentGuildStatus(GuildMembershipStatus.FULL_MEMBER);
                character.addToInventory("Harmonic Light Ensemble Lute Pin");
                JOptionPane.showMessageDialog(this, "You are now a full member and received the Lute Pin!");
                try {
                    reloadPanel();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(initiationButton);
        } else if (status == GuildMembershipStatus.FULL_MEMBER) {
            if (!isGood(character.getAlignment())) {
                JOptionPane.showMessageDialog(this,
                        "You are not good (`alignment >= 0`). You cannot use Harmonic Light Ensemble services.");
            } else {
                buttonPanel.add(performSongButton);
                buttonPanel.add(inspireButton);
                buttonPanel.add(removeDebuffButton);
                buttonPanel.add(sellMusicButton);
                buttonPanel.add(enterStorageButton);
                buttonPanel.add(eatFoodButton);
                buttonPanel.add(sleepBedButton);
            }
        }

        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Avoid unused lambda parameter warnings by not referencing the event parameter.
        performSongButton.addActionListener(evt -> performSongOfHealing());
        inspireButton.addActionListener(evt ->
                JOptionPane.showMessageDialog(this, "You inspire your allies! Their morale is restored."));
        removeDebuffButton.addActionListener(evt -> {
            removeNegativeEffects();
            JOptionPane.showMessageDialog(this, "All negative effects have been removed!");
        });
        sellMusicButton.addActionListener(evt ->
                JOptionPane.showMessageDialog(this, "You sell your sheet music for gold."));
        enterStorageButton.addActionListener(evt ->
                JOptionPane.showMessageDialog(this, "Accessing guild storage..."));

        eatFoodButton.addActionListener(evt -> {
            int currentFood = character.getFood();
            if (currentFood > 0) {
                character.setFood(currentFood - 1);
                JOptionPane.showMessageDialog(this, "You enjoy a meal. Food left: " + character.getFood());
            } else {
                JOptionPane.showMessageDialog(this, "You have no food to eat.");
            }
        });

        sleepBedButton.addActionListener(evt ->
                JOptionPane.showMessageDialog(this,
                        "You rest in a comfortable bed and recover your strength."));

        exitRoomButton.addActionListener(evt -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    // Project-wide alignment rule: alignment >= 0 == GOOD, alignment < 0 == EVIL.
    private static boolean isGood(int alignmentValue) {
        return alignmentValue >= 0;
    }

    private void removeNegativeEffects() {
        Charecter character = Charecter.getInstance();
        character.clearNegativeEffects();
    }

    private void performSongOfHealing() {
        Charecter character = Charecter.getInstance();
        int maxHP = character.getMaxHitPoints();
        int maxMP = character.getMaxMagicPoints();
        character.setHitPoints(maxHP);
        character.setMagicPoints(maxMP);
        JOptionPane.showMessageDialog(this, "Your song restores you to full health and magic!");
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new HarmonicLightEnsemble());
    }

    public String getDescription() { return description; }

    public Alignment getAlignment() { return alignment; }

    public String getGuildName() { return guildName; }
}
