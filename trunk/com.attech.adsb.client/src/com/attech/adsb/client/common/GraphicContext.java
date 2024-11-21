/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.GLParam;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author Saitama
 */
public class GraphicContext {


    
    private float zoom;
    private float zoomMin;
    private float zoomMax;
    private float zoomstep = 0.2f;
    
    private float panx = 0;
    private float pany = 0;
    private Point2f PreviousGLPoint;
    
//    private MouseContext mouseContext = new MouseContext();
    private Dimension screenSize;
    
//    private final GLParam originGLParam;
    private final GLParam glParam;
    private GLAutoDrawable canvas;
    
    private Point2f homePoint;
    private int version;
    private boolean lock;
    private boolean changed = true;
    private GLUgl2 glu;
    
    private boolean showLabel;
    private boolean ndbDisplay;
    private boolean vordmeDisplay;
    private boolean corridorDisplay;
    private boolean routeDisplay;
    private boolean waypointDisplay;
    private GL2 gl;
    private boolean flip;
    
    private static GraphicContext instance;
    
    public GraphicContext() {
        // this.mouseContext = new MouseContext();]
//        this.originGLParam = new GLParam();
        this.glParam = new GLParam();
        this.version = 0;
        this.lock = false;
        this.showLabel = true;
        glu = new GLUgl2();
    }
    
    public void setStartPoint(Point point) {
        PreviousGLPoint = convertFromScreenToOpenGL(point);
        System.out.println("Start point x: " + PreviousGLPoint.getX() + " Y: " + PreviousGLPoint.getY());
    }
    
    public Point2f canculatePan(Point point) {
        Point2f currentPoint = convertFromScreenToOpenGL(point);
        float dx = panx + (PreviousGLPoint.getX() - currentPoint.getX());
        float dy = pany + (PreviousGLPoint.getY() - currentPoint.getY());
        return new Point2f(dx, dy);
    }
    
    public static Point2f fromScreen2OpenGL(GLUgl2 glu, Point point, GLParam glparam) {
        double[] objPos = new double[3];
//        glu.gluUnProject((double) x, (double) glparam.viewport[3] - y, 0.0, glparam.modelview, 0, glparam.projection, 0, glparam.viewport, 0, objPos, 0);
        
        glu.gluUnProject((double) point.x, (double) glparam.getViewport()[3] - point.y, 0.0,
                glparam.getModelview(), 0, glparam.getProjection(), 0,
                glparam.getViewport(), 0, objPos, 0);
        Point2f p = new Point2f((float) objPos[0], (float) objPos[1]);
        return (p);
    }
    
    public Point2f convertFromScreenToOpenGL(Point2f point) {
        double[] objPos = new double[3];
        glu.gluUnProject( point.getX(), (double) glParam.getViewport()[3] - point.getY(), 0.0,
                glParam.getModelview(), 0, glParam.getProjection(), 0,
                glParam.getViewport(), 0, objPos, 0);
        Point2f p = new Point2f((float) objPos[0], (float) objPos[1]);
        return (p);
    }
    
    public Point2f convertFromScreenToOpenGL(Point point) {
        
        double[] objPos = new double[3];
        glu.gluUnProject( (double)point.getX(), (double) glParam.getViewport()[3] - (double) point.getY(), 0.0,
                glParam.getModelview(), 0, glParam.getProjection(), 0,
                glParam.getViewport(), 0, objPos, 0);
        Point2f p = new Point2f((float) objPos[0], (float) objPos[1]);
        return (p);
    }
    
    public Point2f convertFromOpenGLToScreen(GL2 gl, float x, float y, float z) {
        double[] win = new double[3];
//        GLU glu = new GLU();
        Point2f p = new Point2f();
//        getRectOfOpenGL(gl);
        glu.gluProject(x, y, z, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, win, 0);
        p.x = (float) win[0];
        p.y = (float) win[1];
        return p;
    }
    
    public Boolean isTargetOutsideScreen(float x, float y) {
//	Point2f p = new Point2f(longtitude, latitude);
//        p.y = (float) this.getLatitude();
//        p.x = (float) this.getLongtitude();
//        final GL2 gl = glad.getGL().getGL2();

//        p = Convertor.fromWGS842OpenGL(p);

	Point2f p = Convertor.openGLToScreen(gl, x, y, 0.0f);
//        Dimension screenSize = context.getScreenSize();
        return (p.x < 0 || p.x > screenSize.width || p.y < 0 || p.y > screenSize.height);
    }
    
