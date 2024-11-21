/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

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
public class Mode2CodeConfidenceIndicator {
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
    
    @XmlElement(name = "qb4")
    protected boolean qb4;
    
    @XmlElement(name = "qc1")
    protected boolean qc1;
    
    @XmlElement(name = "qc2")
    protected boolean qc2;
    
    @XmlElement(name = "qc4")
    protected boolean qc4;
    
    @XmlElement(name = "qd1")
    protected boolean qd1;
    
    @XmlElement(name = "qd2")
    protected boolean qd2;
    
    @XmlElement(name = "qd4")
    protected boolean qd4;

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

    /**
     * @return the qb4
     */
    public boolean isQb4() {
        return qb4;
    }

    /**
     * @param qb4 the qb4 to set
     */
    public void setQb4(boolean qb4) {
        this.qb4 = qb4;
    }

    /**
     * @return the qc1
     */
    public boolean isQc1() {
        return qc1;
    }

    /**
     * @param qc1 the qc1 to set
     */
    public void setQc1(boolean qc1) {
        this.qc1 = qc1;
    }

    /**
     * @return the qc2
     */
    public boolean isQc2() {
        return qc2;
    }

    /**
     * @param qc2 the qc2 to set
     */
    public void setQc2(boolean qc2) {
        this.qc2 = qc2;
    }

    /**
     * @return the qc4
     */
    public boolean isQc4() {
        return qc4;
    }

    /**
     * @param qc4 the qc4 to set
     */
    public void setQc4(boolean qc4) {
        this.qc4 = qc4;
    }

    /**
     * @return the qd1
     */
    public boolean isQd1() {
        return qd1;
    }

    /**
     * @param qd1 the qd1 to set
     */
    public void setQd1(boolean qd1) {
        this.qd1 = qd1;
    }

    /**
     * @return the qd2
     */
    public boolean isQd2() {
        return qd2;
    }

    /**
     * @param qd2 the qd2 to set
     */
    public void setQd2(boolean qd2) {
        this.qd2 = qd2;
    }

    /**
     * @return the qd4
     */
    public boolean isQd4() {
        return qd4;
    }

    /**
     * @param qd4 the qd4 to set
     */
    public void setQd4(boolean qd4) {
        this.qd4 = qd4;
    }
    
    public static int extract(byte [] bytes, int index, Mode2CodeConfidenceIndicator code) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        byte cByte = bytes[index++];
        
        code.setQa4((cByte & 0x08) > 0);
        code.setQa2((cByte & 0x04) > 0);
        code.setQa1((cByte & 0x02) > 0);
        code.setQb4((cByte & 0x01) > 0);
        
        cByte = bytes[index++];
        code.setQb2((cByte & 0x80) > 0);
        code.setQb1((cByte & 0x40) > 0);
        code.setQc4((cByte & 0x20) > 0);
        code.setQc2((cByte & 0x10) > 0);
        code.setQc1((cByte & 0x08) > 0);
        code.setQd4((cByte & 0x04) > 0);
        code.setQd2((cByte & 0x02) > 0);
        code.setQd1((cByte & 0x01) > 0);
        
        return 2;
    }
    
        
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sMode2 code confidence indicator", indent));
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
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Mode2CodeConfidenceIndicator || qa1: %s | qa2: %s | qa4: %s | qb1: %s | qb2: %s | qb4: %s | ");
        builder.append("qc1: %s | qc2: %s | qc4: %s | qd1: %s | qd2: %s | qd4: %s ]");
        return String.format(builder.toString(), qa1, qa2, qa4, qb1, qb2, qb4, qc1, qc2, qc4, qd1, qd2, qd4);
    }
    
}
