/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.RouteLine;
import com.attech.adsb.client.config.RoutesConfig;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Saitama
 */
public class RouteGraphic extends IDrawableObject{
    
    private final List<RouteLineGraphic> lines;
    
    public RouteGraphic(RoutesConfig config) {
        lines = new ArrayList<>();
        for (RouteLine routeLine : config.getLines()) {
            RouteLineGraphic routeLineGraphic = new RouteLineGraphic(routeLine);
            this.lines.add(routeLineGraphic);
        }
    }
    
    public void add(RouteLineGraphic line) {
        this.lines.add(line);
    }
    
    public void setEnableLabel(Boolean enable) {
	for (RouteLineGraphic line : this.lines) {
	    line.setEnableLabel(enable);
	}
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.enable) return;
        for (RouteLineGraphic line : this.lines) {
            line.draw(gl, context);
        }
    }

}
