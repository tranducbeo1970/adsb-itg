/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;


/**
 *
 * @author andh
 */
public class Convertor {
    
    public static int R1 = 6371;
    
        
    /**
     * 
     * @param point2f
     * @return 
     */
    public static Point2f fromWGS842OpenGL(Point2f point2f) {
        return new Point2f(point2f.getX() * 100, point2f.getY() * 100);
    }
    /**
     * 
     * @param x
     * @return 
     */
    public static float fromWGS842OpenGL(float x) {
        return x * 100;
    }
    
    public static float fromWGS842OpenGL(double x) {
        return (float) (x * 100);
    }
    
    public static Wgs84Coordinate fromDecimalToWgs84Coord(float val) {
        float absolute = Math.abs(val);
        int deg = (int) (val < 0 ?  -Math.floor(absolute) : Math.floor(absolute));
        float minutesNotTruncated =  Math.abs(absolute - Math.abs(deg)) * 60;
        int min = (int) Math.floor(minutesNotTruncated);
        int sec = (int) Math.ceil((minutesNotTruncated - min) * 60);
        return new Wgs84Coordinate(deg, min, sec);
    }
    
     public static float fromWgs84CoordToDecimal(int dec, int min, int sec, String locate) {
        float coord = (float) (dec * 1.0f + min / 60.0f + sec / 3600.0f);
        if (locate == null || locate.isEmpty()) return coord;
        switch (locate) {
            case "W":
            case "S":
                return -coord;
            default:
                return coord;
        }
    }
    
    /**
     * 
     * @param point2f
     * @return 
     */
    public static Point2f fromOpenGL2WGS84(Point2f point2f) {
        return new Point2f(point2f.getX() / 100, point2f.getY() / 100);
    }
    
    /**
     * 
     * @param rad
     * @return 
     */
    public static double fromRad2Deg(double rad) {
        return (rad / Math.PI * 180.0);
    }

    /**
     * 
     * @param val
     * @return 
     */
    public static double fromDeg2Rad(double val) {
        return (Math.PI / 180) * val;
    }
    
    
    public static double toRadian(double val) {
        return (Math.PI / 180) * val;
    }
    
}
