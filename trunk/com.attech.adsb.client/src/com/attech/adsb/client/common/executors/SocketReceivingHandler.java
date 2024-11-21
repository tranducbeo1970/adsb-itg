/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.adsb.client.common.executors;

//import static Common.Main.machine;
import com.attech.adsb.client.common.Common;
import com.attech.adsb.client.common.MLogger;
import com.attech.adsb.client.common.RadarDataDecrypter;
import com.attech.adsb.client.common.TargetManager;
import com.attech.adsb.client.config.UdpConfig;
import com.attech.adsb.record.DataRecorder;
import com.attech.asterix.cat21.v21.Decryptor;
import com.attech.asterix.cat21.v21.Message;
import com.attech.cat01.v120.Cat01Message;
import com.attech.cat02.v10.Message02;
import com.attech.cat34.v127.Message34;
import com.attech.cat48.v121.Cat48Message;
import java.io.File;
//import com.attech.database.dao.AircraftDao;
//import com.attech.database.dao.ExceptionHandler;
//import com.attech.database.dao.FlightControllerDao;
//import com.attech.database.dao.TranfersDao;
//import com.attech.database.entities.Aircraft;
//import com.attech.database.entities.FlightController;
//import com.attech.database.entities.Tranfers;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author andh
 */
public class SocketReceivingHandler extends ThreadBase {


    private static final MLogger logger = MLogger.getLogger(SocketReceivingHandler.class);
    private UdpConfig config;
    private boolean running = false;
    private boolean stop = false;
    private long lastReceived;
    private TargetManager targetManager;

    // private properties
    private static final Decryptor decryptor = new Decryptor();
//    private int j = 0;
//    private BufferDataRecorder record;

    private final DecimalFormat format1 = new DecimalFormat("00");
    private final DecimalFormat format2 = new DecimalFormat("00.0");
    private final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    private long receivedTimeInMiliSecond;
    private final ScheduledExecutorService worker = Executors.newScheduledThreadPool(1);
    private DataRecorder recorder;

    public SocketReceivingHandler(UdpConfig udp) {
        this.config = udp;
       
    }
    
