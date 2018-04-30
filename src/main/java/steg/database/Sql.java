package steg.database;

import java.io.File;
import java.sql.*;

public class Sql {
  /**
   * The sql connection object.
   */
  Connection sql = null;//default connection to null. *NOTE* only 1 connection for entire database.

  /**
   * Default sql constructor.
   */
  public Sql() {
    try {
      //Check if the database exist, if not create the directory and tables. Only happens on first run.
      if (!new File("DB/dbKeys.db").exists()) {
        //create directory
        new File("DB").mkdir();

        this.sql = DriverManager.getConnection("jdbc:sqlite:DB/dbKeys.db"); //connect to database.

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
  public void createNewTable() {
    //Create the sequel command.
       String sqlCommand =
            "CREATE TABLE IF NOT EXISTS keys (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                    + " key TEXT NOT NULL, \n"
                    + " keyword TEXT NOT NULL, \n" +
                    " iv TEXT NOT NULL);";
    try {
      // create query
      Statement stmnt = sql.createStatement();

      // execute query
      stmnt.execute(sqlCommand);

      //insert default keys
      //InsertData insertDefault = new InsertData();
      insert_Key("thisistestkey", "DONT DO IT!", "IVKDF");
      insert_Key("IWOULDNOTUSETHISKEY#@$@#$#@$", "keywords are useful", "ASDFDFAS");

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
   * Get the current database row count.
   * @return number of rows.
   */
  public int rowCount() {
    int count = 0;//default to 0

    try {

      //sqlcount
      String sqlCount =
              "SELECT COUNT(*)\n" +
                      "FROM keys;";

      Statement getCount = sql.createStatement();//create statement object

      //execute statement and get results.
      ResultSet countResults = getCount.executeQuery(sqlCount);

      while (countResults.next()){ //loops through and count.
        count = countResults.getInt(1);
      }

    } catch (SQLException e) { //catch if we fail.
      System.out.println(e.getMessage());
    }
    //return the total row count.
    return count;
  }

  /**
   * Return the key from the current row.
   * @param current The current row selected.
   * @return key from selected row.
   */
  public String getKey(int current){
    String key = null; //default null

    try {
      //sqlcount
      String currentRow =
              "SELECT key FROM keys WHERE rowid =" + current + ";";

      Statement getRow = sql.createStatement();//create statement object

      //execute statement and get results.
      ResultSet rowResults = getRow.executeQuery(currentRow);

      key = rowResults.getString(1);

    } catch (SQLException e) { //catch if we fail.
      System.out.println(e.getMessage());
    }

    return key; //return the key.
  }

  /**
   * Get the keyword from currently selected row.
   * @param current Current row.
   * @return keyword.
   */
  public String getKeyWord(int current){
    String keyWord = null; //default null

    try {
      //sqlcount
      String currentRow =
              "SELECT keyword FROM keys WHERE rowid =" + current + ";";

      Statement getRow = sql.createStatement();//create statement object

      //execute statement and get results.
      ResultSet rowResults = getRow.executeQuery(currentRow);

      keyWord = rowResults.getString(1);

    } catch (SQLException e) { //catch if we fail.
      System.out.println(e.getMessage());
    }

    return keyWord;
  }
}
