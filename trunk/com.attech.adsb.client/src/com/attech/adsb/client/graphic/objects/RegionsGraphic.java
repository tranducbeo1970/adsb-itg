/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Regions;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RegionsGraphic extends IDrawableObject{
    
    protected String type;
    private List<RegionGroupGraphic> lstRegionGroup;
    
     
    public RegionsGraphic(Regions regions) {
        this.type = regions.getType();
        this.lstRegionGroup = new ArrayList<>();
        if(regions.getRegionGroup()!= null){
            regions.getRegionGroup().forEach((regionGroup) -> {
                this.lstRegionGroup.add(new RegionGroupGraphic(regionGroup));
            });
        }
    }
    
    public void setEnable(String name, Boolean enable) {
        for (Iterator<RegionGroupGraphic> it = lstRegionGroup.iterator(); it.hasNext();) {
            RegionGroupGraphic regionGroup = it.next();
	    regionGroup.setEnable(name, enable);
        }
    }
    
    public void setEnable(String groupName, String name, Boolean enable) {
	for (Iterator<RegionGroupGraphic> it = lstRegionGroup.iterator(); it.hasNext();) {
	    RegionGroupGraphic regionGroup = it.next();
	    if (!regionGroup.getName().equalsIgnoreCase(groupName)) {
		continue;
	    }
//	    System.out.println(String.format("-------------- SET %s to %s", groupName, regionGroup.isEnable()));
	    regionGroup.setEnable(name, enable);
	    break;
	}
    }
              
    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!isEnable()) return;
//        System.out.println(String.format("Draw %s ---------------------------- ", this.type));
        for (RegionGroupGraphic regionGroup : this.lstRegionGroup) {
            regionGroup.draw(gl, context);
        }
        
    }   
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }    

    public List<RegionGroupGraphic> getLstRegionGroup() {
        return lstRegionGroup;
    }

    public void setLstRegionGroup(List<RegionGroupGraphic> lstRegionGroup) {
        this.lstRegionGroup = lstRegionGroup;
    }
    //</editor-fold>
       
}
