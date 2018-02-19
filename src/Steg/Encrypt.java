package Steg;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * The encrypt class is used for all things encryption, to include key generation.
 */

public class Encrypt {

    /**
     * Default constructor to null
     */
    public Encrypt(){
    }


    public String Crypt(String msg, String test){
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
     * @throws NoSuchAlgorithmException
     */
    public SecretKey genKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        return keyGen.generateKey();
    }


}
