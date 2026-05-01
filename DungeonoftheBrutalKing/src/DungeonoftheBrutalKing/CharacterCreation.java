
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import DungeonoftheBrutalKing.Classes.*;
import DungeonoftheBrutalKing.Races.RaceEnum;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.Stat;

public class CharacterCreation {

    static LoadSaveGame myGameState = new LoadSaveGame();
    static GameSettings myGameSettings = GameSettings.getInstance();
    Character myChar = Character.getInstance();

    static String InitialCharacterSave = "";
    String toonClass = "";
    String charName = "";
    static int width = 0, height = 0;
    static Dimension size;
    static File charSave;
    static Scanner saveFile;

    static JFrame CharacterCreationFrame;
    static JPanel NameAndStatsPanel, ClassAndClassInfoPanel, ClassInfoAndImagePanel, racePanel;
    static JTextArea toonstatsTextArea, toonclassDescriptionTextArea;
    static JTextField tooncreationTextField;
    static JScrollPane toonstatsScrollPane;
    static JButton reRollStatsButton, saveToonButton, exitToStartMenuButton;
    static JSplitPane CharacterCreationSplitPane;
    static JComboBox<String> characterClass;
    static String[] toonclasslist;
    static Integer[] stat;

    static JComboBox<String> raceComboBox;
    static JLabel raceImageLabel;
    static JTextArea raceDescriptionTextArea;
    static String selectedRace = null;

    static JLabel classImageLabel;
    static BufferedImage ClassImagePicture;
    private static JPanel classImagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private static final int STAT_STAMINA = 0;
    private static final int STAT_CHARISMA = 1;
    private static final int STAT_STRENGTH = 2;
    private static final int STAT_INTELLIGENCE = 3;
    private static final int STAT_WISDOM = 4;
    private static final int STAT_AGILITY = 5;
    private static final int STAT_VITALITY = 6;

    private static final String DEFAULT_STARTING_AMOUNT = "3";

    private static final Map<String, java.lang.Class<?>> classMap = Map.ofEntries(
            Map.entry("Bard", Bard.class),
            Map.entry("Cleric", Cleric.class),
            Map.entry("Hunter", Hunter.class),
            Map.entry("Mage", Mage.class),
            Map.entry("Minstrel", Ministrel.class),
            Map.entry("Paladin", Paladin.class),
            Map.entry("Ranger", Ranger.class),
            Map.entry("Rogue", Rogue.class),
            Map.entry("Thief", Thief.class),
            Map.entry("Warrior", Warrior.class),
            Map.entry("Wizard", Wizard.class)
    );

    public CharacterCreation() throws IOException, InterruptedException {}

    private int[] setAndCalculateStats(Integer[] stat, Object weapon, Object armour) {
        myChar.setAgility(stat[STAT_AGILITY]);
        myChar.setStrength(stat[STAT_STRENGTH]);
        try {
            myChar.getClass().getMethod("setVitality", int.class).invoke(myChar, stat[STAT_VITALITY]);
        } catch (Exception ignored) {}
        myChar.calculateAndSetDefense();
        myChar.calculateAndSetAttack();
        int defense = myChar.getDefense();
        int attack = myChar.getAttackDamage();
        return new int[]{defense, attack};
    }

    public void createCharacter() {
        while (charName == null || charName.trim().isEmpty()) {
            charName = JOptionPane.showInputDialog("Please Enter a Name for Your Character.");
        }
        size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) size.getWidth();
        height = (int) size.getHeight();

        CharacterCreationFrame = new JFrame("Create New Character");
        CharacterCreationFrame.setSize(width, height);
        CharacterCreationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CharacterCreationFrame.setBackground(myGameSettings.getColorBrown());
        CharacterCreationFrame.setUndecorated(true);

        CharacterCreationSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        CharacterCreationFrame.add(CharacterCreationSplitPane);
        CharacterCreationSplitPane.setResizeWeight(.2d);

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
        CharacterCreationSplitPane.setLeftComponent(NameAndStatsPanel);

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

