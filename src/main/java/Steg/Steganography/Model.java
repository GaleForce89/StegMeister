package Steg.Steganography;

public class Model {
  public BasicEncoder encoder;
  public BasicDecoder decoder;

  public Model() {
    this.encoder = new BasicEncoder();
    this.decoder = new BasicDecoder();
  }
}
