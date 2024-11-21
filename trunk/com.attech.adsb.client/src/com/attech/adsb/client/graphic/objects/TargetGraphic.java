/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.config.FilterCondition;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.TargetConfig;
import com.attech.adsb.client.config.TargetLabelDisplay;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author andh
 */
public final class TargetGraphic extends IDrawableObject {
    private static final MLogger logger = MLogger.getLogger(TargetGraphic.class);
    private final List<MouseEvent> clickEvent = new ArrayList<>();
    private final List<String> outOfScreenList = new ArrayList<>();
    private final List<TargetItem> targets;
    
    private TargetLabelDisplay labelDisplay;
    private Boolean outOfScreenWarning = false;
    private TargetItem selectedTarget;
    
    private FilterCondition filterCondition;

    public TargetGraphic(TargetConfig config) {
//	targets = new HashMap<>();
        targets = new ArrayList<>();
    }

//    public synchronized void addTarget(Target target) {
//        TargetItem targetGraphic = new TargetItem(target);
////        this.targets.put(target.getAddress(), targetGraphic);
//        this.targets.add(targetGraphic);
//    }

    public void addTarget(TargetItem target) {
        System.out.println("[TargetGraphic] Adding target " + target.getAddress() + String.format(" (size: %s)", targets.size()));
        target.setTargetLabelDisplay(labelDisplay);
        target.setFilterCondition(filterCondition);
        this.targets.add(target);
    }
    
    public void removeTarget(Target target){
        Iterator targetIterator = targets.iterator();
        while (targetIterator.hasNext()) {
            TargetItem item = (TargetItem) targetIterator.next();
            if (item.getTarget().getAddress().equalsIgnoreCase(target.getAddress())) {
                System.out.println("[TargetGraphic] Remove target item " + target.getAddress() + String.format(" (size: %s)", targets.size()));
                targetIterator.remove();
                return;
            }
        }

    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
	try {
            this.outOfScreenList.clear();

            /*
            for(TargetGraphicItem1 targetGraphicitem : this.targets) {
                  targetGraphicitem.draw(gl, context);
                  if (!this.outOfScreenWarning || !targetGraphicitem.isOutsideScreen(context)) continue;
                    outOfScreenList.add(targetGraphicitem.getCallSign());
            }
            */
            
            int size = targets.size();
            for (int i =0; i < size; i++) {
                TargetItem target = this.targets.get(i);
                if (target.isObsoleted()) continue;
//                if (this.filterCondition !=null && this.filterCondition.isActive()) target.applyFilterCondition(filterCondition);
                target.draw(gl, context);
                if (!this.outOfScreenWarning || !target.isOutsideScreen(context)) continue;
                outOfScreenList.add(target.getCallSign());
            }
            
            final GLUT glut = new GLUT();
            gl.glPushMatrix();
            gl.glLoadIdentity();
            if (context.isFlip() && this.outOfScreenWarning) {
                gl.glColor3f(1.0f, 0.0f, 0.0f);
            } else {
                gl.glColor3f(1.0f, 1.0f, 1.0f);
            }

            Dimension screenSize = context.getScreenSize();
            for (int i = 0; i < outOfScreenList.size(); i++) {
                String text = outOfScreenList.get(i) + " IS OUT OF SCREEN";
                gl.glWindowPos2i(12, screenSize.height - 16 * (i + 1));
                glut.glutBitmapString(GLUT.BITMAP_8_BY_13, text);
            }
            
            
            if (this.filterCondition != null && this.filterCondition.isActive()) {
                gl.glColor3f(0.0f, 1.0f, 0.0f);
                gl.glWindowPos2i(screenSize.width - 100, screenSize.height - 16);
                glut.glutBitmapString(GLUT.BITMAP_8_BY_13, "FILTERED");
            }

            gl.glPopMatrix();

            for (MouseEvent mouseEvent : this.clickEvent) {
                this.selectedTarget = null;
                Point2f mousePoint = context.convertFromScreenToOpenGL(mouseEvent.getPoint());
                Point2f checkingPoint = context.calculateIdentityPosition(mousePoint.x, mousePoint.y);
//              Point2f screenPoint = context.openGLToScreen(mousePoint.x, mousePoint.y);
                Iterator<TargetItem> iterator = this.targets.iterator();
                while (iterator.hasNext()) {
                    TargetItem targetGraphicitem = iterator.next();
                    if (!targetGraphicitem.isInside(checkingPoint)) continue;
//                    if (mouseEvent.getButton() == 1) targetGraphicitem.roteLable();
                    this.selectedTarget = targetGraphicitem;
                    System.out.println("Target " + this.selectedTarget.getCallSign() + " clicked");
                    break;
                }
            }
            
            
            Iterator<TargetItem> iterator2 = this.targets.iterator();
            while (iterator2.hasNext()) {
                TargetItem targetGraphicitem = iterator2.next();
                if (!targetGraphicitem.isObsoleted()) continue;
                iterator2.remove();
                System.out.println("Remove graphics " + targetGraphicitem.getTarget());
            }
            
            
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            this.clickEvent.clear();
        }
    }
    
    public void handeMouseClick(MouseEvent e) {
        this.clickEvent.add(e);
    }

    public TargetLabelDisplay getLabelDisplay() {
	return labelDisplay;
    }

    public void setLabelDisplay(TargetLabelDisplay labelDisplay) {
	this.labelDisplay = labelDisplay;
    }

    public void setOutOfScreenWarning(Boolean outOfScreenWarning) {
	this.outOfScreenWarning = outOfScreenWarning;
    }

    public TargetItem getSelectedTarget() {
        return selectedTarget;
    }

    /**
     * @param filterCondition the filterCondition to set
     */
    public void setFilterCondition(FilterCondition filterCondition) {
        this.filterCondition = filterCondition;
    }
    
    public void applyFilter(FilterCondition filterCondition) {
        this.filterCondition.setCallSign(filterCondition.getCallSign());
        this.filterCondition.setAltitudeHigh(filterCondition.getAltitudeHigh());
        this.filterCondition.setAltitudeLow(filterCondition.getAltitudeLow());
        this.filterCondition.setHideTarget(filterCondition.isHideTarget());
        this.filterCondition.setActive(filterCondition.isActive());

        Iterator<TargetItem> iterator2 = this.targets.iterator();
        while (iterator2.hasNext()) {
            TargetItem targetGraphicitem = iterator2.next();
            targetGraphicitem.setChanged(true);

        }
    }
    
    public FilterCondition getFilterCondition() {
        return this.filterCondition;
    }
}
