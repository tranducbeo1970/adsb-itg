/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.monitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class ServerStatus implements Serializable {

    private List<ThreadStatus> threadStatus;

    public ServerStatus() {
	threadStatus = new ArrayList<>();
    }

    public void addStatus(ThreadStatus status) {
	this.threadStatus.add(status);
    }

    public List<ThreadStatus> getThreadStatus() {
	return threadStatus;
    }

    public void setThreadStatus(List<ThreadStatus> threadStatus) {
	this.threadStatus = threadStatus;
    }

}
