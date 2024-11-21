/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic;

import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.config.Point2f;
import static com.attech.adsb.client.graphic.Convertor.fromDeg2Rad;
import static com.attech.adsb.client.graphic.Convertor.fromRad2DegFloat;
import static com.attech.adsb.client.graphic.Convertor.toRadian;
import com.attech.asterix.cat21.entities.Position;
import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 *
 * @author andh
 */
public class Calculator {
    /**
     * 
     * @param unit
     * @return 
     */
    public static double getEarthRadial(MeasureUnit unit) {
        
        return unit == MeasureUnit.NM ? 3440.08516 : 6371;
    }
    
    /**
     * Calculate the next point by distance and bearing
     * @param point
     * @param angle
     * @param distance
     * @param unit
     * @return 
     */
    public static Point2f getNextPoint(Point2f point, double angle, double distance, MeasureUnit unit) {
        double d = distance;
        double R = getEarthRadial(unit);
        double lon1 = Convertor.fromDeg2Rad(point.getX());
        double lat1 = Convertor.fromDeg2Rad(point.getY());
        double brng = Convertor.fromDeg2Rad(angle);
        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));
        double lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));
        Point2f p2 = new Point2f((float) Convertor.fromRad2Deg(lon2), (float) Convertor.fromRad2Deg(lat2));
