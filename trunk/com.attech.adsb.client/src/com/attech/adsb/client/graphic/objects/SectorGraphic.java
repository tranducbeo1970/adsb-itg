/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;


import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Sector;
import com.attech.adsb.client.config.SectorLine;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.attech.adsb.client.graphic.RGB;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Saitama
 */
public class SectorGraphic extends IDrawableObject{
        
    protected boolean localsector;
    protected boolean displayfixpoint;
    protected List<SectorLineGraphic> lines;
    protected int weight;
    protected RGB color ;
    
    public SectorGraphic() {
        this.localsector = false;
        this.displayfixpoint = true;
        this.lines = new ArrayList<>();
        this.weight = 1;
        this.color = new RGB(0.0f, 0.0f, 0.0f);
    }
   
    public SectorGraphic(Sector sector) {
        lines = new ArrayList<>();
        for (SectorLine line : sector.getLines()) {
            lines.add(new SectorLineGraphic(line));
        }
        this.localsector = sector.isLocalsector();
        this.displayfixpoint = sector.isDisplayfixpoint();
        this.enable = sector.isEnable();
        this.weight = sector.getWeight();
        this.color = new RGB(sector.getColor());
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        for (SectorLineGraphic line : this.lines) {
            line.colorLabel = this.color;
            line.displayfixpoint = this.displayfixpoint;
            line.draw(gl, context);
        }
    }
    
}
