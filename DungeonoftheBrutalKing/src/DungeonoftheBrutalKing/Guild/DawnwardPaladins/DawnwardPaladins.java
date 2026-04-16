
// src/DungeonoftheBrutalKing/Guild/DawnwardPaladins/DawnwardPaladins.java
package DungeonoftheBrutalKing.Guild.DawnwardPaladins;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildSpellsDialog;
import DungeonoftheBrutalKing.Guild.DawnwardPaladins.Spells.DawnwardPaladinsGuildSpellsManager;
import DungeonoftheBrutalKing.Spells.Spell;

public class DawnwardPaladins extends JPanel {

    private static final long serialVersionUID = 1L;
    private final String guildName = "Dawnward Paladins";
    private boolean isMember;
    private final String description;
    private final Alignment alignment = Alignment.GOOD;
    private final GuildType guildType = GuildType.PALADIN;

    public DawnwardPaladins(boolean isMember) throws IOException, InterruptedException, ParseException {
        this.isMember = isMember;
        this.description = "The Dawnward Paladins are a noble order dedicated to justice, protection, and the light of dawn.";

        setLayout(new BorderLayout());

        Charecter character = Charecter.getInstance();
        ArrayList<String> inventory = new ArrayList<>(character.getCharInventory());

        if (!this.isMember || !isGood(character.getAlignment())) {
            MainGameScreen.getInstance().setMessageTextPane(description);
        }

        if (!this.isMember && !inventory.contains("Dawnward Paladins Guild Ring")) {
            if (!isGood(character.getAlignment())) {
                JOptionPane.showMessageDialog(this,
                        "You are not good (`alignment >= 0`). The Dawnward Paladins reject you.");
                return;
            }

            int choice = JOptionPane.showOptionDialog(
                    this,
                    "You are not a member of the Dawnward Paladins. Would you like to join?",
                    "Join Guild",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Join", "Stay/Leave"},
                    "Join"
            );

            if (choice == JOptionPane.YES_OPTION) {
                this.isMember = true;
                character.addToInventory("Dawnward Paladins Guild Ring");
                JOptionPane.showMessageDialog(this,
                        "You have joined the Dawnward Paladins and received the Dawnward Paladins Guild Ring!");
            } else {
                JOptionPane.showMessageDialog(this, "You chose not to join the guild.");
                return;
            }
        }

        JLabel imageLabel = new JLabel(new ImageIcon(
                getClass().getResource("/DungeonoftheBrutalKing/Images/DawnwardPaladins.jpg")));
        add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(10, 1, 10, 10));
        JButton buySpellsButton = new JButton("Buy Spells");
        JButton blessWeaponButton = new JButton("Bless Weapon");
        JButton removeCurseButton = new JButton("Remove Curses/Effects");
        JButton sellItemsButton = new JButton("Sell Items");
        JButton enterStorageButton = new JButton("Guild Storage");
        JButton eatFoodButton = new JButton("Eat Food");
        JButton sleepBedButton = new JButton("Sleep in Bed");
        JButton innButton = new JButton("Go to Inn");
        JButton exitRoomButton = new JButton("Exit Room");

        if (!this.isMember) {
            JButton joinGuildButton = new JButton("Join Guild");
            joinGuildButton.addActionListener(_ -> {
                Charecter ch = Charecter.getInstance();
                if (!isGood(ch.getAlignment())) {
                    JOptionPane.showMessageDialog(this,
                            "You are not good (`alignment >= 0`). The Dawnward Paladins reject you.");
                    return;
                }
                this.isMember = true;
                ch.addToInventory("Dawnward Paladins Guild Ring");
                JOptionPane.showMessageDialog(this, "You have joined the Dawnward Paladins!");
                try {
                    reloadPanel();
                } catch (IOException | InterruptedException | ParseException ex) {
                    ex.printStackTrace();
                    if (ex instanceof InterruptedException) Thread.currentThread().interrupt();
                }
            });
            buttonPanel.add(joinGuildButton);
        } else {
            if (!isGood(character.getAlignment())) {
                JOptionPane.showMessageDialog(this,
                        "You are not good (`alignment >= 0`). You cannot use Dawnward Paladins services.");
            } else {
                buttonPanel.add(buySpellsButton);
                buttonPanel.add(blessWeaponButton);
                buttonPanel.add(removeCurseButton);
                buttonPanel.add(sellItemsButton);
                buttonPanel.add(enterStorageButton);
                buttonPanel.add(eatFoodButton);
                buttonPanel.add(sleepBedButton);
                buttonPanel.add(innButton);
            }
        }

