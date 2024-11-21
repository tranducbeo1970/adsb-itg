/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.Polygon;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saitama
 */
@XmlRootElement(name = "TMA-Warning")
@XmlAccessorType(XmlAccessType.NONE)
public class TmaValidator extends com.attech.adsb.client.common.XmlSerializer implements TargetValidator {   

    private static final MLogger logger = MLogger.getLogger(TmaValidator.class);
    private boolean validatingEnable;
    private boolean updating = false;
    
    @XmlElement(name = "Polygon")    
    private List<Polygon> lstPolygon;    

    public TmaValidator() {
	lstPolygon = new ArrayList<>();
    }

    @Override
    public void save(String filename) {
	serialize(filename, this);
    }
    
    public synchronized void load(String fileName) {                
	try {
	    setUpdating(true);
	    lstPolygon.clear();
	    File file = new File(fileName);
	    if (!file.exists()) return;
	    TmaValidator warning = this.load(fileName, TmaValidator.class);
	    if(warning == null) return;
	    this.lstPolygon = warning.getLstPolygon();
	} finally {
	    setUpdating(false);
	}
    }


    @Override
    public int validate(Target target) {        
	if (isUpdating() || !this.validatingEnable) return 0;      
        float altitude = target.getFlightLevel() * 100 / 3.2808f;
        if (altitude < 0) {
            return 0;
        }        
        for (Polygon polygon : this.lstPolygon) {            
            if (altitude >= polygon.getAlt()) {
                continue;
            }
            if (Distance.isPointIsInside(target.getPosition(), polygon.getLstPoint())) {
                logger.warn("Dectected track %s is on TMA Danger", target.getCallSign());
                target.setWarning(WarnType.MSA, "MSA Danger");
                return 0;
            }

            if (Distance.isPointIsInside(target.getAHeadVectorPoint(), polygon.getLstPoint())) {
                logger.warn("Dectected track %s is on TMA Warning", target.getCallSign());
                target.setWarning(WarnType.MSA, "MSA Warning");
            }
        }
        return 0;
    }
        
    @Override
    public synchronized void setEnableValidator(Boolean enable) {
        this.validatingEnable = enable;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    public void setLstPolygon(List<Polygon> lstPolygon) {
        this.lstPolygon = lstPolygon;
    }

    public List<Polygon> getLstPolygon() {
        return lstPolygon;
    }

    public synchronized boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }
    //</editor-fold>
      

}
