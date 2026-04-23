// src/Guild/ObsidianHexCoven/ObsidianHexCoven.java
package DungeonoftheBrutalKing.Guild.ObsidianHexCoven;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.Guild.ObsidianHexCoven.Spells.ObsidianHexCovenGuildSpellsManager;
import DungeonoftheBrutalKing.SharedData.GuildSpellsDialog;

public class ObsidianHexCoven extends JPanel {

    private static final long serialVersionUID = 1L;
    private final String guildName = "Obsidian Hex Coven";
    private boolean isMember;
    private final String description;
    private final Alignment alignment = Alignment.EVIL;
    private final GuildType guildType = GuildType.MAGE;

    public ObsidianHexCoven(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Obsidian Hex Coven is a guild of dark magic users who embrace chaos and power.";
        setLayout(new BorderLayout());

        Character character = Character.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        if (!this.isMember || !isEvil(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        if (!this.isMember && !inventory.contains("Obsidian Hex Coven Guild Ring")) {
            if (!isEvil(character.getAlignment())) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are not evil (`alignment < 0`). The Obsidian Hex Coven rejects you."
                );
                return;
            }
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You are not a member of the Obsidian Hex Coven. Would you like to join?",
                    "Join Guild",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[] { "Join", "Stay/Leave" },
                    "Join"
            );
            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Obsidian Hex Coven Guild Ring");
                JOptionPane.showMessageDialog(
                        this,
                        "You have joined the Obsidian Hex Coven and received the Obsidian Hex Coven Guild Ring!"
                );
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        showMainRoom();
    }

