/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.AppContext;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.RasterData;
import com.attech.adsb.client.common.TrackStatus;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.TargetLabelConfig;
import com.attech.adsb.client.config.TargetLabelDisplay;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Saitama
 */
public class TargetLabel extends IDrawableObject {

    
    private final static TargetLabelConfig config = Configuration.instance().getTargetConfig().getTargetLabelConfig();

    private final GLUT glut = new GLUT();
    private final DecimalFormat flightLevelFormat = new DecimalFormat("###");
    private boolean obsoleted;
//    private TargetLabelConfig config;

    private RGB warningColor;
    private RGB connectorColor;
    private RGB color;
    private RGB controlledColor;
    private RGB transferFromColor;
    private RGB transferToColor;
    private RGB obsoletedColor;
    
    private float textHeight = 25;
//    private float callsignWidth = 150;
//    private float altitudeWidth = 145;
//    private float speedWidth = 145;
//    private float hdgNoteWidth = 50;
    private float connectorSize = 120;
    private float connectorWeight;
    private float connectorAngleStep = 10;
    private float labelPadding;
    private float connectorAngle = 30;
    private float labelHeight = 100;
    private float labelWidth = 290;
    
//    private Point2f callsignLocation;
//    private Point2f ssrLocation;
//    private Point2f altitudeLocation;
//    private Point2f caltLocation;
//    private Point2f coaltLocation;
//    private Point2f speedLocation;
//    private Point2f hdgNoteLocation;
//    private Point2f infLocation;
//    private Point2f errLocation;

    private String callSign;
    private String address;
    private String ssrCode ="S";
    
    private String controller;
    private String flightLevel;
    private double verticalRate;
    private double velocity;
    private double calt;    
    private double coalt;
    private String hdgNote = "HDG";
    private String inf = "INF";
    private String err = "";
    private TrackStatus trackStatus;
//    private boolean flicked = false;    
//    private double barometic_vertial_reate = 0d;
    private TargetLabelDisplay targetLabelDisplay;
    
    private Point2f targetPoint;  
    private int cldsYlocation;
    
    private float x;
    private float y;
    private float z;
    
    private final Point2f [] labelRectang = new Point2f[4];
    private List<String> lines;
    private List<String> warning;
    
    public TargetLabel() {
        this.color = new RGB(config.getColor());
        this.warningColor = new RGB(config.getWarningColor());
        this.controlledColor = new RGB(config.getControlledColor());
        this.transferFromColor = new RGB(config.getTransferFromColor());
        this.transferToColor = new RGB(config.getTransferToColor());
        this.textHeight = config.getTextHeight();
        this.connectorSize = config.getConnectorSize();
        this.connectorWeight = config.getConnectorWeight();
        this.connectorColor = new RGB(config.getConnectorColor());
        this.obsoletedColor = new RGB(config.getObsoletedColor());

        this.connectorAngle = config.getConnectorAngle();
        this.labelWidth = config.getLabelWidth();
        this.connectorAngleStep = config.getConnectorAngleStep();
        this.z = config.getZindex();

        lines = new ArrayList<>();
        warning = new ArrayList<>();
    }
    
//    public TargetLabel(TargetLabelConfig config) {
//        this();
//        this.color = new RGB(config.getColor());
//        this.controlledColor = new RGB(config.getControlledColor());
//        this.transferFromColor = new RGB(config.getTransferFromColor());
//        this.transferToColor = new RGB(config.getTransferToColor());
//        this.textHeight = config.getTextHeight();
////        this.callsignWidth = config.getCallsignWidth();
////        this.altitudeWidth = config.getAltitudeWidth();
////        this.speedWidth = config.getSpeedWidth();
////        this.hdgNoteWidth = config.getHdgNoteWidth();
//        this.connectorSize = config.getConnectorSize();
//        this.connectorWeight = config.getConnectorWeight();
//        this.connectorColor = new RGB(config.getConnectorColor());
//
//        this.connectorAngle = config.getConnectorAngle();
////        this.labelHeight = config.getLabelHeight();
//        this.labelWidth = config.getLabelWidth();
//        this.connectorAngleStep = config.getConnectorAngleStep();
//
//    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable() || targetLabelDisplay.isDisplayNothing()) return;
        
        // Connector
        if (this.obsoleted) {
            this.obsoletedColor.setColor(gl);
        } else {
            this.connectorColor.setColor(gl);
        }
        
        gl.glLineWidth(this.connectorWeight);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex3f(targetPoint.x, targetPoint.y, z);
        gl.glVertex3f(x, y, z);
        gl.glEnd();

        
        if (this.obsoleted) {
            this.obsoletedColor.setColor(gl);
        } else {
            this.color.setColor(gl);
        }
        int index = 1;
        for (String str : this.lines) {
            gl.glRasterPos3f(labelRectang[0].x, labelRectang[0].y - index * textHeight, z);
            glut.glutBitmapString(index == 0 ? GLUT.BITMAP_9_BY_15 : GLUT.BITMAP_8_BY_13, str);
            index++;
        }
        
