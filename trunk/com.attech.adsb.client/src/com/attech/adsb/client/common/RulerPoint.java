/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.MeasureConfig;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Calculator;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.objects.LabelGraphic;
import com.jogamp.opengl.GL2;
import java.text.DecimalFormat;

/**
 *
 * @author ANDH
 */
public class RulerPoint {
    protected MeasureConfig config = Configuration.instance().getMeasurementConfig();

    protected Point2f point;
    private RulerPoint sourceMeasurePoint;
    protected double distance;
    protected double bearing1;
    protected double bearing2;
    private LabelGraphic labelGraphic;
    private Boolean root;
    private final DecimalFormat format = new DecimalFormat("#########0");
    private MeasureUnit unit;

    public RulerPoint(boolean isRoot) {
        this.labelGraphic = new LabelGraphic(config.getLabel());
        this.root = isRoot;
    }

    public RulerPoint(boolean isRoot, Point2f point2f) {
        this(isRoot);
        this.point = point2f;
    }

    public void calculate(RulerPoint targetPoint) {
        Point2f pp1 = Convertor.fromOpenglToWgs84(this.getPoint());
        Point2f pp2 = Convertor.fromOpenglToWgs84(targetPoint.getPoint());
        distance = Calculator.round(Calculator.getDistance(pp2, pp1, config.getUnit()), 1);
        bearing1 = Calculator.round(Calculator.angleBetween(pp1, pp2), 0);
        bearing2 = Calculator.round((bearing1 + 180) % 360, 0);
    }
    
    public void calculate() {
//        Point2f pp1 = Convertor.fromOpenGL2WGS84(this.getPoint());
//        Point2f pp2 = Convertor.fromOpenGL2WGS84(targetPoint.getPoint());
//        distance = Calculator.round(Calculator.getDistance(pp2, pp1, config.getUnit()), 1);
//        bearing1 = Calculator.round(Calculator.angleBetween(pp1, pp2), 0);
//        bearing2 = Calculator.round((bearing1 + 180) % 360, 0);
    }

    public void draw(GL2 gl) {
        gl.glVertex3f(getPoint().x, getPoint().y, getPoint().z);
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the point
     */
    public Point2f getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Point2f point) {
        this.point = point;
    }

    /**
     * @return the distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * @return the distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * @return the bearing1
     */
    public double getBearing1() {
        return bearing1;
    }

    /**
     * @return the bearing2
     */
    public double getBearing2() {
        return bearing2;
    }

    /**
     * @return the sourceMeasurePoint
     */
    public RulerPoint getSourceMeasurePoint() {
        return sourceMeasurePoint;
    }

    /**
     * @param sourceMeasurePoint the sourceMeasurePoint to set
     */
    public void setSourceMeasurePoint(RulerPoint sourceMeasurePoint) {
        this.sourceMeasurePoint = sourceMeasurePoint;
    }

    /**
     * @return the labelGraphic
     */
    public LabelGraphic getLabelGraphic() {
        labelGraphic.setCalculatedPosition(getPoint().getX(), getPoint().getY());
        labelGraphic.setContent(root ? String.format("Sum: %.1f %s", distance, config.getUnit()) : String.format("D=%.1f %s B=%s/%s", distance, config.getUnit(), format.format(bearing1), format.format(bearing2)));
        return labelGraphic;
    }

    /**
     * @param labelGraphic the labelGraphic to set
     */
    public void setLabelGraphic(LabelGraphic labelGraphic) {
        this.labelGraphic = labelGraphic;
    }

    /**
     * @return the root
     */
    public Boolean getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void isRoot(Boolean root) {
        this.root = root;
    }
    
    
    /**
     * @param unit the unit to set
     */
    public void setUnit(MeasureUnit unit) {
        this.unit = unit;
    }

    //</editor-fold>
}
