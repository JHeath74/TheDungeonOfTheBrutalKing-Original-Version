
package DevTools;

import Maps.DungeonLevel;
import Maps.DungeonLevel1;
import Maps.DungeonLevel2;
import Maps.DungeonLevel3;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DevToolsDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    // Simple in-memory dev flags (wire to your real game state if you have one)
    private static GodModeDialog.DevCombatFlags devCombatFlags =
            new GodModeDialog.DevCombatFlags(false, false);

    public DevToolsDialog(JFrame parent) {
        super(parent, "Developer Tools", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JButton teleportButton = new JButton("Teleport Character");
        JButton mapInfoButton = new JButton("Show Map Numbers");
        JButton godModeButton = new JButton("God Mode / Combat");
        JButton loggingButton = new JButton("Logging Tool");
        JButton closeButton = new JButton("Close");

        teleportButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        mapInfoButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        godModeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loggingButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        closeButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        teleportButton.addActionListener(_ -> {
            TeleportCharacterTool tool = new TeleportCharacterTool(parent, req -> {
                // TODO: Replace with your real teleport logic.
                // Example: game.teleportPlayer(req.dungeonLevel(), req.x(), req.y());
                JOptionPane.showMessageDialog(
                        this,
                        "Teleport requested: level=" + req.dungeonLevel() + ", x=" + req.x() + ", y=" + req.y(),
                        "Teleport",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
            tool.setVisible(true);
        });

        mapInfoButton.addActionListener(_ -> {
            try {
                List<DungeonLevel> levels = List.of(
                        new DungeonLevel1(),
                        new DungeonLevel2(),
                        new DungeonLevel3()
                );
                MapNumbersDialog dialog = new MapNumbersDialog(parent, levels);
                dialog.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex.getMessage(),
                        "DevTools Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        godModeButton.addActionListener(_ -> {
            Supplier<GodModeDialog.DevCombatFlags> getState = () -> devCombatFlags;
            Consumer<GodModeDialog.DevCombatFlags> onChange = flags -> {
                devCombatFlags = Objects.requireNonNull(flags, "flags");
                // TODO: Wire these flags into your combat/damage system.
                // Example: game.getDevFlags().setGodMode(flags.godMode());
                // Example: game.getDevFlags().setCombatDisabled(flags.combatDisabled());
            };

            GodModeDialog dialog = new GodModeDialog(parent, getState, onChange);
            dialog.setVisible(true);
        });

        loggingButton.addActionListener(_ -> {
            LoggingToolDialog dialog = new LoggingToolDialog(parent);
            dialog.setVisible(true);
        });

        closeButton.addActionListener(_ -> dispose());

        panel.add(teleportButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(mapInfoButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(godModeButton);
        panel.add(Box.createVerticalStrut(8));
        panel.add(loggingButton);
        panel.add(Box.createVerticalStrut(16));
        panel.add(closeButton);

        setContentPane(panel);
        getRootPane().setDefaultButton(closeButton);
        registerEscapeToClose();

        pack();
        setMinimumSize(new Dimension(420, 220));
        setLocationRelativeTo(parent);
        // Do not call setVisible(true) in the constructor; show the dialog from the caller.
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
