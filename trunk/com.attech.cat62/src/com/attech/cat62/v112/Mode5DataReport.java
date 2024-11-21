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
public class Mode5DataReport {
    
    private boolean mode5SummaryAvailable;
    private boolean mode5PinAvailable;
    private boolean mode5ReportPosAvailable;
    private boolean mode5GNSSDerivedAltitude;
    private boolean extendedCode1CodeAvailable;
    private boolean timeOffsetAvailable;
    private boolean xpulseAvailable;
    
    private Mode5Summary mode5Summary;
    private Mode5PIN mode5Pin;
    private WGS84Coordinate mode5ReportPos;
    private Mode5GNSSDerivedAltitude mode5GNSSAltitude;
    private int code1;
    private double timeOffSet;
    private XPulsePresent xpulsePresent;

    public static int extract(byte[] bytes, int index, Mode5DataReport code) {
        if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
        byte cbyte = bytes[index++];
        code.setMode5SummaryAvailable((cbyte & 0x80) > 0);
        code.setMode5PinAvailable((cbyte & 0x40) > 0);
        code.setMode5ReportPosAvailable((cbyte & 0x20) > 0);
        code.setMode5GNSSDerivedAltitude((cbyte & 0x10) > 0);
        code.setExtendedCode1CodeAvailable((cbyte & 0x08) > 0);
        code.setTimeOffsetAvailable((cbyte & 0x04) > 0);
        code.setXpulseAvailable((cbyte & 0x02) > 0);
        if ((cbyte & 0x01) == 0) return 1;
        
        int count = 1;
        int result;

        // Subfield #1
        if (code.isMode5SummaryAvailable()) {
            Mode5Summary mode5Summary = new Mode5Summary();
            result = Mode5Summary.extract(bytes, index, mode5Summary);
            if (result < 0) return -1;
            code.setMode5Summary(mode5Summary);
            count += result;
        }
        
        // Subfield #2
        if (code.isMode5PinAvailable()) {
            Mode5PIN mode5pin = new Mode5PIN();
            result = Mode5PIN.extract(bytes, index, mode5pin);
            if (result < 0) return -1;
            code.setMode5Pin(mode5pin);
            count += result;
        }
        
        // Subfield #3
        if (code.isMode5ReportPosAvailable()) {
            if (Byter.validateIndex(index, bytes.length, 6)) return -1;
            WGS84Coordinate pos = new WGS84Coordinate();
            int val = Byter.getComplementNumber(new byte[]{bytes[index++], bytes[index++], bytes[index++]});
            pos.setLat(val * 180 / Math.pow(2, 23));
            val = Byter.getComplementNumber(new byte[]{bytes[index++], bytes[index++], bytes[index++]});
            pos.setLng(val * 180 / Math.pow(2, 23));
            code.setMode5ReportPos(pos);
            count += 6;
        }
        
        // Subfield #4
        if (code.isMode5GNSSDerivedAltitude()) {
            Mode5GNSSDerivedAltitude altitude = new Mode5GNSSDerivedAltitude();
            result = Mode5GNSSDerivedAltitude.extract(bytes, index, altitude);
            if (result < 0) return -1;
            code.setMode5GNSSAltitude(altitude);
            count += result;
        }
        
        // Subfield #5
        if (code.isExtendedCode1CodeAvailable()) {
            if (Byter.validateIndex(index, bytes.length, 2)) return -1;
            int val = (bytes[index++] & 0x0F) << 8 | (bytes[index++] & 0xFF);
            code.setCode1(Integer.parseInt(Integer.toOctalString(val)));
            count += 2;
        }
        
        // Subfield #6
        if (code.isTimeOffsetAvailable()) {
            if (Byter.validateIndex(index, bytes.length, 1)) return -1;
            code.setTimeOffSet(getTimeOffset(bytes[index++]));
            count += 1;
        }
        
        // Subfield #7
        if (code.isXpulseAvailable()) {
            XPulsePresent xpulse= new XPulsePresent();
            result = XPulsePresent.extract(bytes, index, xpulse);
            if (result < 0) return -1;
            code.setXpulsePresent(xpulse);
            count += result;
        }
        return 4;
    }
    
    private static double getTimeOffset(byte byte1) {
        boolean positive = (int) (byte1 & 0x80) == 0;
        return ((positive) ? (byte1 & 0xFF) :  -((~byte1 & 0xFF) + 0x01)) / 128;
    }
    
    /**
     * @return the mode5SummaryAvailable
     */
    public boolean isMode5SummaryAvailable() {
        return mode5SummaryAvailable;
    }

    /**
     * @param mode5SummaryAvailable the mode5SummaryAvailable to set
     */
    public void setMode5SummaryAvailable(boolean mode5SummaryAvailable) {
        this.mode5SummaryAvailable = mode5SummaryAvailable;
    }