        buttonPanel.add(exitRoomButton);
        add(buttonPanel, BorderLayout.SOUTH);

        buySpellsButton.addActionListener(_ -> {
            try {
                java.awt.Window owner = SwingUtilities.getWindowAncestor(this);
                DungeonoftheBrutalKing.Spells.SpellsManager sm = new DungeonoftheBrutalKing.Spells.SpellsManager();
                GuildSpellsDialog dlg = new GuildSpellsDialog(
                        (java.awt.Frame) owner,
                        Charecter.getInstance(),
                        DungeonoftheBrutalKing.SharedData.Guild.DAWNWARD_PALADINS,
                        sm
                );
                dlg.setVisible(true);
            } catch (Exception ex) {
                buyGuildSpell();
            }
        });

        blessWeaponButton.addActionListener(_ ->
                JOptionPane.showMessageDialog(this,
                        "You bless your weapon, imbuing it with holy power! (Dawnward Paladins exclusive service)")
        );

        removeCurseButton.addActionListener(_ -> {
            removeCursesAndEffects();
            JOptionPane.showMessageDialog(this, "All curses and negative effects have been removed!");
        });

        sellItemsButton.addActionListener(_ -> openShopRoom());
        enterStorageButton.addActionListener(_ -> openStorageRoom());

        eatFoodButton.addActionListener(_ -> {
            Charecter ch = Charecter.getInstance();
            if (ch.getCharInventory().remove("Food")) {
                ch.setHungerLevel(0);
                JOptionPane.showMessageDialog(this, "You eat a wholesome meal and feel renewed.");
            } else {
                JOptionPane.showMessageDialog(this, "No food in inventory.");
            }
        });

        sleepBedButton.addActionListener(_ -> openSleepRoom());
        innButton.addActionListener(_ -> openInnRoom());

