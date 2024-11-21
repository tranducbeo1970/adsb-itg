/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.cat02.v10;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hong
 */
public class DecodeTest {
    
    
    public DecodeTest() {
    }
    
//    public void test01() {
//        String fileName = "data/m01";
//        byte[] bytes = Common.loadMessage(fileName);
//        List<Message02> messages = new ArrayList<>();
//        Decrypter02.decode(bytes, messages);
//        for (Message02 m : messages) {
//            m.print();
//            XmlSerializer.serialize("D:\\tmp\\msg01.xml", m);
//        }
//    }
    public void test02() {
        String fileName = "data/m02.c02";
        byte[] bytes = Common.loadMessage(fileName);
        List<Message02> messages = new ArrayList<>();
        Decrypter02.decode(bytes, messages);
        for (Message02 m : messages) {
            m.print();
        }
    }
    
   public static void main(String [] args) {
        DecodeTest test = new DecodeTest();
        test.test02();
    }
}
