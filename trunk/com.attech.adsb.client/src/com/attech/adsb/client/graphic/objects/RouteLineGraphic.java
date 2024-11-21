/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.RouteLine;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Saitama
 */
public class RouteLineGraphic extends IDrawableObject{
    
    private int bufferSize;
    private int weight;
    private float zIndex;
    private RGB color ;
    private List<Point2f> points;
    private final List<LabelGraphic> labels;
    
    public RouteLineGraphic(RouteLine routeLine) {
        labels = new ArrayList<>();
        this.enable = routeLine.getEnabled();
        this.weight = routeLine.getWeight();
        this.zIndex = routeLine.getzIndex();
        this.color = new RGB(routeLine.getColor());
        this.points = routeLine.getPoint2fs();
        int size = points.size();
        for (int i = 1; i < size; i++) {
            Point2f p1 = points.get(i-1);
            Point2f p2 = points.get(i);
            float dx = (p2.getX() - p1.getX())/2;
            float dy = (p2.getY() - p1.getY())/2;
            LabelGraphic labelGrphic = new LabelGraphic(routeLine.getLabel());
            labelGrphic.setCalculatedPosition(p2.getX() + dx, p2.getY() + dy);
            labels.add(labelGrphic);
        }
        this.changed = true;
    }
    
    public void setEnableLabel(Boolean enable) {
	for (LabelGraphic labelGraphic : this.labels) {
	    labelGraphic.setEnable(enable);
	}
    }
    
    
    
//    @Override
//    public synchronized void draw(GL gl, GLU glu, GLUT glut) {
//        if (!this.isEnable()) return;
//        calculate();
//        super.draw(gl, glu, glut);
//
//        gl.glPushMatrix();
//        gl.glLoadIdentity();
//        
//        for (LabelGraphic2 l : labels) {
//            l.draw(gl, glu, glut);
//        }
//        gl.glPopMatrix();
//    }
    
//    @Override
//    protected void calculate() {
//        if (!this.changed) return;
//        super.calculate();
////        for (int i = 1; i < this.points.size(); i++) {
////            float x = (this.points.get(i).x + this.points.get(i - 1).x)/2;
////            float y = (this.points.get(i).y + this.points.get(i - 1).y)/2;
////            LabelGraphic2 routeLabel = new LabelGraphic2();
////            routeLabel.setColor(new RGB(1.0f, 0.0f, 0.0f));
////            routeLabel.setContent("HALLO");
////            routeLabel.setEnable(true);
////            routeLabel.setFont(BitmapFont.BITMAP_9_BY_15);
////            routeLabel.setzIndex(0);
////            routeLabel.setPointOpenGL(new Point2f(x, y));
////            routeLabel.setPaddingX(50);
////            routeLabel.setPaddingY(0);
////            this.labels.add(routeLabel);
////        }
//        this.changed = false;
//    }
//    
//    public synchronized void setLabelVisible(boolean value) {
//        for (LabelGraphic2 l : labels) {
//            l.setEnable(value);
//        }
//    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        calculate();
//        final GL2 gl = glad.getGL().getGL2();
        gl.glLineWidth(this.weight);
        gl.glColor3f(color.red, color.green, color.blue);
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        buffer.rewind();
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
        gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, bufferSize);
        
//        if (!context.isShowLabel()) return;
        for (LabelGraphic labelGraphic : this.labels){
            labelGraphic.draw(gl, context);
        }
    }
    
    /**
     * Calculate point
     */
    protected void calculate() {
        if (!this.isChanged()) return;
        bufferSize = this.points.size();
	this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(bufferSize * 3);
	for (Point2f p : this.points) {
	    buffer.put(new float[]{p.getX(), p.getY(), zIndex});
	}
        this.setChanged(false);
    }
}