        // Refactor
        if (targetLabelDisplay.isTargetALTVisible() && AppContext.instance().isCldsWarning()) {
            gl.glRasterPos3f(labelRectang[0].x + this.flightLevel.length() * 19, labelRectang[0].y - cldsYlocation * textHeight, z);
            if (this.verticalRate > 1 && context.isFlip()) {
                RasterData.flClimb.rewind();
                gl.glBitmap(10, 12, 0.0f, 0.0f, 10.0f, 0.0f, RasterData.flClimb);
            } else if (this.verticalRate < -1 && context.isFlip()) {
                RasterData.flDecend.rewind();
                gl.glBitmap(10, 12, 0.0f, 0.0f, 10.0f, 0.0f, RasterData.flDecend);
            } else if (1 >= this.verticalRate && this.verticalRate >= -1) {
                RasterData.flMaintain.rewind();
                gl.glBitmap(10, 12, 0.0f, 0.0f, 10.0f, 0.0f, RasterData.flMaintain);
            }

//	    if (this.verticalRate == 0) { //this. verticalRate
//		RasterData.flMaintain.rewind();
//		gl.glBitmap(10, 12, 0.0f, 0.0f, 10.0f, 0.0f, RasterData.flMaintain);
//	    } else {
//		if (context.isFlip()) {
//		    if (this.verticalRate > 1) {
//			RasterData.flClimb.rewind();
//			gl.glBitmap(10, 12, 0.0f, 0.0f, 10.0f, 0.0f, RasterData.flClimb);
//
//		    } else if (this.verticalRate < -1) {
//			RasterData.flDecend.rewind();
//			gl.glBitmap(10, 12, 0.0f, 0.0f, 10.0f, 0.0f, RasterData.flDecend);
//		    }
//		}
//	    }
        }
        
        if (targetLabelDisplay.isTargetCTRLVisible() && this.controller != null && !this.controller.isEmpty()) {
            
            switch(this.trackStatus) {
                case CONTROLED:
                    this.controlledColor.setColor(gl);
                    break;
                case TRANSFER_FROM_THIS:
                    this.transferFromColor.setColor(gl);
                    break;
                case TRANSFER_TO_THIS:
                    this.transferToColor.setColor(gl);
                    break;
                default:
                    this.color.setColor(gl);
                    break;
            }
            gl.glRasterPos3f(labelRectang[0].x, labelRectang[0].y - index * textHeight, z);
            glut.glutBitmapString(GLUT.BITMAP_8_BY_13, this.controller);
            index++;
        }
	
        
	if (context.isFlip() && !this.warning.isEmpty()) {
            this.warningColor.setColor(gl);
	    for (String str : this.warning) {
		gl.glRasterPos3f(labelRectang[0].x, labelRectang[0].y - index * textHeight, z);
		glut.glutBitmapString(GLUT.BITMAP_8_BY_13, str);
		index++;
	    }
	}
    }
    
//    private String getDisplayingSSR() {
//        return String.format("%s %s", this.address, this.ssrCode);
//    }
    
