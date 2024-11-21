/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.config.Point2f;
import java.text.DecimalFormat;
//import javax.media.opengl.GL;
//import javax.media.opengl.glu.GLU;

/**
 *
 * @author Tran Duc
 */
public class ConvertCordinate {
  
//    public static float tox(float lon) {
//        float x;
//        x = lon * 100.0f;
//        x = x - (Parameter.centerLong * 100);
//        return (x);
//    }
//    
//     public static float toLong(float x) {
//        float f;
//        //x = lon * 100.0f;
//        f = x / 100 + Parameter.centerLong;
//        return (f);
//    }
//    
//     public static float toLong2(float x) {
//        float f;
//        //x = lon * 100.0f;
//        f = x / 100 + Parameter.centerLongSecond;
//        return (f);
//    }
//
//     public static float toy(float lat) {
//        float y;
//        y = lat * 100.0f;
//        y = y - (Parameter.centerLat * 100);
//        return (y);
//    }
//    
//     public static float toLat(float y) {
//        float f;
//        //x = lon * 100.0f;
//        f = y / 100 + Parameter.centerLat;
//        return (f);
//    }
//     
//     public static float toLat2(float y) {
//        float f;
//        //x = lon * 100.0f;
//        f = y / 100 + Parameter.centerLatSecond;
//        return (f);
//    }
//
//     public static OPenGLCord tinhToaDoWindowToOpenGL(GLU glu, int x, int y, GLParam glparam) {
//        OPenGLCord p = new OPenGLCord();
//        double[] objPos = new double[3];
//        glu.gluUnProject((double) x, (double) glparam.viewport[3] - y, 0.0, glparam.modelview, 0, glparam.projection, 0, glparam.viewport, 0, objPos, 0);
//        p.glx = (float) objPos[0];
//        p.gly = (float) objPos[1];
//        return (p);
//    }
//    
//     public static OPenGLCord tinhToaDoWindowToOpenGLSecond(GLU glu, int x, int y, GLParam glparam) {
//        OPenGLCord p = new OPenGLCord();
//        double[] objPos = new double[3];
//        glu.gluUnProject((double) x, (double) glparam.viewportSecond[3] - y, 0.0, glparam.modelviewSecond, 0, glparam.projectionSecond, 0, glparam.viewportSecond, 0, objPos, 0);
//        p.glx = (float) objPos[0];
//        p.gly = (float) objPos[1];
//        return (p);
//    }
//
//     public static WGS84Position convertDegToDecimal(WGS84Position p) {
//        float lat = (float) p.latitude;
//        float lon = (float) p.longtitude;
//
//        int deg, min, sec;
//        deg = (int) lat / 10000;
//        min = (int) lat / 100 - deg * 100;
//        sec = (int) lat % 100;
//
//        //   System.out.println("Doi LAT " + p.Latitude + " == " + deg + ":" + min + ":" + sec);
//
//        WGS84Position n = new WGS84Position();
//
//        n.latitude = (float) ((min * 60) + sec) / 3600 + deg;
//
//        deg = (int) lon / 10000;
//        min = (int) lon / 100 - deg * 100;
//        sec = (int) lon % 100;
//
//        n.longtitude = (float) ((min * 60) + sec) / 3600 + deg;
//        return (n);
//    }
//
//     public static void getRectOfOpenGL(GL gl) {
//        gl.glGetIntegerv(GL.GL_VIEWPORT, glparam.viewport, 0);
//        gl.glGetDoublev(GL.GL_MODELVIEW_MATRIX, glparam.modelview, 0);
//        gl.glGetDoublev(GL.GL_PROJECTION_MATRIX, glparam.projection, 0);
//    }
//   
//
//     public static GLPosition openGLToScreen(GL gl, float x, float y, float z) {
//        double[] win = new double[3];
//        GLU glu = new GLU();
//        GLPosition p = new GLPosition();
//        getRectOfOpenGL(gl);
//        glu.gluProject(x, y, z, glparam.modelview, 0, glparam.projection, 0, glparam.viewport, 0, win, 0);
//        p.GLx = win[0];
//        p.GLy = glparam.viewport[3] - win[1];
//        return p;
//    }
//
//
//    public static float convertDegHMSLatitudeToOpenGLy(float val) {
//        float ret, ret1;
//        int deg, min, sec;
//        deg = (int) val / 10000;
//        min = (int) val / 100 - deg * 100;
//        sec = (int) val % 100;
//        ret = (float) ((min * 60) + sec) / 3600 + deg;
//        ret1 = (float) ret * 100 - (float) (Parameter.centerLat * 100);
//        return (ret1);
//    }
//
//
//    public static GLPosition convertDegHMSToOpenWindowCord(GL gl, WGS84Position p84) {
//        GLPosition p;
//        float x, y, z;
//        x = convertDegHMSLongitudeToOpenGLx((float) p84.longtitude);
//        y = convertDegHMSLatitudeToOpenGLy((float) p84.latitude);
//        p = openGLToScreen(gl, x, y, 100);
//
//        return p;
//    }
//
//
//    public static GLPosition convertWGS84DecToOpenWindowCord(GL gl, WGS84Position p84) {
//        GLPosition p;
//        float x, y, z;
//        x = tox((float) p84.longtitude);
//        y = toy((float) p84.latitude);
//        p = openGLToScreen(gl, x, y, 100);
//
//        return p;
//    }
//    

