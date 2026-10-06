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
import java.util.Locale;
import java.util.Random;

public class Camera implements KeyListener {
public double xPos, yPos, xDir, yDir, xPlane, yPlane;
public boolean left, right, forward, back;

private static final double MOVE_SPEED     = 0.08;
private static final double ROTATION_SPEED = 0.045;

private final Random random = new Random();
private Combat activeCombat = null;
private int stepsSinceLastCombat = 0;
private String encounterDebugLine = "";
private int debugLogTick = 0;

private static final int[] DEFAULT_FLOOR_PIXELS = Texture.GREY_DUNGEON_FLOOR.pixels.clone();
private static final int[] DEFAULT_CEILING_PIXELS = Texture.GREY_DUNGEON_WALL.pixels.clone();

private final Game game;
private final MainGameScreen mainGameScreen;

private BufferedImage floorImage;
private BufferedImage ceilingImage;
private int[] floorPixels = DEFAULT_FLOOR_PIXELS.clone();
private int[] ceilingPixels = DEFAULT_CEILING_PIXELS.clone();

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
        floorPixels = toTexturePixels(floorImage);
    } catch (IOException e) {
        floorImage = null;
        floorPixels = DEFAULT_FLOOR_PIXELS.clone();
    }
    try {
        ceilingImage = ImageIO.read(new File(GameSettings.getDungeonCeilingTexturePath() + ceilingFileName));
        ceilingPixels = toTexturePixels(ceilingImage);
    } catch (IOException e) {
        // Fallback to wall texture path for legacy maps that reuse wall art as ceiling.
        try {
            ceilingImage = ImageIO.read(new File(GameSettings.getDungeonWallTexturePath() + ceilingFileName));
            ceilingPixels = toTexturePixels(ceilingImage);
        } catch (IOException ignored) {
            ceilingImage = null;
            ceilingPixels = DEFAULT_CEILING_PIXELS.clone();
        }
    }
}

public BufferedImage getFloorImage()   { return floorImage; }
public BufferedImage getCeilingImage() { return ceilingImage; }
public int[] getFloorPixels()          { return floorPixels; }
public int[] getCeilingPixels()        { return ceilingPixels; }

private int[] toTexturePixels(BufferedImage image) {
    if (image == null) {
        return null;
    }

    int[] pixels = new int[Texture.SIZE * Texture.SIZE];
    int width = Math.max(1, image.getWidth());
    int height = Math.max(1, image.getHeight());

    // Normalize any loaded image to Texture.SIZE so Screen indexing stays valid.
    for (int y = 0; y < Texture.SIZE; y++) {
        int srcY = y * height / Texture.SIZE;
        for (int x = 0; x < Texture.SIZE; x++) {
            int srcX = x * width / Texture.SIZE;
            pixels[y * Texture.SIZE + x] = image.getRGB(srcX, srcY);
        }
    }
    return pixels;
}

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

// ── Combat ─────────────────────────────────────────────────────────────[...]

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

    int checkStart = GameSettings.getEncounterCheckStartSteps();
    int forceSteps = GameSettings.getEncounterForceSteps();
    int rollStart = GameSettings.getEncounterRollStart();
    int rollMin = GameSettings.getEncounterRollMin();
    int graceSteps = GameSettings.getEncounterPostCombatGraceSteps();
    int checkInterval = GameSettings.getEncounterCheckIntervalSteps();
    int agility = getPlayerAgilitySafe();
    double avoidChance = getAgilityAvoidChance(agility);

    // Hard cooldown right after combat to avoid immediate back-to-back encounters.
    if (stepsSinceLastCombat < graceSteps) {
        updateEncounterDebugLine(agility, avoidChance, 0.0, -2, checkStart, forceSteps, false, false);
        return;
    }

    // No encounter checks until enough movement has happened
    if (stepsSinceLastCombat < checkStart) {
        updateEncounterDebugLine(agility, avoidChance, 0.0, 0, checkStart, forceSteps, false, false);
        return;
    }

    // Chance ramps up gradually from CHECK_START -> FORCE
    int span = forceSteps - checkStart;
    int progress = stepsSinceLastCombat - checkStart;

    // Starts low and ramps up gradually as step pressure rises.
    int rollSize = Math.max(
        rollMin,
        rollStart - (progress * (rollStart - rollMin) / Math.max(1, span))
    );

    boolean forcedEncounter = stepsSinceLastCombat >= forceSteps;

    // Only perform random encounter rolls on interval ticks, unless the pity force threshold is hit.
    int postStartSteps = Math.max(0, stepsSinceLastCombat - checkStart);
    boolean shouldRollThisStep = forcedEncounter || (postStartSteps % Math.max(1, checkInterval) == 0);
    if (!shouldRollThisStep) {
        updateEncounterDebugLine(agility, avoidChance, 0.0, -1, checkStart, forceSteps, forcedEncounter, false);
        return;
    }

    double encounterChance = forcedEncounter ? 1.0 : (1.0 / Math.max(1, rollSize));
    updateEncounterDebugLine(agility, avoidChance, encounterChance, rollSize, checkStart, forceSteps, forcedEncounter, false);

    boolean shouldEncounter = false;
    if (forcedEncounter) {
        shouldEncounter = true;
    } else if (random.nextInt(rollSize) == 0) {
        shouldEncounter = true;
    }

    if (!shouldEncounter) {
        return;
    }

    if (passesAgilityAvoid(avoidChance)) {
        // If player avoids, reduce immediate retriggers but keep pressure building.
        stepsSinceLastCombat = Math.max(0, checkStart - Math.max(1, checkInterval));
        updateEncounterDebugLine(agility, avoidChance, encounterChance, rollSize, checkStart, forceSteps, forcedEncounter, true);
        return;
    }

    randomCombat();
    stepsSinceLastCombat = 0;
    updateEncounterDebugLine(agility, avoidChance, 0.0, 0, checkStart, forceSteps, false, false);
}

