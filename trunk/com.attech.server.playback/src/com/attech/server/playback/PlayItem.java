/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import java.io.File;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 *
 * @author root
 */
public class PlayItem {
    private long start;
    private long end;
    private int speed;
    private String root;
    private List<File> fileList;
    
    private final int DAY_LENGTH = 86400000;
    private final int HOUR_LENGTH = 3600000;
    
    public static long startTime;
    public long beginTime;
    private Consumer consumer;
    private FileLoader producer;
    private DelayQueue delayQueue;
    private BroadCastUDPSocket socket;
    
    private long foundStartTime;
    
    public PlayItem(String root, long start, long end, int speed) throws SocketException, UnknownHostException {
        this.start = start;
        this.end = end;
        this.speed = speed;
        this.root = root;
        this.fileList = buildFileList(this.start, this.end, this.root);
        // this.initialize();
    }
    
    private List<File> buildFileList(long start, long end, String root) {
        List<File> files = new ArrayList<>();
        // int startFile = (int) (start / HOUR_LENGTH);
        // int endFile = (int) (end / HOUR_LENGTH);
        // File directory = new File(this.root);
        boolean isFound = false;
        
        System.out.println("start: " + start + " end " + end + "current " + System.currentTimeMillis());
        for (long i = start; i < end; i += HOUR_LENGTH) {
            
            int folder = (int) (i / DAY_LENGTH);
            int fileName = (int) (i / HOUR_LENGTH);
            System.out.println("folder: " + folder + " + " + fileName);
            File directory = new File(root, Integer.toString(folder));
            if (!directory.exists()) continue;
            
            File file = new File(directory.getPath(), Integer.toString(fileName) + ".rcd");
            if (!file.exists()) continue;
            
            if (!isFound) {
                this.beginTime = i;
                this.foundStartTime = i;
                isFound = true;
            }
            
            files.add(file);
            System.out.println("Add file : " + file.getPath());
        }
        return files;
    }
    
    private void initialize() throws SocketException, UnknownHostException {
        this.socket = new BroadCastUDPSocket("Localhost", 20552);
        this.delayQueue = new DelayQueue();
        this.consumer = new Consumer(this.delayQueue, this.socket);
        this.producer = new FileLoader(this.delayQueue, this.start, this.end, this.fileList);
    }
    
    public void start() throws SocketException, UnknownHostException {
        
        initialize();
        startTime = System.currentTimeMillis();
        this.consumer.start();
        this.producer.start();
    }
    
    public void stop() {
        this.producer.stop();
        this.consumer.stopThread();
    }
    
    public void pause() {
        this.consumer.suspend();
        this.producer.suspend();
    }
    
    public void resume() {
         startTime = System.currentTimeMillis();
         this.consumer.resume();
         this.producer.resume();
    }
    
}
