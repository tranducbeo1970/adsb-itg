/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat02.v10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dhan
 */
@XmlRootElement(name = "IntegerValue")
@XmlAccessorType(XmlAccessType.NONE)
public class IVal {
    
    @XmlAttribute(name = "value")
    private int value;

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
    
    public void set(int value) {
        this.value = value;
    }
    
    public int val() {
        return this.value;
    }
    
    public void print(int level, String cate) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%s%s: %s", indent, cate, value));
    }
}
