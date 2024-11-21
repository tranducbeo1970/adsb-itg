/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common;

import java.nio.ByteBuffer;

/**
 *
 * @author Administrator
 */
public class RasterData {
    
    public static ByteBuffer flDecend;
    public static ByteBuffer flMaintain;
    public static ByteBuffer flClimb;
    
    public static byte rasterF[] = {
        (byte) 0xc0, (byte) 0x00,
        (byte) 0xc0, (byte) 0x00,
        (byte) 0xc0, (byte) 0x00,
        (byte) 0xc0, (byte) 0x00,
        (byte) 0xc0, (byte) 0x00,
        (byte) 0xff, (byte) 0x00,
        (byte) 0xff, (byte) 0x00,
        (byte) 0xc0, (byte) 0x00,
        (byte) 0xc0, (byte) 0x00,
        (byte) 0xc0, (byte) 0x00,
        (byte) 0xff, (byte) 0xc0,
        (byte) 0xff, (byte) 0xc0
    };
    
    public static byte ArrowLevel[] = {
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x04, (byte) 0x00,
        (byte) 0x06, (byte) 0x00,
        (byte) 0xff, (byte) 0x00,
        (byte) 0xff, (byte) 0x00,
        (byte) 0x06, (byte) 0x00,
        (byte) 0x04, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00
    };    
    
     public static byte Decend[] = {
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x3c, (byte) 0x00,
        (byte) 0x7e, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00
    };    
     
     public static byte Climb[] = {
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x7e, (byte) 0x00,
        (byte) 0x3c, (byte) 0x00,
        (byte) 0x18, (byte) 0x00,
        (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00
    };    
     
     
     
}
