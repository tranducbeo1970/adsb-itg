/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class LinkTechnology {
    private boolean cockpitDisplayDti;
    private boolean modeSExtendSquitter;
    private boolean uat;
    private boolean vdlMode4;
    private boolean otherTechnology;

    /**
     * @return the cockpitDisplayDti
     */
    public boolean isCockpitDisplayDti() {
        return cockpitDisplayDti;
    }

    /**
     * @param cockpitDisplayDti the cockpitDisplayDti to set
     */
    public void setCockpitDisplayDti(boolean cockpitDisplayDti) {
        this.cockpitDisplayDti = cockpitDisplayDti;
    }

    /**
     * @return the modeSExtendSquitter
     */
    public boolean isModeSExtendSquitter() {
        return modeSExtendSquitter;
    }

    /**
     * @param modeSExtendSquitter the modeSExtendSquitter to set
     */
    public void setModeSExtendSquitter(boolean modeSExtendSquitter) {
        this.modeSExtendSquitter = modeSExtendSquitter;
    }

    /**
     * @return the uat
     */
    public boolean isUat() {
        return uat;
    }

    /**
     * @param uat the uat to set
     */
    public void setUat(boolean uat) {
        this.uat = uat;
    }

    /**
     * @return the vdlMode4
     */
    public boolean isVdlMode4() {
        return vdlMode4;
    }

    /**
     * @param vdlMode4 the vdlMode4 to set
     */
    public void setVdlMode4(boolean vdlMode4) {
        this.vdlMode4 = vdlMode4;
    }

    /**
     * @return the otherTechnology
     */
    public boolean isOtherTechnology() {
        return otherTechnology;
    }

    /**
     * @param otherTechnology the otherTechnology to set
     */
    public void setOtherTechnology(boolean otherTechnology) {
        this.otherTechnology = otherTechnology;
    }
    
    @Override
    public String toString() {
        return "Lnk Tech CockPit:" + this.cockpitDisplayDti + " ModeSExtend" + this.modeSExtendSquitter + " OTher:" + this.otherTechnology + " VdlMode4:" + this.vdlMode4 + " OtherTech:" + this.otherTechnology;
    }
}
