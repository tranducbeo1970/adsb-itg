/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asterix.cat21.util;


/**
 *
 * @author Andh
 */
public class Convertor {

    public double deg2Rad(double deg) {
        return deg * (Math.PI / 180);
    }
    public double rad2Deg(double rad) {
        return 180.0 * (rad / Math.PI);
    }
    public Wgs84Position getDestination(Wgs84Position pos1, double km, double angle) {

        double d = km;
        double R = 6371;
        double brng;

        double lat1, lon1, lat2, lon2;
        lat1 = deg2Rad(pos1.getLatitude());
        lon1 = deg2Rad(pos1.getLongtitude());

        brng = deg2Rad(angle);

        lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));
        lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));

        Wgs84Position p2 = new Wgs84Position(rad2Deg(lon2), rad2Deg(lat2));
        return (p2);
    }
    private static Convertor instance;
    public static Convertor newInstance() {
        if (instance == null) {
            instance = new Convertor();
        }
        return instance;
    }
}
