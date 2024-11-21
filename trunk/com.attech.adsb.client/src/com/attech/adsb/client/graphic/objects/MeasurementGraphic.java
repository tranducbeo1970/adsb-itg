/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.MeasureConfig;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Saitama
 */
public class MeasurementGraphic extends IDrawableObject {

    private final static MLogger logger = MLogger.getLogger(MeasurementGraphic.class);
    private final static MeasureConfig config = Configuration.instance().getMeasurementConfig();
    
    private List<MeasureRulerGraphic> rulerGraphics;
    private MeasureRulerGraphic activeRuler;
//    private MeasureConfig config;
    
    public MeasurementGraphic(MeasureConfig config) {
	this.rulerGraphics = new ArrayList<>();
//	this.config = config;
    }
    
    public MeasurementGraphic() {
        this.rulerGraphics = new ArrayList<>();
    }
    
    public void clearRuler() {
	this.rulerGraphics.clear();
    }
    
    public  void setPoint(Point2f point) {
	if (this.activeRuler == null || (config.getMaxMeasurePointPerRuler() >= 0 && this.activeRuler.getPointCount() >= config.getMaxMeasurePointPerRuler())) return;
	this.activeRuler.putRulerPoint(point);
        
    }
    
    public  void setPoint(Target target) {
	if (this.activeRuler == null || (config.getMaxMeasurePointPerRuler() >= 0 && this.activeRuler.getPointCount() >= config.getMaxMeasurePointPerRuler())) return;
	this.activeRuler.putRulerPoint(target);
        
    }
    
    public void updateCandidate(Point2f point) {
	if (this.activeRuler == null) return;
	this.activeRuler.updateCandidatePoint(point);
    }
    
    public void createRuler() {
        if (config.getMaxRulerNumber() >= 0 && rulerGraphics.size() >= config.getMaxRulerNumber()) {
            logger.debug("Exceed max number of ruler");
            return;
        }
	this.activeRuler = new MeasureRulerGraphic();
         logger.debug("Created new rulter");
    }
    
    public void finishRuler() {
	if (this.activeRuler == null) return;
	// this.activeRuler.setActive(false);
        this.activeRuler.finish();
        if (this.activeRuler.getPointCount() < 2) {
            this.activeRuler = null;
            return;
        }
	this.rulerGraphics.add(this.activeRuler);
	this.activeRuler = null;
        logger.debug("Ended ruler editing");
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        try {
            
            for (MeasureRulerGraphic ruler : this.rulerGraphics) {
                ruler.draw(gl, context);
            }

            if (this.activeRuler != null) {
                this.activeRuler.draw(gl, context);
            }
            
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
}