    /**
     * @return the mode5PinAvailable
     */
    public boolean isMode5PinAvailable() {
        return mode5PinAvailable;
    }

    /**
     * @param mode5PinAvailable the mode5PinAvailable to set
     */
    public void setMode5PinAvailable(boolean mode5PinAvailable) {
        this.mode5PinAvailable = mode5PinAvailable;
    }

    /**
     * @return the mode5ReportPosAvailable
     */
    public boolean isMode5ReportPosAvailable() {
        return mode5ReportPosAvailable;
    }

    /**
     * @param mode5ReportPosAvailable the mode5ReportPosAvailable to set
     */
    public void setMode5ReportPosAvailable(boolean mode5ReportPosAvailable) {
        this.mode5ReportPosAvailable = mode5ReportPosAvailable;
    }

    /**
     * @return the mode5GNSSDerivedAltitude
     */
    public boolean isMode5GNSSDerivedAltitude() {
        return mode5GNSSDerivedAltitude;
    }

    /**
     * @param mode5GNSSDerivedAltitude the mode5GNSSDerivedAltitude to set
     */
    public void setMode5GNSSDerivedAltitude(boolean mode5GNSSDerivedAltitude) {
        this.mode5GNSSDerivedAltitude = mode5GNSSDerivedAltitude;
    }

    /**
     * @return the extendedCode1CodeAvailable
     */
    public boolean isExtendedCode1CodeAvailable() {
        return extendedCode1CodeAvailable;
    }

    /**
     * @param extendedCode1CodeAvailable the extendedCode1CodeAvailable to set
     */
    public void setExtendedCode1CodeAvailable(boolean extendedCode1CodeAvailable) {
        this.extendedCode1CodeAvailable = extendedCode1CodeAvailable;
    }

    /**
     * @return the timeOffsetAvailable
     */
    public boolean isTimeOffsetAvailable() {
        return timeOffsetAvailable;
    }

    /**
     * @param timeOffsetAvailable the timeOffsetAvailable to set
     */
    public void setTimeOffsetAvailable(boolean timeOffsetAvailable) {
        this.timeOffsetAvailable = timeOffsetAvailable;
    }

    /**
     * @return the xpulseAvailable
     */
    public boolean isXpulseAvailable() {
        return xpulseAvailable;
    }

    /**
     * @param xpulseAvailable the xpulseAvailable to set
     */
    public void setXpulseAvailable(boolean xpulseAvailable) {
        this.xpulseAvailable = xpulseAvailable;
    }

    /**
     * @return the mode5Summary
     */
    public Mode5Summary getMode5Summary() {
        return mode5Summary;
    }

    /**
     * @param mode5Summary the mode5Summary to set
     */
    public void setMode5Summary(Mode5Summary mode5Summary) {
        this.mode5Summary = mode5Summary;
    }

    /**
     * @return the mode5Pin
     */
    public Mode5PIN getMode5Pin() {
        return mode5Pin;
    }

    /**
     * @param mode5Pin the mode5Pin to set
     */
    public void setMode5Pin(Mode5PIN mode5Pin) {
        this.mode5Pin = mode5Pin;
    }

    /**
     * @return the mode5ReportPos
     */
    public WGS84Coordinate getMode5ReportPos() {
        return mode5ReportPos;
    }

    /**
     * @param mode5ReportPos the mode5ReportPos to set
     */
    public void setMode5ReportPos(WGS84Coordinate mode5ReportPos) {
        this.mode5ReportPos = mode5ReportPos;
    }

    /**
     * @return the mode5GNSSAltitude
     */
    public Mode5GNSSDerivedAltitude getMode5GNSSAltitude() {
        return mode5GNSSAltitude;
    }

    /**
     * @param mode5GNSSAltitude the mode5GNSSAltitude to set
     */
    public void setMode5GNSSAltitude(Mode5GNSSDerivedAltitude mode5GNSSAltitude) {
        this.mode5GNSSAltitude = mode5GNSSAltitude;
    }

    /**
     * @return the code1
     */
    public int getCode1() {
        return code1;
    }

    /**
     * @param code1 the code1 to set
     */
    public void setCode1(int code1) {
        this.code1 = code1;
    }

    /**
     * @return the timeOffSet
     */
    public double getTimeOffSet() {
        return timeOffSet;
    }

    /**
     * @param timeOffSet the timeOffSet to set
     */
    public void setTimeOffSet(double timeOffSet) {
        this.timeOffSet = timeOffSet;
    }

    /**
     * @return the xpulsePresent
     */
    public XPulsePresent getXpulsePresent() {
        return xpulsePresent;
    }

    /**
     * @param xpulsePresent the xpulsePresent to set
     */
    public void setXpulsePresent(XPulsePresent xpulsePresent) {
        this.xpulsePresent = xpulsePresent;
    }
    
}
