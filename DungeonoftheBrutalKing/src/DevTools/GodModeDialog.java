
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Simple DevTools dialog for toggling God Mode and disabling combat.
 *
 * Wire it to your game state by providing:
 * - getState: returns current flags for initial UI state
 * - onChange: applies new flags to the game (and should persist them wherever you keep dev toggles)
 */
public final class GodModeDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public record DevCombatFlags(boolean godMode, boolean combatDisabled) { }

    private final JCheckBox godModeCheck = new JCheckBox("God Mode (invulnerable)");
    private final JCheckBox combatDisabledCheck = new JCheckBox("Disable Combat (no damage / no fighting)");

    public GodModeDialog(JFrame parent,
                         Supplier<DevCombatFlags> getState,
                         Consumer<DevCombatFlags> onChange) {
        super(parent, "God Mode / Combat", false);
        Objects.requireNonNull(getState, "getState");
        Objects.requireNonNull(onChange, "onChange");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        DevCombatFlags initial = safeGet(getState);
        godModeCheck.setSelected(initial.godMode());
        combatDisabledCheck.setSelected(initial.combatDisabled());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        center.add(godModeCheck);
        center.add(Box.createVerticalStrut(6));
        center.add(combatDisabledCheck);

        JButton applyButton = new JButton("Apply");
        JButton closeButton = new JButton("Close");

        applyButton.addActionListener(e -> onChange.accept(readFlags()));
        closeButton.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(applyButton);
        bottom.add(closeButton);

        setLayout(new BorderLayout(8, 8));
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(applyButton);
        registerEscapeToClose();

        pack();
        setLocationRelativeTo(parent);
    }

    private DevCombatFlags readFlags() {
        return new DevCombatFlags(godModeCheck.isSelected(), combatDisabledCheck.isSelected());
    }

    private static DevCombatFlags safeGet(Supplier<DevCombatFlags> getState) {
        try {
            DevCombatFlags v = getState.get();
            return v == null ? new DevCombatFlags(false, false) : v;
        } catch (RuntimeException ex) {
            return new DevCombatFlags(false, false);
        }
    }

    private void registerEscapeToClose() {
        JRootPane root = getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "close");
        am.put("close", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
    }
}

/*
Integration idea (pseudo-code) in your combat system:

// Example: central damage application point
public void applyDamage(Entity attacker, Entity target, int amount) {
    if (devFlags.isCombatDisabled()) return;          // disables all combat outcomes
    if (devFlags.isGodMode() && target.isPlayer()) return; // player invulnerable
    target.hp -= amount;
}

// Optional: also block attack initiation / AI aggression
public boolean canInitiateAttack(Entity attacker, Entity target) {
    return !devFlags.isCombatDisabled();
}

*/ 
