package Locations.TheRustyTankard;

import javax.swing.*;
import java.awt.*;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

public class GetARoom extends JPanel {
    private static final long serialVersionUID = 1L;

    public GetARoom(MainGameScreen mainGameScreen) {
        setLayout(new BorderLayout());

        // Image
        ImageIcon icon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - Lodging.png");
        Image scaledImage = icon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        add(imageLabel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton roomButton = new JButton("Room for 50");
        JButton exitButton = new JButton("Exit");

        roomButton.addActionListener(_ -> {
            Charecter player = mainGameScreen.getPlayer();
            int gold = player.getGold();
            int maxHP = player.getMaxHitPoints();

            if (gold >= 50) {
                player.setGold(gold - 50);
                player.setHitPoints(maxHP);
                mainGameScreen.setMessageTextPane("You pay 50 gold, rest, and recover to full health.");
            } else {
                mainGameScreen.setMessageTextPane("You don't have enough gold for a room.");
            }
        });

        exitButton.addActionListener(_ -> {
            MainGameScreen.replaceWithAnyPanel(new TheRustyTankard(new JPanel(), mainGameScreen).getMainPanel());
        });

        buttonPanel.add(roomButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
