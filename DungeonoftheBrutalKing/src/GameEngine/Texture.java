
package GameEngine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import SharedData.GameSettings;

public class Texture {
    public int[] pixels;
    private String loc;
    public final int SIZE;

    public Texture(String location, int size) {
        loc = location;
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            if (w != SIZE || h != SIZE) {
                BufferedImage resized = new BufferedImage(SIZE, SIZE, image.getType());
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

    public static Texture wood = new Texture(GameSettings.DungeonWallTexturePath+"wood.png", 64); // map value 1
    public static Texture brick = new Texture(GameSettings.DungeonWallTexturePath+"redbrick.png", 64); // map value 2
    public static Texture bluestone = new Texture(GameSettings.DungeonWallTexturePath+"bluestone.png", 64); // map value 3
    public static Texture stone = new Texture(GameSettings.DungeonWallTexturePath+"greystone.png", 64); // map value 4
    public static Texture door = new Texture(GameSettings.DungeonDoorTexturePath+"door.png", 64); // map value 5
    public static Texture door2 = new Texture(GameSettings.DungeonDoorTexturePath+"door2.png", 64); // map value 6
    public static Texture stairsup = new Texture(GameSettings.DungeonStairsTexturePath+"stairsup.png", 64); // map value 7
    public static Texture stairsdown = new Texture(GameSettings.DungeonStairsTexturePath+"stairsdown.png", 64); // map value 8
    public static Texture stairsdownwithgate = new Texture(GameSettings.DungeonStairsTexturePath+"downstairswithgate.png", 64); // map value 9
    public static Texture downstairsdownwithgateandtorches = new Texture(GameSettings.DungeonStairsTexturePath+"downstairswithgateandtorches.png", 64); // map value 0
}
