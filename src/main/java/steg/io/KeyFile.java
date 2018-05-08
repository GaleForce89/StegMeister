package steg.io;

import java.io.*;

import steg.cryptography.Cryptography;

/**
 * KeyFile Class
 * */
public class KeyFile
{
    /*writeKey Method*/
    public void writeKey()
    {
        Cryptography crypt = new Cryptography();

        //Set variables
        String key = crypt.getKeyStr();
        String vector = crypt.getVector();

        try
        {
            //bufferedWriter and PreparedStatement
            BufferedWriter w = new BufferedWriter ( new FileWriter(".\\text.key") );


            //print variables
            w.write(key);
            w.write("\n"); //when reading in file the "," will help distinguish end of key
            w.write(vector);

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
        //Make and send Variables
        try {
            //Call Cryptography
            Cryptography crypt = new Cryptography();
            //Make BufferedReader and FileReader
            BufferedReader w = new BufferedReader ( new FileReader(".\\text.key") );

            //Make and send Variables
            String key = w.readLine();
            crypt.setKey(key);
            String vector = w.readLine();
            crypt.setVector(vector);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
