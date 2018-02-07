/**
 * <h1>StegMeister</h1><br>
 *     StegMeister, hidden opportunities <br><br>
 *         <strong>Features include:</strong><br><br>
 *             <ul>
 *                 <li>Hidden messages</li>
 *                 <li>Encrypted backend</li>
 *                 <li>Key generation</li>
 *                 <li>Fancy UI</li>
 *             </ul>
 *
 * @author Zachary Gale
 * @author Chris Waterman
 * @author add name
 * @author add name
 * @version 0.1 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */

package Steg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StegMeister extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        primaryStage.setTitle("StegMeister");
        primaryStage.getIcons().add(new Image(StegMeister.class.getResourceAsStream("icon.png")));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
