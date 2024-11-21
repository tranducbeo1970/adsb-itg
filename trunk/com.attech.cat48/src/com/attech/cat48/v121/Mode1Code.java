/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Mode2")
@XmlAccessorType(XmlAccessType.NONE)
public class Mode1Code {
    
    @XmlAttribute(name = "not-validated")
    protected boolean notValidated;
    
    @XmlAttribute(name = "garbed")
    protected boolean garbedCode;
    
    @XmlAttribute(name = "from-local-tracker")
    protected boolean localTracker;
    
    @XmlAttribute(name = "value")
    private int value;

    /**
     * @return the notValidated
     */
    public boolean isNotValidated() {
        return notValidated;
    }

    /**
     * @param notValidated the notValidated to set
     */
    public void setNotValidated(boolean notValidated) {
        this.notValidated = notValidated;
    }

    /**
     * @return the garbedCode
     */
    public boolean isGarbedCode() {
        return garbedCode;
    }

    /**
     * @param garbedCode the garbedCode to set
     */
    public void setGarbedCode(boolean garbedCode) {
        this.garbedCode = garbedCode;
    }

    /**
     * @return the localTracker
     */
    public boolean isLocalTracker() {
        return localTracker;
    }

    /**
     * @param localTracker the localTracker to set
     */
    public void setLocalTracker(boolean localTracker) {
        this.localTracker = localTracker;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        String validatedS = this.notValidated ? "Not-validated" : "Validated";
        String garbedS = this.garbedCode ? "Garbed-code" : "Non-garbed-code";
        String smoothS = this.localTracker ? "Smoothed-local-track": "Not-smooth-local-track";
        return String.format("[Mode2Code || %s | %s | %s | Value: %s]", 
                                validatedS, 
                                garbedS, 
                                smoothS, 
                                this.getValue());
    }
    
    public void print(int level) {
        String validated = notValidated ? "Not validated (1)" : "Validated(0)";
        String garbed = garbedCode ? "Garbed (1)" : "Not garbed (0)";
        String localTrackers = localTracker ? "Local-tracker (1)" : "Transponder (0)";
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sMode2 Code", indent));
        System.out.println(String.format("\t%s%s", indent, validated));
        System.out.println(String.format("\t%s%s", indent, garbed));
        System.out.println(String.format("\t%s%s", indent, localTrackers));
        System.out.println(String.format("\t%svalue: %s", indent, value));
    }
    
    public static int extract(byte [] bytes, int index, Mode1Code code) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        
        boolean bVal = Byter.extract(currentByte, 0, 1) > 0;
        code.setNotValidated(bVal);
        
        bVal = Byter.extract(currentByte, 1, 1) > 0;
        code.setGarbedCode(bVal);
        
        bVal = Byter.extract(currentByte, 2, 1) > 0;
        code.setLocalTracker(bVal);
        
        int code2Value = Byter.extract(currentByte, 0, 5);
        int octalValue = Integer.parseInt(Integer.toOctalString(code2Value));
        code.setValue(octalValue);
        return 1;
    }
}
