
// src/DungeonoftheBrutalKing/CharacterCreation.java
package DungeonoftheBrutalKing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import CharecterClass.Bard;
import CharecterClass.Class;
import CharecterClass.Cleric;
import CharecterClass.Hunter;
import CharecterClass.Paladin;
import CharecterClass.Rogue;
import CharecterClass.Warrior;

// Class responsible for character creation functionality
public class CharacterCreation {

    // Static instances for game state and settings
    static LoadSaveGame myGameState = new LoadSaveGame();
    static GameSettings myGameSettings = new GameSettings();

    // Singleton instance for the character
    Charecter myChar = Charecter.Singleton();

    // Static variables for character details
    static String InitialCharecterSave = " ";
    static String toonClass, charName = " ";
    static int width, height = 0;
    static Dimension size;
    static File charSave;
    static Scanner saveFile;

    // GUI components for character creation
    static JFrame CharecterCreationFrame;
    static JPanel NameAndStatsPanel, ClassAndClassInfoPanel, ClassInfoAndImagePanel;
    static JTextArea toonstatsTextArea, toonclassDescriptionTextArea;
    static JTextField tooncreationTextField;
    static JScrollPane toonstatsScrollPane;
    static JButton reRollStatsButton, saveToonButton, exitToStartMenuButton;
    static JSplitPane CharecterCreationSplitPane;
    static JComboBox<String> charectorClass;
    static String[] toonclasslist;
    static Integer[] stat;

    // Lists to store character data
    ArrayList<String> newChar = new ArrayList<>();
    ArrayList<String> newChar2 = new ArrayList<>();

    // Label and image for class display
    static JLabel classImageLabel;
    static BufferedImage ClassImagePicture;

    // Constructor for initializing character creation
    public CharacterCreation() throws IOException, InterruptedException {
        // Empty constructor for setup
    }

    // Method to create a new character
    public void createCharector() {
        GameSettings myGameSettings = new GameSettings();

        // Get screen dimensions
        size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) size.getWidth();
        height = (int) size.getHeight();

        // Setup JFrame for character creation
        CharecterCreationFrame = new JFrame("Create New Charecter");
        CharecterCreationFrame.setSize(width, height);
        CharecterCreationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CharecterCreationFrame.setBackground(myGameSettings.colorBrown);
        CharecterCreationFrame.setUndecorated(true);

        // Setup JSplitPane for layout
        CharecterCreationSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        CharecterCreationFrame.add(CharecterCreationSplitPane);
        CharecterCreationSplitPane.setDividerLocation(.5);
        CharecterCreationSplitPane.setResizeWeight(.2d);

        // Initialize text areas and text field
        toonstatsTextArea = new JTextArea();
        toonclassDescriptionTextArea = new JTextArea();
        tooncreationTextField = new JTextField();
        tooncreationTextField.setEditable(false);

        // Set font for class description
        Font toonClassDescriptionFont = new Font("Verdana", Font.BOLD, 30);
        toonclassDescriptionTextArea.setFont(toonClassDescriptionFont);

        // Set default text for character name
        tooncreationTextField.setText("Name: " + charName);
        toonstatsScrollPane = new JScrollPane();

        // Setup panels and add them to the split pane
        NameAndStatsPanel = new JPanel(new BorderLayout());
        ClassAndClassInfoPanel = new JPanel(new BorderLayout());
        ClassInfoAndImagePanel = new JPanel(new BorderLayout());
        CharecterCreationSplitPane.setLeftComponent(NameAndStatsPanel);
        CharecterCreationSplitPane.setRightComponent(ClassAndClassInfoPanel);

        // Initialize buttons
        exitToStartMenuButton = new JButton();

        // Roll initial stats for the character
        stat = rollstats();
        toonstatsTextArea = new JTextArea();
        toonstatsScrollPane = new JScrollPane(toonstatsTextArea);

        // Display character stats
        toonstatsTextArea.setText("Charecter Stats\n");
        toonstatsTextArea.append("\nSTAMINA: \t\t" + stat[0]);
        toonstatsTextArea.append("\nCHARISMA: \t\t" + stat[1]);
        toonstatsTextArea.append("\nSTRENGTH: \t\t" + stat[2]);
        toonstatsTextArea.append("\nINTELLIGENCE: \t" + stat[3]);
        toonstatsTextArea.append("\nWISDOM: \t\t" + stat[4]);
        toonstatsTextArea.append("\nAGILITY: \t\t" + stat[5]);
        toonstatsTextArea.setEditable(false);

