package Steg.Steganography;

import javafx.scene.image.Image;

public class Model {
    private Encoder encoder;
    private Decoder decoder;

    public Model(Encoder encoder, Decoder decoder){
        this.encoder = encoder;
        this.decoder = decoder;
    }
    public Image encodeImage(Image image, String message){
        return encoder.encodeImage(image, message);
    }
    public String decodeImage(Image image){
        return decoder.decodeImage(image);
    }
}