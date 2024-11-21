/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.receiver;

import com.attech.asd.daemon.client.SimpleTransferPackage;
import com.attech.asd.daemon.AppContext;
import com.attech.asd.daemon.client.ClientInformationManager;
import com.attech.asd.daemon.DataFusion;
import com.attech.asd.daemon.recorder.DataRecorder;
import com.attech.asd.daemon.common.ExceptionHandler;
import com.attech.asd.daemon.def.UdpMode;
import com.attech.asd.database.entities.Client;
import com.attech.asd.database.entities.SensorLogs;
import com.attech.asd.database.entities.Sensors;
import com.attech.cat21.v210.Cat21Decoder;
import com.attech.cat21.v210.Cat21Message;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author AnhTH 
 * Quan ly viec nhan du lieu tu cac cam bien
 */
public class ReceivingHandler implements Runnable {

    private final String name;
    private boolean running; // status
    private UdpMode mode;
    private Date startTime;
    private final int bufferSize;
    private String root;
    private long lastReceived;
    private boolean requestStop;
    private DatagramSocket socket;
    private MulticastSocket msocket;
    private Thread thread;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = Logger.getLogger(ReceivingHandler.class);
    private List<SimpleTransferPackage> transfers;
    private final Sensors sensor;
    private final DataRecorder recorder;
    private String currentDate;

    public ReceivingHandler(Sensors sensor) {
        dateFormat.setTimeZone(AppContext.utc);
        dateFormatGmt.setTimeZone(AppContext.utc);
        
        this.sensor = sensor;
        this.name = String.format("#%s", sensor.getSic());
        this.mode = ("UNICAST".equalsIgnoreCase(sensor.getReceivingMode())) ? UdpMode.UNICAST : UdpMode.MULTICAST;
        this.bufferSize = sensor.getBufferSize();
        this.running = false;
        this.requestStop = false;

        recorder = new DataRecorder(AppContext.getStorageLocation(), Integer.toString(this.sensor.getSic()));
        recorder.setSic(this.sensor.getSic());
        recorder.setCat21(this.sensor.getSensorMode().intValue() < 2);

        transfers = new ArrayList<>();
    }

    private boolean isReset() {
        String date = dateFormatGmt.format(new Date());
        if (date.equalsIgnoreCase(currentDate)) {
            return false;
        }

        this.currentDate = date;
        return true;
    }

    public void stop() {
        logger.info(String.format("Request stop Thread #%s (SIC %s)", this.sensor.getId(), this.sensor.getSic()));
        this.requestStop = true;
        if (socket!=null && !socket.isClosed())
                    socket.close();
        if (msocket!=null && !msocket.isClosed())
                    msocket.close();
        new SensorLogs(
                sensor, 
                String.format("(%s) Request stop Thread #%s (SIC %s)",AppContext.getServerIp(), this.sensor.getId(), this.sensor.getSic()), 
                1).save();
    }

    public void start() {
        if (this.isRunning()) {
            logger.info(String.format("Start thread #%s (SIC %s). Thread is already running.", this.sensor.getId(), this.sensor.getSic()));
            new SensorLogs(
                    sensor, 
                    String.format("(%s) Start thread #%s (SIC %s). Thread is already running.", AppContext.getServerIp(), this.sensor.getId(), this.sensor.getSic()), 
                    1).save();
            return;
        }
        this.requestStop = false;
        thread = new Thread(this, "NO_" + this.sensor.getId());
        thread.start();
        logger.info(String.format("Thread #%s (SIC %s) started", this.sensor.getId(), this.sensor.getSic()));
        new SensorLogs(sensor, String.format("(%s) Thread #%s (SIC %s) started", AppContext.getServerIp(), this.sensor.getId(), this.sensor.getSic()), 1).save();
    }

