// src/SharedData/RandomFactory.java
package SharedData;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Central RNG factory.
 * - gameplayRandom() returns a java.util.Random for game mechanics (reproducible / fast)
 * - secureRandom() returns a SecureRandom for security-sensitive use (keys, tokens)
 */
public final class RandomFactory {
    private static final Random GAME_RNG = new Random();
    private static final SecureRandom SECURE_RNG = new SecureRandom();

    private RandomFactory() { }

    public static Random gameplayRandom() {
        return GAME_RNG;
    }

    public static SecureRandom secureRandom() {
        return SECURE_RNG;
    }

    // Convenience passthroughs
    public static double gameplayDouble() { return GAME_RNG.nextDouble(); }
    public static boolean gameplayBoolean() { return GAME_RNG.nextBoolean(); }
    public static int gameplayInt(int bound) { return GAME_RNG.nextInt(bound); }
}
