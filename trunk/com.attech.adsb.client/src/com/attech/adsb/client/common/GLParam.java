/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.jogamp.opengl.GL2;

/**
 *
 * @author dungnt
 */
public class GLParam {

    public float mouseglx;
    public float mousegly;
    public int viewport[] = new int[4];
    public double modelview[] = new double[16];
    public double projection[] = new double[16];
    
    
    float mouseglxClicked;
    float mouseglyClicked;
    
    public float mouseWinXClicked;
    public float mouseWinYClicked;
    
    float centerGlx;
    float centerGly;
    public float aspectRatio;


    public void getCurrentOpenGLMatrixValue(GL2 gl) {
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);
        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelview, 0);
        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projection, 0);
    }
    
    
    public int viewportSecond[] = new int[4];
    public double modelviewSecond[] = new double[16];
    public double projectionSecond[] = new double[16];
    float centerSecondGlx;
    float centerSecondGly;
    
    public void getCurrentOpenGLMatrixValueSecond(GL2 gl) {
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewportSecond, 0);
        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, modelviewSecond, 0);
        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, projectionSecond, 0);
    }
}
