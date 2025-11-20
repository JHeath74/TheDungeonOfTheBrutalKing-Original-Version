
package DungeonoftheBrutalKing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import Quests.Quest;
import Quests.QuestImpl;
import SharedData.GameSettings;
import SharedData.KeyManager;
import SharedData.EncryptionUtil;

public class LoadSaveGame {

    Character myChar = Character.getInstance();
    int width, height = 0;

    public void AutoSaveGame() throws IOException {
        String savedGameName = "AutoGameSave.Txt";
        String autoSaveGamePath = GameSettings.SavedGameDirectory + File.separator + savedGameName;
        if (!savedGameName.equals("IntialCharecterSave.txt")) {
            try (FileWriter writer = new FileWriter(autoSaveGamePath)) {
                saveAllEncrypted(writer);
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


public void SaveGame(String fileName) throws IOException, ParseException {
    String saveFileName;
    if ("InitialCharecterSave.txt".equals(fileName)) {
        // Always use saveAllEncrypted to ensure prefixes are added
        saveFileName = fileName;
    } else {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String datetime = dateFormat.format(date).replaceAll(":", ".");
        saveFileName = "SavedGame" + datetime + ".txt";
    }
    String fullPath = GameSettings.SavedGameDirectory + File.separator + saveFileName;
    try (FileWriter writer = new FileWriter(fullPath)) {
        saveAllEncrypted(writer); // This method adds prefixes to every line
    }
    JOptionPane.showMessageDialog(null, "Game Saved: " + saveFileName);
}


  

    public void StartGameLoadCharacter() throws IOException {
    	
    	
    	
        File chosenFile = getLastModified(GameSettings.SavedGameDirectory);
        if (chosenFile == null || !chosenFile.exists()) {
            JOptionPane.showMessageDialog(null, "No valid save file found to load the charecter.");
            return;
        }
        loadAllEncrypted(chosenFile);
        myChar.getDirection();
    }

    public void ContinueCurrentGame() throws IOException, InterruptedException, ParseException {
        File chosenFile = getLastModified(GameSettings.SavedGameDirectory);
        if (chosenFile == null || !chosenFile.exists()) {
            JOptionPane.showMessageDialog(null, "No valid save file found to continue the game.");
            return;
        }
        loadAllEncrypted(chosenFile);
        MainGameScreen.getInstance();
    }

    public void LoadGame() {
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
            if (gameInfo.equals("InitialCharacterSave.txt")) {
                int response = JOptionPane.showConfirmDialog(null,
                    "This will reload the original saved game and restart your character",
                    "Reload Save Game", JOptionPane.YES_NO_OPTION);
                if (response != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Please Choose a Different Saved Game File");
                    return;
                }
            }
            try {
                loadAllEncrypted(new File(GameSettings.SavedGameDirectory + File.separator + gameInfo));
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



 // In LoadSaveGame.java



void saveAllEncrypted(FileWriter writer) throws IOException {
    StringBuilder sb = new StringBuilder();

    // Collect all character data with prefixes
    for (String info : myChar.getCharInfo()) {
        sb.append("CHARINFO:").append(info).append(System.lineSeparator());
    }
    for (String spell : myChar.getSpellsLearned()) {
        sb.append("SPELL:").append(spell).append(System.lineSeparator());
    }
    for (String guildSpell : myChar.getGuildSpells()) {
        sb.append("GUILDSPELL:").append(guildSpell).append(System.lineSeparator());
    }
    for (String item : myChar.getCharInventory()) {
        sb.append("INVENTORY:").append(item).append(System.lineSeparator());
    }
    for (Quest quest : myChar.getActiveQuests()) {
        sb.append("QUEST:").append(quest.serialize()).append(System.lineSeparator());
    }

    // Debug: print what will be saved
    System.out.println("Saving data (before encryption):");
    System.out.println(sb);

    if (sb.length() == 0) {
        throw new IOException("No data to save.");
    }

    String salt = EncryptionUtil.generateSalt();
    String encryptionKey = KeyManager.getOrCreateKey();
    try {
        String encrypted = EncryptionUtil.encrypt(sb.toString(), encryptionKey, salt);
        writer.write(salt + System.lineSeparator());
        writer.write(encrypted);
        writer.flush();
    } catch (Exception e) {
        throw new IOException("Encryption failed: " + e.getMessage(), e);
    }
}


 private void loadAllEncrypted(File file) throws IOException {
     myChar.getCharInfo().clear();
     myChar.getSpellsLearned().clear();
     myChar.getGuildSpells().clear();
     myChar.getCharInventory().clear();
     myChar.getActiveQuests().clear();

     String salt;
     StringBuilder encryptedContent = new StringBuilder();
     try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
         salt = reader.readLine();
         if (salt == null || salt.isEmpty()) {
             throw new IOException("Invalid save file: missing salt");
         }
         String line;
         while ((line = reader.readLine()) != null) {
             encryptedContent.append(line);
         }
     }
     if (encryptedContent.length() == 0) {
         throw new IOException("Invalid save file: empty encrypted content");
     }
     String encryptionKey = KeyManager.getOrCreateKey();
     String decrypted;
     try {
         decrypted = EncryptionUtil.decrypt(encryptedContent.toString().trim(), encryptionKey, salt);
     } catch (Exception e) {
         throw new IOException("Decryption failed: " + e.getMessage(), e);
     }

     // Print what was loaded (after decryption)
     System.out.println("Loaded data (after decryption):");
     System.out.println(decrypted);

     try (BufferedReader reader = new BufferedReader(new StringReader(decrypted))) {
         String line;
         while ((line = reader.readLine()) != null) {
             if (line.startsWith("CHARINFO:")) {
                 myChar.getCharInfo().add(line.substring(9));
             } else if (line.startsWith("SPELL:")) {
                 myChar.getSpellsLearned().add(line.substring(6));
             } else if (line.startsWith("GUILDSPELL:")) {
                 myChar.getGuildSpells().add(line.substring(11));
             } else if (line.startsWith("INVENTORY:")) {
                 myChar.getCharInventory().add(line.substring(10));
             } else if (line.startsWith("QUEST:")) {
                 myChar.getActiveQuests().add(QuestImpl.deserialize(line.substring(6)));
             }
         }
     }

     // Print the loaded collections for verification
     System.out.println("charInfo: " + myChar.getCharInfo());
     System.out.println("spellsLearned: " + myChar.getSpellsLearned());
     System.out.println("guildSpells: " + myChar.getGuildSpells());
     System.out.println("charInventory: " + myChar.getCharInventory());
     System.out.println("activeQuests: " + myChar.getActiveQuests());
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

    public void saveAllEncrypted(ArrayList<String> data, String filename) throws IOException {
        String filePath = GameSettings.SavedGameDirectory + File.separator + filename;
        StringBuilder sb = new StringBuilder();
        for (String line : data) {
            sb.append(line).append(System.lineSeparator());
        }
        String salt = EncryptionUtil.generateSalt();
        String encryptionKey = KeyManager.getOrCreateKey();
        try (FileWriter writer = new FileWriter(filePath)) {
            String encrypted = EncryptionUtil.encrypt(sb.toString(), encryptionKey, salt);
            writer.write(salt + System.lineSeparator());
            writer.write(encrypted);
        } catch (Exception e) {
            throw new IOException("Encryption failed: " + e.getMessage(), e);
        }
    }

    public static void readSaveFileDemo() {
        File file = new File(GameSettings.SavedGameDirectory + "InitialCharecterSave.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String salt = reader.readLine();
            StringBuilder encryptedContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                encryptedContent.append(line);
            }
            String encryptionKey = KeyManager.getOrCreateKey();
            String decrypted = EncryptionUtil.decrypt(encryptedContent.toString().trim(), encryptionKey, salt);
            System.out.println(decrypted); // Print the decrypted content
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	public void setCharecterData(ArrayList<String> saveData) {
		 myChar.getCharInfo().clear();
		    myChar.getCharInfo().addAll(saveData);
		
	}
    
    
}
