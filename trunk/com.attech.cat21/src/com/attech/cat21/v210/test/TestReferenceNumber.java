/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.cat21.v210.test;

import com.attech.cat21.v210.IValue;

/**
 *
 * @author andh
 */
public class TestReferenceNumber {
    
    public void test(Object index) {
        Integer a = (Integer) index;
        a+=10;
    }
    
    public void test(int index) {
        index++;
    }
    
    public void test3(IValue value) {
        int val = value.getValue();
        val ++;
        value.setValue(val);
    }
    
    public static void main(String[] args) {
        TestReferenceNumber reference = new TestReferenceNumber();
        Integer index = 0;
        System.out.println("Before: " + index);
        reference.test((Object) index);
        System.out.println("After: " + index);
        
        IValue val = new IValue();
        val.setValue(100);
        System.out.println("Before: " + val);
        reference.test3(val);
        System.out.println("After: " + val);
    }
    
}