    /**
     * Calculate position in loadIdentity mode
     *
     * @param glu
     * @param point
     * @return
     */
    public Point2f calculateIdentityPosition(Point2f point) {

//	GLParam param = ContextObject.instance().getGlParam();
        double[] win = new double[3];
        double[] objPos = new double[3];
	glu.gluProject(point.getX(), point.getY(), 0, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, win, 0);
	// param = ContextObject.instance().getOriginGLParam();
	glu.gluUnProject((double) win[0], (double) win[1], 0.0, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, objPos, 0);
	return new Point2f((float) objPos[0], (float) objPos[1]);
    }
    
    
    public Point2f calculateIdentityPosition(GL2 gl, float x, float y) {
        double[] win = new double[3];
        double[] objPos = new double[3];

        // Doi gia tri tu openGL sang toa do man hinh tai vi tri can hien thi
        // Luu lai gia tri nay de thuc hien
        // zoom pan sau do tinh lai gia tri OPENGL moi
        // dang nhe la hien thi luon
        getRectOfOpenGL(gl);

        // lay toa do man hinh
        glu.gluProject(x, y, 0, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, win, 0);

        // Tro ve trang thai Normal Identity
        gl.glPushMatrix();
        gl.glLoadIdentity();
        getRectOfOpenGL(gl);
        // Tinh toan gia tri moi cua OpenGL dua tren 
        // vi tri man hinh da tinh toan truoc trong truong hop da bi thay doi
        // MATRIX ve ma tran don vi
        //
        glu.gluUnProject((double) win[0], (double) win[1], 0.0, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, objPos, 0);
        // tra lai gia tri moi sau khi da tinh toan
        gl.glPopMatrix();
        getRectOfOpenGL(gl);
        return new Point2f((float) objPos[0], (float) objPos[1]);

    }
    
    public Point2f calculateIdentityPosition(float x, float y) {
        double[] win = new double[3];
        double[] objPos = new double[3];

        // Doi gia tri tu openGL sang toa do man hinh tai vi tri can hien thi
        // Luu lai gia tri nay de thuc hien
        // zoom pan sau do tinh lai gia tri OPENGL moi
        // dang nhe la hien thi luon
        getRectOfOpenGL(gl);

        // lay toa do man hinh
        glu.gluProject(x, y, 0, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, win, 0);

        // Tro ve trang thai Normal Identity
        gl.glPushMatrix();
        gl.glLoadIdentity();
        getRectOfOpenGL(gl);
        // Tinh toan gia tri moi cua OpenGL dua tren 
        // vi tri man hinh da tinh toan truoc trong truong hop da bi thay doi
        // MATRIX ve ma tran don vi
        //
        glu.gluUnProject((double) win[0], (double) win[1], 0.0, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, objPos, 0);
        // tra lai gia tri moi sau khi da tinh toan
        gl.glPopMatrix();
        getRectOfOpenGL(gl);
        return new Point2f((float) objPos[0], (float) objPos[1]);

    }
    
    public Point2f openGLToScreen(GL2 gl, float x, float y) {
        double[] win = new double[3];
//        GLU glu = new GLU();
        Point2f p = new Point2f();
        getRectOfOpenGL(gl);
        glu.gluProject(x, y, 0.0, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, win, 0);
        p.x = (float) win[0];
        p.y = glParam.getViewport(3) - glParam.getViewport(1);
        return p;
    }
     
     
    public Point2f openGLToScreen(float x, float y) {
        double[] win = new double[3];
//        GLU glu = new GLU();
        Point2f p = new Point2f();
        getRectOfOpenGL(gl);
        glu.gluProject(x, y, 0.0, glParam.getModelview(), 0, glParam.getProjection(), 0, glParam.getViewport(), 0, win, 0);
        p.x = (float) win[0];
        p.y = glParam.getViewport(3) - glParam.getViewport(1);
        return p;
    }

