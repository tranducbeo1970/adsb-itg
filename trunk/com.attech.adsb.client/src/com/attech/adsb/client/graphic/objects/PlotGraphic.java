/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.Plot;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

/**
 *
 * @author ANDH
 */
public class PlotGraphic extends IDrawableObject {
    
    private Plot plot;
    private float x;
    private float y;
    
    public PlotGraphic() {
    }
    
    public PlotGraphic(Plot plot) {
	this.plot = plot;
    }
    
    public void calculate() {
	x = Convertor.fromWGS842OpenGL(plot.getLatitude());
	y = Convertor.fromWGS842OpenGL(plot.getLongitude());
    }
    

    @Override
    public void draw( GL2 gl, GraphicContext context) {
	// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