    @Override
    public void run() {
        this.requestStop = false;
        this.setDescription("Starting listenning at " + dateFormat.format(new Date()));
        new SensorLogs(
                sensor, 
                "(" + AppContext.getServerIp() + ") Starting listenning at " + dateFormat.format(new Date()), 
                0).save();
        if (this.mode == UdpMode.UNICAST) {
            try {
                receivingUDPUnicast();
            } catch (SocketException ex) {
                ExceptionHandler.handle(ex);
                new SensorLogs(
                sensor, 
                "(" + AppContext.getServerIp() + ") " + ex.getMessage(), 
                2).save();
                AppContext.getInstance().setMessage(ex.getMessage());
            } catch (IOException ex) {
                ExceptionHandler.handle(ex);
                new SensorLogs(
                sensor, 
                "(" + AppContext.getServerIp() + ") " + ex.getMessage(), 
                2).save();
                AppContext.getInstance().setMessage(ex.getMessage());
            }
        } else {
            try {
                receivingUDPMulticast();
            } catch (IOException ex) {
                new SensorLogs(
                sensor, 
                "(" + AppContext.getServerIp() + ") " + ex.getMessage(), 
                2).save();
                AppContext.getInstance().setMessage(ex.getMessage());
            }
        }
        recorder.close();
        setRunning(false);
        InformationManager.instance().remove(sensor.getId());
        
    }
    
    public void addTranferPackage(SimpleTransferPackage tranfer){
        this.transfers.add(tranfer);
        new SensorLogs(
                sensor, 
                String.format("(%s) Forwarding Data has been set. (Forward to %s:%s)", AppContext.getServerIp(), tranfer.getAddress(), tranfer.getPort()), 
                1).save();
        AppContext.getInstance().setMessage(String.format("Forwarding Data has been set. (Forward to %s:%s)", tranfer.getAddress(), tranfer.getPort()));
    }
    
    public void addTranferPackageThenStart(SimpleTransferPackage tranfer){
        this.transfers.add(tranfer);
        tranfer.setRunning(true);
        new SensorLogs(
                sensor, 
                String.format("(%s) Forwarding Data has been set. (Forward to %s:%s)", AppContext.getServerIp(), tranfer.getAddress(), tranfer.getPort()), 
                1).save();
        AppContext.getInstance().setMessage(String.format("Forwarding Data has been set. (Forward to %s:%s)", tranfer.getAddress(), tranfer.getPort()));
    }

    public void addTranferPackageFromClient(Client client) {
        final SimpleTransferPackage obj = new SimpleTransferPackage(client.getId(), client.getForwardAddress(), client.getForwardPort(), client.getForwardBindIp());
        
        this.transfers.add(obj);
        new SensorLogs(
                sensor, 
                String.format("(%s) Forwarding Data has been set. (Forward to %s:%s)", AppContext.getServerIp(), client.getForwardAddress(), client.getForwardPort()), 
                1).save();
        AppContext.getInstance().setMessage(String.format("Forwarding Data has been set. (Forward to %s:%s)", client.getForwardAddress(), client.getForwardPort()));
    }
    
    public void addTranferPackageFromClientThenStart(Client client) {
        final SimpleTransferPackage obj = new SimpleTransferPackage(client.getId(), client.getForwardAddress(), client.getForwardPort(), client.getForwardBindIp());
        this.transfers.add(obj);
        obj.setRunning(true);
        new SensorLogs(
                sensor, 
                String.format("(%s) Forwarding Data has been set. (Forward to %s:%s)", AppContext.getServerIp(), client.getForwardAddress(), client.getForwardPort()), 
                1).save();
        AppContext.getInstance().setMessage(String.format("Forwarding Data has been set. (Forward to %s:%s)", client.getForwardAddress(), client.getForwardPort()));
    }

    public void removeTranferPackage(int no) {
        for (SimpleTransferPackage trans : transfers) {
            if (trans.getNo() - no == 0) {
                
                ClientInformationManager.getInstance().update(no, 0, false);
                new SensorLogs(sensor, String.format("(%s) Forwarding Data has been remove. (%s)", AppContext.getServerIp(), trans), 1).save();
                AppContext.getInstance().setMessage(String.format("Forwarding Data has been remove. (%s)", trans));
                transfers.remove(trans);
                ClientInformationManager.getInstance().remove(no);
                return;
            }
        }
    }

