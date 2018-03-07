package Steg.Steganography;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.util.Pair;

import java.util.stream.IntStream;

public class BasicDecoder {
    public String decodeImage(Image image) {
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();

        PixelReader reader = image.getPixelReader();

        boolean[] bits = new boolean[imageWidth * imageHeight];
        IntStream.range(0, imageWidth * imageHeight)
            .mapToObj(i -> new Pair<>(i, reader.getArgb(i % imageWidth, i / imageWidth)))
            .forEach(pair -> {
                String binary = Integer.toBinaryString(pair.getValue());
                bits[pair.getKey()] = binary.charAt(binary.length() - 1) == '1';
            });
        //decodes the length
        int length = 0;
        for (int i = 0; i < 32; i++) {
            if (bits[i]) {
                length |= (1 << (31 - i));
            }
        }

        byte[] data = new byte[length];
        for (int i = 0; i < length; i++)
            for (int j = 0; j < 8; j++) {
                if (bits[32 + i * 8 + j])
                    data[i] |= (1 << (7 - j));
            }

        return new String(data);
    }
}
