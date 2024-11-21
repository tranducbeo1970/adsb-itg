/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NhuongND
 */
public class FunctionOpenGL {
    public static GLParam glparam = new GLParam();
     public static void drawArc(GL2 gl, Point2f pos1, float km, int linemode, int anglefrom, int angleto) {         
        gl.glBegin(linemode);  
        Point2f p;
        if(anglefrom > angleto) angleto = angleto + 360;
        for(int angle = anglefrom;angle <= angleto; angle++){
            p =Distance.Destination(pos1, km, angle);
            gl.glVertex3f(p.x , p.y, 1.0f);
        }
        gl.glEnd();                  
    }
     
    public static void drawArcDotLine(GL2 gl, Point2f pos1, float km, int linemode, int anglefrom, int angleto) { 
        gl.glEnable(GL2.GL_LINE_STIPPLE);
        gl.glLineStipple(1, (short) 0xF0F0);
        gl.glBegin(linemode);  
        Point2f p;
        for(int angle = anglefrom;angle <= angleto; angle++){
            p =Distance.Destination(pos1, km, angle);
            gl.glVertex3f(p.x , p.y, 1.0f);
        }
        gl.glEnd();
        gl.glDisable(GL2.GL_LINE_STIPPLE);                 
    }
     
     public static void drawArcTransparen(GL2 gl, Point2f pos1, float km, int linemode, int anglefrom, int angleto) {         
        gl.glBegin(linemode); 
        Point2f p;
        for(int angle = anglefrom;angle < angleto - 1; angle++){
            p =Distance.Destination(pos1, km, angle);
            gl.glVertex3f(p.x , p.y, -10000.0f);
        }
        gl.glEnd();                  
    }    
     
    public static void drawPointProc(GL2 gl, Point2f point, String color1, int size, GraphicContext context) {   
        float x = point.x;
        float y = point.y;    
        int size1 = 2*size;
        RGB color = new RGB(color1);
        gl.glColor3f(color.red, color.green, color.blue);
        gl.glRasterPos3f(x, y, 0);        
        Point2f p = context.calculateIdentityPosition(gl, x, y);        
        gl.glPushMatrix();
        gl.glLoadIdentity();           
            gl.glBegin(GL2.GL_LINE_STRIP);
                gl.glVertex3f((float)p.x - size ,(float) p.y + size, 2000.0f);
                gl.glVertex3f((float)p.x + size ,(float) p.y + size, 2000.0f);
                gl.glVertex3f((float)p.x + size ,(float) p.y - size, 2000.0f);
                gl.glVertex3f((float)p.x - size ,(float) p.y - size, 2000.0f);
                gl.glVertex3f((float)p.x - size ,(float) p.y + size, 2000.0f);
            gl.glEnd();
            gl.glBegin(GL2.GL_LINES);
                gl.glVertex3f((float)p.x - size ,(float) p.y + size, 2000.0f);
                gl.glVertex3f((float)p.x - size1 ,(float) p.y + size1, 2000.0f);
            gl.glEnd();            
            gl.glBegin(GL2.GL_LINES);
                gl.glVertex3f((float)p.x + size ,(float) p.y + size, 2000.0f);
                gl.glVertex3f((float)p.x + size1 ,(float) p.y + size1, 2000.0f);
            gl.glEnd();
            gl.glBegin(GL2.GL_LINES);
                gl.glVertex3f((float)p.x + size ,(float) p.y - size, 2000.0f);
                gl.glVertex3f((float)p.x + size1 ,(float) p.y - size1, 2000.0f);
            gl.glEnd();
            gl.glBegin(GL2.GL_LINES);       
                gl.glVertex3f((float)p.x - size ,(float) p.y - size, 2000.0f);
                gl.glVertex3f((float)p.x - size1 ,(float) p.y - size1, 2000.0f);            
            gl.glEnd();               
        gl.glPopMatrix();                
    }
    
    public static void drawPointApp(GL2 gl, Point2f point, String color1, int size, GraphicContext context) {   
        float x = point.x;
        float y = point.y;   
        RGB color = new RGB(color1);
        gl.glColor3f(color.red, color.green, color.blue);
        gl.glRasterPos3f(x, y, 0);        
        Point2f p = context.calculateIdentityPosition(gl, x, y);        
        gl.glPushMatrix();
        gl.glLoadIdentity();           
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex3f((float)p.x - size ,(float) p.y + size, 2000.0f);
                gl.glVertex3f((float)p.x + size ,(float) p.y + size, 2000.0f);
                gl.glVertex3f((float)p.x + size ,(float) p.y - size, 2000.0f);
                gl.glVertex3f((float)p.x - size ,(float) p.y - size, 2000.0f);
                gl.glVertex3f((float)p.x - size ,(float) p.y + size, 2000.0f);
            gl.glEnd();               
        gl.glPopMatrix();                
    }
    
    public static void drawFixPoint(GL2 gl, Point2f point, String color1, GraphicContext context) {   
        float x = point.x;
        float y = point.y;   
        RGB color = new RGB(color1);
        gl.glColor3f(color.red, color.green, color.blue);
        gl.glRasterPos3f(x, y, 0);        
        Point2f p = context.calculateIdentityPosition(gl, x, y);        
        gl.glPushMatrix();
        gl.glLoadIdentity();           
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex3f((float)p.x ,(float) p.y + 10, 2000.0f);
                gl.glVertex3f((float)p.x + 8 ,(float) p.y -6, 2000.0f);
                gl.glVertex3f((float)p.x -8 ,(float) p.y - 6, 2000.0f);
            gl.glEnd();               
        gl.glPopMatrix();   
    }
    
