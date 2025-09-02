
package GameEngine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import Maps.DungeonLevel1;

public class Game implements Runnable {

    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public ArrayList<Texture> textures;
    private Camera camera;
    public Screen screen;

    private Canvas renderCanvas;
    private JPanel renderPanel;

    public static int[][] map = DungeonLevel1.map;

    public Game() throws IOException, InterruptedException {
        thread = new Thread(this);
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<>();
        initializeTextures();
        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);

        mapWidth = DungeonLevel1.getMapWidth();
        mapHeight = DungeonLevel1.getMapHeight();

        screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);

        renderCanvas = new Canvas();
        renderCanvas.setSize(640, 480);
        renderPanel = new JPanel();
        renderPanel.setLayout(new BorderLayout());
        renderPanel.add(renderCanvas, BorderLayout.CENTER);

        renderCanvas.setFocusable(false);
        renderPanel.setFocusable(true);
        renderPanel.requestFocusInWindow();
        renderPanel.addKeyListener(camera);

        // Add component listener to handle resizing
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
                // Do not call render() here
            }
        });
        // Do not call render() here
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
        final double ns = 1000000000.0 / 60.0; // 60 times per second
        double delta = 0;
        renderPanel.requestFocusInWindow();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                // Camera should be updated by key events
                try {
                    camera.update(map);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                screen.update(camera, pixels);

                delta--;
            }
            render();
        }
    }

    public Camera getCamera() {
        return camera;
    }

    private void initializeTextures() {
        textures.add(Texture.brick);
        textures.add(Texture.stone);
        textures.add(Texture.wood);
        textures.add(Texture.bluestone);
    }
}
