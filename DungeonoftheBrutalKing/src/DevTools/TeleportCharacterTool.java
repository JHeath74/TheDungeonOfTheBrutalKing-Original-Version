package DevTools;

import javax.swing.*;
import java.awt.*;

public class TeleportCharacterTool extends JDialog {
    private static final long serialVersionUID = 1L;

    private JTextField dungeonLevelField;
    private JTextField xField;
    private JTextField yField;

    public TeleportCharacterTool(JFrame parent) {
        super(parent, "Teleport Character Tool", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        panel.add(new JLabel("Dungeon Level:"));
        dungeonLevelField = new JTextField();
        panel.add(dungeonLevelField);

        panel.add(new JLabel("X Coordinate:"));
        xField = new JTextField();
        panel.add(xField);

        panel.add(new JLabel("Y Coordinate:"));
        yField = new JTextField();
        panel.add(yField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(_ -> {
            try {
                int dungeonLevel = Integer.parseInt(dungeonLevelField.getText());
                int x = Integer.parseInt(xField.getText());
                int y = Integer.parseInt(yField.getText());
                teleportPlayer(dungeonLevel, x, y);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(_ -> dispose());

        panel.add(okButton);
        panel.add(cancelButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    // Replace this with your actual player teleport logic
    private void teleportPlayer(int dungeonLevel, int x, int y) {
        System.out.printf("Teleporting player to level %d, x=%d, y=%d%n", dungeonLevel, x, y);
        // Example: MainGameScreen.teleportPlayerTo(dungeonLevel, x, y);
    }
}
