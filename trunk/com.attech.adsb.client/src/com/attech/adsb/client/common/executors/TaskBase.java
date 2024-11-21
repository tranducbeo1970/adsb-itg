/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common.executors;

import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.StateChangeListener;
import com.attech.adsb.client.common.StateEventType;
import javax.swing.SwingWorker;

/**
 *
 * @author andh
 */
public abstract class TaskBase extends SwingWorker<Integer, Integer> {


    protected StateChangeListener stateListeners;
    private String message;

    public abstract String getTitle();

    protected abstract MLogger getLog();

    protected void updateProgress(int progress) {
        publish(new Integer[]{progress});
        this.setProgress(progress);
    }

    public synchronized void setStateChangeLister(StateChangeListener listener) {
        this.stateListeners = listener;
    }

    protected synchronized void stateChanged(StateEventType eventType, Object obj) {
	if (this.stateListeners == null) { return; }
	this.stateListeners.onBackgroundWorkerStateChanged(eventType, obj);
    }
    
    protected synchronized void stateChanged(String message) {
        if (this.stateListeners == null) {
            return;
        }
        stateListeners.onBackgroundWorkerStateChanged(StateEventType.PROGRESS_UDDATE, message);
    }

    protected synchronized void stateChanged(Exception ex) {
        if (this.stateListeners == null) {
            return;
        }

        stateListeners.onBackgroundWorkerStateChanged(StateEventType.ERROR, ex);
    }

    protected void waiting(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            getLog().error(ex);
        }
    }
    
    
    public String getMessage() {
	return message;
    }
    
    protected void setMessage(String message) {
	this.message = message;
    }

}
