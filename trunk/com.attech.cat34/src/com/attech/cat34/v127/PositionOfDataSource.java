/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat34.v127;

/**
 *
 * @author andh
 */
public class PositionOfDataSource {
    private double height;
    private double latitude;
    private double longtitude;

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longtitude
     */
    public double getLongtitude() {
        return longtitude;
    }

    /**
     * @param longtitude the longtitude to set
     */
    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
    
    public static int extract(byte [] bytes, int index, PositionOfDataSource position) {
        if (!Byter.validateIndex(index, bytes.length, 8)) return -1;
        int value = (bytes[index++] & 0xFF) << 8 | (bytes[index++] & 0xFF);
        position.setHeight(value);
        
        value = Byter.getComplementNumber(new byte[]{bytes[index++], bytes[index++], bytes[index++]});
        position.setLatitude((double) value * 180 / Math.pow(2, 23));
        
        value = Byter.getComplementNumber(new byte[]{bytes[index++], bytes[index++], bytes[index++]});
        position.setLongtitude((double) value * 180 / Math.pow(2, 23));
        
        return 8;
    }
    
    public void print(int level) {
        String indent = Common.getLevel(level);
        System.out.println(String.format("%sPosition Of Data Source", indent));
        System.out.println(String.format("\t%sHeight: %s (m)", indent, height));
        System.out.println(String.format("\t%sLatitude: %s (degree)", indent, latitude));
        System.out.println(String.format("\t%sLongtitude: %s", indent, longtitude));
    }
    
    @Override
    public String toString(){
        return String.format("Height: %s (m) | Latitude: %s (degree) | Longtitude: %s", height, latitude, longtitude);
    }
    
}
