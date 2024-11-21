/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat21.v210.test;

import com.attech.cat21.v210.Cat21Message;
import com.attech.cat21.v210.Cat21Decoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andh
 */
public class DecryptTest {
    
    public void read(String path) {
        final File file = new File(path);
        final long fileLength = file.length();
        try {
            int index = 0;
            FileInputStream inputStream = new FileInputStream(path);
            byte[] byts = new byte[3];
            int numRead = 1;
            
            int counting = 0;
            
            long time1 = System.nanoTime();
            System.out.println(Cat21Message.getHeading());
            
            do {
                
                numRead = inputStream.read(byts, 0, 3);
                if (numRead == 0) break;
                if (numRead != 3) break;
                int msgLength = (byts[1] & 0xFF) << 8 | (byts[2] & 0xFF);

                byte[] packages = new byte[msgLength];
                numRead = inputStream.read(packages, 3, msgLength - 3);
                packages[0] = byts[0];
                packages[1] = byts[1];
                packages[2] = byts[2];

                if (numRead != msgLength - 3) break;
                
                List<Cat21Message> messages = new ArrayList<>();
                Cat21Decoder.decode(packages, messages);
                // counting += messages.size();
                for (Cat21Message m : messages) {
                    m.print();
                    //System.out.println("" + m);
                }
                counting++;
                // if (counting == 100) break;
            } while (numRead > 0);
            
            long time2 = System.nanoTime();
            long time3 = time2 - time1;
            System.out.println("Message count: " + counting);
            System.out.println("Time count: " + time3);
        } catch (IOException ex) {
            // Logger.getLogger(CheckRawData.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
    
    public static void main (String [] args) {
        DecryptTest test = new DecryptTest();
        // test.read("/media/andh/working/Projects/ADSB/2014/attech/old_adsb/16241.dat");
        test.read("E:\\works\\temp\\asterix\\raw_data.raw");
        // test.read("/media/dhan/working/ADS-B Data/16427/394254.172.a.rcd");
        // test.read("D:\\Projects\\ADSB\\svn\\trunk\\EncryptCat21\\output.data");
    }
}
