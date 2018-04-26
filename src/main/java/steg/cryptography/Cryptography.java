package steg.cryptography;

import org.apache.commons.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/** The cipher class is the main class used for encrypting/decrypting a file */
public class Cryptography {
  private static SecretKey key; // used to enter password
  private static int keySize = 128; // used to set keysize
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

  /** Clear class variables */
  public static void clearVariables() {
    key = null;
    encryptVector = null;
  }

  /** Encrypt method */
  public static String encrypt(String msg) throws NoSuchPaddingException, NoSuchAlgorithmException {
    initCipher();

    //*setKey(genKey(keySize));
    encryptVector = new byte[16];
    SecureRandom randomNumber = new SecureRandom();
    randomNumber.nextBytes(encryptVector);
    IvParameterSpec ivpspec = new IvParameterSpec(encryptVector);

    String encryptString;
    //*Base64 b64 = new Base64();

    try {
      //set up the Cipher object with the secret key and the initialization vector
      stego.init(Cipher.ENCRYPT_MODE, key, ivpspec);
      //put message in byte array
      byte[] in = msg.getBytes("UTF-8");
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
  public static String decrypt(String msg, String initStr) throws NoSuchPaddingException, NoSuchAlgorithmException {
    initCipher();
    //use base64 object to decode the encrypted string into a byte array
    byte[] decoded = b64.decode(msg);
    byte[] initVector = b64.decode(initStr);

    String finalString;
    IvParameterSpec ivpspec = new IvParameterSpec(initVector);
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
  public static SecretKey getKey() {
    return key;
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
  public static SecretKey genKey() throws InvalidParameterException { //* removed keySize from parameters

    try {
      // throw exception if key size is too large
      if (Cipher.getMaxAllowedKeyLength("AES") < keySize) {
        // unlimited crypto is not installed
        throw new InvalidParameterException(
                "Key size of " + keySize + "bits not supported in this runtime, download JCE files");
      }

      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      return keyGen.generateKey();
    } catch (final NoSuchAlgorithmException e) {
      // AES functionality is part of java se
      throw new IllegalStateException("AES should be preset, please reconfigure java", e);
    }
  }

  /**
   * Save the current key to a file for later use
   *
   * @param path The path and file name to save the key
   */
  public static void saveKey(String path) {
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
  public static int maxKeySize() throws NoSuchAlgorithmException {
    return Cipher.getMaxAllowedKeyLength("AES");
  }
}
