/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import com.attech.server.playback.Configuration;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFilenameFilter;

/**
 *
 * @author root
 */
public class PlayListSamba2 extends PlaylistLocal2{
    
    public PlayListSamba2(String root) {
        super(root);
    }
    
    @Override
    public void build(long start, long end) {
        this.items.clear();
        int startHour = (int) (start / HOUR_LENGTH);
        int endHour = (int) (end / HOUR_LENGTH);
        
        for (int i = startHour; i < endHour; i += 1) {
            final int folder = (int) (i / 24);
            final int fileName = i;
            
            // System.out.println("folder: " + folder + " + " + fileName);
            try {
                SmbFile directory = new SmbFile(root + "/" + Integer.toString(folder) + "/", Configuration.getInstance().getAuth());
                if (!directory.exists()) continue;

                SmbFile[] files = directory.listFiles(new SmbFilenameFilter() {
                    @Override
                    public boolean accept(SmbFile sf, String string) throws SmbException {
                        return string.startsWith(fileName + ".") && string.endsWith(".rcd");
                    }
                });

                if (files == null || files.length == 0) continue;
                
                for (SmbFile f : files) {
                    try {
                        PlayItemSamba item = new PlayItemSamba(f);
                        this.items.add(item);
                    } catch (ClassNotFoundException | IOException ex) {
                        Logger.getLogger(PlaylistLocal2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (MalformedURLException | SmbException ex) {
                Logger.getLogger(PlayListSamba2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (IPlayListEventListener l : this.listener) {
            l.buildCompleted(this.items);
        }
    }
    
}
