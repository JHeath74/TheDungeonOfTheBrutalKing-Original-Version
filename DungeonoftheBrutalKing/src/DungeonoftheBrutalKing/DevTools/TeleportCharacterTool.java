
package DevTools;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;

public class TeleportCharacterTool extends JDialog {
    private static final long serialVersionUID = 1L;

    private static final int FIELD_COLUMNS = 10;

    private final JTextField dungeonLevelField = new JTextField(FIELD_COLUMNS);
    private final JTextField xField = new JTextField(FIELD_COLUMNS);
    private final JTextField yField = new JTextField(FIELD_COLUMNS);

    private final Consumer<TeleportRequest> onTeleport;

    public TeleportCharacterTool(JFrame parent, Consumer<TeleportRequest> onTeleport) {
        super(parent, "Teleport Character Tool", true);
        this.onTeleport = Objects.requireNonNull(onTeleport, "onTeleport");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        installIntegerFilter(dungeonLevelField);
        installIntegerFilter(xField);
        installIntegerFilter(yField);

        dungeonLevelField.setToolTipText("Integer (e.g. 1)");
        xField.setToolTipText("Integer X coordinate");
        yField.setToolTipText("Integer Y coordinate");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.LINE_END;

        c.gridx = 0; c.gridy = 0;
        panel.add(new JLabel("Dungeon Level:"), c);
        c.gridy = 1;
        panel.add(new JLabel("X Coordinate:"), c);
        c.gridy = 2;
        panel.add(new JLabel("Y Coordinate:"), c);

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        c.gridx = 1; c.gridy = 0;
        panel.add(dungeonLevelField, c);
        c.gridy = 1;
        panel.add(xField, c);
        c.gridy = 2;
        panel.add(yField, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("Teleport");
        JButton cancelButton = new JButton("Cancel");
        buttons.add(okButton);
        buttons.add(cancelButton);

        c.gridx = 0; c.gridy = 3;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 0;
        panel.add(buttons, c);

        okButton.addActionListener(_ -> onOk());
        cancelButton.addActionListener(_ -> dispose());

        getRootPane().setDefaultButton(okButton);
        registerEscapeToClose();

        setContentPane(panel);
        pack();
        setLocationRelativeTo(parent);

        SwingUtilities.invokeLater(dungeonLevelField::requestFocusInWindow);
    }

    private void onOk() {
        Integer dungeonLevel = parseIntOrNull(dungeonLevelField.getText());
        Integer x = parseIntOrNull(xField.getText());
        Integer y = parseIntOrNull(yField.getText());

        if (dungeonLevel == null || x == null || y == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid integers for all fields.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (dungeonLevel < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Dungeon Level must be 0 or greater.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            onTeleport.accept(new TeleportRequest(dungeonLevel, x, y));
            dispose();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Teleport failed: " + ex.getMessage(),
                    "Teleport Error",
                    JOptionPane.ERROR_MESSAGE
            );
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

    private static Integer parseIntOrNull(String text) {
        String t = text == null ? "" : text.trim();
        if (t.isEmpty() || "-".equals(t)) return null;
        try {
            return Integer.parseInt(t);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static void installIntegerFilter(JTextField field) {
        if (field.getDocument() instanceof AbstractDocument doc) {
            doc.setDocumentFilter(new IntegerDocumentFilter());
        }
    }

    public record TeleportRequest(int dungeonLevel, int x, int y) { }

    private static final class IntegerDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            replace(fb, offset, 0, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String next = new StringBuilder(current).replace(offset, offset + length, text == null ? "" : text).toString();

            String candidate = next.trim();
            if (candidate.isEmpty() || "-".equals(candidate) || candidate.matches("-?\\d+")) {
                fb.replace(offset, length, text, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}
