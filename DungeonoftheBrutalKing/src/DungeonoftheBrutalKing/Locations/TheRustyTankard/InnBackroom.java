
package Locations.TheRustyTankard;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

public class InnBackroom {
    public static void loadBackroom(JPanel mainPanel, MainGameScreen myMainGameScreen) throws IOException, InterruptedException, ParseException {
        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        ImageIcon originalIcon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - Backroom.jpeg");
        Image resizedImage = originalIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel pictureLabel = new JLabel(resizedIcon);
        pictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        System.out.println(GameSettings.NPCImagePath + "Innkeeper - Backroom.jpeg");
        mainPanel.add(pictureLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton buyWeaponButton = new JButton("Buy Weapon");
        JButton buyArmourButton = new JButton("Buy Armour");
        JButton buyShieldButton = new JButton("Buy Shield");
        JButton exitButton = new JButton("Exit Backroom");

        buyWeaponButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMainGameScreen.setMessageTextPane("\nYou bought a Weapon.");
            }
        });

        buyArmourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMainGameScreen.setMessageTextPane("\nYou bought a Armour.");
            }
        });

        buyShieldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myMainGameScreen.setMessageTextPane("\nYou bought a Shield.");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TheRustyTankard(mainPanel, myMainGameScreen);
            }
        });

        buttonPanel.add(buyWeaponButton);
        buttonPanel.add(buyArmourButton);
        buttonPanel.add(buyShieldButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
