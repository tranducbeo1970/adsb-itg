/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;


import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.MapConfig;
import com.attech.adsb.client.config.MapLine;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Saitama
 */
public class MapGraphic extends IDrawableObject {
    
    private List<MapLineGraphic> lines;
    
   
    public MapGraphic(MapConfig map) {
        lines = new ArrayList<>();
        for (MapLine points : map.getLines()) {
//            MapLineGraphic optLineGrap = new MapLineGraphic(points);
            lines.add(new MapLineGraphic(points));
        }
        this.enable = true;
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        for (MapLineGraphic line : this.lines) {
            line.draw(gl, context);
        }
    }
    
}
