
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import CharecterClass.Cleric;
import CharecterClass.Paladin;
import CharecterClass.Warrior;
import CharecterClass.Rogue;
import CharecterClass.Hunter;
import CharecterClass.Bard;
import SharedData.GameSettings;
import Race.*;

public class CharacterCreation {

    static LoadSaveGame myGameState = new LoadSaveGame();
    static GameSettings myGameSettings = new GameSettings();
    Charecter myChar = Charecter.Singleton();

    static String InitialCharecterSave = " ";
    static String toonClass, charName = " ";
    static int width, height = 0;
    static Dimension size;
    static File charSave;
    static Scanner saveFile;

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

    // Race selection fields
    static JComboBox<String> raceComboBox;
    static JLabel raceImageLabel;
    static JTextArea raceDescriptionTextArea;
    static String[] raceList = RaceFactory.getClassNamesInPackage("Race", RaceFactory.class);
    static String selectedRace = null;

    static JLabel classImageLabel;
    static BufferedImage ClassImagePicture;

    public CharacterCreation() throws IOException, InterruptedException {}

    public void createCharector() {
        size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) size.getWidth();
        height = (int) size.getHeight();

        CharecterCreationFrame = new JFrame("Create New Charecter");
        CharecterCreationFrame.setSize(width, height);
        CharecterCreationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CharecterCreationFrame.setBackground(myGameSettings.getColorBrown());
        CharecterCreationFrame.setUndecorated(true);

        CharecterCreationSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        CharecterCreationFrame.add(CharecterCreationSplitPane);
        CharecterCreationSplitPane.setDividerLocation(.5);
        CharecterCreationSplitPane.setResizeWeight(.2d);

        toonstatsTextArea = new JTextArea();
        toonclassDescriptionTextArea = new JTextArea();
        tooncreationTextField = new JTextField();
        tooncreationTextField.setEditable(false);

        Font toonClassDescriptionFont = new Font("Verdana", Font.BOLD, 30);
        toonclassDescriptionTextArea.setFont(toonClassDescriptionFont);

        tooncreationTextField.setText("Name: " + charName);
        toonstatsScrollPane = new JScrollPane();

        NameAndStatsPanel = new JPanel(new BorderLayout());
        ClassAndClassInfoPanel = new JPanel(new BorderLayout());
        ClassInfoAndImagePanel = new JPanel(new BorderLayout());
        CharecterCreationSplitPane.setLeftComponent(NameAndStatsPanel);

        // --- RACE SELECTION SETUP ---





String[] raceList = RaceFactory.getClassNamesInPackage("Race", RaceFactory.class);
Arrays.sort(raceList);
raceComboBox = new JComboBox<>(raceList);
raceComboBox.setSelectedItem("Human"); // Default selection
selectedRace = "Human"; // Default
raceComboBox = new JComboBox<>(raceList);

        raceImageLabel = new JLabel();
        raceDescriptionTextArea = new JTextArea("Choose your race.");
        raceDescriptionTextArea.setLineWrap(true);
        raceDescriptionTextArea.setEditable(false);

        // --- CLASS SELECTION SETUP ---
        toonclasslist = CharecterClass.Class.toonclassarray;
        java.util.List<String> toonclassList = Arrays.asList(toonclasslist);
        Collections.sort(toonclassList);
        toonclasslist = toonclassList.toArray(new String[0]);
        charectorClass = new JComboBox<>(toonclasslist);
        charectorClass.setSelectedItem(toonClass);
        charectorClass.setEnabled(false);

        toonclassDescriptionTextArea = new JTextArea("Choose Your Class from the Dropdown box above.");
        toonclassDescriptionTextArea.setLineWrap(true);

