/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic;

import com.attech.adsb.client.common.Wgs84Coordinate;
import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.PointX;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import java.awt.Color;

/**
 *
 * @author andh
 */
public class Convertor {
    
    public static GLParam glparam = new GLParam();
    
    public static int R1 = 6371;
    private static final double[] win = new double[3];
    private static final double[] objPos = new double[3];
    
        
    /**
     * Get earth radius in unit
     * @param unit
     * @return 
     */
    public static double getRadius(MeasureUnit unit) {
        if (unit == MeasureUnit.NM) return R1/1.852;
        return R1;
    }
    
    public static String fromColorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()).toUpperCase();
    }
    

//    /**
//     * Convert from screen position into OpenGL
//     * @param glu
//     * @param point
//     * @return 
//     */
//    public static Point2f fromScreen2OpenGL(GLU glu, Point point) {
//        return fromScreen2OpenGL(glu, point, ContextObject.instance().getGlParam());
//    }
//
    /**
     * fromScreen2OriginalOpenGL
     * @param glu
     * @param point
     * @return 
     */
//    public static Point2f fromScreen2OriginalOpenGL(GLUgl2 glu, Point2f point) {
//        return fromScreen2OpenGL(glu, point, Painter.context.getGlParam());
//    }

    /**
     * 
     * @param glu
     * @param point
     * @param glparam
     * @return 
     */
    public static Point2f fromScreen2OpenGL(GLUgl2 glu, Point2f point, GLParam glparam) {
        double[] objPos = new double[3];
        glu.gluUnProject( point.getX(), (double) glparam.getViewport()[3] - point.getY(), 0.0,
                glparam.getModelview(), 0, glparam.getProjection(), 0,
                glparam.getViewport(), 0, objPos, 0);
        Point2f p = new Point2f((float) objPos[0], (float) objPos[1]);
        return (p);
    }
    
    
    
    
    /////////////////////////////////
    
    
//    public static Point2f openGLToScreen(GLUgl2 glu, Point2f point, GLParam glparam) {
//        double[] objPos = new double[3];
//        glu.gluProject( point.getX(), point.getY(), 0.0,glparam.getModelview(), 0, glparam.getProjection(), 0, glparam.getViewport(), 0, objPos, 0);         
//        Point2f p = new Point2f((float) objPos[0], (float) objPos[1]);
//        return (p);
//    }   
    
    public static Point2f openGLToScreen(GL2 gl, float x, float y, float z) {
        double[] win = new double[3];
        GLU glu = new GLU();
        Point2f p = new Point2f();
        getRectOfOpenGL(gl);
        glu.gluProject(x, y, z, glparam.getModelview(), 0, glparam.getProjection(), 0, glparam.getViewport(), 0, win, 0);
        p.x = (float) win[0];
        p.y = (float) win[1];
        return p;
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
    
//    public static Wgs84Coordinate convertLongDecimalToDegHMSDisplay(float val) {
//        float absolute = Math.abs(val);
//        int deg = (int) Math.floor(val);
//        float minutesNotTruncated = (absolute - deg) * 60;
//        int min = (int) Math.floor(minutesNotTruncated);
//        int sec = (int) Math.floor((minutesNotTruncated - min) * 60);
//        return new Wgs84Coordinate(deg, min, sec);
////        return String.format("LON: %s° %s' %s\" E", df1.format(deg), df2.format(min), df2.format(sec));  
//    }
    
    public static void getRectOfOpenGL(GL2 gl) {
        gl.glGetIntegerv(GL2.GL_VIEWPORT, glparam.getViewport(), 0);
        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, glparam.getModelview(), 0);
        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, glparam.getProjection(), 0);
    }
    
    public static void getRectOfOpenGL2(GL2 gl) {
//        gl.glGetIntegerv(GL2.GL_VIEWPORT, glparam.viewportSecond, 0);
//        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, glparam.modelviewSecond, 0);
//        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, glparam.projectionSecond, 0);
    }

//
//    /**
//     * 
//     * @param glu
//     * @param point
//     * @return 
//     */
//    public static Point fromOpenGL2Screen(GLU glu, Point2f point) {
//        return Convertor.fromOpenGL2Screen(glu, point, ContextObject.instance().getGlParam());
//    }
//
//    /**
//     * 
//     * @param glu
//     * @param point
//     * @param glParam
//     * @return 
//     */
//    public static Point fromOpenGL2Screen(GLU glu, Point2f point, GLParam glParam) {
//        double[] win = new double[3];
//        glu.gluProject(point.getX(), point.getY(), 0, glParam.getModelview(), 0,
//                glParam.getProjection(), 0,
//                glParam.getViewport(), 0, win, 0);
//        Point p = new Point((int) Math.round(win[0]), (int) Math.round(glParam.getViewport()[3] - Math.round(win[1])));
//        return p;
//    }
//    
    
//    public static Point2f fromOpenglToWgs84(Point2f point2f) {
//        return new Point2f(point2f.getX() / 100, point2f.getY() / 100);
//    }
    
    
    
    
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
     * @param pointX
     * @return 
     */
    public static PointX fromWGS842OpenGL(PointX pointX) {
        return new PointX (pointX.getX() * 100, pointX.getY() * 100, pointX.name);
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
    
    /**
     * 
     * @param point2f
     * @return 
     */
    public static Point2f fromOpenglToWgs84(Point2f point2f) {
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
    
    public static float fromRad2DegFloat(double val) {
        return (float) ((180 / Math.PI) *  val);
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
    
//    /**
//     * Convert distance in KM or NM to radial
//     * @param distance
//     * @param unit
//     * @return 
//     */
//    public static double fromDistance2Rad(double distance, MeasureUnit unit) {
//        double radius = getRadius(unit);
//        return (distance*180)/(Math.PI*radius);
//    }
//    
//    /**
//     * Convert distance to OpenLG distance
//     * @param distance
//     * @param unit
//     * @return 
//     */
//    public static double fromDistance2OpenGL(double distance, MeasureUnit unit) {
//        final double radius = getRadius(unit);
//        return ((distance*180)/(Math.PI*radius)) * 100;
//    }
//    
//    /**
//     * Calculate position in loadIdentity mode
//     * @param glu
//     * @param point
//     * @return 
//     */
//    public static Point2f calculateIdentityPosition(GLUgl2 glu, Point2f point) {
//        
//        GLParam param = ContextObject.instance().getGlParam();
//        glu.gluProject(point.x, point.y, 0, param.getModelview(), 0, param.getProjection(), 0, param.getViewport(), 0, win, 0);
//        param = ContextObject.instance().getOriginGLParam();
//        glu.gluUnProject((double) win[0], (double) win[1], 0.0, param.getModelview(), 0, param.getProjection(), 0, param.getViewport(), 0, objPos, 0);
//        return new Point2f((float) objPos[0], (float) objPos[1]);
//    }
}
