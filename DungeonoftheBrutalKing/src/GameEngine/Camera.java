package GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;




public class Camera implements KeyListener{
	public double xPos, yPos, xDir, yDir, xPlane, yPlane;
	public boolean left, right, forward, back;
	public final double MOVE_SPEED = .08;
	public final double ROTATION_SPEED = .045;
	public Camera(double x, double y, double xd, double yd, double xp, double yp) {
		xPos = x;
		yPos = y;
		xDir = xd;
		yDir = yd;
		xPlane = xp;
		yPlane = yp;


	}
	@Override
	public void keyPressed(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_LEFT)) {
			left = true;
			System.out.println("Left key pressed");
		}
		if((key.getKeyCode() == KeyEvent.VK_RIGHT)) {
			right = true;
			System.out.println("Right key pressed");
		}
		if((key.getKeyCode() == KeyEvent.VK_UP)) {
			forward = true;
			System.out.println("Up key pressed");
		}
		if((key.getKeyCode() == KeyEvent.VK_DOWN)) {
			back = true;
			System.out.println("Down key pressed");
		}
	}
	@Override
	public void keyReleased(KeyEvent key) {
		if((key.getKeyCode() == KeyEvent.VK_LEFT)) {
			left = false;
		}
		if((key.getKeyCode() == KeyEvent.VK_RIGHT)) {
			right = false;
		}
		if((key.getKeyCode() == KeyEvent.VK_UP)) {
			forward = false;
		}
		if((key.getKeyCode() == KeyEvent.VK_DOWN)) {
			back = false;
		}
	}


public void update(int[][] map) throws IOException {
    System.out.printf("Before update: xPos=%.2f, yPos=%.2f, xDir=%.2f, yDir=%.2f\n", xPos, yPos, xDir, yDir);

    if (forward) {
        if (map[(int)(xPos + xDir * MOVE_SPEED)][(int)yPos] == 0) {
            xPos += xDir * MOVE_SPEED;
        }
        if (map[(int)xPos][(int)(yPos + yDir * MOVE_SPEED)] == 0) {
            yPos += yDir * MOVE_SPEED;
        }
    }
    if (back) {
        if (map[(int)(xPos - xDir * MOVE_SPEED)][(int)yPos] == 0) {
            xPos -= xDir * MOVE_SPEED;
        }
        if (map[(int)xPos][(int)(yPos - yDir * MOVE_SPEED)] == 0) {
            yPos -= yDir * MOVE_SPEED;
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

    System.out.printf("After update:  xPos=%.2f, yPos=%.2f, xDir=%.2f, yDir=%.2f\n", xPos, yPos, xDir, yDir);
}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}





}


