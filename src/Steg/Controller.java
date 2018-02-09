package Steg;

//Import required packages for javafx
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {

    @FXML private javafx.scene.control.TextArea test1; //text areas
    @FXML private javafx.scene.control.TextArea test2;
    @FXML private javafx.scene.control.TextArea test3;
    @FXML private javafx.scene.control.TextField test1input; //inputs
    @FXML private javafx.scene.control.TextField test2input;
    @FXML private javafx.scene.control.TextField test3input;

    @FXML
    public void test1btn(){

        //test1.setText(test1input.getText()); //Test to see if ui functions and it does
        EnCrypt cryptotest = new EnCrypt(); //create a new encryption object

        String msg = "Hopeitworks"; //the message
        String key = "don'tfailmenopwAES"; //our key
        String sMsg;

        sMsg = cryptotest.Crypt(msg, key);

        test1.setText(sMsg);


    }


}
