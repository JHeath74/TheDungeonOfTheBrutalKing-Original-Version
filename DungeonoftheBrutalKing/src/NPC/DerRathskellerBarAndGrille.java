
package NPC;

import javax.sound.sampled.*;
import javax.swing.*;
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

    public DerRathskellerBarAndGrille() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        add(mainPanel);
        setTitle("Main Game Screen");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

   //     displayImage();
    //    playSound();
        promptWhereToSit();
        initializeInnLocation();
        setInnLocation(1, 2, 3, "DerRathskellerBarAndGrille");
    }

    public static void main(String[] args) {
        new DerRathskellerBarAndGrille();
    }

    private void displayImage() {
        ImageIcon icon = new ImageIcon("path/to/your/image.png");
        JLabel label = new JLabel(icon);
        mainPanel.add(label, BorderLayout.NORTH);
    }

    private void playSound() {
        try {
            File soundFile = new File("path/to/your/sound.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void promptWhereToSit() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton barButton = new JButton("Sit at the bar");
        JButton tableButton = new JButton("Sit at a table");
        JButton leaveButton = new JButton("Leave the inn");

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

        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayArea.append("You leave the inn.\n");
            }
        });

        buttonPanel.add(barButton);
        buttonPanel.add(tableButton);
        buttonPanel.add(leaveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void loadInformationProvider() {
        InformationProvider informationProvider = new InformationProvider();
        displayArea.append("Information Provider: " + informationProvider.provideInformation() + "\n");
    }

    private void loadInnkeeper() {
        Innkeeper innkeeper = new Innkeeper();
        displayArea.append("Innkeeper: " + innkeeper.sellItem("Information") + "\n");
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
