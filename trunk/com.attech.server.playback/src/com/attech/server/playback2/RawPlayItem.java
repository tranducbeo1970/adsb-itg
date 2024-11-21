/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback2;

import com.attech.adsb.record.RecordItem;
import com.attech.asterix.cat21.v21.Decryptor;
import com.attech.asterix.cat21.v21.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author root
 */
public class RawPlayItem extends PlayItem {

    Decryptor decryptor = new Decryptor();
    private File file;
    private long offset;

    public RawPlayItem() {
    }

    public RawPlayItem(File file) throws ClassNotFoundException, IOException, IOException {
        this.file = file;
        this.setName(file.getName());
        this.setPath(file.getPath());
        this.load();
    }

    @Override
    protected void load() throws ClassNotFoundException, IOException {

        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] byts = new byte[3];
            int numRead = 1;
            int count = 0;
            int length = 0;
            int cat21 = 0;
            int messageCat21 = 0;
            long time = 0;

            do {

                numRead = inputStream.read(byts, 0, 3);
                if (numRead == 0) {
                    break;
                }
                if (numRead != 3) {
                    // this.lblStatus.setText("Not valid");
                    break;
                }

                int msgLength = (byts[1] & 0xFF) << 8 | (byts[2] & 0xFF);

                byte[] packages = new byte[msgLength];
                numRead = inputStream.read(packages, 3, msgLength - 3);
                packages[0] = byts[0];
                packages[1] = byts[1];
                packages[2] = byts[2];

                if (numRead != msgLength - 3) {
                    break;
                }

                // List<InternalMessage> msg = (List<InternalMessage>) decryptor.extracInternalMesasge(packages, 2.1f);
                List<Message> msg = (List<Message>) decryptor.decrypt(packages);
                if (msg != null) {
                    for (Message mg : msg) {
                        if (mg.getTimeOfReportTranmission() != null) {
                            time = offset + (int) (mg.getTimeOfReportTranmission() * 1000);
                            if (this.start == 0) {
                                this.start = time;
                            }
                            break;
                        }
                    }
                    // cat21++;
                    // messageCat21 += msg.size();
                }
                // if (msg != null) {
                //    cat21++;
                //    messageCat21 += msg.size();
                // }
                count++;
                // this.lblPackages.setText(count + " (packages)");

                length += msgLength;
                // this.lblLength.setText(length + " (bytes)");

                // this.lblCat21.setText(cat21 + " (packages)");
                // this.lblCat21MessageCount.setText(messageCat21 + " (messages)");
            } while (numRead > 0);
            this.end = time;

            // this.lblStatus.setText("Good");
        } catch (IOException ex) {
            ex.printStackTrace();
            // Logger.getLogger(CheckRawData.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try {
//            //use buffering
//            InputStream inputStream = new FileInputStream(this.file);
//            InputStream buffer = new BufferedInputStream(inputStream);
//            ObjectInput input = new ObjectInputStream(buffer);
//
//            // int counting = 0;
//            //deserialize the List
//            RecordItem item = null;
//            try {
//                while ((item = (RecordItem) input.readObject()) != null) {
//                    if (this.start == 0) {
//                        this.start = item.getTime();
//                    }
//                }
//            } catch (EOFException | StreamCorruptedException ex) {
//                this.end = item == null ? this.end : item.getTime();
//                // System.out.println("End file: " + this.path);
//                if (ex instanceof StreamCorruptedException) {
//                    System.out.println("Error: " + ex.getMessage());
//                }
//            } finally {
//                buffer.close();
//                input.close();
//                System.out.println("File: " + name + " (" + file.length() + "(bytes)) " + this.getStartTime() + " - " + this.getEndTime());
//                        
//            }
//        } catch (ClassNotFoundException | IOException ex) {
//            throw ex;
//        }
    }

    @Override
    public List<RecordItem> loadContent() throws ClassNotFoundException, IOException {
        List<RecordItem> items = new ArrayList<>();

        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] byts = new byte[3];
            int numRead = 1;
            int count = 0;
            int length = 0;
            int cat21 = 0;
            int messageCat21 = 0;
            long time = 0;

            do {
                numRead = inputStream.read(byts, 0, 3);
                if (numRead == 0) {
                    break;
                }
                if (numRead != 3) {
                    // this.lblStatus.setText("Not valid");
                    break;
                }

                int msgLength = (byts[1] & 0xFF) << 8 | (byts[2] & 0xFF);

                byte[] packages = new byte[msgLength];
                numRead = inputStream.read(packages, 3, msgLength - 3);
                packages[0] = byts[0];
                packages[1] = byts[1];
                packages[2] = byts[2];

                if (numRead != msgLength - 3) {
                    break;
                }

                // List<InternalMessage> msg = (List<InternalMessage>) decryptor.extracInternalMesasge(packages, 2.1f);
                List<Message> msg = (List<Message>) decryptor.decrypt(packages);
                for (Message mg : msg) {
                    if (mg.getTimeOfReportTranmission() != null) {
                        time = offset + (int) (mg.getTimeOfReportTranmission() * 1000);

                        break;
                    }
                }
                RecordItem item = new RecordItem(time, packages);
                items.add(item);

                count++;
                // this.lblPackages.setText(count + " (packages)");

                length += msgLength;

                // this.lblLength.setText(length + " (bytes)");
                // this.lblCat21.setText(cat21 + " (packages)");
                // this.lblCat21MessageCount.setText(messageCat21 + " (messages)");
            } while (numRead > 0);

            // this.lblStatus.setText("Good");
        } catch (IOException ex) {
            ex.printStackTrace();
            // Logger.getLogger(CheckRawData.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try {
//            //use buffering
//            InputStream inputStream = new FileInputStream(this.file);
//            InputStream buffer = new BufferedInputStream(inputStream);
//            ObjectInput input = new ObjectInputStream(buffer);
//
//            // int counting = 0;
//            //deserialize the List
//            RecordItem item = null;
//            try {
//                while ((item = (RecordItem) input.readObject()) != null) {
//                    items.add(item);
//                }
//            } catch (EOFException | StreamCorruptedException ex) {
//                items.add(item);
//                if (ex instanceof StreamCorruptedException) {
//                    System.out.println("Error: " + ex.getMessage());
//                }
//            } finally {
//                buffer.close();
//                input.close();
//                System.out.println("Read :" + items.size() + " records");
//            }
//        } catch (ClassNotFoundException | IOException ex) {
//            throw ex;
//        }
        return items;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the path
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    @Override
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the start
     */
    @Override
    public long getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    @Override
    public void setStart(long start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    @Override
    public long getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    @Override
    public void setEnd(long end) {
        this.end = end;
    }

    /**
     * @return the status
     */
    @Override
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getStartTime() {
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTimeInMillis(this.start);
        return format.format(calendar.getTime());
    }

    @Override
    public String getEndTime() {
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        calendar.setTimeInMillis(this.end);
        return format.format(calendar.getTime());
    }

    @Override
    public String getItemStatus() {
        switch (this.status) {
            case 0:
                return "Stop";
            case 1:
                return "Playing";
            case 2:
                return "Pause";
            default:
                return "";
        }
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(long offset) {
        this.offset = offset;
    }

}
