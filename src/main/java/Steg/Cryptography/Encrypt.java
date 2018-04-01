package Steg.Cryptography;

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import javax.crypto.spec.IvParameterSpec;
import java.util.Arrays;
import java.lang.StringBuffer;



/** The encrypt class is used for all things encryption, to include key generation. */
public class Encrypt extends Cryptography {

  /** Default constructor to null */
  public Encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException {
    super();
  }

  public String Crypt(String msg, String test) { //DOES NOT WORK, NEED TO CORRECTLY CONVERT BYTE ARRAY TO STRING
    /*
    Heavy work in progress, going to take in strings, create more methods and convert the strings to bytes,
    from there at the end convert bytes to strings to keep things easy on the ui side.
     */

    //try using cipher block chaining, need to create a random initialization vector so ciphertext is different
      // with each encryption
    byte[] initVector = new byte[16]; //create byte array to fill initialization vector
    SecureRandom randomNumber = new SecureRandom(); //new random number to randomize initialization vector
    randomNumber.nextBytes(initVector); //fill byte array with random numbers
    IvParameterSpec ivpspec = new IvParameterSpec(initVector); //set up initialization vector with random byte array

    //save init vector to a file/db?

    //generate and set the random key
    setKey(genKey(keySize));
    StringBuilder strBuilder = new StringBuilder();

    try {
        //set up the Cipher object with the secret key and the initialization vector
        stego.init(Cipher.ENCRYPT_MODE, key, ivpspec);
        byte[] in = msg.getBytes("UTF-8"); //put message in byte array
        byte[] out = stego.doFinal(in); //encrypt message using Cipher object and store in byte array
        strBuilder.append(Arrays.toString(out)); //*****this doesn't work, look into using base64 encoder for conversion*****
    } catch (Exception ex) { // catch if we run into issues.
      ex.printStackTrace();
        return "Something went terribly wrong..... contact support"; // display something went wrong if
        // it does
    }
    return strBuilder.toString();//change this if we implement base64 encoding


  }

  /**
   * Generate a random secret key
   *
   * @return Random secret key
   * @throws InvalidParameterException
   */
  public SecretKey genKey(int keySize) throws InvalidParameterException {

    try {
      // throw exception if key size is too large
      if (Cipher.getMaxAllowedKeyLength("AES") < keySize) {
        // unlimited crypto is not installed
        throw new InvalidParameterException(
            "Key size of " + keySize + "bits not supported in this runtime, download JCE files");
      }

      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      keyGen.init(keySize);

      return keyGen.generateKey();
    } catch (final NoSuchAlgorithmException e) {
      // AES functionality is part of java se
      throw new IllegalStateException("AES should be preset, please reconfigure java", e);
    }
  }

  public void setKeyRand() {
    this.key = genKey(keySize);
  }
}
