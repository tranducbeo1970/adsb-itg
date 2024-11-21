/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Calculator;
import com.attech.adsb.client.graphic.Convertor;
import com.jogamp.opengl.GL2;

/**
 *
 * @author hong
 */
public class TargetRulerPoint extends RulerPoint {
    
    private Target target;
    
    public TargetRulerPoint(boolean isRoot, Target target) {
        super(isRoot);
        this.target = target;
        this.point = Convertor.fromWGS842OpenGL(this.target.getPosition());
        this.point.z = (float)config.getzIndex();
    }
    
    @Override
    public void calculate(RulerPoint targetPoint) {
        Point2f pp1 = this.target.getPosition();
        this.point = Convertor.fromWGS842OpenGL(pp1);
        this.point.z = (float) config.getzIndex();
//        Point2f pp1 = Convertor.fromOpenGL2WGS84(this.getPoint());
        Point2f pp2 = Convertor.fromOpenglToWgs84(targetPoint.getPoint());
        distance = Calculator.round(Calculator.getDistance(pp2, pp1, config.getUnit()), 1);
        bearing1 = Calculator.round(Calculator.angleBetween(pp1, pp2), 0);
        bearing2 = Calculator.round((bearing1 + 180) % 360, 0);
    }
    
    @Override
    public void calculate() {
        Point2f pp1 = this.target.getPosition();
        this.point = Convertor.fromWGS842OpenGL(pp1);
        this.point.z = (float) config.getzIndex();
    }
    
    @Override
    public Point2f getPoint() {
        return point;
    }
    
    @Override
     public void draw(GL2 gl) {
        gl.glVertex3f(getPoint().x, getPoint().y, getPoint().z);
    }
}
