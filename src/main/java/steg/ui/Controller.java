package steg.ui;

// Import required packages
// import com.jfoenix.controls.JFXButton;//currently not in use.

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
import steg.database.Sql;
import steg.steganography.Model;

import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller extends StegMeister implements Initializable {

  /**
   * Private model for encoder/decoder.
   */
  private Model model;

  /**
   * The database sql object. Used for every statement dealing with the database.
   */
  private Sql sql;

  /**
   * Default controller constructor.
   */
  public Controller() {
    this.model = new Model();
    this.sql = new Sql();
  }

  /**
   * The anchor panes for key, encrypt, decrypt, plain text, and reveal panes.
   */
  @FXML
  private AnchorPane keyPane, encryptPane, decryptPane, plainPane, revealPane;
  // panes to control main ui.

  /**
   * The tabpane for credits/about pane.
   */
  @FXML
  private TabPane aboutPane;
  // tabpane is different than anchor.

  /// ** Names assigned to menu buttons. */
  // @FXML private JFXButton keyMenu, encryptMenu, decryptMenu, plainMenu, revealMenu, aboutMenu;
  // bring in jfx buttons for menu.
  // currently not used.

  @FXML
  private StatusBar statusBar;
  // Bottom status bar.

  /**
   * Load in the listview for keys stored in database.
   */
  @FXML
  TableView<Listkey> keyTable;
  // key listview.
  private ObservableList<Listkey> dbList; // The table's data

  /**
   * Columns for table view.
   */
  @FXML
  TableColumn<Listkey, String> keyWordCol, keyCol;

  /**
   * Image loaded into memory for viewing.
   */
  @FXML
  private ImageView image = new ImageView();

  /**
   * Textfield with file locations.
   */
  @FXML
  private TextField fileHideTxt, fileRevealTxt, fileDecryptTxt, fileEncryptTxt, fileKeyTxt;

  /**
   * Text area for message input/output
   */
  @FXML
  private TextArea hideMsgPlain, showMsgPlain, hideMsgEncrypt, showMsgEncrypt;

  /**
   * Load image file into memory separate from imageview.
   */
  @FXML
  private Image imageMemory;

  /**
   * Set the key pane to be visible.
   */
  @FXML // set fxml for methods below.
  public void setKeyPane() {
    // key_pane.toFront(); **Seems visibility suits our needs better than bring to front.
    // keep the comment for later optimizations if better way found.
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      statusBar.setText("No key loaded.");
    } else {
      statusBar.setText("Currently loaded key: " + steg.cryptography.Cryptography.getKeyword());
    }
    // Set the visibility of panes.
    keyPane.visibleProperty().set(true); // visible
    encryptPane.visibleProperty().set(false); // /hidden
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(false);

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Key manager");
  }

  /**
   * Set the encrypt pane to visible.
   */
  public void setEncryptPane() {
    // Set the visibility of panes.
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      statusBar.setText("Load or generate a key before trying to encrypt.");
    } else {
      statusBar.setText("Currently loaded key: " + steg.cryptography.Cryptography.getKeyword());
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(true);
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(false);

    //wipe image memory and file text
    imageMemory = null;
    fileEncryptTxt.setText("");
    hideMsgEncrypt.setText("");

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Encrypt message");
  }
  }

  /**
   * Set decrypt pane to be visible.
   */
  public void setDecryptPane() {
    // Set the visibility of panes.
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      statusBar.setText("Load or generate a key before trying to decrypt.");
      return;
    } else {
      statusBar.setText("Currently loaded key: " + steg.cryptography.Cryptography.getKeyword());
    }
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(false);
    decryptPane.visibleProperty().set(true);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(false);

    //wipe image memory and file text
    imageMemory = null;
    fileDecryptTxt.setText("");
    showMsgEncrypt.setText("");
    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Decrypt message");
  }

  /**
   * Set plain pane to be visible.
   */
  public void setPlainPane() {
    // Set the visibility of panes.
    statusBar.setText("Plaintext mode. Encryption key not currently in use.");
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(false);
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(true);
    revealPane.visibleProperty().set(false);
    aboutPane.visibleProperty().set(false);

    //wipe image memory and file text
    imageMemory = null;
    fileHideTxt.setText("");

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Hide plaintext");
  }

  /**
   * Set reveal pane to be visible.
   */
  public void setRevealPane() {
    // Set the visibility of panes.
    statusBar.setText("Plaintext mode. Encryption key not currently in use.");
    keyPane.visibleProperty().set(false);
    encryptPane.visibleProperty().set(false);
    decryptPane.visibleProperty().set(false);
    plainPane.visibleProperty().set(false);
    revealPane.visibleProperty().set(true);
    aboutPane.visibleProperty().set(false);

    //wipe image memory and file text
    imageMemory = null;
    fileRevealTxt.setText("");

    // adjust stage name.
    getPrimaryStage().setTitle("StegMeister - Reveal plaintext");
  }

  /**
   * Set the about pane to visible.
   */
  public void setAboutPane() {
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      statusBar.setText("No key loaded.");
    } else {
      statusBar.setText("Currently loaded key: " + steg.cryptography.Cryptography.getKeyword());
    }
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
   * @param location  NULL
   * @param resources NULL
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // Set the columns width auto size (Still not working)
    keyTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    keyTable
            .getColumns()
            .get(0)
            .prefWidthProperty()
            .bind(keyTable.widthProperty().multiply(0.4983)); // 49% for id column size
    keyTable
            .getColumns()
            .get(1)
            .prefWidthProperty()
            .bind(keyTable.widthProperty().multiply(0.4983)); // 49% for dt column size

    // table data must match the variables id's in the Listkey class.
    keyCol.setCellValueFactory(new PropertyValueFactory<>("storedKey"));
    keyWordCol.setCellValueFactory(new PropertyValueFactory<>("keyWord"));

    // Set the observable arraylist.
    dbList = FXCollections.observableArrayList();
    keyTable.setItems(dbList);

    // default into (great for loading database)
    // Load Database if exists, else create.
    if (!new File("DB").exists()) {
      // create directory
      new File("DB").mkdir();

      // create db
      //sql.createNewDB("dbKeys.db");
      // create tables
      //sql.createNewTable();
    }
    //steg.database.Connect.connect(); // does not seem to be needed
    refreshDB(); // load the DB.
  }

  /**
   * Generate a random key and set it as the currently active key.
   */
  public void generateKey() {
    steg.cryptography.Cryptography.setKey(steg.cryptography.Cryptography.genKey());
    //immediately save the key to the database so it doesn't get lost
    savetoDB();
  }

  /**
   * Load a key and initialization vector from a text file
   */
  public void loadKey() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open key file"); // set title
    fileChooser
            .getExtensionFilters()
            .addAll( // filter extensions
                    new FileChooser.ExtensionFilter("Key Files", "*.key"));
    File file = fileChooser.showOpenDialog(new Stage());
    //Uncomment when integrated with KeyFile.java
    //steg.io.KeyFile.loadKey(file);
  }

  /**
   * Save a key and initialization vector to a text file
   */
  public void saveKey() {
    if (steg.cryptography.Cryptography.getKeyStr() != null) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save key file"); // set title
      fileChooser
              .getExtensionFilters()
              .addAll( // filter extensions
                      new FileChooser.ExtensionFilter("Key Files", "*.key"));
      File file = fileChooser.showSaveDialog(new Stage());
      if (file != null) {
        try {
          //Uncomment next line when integrated with KeyFile.java
          //steg.io.KeyFile.writeKey(file);
          //substring to just the file path without file:/
          statusBar.setText("Key saved to file: " + file.toURI().toURL().toString().substring(6));
        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    } else {
      statusBar.setText("Load or generate a key before trying to save it.");
    }
  }

  /**
   * Open file chooser and load the selected image into memory.
   */
  public void loadImage() {
    // create new filechooser built in javafx dailog.
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open image file"); // set title
    fileChooser
            .getExtensionFilters()
            .addAll( // filter extensions
                    new FileChooser.ExtensionFilter("Image Files", "*.png"));
    File file = fileChooser.showOpenDialog(new Stage());
    if (file != null) {
      try {
        // work around to load images
        imageMemory = new Image(file.toURI().toURL().toString());
        // image = new ImageView(); // create new to allow garbage collector to clear previous.
        image.setImage(imageMemory);

        // need to set the correct txt field.
        if (plainPane.isVisible()) {

          fileHideTxt.setText(file.toString());

          // clear other text fields
          fileRevealTxt.clear();
          fileEncryptTxt.clear();
          fileDecryptTxt.clear();
        } else if (revealPane.isVisible()) {
          fileRevealTxt.setText(file.toString());

          // clear other fields
          fileHideTxt.clear();
          fileEncryptTxt.clear();
          fileDecryptTxt.clear();
        } else if (encryptPane.isVisible()) {
          fileEncryptTxt.setText(file.toString());

          // clear other fields
          fileRevealTxt.clear();
          fileHideTxt.clear();
          fileDecryptTxt.clear();
        } else if (decryptPane.isVisible()) {
          fileDecryptTxt.setText(file.toString());

          // clear other fields
          fileRevealTxt.clear();
          fileHideTxt.clear();
          fileEncryptTxt.clear();
        }
      } catch (IOException ex) { // catch
        ex.printStackTrace();
      }
    }
  }

  /**
   * Method to save current image from memory to disk.
   */
  public void saveImage() throws NoSuchPaddingException, NoSuchAlgorithmException {
    if (imageMemory == null) {
      statusBar.setText("Load an image before trying to hide a message in it.");
    } else {
      // similar to loadImage
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save Image");
      fileChooser
              .getExtensionFilters()
              .addAll( // filter extensions, only allowing png types
                      new FileChooser.ExtensionFilter("Image Files", "*.png"));
      File file = fileChooser.showSaveDialog(new Stage()); // show dialog

      if (file != null) {
        try {
          // Encode message into image before saving.
          if (encryptPane.isVisible())
            imageMemory =
                    model.encoder.encodeImage(
                            imageMemory, steg.cryptography.Cryptography.encrypt(hideMsgEncrypt.getText()));
          else imageMemory = model.encoder.encodeImage(imageMemory, hideMsgPlain.getText());
          image.setImage(imageMemory);
          // buffered image to save.
          BufferedImage bImage = SwingFXUtils.fromFXImage(imageMemory, null);
          // only allowing png types
          ImageIO.write(bImage, "png", file);
        } catch (IOException ex) {
          System.out.println(ex.getMessage());
        }
      }
    }
  }

  /**
   * Message to reveal plain message in TextArea.
   */
  public void showMsgPlain() {
    if (imageMemory == null) {
      statusBar.setText("Load an image before trying to reveal its message.");
    } else {
      String message = model.decoder.decodeImage(imageMemory); // decode
      showMsgPlain.setText(message); // set text.
    }
  }

  /**
   * Method to reveal encrypted message in TextArea.
   */
  public void showMsgEncrypt() throws NoSuchPaddingException, NoSuchAlgorithmException {
    if (imageMemory == null) {
      statusBar.setText("Load an image before trying to reveal its message.");
    } else {
      String encryptedMsg = model.decoder.decodeImage(imageMemory);
      String decryptedMsg = steg.cryptography.Cryptography.decrypt(encryptedMsg);
      showMsgEncrypt.setText(decryptedMsg);
    }
  }

  /**
   * Called to preview the image in memory.
   */
  public void previewImage() throws NoSuchPaddingException, NoSuchAlgorithmException {
    // Encode message into image before saving
    if (imageMemory == null) {
      statusBar.setText("Load an image before trying to preview it.");
    } else {
      if (encryptPane.isVisible())
        imageMemory =
                model.encoder.encodeImage(
                        imageMemory, steg.cryptography.Cryptography.encrypt(hideMsgEncrypt.getText()));
      else imageMemory = model.encoder.encodeImage(imageMemory, hideMsgPlain.getText());
      image.setImage(imageMemory);
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
      stage.show(); // show the stage.
    }
  }

  /**
   * Refresh the table UI with database values
   */
  public void refreshDB() {
    try {
      //clear current list of table items
      dbList.clear();
      //query the database for keys, keywords
      ResultSet r = sql.getUpdateInfo();
      //loop while the resultset still has items
      while (r.next()) {
        //create a new Listkey item per entry in the database
        Listkey item = new Listkey();
        //set key and keyword for each item
        item.setStoredKey(r.getString(1));
        item.setKeyWord(r.getString(2));
        //add the item to the list
        dbList.add(item);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Load a key from the database
   */
  public void loadFromDb() {

    try {
      //get key and keyword from Listkey table
      Listkey item = keyTable.getSelectionModel().getSelectedItem();
      //make sure an item was selected
      if (item == null) {
        //if no item was selected, set status bar to show this and exit
        statusBar.setText("Select a key to load before pressing the load button.");
      } else {
        //if there was an item selected, set the key and keyword from table values
        steg.cryptography.Cryptography.setKey(item.storedKey);
        steg.cryptography.Cryptography.setKeyword(item.keyWord);
        //get init vector from database (query based on the corresponding keyword)
        String initV = sql.getInitVector(item.keyWord);
        //set the init vector
        steg.cryptography.Cryptography.setVector(initV);
        //set status bar to show completion
        statusBar.setText("Currently loaded key: " + steg.cryptography.Cryptography.getKeyword());
      }
    } catch(Exception e){
      e.printStackTrace();
      }
  }

  public void savetoDB() {
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      statusBar.setText("Generate or load a key before trying to save it to the database.");
    } else {
      // Create a dialog
      TextInputDialog dialog = new TextInputDialog("Keyword");
      dialog.initOwner(getPrimaryStage());
      dialog.setTitle("Add key to database");
      dialog.setHeaderText("Please enter keyword for selected key.");
      dialog.setContentText("Keyword:");

      // Traditional way to get the response value.
      Optional<String> result = dialog.showAndWait();
      //if the user hits cancel, don't add anything
      if (result.isPresent()) {
        //if the user entered a value for the keyword, make sure it's not just whitespace
        if(result.get().trim().length() == 0) {
          statusBar.setText("Keywords cannot have a blank name.");
          //if the user entered a value for the keyword, make sure it's unique in the database
        }else if (sql.inDb(result.get())) {
          //if a keyword is in the db that matches what the user typed, let them know
          statusBar.setText("A key with keyword " + "\"" + result.get() + "\" is already stored.");
          //key and init vector are automatically generated before the call to this method
          //so we need to set everything to null since keyword was invalid
          steg.cryptography.Cryptography.wipeMemory();
        } else {
          try {
            //if the user input a keyword, add the key, keyword, init vector to db
            sql.insert_Key(
                    steg.cryptography.Cryptography.getKeyStr(),
                    result.get(),
                    steg.cryptography.Cryptography.getVector());
          } catch (SQLException e) {
            e.printStackTrace();
          }
          //set status bar to show completion
          statusBar.setText("Key \"" + result.get() + "\" saved to the database.");
          //set the input as currently loaded key's keyword
          steg.cryptography.Cryptography.setKeyword(result.get());
        }
      }
    }
    refreshDB();
  }

  /**
   * Delete an item from the database
   */
  public void deleteFromDb() {
    //use Listkey to check if an item was selected
    Listkey item = keyTable.getSelectionModel().getSelectedItem();
    if (item == null) {
      //if there was no selection, change statusBar text and exit method
      statusBar.setText("Select a key to delete before pressing the delete button.");
      return;
    }
    try {
      //if there was a selection, delete the row that matches the keyword from the item
      sql.deleteRow(item.getKeyWord());
    } catch (Exception e) {
      e.printStackTrace();
    }
    refreshDB();
  }
}