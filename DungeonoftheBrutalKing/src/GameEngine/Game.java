
package GameEngine;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import DungeonoftheBrutalKing.MainGameScreen;
import Locations.TheRustyTankard.TheRustyTankard;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import Maps.DungeonLevel;
import Maps.DungeonLevel1;
import Maps.DungeonLevel2;
import Maps.DungeonLevel3;
import SharedData.LocationType;

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

    private Canvas renderCanvas;
    private JPanel renderPanel;

    private int currentLevelIndex = 0;
    private List<DungeonLevel> levels = new ArrayList<>();
    public int[][] map;

    private DungeonLevel currentDungeonLevel;

    private int lastEventX = -1;
    private int lastEventY = -1;
    private LocationType lastEventType = LocationType.OTHER;

    public Game() throws IOException, InterruptedException, ParseException {
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

        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66, this, MainGameScreen.getInstance());

        initializeLevels();
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
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
                renderCanvas.setSize(width, height);
                screen = new Screen(map, mapWidth, mapHeight, textures, width, height);
                renderPanel.requestFocusInWindow();
            }
        });
    }

    private void initializeLevels() {
        levels.add(new DungeonLevel1());
        levels.add(new DungeonLevel2());
        levels.add(new DungeonLevel3());
    }

    public boolean isWalkable(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
            return false;
        }
        return map[y][x] == 0;
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

        Point stairsUp = level.getStairsUpLocation();
        if (stairsUp != null) {
            camera.setX(stairsUp.x + 0.5);
            camera.setY(stairsUp.y + 0.5);
        } else {
        	outer:
        		for (int y = 0; y < mapHeight; y++) {
        		    for (int x = 0; x < mapWidth; x++) {
        		        if ((x != 0 || y != 0) && isWalkable(x, y)) {
        		            camera.setX(x + 0.5);
        		            camera.setY(y + 0.5);
        		            break outer;
        		        }
        		    }
        		}
        }
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
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                screen.update(camera, pixels);
                checkLevelTransition();
                try {
                    checkLocationEvent();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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
        textures.add(Texture.GreyDungeonWall);
        textures.add(Texture.GreyDungeonDoor);
        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);
        textures.add(Texture.stairsup);
        textures.add(Texture.stairsdown);
        textures.add(Texture.stairsdownwithgate);
        textures.add(Texture.downstairsdownwithgateandtorches);
    }

    public LocationType detectLocation(int x, int y) {
        if (x < 0 || x >= mapWidth || y < 0 || y >= mapHeight) {
            return LocationType.OTHER;
        }
        DungeonLevel currentLevel = levels.get(currentLevelIndex);
        LocationType special = currentLevel.getSpecialLocation(x, y);
        if (special != null) {
            return special;
        }
        int tile = map[y][x];
        switch (tile) {
            case 0: return LocationType.EMPTY;
            case 1: return LocationType.DOOR;
            case 5: return LocationType.STAIRS_DOWN;
            case 6: return LocationType.STAIRS_UP;
            case 7: return LocationType.THE_RUSTY_TANKARD;
            case 8: return LocationType.WELCOME_MESSAGE_RUSTY_TANKARD;
            default: return LocationType.OTHER;
        }
    }

    public void handleLocationEvent(LocationType type) throws IOException, InterruptedException, ParseException {
        switch (type) {
            case DOOR:
                System.out.println("DOOR event handled");
                appendToMessageTextPane("You passed through a door.");
                break;
            case WELCOME_MESSAGE_RUSTY_TANKARD:
                System.out.println("WELCOME_MESSAGE_RUSTY_TANKARD event handled");
                if (!rustyInnWelcomeShown) {
                    appendToMessageTextPane("Welcome to the Rusty Inn");
                    rustyInnWelcomeShown = true;
                }
                break;
            case THE_RUSTY_TANKARD:
                System.out.println("THE_RUSTY_TANKARD event handled");
                appendToMessageTextPane("You have entered The Rusty Tankard.");
                TheRustyTankard rustyTankard = new TheRustyTankard(new JPanel(), MainGameScreen.getInstance());
                MainGameScreen.replaceWithAnyPanel(rustyTankard.getMainPanel());
                break;
            default:
                System.out.println("Other location event handled");
                break;
        }
    }

    public void appendToMessageTextPane(String message) {
        MainGameScreen.appendToMessageTextPane(message);
    }

    public void checkLocationEvent() throws IOException, InterruptedException, ParseException {
        int playerX = (int) camera.getX();
        int playerY = (int) camera.getY();
        LocationType type = detectLocation(playerX, playerY);
        System.out.println("Player at: " + playerX + "," + playerY + " type: " + type);

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
