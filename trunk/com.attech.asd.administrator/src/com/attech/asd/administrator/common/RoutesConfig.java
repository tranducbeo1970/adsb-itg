/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.administrator.common;

import com.attech.asd.administrator.XmlSerializer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "Routes")
@XmlAccessorType(XmlAccessType.NONE)
public class RoutesConfig extends XmlSerializer{
    
    @XmlElement(name = "Ref")
    private List<String> ref;
    
    @XmlElement(name = "Routes")
    private List<RouteLine> lines;
    
    
    private final Map<String, Point2f> points;
    
    
    public RoutesConfig() {
        this.lines = new ArrayList<>();
        this.ref = new ArrayList<>();
	this.points = new HashMap<>();
    }
    
    public void loadDefendency() {
	for (String refFile : this.ref) {
	    GroundStationConfig groundStationConfig = XmlSerializer.load(refFile, GroundStationConfig.class);
	    for (GroundStation groundStation : groundStationConfig.getGroundStations()) {
		if (points.containsKey(groundStation.getName())) continue;
                points.put(groundStation.getName(), new Point2f(groundStation.getX(), groundStation.getY()));
	    }
	}
        
        for (RouteLine routeLine : this.lines) {
            List<Point2f> point2fs = new ArrayList<>();
            for (String key : routeLine.getPoints()) {
                Point2f point2f = this.points.get(key);
                if (point2f == null) continue;
                point2fs.add(Convertor.fromWGS842OpenGL(point2f));
            }
            
            routeLine.setPoint2fs(point2fs);
        }
    }
    
    public List<XYLineAnnotation> getXYLines(){
        List<XYLineAnnotation> tmp = new ArrayList<>();
        lines.forEach((routeLine) -> {
            for (int i = 0; i< routeLine.getPoint2fs().size() - 1; i ++){
                Point2f a = Convertor.fromOpenGL2WGS84(routeLine.getPoint2fs().get(i));
                Point2f b = Convertor.fromOpenGL2WGS84(routeLine.getPoint2fs().get(i+1));
                tmp.add(new XYLineAnnotation(
                        a.x, 
                        a.y,
                        b.x, 
                        b.y,
                        new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f), 
                        Color.decode(routeLine.getColor())));
            }
        });
        return tmp;
    }
    
    public List<XYTextAnnotation> getXYTextName(){
        List<XYTextAnnotation> tmp = new ArrayList<>();
        lines.forEach((routeLine) -> {
            for (int i = 0; i< routeLine.getPoint2fs().size() - 1; i ++){
                Point2f p = Convertor.fromOpenGL2WGS84(routeLine.getPoint2fs().get(i));
                Point2f p1 = Convertor.fromOpenGL2WGS84(routeLine.getPoint2fs().get(i+1));
                XYTextAnnotation name = new XYTextAnnotation(routeLine.getName(), (p.getX() + p1.getX())/2 + 0.06, (p.getY() + p1.getY())/2 + 0.06); // + 0.08 to higher
                name.setPaint(Color.decode(routeLine.getColor()));
                tmp.add(name);
            }
        });
        return tmp;
    }
    
    public Point2f getPoint2f(String key) {
        return this.points.get(key);
    }

    public void addLine(RouteLine line) {
        this.lines.add(line);
    }
    
    public void addRef(String file) {
        this.ref.add(file);
    }
    
    public Set<String> getListPoint(){
        return points.keySet();
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">


    /**
     * @return the lines
     */
    public List<RouteLine> getLines() {
        return lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(List<RouteLine> lines) {
        this.lines = lines;
    }
    
    /**
     * @return the ref
     */
    public List<String> getRef() {
        return ref;
    }

    /**
     * @param ref the ref to set
     */
    public void setRef(List<String> ref) {
        this.ref = ref;
    }

    //</editor-fold>
    
    
    
}
