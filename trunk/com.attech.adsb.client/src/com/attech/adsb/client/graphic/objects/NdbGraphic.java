/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.config.GroundStation;
import com.attech.adsb.client.config.Label;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

/**
 *
 * @author Saitama
 */
public class NdbGraphic extends IDrawableObject {

    private static final MLogger logger = MLogger.getLogger(NdbGraphic.class);
    
//    protected int weight;
    private Point2f point;
    private Point2f point1;
    private Point2f point2;
    private Point2f point3;
    private Point2f point4;
    private RGB color;
    private float zIndex;
    private int texture = 13;
    private int w;
    private int h;
    private LabelGraphic labelGrphic;
    
    public NdbGraphic() {
    }
    
    public NdbGraphic(GroundStation groundStation, int width, int height) {
        this.enable = groundStation.getEnabled();
        this.point = new Point2f(Convertor.fromWGS842OpenGL(groundStation.getX()), Convertor.fromWGS842OpenGL(groundStation.getY()));
        this.zIndex = groundStation.getzIndex();
        this.color = new RGB(groundStation.getColor());
        
        Label label = groundStation.getLabel();
        if (label.getX() == null) label.setX(groundStation.getX());
        if (label.getY() == null) label.setY(groundStation.getY());
        
        labelGrphic = new  LabelGraphic(label);
        this.w = width;
        this.h = height;
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!isEnable()) {
	    return;
	}
//	final GL2 gl = glad.getGL().getGL2();

	gl.glEnable(GL2.GL_TEXTURE_2D);
	gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
	gl.glBegin(GL2.GL_POLYGON);
	gl.glColor3f(color.red, color.green, color.blue);
	gl.glTexCoord3d(0, 0, zIndex);
	gl.glVertex3d(point1.getX(), point1.getY(), zIndex);
	gl.glTexCoord3d(1, 0, zIndex);
	gl.glVertex3d(point2.getX(), point2.getY(), zIndex);
	gl.glTexCoord3d(1, 1, zIndex);
	gl.glVertex3d(point3.getX(), point3.getY(), zIndex);
	gl.glTexCoord3d(0, 1, zIndex);
	gl.glVertex3d(point4.getX(), point4.getY(), zIndex);
	gl.glEnd();
	gl.glDisable(GL2.GL_TEXTURE_2D);

//        if (context.isShowLabel()) 
	labelGrphic.draw(gl, context);
        
    }
    
    public void setEnableLabel(Boolean enable) {
	this.labelGrphic.setEnable(enable);
    }
    
    public void calculate(GL2 gl, GraphicContext context) {
        logger.debug("Recalculate due to version changed");
        Point2f p1 = context.calculateIdentityPosition(gl, point.getX(), point.getY());
        point1 = new Point2f(p1.getX() - w / 2, p1.getY() + h / 2);
        point2 = new Point2f(p1.getX() + w / 2, p1.getY() + h / 2);
        point3 = new Point2f(p1.getX() + w / 2, p1.getY() - h / 2);
        point4 = new Point2f(p1.getX() - w / 2, p1.getY() - h / 2);
        labelGrphic.setCalculatedPosition(p1.getX(), p1.getY());
    }

    
}
