
package DungeonoftheBrutalKing;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.util.Objects;

import javax.swing.*;
import javax.swing.text.*;


import Quests.Quest;
import Quests.QuestManager;
import SharedData.GameSettings;

public class MainGameScreen extends JFrame {
	private static final long serialVersionUID = 1L;

	private static MainGameScreen instance;

	Charecter myChar = Charecter.Singleton();
	GameSettings myGameSettings = new GameSettings();
	LoadSaveGame myGameState = new LoadSaveGame();
	GameMenuItems myGameMenuItems = new GameMenuItems();
	CharacterCreation myCharacterCreation = new CharacterCreation();
	//private QuestManager questManager = new QuestManager(myChar);

	JFrame MainGameScreenFrame = null;
	JPanel p1Panel, p2Panel, p3Panel, p4Panel = null;
	public JPanel GameImagesAndCombatPanel = null;
	private JPanel originalPanel;

	JTextField CharNameClassLevelField, CharStatsField, CharStats2Field, CharXPHPGoldField = null;
	static JTextPane MessageTextPane = null;

	JMenuBar menuBar = null;
	JMenu gameMenu, charecterMenu, settingsMenu, helpMenu = null;
	JMenuItem newGameMenuItem, LoadSavedGameMenuItem, saveMenuItem, exitGameMenuItem, charecterstatsMenuItem,
	charecterinventoryMenuItem, mapMenu, gameSettingsMenuItem, aboutMenuItem, helpMenuItem, mapFloor1MenuItem,
	mapFloor2MenuItem, mapFloor3MenuItem, mapFloor4MenuItem, displayActiveQuestsMenuItem = null;

	JSplitPane PicturesAndTextUpdatesPane = null;

	Dimension screenSize = null;
	int width, height = 0;

	Timer timer = null;
	private TimeClock clock;
	private Canvas gameImagesAndCombatCanvas;
	public static JTextArea CombatMessageArea = new JTextArea();

	public static MainGameScreen getInstance() throws IOException, InterruptedException, ParseException {
		if (instance == null) {
			instance = new MainGameScreen();
		}
		return instance;
	}

	public MainGameScreen() throws IOException, InterruptedException, ParseException {
		myCharacterCreation = new CharacterCreation();


		// MainGameScreen.java
		//QuestManager questManager = new QuestManager(myChar);
	//	questManager.displayActiveQuests(); // Generate and display quests for dungeon level 1


		MessageTextPane = new JTextPane();
		MessageTextPane.setEditable(false);
		MessageTextPane.setFont(new Font("Arial", Font.PLAIN, 14));

		MainGameScreenFrame = new JFrame("Dungeon of the Brutal King");
		
        for (int i = 0; i < myChar.CharInfo.size(); i++) {
            System.out.println("Index: " + i + " : " + myChar.CharInfo.get(i));
        }

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) size.getWidth();
		height = (int) size.getHeight();

		MainGameScreenFrame.setSize(width, height);
		MainGameScreenFrame.setLayout(new BorderLayout());
		MainGameScreenFrame.setForeground(myGameSettings.getColorBrown());
		MainGameScreenFrame.setUndecorated(true);
		MainGameScreenFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		p1Panel = new JPanel(new BorderLayout());
		p2Panel = new JPanel(new BorderLayout());
		p3Panel = new JPanel(new BorderLayout());
		p4Panel = new JPanel(new BorderLayout());
		GameImagesAndCombatPanel = new JPanel(new BorderLayout());
		

		// Replace GameImagesAndCombatPanel initialization
		//GameImagesAndCombatPanel = new RenderPanel();
		//GameImagesAndCombatPanel.setLayout(new BorderLayout());


