/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import com.attech.asd.amhs.database.dao.DBException;
import com.attech.asd.amhs.database.dao.MessageAccountDao;
import com.attech.asd.amhs.database.entities.MessageAccount;
import com.attech.asd.amhs.service.monitor.ThreadStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author andh
 */
public class ThreadManager {


    private static final MLogger logger = MLogger.getLogger(ThreadManager.class);
    private final HashMap<Integer, ReceivingThread> threads = new HashMap<>();
    private List<MessageAccount> accountList;
    private final MessageAccountDao messageAccountDao = new MessageAccountDao();
    private ThreadEventHandler threadEventHandler;

    public ThreadManager() {
    }

    public void intialize() throws DBException {
//        List<MessageAccount> messageAccounts = messageAccountDao.getAll();
//        for (MessageAccount messageAccount : messageAccounts) {
//            ReceivingThread thread = new ReceivingThread(messageAccount);
//            this.threads.put(thread.getAccountID(), thread);
//        }

    }

    public void start() throws DBException {
	logger.info("Start all thread");
	accountList = messageAccountDao.getAll();
	for (MessageAccount account : accountList) {
	    ReceivingThread thread = this.threads.get(account.getId());
	    if (thread == null) {
		thread = new ReceivingThread(account);
		this.threads.put(account.getId(), thread);
                if (account.getEnable())
                    thread.start();
                //continue;
	    }
            /*
	    if (!account.getEnable()) {
		logger.info("Thread %s:%s is not enabled. Enable thread before start.", account.getId(), account.getName());
		if (thread.isRunning()) {
		    thread.stopThread();
		}
		continue;
	    }
            
	    if (!thread.isAlive()) {
		logger.info("Thread %s:%s is starting.", account.getId(), account.getName());
		thread = new ReceivingThread(account);
		thread.start();
	    }
            */
	}
    }

    public void stop() throws DBException {
	logger.info("Stop all thread");
	for (ReceivingThread thread : this.threads.values()) {
	    thread.stopThread();
	}
    }

    public void start(Integer id) throws DBException {
	if (id == null) {
	    return;
	}
	accountList = messageAccountDao.getAll();
	MessageAccount currentAccount = null;
	for (MessageAccount account : accountList) {
	    if (account.getId() != id) {
		continue;
	    }
	    currentAccount = account;
	    break;
	}
	if (currentAccount == null) {
	    logger.warn("Account %s is not existed.", id);
	    return;
	}
	ReceivingThread thread = this.threads.get(id);
	if (thread != null) {
	    if (!thread.isAlive() && currentAccount.getEnable()) {
		thread = new ReceivingThread(currentAccount);
		thread.start();
	    }
            threads.put(id, thread); // Phai cap nhat lai thread cho threadManager
            //System.out.println("AnhTH DEBUG");
	    return;
	}
	thread = new ReceivingThread(currentAccount);
	threads.put(id, thread);
	if (currentAccount.getEnable()) {
	    thread.start();
	}
    }

    public void stop(Integer id) {
	if (id == null) {
	    return;
	}
	ReceivingThread thread = this.threads.get(id);
	if (thread == null) {
	    logger.warn("Thread %s is not existed.", id);
	    return;
	}
	thread.stopThread();
    }

    public List<ThreadStatus> getThreadStatus() {
	List<ThreadStatus> status = new ArrayList<>();
	for (ReceivingThread thread : threads.values()) {
            
            ThreadStatus statusItem =  thread.getStatus();
	    status.add(statusItem);
            logger.debug(statusItem.toString());
	}
	return status;
    }
    
    public void setThreadEventHandler(ThreadEventHandler threadEventHandler) {
        this.threadEventHandler = threadEventHandler;
    }

}
