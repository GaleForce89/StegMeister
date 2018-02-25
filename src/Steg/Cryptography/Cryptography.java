package Steg.Cryptography;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * The cipher class it the main class used for encrypting/decrypting a file
 */
public class Cryptography {
    SecretKey key; //used to enter password
    String msg; //used for message
    int keySize; //used to set keysize
    Cipher stego; //aptly named cipher

    //Encrypt encrypt = new Encrypt(); //create our encryption object

    /**
     * Default constructor to null
     */
    public Cryptography() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.key = null;
        this.msg = null;
        this.keySize = 128; //default to 128bits
        this.stego = Cipher.getInstance("AES/CBC/PKCS5Padding");
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
     */
   // public void setKeyRand() {
   //     this.key = encrypt.genKey(keySize);
    //}

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

    /**
     * Check the maximum key size supported by the jre
     *
     * @return Key size as int
     * @throws NoSuchAlgorithmException
     */
    public int maxKeySize() throws NoSuchAlgorithmException {
        return Cipher.getMaxAllowedKeyLength("AES");
    }
}
