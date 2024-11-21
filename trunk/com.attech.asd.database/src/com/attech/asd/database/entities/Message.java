/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.database.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author hong
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findByAccountID", query = "SELECT m FROM Message m WHERE m.accountID = :accountID"),
    @NamedQuery(name = "Message.findBySeq", query = "SELECT m FROM Message m WHERE m.seq = :seq"),
    @NamedQuery(name = "Message.findByDate", query = "SELECT m FROM Message m WHERE m.date = :date"),
    @NamedQuery(name = "Message.findByDof", query = "SELECT m FROM Message m WHERE m.dof = :dof"),
    @NamedQuery(name = "Message.findByCallsign", query = "SELECT m FROM Message m WHERE m.callsign = :callsign"),
    @NamedQuery(name = "Message.findByOrigin", query = "SELECT m FROM Message m WHERE m.origin = :origin"),
    @NamedQuery(name = "Message.findByCategory", query = "SELECT m FROM Message m WHERE m.category = :category"),
    @NamedQuery(name = "Message.findByPriority", query = "SELECT m FROM Message m WHERE m.priority = :priority"),
    @NamedQuery(name = "Message.findByParsingCode", query = "SELECT m FROM Message m WHERE m.parsingCode = :parsingCode"),
    @NamedQuery(name = "Message.findBySubmissionTime", query = "SELECT m FROM Message m WHERE m.submissionTime = :submissionTime"),
    @NamedQuery(name = "Message.findByDeliveryTime", query = "SELECT m FROM Message m WHERE m.deliveryTime = :deliveryTime"),
    @NamedQuery(name = "Message.findByCreatedDate", query = "SELECT m FROM Message m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "Message.findByUpdatedDate", query = "SELECT m FROM Message m WHERE m.updatedDate = :updatedDate")})

@NamedNativeQueries({
    @NamedNativeQuery(
            name = "cleanFplBeforeDof",
            query = "CALL CleanFplBeforeDof(:dof)"
    )
})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "AccountID")
    private int accountID;
    @Basic(optional = false)
    @Column(name = "Seq")
    private Integer seq;
    @Basic(optional = false)
    @Column(name = "Date")
    private String date;
    @Column(name = "DOF")
    private String dof;
    @Column(name = "Callsign")
    private String callsign;
    @Basic(optional = false)
    @Column(name = "Origin")
    private String origin;
    @Column(name = "Category")
    private String category;
    @Basic(optional = false)
    @Column(name = "Priority")
    private int priority;
    @Basic(optional = false)
    @Lob
    @Column(name = "Content")
    private String content;
    @Column(name = "ParsingCode")
    private Integer parsingCode;
    @Column(name = "SubmissionTime")
    private String submissionTime;
    @Column(name = "DeliveryTime")
    private String deliveryTime;
    @Basic(optional = false)
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, int accountID, Integer seq, String date, String dof, String origin, String category, int priority, String content, Date createdDate, Date updatedDate) {
        this.id = id;
        this.accountID = accountID;
        this.seq = seq;
        this.date = date;
        this.dof = dof;
        this.origin = origin;
        this.category = category;
        this.priority = priority;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDof() {
        return dof;
    }

    public void setDof(String dof) {
        this.dof = dof;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getParsingCode() {
        return parsingCode;
    }

    public void setParsingCode(Integer parsingCode) {
        this.parsingCode = parsingCode;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.attech.hibernation.entities.Message[ id=" + id + " ]";
    }
    
}
