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
import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.VVD;
import com.attech.adsb.client.config.VVDs;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Saitama
 */
public class VVDsGraphic extends IDrawableObject implements TargetValidator{
    private static final MLogger logger = MLogger.getLogger(VVDsGraphic.class);
    
    private boolean validatingEnable;
    private List<VVDGraphic> lstVVD;
    

    public VVDsGraphic(VVDs vvds) {
        lstVVD = new ArrayList<>();
        for (VVD item : vvds.getVVD()) {
            lstVVD.add(new VVDGraphic(item));
        }
        this.enable = true;
    }

    public List<VVDGraphic> getLstVVD() {
        return lstVVD;
    }

    public void setLstVVD(List<VVDGraphic> lstVVD) {
        this.lstVVD = lstVVD;
    }
    
    public void setEnable(String key, Boolean enable) {
        for (VVDGraphic item : this.lstVVD) {
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
        for (VVDGraphic item : this.lstVVD) {
            item.draw(gl, context);
        }
    }
    
    @Override
    public int validate(Target target) {
        
        if (!validatingEnable && !target.isAvailable()) {
            return 0;
        }
        

        int hitLevel = 0;
        for (VVDGraphic vvdGraphic : this.lstVVD) {
            hitLevel = vvdGraphic.validate(target);

            switch (hitLevel) {
                case 2:
//                    target.setWarning(WarnType.VVP, WarnLevel.DANGER);
                    target.setWarnLevel(hitLevel);
                    target.setWarning(WarnType.VVP, "VVD Danger");
                    logger.warn("Target %s is on VVD Danger", target.getCallSign());
                    return 0;
                case 1:
                    target.setWarnLevel(hitLevel);
                    target.setWarning(WarnType.VVP, "VVD Warning");
                    logger.warn("Target %s is on VVD Warning", target.getCallSign());
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
