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
 *   <li>Encrypted backend
 *   <li>Key generation
 *   <li>Fancy UI
 * </ul>
 *
 * @author Zachary Gale
 * @author Chris Waterman
 * @author add name
 * @author add name
 * @version 0.1 (Current version number)
 * @since 0.1 (The version that the class was first added to the project)
 */
package steg;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.DriverManager;

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
import steg.database.Connect;
import steg.database.CreateDB;
import steg.database.CreateTable;
import steg.database.InsertData;
import java.sql.*;

public class StegMeister extends Application {

  private static Stage pStage; // primary stage

  public static void main(String[] args) {
    Connect connObj = new Connect();
    connObj.connect();
    CreateDB DBObj = new CreateDB();
    DBObj.createNewDB("dbKeys.db");
    CreateTable createT = new CreateTable();
    createT.createNewTable();
    InsertData insertDOBJ = new InsertData();
    launch(args);

      //create new DB file structure if doesn't exist
      //createNewDB("test.db");
      //launch(args);
  }

  /*
  public static void createNewDB(String fName){
      //connect to DB
      String url = "jdbc:sqlite:C:/SQLite/db/" + fName;

      try(Connection conn = DriverManager.getConnection(url)){
          if(conn != null){
              DatabaseMetaData meta = conn.getMetaData(); //get metadata if db exists
              System.out.println("the driver name is " + meta.getDriverName());
              System.out.println("New Database created successfully.");
          }
      }catch(SQLException e){
            System.out.println(e.getMessage());
      }
  }
  */

  private static void showError(Thread t, Throwable e) {
    System.err.println("***Default exception handler***");
    if (Platform.isFxApplicationThread()) {
      showErrorDialog(e);
    } else {
      System.err.println("An unexpected error occurred in " + t);
    }
  }

  private static void showErrorDialog(Throwable e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Exception Dialog");
    alert.setHeaderText("Something went horribly wrong...");
    alert.setContentText("Click the button to view");
    alert.setResizable(true);
    alert.initOwner(getPrimaryStage());

    // Create expandable Exception.
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    String exceptionText = sw.toString();

    Label label = new Label("The exception stacktrace was:");

    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);

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

    alert.showAndWait();
  }

  public static Stage getPrimaryStage() {
    return pStage;
  }

  private void setPrimaryStage(Stage pStage) {
    StegMeister.pStage = pStage;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Thread.setDefaultUncaughtExceptionHandler(StegMeister::showError);
    setPrimaryStage(primaryStage);
    Parent root = FXMLLoader.load(getClass().getResource("/ui/Main.fxml"));
    primaryStage.setTitle("StegMeister");
    primaryStage
        .getIcons()
        .add(new Image(StegMeister.class.getResourceAsStream("/icons/main_icon.png")));
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
  }
}
