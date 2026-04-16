
// `src/DungeonoftheBrutalKing/Guild/SilverwardSentinels/SilverwardSentinels.java`
package DungeonoftheBrutalKing.Guild.SilverwardSentinels;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Spells.SpellFactory;
import DungeonoftheBrutalKing.Guild.SilverwardSentinels.Spells.SilverwardSentinelsGuildSpellsManager;

public class SilverwardSentinels extends JPanel {

    private static final long serialVersionUID = 1L;

    private final String guildName = "Silverward Sentinels";
    private boolean isMember;
    private final Alignment alignment = Alignment.GOOD;
    private final String description;
    private final GuildType guildType = GuildType.RANGER;

    public SilverwardSentinels(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Silverward Sentinels are a guild of noble warriors dedicated to justice and protection.";

        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        // Enforce one-guild-at-a-time membership
        if (!isMember && !inventory.contains("Silverward Sentinels Guild Ring")) {
            GuildType currentGuild = character.getCurrentGuild();
            if (currentGuild != null && currentGuild != GuildType.RANGER) {
                JOptionPane.showMessageDialog(
                        this,
                        "You are already a member of another guild. Leave your current guild before joining the Silverward Sentinels."
                );
                exitToMainGame();
                return;
            }

            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You are not a member of the Silverward Sentinels. Would you like to join?",
                    "Join Guild",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Join", "Stay/Leave"},
                    "Join"
            );

            if (choice != JOptionPane.YES_OPTION) {
                exitToMainGame();
                return;
            }
            // Keep existing join behavior (if any) in your omitted code.
        }

