
package DungeonoftheBrutalKing.DevTools;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import java.util.function.Consumer;

public class TeleportCharacterTool extends JDialog {

    private static final long serialVersionUID = 1L;
    private static final int FIELD_COLUMNS = 10;

    private final JTextField dungeonLevelField = new JTextField(FIELD_COLUMNS);
    private final JTextField xField            = new JTextField(FIELD_COLUMNS);
    private final JTextField yField            = new JTextField(FIELD_COLUMNS);

    private final Consumer<TeleportRequest> onTeleport;

    public TeleportCharacterTool(JFrame parent, Consumer<TeleportRequest> onTeleport) {
        super(parent, "Teleport Character Tool", true);
        this.onTeleport = Objects.requireNonNull(onTeleport, "onTeleport must not be null");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        installNonNegativeFilter(dungeonLevelField);
        installNonNegativeFilter(xField);
        installNonNegativeFilter(yField);

        dungeonLevelField.setToolTipText("Dungeon level (0 or greater)");
        xField.setToolTipText("X coordinate (0 or greater)");
        yField.setToolTipText("Y coordinate (0 or greater)");

        JPanel formPanel = buildFormPanel();

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton     = new JButton("Teleport");
        JButton cancelButton = new JButton("Cancel");
        buttons.add(okButton);
        buttons.add(cancelButton);

        GridBagConstraints gbc = buttonConstraints();
        formPanel.add(buttons, gbc);

        okButton.addActionListener(this::onTeleportClicked);
        cancelButton.addActionListener(_ -> dispose());

        getRootPane().setDefaultButton(okButton);
        registerEscapeToClose();

        setContentPane(formPanel);
        pack();
        setLocationRelativeTo(parent);

        // Focus first field after dialog is shown
        SwingUtilities.invokeLater(dungeonLevelField::requestFocusInWindow);
    }

    // ── Form Builder ──────────────────────────────────────────────────────────

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addFormRow(panel, "Dungeon Level:", dungeonLevelField, 0);
        addFormRow(panel, "X Coordinate:", xField,            1);
        addFormRow(panel, "Y Coordinate:", yField,            2);

        return panel;
    }

    private static void addFormRow(JPanel panel, String labelText, JComponent field, int row) {
        GridBagConstraints label = new GridBagConstraints();
        label.gridx  = 0; label.gridy = row;
        label.anchor = GridBagConstraints.LINE_END;
        label.insets = new Insets(4, 4, 4, 4);
        panel.add(new JLabel(labelText), label);

        GridBagConstraints input = new GridBagConstraints();
        input.gridx   = 1; input.gridy = row;
        input.anchor  = GridBagConstraints.LINE_START;
        input.fill    = GridBagConstraints.HORIZONTAL;
        input.weightx = 1.0;
        input.insets  = new Insets(4, 4, 4, 4);
        panel.add(field, input);
    }

    private static GridBagConstraints buttonConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx      = 0; gbc.gridy = 3;
        gbc.gridwidth  = 2;
        gbc.fill       = GridBagConstraints.NONE;
        gbc.anchor     = GridBagConstraints.LINE_END;
        gbc.insets     = new Insets(4, 4, 4, 4);
        return gbc;
    }

    // ── Action Handlers ───────────────────────────────────────────────────────

    private void onTeleportClicked(ActionEvent ignored) {
        Integer dungeonLevel = parseNonNegativeInt(dungeonLevelField.getText());
        Integer x            = parseNonNegativeInt(xField.getText());
        Integer y            = parseNonNegativeInt(yField.getText());

        if (dungeonLevel == null) { showError("Dungeon Level must be a non-negative integer."); return; }
        if (x == null)            { showError("X Coordinate must be a non-negative integer.");  return; }
        if (y == null)            { showError("Y Coordinate must be a non-negative integer.");  return; }

        try {
            onTeleport.accept(new TeleportRequest(dungeonLevel, x, y));
            dispose();
        } catch (RuntimeException ex) {
            showError("Teleport failed: " + ex.getMessage());
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private void registerEscapeToClose() {
        JRootPane root = getRootPane();
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("ESCAPE"), "close");
        root.getActionMap().put("close", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent ignored) { dispose(); }
        });
    }

    private static Integer parseNonNegativeInt(String text) {
        if (text == null) return null;
        String trimmed = text.trim();
        if (trimmed.isEmpty()) return null;
        try {
            int value = Integer.parseInt(trimmed);
            return value >= 0 ? value : null;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static void installNonNegativeFilter(JTextField field) {
        if (field.getDocument() instanceof AbstractDocument doc) {
            doc.setDocumentFilter(new NonNegativeIntegerFilter());
        }
    }

    // ── Record ────────────────────────────────────────────────────────────────

    public record TeleportRequest(int dungeonLevel, int x, int y) { }

    // ── Document Filter ───────────────────────────────────────────────────────

    private static final class NonNegativeIntegerFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            replace(fb, offset, 0, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String next = new StringBuilder(current)
                    .replace(offset, offset + length, text == null ? "" : text)
                    .toString()
                    .trim();

            // Only allow empty string or non-negative integer
            if (next.isEmpty() || next.matches("\\d+")) {
                fb.replace(offset, length, text, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}
