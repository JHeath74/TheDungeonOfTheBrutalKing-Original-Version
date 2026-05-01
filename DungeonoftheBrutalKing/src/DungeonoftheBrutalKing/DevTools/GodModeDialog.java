package DungeonoftheBrutalKing.DevTools;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Developer dialog for toggling God Mode and disabling combat in the game.
 * <p>
 * Allows developers to quickly enable invulnerability (God Mode) or disable all combat interactions.
 * The dialog is initialized with the current state via a Supplier, and changes are applied via a Consumer.
 * Includes keyboard shortcut (Escape) to close the dialog.
 */
public final class GodModeDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    /** Immutable record representing the dev combat flags. */
    public record DevCombatFlags(boolean godMode, boolean combatDisabled) { }

    private final JCheckBox godModeCheck = new JCheckBox("God Mode (invulnerable)");
    private final JCheckBox combatDisabledCheck = new JCheckBox("Disable Combat (no damage / no fighting)");

    /**
     * Constructs the GodModeDialog.
     *
     * @param parent   the parent JFrame (may be null)
     * @param getState supplies the current DevCombatFlags for initial UI state
     * @param onChange called with new DevCombatFlags when Apply is pressed
     */
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

        applyButton.addActionListener(_ -> onChange.accept(readFlags()));
        closeButton.addActionListener(_ -> dispose());

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

    /** Reads the current state of the checkboxes as DevCombatFlags. */
    private DevCombatFlags readFlags() {
        return new DevCombatFlags(godModeCheck.isSelected(), combatDisabledCheck.isSelected());
    }

    /** Safely gets the initial DevCombatFlags, falling back to defaults if needed. */
    private static DevCombatFlags safeGet(Supplier<DevCombatFlags> getState) {
        try {
            DevCombatFlags v = getState.get();
            return v == null ? new DevCombatFlags(false, false) : v;
        } catch (RuntimeException ex) {
            return new DevCombatFlags(false, false);
        }
    }

    /** Registers Escape key to close the dialog. */
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
