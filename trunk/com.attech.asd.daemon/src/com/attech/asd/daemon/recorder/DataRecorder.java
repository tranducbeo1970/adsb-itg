package com.attech.asd.daemon.recorder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.attech.asd.daemon.AppContext;
import com.attech.asd.daemon.PackageBuilder;
import com.attech.adsb.record.RecordItem;
import com.attech.asd.database.SensorsDao;
import com.attech.asd.database.entities.FileRecord;
import com.attech.asd.database.entities.FusedFileRecord;
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
 * @author ANhTH
 */
public class DataRecorder {

    private final String rootFolder;
    private final String identity;
    private final PackageBuilder builder;
    private final SimpleDateFormat folderFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat fileFormat = new SimpleDateFormat("HH");
    private final DecimalFormat decimalFormat = new DecimalFormat("000");
    private File file;
    private ObjectOutputStream objectStream;
    private boolean openning;

    private String tmpFileName;
    private String curFolder;
    private String curFile;
    private String tmpFolder;
    private String tmpFile;
    private Date date;
    private int sic;
    private FileRecord record;
    private FusedFileRecord recordFused;
    private boolean isCat21;
    
    private static final Logger logger = Logger.getLogger(DataRecorder.class);

    public DataRecorder(String path, String identify) {
        this.folderFormat.setTimeZone(AppContext.utc);
        this.fileFormat.setTimeZone(AppContext.utc);
        
        this.rootFolder = path;
        this.identity = identify;
        this.builder = new PackageBuilder();
        sic = 0;
        isCat21 = true;
    }
    
    public void setCat21(boolean value){
        this.isCat21 = value;
    }

    private void prepairingFile(long time) throws FileNotFoundException, IOException {
        date = new Date(time);
        tmpFolder = folderFormat.format(date);
        tmpFile = fileFormat.format(date);
        
        if (tmpFolder.equalsIgnoreCase(curFolder) && tmpFile.equalsIgnoreCase(curFile) && openning) {  
            return;
        }

        this.close();

        final File directory = (sic == 0) ? new File(rootFolder, tmpFolder) : new File(rootFolder, String.format("%s/%s", sic, tmpFolder));
        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (!directory.exists()) {
            logger.error("File not found!");
            return;
        }

        final String path = directory.getPath();
        String fileName = "";
        File tmpF;
        for (int i = 0; i < 999; i++) {
            fileName = String.format("%s.%s.%s.%s.rcd", tmpFolder, tmpFile, decimalFormat.format(i), this.identity);
            tmpF = new File(path, fileName);
            if (tmpF.exists()) {
                continue;
            }
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

        if (sic == 0) {
            recordFused = new FusedFileRecord();
            recordFused.setAbsolutePath(file.getAbsolutePath());
            recordFused.setFileName(fileName);
            recordFused.setFromtime(date);
            recordFused.setStatus(0);
            recordFused.save();
        } else {
            record = new FileRecord();
            record.setSensor(new SensorsDao().getSensorBySic(sic));
            record.setAbsolutePath(file.getAbsolutePath());
            record.setFileName(fileName);
            record.setFromtime(date);
            record.setStatus(0);
            record.save();
        }
    }

    public void append(byte[] bytes) {
        this.builder.append(bytes);
    }

    public synchronized void flush() throws IOException {
        if (builder.isEmpty()) {
            return;
        }
        
        if (isCat21)
            write(builder.toByteArray());
        else
            write(builder.toByteArray2());        
        
    }

    public synchronized void write(RecordItem item) throws IOException {
        this.prepairingFile(item.getTime());
        if (!this.openning) {
            return;
        }
        this.objectStream.writeObject(item);
    }

    public synchronized void write(byte[] bytes) throws IOException {
        final long time = System.currentTimeMillis();
        this.prepairingFile(time);
        if (!this.openning) {
            return;
        }
        final RecordItem item = new RecordItem(time, bytes);
        
        this.objectStream.writeObject(item);
    }

    public void close() {
        try {
            if (this.record != null) {
                this.record.setTotime(date);
                this.record.setStatus(1);
                this.record.save();
            }
            if (this.recordFused != null) {
                this.recordFused.setTotime(date);
                this.recordFused.setStatus(1);
                this.recordFused.save();
            }

            this.tmpFileName = null;
            if (this.objectStream == null) {
                return;
            }
            this.objectStream.flush();            
            this.objectStream.close();            
            this.file = null;
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                if(this.objectStream != null) {
                    this.objectStream.flush();            
                    this.objectStream.close(); 
                    this.file = null;
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            this.openning = false;
        }
    }

    public synchronized String getCurrentFile() {
        return this.tmpFileName == null ? "" : this.tmpFileName;
    }

    /**
     * @return the sic
     */
    public int getSic() {
        return sic;
    }

    /**
     * @param sic the sic to set
     */
    public void setSic(int sic) {
        this.sic = sic;
    }

}
