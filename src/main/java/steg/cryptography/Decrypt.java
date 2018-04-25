package steg.cryptography;

import org.apache.commons.codec.binary.Base64;

import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Decrypt extends Cryptography {
  /** Default constructor to null */
  public Decrypt(SecretKey k, IvParameterSpec iv) throws NoSuchAlgorithmException, NoSuchPaddingException {
    super();
    //set key and IvParameterSpec variables created when encryption happened
    this.key = k;
    this.ivpspec = iv;
  }

  public String Crypt(String msg) {
    //new base64 object for decryption
    Base64 b64 = new Base64();
    //use base64 object to decode the encrypted string into a byte array
    byte[] decoded = b64.decode(msg);
    System.out.print("Encrypted message as a byte array: ");
    for(byte a : decoded) {
      System.out.print(a);
    }

    String done;
    //IvParameterSpec ivspec = new IvParameterSpec(initVector);
    try {
      //create a new cipher object
      Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
      //set up the cipher object to decrypt using the key and IvParameterSpec
      c.init(Cipher.DECRYPT_MODE, getKey(), ivpspec);
      //decrypt the byte array using the cipher object, store decrypted plaintext in a string
      done = new String(c.doFinal(decoded), "UTF-8");
    } catch (Exception ex) {
      ex.printStackTrace();
      // display something went wrong if it does
      return "Something went terribly wrong..... contact support";

    }
    return done;
  }
}
