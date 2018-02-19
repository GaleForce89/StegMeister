package Steg;

//Import required packages for javafx
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.security.NoSuchAlgorithmException;

public class Controller {

    @FXML private javafx.scene.control.TextArea test1; //text areas
    @FXML private javafx.scene.control.TextArea test2;
    @FXML private javafx.scene.control.TextArea test3;
    @FXML private javafx.scene.control.TextField test1input; //inputs
    @FXML private javafx.scene.control.TextField test2input;
    @FXML private javafx.scene.control.TextField test3input;

    @FXML
    public void test1btn() throws NoSuchAlgorithmException {

        //display error if the input is empty **update when we add keys**
        if(test1input.getText().trim().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR); //built in javafx alert dialogs
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a password or select a key file");
            alert.showAndWait();

            return;//exit
        }

        //test1.setText(test1input.getText()); //Test to see if ui functions and it does
        Cipher cryptotest = new Cipher(); //create a new encryption object

        String msg = "Hopeitworks"; //the message
        String key = "don'tfailmenopwAES"; //our key
        String sMsg;

        //sMsg = cryptotest.Crypt(msg, key);
        cryptotest.setKeyRand();
        cryptotest.saveKey("saved.key");
        System.out.println(cryptotest.getKey());//console output
        test1.setText(cryptotest.getKey().toString());//gui output


    }


}
