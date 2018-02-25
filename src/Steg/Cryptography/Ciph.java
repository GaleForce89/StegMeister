package Steg.Cryptography;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class Ciph {
    public Encrypt Encrypt; //public interfaces
    public Decrypt Decrypt;

    public Ciph() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.Encrypt =  new Encrypt();
       this.Decrypt = new Decrypt();
    }
}
