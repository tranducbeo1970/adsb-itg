/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Convertor;
import java.awt.Point;
import java.util.List;
//import javax.media.opengl.GL;


enum DistanceType {

    Miles, Meters, Kilometers
};

/**
 *
 * @author Administrator
 */
public class Distance {
    
    private static final int R = 6371;

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::
    //::
    //::
    //::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//    public static double pointToLineDistance(GL gl, WGS84Position A, WGS84Position B, WGS84Position P) {
//        WGS84Position pos;
//        double bearing;
//        bearing = Distance.Bearing(A, B);
//        WGS84Position des = Distance.Destination(P, 60, bearing - 90.0f);
//        pos = FunctionOpenGL.intersection(A, B, P, des);
//
//        
//        gl.glColor3f(0.6f, 0.0f, 0.6f);
//        gl.glBegin(GL.GL_POINTS);
//        gl.glVertex3f(ConvertCordinate.convertWGS84LongitudeDecimalOpenGLx((float) pos.longtitude), ConvertCordinate.convertWGS84LatitudeDecimalOpenGLy((float) pos.latitude), 0.0f);
//        gl.glEnd();
//        
//        if(A.longtitude < B.longtitude ) {
//            if (pos.longtitude > B.longtitude) {
//                pos = B;
//            } else if (pos.longtitude < A.longtitude) {
//                pos = A;
//            }
//        } else if (A.longtitude > B.longtitude)  {
//            if (pos.longtitude > A.longtitude) {
//                pos = A;
//            } else if (pos.longtitude < B.longtitude) {
//                pos = B;
//            }
//            
//        }
//                       
//        if(A.latitude < B.latitude) {
//            if(pos.latitude > B.latitude) {
//                pos = B;
//            } else if(pos.latitude < A.latitude){
//                pos = A;
//            }
//        } else if(A.latitude > B.latitude){
//            if(pos.latitude > A.latitude) {
//                pos = A;
//            } else if(pos.latitude < B.latitude){
//                pos = B;
//            }
//        }
//        
//        
//        gl.glColor3f(0.6f, 0.0f, 0.6f);
//        FunctionOpenGL.lines(gl, P, pos, GL.GL_LINES,Layer.layer4);
//
//        double d = Distance.DistanceWGS84(P, pos);
//        return d;
//    }
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //::
    //::
    //::
    //::
    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//    static public double DistanceWGS84(WGS84Position pos1, WGS84Position pos2) {
//        double R = 6371;
//        double dLat = toRadian(pos2.latitude - pos1.latitude);
//        double dLon = toRadian(pos2.longtitude - pos1.longtitude);
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(toRadian(pos1.latitude)) * Math.cos(toRadian(pos2.latitude))
//                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//        //double gt = Math.asin(a);
//
//        //double c = 2 * Math.Asin(Math.Min(1, Math.Sqrt(a)));
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double d = R * c;
//        return (d);
//
//    }

//    static public double DistanceWGS84(Position pos1, Position pos2, DistanceType type) {
//        double R = (type == DistanceType.Miles) ? 3440 : 6371;
//        double dLat = toRadian(pos2.Latitude - pos1.Latitude);
//
//        double dLon = toRadian(pos2.Longitude - pos1.Longitude);
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(toRadian(pos1.Latitude)) * Math.cos(toRadian(pos2.Latitude))
//                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//
//        double gt = Math.asin(a);
//
//        //double c = 2 * Math.Asin(Math.Min(1, Math.Sqrt(a)));
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double d = R * c;
//        return d;
//    }

//    static public double DistanceWGS84(WGS84Position pos1, WGS84Position pos2, DistanceType type) {
//        double R = (type == DistanceType.Miles) ? 3440 : 6371;
//        double dLat = toRadian(pos2.latitude - pos1.latitude);
//
//        double dLon = toRadian(pos2.longtitude - pos1.longtitude);
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(toRadian(pos1.latitude)) * Math.cos(toRadian(pos2.latitude))
//                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//
//        double gt = Math.asin(a);
//
//        //double c = 2 * Math.Asin(Math.Min(1, Math.Sqrt(a)));
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double d = R * c;
//        return d;
//    }
    
