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
import java.util.List;

/**
 *
 * @author root
 */
public class ItemIndex {
    protected long startTime;
    protected long endTime;
    protected String filePath;
    protected List<RecordItem> items;
    protected int count;
    
    public ItemIndex(String fileName, long start, long end) {
        this.filePath = fileName;
        load(start, end);
    }
    
    protected void load(long start, long end) {
        this.items = new ArrayList<>();
        try {
            //use buffering
            InputStream inputStream = new FileInputStream(this.filePath);
            InputStream buffer = new BufferedInputStream(inputStream);
            ObjectInput input = new ObjectInputStream(buffer);

            // int counting = 0;
            //deserialize the List
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
            }
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<RecordItem> getContent() {
        return items;
    }

    public void setContent(List<RecordItem> content) {
        this.items = content;
    }

    public int getCount() {
        return count;
    }
    
}
