/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import com.attech.adsb.record.RecordItem;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

/**
 *
 * @author root
 */
public class PlayListSamba extends PlayListLocal {
    
    // private NtlmPasswordAuthentication auth;
    
    public PlayListSamba(String root, BlockingQueue<RecordItem> blockingQueue) {
        super(root, blockingQueue);
        // auth = new NtlmPasswordAuthentication("", Configuration.getInstance().getUser(), Configuration.getInstance().getPass());
    }
    
    
    @Override
    public void build(long start, long end) {
        
        this.playbackList = new ArrayList<>();
        this.start = start;
        this.end = end;
        // System.out.println("start: " + start + " end " + end + "current " + System.currentTimeMillis());
        
        for (long i = start; i < end; i += HOUR_LENGTH) {
            try {
                int folder = (int) (i / DAY_LENGTH);
                int fileName = (int) (i / HOUR_LENGTH);
                
                SmbFile smbFile = new SmbFile(Configuration.getInstance().getLocation() + "/" + folder, Configuration.getInstance().getAuth());
                if (!smbFile.exists()) continue;
                
                SmbFile file = new SmbFile(Configuration.getInstance().getLocation() + "/" + folder + "/" + Integer.toString(fileName) + ".rcd", Configuration.getInstance().getAuth());
                if (!file.exists()) continue;
                long begin = (i == start ? start : 0);
                
                ItemIndex itemindex = new PlayItemSamba(file.getPath(), begin, end);
                this.playbackList.add(itemindex);
                // System.out.println("Add file : " + file.getPath());
            } catch (    MalformedURLException | SmbException ex) {
                Logger.getLogger(PlayListSamba.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (this.playbackList.isEmpty()) {
            setFoundStart(0);
            setFoundEnd(0);
        } else {
            setFoundStart(this.playbackList.get(0).getStartTime());
            setFoundEnd(this.playbackList.get(this.playbackList.size()-1).getEndTime());
        }
    }
    
}
