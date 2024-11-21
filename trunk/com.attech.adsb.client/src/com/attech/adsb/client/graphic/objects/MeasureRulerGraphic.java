/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.RulerPoint;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TargetRulerPoint;
import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.Label;
import com.attech.adsb.client.config.MeasureConfig;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class MeasureRulerGraphic extends IDrawableObject {

    private static final MeasureConfig config = Configuration.instance().getMeasurementConfig();
    
    private List<RulerPoint> points;
    private boolean active;
    private RulerPoint candidatePoint;
//    private MeasureConfig config;
    private final int maximumRulerPoint;
    private final int weight;
    private final Short lineStyle;
    private final RGB color;
    private final int pointWeight;
    private final Label labelConfig;
    private final float zIndex;
    private final MeasureUnit unit;
    
    
    
    public MeasureRulerGraphic() {
	this.points = new ArrayList<>();
	this.active = true;
	this.maximumRulerPoint = config.getMaximumRulerPoint();
	this.weight = config.getLineWeight();
	this.lineStyle = config.getLineStyleInShort(); // Short.parseShort(config.getLineStyle(), 16);
	this.color = new RGB(config.getLineColor());
	this.pointWeight = config.getPointWeight();
	this.labelConfig = config.getLabel();
	this.zIndex = config.getzIndex();
        this.unit = config.getUnit();
	
    }
    
    public void finish() {
        this.setActive(false);
        this.candidatePoint = null;
        this.calculate();
    }
    
    public void updateCandidatePoint(Point2f point) {
	point.z = this.zIndex;
	if (this.candidatePoint == null) {
	    this.candidatePoint = new RulerPoint(false, point);
	} else {
	    this.candidatePoint.setPoint(point);
	}
	calculate();
    }
    
    public void calculate() {
	int size = this.points.size();
	if (size == 0) return;
	double total = 0;
        this.points.get(0).calculate();
        for (int i = 1; i < size; i++) {
            this.points.get(i).calculate(this.points.get(i - 1));
            total += this.points.get(i).getDistance();
        }

        if (this.active && this.candidatePoint != null) {
            this.candidatePoint.calculate(this.points.get(size - 1));
            total += this.candidatePoint.getDistance();
        }
	
	this.points.get(0).setDistance(total);
    }
    
    public synchronized void putRulerPoint(Point2f point) {
	point.z = this.zIndex;
        RulerPoint newPoint = this.points.isEmpty() ? new RulerPoint(true, point) : new RulerPoint(false, point);
        this.points.add(newPoint);
    }
    
    public synchronized void putRulerPoint(Target target) {
        TargetRulerPoint newPoint = this.points.isEmpty() ? new TargetRulerPoint(true, target) : new TargetRulerPoint(false, target);
        this.points.add(newPoint);
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (this.points.isEmpty()) return;
	calculate();
	gl.glLineWidth(this.weight);
	gl.glColor3f(color.red, color.green, color.blue);
        
        gl.glEnable(GL2.GL_LINE_STIPPLE);
        gl.glLineStipple(1, this.lineStyle);

        // Drawing line        
	gl.glBegin(GL2.GL_LINE_STRIP);
	for (RulerPoint point : this.points){
            point.draw(gl);
	}

	if (this.isActive() && this.candidatePoint != null) {
	    this.candidatePoint.draw(gl);
	}
	gl.glEnd();
        
        
        gl.glDisable(GL2.GL_LINE_STIPPLE);
        
        // Drawing Points
	gl.glPointSize(this.pointWeight);
	gl.glBegin(GL2.GL_POINTS);
	for (RulerPoint point : this.points) {
	    point.draw(gl);
	}
	gl.glEnd();
        
        // Drawing Label
        for (RulerPoint point : this.points) {
            point.getLabelGraphic().draw(gl, context);
        }

        if (this.isActive() && this.candidatePoint != null) {
            this.candidatePoint.getLabelGraphic().draw(gl, context);
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">

    /**
     * @return the points
     */
    public List<RulerPoint> getPoints() {
	return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(List<RulerPoint> points) {
	this.points = points;
    }
    
    public int getPointCount() {
        return this.points.size();
    }

    /**
     * @return the active
     */
    public boolean isActive() {
	return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
	this.active = active;
    }
    
    //</editor-fold>

  
    
}
