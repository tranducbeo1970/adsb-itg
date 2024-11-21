/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic;

import com.jogamp.opengl.GL2;
import java.awt.Color;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Color")
@XmlAccessorType(XmlAccessType.NONE)
public class RGB {

    @XmlAttribute(name = "red")
    public float red;
    
    @XmlAttribute(name = "green")
    public float green;
    
    @XmlAttribute(name = "blue")
    public float blue;
    
    public RGB() {
    }
    
    public RGB(String nm) {
        Color color = Color.decode(nm.replace("#", "0x"));
        this.red = color.getRed() / 255.0f;
        this.blue = color.getBlue() / 255.0f;
        this.green = color.getGreen() / 255.0f;
    }

    public RGB(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public void setColor(GL2 gl) {
        gl.glColor3f(red, green, blue);
        
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }
    
    @Override
    public String toString() {
        return "#" + toString(red) + toString(green) + toString(blue);
    }
    
    private String toString(float value) {
        int v = Math.round(value * 255);
        String s = Integer.toHexString(v).toUpperCase();
        if (s.length() == 1) s = "0" + s;
        return s;
    }
}
