/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TargetValidator;
import com.attech.adsb.client.common.WarnLevel;
import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.VVR;
import com.attech.adsb.client.config.VVRs;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Saitama
 */
public class VVRsGraphic extends IDrawableObject implements TargetValidator{
    
    private static final MLogger logger = MLogger.getLogger(VVRsGraphic.class);
    
    private boolean validatingEnable;
    private List<VVRGraphic> lstVVR;
   
    public VVRsGraphic(VVRs vvds) {
        lstVVR = new ArrayList<>();
        for (VVR item : vvds.getVVR()) {
            lstVVR.add(new VVRGraphic(item));
        }
        this.enable = true;
    } 

    public List<VVRGraphic> getLstVVR() {
        return lstVVR;
    }

    public void setLstVVR(List<VVRGraphic> lstVVR) {
        this.lstVVR = lstVVR;
    }
    
    public void setEnable(String key, Boolean enable) {
        for (VVRGraphic item : this.lstVVR) {
            if (item.getName().equalsIgnoreCase(key)) {
                item.setEnable(enable);
                logger.info("Set display %s to %s", key, enable);
                break;
            }
        }
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        for (VVRGraphic item : this.lstVVR) {
            item.draw(gl, context);
        }
    }

    @Override
    public int validate(Target target) {
        if (!validatingEnable && !target.isAvailable()) {
            return 0;
        }

        int hitLevel = 0;
        for (VVRGraphic vvrGraphic : this.lstVVR) {
            hitLevel = vvrGraphic.validate(target);
            switch (hitLevel) {
                case 2:
                    target.setWarnLevel(hitLevel);
                    target.setWarning(WarnType.VVP, "VVR Danger");
                    logger.info("Target %s is on VVR Danger", target.getCallSign());
                    return 0;
                case 1:
                    target.setWarnLevel(hitLevel);
                    target.setWarning(WarnType.VVP, "VVR Warning");
                    logger.info("Target %s is on VVR Warning", target.getCallSign());
                    break;
            }
        }
        return 0;
    }    
    
    @Override
    public void setEnableValidator(Boolean enable) {
        this.validatingEnable = enable;
        logger.info("Set target validation to %s", enable);
    }
    
}