    public static Point2f convertDegHMSToDecimal(String Lat, String Long) {
        Point2f pos = new Point2f();
        float deg, min, sec;
        deg = Float.parseFloat(Lat.split(" ")[0]);
        min = Float.parseFloat(Lat.split(" ")[1]);
        sec = Float.parseFloat(Lat.split(" ")[2]);
        pos.y = deg + ((min * 60) + sec) / 3600;

        deg = Float.parseFloat(Long.split(" ")[0]);
        min = Float.parseFloat(Long.split(" ")[1]);
        sec = Float.parseFloat(Long.split(" ")[2]);
        pos.x = deg + ((min * 60) + sec) / 3600;
        return pos;
    }    
    
    public static String convertLatDecimalToDegHMSNoSpace(float val) {

        DecimalFormat df1 = new DecimalFormat("###");
        String s1;

        int sec = (int) Math.round(val * 3600);
        int deg = sec / 3600;
        sec = Math.abs(sec % 3600);
        int min = sec / 60;
        sec %= 60;
        //s1 = df1.format(deg) + " " + df1.format(min) + " " + df1.format(sec);
        s1 = df1.format(deg);
        if (df1.format(deg).length() == 1) {
            s1 = "0" + df1.format(deg);
        }
        if (df1.format(min).length() == 1) {
            s1 += "0" + df1.format(min);
        } else {
            s1 += df1.format(min);
        }
        if (df1.format(sec).length() == 1) {
            s1 += "0" + df1.format(sec);
        } else {
            s1 += df1.format(sec);
        }
        return s1;

    }
//    
//    

    public static Point2f convertScreenToWGS84(Point2f p) {
        Point2f result = new Point2f();
        float x = p.getX()/100;
        float y = p.getY()/100;
        result.setX(x);
        result.setY(y);
        return result;
    }
    
    public static Point2f convertWGS84ToScreen(Point2f p) {
        Point2f result = new Point2f();
        float x = p.getX()*100;
        float y = p.getY()*100;
        result.setX(x);
        result.setY(y);
        return result;
    }
    
    public static Point2f convertWGS84ToScreen10(Point2f p) {
        Point2f result = new Point2f();
        float x = p.getX()*10;
        float y = p.getY()*10;
        result.setX(x);
        result.setY(y);
        return result;
    }
    
