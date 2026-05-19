

package DungeonoftheBrutalKing.GameEngine;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Combat;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Enemies.MonsterSelector;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.LocationType;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Random;
public class Camera implements KeyListener {
public double xPos, yPos, xDir, yDir, xPlane, yPlane;
public boolean left, right, forward, back;

private static final double MOVE_SPEED     = 0.08;
private static final double ROTATION_SPEED = 0.045;

private final Random random = new Random();
private Combat activeCombat = null;
private int stepsSinceLastCombat = 0;

private final Game game;
private final MainGameScreen mainGameScreen;

private BufferedImage floorImage;
private BufferedImage ceilingImage;

public Camera(double x, double y, double xd, double yd, double xp, double yp,
              Game game, MainGameScreen mainGameScreen) {
    xPos   = x;
    yPos   = y;
    xDir   = xd;
    yDir   = yd;
    xPlane = xp;
    yPlane = yp;
    this.game           = game;
    this.mainGameScreen = mainGameScreen;
}

// ── Environment Images ────────────────────────────────────────────────────

public void loadEnvironmentImages(String floorFileName, String ceilingFileName) {
    try {
        floorImage = ImageIO.read(new File(GameSettings.getDungeonFloorTexturePath() + floorFileName));
    } catch (IOException e) {
        floorImage = null;
    }
    try {
        ceilingImage = ImageIO.read(new File(GameSettings.getDungeonFloorTexturePath() + ceilingFileName));
    } catch (IOException e) {
        ceilingImage = null;
    }
}

public BufferedImage getFloorImage()   { return floorImage; }
public BufferedImage getCeilingImage() { return ceilingImage; }

// ── Position / Direction ──────────────────────────────────────────────────

public int  getX() { return (int) xPos; }
public int  getY() { return (int) yPos; }
public void setX(double x) { this.xPos = x; }
public void setY(double y) { this.yPos = y; }

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
    yPlane =  xDir * fov;
}

public void resetMovementFlags() {
    forward = false;
    back    = false;
    left    = false;
    right   = false;
}

// ── KeyListener ───────────────────────────────────────────────────────────

@Override
public void keyPressed(KeyEvent key) {
    if (key.getKeyCode() == KeyEvent.VK_LEFT)  left    = true;
    if (key.getKeyCode() == KeyEvent.VK_RIGHT) right   = true;
    if (key.getKeyCode() == KeyEvent.VK_UP)    forward = true;
    if (key.getKeyCode() == KeyEvent.VK_DOWN)  back    = true;
}

@Override
public void keyReleased(KeyEvent key) {
    if (key.getKeyCode() == KeyEvent.VK_LEFT)  left    = false;
    if (key.getKeyCode() == KeyEvent.VK_RIGHT) right   = false;
    if (key.getKeyCode() == KeyEvent.VK_UP)    forward = false;
    if (key.getKeyCode() == KeyEvent.VK_DOWN)  back    = false;
}

@Override
public void keyTyped(KeyEvent arg0) { }

// ── Update Loop ───────────────────────────────────────────────────────────

public void update(int[][] map) throws IOException, InterruptedException, ParseException {
    boolean moved = false;

    if (forward) {
        int nextX = (int) (xPos + xDir * MOVE_SPEED);
        int nextY = (int) (yPos + yDir * MOVE_SPEED);

        if (nextX >= 0 && nextX < map.length && (int) yPos >= 0 && (int) yPos < map[0].length) {
            if (map[nextX][(int) yPos] != 1) {
                xPos += xDir * MOVE_SPEED;
                moved = true;
            }
        }
        if ((int) xPos >= 0 && (int) xPos < map.length && nextY >= 0 && nextY < map[0].length) {
            if (map[(int) xPos][nextY] != 1) {
                yPos += yDir * MOVE_SPEED;
                moved = true;
            }
        }
    }

    if (back) {
        int prevX = (int) (xPos - xDir * MOVE_SPEED);
        int prevY = (int) (yPos - yDir * MOVE_SPEED);

        if (map[prevX][(int) yPos] == 0) {
            xPos -= xDir * MOVE_SPEED;
            moved = true;
        }
        if (map[(int) xPos][prevY] == 0) {
            yPos -= yDir * MOVE_SPEED;
            moved = true;
        }
    }

    if (right) {
        double oldxDir   = xDir;
        xDir   = xDir    * Math.cos(-ROTATION_SPEED) - yDir   * Math.sin(-ROTATION_SPEED);
        yDir   = oldxDir * Math.sin(-ROTATION_SPEED) + yDir   * Math.cos(-ROTATION_SPEED);
        double oldxPlane = xPlane;
        xPlane = xPlane   * Math.cos(-ROTATION_SPEED) - yPlane * Math.sin(-ROTATION_SPEED);
        yPlane = oldxPlane * Math.sin(-ROTATION_SPEED) + yPlane * Math.cos(-ROTATION_SPEED);
    }

    if (left) {
        double oldxDir   = xDir;
        xDir   = xDir    * Math.cos(ROTATION_SPEED) - yDir   * Math.sin(ROTATION_SPEED);
        yDir   = oldxDir * Math.sin(ROTATION_SPEED) + yDir   * Math.cos(ROTATION_SPEED);
        double oldxPlane = xPlane;
        xPlane = xPlane   * Math.cos(ROTATION_SPEED) - yPlane * Math.sin(ROTATION_SPEED);
        yPlane = oldxPlane * Math.sin(ROTATION_SPEED) + yPlane * Math.cos(ROTATION_SPEED);
    }

    if (moved) {
        Character.getInstance().setPosition(getX(), getY(), 0);
        LocationType type = game.detectLocation(getX(), getY());
        game.handleLocationEvent(type);
        onPlayerStep();
    }
}

// ── Combat ────────────────────────────────────────────────────────────────

public void randomCombat() throws IOException, InterruptedException, ParseException {
    if (getActiveCombat() == null) {
        setActiveCombat(new Combat(this, game.getMainGamePanel()));
        mainGameScreen.savePreCombatPosition();
        getActiveCombat().setMyEnemies(MonsterSelector.selectRandomMonster());
        getActiveCombat().combatEncounter();
    }
}

public void endCombat() {
    mainGameScreen.restoreOriginalPanel();
    setActiveCombat(null);
    game.getRenderPanel().requestFocusInWindow();
    resetMovementFlags();
}

public void onPlayerStep() throws IOException, InterruptedException, ParseException {
    stepsSinceLastCombat++;

    if (stepsSinceLastCombat >= 150) {
        randomCombat();
        stepsSinceLastCombat = 0;
    } else if (stepsSinceLastCombat >= 100) {
        if (random.nextInt(75) == 0) {
            randomCombat();
            stepsSinceLastCombat = 0;
        }
    }
}

public Combat getActiveCombat()                    { return activeCombat; }
public void   setActiveCombat(Combat activeCombat) { this.activeCombat = activeCombat; }
}  
