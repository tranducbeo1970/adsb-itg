/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.Distance;
import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.common.TargetValidator;
import com.attech.adsb.client.common.WarnLevel;
import com.attech.adsb.client.common.enums.MeasureUnit;
import com.attech.adsb.client.common.enums.WarnType;
import com.attech.adsb.client.config.AMA;
import com.attech.adsb.client.config.AMACell;
import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.PointX;
import com.attech.adsb.client.graphic.Convertor;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author NhuongND
 */
public class AmaGraphic extends IDrawableObject implements TargetValidator {
    
    private static final MLogger logger = MLogger.getLogger(AmaGraphic.class);
    
    private boolean validatingEnable;
    private final static double FEET = 3.28084;
    private static AmaGraphic instance;
    private List<AMACell> lstCells;
    private List<Point2f> lstPoints;
    private PointX text;
    private String nameFocusNgoai;
    private String nameFocusTrong;
    private AMA ama;
    private RGB color;
    private int lineWeight;
    private Point2f textPoint;
    private Short outlineStyle;
    private int textFont;
    private float zindex;

    private GLUT glut;
    
    private List<AmaCellGraphic> cellGraphics;
   
    public AmaGraphic(AMA ama) {
        this.glut = new GLUT();
        this.cellGraphics = new ArrayList<>();
        this.ama = ama;
        
        this.color = new RGB(ama.getColor());
        this.outlineStyle = ama.getLineStyleInShort(ama.getOutlineStyle());
        this.lineWeight = ama.getLineWeight();
        this.textFont = ama.getFont();
        this.zindex = ama.getzIndex();
        this.changed = true;
        
        calculate();
        this.enable = true;        
    } 

    private AmaGraphic() {
        this.glut = new GLUT();
        this.cellGraphics = new ArrayList<>();
       
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
         if (!this.isChanged()) calculate();
//        drawOuntline(glad);
        for (AmaCellGraphic cellGraph : this.cellGraphics) {
            cellGraph.draw(gl, context);
        }
                         
    }
    
//    private void drawOuntline(GLAutoDrawable glad) {
//      
//        final GL2 gl = glad.getGL().getGL2();
//        
//        gl.glEnable(GL2.GL_LINE_STIPPLE);
//        gl.glLineStipple(1, (short) this.outlineStyle);
//        gl.glLineWidth(this.lineWeight);
//        gl.glColor3f(color.red, color.green, color.blue);
//
//        buffer.rewind();
//        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
//        gl.glVertexPointer(3, GL2.GL_FLOAT, 0, buffer);
//        gl.glDrawArrays(GL2.GL_LINE_LOOP, 0, lstPoints.size());
//        gl.glDisableClientState( GL2.GL_VERTEX_ARRAY );
//
//        gl.glDisable(GL2.GL_LINE_STIPPLE);
//        gl.glEnd();
//        gl.glRasterPos3f(text.x, text.y, this.zindex);
//        glut.glutBitmapString(this.textFont, text.getName());
//    }
    
    private void calculate() {
        if (!this.isChanged()) return;
        try {
            lstCells = ama.getLstCells();
            for (AMACell cell : lstCells) {
                AmaCellGraphic amaGraphic = new AmaCellGraphic(cell);
                amaGraphic.setLineStyle(ama.getLineStyleInShort(ama.getCellLineStyle()));
                amaGraphic.setLineWeight(ama.getLineWeight());
                amaGraphic.setFont(ama.getFont());
                amaGraphic.setMargin(ama.getMargin());
                amaGraphic.setzIndex(ama.getzIndex());
                amaGraphic.setColor(new RGB(ama.getColor()));
                amaGraphic.setzIndex(ama.getzIndex());
                amaGraphic.calculate();
                cellGraphics.add(amaGraphic);
            }

            lstPoints = ama.getLstPoints();
            int size = lstPoints.size();
            buffer = this.buffer = Buffers.newDirectFloatBuffer(size * 3);
            for (Point2f p : lstPoints) {
                buffer.put(Convertor.fromWGS842OpenGL(p.x)).put(Convertor.fromWGS842OpenGL(p.y)).put(this.zindex);
            }

            text = ama.getText();
            text.x = Convertor.fromWGS842OpenGL(text.getX());
            text.y = Convertor.fromWGS842OpenGL(text.getY());
        } finally {
            this.changed = false;
        }
    }
    
