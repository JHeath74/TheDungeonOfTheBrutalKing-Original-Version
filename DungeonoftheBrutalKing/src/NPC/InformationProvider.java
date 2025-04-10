
package NPC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class InformationProvider extends JFrame {
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

    public InformationProvider() {
        // Set up the frame
        setTitle("Information Listener");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JButton listenButton = new JButton("Listen for Information");
        JButton exitButton = new JButton("Exit to DerRathskellerBarAndGrille");

        // Add action listeners
        listenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String info = provideInformation();
                displayArea.append(info + "\n");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current frame
                new DerRathskellerBarAndGrille(null, displayArea).setVisible(true); // Navigate back
            }
        });

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(listenButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
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
            new InformationProvider().setVisible(true);
        });
    }
}
