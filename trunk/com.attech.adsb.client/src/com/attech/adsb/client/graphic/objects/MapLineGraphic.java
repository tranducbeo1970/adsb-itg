/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.graphic.RGB;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.MapLine;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;


public class MapLineGraphic extends IDrawableObject{
    
    protected int weight;
    protected int size;
    protected float zIndex;
    protected RGB color ;
    protected List<Point2f> points;
    
    /**
     * Constructor
     */
    public MapLineGraphic() {
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
    public MapLineGraphic(Point2f start, Point2f end) {
        this();
        this.points.add(Convertor.fromWGS842OpenGL(start));
        this.points.add(Convertor.fromWGS842OpenGL(end));
        this.changed = true;
    }
    
    public MapLineGraphic(MapLine mapline) {
        this();
        this.enable = mapline.isEnable();
        this.points = new ArrayList<>();
        mapline.getPoints().forEach((point) -> {
            this.points.add(Convertor.fromWGS842OpenGL(point));
        });
        this.weight = mapline.getWeight();
        this.zIndex = mapline.getzIndex();
        this.color = new RGB(mapline.getColor());
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!isEnable()) return;
        if (isChanged()) this.calculate();
//        final GL2 gl = glad.getGL().getGL2();
        gl.glLineWidth(this.weight);
        gl.glColor3f(color.red, color.green, color.blue);
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        buffer.rewind();
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
        gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, size);
        
//        if (highlightPoint) {
//            buffer.rewind();
//            gl.glPointSize(2);
//            gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
//            gl.glDrawArrays(GL2.GL_POINTS, 0, size);
//        }
    }
    
    /**
     * Calculate point
     */
    protected void calculate() {

        size = this.points.size();
	this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(size*3);
	for (Point2f p : this.points) {
//	    buffer.put(new float[]{p.getX(), p.getY(), zIndex});
//	    buffer.put(new float[]{p.getX()});
//	    buffer.put(new float[]{p.getY()});
//	    buffer.put(new float[]{zIndex});
	    buffer.put(p.getX()).put(p.getY()).put(zIndex);
//	    buffer.put(p.getX());
//	    buffer.put(p.getY());
//	    buffer.put(zIndex);
	}
        this.setChanged(false);
    }
    

}
