package Steg.Cryptography;

import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

public class Ciph {
  public Encrypt Encrypt; // public interfaces
  public Decrypt Decrypt;

  public Ciph() throws NoSuchAlgorithmException, NoSuchPaddingException {
    this.Encrypt = new Encrypt();
    this.Decrypt = new Decrypt();
  }
}