        // Fix: call the correct MainGameScreen API. Use static restoreOriginalPanel\(\) if that's what your class provides.
        exitRoomButton.addActionListener(_ -> {
            try {
                // If your MainGameScreen has a non-static restoreOriginalPanel\(\),
                // replace the next line with:
                 MainGameScreen.getInstance().restoreOriginalPanel();
                //MainGameScreen.restoreOriginalPanel();
            } catch (Exception ex) {
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
        JDialog dialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Guild Storage", true);

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        DefaultListModel<String> invModel = new DefaultListModel<>();
        DefaultListModel<String> storageModel = new DefaultListModel<>();
        Charecter ch = Charecter.getInstance();

        for (String s : ch.getCharInventory()) invModel.addElement(s);
        java.util.List<String> storage = ch.getGuildStorage();
        for (String s : storage) storageModel.addElement(s);

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
        panel.add(new JLabel("Inventory (left) / Storage (right)"), BorderLayout.NORTH);
        panel.add(listsPanel, BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openShopRoom() {
        JDialog dialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Guild Storage", true);

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        DefaultListModel<String> invModel = new DefaultListModel<>();
        Charecter ch = Charecter.getInstance();
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
            String[] gear = {"Paladin Armour", "Paladin Sword"};
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

        panel.add(new JLabel("Your Inventory:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(invList), BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openSleepRoom() {
        JDialog dialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Paladin's BedChamber", true);

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JButton sleepBtn = new JButton("Sleep");
        JButton leaveBtn = new JButton("Leave");

        sleepBtn.addActionListener(_ -> {
            Charecter ch = Charecter.getInstance();
            ch.restoreHitPoints(ch.getMaxHitPoints());
            JOptionPane.showMessageDialog(dialog, "You sleep soundly and feel fully restored.");
        });
        leaveBtn.addActionListener(_ -> dialog.dispose());

        JPanel btns = new JPanel();
        btns.add(sleepBtn);
        btns.add(leaveBtn);

        panel.add(new JLabel("A peaceful bed awaits you."), BorderLayout.CENTER);
        panel.add(btns, BorderLayout.SOUTH);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void openInnRoom() {
        JDialog dialog = new JDialog((java.awt.Frame) SwingUtilities.getWindowAncestor(this), "Paladin's Inn", true);

        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JButton buyFoodBtn = new JButton("Buy Food (20g)");
        JButton buyDrinkBtn = new JButton("Buy Drink (10g)");
        JButton eatBtn = new JButton("Eat Food");
        JButton drinkBtn = new JButton("Drink");
        JButton leaveBtn = new JButton("Leave");

        buyFoodBtn.addActionListener(_ -> {
            Charecter ch = Charecter.getInstance();
            if (ch.getGold() >= 20) {
                ch.setGold(ch.getGold() - 20);
                ch.getCharInventory().add("Food");
                JOptionPane.showMessageDialog(dialog, "Bought food.");
            } else {
                JOptionPane.showMessageDialog(dialog, "Not enough gold.");
            }
        });

        buyDrinkBtn.addActionListener(_ -> {
            Charecter ch = Charecter.getInstance();
            if (ch.getGold() >= 10) {
                ch.setGold(ch.getGold() - 10);
                ch.getCharInventory().add("Drink");
                JOptionPane.showMessageDialog(dialog, "Bought drink.");
            } else {
                JOptionPane.showMessageDialog(dialog, "Not enough gold.");
            }
        });

        eatBtn.addActionListener(_ -> {
            Charecter ch = Charecter.getInstance();
            if (ch.getCharInventory().remove("Food")) {
                ch.setHungerLevel(0);
                JOptionPane.showMessageDialog(dialog, "You eat and are no longer hungry.");
            } else {
                JOptionPane.showMessageDialog(dialog, "No food in inventory.");
            }
        });

        drinkBtn.addActionListener(_ -> {
            Charecter ch = Charecter.getInstance();
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

        panel.add(new JLabel("Welcome to the Inn!"), BorderLayout.NORTH);
        panel.add(btns, BorderLayout.CENTER);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

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
        int maxSpells = 6;

        if (!isMember) {
            JOptionPane.showMessageDialog(this, "You must be a member of the Dawnward Paladins to buy guild spells.");
            return;
        }
        if (!isGood(character.getAlignment())) {
            JOptionPane.showMessageDialog(this, "You are not good (`alignment >= 0`). You cannot buy guild spells here.");
            return;
        }
        if (!inventory.contains("Dawnward Paladins Guild Ring")) {
            JOptionPane.showMessageDialog(this, "You need the Dawnward Paladins Guild Ring to buy guild spells.");
            return;
        }
        if (getGuildSpellsCount() >= maxSpells) {
            JOptionPane.showMessageDialog(this, "You cannot have more than " + maxSpells + " guild spells.");
            return;
        }

        DawnwardPaladinsGuildSpellsManager manager =
                new DawnwardPaladinsGuildSpellsManager(DungeonoftheBrutalKing.SharedData.Guild.DAWNWARD_PALADINS);
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
        }
        // Legacy dialog code omitted.
    }

    private void reloadPanel() throws IOException, InterruptedException, ParseException {
        removeAll();
        setLayout(new BorderLayout());
        add(new DawnwardPaladins(isMember), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public String getDescription() { return description; }
    public Alignment getAlignment() { return alignment; }
    public String getGuildName() { return guildName; }
    public GuildType getGuildType() { return guildType; }
    public boolean removeGuildSpell(String spell) { return Charecter.getInstance().getGuildSpells().remove(spell); }
    public int getGuildSpellsCount() { return Charecter.getInstance().getGuildSpells().size(); }
    public void addGuildSpell(String spell) {
        if (Charecter.getInstance().getGuildSpells().size() < 6) {
            Charecter.getInstance().getGuildSpells().add(spell);
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 6 guild spells.");
        }
    }
    public ArrayList<String> getGuildSpells() { return new ArrayList<>(Charecter.getInstance().getGuildSpells()); }
}