    public static String convertLatDecimalToDegHMS(float val) {
        DecimalFormat df1 = new DecimalFormat("###");
        String s1;
        int sec = (int) Math.round(val * 3600);
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
    
    public static String convertLatDecimalToDegHMSDisplay(float val) {
        float absolute = Math.abs(val);
        int deg = (int) Math.floor(val);
        float minutesNotTruncated =  (absolute - deg) * 60;
        int min = (int) Math.floor(minutesNotTruncated);
        int sec = (int) Math.floor((minutesNotTruncated - min) * 60);
        return String.format("LAT: %s° %s' %s\" N", df1.format(deg), df2.format(min), df2.format(sec));
    }

    private static DecimalFormat df1 = new DecimalFormat("###");
    private static  DecimalFormat df2 = new DecimalFormat("00");
        
    public static String convertLongDecimalToDegHMS(float val) {
        DecimalFormat df1 = new DecimalFormat("###");
        DecimalFormat df2 = new DecimalFormat("00");
        String s1;
        int sec = (int) Math.round(val * 3600);
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
    
    public static String convertLongDecimalToDegHMSDisplay(float val) {
        float absolute = Math.abs(val);
        int deg = (int) Math.floor(val);
        float minutesNotTruncated = (absolute - deg) * 60;
        int min = (int) Math.floor(minutesNotTruncated);
        int sec = (int) Math.floor((minutesNotTruncated - min) * 60);
        return String.format("LON: %s° %s' %s\" E", df1.format(deg), df2.format(min), df2.format(sec));  
    }

//    public static float convertDegHMSLongitudeToOpenGLx(float val) {
//        float ret, ret1;
//        int deg, min, sec;
//        deg = (int) val / 10000;
//        min = (int) val / 100 - deg * 100;
//        sec = (int) val % 100;
//        ret = (float) ((min * 60) + sec) / 3600 + deg;
//        ret = convertDegree2Decimal(val);
//        ret1 = (float) ret * 100 - (float) (Parameter.centerLong * 100);
//        return (ret1);
//    }

    public static float convertDegree2Decimal(float val) {
        float ret;
        int deg, min, sec;
        deg = (int) val / 10000;
        min = (int) val / 100 - deg * 100;
        sec = (int) val % 100;
        ret = (float) ((min * 60) + sec) / 3600 + deg;
        return (ret);
    }

    public static float convertDegHMSLatitudeToDecimal(float val) {
        float ret;
        int deg, min, sec;
        deg = (int) val / 10000;
        min = (int) val / 100 - deg * 100;
        sec = (int) val % 100;
        ret = (float) ((min * 60) + sec) / 3600 + deg;
        return (ret);
    }

    public static float convertDegHMSToDecimal(float val) {
        float ret;
        int deg, min;
        float sec;
        deg = (int) val / 10000;
        min = (int) val / 100 - deg * 100;
        sec = val % 100;
        ret = (float) ((min * 60) + sec) / 3600 + deg;

        return (ret);
    }
    
//    /*
//    * use this method
//    */
//    public static float convertWGS84DToOpenGL(float val, float offset) {
//        return (val - offset) * 100;
//    }
//
//    public static float convertWGS84LongitudeDecimalOpenGLx(float val) {
//        float ret;
//        ret = (float) val * 100 - (float) (Parameter.centerLong * 100);
//        return (ret);
//    }
//
//    public static float convertWGS84LongitudeDecimalOpenGLx2(float val) {
//        float ret;
//        ret = (float) val * 100 - (float) (Parameter.centerLongSecond * 100);
//        return (ret);
//    }
//
//    public static float convertWGS84LatitudeDecimalOpenGLy(float val) {
//        float ret;
//        ret = (float) val * 100 - (float) (Parameter.centerLat * 100);
//        return (ret);
//    }
//    
//    public static float convertWGS84LatitudeDecimalOpenGLy2(float val) {
//        float ret;
//        ret = (float) val * 100 - (float) (Parameter.centerLatSecond * 100);
//        return (ret);
//    }
}