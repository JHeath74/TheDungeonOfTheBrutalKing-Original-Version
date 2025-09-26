
// DungeonLevelGenerator.java
package SharedData;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class DungeonLevelGenerator {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("src/DungeonoftheBrutalKing/Images/Level/DungeonLevels/DungeonLevel1.png"));
            int width = image.getWidth();
            int height = image.getHeight();
            int[][] binaryMaze = new int[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = image.getRGB(x, y);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = pixel & 0xff;
                    int gray = (int)(0.299 * red + 0.587 * green + 0.114 * blue);
                    binaryMaze[y][x] = (gray < 128) ? 1 : 0;
                }
            }

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    System.out.print(binaryMaze[y][x] + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
