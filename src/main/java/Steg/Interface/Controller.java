package Steg.Interface;

// Import required packages for javafx

import Steg.Cryptography.Ciph;
import Steg.StegMeister;
import Steg.Steganography.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public class Controller extends StegMeister {
    private Ciph Ciph;
    private Model model;
    // Image imagee = new
    // Image("https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/White_Cliffs_of_Dover_02.JPG/450px-White_Cliffs_of_Dover_02.JPG");
    @FXML
    private javafx.scene.control.TextArea test1; // text areas
    @FXML
    private javafx.scene.control.TextArea test2;
    @FXML
    private javafx.scene.control.TextArea test3;
    @FXML
    private javafx.scene.control.TextField test1input; // inputs
    @FXML
    private javafx.scene.control.TextField test2input;
    @FXML
    private javafx.scene.control.TextField test3input;
    @FXML
    private ImageView imgb = new ImageView(); // before
    @FXML
    private ImageView imga = new ImageView(); // after

    public Controller() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.Ciph = new Ciph(); // initialize new ciph
        this.model = new Model();
        // this.model = model;
    }

    @FXML
    public void test1btn() throws NoSuchAlgorithmException {

        // display error if the input is empty **update when we add keys**
        if (test1input.getText().trim().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR); // built in javafx alert dialogs
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a password or select a key file");
            alert.initOwner(getPrimaryStage());
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(400, 180);
            alert.showAndWait();

            return; // exit
        }

        // test1.setText(test1input.getText()); //Test to see if ui functions and it does
        // Encrypt cryptotest = new Encrypt(); //create a new encryption object
        // Ciph Ciph = new Ciph();

        String msg = "Hopeitworks"; // the message
        String key = "don'tfailmenopwAES"; // our key
        String sMsg;

        // sMsg = cryptotest.Crypt(msg, key);
        Ciph.Encrypt.setKeyRand();
        Ciph.Encrypt.saveKey("saved.key");
        System.out.println(Ciph.Encrypt.maxKeySize());
        System.out.println(Ciph.Encrypt.getKey()); // console output
        test1.setText(Ciph.Encrypt.getKey().toString()); // gui output
    }

    /*
    public void setImgb(){ //example set image
       imgb.setImage(imagee);
    }
    */
    public void onEncode() {
        Image modified = model.encoder.encodeImage(imgb.getImage(), test2input.getText());
        imga.setImage(modified);
    }

    public void onDecode() {
        String message = model.decoder.decodeImage(imga.getImage());
        test3input.setText(message);
    }

    public void injectUI(ImageView original, ImageView modified, TextArea fieldMessage) {
        this.imgb = original;
        this.imga = modified;
        this.test3 = fieldMessage;
    }
}
