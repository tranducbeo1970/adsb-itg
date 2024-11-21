/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Region;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;


public class RegionGraphic extends IDrawableObject{
    
    protected String name;
    protected String type;
    protected Boolean enabled;
    protected Boolean displayName;
    protected Boolean selected;
    private List<RegionLineGraphic> lstRegionLine;
    
     
    RegionGraphic(Region region) {
        this.name = region.getName();
        this.type = region.getType();
        this.enabled = region.getEnabled();
        this.displayName = region.getDisplayName();
        this.selected = region.getSelected();    
        
        this.lstRegionLine = new ArrayList<>();
        if(region.getLstRegionline() != null){
            region.getLstRegionline().forEach((line) -> {
                line.setRegionType(this.type);
                this.lstRegionLine.add(new RegionLineGraphic(line));
            });
        }   
    }
              
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!isEnable()) return;                       
//        if (this.selected == null) return; 
//        if (!this.selected) return; 
        if(this.lstRegionLine == null) return;
//	 System.out.println(String.format("-------- Draw region %s : %s", this.name, this.isEnable()));
        for (RegionLineGraphic regionLine : this.lstRegionLine) {
            regionLine.displayName = this.displayName;
            regionLine.draw(gl, context);
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getDisplayName() {
        return displayName;
    }

    public void setDisplayName(Boolean displayName) {
        this.displayName = displayName;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
   //</editor-fold>
}
