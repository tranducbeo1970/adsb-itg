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
 * @author dhan
 */
@XmlRootElement(name = "DoubleValue")
@XmlAccessorType(XmlAccessType.NONE)
public class DVal {
    @XmlAttribute(name = "value")
    private double value;

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
    
    public double val() {
        return value;
    }
    
    public void print(int level, String cate) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%s%s: %s", indent, cate, value));
    }
}
