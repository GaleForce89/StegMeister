package steg.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import steg.cryptography.Cryptography;


public class SaveKey /**SaveKey Method*/
{
    public void GetKey(String[] args)
    {
        Cryptography crypt = new Cryptography();

        //Set variables
        String key = crypt.getKeyStr();
        String keyword = crypt.getKeyword();

        try
        {
            //bufferedWriter and PreparedStatement
            BufferedWriter w = new BufferedWriter ( new FileWriter(".\\text.txt") );


            //print variables
            w.write(key);
            w.write(","); //when reading in file the "," will help distinguish end of key
            w.write(keyword);

            w.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
