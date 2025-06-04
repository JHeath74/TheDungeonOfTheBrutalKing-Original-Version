
package GameEngine;


import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;

import DungeonoftheBrutalKing.MainGameScreen;
import Maps.DungeonLevel;
import Maps.DungeonLevel1;
import Maps.DungeonLevel2;
import Maps.DungeonLevel3;
import Maps.DungeonLevel4;

public class Game implements Runnable {

    MainGameScreen myMainGameScreen = new MainGameScreen();

    private static final long serialVersionUID = 1L;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public ArrayList<Texture> textures;
    public Camera camera;
    public Screen screen;
    private DungeonLevel dungeonLevel;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;

    private Game() throws IOException {
        thread = new Thread(this);
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<>();
        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);
        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);

        dungeonLevel = getDungeonLevel(1);
        map = getDungeonMap(1);
        mapWidth = dungeonLevel.getMapWidth();
        mapHeight = dungeonLevel.getMapHeight();

        screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
        myMainGameScreen.addKeyListener(camera);
        start();
    }


public synchronized void start() {
    if (!running && thread.getState() == Thread.State.NEW) { // Ensure thread is only started once
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
        BufferStrategy bs = myMainGameScreen.getGameImagesAndCombatCanvas().getBufferStrategy();
        if (bs == null) {
            myMainGameScreen.getGameImagesAndCombatCanvas().createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.dispose();
        bs.show();
    }

    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0; // 60 times per second
        double delta = 0;
        myMainGameScreen.requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                screen.update(camera, pixels);
                try {
                    camera.update(map);
                    Point playerPosition = new Point((int) camera.xPos, (int) camera.yPos);
                    Point transitionPoint = getTransitionPoint(dungeonLevel.getDungeonLevelNumber());
                    if (playerPosition.equals(transitionPoint)) {
                        changeLevel(dungeonLevel.getDungeonLevelNumber() + 1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                delta--;
            }
            render();
        }
    }
    

    public void changeLevel(int level) {
        dungeonLevel = getDungeonLevel(level);
        map = getDungeonMap(level);
        mapWidth = dungeonLevel.getMapWidth();
        mapHeight = dungeonLevel.getMapHeight();
        screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
        myMainGameScreen.setMessageTextPane("You have entered Dungeon Level " + level + ".\n");
    }

    public int[][] getDungeonMap(int level) {
        DungeonLevel dungeonLevel = getDungeonLevel(level);
        return dungeonLevel.getMap();
    }

    public DungeonLevel getDungeonLevel(int level) {
        switch (level) {
            case 1:
                return new DungeonLevel1();
            case 2:
                return new DungeonLevel2();
            case 3:
                return new DungeonLevel3();
            case 4:
                return new DungeonLevel4();
            default:
                throw new IllegalArgumentException("Invalid dungeon level: " + level);
        }
    }

    public Point getTransitionPoint(int level) {
        switch (level) {
            case 1:
                return new Point(4, 4);
            case 2:
                return new Point(2, 2);
            case 3:
                return new Point(6, 6);
            case 4:
                return new Point(8, 8);
            default:
                throw new IllegalArgumentException("Invalid dungeon level: " + level);
        }
    }
}
