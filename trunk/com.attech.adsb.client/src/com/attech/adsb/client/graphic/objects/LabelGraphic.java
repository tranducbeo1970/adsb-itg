/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Label;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * http://www.felixgers.de/teaching/jogl/fontsDisplaylist.html
 * @author Saitama
 */
public class LabelGraphic extends IDrawableObject{

    
    
    protected String content;
    protected Point2f point;
    protected RGB color;
    protected float zIndex;
    private int paddingX;
    private int paddingY;
    private float xIndex;
    private float yIndex;
    private GLUT glut = new GLUT();
    private int fontSize;
    private boolean drawBackground = false;
    private RGB backgroundColor;
    private int contenCharacterSize = 0;
    private int contentHeight = 0;
    
    
    public LabelGraphic() {
    }
    
    public LabelGraphic(Label label) {
        this.content = label.getContent();
        this.color = new RGB(label.getColor());
        this.zIndex = label.getzIndex();
        
        if (label.getX() != null && label.getY() != null) {
            this.point = new Point2f(Convertor.fromWGS842OpenGL(label.getX()), Convertor.fromWGS842OpenGL(label.getY()));
        }
        this.paddingX = label.getPaddingX();
        this.paddingY = label.getPaddingY();
        this.fontSize = label.getFontSize();
        this.enable = label.getEnabled();
        
        this.drawBackground = label.getDrawBackground() == null ? false :label.getDrawBackground();
        this.contenCharacterSize = label.getContentUnitSize() == null ? 0 : label.getContentUnitSize();
        this.backgroundColor = new RGB(label.getBackgroundColor() == null ? "#FFFFFF" : label.getBackgroundColor());
        this.contentHeight = label.getContentHeight() == null ? 0 : label.getContentHeight();
        
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {

        if (!this.enable) return;
//        final GL2 gl = glad.getGL().getGL2();
        gl.glColor3f(color.red, color.green, color.blue);
        
        Point2f p1 = context.calculateIdentityPosition(gl, xIndex, yIndex);
        
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glRasterPos3f(p1.x + paddingX, p1.y + paddingY, zIndex);
        glut.glutBitmapString(this.fontSize, content);
        gl.glPopMatrix();
        
        if (!this.drawBackground) return;
        
//        Point2f p1 = context.calculateIdentityPosition(gl, xIndex, yIndex);
//        gl.glPushMatrix();
//        gl.glLoadIdentity();
        
        int i = (this.content.length() + 2 )  * this.contenCharacterSize;
        gl.glColor3f(this.backgroundColor.red, this.backgroundColor.green, this.backgroundColor.blue);
        
        
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3f(p1.x, p1.y - 4, this.zIndex - 1 );
        gl.glVertex3f(p1.x + i, p1.y - 4, this.zIndex - 1);
        gl.glVertex3f(p1.x + i, p1.y + this.contentHeight, this.zIndex - 1);
        gl.glVertex3f(p1.x, p1.y + contentHeight, this.zIndex - 1);
        gl.glEnd();
//        gl.glPopMatrix();
    }
    
    public void setCalculatedPosition(float x, float y) {
        xIndex = x ;
        yIndex = y ;
    }
    
    public void setPositionByZoom(float zoom) {
        xIndex = xIndex + zoom*10;
        yIndex = yIndex + zoom*10;
    }
    


    //<editor-fold defaultstate="collapsed" desc=" Class properties ">

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return this.content;
    }

    /**
     * @param point 
     */
    public void setPointOpenGL(Point2f point) {
        this.point = point;
        changed = true;
        
    }

    /**
     * @param color the color to set
     */
    public void setColor(RGB color) {
        this.color = color;
    }

    /**
     * @param zIndex the zIndex to set
     */
    public void setzIndex(float zIndex) {
        this.zIndex = zIndex;
    }    
    
    public void setFontSize(int fontSize) {
	this.fontSize = fontSize;
    }
    
    /**
     * @return the drawBackground
     */
    public boolean isDrawBackground() {
        return drawBackground;
    }

    /**
     * @param drawBackground the drawBackground to set
     */
    public void setDrawBackground(boolean drawBackground) {
        this.drawBackground = drawBackground;
    }

    /**
     * @return the backgroundColor
     */
    public RGB getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(RGB backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the contenCharacterSize
     */
    public int getContenCharacterSize() {
        return contenCharacterSize;
    }

    /**
     * @param contenCharacterSize the contenCharacterSize to set
     */
    public void setContenCharacterSize(int contenCharacterSize) {
        this.contenCharacterSize = contenCharacterSize;
    }

    /**
     * @return the contentHeight
     */
    public int getContentHeight() {
        return contentHeight;
    }

    /**
     * @param contentHeight the contentHeight to set
     */
    public void setContentHeight(int contentHeight) {
        this.contentHeight = contentHeight;
    }
    
    //</editor-fold>

}
