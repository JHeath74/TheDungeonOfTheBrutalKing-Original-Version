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
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import SharedData.Guild;
import Spells.Spell;
import Spells.SpellFactory;
import Guild.CrimsonVeilRogues.Spells.CrimsonVeilRoguesGuildSpellsManager;

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
        GuildMembershipStatus status = character.getCurrentGuildStatus();

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeunoftheBrutalKing/Images/CrimsonVeilRogues.jpg")));
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
            questButton.addActionListener(event -> {
                character.setCurrentGuildStatus(GuildMembershipStatus.INITIATE);
                JOptionPane.showMessageDialog(this, "Trial complete! You are now an Initiate.");
                try { reloadPanel(); } catch (Exception ex) { ex.printStackTrace(); }
            });
            buttonPanel.add(questButton);
        } else if (status == GuildMembershipStatus.INITIATE) {
            JButton initiationButton = new JButton("Complete Initiation Task");
            initiationButton.addActionListener(event -> {
                character.setCurrentGuildStatus(GuildMembershipStatus.FULL_MEMBER);
                character.addToInventory("Crimson Veil Emblem");
                JOptionPane.showMessageDialog(this, "You are now a full member and received the Crimson Veil Emblem!");
                try { reloadPanel(); } catch (Exception ex) { ex.printStackTrace(); }
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

        buySpellsButton.addActionListener(e -> buyGuildSpell());
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

    // Show guild spell buy/sell dialog. Price fixed at 250 gold, refund is ~10%.
    private void buyGuildSpell() {
        Charecter player = Charecter.getInstance();
        int maxSpells = 6;

        // Ensure full member
        if (player.getCurrentGuildStatus() != GuildMembershipStatus.FULL_MEMBER) {
            JOptionPane.showMessageDialog(this, "You must be a full member of the Crimson Veil Rogues to buy guild spells.");
            return;
        }

        // EVIL-only
        if (!isEvil(player.getAlignment())) {
            JOptionPane.showMessageDialog(this, "You are not evil (alignment < 0). The Crimson Veil Rogues won't deal with you.");
            return;
        }

        // Require emblem
        if (!player.getCharInventory().contains("Crimson Veil Emblem")) {
            JOptionPane.showMessageDialog(this, "You need the Crimson Veil Emblem to buy guild spells.");
            return;
        }

        if (getGuildSpellsCount() >= maxSpells) {
            JOptionPane.showMessageDialog(this, "You cannot have more than " + maxSpells + " guild spells.");
            // still allow viewing/selling
        }

        CrimsonVeilRoguesGuildSpellsManager manager = new CrimsonVeilRoguesGuildSpellsManager(Guild.CRIMSON_VEIL_ROGUES);
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
        final JLabel goldLabel = new JLabel("Your gold: " + player.getGold());

        list.addListSelectionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) { desc.setText(""); infoLabel.setText("Select a spell to view details."); return; }
            Spell s = SpellFactory.createGuildSpell(sel, Guild.CRIMSON_VEIL_ROGUES);
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
            try { possibleOwner = DungeunoftheBrutalKing.MainGameScreen.getInstance(); } catch (Exception ignored) { possibleOwner = null; }
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

    public int getGuildSpellsCount() { return Charecter.getInstance().getGuildSpells().size(); }

    public void addGuildSpell(String spell) {
        if (Charecter.getInstance().getGuildSpells().size() < 6) {
            Charecter.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }

    public boolean removeGuildSpell(String spell) { return Charecter.getInstance().getGuildSpells().remove(spell); }
}