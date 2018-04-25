package steg.cryptography;

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.codec.binary.Base64;


/** The encrypt class is used for all things encryption, to include key generation. */
public class Encrypt extends Cryptography {

  /** Default constructor to null */
  public Encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException {
    super();
    setKey(genKey(keySize));
    byte[] initvector = new byte[16];
    SecureRandom randomNumber = new SecureRandom();
    randomNumber.nextBytes(initvector);
    ivpspec = new IvParameterSpec(initvector);
  }

  public String Crypt(String msg) {

    //try using cipher block chaining, need to create a random initialization vector so ciphertext is different
      // with each encryption
    //create byte array to fill initialization vector
    //byte[] initVector = new byte[16];
    //new random number to randomize initialization vector
    //SecureRandom randomNumber = new SecureRandom();
    //fill byte array with random numbers
    //randomNumber.nextBytes(initVector);
    //set up initialization vector with random byte array
    //ivpspec = new IvParameterSpec(initVector);

    //save init vector to a file/db?

    //key
    String encryptString;
    Base64 b64 = new Base64();

    try {
        //set up the Cipher object with the secret key and the initialization vector
        stego = Cipher.getInstance("AES/CBC/PKCS5Padding");
        stego.init(Cipher.ENCRYPT_MODE, getKey(), ivpspec);
        //put message in byte array
        byte[] in = msg.getBytes("UTF-8");
        System.out.print("Message as a byte array: ");
        for(byte a : in) {
          System.out.print(a);
        }
        System.out.println();
        //encrypt message using Cipher object and store in byte array
        byte[] out = stego.doFinal(in);
        System.out.print("Encrypted message as byte array: ");
        for(byte b : out) {
          System.out.print(b);
        }
        System.out.println();
        encryptString = b64.encodeToString(out).trim();
        System.out.print("\nEncrypted message as a string: " + encryptString + "\n");
        //catch if we run into issues
    } catch (Exception ex) {
      ex.printStackTrace();
        // display something went wrong if it does
        return "Something went terribly wrong..... contact support";

    }
    return encryptString;


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
