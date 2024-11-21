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
@XmlRootElement(name = "Mode3ACodeConfidenceIndicator")
@XmlAccessorType(XmlAccessType.NONE)
public class Mode3ACodeConfidenceIndicator extends Mode2CodeConfidenceIndicator {
    
    public static int extract(byte [] bytes, int index, Mode3ACodeConfidenceIndicator code) {
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
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[Mode3ACodeConfidenceIndicator || qa1: %s | qa2: %s | qa4: %s | qb1: %s | qb2: %s | qb4: %s | ");
        builder.append("qc1: %s | qc2: %s | qc4: %s | qd1: %s | qd2: %s | qd4: %s ]");
        return String.format(builder.toString(), qa1, qa2, qa4, qb1, qb2, qb4, qc1, qc2, qc4, qd1, qd2, qd4);
    }

    @Override
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sMode3A code confidence indicator", indent));
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
