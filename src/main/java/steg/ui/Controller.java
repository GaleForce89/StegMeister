package steg.ui;

// Import required packages

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;
import steg.StegMeister;
import steg.cryptography.Ciph;
import steg.steganography.Model;

import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

// import org.controlsfx.control.StatusBar;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;
// import javafx.scene.control.TextArea;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import java.sql.*;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import steg.database.InsertData;
// import javafx.scene.layout.Pane;
// import steg.database.Connect;
// import javafx.scene.control.Tab;

public class Controller extends StegMeister implements Initializable {
  /** Private ciph for encryption/decryption. */
  private Ciph cryptogram; // cipher object.

  /** Private model for encoder/decoder. */
  private Model model;

  double imageWidth, imageHeight; // used to hold original image size.

  /**
   * Default controller constructor.
   *
   * @throws NoSuchPaddingException If padding DNE.
   * @throws NoSuchAlgorithmException if algo DNE.
   */
  public Controller() throws NoSuchPaddingException, NoSuchAlgorithmException {
    this.cryptogram = new Ciph(); // initialize new ciph
    this.model = new Model();
  }

  // TEST UI CODE ~Will remove towards end of project.
  /*
  @FXML private javafx.scene.control.TextArea test1; // text areas
  @FXML private javafx.scene.control.TextArea test2;
  @FXML private javafx.scene.control.TextArea test3;
  @FXML private javafx.scene.control.TextField test1input; // inputs
  @FXML private javafx.scene.control.TextField test2input;
  @FXML private javafx.scene.control.TextField test3input;
  @FXML private ImageView imgb = new ImageView(); // before
  @FXML private ImageView imga = new ImageView(); // after
  */

  /** The anchor panes for key, encrypt, decrypt, plain text, and reveal panes. */
  @FXML private AnchorPane keyPane, encryptPane, decryptPane, plainPane, revealPane;
  // panes to control main ui.

  /** The tabpane for credits/about pane. */
  @FXML private TabPane aboutPane;
  // tabpane is different than anchor.

  /** Names assigned to menu buttons. */
  @FXML private JFXButton keyMenu, encryptMenu, decryptMenu, plainMenu, revealMenu, aboutMenu;
  // bring in jfx buttons for menu.
  // currently not used.

  @FXML private StatusBar statusBar;
  // Bottom status bar.

  /** Load in the listview for keys stored in database. */
  @FXML TableView<Listkey> keyTable;
  // key listview.
  // The table's data
  ObservableList<Listkey> dbList;

  /** Columns for table view. */
  @FXML TableColumn<Listkey, String> keyWordCol, keyCol;

  /** Image loaded into memory for viewing. */
  @FXML private ImageView image = new ImageView();

  @FXML private TextField fileHideTxt, fileRevealTxt;
  @FXML private TextArea hideMsgPlain, showMsgPlain;

  @FXML public Image imageMemory;

  /** Set the key pane to be visible. */
  @FXML // set fxml for methods below.
  public void setKeyPane() {
    // key_pane.toFront(); **Seems visibility suits our needs better than bring to front.
    // keep the comment for later optimizations if better way found.
    // Set the visibility of panes.
    keyPane.visibleProperty().set(true); // visible
    encryptPane.visibleProperty().set(false); // /hidden
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(false);

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Key manager");

    // adjust the background colors of buttons to match ui
    // possibly figure out more mouse events to handle this. Or a new active method.
    // keyMenu.getStyleClass().add("JFXbutton-selected"); //activate keyMenu
    // encryptMenu.getStyleClass().add("JFXbutton-inactive");
    // decryptMenu.getStyleClass().add("JFXbutton-inactive");
    // plainMenu.getStyleClass().add("JFXbutton-inactive");
    // revealMenu.getStyleClass().add("JFXbutton-inactive");
    // aboutMenu.getStyleClass().add("JFXbutton-inactive");
    // keyMenu.getStyleClass().add("JFXbutton-selected"); //activate keyMenu
  }

