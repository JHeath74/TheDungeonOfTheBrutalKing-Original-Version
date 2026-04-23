// src/DungeonoftheBrutalKing/CharacterCreation.java
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

import DungeonoftheBrutalKing.Classes.Bard;
import DungeonoftheBrutalKing.Classes.Cleric;
import DungeonoftheBrutalKing.Classes.Hunter;
import DungeonoftheBrutalKing.Classes.Mage;
import DungeonoftheBrutalKing.Classes.Ministrel;
import DungeonoftheBrutalKing.Classes.Paladin;
import DungeonoftheBrutalKing.Classes.Ranger;
import DungeonoftheBrutalKing.Classes.Rogue;
import DungeonoftheBrutalKing.Classes.Thief;
import DungeonoftheBrutalKing.Classes.Warrior;
import DungeonoftheBrutalKing.Classes.Wizard;
import DungeonoftheBrutalKing.Races.RaceEnum;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.Stat;
import DungeonoftheBrutalKing.Weapon.Hand;
import DungeonoftheBrutalKing.Armour.Skin;

public class CharacterCreation {

	//static LoadSaveGame2 myGameState = new LoadSaveGame2();
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

	// Stat indexes (adds VITALITY at index 6)
	private static final int STAT_STAMINA = 0;
	private static final int STAT_CHARISMA = 1;
	private static final int STAT_STRENGTH = 2;
	private static final int STAT_INTELLIGENCE = 3;
	private static final int STAT_WISDOM = 4;
	private static final int STAT_AGILITY = 5;
	private static final int STAT_VITALITY = 6;
	
