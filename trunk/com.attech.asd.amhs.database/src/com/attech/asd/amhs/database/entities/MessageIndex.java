/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.database.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ANDH
 */
@Entity
@Table(name = "message_index")
@NamedQueries({
    @NamedQuery(name = "MessageIndex.findProcessList", query = "SELECT m FROM MessageIndex m WHERE m.accountID = :accountID AND m.retryCount < 10 ORDER BY m.id"),
    @NamedQuery(name = "MessageIndex.findAll", query = "SELECT m FROM MessageIndex m"),
    @NamedQuery(name = "MessageIndex.findById", query = "SELECT m FROM MessageIndex m WHERE m.id = :id"),
    @NamedQuery(name = "MessageIndex.findByAccountID", query = "SELECT m FROM MessageIndex m WHERE m.accountID = :accountID"),
    @NamedQuery(name = "MessageIndex.findBySeq", query = "SELECT m FROM MessageIndex m WHERE m.seq = :seq"),
    @NamedQuery(name = "MessageIndex.findByOrigin", query = "SELECT m FROM MessageIndex m WHERE m.origin = :origin"),
    @NamedQuery(name = "MessageIndex.findBySubject", query = "SELECT m FROM MessageIndex m WHERE m.subject = :subject"),
    @NamedQuery(name = "MessageIndex.findBySubmissionTime", query = "SELECT m FROM MessageIndex m WHERE m.submissionTime = :submissionTime"),
    @NamedQuery(name = "MessageIndex.findByErrorCode", query = "SELECT m FROM MessageIndex m WHERE m.errorCode = :errorCode"),
    @NamedQuery(name = "MessageIndex.findByRetryCount", query = "SELECT m FROM MessageIndex m WHERE m.retryCount = :retryCount"),
    @NamedQuery(name = "MessageIndex.findByCreatedDate", query = "SELECT m FROM MessageIndex m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MessageIndex.findByLastUpdatedDate", query = "SELECT m FROM MessageIndex m WHERE m.lastUpdatedDate = :lastUpdatedDate")})
public class MessageIndex implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "AccountID")
    private int accountID;
    @Column(name = "Seq")
    private Integer seq;
    @Column(name = "Origin")
    private String origin;
    @Column(name = "Subject")
    private String subject;
    @Column(name = "SubmissionTime")
    private String submissionTime;
    @Column(name = "ErrorCode")
    private Integer errorCode;
    @Column(name = "RetryCount")
    private Integer retryCount;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "LastUpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;
     @Column(name = "Description")
    private String description;

    public MessageIndex() {
        this.errorCode = 0;
        this.retryCount = 0;
    }

    public MessageIndex(Integer id) {
        this();
	this.id = id;
    }

    public MessageIndex(Integer id, int accountID) {
        this();
	this.id = id;
	this.accountID = accountID;
    }
    
    /* PROPERTIES */
    //<editor-fold defaultstate="collapsed" desc="Class properties">
    
    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public int getAccountID() {
	return accountID;
    }

    public void setAccountID(int accountID) {
	this.accountID = accountID;
    }

    public Integer getSeq() {
	return seq;
    }

    public void setSeq(Integer seq) {
	this.seq = seq;
    }

    public String getOrigin() {
	return origin;
    }

    public void setOrigin(String origin) {
	this.origin = origin;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getSubmissionTime() {
	return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
	this.submissionTime = submissionTime;
    }

    public Integer getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
	this.errorCode = errorCode;
    }

    public Integer getRetryCount() {
	return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
	this.retryCount = retryCount;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
	return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
	this.lastUpdatedDate = lastUpdatedDate;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    //</editor-fold>
    
    @Override
    public int hashCode() {
	int hash = 0;
	hash += (id != null ? id.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof MessageIndex)) {
	    return false;
	}
	MessageIndex other = (MessageIndex) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.attech.hibernation.entities.MessageIndex[ id=" + id + " ]";
    }
    
    public void increasingRetryCount() {
        if (this.retryCount == null) {
            this.retryCount = 0;
        }
        this.retryCount++;
    }
}
