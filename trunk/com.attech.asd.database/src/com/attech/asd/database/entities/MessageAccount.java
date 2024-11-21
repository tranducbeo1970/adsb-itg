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
import javax.persistence.Id;
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
@Table(name = "message_account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MessageAccount.findAll", query = "SELECT m FROM MessageAccount m"),
    @NamedQuery(name = "MessageAccount.findById", query = "SELECT m FROM MessageAccount m WHERE m.id = :id"),
    @NamedQuery(name = "MessageAccount.findByAccount", query = "SELECT m FROM MessageAccount m WHERE m.account = :account"),
    @NamedQuery(name = "MessageAccount.findByPassword", query = "SELECT m FROM MessageAccount m WHERE m.password = :password"),
    @NamedQuery(name = "MessageAccount.findByConnectionString", query = "SELECT m FROM MessageAccount m WHERE m.connectionString = :connectionString"),
    @NamedQuery(name = "MessageAccount.findByName", query = "SELECT m FROM MessageAccount m WHERE m.name = :name"),
    @NamedQuery(name = "MessageAccount.findByCheckPoint", query = "SELECT m FROM MessageAccount m WHERE m.checkPoint = :checkPoint"),
    @NamedQuery(name = "MessageAccount.findByEnable", query = "SELECT m FROM MessageAccount m WHERE m.enable = :enable"),
    @NamedQuery(name = "MessageAccount.findByDescription", query = "SELECT m FROM MessageAccount m WHERE m.description = :description"),
    @NamedQuery(name = "MessageAccount.findByCreatedDate", query = "SELECT m FROM MessageAccount m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MessageAccount.findByUpdatedDate", query = "SELECT m FROM MessageAccount m WHERE m.updatedDate = :updatedDate")})
public class MessageAccount implements Serializable {



    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Account")
    private String account;
    @Column(name = "Password")
    private String password;
    @Column(name = "ConnectionString")
    private String connectionString;
    @Column(name = "Name")
    private String name;
    @Column(name = "CheckPoint")
    private String checkPoint;
    @Column(name = "Enable")
    private Boolean enable;
    @Column(name = "Description")
    private String description;
    @Column(name = "CreatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "UpdatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    private Integer updateInterval;

    public MessageAccount() {
    }

    public MessageAccount(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckPoint() {
        return checkPoint;
    }

    public void setCheckPoint(String checkPoint) {
        this.checkPoint = checkPoint;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    /**
     * @return the updateInterval
     */
    public Integer getUpdateInterval() {
	return updateInterval;
    }

    /**
     * @param updateInterval the updateInterval to set
     */
    public void setUpdateInterval(Integer updateInterval) {
	this.updateInterval = updateInterval;
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
        if (!(object instanceof MessageAccount)) {
            return false;
        }
        MessageAccount other = (MessageAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.MessageAccount[ id=" + id + " ]";
    }
    
}
