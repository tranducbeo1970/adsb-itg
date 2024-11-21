/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import com.attech.server.playback.Configuration;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class PlaylistLocal2 implements IPlayList2{
    protected final int DAY_LENGTH = 86400000;
    protected final int HOUR_LENGTH = 3600000;
    
    protected String root;
    protected List<PlayItemBase> items;
    protected List<IPlayListEventListener> listener;
    
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    
    private int speed = 1;
    private final int step = 200;
    // private Timer timer;
    // private List<RecordItem> contents;
    private Playing playing;
    private int index;
    
    public PlaylistLocal2(String root) {
        this.root =  root;
        this.items = new ArrayList<>();
        this.listener = new ArrayList<>();
    }
    
    @Override
    public void selectItem(int index) throws ClassNotFoundException, IOException {
        this.index = index;
        if (this.playing != null) {
            this.playing.stop();
        }
        initializePlay(index);
    }
    
    private void initializePlay(final int listIndex) throws ClassNotFoundException, IOException{
        playing = new Playing(this.items.get(listIndex), this.speed, this.step, Configuration.getInstance().getAddress(), Configuration.getInstance().getPort());
        playing.add(new IPlayingEventListener() {

            @Override
            public void stop() {
                for (IPlayListEventListener l : listener) {
                    l.stop(listIndex, playing.isEnd());
                }
                // if (!playing.isEnd()) return;
            }

            @Override
            public void start() {
                for (IPlayListEventListener l : listener) {
                    l.play(listIndex, (int) (items.get(listIndex).getEnd() - items.get(listIndex).getStart()));
                }
            }

            @Override
            public void pause() {
                for (IPlayListEventListener l : listener) {
                    l.pause(listIndex);
                }
            }

            @Override
            public void progress(int progress, long time) {
                for (IPlayListEventListener l : listener) {
                    l.progress(progress, time);
                }
            }

            @Override
            public void resume() {
                for (IPlayListEventListener l : listener) {
                    l.play(listIndex, (int) (items.get(listIndex).getEnd() - items.get(listIndex).getStart()));
                }
            }

            @Override
            public void end() {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    @Override
    public void build(long start, long end) {
        this.items.clear();
        int startHour = (int) (start / HOUR_LENGTH);
        int endHour = (int) (end / HOUR_LENGTH);
        
        Date date  = new Date(start);
        String fld = format.format(date);
                System.out.println("Folder: " + fld);
        File directory = new File(root, fld);
        if (directory.exists()) {
            File[] files = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".rcd");
                }
            });

            for (File f : files) {
                try {
                    // PlayItem item = new PlayItem(f);
                    PlayItem item = new RawPlayItem(f);
                    this.items.add((PlayItemBase) item);
                } catch (ClassNotFoundException | IOException ex) {
                    Logger.getLogger(PlaylistLocal2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
            
            
        

//        for (int i = startHour; i < endHour; i += 1) {
//            final int folder = (int) (i / 24);
//            final int fileName = i;
//            
//            System.out.println("folder: " + folder + " + " + fileName);
//            File directory = new File(root, Integer.toString(folder));
//            if (!directory.exists()) continue;
//            
//            File [] files = directory.listFiles(new FilenameFilter() {
//                @Override
//                public boolean accept(File dir, String name) {
//                    return name.startsWith(fileName + ".") && name.endsWith(".rcd");
//                }
//            });
//            
//            if (files == null || files.length == 0) continue;
//            
//            for (File f : files) {
//                try {
//                    PlayItem item = new PlayItem(f);
//                    this.items.add((PlayItemBase) item);
//                } catch (ClassNotFoundException | IOException ex) {
//                    Logger.getLogger(PlaylistLocal2.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
        
        for (IPlayListEventListener l : this.listener) {
            l.buildCompleted(this.items);
        }
    }

    @Override
    public void addListenre(IPlayListEventListener listener) {
        this.listener.add(listener);
    }
    
    @Override
    public void play() {
        if (this.playing == null) return;
        if (this.playing.isPause()) this.playing.resume();
        else 
            this.playing.play();
    }
    
    @Override
    public void pause() {
        if (this.playing == null) return;
        this.playing.pause();
    }
    
    @Override
    public void stop() {
        if (this.playing == null) return;
        this.playing.stop();
    }

    @Override
    public int getCurrentIndex() {
        return this.index;
    }

    @Override
    public boolean isAvailable() {
        return this.index < this.items.size() - 1;
    }

    @Override
    public void seek(long value) {
        this.playing.seek(value);
    }
    
    @Override
    public void increaseSpeed() {
        if (this.speed == 5) 
            this.speed = 1;
        else 
            this.speed++;
       playing.setSpeed(speed);
    }
    
    @Override
    public int getSpeed() {
        return this.speed;
    }
}
 