package steg.steganography;


/**
 * All credit for this file belongs to
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 * github: https://github.com/AlmasB/FXTutorials/tree/master/src/com/almasb/steg
 */

import java.util.stream.IntStream;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.util.Pair;

public class BasicDecoder {
  public String decodeImage(Image image) {
    int imageWidth = (int) image.getWidth();
    int imageHeight = (int) image.getHeight();

    PixelReader reader = image.getPixelReader();
    // Read all of the bits of the image
    boolean[] bits = new boolean[imageWidth * imageHeight];
    IntStream.range(0, imageWidth * imageHeight)
        .mapToObj(i -> new Pair<>(i, reader.getArgb(i % imageWidth, i / imageWidth)))
        .forEach(
            pair -> {
              // Convert pair values to binary
              String binary = Integer.toBinaryString(pair.getValue());
              // Read least significant bit and determine if least significant bit is 1 or 0
              bits[pair.getKey()] = binary.charAt(binary.length() - 1) == '1';
            });
    // decodes the length in order to read the message
    int length = 0;
    for (int i = 0; i < 32; i++) {
      //if bit is set
      if (bits[i]) {
        //left shift the bit by 31 - i
        length |= (1 << (31 - i));
      }
    }
    // Create array equal to length value from above
    byte[] data = new byte[length];
    for (int i = 0; i < length; i++)
      // read 8 bits decoded into a single byte
      for (int j = 0; j < 8; j++) {
          /**
           * Use the 32 bit offset
           * plus i * 8 which is the number of bits
           * plus j to navigate to the particular bit in use
           * then shift the bit left 7-j places then check to see if
           * the bit is equal to 1 if it is, then it is set in that byte
           */
        if (bits[32 + i * 8 + j]){ data[i] |= (1 << (7 - j));}
      }
    // Return byte array containing message
    return new String(data);
  }
}
