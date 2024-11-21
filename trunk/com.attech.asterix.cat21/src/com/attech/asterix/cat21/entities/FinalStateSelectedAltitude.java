/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.entities;

/**
 *
 * @author andh
 */
public class FinalStateSelectedAltitude {
    private boolean isManageVerticalModeActive;
    private boolean isAltitudeHoldModeActive;
    private boolean isApproachModeActive;
    private int altitude;

    /**
     * @return the isManageVerticalModeActive
     */
    public boolean getIsManageVerticalModeActive() {
        return isManageVerticalModeActive;
    }

    /**
     * @param isManageVerticalModeActive the isManageVerticalModeActive to set
     */
    public void setIsManageVerticalModeActive(boolean isManageVerticalModeActive) {
        this.isManageVerticalModeActive = isManageVerticalModeActive;
    }

    /**
     * @return the isAltitudeHoldModeActive
     */
    public boolean getIsAltitudeHoldModeActive() {
        return isAltitudeHoldModeActive;
    }

    /**
     * @param isAltitudeHoldModeActive the isAltitudeHoldModeActive to set
     */
    public void setIsAltitudeHoldModeActive(boolean isAltitudeHoldModeActive) {
        this.isAltitudeHoldModeActive = isAltitudeHoldModeActive;
    }

    /**
     * @return the isApproachModeActive
     */
    public boolean getIsApproachModeActive() {
        return isApproachModeActive;
    }

    /**
     * @param isApproachModeActive the isApproachModeActive to set
     */
    public void setIsApproachModeActive(boolean isApproachModeActive) {
        this.isApproachModeActive = isApproachModeActive;
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
