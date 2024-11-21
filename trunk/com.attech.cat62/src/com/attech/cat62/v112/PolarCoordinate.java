/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat62.v112;

import java.nio.ByteBuffer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "PolarCoordinate")
@XmlAccessorType(XmlAccessType.NONE)
public class PolarCoordinate {
    
    @XmlAttribute(name = "rho")
    private double rho;
    
    @XmlAttribute(name = "theta")
    private double theta;

    /**
     * @return the rho
     */
    public double getRho() {
        return rho;
    }

    /**
     * @param rho the rho to set
     */
    public void setRho(double rho) {
        this.rho = rho;
    }

    /**
     * @return the theta
     */
    public double getTheta() {
        return theta;
    }

    /**
     * @param theta the theta to set
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }
    
    public static int extract(byte[] bytes, int index, PolarCoordinate polarCoordinate) {
        if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        double dVal = (double) value / 256;
        polarCoordinate.setRho(dVal);
        
        value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        dVal = (double) (value * 360 / Math.pow(2, 16));
        polarCoordinate.setTheta(dVal);
        return 4;
    }
    
    @Override
    public String toString() {
        return String.format("[PolarCoordinate || RHO: %s | Theta : %s]", this.rho, this.theta);
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sPolar Coordinate", indent));
        System.out.println(String.format("\t%srho: %s", indent, rho));
        System.out.println(String.format("\t%stheta: %s", indent, theta));
    }
    
    
}
