
package GameEngine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import Maps.DungeonLevel1;
import Maps.DungeonLevel2;

public class Game implements Runnable {

    private int mapWidth;
    private int mapHeight;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public ArrayList<Texture> textures;
    private Camera camera;
    public Screen screen;

    private Canvas renderCanvas;
    private JPanel renderPanel;

    private int currentLevel = 1;
    private Map<Integer, Object> levels = new HashMap<>();
    public int[][] map;

    public Game() throws IOException, InterruptedException {
        thread = new Thread(this);
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<>();
        initializeTextures();
        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);

        initializeLevels();
        changeLevel(currentLevel);

        renderCanvas = new Canvas();
        renderCanvas.setSize(640, 480);
        renderPanel = new JPanel();
        renderPanel.setLayout(new BorderLayout());
        renderPanel.add(renderCanvas, BorderLayout.CENTER);

        renderCanvas.setFocusable(false);
        renderPanel.setFocusable(true);
        renderPanel.requestFocusInWindow();
        renderPanel.addKeyListener(camera);

        renderPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = renderPanel.getWidth();
                int height = renderPanel.getHeight();
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
                renderCanvas.setSize(width, height);
                screen = new Screen(map, mapWidth, mapHeight, textures, width, height);
                renderPanel.requestFocusInWindow();
            }
        });
    }

    private void initializeLevels() {
        levels.put(1, new DungeonLevel1());
        levels.put(2, new DungeonLevel2());
        // Add more levels as needed, e.g. levels.put(3, new DungeonLevel3());
    }

    public void changeLevel(int levelNumber) {
        Object levelObj = levels.get(levelNumber);
        if (levelObj instanceof DungeonLevel1) {
            DungeonLevel1 level = (DungeonLevel1) levelObj;
            map = level.getMap();
            mapWidth = DungeonLevel1.getMapWidth();
            mapHeight = DungeonLevel1.getMapHeight();
        } else if (levelObj instanceof DungeonLevel2) {
            DungeonLevel2 level = (DungeonLevel2) levelObj;
            map = level.getMap();
            mapWidth = DungeonLevel2.getMapWidth();
            mapHeight = DungeonLevel2.getMapHeight();
        }
        // Add more else-if blocks for additional levels
        currentLevel = levelNumber;
        screen = new Screen(map, mapWidth, mapHeight, textures, image.getWidth(), image.getHeight());
    }

    public void checkLevelTransition() {
        int playerX = (int) camera.getX();
        int playerY = (int) camera.getY();
        int tile = map[playerY][playerX];

        if (tile == 5) {
            changeLevel(currentLevel + 1);
        } else if (tile == 6) {
            changeLevel(currentLevel - 1);
        }
    }

    public JPanel getRenderPanel() {
        return renderPanel;
    }

    public synchronized void start() {
        if (!running) {
            running = true;
            thread.start();
        }
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        BufferStrategy bs = renderCanvas.getBufferStrategy();
        if (bs == null) {
            renderCanvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        bs.show();
        java.awt.Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        renderPanel.requestFocusInWindow();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                try {
                    camera.update(map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                screen.update(camera, pixels);
                checkLevelTransition();
                delta--;
            }
            render();
        }
    }

    public Camera getCamera() {
        return camera;
    }


private void initializeTextures() {
    textures.add(Texture.brick);      // 1
    textures.add(Texture.stone);      // 2
    textures.add(Texture.wood);       // 3
    textures.add(Texture.bluestone);  // 4
    textures.add(Texture.door);       // 5
    textures.add(Texture.door2);      // 6
    textures.add(Texture.stairsup);   // 7
    textures.add(Texture.stairsdown); // 8
}

}