    @Override
    public void run() {
        
        if (this.config.isRecord()) {
            recorder = new DataRecorder(this.config.getRecordingLocation(), this.config.getIdentfyString());
            worker.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DAY_OF_YEAR, -config.getRecordingLimit());
                        Date date = cal.getTime();
                        String folderName = dateformat.format(date);
//                        System.out.println("Expired date is " + folderName);

                        File file = new File(config.getRecordingLocation());
                        if (!file.isDirectory()) {
                            return;
                        }

                        File[] files = file.listFiles();
                        for (File childFile : files) {
                            if (!childFile.isDirectory()) {
                                continue;
                            }

                            String name = childFile.getName();
                            if (folderName.compareTo(name) < 0) {
                                continue;
                            }

//                            System.out.println("Delete " + name);
                            delete(childFile);
                        }
                    } catch (Exception ex) {
                        logger.error(ex);
                    }

                }
            }, 0, 1, TimeUnit.DAYS);
        }

        try {

            while (!Thread.interrupted()) {
                switch (config.getMode()) {
                    case "UNICAST":
                        unicastHandler();
                        break;
                    case "MULTICAST":
                        multicastHandler();
                        break;
                }
            }

        } catch (InterruptedException | SocketException | UnknownHostException ex) {
            logger.error(ex);
        }
    }

    private synchronized void unicastHandler() throws InterruptedException, UnknownHostException, SocketException {
        try {
            DatagramSocket socket;
            if (this.config.getBindingIP() != null) {
                InetAddress ip = InetAddress.getByName(this.config.getBindingIP());
                socket = new DatagramSocket(this.config.getPort(), ip);
                logger.info("Start listenning on port %s (%s)", this.config.getPort(), this.config.getBindingIP());
            } else {
                socket = new DatagramSocket(this.config.getPort());
                logger.info("Start listenning on port %s", this.config.getPort());
            }

            try {
                final byte[] buffer = new byte[this.config.getBufferSize()];
                final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                while (!Thread.interrupted()) {
                    socket.receive(packet);
                    if (packet.getLength() < 3) {
                        continue;
                    }
                    if ((packet.getData()[0] & 0xFF) == 21) { // ADS-B
                        receivedTimeInMiliSecond = System.currentTimeMillis();
                        processDataPacket(packet);
                    } else { // RADAR
                        //logger.debug("Received package (%s bytes)", packet.getLength());
                        receivedTimeInMiliSecond = System.currentTimeMillis();
                        processRadarDataPacket(packet);
                    }
                }
            } catch (IOException ex) {
                logger.error(ex);
//            this.lock(this.config.getRetryInterval());
            } catch (Exception ex) {
                logger.error(ex);
            } finally {
                socket.close();
            }
        } catch (BindException ex) {
            logger.info("Cannot open port %s cause using by another program. Program is existed.", this.config.getPort());
//            System.exit(0);
        }
    }
   
    private void processDataPacket(DatagramPacket packet) throws IOException {
        int length = packet.getLength();
        byte[] data = new byte[length];
        System.arraycopy(packet.getData(), 0, data, 0, length);
        
        List<Message> message = decryptor.decrypt(data);
       	if (message == null) return;
        
        if (this.config.isRecord()) recorder.write(data );
        if (targetManager == null) return;
        
	targetManager.updateTarget(message);

    }
    
    private void processRadarDataPacket(DatagramPacket packet) throws IOException {
        int length = packet.getLength();
        byte[] data = new byte[length];
        System.arraycopy(packet.getData(), 0, data, 0, length);
        List<Cat01Message> messages01 = new ArrayList<>();
        List<Message02> messages02 = new ArrayList<>();
        List<Message34> messages34 = new ArrayList<>();
        List<Cat48Message> messages48 = new ArrayList<>();
        RadarDataDecrypter.decode(data, messages01, messages02, messages34, messages48);
        //List<Message> message = decryptor.decrypt(data);
       	if (messages01.isEmpty() && messages48.isEmpty()) {
            return;
        }
        if (messages48.size() > 0){
            if (this.config.isRecord()) 
                recorder.write(data );
            if (targetManager == null) 
                return;
            targetManager.updateTargetCat48(messages48);
        }
	if (messages01.size() > 0){
            if (this.config.isRecord()) 
                recorder.write(data );
            if (targetManager == null) 
                return;
            targetManager.updateTargetCat01(messages01);
        }

    }
    
    private void multicastHandler() throws InterruptedException {
	try {
	    final byte[] buffer = new byte[this.config.getBufferSize()];
	    final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	    final MulticastSocket socket = new MulticastSocket(this.config.getPort());
	    if (this.config.getBindingIP() != null) {
		InetAddress bindingAddress = InetAddress.getByName(this.config.getBindingIP());
		socket.setInterface(bindingAddress);
	    }
	    final InetAddress address = InetAddress.getByName(this.config.getMulticastIP());
	    socket.joinGroup(address);
	    logger.info("DUC 1 Start joining multicast %s:%s", this.config.getMulticastIP(), this.config.getPort());
	    while (!Thread.interrupted()) {
		socket.receive(packet);
                if (packet.getLength() < 3) {
                    continue;
                }
                if ((packet.getData()[0] & 0xFF) == 21) { // ADS-B
                    logger.debug("Received package (%s bytes)", packet.getLength());
                    receivedTimeInMiliSecond = System.currentTimeMillis();
                    processDataPacket(packet);
                } else { // RADAR
                    logger.debug("Received package (%s bytes)", packet.getLength());
                    receivedTimeInMiliSecond = System.currentTimeMillis();
                    processRadarDataPacket(packet);
                }
                /*
		if ((packet.getData()[0] & 0xFF) != 21 || packet.getLength() < 3) {
		    continue;
		}
		logger.debug("Received package (%s bytes)", packet.getLength());
		receivedTimeInMiliSecond = System.currentTimeMillis();
		processDataPacket(packet);
                */
	    }
	} catch (IOException ex) {
	    logger.error(ex);
//	    this.lock(this.config.getRetryInterval());
	} catch (Exception ex) {
	    logger.error(ex);
	} finally {
	}
    }
    
    private void delete(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    delete(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }

    }

    public boolean isRunning() {
        return this.running;
    }
    
    @Override
    protected MLogger getLogger() {
//	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }
    
    /**
     * @param targetManager the targetManager to set
     */
    public void setTargetManager(TargetManager targetManager) {
	this.targetManager = targetManager;
    }
    
    public static void main(String [] args) {
        DOMConfigurator.configure(Common.CFG_LOG);
        UdpConfig config = UdpConfig.load("config/udp.xml", UdpConfig.class);
        SocketReceivingHandler thread = new SocketReceivingHandler(config);
        thread.start();
    }
}
