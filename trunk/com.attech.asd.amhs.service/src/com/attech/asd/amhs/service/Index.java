/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service;

import com.isode.x400api.MSListResult;
import com.isode.x400api.X400_att;
import com.isode.x400api.X400ms;

/**
 *
 * @author andh
 */
public class Index implements Comparable<Index> {

    private Integer sequence;
    private String submissionTime;
    private String messageId;
    private String subject;
    private String origin;

    // 1: New 2: Listed 3: Fetched
    private Integer status;

    private String type;

    // 0: Stored 1: Sent
    private Integer clazz;

    // 0: Normal 1: Low 2: High
    private Integer priority = 0;

    public Index() {
    }

    public Index(MSListResult msListResult, int index) {
        this.submissionTime = getStrParam(msListResult, X400_att.X400_S_MESSAGE_SUBMISSION_TIME, index);
        this.status = getIntParam(msListResult, X400_att.X400_N_MS_ENTRY_STATUS, index);
        this.type = getStrParam(msListResult, X400_att.X400_S_OBJECTTYPE, index);
        this.priority = getIntParam(msListResult, X400_att.X400_N_PRIORITY, index);
        this.subject = getStrParam(msListResult, X400_att.X400_S_SUBJECT, index);
        this.sequence = getIntParam(msListResult, X400_att.X400_N_MS_SEQUENCE_NUMBER, index);
        this.origin = getStrParam(msListResult, X400_att.X400_S_OR_ADDRESS, index);
        this.messageId = getStrParam(msListResult, X400_att.X400_S_MESSAGE_IDENTIFIER, index);
    }

    private String getStrParam(MSListResult mslist, int param, int index) {
        final StringBuffer result = new StringBuffer();
        final int code = com.isode.x400api.X400ms.x400_ms_listgetstrparam(mslist, param, index, result);
        if (code == X400_att.X400_E_NOERROR) {
            return result.toString();
        }

        return null;
    }

    private Integer getIntParam(MSListResult mslist, int param, int index) {
        int code = X400ms.x400_ms_listgetintparam(mslist, param, index);
        if (code == X400_att.X400_E_NO_MORE_RESULTS) {
            return null;
        }

        if (code == X400_att.X400_E_NO_VALUE) {
            return null;
        }

        if (code != X400_att.X400_E_NOERROR) {
            return null;
        }

        return mslist.GetIntValue();
    }

    //<editor-fold defaultstate="collapsed" desc="Class property methods">
    /**
     * @return the sequence
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the submissionTime
     */
    public String getSubmissionTime() {
        return submissionTime;
    }

    /**
     * @param submissionTime the submissionTime to set
     */
    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the clazz
     */
    public Integer getClazz() {
        return clazz;
    }

    /**
     * @param clazz the clazz to set
     */
    public void setClazz(Integer clazz) {
        this.clazz = clazz;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    //</editor-fold>

    @Override
    public int compareTo(Index o) {
        return Integer.compare(this.sequence, o.getSequence());
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Message Index :\n");
        builder.append(String.format("    Sequence: %s\n", this.sequence));
        builder.append(String.format("    SubmissionTime: %s\n", this.submissionTime));
        builder.append(String.format("    Subject: %s\n", this.subject));
        builder.append(String.format("    Origin: %s\n", this.origin));
        builder.append(String.format("    Message ID: %s\n", this.messageId));
        builder.append(String.format("    Priority: %s\n", this.priority));
        builder.append(String.format("    Type: %s\n", this.type));
        builder.append(String.format("    Status: %s\n", this.status));
        builder.append(String.format("    Class: %s\n", this.clazz));
        return builder.toString();
    }

}
