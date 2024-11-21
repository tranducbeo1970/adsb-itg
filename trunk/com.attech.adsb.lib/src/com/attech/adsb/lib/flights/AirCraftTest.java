/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.flights;

import java.io.File;
import java.util.List;

/**
 *
 * @author andh
 */
public class AirCraftTest {
    public static void main(String[] args) {
        AirCraft aircraft = AirCraft.load(new File("780A47.xml"));
        System.out.println("Total message = " + aircraft.getMessages().size() + " [3506]");
        List<Flight> flights = aircraft.buildUpFlight(1800000);
        System.out.println("Total flight = " + flights.size() + " [2]");
        for (Flight flight : flights) {
            System.out.printf("Callsign: %s message: %s%n", flight.callsign(), flight.size());
        }
        
        
        aircraft = AirCraft.load(new File("8880C7.xml"));
        System.out.println("Total message = " + aircraft.getMessages().size() + " [4758]");
        flights = aircraft.buildUpFlight(1800000);
        System.out.println("Total flight = " + flights.size() + " [2]");
        for (Flight flight : flights) {
            System.out.printf("Callsign: %s message: %s%n", flight.callsign(), flight.size());
        }

    }
    
    
}
