/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

/**
 *
 * @author Tang Hai Anh
 */
public class WGS84Position {
    public double latitude;       // Vi do = Y
    public double longitude;      // Kinh do = X

    public WGS84Position() {
        latitude = 0;
        longitude = 0;
    }

    public WGS84Position(double lat, double lon) {
        latitude = lat;
        longitude = lon;
    }
}
