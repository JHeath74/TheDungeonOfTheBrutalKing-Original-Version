
package NPC.DerRathskellerBarAndGrille;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import DungeonoftheBrutalKing.MainGameScreen;
import SharedData.GameSettings;
import SharedData.MusicPlayer;

public class DerRathskellerBarAndGrille extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private JTextArea displayArea;
    private String[][][] innLocation;

    GameSettings myGameSettings = new GameSettings();
    MainGameScreen myMainGameScreen;

public DerRathskellerBarAndGrille(JPanel mainPanel2) {
    mainPanel = mainPanel2; // Use the passed mainPanel
    mainPanel.setLayout(new BorderLayout());

 //   myMainGameScreen.replaceWithAnyPanel(mainPanel);

    // Add welcome message
    JLabel welcomeLabel = new JLabel("Welcome to DerRathskellerBarAndGrille!", SwingConstants.CENTER);
    welcomeLabel.setFont(new Font("Serif", Font.BOLD, 18));
    mainPanel.add(welcomeLabel, BorderLayout.NORTH);

    setTitle("DerRathskellerBarAndGrille");
    setPreferredSize(new Dimension(800, 600)); // Adjust dimensions as needed

    displayImage(); // Add the image to the top
    promptWhereToSit(); // Add buttons and text area

    add(mainPanel); // Add the main panel to the JFrame
    pack();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setVisible(true);
}


    public static void main(String[] args) {
        new DerRathskellerBarAndGrille(new JPanel());
    }


private void displayImage() {
    ImageIcon icon = new ImageIcon(GameSettings.NPCImagePath + "Innkeeper - DerRathskellerBarAndGrille.jpeg");
    Image scaledImage = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH); // Adjust width and height as needed
    JLabel label = new JLabel(new ImageIcon(scaledImage));
    // Add the image to the top of the main panel
    mainPanel.add(label, BorderLayout.NORTH);
}



    private void playSound() {
        String soundFilePath = "path/to/your/sound.wav"; // Replace with the actual path
        MusicPlayer.wavePlayer(soundFilePath);
    }


    private void promptWhereToSit() {
        // Create a panel for buttons
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
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        leaveButton.addActionListener(e -> myMainGameScreen.setMessageTextPane("You leave the inn.\n"));

        buttonPanel.add(barButton);
        buttonPanel.add(tableButton);
        buttonPanel.add(backroomButton);
        buttonPanel.add(leaveButton);

        // Create a new panel to hold the text area and buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        // Add the text area to the center of the new panel
        centerPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Add the button panel to the bottom of the new panel
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the new panel to the main panel
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void loadInformationProvider() {
        SwingUtilities.invokeLater(() -> {
            try {
				new InformationProvider().setVisible(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Display the InformationProvider GUI
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
