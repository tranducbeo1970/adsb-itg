/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat34.v127;

import java.nio.ByteBuffer;

/**
 *
 * @author hong
 */
public class DynamicWindow {
    private double rhoStart;
    private double rhoEnd;
    private double thetaStart;
    private double thetaEnd;

    /**
     * @return the rhoStart
     */
    public double getRhoStart() {
        return rhoStart;
    }

    /**
     * @param rhoStart the rhoStart to set
     */
    public void setRhoStart(double rhoStart) {
        this.rhoStart = rhoStart;
    }

    /**
     * @return the rhoEnd
     */
    public double getRhoEnd() {
        return rhoEnd;
    }

    /**
     * @param rhoEnd the rhoEnd to set
     */
    public void setRhoEnd(double rhoEnd) {
        this.rhoEnd = rhoEnd;
    }

    /**
     * @return the thetaStart
     */
    public double getThetaStart() {
        return thetaStart;
    }

    /**
     * @param thetaStart the thetaStart to set
     */
    public void setThetaStart(double thetaStart) {
        this.thetaStart = thetaStart;
    }

    /**
     * @return the thetaEnd
     */
    public double getThetaEnd() {
        return thetaEnd;
    }

    /**
     * @param thetaEnd the thetaEnd to set
     */
    public void setThetaEnd(double thetaEnd) {
        this.thetaEnd = thetaEnd;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sGeneric Polar Window", indent));
        System.out.println(String.format("\t%sRHO start: %s", indent, rhoStart));
        System.out.println(String.format("\t%sRHO end: %s", indent, rhoEnd));
        System.out.println(String.format("\t%sTHETA end: %s", indent, thetaStart));
        System.out.println(String.format("\t%sTHETA end: %s", indent, thetaEnd));
    }
    
    public static int extract(byte[] bytes, int index, DynamicWindow dynamicWindow) {
        if (!Byter.validateIndex(index, bytes.length, 6)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        double dvalue = (double) value / 128;
        dynamicWindow.setRhoStart(dvalue);
        
        value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        dvalue = (double) value / 128;
        dynamicWindow.setRhoEnd(dvalue);
        
        value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        dvalue = (double) value * 360 / Math.pow(2, 16);
        dynamicWindow.setThetaStart(dvalue);
        
         value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        dvalue = (double) value * 360 / Math.pow(2, 16);
        dynamicWindow.setThetaEnd(dvalue);
        
        return 6;
    }
}
