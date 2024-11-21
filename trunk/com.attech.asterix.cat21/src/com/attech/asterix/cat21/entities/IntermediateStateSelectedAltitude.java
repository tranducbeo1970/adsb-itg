/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class IntermediateStateSelectedAltitude {
    private Boolean isSourceInformationProvide;
    private short source;
    private int altitude;

    /**
     * @return the isSourceInformationProvide
     */
    public Boolean getIsSourceInformationProvide() {
        return isSourceInformationProvide;
    }

    /**
     * @param isSourceInformationProvide the isSourceInformationProvide to set
     */
    public void setIsSourceInformationProvide(Boolean isSourceInformationProvide) {
        this.isSourceInformationProvide = isSourceInformationProvide;
    }

    /**
     * @return the source
     */
    public short getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(short source) {
        this.source = source;
    }

    /**
     * @return the altitude
     */
    public int getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
