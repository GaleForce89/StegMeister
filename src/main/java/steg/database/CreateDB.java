package steg.database;

import java.sql.*;
import java.util.*;

public class CreateDB {

  public static void createNewDB(String fName) {

    // connect to DB
    String url = "jdbc:sqlite:DB/" + fName;

    try (Connection conn = DriverManager.getConnection(url)) {
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("the driver name is " + meta.getDriverName());
        System.out.println("A new db has been created. ");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
