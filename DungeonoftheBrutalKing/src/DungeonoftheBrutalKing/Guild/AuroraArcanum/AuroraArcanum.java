package DungeonoftheBrutalKing.Guild.AuroraArcanum;

import java.awt.BorderLayout;
import java.awt.Frame;
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
import javax.swing.event.ListSelectionListener;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.Guild.AuroraArcanum.Spells.AuroraArcanumGuildSpellsManager;
import DungeonoftheBrutalKing.Spells.SpellBalanceManager;
import DungeonoftheBrutalKing.SharedData.GuildSpellsDialog;
import DungeonoftheBrutalKing.Spells.SpellsManager;

public class AuroraArcanum extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Aurora Arcanum";
    private final String description =
            "The Aurora Arcanum is a guild of enlightened sorcerers who harness the power of celestial magic to bring balance and wisdom to the realm.";
    private final Alignment alignment = Alignment.GOOD;
    private final GuildType guildType = GuildType.WIZARD;

    public AuroraArcanum() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());

        Character charecter = Character.getInstance();

        // Keep current guild in sync (prevents unused field warning and matches other guild panels).
        charecter.setCurrentGuild(guildType);

        GuildMembershipStatus status = charecter.getCurrentGuildStatus();

        // Show description for non-members and also for EVIL characters entering a GOOD-only guild.
        if (status == GuildMembershipStatus.NOT_MEMBER || !isGood(charecter.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        JLabel imageLabel = new JLabel(new ImageIcon(
                getClass().getResource("/DungeonoftheBrutalKing/Images/AuroraArcanum.jpg")));
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

        // GOOD-only gating for membership progression.
        if (!isGood(charecter.getAlignment())) {
            JOptionPane.showMessageDialog(
                    this,
                    "You are not good (`alignment >= 0`). The Aurora Arcanum refuses you."
            );
        } else {
            if (status == GuildMembershipStatus.NOT_MEMBER) {
                JButton questButton = new JButton("Start Guild Quest");
                questButton.addActionListener(evt -> {
                    if (!isGood(charecter.getAlignment())) {
                        JOptionPane.showMessageDialog(
                                this,
                                "You are not good (`alignment >= 0`). You cannot join the Aurora Arcanum."
                        );
                        return;
                    }
                    charecter.setCurrentGuild(guildType);
                    charecter.setCurrentGuildStatus(GuildMembershipStatus.INITIATE);
                    JOptionPane.showMessageDialog(this, "Quest complete! You are now an Initiate.");
                    reloadPanelSafe();
                });
                buttonPanel.add(questButton);

            } else if (status == GuildMembershipStatus.INITIATE) {
                JButton initiationButton = new JButton("Complete Initiation Task");
                initiationButton.addActionListener(evt -> {
                    if (!isGood(charecter.getAlignment())) {
                        JOptionPane.showMessageDialog(
                                this,
                                "You are not good (`alignment >= 0`). You cannot advance in this guild."
                        );
                        return;
                    }
                    charecter.setCurrentGuild(guildType);
                    charecter.setCurrentGuildStatus(GuildMembershipStatus.FULL_MEMBER);
                    charecter.addToInventory("Aurora Arcanum Guild Ring");
                    JOptionPane.showMessageDialog(
                            this,
                            "You are now a full member and received the Guild Ring!"
                    );
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
        }

        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(evt -> {
            try {
                // Show the reusable guild spell dialog
                java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
                DungeonoftheBrutalKing.Spells.SpellsManager sm = new DungeonoftheBrutalKing.Spells.SpellsManager();
                GuildSpellsDialog dlg = new GuildSpellsDialog((Frame) owner, Character.getInstance(), Guild.AURORA_ARCANUM, sm);
                dlg.setVisible(true);
            } catch (Exception ex) {
                // fallback to legacy flow if anything goes wrong
                buyGuildSpell();
            }
        });
        enchantItemButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Enchanting item..."));
        removeCurseButton.addActionListener(evt -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });
        sellItemsButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Selling items..."));
        enterStorageButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Accessing guild storage..."));
        eatFoodButton.addActionListener(evt -> {
            int currentFood = charecter.getFood();
            if (currentFood > 0) {
                charecter.setFood(currentFood - 1);
                JOptionPane.showMessageDialog(
                        this,
                        "You eat a hearty meal. Food left: " + charecter.getFood()
                );
            } else {
                JOptionPane.showMessageDialog(this, "You have no food to eat.");
            }
        });
        sleepBedButton.addActionListener(evt ->
                JOptionPane.showMessageDialog(this, "You rest in a comfortable bed and recover your strength."));
        exitRoomButton.addActionListener(evt -> {
            try {

            	MainGameScreen.getInstance().restoreOriginalPanel();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }



    private void buyGuildSpell() {
        Character charecter = Character.getInstance();
        int wisdom = charecter.getWisdom();
        int maxSpells = 6;
        int currentGuildSpells = getGuildSpellsCount();

        if (!isGood(charecter.getAlignment())) {
            JOptionPane.showMessageDialog(
                    this,
                    "You are not good (`alignment >= 0`). You cannot buy guild spells here."
            );
            return;
        }

        if (charecter.getCurrentGuildStatus() != GuildMembershipStatus.FULL_MEMBER) {
            JOptionPane.showMessageDialog(
                    this,
                    "You must be a full member of the Aurora Arcanum to buy guild spells."
            );
            return;
        }

        if (currentGuildSpells >= maxSpells) {
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot have more than " + maxSpells + " guild spells."
            );
            return;
        }

        if (wisdom <= 0) {
            JOptionPane.showMessageDialog(this, "You need sufficient wisdom to buy guild spells.");
            return;
        }

        // Build guild spells manager and available spell list
        AuroraArcanumGuildSpellsManager manager = new AuroraArcanumGuildSpellsManager(Guild.AURORA_ARCANUM);
        Map<String, Spell> all = manager.getAllSpells();

        // Determine which spells are not owned yet (case-insensitive)
        Set<String> owned = Character.getInstance().getGuildSpells();
        Set<String> ownedLower = new java.util.HashSet<>();
        for (String o : owned) if (o != null) ownedLower.add(o.toLowerCase());
        List<String> available = new ArrayList<>();
        for (Spell spell : all.values()) {
            if (spell == null) continue;
            String canon = spell.getName();
            if (canon == null) continue;
            if (!ownedLower.contains(canon.toLowerCase())) {
                available.add(canon);
            }
        }

        if (available.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There are no new guild spells available to purchase right now.");
            return;
        }

        // Create dialog UI
        JList<String> list = new JList<>(available.toArray(new String[0]));
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JTextArea desc = new JTextArea(10, 40);
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);

        JLabel infoLabel = new JLabel("Select a spell to view details.");
        // show player's current gold in the dialog and reuse below
        final Character player = Character.getInstance();
        final JLabel goldLabel = new JLabel("Your gold: " + player.getGold());

        list.addListSelectionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) {
                desc.setText("");
                infoLabel.setText("Select a spell to view details.");
                return;
            }
            Spell s = SpellFactory.createGuildSpell(sel, Guild.AURORA_ARCANUM);
            if (s != null) {
                desc.setText(s.getDescription());
                int reqMp = s.getRequiredMagicPoints();
                // fixed price per requirements
                int price = 250;
                int refund = Math.max(1, price / 10); // 10% refund
                infoLabel.setText("Required MP: " + reqMp + "    Price: " + price + " gold    Refund: " + refund + " gold");
            } else {
                desc.setText("(Details not available)");
                infoLabel.setText("");
            }
        });

        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.add(new JScrollPane(list), BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout(6, 6));
        // top of right pane: player's gold (left) and spell info (center)
        javax.swing.JPanel northPanel = new javax.swing.JPanel(new BorderLayout());
        northPanel.add(goldLabel, BorderLayout.WEST);
        northPanel.add(infoLabel, BorderLayout.CENTER);
        right.add(northPanel, BorderLayout.NORTH);
        right.add(new JScrollPane(desc), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 3, 6, 6));
        JButton buyBtn = new JButton("Buy");
        JButton sellBtn = new JButton("Sell");
        JButton backBtn = new JButton("Back");
        buttons.add(buyBtn);
        buttons.add(sellBtn);
        buttons.add(backBtn);
        right.add(buttons, BorderLayout.SOUTH);

        p.add(right, BorderLayout.CENTER);

        // Safely resolve an owner window for the dialog. If the panel isn't embedded in
        // a visible window yet, fall back to the main game frame. If none is available,
        // create an unowned dialog.
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
            if (Character.getInstance().getGuildSpells().size() >= maxSpells) {
                JOptionPane.showMessageDialog(dialog, "You cannot have more than " + maxSpells + " guild spells.");
                return;
            }
            // Prevent purchasing a spell already owned (case-insensitive)
            for (String o : new java.util.ArrayList<>(Character.getInstance().getGuildSpells())) {
                if (o != null && o.equalsIgnoreCase(sel)) { JOptionPane.showMessageDialog(dialog, "You already own " + sel + "."); return; }
            }
            Spell s = SpellFactory.createGuildSpell(sel, Guild.AURORA_ARCANUM);
            if (s == null) { JOptionPane.showMessageDialog(dialog, "Unable to retrieve spell details. Purchase aborted."); return; }
            int price = 250; // fixed price
            int gold = player.getGold();
            if (gold < price) { JOptionPane.showMessageDialog(dialog, "You need " + price + " gold to buy this spell. You have " + gold + " gold."); return; }
            int confirm = JOptionPane.showConfirmDialog(dialog,
                    "Buy '" + s.getName() + "' for " + price + " gold?\nGold after purchase: " + (gold - price),
                    "Confirm Purchase",
                    JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
            // deduct gold and add spell
            player.setGold(gold - price);
            addGuildSpell(s.getName());
            JOptionPane.showMessageDialog(dialog, "You have purchased " + s.getName() + " for " + price + " gold. Gold remaining: " + player.getGold());
            // update gold label in dialog if still visible
            goldLabel.setText("Your gold: " + player.getGold());
            dialog.dispose();
            try { reloadPanelSafe(); } catch (Exception ignored) {}
        });

        sellBtn.addActionListener(ev -> {
            List<String> ownedList = new ArrayList<>(Character.getInstance().getGuildSpells());
            if (ownedList.isEmpty()) { JOptionPane.showMessageDialog(dialog, "You have no guild spells to sell."); return; }
            String sel = (String) JOptionPane.showInputDialog(dialog, "Select spell to sell:", "Sell Spell", JOptionPane.PLAIN_MESSAGE, null, ownedList.toArray(new String[0]), ownedList.get(0));
            if (sel != null) {
                int price = 250;
                int refund = Math.max(1, price / 10); // fixed 10% refund
                int confirm = JOptionPane.showConfirmDialog(dialog, "Sell '" + sel + "' for " + refund + " gold?", "Confirm Sell", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;

                String toRemove = null;
                for (String o : new ArrayList<>(Character.getInstance().getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { toRemove = o; break; } }
                boolean removed = false; if (toRemove != null) removed = removeGuildSpell(toRemove);
                if (removed) {
                    player.setGold(player.getGold() + refund);
                    JOptionPane.showMessageDialog(dialog, "You sold " + sel + " and received " + refund + " gold. Gold now: " + player.getGold());
                    goldLabel.setText("Your gold: " + player.getGold());
                    // refresh available list
                    owned.clear(); owned.addAll(Character.getInstance().getGuildSpells());
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

    // Project-wide alignment rule: alignment >= 0 == GOOD, alignment < 0 == EVIL.
    private static boolean isGood(int alignmentValue) {
        return alignmentValue >= 0;
    }

    private void removeCursesAndEffects() {
        Character charecter = Character.getInstance();
        charecter.clearCurses();
        charecter.clearNegativeEffects();
    }

    // Helper used to safely reload this guild panel after changes
    private void reloadPanelSafe() {
        try {
            removeAll();
            revalidate();
            repaint();
            add(new AuroraArcanum());
        } catch (Exception e) {
            // best-effort: ignore reload failures
        }
    }

    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }

    public boolean removeGuildSpell(String spell) {
        return Character.getInstance().getGuildSpells().remove(spell);
    }

    public int getGuildSpellsCount() {
        return Character.getInstance().getGuildSpells().size();
    }

    public void addGuildSpell(String spell) {
        if (Character.getInstance().getGuildSpells().size() < 6) {
            Character.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }
}