	private static final Map<String, Class<?>> classMap = Map.ofEntries(
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

	// Applies rolled stats to the runtime character and returns computed defense/attack.


	private int[] setAndCalculateStats(Integer[] stat, Object weapon, Object armour) {
		// Use names for equipped weapon and armour
		
		
		myChar.setEquippedWeapon("Hand");
		myChar.setEquippedArmour("Skin");
		myChar.setAgility(stat[STAT_AGILITY]);
		myChar.setStrength(stat[STAT_STRENGTH]);

		try {
			myChar.getClass().getMethod("setVitality", int.class).invoke(myChar, stat[STAT_VITALITY]);
		} catch (Exception ignored) {
		}

		myChar.calculateAndSetDefense();
		myChar.calculateAndSetAttack();
		int defense = myChar.getDefense();
		int attack = myChar.getAttackDamage();
		return new int[]{defense, attack};
	}



	public void createCharector() {
	    // Prompt for name before anything else
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

	    toonclasslist = DungeonoftheBrutalKing.Classes.Class.toonclassarray;
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
	            displayStats(stat);
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
	    displayStats(stat);

	    reRollStatsButton = new JButton("Reroll Stats");
	    reRollStatsButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            stat = rollstats();
	            System.out.println("Calling displayStats with: " + Arrays.toString(stat));
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
	            saveData.add(String.valueOf(stat[STAT_STAMINA]));
	            saveData.add(String.valueOf(stat[STAT_CHARISMA]));
	            saveData.add(String.valueOf(stat[STAT_STRENGTH]));
	            saveData.add(String.valueOf(stat[STAT_INTELLIGENCE]));
	            saveData.add(String.valueOf(stat[STAT_WISDOM]));
	            saveData.add(String.valueOf(stat[STAT_AGILITY]));
	            saveData.add(String.valueOf(stat[STAT_VITALITY]));
	            saveData.add(gold().toString());
	            saveData.add("3");
	            saveData.add("3");
	            saveData.add("3");
	            saveData.add("0");
	            myChar.setEquippedWeapon("Hand");
	            myChar.setEquippedArmour("Skin");
	            myChar.setPosition(3, 4, 0);
	            saveData.add(myChar.getEquippedWeapon() != null ? myChar.getEquippedWeapon() : "Hand");
	            saveData.add(myChar.getEquippedArmour() != null ? myChar.getEquippedArmour() : "Skin");
	            saveData.add("None");
	            saveData.add("0");
	            saveData.add("2");
	            saveData.add("3");
	            saveData.add("1");
	            saveData.add("180.0");
	            int[] results = setAndCalculateStats(stat, myChar.getEquippedWeapon(), myChar.getEquippedArmour());
	            int defense = results[0];
	            int attack = results[1];
	            saveData.add(String.valueOf(defense));
	            saveData.add(String.valueOf(attack));
	            saveData.add(String.valueOf(ToonHP(stat, saveData)));
	           
	            
	            System.out.println("Saving character with data: " + saveData);
	            
	            try {
	                myGameState.saveAllEncrypted(saveData, "InitialCharecterSave.txt");
	           // 	myGameState.saveAll(saveData, "InitialCharecterSave.txt");
	            	myChar.getCharInfo().clear();
	                myChar.getCharInfo().addAll(saveData);
	                
	        
	                
	                CharecterCreationFrame.dispose();
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
	            CharecterCreationFrame.dispose();
	            try {
	                GameStart startMenu = new GameStart();
	                JFrame startMenuFrame = startMenu.getStartMenuFrame();
	                if (startMenuFrame != null) {
	                    startMenuFrame.setVisible(true);
	                    displayStats(stat);
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
	    NameAndStatsPanel.add(toonstatsScrollPane, BorderLayout.CENTER);
	    toonstatsScrollPane.setViewportView(toonstatsTextArea);
	    NameAndStatsPanel.add(reRollStatsButton, BorderLayout.SOUTH);

	    ClassInfoAndImagePanel.add(charectorClass, BorderLayout.NORTH);
	    ClassInfoAndImagePanel.add(toonclassDescriptionTextArea, BorderLayout.SOUTH);

	    CharecterCreationFrame.setLocationRelativeTo(null);
	    CharecterCreationFrame.toFront();
	    CharecterCreationFrame.requestFocus();
	    CharecterCreationFrame.setVisible(true);
	    displayStats(stat);

	    tooncreationTextField.setText("Name: " + charName);
	    new GameMenuItems();
	}
	public static String getRaceImagePath(String race) {
		try {
			Class<?> raceClass = Class.forName("DungeonoftheBrutalKing.Races." + race);
			Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
			return (String) raceClass.getMethod("getRaceImagePath").invoke(raceInstance);
		} catch (Exception e) {
			System.err.println("Failed to load race image path for: " + race + " -> " + e.getMessage());
			return null;
		}
	}

	private static String getRaceDescription(String race) {
		try {
			Class<?> raceClass = Class.forName("DungeonoftheBrutalKing.Races." + race);
			Object raceInstance = raceClass.getDeclaredConstructor().newInstance();
			return (String) raceClass.getMethod("getRaceDescription").invoke(raceInstance);
		} catch (Exception e) {
			System.err.println("Failed to load race description for: " + race + " -> " + e.getMessage());
			return "No description available.";
		}
	}

	private static String[] getClassesForRace(String race) {
		try {
			Class<?> raceClass = Class.forName("DungeonoftheBrutalKing.Races." + race);
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


private static void displayStats(Integer[] stat) {
    if (stat == null) return;
    final Integer[] s = stat.clone();
    SwingUtilities.invokeLater(() -> {
        StringBuilder sb = new StringBuilder();
        sb.append("CHARECTOR STATS\n");
        sb.append("\nSTAMINA: \t\t").append(s[STAT_STAMINA]);
        sb.append("\nCHARISMA: \t\t").append(s[STAT_CHARISMA]);
        sb.append("\nSTRENGTH: \t\t").append(s[STAT_STRENGTH]);
        sb.append("\nINTELLIGENCE: \t").append(s[STAT_INTELLIGENCE]);
        sb.append("\nWISDOM: \t\t").append(s[STAT_WISDOM]);
        sb.append("\nAGILITY: \t\t").append(s[STAT_AGILITY]);
        sb.append("\nVITALITY: \t\t").append(s[STAT_VITALITY]);

        // Add Hit Points using ToonHP
        String className = toonClass != null ? toonClass : "";
        ArrayList<String> tempChar = new ArrayList<>();
        tempChar.add(""); // name placeholder
        tempChar.add(className); // class
        int hp = ToonHP(s, tempChar);
        sb.append("\nHIT POINTS: \t\t").append(hp);

        if (!className.isEmpty()) {
            if (isMagicUser(className)) {
                int mp = calculateMagicPoints(s, className);
                sb.append("\nMAGIC POINTS: \t").append(mp);
            } else {
                Random rand = new Random();
                double multiplier = 1.0 + (0.5 * rand.nextDouble());
                int actionPoints = (int) Math.round((s[STAT_STRENGTH] + s[STAT_AGILITY]) * multiplier);
                sb.append("\nACTION POINTS: \t").append(actionPoints);
            }
        }

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

		// NEW: 7 stats including Vitality
		Integer[] stats = new Integer[7];
		for (int i = 0; i < stats.length; i++) {
			stats[i] = (int) (Math.random() * range) + lowerbound;
		}

		return stats;
	}

	// NEW: HP derived from Vitality (keeps class multiplier pattern)
	public static Integer ToonHP(Integer[] stat, ArrayList<String> newChar) {
	   


		if (stat == null || stat.length <= 6 || newChar == null || newChar.size() < 2 || newChar.get(1) == null) {
	        return 0;
	    }
	    String clazz = newChar.get(1);
	    int baseHP = switch (clazz) {
	        case "Paladin", "Warrior" -> 2;
	        default -> 1;
	    };
	    return baseHP * (stat[STAT_VITALITY] * 10);

	}

	public static int ToonMP(Integer[] stat, ArrayList<String> newChar) {
		if (newChar.size() < 2 || newChar.get(1) == null) {
			return 0;
		}
		String characterClass = newChar.get(1);
		int points;
		if (isMagicUser(characterClass)) {
			points = calculateMagicPoints(stat, characterClass);
		} else {
			points = ToonActionPoints(stat, characterClass);
		}
		return points;
	}



static boolean isMagicUser(String characterClass) {
    Class<?> clazz = classMap.get(characterClass);
    if (clazz != null) {
        try {
            DungeonoftheBrutalKing.Classes.Class classInstance =
                (DungeonoftheBrutalKing.Classes.Class) clazz.getDeclaredConstructor().newInstance();
            return classInstance.isMagicUser();
        } catch (Exception e) {
            // Optionally log the error
            return false;
        }
    }
    return false;
}





private static int calculateMagicPoints(Integer[] stat, String characterClass) {
    Class<?> clazz = classMap.get(characterClass);
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
    Class<?> clazz = classMap.get(characterClass);
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