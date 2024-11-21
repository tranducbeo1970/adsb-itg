/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.Plot;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TrackAge;
import com.attech.adsb.client.common.TrackCondition;
import com.attech.adsb.client.common.TrackStatus;
import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.Configuration;
import com.attech.adsb.client.config.FilterCondition;
import com.attech.adsb.client.config.MTCAConfig;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.STCAConfig;
import com.attech.adsb.client.config.TargetConfig;
import com.attech.adsb.client.config.TargetLabelDisplay;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ANDH
 */
public class TargetItem extends IDrawableObject implements Runnable{
    private static final STCAConfig stcaConfig = Configuration.instance().getStcaConfig();
    private static final MTCAConfig mtcaConfig = Configuration.instance().getMtcaConfig();
    private static final TargetConfig targetConfig = Configuration.instance().getTargetConfig();
    private static final int TRIANGLE_AMOUNT = 30;
    private static final float PI_SQUARE = (float) 2.0f * (float) Math.PI;
    
    private String callSign;
    private String address;
    private final List<Plot> plots = new ArrayList<>();
    private final Map<WarnType, String> warningList = new HashMap<>();
    
    private TrackAge trackAge;
    private final Point2f[] targetRectang = new Point2f[] {new Point2f(),new Point2f(),new Point2f(),new Point2f() };
    private Point2f targetLocation;
    private RGB normalColor1 ;
    private RGB normalColor2;
    private RGB warningColor;
    private RGB circleWarnColor;
    private RGB circleAlarmColor;
    private RGB obsoletedColor;
    private RGB traceColor;
    
    public float x;
    public float y;
    public float z;
    public float z2;
    public float speedVectorZIndex;
    
    private int i;
    private float xIndex;
    private float yIndex;
    private TargetLabel label;
    private boolean filterMatched = true;
    private FilterCondition filterCondition;
    
    private Point2f speedVector;
    private TargetLabelDisplay targetLabelDisplay;
    private int warnLevel;
    
    private Circle circle;
    private TrackStatus trackStatus;
    private boolean isRefreshAll = false;

    private Target target;

    public TargetItem() {
        this.label = new TargetLabel();
        this.normalColor1 = new RGB(targetConfig.getNormalColor1());
        this.normalColor2 = new RGB(targetConfig.getNormalColor2());
        this.warningColor = new RGB(targetConfig.getWarningColor());
        this.circleAlarmColor = new RGB(targetConfig.getCircleAlarmColor());
        this.circleWarnColor = new RGB(targetConfig.getCircleWarnColor());
        this.obsoletedColor = new RGB(targetConfig.getObsoletedTargetColor());
        this.traceColor = new RGB(targetConfig.getTraceColor());
        this.z = targetConfig.getTargetZIndex();
        this.z2 = targetConfig.getTargetZIndex2();
        
        this.circle = new Circle();
        this.circle.setzIndex((float) z);
        this.circle.setRadiusUnit(MeasureUnit.NM);
        this.isRefreshAll = true;
        
    }
    
    public TargetItem(Target target) {
        this();
        this.target = target;
	this.address = target.getAddress();
	this.callSign = target.getCallSign();
        this.target.addGraphicNotify(this::targetChanged);
    }
    
    public void setWarning(WarnType warning) {
        this.warningList.put(warning, "WARNING");
    }
    
    public void targetChanged(Object obj) {
        this.setChanged(true);
        this.isRefreshAll = (Boolean) obj;
    }
    
    public void applyFilterCondition() {
        this.filterMatched = true;
        if (filterCondition == null) return;
        if (!filterCondition.isActive()) {
//            this.filterMatched = true;
            return;
        }
        
        String filterCallsign = filterCondition.getCallSign();
        if (filterCallsign != null && !filterCallsign.isEmpty()) {

            String targetCallSign = this.target.getCallSign();
            if (targetCallSign == null || targetCallSign.isEmpty()) {
                this.filterMatched = false;
                return;
            }

            if (!targetCallSign.contains(filterCallsign) && !targetCallSign.equalsIgnoreCase(filterCallsign)) {
                this.filterMatched = false;
                return;
            }

        }

        if (filterCondition.getAltitudeLow() != null && this.target.getFlightLevel() < filterCondition.getAltitudeLow()) {
            this.filterMatched = false;
            return;
        }

        if (filterCondition.getAltitudeHigh() != null && this.target.getFlightLevel() > filterCondition.getAltitudeHigh()) {
            this.filterMatched = false;
            return;
        }

        this.filterMatched = true;
    }
    