    private synchronized void receivingUDPUnicast() throws UnknownHostException, SocketException, IOException {
        setRunning(true);
        InformationManager.instance().update(this.sensor.getId(), 0, 0);
        // init socket
        final InetAddress ip = InetAddress.getByName(this.sensor.getReceivingBindIp());
        this.socket = new DatagramSocket(null);
        this.socket.bind(new InetSocketAddress(ip, this.sensor.getReceivingPort())); // Binding to port
        logger.info(String.format("DUC 001 Thread #%s (SIC: %s) open connection (add: %s, port: %s, type: %s)", this.sensor.getId(), this.sensor.getSic(), this.sensor.getReceivingBindIp(), this.sensor.getReceivingPort(), this.mode));
        new SensorLogs(
                sensor, 
                String.format(" DUC 002 (%s) Thread #%s (SIC: %s) open connection (add: %s, port: %s, type: %s)", AppContext.getServerIp(), this.sensor.getId(), this.sensor.getSic(), this.sensor.getReceivingBindIp(), this.sensor.getReceivingPort(), this.mode), 
                0).save();
        
        final byte[] buffer = new byte[this.bufferSize];
        final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        byte[] data;
        int length;
        if (sensor.getSensorMode() < 2) { // is ADS-B
            final List<Cat21Message> messages = new ArrayList<>();
            while (!requestStop) {
                messages.clear();
                this.socket.receive(packet);
                if ((packet.getData()[0] & 0xFF) != 21) {
                    continue;
                }
                updateLastReceicedTime();
                length = packet.getLength();
                data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                Cat21Decoder.decode(data, messages);

                //Neu co forward data thi thuc hien
                if (transfers != null && !transfers.isEmpty()) {
                    logger.info("251-Forward packets");
                    for (SimpleTransferPackage transferPackage : transfers) {
                        if (transferPackage.isRunning()){
                            int forward = transferPackage.transfer(data);
                            ClientInformationManager.getInstance().update(transferPackage.getNo(), 1, true);
                        }
                    }
                }
                //Chuyen data vao Datafusion
                DataFusion.getInstance().fuse(messages);
                //Thuc hien Record file
                this.recorder.append(data);
                this.recorder.flush();
                InformationManager.instance().update(this.sensor.getId(), 1, 0);
                this.lastReceived = System.currentTimeMillis();
            }
        } else { // RADAR
            while (!requestStop) {
                this.socket.receive(packet);
                updateLastReceicedTime();
                length = packet.getLength();
                data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                
                //Neu co forward data thi thuc hien
                if (transfers != null && !transfers.isEmpty()) {
                    logger.info("276-Forward packets");
                    for (SimpleTransferPackage transferPackage : transfers) {
                        if (transferPackage.isRunning()){
                            int forward = transferPackage.transfer(data);
                            ClientInformationManager.getInstance().update(transferPackage.getNo(), 1, true);
                        }
                    }
                }
                
                this.recorder.append(data);
                this.recorder.flush();
                InformationManager.instance().update(this.sensor.getId(), 1, 0);
                
                this.lastReceived = System.currentTimeMillis();
            }
        }
        this.socket.close();
    }