  /** Set the encrypt pane to visible. */
  public void setEncryptPane() {
    // Set the visibility of panes.
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(true);
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(false);

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Encrypt message");
  }

  /** Set decrypt pane to be visible. */
  public void setDecryptPane() {
    // Set the visibility of panes.
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(false);
    decryptPane.visibleProperty().set(true);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(false);

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Decrypt message");
  }

  /** Set plain pane to be visible. */
  public void setPlainPane() {
    // Set the visibility of panes.
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(false);
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(true);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(false);

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Hide plaintext");
  }

  /** Set reveal pane to be visible. */
  public void setRevealPane() {
    // Set the visibility of panes.
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(false);
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(true);
    aboutPane.visibleProperty().set(false);

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Reveal plaintext");
  }

  /** Set the about pane to visible. */
  public void setAboutPane() {
    // Set the visibility of panes.
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(false);
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(true);

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - About");
  }

  /**
   * default initilizer for controller.
   *
   * @param location NULL
   * @param resources NULL
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Set the columns width auto size (Still not working)
    keyTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    keyTable
        .getColumns()
        .get(0)
        .prefWidthProperty()
        .bind(keyTable.widthProperty().multiply(.49)); // 49% for id column size
    keyTable
        .getColumns()
        .get(1)
        .prefWidthProperty()
        .bind(keyTable.widthProperty().multiply(0.49)); // 49% for dt column size

    // table data must match the variables id's in the Listkey class.
    keyCol.setCellValueFactory(new PropertyValueFactory<Listkey, String>("storedKey"));
    keyWordCol.setCellValueFactory(new PropertyValueFactory<Listkey, String>("keyWord"));

    // Set the observable arraylist.
    dbList = FXCollections.observableArrayList();
    keyTable.setItems(dbList);

    // default into (great for loading database)
    Listkey item = new Listkey();
    item.setStoredKey("sdf");
    item.setKeyWord("sdfdfds");
    dbList.add(item);
    Listkey item2 = new Listkey();
    item2.setStoredKey("sdfdsfsdfsd");
    item2.setKeyWord("nooooo");
    dbList.add(item2);
  }

  /**
   * Open file chooser and load the selected image into memory.
   *
   * @throws IOException Print stacktrace.
   */
  public void loadImage() throws IOException {

    // create new filechooser built in javafx dailog.
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open image file"); // set title
    fileChooser
        .getExtensionFilters()
        .addAll( // filter extensions
            new FileChooser.ExtensionFilter("Image Files", "*.jpeg", "*.jpg", "*.png"));
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        // work around to load images
        imageMemory = new Image(file.toURI().toURL().toString());
        image = new ImageView(); // create new to allow garbage collector to clear previous.
        image.setImage(imageMemory);

        // need to set the correct txt field.
        if (plainPane.isVisible()) {
          fileHideTxt.setText(file.toString());

          // clear other text fields
          fileRevealTxt.clear();
        } else if (revealPane.isVisible()) {
          fileRevealTxt.setText(file.toString());

          // clear other fields
          fileHideTxt.clear();
        }
      } catch (IOException ex) { // catch
        ex.printStackTrace();
      }
    }
  }

  /** Method to save current image from memory to disk. */
  public void saveImage() {

    // Encode message into image before saving. (will modify for encrypt later)
    Image encoded = model.encoder.encodeImage(imageMemory, hideMsgPlain.getText());
    imageMemory = encoded;
    image.setImage(imageMemory);

    // similar to loadImage
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Image");
    fileChooser
        .getExtensionFilters()
        .addAll( // filter extensions
            new FileChooser.ExtensionFilter("Image Files", "*.jpeg", "*.jpg", "*.png"));
    File file = fileChooser.showSaveDialog(new Stage()); // show dialog

    if (file != null) {
      try {
        String extension = file.getName(); // get the path
        String fileExtension =
            extension.substring(
                extension.indexOf(".") + 1, file.getName().length()); // get extension.

        // buffered image to save.
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageMemory, null);

        // check if png, jpg, or jpeg.
        switch (fileExtension) {
          case "png":
            ImageIO.write(bImage, "png", file);
            break;
          case "jpg":
            ImageIO.write(bImage, "jpg", file);
            break;
          case "jpeg":
            ImageIO.write(bImage, "jpeg", file);
            break;
          default:
            // write some sort of dialog or something.
            System.out.println("dfgs");
            break;
        }
      } catch (IOException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }

  public void showMsgPlain() {
    String message = model.decoder.decodeImage(imageMemory); // decode
    showMsgPlain.setText(message); // set text.
  }

  /** Called to preview the image in memory. */
  public void previewImage() {
    image.setPreserveRatio(true); // preserve ratio
    image.setSmooth(true); // keep image smooth
    image.setCache(true); // cache image
    Group root = new Group(); // create new group root
    Scene scene = new Scene(root); // create new scene
    scene.setFill(Color.BLACK); // set the scene background black.
    HBox box = new HBox(); // create hbox
    box.setStyle("-fx-background-color: #FFFFFF"); // set white background for hbox.
    box.getChildren().add(image); // add image
    box.setPrefWidth(400); // pref width
    box.setPrefWidth(400); // pref height
    Stage stage = new Stage(); // create new stage
    stage.setTitle("Image Preview"); // set title
    root.getChildren().add(box); // add the hbox to group
    stage.setWidth(400); // pref width
    stage.setHeight(400); // pref height
    stage.setScene(scene); // set the scene
    stage.sizeToScene(); // scene to size scaling
    stage.initOwner(getPrimaryStage()); // set owner as primary stage
    image.fitWidthProperty().bind(stage.widthProperty()); // bind the image to stage for rescaling.
    // set Icon
    stage.getIcons().add(new Image(StegMeister.class.getResourceAsStream("/icons/main_icon.png")));

    // encode before showing
    // Encode message into image before saving. (will modify for encrypt later)
    Image encoded = model.encoder.encodeImage(image.getImage(), hideMsgPlain.getText());
    image.setImage(encoded);
    stage.show(); // show the stage.
  }

  // ****************DELETE AFTER UI IS COMPLETED AND INTEGRATED!
  /*
  @FXML
  public void test1btn() throws NoSuchAlgorithmException, SQLException {

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
    //insert key into database
    /*
    String sql = "INSERT INTO keys(key) VALUES(?)";
    //check connection
    try(Connection conn = this.connect();
        //prepare query
        PreparedStatement pstmnt = conn.prepareStatement(sql)) {
      pstmnt.setString(1, Ciph.Encrypt.toString());
      //execute statement
      pstmnt.executeUpdate();
      System.out.println("key successfully uploaded to database");
    }
    catch(SQLException e){
      System.out.println(e.getMessage());
    }
    //*******************************************
    //* ADD above code into new button, it causes an exception and needs to be isolated
    //*/

  /*
    System.out.println(Ciph.Encrypt.maxKeySize());
    System.out.println(Ciph.Encrypt.getKey()); // console output
    test1.setText(Ciph.Encrypt.getKey().toString()); // gui output
    Connect connObj = new Connect();
    connObj.connect();
    InsertData insertDOBJ = new InsertData();
      try {
          insertDOBJ.insert_Key(Ciph.Encrypt.getKey().toString(), "fill4Now");
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

    /*
    public void setImgb(){ //example set image
       imgb.setImage(imagee);
    }
   // */
  /*
  public void onEncode() {
    Image modified = model.encoder.encodeImage(imgb.loadImage(), test2input.getText());
    imga.setImage(modified);
  }

  public void onDecode() {
    String message = model.decoder.decodeImage(imga.loadImage());
    test3input.setText(message);
  }

  public void injectUI(ImageView original, ImageView modified, TextArea fieldMessage) {
    this.imgb = original;
    this.imga = modified;
    this.test3 = fieldMessage;
  }
  */
}
