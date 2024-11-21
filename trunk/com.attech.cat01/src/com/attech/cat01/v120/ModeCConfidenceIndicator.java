/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "ModeCConfidenceIndicator")
@XmlAccessorType(XmlAccessType.NONE)
public class ModeCConfidenceIndicator extends Mode2CodeConfidenceIndicator {
    
    @XmlAttribute(name = "validated")
    private boolean validated;
    
    @XmlAttribute(name = "garbed")
    private boolean garbedCode;
    
    @XmlElement(name = "a1")
    private boolean a1;
    
    @XmlElement(name = "a2")
    private boolean a2;
    
    @XmlElement(name = "a4")
    private boolean a4;
    
    @XmlElement(name = "b1")
    private boolean b1;
    
    @XmlElement(name = "b2")
    private boolean b2;
    
    @XmlElement(name = "b4")
    private boolean b4;
    
    @XmlElement(name = "c1")
    private boolean c1;
    
    @XmlElement(name = "c2")
    private boolean c2;
    
    @XmlElement(name = "c4")
    private boolean c4;
    
    @XmlElement(name = "d1")
    private boolean d1;
    
    @XmlElement(name = "d2")
    private boolean d2;
    
    @XmlElement(name = "d4")
    private boolean d4;

    /**
     * @return the validated
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * @param validated the validated to set
     */
    public void setValidated(boolean validated) {
        this.validated = validated;
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
     * @return the a1
     */
    public boolean isA1() {
        return a1;
    }

    /**
     * @param a1 the a1 to set
     */
    public void setA1(boolean a1) {
        this.a1 = a1;
    }

    /**
     * @return the a2
     */
    public boolean isA2() {
        return a2;
    }

    /**
     * @param a2 the a2 to set
     */
    public void setA2(boolean a2) {
        this.a2 = a2;
    }

    /**
     * @return the a4
     */
    public boolean isA4() {
        return a4;
    }

    /**
     * @param a4 the a4 to set
     */
    public void setA4(boolean a4) {
        this.a4 = a4;
    }

    /**
     * @return the b1
     */
    public boolean isB1() {
        return b1;
    }

    /**
     * @param b1 the b1 to set
     */
    public void setB1(boolean b1) {
        this.b1 = b1;
    }

    /**
     * @return the b2
     */
    public boolean isB2() {
        return b2;
    }

    /**
     * @param b2 the b2 to set
     */
    public void setB2(boolean b2) {
        this.b2 = b2;
    }

    /**
     * @return the b4
     */
    public boolean isB4() {
        return b4;
    }

    /**
     * @param b4 the b4 to set
     */
    public void setB4(boolean b4) {
        this.b4 = b4;
    }

    /**
     * @return the c1
     */
    public boolean isC1() {
        return c1;
    }

    /**
     * @param c1 the c1 to set
     */
    public void setC1(boolean c1) {
        this.c1 = c1;
    }

    /**
     * @return the c2
     */
    public boolean isC2() {
        return c2;
    }

    /**
     * @param c2 the c2 to set
     */
    public void setC2(boolean c2) {
        this.c2 = c2;
    }

    /**
     * @return the c4
     */
    public boolean isC4() {
        return c4;
    }

    /**
     * @param c4 the c4 to set
     */
    public void setC4(boolean c4) {
        this.c4 = c4;
    }

    /**
     * @return the d1
     */
    public boolean isD1() {
        return d1;
    }

    /**
     * @param d1 the d1 to set
     */
    public void setD1(boolean d1) {
        this.d1 = d1;
    }

    /**
     * @return the d2
     */
    public boolean isD2() {
        return d2;
    }

    /**
     * @param d2 the d2 to set
     */
    public void setD2(boolean d2) {
        this.d2 = d2;
    }

    /**
     * @return the d4
     */
    public boolean isD4() {
        return d4;
    }

    /**
     * @param d4 the d4 to set
     */
    public void setD4(boolean d4) {
        this.d4 = d4;
    }
    
    public static int extract(byte [] bytes, int index, ModeCConfidenceIndicator code) {
        if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
        byte cByte = bytes[index++];
        code.setValidated((cByte & 0x80) == 0);
        code.setGarbedCode((cByte & 0x40) > 0);
        code.setC1((cByte & 0x08) > 0);
        code.setA1((cByte & 0x04) > 0);
        code.setC2((cByte & 0x02) > 0);
        code.setA2((cByte & 0x01) > 0);
        
        cByte = bytes[index++];
        code.setC4((cByte & 0x80) > 0);
        code.setA4((cByte & 0x40) > 0);
        code.setB1((cByte & 0x20) > 0);
        code.setD1((cByte & 0x10) > 0);
        code.setB2((cByte & 0x08) > 0);
        code.setD2((cByte & 0x04) > 0);
        code.setB4((cByte & 0x02) > 0);
        code.setD4((cByte & 0x01) > 0);
        
        cByte = bytes[index++];
        code.setQc1((cByte & 0x08) > 0);
        code.setQa1((cByte & 0x04) > 0);
        code.setQc2((cByte & 0x02) > 0);
        code.setQa2((cByte & 0x01) > 0);
        
        cByte = bytes[index++];
        code.setQc4((cByte & 0x80) > 0);
        code.setQa4((cByte & 0x40) > 0);
        code.setQb1((cByte & 0x20) > 0);
        code.setQd1((cByte & 0x10) > 0);
        code.setQb2((cByte & 0x08) > 0);
        code.setQd2((cByte & 0x04) > 0);
        code.setQb4((cByte & 0x02) > 0);
        code.setQd4((cByte & 0x01) > 0);
        
        return 4;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[ ModeCConfidenceIndicator|| validated: %s | garbed-code: %s | a1: %s | a2: %s | a4 : %s");
        builder.append("b1: %s | b2: %s | b4 : %s | c1: %s | c2: %s | c4 : %s | d1: %s | d2: %s | d4 : %s]");
        return String.format(builder.toString(), validated, garbedCode, a1, a2, a4, b1, b2, b4, c1, c2, c4, d1, d2, d4);
    }
    
    @Override
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sModeC code confidence indicator", indent));
        System.out.println(String.format("  %sqa1: %s", indent, qa1));
        System.out.println(String.format("  %sqa2: %s", indent, qa2));
        System.out.println(String.format("  %sqa4: %s", indent, qa4));
        System.out.println(String.format("  %sqb1: %s", indent, qb1));
        System.out.println(String.format("  %sqb2: %s", indent, qb2));
        System.out.println(String.format("  %sqb4: %s", indent, qb4));
        System.out.println(String.format("  %sqc1: %s", indent, qc1));
        System.out.println(String.format("  %sqc2: %s", indent, qc2));
        System.out.println(String.format("  %sqc4: %s", indent, qc4));
        System.out.println(String.format("  %sqd1: %s", indent, qd1));
        System.out.println(String.format("  %sqd2: %s", indent, qd2));
        System.out.println(String.format("  %sqd4: %s", indent, qd4));
    }
}
