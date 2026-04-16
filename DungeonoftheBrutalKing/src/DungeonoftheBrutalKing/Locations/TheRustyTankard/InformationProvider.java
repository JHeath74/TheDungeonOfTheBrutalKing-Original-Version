
// File: `src/DungeonoftheBrutalKing/Locations/TheRustyTankard/InformationProvider.java`
package DungeonoftheBrutalKing.Locations.TheRustyTankard;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

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
import java.io.IOException;
import java.text.ParseException;

public class InformationProvider extends JPanel {
    private static final long serialVersionUID = 1L;

    private static final int SPECIAL_EVERY_N_LISTENS = 20;
    private static final int BOTTOM_BAR_HEIGHT = 80;

    private final String[] randomInfo = {
            "The sky is blue.",
            "Water boils at 100°C.",
            "Cats sleep for 70% of their lives.",
            "The Eiffel Tower can be 15 cm taller during the summer.",
            "Bananas are berries, but strawberries are not.",
            "Octopuses have three hearts.",
            "Honey never spoils.",
            "Sharks existed before trees.",
            "A group of flamingos is called a 'flamboyance'.",
            "The moon has moonquakes."
    };

    private final String specialInfo = "This is the 20th attempt special information!";

    private int listenCounter = 0;

    private final MainGameScreen mainGameScreen;

    private final ImageIcon baseIcon;
    private final JLabel imageLabel;

    public InformationProvider(MainGameScreen mainGameScreen) {
        this.mainGameScreen = mainGameScreen;

        setLayout(new BorderLayout());

        String imagePath = GameSettings.NPCImagePath + "Innkeeper - InformationProvider.png";
        this.baseIcon = new ImageIcon(imagePath);
        this.imageLabel = new JLabel();
        add(imageLabel, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshScaledImage();
            }
        });
        SwingUtilities.invokeLater(this::refreshScaledImage);

        JButton listenButton = new JButton("Listen for Information");
        JButton exitButton = new JButton("Exit to The Rusty Tankard");

        listenButton.addActionListener(_ -> {
            String info = provideInformation();
            uiSafely(() -> mainGameScreen.setMessageTextPane(info + "\n"));
        });

        exitButton.addActionListener(_ -> exitToRustyTankard());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(listenButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
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

    private String provideInformation() {
        listenCounter++;

        if (listenCounter % SPECIAL_EVERY_N_LISTENS == 0) {
            return specialInfo;
        }

        if (randomInfo.length == 0) {
            return "";
        }

        return randomInfo[RandomFactory.gameplayInt(randomInfo.length)];
    }

    private void refreshScaledImage() {
        int w = Math.max(1, getWidth());
        int h = Math.max(1, getHeight());

        int targetW = w;
        int targetH = Math.max(1, h - BOTTOM_BAR_HEIGHT);

        Image img = baseIcon.getImage();
        Image scaled = img.getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaled));

        revalidate();
        repaint();
    }

    private void uiSafely(UiAction action) {
        if (mainGameScreen == null || action == null) return;
        try {
            action.run();
        } catch (IOException | InterruptedException | ParseException | RuntimeException ignored) {
            // Keep flow working even if UI calls fail.
        }
    }

    @FunctionalInterface
    private interface UiAction {
        void run() throws IOException, InterruptedException, ParseException;
    }
}
