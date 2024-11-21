/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author haianh
 */
public class Test {
    public static void main(String args[]) {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "ZERO");
        map.put(1, "ONE");
        map.put(2, "TWO");
        map.put(3, "THREE");
        map.values().forEach((element) -> {System.out.println(element);});
        
        map.put(4, "FOUR");
        map.put(1, "FIRST");
        map.values().forEach((element) -> {System.out.println(element);});
    }
}
