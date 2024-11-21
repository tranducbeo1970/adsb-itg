/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat01.v120;

import static com.attech.cat01.v120.Cat01Decoder.getComplementNumber;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "CartesianCoordinate")
@XmlAccessorType(XmlAccessType.NONE)
public class CartesianCoordinate {
    
    @XmlAttribute(name = "x")
    public double x;
    
    @XmlAttribute(name = "y")
    public double y;
    
    private int originX;
    private int originY;

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the originX
     */
    public int getOriginX() {
        return originX;
    }

    /**
     * @param originX the originX to set
     */
    public void setOriginX(int originX) {
        this.originX = originX;
    }

    /**
     * @return the originY
     */
    public int getOriginY() {
        return originY;
    }

    /**
     * @param originY the originY to set
     */
    public void setOriginY(int originY) {
        this.originY = originY;
    }
    
    @Override
    public String toString() {
        //return String.format("[CartesianCoordinate || x: %s | y:%s | originX: %s | originY: %s]", x, y, originX, originY);
        return String.format("x:%s | y:%s", x, y);
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sCartesian Coordinate", indent));
        System.out.println(String.format("\t%sX: %s", indent, this.x));
        System.out.println(String.format("\t%sY: %s", indent, this.y));
        System.out.println(String.format("\t%soriginX: %s", indent, this.originX));
        System.out.println(String.format("\t%soriginY: %s", indent, this.originY));
    }
    
    public static int extract(byte [] bytes, int index, CartesianCoordinate coordinate) {
        if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
        int x = getComplementNumber(new byte[]{bytes[index++], bytes[index++]});
        coordinate.setOriginX(x);
        
        int y = getComplementNumber(new byte[]{bytes[index++], bytes[index++]});
        coordinate.setOriginY(y);
        
        coordinate.calculate(0);
        return 4;
    }
    
    public void calculate(int scalingFactor) {
        x = this.originX * Math.pow(2, -6 + scalingFactor);
        y = this.originY * Math.pow(2, -6 + scalingFactor);
    }
}
