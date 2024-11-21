/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

/**
 *
 * @author andh
 */
public class Status {
    private boolean initialisation;
    private boolean combined;
    private boolean manoeuvring;
    private boolean doubtfulPlot2TrackAssociation;
    private int chain;
    private boolean ghostTrack;
    private boolean lastReport;

    /**
     * @return the initialization
     */
    public boolean isInitialisation() {
        return initialisation;
    }

    /**
     * @param initialisation the initialization to set
     */
    public void setInitialisation(boolean initialisation) {
        this.initialisation = initialisation;
    }

    /**
     * @return the combined
     */
    public boolean isCombined() {
        return combined;
    }

    /**
     * @param combinded the combined to set
     */
    public void setCombined(boolean combinded) {
        this.combined = combinded;
    }

    /**
     * @return the manoeuvring
     */
    public boolean isManoeuvring() {
        return manoeuvring;
    }

    /**
     * @param manoeuvring the manoeuvring to set
     */
    public void setManoeuvring(boolean manoeuvring) {
        this.manoeuvring = manoeuvring;
    }

    /**
     * @return the chain
     */
    public int getChain() {
        return chain;
    }

    /**
     * @param chain the chain to set
     */
    public void setChain(int chain) {
        this.chain = chain;
    }

    /**
     * @return the ghostTrack
     */
    public boolean isGhostTrack() {
        return ghostTrack;
    }

    /**
     * @param ghostTrack the ghostTrack to set
     */
    public void setGhostTrack(boolean ghostTrack) {
        this.ghostTrack = ghostTrack;
    }

    /**
     * @return the lastReport
     */
    public boolean isLastReport() {
        return lastReport;
    }

    /**
     * @param lastReport the lastReport to set
     */
    public void setLastReport(boolean lastReport) {
        this.lastReport = lastReport;
    }
    
    public static int extract(byte [] bytes, int index, Status status) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cByte = bytes[index++];
        // System.out.println("Track Status: " + Integer.toBinaryString(cByte));
        
        // bit 8
        status.setInitialisation((cByte & 0x80) > 0); 
        
        // bit 7
        status.setCombined((cByte & 0x40) > 0); 
        
        // bit 6
        status.setManoeuvring((cByte & 0x20) > 0); 
        
        // bit 5
        status.setDoubtfulPlot2TrackAssociation((cByte & 0x10) > 0);
        
        // bit 4
        status.setChain((cByte & 0x08) > 0 ? 2 : 1);
        
        // bit 2
        status.setGhostTrack((cByte & 0x02) > 0);
        
        boolean extended = (cByte & 0x01) > 0;
        if (!extended) return 1;
        
        if (!Byter.validateIndex(index, bytes.length, 1)) return 1;
        
        cByte = bytes[index++];
        status.setLastReport((cByte & 0x80) > 0); 
        extended = (cByte & 0x01) > 0;
        int count = 2;
        while (extended) {
            if (!Byter.validateIndex(index, bytes.length, 1)) return count;
            extended = (bytes[index++] & 0x01) > 0;
            count++;
        }
        return count;
    }

    /**
     * @return the doubtfulPlot2TrackAssociation
     */
    public boolean isDoubtfulPlot2TrackAssociation() {
        return doubtfulPlot2TrackAssociation;
    }

    /**
     * @param doubtfulPlot2TrackAssociation the doubtfulPlot2TrackAssociation to set
     */
    public void setDoubtfulPlot2TrackAssociation(boolean doubtfulPlot2TrackAssociation) {
        this.doubtfulPlot2TrackAssociation = doubtfulPlot2TrackAssociation;
    }
    
    @Override
    public String toString() {
        String initS = this.initialisation ? "Initialisation" : "Not-initialisation";
        String combines = this.combined ? "Combines" : "Not-combines";
        String manoeuvres = this.manoeuvring ? "Manoeuvring" : "Not-manoeuvring";
        String doubt = this.doubtfulPlot2TrackAssociation ? "Doubt" : "No-doubt";
        String ghost = this.ghostTrack ? "Ghost-track" : "Real-track";
        String report = this.lastReport ? "Last-report" : "Not-last-report";
        return String.format("[Status || %s | %s | %s | %s | Chain: %s | %s | %s ]", 
                                initS, 
                                combines, 
                                manoeuvres, 
                                doubt, 
                                this.chain, 
                                ghost, 
                                report);
    }
    
    public void print(int level) {
        String init = this.initialisation ? "Initialisation(1)" : "Not-initialisation(0)";
        String combine = this.combined ? "Combines(1)" : "Not-combines(0)";
        String manoeuvres = this.manoeuvring ? "Manoeuvring(1)" : "Not-manoeuvring(0)";
        String doubt = this.doubtfulPlot2TrackAssociation ? "Doubt(1)" : "No-doubt(0)";
        String ghost = this.ghostTrack ? "Ghost-track(1)" : "Real-track(0)";
        String report = this.lastReport ? "Last-report(1)" : "Not-last-report(0)";
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sStatus", indent));
        System.out.println(String.format("\t%s%s", indent, init));
        System.out.println(String.format("\t%s%s", indent, combine));
        System.out.println(String.format("\t%s%s", indent, manoeuvres));
        System.out.println(String.format("\t%s%s", indent, doubt));
        System.out.println(String.format("\t%s%s", indent, ghost));
        System.out.println(String.format("\t%s%s", indent, report));
        System.out.println(String.format("\t%s%s", indent, indent));
        
    }
    
}
