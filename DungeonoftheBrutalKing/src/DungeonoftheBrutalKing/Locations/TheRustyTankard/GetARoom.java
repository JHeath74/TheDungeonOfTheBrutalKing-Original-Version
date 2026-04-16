
// File: `src/DungeonoftheBrutalKing/Locations/TheRustyTankard/GetARoom.java`
package DungeonoftheBrutalKing.Locations.TheRustyTankard;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GetARoom extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int ROOM_COST = 50;

    private final MainGameScreen mainGameScreen;

    private final ImageIcon baseIcon;
    private final JLabel imageLabel;

    public GetARoom(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;

        setLayout(new BorderLayout());

        // Image
        this.baseIcon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - Lodging.png");
        this.imageLabel = new JLabel();
        add(imageLabel, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshScaledImage();
            }
        });
        SwingUtilities.invokeLater(this::refreshScaledImage);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton roomButton = new JButton("Room for " + ROOM_COST);
        JButton exitButton = new JButton("Exit");

        roomButton.addActionListener(_ -> handleRoomPurchase());
        exitButton.addActionListener(_ -> exitToRustyTankard());

        buttonPanel.add(roomButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleRoomPurchase() {
        if (mainGameScreen == null) return;

        Charecter player;
        try {
            player = Charecter.getInstance();
        } catch (RuntimeException ex) {
            return;
        }
        if (player == null) return;

        int gold = player.getGold();
        int maxHP = player.getMaxHitPoints();

        if (gold >= ROOM_COST) {
            player.setGold(gold - ROOM_COST);
            player.setHitPoints(maxHP);
            uiSafely(() -> mainGameScreen.setMessageTextPane(
                "You pay " + ROOM_COST + " gold, rest, and recover to full health."
            ));
        } else {
            uiSafely(() -> mainGameScreen.setMessageTextPane(
                "You don't have enough gold for a room."
            ));
        }
    }

    private void exitToRustyTankard() {
        if (mainGameScreen == null) return;

        JPanel fallback = new JPanel();
        JPanel targetPanel;
        try {
            targetPanel = new TheRustyTankard(fallback, mainGameScreen).getMainPanel();
        } catch (RuntimeException ex) {
            targetPanel = fallback;
        }

        JPanel finalTarget = targetPanel != null ? targetPanel : fallback;
        uiSafely(() -> mainGameScreen.replaceWithAnyPanel(finalTarget));
    }

    private void refreshScaledImage() {
        int w = Math.max(1, getWidth());
        int h = Math.max(1, getHeight());

        int targetW = w;
        int targetH = Math.max(1, h - 80);

        Image img = baseIcon.getImage();
        Image scaled = img.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
        revalidate();
        repaint();
    }

    private void uiSafely(UiAction action) {
        try {
            action.run();
        } catch (Exception ignored) {
            // Keep flow working even if UI fails.
        }
    }

    @FunctionalInterface
    private interface UiAction {
        void run() throws Exception;
    }
}