    @Override
    public void draw( GL2 gl, GraphicContext context) {
//        System.out.println("Draw target ..." + this.getCallSign() + " Avail: " + this.isEnable() + "  Enable: " + this.isEnable() + " FILTER: matched+ " + this.filterMatched);
        if (!this.isEnable()) return;
        if (!this.target.isAvailable()) return;
        if (!this.filterMatched && this.filterCondition.isHideTarget()) return;
       
//        System.out.println("Draw target " + this.getCallSign());
        calculate(gl, context);
        if (isWarning()) {
            drawWarningSymbol(gl, context, targetRectang);
        } else {
            drawNormalSymbol(gl, context, targetLocation);
        }
	
        if (this.warnLevel > 0) this.circle.draw(gl, context);        
        if (this.targetLabelDisplay.isTargetTrackingVisible()) drawTrace(gl);
    }
    
    private void drawTrace(GL2 gl) {
        long period = System.currentTimeMillis() - targetLabelDisplay.getHistoryLength();
        this.traceColor.setColor(gl);
        gl.glPointSize(targetConfig.getTraceWeight());
	gl.glBegin(GL2.GL_POINTS);
        for (Plot plot : plots) {
            if (!plot.isValid(period)) break;
            gl.glVertex3f(plot.getX(), plot.getY(),this.z2);
        }
        gl.glEnd();
    }
    
    private void calculate(GL2 gl, GraphicContext context) {
        try {
            
            if (this.isChanged()) {
                
                if (this.isRefreshAll) {
                    this.x = Convertor.fromWGS842OpenGL(this.target.getLongtitude());
                    this.y = Convertor.fromWGS842OpenGL(this.target.getLatitude());
                    plots.add(0, new Plot(this.x, this.y, this.z, false));
                }
                
                this.speedVector = Convertor.fromWGS842OpenGL(this.target.getSpeedVector());
                //this.condition = this.target.getTrackCondition();
                this.warnLevel = this.target.getHitLevel();
                this.trackStatus = this.target.getTrackStatus();
                this.trackAge = this.target.getTrackAge();

                label.setCallSign(this.target.getCallSign());
                label.setAddress(this.target.getAddress());
                label.setCalt(0);
                label.setCoalt(0);
                label.setFlightLevel(this.target.getFlightLevel());
                // label.setSsrCode(Integer.toOctalString(this.target.getMode3a()));
                label.setSsrCode(this.target.getMode3a() + "");
                label.setVelocity(this.target.getSpeed());
                label.setVerticalRate(this.target.getVerticalRate());
                label.setController(this.target.getController());
                label.setTrackStatus(this.trackStatus);
                label.setObsoleted(this.trackAge != TrackAge.GOOD);
                label.clearWarning();
                
                Iterator<String> warningIterators = this.target.getWarningList().values().iterator();
                while (warningIterators.hasNext()) {
                    label.addWarning(warningIterators.next());
                }
                
                Point2f position =  this.target.getPosition();
                switch (this.warnLevel) {
                    case 1:
                        this.circle.setChanged(true);
                        this.circle.setColor(this.circleWarnColor);
                        this.circle.setCenterPoint(position);
                        this.circle.setRadius(stcaConfig.getHorizonWarnThreshold());                     
                        this.circle.calculate();
                        
                        break;
                    case 2:
                        this.circle.setChanged(true);
                        this.circle.setColor(this.circleAlarmColor);
                        this.circle.setCenterPoint(position);
                        this.circle.setRadius(stcaConfig.getHorizonAlarmThreshold());                        
                        this.circle.calculate();
                        
                        break;
                }
                
                applyFilterCondition();
            }

            targetLocation = context.calculateIdentityPosition(gl, x, y);
            targetRectang[0].x = targetLocation.x;
            targetRectang[0].y = targetLocation.y + targetConfig.getTargetSize();
            targetRectang[1].x = targetLocation.x + targetConfig.getTargetSize();
            targetRectang[1].y = targetLocation.y;
            targetRectang[2].x = targetLocation.x;
            targetRectang[2].y = targetLocation.y - targetConfig.getTargetSize();
            targetRectang[3].x = targetLocation.x - targetConfig.getTargetSize();
            targetRectang[3].y = targetLocation.y;
            
            label.calculate(targetLocation);

        } finally {
            this.setChanged(false);
            this.isRefreshAll = false;
        }
    }
    
