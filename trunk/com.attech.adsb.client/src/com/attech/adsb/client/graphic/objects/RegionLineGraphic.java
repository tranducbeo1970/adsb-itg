/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.Distance;
import com.attech.adsb.client.common.FunctionOpenGL;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Label;
import com.attech.adsb.client.graphic.RGB;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.RegionLine;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.List;


public class RegionLineGraphic extends IDrawableObject{
    
    private int bufferSize;
    protected String type;
    protected String regionType;
    protected String angles;
    protected float radius;
    protected int weight;
    protected int size;
    protected float zIndex;
    protected Boolean displayName;
    protected RGB color ;
    protected List<Point2f> points;
    private final List<LabelGraphic> labels;
    
    /**
     * Constructor
     */
    public RegionLineGraphic() {
        this.type = "";
        this.regionType = "";
        this.angles = "";
        this.radius = 0f;
        this.enable = true;
        this.displayName = true;
        this.changed = true;
        this.points = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.weight = 1;
        this.zIndex = 1000;
        this.color = new RGB(0.0f, 0.0f, 0.0f);
    }       

    RegionLineGraphic(RegionLine line) {    
        this.type = line.getType();
        this.radius = line.getRadius();
        this.weight = line.getWeight();
        this.zIndex = line.getzIndex();
        this.angles = line.getAngles();
        this.color = new RGB(line.getColor());
        this.labels = new ArrayList<>();
        this.points = line.getPoint2fs();
        this.regionType = line.getRegionType();
        if(this.regionType.equals("PROC")){
            for (int i = 0; i < this.points.size(); i++) {
                Point2f p = points.get(i);
                Label label = new Label(line.getPoints().get(i));
                label.setColor("#E2E2E2");
//                label.setColor(this.color.toString());
                LabelGraphic labelGraphic = new LabelGraphic(label);
                labelGraphic.setCalculatedPosition(p.getX() - 2, p.getY() + 2);
                labels.add(labelGraphic);
            }
        }
        if(this.regionType.equals("FIX")){
            for (int i = 0; i < this.points.size(); i++) {
                String name = "";
                String[] str = line.getPoints().get(i).split("-");
                if(str.length==3){
                    name = str[2];
                }     
                Point2f p = points.get(i);
                Label label = new Label(name);
                label.setColor("#E2E2E2");
                label.setFontSize(6);
                LabelGraphic labelGraphic = new LabelGraphic(label);
                labelGraphic.setCalculatedPosition(p.getX() - 2, p.getY() - 3);
                labels.add(labelGraphic);
            }
        }
        if(this.regionType.equals("TMA")){
            for (int i = 0; i < this.points.size(); i++) {
                Point2f p = points.get(i);
                String namePoint = line.getPoints().get(i);
                String str[] = namePoint.split("-");
                if(str[1].equals("TEXTPOS")){
                    Label label = new Label(str[2]);
                    label.setColor(color.toString());
                    LabelGraphic labelGraphic = new LabelGraphic(label);
                    labelGraphic.setCalculatedPosition(p.getX(), p.getY());
                    labels.add(labelGraphic);
                }                
            }
        }
        this.changed = true;      
    }              
   
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
//        final GL2 gl = glad.getGL().getGL2();
        
        if(this.regionType.equals("PROC")){
            if(this.type.equals("LINES")){
                calculate();            
                if(bufferSize <= 0) return;        

                gl.glLineWidth(this.weight);
                gl.glColor3f(color.red, color.green, color.blue);
                gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
                buffer.rewind();
                gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
                gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, bufferSize);
            }

            if(this.type.equals("CIRCLE")){
                gl.glColor3f(color.red, color.green, color.blue);
                FunctionOpenGL.drawArc(gl, this.points.get(0), this.radius , GL2.GL_LINE_STRIP, 0, 360);
            }
            
            if(this.type.equals("ARC")){     
                if(this.points.size() == 3){                    
                    Point2f center = this.points.get(0);
                    int angleFrom = (int) Distance.Bearing(center, this.points.get(1));
                    int angleTo = (int) Distance.Bearing(center, this.points.get(2));
                    gl.glColor3f(color.red, color.green, color.blue);                    
                    FunctionOpenGL.drawArc(gl, center, this.radius , GL2.GL_LINE_STRIP, angleFrom, angleTo);
                 }else{
                     return;
                 }                        
            }

