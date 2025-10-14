
// Java
package GameEngine;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import GameEngine.Texture;
import Maps.DungeonLevel;
import Maps.DungeonLevel1;
import Maps.DungeonLevel2;
import Maps.DungeonLevel3;

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

    private int currentLevelIndex = 0;
    private List<DungeonLevel> levels = new ArrayList<>();
    public int[][] map;

    public Game() throws IOException, InterruptedException {
        renderCanvas = new Canvas();
        renderCanvas.setSize(640, 480);
        renderPanel = new JPanel();
        renderPanel.setLayout(new BorderLayout());
        renderPanel.add(renderCanvas, BorderLayout.CENTER);

        try {
            thread = new Thread(this);
            image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
            pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            textures = new ArrayList<>();
            initializeTextures();
            camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);

            initializeLevels();
            changeLevel(currentLevelIndex);

            for (int y = 0; y < mapHeight; y++) {
                for (int x = 0; x < mapWidth; x++) {
                    if (isWalkable(x, y)) {
                        camera.setX(x + 0.5);
                        camera.setY(y + 0.5);
                        break;
                    }
                }
            }

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initializeLevels() {
        levels.add(new DungeonLevel1());
        levels.add(new DungeonLevel2());
        
    }

    public boolean isWalkable(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
            return false;
        }
        return map[y][x] == 0;
    }

    public void changeLevel(int levelIndex) {
        if (levelIndex < 0 || levelIndex >= levels.size()) return;
        DungeonLevel level = levels.get(levelIndex);
        map = level.getMap();
        mapWidth = DungeonLevel.getMapWidth();
        mapHeight = DungeonLevel.getMapHeight();
        currentLevelIndex = levelIndex;
        screen = new Screen(map, mapWidth, mapHeight, textures, image.getWidth(), image.getHeight());
    }

    public void goToNextLevel() {
        if (currentLevelIndex < levels.size() - 1) {
            changeLevel(currentLevelIndex + 1);
        }
    }

    public void goToPreviousLevel() {
        if (currentLevelIndex > 0) {
            changeLevel(currentLevelIndex - 1);
        }
    }

    public void checkLevelTransition() {
        int playerX = (int) camera.getX();
        int playerY = (int) camera.getY();
        int tile = map[playerY][playerX];

        if (tile == 5) {
            goToNextLevel();
        } else if (tile == 6) {
            goToPreviousLevel();
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
                } catch (IOException | InterruptedException | ParseException e) {
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
       

        // Wall and door textures (indices chosen for mapping)
        textures.add(Texture.GreyDungeonWall); // index 1 - grey dungeon wall
        textures.add(Texture.GreyDungeonDoor); // index 2 - grey dungeon door

        // Additional wall textures
        textures.add(Texture.wood);        // index 3 - wood wall
        textures.add(Texture.brick);       // index 4 - red brick wall
        textures.add(Texture.bluestone);   // index 5 - blue stone wall
        textures.add(Texture.stone);       // index 6 - grey stone wall

        // Stairs and special tiles
        textures.add(Texture.stairsup);    // index 7 - stairs up
        textures.add(Texture.stairsdown);  // index 8 - stairs down
        textures.add(Texture.stairsdownwithgate); // index 9 - stairs down with gate
        textures.add(Texture.downstairsdownwithgateandtorches); // index 10 - stairs down with gate and torches
    }

    public LocationType detectLocation(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
            return LocationType.OTHER;
        }
        int tile = map[y][x];
        switch (tile) {
            case 0: return LocationType.EMPTY;
            case 5: return LocationType.STAIRS_DOWN;
            case 6: return LocationType.STAIRS_UP;
            case 7: return LocationType.INN;
            default: return LocationType.OTHER;
        }
    }

}
