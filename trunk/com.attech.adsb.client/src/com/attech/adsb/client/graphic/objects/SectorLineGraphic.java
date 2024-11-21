/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.FunctionOpenGL;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Label;
import com.attech.adsb.client.graphic.RGB;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.PointX;
import com.attech.adsb.client.config.SectorLine;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.List;


public class SectorLineGraphic extends IDrawableObject{
    
    protected int weight;
    protected int size;
    protected float zIndex;
    protected RGB color ;
    protected RGB colorLabel ;
    protected List<PointX> points;
    protected List<Point2f> lstFixPoints;
    protected List<LabelGraphic> lstLabels;
    protected boolean displayfixpoint;
    
    /**
     * Constructor
     */
    public SectorLineGraphic() {
        this.enable = true;
        this.changed = true;
        this.displayfixpoint = true;
        this.points = new ArrayList<>();
        this.lstFixPoints = new ArrayList<>();
        this.lstLabels = new ArrayList<>();
        this.weight = 1;
        this.zIndex = 1000;
        this.color = new RGB(0.0f, 0.0f, 0.0f);
        this.colorLabel = new RGB(0.0f, 0.0f, 0.0f);
    }
    
    /**
     * Constructor
     * @param start
     * @param end 
     */
    public SectorLineGraphic(PointX start, PointX end) {
        this();
        this.points.add(Convertor.fromWGS842OpenGL(start));
        this.points.add(Convertor.fromWGS842OpenGL(end));
        this.changed = true;
    }
    
    public SectorLineGraphic(SectorLine sectorline) {
        this();
        this.enable = sectorline.isEnable();
        this.lstFixPoints = new ArrayList<>();
        this.lstLabels = new ArrayList<>();
        this.points = new ArrayList<>();
        sectorline.getPoints().forEach((point) -> {
            this.points.add(Convertor.fromWGS842OpenGL(point));
            if(point.name!=null && !point.name.equals("")){
                Point2f p = Convertor.fromWGS842OpenGL(new Point2f(point.x, point.y));
                this.lstFixPoints.add(p);
                
                Label label = new Label(point.name);
                label.setColor(this.colorLabel.toString());
                label.setFontSize(6);
                LabelGraphic labelGraphic = new LabelGraphic(label);
                labelGraphic.setCalculatedPosition(p.x - 3, p.y - 6);
                this.lstLabels.add(labelGraphic);
            }
        });
        this.weight = sectorline.getWeight();
        this.zIndex = sectorline.getzIndex();
        this.color = new RGB(sectorline.getColor());        
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!isEnable()) return;
        if (isChanged()) this.calculate();
//        final GL2 gl = glad.getGL().getGL2();
        gl.glLineWidth(this.weight);
        gl.glColor3f(this.color.red, this.color.green, this.color.blue);
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        buffer.rewind();
        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
        gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, size);  
        
        if(this.displayfixpoint){
            if(this.lstLabels.size()>0){
                for (LabelGraphic labelGraphic : this.lstLabels){  
                    labelGraphic.setColor(this.colorLabel);
                    labelGraphic.draw(gl, context);
                }
            }

            if(this.lstFixPoints.size()>0){
                for (Point2f p : this.lstFixPoints){
                    FunctionOpenGL.drawFixPointSector(gl, p, this.colorLabel.toString(), context);
                }
            }
        }
    }
    
    /**
     * Calculate point
     */
    protected void calculate() {

        size = this.points.size();
	this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(size*3);
	for (PointX p : this.points) {
	    buffer.put(p.getX()).put(p.getY()).put(zIndex);
	}
        this.setChanged(false);
    }
    

}
