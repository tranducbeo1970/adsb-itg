/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat62.v112;

/**
 *
 * @author andh
 */
public class WGS84Coordinate {
    public double lng;
    public double lat;
    private static double delta = 180 / Math.pow(2, 25);
    
    public WGS84Coordinate() {
    }
    
    public WGS84Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    
    public static int decode(byte [] bytes, int index, WGS84Coordinate wgs84Coord) {
        if (!Byter.validateIndex(index, bytes.length, 8)) return -1;
        
        byte[] latBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++], bytes[index++]};
        int value = Byter.getComplementNumber(latBytes);
        wgs84Coord.setLat((double) (value * delta));  // lat = val * 180 / 2^25

        latBytes = new byte[]{bytes[index++], bytes[index++], bytes[index++], bytes[index++]};
        value = Byter.getComplementNumber(latBytes);
        wgs84Coord.setLng((double) (value * delta)); // lng = val * 180 / 2^25
        return 8;
    }
    
    public byte[] encode() {
        byte [] result = new byte [8];
        int lngVal = (int) (lng * 33554432 / 180);
        int latVal = (int) (lat * 33554432 / 180);
        byte [] ba = Byter.getComplementNumberInByte(latVal);
        System.arraycopy(ba, 0, result, 0, 4);
        ba = Byter.getComplementNumberInByte(lngVal);
        System.arraycopy(ba, 0, result, 4, 4);
        return result;
    }
    
    
    @Override
    public String toString() {
        return "Longtitude: " + this.getLng() + " Latitude:" + this.getLat();
    }

    /**
     * @return the Longtitude
     * 
     */
    public double getLng() {
        return lng;
    }

    /**
     * @param lng the longtitude to set
     */
    public void setLng(double lng) {
        this.lng = lng;
    }

    /**
     * @return the latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * @param lat the latitude to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }
}
