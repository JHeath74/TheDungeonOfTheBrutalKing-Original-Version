
package DungeonoftheBrutalKing;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import DungeonoftheBrutalKing.Quests.Quest;
import DungeonoftheBrutalKing.Quests.QuestImpl;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import DungeonoftheBrutalKing.SharedData.GuildType;

public class LoadSaveGame2 {

    Character myChar = Character.getInstance();
    int width, height = 0;

    public void AutoSaveGame() throws IOException {
        String savedGameName = "AutoGameSave.Txt";
        String autoSaveGamePath = GameSettings.SavedGameDirectory + File.separator + savedGameName;
        if (!savedGameName.equals("InitialCharecterSave.txt")) {
            try (FileWriter writer = new FileWriter(autoSaveGamePath)) {
                saveAll(writer);
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
            saveFileName = fileName;
        } else {
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            String datetime = dateFormat.format(date).replaceAll(":", ".");
            saveFileName = "SavedGame" + datetime + ".txt";
        }
        String fullPath = GameSettings.SavedGameDirectory + File.separator + saveFileName;
        try (FileWriter writer = new FileWriter(fullPath)) {
            saveAll(writer);
        }
        JOptionPane.showMessageDialog(null, "Game Saved: " + saveFileName);
    }

    public void StartGameLoadCharacter() throws IOException {
        File chosenFile = getLastModified(GameSettings.SavedGameDirectory);
        if (chosenFile == null || !chosenFile.exists()) {
            JOptionPane.showMessageDialog(null, "No valid save file found to load the charecter.");
            return;
        }
        loadAll(chosenFile);

        myChar.getDirection();

        ArrayList<String> charInfo = myChar.getCharInfo();
        StringBuilder info = new StringBuilder("Character Info:\n");
        for (int i = 0; i < charInfo.size(); i++) {
            info.append("[").append(i).append("]: ").append(charInfo.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(null, info.toString());
    }

    public void ContinueCurrentGame() throws IOException, InterruptedException, ParseException {
        File chosenFile = getLastModified(GameSettings.SavedGameDirectory);
        if (chosenFile == null || !chosenFile.exists()) {
            JOptionPane.showMessageDialog(null, "No valid save file found to continue the game.");
            return;
        }
        loadAll(chosenFile);
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
            if (gameInfo == null) {
                JOptionPane.showMessageDialog(null, "No game selected.");
                return;
            }
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
                loadAll(new File(GameSettings.SavedGameDirectory + File.separator + gameInfo));
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

    void saveAll(FileWriter writer) throws IOException {
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
        for (Map.Entry<GuildType, GuildMembershipStatus> entry : myChar.getGuildStatusMap().entrySet()) {
            sb.append("GUILDINFO:")
              .append(entry.getKey().name())
              .append(":")
              .append(entry.getValue().name())
              .append(System.lineSeparator());
        }

        if (sb.length() == 0) {
            throw new IOException("No data to save.");
        }

        writer.write(sb.toString());
        writer.flush();
    }

    private void loadAll(File file) throws IOException {
        myChar.getCharInfo().clear();
        myChar.getSpellsLearned().clear();
        myChar.getGuildSpells().clear();
        myChar.getCharInventory().clear();
        myChar.getActiveQuests().clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
                } else if (line.startsWith("GUILDINFO:")) {
                    String[] parts = line.substring(10).split(":");
                    if (parts.length == 2) {
                        GuildType guildType = GuildType.valueOf(parts[0]);
                        GuildMembershipStatus status = GuildMembershipStatus.valueOf(parts[1]);
                        myChar.getGuildStatusMap().put(guildType, status);
                    }
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

    public void saveAll(ArrayList<String> data, String filename) throws IOException {
        String filePath = GameSettings.SavedGameDirectory + File.separator + filename;
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String line : data) {
                writer.write(line + System.lineSeparator());
            }
        }
    }

    public void setCharecterData(ArrayList<String> saveData) {
        myChar.getCharInfo().clear();
        myChar.getCharInfo().addAll(saveData);
    }
}
