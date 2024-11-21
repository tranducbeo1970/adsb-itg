/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;


/**
 *
 * @author Saitama
 */
public abstract class BaseThread extends Thread {

    protected boolean requestStop;

    protected String presentationAddress;
    protected String user;
    protected String password;
    protected Integer autoActionId;

    protected Connection connection;

    public BaseThread() {
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
//        try {
	synchronized (this) {
	    if (timeout == 0) {
		getLogger().debug("Waiting");
		this.wait();
	    } else {
		getLogger().info("Waiting in %s (ms)", timeout);
		this.wait(timeout);
	    }
	}

//        } catch (InterruptedException ex) {
//            getLogger().error("Interrupted");
//            getLogger().debug(ex);
//            Thread.currentThread().interrupt();
//        }
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
//	try {
            if (time <= 0 ) {
                return;
            }
            
	    Thread.sleep(time);
//	} catch (InterruptedException ex) {
//	    getLogger().error(ex);
//	}
    }

//    public void setAccount(Account account) {
//	this.user = account.getMailbox();
//	this.password = account.getPassword();
//	this.connectionType = account.getType();
//	this.presentationAddress = account.getServerAddress();
//	this.autoActionId = account.getAutoAlert();
//
//    }

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
