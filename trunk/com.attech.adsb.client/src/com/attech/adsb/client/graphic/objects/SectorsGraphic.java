/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;


import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Sector;
import com.attech.adsb.client.config.Sectors;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Saitama
 */
public class SectorsGraphic extends IDrawableObject{
    
    private List<SectorGraphic> lstSectors;
   
    public SectorsGraphic(Sectors sectors) {
        lstSectors = new ArrayList<>();
        for (Sector sector : sectors.getSectors()) {
            lstSectors.add(new SectorGraphic(sector));
        }
        this.enable = true;
    }

    public SectorGraphic getLocalSector() {
        SectorGraphic localSector = new SectorGraphic();
        for (SectorGraphic sector : this.lstSectors) {
            if(sector.localsector){
                for (SectorLineGraphic line : sector.lines) {
                    line.weight = sector.weight;
                }
                localSector = sector;
            }
        }
        return localSector;
    }   

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        for (SectorGraphic sector : this.lstSectors) {
            sector.draw(gl, context);
        }
    }
    
}
