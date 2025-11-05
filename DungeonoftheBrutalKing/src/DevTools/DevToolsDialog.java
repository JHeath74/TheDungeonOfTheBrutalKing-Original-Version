package DevTools;

import javax.swing.*;
import Maps.DungeonLevel;
import Maps.DungeonLevel1;
import Maps.DungeonLevel2;
import Maps.DungeonLevel3;

public class DevToolsDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public DevToolsDialog(JFrame parent) {
        super(parent, "Developer Tools", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Create panel and set layout/border
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create buttons
        JButton teleportButton = new JButton("Teleport Character");
        JButton mapInfoButton = new JButton("Show Map Numbers");
        JButton closeButton = new JButton("Close");

        // Add action listeners
		
		  teleportButton.addActionListener(_ -> { TeleportCharacterTool tool = new
		  TeleportCharacterTool(parent); tool.setVisible(true); });
		 
        mapInfoButton.addActionListener(_ -> {
            try {
                java.util.List<DungeonLevel> levels = java.util.List.of(
                    new DungeonLevel1(),
                    new DungeonLevel2(),
                    new DungeonLevel3()
                );
                MapNumbersDialog dialog = new MapNumbersDialog(parent, levels);
                dialog.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(parent, "Error: " + ex.getMessage());
            }
        });

        closeButton.addActionListener(_ -> dispose());

        // Add buttons to panel
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Less padding
        panel.add(teleportButton);
        panel.add(mapInfoButton);
        panel.add(closeButton);
        panel.validate();
        panel.revalidate();
        panel.repaint();
        
    

        // Add panel to dialog and set properties
        getContentPane().add(panel);
        setMinimumSize(new java.awt.Dimension(1200, 600));
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    

}
