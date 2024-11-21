/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.Painter;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.awt.Point;

/**
 *
 * @author ANDH
 */
public class MouseGraphic extends IDrawableObject {

    private Point2f mousePoint ;
    
    public MouseGraphic() {
	
    }
    
    public void update(Point point) {
//	mousePoint = context.convertFromScreenToOpenGL(point);
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
	
	if (!this.isEnable()) return;
	if (this.mousePoint == null) return;
	
//	GL2 gl = glad.getGL().getGL2();
//	gl.glLineWidth(0.5f);
//        gl.glColor3f(1.0f, 1.0f, 1.0f);
//
//	gl.glEnable(GL2.GL_LINE_STIPPLE);
//	gl.glLineStipple(1, (short) 0xaaaa);
//	
//        gl.glBegin(GL2.GL_LINES);
//        gl.glVertex3f(mousePoint.getX() - 5f, mousePoint.getY() - 5f, 10000f);
//        gl.glVertex3f(mousePoint.getX() + 5f, mousePoint.getY() + 5f, 10000f);
//        gl.glEnd();
//        
//        gl.glBegin(GL2.GL_LINES);
//        gl.glVertex3f(mousePoint.getX() + 5f, mousePoint.getY() - 5f, 10000f);
//        gl.glVertex3f(mousePoint.getX() - 5f, mousePoint.getY() + 5f, 10000f);
//        gl.glEnd();
//	gl.glDisable(GL2.GL_LINE_STIPPLE);
	
    }
    
}