    public void onMouseHover(Point2f p) {
        if (!this.isEnable()) return;
        this.cellGraphics.forEach(cellGraphic -> {
            boolean b = cellGraphic.isHover(p);
            cellGraphic.setMouseHover(b);
        });
    }
    
//    public void determineAMAViolated(Target target) {
////        System.out.println(">>> Target : " + target.getCallSign() + " AMA calculation");
//        if (!target.isAvailable()) {
//            System.out.println("    AMA " + target.getCallSign() + " is not available");
//            return;
//        }
//            
//        Point2f position = Convertor.fromWGS842OpenGL(target.GetPosition());
//        Point2f futurePosition = Convertor.fromWGS842OpenGL(target.getAHeadVectorPoint());
//        float altitude = (float) (target.getFlightLevel() * 100 * FEET);
//        int finalWarning = 0;
//        for (AmaCellGraphic cellGraphic : this.cellGraphics) {
//            int result = cellGraphic.isViolate(position, futurePosition, altitude);
//            if (result == 3) {
//                target.setWarning(WarnType.AMA_ALERT, "AMA Danger");
//                System.out.println(target.getCallSign() + " Danger");
//                return;
//            }
//            
//            if (result <= finalWarning) continue;
//            finalWarning = result;
//        }
//        
//        switch (finalWarning) {
//            case 2:
//                target.setWarning(WarnType.AMA_ALERT, "AMA Alarm");
//                System.out.println(target.getCallSign() + " Alarm");
//                break;
//            case 1:
//                target.setWarning(WarnType.AMA_ALERT, "AMA Warning");
//                System.out.println(target.getCallSign() + " Warning");
//                break;
//            default:
//                target.remove(WarnType.AMA_ALERT);
//                System.out.println(target.getCallSign() + " Safe");
//                break;
//        }
//    }
//    
    public AMACell TestInsideAMATrong(Point2f p) {        
        boolean b;
        AMACell ret = null;
        p = Convertor.fromWGS842OpenGL(p);
        if(lstCells != null && lstCells.size() > 0){
            for (int i = 0; i < lstCells.size(); i++) {
                List<Point2f> CacDiem = new ArrayList<>();
                Point2f p1 = new Point2f();
                p1.y = lstCells.get(i).lat1;
                p1.x = lstCells.get(i).lon1;
                CacDiem.add(p1);

                Point2f p2 = new Point2f();
                p2.y = lstCells.get(i).lat2;
                p2.x = lstCells.get(i).lon1;
                CacDiem.add(p2);

                Point2f p3 = new Point2f();
                p3.y = lstCells.get(i).lat2;
                p3.x = lstCells.get(i).lon2;
                CacDiem.add(p3);

                Point2f p4 = new Point2f();
                p4.y = lstCells.get(i).lat1;
                p4.x = lstCells.get(i).lon2;
                CacDiem.add(p4);

                b = Distance.isPointIsInside(p, CacDiem);
                if (b) {
                    ret = lstCells.get(i);                
                }
            }
        }        
        return ret;
    }  
    
    public AMACell TestInsideAMATrong(Point2f p,String exception) {
        boolean b;
        AMACell ret = null;    
        p = Convertor.fromWGS842OpenGL(p);
        for (int i = 0; i < lstCells.size(); i++) {
            if (!exception.isEmpty() && !lstCells.get(i).name.equals(exception)) {
                List<Point2f> CacDiem = new ArrayList<>();
                Point2f p1 = new Point2f();
                p1.y = lstCells.get(i).lat1;
                p1.x = lstCells.get(i).lon1;
                CacDiem.add(p1);

                Point2f p2 = new Point2f();
                p2.y = lstCells.get(i).lat2;
                p2.x = lstCells.get(i).lon1;
                CacDiem.add(p2);

                Point2f p3 = new Point2f();
                p3.y = lstCells.get(i).lat2;
                p3.x = lstCells.get(i).lon2;
                CacDiem.add(p3);

                Point2f p4 = new Point2f();
                p4.y = lstCells.get(i).lat1;
                p4.x = lstCells.get(i).lon2;
                CacDiem.add(p4);

                b = Distance.isPointIsInside(p, CacDiem);
                if (b) {
                    ret = lstCells.get(i);                
                }
            }
        }
        return ret;
    }    
    
