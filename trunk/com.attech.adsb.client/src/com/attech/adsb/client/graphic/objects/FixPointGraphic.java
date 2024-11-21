/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.config.GroundStation;
import com.attech.adsb.client.config.GroundStationConfig;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Saitama
 */
public class FixPointGraphic extends IDrawableObject {

    private static final MLogger logger = MLogger.getLogger(FixPointGraphic.class);
    private final List<SingleFixFointGraphic> fixPoints;
    
    public FixPointGraphic(GroundStationConfig config) {
        fixPoints = new ArrayList<>();
        for (GroundStation stationConfig : config.getGroundStations()) {
            fixPoints.add(new SingleFixFointGraphic(stationConfig));
        }
    }
    
    public void setEnableLabel(Boolean enable) {
	for (SingleFixFointGraphic stationGraphic : this.fixPoints) {
	    stationGraphic.setEnableLabel(enable);
	}
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
//        final GL2 gl = glad.getGL().getGL2();
        calculate(gl, context);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        
        for (SingleFixFointGraphic stationGraphic : this.fixPoints) {
            stationGraphic.draw(gl, context);
        }

        gl.glPopMatrix();
    }
    
    private void calculate( GL2 gl, GraphicContext context) {
        if (!context.getGlParam().isObsoleted(version)) return;
        try {
            logger.debug("Recalculate due to version changed");
            for (SingleFixFointGraphic stationGraphic : this.fixPoints) {
                stationGraphic.calculate(gl, context);
            }
        } finally {
            this.version = context.getGlParam().getVersion();
        }
    }
}
