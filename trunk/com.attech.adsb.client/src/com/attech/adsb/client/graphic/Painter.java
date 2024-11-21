/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic;


//import com.attech.client.graphics.IDrawableObject;
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.GLAutoDrawable;
//import com.jogamp.opengl.GLEventListener;
import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MouseContext;
import com.attech.adsb.client.common.MyFont;
import com.attech.adsb.client.common.RasterData;
import com.attech.adsb.client.config.AppConfig;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.Point2f;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.gl2.GLUgl2;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import java.awt.Dimension;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import static javax.media.opengl.GL.GL_BLEND;
import static javax.media.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static javax.media.opengl.GL.GL_SRC_ALPHA;


/**
 *
 * @author Saitama
 */
public class Painter implements GLEventListener {

    private boolean flag = false;
    
    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//    private int width, height;
    private GLAutoDrawable glad;
    private final List<IDrawableObject> drawList;
    private  GLUgl2 glu ;
    private final GLUT glut = new GLUT();
    
    private double zoom;
    private float panx = 0;
    private float pany = 0;
    private RGB bgColor;
    private boolean refresh;
    private final MyFont myfont = new MyFont();
    private GLParam glParam;
    
    private GraphicContext context;
          
    
    private Texture texture; // texture over the shape
    
    // Texture image flips vertically. Shall use TextureCoords class to retrieve the
    // top, bottom, left and right coordinates.
    private float textureTop;
    private float textureBottom;
    private float textureLeft;
    private float textureRight;

    public Painter() {
        drawList = new ArrayList<>();
        context = new GraphicContext();
	glParam = new GLParam();
        refresh = true;
    }
    
    public synchronized void addGraphicObject(IDrawableObject graphicObject) {
        
        this.drawList.add(graphicObject);
    }
    
    public void updatePan(float dx, float dy) {
        panx -=  dx;
        pany -=  dy;
        refresh = true;
    }
    
    
    @Override
    public void init(GLAutoDrawable glad) {
        GL2 gl = glad.getGL().getGL2();
        glu = new GLUgl2();
                
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glDepthFunc(GL2.GL_LEQUAL);
	gl.glClearDepth(1.0f);                                          // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);					// Enables Depth Testing
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);	// Really Nice Perspective Calculations
                     
        // Init byte buffer with just the letter F.
        RasterData.flMaintain = ByteBuffer.allocateDirect(RasterData.ArrowLevel.length);
        RasterData.flMaintain.put(RasterData.ArrowLevel);
        RasterData.flMaintain.rewind();

        RasterData.flDecend = ByteBuffer.allocateDirect(RasterData.Decend.length);
        RasterData.flDecend.put(RasterData.Decend);
        RasterData.flDecend.rewind();
        RasterData.flClimb = ByteBuffer.allocateDirect(RasterData.Climb.length);
        RasterData.flClimb.put(RasterData.Climb);
        RasterData.flClimb.rewind();        
        myfont.init(gl);
                        
        try {       
            File im = new File(Common.RES_TOPOGRAPHIC);
            BufferedImage image = ImageIO.read(im);
            texture = AWTTextureIO.newTexture(gl.getGLProfile(), image, true);
            texture.getWidth();
            image.flush();   
        } catch (IOException e) {
            e.printStackTrace();
        }      
                
        // Use linear filter for texture if image is larger than the original texture
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
        // Use linear filter for texture if image is smaller than the original texture
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        // Texture image flips vertically. Shall use TextureCoords class to retrieve
        // the top, bottom, left and right coordinates, instead of using 0.0f and 1.0f.
  
