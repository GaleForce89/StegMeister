package Steg.Steganography;

public class Model {
    public BasicEncoder encoder;
    public BasicDecoder decoder;

    public Model(){
        this.encoder = new BasicEncoder();
        this.decoder = new BasicDecoder();
    }
    //public Image encodeImage(Image image, String message){
   //     return encoder.encodeImage(image, message);
  //  }
  //  public String decodeImage(Image image){
   //     return decoder.decodeImage(image);
   // }
}