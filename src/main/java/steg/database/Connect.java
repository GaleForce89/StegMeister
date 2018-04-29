package steg.database;

import java.io.File;
import java.sql.*;

public class Connect {

  public static Connection connect() {
    Connection conn = null;
    try {
      String url = "jdbc:sqlite:DB/dbKeys.db";

      conn = DriverManager.getConnection(url);
      //System.out.println("connection established.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
      return conn;
  }

  public static void createNewDB(String fName) {
    //Check if directory exists, if not create directory.
    if(!new File("DB").exists()){
      //create directory
      new File("DB").mkdir();
    }
    // connect to DB
    String url = "jdbc:sqlite:DB/" + fName;

    try (Connection conn = DriverManager.getConnection(url)) {
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        // System.out.println("the driver name is " + meta.getDriverName());
        // System.out.println("A new db has been created. ");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public static void createNewTable() {
    // SQLite connection
    String url = "jdbc:sqlite:DB/dbKeys.db";

    String sql =
            "CREATE TABLE IF NOT EXISTS keys (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                    + " key TEXT NOT NULL, \n"
                    + " keyword TEXT NOT NULL, \n" +
                    " iv TEXT NOT NULL);";
    try (Connection conn = DriverManager.getConnection(url)) {
      // create query
      Statement stmnt = conn.createStatement();

      // execute query
      stmnt.execute(sql);

      //insert default keys
      //InsertData insertDefault = new InsertData();
      //insertDefault.insert_Key("thisistestkey", "DONT DO IT!", "IVKDF");
      //insertDefault.insert_Key("IWOULDNOTUSETHISKEY#@$@#$#@$", "keywords are useful", "ASDFDFAS");


      // output if table created
      //System.out.println("Table created");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
  public void insert_Key(String key, String keyword, String iv) throws SQLException {
    // prepare statement
    String sql = "INSERT INTO keys(key, keyword, iv) VALUES(?,?,?)";
    try (Connection conn = this.connect();
         PreparedStatement pstmnt = conn.prepareStatement(sql)) {
      pstmnt.setString(1, key);
      pstmnt.setString(2, keyword);
      pstmnt.setString(3, iv);
      // execute statement
      pstmnt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public int rowCount() {
    int count = 0;//default to 0

    Connection conn = null; //establish a connection object.
    try {
      String url = "jdbc:sqlite:DB/dbKeys.db";

      conn = DriverManager.getConnection(url);//connect to db
      //sqlcount
      String sqlCount =
              "SELECT COUNT(*)\n" +
                      "FROM keys;";

      Statement getCount = conn.createStatement();//create statement object

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

    Connection conn = null; //establish a connection object.
    try {
      String url = "jdbc:sqlite:DB/dbKeys.db";

      conn = DriverManager.getConnection(url);//connect to db
      //sqlcount
      String currentRow =
              "SELECT key FROM keys WHERE rowid =" + current + ";";

      Statement getRow = conn.createStatement();//create statement object

      //execute statement and get results.
      ResultSet rowResults = getRow.executeQuery(currentRow);

      key = rowResults.getString(1);

    } catch (SQLException e) { //catch if we fail.
      System.out.println(e.getMessage());
    }



    return key;
  }

  /**
   * Get the keyword from currently selected row.
   * @param current Current row.
   * @return keyword.
   */
  public String getKeyWord(int current){
    String keyWord = null; //default null

    Connection conn = null; //establish a connection object.
    try {
      String url = "jdbc:sqlite:DB/dbKeys.db";

      conn = DriverManager.getConnection(url);//connect to db
      //sqlcount
      String currentRow =
              "SELECT keyword FROM keys WHERE rowid =" + current + ";";

      Statement getRow = conn.createStatement();//create statement object

      //execute statement and get results.
      ResultSet rowResults = getRow.executeQuery(currentRow);

      keyWord = rowResults.getString(1);

    } catch (SQLException e) { //catch if we fail.
      System.out.println(e.getMessage());
    }

    return keyWord;
  }
}