		try {
			myGameState.StartGameLoadCharecter();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		CharNameClassLevelField = new JTextField();
		CharNameClassLevelField.setFont(myGameSettings.getFontTimesNewRoman());
		CharNameClassLevelField.setBackground(myGameSettings.getColorGreen());
		CharNameClassLevelField.setForeground(myGameSettings.getColorWhite());
		CharNameClassLevelField.setColumns(3);
		CharNameClassLevelField.setEditable(false);

		CharStatsField = new JTextField();
		CharStatsField.setLayout(new FlowLayout());
		CharStatsField.setFont(myGameSettings.getFontTimesNewRoman());
		CharStatsField.setBackground(myGameSettings.getColorBlue());
		CharStatsField.setForeground(myGameSettings.getColorWhite());
		CharStatsField.setEditable(false);

		CharStats2Field = new JTextField();
		CharStats2Field.setLayout(new FlowLayout());
		CharStats2Field.setFont(myGameSettings.getFontTimesNewRoman());
		CharStats2Field.setBackground(myGameSettings.getColorBlue());
		CharStats2Field.setForeground(myGameSettings.getColorWhite());
		CharStats2Field.setEditable(false);

		CharXPHPGoldField = new JTextField();
		CharXPHPGoldField.setLayout(getLayout());
		CharXPHPGoldField.setFont(myGameSettings.getFontTimesNewRoman());
		CharXPHPGoldField.setBackground(myGameSettings.getColorPurple());
		CharXPHPGoldField.setForeground(myGameSettings.getColorWhite());
		CharXPHPGoldField.setColumns(3);
		CharXPHPGoldField.setEditable(false);

		p1Panel.add(p2Panel, BorderLayout.NORTH);
		p1Panel.add(p3Panel, BorderLayout.CENTER);
		p1Panel.add(p4Panel, BorderLayout.SOUTH);
		p2Panel.add(CharNameClassLevelField);
		p3Panel.add(CharStatsField, BorderLayout.NORTH);
		p3Panel.add(CharStats2Field, BorderLayout.SOUTH);
		p4Panel.add(CharXPHPGoldField);

		ActionListener task = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				CharNameClassLevelField.setText("Name: " + myChar.CharInfo.get(0) + "\t\t"
						+ "Level: " + myChar.CharInfo.get(2) + "\t\t"
						+ "Experience: " + myChar.CharInfo.get(3)
						+ "\t\t" + "Class: " + myChar.CharInfo.get(1) + "\t\t");

				CharStatsField.setText("Stamina:\t"
						+ "Charisma: \t"
						+ "Strength: \t"
						+ "Intelligence:\t "
						+ "Wisdom: \t"
						+ "Agility: \t");

				CharStats2Field.setText(myChar.CharInfo.get(6) + "\t" +
						myChar.CharInfo.get(7) + "\t" +
						myChar.CharInfo.get(8) + "\t" +
						myChar.CharInfo.get(9) + "\t" +
						myChar.CharInfo.get(10) + "\t" +
						myChar.CharInfo.get(11));

				CharXPHPGoldField.setText("Hit Points: " + myChar.CharInfo.get(4) + "\t\t"
						+ "Action Points: " + myChar.CharInfo.get(5) + "\t"
						+ "Gold: " + myChar.CharInfo.get(12) + "\t"
						+ "Food: " + myChar.CharInfo.get(13) + "\t"
						+ "Water: " + myChar.CharInfo.get(14) + "\t"
						+ "Torches: " + myChar.CharInfo.get(15) + "\t"
						+ "Gems: " + myChar.CharInfo.get(16) + "\t");
			}
		};
		timer = new Timer(100, task);
		timer.setRepeats(true);
		timer.start();


		// Java
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(25, 35));
		menuBar.setFont(new Font("sans-serif", Font.ROMAN_BASELINE, 22));
		menuBar.setBackground(myGameSettings.getColorPlum());

		// Create menus
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);

		charecterMenu = new JMenu("Character");
		charecterMenu.setMnemonic(KeyEvent.VK_C);

		settingsMenu = new JMenu("Preferences");
		settingsMenu.setMnemonic(KeyEvent.VK_P);

		helpMenu = new JMenu("About");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		// Add menus to the menuBar
		menuBar.add(gameMenu);
		menuBar.add(charecterMenu);
		menuBar.add(settingsMenu);
		menuBar.add(helpMenu);

		// Set the menuBar to the frame
		MainGameScreenFrame.setJMenuBar(menuBar);



		// Java
		// Add missing menu items to the respective menus

		// Game Menu Items
		newGameMenuItem = new JMenuItem("New Game");
		newGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		newGameMenuItem.getAccessibleContext().setAccessibleDescription("Start a new game");
		newGameMenuItem.addActionListener(e -> {
			int result = JOptionPane.showConfirmDialog(rootPane,
					"Are you sure you wish to delete your current game and start a new one?", "Start New Game?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (result == JOptionPane.YES_OPTION) {
				dispose();
				try {
					Files.createDirectories(Paths.get("src/AlternateRealityTheDungeon/TextFiles/SaveGame"));
					BufferedWriter writer = Files.newBufferedWriter(Paths
							.get("src/AlternateRealityTheDungeon/TextFiles/SaveGame/InitialCharecterSave.txt"));
					writer.write("");
					writer.flush();
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				File d = new File(GameSettings.SavedGameDirectory);
				for (File file : d.listFiles()) {
					if (!file.isDirectory()) {
						file.delete();
					}
				}
				try {
					myCharacterCreation = new CharacterCreation();
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
				myCharacterCreation.createCharector();
			}


		});

		LoadSavedGameMenuItem = new JMenuItem("Load Saved Game");
		LoadSavedGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		LoadSavedGameMenuItem.getAccessibleContext().setAccessibleDescription("Load a saved game");
		LoadSavedGameMenuItem.addActionListener(e -> {
			myGameState.LoadGame();
		});

		saveMenuItem = new JMenuItem("Save Game");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		saveMenuItem.getAccessibleContext().setAccessibleDescription("Save the current game");
		saveMenuItem.addActionListener(e -> {
			try {
				myGameState.SaveGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		exitGameMenuItem = new JMenuItem("Exit Game");
		exitGameMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		exitGameMenuItem.getAccessibleContext().setAccessibleDescription("Exit the game");
		exitGameMenuItem.addActionListener(e -> {
			System.exit(0);
		});

		// Add items to Game Menu
		gameMenu.add(newGameMenuItem);
		gameMenu.add(LoadSavedGameMenuItem);
		gameMenu.add(saveMenuItem);
		gameMenu.add(exitGameMenuItem);


		// Character Menu Items
		charecterstatsMenuItem = new JMenuItem("Character Stats");
		charecterstatsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		charecterstatsMenuItem.getAccessibleContext().setAccessibleDescription("View character stats");
		charecterstatsMenuItem.addActionListener(e -> {
			myGameMenuItems.Stats();
		});

		charecterinventoryMenuItem = new JMenuItem("Character Inventory");
		charecterinventoryMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
		charecterinventoryMenuItem.getAccessibleContext().setAccessibleDescription("View character inventory");
		charecterinventoryMenuItem.addActionListener(e -> {
			myGameMenuItems.Inventory();
		});

		// Inside the MainGameScreen constructor
		JMenuItem displayActiveQuestsMenuItem = new JMenuItem("Display Active Quests");
		displayActiveQuestsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		displayActiveQuestsMenuItem.getAccessibleContext().setAccessibleDescription("Display Active Quests");
		displayActiveQuestsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("Active Quests:\n");
			//	questManager.displayActiveQuests(); // Generate and display quests for dungeon level 1


				/*
				 * StringBuilder questsInfo = new StringBuilder();
				 * questsInfo.append("Active Quests:\n"); for (Quest quest :
				 * questManager.getActiveQuests()) {
				 * questsInfo.append("Quest: ").append(quest.getName()).append("\n");
				 * questsInfo.append("Description: ").append(quest.getDescription()).append(
				 * "\n\n"); }
				 * 
				 * 
				 * JOptionPane.showMessageDialog(MainGameScreenFrame, questsInfo.toString(),
				 * "Active Quests", JOptionPane.INFORMATION_MESSAGE);
				 */
			}
		});


		// Add items to Character Menu
		charecterMenu.add(charecterstatsMenuItem);
		charecterMenu.add(charecterinventoryMenuItem);
		charecterMenu.add(displayActiveQuestsMenuItem);

		// Settings Menu Items
		gameSettingsMenuItem = new JMenuItem("Game Settings");
		gameSettingsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
		gameSettingsMenuItem.getAccessibleContext().setAccessibleDescription("Adjust game settings");
		gameSettingsMenuItem.addActionListener(e -> {
			// Logic for game settings
		});

		// Add items to Settings Menu
		settingsMenu.add(gameSettingsMenuItem);

		// Help Menu Items
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		aboutMenuItem.getAccessibleContext().setAccessibleDescription("About the game");

aboutMenuItem.addActionListener(e -> {
    JDialog aboutDialog = new JDialog(MainGameScreenFrame, "About Information", true);
    aboutDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    JPanel panel = new JPanel(new BorderLayout());

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(event -> aboutDialog.dispose());

    JTextPane aboutTextPane = new JTextPane();
    aboutTextPane.setEditable(false);

    StyledDocument doc = aboutTextPane.getStyledDocument();

    // Define styles
    Style headerStyle = doc.addStyle("Header", null);
    StyleConstants.setFontSize(headerStyle, 18);
    StyleConstants.setBold(headerStyle, true);
    StyleConstants.setForeground(headerStyle, Color.BLUE);

    Style bodyStyle = doc.addStyle("Body", null);
    StyleConstants.setFontSize(bodyStyle, 14);
    StyleConstants.setForeground(bodyStyle, Color.BLACK);

    Style footerStyle = doc.addStyle("Footer", null);
    StyleConstants.setFontSize(footerStyle, 12);
    StyleConstants.setItalic(footerStyle, true);
    StyleConstants.setForeground(footerStyle, Color.GRAY);

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            Objects.requireNonNull(getClass().getResourceAsStream("/DungeonoftheBrutalKing/TextFiles/About.txt"))))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Header:")) {
                doc.insertString(doc.getLength(), line.replace("Header:", "") + "\n", headerStyle);
            } else if (line.startsWith("Footer:")) {
                doc.insertString(doc.getLength(), line.replace("Footer:", "") + "\n", footerStyle);
            } else {
                doc.insertString(doc.getLength(), line + "\n", bodyStyle);
            }
        }
    } catch (IOException | BadLocationException ex) {
        ex.printStackTrace();
    }

    JScrollPane scrollPane = new JScrollPane(aboutTextPane);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(closeButton, BorderLayout.SOUTH);

    aboutDialog.add(panel);
    aboutDialog.pack();
    aboutDialog.setLocationRelativeTo(MainGameScreenFrame);
    aboutDialog.setVisible(true);
});


		helpMenuItem = new JMenuItem("Help");
		helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		helpMenuItem.getAccessibleContext().setAccessibleDescription("Help information");




