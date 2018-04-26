package steg.cryptography;

import org.apache.commons.codec.binary.Base64;

//import java.io.FileOutputStream;
//import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

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

  /** Get the current key's keyword */
  public static String getKeyword() {

    return keyword;
  }

  /** Set the current key's keyword */
  public static void setKeyword(String kw) {

    keyword = kw;
  }

  /** Encrypt method */
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

  /**Decrypt method */
  public static String decrypt(String msg) throws NoSuchPaddingException, NoSuchAlgorithmException {
    //set up the cipher
      if(stego == null)
          initCipher();
    //use base64 object to decode the encrypted string into a byte array
    byte[] decoded = b64.decode(msg);

    String finalString;
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
   * Get the current key held in memory
   *
   * @return key
   */
  public static String getKeyStr() {
    if(key == null)
      return null;
    byte[] array = key.getEncoded();
    return b64.encodeAsString(array).trim();
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
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");

      encryptVector = new byte[16];
      SecureRandom randomNumber = new SecureRandom();
      randomNumber.nextBytes(encryptVector);

      return keyGen.generateKey();
    } catch (final NoSuchAlgorithmException e) {
      // AES functionality is part of java se
      throw new IllegalStateException("AES should be preset, please reconfigure Java.", e);
    }
  }

  ///**
   //* Save the current key to a file for later use
   //*
   //* @param path The path and file name to save the key
   //*/
  //public static void saveKey(String path) {
    //try (FileOutputStream out = new FileOutputStream(path)) {
      //byte[] keys = key.getEncoded();
      //out.write(keys);
    //} catch (IOException e) {
      //e.printStackTrace();
    //}
  //}
}