        // KHONG CO DOAN NAY SE BI SAI MAU
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);        
       
        TextureCoords textureCoords = texture.getImageTexCoords();
        textureTop = textureCoords.top();
        textureBottom = textureCoords.bottom();
        textureLeft = textureCoords.left();
        textureRight = textureCoords.right();        
         
        gl.getContext();
        glu = new GLUgl2();
        context.setGl(gl);
        
    }

    
    @Override
    public void dispose(GLAutoDrawable glad) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void textShowParam(GL2 gl) {
        int line=1,i;
        //gl.glRasterPos3f(-Parameter.WindowScreenWidth, Parameter.WindowScreenHeight - 20 , layer);
        gl.glPushAttrib(GL.GL_COLOR);

        gl.glColor4f(0, 0, 0.4f,0.1f);
        gl.glBegin(GL.GL_POLYGON);
        gl.glEnable(GL_BLEND);
        gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);  
        Dimension d = context.getScreenSize();
        
        gl.glVertex3i(-d.width, d.height, 90);
        gl.glVertex3i(-d.width + 730, d.height, 90);
        gl.glVertex3i(-d.width + 730, d.height - 400, 90);
        gl.glVertex3i(-d.width, d.height - 400, 90);
        gl.glEnd();
        
        gl.glColor3f(1.0f, 1.0f, 0.8f);        
        
        i = 28;
        gl.glRasterPos3f(-d.width, d.height - (line*i), 100);
        glut.glutBitmapString(com.sun.opengl.util.GLUT.BITMAP_8_BY_13, "ATETCH");
        
        line ++;
        gl.glRasterPos3f(-d.width, d.height - (line*i), 100);
        glut.glutBitmapString(com.sun.opengl.util.GLUT.BITMAP_8_BY_13, "WIN: " + Integer.toString(d.width) + " x " + Integer.toString(d.height));
        
        Point2f p = MouseContext.instance().getCurrentPoint();
        line ++;
        gl.glRasterPos3f(-d.width, d.height - (line*i), 100);
        if(p!=null)
        glut.glutBitmapString(com.sun.opengl.util.GLUT.BITMAP_8_BY_13, "Mouse GL: " + Float.toString(p.x) + " x " + Float.toString(p.y));
        
        line ++;
        gl.glRasterPos3f(-d.width, d.height - (line*i), 100);
        glut.glutBitmapString(com.sun.opengl.util.GLUT.BITMAP_8_BY_13, "Panx: " + Float.toString(panx) + " Pany " + Float.toString(pany));
        
        line ++;
        gl.glRasterPos3f(-d.width, d.height - (line*i), 100);
        glut.glutBitmapString(com.sun.opengl.util.GLUT.BITMAP_8_BY_13, "ZOOM: " + Double.toString(zoom));
              
        gl.glPopAttrib();
    }
    
    @Override
   public void display(GLAutoDrawable glad) {
               
        final GL2 gl = glad.getGL().getGL2();
        
        clearScreen(gl);
        context.setGl(gl);   
        
        AppConfig appConfig = Configuration.instance().getGraphic();
        if(appConfig.getBoolean("TOPOGRAPHIC")){
            gl.glPushMatrix();
            gl.glLoadIdentity();  

//            textShowParam(gl);  // for dev        

            gl.glScaled(zoom, zoom, zoom);
            gl.glTranslatef(10922 - panx ,1299 - pany, 0.0f);   // DI CHUYEN    

            changeZoom();

            gl.glEnable(GL2.GL_TEXTURE_2D);        
            gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject(gl));

            float PicWidth =  texture.getWidth()/4.5f;
            float PicHeight = texture.getHeight()/4.5f;       
            int dx = -80;
            int dy = 210;

            gl.glBegin(GL2.GL_QUADS);
                gl.glTexCoord3f(0.0f, 1.0f,-10.0f);                
                gl.glVertex3f(-PicWidth + dx, -PicHeight + dy, -10.0f);   // Bot l            
                gl.glTexCoord3f(0.0f, 0.0f,-10.0f);
                gl.glVertex3f(-PicWidth + dx, PicHeight + dy, -10.0f);            
                gl.glTexCoord3f(1.0f, 0.0f,-10.0f);
                gl.glVertex3f(PicWidth + dx, PicHeight + dy, -10.0f);            
                gl.glTexCoord3f(1.0f, 1.0f,-10.0f);
                gl.glVertex3f(PicWidth + dx, -PicHeight + dy, -10.0f);
            gl.glEnd();   
            gl.glDisable(GL2.GL_TEXTURE_2D);
            gl.glPopMatrix();
        }
                
        // HIEN THI CAC PHAN TRUOC DAY         
        for (IDrawableObject drawObject : this.drawList) {
            drawObject.draw(gl, context);
        }        
        gl.glFlush();               
    }

    @Override
    public void reshape(GLAutoDrawable glad, int x, int y, int width, int height) {
        this.glad = glad;        
        final GL2 gl = glad.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();               
        gl.glOrtho(-width, width, -height, height, -4000000.0f, 4000000.0f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, width, height);        
        
        context.setScreenSize(width, height);
        refresh = true;
    }

    public Point2f convertFromScreenToOpenGL(Point point) {
        
        double[] objPos = new double[3];
        glu.gluUnProject( (double)point.getX(), (double) glParam.viewport[3] - (double) point.getY(), 0.0,
                glParam.modelview, 0, glParam.getProjection(), 0,
                glParam.viewport, 0, objPos, 0);
        Point2f p = new Point2f((float) objPos[0], (float) objPos[1]);
        return (p);
    }
    
    private void clearScreen(GL2 gl) {
        gl.glClearColor(bgColor.red, bgColor.green, bgColor.blue, 0.0f);                
        
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        if (refresh) {
            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glLoadIdentity();
            gl.glScaled(zoom, zoom, zoom);
            gl.glTranslatef(-panx, -pany, 0.0f);
            context.getGlParam().extract(gl);
	    glParam.extract(gl);
            refresh = false;
        }
    }
    
    private void changeZoom() {
        if(zoom < 2.12){            
            if(pany > 2020){pany = 2020;}    // TOP
            if(pany < 986){pany = 986;}      // BOTTOM
            if(panx < 9779){panx = 9779;}    // LEFT
            if(panx > 11900){panx = 11900;}  // RIGHT          
        }
        else if(2.12 < zoom && zoom < 2.26){  
            if(pany > 2055){pany = 2055;}
            if(pany < 969){pany = 969;}    
            if(panx < 9669){panx = 9669;}
            if(panx > 12020){panx = 12020;}
        }
        else if(2.26 < zoom && zoom < 2.47){  
            if(pany > 2092){pany = 2092;}
            if(pany < 929){pany = 929;}    
            if(panx < 9606){panx = 9606;}
            if(panx > 12052){panx = 12052;}
        }
        else if(2.47 < zoom && zoom < 2.69){  
            if(pany > 2112){pany = 2112;}
            if(pany < 890){pany = 890;}    
            if(panx < 9575){panx = 9575;}
            if(panx > 12131){panx = 12131;}
        }
        else if(2.69 < zoom && zoom < 2.97){  
            if(pany > 2161){pany = 2161;}
            if(pany < 875){pany = 875;}    
            if(panx < 9484){panx = 9484;}
            if(panx > 12161){panx = 12161;}
        }
        else if(2.97 < zoom && zoom < 3.25){  
            if(pany > 2200){pany = 2200;}
            if(pany < 848){pany = 848;}    
            if(panx < 9411){panx = 9411;}
            if(panx > 12282){panx = 12282;}
        }
        else if(3.25 < zoom && zoom < 3.63){  
            if(pany > 2222){pany = 2222;}
            if(pany < 808){pany = 808;}    
            if(panx < 9380){panx = 9380;}
            if(panx > 12313){panx = 12313;}
        }
        else if(3.63 < zoom && zoom < 4.13){  
            if(pany > 2244){pany = 2244;}
            if(pany < 767){pany = 767;}    
            if(panx < 9313){panx = 9313;}
            if(panx > 12404){panx = 12404;}
        }
        else if(4.13 < zoom && zoom < 4.85){  
            if(pany > 2296){pany = 2296;}
            if(pany < 723){pany = 723;}    
            if(panx < 9252){panx = 9252;}
            if(panx > 12404){panx = 12404;}
        } 
        else if(4.85 < zoom && zoom < 5.74){  
            if(pany > 2323){pany = 2296;}
            if(pany < 696){pany = 723;}    
            if(panx < 9169){panx = 9252;}
            if(panx > 12535){panx = 12404;}
        } 
        else if(5.74 < zoom && zoom < 7.11){  
            if(pany > 2356){pany = 2296;}
            if(pany < 666){pany = 723;}    
            if(panx < 9119){panx = 9252;}
            if(panx > 12600){panx = 12404;}
        } 
        else if(7.11 < zoom){  
            if(pany > 2392){pany = 2296;}
            if(pany < 626){pany = 723;}    
            if(panx < 9039){panx = 9252;}
            if(panx > 12666){panx = 12404;}
        }              
    }

    //<editor-fold defaultstate="collapsed" desc=" Class properties ">
    /**
     * @return the zoom
     */
    public double getZoom() {
        return zoom;
    }

    /**
     * @param zoom the zoom to set
     */
    public void setZoom(double zoom) {
        this.zoom = zoom;
        refresh = true;
    }

    /**
     * @return the panx
     */
    public float getPanx() {
        return panx;
    }

    /**
     * @param panx the panx to set
     */
    public void setPanx(float panx) {
        this.panx = panx;
        this.refresh = true;
    }

    /**
     * @return the pany
     */
    public float getPany() {
        return pany;
    }

    /**
     * @param pany the pany to set
     */
    public void setPany(float pany) {
        this.pany = pany;
        this.refresh = true;
    }

    /**
     * @return the bgColor
     */
    public RGB getBgColor() {
        return bgColor;
    }

    /**
     * @param bgColor the bgColor to set
     */
    public void setBgColor(RGB bgColor) {
        this.bgColor = bgColor;
    }

    public GraphicContext getContext() {
	return this.context;
    }
    
    //</editor-fold>
     
}
