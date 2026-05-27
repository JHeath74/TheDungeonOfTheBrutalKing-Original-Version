
// File: `src/DungeonoftheBrutalKing/GameEngine/Game.java`
package DungeonoftheBrutalKing.GameEngine;

import DungeonoftheBrutalKing.Locations.TheRustyTankard.TheRustyTankard;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Maps.DungeonLevel;
import DungeonoftheBrutalKing.Maps.DungeonLevel1;
import DungeonoftheBrutalKing.Maps.DungeonLevel2;
import DungeonoftheBrutalKing.Maps.DungeonLevel3;
import DungeonoftheBrutalKing.SharedData.LocationType;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

    private static boolean rustyInnWelcomeShown = false;

    private final MainGameScreen mainGameScreen;

    private Canvas renderCanvas;
    private JPanel renderPanel;

    private int currentLevelIndex = 0;
    private final List<DungeonLevel> levels = new ArrayList<>();
    public int[][] map;

    private DungeonLevel currentDungeonLevel;

    private int lastEventX = -1;
    private int lastEventY = -1;
    private LocationType lastEventType = LocationType.OTHER;

    private boolean preserveCameraOnNextLevelChange = false;

    public Game() throws IOException, InterruptedException, ParseException {
        this.mainGameScreen = MainGameScreen.getInstance();

        renderCanvas = new Canvas();
        renderCanvas.setSize(640, 480);

        renderPanel = new JPanel();
        renderPanel.setLayout(new BorderLayout());
        renderPanel.add(renderCanvas, BorderLayout.CENTER);

        thread = new Thread(this);

        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        textures = new ArrayList<>();
        initializeTextures();

        camera = new Camera(4.5, 4.5, 1, 0, 0, -0.66, this, mainGameScreen);

        initializeLevels();

        preserveCameraOnNextLevelChange = true; // keep constructor spawn for first load
        changeLevel(currentLevelIndex);

        renderCanvas.setFocusable(false);
        renderPanel.setFocusable(true);
        renderPanel.requestFocusInWindow();
        renderPanel.addKeyListener(camera);

        renderPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = renderPanel.getWidth();
                int height = renderPanel.getHeight();
                if (width <= 0 || height <= 0) return;

                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

                renderCanvas.setSize(width, height);

                if (map != null) {
                    screen = new Screen(map, mapWidth, mapHeight, textures, width, height);
                }
                renderPanel.requestFocusInWindow();
            }
        });
    }

    private void initializeLevels() throws IOException, InterruptedException, ParseException {
        levels.add(new DungeonLevel1());
        levels.add(new DungeonLevel2());
        levels.add(new DungeonLevel3());
    }

    public boolean isWalkable(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) return false;
        return map != null && map[y][x] == 0;
    }

    public JPanel getMainGamePanel() {
        return renderPanel;
    }

    public void changeLevel(int levelIndex) {
        if (levelIndex < 0 || levelIndex >= levels.size()) return;

        DungeonLevel level = levels.get(levelIndex);
        map = level.getMap();
        mapWidth = DungeonLevel.getMapWidth();
        mapHeight = DungeonLevel.getMapHeight();
        currentLevelIndex = levelIndex;
        currentDungeonLevel = level;

        screen = new Screen(map, mapWidth, mapHeight, textures, image.getWidth(), image.getHeight());

        if (preserveCameraOnNextLevelChange) {
            preserveCameraOnNextLevelChange = false;

            int spawnX = (int) camera.getX();
            int spawnY = (int) camera.getY();
            if (!isWalkable(spawnX, spawnY)) {
                Point fallback = findFirstWalkableExcludingOrigin();
                if (fallback != null) {
                    camera.setX(fallback.x + 0.5);
                    camera.setY(fallback.y + 0.5);
                }
            }
            return;
        }

        Point stairsUp = level.getStairsUpLocation();
        if (stairsUp != null && isWalkable(stairsUp.x, stairsUp.y)) {
            camera.setX(stairsUp.x + 0.5);
            camera.setY(stairsUp.y + 0.5);
            return;
        }

        Point fallback = findFirstWalkableExcludingOrigin();
        if (fallback != null) {
            camera.setX(fallback.x + 0.5);
            camera.setY(fallback.y + 0.5);
        }
    }

    private Point findFirstWalkableExcludingOrigin() {
        if (map == null) return null;
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if ((x != 0 || y != 0) && isWalkable(x, y)) {
                    return new Point(x, y);
                }
            }
        }
        return null;
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
        LocationType loc = detectLocation(playerX, playerY);

        if (loc == LocationType.STAIRS_DOWN) {
            goToNextLevel();
        } else if (loc == LocationType.STAIRS_UP) {
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
            Thread.currentThread().interrupt();
        }
    }

    public void render() {
        BufferStrategy bs = renderCanvas.getBufferStrategy();
        if (bs == null) {
            renderCanvas.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        try {
            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        } finally {
            bs.show();
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1_000_000_000.0 / 60.0;
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

                if (screen != null) {
                    screen.update(camera, pixels);
                }

                checkLevelTransition();

                try {
                    checkLocationEvent();
                } catch (IOException | InterruptedException | ParseException e) {
                    e.printStackTrace();
                }

                delta--;
            }

            render();
        }
    }

    public Camera getCamera() {
        return camera;
    }

    private void initializeTextures() {
        textures.add(Texture.GREY_DUNGEON_WALL);
        textures.add(Texture.GREY_DUNGEON_DOOR);
        textures.add(Texture.WOOD);
        textures.add(Texture.BRICK);
        textures.add(Texture.BLUESTONE);
        textures.add(Texture.STONE);
        textures.add(Texture.STAIRS_UP);
        textures.add(Texture.STAIRS_DOWN);
        textures.add(Texture.STAIRS_DOWN_GATE);
        textures.add(Texture.STAIRS_DOWN_GATE_TORCHES);
        textures.add(Texture.GREY_DUNGEON_FLOOR);
    }

    public LocationType detectLocation(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) return LocationType.OTHER;

        DungeonLevel currentLevel = levels.get(currentLevelIndex);
        LocationType special = currentLevel.getSpecialLocation(x, y);
        if (special != null) return special;

        if (map == null) return LocationType.OTHER;

        int tile = map[y][x];
        return switch (tile) {
            case 0 -> LocationType.EMPTY;
            case 1 -> LocationType.DOOR;
            case 5 -> LocationType.STAIRS_DOWN;
            case 6 -> LocationType.STAIRS_UP;
            case 7 -> LocationType.THE_RUSTY_TANKARD;
            case 8 -> LocationType.WELCOME_MESSAGE_RUSTY_TANKARD;
            default -> LocationType.OTHER;
        };
    }

    public void handleLocationEvent(LocationType type) throws IOException, InterruptedException, ParseException {
        switch (type) {
            case DOOR -> {
                System.out.println("DOOR event handled");
                appendToMessageTextPane("You passed through a door.");
            }
            case WELCOME_MESSAGE_RUSTY_TANKARD -> {
                System.out.println("WELCOME_MESSAGE_RUSTY_TANKARD event handled");
                if (!rustyInnWelcomeShown) {
                    appendToMessageTextPane("Welcome to the Rusty Inn");
                    rustyInnWelcomeShown = true;
                }
            }
            case THE_RUSTY_TANKARD -> {
                System.out.println("THE_RUSTY_TANKARD event handled");
                appendToMessageTextPane("You have entered The Rusty Tankard.");
                TheRustyTankard rustyTankard = new TheRustyTankard(new JPanel(), mainGameScreen);

           mainGameScreen.replaceWithAnyPanel(rustyTankard.getMainPanel());
            }
            default -> {
            }
        }
    }

    public void appendToMessageTextPane(String message) throws IOException, InterruptedException, ParseException {
      
        mainGameScreen.appendToMessageTextPane(message);
    }

    public void checkLocationEvent() throws IOException, InterruptedException, ParseException {
        int playerX = (int) camera.getX();
        int playerY = (int) camera.getY();
        LocationType type = detectLocation(playerX, playerY);

        if (playerX != lastEventX || playerY != lastEventY) {
            if (type != lastEventType) {
                handleLocationEvent(type);
                lastEventType = type;
            }
            lastEventX = playerX;
            lastEventY = playerY;
        }
    }

    public DungeonLevel getCurrentDungeonLevelInstance() {
        return currentDungeonLevel;
    }
}