    static public double DistancePoint2f(Point2f pos1, Point2f pos2) {
        double R = 6371;
        double dLat = toRadian(pos2.getLatitude() - pos1.getLatitude());
        double dLon = toRadian(pos2.getLongtitude() - pos1.getLongtitude());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(toRadian(pos1.getLatitude())) * Math.cos(toRadian(pos2.getLatitude()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return (d);

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
    static public Point2f Destination(Point2f pos1, double d, double angle) {
      
        double brng = Math.toRadians(angle);

        pos1 = ConvertCordinate.convertScreenToWGS84(pos1);
        
        double lat1, lon1, lat2, lon2;
        lat1 = Math.toRadians(pos1.y);
        lon1 = Math.toRadians(pos1.x);

        lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));
        lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));

        Point2f p2 = new Point2f();
        p2.y = (float) Math.toDegrees(lat2);
        p2.x = (float) Math.toDegrees(lon2);
        
        p2 = ConvertCordinate.convertWGS84ToScreen(p2);

        return (p2);
    }
    
     public static Point2f DestinationWGS84(Point2f pos1, double d, double angle) {
      
        double brng = Math.toRadians(angle);

//        pos1 = ConvertCordinate.convertScreenToWGS84(pos1);
        
        double lat1, lon1, lat2, lon2;
        lat1 = Math.toRadians(pos1.y);
        lon1 = Math.toRadians(pos1.x);

        lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));
        lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));

        Point2f p2 = new Point2f();
        p2.y = (float) Math.toDegrees(lat2);
        p2.x = (float) Math.toDegrees(lon2);
        
        p2 = ConvertCordinate.convertWGS84ToScreen(p2);

        return (p2);
    }
    
    
            
    static public Point2f DestinationSPV(Point2f pos1, double km, double angle) {

        Point2f p1 = new Point2f();
        p1.y = pos1.y;
        p1.x = pos1.x;

        double d = km;
        double R = 6371;
        double brng;

        double lat1, lon1, lat2, lon2;
        lat1 = toRadian(pos1.y);
        lon1 = toRadian(pos1.x);

        brng = deg2rad(angle);

        lat2 = Math.asin(Math.sin(lat1) * Math.cos(d / R) + Math.cos(lat1) * Math.sin(d / R) * Math.cos(brng));
        lon2 = lon1 + Math.atan2(Math.sin(brng) * Math.sin(d / R) * Math.cos(lat1), Math.cos(d / R) - Math.sin(lat1) * Math.sin(lat2));

        Point2f p2 = new Point2f();
        p2.y = (float) rad2deg(lat2);
        p2.x = (float) rad2deg(lon2);

        return (p2);
    }
           
    static public double Bearing(Point2f pos1, Point2f pos2) {        
        pos1 = Convertor.fromOpenglToWgs84(pos1);
        pos2 = Convertor.fromOpenglToWgs84(pos2);
        //Convert input values to radians
        double x1, x2, y1, y2;

        y1 = toRadian(pos1.y/100);
        x1 = toRadian(pos1.x/100);
        y2 = toRadian(pos2.y/100);
        x2 = toRadian(pos2.x/100);
        
        double deltaX = x2 - x1;

        double y = Math.sin(deltaX) * Math.cos(y2);
        double x = Math.cos(y1) * Math.sin(y2) - Math.sin(y1) * Math.cos(y2) * Math.cos(deltaX);
        double bearing = Math.atan2(y, x);
        double b = ConvertToBearing(RadToDeg(bearing));
        return b;
    }       
    
    static public boolean isPointIsInside(Point2f pos, List<Point2f> Polygon) {
        float GLx = pos.getLongtitude();
        float GLy = pos.getLatitude();
        int m, n = Polygon.size() - 1;
        boolean isInside = false;

        float mapGLx[] = new float[n + 1];
        float mapGLy[] = new float[n + 1];
        for (int k = 0; k < Polygon.size(); k++) {
            mapGLx[k] = Polygon.get(k).getLongtitude();
            mapGLy[k] = Polygon.get(k).getLatitude();
        }
        for (m = 0; m < mapGLx.length; m++) {
            if ((mapGLy[m] < GLy && mapGLy[n] >= GLy || mapGLy[n] < GLy && mapGLy[m] >= GLy) && (mapGLx[m] <= GLx || mapGLx[n] <= GLx)) {
                isInside ^= (mapGLx[m] + (GLy - mapGLy[m]) / (mapGLy[n] - mapGLy[m]) * (mapGLx[n] - mapGLx[m]) < GLx);
            }
            n = m;
        }
        return isInside;
    }
    
    /**
     * https://stackoverflow.com/questions/1119451/how-to-tell-if-a-line-intersects-a-polygon-in-c
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return 
     */
    public static boolean isLineIntersection(Point2f start1, Point2f end1, Point2f start2, Point2f end2) {
        float denom = ((end1.x - start1.x) * (end2.y - start2.y)) - ((end1.y - start1.y) * (end2.x - start2.x));
        //  AB & CD are parallel 
        if (denom == 0) {
            return false;
        }
        float numer = ((start1.y - start2.y) * (end2.x - start2.x)) - ((start1.x - start2.x) * (end2.y - start2.y));
        float r = numer / denom;
        float numer2 = ((start1.y - start2.y) * (end1.x - start1.x)) - ((start1.x - start2.x) * (end1.y - start1.y));
        float s = numer2 / denom;
//        // Find intersection point
//        PointF result = new PointF();
//        result.X = start1.X + (r * (end1.X - start1.X));
//        result.Y = start1.Y + (r * (end1.Y - start1.Y));
//
//        return result;
        return !((r < 0 || r > 1) || (s < 0 || s > 1));
    }
    
    public static boolean isLineCrossPolygon(Point2f start1, Point2f end1, List<Point2f> polygon) {
        for (int i = 0; i < polygon.size(); i++) {
            int j = i + 1;
            if (j == polygon.size()) j = 0;
            if (isLineIntersection(start1, end1, polygon.get(i), polygon.get(j))) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean  isLineIntersectionCircle(Point2f pointA, Point2f pointB, Point2f center, double radius) {
        double baX = pointB.x - pointA.x;
        double baY = pointB.y - pointA.y;
        double caX = center.x - pointA.x;
        double caY = center.y - pointA.y;

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return false;
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Point2f p1 = new Point2f(pointA.x - baX * abScalingFactor1, pointA.y - baY * abScalingFactor1);
        boolean inside1 = isPointInsideLine(pointA, pointB, p1);
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return inside1;
        }
        Point2f p2 = new Point2f(pointA.x - baX * abScalingFactor2, pointA.y - baY * abScalingFactor2);
        boolean inside2 = isPointInsideLine(pointA, pointB, p2);
        return inside1 || inside2;
    }
    
    private static double distanceOf2Points(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2));
    }
    
    private static double distanceOf2Points(Point2f p1, Point2f p2) {
        return Math.sqrt(Math.pow(p1.y-p2.y, 2) + Math.pow(p1.x-p2.x, 2));
    }
    
    private static boolean isPointInsideLine(Point2f p1, Point2f p2, Point2f p3) {
        double dLine = distanceOf2Points(p1, p2);
        double d1 = distanceOf2Points(p1, p3);
        double d2 = distanceOf2Points(p2, p3);
        return !(d1 > dLine || d2 > dLine);
    }

    public static double ConvertToBearing(double deg) {
        return (deg + 360) % 360;
    }

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

// EOF //