
package DevTools;

import javax.swing.*;
import java.awt.*;

public class TeleportCharacterTool extends JDialog {
    private static final long serialVersionUID = 1L;

    public TeleportCharacterTool(JFrame parent) {
        super(parent, "Teleport Character", true);
        System.out.println("TeleportCharacterTool dialog created");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("X:"));
        JTextField xField = new JTextField();
        panel.add(xField);

        panel.add(new JLabel("Y:"));
        JTextField yField = new JTextField();
        panel.add(yField);

        JButton teleportBtn = new JButton("Teleport");
        teleportBtn.addActionListener(_ -> {
            String xText = xField.getText();
            String yText = yField.getText();
            // TODO: Add actual teleport logic here
            JOptionPane.showMessageDialog(this, "Teleporting to X: " + xText + ", Y: " + yText);
            dispose();
        });
        panel.add(teleportBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(_ -> dispose());
        panel.add(cancelBtn);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
}
