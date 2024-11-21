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
public class SingleFixFointGraphic extends IDrawableObject {

    private static final MLogger logger = MLogger.getLogger(SingleFixFointGraphic.class);

    protected int size;
    protected int weight;

    private Point2f point;
    private Point2f point1;
    private Point2f point2;
    private Point2f point3;
    private RGB color;
    private float zIndex;
    private LabelGraphic labelGrphic;

    public SingleFixFointGraphic(GroundStation groundStation) {
        this.enable = groundStation.getEnabled();
        this.point = new Point2f(Convertor.fromWGS842OpenGL(groundStation.getX()), Convertor.fromWGS842OpenGL(groundStation.getY()));
        this.zIndex = groundStation.getzIndex();
        this.color = new RGB(groundStation.getColor());
        this.size = groundStation.getSize();
        this.weight = groundStation.getWeight();

        Label label = groundStation.getLabel();
        if (label.getX() == null) {
            label.setX(groundStation.getX());
        }
        if (label.getY() == null) {
            label.setY(groundStation.getY());
        }

        labelGrphic = new LabelGraphic(label);

    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!isEnable()) return;
//        final GL2 gl = glad.getGL().getGL2();

//        gl.glLineWidth(this.weight);
//        gl.glColor3f(color.red, color.green, color.blue);
//        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
//        buffer.rewind();
//        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
//        gl.glDrawArrays(GL2.GL_TRIANGLE_STRIP, 0, size);

        gl.glColor3f(color.red, color.green, color.blue);
        gl.glBegin(GL2.GL_TRIANGLE_STRIP);
        gl.glVertex3f(point1.getX(), point1.getY(), zIndex);
        gl.glVertex3f(point2.getX(), point2.getY(), zIndex);
        gl.glVertex3f(point3.getX(), point3.getY(), zIndex);
        gl.glEnd();
        
//        if (!context.isShowLabel()) return;
        this.labelGrphic.draw(gl, context);
    }
    
    public void setEnableLabel(Boolean enable) {
	this.labelGrphic.setEnable(enable);
    }

    public void calculate(GL2 gl, GraphicContext context) {
//        logger.debug("Recalculated");
        float dx = (float) (size * 2 * Math.sqrt(2) / 3);
        float dy = size / 2;
        Point2f p1 = context.calculateIdentityPosition(gl, point.getX(), point.getY());
        /*
        p1.set(p.x, p.y + size);
        p2.set(p.x + dx, p.y  - dy);
        p3.set(p.x - dx, p.y  - dy);
         */

        
        point1 = new Point2f(p1.getX(), p1.getY() + size);
        point2 = new Point2f(p1.getX() + dx, p1.getY() - dy);
        point3 = new Point2f(p1.getX() - dx, p1.getY() - dy);
        
        
        /*
        this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(3 * 3);
        buffer.put(new float[]{p1.getX(), p1.getY() + size, zIndex});
        buffer.put(new float[]{p1.getX() + dx, p1.getY() - dy, zIndex});
        buffer.put(new float[]{p1.getX() - dx, p1.getY() - dy, zIndex});
        */
        
        labelGrphic.setCalculatedPosition(p1.getX(), p1.getY());
    }

}
