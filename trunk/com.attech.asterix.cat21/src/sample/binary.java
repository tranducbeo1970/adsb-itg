/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

/**
 *
 * @author George
 */
public class binary {
    public static void main(String [] args) {
        int a = 121;
        //int b = 
        //a = (~a) & 0xFF;
        int lat = 121;
        System.out.println(">: " + Integer.toBinaryString(lat));
        System.out.println("Binary: " + ((~a) & 0xFF));
        System.out.println(">: " + Integer.toBinaryString(0x80));
        lat = lat & 0x80;
        System.out.println("Binary: " + Integer.toBinaryString(lat));
        
    }
}
