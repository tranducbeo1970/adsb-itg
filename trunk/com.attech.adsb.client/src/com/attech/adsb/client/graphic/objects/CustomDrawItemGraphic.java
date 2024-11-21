/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.Distance;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.common.enums.ShapeType;
import com.attech.adsb.client.config.CustomDrawItem;
import com.attech.adsb.client.graphic.RGB;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Calculator;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.ArrayList;
import java.util.List;


public class CustomDrawItemGraphic extends IDrawableObject{



    protected LabelGraphic name;
    protected ShapeType type;
    protected float radius;
//    protected String angle;
    protected float level;
    protected int weight;
    protected int size;
    protected float zIndex;
    protected RGB color ;
    protected List<Point2f> points;
    private CustomDrawItem drawItem;
    private Point2f location;
    private boolean preview;
    private GLUT glut = new GLUT();
    
    /**
     * Constructor
     */
    public CustomDrawItemGraphic() {
        this.name = new LabelGraphic();
        this.type = ShapeType.POLYGON;
        this.enable = true;
        this.radius = 0f;
//        this.angle = "";
        this.level = 0;
        this.enable = true;
        this.changed = true;
        this.points = new ArrayList<>();
        this.weight = 1;
        this.zIndex = 1000;
        this.color = new RGB(0.0f, 0.0f, 0.0f);
    }
    
    /**
     * Constructor
     * @param start
     * @param end 
     */
    public CustomDrawItemGraphic(Point2f start, Point2f end) {
        this();
        this.points.add(Convertor.fromWGS842OpenGL(start));
        this.points.add(Convertor.fromWGS842OpenGL(end));
        this.changed = true;
    }
    
    public CustomDrawItemGraphic(CustomDrawItem drawItem) {
        this();
        this.drawItem = drawItem;
        this.name = new LabelGraphic(drawItem.getLabel());
        this.type = drawItem.getType();
        this.level = drawItem.getLevel()*100;
        this.weight = drawItem.getWeight();
        this.zIndex = drawItem.getzIndex();
        this.color = new RGB(drawItem.getColor());
    }
    
    public void update(CustomDrawItem drawItem) {
        this.enable = false;
        this.drawItem = drawItem;
        this.name = new LabelGraphic(drawItem.getLabel());
        this.type = drawItem.getType();
        this.level = drawItem.getLevel();
        this.weight = drawItem.getWeight();
        this.zIndex = drawItem.getzIndex();
        this.color = new RGB(drawItem.getColor());
        drawItem.setEnabled(true);
        this.changed = true;
        this.enable = true;
        this.preview = true;
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (this.drawItem == null || !this.drawItem.isEnabled()) return;
        if (isChanged()) this.calculate();
        
        gl.glLineWidth(this.weight);
        gl.glColor3f(this.color.red, this.color.green, this.color.blue);
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        buffer.rewind();
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
        gl.glDrawArrays(GL2.GL_LINE_LOOP, 0, size);

        // Draw Label
        if (context.isShowLabel()) {
            this.name.draw(gl, context);
        }

        if (!preview) return; 
        
        for (int index = 0; index < this.points.size(); index++) {
            Point2f p1 = this.points.get(index);
            
            gl.glRasterPos3f(p1.x - 5, p1.y - 5 , zIndex);
            glut.glutBitmapString(7, Integer.toString(index + 1));
        }

    }
    
    /**
     * Calculate point
     */
    protected void calculate() {
        switch (this.type) {
            case POLYGON:
                this.size = this.drawItem.getPoints().size();
                this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(size * 3);
                Point2f labelPoint = Convertor.fromWGS842OpenGL(this.drawItem.getPoints(0));
                this.location = labelPoint;
                this.name.setCalculatedPosition(labelPoint.getX(), labelPoint.getY());
                this.points = new ArrayList<>();
                for (Point2f p : this.drawItem.getPoints()) {
                    Point2f point = Convertor.fromWGS842OpenGL(p);
                    this.buffer.put(point.getX()).put(point.getY()).put(zIndex);
                    this.points.add(point);
                }
                break;
                
            case ARC:
                this.points = new ArrayList<>();
                int stepCount =  drawItem.getAngleEnd() - drawItem.getAngleStart(); //  Integer.parseInt(st[1]) - Integer.parseInt(st[0]);
                Point2f centerPoint = Convertor.fromWGS842OpenGL(this.drawItem.getCenterPoint());
                this.location = centerPoint;
                this.name.setCalculatedPosition(centerPoint.getX(), centerPoint.getY());
                if (stepCount < 360) {
                    size = stepCount + 1;
                    this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(size * 3);
                    buffer.put(centerPoint.getX()).put(centerPoint.getY()).put(zIndex);
                    for (int angle = drawItem.getAngleStart(); angle < drawItem.getAngleEnd(); angle++) {
                        Point2f newPoint = Convertor.fromWGS842OpenGL(Calculator.getNextPoint(this.drawItem.getCenterPoint(), angle, this.drawItem.getRadius(), MeasureUnit.NM));
                        this.buffer.put(newPoint.getX()).put(newPoint.getY()).put(zIndex);
                        this.points.add(newPoint);
                    }
                    break;
                } 
                
                size = 360;
                this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(size * 3);
                for (int angle = 0; angle < 360; angle++) {
                    Point2f newPoint = Convertor.fromWGS842OpenGL(Calculator.getNextPoint(this.drawItem.getCenterPoint(), angle, this.drawItem.getRadius(), MeasureUnit.NM));
                    this.buffer.put(newPoint.getX()).put(newPoint.getY()).put(zIndex);
                    this.points.add(newPoint);
                }
                break;
        }
        
        this.setChanged(false);
    }
    
    public boolean isObsoleted() {
        return this.drawItem.isObsoleted();
    }

    /**
     * @return the location
     */
    public Point2f getLocation() {
        return location;
    }

    /**
     * @param preview the preview to set
     */
    public void setPreview(boolean preview) {
        this.preview = preview;
    }

    @Override
    public int validate(Target target) {
        
        double height = 100 * target.getFlightLevel() ;
        if (height <= 0 || height >= level) 
            return 0;
        
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
