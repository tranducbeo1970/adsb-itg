/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ANDH
 */
@XmlRootElement(name = "SignalQuality")
@XmlAccessorType(XmlAccessType.NONE)
public class SignalQuality {

    @XmlAttribute(name = "nic")
    private int nic;
    
    @XmlAttribute(name = "nac")
    private int nac;
    
    @XmlAttribute(name = "sil")
    private int sil;
    
    public SignalQuality() {
    }
    
    public SignalQuality(int nic, int nac, int sil) {
        this.nic = nic;
        this.nac = nac;
        this.sil = sil;
    }
    
    public boolean validate(int nic, int nac, int sil) {
        return nic >= this.nic && nac >= this.nac && sil >= this.sil;
    }

    /**
     * @return the nic
     */
    public int getNic() {
        return nic;
    }

    /**
     * @param nic the nic to set
     */
    public void setNic(int nic) {
        this.nic = nic;
    }

    /**
     * @return the nac
     */
    public int getNac() {
        return nac;
    }

    /**
     * @param nac the nac to set
     */
    public void setNac(int nac) {
        this.nac = nac;
    }

    /**
     * @return the sil
     */
    public int getSil() {
        return sil;
    }

    /**
     * @param sil the sil to set
     */
    public void setSil(int sil) {
        this.sil = sil;
    }
}
