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
    this.key = k;
    this.ivpspec = iv;
  }

  public String Crypt(String msg) {
    Base64 b64 = new Base64();
    byte[] decoded = b64.decode(msg);
    System.out.print("Encrypted message as a byte array: ");
    for(byte a : decoded) {
      System.out.print(a);
    }

    String done;
    //IvParameterSpec ivspec = new IvParameterSpec(initVector);
    try {

      Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
      c.init(Cipher.DECRYPT_MODE, getKey(), ivpspec);
    done = new String(c.doFinal(decoded), "UTF-8");
    } catch (Exception ex) {
      ex.printStackTrace();
      // display something went wrong if it does
      return "Something went terribly wrong..... contact support";

    }
    System.out.println("\nDecoded string: " + done);
    return done;
  }
}
