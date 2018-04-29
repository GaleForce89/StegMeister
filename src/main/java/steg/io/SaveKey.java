package steg.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SaveKey
{
    private Connection connect() {
        String url = "jdbc:sqlite:DB/dbKeys.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void GetKey(String[] args)
    {
        steg.database.InsertData needKey = new steg.database.InsertData();
        steg.database.Connect Connect = new steg.database.Connect();

        String sqlKey = "SELECT * FROM keys WHERE key";
        String sqlKeyword = "SELECT * FROM key WHERE keyword";


        try (Connection conn = this.connect())
        {
            BufferedWriter w = new BufferedWriter ( new FileWriter(".\\text.txt") );
            PreparedStatement psKey = conn.prepareStatement(sqlKey);
            PreparedStatement psKeyword = conn.prepareStatement(sqlKeyword);

            int key = psKey.executeUpdate();
            int keyword = psKeyword.executeUpdate();

            w.write(key);
            w.write(","); //when reading in file the "," will help distinguish end of key
            w.write(keyword);

            w.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
