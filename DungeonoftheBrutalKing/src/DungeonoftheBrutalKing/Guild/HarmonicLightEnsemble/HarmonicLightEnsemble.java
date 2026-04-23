package DungeonoftheBrutalKing.Guild.HarmonicLightEnsemble;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.*;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.Guild.HarmonicLightEnsemble.Spells.HarmonicLightEnsembleGuildSpellsManager;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GuildSpellsDialog;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;

public class HarmonicLightEnsemble extends JPanel {
    private static final long serialVersionUID = 1L;
    private final String guildName = "Harmonic Light Ensemble";
    private final String description = "A guild of bards whose melodies heal, uplift, and rally the forces of good.";
    private final Alignment alignment = Alignment.GOOD;
    private final GuildType guildType = GuildType.MINSTREL;

    public HarmonicLightEnsemble() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());
        showMainRoom();
    }

    private void showMainRoom() throws IOException, InterruptedException, ParseException {
        removeAll();
        Character character = Character.getInstance();
        character.setCurrentGuild(guildType);
        GuildMembershipStatus status = character.getCurrentGuildStatus();

        if (status == GuildMembershipStatus.NOT_MEMBER || !isGood(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        add(new JLabel(new ImageIcon(getClass().getResource(
                "/DungeonoftheBrutalKing/Images/HarmonicLightEnsemble.jpg")) ), BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton innkeeperButton = new JButton("Innkeeper (Shop)");
        JButton innButton = new JButton("Inn Room");
        JButton healerButton = new JButton("Healer");
        JButton storageButton = new JButton("Storage Room");
        JButton bedroomButton = new JButton("Bedroom");

        innkeeperButton.addActionListener(e -> setRoomPanel(new ShopRoomPanel(this)));
        innButton.addActionListener(e -> setRoomPanel(new InnRoomPanel(this)));
        healerButton.addActionListener(e -> setRoomPanel(new HealerRoomPanel(this)));
        storageButton.addActionListener(e -> setRoomPanel(new StorageRoomPanel(this)));
        bedroomButton.addActionListener(e -> setRoomPanel(new BedroomRoomPanel(this)));

        buttonPanel.add(innkeeperButton);
        buttonPanel.add(innButton);
        buttonPanel.add(healerButton);
        buttonPanel.add(storageButton);
        buttonPanel.add(bedroomButton);

        add(buttonPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void setRoomPanel(JPanel panel) {
        removeAll();
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // --- Room Panels ---

    private class ShopRoomPanel extends JPanel {
        ShopRoomPanel(JPanel previous) {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/Shop.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(4, 1, 10, 10));
            JButton buyWeapons = new JButton("Buy Weapons");
            JButton buyArmour = new JButton("Buy Armour");
            JButton buySpells = new JButton("Buy Spells");
            JButton exit = new JButton("Exit");

            buyWeapons.addActionListener(e -> JOptionPane.showMessageDialog(this, "You browse guild weapons."));
            buyArmour.addActionListener(e -> JOptionPane.showMessageDialog(this, "You browse guild armour."));
            buySpells.addActionListener(e -> buyGuildSpell());
            exit.addActionListener(e -> setRoomPanel(HarmonicLightEnsemble.this));

            buttons.add(buyWeapons);
            buttons.add(buyArmour);
            buttons.add(buySpells);
            buttons.add(exit);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class InnRoomPanel extends JPanel {
        InnRoomPanel(JPanel previous) {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/InnRoom.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(4, 1, 10, 10));
            JButton performSong = new JButton("Perform Song");
            JButton eatFood = new JButton("Eat Food");
            JButton inspire = new JButton("Inspire");
            JButton exit = new JButton("Exit");

            performSong.addActionListener(e -> performSongOfHealing());
            eatFood.addActionListener(e -> {
                int currentFood = Character.getInstance().getFood();
                if (currentFood > 0) {
                    Character.getInstance().setFood(currentFood - 1);
                    JOptionPane.showMessageDialog(this, "You enjoy a meal. Food left: " + Character.getInstance().getFood());
                } else {
                    JOptionPane.showMessageDialog(this, "You have no food to eat.");
                }
            });
            inspire.addActionListener(e -> JOptionPane.showMessageDialog(this, "You inspire your allies! Their morale is restored."));
            exit.addActionListener(e -> setRoomPanel(HarmonicLightEnsemble.this));

            buttons.add(performSong);
            buttons.add(eatFood);
            buttons.add(inspire);
            buttons.add(exit);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class HealerRoomPanel extends JPanel {
        HealerRoomPanel(JPanel previous) {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/HealerRoom.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton removeDebuff = new JButton("Remove Debuff / Status");
            JButton exit = new JButton("Exit");

            removeDebuff.addActionListener(e -> {
                removeNegativeEffects();
                JOptionPane.showMessageDialog(this, "All negative effects have been removed!");
            });
            exit.addActionListener(e -> setRoomPanel(HarmonicLightEnsemble.this));

            buttons.add(removeDebuff);
            buttons.add(exit);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class StorageRoomPanel extends JPanel {
        StorageRoomPanel(JPanel previous) {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/StorageRoom.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton putIn = new JButton("Put Item Into Storage");
            JButton takeOut = new JButton("Take Item Out of Storage");
            JButton exit = new JButton("Exit");

            putIn.addActionListener(e -> JOptionPane.showMessageDialog(this, "You put an item into storage."));
            takeOut.addActionListener(e -> JOptionPane.showMessageDialog(this, "You take an item out of storage."));
            exit.addActionListener(e -> setRoomPanel(HarmonicLightEnsemble.this));

            buttons.add(putIn);
            buttons.add(takeOut);
            buttons.add(exit);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class BedroomRoomPanel extends JPanel {
        BedroomRoomPanel(JPanel previous) {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/Bedroom.jpg"))), BorderLayout.NORTH);
            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton sleep = new JButton("Sleep (Restore HP)");
            JButton exit = new JButton("Exit");

            sleep.addActionListener(e -> {
                Character character = Character.getInstance();
                character.setHitPoints(character.getMaxHitPoints());
                JOptionPane.showMessageDialog(this, "You rest and recover your hit points.");
            });
            exit.addActionListener(e -> setRoomPanel(HarmonicLightEnsemble.this));

            buttons.add(sleep);
            buttons.add(exit);
            add(buttons, BorderLayout.CENTER);
        }
    }

    // --- Utility and Guild Logic ---

    private static boolean isGood(int alignmentValue) {
        return alignmentValue >= 0;
    }

    private void removeNegativeEffects() {
        Character character = Character.getInstance();
        character.clearNegativeEffects();
    }

    private void performSongOfHealing() {
        Character character = Character.getInstance();
        int maxHP = character.getMaxHitPoints();
        int maxMP = character.getMaxMagicPoints();
        character.setHitPoints(maxHP);
        character.setMagicPoints(maxMP);
        JOptionPane.showMessageDialog(this, "Your song restores you to full health and magic!");
    }

    private void buyGuildSpell() {
        Character player = Character.getInstance();
        int maxSpells = 6;
        if (!isGood(player.getAlignment())) {
            JOptionPane.showMessageDialog(this, "You are not good (`alignment >= 0`). You cannot use Harmonic Light Ensemble services.");
            return;
        }
        if (getGuildSpellsCount() >= maxSpells) {}
        HarmonicLightEnsembleGuildSpellsManager manager = new HarmonicLightEnsembleGuildSpellsManager(Guild.HARMONIC_LIGHT_ENSEMBLE);
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
        final JLabel goldLabel = new JLabel("Your gold: " + player.getGold());
        list.addListSelectionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) { desc.setText(""); infoLabel.setText("Select a spell to view details."); return; }
            Spell s = SpellFactory.createGuildSpell(sel, Guild.HARMONIC_LIGHT_ENSEMBLE);
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
        final JDialog dialog = new JDialog((Frame) possibleOwner, "Buy Guild Spells", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.getContentPane().add(p);
        dialog.pack();
        dialog.setLocationRelativeTo(possibleOwner == null ? this : possibleOwner);
        buyBtn.addActionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(dialog, "Please select a spell first."); return; }
            if (Character.getInstance().getGuildSpells().size() >= maxSpells) { JOptionPane.showMessageDialog(dialog, "You cannot have more than " + maxSpells + " guild spells."); return; }
            for (String o : new java.util.ArrayList<>(Character.getInstance().getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { JOptionPane.showMessageDialog(dialog, "You already own " + sel + "."); return; } }
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
        });
        sellBtn.addActionListener(ev -> {
            java.util.List<String> ownedList = new java.util.ArrayList<>(Character.getInstance().getGuildSpells());
            if (ownedList.isEmpty()) { JOptionPane.showMessageDialog(dialog, "You have no guild spells to sell."); return; }
            String sel = (String) JOptionPane.showInputDialog(dialog, "Select spell to sell:", "Sell Spell", JOptionPane.PLAIN_MESSAGE, null, ownedList.toArray(new String[0]), ownedList.get(0));
            if (sel != null) {
                int price = 250;
                int refund = Math.max(1, price / 10);
                int confirm = JOptionPane.showConfirmDialog(dialog, "Sell '" + sel + "' for " + refund + " gold?", "Confirm Sell", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;
                String toRemove = null;
                for (String o : new java.util.ArrayList<>(Character.getInstance().getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { toRemove = o; break; } }
                boolean removed = false; if (toRemove != null) removed = removeGuildSpell(toRemove);
                if (removed) {
                    player.setGold(player.getGold() + refund);
                    JOptionPane.showMessageDialog(dialog, "You sold " + sel + " and received " + refund + " gold. Gold now: " + player.getGold());
                    goldLabel.setText("Your gold: " + player.getGold());
                } else {
                    JOptionPane.showMessageDialog(dialog, "Failed to sell " + sel + ".");
                }
            }
        });
        backBtn.addActionListener(ev -> dialog.dispose());
        dialog.setVisible(true);
    }

    public int getGuildSpellsCount() { return Character.getInstance().getGuildSpells().size(); }
    public void addGuildSpell(String spell) {
        if (Character.getInstance().getGuildSpells().size() < 6) {
            Character.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }
    public boolean removeGuildSpell(String spell) { return Character.getInstance().getGuildSpells().remove(spell); }
    public java.util.ArrayList<String> getGuildSpells() { return new java.util.ArrayList<>(Character.getInstance().getGuildSpells()); }
    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }
}