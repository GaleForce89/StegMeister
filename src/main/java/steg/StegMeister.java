/**
 *
 *
 * <h1>StegMeister</h1>
 *
 * <p><br>
 * StegMeister, hidden opportunities <br>
 * <br>
 * <strong>Features include:</strong><br>
 * <br>
 *
 * <p>
 *
 * <ul>
 *   <li>Hidden messages
 *   <li>Encrypted backend (Work in progress)
 *   <li>Key generation
 *   <li>Fancy UI
 * </ul>
 *
 * @author Zachary Gale
 * @author Chris Waterman
 * @author add name
 * @author add name
 * @version 1.0 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
package steg; // Main package name. all other classes will branch off of main package.

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class StegMeister extends Application {

  private static Stage pStage; // primary stage

  /**
   * StegMeister main method. Commandline not implemented. //TODO
   *
   * @param args N/A
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * Default error exception handling. launches the showerrordialog.
   *
   * @param t Thread in which error occurred.
   * @param e The caught exception.
   */
  private static void showError(Thread t, Throwable e) {
    System.err.println("***Default exception handler***");
    if (Platform.isFxApplicationThread()) {
      showErrorDialog(e);
    } else {
      System.err.println("An unexpected error occurred in " + t);
    }
  }

  /**
   * Default error dialog, catches all exceptions in ui.
   *
   * @param e Stack trace.
   */
  private static void showErrorDialog(Throwable e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Exception Dialog"); // set title
    alert.setHeaderText(
        "Something went horribly wrong..."); // set header (needs updating to specify)
    alert.setContentText("Click the button to view");
    alert.setResizable(true);
    alert.initOwner(getPrimaryStage()); // set main stage as owner.

    // Create expandable Exception.
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw); // print pipe stacktrace into print writer.
    String exceptionText = sw.toString();

    Label label = new Label("The exception stacktrace was:"); // label to inform user.

    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false); // do not allow user to edit the textarea.
    textArea.setWrapText(true); // wrap text if needed.

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.setMaxHeight(Double.MAX_VALUE);
    expContent.add(label, 0, 0);
    expContent.add(textArea, 0, 1);

    // Set expandable Exception into the dialog pane.
    alert.getDialogPane().setExpandableContent(expContent);
    alert.getDialogPane().setPrefWidth(600);

    alert.showAndWait(); // show and wait.
  }

  /**
   * Get the primary stage of the project.
   *
   * @return Primary stage.
   */
  public static Stage getPrimaryStage() {
    return pStage;
  }

  /**
   * Set the primary stage of the project.
   *
   * @param pStage Primary stage.
   */
  private void setPrimaryStage(Stage pStage) {
    StegMeister.pStage = pStage;
  }

  /**
   * Runs after main, loads the project with fxml loader.
   *
   * @param primaryStage The primary stage called with fxml loader.
   * @throws Exception A catch all exception.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(StegMeister::showError); // global exception handling.
    setPrimaryStage(primaryStage); // set the primary stage
    Parent root = FXMLLoader.load(getClass().getResource("/ui/Main.fxml")); // load fxml file
    primaryStage.setTitle("StegMeister - Key manager"); // Set the title of initial window.
    primaryStage
        .getIcons() // get icon file
        .add(new Image(StegMeister.class.getResourceAsStream("/icons/main_icon.png")));
    primaryStage.setScene(new Scene(root, 800, 490)); // set preferred size.
    primaryStage.show(); // show stage.
  }
}
