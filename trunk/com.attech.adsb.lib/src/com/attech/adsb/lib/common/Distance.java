/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

enum DistanceType {

    Miles, Meters, Kilometers
};
/**
 *
 * @author Tang Hai Anh
 */
public class Distance {
    
    static public double DistanceWGS84(WGS84Position pos1, WGS84Position pos2) {
        double R = 6371;
        double dLat = toRadian(pos2.latitude - pos1.latitude);
        double dLon = toRadian(pos2.longitude - pos1.longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(toRadian(pos1.latitude)) * Math.cos(toRadian(pos2.latitude))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double gt = Math.asin(a);

        //double c = 2 * Math.Asin(Math.Min(1, Math.Sqrt(a)));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return (d);

    }

    static public double DistanceWGS84(WGS84Position pos1, WGS84Position pos2, DistanceType type) {
        double R = (type == DistanceType.Miles) ? 3440 : 6371;
        double dLat = toRadian(pos2.latitude - pos1.latitude);

        double dLon = toRadian(pos2.longitude - pos1.longitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(toRadian(pos1.latitude)) * Math.cos(toRadian(pos2.latitude))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);


        double gt = Math.asin(a);

        //double c = 2 * Math.Asin(Math.Min(1, Math.Sqrt(a)));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d;
    }

    static public double GetDistanceBetweenPoints(double lat1, double long1, double lat2, double long2) {
        double distance = 0;

        double dLat = (lat2 - lat1) / 180 * Math.PI;
        double dLong = (long2 - long1) / 180 * Math.PI;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat2) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        //Calculate radius of earth
        // For this you can assume any of the two points.
        double radiusE = 6378135; // Equatorial radius, in metres
        double radiusP = 6356750; // Polar Radius

        //Numerator part of function
        double nr = Math.pow(radiusE * radiusP * Math.cos(lat1 / 180 * Math.PI), 2);
        //Denominator part of the function
        double dr = Math.pow(radiusE * Math.cos(lat1 / 180 * Math.PI), 2)
                + Math.pow(radiusP * Math.sin(lat1 / 180 * Math.PI), 2);
        double radius = Math.sqrt(nr / dr);

        //Calaculate distance in metres.
        distance = radius * c;
        return distance;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::
    //::http://www.cnblogs.com/luqingfei/archive/2007/09/13/891450.html
    //::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    static public WGS84Position Destination(WGS84Position pos1, double km, double angle) {

        WGS84Position p1 = new WGS84Position();
        p1.latitude = pos1.latitude;
        p1.longitude = pos1.longitude;

        double d = km;
        double R = 6371;
        double brng;

        double lat1, lon1, lat2, lon2;
        lat1 = toRadian(pos1.latitude);
        lon1 = toRadian(pos1.longitude);

        brng = deg2rad(angle);

        lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));
        lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));

        WGS84Position p2 = new WGS84Position();
        p2.latitude = (float) rad2deg(lat2);
        p2.longitude = (float) rad2deg(lon2);

        return (p2);
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::
    //::http://www.cnblogs.com/luqingfei/archive/2007/09/13/891450.html
    //:: Dung used
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::
    //::
    //::
    //::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    static public double Bearing(WGS84Position pos1, WGS84Position pos2) {
        //Convert input values to radians
        double lat1, lat2, long1, long2;

        lat1 = toRadian(pos1.latitude);
        long1 = toRadian(pos1.longitude);
        lat2 = toRadian(pos2.latitude);
        long2 = toRadian(pos2.longitude);

        double deltaLong = long2 - long1;

        double y = Math.sin(deltaLong) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLong);
        double bearing = Math.atan2(y, x);
        double b = ConvertToBearing(RadToDeg(bearing));
        return b;
    }

    static public double Bearing(Double longtitude1, Double latitude1 , Double longtitude2, Double latitude2) {
        //Convert input values to radians
        double lat1, lat2, long1, long2;

        lat1 = toRadian(latitude1);
        long1 = toRadian(longtitude1);
        lat2 = toRadian(latitude2);
        long2 = toRadian(longtitude2);

        double deltaLong = long2 - long1;

        double y = Math.sin(deltaLong) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLong);
        double bearing = Math.atan2(y, x);
        double b = ConvertToBearing(RadToDeg(bearing));
        return b;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::
    //::
    //::
    //::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static double ConvertToBearing(double deg) {
        return (deg + 360) % 360;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::
    //::
    //::
    //::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static double RadToDeg(double radians) {
        return radians * (180 / Math.PI);
    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::  This function converts decimal degrees to radians             :::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    static public double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::  This function converts radians to decimal degrees             :::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    static private double rad2deg(double rad) {
        return (rad / Math.PI * 180.0);
    }

    static private double toRadian(double val) {
        return (Math.PI / 180) * val;
    }
}
