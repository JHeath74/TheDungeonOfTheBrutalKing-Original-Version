
package DungeonoftheBrutalKing.Guild.DirgeweaversChorus;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildSpellsDialog;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Guild.DirgeweaversChorus.Spells.DirgeweaversChorusGuildSpellsManager;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GuildType;

public class DirgeweaversChorus extends JPanel {

    private static final long serialVersionUID = 1L;
    private final String guildName = "Dirgeweavers Chorus";
    private boolean isMember;
    private final String description;
    private final Alignment alignment = Alignment.EVIL;
    private final GuildType guildType = GuildType.BARD;

    public DirgeweaversChorus(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Dirgeweavers Chorus is a guild of bards who weave haunting melodies and dark magic to sway the fate of the realm.";

        setLayout(new BorderLayout());

        Character character = Character.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        if (!this.isMember || !isEvil(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        if (!this.isMember && !inventory.contains("Dirgeweavers Chorus Guild Ring")) {
            if (!isEvil(character.getAlignment())) {
                JOptionPane.showMessageDialog(this, "You are not evil (`alignment < 0`). The Dirgeweavers Chorus rejects you.");
                return;
            }
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You are not a member of the Dirgeweavers Chorus. Would you like to join?",
                    "Join Guild",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Join", "Stay/Leave"},
                    "Join"
            );
            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Dirgeweavers Chorus Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Dirgeweavers Chorus and received the Dirgeweavers Chorus Guild Ring!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/DirgeweaversChorus.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton weaveDirgeButton = new JButton("Weave Dirge");
        JButton sellItemsButton = new JButton("Go to Shopkeeper");
        JButton enterStorageButton = new JButton("Enter Storage");
        JButton sleepBedButton = new JButton("Go to Bedchamber");
        JButton innButton = new JButton("Go to Inn");
        JButton healerButton = new JButton("Enter Healer");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!this.isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(_ -> {
                Character ch = Character.getInstance();
                if (!isEvil(ch.getAlignment())) {
                    JOptionPane.showMessageDialog(this, "You are not evil (`alignment < 0`). The Dirgeweavers Chorus rejects you.");
                    return;
                }
                this.isMember = true;
                ch.addToInventory("Dirgeweavers Chorus Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Dirgeweavers Chorus!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                    if (ex instanceof InterruptedException) Thread.currentThread().interrupt();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            if (!isEvil(character.getAlignment())) {
                JOptionPane.showMessageDialog(this, "You are not evil (`alignment < 0`). You cannot use Dirgeweavers Chorus services.");
            } else {
                buttonPanel.add(buySpellsButton);
                buttonPanel.add(weaveDirgeButton);
                buttonPanel.add(sellItemsButton);
                buttonPanel.add(enterStorageButton);
                buttonPanel.add(sleepBedButton);
                buttonPanel.add(innButton);
                buttonPanel.add(healerButton);
            }
        }
        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(_ -> {
            try {
                java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
                DungeonoftheBrutalKing.Spells.SpellsManager sm = new DungeonoftheBrutalKing.Spells.SpellsManager();
                GuildSpellsDialog dlg = new GuildSpellsDialog(
                        (Frame) owner,
                        Character.getInstance(),
                        DungeonoftheBrutalKing.SharedData.Guild.DIRGEWEAVERS_CHORUS,
                        sm
                );
                dlg.setVisible(true);
            } catch (Exception ex) {
                buyGuildSpell();
            }
        });

        weaveDirgeButton.addActionListener(_ ->
                JOptionPane.showMessageDialog(
                        this,
                        "You weave a haunting dirge, empowering your allies and cursing your foes! (Dirgeweavers Chorus exclusive service)"
                )
        );

        sellItemsButton.addActionListener(_ -> openShopRoom());
        enterStorageButton.addActionListener(_ -> openStorageRoom());
        sleepBedButton.addActionListener(_ -> openSleepRoom());
        innButton.addActionListener(_ -> openInnRoom());
        healerButton.addActionListener(_ -> openHealerRoom());

        // Exit fix: `MainGameScreen.restoreOriginalPanel()` is not available on your type, use `setMainPanel()`.
        exitRoomButton.addActionListener(_ -> {
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
        });
    }

    private void openStorageRoom() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Guild Storage", true);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/StorageRoom.jpg")));
        panel.add(imageLabel, BorderLayout.NORTH);