    public AMACell TestInsideAMANgoai(Point2f p, String exception) {
        boolean b;
        AMACell ret = null;
        p = Convertor.fromWGS842OpenGL(p);
        for (int i = 0; i < lstCells.size(); i++) {
            if (!exception.isEmpty() && !lstCells.get(i).name.equals(exception)) {

                List<Point2f> CacDiem = new ArrayList<>();
                Point2f p1 = new Point2f();
                p1.y = lstCells.get(i).lat3;
                p1.x = lstCells.get(i).lon3;
                CacDiem.add(p1);

                Point2f p2 = new Point2f();
                p2.y = lstCells.get(i).lat4;
                p2.x = lstCells.get(i).lon3;
                CacDiem.add(p2);

                Point2f p3 = new Point2f();
                p3.y = lstCells.get(i).lat4;
                p3.x = lstCells.get(i).lon4;
                CacDiem.add(p3);

                Point2f p4 = new Point2f();
                p4.y = lstCells.get(i).lat3;
                p4.x = lstCells.get(i).lon4;
                CacDiem.add(p4);

                b = Distance.isPointIsInside(p, CacDiem);
                if (b) {
                    ret = lstCells.get(i);
                }
            }
        }
        return ret;
    }
    
    //<editor-fold defaultstate="collapsed" desc=" Class properties ">       

    public static AmaGraphic getInstance() {
        if (instance == null) instance = new AmaGraphic();
        return instance;
    }
    
    public List<AMACell> getLstCells() {
        return lstCells;
    }

    public void setLstCells(List<AMACell> lstCells) {
        this.lstCells = lstCells;
    }
    
    public void addCells(AMACell Cells) {
        this.lstCells.add(Cells);
    }

    public List<Point2f> getLstPoints() {
        return lstPoints;
    }

    public void setLstPoints(List<Point2f> lstPoints) {
        this.lstPoints = lstPoints;
    }
    
    public void addPoints(Point2f point) {
        this.lstPoints.add(point);
    }

    public PointX getText() {
        return text;
    }

    public void setText(PointX text) {
        this.text = text;
    }
    
    public String getNameFocusNgoai() {
        return nameFocusNgoai;
    }

    public void setNameFocusNgoai(String nameFocusNgoai) {
        this.nameFocusNgoai = nameFocusNgoai;
    }

    public String getNameFocusTrong() {
        return nameFocusTrong;
    }

    public void setNameFocusTrong(String nameFocusTrong) {
        this.nameFocusTrong = nameFocusTrong;
    }    
    
    //</editor-fold>

    @Override
    public int validate(Target target) {
        if (!this.validatingEnable || !target.isAvailable()) {
            return 0;
        }
	
	logger.debug("Checking target %s for AMA", target.getCallSign());
        
        Point2f position = Convertor.fromWGS842OpenGL(target.getPosition());
        Point2f closeRangePos = Convertor.fromWGS842OpenGL(target.getNextPointInDistance(ama.getCloseRangeThresHold(), MeasureUnit.KM)); //Calculator.calculateLocation(position, ama.getCloseRangeThresHold(), target.getHeading(), MeasureUnit.KM);
        Point2f longRangePos = Convertor.fromWGS842OpenGL(target.getNextPointInDistance(ama.getLongRangeThresHold(), MeasureUnit.KM));
//        Point2f futurePosition = Convertor.fromWGS842OpenGL(target.getAHeadVectorPoint());
//        float altitude = (float) (target.getFlightLevel() * 100 * FEET);
         float altitude = (float) (target.getFlightLevel() * 100);
        int finalWarning = 0;
        for (AmaCellGraphic cellGraphic : this.cellGraphics) {
            int result = cellGraphic.isViolate(position, closeRangePos, longRangePos, altitude);
            if (result == 3) {
                target.setWarning(WarnType.AMA, "AMA Danger");
		logger.warn("Target %s is on AMA danger", target.getCallSign());
                return 3;
            }
            
            finalWarning = result <= finalWarning ? finalWarning : result;
//            if (result <= finalWarning) continue;
//            finalWarning = result;
        }
        
        switch (finalWarning) {
            case 2:
                
//                target.setWarning(WarnType.AMA, WarnLevel.WARN);
                target.setWarning(WarnType.AMA, "AMA Warning");
		logger.warn("Target %s is on AMA warning", target.getCallSign());
                break;
            case 1:
//                target.setWarning(WarnType.AMA, WarnLevel.ALARM);
                target.setWarning(WarnType.AMA, "AMA Alert");
		logger.warn("Target %s is on AMA alert", target.getCallSign());
                break;
            default:
                break;
        }
        return 0;
    }

    @Override
    public void setEnableValidator(Boolean enable) {
        this.validatingEnable = enable;
    }
}
