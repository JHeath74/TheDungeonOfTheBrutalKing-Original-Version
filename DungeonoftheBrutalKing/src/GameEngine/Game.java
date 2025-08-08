
package GameEngine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JPanel;

import DungeonoftheBrutalKing.MainGameScreen;

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

    public static int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 2},
        {1, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 3, 0, 0, 0, 3, 0, 2, 2, 2, 0, 2, 2, 2},
        {1, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 3, 3, 0, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2}
    };

    public Game() throws IOException, InterruptedException {
        thread = new Thread(this);
        image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<>();
        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);
        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
        screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);

        renderCanvas = new Canvas();
        renderCanvas.setSize(640, 480);
        renderPanel = new JPanel();
        renderPanel.setLayout(new java.awt.BorderLayout());
        renderPanel.add(renderCanvas, java.awt.BorderLayout.CENTER);

        renderCanvas.setFocusable(false);
        renderPanel.setFocusable(true);
        renderPanel.requestFocusInWindow();
        renderPanel.addKeyListener(camera);

        // Add component listener to handle resizing
        renderPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int width = renderPanel.getWidth();
                int height = renderPanel.getHeight();
                image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
                renderCanvas.setSize(width, height);
                screen = new Screen(map, mapWidth, mapHeight, textures, width, height);
            }
        });
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
                screen.update(camera, pixels);
                delta--;
            }
            render();
        }
    }

	public Camera getCamera() {
		// TODO Auto-generated method stub
		return camera;
	}
}
