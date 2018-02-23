package Steg.Steganography;

import javafx.scene.image.Image;

public interface Encoder {
    Image encodeImage(Image image, String message);
}

