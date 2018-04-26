package steg.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB {

  public static void createNewDB(String fName) {
    // Check if directory exists, if not create directory.
    if (!new File("DB").exists()) {
      // create directory
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
}
