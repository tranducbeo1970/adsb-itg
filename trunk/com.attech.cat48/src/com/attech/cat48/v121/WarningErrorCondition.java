/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

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
            byte b = bytes[index++];
            int value = Byter.extract(b, 0, 7);
            condition.add(value);
            count ++;
            if ((b & 0x01) > 0) break;
        }
        return count;
    }
    
    public String getWarningReason(int value) {
        switch (value) {
            case 0: return "(0) Not defined; never used.";
            case 1: return "(1) Multipath Reply (Reflection)";
            case 2: return "(2) Reply due to sidelobe interrogation/reception";
            case 3: return "(3) Split plot";
            case 4: return "(4) Second time around reply";
            case 5: return "(5) Angel";
            case 6: return "(6) Slow moving target correlated with road infrastructure (terrestrial vehicle)";
            case 7: return "(7) Fixed PSR plot";
            case 8: return "(8) Slow PSR target";
            case 9: return "(9) Low quality PSR plot";
            case 10: return "(10) Phantom SSR plot";
            case 11: return "(11) Non-Matching Mode-3/A Code";
            case 12: return "(12) Mode C code / Mode S altitude code abnormal value compared to the track";
            case 13: return "(13) Target in Clutter Area";
            case 14: return "(14) Maximum Doppler Response in Zero Filter";
            case 15: return "(15) Transponder anomaly detected";
            case 16: return "(16) Duplicated or Illegal Mode S Aircraft Address";
            case 17: return "(17) Mode S error correction applied";
            case 18: return "(18) Undecodable Mode C code / Mode S altitude code";
            case 19: return "(19) Birds";
            case 20: return "(20) Flock of Birds";
            case 21: return "(21) Mode 1 was present in original reply";
            case 22: return "(22) Mode 2 was present in original reply";
            case 23: return "(23) Plot potentially caused by Wind Turbine";
            default: return String.format("(%s) Unkown", value);
        }
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
        for (Integer val : this) {
            System.out.println(String.format("\t%s%s", indent, getWarningReason(val)));
        }
    }
}
