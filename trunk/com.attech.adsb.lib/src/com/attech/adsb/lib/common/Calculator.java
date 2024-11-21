/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

/**
 *
 * @author andh
 */
public class Calculator {
    
    
    public static double distanceBetween(double longtitude1, double latitude1, double longtitude2, double latitude2) {
        
        double R = 3440.08516;
        double dLat = fromDeg2Rad(latitude2 - latitude1);
        double dLon = fromDeg2Rad(longtitude2 - longtitude1);

        /*
        System.out.println("dLat = " + dLat);
        System.out.println("Math.sin(dLat / 2) = " + Math.sin(dLat / 2));
        System.out.println("Math.cos(Convertor.fromDeg2Rad(pos1.y)) = " + Math.cos(Convertor.fromDeg2Rad(pos1.y)));
        System.out.println("Math.cos(Convertor.fromDeg2Rad(pos2.y) = " + Math.cos(Convertor.fromDeg2Rad(pos2.y)));
        System.out.println("Math.sin(dLon / 2) = " + Math.sin(dLon / 2));
        */
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(fromDeg2Rad(latitude1)) * Math.cos(fromDeg2Rad(latitude2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        //System.out.println("a = " + a);
        double gt = Math.asin(a);
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
    
    public static double fromDeg2Rad(double val) {
        return (Math.PI / 180) * val;
    }

   
}
