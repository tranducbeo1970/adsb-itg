/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "QualityFilter")
@XmlAccessorType(XmlAccessType.NONE)
public class QualityFilter {

    @XmlAttribute(name = "enabled")
    private boolean enabled;
    
    @XmlAttribute(name = "nic")
    private int nic;
    
    @XmlAttribute(name = "nac")
    private int nac;
    
    @XmlAttribute(name = "sil")
    private int sil;

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
