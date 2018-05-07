package steg.steganography;

/**
 * All credit for this file belongs to
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 * github: https://github.com/AlmasB/FXTutorials/tree/master/src/com/almasb/steg
 */

public class Model {
  // Reference the encoder and decoder from BasicEncoder and BasicDecoder classes
  public BasicEncoder encoder;
  public BasicDecoder decoder;

  public Model() {
    // Creates basic encoder and basic decoder objects
    this.encoder = new BasicEncoder();
    this.decoder = new BasicDecoder();
  }
}
