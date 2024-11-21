/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.tools;

import com.attech.adsb.record.RecordItem;
import com.attech.asterix.cat21.v21.Decryptor;
import com.attech.asterix.cat21.v21.Message;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author root
 */
public class RecordLoader {
         private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    private String name;
    Decryptor decryptor = new Decryptor();
    
    public RecordLoader(String name) {
        this.name = name;
    }
    
    public void readObject() throws FileNotFoundException, IOException {
        try {
            //use buffering

            InputStream file = new FileInputStream("D:\\tmp\\2020-08-21\\00.000.sector01.rcd");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);
            
            //ObjectInputStream input = new ObjectInputStream(new FileInputStream("C:\\Recorded\\15800\\379202.rcd"));
            int counting = 0;
            try {
                //deserialize the List
                RecordItem item;
                while ((item = (RecordItem) input.readObject()) != null) {
                    counting++;
                    System.out.println(format.format(new Date(item.getTime())) + " : " + item.getBytes().length);
//                    List<Message> msg = (List<Message>) decryptor.decrypt(item.getBytes());
//                    for (Message m : msg) {
//                        System.out.println(m);
//                    }
                }
            } catch(EOFException ex) {
                System.out.println("End of file");
                //display its data
            } finally {
                input.close();
            }
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main (String [] args) throws IOException {
        RecordLoader loader = new RecordLoader("");
        loader.readObject();
    }
}
