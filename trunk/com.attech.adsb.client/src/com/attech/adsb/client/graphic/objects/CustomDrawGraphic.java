/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;


import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TargetValidator;
import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.CustomDrawListConfig;
import com.attech.adsb.client.config.CustomDrawItem;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author Saitama
 */
public class CustomDrawGraphic extends IDrawableObject implements TargetValidator{
    
    private static final MLogger logger = MLogger.getLogger(CustomDrawGraphic.class);
    
    private boolean validatingEnable = enable;
    private List<CustomDrawItemGraphic> lstDrawItem;
    private CustomDrawListConfig config;
    private CustomDrawItemGraphic preview;
   
    public CustomDrawGraphic() {
        this.lstDrawItem = new ArrayList<>();
        this.preview = new CustomDrawItemGraphic();
        this.preview.setEnable(false);
        this.enable = true;
    }

    public CustomDrawGraphic(CustomDrawListConfig drawTool) {
        this();
        this.config = drawTool;
        for (CustomDrawItem item : drawTool.getDrawItems()) {
            lstDrawItem.add(new CustomDrawItemGraphic(item));
        }
    }
    
    public void preview(CustomDrawItem item) {
        config.update(item);
        preview.update(item);
    }
    
    public void turnOffPreview() {
        this.preview.setEnable(false);
    }
    
    public List<CustomDrawItemGraphic> getLstDrawItem() {
        return lstDrawItem;
    }

    public void setLstDrawItem(List<CustomDrawItemGraphic> lstDrawItem) {
        this.lstDrawItem = lstDrawItem;
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        
        Iterator<CustomDrawItemGraphic> iterator2 = this.lstDrawItem.iterator();
        while (iterator2.hasNext()) {
            CustomDrawItemGraphic targetGraphicitem = iterator2.next();
            if (!targetGraphicitem.isObsoleted()) {
                continue;
            }
            iterator2.remove();
        }
        
        for (CustomDrawItemGraphic item : this.lstDrawItem) {
            item.draw(gl, context);
        }
        
        if (this.preview.isEnable()) this.preview.draw(gl, context);
    }

    @Override
    public int validate(Target target) {
        
        if (!validatingEnable || !target.isAvailable()) {
            return 0;
        }
        int hitLevel = 0;
        for (CustomDrawItemGraphic cusDrawGraphic : this.lstDrawItem) {
            hitLevel = cusDrawGraphic.validate(target);

            switch (hitLevel) {
                case 2:
//                    target.setWarning(WarnType.VVP, WarnLevel.DANGER);
                    target.setWarnLevel(hitLevel);
                    target.setWarning(WarnType.CUSTOM_DRAW, "Drawing Danger");
                    logger.warn("Target %s is on Drawing Danger", target.getCallSign());
                    return 0;
                case 1:
                    target.setWarnLevel(hitLevel);
                    target.setWarning(WarnType.CUSTOM_DRAW, "Drawing Warning");
                    logger.warn("Target %s is on Drawing Warning", target.getCallSign());
                    break;
            }
        }
        return 0;
	// Implementation
    }
    
    @Override
    public void setEnableValidator(Boolean enable) {
        this.validatingEnable = enable;
	logger.info("Set custom drawing validation to %s", enable);
    }
    
    public List<CustomDrawItem> getDrawList() {
        return this.config.getDrawItems();
    }
    
    public void add(CustomDrawItem item) {
        this.config.add(item);
        this.config.save(Common.RES_DRAW_TOOL);
        CustomDrawItemGraphic graphicItem = new CustomDrawItemGraphic(item);
        this.lstDrawItem.add(graphicItem);
    }
    
    public void remove(CustomDrawItem item) {
        item.setObsoleted(true);
        this.config.remvove(item);
        this.config.save(Common.RES_DRAW_TOOL);
    }
    
    public void save() {
        this.config.save(Common.RES_DRAW_TOOL);
    }

   
    
}
