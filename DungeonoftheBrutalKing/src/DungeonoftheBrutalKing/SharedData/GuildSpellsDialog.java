package DungeonoftheBrutalKing.SharedData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Reusable dialog to show and buy/sell guild spells.
 * Rules enforced:
 * - Price: 250 gold per spell
 * - Max owned spells: 6
 * - Cannot buy a spell already owned
 * - Selling returns 10% of price (rounded down, min 1)
 * - Confirms before purchase and before sell
 */
public class GuildSpellsDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    // Use Object and reflection to avoid compile-time coupling to game classes.
    private final Object player;
    private final Object guild;
    private final Object spellsManager;

    private final DefaultListModel<String> availableModel = new DefaultListModel<>();
    private final DefaultListModel<String> ownedModel = new DefaultListModel<>();

    private final JLabel goldLabel = new JLabel();

    public static final int SPELL_PRICE = 250;
    public static final int MAX_SPELLS = 6;

    public GuildSpellsDialog(Frame owner, Object player, Object guild, Object spellsManager) {
        super(owner, (guild == null ? "Guild" : guild.toString()) + " - Guild Spells", true);
        this.player = player;
        this.guild = guild;
        this.spellsManager = spellsManager;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
        pack();
        setLocationRelativeTo(owner);

        // refresh gold display when dialog shown
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                refreshGold();
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout(8,8));

        JPanel top = new JPanel(new BorderLayout());
        goldLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        top.add(new JLabel("Price: " + SPELL_PRICE + " gold each. Sell returns 10%."), BorderLayout.WEST);
        top.add(goldLabel, BorderLayout.EAST);

        JPanel center = new JPanel(new GridLayout(1,2,8,8));

        JList<String> availableList = new JList<>(availableModel);
        availableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane availScroll = new JScrollPane(availableList);
        availScroll.setBorder(BorderFactory.createTitledBorder("Available Spells"));

        JList<String> ownedList = new JList<>(ownedModel);
        ownedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane ownedScroll = new JScrollPane(ownedList);
        ownedScroll.setBorder(BorderFactory.createTitledBorder("Owned Spells"));

        center.add(availScroll);
        center.add(ownedScroll);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton buyBtn = new JButton("Buy");
        JButton sellBtn = new JButton("Sell");
        JButton closeBtn = new JButton("Close");

        buyBtn.addActionListener(e -> {
            String sel = availableList.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(this, "Select a spell to buy.", "Buy", JOptionPane.INFORMATION_MESSAGE); return; }
            handleBuy(sel);
        });

        sellBtn.addActionListener(e -> {
            String sel = ownedList.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(this, "Select an owned spell to sell.", "Sell", JOptionPane.INFORMATION_MESSAGE); return; }
            handleSell(sel);
        });

        closeBtn.addActionListener(e -> dispose());

        buttons.add(buyBtn);
        buttons.add(sellBtn);
        buttons.add(closeBtn);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void loadData() {
        availableModel.clear();
        ownedModel.clear();

        // Load all spells from manager via reflection and filter by guild
        try {
            Method getAll = spellsManager.getClass().getMethod("getAllSpells");
            Object allObj = getAll.invoke(spellsManager);
            if (allObj instanceof Map) {
                Map<?, ?> all = (Map<?, ?>) allObj;
                for (Object spObj : all.values()) {
                    if (spObj == null) continue;
                    boolean isGuildSpell = false;
                    try {
                        Method isGuild = spObj.getClass().getMethod("isGuildSpell");
                        Object b = isGuild.invoke(spObj);
                        if (b instanceof Boolean) isGuildSpell = (Boolean) b;
                    } catch (NoSuchMethodException ignored) { isGuildSpell = true; }
                    if (!isGuildSpell) continue;
                    // compare guild by toString() to avoid requiring exact enum type
                    Object spellGuild = null;
                    try { Method getGuild = spObj.getClass().getMethod("getSpellGuild"); spellGuild = getGuild.invoke(spObj); } catch (Exception ignored) {}
                    if (guild != null && spellGuild != null && !guild.toString().equals(spellGuild.toString())) continue;
                    availableModel.addElement(formatListEntry(spObj));
                }
            }
        } catch (Exception ex) {
            // ignore - leave availableModel empty
        }

        // assume player has a method getSpellsOwned() or getGuildSpells() returning List<String> names; if not, fallback to empty
        List<String> owned = new ArrayList<>();
        try {
            Object o;
            try {
                o = player.getClass().getMethod("getSpellsOwned").invoke(player);
            } catch (NoSuchMethodException e) {
                // fallback to older getGuildSpells
                o = player.getClass().getMethod("getGuildSpells").invoke(player);
            }
            if (o instanceof List) {
                List<?> l = (List<?>) o;
                for (Object it : l) owned.add(String.valueOf(it));
            }
        } catch (Exception ignored) { }

        for (String name : owned) {
            ownedModel.addElement(name);
        }

        refreshGold();
    }

    private String formatListEntry(Object s) {
        try {
            Method getName = s.getClass().getMethod("getName");
            Method getDesc = s.getClass().getMethod("getDescription");
            Object n = getName.invoke(s);
            Object d = getDesc.invoke(s);
            return String.valueOf(n) + " - " + String.valueOf(d);
        } catch (Exception e) {
            return String.valueOf(s);
        }
    }

    private void refreshGold() {
        int gold = 0;
        try { Method mg = player.getClass().getMethod("getGold"); Object g = mg.invoke(player); if (g instanceof Number) gold = ((Number) g).intValue(); } catch (Exception ignored) {}
        goldLabel.setText("Gold: " + gold);
    }

    private void handleBuy(String listEntry) {
        // extract name before ' - '
        String name = listEntry.split(" - ",2)[0];

        // check owned
        if (isOwned(name)) {
            JOptionPane.showMessageDialog(this, "You already own that spell.", "Buy", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // check max limit
        if (ownedModel.size() >= MAX_SPELLS) {
            JOptionPane.showMessageDialog(this, "You cannot own more than " + MAX_SPELLS + " spells.", "Buy", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int gold = 0;
        try { Method mg = player.getClass().getMethod("getGold"); Object g = mg.invoke(player); if (g instanceof Number) gold = ((Number) g).intValue(); } catch (Exception ignored) {}
        if (gold < SPELL_PRICE) {
            JOptionPane.showMessageDialog(this, "You do not have enough gold to buy that spell.", "Buy", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Buy '" + name + "' for " + SPELL_PRICE + " gold?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // deduct gold reflectively and add spell
        try { Method sg = player.getClass().getMethod("setGold", int.class); sg.invoke(player, gold - SPELL_PRICE); } catch (Exception ignored) {}
        addOwnedSpell(name);
        refreshGold();
        JOptionPane.showMessageDialog(this, "Purchased " + name + ".", "Buy", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleSell(String name) {
        int confirm = JOptionPane.showConfirmDialog(this, "Sell '" + name + "' for " + getSellAmount() + " gold?", "Confirm Sell", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int gold = 0; int sellAmt = getSellAmount();
        try { Method mg = player.getClass().getMethod("getGold"); Object g = mg.invoke(player); if (g instanceof Number) gold = ((Number) g).intValue(); } catch (Exception ignored) {}
        try { Method sg = player.getClass().getMethod("setGold", int.class); sg.invoke(player, gold + sellAmt); } catch (Exception ignored) {}
        removeOwnedSpell(name);
        refreshGold();
        JOptionPane.showMessageDialog(this, "Sold " + name + " for " + sellAmt + " gold.", "Sell", JOptionPane.INFORMATION_MESSAGE);
    }

    private int getSellAmount() {
        return Math.max(1, (int) Math.floor(SPELL_PRICE * 0.10));
    }

    private boolean isOwned(String spellName) {
        for (int i=0;i<ownedModel.size();i++) if (ownedModel.get(i).equals(spellName)) return true;
        return false;
    }

    private void addOwnedSpell(String name) {
        ownedModel.addElement(name);
        // Try to call player's addSpell method if it exists
        try { player.getClass().getMethod("addSpell", String.class).invoke(player, name); } catch (Exception ignored) {}
    }

    private void removeOwnedSpell(String name) {
        for (int i=0;i<ownedModel.size();i++) if (ownedModel.get(i).equals(name)) { ownedModel.remove(i); break; }
        try { player.getClass().getMethod("removeSpell", String.class).invoke(player, name); } catch (Exception ignored) {}
    }
}