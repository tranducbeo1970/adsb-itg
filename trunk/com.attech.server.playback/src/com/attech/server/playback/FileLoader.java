/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import com.attech.adsb.record.RecordItem;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 *
 * @author root
 */
public class FileLoader extends Thread {

    private DelayQueue queue;
    private final long startTime;
    private final long endTime;
    private List<File> files;
    private boolean isEnd;

    public FileLoader(DelayQueue queue, long startTime, long endTime, List<File> files) {
        this.queue = queue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.files = files;
        this.isEnd = false;
    }

    @Override
    public void run() {

        long beginTime = 0;
        for (File file : files) {
            try {

                //use buffering
                InputStream inputStream = new FileInputStream(file);
                InputStream buffer = new BufferedInputStream(inputStream);
                ObjectInput input = new ObjectInputStream(buffer);

                int counting = 0;
                //deserialize the List
                RecordItem item;
                BroadCastDelayItem content = null;
                long start = System.currentTimeMillis();
                try {
                    while ((item = (RecordItem) input.readObject()) != null) {
                        
                        if (item.getTime() < this.startTime || item.getTime() > this.endTime) continue;
                        if (beginTime == 0) {
                            beginTime = item.getTime();
                        }
                        counting++;
                        System.out.println(">" + counting + " - " + item);

                        content = new BroadCastDelayItem(item, beginTime, PlayItem.startTime);
                        this.queue.put(content);

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
                } catch (EOFException ex) {
                    this.queue.put(content);
                    System.out.println("End file: " + file.getName());
                } catch (StreamCorruptedException ex) {
                    System.out.println("End file: " + file.getName());
                } finally {
                    buffer.close();
                    input.close();
                }
            } catch (ClassNotFoundException | IOException ex) {
                ex.printStackTrace();
            }
        }
        
        this.isEnd = true;
    }

    public boolean isEnd() {
        return isEnd;
    }
    
    public void stopThread() {
        this.stop();
        this.queue.clear();
    }
}
