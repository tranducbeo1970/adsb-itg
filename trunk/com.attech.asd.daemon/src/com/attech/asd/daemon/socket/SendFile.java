/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.socket;

import com.attech.asd.daemon.common.ExceptionHandler;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haianh
 */
public class SendFile implements Runnable {

    private Socket socket;
    private String pathFile;

    public SendFile(Socket socket, String file) {
        this.socket = socket;
        this.pathFile = file;
    }

    private void sendFile() throws IOException {
        FileInputStream fis = null;
        BufferedInputStream buffer = null;
        OutputStream os = null;
        try { 
        File myFile = new File(pathFile);
        byte[] mybytearray = new byte[(int) myFile.length()];
        fis = new FileInputStream(myFile);
        buffer = new BufferedInputStream(fis);
        buffer.read(mybytearray, 0, mybytearray.length);
        os = socket.getOutputStream();
        //System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
        //System.out.println("Done.");
        }
        finally {
          if (buffer != null) buffer.close();
          if (os != null) os.close();
          //if (sock!=null) sock.close();
          
        }
    }

    @Override
    public void run() {
        try {
            sendFile();
        } catch (IOException ex) {
            ExceptionHandler.handle(ex);
        } finally {
            System.out.println("Thread disposed.");
        }
        
    }
}
