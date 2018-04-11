package steg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void createNewTable(){
        //SQLite connection
        String url = "jdbc:sqlite:DB/dbKeys.db";

        String sql= "CREATE TABLE IF NOT EXISTS keys (\n" +
                " id integer PRIMARY KEY, \n" +
                " key VARCHAR NOT NULL, \n" +
                " keyword VARCHAR NOT NULL);";
        try(Connection conn = DriverManager.getConnection(url)){
            //create query
            Statement stmnt = conn.createStatement();

            //execute query
            stmnt.execute(sql);

            //output if table created
            System.out.println("Table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}