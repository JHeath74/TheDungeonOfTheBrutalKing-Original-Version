
package NPC;

import javax.sound.sampled.*;
import javax.swing.*;

import DungeonoftheBrutalKing.MusicPlayer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class DerRathskellerBarAndGrille extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private JTextArea displayArea;
    private String[][][] innLocation;

    public DerRathskellerBarAndGrille(JPanel mainPanel2, JTextArea displayArea2) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        add(mainPanel);
        setTitle("Main Game Screen");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

   ///    displayImage();
    //    playSound();
        promptWhereToSit();
        initializeInnLocation();
        setInnLocation(1, 2, 3, "DerRathskellerBarAndGrille");
    }

    public static void main(String[] args) {
        new DerRathskellerBarAndGrille(new JPanel(), new JTextArea());
    }

    private void displayImage() {
        ImageIcon icon = new ImageIcon("path/to/your/image.png");
        JLabel label = new JLabel(icon);
        mainPanel.add(label, BorderLayout.NORTH);
    }

    private void playSound() {
        String soundFilePath = "path/to/your/sound.wav"; // Replace with the actual path
        MusicPlayer.wavePlayer(soundFilePath);
    }

    private void promptWhereToSit() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton barButton = new JButton("Sit at the bar");
        JButton tableButton = new JButton("Sit at a table");
        JButton leaveButton = new JButton("Leave the inn");
        JButton backroomButton = new JButton("Go to the backroom");

        barButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadInnkeeper();
            }
        });

        tableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadInformationProvider();
            }
        });
        
        backroomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });

        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayArea.append("You leave the inn.\n");
            }
        });

        buttonPanel.add(barButton);
        buttonPanel.add(tableButton);
        buttonPanel.add(backroomButton);
        buttonPanel.add(leaveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void loadInformationProvider() {
        SwingUtilities.invokeLater(() -> {
            new InformationProvider().setVisible(true); // Display the InformationProvider GUI
        });
    }

    private void loadInnkeeper() {
        Innkeeper innkeeper = new Innkeeper(mainPanel, displayArea);
        innkeeper.setupUI();
        
    }
    
    private void loadInnBackroom() {
    	InnBackroom backroom = new InnBackroom();
        backroom.innBackroom();
    }

    private void initializeInnLocation() {
        innLocation = new String[10][10][10]; // Adjust the size as needed
    }

    private void setInnLocation(int x, int y, int z, String innName) {
        innLocation[x][y][z] = innName;
    }

    private String getInnLocation(int x, int y, int z) {
        return innLocation[x][y][z];
    }
}
