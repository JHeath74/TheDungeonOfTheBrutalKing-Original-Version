
package DevTools;

import Maps.DungeonLevel;
import Maps.MapEntity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MapNumbersDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    public MapNumbersDialog(JFrame parent, List<DungeonLevel> levels) {
        super(parent, "Map Numbers", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea(20, 60);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setTabSize(4);

        String text = buildText(levels);
        textArea.setText(text);
        textArea.setCaretPosition(0);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(closeButton);

        setLayout(new BorderLayout(8, 8));
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(closeButton);
        registerEscapeToClose();

        pack();
        setLocationRelativeTo(parent);
    }

    private static String buildText(List<DungeonLevel> levels) {
        if (levels == null || levels.isEmpty()) {
            return "No levels provided.\n";
        }

        record Row(int number, int level, int x, int y) { }

        List<Row> rows = new ArrayList<>();

        for (DungeonLevel level : levels) {
            if (level == null) continue;

            List<? extends MapEntity> entities;
            try {
                entities = level.getEntities();
            } catch (RuntimeException ex) {
                continue;
            }
            if (entities == null) continue;

            for (MapEntity entity : entities) {
                if (entity == null) continue;

                int number = entity.getNumber();
                if (number == 0 || number == 1) continue;

                rows.add(new Row(
                        number,
                        level.getDungeonLevelNumber(),
                        entity.getX(),
                        entity.getY()
                ));
            }
        }

        rows.sort(Comparator
                .comparingInt(Row::number)
                .thenComparingInt(Row::level)
                .thenComparingInt(Row::x)
                .thenComparingInt(Row::y));

        if (rows.isEmpty()) {
            return "No numbered entities found (excluding 0 and 1).\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s %-7s %-6s %-6s%n", "Number", "Level", "X", "Y"));
        sb.append(String.format("%-8s %-7s %-6s %-6s%n", "------", "-----", "--", "--"));

        for (Row r : rows) {
            sb.append(String.format("%-8d %-7d %-6d %-6d%n", r.number(), r.level(), r.x(), r.y()));
        }

        return sb.toString();
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
