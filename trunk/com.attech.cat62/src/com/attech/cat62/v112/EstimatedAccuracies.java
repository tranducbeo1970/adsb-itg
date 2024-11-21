/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class EstimatedAccuracies {
    private boolean estPosAccCastersianAvailable;
    private boolean xyCovarianceAvailable;
    private boolean estPosAccWGS84Available;
    private boolean estGeoAltAccAvailable;
    private boolean estBaroAltAccAvailable;
    private boolean estVelocityAvailable;
    private boolean estAccelerationAvailable;
    private boolean estRateOfClimbAvailable;
    
    private double estPosAccCastersianX, estPosAccCastersianY; 
    private double xyCovariance;
    private double estPosAccWGS84Lat, estPosAccWGS84Lng;
    private double estGeoAltAcc;
    private double estBaroAltAcc;
    private double estVelocityX, estVelocityY;
    private double estAccelerationX, estAccelerationY;
    private double estRateOfClimb;
    
    public static int extract(byte[] bytes, int index, EstimatedAccuracies code) {
        // #1
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setEstPosAccCastersianAvailable((cbyte & 0x80) > 0);
        code.setXyCovarianceAvailable((cbyte & 0x40) > 0);
        code.setEstPosAccWGS84Available((cbyte & 0x20) > 0);
        code.setEstGeoAltAccAvailable((cbyte & 0x10) > 0);
        code.setEstBaroAltAccAvailable((cbyte & 0x08) > 0);
        code.setEstVelocityAvailable((cbyte & 0x04) > 0);
        code.setEstAccelerationAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 1;
        
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        cbyte = bytes[index++];
        code.setEstRateOfClimbAvailable((cbyte & 0x80) > 0);
        if ((cbyte & 0x01) == 0) return 2;
        
        int count = 2;
        
        // Subfield #1
        if (code.isEstPosAccCastersianAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
            int val = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            code.setEstPosAccCastersianX(val * 0.5);
            val = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            code.setEstPosAccCastersianY(val * 0.5);
            count += 4;
        }
        
        // Subfield #2
        if (code.isXyCovarianceAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
            int val = Byter.getComplementNumber(new byte []{bytes[index++], bytes[index++]});
            code.setXyCovariance(val * 0.5);
            count += 2;
        }
        
        // Subfield #3
        if (code.isEstPosAccWGS84Available()) {
            if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
            int val = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            code.setEstPosAccWGS84Lat(val * 180 / Math.pow(2, 25));
            val = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            code.setEstPosAccWGS84Lng(val * 180 / Math.pow(2, 25));
            count += 4;
        }
        
        // Subfield #4
        if (code.isEstGeoAltAccAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            code.setEstGeoAltAcc((bytes[index++] & 0xFF) * 6.25);
            count += 1;
        }
        
        // Subfield #5
        if (code.isEstBaroAltAccAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            code.setEstBaroAltAcc((bytes[index++] & 0xFF) * 0.25);
            count += 1;
        }
        
        // Subfield #6
        if (code.isEstVelocityAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
            code.setEstVelocityX((bytes[index++] & 0xFF) * 0.25);
            code.setEstVelocityY((bytes[index++] & 0xFF) * 0.25);
            count += 2;
        }
        
        // Subfield #7
        if (code.isEstAccelerationAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
            code.setEstAccelerationX((bytes[index++] & 0xFF) * 0.25);
            code.setEstAccelerationY((bytes[index++] & 0xFF) * 0.25);
            count += 2;
        }
        
        // Subfield #8
        if (code.isEstRateOfClimbAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
            code.setEstAccelerationX((bytes[index++] & 0xFF) * 6.25);
            count += 1;
        }
        return count;
    }

    /**
     * @return the estPosAccCastersianAvailable
     */
    public boolean isEstPosAccCastersianAvailable() {
        return estPosAccCastersianAvailable;
    }

    /**
     * @param estPosAccCastersianAvailable the estPosAccCastersianAvailable to set
     */
    public void setEstPosAccCastersianAvailable(boolean estPosAccCastersianAvailable) {
        this.estPosAccCastersianAvailable = estPosAccCastersianAvailable;
    }

    /**
     * @return the xyCovarianceAvailable
     */
    public boolean isXyCovarianceAvailable() {
        return xyCovarianceAvailable;
    }

    /**
     * @param xyCovarianceAvailable the xyCovarianceAvailable to set
     */
    public void setXyCovarianceAvailable(boolean xyCovarianceAvailable) {
        this.xyCovarianceAvailable = xyCovarianceAvailable;
    }

    /**
     * @return the estPosAccWGS84Available
     */
    public boolean isEstPosAccWGS84Available() {
        return estPosAccWGS84Available;
    }

    /**
     * @param estPosAccWGS84Available the estPosAccWGS84Available to set
     */
    public void setEstPosAccWGS84Available(boolean estPosAccWGS84Available) {
        this.estPosAccWGS84Available = estPosAccWGS84Available;
    }

    /**
     * @return the estGeoAltAccAvailable
     */
    public boolean isEstGeoAltAccAvailable() {
        return estGeoAltAccAvailable;
    }

    /**
     * @param estGeoAltAccAvailable the estGeoAltAccAvailable to set
     */
    public void setEstGeoAltAccAvailable(boolean estGeoAltAccAvailable) {
        this.estGeoAltAccAvailable = estGeoAltAccAvailable;
    }

    /**
     * @return the estBaroAltAccAvailable
     */
    public boolean isEstBaroAltAccAvailable() {
        return estBaroAltAccAvailable;
    }

    /**
     * @param estBaroAltAccAvailable the estBaroAltAccAvailable to set
     */
    public void setEstBaroAltAccAvailable(boolean estBaroAltAccAvailable) {
        this.estBaroAltAccAvailable = estBaroAltAccAvailable;
    }

    /**
     * @return the estVelocityAvailable
     */
    public boolean isEstVelocityAvailable() {
        return estVelocityAvailable;
    }

    /**
     * @param estVelocityAvailable the estVelocityAvailable to set
     */
    public void setEstVelocityAvailable(boolean estVelocityAvailable) {
        this.estVelocityAvailable = estVelocityAvailable;
    }

    /**
     * @return the estAccelerationAvailable
     */
    public boolean isEstAccelerationAvailable() {
        return estAccelerationAvailable;
    }

    /**
     * @param estAccelerationAvailable the estAccelerationAvailable to set
     */
    public void setEstAccelerationAvailable(boolean estAccelerationAvailable) {
        this.estAccelerationAvailable = estAccelerationAvailable;
    }

    /**
     * @return the estRateOfClimbAvailable
     */
    public boolean isEstRateOfClimbAvailable() {
        return estRateOfClimbAvailable;
    }

    /**
     * @param estRateOfClimbAvailable the estRateOfClimbAvailable to set
     */
    public void setEstRateOfClimbAvailable(boolean estRateOfClimbAvailable) {
        this.estRateOfClimbAvailable = estRateOfClimbAvailable;
    }

    /**
     * @return the estPosAccCastersianX
     */
    public double getEstPosAccCastersianX() {
        return estPosAccCastersianX;
    }

    /**
     * @param estPosAccCastersianX the estPosAccCastersianX to set
     */
    public void setEstPosAccCastersianX(double estPosAccCastersianX) {
        this.estPosAccCastersianX = estPosAccCastersianX;
    }

    /**
     * @return the estPosAccCastersianY
     */
    public double getEstPosAccCastersianY() {
        return estPosAccCastersianY;
    }

    /**
     * @param estPosAccCastersianY the estPosAccCastersianY to set
     */
    public void setEstPosAccCastersianY(double estPosAccCastersianY) {
        this.estPosAccCastersianY = estPosAccCastersianY;
    }

    /**
     * @return the xyCovariance
     */
    public double getXyCovariance() {
        return xyCovariance;
    }

    /**
     * @param xyCovariance the xyCovariance to set
     */
    public void setXyCovariance(double xyCovariance) {
        this.xyCovariance = xyCovariance;
    }

    /**
     * @return the estPosAccWGS84Lat
     */
    public double getEstPosAccWGS84Lat() {
        return estPosAccWGS84Lat;
    }

    /**
     * @param estPosAccWGS84Lat the estPosAccWGS84Lat to set
     */
    public void setEstPosAccWGS84Lat(double estPosAccWGS84Lat) {
        this.estPosAccWGS84Lat = estPosAccWGS84Lat;
    }

    /**
     * @return the estPosAccWGS84Lng
     */
    public double getEstPosAccWGS84Lng() {
        return estPosAccWGS84Lng;
    }

    /**
     * @param estPosAccWGS84Lng the estPosAccWGS84Lng to set
     */
    public void setEstPosAccWGS84Lng(double estPosAccWGS84Lng) {
        this.estPosAccWGS84Lng = estPosAccWGS84Lng;
    }

    /**
     * @return the estGeoAltAcc
     */
    public double getEstGeoAltAcc() {
        return estGeoAltAcc;
    }

    /**
     * @param estGeoAltAcc the estGeoAltAcc to set
     */
    public void setEstGeoAltAcc(double estGeoAltAcc) {
        this.estGeoAltAcc = estGeoAltAcc;
    }

    /**
     * @return the estBaroAltAcc
     */
    public double getEstBaroAltAcc() {
        return estBaroAltAcc;
    }

    /**
     * @param estBaroAltAcc the estBaroAltAcc to set
     */
    public void setEstBaroAltAcc(double estBaroAltAcc) {
        this.estBaroAltAcc = estBaroAltAcc;
    }

    /**
     * @return the estVelocityX
     */
    public double getEstVelocityX() {
        return estVelocityX;
    }

    /**
     * @param estVelocityX the estVelocityX to set
     */
    public void setEstVelocityX(double estVelocityX) {
        this.estVelocityX = estVelocityX;
    }

    /**
     * @return the estVelocityY
     */
    public double getEstVelocityY() {
        return estVelocityY;
    }

    /**
     * @param estVelocityY the estVelocityY to set
     */
    public void setEstVelocityY(double estVelocityY) {
        this.estVelocityY = estVelocityY;
    }

    /**
     * @return the estAccelerationX
     */
    public double getEstAccelerationX() {
        return estAccelerationX;
    }

    /**
     * @param estAccelerationX the estAccelerationX to set
     */
    public void setEstAccelerationX(double estAccelerationX) {
        this.estAccelerationX = estAccelerationX;
    }

    /**
     * @return the estAccelerationY
     */
    public double getEstAccelerationY() {
        return estAccelerationY;
    }

    /**
     * @param estAccelerationY the estAccelerationY to set
     */
    public void setEstAccelerationY(double estAccelerationY) {
        this.estAccelerationY = estAccelerationY;
    }

    /**
     * @return the estRateOfClimb
     */
    public double getEstRateOfClimb() {
        return estRateOfClimb;
    }

    /**
     * @param estRateOfClimb the estRateOfClimb to set
     */
    public void setEstRateOfClimb(double estRateOfClimb) {
        this.estRateOfClimb = estRateOfClimb;
    }
}
