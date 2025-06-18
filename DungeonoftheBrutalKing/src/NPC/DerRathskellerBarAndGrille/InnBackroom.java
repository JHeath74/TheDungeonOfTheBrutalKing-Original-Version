
package NPC.DerRathskellerBarAndGrille;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import DungeonoftheBrutalKing.GameSettings;
import DungeonoftheBrutalKing.MainGameScreen;

public class InnBackroom {
    public static void loadBackroom() throws IOException, InterruptedException {

    	GameSettings myGameSettings = new GameSettings();
    	MainGameScreen myMainGameScreen = new MainGameScreen();

        // Create the main frame
        JFrame frame = new JFrame("Inn Backroom");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Add the picture at the top

     // Add the picture at the top
	     ImageIcon originalIcon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - Backroom.jpeg");
	     Image resizedImage = originalIcon.getImage().getScaledInstance(640, 480, Image.SCALE_SMOOTH);
	     ImageIcon resizedIcon = new ImageIcon(resizedImage);
	     JLabel pictureLabel = new JLabel(resizedIcon);
	     frame.add(pictureLabel, BorderLayout.NORTH);


        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Horizontal layout with spacing

        // Create buttons
        JButton buyWeaponButton = new JButton("Buy Weapon");
        JButton buyArmourButton = new JButton("Buy Armour");
        JButton buyShieldButton = new JButton("Buy Shield");
        JButton exitButton = new JButton("Exit Backroom");

        // Add action listeners to buttons
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
                frame.dispose(); // Close the window
            }
        });

        // Add buttons to the panel
        buttonPanel.add(buyWeaponButton);
        buttonPanel.add(buyArmourButton);
        buttonPanel.add(buyShieldButton);
        buttonPanel.add(exitButton);

        // Add the button panel to the frame
        frame.add(buttonPanel, BorderLayout.CENTER);

     // Adjust the frame size to fit its contents
        frame.pack();

        // Make the frame visible
        frame.setVisible(true);
    }
}
