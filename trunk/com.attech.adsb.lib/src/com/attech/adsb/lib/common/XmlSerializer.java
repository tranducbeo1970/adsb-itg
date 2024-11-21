/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.lib.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author root
 */
public class XmlSerializer {

    public static void serialize(String location, Object value) {
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(location);
//            JAXBContext context = JAXBContext.newInstance(value.getClass());
//            Marshaller m = context.createMarshaller();
//            m.marshal(value, os);
//        } catch (FileNotFoundException | JAXBException ex) {
//            Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                os.close();
//            } catch (IOException ex) {
//                Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

        File file = new File(location);
        serialize(file, value);
        
    }
    
    public static void serialize(File file, Object value) {
        BufferedOutputStream outputStream = null; 
        // FileOutputStream os = null;
        try {
            // os = new FileOutputStream(file);
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            JAXBContext context = JAXBContext.newInstance(value.getClass());
            Marshaller m = context.createMarshaller();
            
            // m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // m.setProperty("com.sun.xml.bind.xmlHeaders",  "<?xml-stylesheet type=\"text/xsl\" href=\"nameoffile.xsl\" ?>");
            // m.marshal(value, os);
            m.marshal(value, outputStream);
        } catch (FileNotFoundException | JAXBException ex) {

            System.out.println(String.format("Error on writing file %s %n", file.getAbsolutePath()));
            Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException ex) {

                System.out.println(String.format("Error on writing file %s %n", file.getAbsolutePath()));
                Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void serialize(File file, Object value, boolean append) {
        BufferedOutputStream outputStream = null; 
        // FileOutputStream os = null;
        try {
            // os = new FileOutputStream(file);
            outputStream = new BufferedOutputStream(new FileOutputStream(file, append));
            JAXBContext context = JAXBContext.newInstance(value.getClass());
            Marshaller m = context.createMarshaller();
            
            // m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // m.setProperty("com.sun.xml.bind.xmlHeaders",  "<?xml-stylesheet type=\"text/xsl\" href=\"nameoffile.xsl\" ?>");
            // m.marshal(value, os);
            m.marshal(value, outputStream);
        } catch (FileNotFoundException | JAXBException ex) {

            System.out.println(String.format("Error on writing file %s %n", file.getAbsolutePath()));
            Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException ex) {

                System.out.println(String.format("Error on writing file %s %n", file.getAbsolutePath()));
                Logger.getLogger(XmlSerializer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static Object deserialize(String location, Class<?> cls) {
//        JAXBContext context;
//        try {
//            context = JAXBContext.newInstance(cls);
//            Unmarshaller m = context.createUnmarshaller();
//            FileInputStream stream = new FileInputStream(new File(location));
//            return m.unmarshal(stream);
//        } catch (JAXBException | FileNotFoundException ex) {
//            System.out.println("Error on loading file " + location);
//            return null;
//        }

        final File file = new File(location);
        return deserialize(file, cls);
    }
    
    public static Object deserialize(File file, Class<?> cls) {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(cls);
            Unmarshaller m = context.createUnmarshaller();
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            // FileInputStream stream = new FileInputStream(file);
            // return m.unmarshal(stream);
            return m.unmarshal(inputStream);
        } catch (JAXBException | FileNotFoundException ex) {
            System.out.printf("Error on loading file %s (%s) %n", file.getAbsolutePath(), ex.getMessage());
            return null;
        }
    }
}
