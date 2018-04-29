package steg.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import steg.cryptography.Cryptography;

/**SaveKey Method*/
public class SaveKey
{
    public void GetKey()
    {
        Cryptography crypt = new Cryptography();

        //Set variables
        String key = crypt.getKeyStr();
        String keyword = crypt.getVector();

        try
        {
            //bufferedWriter and PreparedStatement
            BufferedWriter w = new BufferedWriter ( new FileWriter(".\\text.key") );


            //print variables
            w.write(key);
            w.write("\n"); //when reading in file the "," will help distinguish end of key
            w.write(keyword);

            w.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
