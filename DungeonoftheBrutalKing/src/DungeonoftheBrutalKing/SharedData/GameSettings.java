package DungeonoftheBrutalKing.SharedData;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

public class GameSettings {

    // Static file paths
    public static String MenuBarImagePath = "MenuBar/";
    public static String EnemiesImagePath = "src/DungeonoftheBrutalKing/Images/Enemies/";
    public static String FontPath = "src/DungeonoftheBrutalKing/Fonts/";
    public static String DungeonFloorTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Floor/";
    public static String DungeonWallTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Wall/";
    public static String DungeonStairsTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Stairs/";
    public static String DungeonDoorTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Door/";
    public static String SavedGameDirectory = "src/DungeonoftheBrutalKing/SaveGame";
    public static String StoryIntroductionPath = "Messages/StoryIntroduction/";
    public static String StartMenuPath = "Program/StartMenu/";
    public static String ClassImagesPath = "src/DungeonoftheBrutalKing/Images/Classes/";
    public static String SoundEffectsPath = "src/DungeonoftheBrutalKing/SoundEffects/";
    public static String NPCImagePath = "src/DungeonoftheBrutalKing/Images/NPC/";
    public static String RaceImagesPath = "src/DungeonoftheBrutalKing/Images/Race/";
    public static String QuestImagesPath = "src/DungeonoftheBrutalKing/Images/Quests/";
    public static String DungeonCeilingTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Ceiling/";
    
    
    // Instance colors
    private final Color colorBrown = new Color(165, 42, 42);
    private final Color colorLightBrown = new Color(196, 164, 132);
    private final Color colorLightYellow = new Color(255, 255, 224);
    private final Color colorBlack = new Color(20, 20, 20);
    private final Color colorWhite = new Color(255, 255, 255);
    private final Color colorCoral = new Color(255, 127, 80);
    private final Color colorGreen = new Color(0, 128, 0);
    private final Color colorPurple = new Color(128, 0, 128);
    private final Color colorBlue = new Color(0, 0, 255);
    private final Color colorPlum = new Color(221, 160, 221);

    // Fonts
    private Font fontTimesNewRoman;
    private Font fontAvatar;
    private Font fontWelcomeMessage;

    // Images (initialize as needed)
    private BufferedImage myJMenuBarPicture;
    private BufferedImage myStartMenuPicture;

    // Thread-safe singleton
    private static volatile GameSettings instance;

    public GameSettings() {
        initializeFonts();
    }

    public static GameSettings getInstance() {
        if (instance == null) {
            synchronized (GameSettings.class) {
                if (instance == null) {
                    instance = new GameSettings();
                }
            }
        }
        return instance;
    }

    // Font initialization with proper loading for custom font
    private void initializeFonts() {
        fontTimesNewRoman = new Font("Times New Roman", Font.PLAIN, 20);
        try (InputStream is = new File(FontPath + "avatar.ttf").exists()
                ? new java.io.FileInputStream(FontPath + "avatar.ttf")
                : null) {
            if (is != null) {
                fontAvatar = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 20f);
            } else {
                fontAvatar = new Font("SansSerif", Font.PLAIN, 20);
            }
        } catch (FontFormatException | IOException e) {
            fontAvatar = new Font("SansSerif", Font.PLAIN, 20);
        }
        fontWelcomeMessage = new Font("Segoe Script", Font.BOLD, 20);
    }

    // Utility methods
    public BufferedImage loadImage(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) return null;
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void playMusic(String fileName) {
        File musicFile = new File(SoundEffectsPath + fileName);
        if (!musicFile.exists()) return;
        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Color getters
    public Color getColorBrown() { return colorBrown; }
    public Color getColorLightBrown() { return colorLightBrown; }
    public Color getColorLightYellow() { return colorLightYellow; }
    public Color getColorBlack() { return colorBlack; }
    public Color getColorWhite() { return colorWhite; }
    public Color getColorCoral() { return colorCoral; }
    public Color getColorGreen() { return colorGreen; }
    public Color getColorPurple() { return colorPurple; }
    public Color getColorBlue() { return colorBlue; }
    public Color getColorPlum() { return colorPlum; }

    // Font getters
    public Font getFontTimesNewRoman() { return fontTimesNewRoman; }
    public Font getFontAvatar() { return fontAvatar; }
    public Font getFontWelcomeMessage() { return fontWelcomeMessage; }

    // Image getters
    public BufferedImage getMyJMenuBarPicture() { return myJMenuBarPicture; }
    public BufferedImage getMyStartMenuPicture() { return myStartMenuPicture; }

    // Path getters
    public static String getMenuBarImagePath() { return MenuBarImagePath; }
    public static String getMonsterImagePath() { return EnemiesImagePath; }
    public static String getFontPath() { return FontPath; }
    public static String getQuestImagesPath() { return QuestImagesPath; }
    public static String getDungeonFloorTexturePath() { return DungeonFloorTexturePath; }
    public static String getDungeonWallTexturePath() { return DungeonWallTexturePath; }
    public static String getDungeonStairsTexturePath() { return DungeonStairsTexturePath; }
    public static String getDungeonDoorTexturePath() { return DungeonDoorTexturePath; }
    public static String getStartMenuPath() { return StartMenuPath; }
    public static String getStoryIntroductionPath() { return StoryIntroductionPath; }
    public static String getClassImagesPath() { return ClassImagesPath; }
    public static String getSoundEffectsPath() { return SoundEffectsPath; }
    public static String getSavedGameDirectory() { return SavedGameDirectory; }
    public static String getNPCImagePath() { return NPCImagePath; }
    public static String getRaceImagesPath() { return RaceImagesPath; }

    public static String getDungeonCeilingTexturePath() {
        return DungeonCeilingTexturePath;
    }



}


