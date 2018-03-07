package Steg.Cryptography;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

/**
 * The encrypt class is used for all things encryption, to include key generation.
 */

public class Encrypt extends Cryptography {

    /**
     * Default constructor to null
     */
    public Encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super();
    }

    /**
     * Default constructor to null
     */
    //public Encrypt() throws NoSuchAlgorithmException, NoSuchPaddingException {
    //     super();
    //}
    public String Crypt(String msg, String test) {
        /*
        Heavy work in progress, going to take in strings, create more methods and convert the strings to bytes,
        from there at the end convert bytes to strings to keep things easy on the ui side.
         */

        try { //lets try to create the cipher and hope it works!


        } catch (Exception ex) { //catch if we run into issues.
            ex.printStackTrace();
        }

        return "Something went terribly wrong..... contact support"; //display something went wrong if it does
    }


    /**
     * Generate a random secret key
     *
     * @return Random secret key
     * @throws InvalidParameterException
     */
    public SecretKey genKey(int keySize) throws InvalidParameterException {

        try {
            //throw exceptiopn if key size is to large
            if (Cipher.getMaxAllowedKeyLength("AES") < keySize) {
                // unlimited crypto is not installed
                throw new InvalidParameterException("Key size of " + keySize
                    + "bits not supported in this runtime, download JCE files");
            }

            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keySize);

            return keyGen.generateKey();
        } catch (final NoSuchAlgorithmException e) {
            // AES functionality is part of java se
            throw new IllegalStateException(
                "AES should be preset, please reconfigure java", e);
        }
    }

    public void setKeyRand() {
        this.key = genKey(keySize);
    }


}
