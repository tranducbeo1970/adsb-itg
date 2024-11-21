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
public class SystemTrackUpdateAges {
    private boolean trackAgeAvailable;
    private boolean psrAgeAvailable;
    private boolean ssrAgeAvailable;
    private boolean modeSAgeAvailable;
    private boolean adscAgeAvailable;
    private boolean adsbESAgeAvailable;
    private boolean adsbVDLMode4AgeAvailable;
    
    private boolean adsbUATAgeAvailable;
    private boolean loopAgeAvailable;
    private boolean multilaterationAgeAvailable;
    
    private double trackAge;
    private double psrAge;
    private double ssrAge;
    private double modeSAge;
    private double adscAge;
    private double adsbESAge;
    private double adsbVdlMode4Age;
    private double adsbUATAge;
    private double loopAge;
    private double multilaterationAge;
    
    public static int extract(byte[] bytes, int index, SystemTrackUpdateAges code) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setTrackAgeAvailable((cbyte & 0x80) > 0);
        code.setPsrAgeAvailable((cbyte & 0x40) > 0);
        code.setSsrAgeAvailable((cbyte & 0x20) > 0);
        code.setModeSAgeAvailable((cbyte & 0x10) > 0);
        code.setAdscAgeAvailable((cbyte & 0x08) > 0);
        code.setAdsbESAgeAvailable((cbyte & 0x04) > 0);
        code.setAdsbVDLMode4AgeAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 1;
        
        cbyte = bytes[index++];
        code.setAdsbUATAgeAvailable((cbyte & 0x80) > 0);
        code.setLoopAgeAvailable((cbyte & 0x40) > 0);
        code.setMultilaterationAgeAvailable((cbyte & 0x20) > 0);
        if ((cbyte & 0x01) == 0) return 2;
        
        int count = 2;
        
