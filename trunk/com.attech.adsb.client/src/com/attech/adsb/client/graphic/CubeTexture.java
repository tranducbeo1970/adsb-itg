/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic;

import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class CubeTexture implements GLEventListener {

    private GLU glu = new GLU();
    private float xrot, yrot, zrot;
    private int texture;    
    private Texture t; 

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();                       // Reset The View
        gl.glTranslatef(0f, 0f, -5.0f);

//        gl.glColor3f(1f, 1f, 1f);
//        gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
//        
//        gl.glBegin(GL2.GL_QUADS);
//            gl.glTexCoord2f(0.0f, 1.0f);
//            gl.glVertex3f(-3.0f, -2.0f, 1.0f);
//            
//            gl.glTexCoord2f(0.0f, 0.0f);
//            gl.glVertex3f(-3.0f, 2.0f, 1.0f);
//            
//            gl.glTexCoord2f(1.0f, 0.0f);
//            gl.glVertex3f(3.0f, 2.0f, 1.0f);
//            
//            gl.glTexCoord2f(1.0f, 1.0f);
//            gl.glVertex3f(3.0f, -2.0f, 1.0f);
//        gl.glEnd();
//        
//        gl.glFlush();

        gl.glPointSize(30f);
        
        gl.glBegin(GL2.GL_POINT);
            gl.glVertex2f(0.5f, -0.5f);
        gl.glEnd();
    }
      

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // method body
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        
//        try {
//            File im = new File("D:\\vn.jpg ");
//            //File im = new File("D:\\Temp\\Map\\GLOBALeb3colshade.jpg");
//            BufferedImage image = ImageIO.read(im);
//            t = AWTTextureIO.newTexture(gl.getGLProfile(), image, true);
//            t.getWidth();
//            //Texture t = TextureIO.newTexture(im, true);
//            texture = t.getTextureObject(gl);
//            
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
//        if (height <= 0) {
//            height = 1;
//        }
//        final float h = (float) width / (float) height;
//        gl.glViewport(0, 0, width, height);
//        gl.glMatrixMode(GL2.GL_PROJECTION);
//        gl.glLoadIdentity();
//        glu.gluPerspective(45.0f, h, 1.0, 20.0);
//        gl.glMatrixMode(GL2.GL_MODELVIEW);
//        gl.glLoadIdentity();
        
        if (height <= 0){ height = 1; }   
        gl.glViewport(0, 0, width, height); ////// 
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1f, 1f, -1f, 1f, -1.0f, 1.0f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, width, height);
    }

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas 
        final GLJPanel glcanvas = new GLJPanel(capabilities);
        CubeTexture r = new CubeTexture();
        glcanvas.addGLEventListener(r);
        glcanvas.setSize(400, 400);
        final JFrame frame = new JFrame(" Textured Cube");
        frame.getContentPane().add(glcanvas);
        frame.setSize(600, 600);
        frame.setVisible(true);
//        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
//        animator.start();
    }
}
