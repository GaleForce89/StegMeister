package steg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
  public static void createNewTable() {
    // SQLite connection
    String url = "jdbc:sqlite:DB/dbKeys.db";

    String sql =
        "CREATE TABLE IF NOT EXISTS keys (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
            + " key TEXT NOT NULL, \n"
            + " keyword TEXT NOT NULL);";
    try (Connection conn = DriverManager.getConnection(url)) {
      // create query
      Statement stmnt = conn.createStatement();

      // execute query
      stmnt.execute(sql);

      //insert default keys
      InsertData insertDefault = new InsertData();
      insertDefault.insert_Key("thisistestkey", "DONT DO IT!");
      insertDefault.insert_Key("IWOULDNOTUSETHISKEY#@$@#$#@$", "keywords are useful");


      // output if table created
      //System.out.println("Table created");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
