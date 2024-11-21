/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import com.attech.adsb.record.RecordItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

public class PlayListLocal implements IPlayList {
    protected BlockingQueue<RecordItem> blockingQueue;
    protected Timer timer;
    protected long step = 300;
    protected long start;
    protected long end;
    protected long foundStart;
    protected long foundEnd;
    protected List<ItemIndex> playbackList;
    protected ItemIndex current;
    protected int index;
    protected int currentIndex;
    protected long currentTime;
    protected String root;
    protected boolean isPause;
    
    protected final int DAY_LENGTH = 86400000;
    protected final int HOUR_LENGTH = 3600000;
    protected List _listeners = new ArrayList();
    
    public PlayListLocal(String root, BlockingQueue<RecordItem> blockingQueue) {
        this.playbackList = new ArrayList<>();
        this.root = root;
        this.blockingQueue = blockingQueue;
        this.timer =new Timer(300, new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              processRecord(e);
          }
      });
        this.timer.setRepeats(true);
    }
    
    @Override
    public void build(long start, long end) {
        this.playbackList = new ArrayList<>();
        this.start = start;
        this.end = end;
        // System.out.println("start: " + start + " end " + end + "current " + System.currentTimeMillis());
        
        for (long i = start; i < end; i += HOUR_LENGTH) {
            int folder = (int) (i / DAY_LENGTH);
            int fileName = (int) (i / HOUR_LENGTH);
            
            // System.out.println("folder: " + folder + " + " + fileName);
            File directory = new File(root, Integer.toString(folder));
            if (!directory.exists()) continue;
            
            File file = new File(directory.getPath(), Integer.toString(fileName) + ".rcd");
            if (!file.exists()) continue;

            long begin =(i== start ? start : 0);
            ItemIndex itemindex = new ItemIndex(file.getPath(), begin, end);
            this.playbackList.add(itemindex);
            // System.out.println("Add file : " + file.getPath());
        }
        
        if (this.playbackList.isEmpty()) {
            setFoundStart(0);
            setFoundEnd(0);
        } else {
            setFoundStart(this.playbackList.get(0).getStartTime());
            setFoundEnd(this.playbackList.get(this.playbackList.size()-1).getEndTime());
        }
        
    }

    @Override
    public long getFoundStart() {
        return foundStart;
    }

    @Override
    public void setFoundStart(long foundStart) {
        this.foundStart = foundStart;
    }

    @Override
    public long getFoundEnd() {
        return foundEnd;
    }

    @Override
    public void setFoundEnd(long foundEnd) {
        this.foundEnd = foundEnd;
    }
    
    @Override
    public void start() {
        if (!isPause) reset();
        this.timer.start();
    }
    
    @Override
    public void stop() {
        isPause = false;
        this.timer.stop();
    }

    @Override
    public void pause() {
        isPause = true;
        this.timer.stop();
    }
    
    @Override
    public synchronized void addUpdateListener(ActionListener l) {
        _listeners.add(l);
    }
    
    @Override
    public int getCurrentValue() {
        return (int) (this.currentTime - this.foundStart);
    }
    
    @Override
    public long getCurrentTime() {
        return this.currentTime;
    }
    
    private void processRecord(ActionEvent e) {
        long nextTime = this.currentTime + step;
        long temp = this.current.getContent().get(currentIndex).getTime();
        while (temp <= nextTime) {
            if (currentIndex < this.current.getCount()) {
                this.process(this.current.getContent().get(currentIndex));
                temp = this.current.getContent().get(currentIndex).getTime();
                this.currentIndex++;
            } else if (index < this.playbackList.size()-1) {
                this.index ++;
                this.current = this.playbackList.get(this.index);
                this.currentIndex =0;
                continue;
            } else {
                this.timer.stop();
                break;
            }
        }
        this.currentTime = nextTime;
        _fireEvent();
    }
    
    private void process(RecordItem item) {
        try {
            this.blockingQueue.put(item);
        } catch (InterruptedException ex) {
            Logger.getLogger(PlayListLocal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void reset() {
        this.current = this.playbackList.get(0);
        this.index = 0;
        this.currentIndex = 0;
        this.currentTime = this.getFoundStart();
        
        long startCurrentTime = currentIndex == 0 ? 0 : this.currentIndex - step;
        for (int i = 0; i < this.playbackList.size(); i++) {
            if (this.playbackList.get(i).getStartTime() > startCurrentTime) continue;
            if (this.playbackList.get(i).getEndTime() < startCurrentTime) continue;
            this.index = i;
            this.current = this.playbackList.get(index);
            for (int j = 0; j<this.current.getCount(); j++) {
                if (this.current.getContent().get(j).getTime() < startCurrentTime) continue;
                this.currentIndex = j;
                break;
            }
            break;
        }
    }
    
    private synchronized void _fireEvent() {
        // MoodEvent mood = new MoodEvent(this, _mood);
        Iterator listeners = _listeners.iterator();
        ActionEvent e = new ActionEvent(this, 0, "");
        while (listeners.hasNext()) {
            ((ActionListener) listeners.next()).actionPerformed(e);
        }
    }
}