    private void getRectOfOpenGL(GL2 gl) {
        gl.glGetIntegerv(GL2.GL_VIEWPORT, glParam.getViewport(), 0);
        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, glParam.getModelview(), 0);
        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, glParam.getProjection(), 0);
    }

    public synchronized void zoomIn(){
        if (zoom + zoomstep > zoomMax) return;
        if (this.lock) return;
        this.setZoom(zoom + zoomstep);
        this.canvas.display();
    }
    
    public synchronized void zoomOut() {
        
        if (zoom - zoomstep < zoomMin) return;
        if (this.lock) return;
        this.setZoom(zoom - zoomstep);
        this.canvas.display();
    }

    public static GraphicContext instance() {
        if (instance == null) instance = new GraphicContext();
        return instance;
    }
    
    public synchronized void updateDragging() {
        if (this.lock) return;
//        this.setPanx(mouseContext.dx);
//        this.setPany(mouseContext.dy);
        // this.panx = mouseContext.dx;
        // this.pany = mouseContext.dy;
        // setChanged(true);
        this.canvas.display();
        
    }
    
    public void setHomePoint(Point2f home) {
        homePoint = home;
        Point2f hpWGS84 = Convertor.fromWGS842OpenGL(home);
        GraphicContext.instance().setPanx(-hpWGS84.getX());
        GraphicContext.instance().setPany(-hpWGS84.getY());
    }
    
    public void resetHomePoint() {
        Point2f hpWGS84 = Convertor.fromWGS842OpenGL(homePoint);
        GraphicContext.instance().setPanx(-hpWGS84.getX());
        GraphicContext.instance().setPany(-hpWGS84.getY());
        this.canvas.display();
    }
    
    public synchronized void flip() {
	this.flip = !flip;
    }
    
    @Override
    public String toString() {
        return String.format("Zoom level: %1f x: %4.5f y: %4.5f", zoom, getPanx(), getPany());
    }
    
    /**
     * @return the zoom
     */
    public float getZoom() {
        return zoom;
    }

    /**
     * @param zoom the zoom to set
     */
    public synchronized void setZoom(float zoom) {
        if (this.lock) return;
        if (this.zoom == zoom) return;
        this.zoom = zoom;
        inscreaseVerion();
        changed = true;
        if (this.canvas != null) this.canvas.display();
    }

    /**
     * @return the panx
     */
    public float getPanx() {
        return panx;
    }

    /**
     * @param panx the panx to set
     */
    public synchronized void setPanx(float panx) {
        if (this.panx == panx) return;
        this.panx = panx;
        inscreaseVerion();
        changed = true;
        // if (this.canvas != null) this.canvas.display();
    }

    /**
     * @return the pany
     */
    public float getPany() {
        return pany;
    }

    /**
     * @param pany the pany to set
     */
    public synchronized void setPany(float pany) {
        if (this.pany == pany) return;
        this.pany = pany;
        inscreaseVerion();
        changed = true;
        // if (this.canvas != null) this.canvas.display();
    }

    /**
     * @param gl the gl to set
     */
    public void setGl(GL2 gl) {
        this.gl = gl;
    }
    
        /**
     * @return the flip
     */
    public boolean isFlip() {
	return flip;
    }

    /**
     * @param flip the flip to set
     */
    public void setFlip(boolean flip) {
	this.flip = flip;
    }

    
