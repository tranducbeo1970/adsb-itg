/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class FinalStateSelectedAltitude {
    private boolean managedVertical;
    private boolean altitudeHold;
    private boolean approach;
    private int altitude;

    public static int extract(byte[] bytes, int index, FinalStateSelectedAltitude code) {

        if (!Byter.validateIndex(index, bytes.length, 2)) {
            return -1;
        }
        
        byte currentByte = bytes[index++];
        int unit = (currentByte >> 7) & 0x01;
        code.setManagedVertical(unit > 0);

        unit = (currentByte >> 6) & 0x01;
        code.setAltitudeHold(unit > 0);

        unit = (currentByte >> 5) & 0x01;
        code.setApproach(unit > 0);
        unit = getComplementNumber(currentByte, bytes[index++]);
        code.setAltitude(unit * 25);
        return 2;
    }

    private static int getComplementNumber(byte byte1, byte byte2) {
        boolean positive = (int) (byte1 & 0x10) == 0;
        return (positive) ? (byte1 & 0x1F) << 8 | (byte2 & 0xFF) : 
                -(((~byte1 & 0x1F) << 8 | (~byte2 & 0xFF)) + 0x01);
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
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
//        builder.append("Altitude: ").append(this.getAltitude()).append(" ");
//        builder.append("Hold Mode Active: ").append(this.getIsAltitudeHoldModeActive()).append(" ");
//        builder.append("Approach Mode Active: ").append(this.getIsApproachModeActive()).append(" ");
//        builder.append("Manage Vertical Mode Active: ").append(this.getIsManageVerticalModeActive()).append(" ");
        return builder.toString();
    }

    /**
     * @return the managedVertical
     */
    public boolean isManagedVertical() {
        return managedVertical;
    }

    /**
     * @param managedVertical the managedVertical to set
     */
    public void setManagedVertical(boolean managedVertical) {
        this.managedVertical = managedVertical;
    }

    /**
     * @return the altitudeHold
     */
    public boolean isAltitudeHold() {
        return altitudeHold;
    }

    /**
     * @param altitudeHold the altitudeHold to set
     */
    public void setAltitudeHold(boolean altitudeHold) {
        this.altitudeHold = altitudeHold;
    }

    /**
     * @return the approach
     */
    public boolean isApproach() {
        return approach;
    }

    /**
     * @param approach the approach to set
     */
    public void setApproach(boolean approach) {
        this.approach = approach;
    }
}