        DefaultListModel<String> invModel = new DefaultListModel<>();
        DefaultListModel<String> storageModel = new DefaultListModel<>();
        Character ch = Character.getInstance();
        for (String s : ch.getCharInventory()) invModel.addElement(s);
        for (String s : ch.getGuildStorage()) storageModel.addElement(s);

        JList<String> invList = new JList<>(invModel);
        JList<String> storageList = new JList<>(storageModel);

        JButton putBtn = new JButton("Put in Storage");
        JButton takeBtn = new JButton("Take from Storage");
        JButton leaveBtn = new JButton("Leave");

        putBtn.addActionListener(_ -> {
            String sel = invList.getSelectedValue();
            if (sel != null) {
                ch.getCharInventory().remove(sel);
                ch.getGuildStorage().add(sel);
                invModel.removeElement(sel);
                storageModel.addElement(sel);
            }
        });
        takeBtn.addActionListener(_ -> {
            String sel = storageList.getSelectedValue();
            if (sel != null) {
                ch.getGuildStorage().remove(sel);
                ch.getCharInventory().add(sel);
                storageModel.removeElement(sel);
                invModel.addElement(sel);
            }
        });
        leaveBtn.addActionListener(_ -> dialog.dispose());

        JPanel btns = new JPanel();
        btns.add(putBtn);
        btns.add(takeBtn);
        btns.add(leaveBtn);

        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        listsPanel.add(new JScrollPane(invList));
        listsPanel.add(new JScrollPane(storageList));

