package steg.cryptography;

import org.apache.commons.codec.binary.Base64;

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/** The Cryptography class is the main class used for encrypting/decrypting a file */
public class Cryptography {
  private static SecretKey key; // used to enter password
  private static String keyword; //keyword tied to currently loaded key
  private static Cipher stego; // cipher object
  private static byte[] encryptVector; //initialization vector
  private static Base64 b64 = new Base64(); //used for encoding a byte array into a string and vice versa


  /** Initialize the Cipher object */
  private static void initCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
    stego = Cipher.getInstance("AES/CBC/PKCS5Padding");
  }

  /** Get current encryption initialization vector */
  public static String getVector() {

      return b64.encodeToString(encryptVector).trim();
  }

  /** Set current encryption initialization vector */
  public static void setVector(String v) {

      encryptVector = b64.decode(v);
  }

  /** Get the current key's keyword */
  public static String getKeyword()
  {
    return keyword;
  }

  /** Set the current key's keyword */
  public static void setKeyword(String kw) {

      keyword = kw;
  }

  /**
   * Get the current key held in memory as a String
   *
   * @return key
   */
  public static String getKeyStr() {
    if(key == null)
      return null;
    byte[] array = key.getEncoded();
    return b64.encodeAsString(array).trim();
  }

  /** Set the current key held in memory */
  public static void setKey(String k) {
      //have to use SecretKeySpec to set the key because of how b64's decode method works
      key = new SecretKeySpec(b64.decode(k), "AES");
  }

  /** Encrypt a message */
  public static String encrypt(String msg) throws NoSuchPaddingException, NoSuchAlgorithmException {
    //set up the cipher
    if(stego == null)
      initCipher();
    //create the IvParameterSpec with the initialization vector
    IvParameterSpec ivpspec = new IvParameterSpec(encryptVector);
    String encryptString;

    try {
      //set up the Cipher object with the secret key and the IvParameterSpec
      stego.init(Cipher.ENCRYPT_MODE, key, ivpspec);
      //put message in byte array
      byte[] in = msg.getBytes("UTF-8");
      //encrypt message
      byte[] out = stego.doFinal(in);
      //encode the byte array into a string
      encryptString = b64.encodeToString(out).trim();
      //catch if we run into issues
    } catch (Exception ex) {
      ex.printStackTrace();
      // display something went wrong if it does
      return "Something went terribly wrong..... contact support";

    }
    return encryptString;

  }

  /**Decrypt a message */
  public static String decrypt(String msg) throws NoSuchPaddingException, NoSuchAlgorithmException {

      //set up the cipher
      if(stego == null)
          initCipher();
      //use base64 object to decode the encrypted string into a byte array
      byte[] decoded = b64.decode(msg);

      String finalString;
      //set up the IvParameterSpec with the init vector to decrypt
      IvParameterSpec ivpspec = new IvParameterSpec(encryptVector);
      try {
          //set up the cipher object to decrypt using the key and IvParameterSpec
          stego.init(Cipher.DECRYPT_MODE, key, ivpspec);
          //decrypt the byte array using the cipher object, store decrypted plaintext in a string
          finalString = new String(stego.doFinal(decoded), "UTF-8");
      } catch (Exception ex) {
          ex.printStackTrace();
      // display something went wrong if it does
      return "Something went terribly wrong..... contact support";

      }
      return finalString;
  }

  /**
   * Set the secret key manually
   *
   * @param newKey Secret key
   */
  public static void setKey(SecretKey newKey) {
    key = newKey;
  }

  /** Generate and set a random secret key */
  public static SecretKey genKey() throws InvalidParameterException {

    try {
      //set up the key generator
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      //initialize init vector
      encryptVector = new byte[16];
      //generate random numbers to fill init vector
      SecureRandom randomNumber = new SecureRandom();
      randomNumber.nextBytes(encryptVector);
      //generate and set the key
      return keyGen.generateKey();
    } catch (final NoSuchAlgorithmException e) {
      // AES functionality is part of java se
      throw new IllegalStateException("AES should be preset, please reconfigure Java.", e);
    }
  }

  /**
   * Sets the key, keyword, and encryptVector class variables to null
   */
  public static void wipeMemory() {
    key = null;
    keyword = null;
    encryptVector = null;
  }
}