//    /**
//     * @return the mouseContext
//     */
//    public MouseContext getMouseContext() {
//        return mouseContext;
//    }
//
//    /**
//     * @param mouseContext the mouseContext to set
//     */
//    public void setMouseContext(MouseContext mouseContext) {
//        this.mouseContext = mouseContext;
//    }

    /**
     * @return the screenSize
     */
    public Dimension getScreenSize() {
        return screenSize;
    }

    /**
     * @param screenSize the screenSize to set
     */
    public void setScreenSize(Dimension screenSize) {
        this.screenSize = screenSize;
    }
    
    public void setScreenSize(int width, int height) {
        if (this.screenSize == null) {
            this.screenSize = new Dimension(width, height);
            return;
        }
        this.screenSize.setSize(width, height);
    }
    
    /**
     * @return the canvas
     */
    public synchronized GLAutoDrawable getCanvas() {
        return canvas;
    }

    /**
     * @param canvas the canvas to set
     */
    public void setCanvas(GLAutoDrawable canvas) {
        this.canvas = canvas;
    }

    /**
     * @return the zoomMax
     */
    public float getZoomMax() {
        return zoomMax;
    }

    /**
     * @param zoomMax the zoomMax to set
     */
    public void setZoomMax(float zoomMax) {
        this.zoomMax = zoomMax;
    }

    /**
     * @return the zoomstep
     */
    public float getZoomstep() {
        return zoomstep;
    }

    /**
     * @param zoomstep the zoomstep to set
     */
    public void setZoomstep(float zoomstep) {
        this.zoomstep = zoomstep;
    }

    /**
     * @return the zoomMin
     */
    public float getZoomMin() {
        return zoomMin;
    }

    /**
     * @param zoomMin the zoomMin to set
     */
    public void setZoomMin(float zoomMin) {
        this.zoomMin = zoomMin;
    }

    /**
     * @return the changed
     */
    public synchronized boolean isChanged() {
        return changed;
    }

    /**
     * @param changed the changed to set
     */
    public synchronized void setChanged(boolean changed) {
        this.changed = changed;
    }

//    /**
//     * @return the originGLParam
//     */
//    public GLParam getOriginGLParam() {
//        return originGLParam;
//    }

    /**
     * @param originGLParam the originGLParam to set
     */
//    public void setOriginGLParam(GLParam originGLParam) {
//        this.originGLParam = originGLParam;
//    }

    /**
     * @return the glParam
     */
    public GLParam getGlParam() {
        return glParam;
    }

//    /**
//     * @param glParam the glParam to set
//     */
//    public void setGlParam(GLParam glParam) {
//        this.glParam = glParam;
//    }

    /**
     * @return the homePoint
     */
    public Point2f getHomePoint() {
        return homePoint;
    }

    /**
     * @return the version
     */
    public synchronized int getVersion() {
        return version;
    }
    
    public synchronized void inscreaseVerion() {
        // if (version >= Integer.MAX_VALUE - 10) {
        if (version >= 1000) {
            version = 0;
            return;
        }
        version++;
    }
    
    public synchronized void update() {
        if (!changed) return;
        inscreaseVerion();
        System.out.println("Change update status");
        changed = false;
        
    }

    /**
     * @return the showLabel
     */
    public boolean isShowLabel() {
        return showLabel;
    }

    /**
     * @param showLabel the showLabel to set
     */
    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
    }

    
    /**
     * @param lock the lock to set
     */
    public synchronized void setLock(boolean lock) {
        this.lock = lock;
    }

    /**
     * @return the ndbDisplay
     */
    public boolean isNdbDisplay() {
        return ndbDisplay;
    }

    /**
     * @param ndbDisplay the ndbDisplay to set
     */
    public void setNdbDisplay(boolean ndbDisplay) {
        this.ndbDisplay = ndbDisplay;
    }

    /**
     * @return the vordmeDisplay
     */
    public boolean isVordmeDisplay() {
        return vordmeDisplay;
    }

    /**
     * @param vordmeDisplay the vordmeDisplay to set
     */
    public void setVordmeDisplay(boolean vordmeDisplay) {
        this.vordmeDisplay = vordmeDisplay;
    }

    /**
     * @return the corridorDisplay
     */
    public boolean isCorridorDisplay() {
        return corridorDisplay;
    }

    /**
     * @param corridorDisplay the corridorDisplay to set
     */
    public void setCorridorDisplay(boolean corridorDisplay) {
        this.corridorDisplay = corridorDisplay;
    }

    /**
     * @return the routeDisplay
     */
    public boolean isRouteDisplay() {
        return routeDisplay;
    }

    /**
     * @param routeDisplay the routeDisplay to set
     */
    public void setRouteDisplay(boolean routeDisplay) {
        this.routeDisplay = routeDisplay;
    }
    
     /**
     * @return the waypointDisplay
     */
    public boolean isWaypointDisplay() {
        return waypointDisplay;
    }

    /**
     * @param waypointDisplay the waypointDisplay to set
     */
    public void setWaypointDisplay(boolean waypointDisplay) {
        this.waypointDisplay = waypointDisplay;
    }
}


