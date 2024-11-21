/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

/**
 *
 * @author an
 */
public class CommunicationCapacityAndFlightStatus {
    private int com;
    private int stat;
    private int si;
    private int mssc;
    private int arc;
    private int aic;
    private int b1a;
    private int b1b;

    /**
     * @return the com
     */
    public int getCom() {
        return com;
    }

    /**
     * @param com the com to set
     */
    public void setCom(int com) {
        this.com = com;
    }

    /**
     * @return the stat
     */
    public int getStat() {
        return stat;
    }

    /**
     * @param stat the stat to set
     */
    public void setStat(int stat) {
        this.stat = stat;
    }

    /**
     * @return the si
     */
    public int getSi() {
        return si;
    }

    /**
     * @param si the si to set
     */
    public void setSi(int si) {
        this.si = si;
    }

    /**
     * @return the mssc
     */
    public int getMssc() {
        return mssc;
    }

    /**
     * @param mssc the mssc to set
     */
    public void setMssc(int mssc) {
        this.mssc = mssc;
    }

    /**
     * @return the arc
     */
    public int getArc() {
        return arc;
    }

    /**
     * @param arc the arc to set
     */
    public void setArc(int arc) {
        this.arc = arc;
    }

    /**
     * @return the aic
     */
    public int getAic() {
        return aic;
    }

    /**
     * @param aic the aic to set
     */
    public void setAic(int aic) {
        this.aic = aic;
    }

    /**
     * @return the b1a
     */
    public int getB1a() {
        return b1a;
    }

    /**
     * @param b1a the b1a to set
     */
    public void setB1a(int b1a) {
        this.b1a = b1a;
    }

    /**
     * @return the b1b
     */
    public int getB1b() {
        return b1b;
    }

    /**
     * @param b1b the b1b to set
     */
    public void setB1b(int b1b) {
        this.b1b = b1b;
    }
    
    public static int extract(byte [] bytes, int index, CommunicationCapacityAndFlightStatus status) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte cbyter = bytes[index++];
        //System.out.printf("Communication status: %s %n", Integer.toHexString(cbyter & 0xFF));
        status.setCom(Byter.extract(cbyter, 5, 3));
        status.setStat(Byter.extract(cbyter, 2, 3));
        status.setSi(Byter.extract(cbyter, 1));
        
        cbyter = bytes[index++];
        // System.out.printf("Communication status: %s %n", Integer.toHexString(cbyter & 0xFF));
        status.setMssc(Byter.extract(cbyter, 7));
        status.setArc(Byter.extract(cbyter, 6));
        status.setAic(Byter.extract(cbyter, 5));
        status.setB1a(Byter.extract(cbyter, 4));
        status.setB1b(Byter.extract(cbyter, 0, 4));
        
        return 2;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sCommunication capacity and flight status", indent));
        System.out.println(String.format("\t%sCOM: %s", indent, com));
        System.out.println(String.format("\t%sSTAT: %s", indent, stat));
        System.out.println(String.format("\t%sSI: %s", indent, si));
        System.out.println(String.format("\t%sMSSC: %s", indent, mssc));
        System.out.println(String.format("\t%sARC: %s", indent, arc));
        System.out.println(String.format("\t%sAIC: %s", indent, aic));
        System.out.println(String.format("\t%sB1A: %s", indent, b1a));
        System.out.println(String.format("\t%sB1B: %s", indent, b1b));
    }
    
    @Override
    public String toString() {
        //String indent = Common.getLevel(1);
        return (String.format("COM: %s | STAT: %s | SI: %s | MSSC: %s | ARC: %s | AIC: %s | B1A: %s | B1B: %s", 
                com, stat, si, mssc, arc, aic, b1a, b1b));
    }
}
