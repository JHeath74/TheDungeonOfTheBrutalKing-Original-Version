

/**
 * The LoadSaveGame class is responsible for managing the saving and loading of game states.
 * It provides functionality for auto-saving, quick-saving, loading saved games, and continuing
 * the most recently saved game. The class also includes utility methods for retrieving the
 * most recently modified save file and counting the number of saved game files.
 */


package DungeonoftheBrutalKing;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 * The LoadSaveGame class provides functionality for saving, loading, and managing game states.
 * It handles operations such as auto-saving, quick-saving, loading saved games, and continuing the last game.
 */
public class LoadSaveGame {

    // Singleton instance of the character
    Charecter myChar = Charecter.Singleton();

    // Game settings instance for file paths and preferences
    GameSettings myGameSettings = new GameSettings();

    // Stores the game state as a list of lists
    ArrayList<ArrayList<?>> GameState = new ArrayList<>();
    int width, height = 0;

    /**
     * Automatically saves the current game state to a predefined file.
     * Displays a message upon successful save or failure.
     * 
     * @throws IOException if an I/O error occurs during saving.
     */
    public void AutoSaveGame() throws IOException {
        String SavedGameName = "AutoGameSave.Txt";

        if (!SavedGameName.equals("IntialCharecterSave.txt")) {
            String AutoSaveGameName = myGameSettings.SavedGameDirectory + SavedGameName;

            FileWriter writer = new FileWriter(AutoSaveGameName);

            for (String Charinfo : myChar.CharInfo) {
                writer.write(Charinfo + System.lineSeparator());
            }
            writer.close();

            JOptionPane.showMessageDialog(null, "Game Saved: " + SavedGameName);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Unable to Save Current Game Over Saved Game called  'InitialCharecterSave.txt'\n");
        }
    }

    /**
     * Continues the most recently saved game by loading its data into the character instance.
     * 
     * @throws IOException if an I/O error occurs during loading.
     */
    public void ContinueCurrentGame() throws IOException {
        ArrayList<String> SaveLoadChar = new ArrayList<>();
        File chosenFile = getLastModified(myGameSettings.SavedGameDirectory);

        BufferedReader bufReader = new BufferedReader(new FileReader(chosenFile));

        String line = bufReader.readLine();
        while (line != null) {
            SaveLoadChar.add(line);
            line = bufReader.readLine();
        }

        myChar.CharInfo.addAll(SaveLoadChar);

        bufReader.close();

        new MainGameScreen();
    }

    /**
     * Displays a UI for selecting and loading a saved game.
     * Allows the user to choose a saved game file and load its data.
     */
    public void LoadGame() {
        ArrayList<String> LoadChar = new ArrayList<>();

        // Get screen dimensions
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) size.getWidth();
        height = (int) size.getHeight();

        // Create a JFrame for loading games
        JFrame loadGame = new JFrame("Load Game");
        loadGame.setSize(width, height);

        JPanel lg = new JPanel(new BorderLayout());
        JButton load = new JButton("Load Game");
        JComboBox<String> loadGameSelection = new JComboBox<>();

        // Populate the JComboBox with saved game files
        File loadgamefiles = new File(myGameSettings.SavedGameDirectory);
        File[] listOfFiles = loadgamefiles.listFiles();

        for (File listOfFile : listOfFiles) {
            loadGameSelection.addItem(listOfFile.getName());
        }

        // Add action listener for loading the selected game
        loadGameSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String gameInfo = loadGameSelection.getSelectedItem().toString();

                        if (gameInfo.equals("InitialCharecterSave.txt")) {
                            int response = JOptionPane.showConfirmDialog(null,
                                    "This will reload the original saved game and restart your character",
                                    "Reload Save Game", JOptionPane.YES_NO_OPTION);
                            if (response == JOptionPane.YES_OPTION) {
                                try {
                                    BufferedReader bufReader = new BufferedReader(
                                            new FileReader(myGameSettings.SavedGameDirectory + gameInfo));
                                    String line = bufReader.readLine();
                                    while (line != null) {
                                        LoadChar.add(line);
                                        line = bufReader.readLine();
                                    }

                                    Singleton.myCharSingleton().CharInfo.addAll(LoadChar);
                                    bufReader.close();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Please Choose a Different Saved Game File");
                            }
                        } else {
                            // TODO: Implement logic for loading serialized game data
                        }

                        JOptionPane.showMessageDialog(null, "Game Loaded: " + gameInfo);

                        loadGame.dispose();
                    }
                });
            }
        });

        lg.add(loadGameSelection);
        loadGame.add(lg, BorderLayout.CENTER);
        loadGame.add(load, BorderLayout.SOUTH);

        loadGame.setLocationRelativeTo(null);
        loadGame.setSize(640, 480);

        loadGame.setVisible(true);
    }

    /**
     * Saves the current game state to a new file with a timestamped name.
     * 
     * @throws IOException if an I/O error occurs during saving.
     * @throws ParseException if the date format is invalid.
     */
    public void SaveGame() throws IOException, ParseException {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-hh:mm:ss");

        String datetime = dateFormat.format(date);
        datetime = datetime.replaceAll(":", ".");

        String SavedGameName = "SavedGame" + datetime + ".txt";

        if (!SavedGameName.equals("IntialCharecterSave.txt")) {
            String SaveGameName = myGameSettings.SavedGameDirectory + SavedGameName;

            FileWriter writer = new FileWriter(SaveGameName);

            for (String Charinfo : myChar.CharInfo) {
                writer.write(Charinfo + System.lineSeparator());
            }
            writer.close();

            JOptionPane.showMessageDialog(null, "Game Saved: " + SavedGameName);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Unable to Save Current Game Over Saved Game called  'InitialCharecterSave.txt'\n");
        }
    }

    /**
     * Loads the character data from the most recently saved game file.
     * 
     * @throws IOException if an I/O error occurs during loading.
     */
    public void StartGameLoadCharecter() throws IOException {
        ArrayList<String> SaveLoadChar = new ArrayList<>();
        File chosenFile = getLastModified(myGameSettings.SavedGameDirectory);

        BufferedReader bufReader = new BufferedReader(new FileReader(chosenFile));

        String line = bufReader.readLine();
        while (line != null) {
            SaveLoadChar.add(line);
            line = bufReader.readLine();
        }

        Singleton.myCharSingleton().CharInfo.addAll(SaveLoadChar);

        bufReader.close();
    }

    /**
     * Quickly saves the current game state to a predefined quick-save file.
     * 
     * @throws IOException if an I/O error occurs during saving.
     */
    public void QuickSaveCharecter() throws IOException {
        String SavedGameName = "QuickSaveGame.Txt";

        File file = new File(myGameSettings.SavedGameDirectory + "QuickSaveGame.txt");

        if (file.exists()) {
            file.delete();
        }

        if (!SavedGameName.equals("IntialCharecterSave.txt")) {
            String AutoSaveGameName = myGameSettings.SavedGameDirectory + SavedGameName;

            FileWriter writer = new FileWriter(AutoSaveGameName);

            for (String Charinfo : myChar.CharInfo) {
                writer.write(Charinfo + System.lineSeparator());
            }
            writer.close();

            JOptionPane.showMessageDialog(null, "Game Saved: " + SavedGameName);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Unable to Save Current Game Over Saved Game called  'InitialCharecterSave.txt'\n");
        }
    }

    /**
     * Retrieves the most recently modified file from the specified directory.
     * 
     * @param SavedGameDirectory The directory containing saved game files.
     * @return The most recently modified file.
     */
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

    /**
     * Counts the number of files in the saved game directory.
     * 
     * @return The number of saved game files.
     */
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
