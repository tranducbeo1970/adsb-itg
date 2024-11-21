/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.config.RegionGroup;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RegionGroupGraphic extends IDrawableObject {

    protected String type;
    private String name;
    private List<RegionGraphic> lstRegion;

    public RegionGroupGraphic(RegionGroup regionGroup) {
	this.type = regionGroup.getType();
	this.name = regionGroup.getName();
	this.lstRegion = new ArrayList<>();
	if (regionGroup.getRegion() != null) {
	    regionGroup.getRegion().forEach((region) -> {
		region.loadFixPoint();
		this.lstRegion.add(new RegionGraphic(region));
	    });
	}
    }

    public void setEnable(String name, Boolean enable) {
	for (Iterator<RegionGraphic> it = lstRegion.iterator(); it.hasNext();) {
	    RegionGraphic regionGroup = it.next();
	    if (!regionGroup.getName().equalsIgnoreCase(name)) {
		continue;

	    }
	    regionGroup.setEnable(enable);
//	    System.out.println(String.format("-------------- SET %s to %s", name, regionGroup.isEnable()));
	    break;
	}
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
	if (!isEnable()) {
	    return;
	}
//	System.out.println(String.format("---- Draw %s:%s", this.type, this.name));
	for (RegionGraphic region : this.lstRegion) {
	    region.draw(gl, context);
	}

    }
    
    public int isViolate(Target target) {
	if (!this.type.equalsIgnoreCase("TMA")) return 0;
	
	return 0;
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public List<RegionGraphic> getLstRegion() {
	return lstRegion;
    }

    public void setLstRegion(List<RegionGraphic> lstRegion) {
	this.lstRegion = lstRegion;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }
    //</editor-fold>

}
