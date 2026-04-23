
// src/DungeonoftheBrutalKing/Guild/CrimsonVeilRogues/CrimsonVeilRogues.java
package DungeonoftheBrutalKing.Guild.CrimsonVeilRogues;

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

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import DungeonoftheBrutalKing.SharedData.GuildSpellsDialog;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Guild.CrimsonVeilRogues.Spells.CrimsonVeilRoguesGuildSpellsManager;

public class CrimsonVeilRogues extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Crimson Veil Rogues";
    private final String description =
            "The Crimson Veil Rogues are a notorious guild of evil rogues, masters of deception, stealth, and ruthless ambition.";
    private final Alignment alignment = Alignment.EVIL;
    private final GuildType guildType = GuildType.ROGUE;

    public CrimsonVeilRogues() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        Character character = Character.getInstance();
        GuildMembershipStatus status = character.getCurrentGuildStatus();

        JLabel imageLabel = new JLabel(new ImageIcon(
                getClass().getResource("/DungeonoftheBrutalKing/Images/CrimsonVeilRogues.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
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
            questButton.addActionListener(_ -> {
                character.setCurrentGuildStatus(GuildMembershipStatus.INITIATE);
                JOptionPane.showMessageDialog(this, "Trial complete! You are now an Initiate.");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                    if (ex instanceof InterruptedException) Thread.currentThread().interrupt();
                }
            });
            buttonPanel.add(questButton);
        } else if (status == GuildMembershipStatus.INITIATE) {
            JButton initiationButton = new JButton("Complete Initiation Task");
            initiationButton.addActionListener(_ -> {
                character.setCurrentGuildStatus(GuildMembershipStatus.FULL_MEMBER);
                character.addToInventory("Crimson Veil Emblem");
                JOptionPane.showMessageDialog(this,
                        "You are now a full member and received the Crimson Veil Emblem!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                    if (ex instanceof InterruptedException) Thread.currentThread().interrupt();
                }
            });
            buttonPanel.add(initiationButton);
        } else if (status == GuildMembershipStatus.FULL_MEMBER) {
            buttonPanel.add(buySpellsButton);
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

        buySpellsButton.addActionListener(_ -> {
            try {
                java.awt.Window owner = SwingUtilities.getWindowAncestor(this);

                CrimsonVeilRoguesGuildSpellsManager manager =
                        new CrimsonVeilRoguesGuildSpellsManager(Guild.CRIMSON_VEIL_ROGUES);

                GuildSpellsDialog dlg = new GuildSpellsDialog(
                        (java.awt.Frame) owner,
                        Character.getInstance(),
                        Guild.CRIMSON_VEIL_ROGUES,
                        manager
                );
                dlg.setVisible(true);
            } catch (Exception ex) {
                buyGuildSpell();
            }
        });

        ambushButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You set up a deadly ambush..."));
        sneakButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You move silently through the shadows..."));
        sabotageButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You sabotage a rival's plans..."));
        sellSecretsButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Selling stolen secrets..."));
        enterLairButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Entering the hidden lair..."));

        eatFoodButton.addActionListener(_ -> {
            int currentFood = character.getFood();
            if (currentFood > 0) {
                character.setFood(currentFood - 1);
                JOptionPane.showMessageDialog(this, "You eat a quick meal. Food left: " + character.getFood());
            } else {
                JOptionPane.showMessageDialog(this, "You have no food to eat.");
            }
        });

        sleepBedButton.addActionListener(_ ->
                JOptionPane.showMessageDialog(this, "You rest in a secret bed and recover your strength."));

        exitRoomButton.addActionListener(_ -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to exit the room right now.\n" + ex.getClass().getSimpleName() + ": " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                if (ex instanceof InterruptedException) Thread.currentThread().interrupt();
            }
        });
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        setLayout(new BorderLayout());
        add(new CrimsonVeilRogues(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }
    public GuildType getGuildType() { return guildType; }

    private static boolean isEvil(int alignmentValue) {
        return alignmentValue < 0;
    }

    // Legacy fallback for spell buying
    private void buyGuildSpell() {
        Character player = Character.getInstance();
        int maxSpells = 6;

        if (player.getCurrentGuildStatus() != GuildMembershipStatus.FULL_MEMBER) {
            JOptionPane.showMessageDialog(this,
                    "You must be a full member of the Crimson Veil Rogues to buy guild spells.");
            return;
        }
        if (!isEvil(player.getAlignment())) {
            JOptionPane.showMessageDialog(this,
                    "You are not evil (alignment < 0). The Crimson Veil Rogues won't deal with you.");
            return;
        }
        if (!player.getCharInventory().contains("Crimson Veil Emblem")) {
            JOptionPane.showMessageDialog(this, "You need the Crimson Veil Emblem to buy guild spells.");
            return;
        }
        if (getGuildSpellsCount() >= maxSpells) {
            JOptionPane.showMessageDialog(this, "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }

        CrimsonVeilRoguesGuildSpellsManager manager =
                new CrimsonVeilRoguesGuildSpellsManager(Guild.CRIMSON_VEIL_ROGUES);
        java.util.Map<String, Spell> all = manager.getAllSpells();

        java.util.Set<String> owned = Character.getInstance().getGuildSpells();
        java.util.Set<String> ownedLower = new java.util.HashSet<>();
        for (String o : owned) if (o != null) ownedLower.add(o.toLowerCase());

        java.util.List<String> available = new java.util.ArrayList<>();
        for (Spell sp : all.values()) {
            if (sp == null) continue;
            String canon = sp.getName();
            if (canon == null) continue;
            if (!ownedLower.contains(canon.toLowerCase())) available.add(canon);
        }

        if (available.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There are no new guild spells available to purchase right now.");
        }
    }

    public int getGuildSpellsCount() { return Character.getInstance().getGuildSpells().size(); }

    public void addGuildSpell(String spell) {
        if (Character.getInstance().getGuildSpells().size() < 6) {
            Character.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }

    public boolean removeGuildSpell(String spell) {
        return Character.getInstance().getGuildSpells().remove(spell);
    }
}