private boolean passesAgilityAvoid(double avoidChance) {
    return random.nextDouble() < avoidChance;
}

private double getAgilityAvoidChance(int agility) {
    // Stronger AGI scaling: 5% base, +1.2%/AGI, +0.5%/AGI over 15, capped at 55%.
    int safeAgility = Math.max(0, agility);
    int bonusAgility = Math.max(0, safeAgility - 15);
    double avoidChance = 0.05 + (safeAgility * 0.012) + (bonusAgility * 0.005);
    return Math.max(0.0, Math.min(0.55, avoidChance));
}

private int getPlayerAgilitySafe() {
    try {
        return Math.max(0, Character.getInstance().getAgility());
    } catch (Exception ignored) {
        // Fallback if player data is unavailable
    }
    return 0;
}

private void updateEncounterDebugLine(int agility, double avoidChance, double encounterChance, int rollSize,
                                      int checkStart, int forceSteps, boolean forced, boolean avoided) {
    int graceSteps = GameSettings.getEncounterPostCombatGraceSteps();

    if (stepsSinceLastCombat < graceSteps) {
        int untilRolls = graceSteps - stepsSinceLastCombat;
        encounterDebugLine = String.format(
            Locale.US,
            "Encounter: cooldown (%d steps to checks) | Avoid: %.1f%% (AGI %d) | Steps: %d/%d",
            untilRolls,
            avoidChance * 100.0,
            agility,
            stepsSinceLastCombat,
            forceSteps
        );
    } else if (rollSize == -1) {
        encounterDebugLine = String.format(
            Locale.US,
            "Encounter: gated (interval step) | Avoid: %.1f%% (AGI %d) | Steps: %d/%d",
            avoidChance * 100.0,
            agility,
            stepsSinceLastCombat,
            forceSteps
        );
    } else if (stepsSinceLastCombat < checkStart) {
        int untilChecks = checkStart - stepsSinceLastCombat;
        encounterDebugLine = String.format(
            Locale.US,
            "Encounter: warmup (%d steps to checks) | Avoid: %.1f%% (AGI %d) | Steps: %d/%d",
            untilChecks,
            avoidChance * 100.0,
            agility,
            stepsSinceLastCombat,
            forceSteps
        );
    } else if (rollSize == -2) {
        encounterDebugLine = String.format(
            Locale.US,
            "Encounter: cooldown | Avoid: %.1f%% (AGI %d) | Steps: %d/%d",
            avoidChance * 100.0,
            agility,
            stepsSinceLastCombat,
            forceSteps
        );
    } else {
        String encounterText = forced
            ? "FORCED"
            : String.format(Locale.US, "1/%d (%.2f%%)", Math.max(1, rollSize), encounterChance * 100.0);
        String avoidText = avoided ? " (avoided)" : "";
        encounterDebugLine = String.format(
            Locale.US,
            "Encounter: %s | Avoid: %.1f%% (AGI %d)%s | Steps: %d/%d",
            encounterText,
            avoidChance * 100.0,
            agility,
            avoidText,
            stepsSinceLastCombat,
            forceSteps
        );
    }

    if (GameSettings.isEncounterDebugLogEnabled()) {
        debugLogTick++;
        if (debugLogTick >= GameSettings.getEncounterDebugLogIntervalSteps()) {
            System.out.println(encounterDebugLine);
            debugLogTick = 0;
        }
    }
}

public String getEncounterDebugLine() {
    return encounterDebugLine;
}

public Combat getActiveCombat()                    { return activeCombat; }
public void   setActiveCombat(Combat activeCombat) { this.activeCombat = activeCombat; }
}