        // --- RACE SELECTION LOGIC ---
        raceComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRace = raceComboBox.getSelectedItem().toString();
                try {
                    BufferedImage raceImg = ImageIO.read(new File("src/DungeonoftheBrutalKing/Images/Race/" + selectedRace + ".png"));
                    raceImageLabel.setIcon(new ImageIcon(raceImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                } catch (IOException ex) {
                    raceImageLabel.setIcon(null);
                }
                raceDescriptionTextArea.setText(getRaceDescription(selectedRace));
                charectorClass.setEnabled(true);
                charectorClass.setModel(new DefaultComboBoxModel<>(getClassesForRace(selectedRace)));
            }
        });

        // --- CLASS SELECTION LOGIC ---
        charectorClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toonClass = charectorClass.getSelectedItem() != null ? charectorClass.getSelectedItem().toString() : "";
                StringBuilder info = new StringBuilder();
                info.append("Class: ").append(toonClass).append("\n\n");
                try {
                    switch (toonClass) {
                        case "Paladin":
                            info.append(Paladin.ClassDescription());
                            classImage("Paladin");
                            break;
                        case "Cleric":
                            info.append(Cleric.ClassDescription());
                            classImage("Cleric");
                            break;
                        case "Rogue":
                            info.append(Rogue.ClassDescription());
                            classImage("Rogue");
                            break;
                        case "Hunter":
                            info.append(Hunter.ClassDescription());
                            classImage("Hunter");
                            break;
                        case "Warrior":
                            info.append(Warrior.ClassDescription());
                            classImage("Warrior");
                            break;
                        case "Bard":
                            info.append(Bard.ClassDescription());
                            classImage("Bard");
                            break;
                        default:
                            info.append("No description available.");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                toonclassDescriptionTextArea.setText(info.toString());
            }
        });

        // --- STATS ---
        stat = rollstats();
        toonstatsTextArea = new JTextArea();
        toonstatsScrollPane = new JScrollPane(toonstatsTextArea);
        displayStats(stat);

        reRollStatsButton = new JButton("Reroll Stats");
        reRollStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stat = rollstats();
                displayStats(stat);
            }
        });

        // --- SAVE BUTTON ---
        saveToonButton = new JButton("Save Charecter");
        saveToonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check for race and class selection before saving
                if (selectedRace == null || selectedRace.isEmpty() || toonClass == null || toonClass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select both a race and a class before saving.");
                    return;
                }
                try {
                    FileWriter writer = new FileWriter("src/DungeonoftheBrutalKing/SaveGame/InitialCharecterSave.txt");
                    ArrayList<String> saveData = new ArrayList<>();

                    toonName(tooncreationTextField, charName, saveData);

                    saveData.add(selectedRace);
                    saveData.add(toonClass);
                    saveData.add("0");
                    saveData.add("0");
                    saveData.add(String.valueOf(ToonHP(stat, saveData)));
                    saveData.add(String.valueOf(ToonMP(stat, saveData)));

                    for (Integer s : stat) saveData.add(String.valueOf(s));

                    saveData.add(gold().toString());
                    saveData.add("3");
                    saveData.add("3");
                    saveData.add("3");
                    saveData.add("0");

                    for (String str : saveData) {
                        writer.write(str + System.lineSeparator());
                    }

                    writer.close();
                    CharecterCreationFrame.dispose();
                    new MainGameScreen();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "Error:\n " + e1);
                    e1.printStackTrace();
                } catch (InterruptedException | ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // --- EXIT BUTTON ---
        exitToStartMenuButton = new JButton("Return to Start Menu");
        exitToStartMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CharecterCreationFrame.dispose();
                try {
                    new GameStart();
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel racePanel = new JPanel(new BorderLayout());
        racePanel.add(raceComboBox, BorderLayout.NORTH);
        racePanel.add(raceImageLabel, BorderLayout.CENTER);
        racePanel.add(raceDescriptionTextArea, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(racePanel, BorderLayout.NORTH);
        rightPanel.add(ClassInfoAndImagePanel, BorderLayout.CENTER);
        rightPanel.add(saveToonButton, BorderLayout.SOUTH);

        CharecterCreationSplitPane.setRightComponent(rightPanel);

        NameAndStatsPanel.add(tooncreationTextField, BorderLayout.NORTH);
        NameAndStatsPanel.add(toonstatsTextArea, BorderLayout.CENTER);
        NameAndStatsPanel.add(reRollStatsButton, BorderLayout.SOUTH);

        // After initializing ClassInfoAndImagePanel
        ClassInfoAndImagePanel.add(charectorClass, BorderLayout.NORTH);
        ClassInfoAndImagePanel.add(toonclassDescriptionTextArea, BorderLayout.SOUTH);

        CharecterCreationFrame.setLocationRelativeTo(null);
        CharecterCreationFrame.toFront();
        CharecterCreationFrame.requestFocus();
        CharecterCreationFrame.setVisible(true);

        while (charName == null || charName.trim().isEmpty()) {
            charName = JOptionPane.showInputDialog("Please Enter a Name for Your Character.");
        }
        tooncreationTextField.setText("Name: " + charName);
        new GameMenuItems();
    }

    private void displayStats(Integer[] stat) {
        toonstatsTextArea.setText("Charecter Stats\n");
        toonstatsTextArea.append("\nSTAMINA: \t\t" + stat[0]);
        toonstatsTextArea.append("\nCHARISMA: \t\t" + stat[1]);
        toonstatsTextArea.append("\nSTRENGTH: \t\t" + stat[2]);
        toonstatsTextArea.append("\nINTELLIGENCE: \t" + stat[3]);
        toonstatsTextArea.append("\nWISDOM: \t\t" + stat[4]);
        toonstatsTextArea.append("\nAGILITY: \t\t" + stat[5]);
        toonstatsTextArea.setEditable(false);
    }

    private String getRaceDescription(String race) {
        try {

Class<?> raceClass = Class.forName("Race." + race);

            Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
            java.lang.reflect.Field descField = raceClass.getField("description");
            return (String) descField.get(raceInstance);
        } catch (Exception e) {
            return "No description available.";
        }
    }

    private String[] getClassesForRace(String race) {
        return switch (race) {
            case "Elf" -> new String[]{"Cleric", "Bard", "Hunter"};
            case "Dwarf" -> new String[]{"Warrior", "Paladin"};
            case "Orc" -> new String[]{"Warrior", "Rogue"};
            default -> CharecterClass.Class.toonclassarray;
        };
    }

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

    public static Integer[] rollstats() {
        int range = 20;
        int lowerbound = 10;
        Integer[] stats = new Integer[6];
        for (int i = 0; i < stats.length; i++) {
            stats[i] = (int) (Math.random() * range) + lowerbound;
        }
        return stats;
    }

    public Integer ToonHP(Integer stat[], ArrayList<String> newChar) {
        if (newChar.size() < 3 || newChar.get(2) == null) return 0;
        String Class = newChar.get(2);
        int baseHP = switch (Class) {
            case "Paladin", "Warrior" -> 2;
            default -> 1;
        };
        return baseHP * ((stat[2] * 2) + stat[0]);
    }

    public int ToonMP(Integer[] stat, ArrayList<String> newChar) {
        if (newChar.size() < 3 || newChar.get(2) == null) return 0;
        String characterClass = newChar.get(2);
        int points;
        if (isMagicUser(characterClass)) {
            points = calculateMagicPoints(stat, characterClass);
        } else {
            points = ToonActionPoints(stat);
        }
        return points;
    }

    boolean isMagicUser(String characterClass) {
        return Arrays.asList("Cleric", "Paladin", "Bard").contains(characterClass);
    }

    private int calculateMagicPoints(Integer[] stat, String characterClass) {
        int baseMP = switch (characterClass) {
            case "Paladin" -> 14;
            case "Cleric" -> 20;
            case "Bard" -> 12;
            default -> 1;
        };
        return baseMP + ((stat[3] * 2) + stat[4]);
    }

    public int ToonActionPoints(Integer[] stat) {
        return (stat[2] * 2) + stat[5];
    }

    public Integer gold() {
        Random random = new Random();
        int min = 50;
        int max = 100;
        return random.nextInt(max - min + 1) + min;
    }

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
