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
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.text.ParseException;

public class InnBackroom {
    private static final int BOTTOM_BAR_HEIGHT = 80;

    public static void loadBackroom(JPanel mainPanel, MainGameScreen myMainGameScreen) {
        if (mainPanel == null) return;

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        // Use the static getter for NPC image path
        String imagePath = GameSettings.getNPCImagePath() + "TheRustyTankard/Innkeeper - Backroom.jpeg";
        ImageIcon baseIcon = new ImageIcon(imagePath);

        JLabel pictureLabel = new JLabel();
        pictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(pictureLabel, BorderLayout.CENTER);

        Runnable refreshImage = () -> {
            int w = Math.max(1, mainPanel.getWidth());
            int h = Math.max(1, mainPanel.getHeight());

            int targetW = w;
            int targetH = Math.max(1, h - BOTTOM_BAR_HEIGHT);

            Image scaled = baseIcon.getImage().getScaledInstance(targetW, targetH, Image.SCALE_SMOOTH);
            pictureLabel.setIcon(new ImageIcon(scaled));
            mainPanel.revalidate();
            mainPanel.repaint();
        };

        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                refreshImage.run();
            }
        });
        SwingUtilities.invokeLater(refreshImage);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton buyWeaponButton = new JButton("Buy Weapon");
        JButton buyArmourButton = new JButton("Buy Armour");
        JButton buyShieldButton = new JButton("Buy Shield");
        JButton exitButton = new JButton("Exit Backroom");

        buyWeaponButton.addActionListener(_ -> uiSafely(myMainGameScreen,
                () -> myMainGameScreen.setMessageTextPane("\nYou bought a Weapon.")
        ));

        buyArmourButton.addActionListener(_ -> uiSafely(myMainGameScreen,
                () -> myMainGameScreen.setMessageTextPane("\nYou bought a Armour.")
        ));

        buyShieldButton.addActionListener(_ -> uiSafely(myMainGameScreen,
                () -> myMainGameScreen.setMessageTextPane("\nYou bought a Shield.")
        ));

        exitButton.addActionListener(_ -> exitToRustyTankard(mainPanel, myMainGameScreen));

        buttonPanel.add(buyWeaponButton);
        buttonPanel.add(buyArmourButton);
        buttonPanel.add(buyShieldButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private static void exitToRustyTankard(JPanel mainPanel, MainGameScreen myMainGameScreen) {
        if (myMainGameScreen == null) return;

        JPanel fallback = mainPanel != null ? mainPanel : new JPanel();
        JPanel targetPanel;

        try {
            targetPanel = new TheRustyTankard(fallback, myMainGameScreen).getMainPanel();
        } catch (RuntimeException ex) {
            targetPanel = fallback;
        }

        JPanel finalTarget = targetPanel != null ? targetPanel : fallback;
        // Call replaceWithAnyPanel statically as required
        uiSafely(myMainGameScreen, () -> MainGameScreen.replaceWithAnyPanel(finalTarget));
    }

    private static void uiSafely(MainGameScreen myMainGameScreen, UiAction action) {
        if (myMainGameScreen == null || action == null) return;
        try {
            action.run();
        } catch (IOException | InterruptedException | ParseException | RuntimeException ignored) {
            // Keep navigation/UI flow working even if UI calls fail.
        }
    }

    @FunctionalInterface
    private interface UiAction {
        void run() throws IOException, InterruptedException, ParseException;
    }
}
