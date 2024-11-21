package com.attech.cat62.v112;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andh
 */
public class MeasureInfo {
    private boolean sensorIDAvailable;
    private boolean measurePosAvailable;
    private boolean measured3DHeightAvailable;
    private boolean lastMeasureModeCAvailable;
    private boolean lastMeasureMode3AAvailable;
    private boolean reportTypeAvailable;
    
    private int sic, sac;
    private double measuredPosTHETA, measuredPosRHO;
    private double measured3DHeight;
    private ModeCCode lastMeasuredModeC;
    private ModeACode lastMeasureMode3A;
    private ReportType reportType;
    
    public static int extract(byte[] bytes, int index, MeasureInfo code) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setSensorIDAvailable((cbyte & 0x80) > 0);
        code.setMeasurePosAvailable((cbyte & 0x40) > 0);
        code.setMeasured3DHeightAvailable((cbyte & 0x20) > 0);
        code.setLastMeasureModeCAvailable((cbyte & 0x10) > 0);
        code.setLastMeasureMode3AAvailable((cbyte & 0x08) > 0);
        code.setReportTypeAvailable((cbyte & 0x04) > 0);
        if ((cbyte & 0x01) == 0) return 1;
        
        int count = 0;
        
        // SubField #1
        if (code.isSensorIDAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
            code.setSac(bytes[index++] & 0xFF);
            code.setSic(bytes[index++] & 0xFF);
            count += 2;
        }
        
        // SubField #2
        if (code.isMeasurePosAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
            int val = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            code.setMeasuredPosRHO(val/256);
            val = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            code.setMeasuredPosTHETA(val * 360/Math.pow(2, 16));
            count += 4;
        }
        
        // SubField #3
        if (code.isMeasured3DHeightAvailable()) {
            if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
            int val = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
            code.setMeasuredPosRHO(val*25);
            count += 2;
        }
        
        // SubField #4
        if (code.isLastMeasureModeCAvailable()) {
            ModeCCode modeC = new ModeCCode();
            int result = ModeCCode.extract(bytes, index, modeC);
            if (result < 0) return -1;
            code.setLastMeasuredModeC(modeC);
            count += result;
        }
        
        // SubField #5
        if (code.isLastMeasureMode3AAvailable()) {
            ModeACode modeA = new ModeACode();
            int result = ModeACode.extract(bytes, index, modeA);
            if (result < 0) return -1;
            code.setLastMeasureMode3A(modeA);
            count += result;
        }
        
        // SubField #6
        if (code.isReportTypeAvailable()) {
            ReportType reportType = new ReportType();
            int result = ReportType.extract(bytes, index, reportType);
            if (result < 0) return -1;
            code.setReportType(reportType);
            count += result;
        }
        
        return count;
    }

    /**
     * @return the sensorIDAvailable
     */
    public boolean isSensorIDAvailable() {
        return sensorIDAvailable;
    }

    /**
     * @param sensorIDAvailable the sensorIDAvailable to set
     */
    public void setSensorIDAvailable(boolean sensorIDAvailable) {
        this.sensorIDAvailable = sensorIDAvailable;
    }

    /**
     * @return the measurePosAvailable
     */
    public boolean isMeasurePosAvailable() {
        return measurePosAvailable;
    }

    /**
     * @param measurePosAvailable the measurePosAvailable to set
     */
    public void setMeasurePosAvailable(boolean measurePosAvailable) {
        this.measurePosAvailable = measurePosAvailable;
    }

    /**
     * @return the measured3DHeightAvailable
     */
    public boolean isMeasured3DHeightAvailable() {
        return measured3DHeightAvailable;
    }

    /**
     * @param measured3DHeightAvailable the measured3DHeightAvailable to set
     */
    public void setMeasured3DHeightAvailable(boolean measured3DHeightAvailable) {
        this.measured3DHeightAvailable = measured3DHeightAvailable;
    }

    /**
     * @return the lastMeasureModeCAvailable
     */
    public boolean isLastMeasureModeCAvailable() {
        return lastMeasureModeCAvailable;
    }

    /**
     * @param lastMeasureModeCAvailable the lastMeasureModeCAvailable to set
     */
    public void setLastMeasureModeCAvailable(boolean lastMeasureModeCAvailable) {
        this.lastMeasureModeCAvailable = lastMeasureModeCAvailable;
    }

    /**
     * @return the lastMeasureMode3AAvailable
     */
    public boolean isLastMeasureMode3AAvailable() {
        return lastMeasureMode3AAvailable;
    }

    /**
     * @param lastMeasureMode3AAvailable the lastMeasureMode3AAvailable to set
     */
    public void setLastMeasureMode3AAvailable(boolean lastMeasureMode3AAvailable) {
        this.lastMeasureMode3AAvailable = lastMeasureMode3AAvailable;
    }

    /**
     * @return the reportTypeAvailable
     */
    public boolean isReportTypeAvailable() {
        return reportTypeAvailable;
    }

    /**
     * @param reportTypeAvailable the reportTypeAvailable to set
     */
    public void setReportTypeAvailable(boolean reportTypeAvailable) {
        this.reportTypeAvailable = reportTypeAvailable;
    }

    /**
     * @return the sic
     */
    public int getSic() {
        return sic;
    }

    /**
     * @param sic the sic to set
     */
    public void setSic(int sic) {
        this.sic = sic;
    }

    /**
     * @return the sac
     */
    public int getSac() {
        return sac;
    }

    /**
     * @param sac the sac to set
     */
    public void setSac(int sac) {
        this.sac = sac;
    }

    /**
     * @return the measuredPosTHETA
     */
    public double getMeasuredPosTHETA() {
        return measuredPosTHETA;
    }

    /**
     * @param measuredPosTHETA the measuredPosTHETA to set
     */
    public void setMeasuredPosTHETA(double measuredPosTHETA) {
        this.measuredPosTHETA = measuredPosTHETA;
    }

    /**
     * @return the measuredPosRHO
     */
    public double getMeasuredPosRHO() {
        return measuredPosRHO;
    }

    /**
     * @param measuredPosRHO the measuredPosRHO to set
     */
    public void setMeasuredPosRHO(double measuredPosRHO) {
        this.measuredPosRHO = measuredPosRHO;
    }

    /**
     * @return the measured3DHeight
     */
    public double getMeasured3DHeight() {
        return measured3DHeight;
    }

    /**
     * @param measured3DHeight the measured3DHeight to set
     */
    public void setMeasured3DHeight(double measured3DHeight) {
        this.measured3DHeight = measured3DHeight;
    }

    /**
     * @return the lastMeasuredModeC
     */
    public ModeCCode getLastMeasuredModeC() {
        return lastMeasuredModeC;
    }

    /**
     * @param lastMeasuredModeC the lastMeasuredModeC to set
     */
    public void setLastMeasuredModeC(ModeCCode lastMeasuredModeC) {
        this.lastMeasuredModeC = lastMeasuredModeC;
    }

    /**
     * @return the lastMeasureMode3A
     */
    public ModeACode getLastMeasureMode3A() {
        return lastMeasureMode3A;
    }

    /**
     * @param lastMeasureMode3A the lastMeasureMode3A to set
     */
    public void setLastMeasureMode3A(ModeACode lastMeasureMode3A) {
        this.lastMeasureMode3A = lastMeasureMode3A;
    }

    /**
     * @return the reportType
     */
    public ReportType getReportType() {
        return reportType;
    }

    /**
     * @param reportType the reportType to set
     */
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
}