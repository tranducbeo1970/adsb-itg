/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic;

//import com.attech.client.graphics.IDrawableObject;
//import com.attech.client.graphics.Point2f;
//import com.jogamp.opengl.GL;
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.glu.GLU;
//import com.jogamp.opengl.glu.GLU;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Point2f;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Lines")
@XmlAccessorType(XmlAccessType.NONE)
public class Line extends IDrawableObject{
    
    private String note;
    private RGB color ;
    private Point2f start;
    private Point2f end;
    private float zIndex = 0;
    
    public Line() {
        this.enable = true;
    }
    
    public Line(Point2f start, Point2f end, RGB color) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.enable = true;
    }

//    public synchronized void draw(GL2 gl, GLU glu, GLUT glut) {
//        if (!this.isEnable()) return;
//        if (getColor() != null) gl.glColor3f(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
//        gl.glBegin(GL.GL_LINES);
//        gl.glVertex3f(getStart().getX(), getStart().getY(), getzIndex());
//        gl.glVertex3f(getEnd().getX(), getEnd().getY(), getzIndex());
//        gl.glEnd();
//        
//        gl.glPointSize(10);
//        gl.glBegin(GL.GL_POINTS);
//        gl.glVertex3f(getStart().getX(), getStart().getY(), zIndex);
//        gl.glVertex3f(getEnd().getX(), getEnd().getY(), getzIndex());
//        gl.glEnd();
//    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the color
     */
    public RGB getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(RGB color) {
        this.color = color;
    }

    /**
     * @return the start
     */
    public Point2f getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Point2f start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Point2f getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Point2f end) {
        this.end = end;
    }

    /**
     * @return the zIndex
     */
    public float getzIndex() {
        return zIndex;
    }

    /**
     * @param zIndex the zIndex to set
     */
    public void setzIndex(float zIndex) {
        this.zIndex = zIndex;
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
//        final GL2 gl = glad.getGL().getGL2();
        if (getColor() != null) gl.glColor3f(getColor().getRed(), getColor().getGreen(), getColor().getBlue());
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(getStart().getX(), getStart().getY(), getzIndex());
        gl.glVertex3f(getEnd().getX(), getEnd().getY(), getzIndex());
        gl.glEnd();
        
        gl.glPointSize(10);
        gl.glBegin(GL2.GL_POINTS);
        gl.glVertex3f(getStart().getX(), getStart().getY(), zIndex);
        gl.glVertex3f(getEnd().getX(), getEnd().getY(), getzIndex());
        gl.glEnd();
    }
}
