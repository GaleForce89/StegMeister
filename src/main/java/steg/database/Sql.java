package steg.database;

import java.io.File;
import java.sql.*;

public class Sql {
  /**
   * The sql connection object.
   */
  private Connection sql = null;//default connection to null. *NOTE* only 1 connection for entire database.


  /**
   * Default sql constructor.
   */
  public Sql() {
    try {

      //Check if the database exist, if not create the directory and tables. Only happens on first run.
      if (!new File("DB/dbKeys.db").exists()) {
        //create directory
        new File("DB").mkdir();


        sql = DriverManager.getConnection("jdbc:sqlite:DB/dbKeys.db"); //connect to database.

        //Create table.
        createNewTable();

      }else {//connect directly to database.
        this.sql = DriverManager.getConnection("jdbc:sqlite:DB/dbKeys.db"); //connect to database.
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Create the database table.
   */

  private void createNewTable() {
    //Create the sequel command.
    String sqlCommand =
            "CREATE TABLE IF NOT EXISTS keys (\n"
                    //+ " id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                    + " key TEXT NOT NULL, \n"
                    + " keyword TEXT PRIMARY KEY NOT NULL, \n" +
                    " iv TEXT NOT NULL);";
    try {
      // create query
      Statement stmnt = sql.createStatement();

      // execute query
      stmnt.execute(sqlCommand);

      //insert default keys
      //InsertData insertDefault = new InsertData();

      insert_Key("pwmBLWgstaNrHNE+vzI93w==", "Test Key 1", "J/y4+dzVT01uFFz7MAcd0A==");
      insert_Key("xCslWtmKhMIZnjz3dNfP0w==", "Test Key 2", "NJKdkWtg2fFAPZujbE3KEQ==");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Insert values into the database.
   * @param key The users key.
   * @param keyword User keyword.
   * @param iv Initialization vector.
   * @throws SQLException
   */
  public void insert_Key(String key, String keyword, String iv) throws SQLException {
    // prepare statement
    String sqlCommand = "INSERT INTO keys(key, keyword, iv) VALUES(?,?,?)";

    try {//create the sql statement.
      PreparedStatement pstmnt = sql.prepareStatement(sqlCommand);
      pstmnt.setString(1, key);
      pstmnt.setString(2, keyword);
      pstmnt.setString(3, iv);
      // execute statement
      pstmnt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  /**

   * Returns a ResultSet with every key and keyword from the database
   */
  public ResultSet getUpdateInfo() {
    String query = "SELECT key, keyword FROM keys";
    ResultSet res = null;
    try {
      Statement st = sql.createStatement();
      res = st.executeQuery(query);
    }catch(SQLException e) {
      e.printStackTrace();
    }
    return res;

  }

  /**
   * Returns the init vector as a String using its corresponding keyword
   */
  public String getInitVector(String keyword) {

    String iv = null;

    try {
      ResultSet result = sql.createStatement().executeQuery("SELECT iv FROM keys WHERE keyword = \'" + keyword + "\';");
      iv = result.getString(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return iv;
  }

  /**
   * Deletes a row from the database using a corresponding keyword
   */
  public void deleteRow(String current) {
    try {
      sql.createStatement().executeUpdate("DELETE FROM keys WHERE keyword = \'" + current + "\';");
    }catch(SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks to see if an entry exists in the database with a given keyword (false if that keyword is not in the database)
   */
  public boolean inDb(String keyword) {
      ResultSet result = null;
      boolean indb = true;
      try {
          result = sql.createStatement().executeQuery("SELECT keyword FROM keys WHERE keyword = \'" + keyword + "\';");
          if(!result.isBeforeFirst())
              indb = false;
      }catch (SQLException e) {
          e.printStackTrace();
      }
      return indb;
  }
}