    private void receivingUDPMulticast1() throws UnknownHostException, IOException {
        this.setRunning(true);
        InformationManager.instance().update(this.sensor.getId(), 0, 0);

        
  /*
   
  DUC SUA MULTICAST    
  
   */  
  
        String mcastadd = this.sensor.getReceivingMulticastAddress();
        int port = this.sensor.getReceivingPort();
        logger.info("DIA CHI MCAST = " + mcastadd + " PORT + " + Integer.toString(port));
        final InetAddress address = InetAddress.getByName(mcastadd);
        final byte[] buffer = new byte[this.bufferSize];
        final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                
        msocket = new MulticastSocket(port);
        msocket.joinGroup(address);
        
        
        logger.info(String.format("DUC 2 Thread #%s (SIC %s) join multicast group (add: %s, port: %s, type: %s)", this.sensor.getId(), this.sensor.getSic(), sensor.getReceivingMulticastAddress(), this.sensor.getReceivingPort(), this.mode));
        new SensorLogs(
                sensor, 
                String.format("DUC 3 (%s) Thread #%s (SIC %s) join multicast group (add: %s, port: %s, type: %s)", AppContext.getServerIp(), this.sensor.getId(), this.sensor.getSic(), sensor.getReceivingMulticastAddress(), this.sensor.getReceivingPort(), this.mode), 
                0).save();
        byte[] data;
        int length = 0;
        
        if (sensor.getSensorMode() < 2) { // is ADS-B
            final List<Cat21Message> messages = new ArrayList<>();
            while (!requestStop) {
                messages.clear();
                this.msocket.receive(packet);
                if ((packet.getData()[0] & 0xFF) != 21) {
                    continue;
                }
                updateLastReceicedTime();
                length = packet.getLength();
                data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                Cat21Decoder.decode(data, messages);

                //Neu co forward data thi thuc hien
                if (transfers != null && !transfers.isEmpty()) {
                    logger.info("340-Forward packets");
                    for (SimpleTransferPackage transferPackage : transfers) {
                        if (transferPackage.isRunning()){
                            int forward = transferPackage.transfer(data);
                            ClientInformationManager.getInstance().update(transferPackage.getNo(), 1, true);
                        }
                    }
                }
                //Chuyen data vao Datafusion
                DataFusion.getInstance().fuse(messages);
                //Thuc hien Record file
                this.recorder.append(data);
                this.recorder.flush();
                InformationManager.instance().update(this.sensor.getId(), 1, 0);
                this.lastReceived = System.currentTimeMillis();
            }
        } else { // RADAR
            while (!requestStop) {
                this.msocket.receive(packet);
                updateLastReceicedTime();
                length = packet.getLength();
                data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                
                //Neu co forward data thi thuc hien
                if (transfers != null && !transfers.isEmpty()) {
                    logger.info("365-Forward packets");
                    for (SimpleTransferPackage transferPackage : transfers) {
                        if (transferPackage.isRunning()){
                            int forward = transferPackage.transfer(data);
                            ClientInformationManager.getInstance().update(transferPackage.getNo(), 1, true);
                        }
                    }
                }
                
                this.recorder.append(data);
                this.recorder.flush();
                InformationManager.instance().update(this.sensor.getId(), 1, 0);
                
                this.lastReceived = System.currentTimeMillis();
            }
        }
        this.msocket.close();
    }

