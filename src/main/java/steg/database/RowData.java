package steg.database;

import java.sql.*;

public class RowData {

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
