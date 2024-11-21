/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.config;

import com.attech.adsb.client.common.Wgs84Coordinate;
import java.text.DecimalFormat;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Point")
@XmlAccessorType(XmlAccessType.NONE)
public class Point2f {

    /***
     * Y index (Decaster) Or  Latitude (WGS84)
     */
    @XmlAttribute(name="y")
    public float y;
    
     /***
     * X index (Decaster) Or  Longtitude (WGS84)
     */
    @XmlAttribute(name="x")
    public float x;
    
    @XmlAttribute(name="z")
    public Float z;
    
    @XmlAttribute(name="color")
    private String color;
    
    @XmlAttribute(name="lon-coord")
    private String lonCoord;
    
    @XmlAttribute(name="lat-coord")
    private String latCoord;
    
    public Point2f() {
    }
    
    public Point2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Point2f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Point2f(Point2f point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point2f(double longtitude, double latitude) {
        this.x = (float) longtitude;
        this.y = (float) latitude;
    }
    
    @Override
    public String toString() {
        return "lon "  +this.x + " lat " + this.y;
    }
        
    private static DecimalFormat df1 = new DecimalFormat("###");
    private static  DecimalFormat df2 = new DecimalFormat("00");
    
    public String convertLatToDegHMSDisplay() {
        float absolute = Math.abs(this.y);
        int deg = (int) Math.floor(this.y);
        float minutesNotTruncated =  (absolute - deg) * 60;
        int min = (int) Math.floor(minutesNotTruncated);
        int sec = (int) Math.floor((minutesNotTruncated - min) * 60);
        return String.format("LAT: %s° %s' %s\" N", df1.format(deg), df2.format(min), df2.format(sec));
    }
    
    public String convertLongToDegHMSDisplay() {
        float absolute = Math.abs(this.x);
        int deg = (int) Math.floor(this.x);
        float minutesNotTruncated = (absolute - deg) * 60;
        int min = (int) Math.floor(minutesNotTruncated);
        int sec = (int) Math.floor((minutesNotTruncated - min) * 60);
        return String.format("LON: %s° %s' %s\" E", df1.format(deg), df2.format(min), df2.format(sec));  
    }
    
    public String convertLongToDegHMS() {
        String s1;
        int sec = (int) Math.round(this.x * 3600);
        int deg = sec / 3600;
        sec = Math.abs(sec % 3600);
        int min = sec / 60;
        sec %= 60;       
        
        s1 = df1.format(deg) + " ";
        if (df1.format(deg).length() == 1) {
            s1 = "00" + df1.format(deg) + " ";
        }
        if (df1.format(deg).length() == 2) {
            s1 = "0" + df1.format(deg) + " ";
        }
        if (df1.format(min).length() == 1) {
            s1 += "0" + df1.format(min) + " ";
        } else {
            s1 += df1.format(min) + " ";
        }
        if (df1.format(sec).length() == 1) {
            s1 += "0" + df1.format(sec) + " ";
        } else {
            s1 += df1.format(sec) + " ";
        }
        return s1;
    }
    
    public String convertLatToDegHMS() {
        String s1;
        int sec = (int) Math.round(this.y * 3600);
        int deg = sec / 3600;
        sec = Math.abs(sec % 3600);
        int min = sec / 60;
        sec %= 60;        
               
        s1 = df1.format(deg) + " ";
        if (df1.format(deg).length() == 1) {
            s1 = "0" + df1.format(deg) + " ";
        }
        if (df1.format(min).length() == 1) {
            s1 += "0" + df1.format(min) + " ";
        } else {
            s1 += df1.format(min) + " ";
        }
        if (df1.format(sec).length() == 1) {
            s1 += "0" + df1.format(sec) + " ";
        } else {
            s1 += df1.format(sec) + " ";
        }
        return s1;
    }
    
    public void convertScreenToWGS84() {
        this.x = this.x/100;
        this.y = this.y/100;
    }
    
    public void convertWGS84ToScreen() {
        this.x = this.x*100;
        this.y = this.y*100;
    }
    
    public float convertLongScreenToWGS84() {
        return this.x/100;
    }
    
    public float convertLatScreenToWGS84() {
        return this.y/100;
    }
    
    public float convertLongWGS84ToScreen() {
        return this.x*100;
    }
    
    public float convertLatWGS84ToScreen() {
        return this.y*100;
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    
    public void set(Point2f p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }

    public void setX(float longtitude) { this.x = longtitude; }

    public float getY() { return y; }

    public void setY(float latitude) { this.y = latitude; }
    
    public void setLongtitude(float longtitude) {
	this.x = longtitude;
    }
    
    public float getLongtitude() {
	return this.x;
    }
    
    public void setLatitude(float latitude) {
	this.y = latitude;
    }
    
    public float getLatitude() {
	return this.y;
    }
    
    /**
     * @return the z
     */
    public Float getZ() {
	return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(Float z) {
	this.z = z;
    }

    /**
     * @return the color
     */
    public String getColor() {
	return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
	this.color = color;
    }

    public Point2f makeCopy() {
        return new Point2f(x, y);
    }
    
    public Point2f makeCopy(float paddingX, float paddingY) {
        return new Point2f(x + paddingX, y + paddingY);
    }
    
    
    /**
     * @return the lonCoord
     */
    public String getLonCoord() {
        return lonCoord;
    }

    /**
     * @param lonCoord the lonCoord to set
     */
    public void setLonCoord(String lonCoord) {
        this.lonCoord = lonCoord;
    }

    /**
     * @return the latCoord
     */
    public String getLatCoord() {
        return latCoord;
    }

    /**
     * @param latCoord the latCoord to set
     */
    public void setLatCoord(String latCoord) {
        this.latCoord = latCoord;
    }
    //</editor-fold>
      
}
