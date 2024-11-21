/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.adsb.client.graphic;

import com.jogamp.opengl.GL2;


/**
 *
 * @author andh
 */
public class GLParam {

    
    public int[] viewport ;
    public double[] modelview, projection;
    public long version;
    
//    public int viewportSecond[] = new int[4];
//    public double modelviewSecond[] = new double[16];
//    public double projectionSecond[] = new double[16];
    
    public GLParam() {
        viewport = new int[4];
        modelview = new double[16];
        projection = new double[16];
        version = 0;
    }

    public int[] getViewport() {
        return viewport;
    }
    
    public int getViewport(int index) {
        return viewport[index];
    }

    public void setViewport(int[] viewport) {
        this.viewport = viewport;
    }

    public double[] getModelview() {
        return modelview;
    }
    
    public double getModelview(int index) {
        return modelview[index];
    }

    public void setModelview(double[] modelview) {
        this.modelview = modelview;
    }

    public double[] getProjection() {
        return projection;
    }
    
    public double getProjection(int index) {
        return projection[index];
    }

    public void setProjection(double[] projection) {
        this.projection = projection;
    }
    
    
    /**
     * @return the version
     */
    public long getVersion() {
        return version;
    }
    
    public void extract(GL2 gl) {
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelview, 0);
        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);
        version= System.currentTimeMillis();
    }
    
    public boolean isObsoleted(long version) {
        return this.version != version;
    }
}
