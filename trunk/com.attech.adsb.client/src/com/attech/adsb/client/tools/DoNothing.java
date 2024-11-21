/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.tools;

import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.Target;
import com.attech.adsb.client.config.Configuration;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author ANDH
 */
public class DoNothing {
    public static void main(String [] args) {
        DOMConfigurator.configure(Common.CFG_LOG);
        Configuration.instance().load();
        System.out.println("MAX INTEGER: " + Integer.MAX_VALUE);
        System.out.println("Current Time: " + System.currentTimeMillis());
        
        List<Target> targets = new ArrayList<>();
        Target target = new Target();
        target.setAddress("8880AB");
        targets.add(target);
        
        Target target2 = new Target();
        target2.setAddress("8880AB");
        
        Target target3 = new Target();
        target3.setAddress("8880AC");
        
        List<Target> targets2 = new ArrayList<>();
        targets2.add(target3);
        
        System.out.println("Compare = " + (target.equals(target2)));
        System.out.println("Contain in Array = " + (targets.contains(target2)));
        System.out.println("Contain in Array = " + (targets2.contains(target2)));
        System.out.println("Contain in Array = " + (target.equals("8880AB")));
        float coord = (float) (17*1.0f + 40/60.0f + 41/3600.0f);
        System.out.println("Result: " + coord) ;
        
    }
}