    private void drawNormalSymbol(GL2 gl, GraphicContext context, Point2f p1) {

        this.normalColor2.setColor(gl);
        gl.glLineWidth(targetConfig.getWeight());

	gl.glPushMatrix();
	gl.glLoadIdentity();

	gl.glBegin(GL2.GL_LINES);
	gl.glVertex3f(p1.x - targetConfig.getTargetSize(), p1.y, z);
	gl.glVertex3f(p1.x - targetConfig.getTargetInnerSize(), p1.y, z);

	gl.glVertex3f(p1.x, p1.y + targetConfig.getTargetSize(), z);
	gl.glVertex3f(p1.x, p1.y + targetConfig.getTargetInnerSize(), z);

	gl.glVertex3f(p1.x + targetConfig.getTargetInnerSize(), p1.y, z);
	gl.glVertex3f(p1.x + targetConfig.getTargetSize(), p1.y, z);

	gl.glVertex3f(p1.x, p1.y - targetConfig.getTargetSize(), z);
	gl.glVertex3f(p1.x, p1.y - targetConfig.getTargetInnerSize(), z);
	gl.glEnd();

        this.normalColor1.setColor(gl);
	gl.glBegin(GL2.GL_TRIANGLE_FAN);
	gl.glVertex3f(p1.x, p1.y, z);

	double angleStep;
	for (i = 0; i <= TRIANGLE_AMOUNT; i++) {
	    angleStep = i * PI_SQUARE / TRIANGLE_AMOUNT;
	    xIndex = (float) (p1.x + (targetConfig.getTargetRadius() * Math.cos(angleStep)));
	    yIndex = (float) (p1.y + (targetConfig.getTargetRadius() * Math.sin(angleStep)));
	    gl.glVertex3f(xIndex, yIndex, z);
	}
	gl.glEnd();
        
        if (!this.isCancel() && this.filterMatched) {
	    label.draw(gl, context);
	}

	gl.glPopMatrix();
        this.normalColor2.setColor(gl);
        if (speedVector.x != 0 && speedVector.y != 0) {
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(x, y, z2);
            gl.glVertex3f(speedVector.x, speedVector.y, z2);
            gl.glEnd();
        }
    }
    
    private void drawWarningSymbol(GL2 gl, GraphicContext context, Point2f[] Points) {
        if (this.trackAge != TrackAge.GOOD) {
            this.obsoletedColor.setColor(gl);
        } else {
            this.warningColor.setColor(gl);
        }
	
        gl.glLineWidth(targetConfig.getWeight());
        
        if (speedVector.x != 0 && speedVector.y != 0) {
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex3f(x, y, z - 1);
            gl.glVertex3f(speedVector.x, speedVector.y, z - 1);
            gl.glEnd();
        }

        gl.glPushMatrix();
        gl.glLoadIdentity();

        gl.glBegin(GL2.GL_LINES);

        gl.glVertex3f((float) Points[0].x, (float) Points[0].y, z);
        gl.glVertex3f((float) Points[2].x, (float) Points[2].y, z);

        gl.glVertex3f((float) Points[1].x, (float) Points[1].y, z);
        gl.glVertex3f((float) Points[3].x, (float) Points[3].y, z);
        gl.glEnd();

        if (!this.isCancel() && this.filterMatched) {
	    label.draw(gl, context);
	}
        
        gl.glPopMatrix();
    }
    
    private boolean isWarning() {
        return this.label.isWarning() || this.trackAge != TrackAge.GOOD;
    }
    
    public boolean isSelected(Point2f mousePoint) {
         return isInside(mousePoint);
    }
    
    public void roteLable() {
        this.label.rotatingLabel();
	this.label.calculate(targetLocation);
    }
    
    public void handleMouseClick(Point2f mousePoint) {
        if (!isInside(mousePoint)) return;
        System.out.println("Target " + this.callSign + " is clicked");
        this.label.rotatingLabel();
	this.label.calculate(targetLocation);
        
    }
    
    public boolean isInside(Point2f mousePoint) {
        if (mousePoint == null) {
            System.out.println("MOUSE POINT NULL");
        }
        
         if (targetLocation == null  ) {
            System.out.println("TARGET LOCATION IS NULL");
            return false;
        }
        return (Math.abs(targetLocation.x - mousePoint.x) < targetConfig.getTargetSize()) && (Math.abs(targetLocation.y - mousePoint.y)) < targetConfig.getTargetSize();
    }
     
    public boolean isOutsideScreen(GraphicContext context) {
	return context.isTargetOutsideScreen(x, y);
    } 
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">

    
    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
        this.label.setCallSign(callSign);
        
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.label.setAddress(address);
    }

    public void setTargetLabelDisplay(TargetLabelDisplay targetLabelDisplay) {
	this.targetLabelDisplay = targetLabelDisplay;
	this.label.setTargetLabelDisplay(targetLabelDisplay);
    }

    public Target getTarget() {
        return this.target;
    }
    
    
    public TrackStatus getTrackStatus() {
        return trackStatus;
    }

    public void setTrackStatus(TrackStatus trackStatus) {
        this.trackStatus = trackStatus;
    }

     /**
     * @return the obsoleted
     */
    public synchronized boolean isObsoleted() {
        return this.trackAge == TrackAge.OBSOLETED;
    }
    
    public boolean isCancel() {
        return this.trackStatus == TrackStatus.CANCEL;
    }
    
    /**
     * @return the filterMatched
     */
    public boolean isFilterMatched() {
        return filterMatched;
    }
    
    /**
     * @param filterCondition the filterCondition to set
     */
    public void setFilterCondition(FilterCondition filterCondition) {
        this.filterCondition = filterCondition;
    }
    
    //</editor-fold>

    @Override
    public void run() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

}
