
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import Classes.Bard;
import Classes.Cleric;
import Classes.Hunter;
import Classes.Paladin;
import Classes.Rogue;
import Classes.Warrior;
import Classes.Wizard;
import Races.RaceEnum;
import SharedData.GameSettings;

public class CharacterCreation {

    static LoadSaveGame myGameState = new LoadSaveGame();
    static GameSettings myGameSettings = new GameSettings();
    Character myChar = Character.getInstance();

    static String InitialCharecterSave = " ";
    static String toonClass, charName = " ";
    static int width, height = 0;
    static Dimension size;
    static File charSave;
    static Scanner saveFile;

    static JFrame CharecterCreationFrame;
    static JPanel NameAndStatsPanel, ClassAndClassInfoPanel, ClassInfoAndImagePanel, racePanel;
    static JTextArea toonstatsTextArea, toonclassDescriptionTextArea;
    static JTextField tooncreationTextField;
    static JScrollPane toonstatsScrollPane;
    static JButton reRollStatsButton, saveToonButton, exitToStartMenuButton;
    static JSplitPane CharecterCreationSplitPane;
    static JComboBox<String> charectorClass;
    static String[] toonclasslist;
    static Integer[] stat;

    static JComboBox<String> raceComboBox;
    static JLabel raceImageLabel;
    static JTextArea raceDescriptionTextArea;
    static String selectedRace = null;

    static JLabel classImageLabel;
    static BufferedImage ClassImagePicture;
    private static JPanel classImagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private static final Map<String, Class<?>> classMap = Map.of(
         "Paladin", Paladin.class,
         "Cleric", Cleric.class,
         "Rogue", Rogue.class,
         "Hunter", Hunter.class,
         "Warrior", Warrior.class,
         "Bard", Bard.class,
         "Wizard", Wizard.class
     );

    public CharacterCreation() throws IOException, InterruptedException {}

    private int[] setAndCalculateStats(Integer[] stat, String armour, String shield, String weapon) {
        myChar.setAgility(stat[5]);
        myChar.setStrength(stat[2]);
        myChar.setArmour(armour);
        myChar.setShield(shield);
        myChar.setWeapon(weapon);
        myChar.calculateAndSetDefense();
        myChar.calculateAndSetAttack();
        int defense = myChar.getDefense();
        int attack = myChar.getAttackDamage();
        return new int[] { defense, attack };
    }

    public void createCharector() {
        while (charName == null || charName.trim().isEmpty()) {
            charName = JOptionPane.showInputDialog("Please Enter a Name for Your Character.");
        }

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

        String[] raceList = Arrays.stream(RaceEnum.values())
                .map(RaceEnum::name)
                .toArray(String[]::new);
        Arrays.sort(raceList);
        raceComboBox = new JComboBox<>(raceList);
        raceComboBox.setSelectedItem("Human");
        selectedRace = "Human";

        raceImageLabel = new JLabel();
        raceDescriptionTextArea = new JTextArea("Choose your race.");
        raceDescriptionTextArea.setLineWrap(true);
        raceDescriptionTextArea.setWrapStyleWord(true);
        raceDescriptionTextArea.setEditable(false);
        raceDescriptionTextArea.setColumns(60);
        raceDescriptionTextArea.setRows(10);

        JScrollPane raceDescriptionScrollPane = new JScrollPane(raceDescriptionTextArea);
        int lineHeight = raceDescriptionTextArea.getFontMetrics(raceDescriptionTextArea.getFont()).getHeight();
        raceDescriptionScrollPane.setPreferredSize(new Dimension(800, lineHeight * 12));

        racePanel = new JPanel(new BorderLayout());
        racePanel.add(raceComboBox, BorderLayout.NORTH);
        JPanel raceImagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        raceImagePanel.add(raceImageLabel);
        racePanel.add(raceImagePanel, BorderLayout.CENTER);
        racePanel.add(raceDescriptionScrollPane, BorderLayout.SOUTH);

        toonclasslist = Classes.Class.toonclassarray;
        java.util.List<String> toonclassList = Arrays.asList(toonclasslist);
        Collections.sort(toonclassList);
        toonclasslist = toonclassList.toArray(new String[0]);
        charectorClass = new JComboBox<>(toonclasslist);
        charectorClass.setSelectedItem(toonClass);
        charectorClass.setEnabled(false);

        toonclassDescriptionTextArea = new JTextArea("Choose Your Class from the Dropdown box above.");
        toonclassDescriptionTextArea.setLineWrap(true);

        raceComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRace = raceComboBox.getSelectedItem().toString();
                String imagePath = CharacterCreation.getRaceImagePath(selectedRace);
                if (imagePath != null) {
                    try {
                        BufferedImage raceImg = ImageIO.read(new File(imagePath));
                        raceImageLabel.setIcon(new ImageIcon(raceImg.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                    } catch (IOException ex) {
                        raceImageLabel.setIcon(null);
                    }
                } else {
                    raceImageLabel.setIcon(null);
                }
                raceDescriptionTextArea.setText(getRaceDescription(selectedRace));
                charectorClass.setEnabled(true);
                charectorClass.setModel(new DefaultComboBoxModel<>(getClassesForRace(selectedRace)));
            }
        });

        charectorClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                toonClass = charectorClass.getSelectedItem() != null ? charectorClass.getSelectedItem().toString() : "";
                StringBuilder info = new StringBuilder();
                info.append("Class: ").append(toonClass).append("\n\n");
                Class<?> clazz = classMap.get(toonClass);
                String imageName = toonClass;
                if (clazz != null) {
                    try {
                        String desc = (String) clazz.getMethod("ClassDescription").invoke(null);
                        info.append(desc);
                        classImage(imageName);
                    } catch (Exception ex) {
                        info.append("No description available.");
                    }
                } else {
                    info.append("No description available.");
                }
                toonclassDescriptionTextArea.setText(info.toString());
            }
        });

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

        saveToonButton = new JButton("Save Charecter");
        saveToonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRace == null || selectedRace.isEmpty() || toonClass == null || toonClass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select both a race and a class before saving.");
                    return;
                }
                File saveDir = new File("src/DungeonoftheBrutalKing/SaveGame");
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                ArrayList<String> saveData = new ArrayList<>();
                saveData.add(charName);
                saveData.add(toonClass);
                saveData.add(selectedRace);
                saveData.add("1");
                saveData.add("0");
                saveData.add(String.valueOf(ToonHP(stat, saveData)));
                saveData.add(String.valueOf(ToonMP(stat, saveData)));
                saveData.add(String.valueOf(stat[0]));
                saveData.add(String.valueOf(stat[1]));
                saveData.add(String.valueOf(stat[2]));
                saveData.add(String.valueOf(stat[3]));
                saveData.add(String.valueOf(stat[4]));
                saveData.add(String.valueOf(stat[5]));
                saveData.add(gold().toString());
                saveData.add("3");
                saveData.add("3");
                saveData.add("3");
                saveData.add("0");
                saveData.add(myChar.getWeapon() != null ? myChar.getWeapon() : "None");
                saveData.add(myChar.getArmour() != null ? myChar.getArmour() : "None");
                saveData.add(myChar.getShield() != null ? myChar.getShield() : "None");
                saveData.add("0");
                saveData.add("2");
                saveData.add("3");
                saveData.add("1");
                saveData.add("180.0");
                int[] results = setAndCalculateStats(stat, "", "", "");
                int defense = results[0];
                int attack = results[1];
                saveData.add(String.valueOf(defense));
                saveData.add(String.valueOf(attack));
                saveData.add(String.valueOf(ToonHP(stat, saveData)));

                System.out.println("Saving data: " + saveData);
                myGameState.setCharecterData(saveData);

                try (FileWriter writer = new FileWriter("src/DungeonoftheBrutalKing/SaveGame/InitialCharecterSave.txt")) {
                    myGameState.saveAllEncrypted(writer);
                    CharecterCreationFrame.dispose();
                    MainGameScreen.getInstance();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Error saving character:\n" + e1.getMessage());
                    e1.printStackTrace();
                }

            }
        });

        exitToStartMenuButton = new JButton("Return to Start Menu");
        exitToStartMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CharecterCreationFrame.dispose();
                try {
                    GameStart startMenu = new GameStart();
                    JFrame startMenuFrame = startMenu.getStartMenuFrame();
                    if (startMenuFrame != null) {
                        startMenuFrame.setVisible(true);
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveToonButton);
        buttonPanel.add(exitToStartMenuButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(racePanel, BorderLayout.NORTH);
        rightPanel.add(ClassInfoAndImagePanel, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        CharecterCreationSplitPane.setRightComponent(rightPanel);

        NameAndStatsPanel.add(tooncreationTextField, BorderLayout.NORTH);
        NameAndStatsPanel.add(toonstatsTextArea, BorderLayout.CENTER);
        NameAndStatsPanel.add(reRollStatsButton, BorderLayout.SOUTH);

        ClassInfoAndImagePanel.add(charectorClass, BorderLayout.NORTH);
        ClassInfoAndImagePanel.add(toonclassDescriptionTextArea, BorderLayout.SOUTH);

        CharecterCreationFrame.setLocationRelativeTo(null);
        CharecterCreationFrame.toFront();
        CharecterCreationFrame.requestFocus();
        CharecterCreationFrame.setVisible(true);

        tooncreationTextField.setText("Name: " + charName);
        new GameMenuItems();
    }

    public static String getRaceImagePath(String race) {
        try {
            Class<?> raceClass = Class.forName("Races." + race);
            Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
            return (String) raceClass.getMethod("getRaceImagePath").invoke(raceInstance);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getRaceDescription(String race) {
        try {
            Class<?> raceClass = Class.forName("Races." + race);
            Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
            return (String) raceClass.getMethod("getRaceDescription").invoke(raceInstance);
        } catch (Exception e) {
            return "No description available.";
        }
    }

    private static String[] getClassesForRace(String race) {
        try {
            Class<?> raceClass = Class.forName("Races." + race);
            Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
            java.util.List<?> allowedRaw = (java.util.List<?>) raceClass.getMethod("getAllowedClasses").invoke(raceInstance);
            java.util.List<String> allowed = new java.util.ArrayList<>();
            for (Object o : allowedRaw) {
                allowed.add((String) o);
            }
            return allowed.toArray(new String[0]);
        } catch (Exception e) {
            return Classes.Class.toonclassarray;
        }
    }

    private static void displayStats(Integer[] stat) {
        toonstatsTextArea.setText("Charecter Stats\n");
        toonstatsTextArea.append("\nSTAMINA: \t\t" + stat[0]);
        toonstatsTextArea.append("\nCHARISMA: \t\t" + stat[1]);
        toonstatsTextArea.append("\nSTRENGTH: \t\t" + stat[2]);
        toonstatsTextArea.append("\nINTELLIGENCE: \t" + stat[3]);
        toonstatsTextArea.append("\nWISDOM: \t\t" + stat[4]);
        toonstatsTextArea.append("\nAGILITY: \t\t" + stat[5]);
        toonstatsTextArea.setEditable(false);
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

    public static Integer ToonHP(Integer stat[], ArrayList<String> newChar) {
        if (newChar.size() < 3 || newChar.get(2) == null) {
            return 0;
        }
        String Class = newChar.get(2);
        int baseHP = switch (Class) {
            case "Paladin", "Warrior" -> 2;
            default -> 1;
        };
        return baseHP * ((stat[2] * 2) + stat[0]);
    }

    public static int ToonMP(Integer[] stat, ArrayList<String> newChar) {
        if (newChar.size() < 3 || newChar.get(2) == null) {
            return 0;
        }
        String characterClass = newChar.get(2);
        int points;
        if (isMagicUser(characterClass)) {
            points = calculateMagicPoints(stat, characterClass);
        } else {
            points = ToonActionPoints(stat);
        }
        return points;
    }

    static boolean isMagicUser(String characterClass) {
        return Arrays.asList("Cleric", "Paladin", "Bard").contains(characterClass);
    }

    private static int calculateMagicPoints(Integer[] stat, String characterClass) {
        int baseMP = switch (characterClass) {
            case "Paladin" -> 14;
            case "Cleric" -> 20;
            case "Bard" -> 12;
            default -> 1;
        };
        return baseMP + ((stat[3] * 2) + stat[4]);
    }

    public static int ToonActionPoints(Integer[] stat) {
        return (stat[2] * 2) + stat[5];
    }

    public static Integer gold() {
        Random random = new Random();
        int min = 50;
        int max = 100;
        return random.nextInt(max - min + 1) + min;
    }

    private static void classImage(String classImage) throws IOException {
        classImagePanel.removeAll();
        ClassImagePicture = ImageIO.read(new File(GameSettings.ClassImagesPath + classImage + ".png"));
        classImageLabel = new JLabel();
        int panelWidth = classImagePanel.getWidth() > 0 ? classImagePanel.getWidth() : 640;
        int panelHeight = classImagePanel.getHeight() > 0 ? classImagePanel.getHeight() : 480;
        Image scaledImage = ClassImagePicture.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);
        ImageIcon img = new ImageIcon(scaledImage);
        classImageLabel.setIcon(img);
        classImagePanel.add(classImageLabel);
        ClassInfoAndImagePanel.add(classImagePanel, BorderLayout.CENTER);
        classImagePanel.revalidate();
        classImagePanel.repaint();
    }
}
