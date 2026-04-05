package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.*;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells.ObsidianShadowSyndicateGuildSpellsManager;
import DungeonoftheBrutalKing.SharedData.GuildSpellsDialog;

public class ObsidianShadowSyndicate extends JPanel {

    private static final long serialVersionUID = 1L;
    private final String guildName = "Obsidian Shadow Syndicate";
    private final String description =
            "The Obsidian Shadow Syndicate is a secretive and ruthless guild of master thieves, thriving in darkness and chaos, feared for their cunning and evil deeds.";
    private final Alignment alignment = Alignment.EVIL;
    GuildType guildType = GuildType.THIEF;

    public ObsidianShadowSyndicate() throws IOException, InterruptedException, ParseException {
        setLayout(new BorderLayout());
        Charecter character = Charecter.getInstance();
        GuildMembershipStatus status = character.getCurrentGuildStatus();

        if (status == GuildMembershipStatus.FULL_MEMBER && isEvil(character.getAlignment())) {
            showMainRoom();
        } else {
            JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource(
                    "/DungeonoftheBrutalKing/Images/ObsidianShadowSyndicate.jpg")));
            add(imageLabel, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridLayout(9, 1, 10, 10));
            if (status == GuildMembershipStatus.NOT_MEMBER) {
                JButton questButton = new JButton("Start Guild Heist");
                questButton.addActionListener(e -> {
                    if (!isEvil(character.getAlignment())) {
                        JOptionPane.showMessageDialog(this,
                                "You are not evil (alignment < 0). The Obsidian Shadow Syndicate rejects you.");
                        return;
                    }
                    GuildType currentGuild = character.getCurrentGuild();
                    if (currentGuild != null && currentGuild != GuildType.THIEF) {
                        JOptionPane.showMessageDialog(this,
                                "You are already a member of another guild. Leave your current guild before joining the Obsidian Shadow Syndicate.");
                        return;
                    }
                    character.setCurrentGuildStatus(GuildMembershipStatus.INITIATE);
                    character.setCurrentGuild(GuildType.THIEF);
                    JOptionPane.showMessageDialog(this, "Heist complete! You are now an Initiate.");
                    try { reloadPanel(); } catch (Exception ex) { ex.printStackTrace(); }
                });
                buttonPanel.add(questButton);
            } else if (status == GuildMembershipStatus.INITIATE) {
                JButton initiationButton = new JButton("Complete Initiation Task");
                initiationButton.addActionListener(e -> {
                    if (!isEvil(character.getAlignment())) {
                        JOptionPane.showMessageDialog(this,
                                "You are not evil (alignment < 0). You cannot advance in this guild.");
                        return;
                    }
                    GuildType currentGuild = character.getCurrentGuild();
                    if (currentGuild != null && currentGuild != GuildType.THIEF) {
                        JOptionPane.showMessageDialog(this,
                                "You are already a member of another guild. Leave your current guild before joining the Obsidian Shadow Syndicate.");
                        return;
                    }
                    character.setCurrentGuildStatus(GuildMembershipStatus.FULL_MEMBER);
                    character.setCurrentGuild(GuildType.THIEF);
                    character.addToInventory("Obsidian Shadow Syndicate Emblem");
                    JOptionPane.showMessageDialog(this,
                            "You are now a full member and received the Syndicate Emblem!");
                    try { reloadPanel(); } catch (Exception ex) { ex.printStackTrace(); }
                });
                buttonPanel.add(initiationButton);
            } else if (status == GuildMembershipStatus.FULL_MEMBER) {
                JOptionPane.showMessageDialog(this,
                        "You are not evil (alignment < 0). You cannot use Obsidian Shadow Syndicate services.");
            }
            JButton exitRoomButton = new JButton("Exit Room");
            exitRoomButton.addActionListener(e -> {
                try { MainGameScreen.getInstance().restoreOriginalPanel(); } catch (Exception ex) { ex.printStackTrace(); }
            });
            buttonPanel.add(exitRoomButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    private void showMainRoom() {
        removeAll();
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource(
                "/DungeonoftheBrutalKing/Images/ObsidianShadowSyndicate.jpg")));
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
            buySpellsButton.addActionListener(e -> showGuildSpells());
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
            eatFoodButton.addActionListener(e -> {
                int currentFood = Charecter.getInstance().getFood();
                if (currentFood > 0) {
                    Charecter.getInstance().setFood(currentFood - 1);
                    JOptionPane.showMessageDialog(this, "You eat food and feel refreshed! Food left: " + Charecter.getInstance().getFood());
                } else {
                    JOptionPane.showMessageDialog(this, "You have no food to eat.");
                }
            });
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
                Charecter.getInstance().clearCurses();
                Charecter.getInstance().clearNegativeEffects();
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
                Charecter.getInstance().restoreHitPoints(Charecter.getInstance().getMaxHitPoints());
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

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        revalidate();
        repaint();
        add(new ObsidianShadowSyndicate());
    }

    private void showGuildSpells() {
        Charecter player = Charecter.getInstance();
        int maxSpells = 6;
        if (!isEvil(player.getAlignment())) {
            JOptionPane.showMessageDialog(this, "You are not evil (alignment < 0). You cannot use Obsidian Shadow Syndicate services.");
            return;
        }
        ObsidianShadowSyndicateGuildSpellsManager manager = new ObsidianShadowSyndicateGuildSpellsManager(SharedData.Guild.OBSIDIAN_SHADOW_SYNDICATE);
        java.util.Map<String, Spell> all = manager.getAllSpells();
        java.util.Set<String> owned = player.getGuildSpells();
        java.util.Set<String> ownedLower = new java.util.HashSet<>();
        for (String o : owned) if (o != null) ownedLower.add(o.toLowerCase());
        java.util.List<String> available = new java.util.ArrayList<>();
        for (Spell sp : all.values()) {
            if (sp == null) continue;
            String canon = sp.getName();
            if (canon == null) continue;
            if (!ownedLower.contains(canon.toLowerCase())) available.add(canon);
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
            Spell s = SpellFactory.createGuildSpell(sel, SharedData.Guild.OBSIDIAN_SHADOW_SYNDICATE);
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
            dialog = new JDialog((java.awt.Frame) possibleOwner, "Obsidian Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        } else if (possibleOwner instanceof java.awt.Dialog) {
            dialog = new JDialog((java.awt.Dialog) possibleOwner, "Obsidian Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        } else {
            dialog = new JDialog((java.awt.Frame) null, "Obsidian Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        }
        dialog.getContentPane().add(p);
        dialog.pack();
        dialog.setLocationRelativeTo(possibleOwner == null ? this : possibleOwner);
        buyBtn.addActionListener(ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(dialog, "Please select a spell first."); return; }
            if (player.getGuildSpells().size() >= maxSpells) { JOptionPane.showMessageDialog(dialog, "You cannot have more than " + maxSpells + " guild spells."); return; }
            for (String o : new java.util.ArrayList<>(player.getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { JOptionPane.showMessageDialog(dialog, "You already own " + sel + "."); return; } }
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
            java.util.List<String> ownedList = new java.util.ArrayList<>(player.getGuildSpells());
            if (ownedList.isEmpty()) { JOptionPane.showMessageDialog(dialog, "You have no guild spells to sell."); return; }
            String sel = (String) JOptionPane.showInputDialog(dialog, "Select spell to sell:", "Sell Spell", JOptionPane.PLAIN_MESSAGE, null, ownedList.toArray(new String[0]), ownedList.get(0));
            if (sel != null) {
                int price = 250;
                int refund = Math.max(1, price / 10);
                int confirm = JOptionPane.showConfirmDialog(dialog, "Sell '" + sel + "' for " + refund + " gold?", "Confirm Sell", JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) return;
                String toRemove = null;
                for (String o : new java.util.ArrayList<>(player.getGuildSpells())) { if (o != null && o.equalsIgnoreCase(sel)) { toRemove = o; break; } }
                boolean removed = false; if (toRemove != null) removed = removeGuildSpell(toRemove);
                if (removed) {
                    player.setGold(player.getGold() + refund);
                    JOptionPane.showMessageDialog(dialog, "You sold " + sel + " and received " + refund + " gold. Gold now: " + player.getGold());
                    goldLabel.setText("Your gold: " + player.getGold());
                    owned.clear(); owned.addAll(player.getGuildSpells());
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
    public java.util.ArrayList<String> getGuildSpells() { return new java.util.ArrayList<>(Charecter.getInstance().getGuildSpells()); }
    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }
}
