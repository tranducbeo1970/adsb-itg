/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.playbackserver;

import com.attech.adsb.playbackserver.config.ReadMode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import org.apache.log4j.Logger;

/**
 *
 * @author an
 */
public class PlaybackTask extends SwingWorker<Integer, Integer> {

    private static final Logger logger = Logger.getLogger(PlaybackTask.class);
    private final List<IStateChangeListener> stateListeners;
    private int interval;
    private List<PlaybackFile> files;
    private List<Broadcaster> broadcaster;
    private int current;
    private int amount;
    private int count;
    private ReadMode readmode;

    public PlaybackTask() {
        this.stateListeners = new ArrayList<>();
    }

    public PlaybackTask(List<PlaybackFile> files, List<Broadcaster> broadcaster, int startIndex, int interval, ReadMode mode) {
        this.files = files;
        this.broadcaster = broadcaster;
        this.interval = interval;
        this.stateListeners = new ArrayList<>();
        this.current = startIndex;
        this.readmode = mode;
    }
    
    private PlaybackFile getCurrent(){
        if (current < 0 || current >= this.files.size()) {
            return null;
        }
        
        return this.files.get(current);
    }
    
    
    private void moveNext() {
        current ++;
        if (current < 0 || current >= this.files.size()) {
            current = 0;
        }
    }
    
    private void playback(PlaybackFile file) throws IOException, ClassNotFoundException {
        stateChanged(current, "Loading");
        logger.info("Loading file "  + file.getName());
        file.setReadMode(this.readmode);

        this.amount = file.getMessageCount();
        
        stateChanged(current, "Playing");
        logger.info("Start playingback file "  + file.getName());
        
        file.open();
        count = 0;
        while (!this.isCancelled()) {
            byte [] bytes = file.read();
            
            if (bytes == null) {
                break;
            }
            count++;
            for (Broadcaster broadCaster : this.broadcaster) {
                broadCaster.send(bytes);
            }
            
            stateChanged(current, "Playing " + count + "/" +amount);
            waiting(this.interval);
        }
        stateChanged(current, "");
    }

    @Override
    protected Integer doInBackground() throws Exception {
        logger.info("background start");
        this.setProgress(0);
        waiting(100);
        
        
        if (files == null || files.isEmpty()) {
            publish(new Integer[]{100});
            this.setProgress(100);
            waiting(1000);
            return 0;
        }

        try {            
            while (!this.isCancelled()) {
                PlaybackFile file = this.getCurrent();
                try {
                    playback(file);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }                
                moveNext();
            }
            
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            waiting(1000);
            return 0;
        }
    }

    @Override
    protected void done() {
        if (isCancelled()) {
            // System.out.println("Cancel");
        } else {
            this.setProgress(100);
        }
    }

    private void waiting(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
    }

    public synchronized void addStateChangeLister(IStateChangeListener listener) {
        this.stateListeners.add(listener);
    }

    private synchronized void stateChanged(int index, String state) {
        if (this.stateListeners == null || this.stateListeners.isEmpty()) {
            return;
        }
        for (IStateChangeListener listener : this.stateListeners) {
            listener.StateChange(index, state);
        }
    }
}
