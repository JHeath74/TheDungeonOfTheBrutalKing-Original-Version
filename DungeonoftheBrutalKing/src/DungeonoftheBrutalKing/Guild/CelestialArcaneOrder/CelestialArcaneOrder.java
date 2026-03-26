// `src/Guild/CelestialArcaneOrder/CelestialArcaneOrder.java`
package Guild.CelestialArcaneOrder;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.Alignment;
import SharedData.GuildType;
import Spells.Spell;
import Spells.SpellFactory;
import Spells.SpellBalanceManager;
import Guild.CelestialArcaneOrder.Spells.CelestialArcaneOrderGuildSpellsManager;

public class CelestialArcaneOrder extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Celestial Arcane Order";
    private boolean isMember;
    private final String description;
    private final Alignment alignment = Alignment.GOOD;
    private final GuildType guildType = GuildType.CLERIC;

    public CelestialArcaneOrder(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description =
                "The Celestial Arcane Order is a guild of clerics who study the stars and wield cosmic magic for the good of the realm.";

        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        // Show description for non-members and also for EVIL characters entering a GOOD-only guild.
        if (!this.isMember || !isGood(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        // GOOD-only guild: block join prompt early if player is EVIL.
        if (!this.isMember && !inventory.contains("Celestial Arcane Order Guild Ring")) {
            if (!isGood(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not good (`alignment >= 0`). The Celestial Arcane Order refuses you."
                );
                return;
            }

            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You are not a member of the Celestial Arcane Order. Would you like to join?",
                    "Join Guild",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] { "Join", "Stay/Leave" },
                    "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Celestial Arcane Order Guild Ring");
                JOptionPane.showMessageDialog(
                        this,
                        "You have joined the Celestial Arcane Order and received the Celestial Arcane Order Guild Ring!"
                );
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        JLabel imageLabel = new JLabel(new ImageIcon(
                getClass().getResource("/DungeonoftheBrutalKing/Images/CelestialArcaneOrder.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton stargazeButton = new JButton("Stargaze (Celestial Insight)");
        JButton removeCurseButton = new JButton("Remove Curses/Effects");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Guild Storage");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!this.isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(evt -> {
                Charecter ch = Charecter.getInstance();
                if (!isGood(ch.getAlignment())) {
                    JOptionPane.showMessageDialog(
                            this,
                            "You are not good (`alignment >= 0`). The Celestial Arcane Order refuses you."
                    );
                    return;
                }

                this.isMember = true;
                ch.addToInventory("Celestial Arcane Order Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Celestial Arcane Order!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            if (!isGood(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not good (`alignment >= 0`). You cannot use Celestial Arcane Order services."
                );
            } else {
                buttonPanel.add(buySpellsButton);
                buttonPanel.add(stargazeButton);
                buttonPanel.add(removeCurseButton);
                buttonPanel.add(sellItemsButton);
                buttonPanel.add(enterStorageButton);
                buttonPanel.add(eatFoodButton);
                buttonPanel.add(sleepBedButton);
            }
        }

        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);
        

buySpellsButton.addActionListener(evt -> {
    try {
        java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
        Spells.SpellsManager sm = new Spells.SpellsManager();
        SharedData.GuildSpellsDialog dlg = new SharedData.GuildSpellsDialog(
            (java.awt.Frame) owner,
            DungeonoftheBrutalKing.Charecter.getInstance(),
            SharedData.Guild.CELESTIAL_ARCANE_ORDER,
            sm
        );
        dlg.setVisible(true);
    } catch (Exception ex) {
        buyGuildSpell();
    }
});


        buySpellsButton.addActionListener(evt -> buyGuildSpell());

        stargazeButton.addActionListener(evt -> JOptionPane.showMessageDialog(
                this,
                "You gaze at the stars and gain cosmic insight. (Celestial Arcane Order exclusive service)"
        ));

        removeCurseButton.addActionListener(evt -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });

        sellItemsButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You eat a nourishing meal and feel revitalized."));
        sleepBedButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "You rest in a celestial bed and recover your strength."));

        exitRoomButton.addActionListener(evt -> {
            try {
                MainGameScreen.getInstance().restoreOriginalPanel();
            } catch (IOException | InterruptedException | ParseException ex) {
                ex.printStackTrace();
            }
        });
    }

    // Project-wide alignment rule: alignment >= 0 == GOOD, alignment < 0 == EVIL.
    private static boolean isGood(int alignmentValue) {
        return alignmentValue >= 0;
    }

    private void removeCursesAndEffects() {
        Charecter character = Charecter.getInstance();
        character.clearCurses();
        character.clearNegativeEffects();
    }

    private void buyGuildSpell() {
        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());
        int wisdom = character.getWisdom();
        int maxSpells = 6;
        int currentGuildSpells = getGuildSpellsCount();

        if (!isMember) {
            JOptionPane.showMessageDialog(this,
                    "You must be a member of the Celestial Arcane Order to buy guild spells.");
            return;
        }

        if (!isGood(character.getAlignment())) {
            JOptionPane.showMessageDialog(this,
                    "You are not good (`alignment >= 0`). You cannot buy guild spells here.");
            return;
        }

        if (!inventory.contains("Celestial Arcane Order Guild Ring")) {
            JOptionPane.showMessageDialog(this,
                    "You need the Celestial Arcane Order Guild Ring to buy guild spells.");
            return;
        }

        if (currentGuildSpells >= maxSpells) {
            JOptionPane.showMessageDialog(this,
                    "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }

        if (wisdom <= 0) {
            JOptionPane.showMessageDialog(this, "You need sufficient wisdom to buy guild spells.");
            return;
        }

        // Build manager and available spells list
        CelestialArcaneOrderGuildSpellsManager manager = new CelestialArcaneOrderGuildSpellsManager(SharedData.Guild.CELESTIAL_ARCANE_ORDER);
        Map<String, Spell> all = manager.getAllSpells();

        // Owned (case-insensitive)
        Set<String> owned = Charecter.getInstance().getGuildSpells();
        Set<String> ownedLower = new java.util.HashSet<>();
        for (String o : owned) if (o != null) ownedLower.add(o.toLowerCase());

        List<String> available = new ArrayList<>();
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

        // Dialog UI
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
            if (sel == null) {
                desc.setText(""); infoLabel.setText("Select a spell to view details."); return;
            }
            Spell s = SpellFactory.createGuildSpell(sel, SharedData.Guild.CELESTIAL_ARCANE_ORDER);
            if (s != null) {
                desc.setText(s.getDescription());
                int reqMp = s.getRequiredMagicPoints();
                int price = 250; // fixed price as requested
                int refund = Math.max(1, price / 10);
                infoLabel.setText("Required MP: " + reqMp + "    Price: " + price + " gold    Refund: " + refund + " gold");
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

        // Safely resolve an owner window for the dialog like other guild panels.
        java.awt.Window possibleOwner = SwingUtilities.getWindowAncestor(this);
        if (possibleOwner == null) {
            try {
                possibleOwner = DungeonoftheBrutalKing.MainGameScreen.getInstance();
            } catch (Exception ignored) {
                possibleOwner = null;
            }
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
            // Prevent duplicates (extra safety)
            for (String o : new java.util.ArrayList<>(Charecter.getInstance().getGuildSpells())) {
                if (o != null && o.equalsIgnoreCase(sel)) { JOptionPane.showMessageDialog(dialog, "You already own " + sel + "."); return; }
            }
            Spell s = SpellFactory.createGuildSpell(sel, SharedData.Guild.CELESTIAL_ARCANE_ORDER);
            if (s == null) { JOptionPane.showMessageDialog(dialog, "Unable to retrieve spell details. Purchase aborted."); return; }
            // Fixed price as requested
            int price = 250;
            int gold = player.getGold();
            if (gold < price) { JOptionPane.showMessageDialog(dialog, "You need " + price + " gold to buy this spell. You have " + gold + " gold."); return; }
            // Confirm purchase
            int confirm = JOptionPane.showConfirmDialog(dialog,
                    "Buy '" + s.getName() + "' for " + price + " gold?\nGold after purchase: " + (gold - price),
                    "Confirm Purchase",
                    JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
            // Deduct and add
            player.setGold(gold - price);
            addGuildSpell(s.getName());
            JOptionPane.showMessageDialog(dialog, "You have purchased " + s.getName() + " for " + price + " gold. Gold remaining: " + player.getGold());
            goldLabel.setText("Your gold: " + player.getGold());
            dialog.dispose();
            try { reloadPanel(); } catch (Exception ignored) {}
        });

        sellBtn.addActionListener(ev -> {
            List<String> ownedList = new ArrayList<>(Charecter.getInstance().getGuildSpells());
            if (ownedList.isEmpty()) { JOptionPane.showMessageDialog(dialog, "You have no guild spells to sell."); return; }
            String sel = (String) JOptionPane.showInputDialog(dialog, "Select spell to sell:", "Sell Spell", JOptionPane.PLAIN_MESSAGE, null, ownedList.toArray(new String[0]), ownedList.get(0));
            if (sel != null) {
                // compute refund using balance manager (if available) or default
                int refund = 1;
                Spell soldSpell = SpellFactory.createGuildSpell(sel, SharedData.Guild.CELESTIAL_ARCANE_ORDER);
                if (soldSpell != null) {
                    String priceKey = "spell.cost." + SharedData.Guild.CELESTIAL_ARCANE_ORDER.name() + "." + soldSpell.getName();
                    int defaultPrice = Math.max(50, soldSpell.getRequiredMagicPoints() * 10);
                    int price = SpellBalanceManager.getInt(priceKey, defaultPrice);
                    refund = Math.max(1, price / 10);
                }
                int confirm = JOptionPane.showConfirmDialog(dialog, "Sell '" + sel + "' for " + refund + " gold?", "Confirm Sell", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                String toRemove = null;
                for (String o : new ArrayList<>(Charecter.getInstance().getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { toRemove = o; break; } }
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
        add(new CelestialArcaneOrder(isMember));
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getDescription() {
        return description;
    }

    public String getGuildName() {
        return guildName;
    }

    public GuildType getGuildType() {
        return guildType;
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

    public boolean removeGuildSpell(String spell) {
        return Charecter.getInstance().getGuildSpells().remove(spell);
    }

    public ArrayList<String> getGuildSpells() {
        return new ArrayList<>(Charecter.getInstance().getGuildSpells());
    }
}