helpMenuItem.addActionListener(e -> {
    JDialog helpDialog = new JDialog(MainGameScreenFrame, "Help Information", true);
    helpDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    JPanel panel = new JPanel(new BorderLayout());

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(event -> helpDialog.dispose());

    JTextPane helpTextPane = new JTextPane();
    helpTextPane.setEditable(false);

    StyledDocument doc = helpTextPane.getStyledDocument();

    // Define styles
    Style headerStyle = doc.addStyle("Header", null);
    StyleConstants.setFontSize(headerStyle, 18);
    StyleConstants.setBold(headerStyle, true);
    StyleConstants.setForeground(headerStyle, Color.BLUE);

    Style bodyStyle = doc.addStyle("Body", null);
    StyleConstants.setFontSize(bodyStyle, 14);
    StyleConstants.setForeground(bodyStyle, Color.BLACK);

    Style footerStyle = doc.addStyle("Footer", null);
    StyleConstants.setFontSize(footerStyle, 12);
    StyleConstants.setItalic(footerStyle, true);
    StyleConstants.setForeground(footerStyle, Color.GRAY);

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
            Objects.requireNonNull(getClass().getResourceAsStream("/DungeonoftheBrutalKing/TextFiles/Help.txt"))))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Header:")) {
                doc.insertString(doc.getLength(), line.replace("Header:", "") + "\n", headerStyle);
            } else if (line.startsWith("Footer:")) {
                doc.insertString(doc.getLength(), line.replace("Footer:", "") + "\n", footerStyle);
            } else {
                doc.insertString(doc.getLength(), line + "\n", bodyStyle);
            }
        }
    } catch (IOException | BadLocationException ex) {
        ex.printStackTrace();
    }

    JScrollPane scrollPane = new JScrollPane(helpTextPane);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(closeButton, BorderLayout.SOUTH);

    helpDialog.add(panel);
    helpDialog.pack();
    helpDialog.setLocationRelativeTo(MainGameScreenFrame);
    helpDialog.setVisible(true);
});



		// Add items to Help Menu
		helpMenu.add(aboutMenuItem);
		helpMenu.add(helpMenuItem);


		// Java



		//  gameMenu.add(newGameMenuItem);

		PicturesAndTextUpdatesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		PicturesAndTextUpdatesPane.setDividerLocation(width - 200);
		PicturesAndTextUpdatesPane.setResizeWeight(.90d);
		PicturesAndTextUpdatesPane.setLeftComponent(GameImagesAndCombatPanel);
		PicturesAndTextUpdatesPane.setRightComponent(MessageTextPane);

		MainGameScreenFrame.add(PicturesAndTextUpdatesPane, BorderLayout.CENTER);
		MainGameScreenFrame.add(p1Panel, BorderLayout.NORTH);



		clock = new TimeClock(TimeClock.Month.REBIRTH, MessageTextPane);
		clock.startClock();

		MainGameScreenFrame.setVisible(true);
	}

	public void setMessageTextPane(JTextPane messageTextPane) {
		MessageTextPane = messageTextPane;
	}

	public void setMessageTextPane(String string) {
		appendToMessageTextPane(string);
	}

	public static void appendToMessageTextPane(String text) {
		StyledDocument doc = MessageTextPane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void replaceWithAnyPanel(JPanel newPanel) {
		if (newPanel != null) {
			if (originalPanel == null) {
				originalPanel = (JPanel) PicturesAndTextUpdatesPane.getLeftComponent();
			}
			PicturesAndTextUpdatesPane.setLeftComponent(newPanel);
			PicturesAndTextUpdatesPane.revalidate();
			PicturesAndTextUpdatesPane.repaint();
		} else {
			throw new IllegalArgumentException("Panel cannot be null");
		}
	}

	public void restoreOriginalPanel() {
		if (originalPanel != null) {
			PicturesAndTextUpdatesPane.setLeftComponent(originalPanel);
			PicturesAndTextUpdatesPane.revalidate();
			PicturesAndTextUpdatesPane.repaint();
		} else {
			throw new IllegalStateException("No original panel to restore.");
		}
	}

	public JPanel getGameImagesAndCombatPanel() {
		return GameImagesAndCombatPanel;
	}

	public Canvas getGameImagesAndCombatCanvas() {
		return gameImagesAndCombatCanvas;
	}


	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
		MainGameScreen.getInstance();
	}
}
