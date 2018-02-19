package Steg;

import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * The cipher class it the main class used for encrypting/decrypting a file
 */
public class Cipher {
    SecretKey key; //used to enter password
    String msg; //used for message

    Encrypt encrypt = new Encrypt(); //create our encryption object

    /**
     * Default constructor to null
     */
    public Cipher() {
        this.key = null;
        this.msg = null;
    }

    /**
     * Get the current key held in memory
     *
     * @return key
     */
    public SecretKey getKey() {
        return key;
    }

    /**
     * Set the secret key manually
     *
     * @param key Secret key
     */
    public void setKey(SecretKey key) {
        this.key = key;
    }

    /**
     * Generate and set a random secret key
     *
     * @throws NoSuchAlgorithmException
     */
    public void setKeyRand() throws NoSuchAlgorithmException {
        this.key = encrypt.genKey();
    }

    /**
     * Save the current key to a file for later use
     *
     * @param path The path and file name to save the key
     */
    public void saveKey(String path) {
        try (FileOutputStream out = new FileOutputStream(path)) {
            byte[] keys = key.getEncoded();
            out.write(keys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
