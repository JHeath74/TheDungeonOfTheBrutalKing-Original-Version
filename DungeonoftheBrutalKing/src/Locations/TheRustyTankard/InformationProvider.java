
package Locations.TheRustyTankard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

public class InformationProvider extends JPanel {
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
    private final MainGameScreen myMainGameScreen;

    public InformationProvider(MainGameScreen mainGameScreen) {
        this.myMainGameScreen = mainGameScreen;
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        
        String imagePath = GameSettings.NPCImagePath + "Innkeeper - InformationProvider.png";
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        add(imageLabel, BorderLayout.NORTH);

        JButton listenButton = new JButton("Listen for Information");
        JButton exitButton = new JButton("Exit to The Rusty Tankard");

        listenButton.addActionListener(e -> {
            String info = provideInformation();
            myMainGameScreen.setMessageTextPane(info + "\n");
        });

        exitButton.addActionListener(e -> {
            MainGameScreen.replaceWithAnyPanel(
                new TheRustyTankard(new JPanel(), myMainGameScreen).getMainPanel()
            );
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(listenButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String provideInformation() {
        listenCounter++;
        if (listenCounter % 20 == 0) {
            return specialInfo;
        }
        return randomInfo[new Random().nextInt(randomInfo.length)];
    }
}
