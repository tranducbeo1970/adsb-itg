/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import com.attech.adsb.record.RecordItem;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 *
 * @author root
 */
public class PlayItemSamba extends ItemIndex{
    
    public PlayItemSamba (String fileName, long start, long end)  {
        super(fileName, start, end);
    }
    
    @Override
    protected void load(long start, long end) {
        this.items = new ArrayList<>();
        try {
            //use buffering
            SmbFile smbFile = new SmbFile(this.filePath, Configuration.getInstance().getAuth());
            SmbFileInputStream inputStream = new SmbFileInputStream(smbFile);
            InputStream buffer = new BufferedInputStream(inputStream);
            ObjectInput input = new ObjectInputStream(buffer);

            // int counting = 0;
            // deserialize the List
            RecordItem item = null;
            try {
                while ((item = (RecordItem) input.readObject()) != null) {
                    if (start != 0 && item.getTime() < start) continue; 
                    if (end != 0 && item.getTime() > end) continue;
                    
                    if (this.startTime == 0) this.startTime = item.getTime() ;
                    // counting++;
                    items.add(item);
                    // System.out.println(">" + counting + " - " + item);

                    
                    /*
                     if (content == null || !content.isPack(item)) {
                     if (content != null) {
                     this.queue.put(content);
                     System.out.println("Update: ");
                     }
                     content = new BroadCastDelayItem(item, startTime, System.currentTimeMillis());
                     System.out.println("Create: ");
                     }
                     */
                }
            } catch (EOFException | StreamCorruptedException ex) {
                this.endTime = item == null ? this.endTime : item.getTime();
                items.add(item);
                this.count = items.size();
                System.out.println("End file: " + this.filePath);
                if (ex instanceof StreamCorruptedException) {
                    System.out.println("Error: " + ex.getMessage());
                }
            } finally {
                buffer.close();
                input.close();
                inputStream.close();
            }
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
