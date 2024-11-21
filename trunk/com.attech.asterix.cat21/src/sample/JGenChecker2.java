/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import com.attech.asterix.cat21.Message;
import com.attech.asterix.cat21.MessageDecryptor;
import exception.InvalidFormatException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andh
 */
public class JGenChecker2 {

    public static void main(String[] args) {

        System.out.print("File path:");

        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String s = bufferRead.readLine();

            byte[] byts = new byte[3];
            int offSet = 0;
            int numRead = 1;

            FileInputStream inputStream = new FileInputStream(s);
            MessageDecryptor deCrypt = new MessageDecryptor();

            long counting = 0;
            long timeCounting = 0;
            
            // BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while (numRead > 0) {
                numRead = inputStream.read(byts, 0, 3);
                if (numRead == 3) {
                    int length = byts[1] & 0xFF;
                    length = length << 8 | (byts[2] & 0xFF);
                    
                    /*
                    System.out.println("byte[1] " + Integer.toBinaryString((int) byts[1]) + "-" + Integer.toHexString((int) byts[1]) + "-" + (byts[1] & 0xFF));
                    System.out.println("byte[2] " + Integer.toBinaryString((int) byts[2]) + "-" + Integer.toHexString((int) byts[2]) + "-" + (byts[2] & 0xFF));
                    System.out.println("byte[2] " + Integer.toBinaryString((int) byts[2]) +"-" + (byts[2] & 0xFF));
                    System.out.println("Length: " + length);
                    */

                    byte[] record = new byte[length];
                    numRead = inputStream.read(record, 3, length-3);
                    record[0] = byts[0];
                    record[1] = byts[1];
                    record[2] = byts[2];

                    long time = System.nanoTime();
                    Message message = deCrypt.decrypt(record);
                    time = System.nanoTime() - time;
                    System.out.println("[" + time + "] " +  message);

                    offSet += numRead;
                    
                    counting++;
                    timeCounting += time;
                }
            }

            inputStream.close();
            
            System.out.println("Record: " + counting +" Process time: " + timeCounting +" Average: " + timeCounting/counting);;

        } catch (InvalidFormatException ex) {
            Logger.getLogger(JGenChecker2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }
}
