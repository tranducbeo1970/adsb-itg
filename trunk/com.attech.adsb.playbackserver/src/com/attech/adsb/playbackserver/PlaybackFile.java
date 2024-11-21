/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.playbackserver;

import com.attech.adsb.playbackserver.config.ReadMode;
import com.attech.lib.record.RecordItem;
//import com.attech.adsb.record.RecordItem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class PlaybackFile {

    private static final Logger logger = Logger.getLogger(PlaybackFile.class);

    private int no;
    private String path;
    private String status;
    private File file;
    private String name;
    private boolean playing;

    private FileInputStream inputStream;
    private ObjectInput input;
    private ReadMode readmode;

    public PlaybackFile() {
    }

    public PlaybackFile(File file, ReadMode readmode) {
        this.file = file;
        this.path = file.getAbsolutePath();
        this.name = file.getName();
        this.readmode = readmode;
    }
    
    public void setReadMode(ReadMode readmode) {
        this.readmode = readmode;
    }

    public void open() throws FileNotFoundException, IOException {
        logger.info("Open file " + this.name);
        inputStream = new FileInputStream(file);
        if (readmode == ReadMode.Recorded || readmode == ReadMode.RawAsd) {
            InputStream buffer = new BufferedInputStream(inputStream);
            input = new ObjectInputStream(buffer);
        }
    }
    
    public byte [] read () throws IOException, ClassNotFoundException {
        if(this.readmode == ReadMode.Raw)
            return readRaw();
        if(this.readmode == ReadMode.Recorded)
            return readRecorded();
        if(this.readmode == ReadMode.RawAsd)
            return readRawAsd();
        
        return readRaw();
    }

    private byte[] readRaw() throws IOException {
        byte[] byts = new byte[3];
        int numRead = 1;

        numRead = inputStream.read(byts, 0, 3);
        if (numRead == 0) {
            return null;
        }
        if (numRead != 3) {
            return null;
        }

        int msgLength = (byts[1] & 0xFF) << 8 | (byts[2] & 0xFF);

        byte[] packages = new byte[msgLength];
        numRead = inputStream.read(packages, 3, msgLength - 3);
        packages[0] = byts[0];
        packages[1] = byts[1];
        packages[2] = byts[2];

        if ((packages[0] & 0xFF) != 21) {
            logger.info("None asterix cat21 message 's detected on file " + this.name);
            return null;
        }

        if (numRead != msgLength - 3) {
            logger.info("Invalid cat21 message 's detected on file " + this.name);
            return null;
        }

        return packages;
    }
        
    private byte[] readRecorded() throws IOException, ClassNotFoundException {
        RecordItem item = (RecordItem) input.readObject();
        if (item == null) return null;
        return item.getBytes();
    }
    
    private byte[] readRawAsd() throws IOException, ClassNotFoundException {
        com.attech.adsb.record.RecordItem item = (com.attech.adsb.record.RecordItem) input.readObject(); 
        if (item == null) return null;
        return item.getBytes();
    }

    public void close() {
        try {
            this.input.close();
            this.inputStream.close();
            
        } catch (Exception ex) {
            logger.error("Closing file " + this.getName() + " gets error " + ex.getMessage());
        }

    }
    
    public int getMessageCount() throws IOException, ClassNotFoundException {
        if(this.readmode == ReadMode.Raw)
            return getRawMessageCount();
        if(this.readmode == ReadMode.Recorded)
            return getRecordedMessageCount();
        if(this.readmode == ReadMode.RawAsd)
            return getRawAsdMessageCount();
        
        return getRawMessageCount();
    }

    private int getRawMessageCount() throws FileNotFoundException, IOException {
        logger.info("Counting number of message on file " + this.getName());
        int count = 0;
        FileInputStream inputStream = new FileInputStream(file);

        try {            
            byte[] byts = new byte[3];
            int numRead = 1;

            do {
                numRead = inputStream.read(byts, 0, 3);
                if (numRead == 0) {
                    logger.info("File: " + this.getName() + " has " + count + " (messages)");
                    return count;
                }
                if (numRead != 3) {
                    logger.info("File: " + this.getName() + " has " + count + " (messages)");
                    return count;
                }

                int msgLength = (byts[1] & 0xFF) << 8 | (byts[2] & 0xFF);

                byte[] packages = new byte[msgLength];
                numRead = inputStream.read(packages, 3, msgLength - 3);
                packages[0] = byts[0];
                packages[1] = byts[1];
                packages[2] = byts[2];

                if ((packages[0] & 0xFF) != 21) {
                    logger.info("None asterix cat21 message 's detected on file " + this.name);
                    logger.info("File: " + this.getName() + " has " + count + " (messages)");
                    return count;
                }

                if (numRead != msgLength - 3) {
                    logger.info("Invalid cat21 message 's detected on file " + this.name);
                    logger.info("File: " + this.getName() + " has " + count + " (messages)");
                    return count;
                }
                count++;
            } while (numRead > 0);

            logger.info("File: " + this.getName() + " has " + count + " (messages)");
            return count;
        } finally {
            inputStream.close();
        }

    }
    
    private int getRawAsdMessageCount() throws ClassNotFoundException, IOException {
        return 0;
    }
    
    private int getRecordedMessageCount() throws ClassNotFoundException, IOException {
        return 0;
    }

    /**
     * @return the no
     */
    public int getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(int no) {
        this.no = no;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the playing
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * @param playing the playing to set
     */
    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

}
