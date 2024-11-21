/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.Distance;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.AMACell;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andh
 */
public class AmaCellGraphic extends IDrawableObject{
 
    private final static double FEET = 3.28084;
    public final List<Point2f> points = new ArrayList<>();
    public final List<Point2f> outsidePoints = new ArrayList<>();
    public FloatBuffer outlineBuffer;
    private String text;
    private String text1;
    private String text2;
    private int size;
    private final AMACell cell;
    private final GLUT glut = new GLUT();
    private Point2f textPoint;

    private int lineWeight;
    private float zIndex;
    private RGB color;
    private int font;
    private Short lineStyle;
    private int margin;
    private boolean mouseHover;
    private float[] buff;
    private float minAltInFeet;
    
    public AmaCellGraphic(AMACell cell) {
        this.cell = cell;
        this.changed = true;
        this.mouseHover = false;
    }
    
    public boolean isHover(Point2f point) {
        return Distance.isPointIsInside(point, points);
    }
    
    public boolean isHoverOutline(Point2f point) {
        return Distance.isPointIsInside(point, outsidePoints);
    }
    
    /**
     * 
     * @param currentPos: Current target position in WGS coordinate 
     * @param closeRangPos: Close-Range Point in WGS coordinate
     * @param longRangePos: Long-Range Point in WGS coordinate
     * @param altitude: Current target altitude in feet
     * @return 3: Danger 2: Warning 1: Alert 0: Good
     */
    public int isViolate(Point2f currentPos, Point2f closeRangPos, Point2f longRangePos, float altitude) {
        if (altitude < 0 || altitude >= minAltInFeet) return 0;
        
        if (Distance.isPointIsInside(currentPos, points)) {
            return 3;
        } 
        
        if (Distance.isPointIsInside(closeRangPos, points)) {
            return 2;
        }
        
        if (Distance.isPointIsInside(longRangePos, points)) {
            return 1;
        }
        
        return 0;
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        calculate();
        gl.glColor3f(color.red, color.green, color.blue);
        gl.glLineWidth(this.lineWeight);

        Point2f p = context.calculateIdentityPosition(textPoint.x, textPoint.y);

        gl.glPushMatrix();
        gl.glLoadIdentity();

        text(glut, gl, (float) p.x, (float) p.y, text, this.zIndex);
        text(glut, gl, (float) p.x, (float) p.y - 25, text1, this.zIndex);
        text(glut, gl, (float) p.x, (float) p.y - 50, text2, this.zIndex);

        gl.glPopMatrix();

        gl.glEnable(GL2.GL_LINE_STIPPLE);
        gl.glLineStipple(1, (short) this.lineStyle);

        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, Buffers.newDirectFloatBuffer(buff));
        gl.glDrawArrays(GL2.GL_LINE_LOOP, 0, this.points.size());
        gl.glDisable(GL2.GL_LINE_STIPPLE);

        if (this.mouseHover) {
            gl.glDrawArrays(GL2.GL_LINE_LOOP, this.points.size(), this.outsidePoints.size());
        }

        gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnd();

    }
    
    public void calculate() {
        if (!this.isChanged()) return;
        System.out.println("AMA Cell recalculate");
        try {
            points.add(Convertor.fromWGS842OpenGL(new Point2f(cell.lon1, cell.lat1)));
            points.add(Convertor.fromWGS842OpenGL(new Point2f(cell.lon1, cell.lat2)));
            points.add(Convertor.fromWGS842OpenGL(new Point2f(cell.lon2, cell.lat2)));
            points.add(Convertor.fromWGS842OpenGL(new Point2f(cell.lon2, cell.lat1)));

            double dis = margin * Math.sqrt(2);
            Point2f pos1 = new Point2f();
            Point2f pos2;
            //Trai tren
            pos1.y = cell.lat2;
            pos1.x = cell.lon1;

             pos2 = Distance.Destination(points.get(1), dis, 315);
            cell.lat3 = (float) pos2.y;
            cell.lon3 = (float) pos2.x;
            //Trai duoi
            pos1.y = cell.lat1;
            pos1.x = cell.lon1;
            pos2 = Distance.Destination(points.get(0), dis, 225);
            cell.lat4 = (float) pos2.y;
            //Phai tren
            pos1.y = cell.lat2;
            pos1.x = cell.lon2;
            pos2 = Distance.Destination(points.get(2), dis, 45);
            cell.lon4 = (float) pos2.x;

            
            outsidePoints.add(new Point2f(cell.lon3, cell.lat3));
            outsidePoints.add(new Point2f(cell.lon3, cell.lat4));
            outsidePoints.add(new Point2f(cell.lon4, cell.lat4));
            outsidePoints.add(new Point2f(cell.lon4, cell.lat3));
            text = this.cell.getName();
            text1 = String.format("%.0fm", cell.alt);
            text2 = String.format("%.0ff", cell.alt * FEET);
            this.minAltInFeet = (float)(cell.alt * FEET);

            textPoint = new Point2f(Convertor.fromWGS842OpenGL(cell.lon1) + 5, Convertor.fromWGS842OpenGL(cell.lat2));
            size = this.points.size() + this.outsidePoints.size();
            buff = new float[size * 3];
            this.buffer = Buffers.newDirectFloatBuffer(size * 3);
//            BufferUtils.createFloatBuffer();
            
            int index = 0;
            for (Point2f p : this.points ) {
                 buff[index++] = p.getX();
                 buff[index++] = p.getY();
                 buff[index++] = this.zIndex;
            }
            
            for (Point2f p : this.outsidePoints ) {
                 buff[index++] = p.getX();
                 buff[index++] = p.getY();
                 buff[index++] = this.zIndex;
            }
            
        } finally {
            this.changed = false;
        }
    }
    
    private void text(GLUT glut, GL2 gl, float x, float y, String ss, float layer) {
        gl.glRasterPos3f(x, y - 25, layer);
//        glut.glutBitmapString(GLUT.BITMAP_8_BY_13, ss);
        glut.glutBitmapString(this.font, ss);
    }

    public void setLineWeight(int lineWeight) {
        this.lineWeight = lineWeight;
    }

    public void setzIndex(float zIndex) {
        this.zIndex = zIndex;
    }

    public void setColor(RGB color) {
        this.color = color;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public void setLineStyle(Short lineStyle) {
        this.lineStyle = lineStyle;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }
    
    public String getName() {
        return this.cell.getName();
    }
    
    public void setMouseHover(boolean mouseHover) {
        this.mouseHover = mouseHover;
    }

}
