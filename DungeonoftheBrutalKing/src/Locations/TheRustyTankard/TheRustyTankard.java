
// src/NPC/DerRathskellerBarAndGrille/DerRathskellerBarAndGrille.java
package Locations.TheRustyTankard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import SharedData.MusicPlayer;

public class TheRustyTankard extends JFrame {
    private static final long serialVersionUID = 1L;
    public JPanel mainPanel;
    private JTextArea displayArea;

    GameSettings myGameSettings = new GameSettings();
    MainGameScreen myMainGameScreen;
    
    private final int x = 2;
    private final int y = 4;
    private final int z = 1;

    public TheRustyTankard(JPanel mainPanel2) {
        mainPanel = mainPanel2;
        mainPanel.setLayout(new BorderLayout());

        setTitle("The Rusty Tankard");
        setPreferredSize(new Dimension(800, 600));

        displayImage();
        promptWhereToSit();

        add(mainPanel);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TheRustyTankard(new JPanel());
    }

    private void displayImage() {
        ImageIcon icon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - DerRathskellerBarAndGrille.jpeg");
        Image scaledImage = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        mainPanel.add(label, BorderLayout.NORTH);
    }

    private void playSound() {
        String soundFilePath = "path/to/your/sound.wav";
        MusicPlayer.wavePlayer(soundFilePath);
    }

    private void promptWhereToSit() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton barButton = new JButton("Sit at the bar");
        JButton tableButton = new JButton("Sit at a table");
        JButton leaveButton = new JButton("Leave the inn");
        JButton backroomButton = new JButton("Go to the backroom");

        barButton.addActionListener(e -> loadInnkeeper());
        tableButton.addActionListener(e -> loadInformationProvider());
        backroomButton.addActionListener(e -> {
            try {
                loadInnBackroom();
            } catch (IOException | InterruptedException | ParseException e1) {
                e1.printStackTrace();
            }
        });
        leaveButton.addActionListener(e -> myMainGameScreen.setMessageTextPane("You leave the inn.\n"));

        buttonPanel.add(barButton);
        buttonPanel.add(tableButton);
        buttonPanel.add(backroomButton);
        buttonPanel.add(leaveButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        centerPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);
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
        Innkeeper innkeeper = new Innkeeper(mainPanel, displayArea);
        innkeeper.setupUI();
    }

    private void loadInnBackroom() throws IOException, InterruptedException, ParseException {
        InnBackroom myInnBackroom = new InnBackroom();
        InnBackroom.loadBackroom();
    }
}
