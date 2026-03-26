
package SharedData;

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

public class GameSettings {


	//*********************************************************************************
	//--------------------------- Static Fields ---------------------------------------
	//*********************************************************************************
	public static String MenuBarImagePath, MonsterImagePath, FontPath, DungeonFloorTexturePath,
	DungeonWallTexturePath, StartMenuPath, StoryIntroductionPath, ClassImagesPath,
	SoundEffectsPath, SavedGameDirectory, NPCImagePath, DungeonStairsTexturePath,
	DungeonDoorTexturePath, RoomImagePath, QuestImagesPath;

	//*********************************************************************************
	//--------------------------- Instance Variables ----------------------------------
	//*********************************************************************************
	private Color colorBrown;

	private Color colorLightBrown;

	private Color colorLightYellow;

	private Color colorBlack;

	private Color colorWhite;

	private Color colorCoral;

	private Color colorGreen;

	private Color colorPurple;

	private Color colorBlue;

	private Color colorPlum;


	public static String RaceImagesPath;

	private Font fontTimesNewRoman, fontAvatar, fontWelcomeMessage;
	private BufferedImage myJMenuBarPicture, myStartMenuPicture;



	//*********************************************************************************
	//--------------------------- Constructor -----------------------------------------
	//*********************************************************************************
	public GameSettings() {
		initializeFilePaths();
		initializeColors();
		initializeFonts();
	}

	//*********************************************************************************
	//--------------------------- Initialization Methods ------------------------------
	//*********************************************************************************




	private void initializeFilePaths() {
		MenuBarImagePath = "MenuBar/";
		MonsterImagePath = "src/DungeonoftheBrutalKing/Images/Monsters/";
		FontPath = "src/DungeonoftheBrutalKing/Fonts/";
		DungeonFloorTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Floor/";
		DungeonWallTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Wall/";
		DungeonStairsTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Stairs/";
		DungeonDoorTexturePath = "src/DungeonoftheBrutalKing/Images/Level/Door/";
		SavedGameDirectory = "src/DungeonoftheBrutalKing/SaveGame";
		StoryIntroductionPath = "Messages/StoryIntroduction/";
		StartMenuPath = "Program/StartMenu/";
		ClassImagesPath = "src/DungeonoftheBrutalKing/Images/Classes/";
		SoundEffectsPath = "src/DungeonoftheBrutalKing/SoundEffects/"; // Updated path
		NPCImagePath = "src/DungeonoftheBrutalKing/Images/NPC/";
		RaceImagesPath = "src/DungeonoftheBrutalKing/Images/Race/";
		QuestImagesPath = "src/DungeonoftheBrutalKing/Images/Quests/";
	}





	private void initializeColors() {
		colorBrown = new Color(165, 42, 42);
		colorLightBrown = new Color(196, 164, 132);
		colorLightYellow = new Color(255, 255, 224);
		colorBlack = new Color(20, 20, 20);
		colorWhite = new Color(255, 255, 255);
		colorCoral = new Color(255, 127, 80);
		colorBrown = new Color(165, 42, 42);
		colorLightBrown = new Color(196, 164, 132);
		colorLightYellow = new Color(255, 255, 224);
		colorBlack = new Color(20, 20, 20);
		colorWhite = new Color(255, 255, 255);
		colorCoral = new Color(255, 127, 80);
		colorGreen = new Color(0, 128, 0);
		colorPurple = new Color(128, 0, 128);
		colorBlue = new Color(0, 0, 255);
		colorPlum = new Color(221, 160, 221);
	}

	public Color getColorGreen() {
		return colorGreen;
	}

	public Color getColorPurple() {
		return colorPurple;
	}

	private void initializeFonts() {
		fontTimesNewRoman = new Font("Times New Roman", Font.PLAIN, 20);
		fontAvatar = new Font(FontPath + "avatar.ttf", Font.PLAIN, 20);
		fontWelcomeMessage = new Font("Segoe Script", Font.BOLD, 20);
	}

	//*********************************************************************************
	//--------------------------- Utility Methods -------------------------------------
	//*********************************************************************************
	public BufferedImage loadImage(String fileName) {
		try {
			return ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void playMusic(String fileName) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
		File musicFile = new File(SoundEffectsPath + fileName);
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
	}

	//*********************************************************************************
	//--------------------------- Singleton Implementation ----------------------------
	//*********************************************************************************
	private static GameSettings instance;

	public static GameSettings getInstance() {
		if (instance == null) {
			instance = new GameSettings();
		}
		return instance;
	}

	//*********************************************************************************
	//--------------------------- Getters ---------------------------------------------
	//*********************************************************************************
	public static String getMenuBarImagePath() {
		return MenuBarImagePath;
	}

	public static String getMonsterImagePath() {
		return MonsterImagePath;
	}

	public static String getFontPath() {
		return FontPath;
	}
	
	public static String getQuestImagesPath() {
	    return QuestImagesPath;
	}

	public static String getDungeonFloorTexturePath() {
		return DungeonFloorTexturePath;
	}

	public static String getDungeonWallTexturePath() {
		return DungeonWallTexturePath;
	}

	public static String getStartMenuPath() {
		return StartMenuPath;
	}

	public static String getStoryIntroductionPath() {
		return StoryIntroductionPath;
	}

	public static String getClassImagesPath() {
		return ClassImagesPath;
	}

	public static String getSoundEffectsPath() {
		return SoundEffectsPath;
	}

	public static String getSavedGameDirectory() {
		return SavedGameDirectory;
	}

	public static String getNPCImagePath() {
		return NPCImagePath;
	}

	public Color getColorBrown() {
		return colorBrown;
	}

	public Color getColorLightBrown() {
		return colorLightBrown;
	}

	public Color getColorLightYellow() {
		return colorLightYellow;
	}

	public Color getColorBlack() {
		return colorBlack;
	}

	public Color getColorWhite() {
		return colorWhite;
	}

	public Color getColorCoral() {
		return colorCoral;
	}

	public Color getColorBlue() {
		return colorBlue;
	}


	public Color getColorPlum() {
		return colorPlum;
	}

	public Font getFontTimesNewRoman() {
		return fontTimesNewRoman;
	}

	public Font getFontAvatar() {
		return fontAvatar;
	}

	public Font getFontWelcomeMessage() {
		return fontWelcomeMessage;
	}

	public BufferedImage getMyJMenuBarPicture() {
		return myJMenuBarPicture;
	}

	public BufferedImage getMyStartMenuPicture() {
		return myStartMenuPicture;
	}


	public static String getRaceImagesPath() {
		return RaceImagesPath;
	}

	public static GameSettings Singleton() {
		// TODO Auto-generated method stub
		return null;
	}





}
