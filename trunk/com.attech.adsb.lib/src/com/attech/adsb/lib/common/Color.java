/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Color")
@XmlAccessorType(XmlAccessType.NONE)
public class Color {

    public float r;
    public float g;
    public float b;
    
    @XmlAttribute(name="code")
    private String code;
    
    public Color() {
    }
    
    public Color(String code) {
        this.code = code;
        decode(code);
    }
    
    private void decode(String code) {
        final java.awt.Color col = java.awt.Color.decode(code);
        r = col.getRed() / 255;
        g = col.getGreen() / 255;
        b = col.getBlue() / 255;
    }
    
    @Override
    public String toString() {
        return toString(r) + toString(g) + toString(b);
    }
    
    private String toString(float value) {
        int v = Math.round(value * 100 * 255);
        String s = Integer.toHexString(v).toUpperCase();
        if (s.length() == 1) s = "0" + s;
        return s;
    }

    public String getCode() { return code; }

    public void setCode(String code) {
        this.code = code;
        decode(code);
    }
}
