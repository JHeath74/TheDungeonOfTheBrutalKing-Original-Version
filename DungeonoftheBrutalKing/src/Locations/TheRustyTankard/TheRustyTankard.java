
// src/Locations/TheRustyTankard/TheRustyTankard.java
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

public class TheRustyTankard extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JPanel mainPanel;
    private final MainGameScreen myMainGameScreen;

    public TheRustyTankard(JPanel mainPanel2, MainGameScreen mainGameScreen) {
        mainPanel = mainPanel2;
        myMainGameScreen = mainGameScreen;
        mainPanel.setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        displayImage();
        promptWhereToSit();
        add(mainPanel);
    }

    private void displayImage() {
        ImageIcon icon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - DerRathskellerBarAndGrille.jpeg");
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

        barButton.addActionListener(_ -> loadInnkeeper());
        tableButton.addActionListener(_ -> loadInformationProvider());
        backroomButton.addActionListener(_ -> {
            try {
                loadInnBackroom();
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
        });
        leaveButton.addActionListener(_ -> {
            if (myMainGameScreen != null) {
                myMainGameScreen.setMessageTextPane("You leave the inn.\n");
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

    private void loadInformationProvider() {
        SwingUtilities.invokeLater(() -> {
            try {
                new InformationProvider().setVisible(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadInnkeeper() {
        Innkeeper innkeeper = new Innkeeper(mainPanel, myMainGameScreen);
        innkeeper.setupUI();
    }

    private void loadInnBackroom() throws IOException, InterruptedException, ParseException {
        InnBackroom.loadBackroom(mainPanel, myMainGameScreen);
    }
}
