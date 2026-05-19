
package DungeonoftheBrutalKing.GameEngine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import DungeonoftheBrutalKing.SharedData.GameSettings;

public enum Texture {
    WOOD(GameSettings.getDungeonWallTexturePath() + "wood.png"),
    BRICK(GameSettings.getDungeonWallTexturePath() + "redbrick.png"),
    BLUESTONE(GameSettings.getDungeonWallTexturePath() + "bluestone.png"),
    STONE(GameSettings.getDungeonWallTexturePath() + "greystone.png"),
    STAIRS_UP(GameSettings.getDungeonStairsTexturePath() + "stairsup.png"),
    STAIRS_DOWN(GameSettings.getDungeonStairsTexturePath() + "stairsdown.png"),
    STAIRS_DOWN_GATE(GameSettings.getDungeonStairsTexturePath() + "downstairswithgate.png"),
    STAIRS_DOWN_GATE_TORCHES(GameSettings.getDungeonStairsTexturePath() + "downstairswithgateandtorches.png"),
    GREY_DUNGEON_DOOR(GameSettings.getDungeonDoorTexturePath() + "GreyDungeonDoor.png"),
    GREY_DUNGEON_WALL(GameSettings.getDungeonWallTexturePath() + "GreyDungeonWall.png");

    public static final int SIZE = 64;
    public final int[] pixels = new int[SIZE * SIZE];

    Texture(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            if (image.getWidth() != SIZE || image.getHeight() != SIZE) {
                BufferedImage resized = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = resized.createGraphics();
                g2d.drawImage(image, 0, 0, SIZE, SIZE, null);
                g2d.dispose();
                image = resized;
            }
            image.getRGB(0, 0, SIZE, SIZE, pixels, 0, SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
