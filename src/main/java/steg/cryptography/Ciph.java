package steg.cryptography;

import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

public class Ciph {
  public Encrypt Encrypt; // public interfaces
  public Decrypt Decrypt;

  public Ciph() throws NoSuchAlgorithmException, NoSuchPaddingException {
    this.Encrypt = new Encrypt();
    this.Decrypt = new Decrypt(this.Encrypt.getKey(), this.Encrypt.ivpspec);

    String message = "Test message";
    System.out.println("Original message: " + message);
    String encrypted = Encrypt.Crypt(message);
    String decrypted = Decrypt.Crypt(encrypted);
  }
}