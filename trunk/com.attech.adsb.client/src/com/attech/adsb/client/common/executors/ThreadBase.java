/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common.executors;

import com.attech.adsb.client.common.MLogger;


/**
 *
 * @author Saitama
 */
public abstract class ThreadBase extends Thread {

    protected boolean requestStop;



    public ThreadBase() {
	requestStop = false;
    }

    public void stopThread() {
        getLogger().info("Sending stop signal (Thread state: %s)", this.getState());
        interrupt();
        while (this.isAlive()) {
            getLogger().debug("Request stopping (state: %s)", this.getState());
            interrupt();
        }
    }

    protected void lock(long timeout) throws InterruptedException {
	synchronized (this) {
	    if (timeout == 0) {
		getLogger().debug("Waiting");
		this.wait();
	    } else {
		getLogger().info("Waiting in %s (ms)", timeout);
		this.wait(timeout);
	    }
	}
    }

    public void unlock() {
	synchronized (this) {
	    this.notify();
	}
    }

    public synchronized boolean isStopped() {
	return this.getState() == Thread.State.TERMINATED;
    }

    protected void sleep(int time) throws InterruptedException {
            if (time <= 0 ) {
                return;
            }
            
	    Thread.sleep(time);
    }


    /**
     * @return the requestStop
     */
    protected synchronized boolean isRequestStop() {
        // getLogger().debug("Get request stop result: %s", requestStop);
	return requestStop;
    }

    /**
     * @param requestStop the requestStop to set
     */
    protected synchronized void setRequestStop(boolean requestStop) {
	this.requestStop = requestStop;
	getLogger().debug("Set requested stop flag to %s", requestStop);
    }

    protected abstract MLogger getLogger();
}
