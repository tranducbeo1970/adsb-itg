/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat48.v121;

import java.nio.ByteBuffer;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andh
 */
@XmlRootElement(name = "CalculatedPolarVelocity")
@XmlAccessorType(XmlAccessType.NONE)
public class CalculatedPolarVelocity {
    
    @XmlAttribute(name = "speed")
    private double speed;
    
    @XmlAttribute(name = "heading")
    private double heading;

    /**
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return the heading
     */
    public double getHeading() {
        return heading;
    }

    /**
     * @param heading the heading to set
     */
    public void setHeading(double heading) {
        this.heading = heading;
    }
    
    @Override
    public String toString() {
        //return String.format("[CalculatedPolarVelocity || Speed: %s | Heading: %s]", this.speed, this.heading);
        return String.format("Speed: %.2f | Heading: %.4f", this.speed, this.heading);
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sCalculated Polar Velocity", indent));
        System.out.println(String.format("\t%sSpeed: %s", indent, this.speed));
        System.out.println(String.format("\t%sHeading: %s", indent, this.heading));
    }
    
    public static int extract(byte [] bytes, int index, CalculatedPolarVelocity polarVelocity) {
        if (!Byter.validateIndex(index, bytes.length, 4)) return -1;
        // System.out.printf("Velocity ");
        int value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        double dVal = (double) value * 0.22;
        polarVelocity.setSpeed(dVal);
        
        value = ByteBuffer.wrap(new byte[]{0x00, 0x00, bytes[index++], bytes[index++]}).getInt();
        dVal = (double) value * 0.0055;
        polarVelocity.setHeading(dVal);
        return 4;
    }
}
