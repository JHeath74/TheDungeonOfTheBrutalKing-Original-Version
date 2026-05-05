
package DungeonoftheBrutalKing.DevTools;

import DungeonoftheBrutalKing.Maps.DungeonLevel;
import DungeonoftheBrutalKing.Maps.DungeonLevel1;
import DungeonoftheBrutalKing.Maps.DungeonLevel2;
import DungeonoftheBrutalKing.Maps.DungeonLevel3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DevToolsDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private static final Dimension VIEWPORT_SIZE = new Dimension(420, 520);

    private static final GodModeDialog.DevCombatFlags[] devCombatFlags = {
            new GodModeDialog.DevCombatFlags(false, false)
    };

    public DevToolsDialog(JFrame parent) {
        super(parent, "Developer Tools", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JButton teleportButton = new JButton("Teleport Character");
        JButton mapInfoButton = new JButton("Show Map Numbers");
        JButton godModeButton = new JButton("God Mode / Combat");
        JButton loggingButton = new JButton("Logging Tool");
        JButton gameCaptureButton = new JButton("Game Log Capture (Toggle)");
        JButton loggingToolsButton = new JButton("Logging Tools Dialog");
        JButton mapNumbersButton = new JButton("Map Numbers Dialog");
        JButton closeButton = new JButton("Cancel");


     // In `DevToolsDialog` (or wherever you launch TeleportCharacterTool)
     teleportButton.addActionListener(e -> {
         SwingUtilities.invokeLater(() -> {
             TeleportCharacterTool tool = new TeleportCharacterTool(parent, req -> {
                 JOptionPane.showMessageDialog(
                         parent,
                         "Teleport requested: level=" + req.dungeonLevel() + ", x=" + req.x() + ", y=" + req.y(),
                         "Teleport",
                         JOptionPane.INFORMATION_MESSAGE
                 );
             });
             tool.setVisible(true);
         });
     });


        mapInfoButton.addActionListener(_ -> {
            try {
                List<DungeonLevel> levels = List.of(new DungeonLevel1(), new DungeonLevel2(), new DungeonLevel3());
                MapNumbersDialog dialog = new MapNumbersDialog(parent, levels);
                dialog.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "DevTools Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        godModeButton.addActionListener(_ -> {
            Supplier<GodModeDialog.DevCombatFlags> getState = () -> devCombatFlags[0];
            Consumer<GodModeDialog.DevCombatFlags> onChange = flags -> devCombatFlags[0] = Objects.requireNonNull(flags, "flags");
            GodModeDialog dialog = new GodModeDialog(parent, getState, onChange);
            dialog.setVisible(true);
        });

        loggingButton.addActionListener(_ -> {
            LoggingToolDialog dialog = new LoggingToolDialog(parent);
            dialog.setVisible(true);
        });

        gameCaptureButton.addActionListener(_ -> {
            if (!GameLogCapture.isInstalled()) {
                GameLogCapture.install();
                JOptionPane.showMessageDialog(
                        this,
                        "Game log capture started.\nLogs folder: " + GameLogCapture.getCurrentRunFolder(),
                        "Game Log Capture",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                GameLogCapture.uninstall();
                JOptionPane.showMessageDialog(
                        this,
                        "Game log capture stopped.",
                        "Game Log Capture",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        loggingToolsButton.addActionListener(_ -> {
            LoggingToolDialog dialog = new LoggingToolDialog(parent);
            dialog.setVisible(true);
        });

        mapNumbersButton.addActionListener(_ -> {
            try {
                List<DungeonLevel> levels = List.of(new DungeonLevel1(), new DungeonLevel2(), new DungeonLevel3());
                MapNumbersDialog dialog = new MapNumbersDialog(parent, levels);
                dialog.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "DevTools Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        closeButton.addActionListener(_ -> dispose());

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 8, 0);

        int row = 0;

        gbc.gridy = row++;
        content.add(teleportButton, gbc);

        gbc.gridy = row++;
        content.add(mapInfoButton, gbc);

        gbc.gridy = row++;
        content.add(godModeButton, gbc);

        gbc.gridy = row++;
        content.add(loggingButton, gbc);

        gbc.gridy = row++;
        content.add(gameCaptureButton, gbc);

        gbc.gridy = row++;
        content.add(loggingToolsButton, gbc);

        gbc.gridy = row++;
        content.add(mapNumbersButton, gbc);

        gbc.insets = new Insets(16, 0, 0, 0);
        gbc.gridy = row++;
        content.add(closeButton, gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy = row;
        gbc.weighty = 1.0;
        content.add(Box.createVerticalGlue(), gbc);

        JScrollPane scrollPane = new JScrollPane(
                content,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(VIEWPORT_SIZE);

        setContentPane(scrollPane);

        registerEscapeToClose();

        setMinimumSize(new Dimension(420, 360));
        setPreferredSize(VIEWPORT_SIZE);
        pack();
        setLocationRelativeTo(parent);
        setResizable(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (getHeight() < getMinimumSize().height) {
                    setSize(Math.max(getWidth(), getMinimumSize().width), getMinimumSize().height);
                }
            }
        });
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