        // Setup character class dropdown
        toonclasslist = Class.toonclassarray;
       // List<String> toonclassList = Arrays.asList(toonclasslist);
        java.util.List<String> toonclassList = Arrays.asList(toonclasslist);
        Collections.sort(toonclassList);
        toonclasslist = toonclassList.toArray(new String[0]);
        charectorClass = new JComboBox<>(toonclasslist);
        charectorClass.setSelectedItem(toonClass);

        // Set default class description
        toonclassDescriptionTextArea = new JTextArea("Choose Your Class from the Dropdown box above.");
        toonclassDescriptionTextArea.setLineWrap(true);

        // Add action listener for class selection
        charectorClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toonClass = charectorClass.getSelectedItem().toString();
                toonclassDescriptionTextArea.setText(toonClass);

                // Update class description and image based on selection
                try {
                    if (toonClass.equals("Paladin")) {
                        toonclassDescriptionTextArea.setText(Paladin.ClassDescription());
                        classImage("Paladin");
                    } else if (toonClass.equals("Cleric")) {
                        toonclassDescriptionTextArea.setText(Cleric.ClassDescription());
                        classImage("Cleric");
                    } else if (toonClass.equals("Rogue")) {
                        toonclassDescriptionTextArea.setText(Rogue.ClassDescription());
                        classImage("Rogue");
                    } else if (toonClass.equals("Hunter")) {
                        toonclassDescriptionTextArea.setText(Hunter.ClassDescription());
                        classImage("Hunter");
                    } else if (toonClass.equals("Warrior")) {
                        toonclassDescriptionTextArea.setText(Warrior.ClassDescription());
                        classImage("Warrior");
                    } else if (toonClass.equals("Bard")) {
                        toonclassDescriptionTextArea.setText(Bard.ClassDescription());
                        classImage("Bard");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Add action listener for rerolling stats
        reRollStatsButton = new JButton("Reroll Stats");
        reRollStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer[] stat = rollstats();
                toonstatsTextArea.setText("Charecter Stats\n");
                toonstatsTextArea.append("\nSTAMINA: \t\t" + stat[0]);
                toonstatsTextArea.append("\nCHARISMA: \t\t" + stat[1]);
                toonstatsTextArea.append("\nSTRENGTH: \t\t" + stat[2]);
                toonstatsTextArea.append("\nINTELLIGENCE: \t" + stat[3]);
                toonstatsTextArea.append("\nWISDOM: \t\t" + stat[4]);
                toonstatsTextArea.append("\nAGILITY: \t\t" + stat[5]);
            }
        });

        // Add action listener for saving the character
        saveToonButton = new JButton("Save Charecter");
        saveToonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter writer = new FileWriter("src/DungeonoftheBrutalKing/SaveGame/InitialCharecterSave.txt");
                    toonName(tooncreationTextField, charName, newChar);

                    // Add character details to the save file
                    newChar.add(toonClass);
                    newChar.add("0"); // Level
                    newChar.add("0"); // Experience
                    newChar.add(String.valueOf(ToonHP(stat, newChar))); // Hit Points
                    newChar.add(String.valueOf(ToonMP(stat, newChar))); // Magic Points
                    newChar2.add(gold().toString()); // Gold
                    newChar2.add("3"); // Food
                    newChar2.add("3"); // Water
                    newChar2.add("3"); // Torches
                    newChar2.add("0"); // Gems

                    // Write character data to the file
                    for (String str : newChar) {
                        writer.write(str + System.lineSeparator());
                    }
                    for (Integer str2 : stat) {
                        writer.write(str2 + System.lineSeparator());
                    }
                    for (String str3 : newChar2) {
                        writer.write(str3 + System.lineSeparator());
                    }

                    writer.close();
                    CharecterCreationFrame.dispose();
                    new MainGameScreen();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error:\n " + e1);
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        // Add action listener for returning to the start menu
        exitToStartMenuButton = new JButton("Return to Start Menu");
        exitToStartMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CharecterCreationFrame.dispose();
                new GameStart();
            }
        });

        // Add components to panels
        JLabel classImage = new JLabel();
        NameAndStatsPanel.add(tooncreationTextField, BorderLayout.NORTH);
        NameAndStatsPanel.add(toonstatsTextArea, BorderLayout.CENTER);
        NameAndStatsPanel.add(reRollStatsButton, BorderLayout.SOUTH);
        ClassAndClassInfoPanel.add(charectorClass, BorderLayout.NORTH);
        ClassAndClassInfoPanel.add(ClassInfoAndImagePanel, BorderLayout.CENTER);
        ClassInfoAndImagePanel.add(toonclassDescriptionTextArea, BorderLayout.SOUTH);
        ClassInfoAndImagePanel.add(classImage, BorderLayout.NORTH);
        ClassAndClassInfoPanel.add(saveToonButton, BorderLayout.SOUTH);

        // Finalize frame setup
        CharecterCreationFrame.setLocationRelativeTo(null);
        CharecterCreationFrame.toFront();
        CharecterCreationFrame.requestFocus();
        CharecterCreationFrame.setVisible(true);

        // Prompt for character name if not set
        while (charName == null || charName.trim().isEmpty()) {
            charName = JOptionPane.showInputDialog("Please Enter a Name for Your Character.");
        }
        tooncreationTextField.setText("Name: " + charName);
        new GameMenuItems();
    }

    // Method to validate and set character name
    public static void toonName(JTextField tooncreation, String charName, ArrayList<String> newChar) {
        boolean inputAccepted = false;
        while (!inputAccepted) {
            if (charName.equals("") || charName.equals("Please Enter a User Name.") || charName.equals(" ")) {
                charName = JOptionPane.showInputDialog("Please Enter a Name for Your Charater.");
            } else {
                if (charName.matches("^[A-Za-z]\\w{3,29}$")) {
                    tooncreation.setText(charName);
                    inputAccepted = true;
                    newChar.add(charName);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Username must be 4 to 30 charecters long and consist of Numbers and Letters",
                            "Invalid UserName", JOptionPane.INFORMATION_MESSAGE);
                    toonName(tooncreation, charName, newChar);
                }
            }
        }
    }

    // Method to roll random stats for the character
    public static Integer[] rollstats() {
        int range = 20;
        int lowerbound = 10;
        Integer[] stats = new Integer[6];
        for (int i = 0; i < stats.length; i++) {
            stats[i] = (int) (Math.random() * range) + lowerbound;
        }
        return stats;
    }

    // Method to calculate character hit points based on stats and class
    public Integer ToonHP(Integer stat[], ArrayList<String> newChar) {
        String Class = newChar.get(1);
        int baseHP = 0;
        switch (Class) {
            case "Paladin":
                baseHP = 2;
                break;
            case "Cleric":
                baseHP = 1;
                break;
            case "Rogue":
                baseHP = 1;
                break;
            case "Hunter":
                baseHP = 1;
                break;
            case "Warrior":
                baseHP = 2;
                break;
            case "Bard":
                baseHP = 1;
                break;
            default:
                baseHP = 1;
                break;
        }
        return baseHP * ((stat[2] * 2) + stat[0]);
    }


 // Method to calculate character magic points or action points based on stats and class
 public int ToonMP(Integer[] stat, ArrayList<String> newChar) {
     String characterClass = newChar.get(1); // Get the character's class
     int points;

     if (isMagicUser(characterClass)) {
         points = calculateMagicPoints(stat, characterClass); // Assign magic points
     } else {
         points = ToonActionPoints(stat); // Assign action points
     }

     return points;
 }

 // Helper method to check if the class is a magic user
 boolean isMagicUser(String characterClass) {
     return Arrays.asList("Cleric", "Paladin", "Bard").contains(characterClass);
 }

 // Method to calculate magic points for magic users
 private int calculateMagicPoints(Integer[] stat, String characterClass) {
     int baseMP = switch (characterClass) {
         case "Paladin" -> 14;
         case "Cleric" -> 20;
         case "Bard" -> 12;
         default -> 1;
     };
     return baseMP + ((stat[3] * 2) + stat[4]);
 }

 // Method to calculate action points for non-magic users
 public int ToonActionPoints(Integer[] stat) {
     return (stat[2] * 2) + stat[5]; // Example formula for action points
 }


    // Method to generate random gold for the character
    public Integer gold() {
        Random random = new Random();
        int min = 50;
        int max = 100;
        return random.nextInt(max - min + 1) + min;
    }

    // Method to set the class image based on the selected class
    private static void classImage(String classImage) throws IOException {
        if (classImageLabel != null) {
            ClassInfoAndImagePanel.remove(classImageLabel);
        }
        ClassImagePicture = ImageIO.read(new File(GameSettings.ClassImagesPath + classImage + ".png"));
        classImageLabel = new JLabel();
        classImageLabel.setSize(ClassInfoAndImagePanel.getWidth(), ClassInfoAndImagePanel.getHeight());
        Image newClassImagePicture = ClassImagePicture.getScaledInstance(640, 480, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(newClassImagePicture);
        classImageLabel.setIcon(img);
        ClassInfoAndImagePanel.add(classImageLabel);
        classImageLabel.revalidate();
    }
}
