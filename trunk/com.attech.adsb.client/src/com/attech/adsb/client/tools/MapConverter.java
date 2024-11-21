/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.tools;

import com.attech.adsb.client.config.Point2f;
import com.attech.adsb.client.config.PointX;
import com.attech.adsb.client.config.Sector;
import com.attech.adsb.client.config.SectorLine;
import com.attech.adsb.client.config.Sectors;
import com.attech.adsb.client.graphic.RGB;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ANDH
 */
public class MapConverter {
    
    
    private Sector currSector;
    private Sectors sectorConfig;
    private SectorLine sectorLine;
    
    public MapConverter() {
	this.sectorLine = new SectorLine();
	this.currSector = new Sector();
	this.sectorConfig = new Sectors();
    }
    
    public static void main(String [] args) {
	MapConverter converter = new MapConverter();
	converter.read("sector1.txt");
	
    }
    
    private void read(String fileName) {
        try {
            FileReader input = new FileReader(fileName);
            BufferedReader bufRead = new BufferedReader(input);
            String line;                                           // String that holds current file line
//	    Sectors sector = new Sectors();
	    
		    
//            tempLine = new OpenGLLine();
	    sectorLine = new SectorLine();
            while ((line = bufRead.readLine()) != null) {
                line = line.trim();
                if (line.indexOf("{") == 0 || line.indexOf("$") == 0) continue;
		System.out.println("> " + line);
                
		if (line.toUpperCase().trim().startsWith("NAME")) {
		    String name = line.substring("Name".length());
		    this.currSector.setName(name);
		    System.out.println("Name: " + name);
		    continue;
		}
		
		if (line.toUpperCase().trim().startsWith("END")) {
		    this.currSector.addSectorLine(this.sectorLine);
		    this.sectorConfig.addSector(currSector);
		    this.currSector = new Sector();
		    System.out.println("Create new Sector");
		    this.sectorLine = new SectorLine();
		    System.out.println("Create new line");
		    continue;
		}
		
                if (line.indexOf("COLOR") == 0) {
                    setVertexColor(line);
                    continue;
                }
                addVertex(line);
            }
            
            bufRead.close();
	    
	    this.sectorConfig.save("converted_sector.xml");
	    System.out.println("Completed");
	    
	    
            
        } catch (IOException e) {
            e.printStackTrace();
//            ExHandler.handle(e);
        }
    }

    private void addVertex(String s) {
	float x, y;
	String[] words = s.split(" ");
	for (String word : words) {
	    String[] vt = word.split("\\+");
	    for (int j = 0; j < vt.length; j++) {

		if (vt[j].indexOf("-1") == 0) {
		    // Het 1 duong
		    // Common.Main.VertextList.add(new Common.Vertex(2000));
//                    this.vertices.add(new Common.Vertex(2000));
//                    this.lines.add(tempLine);
//                    tempLine = new OpenGLLine();

		    this.currSector.addSectorLine(sectorLine);
		    this.sectorLine = new SectorLine();
		    System.out.println("Create new line");
		    continue;
		}

		y = Float.parseFloat(vt[j++]);
		x = Float.parseFloat(vt[j]);
//                this.vertices.add(new Common.Vertex(x, y));
//                tempLine.addWGS84Vertex(x, y, Layer.layer1);
		this.sectorLine.addPoint(new PointX(x, y,""));
		System.out.println("Set point: x = " + x + " y = " + y);
	    }
	}
    }
	
    private void setVertexColor(String s) {
	String[] words = s.split(" ");
	RGB rgb = new RGB(Float.parseFloat(words[1]), Float.parseFloat(words[2]), Float.parseFloat(words[3]));
	this.sectorLine.setColor(rgb.toString());
	
	System.out.println("Set color: " + rgb.toString());
    }
}
