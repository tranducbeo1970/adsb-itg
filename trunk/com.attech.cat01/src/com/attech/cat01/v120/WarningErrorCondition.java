/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "WarningErrorCondition")
@XmlAccessorType(XmlAccessType.NONE)
public class WarningErrorCondition extends ArrayList<Integer> {
    
    public static int extract(byte [] bytes, int index, WarningErrorCondition condition ) {
        int count = 0;
        while (true) {
            if (!Byter.validateIndex(index, bytes.length, 1)) break;
            int b = bytes[index++] & 0xFF;
            // System.out.println("CON=" + Integer.toBinaryString(b & 0xFF));
            // System.out.println("CON=" + Integer.toBinaryString((b >> 1)));
            // System.out.println("CON=" + Integer.toBinaryString((b >> 1)));
            int value = (b >> 1) & 0xFF; //Byter.extract(b, 0, 7);
            condition.add(value);
            count ++;
            if ((b & 0x01) == 0) break;
        }
        return count;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[WarningErrorCondition | ");
        for (Integer str : this) {
            builder.append("|").append(str);
        }
        builder.append("]");
        return builder.toString();
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sWarning Error Condition", indent));
        for (Integer str : this) {
            System.out.println(String.format("\t%s%s", indent, str));
        }
    }
}