        if (code.isTrackAgeAvailable()) {
            code.setTrackAge((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        if (code.isPsrAgeAvailable()) {
            code.setPsrAge((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        if (code.isSsrAgeAvailable()) {
            code.setSsrAge((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        if (code.isModeSAgeAvailable()) {
            code.setModeSAge((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        if (code.isAdscAgeAvailable()) {
            code.setAdscAge(((bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF)) * 0.25);
            count+=2;
        }
        if (code.isAdsbESAgeAvailable()) {
            code.setAdsbESAge((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        if (code.isAdsbVDLMode4AgeAvailable()) {
            code.setAdsbVdlMode4Age((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        if (code.isAdsbUATAgeAvailable()) {
            code.setAdsbUATAge((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        if (code.isLoopAgeAvailable()) {
            code.setLoopAge((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        if (code.isMultilaterationAgeAvailable()) {
            code.setMultilaterationAge((bytes[index++] & 0xFF) * 0.25);
            count++;
        }
        return count;
    }

    /**
     * @return the trackAgeAvailable
     */
    public boolean isTrackAgeAvailable() {
        return trackAgeAvailable;
    }

    /**
     * @param trackAgeAvailable the trackAgeAvailable to set
     */
    public void setTrackAgeAvailable(boolean trackAgeAvailable) {
        this.trackAgeAvailable = trackAgeAvailable;
    }

    /**
     * @return the psrAgeAvailable
     */
    public boolean isPsrAgeAvailable() {
        return psrAgeAvailable;
    }

    /**
     * @param psrAgeAvailable the psrAgeAvailable to set
     */
    public void setPsrAgeAvailable(boolean psrAgeAvailable) {
        this.psrAgeAvailable = psrAgeAvailable;
    }

    /**
     * @return the ssrAgeAvailable
     */
    public boolean isSsrAgeAvailable() {
        return ssrAgeAvailable;
    }

    /**
     * @param ssrAgeAvailable the ssrAgeAvailable to set
     */
    public void setSsrAgeAvailable(boolean ssrAgeAvailable) {
        this.ssrAgeAvailable = ssrAgeAvailable;
    }

    /**
     * @return the modeSAgeAvailable
     */
    public boolean isModeSAgeAvailable() {
        return modeSAgeAvailable;
    }

    /**
     * @param modeSAgeAvailable the modeSAgeAvailable to set
     */
    public void setModeSAgeAvailable(boolean modeSAgeAvailable) {
        this.modeSAgeAvailable = modeSAgeAvailable;
    }

    /**
     * @return the adscAgeAvailable
     */
    public boolean isAdscAgeAvailable() {
        return adscAgeAvailable;
    }

    /**
     * @param adscAgeAvailable the adscAgeAvailable to set
     */
    public void setAdscAgeAvailable(boolean adscAgeAvailable) {
        this.adscAgeAvailable = adscAgeAvailable;
    }

    /**
     * @return the adsbESAgeAvailable
     */
    public boolean isAdsbESAgeAvailable() {
        return adsbESAgeAvailable;
    }

    /**
     * @param adsbESAgeAvailable the adsbESAgeAvailable to set
     */
    public void setAdsbESAgeAvailable(boolean adsbESAgeAvailable) {
        this.adsbESAgeAvailable = adsbESAgeAvailable;
    }

    /**
     * @return the adsbVDLMode4AgeAvailable
     */
    public boolean isAdsbVDLMode4AgeAvailable() {
        return adsbVDLMode4AgeAvailable;
    }

    /**
     * @param adsbVDLMode4AgeAvailable the adsbVDLMode4AgeAvailable to set
     */
    public void setAdsbVDLMode4AgeAvailable(boolean adsbVDLMode4AgeAvailable) {
        this.adsbVDLMode4AgeAvailable = adsbVDLMode4AgeAvailable;
    }

    /**
     * @return the adsbUATAgeAvailable
     */
    public boolean isAdsbUATAgeAvailable() {
        return adsbUATAgeAvailable;
    }

    /**
     * @param adsbUATAgeAvailable the adsbUATAgeAvailable to set
     */
    public void setAdsbUATAgeAvailable(boolean adsbUATAgeAvailable) {
        this.adsbUATAgeAvailable = adsbUATAgeAvailable;
    }

    /**
     * @return the loopAgeAvailable
     */
    public boolean isLoopAgeAvailable() {
        return loopAgeAvailable;
    }

    /**
     * @param loopAgeAvailable the loopAgeAvailable to set
     */
    public void setLoopAgeAvailable(boolean loopAgeAvailable) {
        this.loopAgeAvailable = loopAgeAvailable;
    }

    /**
     * @return the multilaterationAgeAvailable
     */
    public boolean isMultilaterationAgeAvailable() {
        return multilaterationAgeAvailable;
    }

    /**
     * @param multilaterationAgeAvailable the multilaterationAgeAvailable to set
     */
    public void setMultilaterationAgeAvailable(boolean multilaterationAgeAvailable) {
        this.multilaterationAgeAvailable = multilaterationAgeAvailable;
    }

    /**
     * @return the trackAge
     */
    public double getTrackAge() {
        return trackAge;
    }

    /**
     * @param trackAge the trackAge to set
     */
    public void setTrackAge(double trackAge) {
        this.trackAge = trackAge;
    }

    /**
     * @return the psrAge
     */
    public double getPsrAge() {
        return psrAge;
    }

    /**
     * @param psrAge the psrAge to set
     */
    public void setPsrAge(double psrAge) {
        this.psrAge = psrAge;
    }

    /**
     * @return the ssrAge
     */
    public double getSsrAge() {
        return ssrAge;
    }

    /**
     * @param ssrAge the ssrAge to set
     */
    public void setSsrAge(double ssrAge) {
        this.ssrAge = ssrAge;
    }

    /**
     * @return the modeSAge
     */
    public double getModeSAge() {
        return modeSAge;
    }

    /**
     * @param modeSAge the modeSAge to set
     */
    public void setModeSAge(double modeSAge) {
        this.modeSAge = modeSAge;
    }

    /**
     * @return the adscAge
     */
    public double getAdscAge() {
        return adscAge;
    }

    /**
     * @param adscAge the adscAge to set
     */
    public void setAdscAge(double adscAge) {
        this.adscAge = adscAge;
    }

    /**
     * @return the adsbESAge
     */
    public double getAdsbESAge() {
        return adsbESAge;
    }

    /**
     * @param adsbESAge the adsbESAge to set
     */
    public void setAdsbESAge(double adsbESAge) {
        this.adsbESAge = adsbESAge;
    }

    /**
     * @return the adsbVdlMode4Age
     */
    public double getAdsbVdlMode4Age() {
        return adsbVdlMode4Age;
    }

    /**
     * @param adsbVdlMode4Age the adsbVdlMode4Age to set
     */
    public void setAdsbVdlMode4Age(double adsbVdlMode4Age) {
        this.adsbVdlMode4Age = adsbVdlMode4Age;
    }

    /**
     * @return the adsbUATAge
     */
    public double getAdsbUATAge() {
        return adsbUATAge;
    }

    /**
     * @param adsbUATAge the adsbUATAge to set
     */
    public void setAdsbUATAge(double adsbUATAge) {
        this.adsbUATAge = adsbUATAge;
    }

    /**
     * @return the loopAge
     */
    public double getLoopAge() {
        return loopAge;
    }

    /**
     * @param loopAge the loopAge to set
     */
    public void setLoopAge(double loopAge) {
        this.loopAge = loopAge;
    }

    /**
     * @return the multilaterationAge
     */
    public double getMultilaterationAge() {
        return multilaterationAge;
    }

    /**
     * @param multilaterationAge the multilaterationAge to set
     */
    public void setMultilaterationAge(double multilaterationAge) {
        this.multilaterationAge = multilaterationAge;
    }
    
    
            
}
