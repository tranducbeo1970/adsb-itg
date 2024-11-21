/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author root
 */
public class PlayItem extends PlayItemBase{

    private File file;

    public PlayItem() {
    }
    
    public PlayItem(File file) throws ClassNotFoundException, IOException, IOException {
        this.file = file;
        this.setName(file.getName());
        this.setPath(file.getPath());
        this.load();
    }
    
    protected void load() throws ClassNotFoundException, IOException {
        try {
            //use buffering
            InputStream inputStream = new FileInputStream(this.file);
            InputStream buffer = new BufferedInputStream(inputStream);
            ObjectInput input = new ObjectInputStream(buffer);

            // int counting = 0;
            //deserialize the List
            RecordItem item = null;
            try {
                while ((item = (RecordItem) input.readObject()) != null) {
                    if (this.start == 0) {
                        this.start = item.getTime();
                    }
                }
            } catch (EOFException | StreamCorruptedException ex) {
                this.end = item == null ? this.end : item.getTime();
                // System.out.println("End file: " + this.path);
                if (ex instanceof StreamCorruptedException) {
                    System.out.println("Error: " + ex.getMessage());
                }
            } finally {
                buffer.close();
                input.close();
                System.out.println("File: " + name + " (" + file.length() + "(bytes)) " + this.getStartTime() + " - " + this.getEndTime());
                        
            }
        } catch (ClassNotFoundException | IOException ex) {
            throw ex;
        }
    }
    
    @Override
    public List<RecordItem> loadContent() throws ClassNotFoundException, IOException {
        List<RecordItem> items = new ArrayList<>();
        try {
            //use buffering
            InputStream inputStream = new FileInputStream(this.file);
            InputStream buffer = new BufferedInputStream(inputStream);
            ObjectInput input = new ObjectInputStream(buffer);

            // int counting = 0;
            //deserialize the List
            RecordItem item = null;
            try {
                while ((item = (RecordItem) input.readObject()) != null) {
                   items.add(item);
                }
            } catch (EOFException | StreamCorruptedException ex) {
                items.add(item);
                if (ex instanceof StreamCorruptedException) {
                    System.out.println("Error: " + ex.getMessage());
                }
            } finally {
                buffer.close();
                input.close();
                System.out.println("Read :" + items.size() + " records");
            }
        } catch (ClassNotFoundException | IOException ex) {
            throw ex;
        }
        return items;
    }
    
    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the start
     */
    @Override
    public long getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    @Override
    public void setStart(long start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    @Override
    public long getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    @Override
    public void setEnd(long end) {
        this.end = end;
    }

    /**
     * @return the status
     */
    @Override
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    @Override
    public void setStatus(int status) {
        this.status = status;
    }
    
    @Override
    public String getStartTime() {
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTimeInMillis(this.start);
        return format.format(calendar.getTime());
    }
    
    @Override
    public String getEndTime() {
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTimeInMillis(this.end);
        return format.format(calendar.getTime());
    }
    
    
    @Override
    public String getItemStatus() {
        switch (this.status) {
            case 0:
                return "Stop";
            case 1:
                 return "Playing";
            case 2:
                return "Pause";
            default:
                return "";
        }
    }
    
    
}