   public static void drawVispPoint(GL2 gl, Point2f point, String color1, int size, GraphicContext context) {   
        float x = point.x;
        float y = point.y;   
        RGB color = new RGB(color1);
        gl.glColor3f(color.red, color.green, color.blue);
        gl.glRasterPos3f(x, y, 0);        
        Point2f p = context.calculateIdentityPosition(gl, x, y);        
        gl.glPushMatrix();
        gl.glLoadIdentity();           
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex3f((float)p.x - size ,(float) p.y + size, 2000.0f);
                gl.glVertex3f((float)p.x + size ,(float) p.y + size, 2000.0f);
                gl.glVertex3f((float)p.x + size ,(float) p.y - size, 2000.0f);
                gl.glVertex3f((float)p.x - size ,(float) p.y - size, 2000.0f);
                gl.glVertex3f((float)p.x - size ,(float) p.y + size, 2000.0f);
            gl.glEnd();               
        gl.glPopMatrix();    
    }
    
    public static void drawLine(GL2 gl, Point2f p1, Point2f p2, int LineMode, float layer) {
       gl.glBegin(LineMode);
       gl.glVertex3f(p1.x,p1.y,layer);
       gl.glVertex3f(p2.x,p2.y,layer);
       gl.glEnd();
   }
    
   public static void drawDuongBang(GL2 gl, List<Point2f> points,float layer) {
        gl.glColor3f(1.0f,0.0f,0.0f);
        for (int j = 0; j < points.size() - 1; j++) {
            FunctionOpenGL.drawLine(gl, points.get(j), points.get(j + 1), GL2.GL_LINE_STRIP,layer);
            j++;
        }
    }
    
     public static void drawDuongLan(GL2 gl, List<Point2f> points,int index,RGB rgb,float layer) {
        float MajorTick = 0.8f;
        float MinorTick = 0.4f;
        
        double HuongTick = 90;
        if(index == 1 || index == 2){
            HuongTick = -90;
        }
        
        // Color
        gl.glColor3f(rgb.red,rgb.green,rgb.blue);

        List<Double> Bearings = new ArrayList<>();        
        for (int j = 0; j < points.size(); j++) {
            Bearings.add(Distance.Bearing(points.get(j + 1), points.get(j)));
            Bearings.add(Distance.Bearing(points.get(j), points.get(j + 1)));
            j++;
        }        
        double bear = Bearings.get(index);        
        Point2f pos = Distance.Destination(points.get(0), 25 * 1.852f, bear);
        FunctionOpenGL.drawLine(gl, points.get(index), pos, GL2.GL_LINE_STRIP,layer);
        FunctionOpenGL.drawLine(gl, points.get(index), Distance.Destination(points.get(index), 0.5, bear + 90), GL2.GL_LINE_STRIP,layer);
        for (int n = 1; n <= 5; n++) {
            Point2f posTemp = Distance.Destination(points.get(0), 5 * n * 1.852f, bear);
            FunctionOpenGL.drawLine(gl, posTemp, Distance.Destination(posTemp, MajorTick, bear + HuongTick), GL2.GL_LINE_STRIP,layer);
        }
        for (int n = 1; n <= 10; n++) {
            Point2f posTemp = Distance.Destination(points.get(0), n * 1.852f, bear);
            FunctionOpenGL.drawLine(gl, posTemp, Distance.Destination(posTemp, MinorTick, bear + HuongTick), GL2.GL_LINE_STRIP,layer);
        }
    }
     
     public static Point2f tinhToaDoOpenGLKhiLoadIdentity(GL2 gl, float x, float y) {
        double[] win = new double[3];
        double[] objPos = new double[3];
        GLU glu = new GLU();
        Point2f p = new Point2f();

        // Doi gia tri tu openGL sang toa do man hinh tai vi tri can hien thi
        // Luu lai gia tri nay de thuc hien
        // zoom pan sau do tinh lai gia tri OPENGL moi
        // dang nhe la hien thi luon

        getRectOfOpenGL(gl);
        
        // lay toa do man hinh
        glu.gluProject(x, y, 0, glparam.modelview, 0, glparam.projection, 0, glparam.viewport, 0, win, 0);

        // Tro ve trang thai Normal Identity
        gl.glPushMatrix();
        gl.glLoadIdentity();
        getRectOfOpenGL(gl);
        // Tinh toan gia tri moi cua OpenGL dua tren 
        // vi tri man hinh da tinh toan truoc trong truong hop da bi thay doi
        // MATRIX ve ma tran don vi
        //
        glu.gluUnProject((double) win[0], (double) win[1], 0.0, glparam.modelview, 0, glparam.projection, 0, glparam.viewport, 0, objPos, 0);
        // tra lai gia tri moi sau khi da tinh toan
        gl.glPopMatrix();
        p.y = (float) objPos[1];
        p.x = (float) objPos[0];
        return p;
    }
     
    public static void getRectOfOpenGL(GL2 gl) {
        gl.glGetIntegerv(GL2.GL_VIEWPORT, glparam.viewport, 0);
        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, glparam.modelview, 0);
        gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, glparam.projection, 0);
    }
    
    public static void drawFixPointSector(GL2 gl, Point2f point, String color1, GraphicContext context) {   
        float x = point.x;
        float y = point.y;   
        RGB color = new RGB(color1);
        gl.glColor3f(color.red, color.green, color.blue);
        gl.glRasterPos3f(x, y, 0);        
        Point2f p = context.calculateIdentityPosition(gl, x, y);        
        gl.glPushMatrix();
        gl.glLoadIdentity();           
            FunctionOpenGL.drawArc(gl, p, 6, GL2.GL_POLYGON, 0, 360);               
        gl.glPopMatrix();   
    }
     
}
