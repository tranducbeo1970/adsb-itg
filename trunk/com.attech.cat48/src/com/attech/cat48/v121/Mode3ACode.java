/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "Mode3A")
@XmlAccessorType(XmlAccessType.NONE)
public class Mode3ACode extends Mode2Code{
    
    /**
     * Extract mode 3A code from byte array
     * @param bytes (byte array data)
     * @param index (start index of the array)
     * @param code (stored object)
     * @return number of read byte
     */
    public static int extract(byte [] bytes, int index, Mode3ACode code){
        
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte currentByte = bytes[index++];
        
        boolean bVal = Byter.extract(currentByte, 7, 1) > 0;
        code.setNotValidated(bVal);
        
        bVal = Byter.extract(currentByte, 6, 1) > 0;
        code.setGarbedCode(bVal);
        
        bVal = Byter.extract(currentByte, 5, 1) > 0;
        code.setLocalTracker(bVal);
        
        int iVal = ((currentByte & 0x0F) << 8 ) | (bytes[index++] & 0xFF);
        iVal= Integer.parseInt(Integer.toOctalString(iVal));
        code.setValue(iVal);
        return 2;
    }
    
    @Override
    public String toString() {
        String validatedS = this.notValidated ? "Not-validated" : "Validated";
        String garbedS = this.garbedCode ? "Garbed-code" : "Non-garbed-code";
        String smoothS = this.localTracker ? "transponder": "local";
        return String.format("[Mode3ACode || %s | %s | %s | Value: %s]", 
                                validatedS, 
                                garbedS, 
                                smoothS, 
                                this.getValue());
    }
    
    @Override
    public void print(int level) {
        String validated = this.notValidated ? "Not validated(1)" : "Validated(0)";
        String garbed = this.garbedCode ? "Garbed(1)" : "Not garbed(0)";
        String smoothS = this.localTracker ? "from transponder(1)": "from local(0)";
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sMode3A Code", indent));
        System.out.println(String.format("\t%s%s", indent, validated));
        System.out.println(String.format("\t%s%s", indent, garbed));
        System.out.println(String.format("\t%s%s", indent, smoothS));
        System.out.println(String.format("\t%sCode: %s", indent, this.getValue()));
    }
}
