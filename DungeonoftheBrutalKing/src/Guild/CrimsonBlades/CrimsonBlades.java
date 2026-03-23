// `src/Guild/CrimsonBlades/CrimsonBlades.java`
package Guild.CrimsonBlades;

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
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import SharedData.Guild;
import Spells.Spell;
import Spells.SpellFactory;
import Guild.CrimsonBlades.Spells.CrimsonBladesGuildSpellsManager;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GuildType;

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

        // Show description on entry for non-members (and also for GOOD characters entering an EVIL-only guild).
        if (!this.isMember || !isEvil(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        // EVIL-only guild: block join prompt early if player is GOOD.
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
            joinGuildButton.addActionListener(evt -> {
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

        buySpellsButton.addActionListener(evt -> buyGuildSpell());

        sharpenBladeButton.addActionListener(evt -> JOptionPane.showMessageDialog(
                this,
                "You sharpen your blade, ready for battle! (Crimson Blades exclusive service)"
        ));

        removeCurseButton.addActionListener(evt -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });

        sellItemsButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You eat a hearty meal and feel invigorated."));
        sleepBedButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You rest in a sturdy bed and recover your strength."));
        trainButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You train rigorously, improving your skills."));

        exitRoomButton.addActionListener(evt -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
        });
    }

    // Project-wide alignment rule: alignment < 0 == EVIL, alignment >= 0 == GOOD.
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
            // still allow viewing/selling but purchases are prevented below
        }

        CrimsonBladesGuildSpellsManager manager = new CrimsonBladesGuildSpellsManager(Guild.CRIMSON_BLADES);
        java.util.Map<String, Spell> all = manager.getAllSpells();

        java.util.Set<String> owned = Charecter.getInstance().getGuildSpells();
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
            return;
        }

        JList<String> list = new JList<>(available.toArray(new String[0]));
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JTextArea desc = new JTextArea(10, 40);
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);

        JLabel infoLabel = new JLabel("Select a spell to view details.");
        final Charecter player = Charecter.getInstance();
        final JLabel goldLabel = new JLabel("Your gold: " + player.getGold());

        list.addListSelectionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) { desc.setText(""); infoLabel.setText("Select a spell to view details."); return; }
            Spell s = SpellFactory.createGuildSpell(sel, Guild.CRIMSON_BLADES);
            if (s != null) {
                desc.setText(s.getDescription());
                int price = 250;
                int refund = Math.max(1, price / 10);
                infoLabel.setText("Price: " + price + " gold    Refund: " + refund + " gold");
            } else {
                desc.setText("(Details not available)"); infoLabel.setText("");
            }
        });

        JPanel p = new JPanel(new BorderLayout(8,8));
        p.add(new JScrollPane(list), BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout(6,6));
        javax.swing.JPanel northPanel = new javax.swing.JPanel(new BorderLayout());
        northPanel.add(goldLabel, BorderLayout.WEST);
        northPanel.add(infoLabel, BorderLayout.CENTER);
        right.add(northPanel, BorderLayout.NORTH);
        right.add(new JScrollPane(desc), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1,3,6,6));
        JButton buyBtn = new JButton("Buy");
        JButton sellBtn = new JButton("Sell");
        JButton backBtn = new JButton("Back");
        buttons.add(buyBtn); buttons.add(sellBtn); buttons.add(backBtn);
        right.add(buttons, BorderLayout.SOUTH);

        p.add(right, BorderLayout.CENTER);

        java.awt.Window possibleOwner = SwingUtilities.getWindowAncestor(this);
        if (possibleOwner == null) {
            try { possibleOwner = DungeonoftheBrutalKing.MainGameScreen.getInstance(); } catch (Exception ignored) { possibleOwner = null; }
        }

        final javax.swing.JDialog dialog;
        if (possibleOwner instanceof java.awt.Frame) {
            dialog = new javax.swing.JDialog((java.awt.Frame) possibleOwner, "Buy Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        } else if (possibleOwner instanceof java.awt.Dialog) {
            dialog = new javax.swing.JDialog((java.awt.Dialog) possibleOwner, "Buy Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        } else {
            dialog = new javax.swing.JDialog((java.awt.Frame) null, "Buy Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        }
        dialog.getContentPane().add(p);
        dialog.pack();
        dialog.setLocationRelativeTo(possibleOwner == null ? this : possibleOwner);

        buyBtn.addActionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(dialog, "Please select a spell first."); return; }
            if (Charecter.getInstance().getGuildSpells().size() >= maxSpells) { JOptionPane.showMessageDialog(dialog, "You cannot have more than " + maxSpells + " guild spells."); return; }
            for (String o : new java.util.ArrayList<>(Charecter.getInstance().getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { JOptionPane.showMessageDialog(dialog, "You already own " + sel + "."); return; } }
            int price = 250;
            int gold = player.getGold();
            if (gold < price) { JOptionPane.showMessageDialog(dialog, "You need " + price + " gold to buy this spell. You have " + gold + " gold."); return; }
            int confirm = JOptionPane.showConfirmDialog(dialog, "Buy '" + sel + "' for " + price + " gold?\nGold after purchase: " + (gold - price), "Confirm Purchase", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
            player.setGold(gold - price);
            addGuildSpell(sel);
            JOptionPane.showMessageDialog(dialog, "You have purchased " + sel + " for " + price + " gold. Gold remaining: " + player.getGold());
            goldLabel.setText("Your gold: " + player.getGold());
            dialog.dispose();
            try { reloadPanel(); } catch (Exception ignored) {}
        });

        sellBtn.addActionListener(ev -> {
            java.util.List<String> ownedList = new java.util.ArrayList<>(Charecter.getInstance().getGuildSpells());
            if (ownedList.isEmpty()) { JOptionPane.showMessageDialog(dialog, "You have no guild spells to sell."); return; }
            String sel = (String) JOptionPane.showInputDialog(dialog, "Select spell to sell:", "Sell Spell", JOptionPane.PLAIN_MESSAGE, null, ownedList.toArray(new String[0]), ownedList.get(0));
            if (sel != null) {
                int price = 250;
                int refund = Math.max(1, price / 10);
                int confirm = JOptionPane.showConfirmDialog(dialog, "Sell '" + sel + "' for " + refund + " gold?", "Confirm Sell", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                String toRemove = null;
                for (String o : new java.util.ArrayList<>(Charecter.getInstance().getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { toRemove = o; break; } }
                boolean removed = false; if (toRemove != null) removed = removeGuildSpell(toRemove);
                if (removed) {
                    player.setGold(player.getGold() + refund);
                    JOptionPane.showMessageDialog(dialog, "You sold " + sel + " and received " + refund + " gold. Gold now: " + player.getGold());
                    goldLabel.setText("Your gold: " + player.getGold());
                    // refresh available list
                    owned.clear(); owned.addAll(Charecter.getInstance().getGuildSpells());
                    ownedLower.clear(); for (String o : owned) if (o != null) ownedLower.add(o.toLowerCase());
                    available.clear(); for (Spell sp : all.values()) { if (sp == null) continue; if (!ownedLower.contains(sp.getName().toLowerCase())) available.add(sp.getName()); }
                    list.setListData(available.toArray(new String[0]));
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to sell " + sel + ".");
                }
            }
        });

        backBtn.addActionListener(ev -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new CrimsonBlades(isMember));
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