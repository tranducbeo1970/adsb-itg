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
import com.attech.adsb.client.config.VVP;
import com.attech.adsb.client.config.VVPs;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Saitama
 */
public class VVPsGraphic extends IDrawableObject implements TargetValidator{
    
    private static final MLogger logger = MLogger.getLogger(VVPsGraphic.class);
    private boolean validatingEnable;
    private List<VVPGraphic> lstVVP;
   
    public VVPsGraphic(VVPs vvds) {
        lstVVP = new ArrayList<>();
        for (VVP item : vvds.getVVP()) {
            lstVVP.add(new VVPGraphic(item));
        }
        this.enable = true;
    } 

    public List<VVPGraphic> getLstVVP() {
        return lstVVP;
    }

    public void setLstVVP(List<VVPGraphic> lstVVP) {
        this.lstVVP = lstVVP;
    }
    
    public void setEnable(String key, Boolean enable) {
        for (VVPGraphic item : this.lstVVP) {
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
        for (VVPGraphic item : this.lstVVP) {
            item.draw(gl, context);
        }
    }
    
     @Override
    public int validate(Target target) {
        
        if (!validatingEnable && !target.isAvailable()) {
            return 0;
        }
        
        int hitLevel = 0;
        for (VVPGraphic vvpGraphic : this.lstVVP) {
            hitLevel = vvpGraphic.validate(target);
            switch (hitLevel) {
                case 2:
                    target.setWarnLevel(hitLevel);
                    target.setWarning(WarnType.VVP, "VVP Danger");
                    logger.info("Target %s is on VVP Danger", target.getCallSign());
                    return 0;
                case 1:
                    target.setWarnLevel(hitLevel);
                    target.setWarning(WarnType.VVP, "VVP Warning");
                    logger.info("Target %s is on VVP Warning", target.getCallSign());
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
