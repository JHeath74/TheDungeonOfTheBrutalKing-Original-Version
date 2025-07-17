
// Import necessary classes for file handling, I/O operations, and GUI components
package DungeonoftheBrutalKing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import SharedData.GameSettings;

// Class to manage saving and loading game states with encryption and decryption
public class LoadSaveGame2 {

    // Singleton instance of the character
    Charecter myChar = Charecter.Singleton();

    // Singleton instance of game settings
    GameSettings myGameSettings = GameSettings.Singleton();

    // Method to save the game state with encryption
    public void saveGameWithEncryption() throws IOException {
        String savedGameName = "EncryptedGameSave.txt"; // Name of the save file
        String saveFilePath = GameSettings.SavedGameDirectory + savedGameName; // Full path to the save file

        FileWriter writer = new FileWriter(saveFilePath); // Create a FileWriter to write to the file

        // Combine data from multiple ArrayLists
        ArrayList<String> combinedData = new ArrayList<>();
        combinedData.addAll(myChar.CharInfo); // Add character info
        combinedData.addAll(myChar.SpellsLearned); // Add learned spells

        // Encrypt and write each line to the file
        for (String data : combinedData) {
            String encryptedData = EncryptionUtil.encrypt(data); // Encrypt the data
            writer.write(encryptedData + System.lineSeparator()); // Write encrypted data to the file
        }

        writer.close(); // Close the FileWriter
        JOptionPane.showMessageDialog(null, "Game Saved: " + savedGameName); // Notify the user
    }

    // Method to load the game state with decryption
    public void loadGameWithDecryption() throws IOException {
        String savedGameName = "EncryptedGameSave.txt"; // Name of the save file
        String saveFilePath = GameSettings.SavedGameDirectory + savedGameName; // Full path to the save file

        BufferedReader reader = new BufferedReader(new FileReader(saveFilePath)); // Create a BufferedReader to read the file
        ArrayList<String> decryptedData = new ArrayList<>(); // List to store decrypted data

        String line;
        while ((line = reader.readLine()) != null) { // Read each line from the file
            String decryptedLine = EncryptionUtil.decrypt(line); // Decrypt the line
            decryptedData.add(decryptedLine); // Add decrypted data to the list
        }

        reader.close(); // Close the BufferedReader

        // Populate ArrayLists with decrypted data
        myChar.CharInfo.clear(); // Clear existing character info
        myChar.SpellsLearned.clear(); // Clear existing learned spells
        for (int i = 0; i < decryptedData.size(); i++) {
            if (i < myChar.CharInfo.size()) {
                myChar.CharInfo.add(decryptedData.get(i)); // Add to character info
            } else {
                myChar.SpellsLearned.add(decryptedData.get(i)); // Add to learned spells
            }
        }

        JOptionPane.showMessageDialog(null, "Game Loaded Successfully"); // Notify the user
    }

    // Method to auto-save the game state
    public void AutoSaveGame() throws IOException {
        String savedGameName = "AutoGameSave.txt"; // Name of the auto-save file
        String autoSaveGamePath = GameSettings.SavedGameDirectory + savedGameName; // Full path to the auto-save file

        try (FileWriter writer = new FileWriter(autoSaveGamePath)) { // Create a FileWriter to write to the file
            for (String charInfo : myChar.CharInfo) {
                String encryptedData = EncryptionUtil.encrypt(charInfo); // Encrypt the character info
                writer.write(encryptedData + System.lineSeparator()); // Write encrypted data to the file
            }
        }

        JOptionPane.showMessageDialog(null, "Game Auto-Saved: " + savedGameName); // Notify the user
    }

    // Method to auto-load the game state
    public void AutoLoadGame() throws IOException {
        String savedGameName = "AutoGameSave.txt"; // Name of the auto-save file
        String autoSaveGamePath = GameSettings.SavedGameDirectory + savedGameName; // Full path to the auto-save file

        try (BufferedReader reader = new BufferedReader(new FileReader(autoSaveGamePath))) { // Create a BufferedReader to read the file
            myChar.CharInfo.clear(); // Clear existing character info
            String line;
            while ((line = reader.readLine()) != null) { // Read each line from the file
                String decryptedData = EncryptionUtil.decrypt(line); // Decrypt the line
                myChar.CharInfo.add(decryptedData); // Add decrypted data to character info
            }
        }

        JOptionPane.showMessageDialog(null, "Game Auto-Loaded Successfully"); // Notify the user
    }

    // Method to load the character data from the most recently modified save file
    public void StartGameLoadCharecter() throws IOException {
        ArrayList<String> SaveLoadChar = new ArrayList<>(); // List to store loaded character data
        File chosenFile = getLastModified(GameSettings.SavedGameDirectory); // Get the most recently modified file

        try (BufferedReader bufReader = new BufferedReader(new FileReader(chosenFile))) { // Create a BufferedReader to read the file
            String line = bufReader.readLine();
            while (line != null) { // Read each line from the file
                String decryptedData = EncryptionUtil.decrypt(line); // Decrypt the line
                SaveLoadChar.add(decryptedData); // Add decrypted data to the list
                line = bufReader.readLine();
            }
        }

        myChar.CharInfo.addAll(SaveLoadChar); // Add loaded data to character info

        JOptionPane.showMessageDialog(null, "Game Loaded Successfully from: " + chosenFile.getName()); // Notify the user
    }

    // Utility method to get the most recently modified file in a directory
    public static File getLastModified(String SavedGameDirectory) {
        File directory = new File(SavedGameDirectory); // Create a File object for the directory
        File[] files = directory.listFiles(File::isFile); // Get all files in the directory
        long lastModifiedTime = Long.MIN_VALUE; // Initialize the last modified time
        File chosenFile = null; // Initialize the chosen file

        if (files != null) {
            for (File file : files) { // Iterate through the files
                if (file.lastModified() > lastModifiedTime) { // Check if the file is more recently modified
                    chosenFile = file; // Update the chosen file
                    lastModifiedTime = file.lastModified(); // Update the last modified time
                }
            }
        }

        return chosenFile; // Return the most recently modified file
    }

    // Utility method to count the number of files in a directory
    public static int getFileCount() {
        File directory = new File(GameSettings.SavedGameDirectory); // Create a File object for the directory
        File[] files = directory.listFiles(File::isFile); // Get all files in the directory
        int count = 0; // Initialize the file count

        if (files != null) {
            for (File file : files) { // Iterate through the files
                count++; // Increment the file count
            }
        }

        return count; // Return the file count
    }
}
