
package DungeonoftheBrutalKing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LoadSaveGame {

	Charecter myChar = Charecter.Singleton();
	GameSettings myGameSettings = new GameSettings();
	ArrayList<ArrayList<?>> GameState = new ArrayList<>();
	int width, height = 0;

	public void AutoSaveGame() throws IOException {
		String savedGameName = "AutoGameSave.Txt";
		String autoSaveGamePath = GameSettings.SavedGameDirectory + savedGameName;

		if (!savedGameName.equals("IntialCharecterSave.txt")) {
			try (FileWriter writer = new FileWriter(autoSaveGamePath)) {
				for (String charInfo : myChar.CharInfo) {
					writer.write(charInfo + System.lineSeparator());
				}
				JOptionPane.showMessageDialog(null, "Game Saved: " + savedGameName);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Error saving game: " + e.getMessage());
				throw e;
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Unable to Save Current Game Over Saved Game called 'InitialCharecterSave.txt'\n");
		}
	}


	public void SaveGame() throws IOException, ParseException {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

		String datetime = dateFormat.format(date);
		datetime = datetime.replaceAll(":", ".");

		String SavedGameName = "SavedGame" + datetime + ".txt";

		if (!SavedGameName.equals("IntialCharecterSave.txt")) {
			String SaveGameName = GameSettings.SavedGameDirectory + SavedGameName;

			try (FileWriter writer = new FileWriter(SaveGameName)) {
				for (String Charinfo : myChar.CharInfo) {
					writer.write(Charinfo + System.lineSeparator());
				}
			}

			JOptionPane.showMessageDialog(null, "Game Saved: " + SavedGameName);
		} else {
			JOptionPane.showMessageDialog(null,
					"Unable to Save Current Game Over Saved Game called 'InitialCharecterSave.txt'\n");
		}
	}


	public void StartGameLoadCharecter() throws IOException {
		ArrayList<String> SaveLoadChar = new ArrayList<>();
		File chosenFile = getLastModified(GameSettings.SavedGameDirectory);

		try (BufferedReader bufReader = new BufferedReader(new FileReader(chosenFile))) {
			String line;
			while ((line = bufReader.readLine()) != null) {
				SaveLoadChar.add(line);
			}
		}

		Singleton.myCharSingleton().CharInfo.addAll(SaveLoadChar);
	}

	public void ContinueCurrentGame() throws IOException, InterruptedException, ParseException {
		ArrayList<String> SaveLoadChar = new ArrayList<>();
		File chosenFile = getLastModified(GameSettings.SavedGameDirectory);

		if (chosenFile == null || !chosenFile.exists()) {
			JOptionPane.showMessageDialog(null, "No valid save file found to continue the game.");
			return;
		}

		try (BufferedReader bufReader = new BufferedReader(new FileReader(chosenFile))) {
			String line;
			while ((line = bufReader.readLine()) != null) {
				SaveLoadChar.add(line);
			}

			myChar.CharInfo.clear();
			myChar.CharInfo.addAll(SaveLoadChar);

			JOptionPane.showMessageDialog(null, "Game continued successfully from: " + chosenFile.getName());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error continuing game: " + e.getMessage());
			throw e;
		}

		new MainGameScreen();
	}

	public void LoadGame() {
		ArrayList<String> LoadChar = new ArrayList<>();

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) size.getWidth();
		height = (int) size.getHeight();

		JFrame loadGame = new JFrame("Load Game");
		loadGame.setSize(width, height);

		JPanel lg = new JPanel(new BorderLayout());
		JButton load = new JButton("Load Game");
		JComboBox<String> loadGameSelection = new JComboBox<>();

		File loadgamefiles = new File(GameSettings.SavedGameDirectory);
		File[] listOfFiles = loadgamefiles.listFiles();

		if (listOfFiles == null || listOfFiles.length == 0) {
			JOptionPane.showMessageDialog(null, "No saved game files found.");
			return;
		}

		for (File listOfFile : listOfFiles) {
			loadGameSelection.addItem(listOfFile.getName());
		}

		load.addActionListener(_ -> {
			String gameInfo = (String) loadGameSelection.getSelectedItem();

			if (gameInfo.equals("InitialCharecterSave.txt")) {
				int response = JOptionPane.showConfirmDialog(null,
						"This will reload the original saved game and restart your character",
						"Reload Save Game", JOptionPane.YES_NO_OPTION);
				if (response != JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Please Choose a Different Saved Game File");
					return;
				}
			}

			try (BufferedReader bufReader = new BufferedReader(
					new FileReader(GameSettings.SavedGameDirectory + gameInfo))) {
				String line;
				while ((line = bufReader.readLine()) != null) {
					LoadChar.add(line);
				}

				Singleton.myCharSingleton().CharInfo.clear();
				Singleton.myCharSingleton().CharInfo.addAll(LoadChar);

				JOptionPane.showMessageDialog(null, "Game Loaded: " + gameInfo);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Error loading game: " + ex.getMessage());
			}

			loadGame.dispose();
		});

		lg.add(loadGameSelection, BorderLayout.CENTER);
		loadGame.add(lg, BorderLayout.CENTER);
		loadGame.add(load, BorderLayout.SOUTH);

		loadGame.setLocationRelativeTo(null);
		loadGame.setSize(640, 480);
		loadGame.setVisible(true);
	}

	public static File getLastModified(String SavedGameDirectory) {
		File directory = new File(SavedGameDirectory);
		File[] files = directory.listFiles(File::isFile);
		long lastModifiedTime = Long.MIN_VALUE;
		File chosenFile = null;

		if (files != null) {
			for (File file : files) {
				if (file.lastModified() > lastModifiedTime) {
					chosenFile = file;
					lastModifiedTime = file.lastModified();
				}
			}
		}

		return chosenFile;
	}

	public static int getFileCount() {
		File directory = new File(GameSettings.SavedGameDirectory);
		File[] files = directory.listFiles(File::isFile);
		int count = 0;

		if (files != null) {
			for (File file : files) {
				count++;
			}
		}

		return count;
	}
}