        if (!isMember) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        showMainRoom();
    }

    private void showMainRoom() {
        removeAll();
        setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel(new ImageIcon(
                getClass().getResource("/DungeonoftheBrutalKing/Images/SilverwardSentinels.jpg")
        ));
        add(imageLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        JButton innkeeperButton = new JButton("Innkeeper");
        JButton innButton = new JButton("Inn");
        JButton healerButton = new JButton("Healer");
        JButton storageButton = new JButton("Storage Room");
        JButton bedroomButton = new JButton("Bedroom");
        JButton exitRoomButton = new JButton("Exit Room");

        innkeeperButton.addActionListener(_ -> showPanel(new ShopRoomPanel()));
        innButton.addActionListener(_ -> showPanel(new InnRoomPanel()));
        healerButton.addActionListener(_ -> showPanel(new HealerRoomPanel()));
        storageButton.addActionListener(_ -> showPanel(new StorageRoomPanel()));
        bedroomButton.addActionListener(_ -> showPanel(new BedroomPanel()));
        exitRoomButton.addActionListener(_ -> exitToMainGame());

        buttonPanel.add(innkeeperButton);
        buttonPanel.add(innButton);
        buttonPanel.add(healerButton);
        buttonPanel.add(storageButton);
        buttonPanel.add(bedroomButton);
        buttonPanel.add(exitRoomButton);

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

    // Shop Room Panel (Innkeeper)
    private class ShopRoomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public ShopRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/ShopRoom.jpg"))), BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton buySpellsButton = new JButton("Buy Spells");
            JButton exitButton = new JButton("Exit");

            buySpellsButton.addActionListener(_ -> buyGuildSpell());
            exitButton.addActionListener(_ -> showMainRoom());

            buttons.add(buySpellsButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    // Inn Room Panel
    private class InnRoomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public InnRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/InnRoom.jpg"))), BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton restButton = new JButton("Rest");
            JButton exitButton = new JButton("Exit");

            restButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You rest at the inn."));
            exitButton.addActionListener(_ -> showMainRoom());

            buttons.add(restButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    // Healer Room Panel
    private class HealerRoomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public HealerRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/HealerRoom.jpg"))), BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton healButton = new JButton("Heal");
            JButton exitButton = new JButton("Exit");

            healButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You are healed."));
            exitButton.addActionListener(_ -> showMainRoom());

            buttons.add(healButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    // Storage Room Panel
    private class StorageRoomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public StorageRoomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/StorageRoom.jpg"))), BorderLayout.NORTH);

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

    // Bedroom Panel
    private class BedroomPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        public BedroomPanel() {
            setLayout(new BorderLayout());
            add(new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/Bedroom.jpg"))), BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton sleepButton = new JButton("Sleep");
            JButton exitButton = new JButton("Exit");

            sleepButton.addActionListener(_ -> JOptionPane.showMessageDialog(this, "You sleep."));
            exitButton.addActionListener(_ -> showMainRoom());

            buttons.add(sleepButton);
            buttons.add(exitButton);
            add(buttons, BorderLayout.CENTER);
        }
    }

    private void buyGuildSpell() {
        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());
        int wisdom = character.getWisdom();
        int alignmentValue = character.getAlignment();
        int maxSpells = 6;

        if (!isMember) {
            JOptionPane.showMessageDialog(this, "You must be a member to buy guild spells.");
            return;
        }

        if (!inventory.contains("Silverward Sentinels Guild Ring")) {
            JOptionPane.showMessageDialog(this, "You need the Silverward Sentinels Guild Ring to buy guild spells.");
            return;
        }

        if (getGuildSpellsCount() >= maxSpells) {
            JOptionPane.showMessageDialog(this, "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }

        if (wisdom <= 0) {
            JOptionPane.showMessageDialog(this, "You need sufficient wisdom to buy guild spells.");
            return;
        }

        boolean isGood = alignmentValue >= 0;
        if (!isGood) {
            JOptionPane.showMessageDialog(this, "Your alignment is evil. You cannot buy guild spells.");
            return;
        }

        SilverwardSentinelsGuildSpellsManager manager =
                new SilverwardSentinelsGuildSpellsManager(Guild.SILVERWARD_SENTINELS);
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
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTextArea desc = new JTextArea(10, 40);
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);

        JLabel infoLabel = new JLabel("Select a spell to view details.");
        final Charecter player = Charecter.getInstance();
        final JLabel goldLabel = new JLabel("Your gold: " + player.getGold());

        list.addListSelectionListener(_ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) {
                desc.setText("");
                infoLabel.setText("Select a spell to view details.");
                return;
            }
            Spell s = SpellFactory.createGuildSpell(sel, Guild.SILVERWARD_SENTINELS);
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

        Window possibleOwner = SwingUtilities.getWindowAncestor(this);
        if (possibleOwner == null) {
            try {
                possibleOwner = MainGameScreen.getInstance();
            } catch (Exception ignored) {
                possibleOwner = null;
            }
        }

        final JDialog dialog;
        if (possibleOwner instanceof Frame) {
            dialog = new JDialog((Frame) possibleOwner, "Buy Guild Spells", Dialog.ModalityType.APPLICATION_MODAL);
        } else if (possibleOwner instanceof Dialog) {
            dialog = new JDialog((Dialog) possibleOwner, "Buy Guild Spells", Dialog.ModalityType.APPLICATION_MODAL);
        } else {
            dialog = new JDialog((Frame) null, "Buy Guild Spells", true);
        }

        dialog.getContentPane().add(p);
        dialog.pack();
        dialog.setLocationRelativeTo(possibleOwner == null ? this : possibleOwner);

        buyBtn.addActionListener(_ev -> {
            String sel = list.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(dialog, "Please select a spell first.");
                return;
            }
            if (player.getGuildSpells().size() >= maxSpells) {
                JOptionPane.showMessageDialog(dialog, "You cannot have more than " + maxSpells + " guild spells.");
                return;
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

        sellBtn.addActionListener(_ev -> {
            java.util.List<String> ownedList = new java.util.ArrayList<>(Charecter.getInstance().getGuildSpells());
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
                boolean removed = removeGuildSpell(sel);
                if (removed) JOptionPane.showMessageDialog(dialog, "Sold " + sel + ".");
                dialog.dispose();
                try {
                    reloadPanel();
                } catch (Exception ignored) {
                }
            }
        });

        backBtn.addActionListener(_ev -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        setLayout(new BorderLayout());
        add(new SilverwardSentinels(isMember), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public Alignment getAlignment() { return alignment; }
    public String getDescription() { return description; }
    public String getGuildName() { return guildName; }
    public GuildType getGuildType() { return guildType; }

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
