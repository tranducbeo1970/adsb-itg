/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.FunctionOpenGL;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Airport;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public final class AirportGraphic extends IDrawableObject{

    protected String name;
    protected String type;
    protected String code;
    protected String iata;
    protected String icao;
    protected Boolean displayCircle;
    protected Boolean displayArc;
    protected Boolean enabled;
    protected Boolean selected;
    protected Point2f point;
    private Boolean available;
    private List<CircleGraphic> lstCircle = new ArrayList<>();
    private List<RegionsGraphic> lstRegions;
    
    public AirportGraphic() {
        lstRegions = new ArrayList<>();
        this.enable = true;
        this.available = true;
    }
     
    public AirportGraphic(Airport airport) {
//        this.name = airport.getName();
//        this.type = airport.getType();
//        this.code = airport.getCode();
//        this.iata = airport.getIata();
//        this.icao = airport.getIcao();
//        this.displayArc = airport.getDisplayArc();
//        this.displayCircle = airport.getDisplayCircle();
////        this.enabled = airport.getEnabled();
//        this.enabled = true;
//        this.selected = airport.getSelected();        
//        this.point = Convertor.fromWGS842OpenGL(airport.getPoint());
//        
//        this.lstRegions = new ArrayList<>();
//        if(airport.getRegions() != null){
//            airport.getRegions().forEach((regions) -> {
//                this.lstRegions.add(new RegionsGraphic(regions));
//            });
//        }
	update(airport);
    }
    
    public void update(Airport airport) {
        this.name = airport.getName();
        this.type = airport.getType();
        this.code = airport.getCode();
        this.iata = airport.getIata();
        this.icao = airport.getIcao();
        this.displayArc = airport.getDisplayArc();
        this.displayCircle = airport.getDisplayCircle();
//        this.enabled = airport.getEnabled();
        this.enabled = true;
        this.selected = airport.getSelected();
        this.point = Convertor.fromWGS842OpenGL(airport.getPoint());
        
        this.lstCircle.clear();
        if (airport.getCircle()!= null) {
            airport.getCircle().forEach((circle) -> {
                this.lstCircle.add(new CircleGraphic(circle));
            });
        }

        this.lstRegions.clear();
        this.lstRegions = new ArrayList<>();
        if (airport.getRegions() != null) {
            airport.getRegions().forEach((regions) -> {
                this.lstRegions.add(new RegionsGraphic(regions));
            });
        }
    }
 
    public synchronized void setEnable(String type, Boolean enable) {
	for (Iterator<RegionsGraphic> it = lstRegions.iterator(); it.hasNext();) {
	    RegionsGraphic graphic = it.next();
	    if (!graphic.getType().equalsIgnoreCase(type)) {
		continue;
	    }
	    graphic.setEnable(enable);
	    break;
	}
    }
    
    public synchronized void setEnable(String type, String itemName, Boolean enable) {
	for (Iterator<RegionsGraphic> it = lstRegions.iterator(); it.hasNext();) {
	    RegionsGraphic graphic = it.next();
	    if (!graphic.getType().equalsIgnoreCase(type)) {
		continue;
	    }
	    graphic.setEnable(itemName, enable);
	    break;
	}
    }

    public synchronized void setEnable(String type, String groupName, String itemName ,Boolean enable) {
       for (Iterator<RegionsGraphic> it = lstRegions.iterator(); it.hasNext();) {
	    RegionsGraphic graphic = it.next();
	    if (!graphic.getType().equalsIgnoreCase(type)) {
		continue;
	    }
	    graphic.setEnable(groupName, itemName, enable);
	    break;
	}
    }
            
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!isEnable()) return;
        if (!getAvailable()) return;
//        if (!this.selected) return;
//        System.out.println(String.format("---------- %s --------------------------------------------------------------------", this.name));
        for (RegionsGraphic regions : this.lstRegions) {
            regions.draw(gl, context);
        }
        
        
        Point2f center = new Point2f(this.point);
//        final GL2 gl = glad.getGL().getGL2();
        
        if(this.displayArc){      
            this.lstCircle.forEach((circle) -> {
                if(circle.getType().equals("ARC")){
                    gl.glColor3f(circle.getColor().red,circle.getColor().green,circle.getColor().blue);
                    if(circle.getMode().equals("DOT_LINE"))
                        FunctionOpenGL.drawArcDotLine(gl, circle.getCenterPoint(), circle.getRadius() , GL2.GL_LINE_STRIP, 0, 360); 
                    else
                        FunctionOpenGL.drawArc(gl, circle.getCenterPoint(), circle.getRadius() , GL2.GL_LINE_STRIP, 0, 360);
                    
                    if(circle.getTransparent()){
                        gl.glColor3f(circle.getColorTransparent().red,circle.getColorTransparent().green,circle.getColorTransparent().blue);
                        FunctionOpenGL.drawArcTransparen(gl, circle.getCenterPoint(), circle.getRadius() , GL2.GL_POLYGON, 0, 360);
                    } 
                }
            });            
        }
        
        if (this.displayCircle) {
            this.lstCircle.forEach((circle) -> {                
                if(circle.getType().equals("CIRCLE")){                    
//                    circle.draw(gl, context);                    
                    gl.glColor3f(circle.getColor().red,circle.getColor().green,circle.getColor().blue);
                    if(circle.getMode().equals("DOT_LINE"))
                        FunctionOpenGL.drawArcDotLine(gl, circle.getCenterPoint(), circle.getRadius() , GL2.GL_LINE_STRIP, 0, 360); 
                    else
                        FunctionOpenGL.drawArc(gl, circle.getCenterPoint(), circle.getRadius() , GL2.GL_LINE_STRIP, 0, 360);
                    
                    if(circle.getTransparent()){
                        gl.glColor3f(circle.getColorTransparent().red,circle.getColorTransparent().green,circle.getColorTransparent().blue);
                        FunctionOpenGL.drawArcTransparen(gl, circle.getCenterPoint(), circle.getRadius() , GL2.GL_POLYGON, 0, 360);
                    }                    
                }
            });            
        }
    }   
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public Boolean getDisplayCircle() {
        return displayCircle;
    }

    public void setDisplayCircle(Boolean displayCircle) {
        this.displayCircle = displayCircle;
    }

    public Boolean getDisplayArc() {
        return displayArc;
    }

    public void setDisplayArc(Boolean displayArc) {
        this.displayArc = displayArc;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Point2f getPoint() {
        return point;
    }

    public void setPoint(Point2f point) {
        this.point = point;
    }

    public List<RegionsGraphic> getLstRegions() {
        return lstRegions;
    }

    public void setLstRegions(List<RegionsGraphic> lstRegions) {
        this.lstRegions = lstRegions;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
    
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
    //</editor-fold>  
    
}
