
package Locations.TheRustyTankard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;

public class TheRustyTankard {
    private final JPanel mainPanel;
    private final MainGameScreen myMainGameScreen;

    public TheRustyTankard(JPanel mainPanel2, MainGameScreen mainGameScreen) {
        this.myMainGameScreen = mainGameScreen;
        this.mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(800, 600));
        displayImage();
        promptWhereToSit();
        MainGameScreen.replaceWithAnyPanel(mainPanel);
    }

    private void displayImage() {

System.out.println(GameSettings.NPCImagePath + "Innkeeper - TheRustyTankard.jpeg");

        ImageIcon icon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - TheRustyTankard.jpeg");
        Image scaledImage = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        mainPanel.add(label, BorderLayout.NORTH);
    }

    private void promptWhereToSit() {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton barButton = new JButton("Sit at the bar");
        JButton tableButton = new JButton("Sit at a table");
        JButton leaveButton = new JButton("Leave the inn");
        JButton backroomButton = new JButton("Go to the backroom");

        barButton.addActionListener(_ -> new Innkeeper(mainPanel, myMainGameScreen));
        tableButton.addActionListener(_ -> loadInformationProvider());
        backroomButton.addActionListener(_ -> {
            try {
                InnBackroom.loadBackroom(mainPanel, myMainGameScreen);
            } catch (IOException | InterruptedException | ParseException e) {
                e.printStackTrace();
            }
        });

leaveButton.addActionListener(_ -> {
    if (myMainGameScreen != null) {
        myMainGameScreen.setMessageTextPane("You leave the inn.\n");
        myMainGameScreen.restoreOriginalPanel();
        SwingUtilities.invokeLater(() -> {
            myMainGameScreen.getGameImagesAndCombatPanel().revalidate();
            myMainGameScreen.getGameImagesAndCombatPanel().repaint();
        });
    }
});


        buttonPanel.add(barButton);
        buttonPanel.add(tableButton);
        buttonPanel.add(backroomButton);
        buttonPanel.add(leaveButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void loadInformationProvider() {
        MainGameScreen.replaceWithAnyPanel(new InformationProvider(myMainGameScreen));
    }
}
