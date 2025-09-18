package DungeonoftheBrutalKing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import SharedData.GameSettings;

public class LoadSaveGame2 {

    Charecter myChar = Charecter.Singleton();
    GameSettings myGameSettings = GameSettings.Singleton();

    public void saveGameWithEncryption() throws IOException {
        String savedGameName = "EncryptedGameSave.txt";
        String saveFilePath = GameSettings.SavedGameDirectory + savedGameName;

        FileWriter writer = new FileWriter(saveFilePath);

        ArrayList<String> combinedData = new ArrayList<>();
        combinedData.addAll(myChar.getCharInfo());
        combinedData.addAll(myChar.getSpellsLearned());

        for (String data : combinedData) {
            String encryptedData = EncryptionUtil.encrypt(data);
            writer.write(encryptedData + System.lineSeparator());
        }

        writer.close();
        JOptionPane.showMessageDialog(null, "Game Saved: " + savedGameName);
    }

    public void loadGameWithDecryption() throws IOException {
        String savedGameName = "EncryptedGameSave.txt";
        String saveFilePath = GameSettings.SavedGameDirectory + savedGameName;

        BufferedReader reader = new BufferedReader(new FileReader(saveFilePath));
        ArrayList<String> decryptedData = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            String decryptedLine = EncryptionUtil.decrypt(line);
            decryptedData.add(decryptedLine);
        }

        reader.close();

        myChar.getCharInfo().clear();
        myChar.getSpellsLearned().clear();
        for (int i = 0; i < decryptedData.size(); i++) {
            if (i < myChar.getCharInfo().size()) {
                myChar.getCharInfo().add(decryptedData.get(i));
            } else {
                myChar.getSpellsLearned().add(decryptedData.get(i));
            }
        }

        JOptionPane.showMessageDialog(null, "Game Loaded Successfully");
    }

    public void AutoSaveGame() throws IOException {
        String savedGameName = "AutoGameSave.txt";
        String autoSaveGamePath = GameSettings.SavedGameDirectory + savedGameName;

        try (FileWriter writer = new FileWriter(autoSaveGamePath)) {
            for (String charInfo : myChar.getCharInfo()) {
                String encryptedData = EncryptionUtil.encrypt(charInfo);
                writer.write(encryptedData + System.lineSeparator());
            }
        }

        JOptionPane.showMessageDialog(null, "Game Auto-Saved: " + savedGameName);
    }

    public void AutoLoadGame() throws IOException {
        String savedGameName = "AutoGameSave.txt";
        String autoSaveGamePath = GameSettings.SavedGameDirectory + savedGameName;

        try (BufferedReader reader = new BufferedReader(new FileReader(autoSaveGamePath))) {
            myChar.getCharInfo().clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String decryptedData = EncryptionUtil.decrypt(line);
                myChar.getCharInfo().add(decryptedData);
            }
        }

        JOptionPane.showMessageDialog(null, "Game Auto-Loaded Successfully");
    }

    public void StartGameLoadCharecter() throws IOException {
        ArrayList<String> SaveLoadChar = new ArrayList<>();
        File chosenFile = getLastModified(GameSettings.SavedGameDirectory);

        try (BufferedReader bufReader = new BufferedReader(new FileReader(chosenFile))) {
            String line = bufReader.readLine();
            while (line != null) {
                String decryptedData = EncryptionUtil.decrypt(line);
                SaveLoadChar.add(decryptedData);
                line = bufReader.readLine();
            }
        }

        myChar.getCharInfo().addAll(SaveLoadChar);

        JOptionPane.showMessageDialog(null, "Game Loaded Successfully from: " + chosenFile.getName());
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
            for (File ignored : files) {
                count++;
            }
        }

        return count;
    }
}
