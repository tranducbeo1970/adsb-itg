/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon;

import com.attech.asd.daemon.recorder.DataRecorder;
import com.attech.asd.daemon.common.ExceptionHandler;
import com.attech.cat21.v210.Cat21Message;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class DataFusion extends Observable {
    private static final Logger logger = Logger.getLogger(DataFusion.class);
    private static DataFusion _instance;
    
    private final SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
    private final HashMap<Integer, FusedItem> fusingList;
    private final List<Cat21Message> messageBuffer1;
    private final List<Cat21Message> messageBuffer2;
    private final DataRecorder fusedRecorder;

    private Timer timer;
    private Timer cleanupTimer;
    private String currentDate;
    
    private boolean switchBuffer;
    private int size = 0;
    private int target;
    private boolean running;
    private int count = 0;
    private int countFused = 0;

    public DataFusion() {
        this.fusingList = new HashMap<>();
        this.messageBuffer1 = new ArrayList<>();
        this.messageBuffer2 = new ArrayList<>();
        this.switchBuffer = false;
        this.fusedRecorder = new DataRecorder(AppContext.getFusedStorageLocation(), "fused");
        this.dateFormatGmt.setTimeZone(AppContext.utc);
    }
    
    private boolean isReset() {
        String date = dateFormatGmt.format(new Date());
        if (date.equalsIgnoreCase(currentDate)) {
            return false;
        }

        this.currentDate = date;
        return true;
    }

    public synchronized void fuse(List<Cat21Message> messages) throws IOException {
        if (isReset()) {
            count = 0;
            countFused = 0;
        }
        count += messages.size();
        
        for (Cat21Message msg : messages) {
            if(msg.getTargetAddress() != null){
                FusedItem item = this.get(msg.getTargetAddress());
                if (!item.validate(msg)) {
                    continue;
                }
                this.fusedRecorder.append(msg.getBytes());
                this.push(msg);
                countFused += 1;
            }
        }
        this.fusedRecorder.flush();
    }

    public static DataFusion getInstance() {
        if (_instance == null) {
            _instance = new DataFusion();
            logger.info("DataFusion has been created");
        }
        return _instance;
    }

    public int targetCount() {
        return target;
    }

    public void stop() {
        this.timer.cancel();
        this.cleanupTimer.cancel();
        this.process();
        this.setRunning(false);
    }

    public void start() {
        this.messageBuffer1.clear();
        this.messageBuffer2.clear();
        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    process();
                } catch (Exception ex) {
                    ExceptionHandler.handle(ex);
                }
            }
        };
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(timerTask, AppContext.getFusePeriodTime(), AppContext.getFusePeriodTime());

        final TimerTask cleanupTimerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    cleanup();
                } catch (Exception ex) {
                    ExceptionHandler.handle(ex);
                }
            }
        };
        this.cleanupTimer = new Timer();
        this.cleanupTimer.scheduleAtFixedRate(cleanupTimerTask, AppContext.getFusionCleanupPeriodTime(), AppContext.getFusionCleanupPeriodTime());
        this.setRunning(true);
    }

    public String getRecordingFile() {
        return this.fusedRecorder.getCurrentFile();
    }

    private FusedItem get(int address) {
        FusedItem item = (FusedItem) fusingList.get(address);
        if (item == null) {
            item = new FusedItem();
            this.fusingList.put(address, item);
            target++;
        }
        return item;
    }

    private void push(Cat21Message msg) {
        final List<Cat21Message> buffer = switchBuffer ? messageBuffer1 : messageBuffer2;
        if (size + msg.getBytes().length > AppContext.getBroadcastPackagetLimit()) {
            process();
        }
        buffer.add(msg);
        size += msg.getBytes().length;
    }

    private synchronized void process() {
        final List<Cat21Message> buffer = switchBuffer ? messageBuffer1 : messageBuffer2;
        switchBuffer = !switchBuffer;
        this.setChanged();
        this.notifyObservers(buffer);
        
        buffer.clear();
        size = 0;
    }

    private void cleanup() {
        Iterator<Entry<Integer, FusedItem>> it = fusingList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, FusedItem> pair = (Map.Entry<Integer, FusedItem>) it.next();
            if (pair.getValue() == null) {
                System.out.printf("NULL: %d \n", pair.getKey());
                continue;
            }            
            if (pair.getValue().isObsoleted(AppContext.getFusionItemTimeout())) {
                it.remove();
            }
        }
        target = this.fusingList.size();
    }
    
    

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @param running the running to set
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
    
    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @return the countFused
     */
    public int getCountFused() {
        return countFused;
    }
}
