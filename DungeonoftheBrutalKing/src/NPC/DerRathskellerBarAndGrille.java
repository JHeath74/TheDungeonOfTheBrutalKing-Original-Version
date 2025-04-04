
// src/NPC/DerRathskellerBarAndGrille.java
package NPC;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DerRathskellerBarAndGrille extends JFrame {
    private JPanel mainPanel;
    private JTextArea displayArea;
    private Inn inn;

    public DerRathskellerBarAndGrille() {
        inn = new Inn();
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

        displayImage();
        playSound();
        promptWhereToSit();
    }

    public static void main(String[] args) {
        new DerRathskellerBarAndGrille();
    }

    private void displayImage() {
        ImageIcon icon = new ImageIcon("path/to/your/image.png");
        JLabel label = new JLabel(icon);
        JOptionPane.showMessageDialog(null, label, "Welcome to DerRathskellerBarAndGrille", JOptionPane.PLAIN_MESSAGE);
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
        String[] options = {"Sit at the bar", "Sit at a table", "Leave the inn"};
        int choice = JOptionPane.showOptionDialog(null, "Where would you like to sit?", "Choose an option",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                promptBarOptions();
                break;
            case 1:
                displayArea.append("You sit at a table.\n");
                break;
            case 2:
                displayArea.append("You leave the inn.\n");
                break;
            default:
                break;
        }
    }

    private void promptBarOptions() {
        JFrame barFrame = new JFrame("Bar Options");
        barFrame.setSize(300, 200);
        barFrame.setLayout(new GridLayout(4, 1));

        JButton eatButton = new JButton("Buy something to eat");
        JButton drinkButton = new JButton("Buy something to drink");
        JButton talkButton = new JButton("Talk to the bartender");
        JButton exitButton = new JButton("Exit to previous prompt");

        Inn.Innkeeper innkeeper = inn.getInnkeeper();

        eatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("Innkeeper: " + innkeeper.sellItem("Food") + "\n");
                barFrame.dispose();
                promptBarOptions();
            }
        });

        drinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("Innkeeper: " + innkeeper.sellItem("Drink") + "\n");
                barFrame.dispose();
                promptBarOptions();
            }
        });

        talkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("Innkeeper: " + innkeeper.sellItem("Information") + "\n");
                barFrame.dispose();
                promptInformationProviderOptions();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                barFrame.dispose();
                promptWhereToSit();
            }
        });

        barFrame.add(eatButton);
        barFrame.add(drinkButton);
        barFrame.add(talkButton);
        barFrame.add(exitButton);

        barFrame.setVisible(true);
    }

    private void promptInformationProviderOptions() {
        JFrame infoFrame = new JFrame("Information Provider Options");
        infoFrame.setSize(300, 200);
        infoFrame.setLayout(new GridLayout(2, 1));

        JButton listenAgainButton = new JButton("Listen again");
        JButton exitButton = new JButton("Exit to previous screen");

        listenAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.append("Information Provider: " + inn.getInformationProvider().provideInformation() + "\n");
                infoFrame.dispose();
                promptInformationProviderOptions();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoFrame.dispose();
                promptBarOptions();
            }
        });

        infoFrame.add(listenAgainButton);
        infoFrame.add(exitButton);

        infoFrame.setVisible(true);
    }

    public class Inn {
        private Innkeeper innkeeper;
        private InformationProvider informationProvider;

        public Inn() {
            innkeeper = new Innkeeper();
            informationProvider = new InformationProvider();
        }

        public Innkeeper getInnkeeper() {
            return innkeeper;
        }

        public InformationProvider getInformationProvider() {
            return informationProvider;
        }

        public class Innkeeper {
            private List<String> itemsForSale;

            public Innkeeper() {
                itemsForSale = Arrays.asList("Food", "Drink", "Information");
            }

            public List<String> getItemsForSale() {
                return itemsForSale;
            }

            public String sellItem(String item) {
                if (itemsForSale.contains(item)) {
                    return "You bought " + item;
                } else {
                    return "Item not available";
                }
            }
        }

        public class InformationProvider {
            public String provideInformation() {
                return "Here is some information.";
            }
        }
    }
}