//        p2.y = (float) Convertor.fromRad2Deg(lat2);
//        p2.x = (float) Convertor.fromRad2Deg(lon2);
        return p2;
    }
    
    public static double getDistance(Point2f pos1, Point2f pos2, MeasureUnit unit) {
        double R = getEarthRadial(unit);
        double dLat = Convertor.fromDeg2Rad(pos2.getY() - pos1.getY());
        double dLon = Convertor.fromDeg2Rad(pos2.getX() - pos1.getX());

        /*
        System.out.println("dLat = " + dLat);
        System.out.println("Math.sin(dLat / 2) = " + Math.sin(dLat / 2));
        System.out.println("Math.cos(Convertor.fromDeg2Rad(pos1.y)) = " + Math.cos(Convertor.fromDeg2Rad(pos1.getY())));
        System.out.println("Math.cos(Convertor.fromDeg2Rad(pos2.y) = " + Math.cos(Convertor.fromDeg2Rad(pos2.getY())));
        System.out.println("Math.sin(dLon / 2) = " + Math.sin(dLon / 2));
        */
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Convertor.fromDeg2Rad(pos1.getY())) * Math.cos(Convertor.fromDeg2Rad(pos2.getY()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        // System.out.println("a = " + a);
        // double gt = Math.asin(a);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        /*
        System.out.println("Math.sqrt(a)= " + Math.sqrt(a));
        System.out.println("Math.sqrt(1 - a) = " + Math.sqrt(1 - a));
        System.out.println("c = " + c);
        System.out.println("Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) = " + Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        */
        double d = R * c;
        return d;
    }
    
    
    public static double getDistance(double lontitude1, double latitude1, double longtitude2, double latitude2, MeasureUnit unit) {
        double R = getEarthRadial(unit);
        double dLat = Convertor.fromDeg2Rad(latitude2 - latitude1); // double dLat = Convertor.fromDeg2Rad(pos2.getY() - pos1.getY());
        double dLon = Convertor.fromDeg2Rad(longtitude2 - lontitude1);

        /*
        System.out.println("dLat = " + dLat);
        System.out.println("Math.sin(dLat / 2) = " + Math.sin(dLat / 2));
        System.out.println("Math.cos(Convertor.fromDeg2Rad(pos1.y)) = " + Math.cos(Convertor.fromDeg2Rad(pos1.getY())));
        System.out.println("Math.cos(Convertor.fromDeg2Rad(pos2.y) = " + Math.cos(Convertor.fromDeg2Rad(pos2.getY())));
        System.out.println("Math.sin(dLon / 2) = " + Math.sin(dLon / 2));
        */
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Convertor.fromDeg2Rad(latitude1)) * Math.cos(Convertor.fromDeg2Rad(latitude2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        // System.out.println("a = " + a);
        // double gt = Math.asin(a);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        /*
        System.out.println("Math.sqrt(a)= " + Math.sqrt(a));
        System.out.println("Math.sqrt(1 - a) = " + Math.sqrt(1 - a));
        System.out.println("c = " + c);
        System.out.println("Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) = " + Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        */
        double d = R * c;
        return d;
    }
    
    public static double angleBetween(Point2f pos1, Point2f pos2) {
        //Convert input values to radians
        double lat1, lat2, long1, long2;

        lat1 = Convertor.fromDeg2Rad(pos1.getY());
        long1 = Convertor.fromDeg2Rad(pos1.getX());
        lat2 = Convertor.fromDeg2Rad(pos2.getY());
        long2 = Convertor.fromDeg2Rad(pos2.getX());

        double deltaLong = long2 - long1;

        double y = Math.sin(deltaLong) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLong);
        double bearing = Math.atan2(y, x);
        double b = convertToBearing(Convertor.fromRad2Deg(bearing));
        return b;
    }

    public static double convertToBearing(double deg) {
        return (deg + 360) % 360;
    }
    
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
   
    public static Point2f calculateLocation(Point2f rootPoint, double distance, double angle, MeasureUnit unit) {

        /*
	* latitude  = x
	* longtitude = y
         */
//        Point2f p1 = new Point2f();
//        p1.y = rootPoint.y;
//        p1.x = rootPoint.x;

        final double d = distance;
        // double R = 6371;
        final double R = getEarthRadial(unit);
//        double brng;

//        double lat2, lon2;
        final double rootLatitude = toRadian(rootPoint.getLatitude());
        final double rootLongtitude = toRadian(rootPoint.getLongtitude());

        final double brng = fromDeg2Rad(angle);

        final double lat2 = Math.asin(Math.sin(rootLatitude) * Math.cos(d / R) + Math.cos(rootLatitude) * Math.sin(d / R) * Math.cos(brng));
        final double lon2 = rootLongtitude + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(rootLatitude), Math.cos(d / R) - Math.sin(rootLatitude) * Math.sin(lat2));

        return new Point2f((float) lat2, (float)lon2);
//        p2.latitude = (float) rad2deg(lat2);
//        p2.longtitude = (float) rad2deg(lon2);

//        return (p2);
    }
    
    public static Point2f calculateLocation(double longtitude, double latitude, double distance, double angle, MeasureUnit unit) {

        /*
	* latitude  = x
	* longtitude = y
         */
//        Point2f p1 = new Point2f();
//        p1.y = rootPoint.y;
//        p1.x = rootPoint.x;

        final double d = distance;
        // double R = 6371;
        final double R = getEarthRadial(unit);
//        double brng;

//        double lat2, lon2;
        final double rootLatitude = toRadian(latitude);
        final double rootLongtitude = toRadian(longtitude);

        final double brng = fromDeg2Rad(angle);

        final double lat2 = Math.asin(Math.sin(rootLatitude) * Math.cos(d / R) + Math.cos(rootLatitude) * Math.sin(d / R) * Math.cos(brng));
        final double lon2 = rootLongtitude + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(rootLatitude), Math.cos(d / R) - Math.sin(rootLatitude) * Math.sin(lat2));

        return new Point2f( Convertor.fromRad2Deg(lon2), Convertor.fromRad2Deg(lat2));
//        p2.latitude = (float) rad2deg(lat2);
//        p2.longtitude = (float) rad2deg(lon2);

//        return (p2);
    }
    
    public static Position CalculateWgs84(double rho, double theta, float originLat, float originLon){
        Position pos = new Position();
        double φ1 = fromDeg2Rad(originLat);
        double λ1 = fromDeg2Rad(originLon);
        double θ = fromDeg2Rad(theta);
        double δ = rho / getEarthRadial(MeasureUnit.KM);
        // tinh toan (radian)
        double φ2 = Math.asin(Math.sin(φ1) * Math.cos(δ)
                + Math.cos(φ1) * Math.sin(δ) * Math.cos(θ));
        double λ2 = λ1 + Math.atan2(Math.sin(θ) * Math.sin(δ) * Math.cos(φ1),
                Math.cos(δ) - Math.sin(φ1) * Math.sin(φ2));
        //doi ve degrees
        float lon = (fromRad2DegFloat(λ2) + 540) % 360 - 180;
        float lat = fromRad2DegFloat(φ2);
        
        pos.setLatitude(lat);
        pos.setLongtitude(lon);
        return pos;
    }
    
}
