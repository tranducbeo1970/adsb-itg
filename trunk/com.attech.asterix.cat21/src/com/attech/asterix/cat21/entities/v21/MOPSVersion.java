/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities.v21;

/**
 *
 * @author andh
 */
public class MOPSVersion {
    private boolean versionNotSupported;
    private short versionNumber;
    private short linkTechnologyType;

    /**
     * @return the versionNotSupported
     */
    public boolean isVersionNotSupported() {
        return versionNotSupported;
    }

    /**
     * @param versionNotSupported the versionNotSupported to set
     */
    public void setVersionNotSupported(boolean versionNotSupported) {
        this.versionNotSupported = versionNotSupported;
    }

    /**
     * @return the versionNumber
     */
    public short getVersionNumber() {
        return versionNumber;
    }

    /**
     * @param versionNumber the versionNumber to set
     */
    public void setVersionNumber(short versionNumber) {
        this.versionNumber = versionNumber;
    }

    /**
     * @return the linkTechnologyType
     */
    public short getLinkTechnologyType() {
        return linkTechnologyType;
    }

    /**
     * @param linkTechnologyType the linkTechnologyType to set
     */
    public void setLinkTechnologyType(short linkTechnologyType) {
        this.linkTechnologyType = linkTechnologyType;
    }
    
    @Override
    public String toString() {
        return "versionNotSupported: " + this.versionNotSupported + " VersionNum: " + versionNumber + "linkTechnologyType: " + linkTechnologyType;
    }
}
