package steg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertData {
    ///connect to the database
    //and return object
    private Connection connect(){
        String url = "jdbc:sqlite:DB/dbKeys.db";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;

    }
    public void insert_Key(String key, String keyword) throws SQLException {
        //prepare statement
        String sql = "INSERT INTO keys(key, keyword) VALUES(?,?)";
        try(Connection conn = this.connect();
            PreparedStatement pstmnt = conn.prepareStatement(sql)) {
            pstmnt.setString(1, key);
            pstmnt.setString(2, keyword);
            //execute statement
            pstmnt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }


    }
}