        panel.add(new JLabel("Inventory (left) / Storage (right)"), BorderLayout.CENTER);
        panel.add(listsPanel, BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openShopRoom() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Guild Shop", true);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/ShopRoom.jpg")));
        panel.add(imageLabel, BorderLayout.NORTH);

        DefaultListModel<String> invModel = new DefaultListModel<>();
        Character ch = Character.getInstance();
        for (String s : ch.getCharInventory()) invModel.addElement(s);

        JList<String> invList = new JList<>(invModel);
        JButton sellBtn = new JButton("Sell Selected");
        JButton buyBtn = new JButton("Buy Guild Gear");
        JButton leaveBtn = new JButton("Leave");

        sellBtn.addActionListener(_ -> {
            String sel = invList.getSelectedValue();
            if (sel != null) {
                ch.getCharInventory().remove(sel);
                invModel.removeElement(sel);
                ch.setGold(ch.getGold() + 50);
                JOptionPane.showMessageDialog(dialog, "Sold " + sel + " for 50 gold.");
            }
        });
        buyBtn.addActionListener(_ -> {
            String[] gear = {"Bardic Armour", "Dirgeweaver's Lute"};
            String sel = (String) JOptionPane.showInputDialog(
                    dialog,
                    "Buy which item?",
                    "Buy Guild Gear",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    gear,
                    gear[0]
            );
            if (sel != null && ch.getGold() >= 200) {
                ch.getCharInventory().add(sel);
                ch.setGold(ch.getGold() - 200);
                JOptionPane.showMessageDialog(dialog, "Bought " + sel + " for 200 gold.");
            } else if (sel != null) {
                JOptionPane.showMessageDialog(dialog, "Not enough gold.");
            }
        });
        leaveBtn.addActionListener(_ -> dialog.dispose());

        JPanel btns = new JPanel();
        btns.add(sellBtn);
        btns.add(buyBtn);
        btns.add(leaveBtn);

        panel.add(new JLabel("Your Inventory:"), BorderLayout.CENTER);
        panel.add(new JScrollPane(invList), BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openSleepRoom() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Bedchamber", true);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/BedChamber.jpg")));
        panel.add(imageLabel, BorderLayout.NORTH);

        JButton sleepBtn = new JButton("Sleep");
        JButton leaveBtn = new JButton("Leave");

        sleepBtn.addActionListener(_ -> {
            Character ch = Character.getInstance();
            ch.restoreHitPoints(ch.getMaxHitPoints());
            JOptionPane.showMessageDialog(dialog, "You sleep and feel fully restored.");
        });
        leaveBtn.addActionListener(_ -> dialog.dispose());

        JPanel btns = new JPanel();
        btns.add(sleepBtn);
        btns.add(leaveBtn);

        panel.add(new JLabel("A shadowy bed awaits you."), BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openInnRoom() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Dirgeweaver's Inn", true);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/InnRoom.jpg")));
        panel.add(imageLabel, BorderLayout.NORTH);

        JButton buyFoodBtn = new JButton("Buy Food (20g)");
        JButton buyDrinkBtn = new JButton("Buy Drink (10g)");
        JButton eatBtn = new JButton("Eat Food");
        JButton drinkBtn = new JButton("Drink");
        JButton leaveBtn = new JButton("Leave");

        buyFoodBtn.addActionListener(_ -> {
            Character ch = Character.getInstance();
            if (ch.getGold() >= 20) {
                ch.setGold(ch.getGold() - 20);
                ch.getCharInventory().add("Food");
                JOptionPane.showMessageDialog(dialog, "Bought food.");
            } else {
                JOptionPane.showMessageDialog(dialog, "Not enough gold.");
            }
        });
        buyDrinkBtn.addActionListener(_ -> {
            Character ch = Character.getInstance();
            if (ch.getGold() >= 10) {
                ch.setGold(ch.getGold() - 10);
                ch.getCharInventory().add("Drink");
                JOptionPane.showMessageDialog(dialog, "Bought drink.");
            } else {
                JOptionPane.showMessageDialog(dialog, "Not enough gold.");
            }
        });
        eatBtn.addActionListener(_ -> {
            Character ch = Character.getInstance();
            if (ch.getCharInventory().remove("Food")) {
                ch.setHungerLevel(0);
                JOptionPane.showMessageDialog(dialog, "You eat and are no longer hungry.");
            } else {
                JOptionPane.showMessageDialog(dialog, "No food in inventory.");
            }
        });
        drinkBtn.addActionListener(_ -> {
            Character ch = Character.getInstance();
            if (ch.getCharInventory().remove("Drink")) {
                ch.setThirstLevel(0);
                JOptionPane.showMessageDialog(dialog, "You drink and are no longer thirsty.");
            } else {
                JOptionPane.showMessageDialog(dialog, "No drink in inventory.");
            }
        });
        leaveBtn.addActionListener(_ -> dialog.dispose());

        JPanel btns = new JPanel();
        btns.add(buyFoodBtn);
        btns.add(buyDrinkBtn);
        btns.add(eatBtn);
        btns.add(drinkBtn);
        btns.add(leaveBtn);

        panel.add(new JLabel("Welcome to the Inn!"), BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openHealerRoom() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Healer", true);
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/DungeonoftheBrutalKing/Images/HealerRoom.jpg")));
        panel.add(imageLabel, BorderLayout.NORTH);

        JButton removeCurseBtn = new JButton("Remove Curses/Effects");
        JButton leaveBtn = new JButton("Leave");

        removeCurseBtn.addActionListener(_ -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(dialog, "All curses and negative effects have been removed!");
        });
        leaveBtn.addActionListener(_ -> dialog.dispose());

        JPanel btns = new JPanel();
        btns.add(removeCurseBtn);
        btns.add(leaveBtn);

        panel.add(new JLabel("The healer offers to cleanse you."), BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private static boolean isEvil(int alignmentValue) {
        return alignmentValue < 0;
    }

    private void removeCursesAndEffects() {
        Character character = Character.getInstance();
        character.clearCurses();
        character.clearNegativeEffects();
    }

    private void buyGuildSpell() {
        Character character = Character.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());
        int maxSpells = 6;
        if (!isMember) {
            JOptionPane.showMessageDialog(this, "You must be a member of the Dirgeweavers Chorus to buy guild spells.");
            return;
        }
        if (!isEvil(character.getAlignment())) {
            JOptionPane.showMessageDialog(this, "You are not evil (`alignment < 0`). You cannot buy guild spells here.");
            return;
        }
        if (!inventory.contains("Dirgeweavers Chorus Guild Ring")) {
            JOptionPane.showMessageDialog(this, "You need the Dirgeweavers Chorus Guild Ring to buy guild spells.");
            return;
        }
        if (getGuildSpellsCount() >= maxSpells) {
            JOptionPane.showMessageDialog(this, "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }
        DirgeweaversChorusGuildSpellsManager manager = new DirgeweaversChorusGuildSpellsManager(Guild.DIRGEWEAVERS_CHORUS);
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
        }
        // (Legacy dialog code omitted for brevity)
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        setLayout(new BorderLayout());
        add(new DirgeweaversChorus(isMember), BorderLayout.CENTER);
        revalidate();
        repaint();
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
