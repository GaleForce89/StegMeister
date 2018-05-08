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
import steg.io.KeyFile;
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
    //upon loading the pane, if there is no key loaded, set the status bar to display this
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      statusBar.setText("No key loaded.");
    } else {
      //otherwise display the key name that is currently loaded
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
    //upon loading the pane, check to see if there is a key loaded
    //if no key loaded, do not switch panes
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      statusBar.setText("Load or generate a key before trying to encrypt.");
    } else {
      //if there is a key loaded, display the key and switch panes
      statusBar.setText("Currently loaded key: " + steg.cryptography.Cryptography.getKeyword());
      // Set the visibility of panes.
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
    //same as encrypt, do not switch panes if no key is loaded
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      statusBar.setText("Load or generate a key before trying to decrypt.");
      return;
    } else {
      //otherwise display the loaded key
      statusBar.setText("Currently loaded key: " + steg.cryptography.Cryptography.getKeyword());
    }
    // Set the visibility of panes.
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
    //using plaintext on this pane, so no key will be in use
    statusBar.setText("Plaintext mode. Encryption key not currently in use.");
    // Set the visibility of panes.
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
    //same as other plaintext pane
    statusBar.setText("Plaintext mode. Encryption key not currently in use.");
    // Set the visibility of panes.
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
    //set the status bar to show no key or the name of the key, if one is loaded
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
    }
    refreshDB(); // load the DB.
  }

  /**
   * Generate a random key and set it as the currently active key.
   */
  public void generateKey() {
    //use the genKey method in Cryptography to generate the key
    steg.cryptography.Cryptography.setKey(steg.cryptography.Cryptography.genKey());
    //immediately save the key to the database so it doesn't get lost
    savetoDB();
  }

  /**
   * Load a key and initialization vector from a text file
   */
  public void loadKey() {
    //open file chooser window, filter for .key files
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open key file"); // set title
    fileChooser
            .getExtensionFilters()
            .addAll( // filter extensions
                    new FileChooser.ExtensionFilter("Key Files", "*.key"));
    //create a file object which stores the selected location to send to KeyFile's loadKey method
    File file = fileChooser.showOpenDialog(new Stage());
    //Uncomment when integrated with KeyFile.java
   KeyFile.loadKey(file);

  }

  /**
   * Save a key and initialization vector to a text file
   */
  public void saveKey() {
    //make sure a key has been loaded before trying to save it
    if (steg.cryptography.Cryptography.getKeyStr() != null) {
      //open a file chooser window, filter for .key files
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save key file"); // set title
      fileChooser
              .getExtensionFilters()
              .addAll( // filter extensions
                      new FileChooser.ExtensionFilter("Key Files", "*.key"));
      //store the result in a file
      File file = fileChooser.showSaveDialog(new Stage());
      //make sure the file is not null
      //NOTE: even though the file chooser is set to filter everything but .key files, if something gets
      //through that is not a .key file, it will not be caught before the program tries to load it.
      if (file != null) {
        try {
          //Uncomment next line when integrated with KeyFile.java
          KeyFile.writeKey(file);
          //set the status bar to show the saved key
          //substring to just the file path without file:/
          statusBar.setText("Key saved to file: " + file.toURI().toURL().toString().substring(6));
        } catch (Exception e) {
          e.printStackTrace();
        }

      }
    } else {
      //if no key is loaded, say so in the status bar
      statusBar.setText("Load or generate a key before trying to save it.");
    }
  }

  /**
   * Open file chooser and load the selected image into memory.
   */
  public void loadImage() {
    // create new filechooser built in javafx dialog.
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
    //make sure an image has been loaded to memory before encrypting in it
    if (imageMemory == null) {
      //no image, update status bar text
      statusBar.setText("Load an image before trying to hide a message in it.");
    } else {
      // similar to loadImage
      //create file chooser, filter for png images
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Save Image");
      fileChooser
              .getExtensionFilters()
              .addAll(
                      new FileChooser.ExtensionFilter("Image Files", "*.png"));
      File file = fileChooser.showSaveDialog(new Stage());
      //make sure the selection is not null
      if (file != null) {
        try {
          // Encode message into image before saving.
          //key encoding and plaintext encoding call the same message
          //so we have to check to see which pane is visible
          //if the encryption pane is visible...
          if (encryptPane.isVisible()) {
            //use Cryptography's encrypt method to encrypt the message text before encoding into image
            imageMemory =
                    model.encoder.encodeImage(
                            imageMemory, steg.cryptography.Cryptography.encrypt(hideMsgEncrypt.getText()));
          }else {
            //otherwise just encode plaintext
            imageMemory = model.encoder.encodeImage(imageMemory, hideMsgPlain.getText());
          }
          image.setImage(imageMemory);
          // buffered image to save.
          BufferedImage bImage = SwingFXUtils.fromFXImage(imageMemory, null);
          // only allowing png types
          //write the image to the file name and location selected in the file chooser
          ImageIO.write(bImage, "png", file);
        } catch (IOException ex) {
          System.out.println(ex.getMessage());
        }
      }
    }
  }

  /**
   * Method to reveal plaintext message in TextArea.
   */
  public void showMsgPlain() {
    //make sure the loaded image is not null
    if (imageMemory == null) {
      //if null, update status bar, do nothing else
      statusBar.setText("Load an image before trying to reveal its message.");
    } else {
      //if not null, decode the plaintext
      String message = model.decoder.decodeImage(imageMemory);
      //show the message in the text area
      showMsgPlain.setText(message);
    }
  }

  /**
   * Method to reveal encrypted message in TextArea.
   */
  public void showMsgEncrypt() throws NoSuchPaddingException, NoSuchAlgorithmException {
    //make sure the loaded image is not null
    if (imageMemory == null) {
      //if null, update status bar, do nothing else
      statusBar.setText("Load an image before trying to reveal its message.");
    } else {
      //if not null, first decode the message from the image
      String encryptedMsg = model.decoder.decodeImage(imageMemory);
      //then use Cryptography's decrypt method to decrypt the encrypted message
      String decryptedMsg = steg.cryptography.Cryptography.decrypt(encryptedMsg);
      //display the final result in the text area
      showMsgEncrypt.setText(decryptedMsg);
    }
  }

  /**
   * Called to preview the image in memory.
   */
  public void previewImage() throws NoSuchPaddingException, NoSuchAlgorithmException {
    //make sure the image loaded to memory is not null
    if (imageMemory == null) {
      //if null, update status bar text, do nothing elsee
      statusBar.setText("Load an image before trying to preview it.");
    } else {
        //if not null, check which pane is visible
        if (encryptPane.isVisible()) {
          //if encryption pane is visible, encrypt the message before encoding into image
          imageMemory =
                  model.encoder.encodeImage(
                          imageMemory, steg.cryptography.Cryptography.encrypt(hideMsgEncrypt.getText()));
        }else {
          //if plaintext pane is visible, just encode plaintext into image
          imageMemory = model.encoder.encodeImage(imageMemory, hideMsgPlain.getText());
        }
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
    //make sure the loaded key is not null
    if (steg.cryptography.Cryptography.getKeyStr() == null) {
      //if null, update status bar text, do nothing else
      statusBar.setText("Generate or load a key before trying to save it to the database.");
    } else {
      //if not null, save the key to the database
      //create a dialog for the user to enter the keyword to assign to the key
      TextInputDialog dialog = new TextInputDialog("Keyword");
      dialog.initOwner(getPrimaryStage());
      dialog.setTitle("Add key to database");
      dialog.setHeaderText("Please enter a keyword for selected key.");
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
    //update the table
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
    //update the table
    refreshDB();
  }
}