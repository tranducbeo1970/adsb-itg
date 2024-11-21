/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Xpulse")
@XmlAccessorType(XmlAccessType.NONE)
public class Xpulse {
    
    @XmlAttribute(name = "mode3A")
    private boolean mode3A;
    
    @XmlAttribute(name = "modeC")
    private boolean modeC;
    
    @XmlAttribute(name = "mode2")
    private boolean mode2;

    /**
     * @return the mode3A
     */
    public boolean isMode3A() {
        return mode3A;
    }

    /**
     * @param mode3A the mode3A to set
     */
    public void setMode3A(boolean mode3A) {
        this.mode3A = mode3A;
    }

    /**
     * @return the modeC
     */
    public boolean isModeC() {
        return modeC;
    }

    /**
     * @param modeC the modeC to set
     */
    public void setModeC(boolean modeC) {
        this.modeC = modeC;
    }

    /**
     * @return the mode2
     */
    public boolean isMode2() {
        return mode2;
    }

    /**
     * @param mode2 the mode2 to set
     */
    public void setMode2(boolean mode2) {
        this.mode2 = mode2;
    }
    
    public static int extract(byte[] bytes, int index, Xpulse xpulse) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte b = bytes[index++];
        xpulse.setMode3A((b & 0x80)> 0);
        xpulse.setModeC((b & 0x20)> 0);
        xpulse.setMode2((b & 0x04)> 0);
        return 1;
    }
    
    @Override
    public String toString() {
        return String.format("[Xpulse || Mode3A: %s | ModeC: %s | Mode2: %s]", mode3A, modeC, mode2);
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sXpulse", indent));
        System.out.println(String.format("\t%sMode 3A: %s", indent, mode3A));
        System.out.println(String.format("\t%sMode C: %s", indent, modeC));
        System.out.println(String.format("\t%sMode 2: %s", indent, mode2));
    }
}
