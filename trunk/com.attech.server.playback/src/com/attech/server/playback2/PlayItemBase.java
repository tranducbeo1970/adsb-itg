/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import com.attech.adsb.record.RecordItem;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author root
 */
public class PlayItemBase {
    protected String name;
    protected String path;
    protected long start;
    protected long end;
    protected int status;
    protected final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    
    public List<RecordItem> loadContent() throws ClassNotFoundException, IOException {
        return null;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the start
     */
    public long getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(long start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public long getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(long end) {
        this.end = end;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getStartTime() {
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTimeInMillis(this.start);
        return format.format(calendar.getTime());
    }
    
    public String getEndTime() {
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTimeInMillis(this.end);
        return format.format(calendar.getTime());
    }
    
    
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
