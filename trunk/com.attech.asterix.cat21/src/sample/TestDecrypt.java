/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import com.attech.asterix.cat21.v21.Decryptor;
import com.attech.asterix.cat21.v21.Message;
import exception.InvalidFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 */
public class TestDecrypt {
    public static void main(String [] args) throws InvalidFormatException, Exception {
        
        String[] files = new String[]{
            "D:\\Projects\\ADSB\\data\\adsb\\20552[4483].raw",
            "D:\\Projects\\ADSB\\data\\adsb\\20551[2041].raw",
            "D:\\Projects\\ADSB\\data\\adsb\\20551.67.raw"
        };
        TestDecrypt test = new TestDecrypt();
        for (String f : files) test.test(f);
    }
    
    private void test(String file) throws InvalidFormatException, Exception {
        List<Message> messages = new ArrayList<>();
        Decryptor decryptor = new Decryptor();
        // String file = "D:\\Projects\\ADSB\\data\\adsb\\20552[4483].raw";
        if (file == null || file.isEmpty()) return;
        
        // try {
            FileInputStream inputStream = new FileInputStream(file);
            
            byte[] byts = new byte[3];
            int numRead = 1; 
            int count = 0;
            int length = 0;
            
            do {
                numRead = inputStream.read(byts, 0, 3);
                if (numRead == 0)  break; 
                if (numRead != 3) break;
                int msgLength = (byts[1] & 0xFF) << 8 | (byts[2] & 0xFF);

                byte[] packages = new byte[msgLength];
                numRead = inputStream.read(packages, 3, msgLength - 3);
                packages[0] = byts[0];
                packages[1] = byts[1];
                packages[2] = byts[2];
                
                if (numRead != msgLength - 3) break;
                
                // List<InternalMessage> msg = (List<InternalMessage>) decryptor.extracInternalMesasge(packages, 2.1f);
                List<Message> msg = (List<Message>) decryptor.decrypt(packages);
                
                if (msg == null ) continue;
                for (Message internalMsg : msg) { messages.add(internalMsg); }

                count++;
                length += msgLength;
            } while (numRead > 0);
            System.out.println("Read: " + count + " (packages) " + length + " (bytes) " + messages.size() + " (messages)");
        
    }
}
