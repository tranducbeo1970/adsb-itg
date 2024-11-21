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
public class ReportType {
    private int reportType;
    private boolean simulated;
    private boolean reportFromFieldMonitor;
    private boolean testTargetReport;

    public static int extract(byte [] bytes, int index, ReportType code){
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cbyte = bytes[index++];
        code.setReportType(cbyte >> 5 & 0x07);
        code.setSimulated((cbyte & 0x10) > 0);
        code.setReportFromFieldMonitor((cbyte & 0x08) > 0);
        code.setReportFromFieldMonitor((cbyte & 0x04) > 0);
        return 1;
    }
    
    /**
     * @return the reportType
     */
    public int getReportType() {
        return reportType;
    }

    /**
     * @param reportType the reportType to set
     */
    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    /**
     * @return the simulated
     */
    public boolean isSimulated() {
        return simulated;
    }

    /**
     * @param simulated the simulated to set
     */
    public void setSimulated(boolean simulated) {
        this.simulated = simulated;
    }

    /**
     * @return the reportFromFieldMonitor
     */
    public boolean isReportFromFieldMonitor() {
        return reportFromFieldMonitor;
    }

    /**
     * @param reportFromFieldMonitor the reportFromFieldMonitor to set
     */
    public void setReportFromFieldMonitor(boolean reportFromFieldMonitor) {
        this.reportFromFieldMonitor = reportFromFieldMonitor;
    }

    /**
     * @return the testTargetReport
     */
    public boolean isTestTargetReport() {
        return testTargetReport;
    }

    /**
     * @param testTargetReport the testTargetReport to set
     */
    public void setTestTargetReport(boolean testTargetReport) {
        this.testTargetReport = testTargetReport;
    }
    
    
    
}
