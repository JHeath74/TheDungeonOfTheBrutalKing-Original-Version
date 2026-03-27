package DungeonoftheBrutalKing.Spells;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Centralized manager for spell tuning values. Loads key/value pairs from
 * `src/Spells/spell-balances.properties` on the classpath and exposes typed
 * getters for code to fetch balance numbers. This keeps tuning centralized.
 */
public final class SpellBalanceManager {

    private static final String PROPS_FILE = "/Spells/spell-balances.properties";
    private static final Properties props = new Properties();

    static {
        try (InputStream in = SpellBalanceManager.class.getResourceAsStream(PROPS_FILE)) {
            if (in != null) props.load(in);
        } catch (IOException ignored) { }
    }

    private SpellBalanceManager() { }

    public static double getDouble(String key, double def) {
        try {
            String s = props.getProperty(key);
            if (s == null) return def;
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return def;
        }
    }

    public static int getInt(String key, int def) {
        try {
            String s = props.getProperty(key);
            if (s == null) return def;
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return def;
        }
    }

    public static String getString(String key, String def) {
        String s = props.getProperty(key);
        return (s == null) ? def : s.trim();
    }
}
