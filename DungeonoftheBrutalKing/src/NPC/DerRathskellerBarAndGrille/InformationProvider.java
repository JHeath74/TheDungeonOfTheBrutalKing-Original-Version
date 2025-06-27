
package NPC.DerRathskellerBarAndGrille;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import DungeonoftheBrutalKing.MainGameScreen;

public class InformationProvider extends JFrame {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String[] randomInfo = {
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
    //specialInfo will be a clue for a quest
    private String specialInfo = "This is the 20th attempt special information!";
    private int listenCounter = 0;
    MainGameScreen myMainGameScreen;




public InformationProvider() throws InterruptedException {

	try {
        myMainGameScreen = new MainGameScreen(); // Handle IOException here
    } catch (IOException | ParseException e) {
        e.printStackTrace(); // Log the exception
        JOptionPane.showMessageDialog(this, "Error initializing MainGameScreen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Set up the frame
    setTitle("Information Listener");
    setPreferredSize(new Dimension(800, 600)); // Increase the size of the JFrame
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // Create components
 //   JTextArea displayArea = new JTextArea();
 //   displayArea.setEditable(false);
  //  JScrollPane scrollPane = new JScrollPane(displayArea);

    JButton listenButton = new JButton("Listen for Information");
    JButton exitButton = new JButton("Exit to DerRathskellerBarAndGrille");

    // Add action listeners
    listenButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String info = provideInformation();
           // displayArea.append(info + "\n");
            myMainGameScreen.setMessageTextPane(info + "\n");
        }
    });

    exitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose(); // Close the current frame
            JPanel mainPanel = new JPanel(); // Initialize a new main panel
            new DerRathskellerBarAndGrille(mainPanel).setVisible(true); // Navigate back
        }
    });

    // Add components to the frame
 //   add(scrollPane, BorderLayout.CENTER);
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(listenButton);
    buttonPanel.add(exitButton);
    add(buttonPanel, BorderLayout.SOUTH);

    pack(); // Adjust layout based on preferred size
}


    protected String provideInformation() {
        listenCounter++;
        if (listenCounter % 20 == 0) {
            return specialInfo;
        }
        Random random = new Random();
        return randomInfo[random.nextInt(randomInfo.length)];
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
				new InformationProvider().setVisible(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
    }
}
