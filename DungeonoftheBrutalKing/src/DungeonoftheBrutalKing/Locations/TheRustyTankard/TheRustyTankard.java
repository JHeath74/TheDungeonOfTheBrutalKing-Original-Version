
package DungeonoftheBrutalKing.Locations.TheRustyTankard;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.GameSettings;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.text.ParseException;

public class TheRustyTankard {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int BOTTOM_BAR_HEIGHT = 80;

    private final JPanel mainPanel;
    private final JPanel returnPanel;
    private final MainGameScreen myMainGameScreen;
    private final ImageIcon baseIcon;
    private final JLabel imageLabel;

    public TheRustyTankard(JPanel returnPanel, MainGameScreen mainGameScreen) {
        this.myMainGameScreen = mainGameScreen;
        this.returnPanel = returnPanel;

        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        String imgPath = GameSettings.getNPCImagePath() + "TheRustyTankard/Innkeeper - TheRustyTankard.jpeg";
        this.baseIcon = new ImageIcon(imgPath);

        if (baseIcon.getImageLoadStatus() != java.awt.MediaTracker.COMPLETE) {
            System.err.println("Failed to load image: " + imgPath);
        }

        this.imageLabel = new JLabel();
        this.imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        displayImage();
        promptWhereToSit();
        // Removed: do NOT call replaceWithAnyPanel here — caller is responsible
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void displayImage() {
        mainPanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshScaledImage();
            }
        });
        SwingUtilities.invokeLater(this::refreshScaledImage);
    }

    private void refreshScaledImage() {
        int w = mainPanel.getWidth();
        int h = mainPanel.getHeight();
        if (w <= 0 || h <= 0) return;

        int targetH = Math.max(1, h - BOTTOM_BAR_HEIGHT);
        Image scaled = baseIcon.getImage().getScaledInstance(w, targetH, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void promptWhereToSit() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton barButton      = new JButton("Sit at the bar");
        JButton tableButton    = new JButton("Sit at a table");
        JButton backroomButton = new JButton("Go to the backroom");
        JButton getRoomButton  = new JButton("Get a Room");
        JButton leaveButton    = new JButton("Leave the inn");

        barButton.addActionListener(e -> {
            if (myMainGameScreen == null) return;
            Innkeeper innkeeper = new Innkeeper(mainPanel, myMainGameScreen);
            innkeeper.setupUI();
        });

        tableButton.addActionListener(e -> loadInformationProvider());

        backroomButton.addActionListener(e ->
                uiSafely(() -> InnBackroom.loadBackroom(mainPanel, myMainGameScreen))
        );

        getRoomButton.addActionListener(e -> {
            if (myMainGameScreen == null) return;
            uiSafely(() -> MainGameScreen.replaceWithAnyPanel(new GetARoom(myMainGameScreen)));
        });

        leaveButton.addActionListener(e -> {
            if (myMainGameScreen == null) return;
            uiSafely(() -> myMainGameScreen.setMessageTextPane("You leave the inn.\n"));

            // Leaving the inn should always return to the dungeon render panel.
            myMainGameScreen.restoreOriginalPanel();

            JPanel hostPanel = myMainGameScreen.getGameImagesAndCombatPanel();
            if (hostPanel != null) {
                hostPanel.revalidate();
                hostPanel.repaint();
            }
        });

        buttonPanel.add(barButton);
        buttonPanel.add(tableButton);
        buttonPanel.add(backroomButton);
        buttonPanel.add(getRoomButton);
        buttonPanel.add(leaveButton);

        JPanel south = new JPanel(new BorderLayout());
        south.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(south, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void loadInformationProvider() {
        if (myMainGameScreen == null) return;
        uiSafely(() -> MainGameScreen.replaceWithAnyPanel(new InformationProvider(myMainGameScreen)));
    }

    private void uiSafely(UiAction action) {
        if (action == null) return;
        try {
            action.run();
        } catch (IOException | InterruptedException | ParseException e) {
            System.err.println("UI action failed: " + e.getMessage());
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.err.println("UI action RuntimeException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    private interface UiAction {
        void run() throws IOException, InterruptedException, ParseException;
    }
}
