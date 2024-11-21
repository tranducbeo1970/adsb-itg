/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import com.attech.asd.database.entities.Circulars;
import com.attech.cat21.v210.Cat21Message;
import com.attech.cat21.v210.Position;

/**
 *
 * @author haianh
 */
public class CircularValidation implements IValidation {
    public Circulars area;
    // private double ilat, ilong, jlat, jlong;
    
    public CircularValidation() {
        area = new Circulars();
    }
    
    public CircularValidation(Circulars area) {
        this.area = area;
    }

    @Override
    public boolean validate(Cat21Message message) {
        Position p = message.getPosition() != null ? message.getPosition() : message.getHighResolutionPosition();
        if (p == null) {
            //System.out.println(message.getPosition() + "  ---  " + message.getHighResolutionPosition());
            return true;
        }
        
        boolean result = validate(p.getLongtitude(), p.getLatitude());
        //System.out.println("VALIDATE: " + result);
       return result;
    }
    
    public boolean validate(double lng, double lat) {
        return area.isIn(lng, lat);
    }
}
