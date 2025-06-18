
// src/DungeonoftheBrutalKing/EncryptionUtil.java
package DungeonoftheBrutalKing;

import java.util.Base64;

/**
 * Utility class for encrypting and decrypting data using a simple XOR-based algorithm.
 * Note: This encryption method is not secure for production use and is only for demonstration purposes.
 */
public class EncryptionUtil {

    // Encryption key (Must be at least 16 characters long)
    private static final String KEY = null;

    /**
     * Encrypts the given data using the XOR-based algorithm and encodes it in Base64.
     *
     * @param data The plain text data to be encrypted.
     * @return The encrypted data encoded in Base64.
     */
    public static String encrypt(String data) {
        // Convert the key and data to byte arrays
        byte[] keyBytes = KEY.getBytes();
        byte[] dataBytes = data.getBytes();
        byte[] encryptedBytes = new byte[dataBytes.length];

        // Perform XOR operation for encryption
        for (int i = 0; i < dataBytes.length; i++) {
            encryptedBytes[i] = (byte) (dataBytes[i] ^ keyBytes[i % keyBytes.length]);
        }

        // Encode the encrypted bytes in Base64 and return
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the given Base64-encoded encrypted data using the XOR-based algorithm.
     *
     * @param encryptedData The encrypted data encoded in Base64.
     * @return The decrypted plain text data.
     */
    public static String decrypt(String encryptedData) {
        // Convert the key to a byte array
        byte[] keyBytes = KEY.getBytes();

        // Decode the Base64-encoded encrypted data
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = new byte[encryptedBytes.length];

        // Perform XOR operation for decryption
        for (int i = 0; i < encryptedBytes.length; i++) {
            decryptedBytes[i] = (byte) (encryptedBytes[i] ^ keyBytes[i % keyBytes.length]);
        }

        // Convert the decrypted bytes to a string and return
        return new String(decryptedBytes);
    }
}