            if(this.displayName){
                for (LabelGraphic labelGraphic : this.labels){
                    labelGraphic.draw(gl, context);
                }
                for (Point2f p : this.points){
                    FunctionOpenGL.drawPointProc(gl, p, "#FFFFFF", 4, context);
                }
            }            
        }
        
        if(this.regionType.equals("TMA")){
            if(this.type.equals("DOT_LINE")){
                calculateTMA();  
                              
                if(bufferSize <= 0) return;        

                gl.glLineWidth(this.weight);
                gl.glColor3f(color.red, color.green, color.blue);
                gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
                buffer.rewind();
                gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
                
                gl.glEnable(GL2.GL_LINE_STIPPLE);
                gl.glLineStipple(1, (short) 0xF0F0);        
                gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, bufferSize);
                gl.glEnd();
                gl.glDisable(GL2.GL_LINE_STIPPLE);
            }

            if(this.type.equals("CIRCLE")){
                gl.glColor3f(color.red, color.green, color.blue);
                FunctionOpenGL.drawArc(gl, this.points.get(0), this.radius , GL2.GL_LINE_STRIP, 0, 360);
                FunctionOpenGL.drawArcTransparen(gl, this.points.get(0), this.radius , GL2.GL_POLYGON, 0, 360);
            }

            //if (!context.isShowLabel()) return;
            for (LabelGraphic labelGraphic : this.labels){
                labelGraphic.draw(gl, context);
            }
        }
        
        if(this.regionType.equals("APP")){
            if(this.type.equals("LINES")){
                calculate();                                
                if(bufferSize <= 0) return;   
                gl.glLineWidth(this.weight);
                gl.glColor3f(color.red, color.green, color.blue);
                gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
                buffer.rewind();
                gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);                
                gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, bufferSize);
            }

            if(this.type.equals("CIRCLE")){
                int angleFrom, angleTo;
                String[] str = this.angles.split(",");
                angleFrom = Integer.parseInt(str[0]);
                angleTo = Integer.parseInt(str[1]);
                if(angleTo<angleFrom){
                    angleTo = angleTo + 360;
                }
                gl.glColor3f(color.red, color.green, color.blue);
                FunctionOpenGL.drawArc(gl, this.points.get(0), this.radius , GL2.GL_LINE_STRIP, angleFrom, angleTo);
            }
            for (Point2f p : this.points){
                FunctionOpenGL.drawPointApp(gl, p, color.toString(), 4, context);
            }
        }
        
        if(this.regionType.equals("ARR")){
            if(this.type.equals("LINES")){
                calculate();                                
                if(bufferSize <= 0) return;   
                gl.glLineWidth(this.weight);
                gl.glColor3f(color.red, color.green, color.blue);
                gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
                buffer.rewind();
                gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);                
                gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, bufferSize);
            }

            if(this.type.equals("CIRCLE")){
                int angleFrom, angleTo;
                String[] str = this.angles.split(",");
                angleFrom = Integer.parseInt(str[0]);
                angleTo = Integer.parseInt(str[1]);
                if(angleTo<angleFrom){
                    angleTo = angleTo + 360;
                }
                gl.glColor3f(color.red, color.green, color.blue);
                FunctionOpenGL.drawArc(gl, this.points.get(0), this.radius , GL2.GL_LINE_STRIP, angleFrom, angleTo);

                gl.glColor3f(color.red, color.green, color.blue);
                FunctionOpenGL.drawArcTransparen(gl, this.points.get(0), this.radius , GL2.GL_POLYGON, angleFrom, angleTo);
            }
        }
        
        if(this.regionType.equals("RWY")){
            FunctionOpenGL.drawDuongBang(gl, points, -6000.0f);
            FunctionOpenGL.drawDuongLan(gl, points, (int) this.zIndex, this.color, -6000.0f);
        }
        
        if(this.regionType.equals("FIX")){
            if(this.type.equals("FIX")){
                for (Point2f p : this.points){
                    FunctionOpenGL.drawFixPoint(gl, p, this.color.toString(), context);
                }
            }

            if(this.type.equals("VISP")){
                for (Point2f p : this.points){
                    FunctionOpenGL.drawVispPoint(gl, p, this.color.toString(), 5, context);
                }
            }

            if(this.displayName){
                for (LabelGraphic labelGraphic : this.labels){
                    labelGraphic.draw(gl, context);
                }                
            }            
        }

    }
    
    protected void calculate() {
        if (!this.isChanged()) return;
        if(this.points.size() > 0){
           bufferSize = this.points.size();
            this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(bufferSize * 3);
            for (Point2f p : this.points) {
                buffer.put(new float[]{p.getX(), p.getY(), zIndex});
            }
            this.setChanged(false); 
        }else{
            return;
        }
        
    }
    
    protected void calculateTMA() {
        if (!this.isChanged()) return;
        if(this.points.size() > 0){
           bufferSize = this.points.size()-1;
            this.buffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(bufferSize * 3);
            for(int i=0;i<this.points.size()-1;i++)
                buffer.put(new float[]{this.points.get(i).getX(), this.points.get(i).getY(), zIndex});     
            this.setChanged(false); 
        }else{
            return;
        }
        
    }

    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    
      
}
