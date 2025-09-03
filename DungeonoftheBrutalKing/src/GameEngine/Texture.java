package GameEngine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
        // Resize if not 64x64
        if (w != SIZE || h != SIZE) {
            BufferedImage resized = new BufferedImage(SIZE, SIZE, image.getType());
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(image, 0, 0, SIZE, SIZE, null);
            g2d.dispose();
            image = resized;
            w = SIZE;
            h = SIZE;
        }
        image.getRGB(0, 0, w, h, pixels, 0, w);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


	public static Texture wood = new Texture("src\\DungeonoftheBrutalKing\\Images\\Level\\Wall\\wood.png", 64);
	public static Texture brick = new Texture("src\\DungeonoftheBrutalKing\\Images\\Level\\Wall\\redbrick.png", 64);
	public static Texture bluestone = new Texture("src\\DungeonoftheBrutalKing\\Images\\Level\\Wall\\bluestone.png", 64);
	public static Texture stone = new Texture("src\\DungeonoftheBrutalKing\\Images\\Level\\Wall\\greystone.png", 64);
	public static Texture door = new Texture("src\\DungeonoftheBrutalKing\\Images\\Level\\Door\\door.png", 64);
	public static Texture door2 = new Texture("src\\DungeonoftheBrutalKing\\Images\\Level\\Door\\door2.png", 64);
	public static Texture stairsup = new Texture("src\\DungeonoftheBrutalKing\\Images\\Level\\Stairs\\stairsup.png", 64);
	public static Texture stairsdown = new Texture("src\\DungeonoftheBrutalKing\\Images\\Level\\Stairs\\stairsdown.png", 64);
}