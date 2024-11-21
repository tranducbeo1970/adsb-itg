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
 * @author anbk
 */
@XmlRootElement(name = "DataSourceID")
@XmlAccessorType(XmlAccessType.NONE)
public class DataSourceID {
    
    @XmlAttribute(name = "sac")
    private int sac;
    
    @XmlAttribute(name = "sic")
    private int sic;

    /** SAC
     * @return  **/
    public int getSac() {
        return sac;
    }

    public void setSac(int sac) {
        this.sac = sac;
    }

    /** SIC
     * @return  **/
    public int getSic() {
        return sic;
    }

    public void setSic(int sic) {
        this.sic = sic;
    }
    
    /**
     * extract data
     * @param bytes
     * @param index
     * @param sourceId
     * @return 
     */
    public static int extract(byte[] bytes, int index, DataSourceID sourceId) {
        if (!Byter.validateIndex(index, bytes.length, 2)) return -1;
        sourceId.setSac(bytes[index++] & 0xFF);
        sourceId.setSic(bytes[index++] & 0xFF);
        return 2;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sDataSource ID", indent));
        System.out.println(String.format("  %sSIC: %s", indent, this.sic));
        System.out.println(String.format("  %sSAC: %s", indent, this.sac));
    }
    
    @Override
    public String toString() {
        return "SAC:" + this.sac + " SIC:" + this.sic;
    }
}
