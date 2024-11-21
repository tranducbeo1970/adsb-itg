package com.attech.adsb.record;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author andh
 */
public class DataRecorder {

    // private final List _listeners = new ArrayList();
    private final String rootFolder;
    private final String identity;
    private final PackageBuilder builder;
    private final SimpleDateFormat folderFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat fileFormat = new SimpleDateFormat("HH");
    private final DecimalFormat decimalFormat = new DecimalFormat("000");
    // private final int DAY_LENGTH = 86400000;
    // private final int HOUR_LENGTH = 3600000;

    // private int currentFolder;
    // private int currentFile;
    private File file;
    private ObjectOutputStream objectStream;
    private boolean openning;
    
    private String tmpFileName;
    private String curFolder;
    private String curFile;
    private String tmpFolder;
    private String tmpFile;
    private Date date;

    private static final Logger logger = Logger.getLogger(DataRecorder.class);

    public DataRecorder(String folder, String identify) {
        this.rootFolder = folder;
        this.identity = identify;
        this.builder = new PackageBuilder();
    }

    private void prepairingFile(long time) throws FileNotFoundException, IOException {
        date = new Date(time);
        tmpFolder = folderFormat.format(date);
        tmpFile = fileFormat.format(date);

        if (tmpFolder.equalsIgnoreCase(curFolder) && 
                tmpFile.equalsIgnoreCase(curFile) && 
                openning) {
            return;
        }
        this.close();

        final File directory = new File(rootFolder, tmpFolder);
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        final String path = directory.getPath();
        String fileName;
        File tmpF;
        for (int i = 0; i < 999; i++) {
            fileName = String.format("%s.%s.%s.rcd", tmpFile, decimalFormat.format(i), this.identity);
            tmpF = new File(path, fileName);
            if (tmpF.exists()) continue;
            this.file = tmpF;
            this.tmpFileName = tmpF.getName();
            logger.info(String.format("Created file path:%s", file.getAbsolutePath()));
            break;
        }

        if (this.file == null) {
            logger.error(String.format("Created file path:%s fail.", file.getAbsolutePath()));
            return;
        }

        final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
        this.objectStream = new ObjectOutputStream(stream);
        this.openning = true;
        this.curFolder = tmpFolder;
        this.curFile = tmpFile;
    }

    public void append(byte[] bytes) {
        this.builder.append(bytes);
    }

    public synchronized void flush() throws IOException {
        if (builder.isEmpty()) return;
        write(builder.toByteArray());
    }

    public synchronized void write(RecordItem item) throws IOException {
        this.prepairingFile(item.getTime());
        if (!this.openning) return;
        this.objectStream.writeObject(item);
    }

    public void write(byte[] bytes) throws IOException {
        final long time = System.currentTimeMillis();
        this.prepairingFile(time);
        if (!this.openning) return;
        final RecordItem item = new RecordItem(time, bytes);
        this.objectStream.writeObject(item);
    }

    public void close() {
        try {
            this.tmpFileName = null;
            if (this.objectStream == null) {
                return;
            }
            this.objectStream.flush();
            this.objectStream.close();
            this.file = null;
        } catch (IOException ex) {
            // Logger.getLogger(DataRecorder.class.getName()).error(ex);
            logger.error(ex.getMessage());
        } finally {
            this.openning = false;
        }
    }
    
    public synchronized String getCurrentFile() {
        return this.tmpFileName == null ? "" : this.tmpFileName;
    }


}
