package Steg;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.NullCipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import java.lang.String;

public class EnCrypt {
    String key; //used to enter password
    String msg; //used for message


    /**
     * Default constructor to null
     */
    public EnCrypt(){
        this.key = null;
        this.msg = null;
    }

    public EnCrypt(String key, String msg){ //may not need additional constructors time will tell
        this.key = key; //construct with a key
        this.msg = msg;
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

        return "Something went terribly wrong....."; //display something went wrong if it does
    }



}
