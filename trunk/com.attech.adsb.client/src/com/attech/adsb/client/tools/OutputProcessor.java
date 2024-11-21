/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.tools;

import com.attech.adsb.record.DataRecorder;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author hong
 */
public class OutputProcessor {

    /**
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }

    private String inputFile;
    private String outputFile;
    private int interval;

    public OutputProcessor() {
    }

    public void makeFile() throws InterruptedException {
        try {
            
            DataRecorder recorder = new DataRecorder(outputFile, "sector01");

            FileInputStream inputStream = new FileInputStream(inputFile);

            byte[] byts = new byte[3];
            int numRead = 1;
            int cat21 = 0;
            int messageCat21 = 0;

            do {
                numRead = inputStream.read(byts, 0, 3);
                if (numRead == 0) {
                    break;
                }
                if (numRead != 3) {
                    break;
                }

                int msgLength = (byts[1] & 0xFF) << 8 | (byts[2] & 0xFF);

                byte[] packages = new byte[msgLength];
                numRead = inputStream.read(packages, 3, msgLength - 3);
                packages[0] = byts[0];
                packages[1] = byts[1];
                packages[2] = byts[2];

                System.out.println("Read: " + numRead + 3);

                if (numRead != msgLength - 3) {
                    break;
                }

                recorder.write(packages);
                System.out.println("Write " + packages.length + " (bytes)");
                Thread.sleep(this.interval);
                
                
//                List<InternalMessage> msg = (List<InternalMessage>) decryptor.extracInternalMesasge(packages, 2.1f);
//                if (msg == null) {
//                    continue;
//                }
//                cat21++;
//                messageCat21 += msg.size();
//
//                for (InternalMessage message : msg) {
//                    String address = Integer.toHexString(message.getTargetAddress()).toUpperCase();
//                    List<InternalMessage> messageList = (List<InternalMessage>) list.get(address);
//                    if (messageList == null) {
//                        messageList = new ArrayList<>();
//                        messageList.add(message);
//                        list.put(address, messageList);
//                    } else {
//                        messageList.add(message);
//                    }
//                }

            } while (numRead > 0);
            recorder.close();
            System.out.println("Done ");

//            FileWriter outFile = new FileWriter(file.getPath());
//            PrintWriter out = new PrintWriter(outFile);
//            out.println(cat21 + " (packages) " + messageCat21 + " (messages)");
//            out.println("----------------------------------------------------");
//            for (String key : list.keySet()) {
//                List<InternalMessage> messageItems = (List<InternalMessage>) list.get(key);
//                System.out.println("Process key " + key + " " + messageItems.size() + " (messages)");
//                out.println(key + " " + messageItems.size() + " (messages) ----------------------------------------");
//                for (InternalMessage item : messageItems) {
//                    out.println(item.toString());
//                }
//            }
//            out.flush();
//            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(CheckRawData.class.getName()).log(Level.SEVERE, null, ex);
//            this.lblStatus.setText(ex.getMessage());
        }
    }

    /**
     * @return the inputFile
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * @param inputFile the inputFile to set
     */
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * @return the outputFile
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

}
