package steg.io;

import java.io.*;

import steg.cryptography.Cryptography;

/**KeyFile Class*/
public class KeyFile
{
    /*rightKey Method*/
    public void rightKey()
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

    /*loadKey Method*/
    public void loadKey()
    {
        //Call Cryptography
        Cryptography crypt = new Cryptography();
        //Make BufferedReader and FileReader
        BufferedReader w = new BufferedReader ( new FileReader(".\\text.key") );

        //Make and send Variables
        String key = w.readLine();
        crypt.setKey(key);
        String keyword = w.readLine();
        crypt.setKeyword(keyword);

    }
}
