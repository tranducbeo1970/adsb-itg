/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import com.attech.adsb.lib.common.Area;
import com.attech.cat21.v210.Cat21Message;
import com.attech.cat21.v210.Position;

/**
 *
 * @author AnhTH
 */
public class AreaValidation implements IValidation {
    
    public Area area;
    private int size;
    // private double ilat, ilong, jlat, jlong;
    
    public AreaValidation() {
        area = new Area();
        size = 0;
    }
    
    public AreaValidation(Area area) {
        this.area = area;
        this.size = area.getPoints().size();
    }

    @Override
    public boolean validate(Cat21Message message) {
        Position p = message.getPosition() != null ? message.getPosition() : message.getHighResolutionPosition();
        if (p == null) {
            //System.out.println(message.getPosition() + "  ---  " + message.getHighResolutionPosition());
            return false;
        }
        
        boolean result = area.validate(p.getLongtitude(), p.getLatitude());
        
        //System.out.println("VALIDATE: " + result);
       return result;
    }
    
    public boolean validate(double lng, double lat) {
        return area.validate(lng, lat);
    }
      
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Area validation: \n");
        int size = this.area.getPoints().size();
        for (int i = 0; i< size; i++) {
            builder.append("     Point").append(i).append(" ").append(this.area.getPoints().get(i)).append("\n");
        }
        return builder.toString();
    }
    
}

