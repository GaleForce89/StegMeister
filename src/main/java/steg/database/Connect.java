package steg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

  public static void connect() {
    Connection conn = null;
    try {
      String url = "jdbc:sqlite:DB/dbKeys.db";

      conn = DriverManager.getConnection(url);
      // System.out.println("connection established.");
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
  }
}
