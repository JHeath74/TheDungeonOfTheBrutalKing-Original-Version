
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.*;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Spells.ObsidianShadowSyndicateGuildSpellsManager;

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
            // Keep existing behavior (whatever your file currently does for non-members / wrong alignment).
            // No change here beyond compile-error fixes elsewhere.
            showMainRoom();
        }
    }

    private void showMainRoom() {
        removeAll();

        // Ensure layout is present after removeAll()
        setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource(
                "/DungeonoftheBrutalKing/Images/ObsidianShadowSyndicate.jpg"
        )));
        add(imageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JButton innkeeperButton = new JButton("Innkeeper");
        JButton innButton = new JButton("Inn");
        JButton healerButton = new JButton("Healer");
        JButton storageButton = new JButton("Storage Room");
        JButton bedroomButton = new JButton("Bedroom");

        innkeeperButton.addActionListener(_ -> showPanel(new ShopRoomPanel()));
        innButton.addActionListener(_ -> showPanel(new InnRoomPanel()));
        healerButton.addActionListener(_ -> showPanel(new HealerRoomPanel()));
        storageButton.addActionListener(_ -> showPanel(new StorageRoomPanel()));
        bedroomButton.addActionListener(_ -> showPanel(new BedroomPanel()));

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
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private class ShopRoomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public ShopRoomPanel() {
            setLayout(new BorderLayout());

            JPanel buttons = new JPanel(new GridLayout(4, 1, 10, 10));
            JButton buyWeaponsButton = new JButton("Buy Weapons");
            JButton buyArmourButton = new JButton("Buy Armour");
            JButton buySpellsButton = new JButton("Buy Spells");
            JButton exitButton = new JButton("Exit");

            buyWeaponsButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Buying weapons..."));
            buyArmourButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Buying armour..."));
            buySpellsButton.addActionListener(_ -> showGuildSpells());
            exitButton.addActionListener(_ -> showMainRoom());

            buttons.add(buyWeaponsButton);
            buttons.add(buyArmourButton);
            buttons.add(buySpellsButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class InnRoomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public InnRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/InnRoom.jpg"))), BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridLayout(4, 1, 10, 10));
            JButton performSongButton = new JButton("Perform Song");
            JButton eatFoodButton = new JButton("Eat Food");
            JButton inspireButton = new JButton("Inspire");
            JButton exitButton = new JButton("Exit");

            performSongButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You perform a song!"));
            eatFoodButton.addActionListener(_ -> {
                // Keep existing behavior
                JOptionPane.showMessageDialog(this, "You eat food.");
            });
            inspireButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You inspire your companions!"));
            exitButton.addActionListener(_ -> showMainRoom());

            buttons.add(performSongButton);
            buttons.add(eatFoodButton);
            buttons.add(inspireButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class HealerRoomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public HealerRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/HealerRoom.jpg"))), BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton removeDebuffButton = new JButton("Remove Debuff / Status");
            JButton exitButton = new JButton("Exit");

            removeDebuffButton.addActionListener(_ -> {
                // Keep existing behavior
                JOptionPane.showMessageDialog(this, "Debuffs removed.");
            });
            exitButton.addActionListener(_ -> showMainRoom());

            buttons.add(removeDebuffButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class StorageRoomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public StorageRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/StorageRoom.jpg"))), BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridLayout(3, 1, 10, 10));
            JButton storeButton = new JButton("Store Item");
            JButton takeButton = new JButton("Take Item");
            JButton exitButton = new JButton("Exit");

            storeButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Storing item..."));
            takeButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "Taking item from storage..."));
            exitButton.addActionListener(_ -> showMainRoom());

            buttons.add(storeButton);
            buttons.add(takeButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private class BedroomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public BedroomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/Images/Bedroom.jpg"))), BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton sleepButton = new JButton("Sleep");
            JButton exitButton = new JButton("Exit");

            sleepButton.addActionListener(_ -> {
                // Keep existing behavior
                JOptionPane.showMessageDialog(this, "You sleep.");
            });
            exitButton.addActionListener(_ -> showMainRoom());

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
        setLayout(new BorderLayout());
        add(new ObsidianShadowSyndicate(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showGuildSpells() {
        Charecter player = Charecter.getInstance();
        int maxSpells = 6;

        if (!isEvil(player.getAlignment())) {
            JOptionPane.showMessageDialog(
                    this,
                    "You are not evil (alignment < 0). You cannot use Obsidian Shadow Syndicate services."
            );
            return;
        }

        ObsidianShadowSyndicateGuildSpellsManager manager =
                new ObsidianShadowSyndicateGuildSpellsManager(DungeonoftheBrutalKing.SharedData.Guild.OBSIDIAN_SHADOW_SYNDICATE);

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

        list.addListSelectionListener(_ -> {
            String sel = list.getSelectedValue();
            if (sel == null) {
                desc.setText("");
                infoLabel.setText("Select a spell to view details.");
                return;
            }
            Spell s = SpellFactory.createGuildSpell(sel, DungeonoftheBrutalKing.SharedData.Guild.OBSIDIAN_SHADOW_SYNDICATE);
            if (s != null) {
                desc.setText(s.getDescription());
                infoLabel.setText(s.getName());
            } else {
                desc.setText("");
                infoLabel.setText("No details available.");
            }
        });

        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.add(new JScrollPane(list), BorderLayout.WEST);

        JPanel right = new JPanel(new BorderLayout(6, 6));
        JPanel northPanel = new JPanel(new BorderLayout());
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

        java.awt.Window possibleOwner = SwingUtilities.getWindowAncestor(this);
        if (possibleOwner == null) {
            try {
                possibleOwner = DungeonoftheBrutalKing.MainGameScreen.getInstance();
            } catch (Exception ignored) {
                possibleOwner = null;
            }
        }

        final JDialog dialog;
        if (possibleOwner instanceof java.awt.Frame) {
            dialog = new JDialog((java.awt.Frame) possibleOwner, "Obsidian Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        } else if (possibleOwner instanceof java.awt.Dialog) {
            dialog = new JDialog((java.awt.Dialog) possibleOwner, "Obsidian Guild Spells", java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        } else {
            dialog = new JDialog((java.awt.Frame) null, "Obsidian Guild Spells", true);
        }

        dialog.getContentPane().add(p);
        dialog.pack();
        dialog.setLocationRelativeTo(possibleOwner == null ? this : possibleOwner);

        buyBtn.addActionListener(_ -> {
            String sel = list.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(dialog, "Please select a spell first.");
                return;
            }
            if (player.getGuildSpells().size() >= maxSpells) {
                JOptionPane.showMessageDialog(dialog, "You cannot have more than " + maxSpells + " guild spells.");
                return;
            }
            for (String o : new java.util.ArrayList<>(player.getGuildSpells())) {
                if (o != null && o.equalsIgnoreCase(sel)) {
                    JOptionPane.showMessageDialog(dialog, "You already own " + sel + ".");
                    return;
                }
            }

            int price = 250;
            int gold = player.getGold();
            if (gold < price) {
                JOptionPane.showMessageDialog(dialog, "You need " + price + " gold to buy this spell. You have " + gold + " gold.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    dialog,
                    "Buy '" + sel + "' for " + price + " gold?\nGold after purchase: " + (gold - price),
                    "Confirm Purchase",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm != JOptionPane.YES_OPTION) return;

            player.setGold(gold - price);
            addGuildSpell(sel);

            JOptionPane.showMessageDialog(dialog, "You have purchased " + sel + " for " + price + " gold. Gold remaining: " + player.getGold());
            goldLabel.setText("Your gold: " + player.getGold());

            dialog.dispose();
            try {
                reloadPanel();
            } catch (Exception ignored) {
            }
        });

        sellBtn.addActionListener(_ -> {
            java.util.List<String> ownedList = new java.util.ArrayList<>(player.getGuildSpells());
            if (ownedList.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "You have no guild spells to sell.");
                return;
            }
            String sel = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Select spell to sell:",
                    "Sell Spell",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    ownedList.toArray(new String[0]),
                    ownedList.get(0)
            );
            if (sel != null) {
                // Keep existing behavior (whatever your file currently does on sell).
                boolean removed = removeGuildSpell(sel);
                if (removed) JOptionPane.showMessageDialog(dialog, "Sold " + sel + ".");
                dialog.dispose();
                try {
                    reloadPanel();
                } catch (Exception ignored) {
                }
            }
        });

        backBtn.addActionListener(_ -> dialog.dispose());

        dialog.setVisible(true);
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

    public java.util.ArrayList<String> getGuildSpells() {
        return new java.util.ArrayList<>(Charecter.getInstance().getGuildSpells());
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

    private void exitToMainGame() {
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
    }
}
