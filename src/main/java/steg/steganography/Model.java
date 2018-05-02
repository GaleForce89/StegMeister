package steg.steganography;

/**
 * All credit for this file belongs to
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 * github: https://github.com/AlmasB/FXTutorials/tree/master/src/com/almasb/steg
 */

public class Model {
  public BasicEncoder encoder;
  public BasicDecoder decoder;

  public Model() {
    this.encoder = new BasicEncoder();
    this.decoder = new BasicDecoder();
  }
}
