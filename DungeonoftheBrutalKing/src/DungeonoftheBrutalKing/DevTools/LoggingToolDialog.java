
package DevTools;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

/**
 * Minimal UI to start/stop log capture to the `logs/` folder.
 */
public final class LoggingToolDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JLabel statusLabel = new JLabel();
    private final JLabel pathLabel = new JLabel();

    public LoggingToolDialog(JFrame parent) {
        super(parent, "Logging Tool", true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel info = new JLabel("<html>Captures JVM logs and System\\.out/System\\.err into a per\\-run folder under <b>logs/</b>.</html>");

        JButton start = new JButton("Start Capture");
        JButton stop = new JButton("Stop Capture");
        JButton openFolder = new JButton("Open Folder");
        JButton close = new JButton("Close");

        start.addActionListener(_ -> {
            GameLogCapture.install();
            refreshUi();
        });

        stop.addActionListener(_ -> {
            GameLogCapture.uninstall();
            refreshUi();
        });

        openFolder.addActionListener(_ -> openCurrentFolder());
        close.addActionListener(_ -> dispose());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        info.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pathLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        center.add(info);
        center.add(Box.createVerticalStrut(10));
        center.add(statusLabel);
        center.add(Box.createVerticalStrut(6));
        center.add(pathLabel);
        center.add(Box.createVerticalStrut(10));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(start);
        buttons.add(stop);
        buttons.add(openFolder);
        buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
        center.add(buttons);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(close);

        setLayout(new BorderLayout(8, 8));
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refreshUi();

        pack();
        setMinimumSize(new Dimension(520, 220));
        setLocationRelativeTo(parent);
    }

    private void refreshUi() {
        boolean on = GameLogCapture.isInstalled();
        statusLabel.setText("Status: " + (on ? "CAPTURING" : "OFF"));

        Path folder = GameLogCapture.getCurrentRunFolder();
        String pathText = (folder == null) ? "(none)" : folder.toAbsolutePath().toString();
        pathLabel.setText("Folder: " + pathText);
    }

    private void openCurrentFolder() {
        Path folder = GameLogCapture.getCurrentRunFolder();
        if (folder == null) return;

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(folder.toUri()));
            }
        } catch (Exception ignored) {
            // best-effort
        }
    }
}
