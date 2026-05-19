
package DungeonoftheBrutalKing.GameEngine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import DungeonoftheBrutalKing.SharedData.GameSettings;

public class Texture {
    public int[] pixels;
    private String loc;
    public final int SIZE;

    public Texture(String location, int size) {
        this.loc = location;
        this.SIZE = size;
        this.pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            if (w != SIZE || h != SIZE) {
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

    public static final Texture wood = new Texture(GameSettings.getDungeonWallTexturePath() + "wood.png", 64);
    public static final Texture brick = new Texture(GameSettings.getDungeonWallTexturePath() + "redbrick.png", 64);
    public static final Texture bluestone = new Texture(GameSettings.getDungeonWallTexturePath() + "bluestone.png", 64);
    public static final Texture stone = new Texture(GameSettings.getDungeonWallTexturePath() + "greystone.png", 64);
    public static final Texture stairsup = new Texture(GameSettings.getDungeonStairsTexturePath() + "stairsup.png", 64);
    public static final Texture stairsdown = new Texture(GameSettings.getDungeonStairsTexturePath() + "stairsdown.png", 64);
    public static final Texture stairsdownwithgate = new Texture(GameSettings.getDungeonStairsTexturePath() + "downstairswithgate.png", 64);
    public static final Texture downstairsdownwithgateandtorches = new Texture(GameSettings.getDungeonStairsTexturePath() + "downstairswithgateandtorches.png", 64);
    public static final Texture GreyDungeonDoor = new Texture(GameSettings.getDungeonDoorTexturePath() + "GreyDungeonDoor.png", 64);
    public static final Texture GreyDungeonWall = new Texture(GameSettings.getDungeonWallTexturePath() + "GreyDungeonWall.png", 64);
}