        toonclasslist = DungeonoftheBrutalKing.Classes.Class.toonclassarray;
        java.util.List<String> toonclassList = Arrays.asList(toonclasslist);
        Collections.sort(toonclassList);
        toonclasslist = toonclassList.toArray(new String[0]);
        characterClass = new JComboBox<>(toonclasslist);
        characterClass.setSelectedItem(toonClass);
        characterClass.setEnabled(false);

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
                characterClass.setEnabled(true);
                characterClass.setModel(new DefaultComboBoxModel<>(getClassesForRace(selectedRace)));
            }
        });

        characterClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                toonClass = characterClass.getSelectedItem() != null ? characterClass.getSelectedItem().toString() : "";
                displayStats(stat, toonClass);
                StringBuilder info = new StringBuilder();
                info.append("Class: ").append(toonClass).append("\n\n");
                java.lang.Class<?> clazz = classMap.get(toonClass);
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
        displayStats(stat, toonClass);

        reRollStatsButton = new JButton("Reroll Stats");
        reRollStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stat = rollstats();
                displayStats(stat, toonClass);
            }
        });

        saveToonButton = new JButton("Save Character");
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
                saveData.add("0");
                saveData.add("0");
                saveData.add(String.valueOf(ToonHP(stat, toonClass)));
                saveData.add(String.valueOf(ToonMP(stat, toonClass)));
                saveData.add(String.valueOf(stat[STAT_STAMINA]));
                saveData.add(String.valueOf(stat[STAT_CHARISMA]));
                saveData.add(String.valueOf(stat[STAT_STRENGTH]));
                saveData.add(String.valueOf(stat[STAT_INTELLIGENCE]));
                saveData.add(String.valueOf(stat[STAT_WISDOM]));
                saveData.add(String.valueOf(stat[STAT_AGILITY]));
                saveData.add(String.valueOf(stat[STAT_VITALITY]));
                saveData.add(gold().toString());
                saveData.add(DEFAULT_STARTING_AMOUNT);
                saveData.add(DEFAULT_STARTING_AMOUNT);
                saveData.add(DEFAULT_STARTING_AMOUNT);
                saveData.add("0");
                myChar.setEquippedWeapon("Hand");
                myChar.setEquippedArmour("Skin");
                saveData.add("Hand");
                saveData.add("Skin");
                saveData.add("None");
                saveData.add("1");
                saveData.add("2");
                saveData.add("0");
                saveData.add("180");
                int[] results = setAndCalculateStats(stat, myChar.getEquippedWeapon(), myChar.getEquippedArmour());
                int defense = results[0];
                int attack = results[1];
                saveData.add(String.valueOf(defense));
                saveData.add(String.valueOf(attack));
                saveData.add(String.valueOf(ToonHP(stat, toonClass)));
                try {
                    myGameState.saveAllEncrypted(saveData, "InitialCharacterSave.txt");
                    myChar.getCharInfo().clear();
                    myChar.getCharInfo().addAll(saveData);
                    
                    System.out.println("Saved Character Data:");
                    for (int i = 0; i < saveData.size(); i++) {
                        System.out.println("Index " + i + ": " + saveData.get(i));
                    }
                    
                    CharacterCreationFrame.dispose();
                    MainGameScreen mainGame = MainGameScreen.getInstance();
                    if (mainGame != null) {
                        mainGame.setVisible(true);
                    } else {
                        System.err.println("Failed to open main game screen after saving character.");
                    }
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
                CharacterCreationFrame.dispose();
                try {
                    GameStart startMenu = new GameStart();
                    JFrame startMenuFrame = startMenu.getStartMenuFrame();
                    if (startMenuFrame != null) {
                        startMenuFrame.setVisible(true);
                        displayStats(stat, toonClass);
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

        CharacterCreationSplitPane.setRightComponent(rightPanel);

        NameAndStatsPanel.add(tooncreationTextField, BorderLayout.NORTH);
        NameAndStatsPanel.add(toonstatsScrollPane, BorderLayout.CENTER);
        toonstatsScrollPane.setViewportView(toonstatsTextArea);
        NameAndStatsPanel.add(reRollStatsButton, BorderLayout.SOUTH);

        ClassInfoAndImagePanel.add(characterClass, BorderLayout.NORTH);
        ClassInfoAndImagePanel.add(toonclassDescriptionTextArea, BorderLayout.SOUTH);

        CharacterCreationFrame.setLocationRelativeTo(null);
        CharacterCreationFrame.toFront();
        CharacterCreationFrame.requestFocus();
        CharacterCreationFrame.setVisible(true);
        CharacterCreationSplitPane.setDividerLocation(0.15);
        displayStats(stat, toonClass);

        tooncreationTextField.setText("Name: " + charName);
        new GameMenuItems();
    }

    public static String getRaceImagePath(String race) {
        try {
            java.lang.Class<?> raceClass = java.lang.Class.forName("DungeonoftheBrutalKing.Races." + race);
            Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
            return (String) raceClass.getMethod("getRaceImagePath").invoke(raceInstance);
        } catch (Exception e) {
            System.err.println("Failed to load race image path for: " + race + " -> " + e.getMessage());
            return null;
        }
    }

    private static String getRaceDescription(String race) {
        try {
            java.lang.Class<?> raceClass = java.lang.Class.forName("DungeonoftheBrutalKing.Races." + race);
            Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
            return (String) raceClass.getMethod("getRaceDescription").invoke(raceInstance);
        } catch (Exception e) {
            System.err.println("Failed to load race description for: " + race + " -> " + e.getMessage());
            return "No description available.";
        }
    }

    private static String[] getClassesForRace(String race) {
        try {
            java.lang.Class<?> raceClass = java.lang.Class.forName("DungeonoftheBrutalKing.Races." + race);
            Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
            java.util.List<?> allowedRaw = (java.util.List<?>) raceClass.getMethod("getAllowedClasses").invoke(raceInstance);
            java.util.List<String> allowed = new java.util.ArrayList<>();
            for (Object o : allowedRaw) {
                allowed.add((String) o);
            }
            return allowed.toArray(new String[0]);
        } catch (Exception e) {
            System.err.println("Failed to get classes for race: " + race + " -> " + e.getMessage());
            return DungeonoftheBrutalKing.Classes.Class.toonclassarray;
        }
    }

    private static void displayStats(Integer[] stat, String toonClass) {
        if (stat == null) return;
        final Integer[] s = stat.clone();
        SwingUtilities.invokeLater(() -> {
            StringBuilder sb = new StringBuilder();
            String className = toonClass != null ? toonClass : "";
            int hp = ToonHP(s, className);
            int mp = isMagicUser(className) ? calculateMagicPoints(s, className) : 0;
            int actionPoints = !isMagicUser(className) ? ToonActionPoints(s, className) : 0;
            sb.append("CHARACTER STATS\n\n");
            sb.append(String.format("%-15s %5d\n", "STAMINA:", s[STAT_STAMINA]));
            sb.append(String.format("%-15s %5d\n", "CHARISMA:", s[STAT_CHARISMA]));
            sb.append(String.format("%-15s %5d\n", "STRENGTH:", s[STAT_STRENGTH]));
            sb.append(String.format("%-15s %5d\n", "INTELLIGENCE:", s[STAT_INTELLIGENCE]));
            sb.append(String.format("%-15s %5d\n", "WISDOM:", s[STAT_WISDOM]));
            sb.append(String.format("%-15s %5d\n", "AGILITY:", s[STAT_AGILITY]));
            sb.append(String.format("%-15s %5d\n", "VITALITY:", s[STAT_VITALITY]));
            sb.append(String.format("%-15s %5d\n", "HIT POINTS:", hp));
            if (!className.isEmpty()) {
                if (isMagicUser(className)) {
                    sb.append(String.format("%-15s %5d\n", "MAGIC POINTS:", mp));
                } else {
                    sb.append(String.format("%-15s %5d\n", "ACTION POINTS:", actionPoints));
                }
            }
            toonstatsTextArea.setPreferredSize(new Dimension(400, toonstatsTextArea.getPreferredSize().height));
            toonstatsTextArea.setText(sb.toString());
            toonstatsTextArea.setCaretPosition(0);
            toonstatsTextArea.setEditable(false);
            toonstatsTextArea.revalidate();
            toonstatsTextArea.repaint();
            if (toonstatsScrollPane != null) {
                toonstatsScrollPane.revalidate();
                toonstatsScrollPane.repaint();
            }
        });
    }

    public static Integer[] rollstats() {
        int range = 20;
        int lowerbound = 10;
        Integer[] stats = new Integer[7];
        for (int i = 0; i < stats.length; i++) {
            stats[i] = (int) (Math.random() * range) + lowerbound;
        }
        return stats;
    }

    public static Integer ToonHP(Integer[] stat, String clazz) {
        if (stat == null || stat.length <= 6 || clazz == null) {
            return 0;
        }
        int baseHP = switch (clazz) {
            case "Paladin", "Warrior" -> 2;
            default -> 1;
        };
        return baseHP * (stat[STAT_VITALITY] * 10);
    }

    public static int ToonMP(Integer[] stat, String characterClass) {
        if (characterClass == null) {
            return 0;
        }
        int points;
        if (isMagicUser(characterClass)) {
            points = calculateMagicPoints(stat, characterClass);
        } else {
            points = ToonActionPoints(stat, characterClass);
        }
        return points;
    }

    static boolean isMagicUser(String characterClass) {
        java.lang.Class<?> clazz = classMap.get(characterClass);
        if (clazz != null) {
            try {
                DungeonoftheBrutalKing.Classes.Class classInstance =
                        (DungeonoftheBrutalKing.Classes.Class) clazz.getDeclaredConstructor().newInstance();
                return classInstance.isMagicUser();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private static int calculateMagicPoints(Integer[] stat, String characterClass) {
        java.lang.Class<?> clazz = classMap.get(characterClass);
        if (clazz != null) {
            try {
                DungeonoftheBrutalKing.Classes.Class classInstance =
                        (DungeonoftheBrutalKing.Classes.Class) clazz.getDeclaredConstructor().newInstance();
                Stat primary = classInstance.getPrimaryStat();
                Stat secondary = classInstance.getSecondaryStat();
                Random rand = new Random();
                int randomBonus = rand.nextInt(6);
                return 10 + (stat[primary.ordinal()] * 2 + stat[secondary.ordinal()]) + randomBonus;
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public static int ToonActionPoints(Integer[] stat, String characterClass) {
        java.lang.Class<?> clazz = classMap.get(characterClass);
        if (clazz != null) {
            try {
                DungeonoftheBrutalKing.Classes.Class classInstance =
                        (DungeonoftheBrutalKing.Classes.Class) clazz.getDeclaredConstructor().newInstance();
                Stat primary = classInstance.getPrimaryStat();
                Stat secondary = classInstance.getSecondaryStat();
                Random rand = new Random();
                double multiplier = 1.0 + (rand.nextDouble() * 0.5);
                int randomBonus = rand.nextInt(4);
                return (int) Math.round((stat[primary.ordinal()] * 2 + stat[secondary.ordinal()]) * multiplier) + randomBonus;
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public static Integer gold() {
        Random random = new Random();
        int min = 50;
        int max = 100;
        return random.nextInt(max - min + 1) + min;
    }

    private static void classImage(String classImage) throws IOException {
        classImagePanel.removeAll();
        ClassImagePicture = ImageIO.read(new File(GameSettings.getClassImagesPath() + classImage + ".png"));
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
