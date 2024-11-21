/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.playback;

import com.attech.adsb.record.RecordItem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

/**
 *
 * @author ANDH
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class Player implements Runnable{

    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
    private boolean running;
    private long start;
    private long end;
    private long current;
    private int index;
    private File file;
    private List<Frame> buffers = new ArrayList<>();
//    private List<Frame> bufferFrame;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
    private ScheduledFuture<?> futureTask;
    private Runnable myTask;
    private long time;
    private int speed = 1000;
    private IPlayerNotify notify;
    
    public Player(File file) {
        this.file = file;
        this.running  = false;
    }
    
    public void load() throws FileNotFoundException, IOException, ClassNotFoundException {
        buffers.clear();
        //use buffering
        InputStream record = new FileInputStream(this.file);
        InputStream buffer = new BufferedInputStream(record);
        ObjectInput input = new ObjectInputStream(buffer);
        //ObjectInputStream input = new ObjectInputStream(new FileInputStream("C:\\Recorded\\15800\\379202.rcd"));
        long currentTime = 0;
        Frame currentFrame = null ;
        try {
            //deserialize the List
            RecordItem item;
            while ((item = (RecordItem) input.readObject()) != null) {
                long timeInSecond = item.getTime() / 1000;
                if (currentTime != timeInSecond) {
                    currentTime = timeInSecond;
                    currentFrame = new Frame(currentTime);
                    currentFrame.add(item.getBytes());
                    buffers.add(currentFrame);
                } else {
                    currentFrame.add(item.getBytes());
                }
            }
        } catch(java.io.EOFException ex) {
            System.out.println("End of file");
        } finally {
            input.close();
        }
        
        if (buffers.isEmpty()) {
            return;
        }
        setStart(buffers.get(0).getSecond());
        setEnd(buffers.get(buffers.size()-1).getSecond());
        current = this.getStart();
        
//        for (Frame frame  : buffers) {
//             System.out.println(format.format(new Date(frame.getSecond() * 1000)) + " : " + frame.getSize());
//        }
        
    }
    
    public void play(long time) {
        if (this.running) {
            return;
        }
//        current = this.getStart();
//        index = 0;
        int newSpeed = (int) (speed / time);
        notify(PlayerState.START, 0, null);
        scheduledExecutorService = Executors.newScheduledThreadPool(5);
        futureTask = scheduledExecutorService.scheduleAtFixedRate(this, 0, newSpeed, TimeUnit.MILLISECONDS);
    }
    
    public void pause() {
        haltScheduleService();
        notify(PlayerState.PAUSE, index, null);
    }
    
    public void stop() {
        haltScheduleService();
        index = 0;
        current = this.getStart();
        notify(PlayerState.STOPPED, index, null);
    }
    
    public void changeSpeed(long time) {
        if (time > 0) {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            int newSpeed = (int) (speed / time);
            futureTask = scheduledExecutorService.scheduleAtFixedRate(this, 0, newSpeed, TimeUnit.MILLISECONDS);
        }
    }
    
    private void haltScheduleService() {
        try {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            scheduledExecutorService.shutdownNow();
        } finally {
            this.running = false;
        }
    }
    
    private void moveNext() {
        if (this.current <= this.getEnd()) {
            this.current++;
        } else {
            if (futureTask != null) {
                futureTask.cancel(true);
            }
            scheduledExecutorService.shutdownNow();
             notify(PlayerState.END, index, null);
        }
    }
    
    private void notify(PlayerState state, long progress, List<byte[]> data) {
        if (notify == null) {
            return;
        }
        
        this.notify.playerStateChangedActionPerformed(state, progress, data);
    }

    @Override
    public void run() {
       
        
        while (index < this.buffers.size()) {
            Frame frame = this.buffers.get(index);
             System.out.println("> Second: " + this.current + " Frame: " + frame.getSecond() + " index: " + this.index);
            if (frame.getSecond() > this.current) {
                break;
            }

           
            notify(PlayerState.PLAYING, this.current, frame.getItems());
            index++;
        }
        moveNext();
    }
    
    public boolean isPlaying() {
         return scheduledExecutorService != null && !scheduledExecutorService.isTerminated();
    }
    
    /**
     * @param notify the notify to set
     */
    public void setNotify(IPlayerNotify notify) {
        this.notify = notify;
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
    
//    public String getStart() {
//        return format.format(new Date(this.start*1000));
//    }
//    
//    public String getEnd() {
//         return format.format(new Date(this.end*1000));
//    }

}
