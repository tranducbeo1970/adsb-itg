/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import com.attech.asterix.InternalMessage;
import com.attech.asterix.cat21.v21.Decryptor;
import com.attech.asterix.cat21.v21.Encryptor;
import com.attech.asterix.cat21.v21.Message;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class TestEncryptor  extends Observable implements Runnable {
    
    private static final Decryptor decryptor = new Decryptor();
    
    public static void main(String [] args) {
        // Encryptor encryptor = new Encryptor();
        List<InternalMessage> message;
        DatagramPacket packet;

        try {
            
            InetAddress ip = InetAddress.getByName("192.168.23.79");
            DatagramSocket socket = new DatagramSocket(null); 
            socket.bind(new InetSocketAddress(ip, 20555));

            byte[] buffer;
            while (true) {
                
                buffer = new byte[2048];
                packet = new DatagramPacket(buffer, buffer.length);
                
                // Waiting for data
                socket.receive(packet);
                
                // Get incoming data
                int length = packet.getLength();
                byte[] data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                System.out.println("Receved");
                
                try {
                    List<Message> messages = decryptor.decrypt(data);
                    if (messages == null) continue;
                    
                    // System.out.println("OK");
                    for (Message msg : messages) {
                        // if (!Integer.toHexString(msg.getTargetAddress()).equalsIgnoreCase("76AA61")) continue;
                        System.out.println(">" + msg);
                        /*
                        if (msg.getCallSign() == null) continue;
                        if (msg.getHighResolutionPosition() == null) continue;
                        if (msg.getFlightLevel() == null) continue;
                        if (msg.getAirborneGroundVector()== null) continue;
                        System.out.println(">>> " + msg);
                        
                        byte [] sent = encryptor.encrypt(
                                                msg.getSic(), 
                                                msg.getSac(), 
                                                msg.getCallSign(), 
                                                Integer.toHexString(msg.getTargetAddress()).toUpperCase(), 
                                                msg.getHighResolutionPosition().getLongtitude(), 
                                                msg.getHighResolutionPosition().getLatitude(), 
                                                msg.getAirborneGroundVector().getGroundSpeed(),
                                                msg.getAirborneGroundVector().getTrackAngle(),
                                                msg.getFlightLevel()
                                        );

                        
                         List<Message> temp = decryptor.decrypt(sent);
                         for (Message tmp : temp) {
                             System.out.println(tmp);
                         }
                         */
                        
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TestEncryptor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (IOException ex) {

        } finally {
            // Logging
        }
    }

    @Override
    public void run() {
        Encryptor encryptor = new Encryptor();
        List<InternalMessage> message;
        DatagramPacket packet;

        try {
            
            InetAddress ip = InetAddress.getByName("192.168.23.79");
            DatagramSocket socket = new DatagramSocket(null); 
            socket.bind(new InetSocketAddress(ip, 20553));

            byte[] buffer;
            while (true) {
                
                buffer = new byte[2048];
                packet = new DatagramPacket(buffer, buffer.length);
                
                // Waiting for data
                socket.receive(packet);
                
                // Get incoming data
                int length = packet.getLength();
                byte[] data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                
                try {
                    List<Message> messages = decryptor.decrypt(data);
                    for (Message msg : messages) {
                        if (msg.getHighResolutionPosition() == null) continue;
                        if (msg.getTrueAirSpeed() == null) continue;
                        System.out.println(">>>");
                        /*
                        byte [] sent = encryptor.encrypt(
                                                msg.getSic(), 
                                                msg.getSac(), 
                                                msg.getCallSign(), 
                                                Integer.toHexString(msg.getTargetAddress()).toUpperCase(), 
                                                msg.getHighResolutionPosition().getLongtitude(), 
                                                msg.getHighResolutionPosition().getLatitude(), 
                                                msg.getTrueAirSpeed().getValue(), 
                                                msg.getFlightLevel(),
                                                msg.getMagneticHeading(),
                                                null
                                        );
                                        */
                    }

                } catch (Exception ex) {
                    Logger.getLogger(TestEncryptor.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (IOException ex) {

        } finally {
            // Logging
        }
    }
}
