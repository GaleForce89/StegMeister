package steg.steganography;

/**
 * All credit for this file belongs to
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 * github: https://github.com/AlmasB/FXTutorials/tree/master/src/com/almasb/steg
 */

import java.util.stream.IntStream;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;

public class BasicEncoder {
  public Image encodeImage(Image image, String message) {
    // Gets the width and height of the image in use
    int width = (int) image.getWidth();
    int height = (int) image.getHeight();
    // Creates an obeject that is a copy of the original image
    WritableImage copy = new WritableImage(image.getPixelReader(), width, height);
    // Creates a writer object that allows you to manipulate the pixels in the image
    PixelWriter writer = copy.getPixelWriter();
    // Creates a reader object that allows you to read the pixels in the image
    PixelReader reader = image.getPixelReader();

    boolean[] bits = encodeImage(message);
    // Alter least significant bit of each pixel
    IntStream.range(0, bits.length)
        // get x and y values of pixel to read and wrap into a pair of an index and a pixel
        .mapToObj(i -> new Pair<>(i, reader.getArgb(i % width, i / width)))
        // If bit is set, then set the least significant bit to 1
        // If bit is not set, set it to 0
        .map(pair -> new Pair<>(pair.getKey(), bits[pair.getKey()] ? pair.getValue() | 1 : pair.getValue() & ~1))
        .forEach(pair -> {
              // Key is the i we are passing through
              int x = pair.getKey() % width;
              int y = pair.getKey() / width;
              // Write modified pixed to the copy of the original image
              writer.setArgb(x, y, pair.getValue());
            });

    return copy;
  }

  private boolean[] encodeImage(String message) {
    //reserve space for message data
    byte[] data = message.getBytes();
    /**
     * Create a boolean array which uses
     * the length which is an integer so we need 32 bits and
     * data.length which is in bytes therefore
     * you multiply the length by 8 to get it's size in bits.
     */
    boolean[] bits = new boolean[32 + data.length * 8];
    // Convert length of message to binary
    String binary = Integer.toBinaryString(data.length);
    // Add zeroes to your binary string
    while (binary.length() < 32) {
      binary = "0" + binary;
    }
    // If character is equal to 1, set bit
    for (int i = 0; i < 32; i++) {
      bits[i] = binary.charAt(i) == '1';
    }
    // Encode message
    for (int i = 0; i < data.length; i++) {
      // Get byte from data array
      byte b = data[i];
      // Convert bytes to bits
      for (int j = 0; j < 8; j++) {
        /**
         * Use the 32 bit offset
         * plus i * 8 which is the number of bits
         * plus j to navigate to the particular bit in use
         * then shift the bits 7-j places then check to see if
         * the bit is equal to 1 if it is, then it is set in that byte
         */
        bits[32 + i * 8 + j] = ((b >> (7 - j)) & 1) == 1;
      }
    }
    //return the bits generated
    return bits;
  }
}
