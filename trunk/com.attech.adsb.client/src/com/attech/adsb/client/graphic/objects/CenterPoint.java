/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Point2f;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;

import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;

/**
 *
 * @author andh
 */
public class CenterPoint  extends IDrawableObject{

    private final int size;
    private int screenCenterX;
    private int screenCenterY;
    private Point2f point;
    private Point2f p1, p2, p3 , p4;
    
    
    public CenterPoint(int size) {
        this.size = size;
//        this.enable = true;
        
    }
    
//    private void calculate(Dimension windowSize) {
//        if (windowSize == null) return;
//        float screenX = windowSize.width / 2f;
//        float screenY = windowSize.height / 2f;
////        if (screenX == screenCenterX && screenY == screenCenterY) return;
//        
////        point = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX, screenY));
//        point = Painter.context.convertFromScreenToOpenGL(new Point2f(screenX, screenY));
////        p1 = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX - size, screenY - size));
////        p2 = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX - size, screenY + size));
////        p3 = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX + size, screenY + size));
////        p4 = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX + size, screenY - size));
//    }
    
    private void calculate(GraphicContext context) {
        if (context == null) return;
        float screenX = context.getScreenSize().width / 2f;
        float screenY = context.getScreenSize().height / 2f;
//        if (screenX == screenCenterX && screenY == screenCenterY) return;
        
//        point = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX, screenY));
        point = context.convertFromScreenToOpenGL(new Point2f(screenX, screenY));
//        p1 = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX - size, screenY - size));
//        p2 = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX - size, screenY + size));
//        p3 = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX + size, screenY + size));
//        p4 = Convertor.fromScreen2OriginalOpenGL(glu, new Point2f(screenX + size, screenY - size));
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
//        GL2 gl = glad.getGL().getGL2();
        calculate(context);
       
        gl.glLineWidth(1.0f);

        gl.glPushMatrix();
        gl.glLoadIdentity();
     
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(0f, 30f,0f);
        gl.glVertex3f(0f, -30f,0f);
        gl.glEnd();
        
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(30f, 0f,0f);
        gl.glVertex3f(-30f, 0f,0f);
        gl.glEnd();
        
        TextRenderer textr = new TextRenderer(new Font("Tahoma", Font.PLAIN, 18));
        textr.setColor(Color.YELLOW);
        textr.begin3DRendering ();
        textr.draw("x: " + point.getX(), 40, 0);
        textr.draw("y: " + point.getY(), 0, 40);
        textr.end3DRendering();

        gl.glPopMatrix();
    }
}
