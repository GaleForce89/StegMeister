package steg.io;

import java.io.*;

import steg.cryptography.Cryptography;

/**
 * KeyFile Class
 * */
public class KeyFile
{
    /*writeKey Method*/
    public static void writeKey(File file)
    {
        Cryptography crypt = new Cryptography();

        //Set variables
        String key = crypt.getKeyStr();
        String vector = crypt.getVector();

        try
        {
            //bufferedWriter and PreparedStatement
            BufferedWriter w = new BufferedWriter ( new FileWriter(file) );


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
    public static void loadKey(File file)
    {
        //Make and send Variables
        try {
            //Call Cryptography
            Cryptography crypt = new Cryptography();
            //Make BufferedReader and FileReader
            BufferedReader w = new BufferedReader ( new FileReader(file) );

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
