/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * I001/060
 * @author andh
 */
@XmlRootElement(name = "Mode2CodeConfidenceIndicator")
@XmlAccessorType(XmlAccessType.NONE)
public class Mode1CodeConfidenceIndicator {
    @XmlElement(name = "qa1")
    protected boolean qa1;
    
    @XmlElement(name = "qa2")
    protected boolean qa2;
    
    @XmlElement(name = "qa4")
    protected boolean qa4;
    
    @XmlElement(name = "qb1")
    protected boolean qb1;
    
    @XmlElement(name = "qb2")
    protected boolean qb2;
    
    /**
     * @return the qa1
     */
    public boolean isQa1() {
        return qa1;
    }

    /**
     * @param qa1 the qa1 to set
     */
    public void setQa1(boolean qa1) {
        this.qa1 = qa1;
    }

    /**
     * @return the qa2
     */
    public boolean isQa2() {
        return qa2;
    }

    /**
     * @param qa2 the qa2 to set
     */
    public void setQa2(boolean qa2) {
        this.qa2 = qa2;
    }

    /**
     * @return the qa4
     */
    public boolean isQa4() {
        return qa4;
    }

    /**
     * @param qa4 the qa4 to set
     */
    public void setQa4(boolean qa4) {
        this.qa4 = qa4;
    }

    /**
     * @return the qb1
     */
    public boolean isQb1() {
        return qb1;
    }

    /**
     * @param qb1 the qb1 to set
     */
    public void setQb1(boolean qb1) {
        this.qb1 = qb1;
    }

    /**
     * @return the qb2
     */
    public boolean isQb2() {
        return qb2;
    }

    /**
     * @param qb2 the qb2 to set
     */
    public void setQb2(boolean qb2) {
        this.qb2 = qb2;
    }

    public static int extract(byte [] bytes, int index, Mode1CodeConfidenceIndicator code) {
        if (!Byter.validateIndex(index, bytes.length, 1)) return -1;
        byte cByte = bytes[index++];
        
        code.setQa4((cByte & 0x10) > 0);
        code.setQa2((cByte & 0x08) > 0);
        code.setQa1((cByte & 0x04) > 0);
        code.setQb2((cByte & 0x02) > 0);
        code.setQb1((cByte & 0x01) > 0);
        
        return 1;
    }
        
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sMode2 code confidence indicator", indent));
        System.out.println(String.format("  %sqa1: %s", indent, qa1));
        System.out.println(String.format("  %sqa2: %s", indent, qa2));
        System.out.println(String.format("  %sqa4: %s", indent, qa4));
        System.out.println(String.format("  %sqb1: %s", indent, qb1));
        System.out.println(String.format("  %sqb2: %s", indent, qb2));
    }
}
