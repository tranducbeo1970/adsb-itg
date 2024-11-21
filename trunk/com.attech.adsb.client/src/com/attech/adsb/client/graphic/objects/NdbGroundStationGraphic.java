/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.ExceptionHandler;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.config.GroundStation;
import com.attech.adsb.client.config.GroundStationConfig;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Saitama
 */
public class NdbGroundStationGraphic extends IDrawableObject {

    private static final MLogger logger = MLogger.getLogger(NdbGroundStationGraphic.class);
    
    private BufferedImage bufferedImage;
    private ByteBuffer bb;
    
    private int zIndex;
    private int w = 0;
    private int h = 0;
    private int texture = 13;
    
    private List<NdbGraphic> ndbStations;
    
    public NdbGroundStationGraphic(GroundStationConfig config) {
        try {
            bufferedImage = ImageIO.read(new File(config.getIcon()));
            w = bufferedImage.getWidth();
            h = bufferedImage.getHeight();

            WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, w, h, 4, null);
            ComponentColorModel colorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8, 8}, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
            BufferedImage dukeImg = new BufferedImage(colorModel, raster, false, null);

            Graphics2D g = dukeImg.createGraphics();
            g.drawImage(bufferedImage, null, null);
            DataBufferByte dukeBuf = (DataBufferByte) raster.getDataBuffer();

            byte[] dukeRGBA = dukeBuf.getData();
            bb = ByteBuffer.wrap(dukeRGBA);
            bb.position(0);
            bb.mark();

            ndbStations = new ArrayList<>();
            for (GroundStation stationConfig : config.getGroundStations()) {
                ndbStations.add(new NdbGraphic(stationConfig, w, h));
            }

        } catch (IOException ex) {
            ExceptionHandler.handle(ex, this.getClass());
        }
    }
    
    public void setEnableLabel(Boolean enable) {
	for (NdbGraphic stationGraphic : this.ndbStations) {
	    stationGraphic.setEnableLabel(enable);
	}
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
//        final GL2 gl = glad.getGL().getGL2();
        calculate(gl, context);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_BLEND);

//        if (isChanged()) {
	gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
	gl.glPixelStorei(GL2.GL_UNPACK_ALIGNMENT, 1);
	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
	gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
	gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
	gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, w, h, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, bb);
//            setChanged(false);
//        }

        gl.glPushMatrix();
        gl.glLoadIdentity();
        
        for (NdbGraphic stationGraphic : this.ndbStations) {
            stationGraphic.draw(gl, context);
        }

        gl.glPopMatrix();
    }
    
    
    private void calculate( GL2 gl, GraphicContext context) {
        if (!context.getGlParam().isObsoleted(version)) return;
        try {
            logger.debug("Recalculate due to version changed");
            for (NdbGraphic stationGraphic : this.ndbStations) {
                stationGraphic.calculate(gl, context);
            }
        } finally {
            this.version = context.getGlParam().getVersion();
        }
    }
}
