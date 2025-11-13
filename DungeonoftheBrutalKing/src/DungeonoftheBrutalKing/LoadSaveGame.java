
// src/DungeonoftheBrutalKing/LoadSaveGame.java
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
import SharedData.EncryptionUtil;

public class LoadSaveGame {

    Charecter myChar = Charecter.getInstance();
    int width, height = 0;

    public void AutoSaveGame() throws IOException {
        String savedGameName = "AutoGameSave.Txt";
        String autoSaveGamePath = GameSettings.SavedGameDirectory + savedGameName;
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

    public void SaveGame() throws IOException, ParseException {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String datetime = dateFormat.format(date).replaceAll(":", ".");
        String SavedGameName = "SavedGame" + datetime + ".txt";
        if (!SavedGameName.equals("IntialCharecterSave.txt")) {
            String SaveGameName = GameSettings.SavedGameDirectory + SavedGameName;
            try (FileWriter writer = new FileWriter(SaveGameName)) {
                saveAllEncrypted(writer);
            }
            JOptionPane.showMessageDialog(null, "Game Saved: " + SavedGameName);
        } else {
            JOptionPane.showMessageDialog(null,
                "Unable to Save Current Game Over Saved Game called 'InitialCharecterSave.txt'\n");
        }
    }

    private void saveAllEncrypted(FileWriter writer) throws IOException {
        StringBuilder sb = new StringBuilder();
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
        String userName = myChar.getName();
        String salt = EncryptionUtil.generateSalt();
        try {
            String encrypted = EncryptionUtil.encrypt(sb.toString(), userName, salt);
            writer.write(salt + System.lineSeparator() + encrypted);
        } catch (Exception e) {
            throw new IOException("Encryption failed: " + e.getMessage(), e);
        }
    }

    public void StartGameLoadCharecter() throws IOException {
        File chosenFile = getLastModified(GameSettings.SavedGameDirectory);
        if (chosenFile == null || !chosenFile.exists()) {
            JOptionPane.showMessageDialog(null, "No valid save file found to load the character.");
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
            if (gameInfo.equals("InitialCharecterSave.txt")) {
                int response = JOptionPane.showConfirmDialog(null,
                    "This will reload the original saved game and restart your character",
                    "Reload Save Game", JOptionPane.YES_NO_OPTION);
                if (response != JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Please Choose a Different Saved Game File");
                    return;
                }
            }
            try {
                loadAllEncrypted(new File(GameSettings.SavedGameDirectory + gameInfo));
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
                throw new IOException("Decryption failed: Invalid encrypted data format (missing salt)");
            }
            String line;
            while ((line = reader.readLine()) != null) {
                encryptedContent.append(line).append(System.lineSeparator());
            }
        }
        if (encryptedContent.length() == 0) {
            throw new IOException("Decryption failed: Invalid encrypted data format (empty content)");
        }
        String userName = myChar.getName();
        String decrypted;
        try {
            decrypted = EncryptionUtil.decrypt(encryptedContent.toString().trim(), userName, salt);
        } catch (Exception e) {
            throw new IOException("Decryption failed: " + e.getMessage(), e);
        }
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
