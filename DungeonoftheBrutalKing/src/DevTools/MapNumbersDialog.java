
package DevTools;

import javax.swing.*;
import Maps.DungeonLevel;
import Maps.MapEntity;

import java.awt.*;
import java.util.List;

// Add serialVersionUID
public class MapNumbersDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public MapNumbersDialog(JFrame parent, List<DungeonLevel> levels) {
        super(parent, "Map Numbers", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea(20, 40);
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (DungeonLevel level : levels) {
            // Use getEntities() if available, otherwise replace with correct method
            List<? extends MapEntity> entities = level.getEntities();
            for (MapEntity entity : entities) {
                int number = entity.getNumber();
                if (number == 0 || number == 1) continue;
                sb.append("Number: ").append(number)
                  .append(" | Level: ").append(level.getDungeonLevelNumber())
                  .append(" | X: ").append(entity.getX())
                  .append(" | Y: ").append(entity.getY())
                  .append("\n");
            }
        }
        textArea.setText(sb.toString());

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}

