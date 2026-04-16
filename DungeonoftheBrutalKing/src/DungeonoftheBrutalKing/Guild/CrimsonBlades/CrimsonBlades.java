
// src/DungeonoftheBrutalKing/Guild/CrimsonBlades/CrimsonBlades.java
package DungeonoftheBrutalKing.Guild.CrimsonBlades;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildSpellsDialog;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Guild.CrimsonBlades.Spells.CrimsonBladesGuildSpellsManager;

public class CrimsonBlades extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Crimson Blades";
    private boolean isMember;
    private final String description;
    private final Alignment alignment = Alignment.EVIL;
    private final GuildType guildType = GuildType.WARRIOR;

    public CrimsonBlades(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Crimson Blades are a fierce guild of warriors, renowned for their skill and honor in battle.";

        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        if (!this.isMember || !isEvil(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        if (!this.isMember && !inventory.contains("Crimson Blades Guild Ring")) {
            if (!isEvil(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not evil (`alignment < 0`). The Crimson Blades reject you."
                );
                return;
            }

            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You are not a member of the Crimson Blades. Would you like to join?",
                    "Join Guild",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] { "Join", "Stay/Leave" },
                    "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Crimson Blades Guild Ring");
                JOptionPane.showMessageDialog(
                        this,
                        "You have joined the Crimson Blades and received the Crimson Blades Guild Ring!"
                );
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        JLabel imageLabel = new JLabel(new ImageIcon(
                getClass().getResource("/DungeonoftheBrutalKing/Images/CrimsonBlades.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton sharpenBladeButton = new JButton("Sharpen Blade");
        JButton removeCurseButton = new JButton("Remove Curses/Effects");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Guild Storage");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton trainButton = new JButton("Train Skills");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!this.isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(e -> {
                Charecter ch = Charecter.getInstance();
                if (!isEvil(ch.getAlignment())) {
                    JOptionPane.showMessageDialog(
                            this,
                            "You are not evil (`alignment < 0`). The Crimson Blades reject you."
                    );
                    return;
                }

                this.isMember = true;
                ch.addToInventory("Crimson Blades Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Crimson Blades!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            if (!isEvil(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not evil (`alignment < 0`). You cannot use Crimson Blades services."
                );
            } else {
                buttonPanel.add(buySpellsButton);
                buttonPanel.add(sharpenBladeButton);
                buttonPanel.add(removeCurseButton);
                buttonPanel.add(sellItemsButton);
                buttonPanel.add(enterStorageButton);
                buttonPanel.add(eatFoodButton);
                buttonPanel.add(sleepBedButton);
                buttonPanel.add(trainButton);
            }
        }

        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(e -> {
            try {
                java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
                DungeonoftheBrutalKing.Spells.SpellsManager sm = new DungeonoftheBrutalKing.Spells.SpellsManager();
                GuildSpellsDialog dlg = new GuildSpellsDialog(
                        (java.awt.Frame) owner,
                        Charecter.getInstance(),
                        Guild.CRIMSON_BLADES,
                        sm
                );
                dlg.setVisible(true);
            } catch (Exception ex) {
                buyGuildSpell();
            }
        });

        sharpenBladeButton.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "You sharpen your blade, ready for battle! (Crimson Blades exclusive service)"
        ));

        removeCurseButton.addActionListener(e -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });

        sellItemsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You eat a hearty meal and feel invigorated."));
        sleepBedButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You rest in a sturdy bed and recover your strength."));
        trainButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You train rigorously, improving your skills."));

        exitRoomButton.addActionListener(e -> {
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
                if (ex instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        });}

    private static boolean isEvil(int alignmentValue) {
        return alignmentValue < 0;
    }

    private void removeCursesAndEffects() {
        Charecter character = Charecter.getInstance();
        character.clearCurses();
        character.clearNegativeEffects();
    }

    private void buyGuildSpell() {
        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());
        int maxSpells = 6;

        if (!isMember) {
            JOptionPane.showMessageDialog(this,
                    "You must be a member of the Crimson Blades to buy guild spells.");
            return;
        }

        if (!isEvil(character.getAlignment())) {
            JOptionPane.showMessageDialog(this,
                    "You are not evil (`alignment < 0`). You cannot buy guild spells here.");
            return;
        }

        if (!inventory.contains("Crimson Blades Guild Ring")) {
            JOptionPane.showMessageDialog(this,
                    "You need the Crimson Blades Guild Ring to buy guild spells.");
            return;
        }

        if (getGuildSpellsCount() >= maxSpells) {
            JOptionPane.showMessageDialog(this,
                    "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }

        CrimsonBladesGuildSpellsManager manager = new CrimsonBladesGuildSpellsManager(Guild.CRIMSON_BLADES);
        java.util.Map<String, Spell> all = manager.getAllSpells();

        java.util.Set<String> owned = Charecter.getInstance().getGuildSpells();
        java.util.Set<String> ownedLower = new java.util.HashSet<>();
        for (String o : owned) {
            if (o != null) ownedLower.add(o.toLowerCase());
        }

        java.util.List<String> available = new java.util.ArrayList<>();
        for (Spell sp : all.values()) {
            if (sp == null) continue;
            String canon = sp.getName();
            if (canon == null) continue;
            if (!ownedLower.contains(canon.toLowerCase())) available.add(canon);
        }

        if (available.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There are no new guild spells available to purchase right now.");
            return;
        }

        String choice = (String) JOptionPane.showInputDialog(
                this,
                "Choose a guild spell to buy:",
                "Buy Guild Spell",
                JOptionPane.QUESTION_MESSAGE,
                null,
                available.toArray(new String[0]),
                available.get(0)
        );

        if (choice == null || choice.isBlank()) return;

        addGuildSpell(choice);
        JOptionPane.showMessageDialog(this, "You learned: " + choice);
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        setLayout(new BorderLayout());
        CrimsonBlades rebuilt = new CrimsonBlades(isMember);
        add(rebuilt, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public String getDescription() {
        return description;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getGuildName() {
        return guildName;
    }

    public GuildType getGuildType() {
        return guildType;
    }

    public boolean removeGuildSpell(String spell) {
        return Charecter.getInstance().getGuildSpells().remove(spell);
    }

    public int getGuildSpellsCount() {
        return Charecter.getInstance().getGuildSpells().size();
    }

    public void addGuildSpell(String spell) {
        if (Charecter.getInstance().getGuildSpells().size() < 6) {
            Charecter.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }

    public ArrayList<String> getGuildSpells() {
        return new ArrayList<>(Charecter.getInstance().getGuildSpells());
    }
}
