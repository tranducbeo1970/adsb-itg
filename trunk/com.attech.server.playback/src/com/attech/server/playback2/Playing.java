/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import com.attech.adsb.record.RecordItem;
import com.attech.server.playback.BroadCastUDPSocket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author root
 */
public class Playing {
    private PlayItemBase item;
    private int speed;
    private int step;
    private List<RecordItem> contents;
    private int currentIndex;
    private long current;
    private Timer timer;
    private BroadCastUDPSocket socket;
    private MessageBuilder builder;
    private List<IPlayingEventListener> eventHandler;
    private boolean isPause;
    private boolean isEnd;
    
    public Playing(PlayItemBase item, int speed, final int step, String ip, int port) throws ClassNotFoundException, IOException {
        this.item = item;
        this.contents = this.item.loadContent();
        this.socket = new BroadCastUDPSocket(ip, port);
        this.builder = new MessageBuilder();
        this.currentIndex = 0;
        this.current = this.item.getStart();
        this.eventHandler = new ArrayList<>();
        this.speed = speed;
        this.step = step;
        initTimer();
    }
    
    private void initTimer() {
        int time = (int) (step/getSpeed());
        timer = new Timer(time, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current += step;
                builder.clear();
                for (int i = currentIndex; i < contents.size(); i++) {
                    RecordItem record = contents.get(i);
                    if (record.getTime() > current) break;
                    builder.append(record.getBytes());
                    currentIndex++;
                }
                
                byte [] bytes = builder.toByteArray();
                
                // System.out.println("send: " + bytes.length);
                
                if (bytes.length > 3) socket.sent(bytes);
                for (IPlayingEventListener p : eventHandler) 
                    p.progress((int)(current - item.getStart()), current);
                
                // System.out.println("Curr" + currentIndex + " / " + contents.size());
                if (currentIndex >= contents.size() - 1) { 
                    isEnd = true;
                    stop();
                }
            }
        });
        timer.setRepeats(true);
    }
    
    public void play() {
        this.isEnd = false;
        this.item.setStatus(1);
        this.currentIndex = 0;
        this.current = this.item.getStart();
        this.timer.start();
        this.isPause = false;
        for (IPlayingEventListener p : eventHandler) {
            p.start();
        }
    }
    
    public void resume() {
        this.item.setStatus(1);
        this.timer.start();
        this.isPause = false;
        
        for (IPlayingEventListener p : eventHandler) {
            p.resume();
        }
    }
    
    public void pause() {
        this.item.setStatus(2);
        this.timer.stop();
        this.isPause = true;
        for (IPlayingEventListener p : eventHandler) {
            p.pause();
        }
    }
    
    public void stop() {
        this.timer.stop();
        this.item.setStatus(0);
        // this.currentIndex = 0;
        // this.current = this.item.getStart();
        this.isPause = false;
        for (IPlayingEventListener p : eventHandler) {
            p.stop();
        }
    }
    
    public void seek(long time) {
        this.pause();
        time += this.item.getStart();
        for (int i = 0; i < this.contents.size(); i++) {
            RecordItem record = contents.get(i);
            if (record.getTime() < time) continue;
            this.currentIndex = i;
            this.current = record.getTime();
            break;
        }
        
        resume();
    }
    
    public void add(IPlayingEventListener playing) {
        this.eventHandler.add(playing);
    }
    
    public boolean isPause() {
        return this.isPause;
    }
    
    public boolean isEnd() {
        return this.isEnd;
    }

    
    public int getSpeed() {
        return speed;
    }

    
    public void setSpeed(int speed) {
        this.speed = speed;
        int time = (int) (step / speed);
        timer.setDelay(time);
    }
    
    
}