//    public void setFlicked(boolean flicked) {
//        this.flicked = flicked;
//    }
    
    public void calculate(Point2f targetPoint) {
	this.buildUpLabel();
	labelHeight = (this.lines.size()  + this.warning.size())* textHeight;
        this.targetPoint = targetPoint;
        double dx = connectorSize * Math.sin(Math.toRadians(connectorAngle));
        double dy = connectorSize * Math.cos(Math.toRadians(connectorAngle));
        
        x = (float) (this.targetPoint.x + dx);
        y = (float) (this.targetPoint.y + dy);

        if (connectorAngle >= 0 && connectorAngle < 90) {
            labelRectang[0] = new Point2f(x, y + labelHeight);
            labelRectang[1] = new Point2f(x + labelWidth, y + labelHeight);
            labelRectang[2] = new Point2f(x + labelWidth, y);
            labelRectang[3] = new Point2f(x, y);
        } else if (connectorAngle >= 90 && connectorAngle < 180) {
            labelRectang[0] = new Point2f(x, y);
            labelRectang[1] = new Point2f(x + labelWidth, y);
            labelRectang[2] = new Point2f(x + labelWidth, y - labelHeight);
            labelRectang[3] = new Point2f(x, y - labelHeight);
        } else if (connectorAngle >= 180 && connectorAngle < 270) {
            labelRectang[0] = new Point2f(x - labelWidth, y);
            labelRectang[1] = new Point2f(x, y);
            labelRectang[2] = new Point2f(x, y - labelHeight);
            labelRectang[3] = new Point2f(x - labelWidth, y - labelHeight);
        } else {
            labelRectang[0] = new Point2f(x - labelWidth, y + labelHeight);
            labelRectang[1] = new Point2f(x, y + labelHeight);
            labelRectang[2] = new Point2f(x, y);
            labelRectang[3] = new Point2f(x - labelWidth, y);
        }

//        callsignLocation.set(labelRectang[0].x, labelRectang[0].y - textHeight);
//        ssrLocation.set(labelRectang[0].x + callsignWidth, labelRectang[0].y - textHeight);
//        altitudeLocation.set(labelRectang[0].x, labelRectang[0].y - cldsYlocation * textHeight);
//        caltLocation.set(labelRectang[0].x + altitudeWidth, labelRectang[0].y - 2 * textHeight);
//        speedLocation.set(labelRectang[0].x, labelRectang[0].y - 3 * textHeight);
//        coaltLocation.set(labelRectang[0].x + speedWidth, labelRectang[0].y - 3 * textHeight);
//        hdgNoteLocation.set(labelRectang[0].x, labelRectang[0].y - 4 * textHeight);
//        infLocation.set(labelRectang[0].x + hdgNoteWidth, labelRectang[0].y - 4 * textHeight);
//        errLocation.set(labelRectang[0].x, labelRectang[0].y + 12);

    }
    
    public void rotatingLabel() {
        connectorAngle = (connectorAngle + this.connectorAngleStep) % 360;
    }
    
    private void buildUpLabel() {
	int count = 0;
	lines.clear();
	
	if (this.targetLabelDisplay.isTargetCallsignVisible() && (this.callSign != null && !this.callSign.isEmpty() )) {
	    lines.add(this.callSign);
	    count++;
	}

	if (targetLabelDisplay.isTargetSSRVisible()) {
	    lines.add(String.format("%s A%s", this.address, this.ssrCode));
	    count++;
	}

	if (targetLabelDisplay.isTargetALTVisible() && this.flightLevel != null) {
	    lines.add(String.format("%s  %s", this.flightLevel, flightLevelFormat.format(this.verticalRate)));
	    
	    count++;
	    cldsYlocation = count;
	    
	}
	
	if (targetLabelDisplay.isTargetSPDVisible()) {
	    lines.add(String.format("%s", flightLevelFormat.format(this.velocity)));
	    count++;
	}
	
//	if (targetLabelDisplay.isTargetCTRLVisible() && (this.controller != null && !this.controller.isEmpty())) {
//	    lines.add(String.format("%s", this.controller));
//	    count++;
//	}

	
//	this.warning.clear();
//	this.warning.add("AMA Danger");
    }
    
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">


    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setFlightLevel(Float flightLevel) {
        if (flightLevel == null) this.setFlightLevel("");
        else 
            this.setFlightLevel(flightLevelFormat.format(flightLevel));
    }

    public void setVerticalRate(double verticalRate) {
        this.verticalRate = verticalRate;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }
  
    public void setTargetPoint(Point2f targetPoint) {
        this.targetPoint = targetPoint;
    }

     public void setConnectorSize(float connectorSize) {
        this.connectorSize = connectorSize;
    }

    public void setConnectorWeight(float connectorWeight) {
        this.connectorWeight = connectorWeight;
    }

    public void setConnectorColor(RGB connectorColor) {
        this.connectorColor = connectorColor;
    }

    public boolean isWarning() {
       return  this.warning.size() > 0;
    }

      public void setLabelPadding(float labelPadding) {
        this.labelPadding = labelPadding;
    }


    /**
     * @param connectorAngle the connectorAngle to set
     */
    public void setConnectorAngle(float connectorAngle) {
        this.connectorAngle = connectorAngle;
    }
 
    public void setCalt(double calt) {
        this.calt = calt;
    }

    public void setCoalt(double coalt) {
        this.coalt = coalt;
    }

    public void setHdgNote(String hdgNote) {
        this.hdgNote = hdgNote;
    }

    public void setInf(String inf) {
        this.inf = inf;
    }
    
    public void setSsrCode(String ssrCode) {
        this.ssrCode = ssrCode;
    }

    public void setFlightLevel(String flightLevel) {
        this.flightLevel = flightLevel;
    }
        
    public void setErr(String err) {
        this.err = err;
    }

    public void setTargetLabelDisplay(TargetLabelDisplay targetLabelDisplay) {
	this.targetLabelDisplay = targetLabelDisplay;
    }

    public void setWarning(List<String> warning) {
	this.warning = warning;
    }
    
    public void clearWarning() {
        this.warning.clear();
    }
    
    public void addWarning(String warning) {
        this.warning.add(warning);
        System.out.println("Add warning " + warning);
    }
    
    public RGB getColor() {
	return color;
    }

    public void setColor(RGB color) {
	this.color = color;
    }
    
    public void setController(String controller) {
	this.controller = controller;
    }

    public void setTrackStatus(TrackStatus trackStatus) {
        this.trackStatus = trackStatus;
    }
    
    
    /**
     * @return the obsoleted
     */
    public boolean isObsoleted() {
        return obsoleted;
    }

    /**
     * @param obsoleted the obsoleted to set
     */
    public void setObsoleted(boolean obsoleted) {
        this.obsoleted = obsoleted;
    }
    
    //</editor-fold>
}
