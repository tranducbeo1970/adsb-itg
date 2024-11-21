/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.Distance;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.graphic.RGB;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.VVD;
import com.attech.adsb.client.graphic.Calculator;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;


public class VVDGraphic extends IDrawableObject{
    protected String name;
    protected String type;
    protected float radius;
    protected int levelmin;
    protected int weight;
    protected int size;
    protected float zIndex;
    protected RGB color;
    protected List<Point2f> points;
    private VVD vvd;
    
    VVDGraphic(VVD vvd) {
        this.points = new ArrayList<>();
        this.vvd = vvd;
        this.name = vvd.getName();
        this.type = vvd.getType();
        this.radius = vvd.getRadius();
        this.levelmin = vvd.getLevelmin();
        this.weight = vvd.getWeight();
        this.enable = vvd.getEnabled();
        this.zIndex = vvd.getzIndex();
        this.color = new RGB(vvd.getColor());
    }
              
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!isEnable()) return;
        if (isChanged()) this.calculate();
        gl.glLineWidth(this.weight);
        gl.glColor3f(this.color.red, this.color.green, this.color.blue);
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        buffer.rewind();
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
        gl.glDrawArrays(GL2.GL_LINE_LOOP, 0, size);
       
    }
    
    /**
     * Calculate point
     */
    protected void calculate() {
        try {
            if (!changed) return;
            switch (this.type) {
                case "POLYGON":
                    this.size = this.vvd.getPoints().size();
                    this.buffer = Buffers.newDirectFloatBuffer(size * 3);
                    for (Point2f point : this.vvd.getPoints()) {
                        Point2f newPoint = Convertor.fromWGS842OpenGL(point);
                        this.points.add(point);
                        buffer.put(newPoint.getX()).put(newPoint.getY()).put(zIndex);

                    }
                case "CIRCLE":
                    this.size = 360;
                    this.buffer = Buffers.newDirectFloatBuffer(size * 3);
                    Point2f center = new Point2f(this.vvd.getPoints().get(0));
                    for (int angle = 0; angle < 360; angle++) {
                        Point2f newPoint = Convertor.fromWGS842OpenGL(Calculator.getNextPoint(center, angle, this.radius, MeasureUnit.KM));
                        this.points.add(newPoint);
                        buffer.put(newPoint.getX()).put(newPoint.getY()).put(zIndex);
                    }
                    break;
            }

        } finally {
            this.setChanged(false);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int validate(Target target) {
//        if (target.isAvailable()) return 0;
        
        double height = target.getFlightLevel() * 100;
        if (height > this.levelmin) return 0;
        
        Point2f position = Convertor.fromWGS842OpenGL(target.getPosition());
        Point2f vectorPoint = Convertor.fromWGS842OpenGL(target.getSpeedVector());
        
        if (Distance.isPointIsInside(position, this.points)) {
            return 2;
        }
        
        if (Distance.isLineCrossPolygon(position, vectorPoint, points)) {
            return 1;
        }
        return 0;
    }
    
    

}
