/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Calculator;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;

/**
 *
 * @author hong
 */
public class Circle extends IDrawableObject{



    private float zIndex;
    private int size = 121;
    private Point2f centerPoint;
    private double radius;
    private RGB color;
    private MeasureUnit radiusUnit;
    
    public Circle () {
        this.enable = true;
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        calculate();
        
//        System.out.println("Draw circle radius: " + this.radius + " color: " + this.color.toString() + " unit: " + radiusUnit);
        this.color.setColor(gl);
        this.buffer.rewind();
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
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
    
        /**
     * @return the centerPoint
     */
    public Point2f getCenterPoint() {
        return centerPoint;
    }

    /**
     * @param centerPoint the centerPoint to set
     */
    public void setCenterPoint(Point2f centerPoint) {
        this.centerPoint = centerPoint;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(double radius) {
        this.radius = radius;
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
     * @return the radiusUnit
     */
    public MeasureUnit getRadiusUnit() {
        return radiusUnit;
    }

    /**
     * @param radiusUnit the radiusUnit to set
     */
    public void setRadiusUnit(MeasureUnit radiusUnit) {
        this.radiusUnit = radiusUnit;
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

}
