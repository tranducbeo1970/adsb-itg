/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic;

//import com.attech.client.enums.LineType;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TargetValidator;
import com.jogamp.opengl.GL2;
import java.nio.FloatBuffer;
//import java.nio.FloatBuffer;
//import com.sun.opengl.util.GLUT;
//import javax.media.opengl.GL;
//import javax.media.opengl.glu.GLU;

/**
 *
 * @author andh
 */
public abstract class IDrawableObject implements TargetValidator{
//    protected static GraphicContext context;

    protected boolean enable = true;
    protected boolean changed = true;
    protected FloatBuffer buffer;
    protected long version = 0;
//    protected boolean displayLabel = true;

//    public abstract void draw(GLAutoDrawable glad, GraphicContext context);
    public abstract void draw(GL2 gl, GraphicContext context);
    
    
    @Override
    public int validate(Target target) {
        return 0;
    }
    
    @Override
    public void setEnableValidator(Boolean enable ){
        
    }

//    public abstract void calculate() ;
    public synchronized void setLabelVisible(boolean value) {
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the enable
     */
    public synchronized boolean isEnable() {
	return enable;
    }

    /**
     * @param enable the enable to set
     */
    public synchronized void setEnable(boolean enable) {
	this.enable = enable;
    }

    /**
     * @return the changed
     */
    public synchronized boolean isChanged() {
	return changed;
    }

    /**
     * @param changed the changed to set
     */
    public synchronized void setChanged(boolean changed) {
	this.changed = changed;
    }

//    /**
//     * @param context the context to set
//     */
//    public static void setContext(GraphicContext graphContext) {
//        context = graphContext;
//    }
    //</editor-fold>
}
