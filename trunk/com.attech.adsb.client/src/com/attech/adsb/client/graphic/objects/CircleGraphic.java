/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.config.Circles;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Calculator;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;

/**
 *
 * @author nhuongnd
 */
public class CircleGraphic extends IDrawableObject{
    
    private int zIndex;
    private int size = 121;
    private Point2f centerPoint;
    private float radius;
    private RGB color;
    private MeasureUnit radiusUnit;
    private String mode;
    private Boolean transparent;
    private RGB colorTransparent;
    private String type;
    
    public CircleGraphic () {
        this.enable = true;
    }
    
    public CircleGraphic (Circles circle) {
        this.zIndex = circle.getzIndex();
        this.centerPoint = Convertor.fromWGS842OpenGL(new Point2f(circle.getX(), circle.getY()));
        this.radius = circle.getRadius();
        if(circle.getMeasureUnit().equals("NM")){
            this.radiusUnit = MeasureUnit.NM;
            this.radius = circle.getRadius()*1.609344f;
        } else{
            this.radiusUnit = MeasureUnit.KM;
            this.radius = circle.getRadius();
        }
        this.color = new RGB(circle.getColor());
        this.mode = circle.getMode();
        this.type = circle.getType();
        this.transparent = circle.getTransparent();        
        this.colorTransparent = new RGB(circle.getColorTransparent());        
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        calculate();
        
        
        this.color.setColor(gl);
        this.buffer.rewind();
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
        
        if(this.mode.equals("DOT_LINE"))
            gl.glLineStipple(1, (short) 0xF0F0);
        
        gl.glDrawArrays(GL2.GL_LINE_LOOP, 0, size);
    }
    
    public  void calculate() {
        if (!this.changed)  return;
        try {
            buffer = Buffers.newDirectFloatBuffer(size * 3);
            float step = 3.0f;
            Point2f pos;
            for (float angle = 0; angle < 361; angle += step) {
                pos = Calculator.calculateLocation(centerPoint.getLongtitude(), centerPoint.getLatitude(), radius, angle, radiusUnit);
                buffer.put(Convertor.fromWGS842OpenGL(pos.x)).put(Convertor.fromWGS842OpenGL(pos.y)).put(this.zIndex);
            }
        } finally {
            this.setChanged(false);
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Point2f getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Point2f centerPoint) {
        this.centerPoint = centerPoint;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public RGB getColor() {
        return color;
    }

    public void setColor(RGB color) {
        this.color = color;
    }

    public MeasureUnit getRadiusUnit() {
        return radiusUnit;
    }

    public void setRadiusUnit(MeasureUnit radiusUnit) {
        this.radiusUnit = radiusUnit;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Boolean getTransparent() {
        return transparent;
    }

    public void setTransparent(Boolean transparent) {
        this.transparent = transparent;
    }

    public RGB getColorTransparent() {
        return colorTransparent;
    }

    public void setColorTransparent(RGB colorTransparent) {
        this.colorTransparent = colorTransparent;
    }
    
    //</editor-fold>  

}
