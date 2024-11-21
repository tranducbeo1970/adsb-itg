/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 *
 * @author root
 */
public class Run {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MalformedURLException, SmbException, UnknownHostException, IOException {
        String user = "adsb";
        String pass = "123";

        String sharedFolder = "record";
        String path = "smb://192.168.23.204/" + sharedFolder;
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", user, pass);
        SmbFile smbFile = new SmbFile(path, auth);
        
        // SmbFileOutputStream smbfos = new SmbFileOutputStream(smbFile);
        // smbfos.write("testing....and writing to a file".getBytes());
        System.out.println("completed ...nice !" + smbFile.exists());
        
         //use buffering
            SmbFileInputStream inputStream = new SmbFileInputStream("");
            // SmbInputStream buffer = new BufferedInputStream(inputStream);
            // SmbObjectInput input = new ObjectInputStream(buffer);
    }
}
