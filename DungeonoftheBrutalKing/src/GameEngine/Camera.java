
package GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;

import DungeonoftheBrutalKing.Combat;

public class Camera implements KeyListener {
    public double xPos, yPos, xDir, yDir, xPlane, yPlane;
    public boolean left, right, forward, back;
    public final double MOVE_SPEED = .08;
    public final double ROTATION_SPEED = .045;
    private final Random random = new Random();

    public Camera(double x, double y, double xd, double yd, double xp, double yp) {
        xPos = x;
        yPos = y;
        xDir = xd;
        yDir = yd;
        xPlane = xp;
        yPlane = yp;
        
        
    }
    

 // src/GameEngine/Camera.java

 public void setX(double x) {
     this.xPos = x;
 }

 public void setY(double y) {
     this.yPos = y;
 }

    

    @Override
    public void keyPressed(KeyEvent key) {
        if ((key.getKeyCode() == KeyEvent.VK_LEFT)) {
            left = true;
        }
        if ((key.getKeyCode() == KeyEvent.VK_RIGHT)) {
            right = true;
        }
        if ((key.getKeyCode() == KeyEvent.VK_UP)) {
            forward = true;
        }
        if ((key.getKeyCode() == KeyEvent.VK_DOWN)) {
            back = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent key) {
        if ((key.getKeyCode() == KeyEvent.VK_LEFT)) {
            left = false;
        }
        if ((key.getKeyCode() == KeyEvent.VK_RIGHT)) {
            right = false;
        }
        if ((key.getKeyCode() == KeyEvent.VK_UP)) {
            forward = false;
        }
        if ((key.getKeyCode() == KeyEvent.VK_DOWN)) {
            back = false;
        }
    }

    public void update(int[][] map) throws IOException, InterruptedException, ParseException {
        boolean moved = false;
        if (forward) {
            int nextX = (int)(xPos + xDir * MOVE_SPEED);
            int nextY = (int)(yPos + yDir * MOVE_SPEED);

            if (map[nextX][(int)yPos] == 5 || map[(int)xPos][nextY] == 5) {
                // Door logic removed
            } else {
                if (map[nextX][(int)yPos] == 0) {
                    xPos += xDir * MOVE_SPEED;
                    moved = true;
                }
                if (map[(int)xPos][nextY] == 0) {
                    yPos += yDir * MOVE_SPEED;
                    moved = true;
                }
            }
        }
        if (back) {
            int prevX = (int)(xPos - xDir * MOVE_SPEED);
            int prevY = (int)(yPos - yDir * MOVE_SPEED);

            if (map[prevX][(int)yPos] == 5 || map[(int)xPos][prevY] == 5) {
                // Door logic removed
            } else {
                if (map[prevX][(int)yPos] == 0) {
                    xPos -= xDir * MOVE_SPEED;
                    moved = true;
                }
                if (map[(int)xPos][prevY] == 0) {
                    yPos -= yDir * MOVE_SPEED;
                    moved = true;
                }
            }
        }
        if (right) {
            double oldxDir = xDir;
            xDir = xDir * Math.cos(-ROTATION_SPEED) - yDir * Math.sin(-ROTATION_SPEED);
            yDir = oldxDir * Math.sin(-ROTATION_SPEED) + yDir * Math.cos(-ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane = xPlane * Math.cos(-ROTATION_SPEED) - yPlane * Math.sin(-ROTATION_SPEED);
            yPlane = oldxPlane * Math.sin(-ROTATION_SPEED) + yPlane * Math.cos(-ROTATION_SPEED);
        }
        if (left) {
            double oldxDir = xDir;
            xDir = xDir * Math.cos(ROTATION_SPEED) - yDir * Math.sin(ROTATION_SPEED);
            yDir = oldxDir * Math.sin(ROTATION_SPEED) + yDir * Math.cos(ROTATION_SPEED);
            double oldxPlane = xPlane;
            xPlane = xPlane * Math.cos(ROTATION_SPEED) - yPlane * Math.sin(ROTATION_SPEED);
            yPlane = oldxPlane * Math.sin(ROTATION_SPEED) + yPlane * Math.cos(ROTATION_SPEED);
        }
       // randomCombat(moved);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // Not used
    }

    public int getX() {
        return (int) xPos;
    }

    public int getY() {
        return (int) yPos;
    }

    public void setPosition(double x, double y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void setDirection(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        xDir = Math.cos(angleRadians);
        yDir = Math.sin(angleRadians);

        double fov = 0.66;
        xPlane = -yDir * fov;
        yPlane = xDir * fov;
    }

    public void randomCombat(boolean moved) throws IOException, InterruptedException, ParseException {
        if (moved && random.nextDouble() < 0.1) { // 10% chance
            Combat combat = new Combat();
            combat.combatEncounter();
        }
    }
}