    /*
    
     HAM MOI
    
    */
    
    
    private void receivingUDPMulticast() throws UnknownHostException, IOException {
        this.setRunning(true);
        InformationManager.instance().update(this.sensor.getId(), 0, 0);
  
  /*----------------------------------------
   
  DUC SUA MULTICAST    
  
        
   ----------------------------------------*/  
  
        String mcastadd = this.sensor.getReceivingMulticastAddress();
        int port = this.sensor.getReceivingPort();
        
        
  
    /*
        String nic = "Ethernet";
        
        final InetAddress address = InetAddress.getByName(mcastadd);
       
        msocket = new MulticastSocket(port);            // TU DAY
        NetworkInterface networkInterface = NetworkInterface.getByName(nic);
        
        //socket.joinGroup(new InetSocketAddress(group, port), networkInterface);
        InetAddress group = InetAddress.getByName(mcastadd);
        msocket.joinGroup(new InetSocketAddress(group, port),networkInterface);
      */  
        
         // Multicast group and port
            String bindIpAddress = this.sensor.getReceivingBindIp();
          
            // IP address of the network interface (e.g., em3)
            //String bindIpAddress = "10.24.24.201"; // Replace with the IP of em3
            
            logger.info("DIA CHI MCAST = " + mcastadd + " PORT + " + Integer.toString(port) + " NIC: " +  bindIpAddress);

            // Create a MulticastSocket and bind it to the specific interface (IP address)
            InetAddress localAddress = InetAddress.getByName(bindIpAddress);
            msocket = new MulticastSocket(port);
            msocket.setInterface(localAddress); // Bind to the NIC by IP

            // Join the multicast group
            InetAddress group = InetAddress.getByName(mcastadd);
            msocket.joinGroup(group);
        
             final byte[] buffer = new byte[this.bufferSize];
            final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                
        
        
        logger.info(String.format("DUC 2 Thread #%s (SIC %s) join multicast group (add: %s, port: %s, type: %s) at NIC IP %s", this.sensor.getId(), this.sensor.getSic(), sensor.getReceivingMulticastAddress(), this.sensor.getReceivingPort(), this.mode,bindIpAddress));
        new SensorLogs(
                sensor, 
                String.format("DUC 3 (%s) Thread #%s (SIC %s) join multicast group (add: %s, port: %s, type: %s)", AppContext.getServerIp(), this.sensor.getId(), this.sensor.getSic(), sensor.getReceivingMulticastAddress(), this.sensor.getReceivingPort(), this.mode), 
                0).save();
        byte[] data;
        int length = 0;
        logger.info("Waiting for data");
        
        if (sensor.getSensorMode() < 2) { // is ADS-B
            final List<Cat21Message> messages = new ArrayList<>();
            while (!requestStop) {
                messages.clear();
                this.msocket.receive(packet);
                
                if ((packet.getData()[0] & 0xFF) != 21 && packet == null) {
                    continue;
                }
                updateLastReceicedTime();
                length = packet.getLength();
                data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                Cat21Decoder.decode(data, messages);

                //Neu co forward data thi thuc hien
                if (transfers != null && !transfers.isEmpty()) {
                    logger.info("469-Forward packets");
                    for (SimpleTransferPackage transferPackage : transfers) {
                        if (transferPackage.isRunning()){
                            int forward = transferPackage.transfer(data);
                            ClientInformationManager.getInstance().update(transferPackage.getNo(), 1, true);
                        }
                    }
                }
                //Chuyen data vao Datafusion
                DataFusion.getInstance().fuse(messages);
                //Thuc hien Record file
                this.recorder.append(data);
                this.recorder.flush();
                InformationManager.instance().update(this.sensor.getId(), 1, 0);
                this.lastReceived = System.currentTimeMillis();
            }
        } else { // RADAR
            while (!requestStop) {
                this.msocket.receive(packet);
                updateLastReceicedTime();
                length = packet.getLength();
                data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                
                //Neu co forward data thi thuc hien
                if (transfers != null && !transfers.isEmpty()) {
                    logger.info("495-Forward packets");
                    for (SimpleTransferPackage transferPackage : transfers) {
                        if (transferPackage.isRunning()){
                            int forward = transferPackage.transfer(data);
                            ClientInformationManager.getInstance().update(transferPackage.getNo(), 1, true);
                        }
                    }
                }
                
                this.recorder.append(data);
                this.recorder.flush();
                InformationManager.instance().update(this.sensor.getId(), 1, 0);
                
                this.lastReceived = System.currentTimeMillis();
            }
        }
        this.msocket.close();
    }

    
    public synchronized boolean isIdle() {
        return System.currentTimeMillis() - this.getLastReceived() > AppContext.getErrorTimeout();
    }

    public boolean isRunning() {
        return this.running;
    }

    /**
     * @return the mode
     */
    public UdpMode getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(UdpMode mode) {
        this.mode = mode;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        //this.description = description;
        InformationItem info = InformationManager.instance().get(this.sensor.getId());
        info.setDes(description);
        InformationManager.instance().put(info);
    }

    /**
     * @return the startTime
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the root
     */
    public String getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * @return the lastReceived
     */
    public long getLastReceived() {
        return lastReceived;
    }

    public long getRecorded() {
        return 0;
    }

    public String getReceiverName() {
        return this.name;
    }

    /**
     * @param running the running to set
     */
    private synchronized void setRunning(boolean running) {
        this.running = running;
        InformationItem info = InformationManager.instance().get(this.sensor.getId());
        info.setStatus(running);
        info.setData(running);
        InformationManager.instance().put(info);
    }

    private synchronized void updateLastReceicedTime() {
        InformationItem info = InformationManager.instance().get(this.sensor.getId());
        info.setLastReceived(System.currentTimeMillis());
        InformationManager.instance().put(info);
    }

    /**
     * @return the socket
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * @return the thread
     */
    public Thread getThread() {
        return thread;
    }
    
    public int getSensorId(){
        return this.sensor.getId();
    }
}
