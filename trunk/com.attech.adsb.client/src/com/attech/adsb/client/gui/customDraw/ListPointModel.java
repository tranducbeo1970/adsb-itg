/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.gui.customDraw;

import com.attech.adsb.client.common.ModelBase;
import com.attech.adsb.client.config.Point2f;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author andh
 */
public class ListPointModel extends ModelBase {
    
//    private final List<Point2f> points;

    public ListPointModel(JTable table) {
	super(table);
//        points = new ArrayList<>();
    }

    @Override
    protected void initialize() {
        this.addColumn("");
        this.addColumn("LATITUDE");
        this.addColumn("LONGITUDE");

        setColumnWidth(0, 0, 0, 0);
        setColumnWidth(1, 150, 220, 200);
        setColumnWidth(2, 150, 220, 200);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public List<Point2f> getPoint() {
        List<Point2f> points = new ArrayList<>();
        for (int i = 0; i < this.getRowCount(); i++) {
            points.add((Point2f) this.getValueAt(i, 0));
        }
        return points;
    }
    
    public void update(List<Point2f> lstPoint) {
	if (lstPoint == null) {
	    return;
	}
        this.clear();
	for (Point2f point : lstPoint) {
	    this.add(point);
	}
    }
   
    public void add(Point2f point) {
//        int rowCount = this.getRowCount() + 1;  
//        String Lat = point.convertLatToDegHMS();
//        String Lon = point.convertLongToDegHMS();
//        String LatString = Lat.split(" ")[0] + "\u00B0 " + Lat.split(" ")[1] + "\' " + Lat.split(" ")[2] + "\'\' " + " N";            
//        String LonString = Lon.split(" ")[0] + "\u00B0 " + Lon.split(" ")[1] + "\' " + Lon.split(" ")[2] + "\'\' " + " E"; 
        this.addRow(
                new Object[]{
                    point,
                    point.getLatCoord(),
                    point.getLonCoord()
                });
//        this.points.add(point);
    }
    
    public void remove(int index) {
        this.removeRow(index);
//        this.points.remove(index);
    }

//    private void udpate(int index, Point2f point) {
//        String Lat = point.convertLatToDegHMS();
//        String Lon = point.convertLongToDegHMS();
//        String LatString = Lat.split(" ")[0] + "\u00B0 " + Lat.split(" ")[1] + "\' " + Lat.split(" ")[2] + "\'\' " + " N";            
//        String LonString = Lon.split(" ")[0] + "\u00B0 " + Lon.split(" ")[1] + "\' " + Lon.split(" ")[2] + "\'\' " + " E"; 
//	this.setValueAt(LatString, index, 1);
//	this.setValueAt(LonString, index, 2);
//        
//    }
    
}
