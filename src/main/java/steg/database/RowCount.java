package steg.database;

import java.sql.*;

public class RowCount {

  public int rowCount() {
    int count = 0; // default to 0

    Connection conn = null; // establish a connection object.
    try {
      String url = "jdbc:sqlite:DB/dbKeys.db";

      conn = DriverManager.getConnection(url); // connect to db
      // sqlcount
      String sqlCount = "SELECT COUNT(*)\n" + "FROM keys;";

      Statement getCount = conn.createStatement(); // create statement object

      // execute statement and get results.
      ResultSet countResults = getCount.executeQuery(sqlCount);

      while (countResults.next()) { // loops through and count.
        count = countResults.getInt(1);
      }

    } catch (SQLException e) { // catch if we fail.
      System.out.println(e.getMessage());
    }
    // return the total row count.
    return count;
  }
}
