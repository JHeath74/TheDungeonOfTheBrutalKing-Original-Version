
package SharedData;

import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

public class KeyManager {
    private static final String KEY_FILE = "TextFiles/encryption.key";

    public static String getOrCreateKey() throws IOException {
        File keyFile = new File(KEY_FILE);
        File parentDir = keyFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Ensure directory exists
        }
        if (keyFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(keyFile))) {
                String key = reader.readLine();
                if (key != null && !key.isEmpty()) {
                    return key;
                }
            }
        }
        String key = generateRandomKey();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(keyFile))) {
            writer.write(key);
        }
        return key;
    }

    private static String generateRandomKey() {
        byte[] key = new byte[32]; // 256 bits
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