    private void showMainRoom() {
        removeAll();
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/ObsidianHexCovenRoom.jpg")));
        add(imageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton innkeeperButton = new JButton("Innkeeper");
        JButton innButton = new JButton("Inn");
        JButton healerButton = new JButton("Healer");
        JButton storageButton = new JButton("Storage Room");
        JButton bedroomButton = new JButton("Bedroom");

        innkeeperButton.addActionListener(e -> showPanel(new ShopRoomPanel()));
        innButton.addActionListener(e -> showPanel(new InnRoomPanel()));
        healerButton.addActionListener(e -> showPanel(new HealerRoomPanel()));
        storageButton.addActionListener(e -> showPanel(new StorageRoomPanel()));
        bedroomButton.addActionListener(e -> showPanel(new BedroomPanel()));

        buttonPanel.add(innkeeperButton);
        buttonPanel.add(innButton);
        buttonPanel.add(healerButton);
        buttonPanel.add(storageButton);
        buttonPanel.add(bedroomButton);

        add(buttonPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showPanel(JPanel panel) {
        removeAll();
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private class ShopRoomPanel extends JPanel {
        public ShopRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/Shop.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(4, 1, 10, 10));
            JButton buyWeaponsButton = new JButton("Buy Weapons");
            JButton buyArmourButton = new JButton("Buy Armour");
            JButton buySpellsButton = new JButton("Buy Spells");
            JButton exitButton = new JButton("Exit");

            buyWeaponsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Buying weapons..."));
            buyArmourButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Buying armour..."));
            buySpellsButton.addActionListener(e -> buyGuildSpell());
            exitButton.addActionListener(e -> showMainRoom());

            buttons.add(buyWeaponsButton);
            buttons.add(buyArmourButton);
            buttons.add(buySpellsButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class InnRoomPanel extends JPanel {
        public InnRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/InnRoom.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(4, 1, 10, 10));
            JButton performSongButton = new JButton("Perform Song");
            JButton eatFoodButton = new JButton("Eat Food");
            JButton inspireButton = new JButton("Inspire");
            JButton exitButton = new JButton("Exit");

            performSongButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You perform a song!"));
            eatFoodButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You eat food and feel refreshed!"));
            inspireButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "You inspire your companions!"));
            exitButton.addActionListener(e -> showMainRoom());

            buttons.add(performSongButton);
            buttons.add(eatFoodButton);
            buttons.add(inspireButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class HealerRoomPanel extends JPanel {
        public HealerRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/HealerRoom.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton removeDebuffButton = new JButton("Remove Debuff / Status");
            JButton exitButton = new JButton("Exit");

            removeDebuffButton.addActionListener(e -> {
                Character.getInstance().clearCurses();
                Character.getInstance().clearNegativeEffects();
                JOptionPane.showMessageDialog(this, "All debuffs and negative statuses removed!");
            });
            exitButton.addActionListener(e -> showMainRoom());

            buttons.add(removeDebuffButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class StorageRoomPanel extends JPanel {
        public StorageRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/StorageRoom.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton storeButton = new JButton("Store Item");
            JButton takeButton = new JButton("Take Item");
            JButton exitButton = new JButton("Exit");

            storeButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Storing item..."));
            takeButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Taking item from storage..."));
            exitButton.addActionListener(e -> showMainRoom());

            buttons.add(storeButton);
            buttons.add(takeButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class BedroomPanel extends JPanel {
        public BedroomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/Bedroom.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton sleepButton = new JButton("Sleep");
            JButton exitButton = new JButton("Exit");

            sleepButton.addActionListener(e -> {
                Character.getInstance().restoreHitPoints(Character.getInstance().getMaxHitPoints());
                JOptionPane.showMessageDialog(this, "You sleep and restore your hit points!");
            });
            exitButton.addActionListener(e -> showMainRoom());

            buttons.add(sleepButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private static boolean isEvil(int alignmentValue) {
        return alignmentValue < 0;
    }

    private void buyGuildSpell() {
        Character character = Character.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());
        int maxSpells = 6;

        if (!isMember) {
            JOptionPane.showMessageDialog(this,
                    "You must be a member of the Obsidian Hex Coven to buy guild spells.");
            return;
        }

        if (!isEvil(character.getAlignment())) {
            JOptionPane.showMessageDialog(this,
                    "You are not evil (`alignment < 0`). You cannot buy guild spells here.");
            return;
        }

        if (!inventory.contains("Obsidian Hex Coven Guild Ring")) {
            JOptionPane.showMessageDialog(this,
                    "You need the Obsidian Hex Coven Guild Ring to buy guild spells.");
            return;
        }

        if (getGuildSpellsCount() >= maxSpells) {
            JOptionPane.showMessageDialog(this,
                    "You cannot have more than " + maxSpells + " guild spells.");
        }

        ObsidianHexCovenGuildSpellsManager manager = new ObsidianHexCovenGuildSpellsManager(Guild.OBSIDIAN_HEX_COVEN);
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
            return;
        }

        JList<String> list = new JList<>(available.toArray(new String[0]));
        list.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JTextArea desc = new JTextArea(10, 40);
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);

        JLabel infoLabel = new JLabel("Select a spell to view details.");
        final Character player = Character.getInstance();
        final JLabel goldLabel = new JLabel("Your gold: " + player.getGold());

        list.addListSelectionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) { desc.setText(""); infoLabel.setText("Select a spell to view details."); return; }
            Spell s = SpellFactory.createGuildSpell(sel, Guild.OBSIDIAN_HEX_COVEN);
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
        JPanel northPanel = new JPanel(new BorderLayout());
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

        final JDialog dialog;
        if (possibleOwner instanceof java.awt.Frame) {
            dialog = new JDialog((java.awt.Frame) possibleOwner, "Buy Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        } else if (possibleOwner instanceof java.awt.Dialog) {
            dialog = new JDialog((java.awt.Dialog) possibleOwner, "Buy Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        } else {
            dialog = new JDialog((java.awt.Frame) null, "Buy Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        }
        dialog.getContentPane().add(p);
        dialog.pack();
        dialog.setLocationRelativeTo(possibleOwner == null ? this : possibleOwner);

        buyBtn.addActionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(dialog, "Please select a spell first."); return; }
            if (Character.getInstance().getGuildSpells().size() >= maxSpells) { JOptionPane.showMessageDialog(dialog, "You cannot have more than " + maxSpells + " guild spells."); return; }
            Spell s = SpellFactory.createGuildSpell(sel, Guild.OBSIDIAN_HEX_COVEN);
            if (s == null) { JOptionPane.showMessageDialog(dialog, "Unable to retrieve spell details. Purchase aborted."); return; }
            int price = 250;
            int gold = player.getGold();
            if (gold < price) { JOptionPane.showMessageDialog(dialog, "You need " + price + " gold to buy this spell. You have " + gold + " gold."); return; }
            int confirm = JOptionPane.showConfirmDialog(dialog, "Buy '" + s.getName() + "' for " + price + " gold?\nGold after purchase: " + (gold - price), "Confirm Purchase", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
            player.setGold(gold - price);
            addGuildSpell(s.getName());
            JOptionPane.showMessageDialog(dialog, "You have purchased " + s.getName() + " for " + price + " gold. Gold remaining: " + player.getGold());
            goldLabel.setText("Your gold: " + player.getGold());
            dialog.dispose();
            try { reloadPanel(); } catch (Exception ignored) {}
        });

        sellBtn.addActionListener(ev -> {
            ArrayList<String> ownedList = new ArrayList<>(Character.getInstance().getGuildSpells());
            if (ownedList.isEmpty()) { JOptionPane.showMessageDialog(dialog, "You have no guild spells to sell."); return; }
            String sel = (String) JOptionPane.showInputDialog(dialog, "Select spell to sell:", "Sell Spell", JOptionPane.PLAIN_MESSAGE, null, ownedList.toArray(new String[0]), ownedList.get(0));
            if (sel != null) {
                int price = 250;
                int refund = Math.max(1, price / 10);
                int confirm = JOptionPane.showConfirmDialog(dialog, "Sell '" + sel + "' for " + refund + " gold?", "Confirm Sell", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;
                String toRemove = null;
                for (String o : new ArrayList<>(Character.getInstance().getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { toRemove = o; break; } }
                boolean removed = false; if (toRemove != null) removed = removeGuildSpell(toRemove);
                if (removed) {
                    player.setGold(player.getGold() + refund);
                    JOptionPane.showMessageDialog(dialog, "You sold " + sel + " and received " + refund + " gold. Gold now: " + player.getGold());
                    goldLabel.setText("Your gold: " + player.getGold());
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

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new ObsidianHexCoven(isMember));
    }

    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }
    public GuildType getGuildType() { return guildType; }
    public boolean removeGuildSpell(String spell) { return Character.getInstance().getGuildSpells().remove(spell); }
    public int getGuildSpellsCount() { return Character.getInstance().getGuildSpells().size(); }
    public void addGuildSpell(String spell) {
        if (Character.getInstance().getGuildSpells().size() < 6) {
            Character.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }
    public ArrayList<String> getGuildSpells() { return new ArrayList<>(Character.getInstance().getGuildSpells()); }
}