/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.monitor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ANDH
 */
public class ThreadStatus implements Serializable {


    private int id;
    private String remark;
    private Status status;
    private int messageCount;
    private Date startTime;
    
    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getRemark() {
	return remark;
    }

    public void setRemark(String remark) {
	this.remark = remark;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) {
	this.status = status;
    }

    public int getMessageCount() {
	return messageCount;
    }

    public void setMessageCount(int messageCount) {
	this.messageCount = messageCount;
    }

    public Date getStartTime() {
	return startTime;
    }

    public void setStartTime(Date startTime) {
	this.startTime = startTime;
    }
    
    @Override
    public String toString()  {
        return String.format("%s:%s %s(msg) %s %s", this.id, this.status, this.messageCount, this.startTime, this.remark);
    }
}
