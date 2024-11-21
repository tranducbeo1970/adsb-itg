/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.graphic.objects;

import com.attech.adsb.client.common.GraphicContext;
import com.attech.adsb.client.config.Airport;
import com.attech.adsb.client.config.Airports;
import com.attech.adsb.client.graphic.IDrawableObject;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author NhuongND
 */
public class AirportsGraphic extends IDrawableObject{
    
    private List<AirportGraphic> lstAirport;
   
    public AirportsGraphic(Airports airports) {
        lstAirport = new ArrayList<>();
        for (Airport aiport : airports.getAirport()) {            
            lstAirport.add(new AirportGraphic(aiport));
        }
        this.enable = true;
    } 

    public List<AirportGraphic> getlstAirport() {
        return lstAirport;
    }

    public void setLstAirport(List<AirportGraphic> lstAirport) {
        this.lstAirport = lstAirport;
    }

    @Override
    public void draw( GL2 gl, GraphicContext context) {
        if (!this.isEnable()) return;
        for (AirportGraphic item : this.lstAirport) {
            item.draw(gl, context);
        }
    }
    
}
