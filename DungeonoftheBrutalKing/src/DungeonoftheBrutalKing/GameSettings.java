
package DungeonoftheBrutalKing;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The GameSettings class manages various settings and resources for the game,
 * including colors, fonts, images, and sound effects. It provides utility
 * methods to load and access these resources.
 */
public class GameSettings {

    // Color definitions for various UI elements
    Color colorBrown, colorLightBrown, colorLightYellow, colorBlack, colorWhite,
        colorLightGreen, colorVeryLightRed, colorVeryDarkBlue, colorVeryLightBlue, colorMagenta,
        colorAqua, colorOlive, colorTeal, colorLightSalmon, colorFireBrick, colorMaroon,
        colorCoral, colorIndianRed, colorDarkGoldenRod, colorGoldenRod, colorPaleGoldenRod,
        colorDarkKhaki, colorMediumBlue, colorBlue, colorPurple, colorGreen, colorPlum, colorGrey = null;

    // Font definitions for various text elements
    Font fontTimesNewRoman, fontLomoCopyLTStdMidi, fontWelcomeMessage,
        fontWelcomeMessage2, fontAvatar = null;

    // Buffered images for UI components
    BufferedImage myJMenuBarPicture, myStartMenuPicture = null;

    // File paths for various resources
    public static String MenuBarImagePath, MonsterImagePath, FontPath,
        DungeonFloorTexturePath, DungeonWallTexturePath,
        StartMenuPath, StoryIntroductionPath, ClassImagesPath,
        SoundEffectsPath, SavedGameDirectory, NPCImagePath = null;

    /**
     * Constructor initializes file paths, colors, and fonts used in the game.
     */
    public GameSettings() {

        //*********************************************************************************
        //--------------------------- File Locations --------------------------------------
        //*********************************************************************************

        // Initialize file paths for images, fonts, and other resources
        MenuBarImagePath = "src\\DungeonoftheBrutalKing\\Images\\Program\\MenuBar\\";
        MonsterImagePath = "src\\DungeonoftheBrutalKing\\Images\\Monsters\\";
        FontPath = "src\\DungeonoftheBrutalKing\\Fonts\\";
        DungeonFloorTexturePath = "src\\DungeonoftheBrutalKing\\Images\\Level\\Floor\\";
        DungeonWallTexturePath = "src\\DungeonoftheBrutalKing\\Images\\Level\\Door\\";
        SavedGameDirectory = "src\\DungeonoftheBrutalKing\\SaveGame\\";
        StoryIntroductionPath = "src\\DungeonoftheBrutalKing\\Images\\Messages\\StoryIntroduction\\";
        StartMenuPath = "src\\DungeonoftheBrutalKing\\Images\\Program\\StartMenu\\";
        ClassImagesPath = "src\\DungeonoftheBrutalKing\\Images\\Classes\\";
        SoundEffectsPath = "src\\DungeonoftheBrutalKing\\\\SoundEffects\\";
        NPCImagePath = "src\\DungeonoftheBrutalKing\\Images\\NPC\\";

        //*********************************************************************************
        //--------------------------- Colors ----------------------------------------------
        //*********************************************************************************

        // Initialize colors for UI elements
        colorBrown = new Color(165, 42, 42);
        colorLightBrown = new Color(196, 164, 132);
        colorLightYellow = new Color(255, 255, 224);
        colorBlack = new Color(20, 20, 20);
        colorWhite = new Color(255, 255, 255);
        colorLightGreen = new Color(102, 255, 102);
        colorVeryLightRed = new Color(153, 0, 0);
        colorVeryDarkBlue = new Color(0, 0, 153);
        colorVeryLightBlue = new Color(51, 204, 255);
        colorAqua = new Color(0, 255, 255);
        colorMagenta = new Color(255, 0, 255);
        colorOlive = new Color(128, 128, 0);
        colorTeal = new Color(0, 128, 128);
        colorLightSalmon = new Color(255, 160, 122);
        colorFireBrick = new Color(178, 34, 34);
        colorMaroon = new Color(128, 0, 0);
        colorCoral = new Color(255, 127, 80);
        colorIndianRed = new Color(205, 92, 92);
        colorDarkGoldenRod = new Color(184, 134, 11);
        colorGoldenRod = new Color(218, 165, 32);
        colorPaleGoldenRod = new Color(238, 232, 170);
        colorDarkKhaki = new Color(189, 183, 107);
        colorMediumBlue = new Color(0, 0, 205);
        colorBlue = new Color(0, 128, 255);
        colorPurple = new Color(153, 51, 255);
        colorGreen = new Color(0, 204, 102);
        colorPlum = new Color(221, 160, 221);
        colorGrey = new Color(128, 128, 128);

        //*********************************************************************************
        //--------------------------- Fonts ----------------------------------------------
        //*********************************************************************************

        // Initialize fonts for text elements
        fontTimesNewRoman = new Font("Times New Roman", Font.PLAIN, 20);
        fontAvatar = new Font(FontPath + "avatar.ttf", Font.PLAIN, 20);
        fontLomoCopyLTStdMidi = new Font(FontPath + "LomoCopyLTSdMidi.ttf", Font.PLAIN, 20);
        fontWelcomeMessage = new Font("Segoe Script", Font.BOLD, 20);
        fontWelcomeMessage2 = new Font(FontPath + "DragonHunter-9Ynxj.otf", Font.PLAIN, 20);
    }

    //*********************************************************************************
    //--------------------------- Pictures --------------------------------------------
    //*********************************************************************************

    /**
     * Loads an image from the specified file path.
     * 
     * @param fileName The path to the image file.
     * @return The loaded BufferedImage, or null if an error occurs.
     */
    public BufferedImage loadImage(String fileName) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace(); // Print error details if image loading fails
        }
        return image;
    }

    /**
     * Loads all required images for the game.
     */
    public void loadImages() {
        myJMenuBarPicture = loadImage(MenuBarImagePath + "MenuBar.png");
        myStartMenuPicture = loadImage(StartMenuPath + "StartMenu.png");
    }

    //*********************************************************************************
    //--------------------------- Music --------------------------------------------
    //*********************************************************************************

    /**
     * Plays a music file from the specified file path.
     * 
     * @param fileName The name of the music file to play.
     * @throws LineUnavailableException If the audio line is unavailable.
     * @throws UnsupportedAudioFileException If the audio file format is unsupported.
     * @throws IOException If an I/O error occurs while loading the file.
     */
    public void playMusic(String fileName) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        File musicFile = new File("//src//DungeonoftheBrutalKing//SoundEffects//" + fileName);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
        Clip clip = AudioSystem.getClip();
        try {
            clip.open(audioStream); // Open the audio stream
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace(); // Print error details if audio loading fails
        }
        clip.start(); // Start playing the audio
    }

    /**
     * Returns a singleton instance of the GameSettings class.
     * 
     * @return The singleton instance of GameSettings.
     */
    public static GameSettings Singleton() {
        // TODO: Implement singleton pattern
        return null;
    }
}
