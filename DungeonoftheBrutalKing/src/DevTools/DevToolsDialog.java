
package DevTools;

import javax.swing.*;

public class DevToolsDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public DevToolsDialog(JFrame parent) {
        super(parent, "Developer Tools", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton teleportButton = new JButton("Teleport Character");
        
        teleportButton.addActionListener(_ -> {
            System.out.println("Teleport Character Tool Launched");
            TeleportCharacterTool tool = new TeleportCharacterTool((JFrame) getParent());
            tool.setVisible(true);
        });

        panel.add(teleportButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(_ -> dispose());
        panel.add(Box.createVerticalStrut(20));
        panel.add(closeButton);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
    }
}
