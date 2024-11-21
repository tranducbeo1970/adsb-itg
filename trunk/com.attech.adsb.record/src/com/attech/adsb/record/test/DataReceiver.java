package com.attech.adsb.record.test;


import com.attech.adsb.record.DataRecorder;
import com.attech.asterix.IMessageDecryptor;
import com.attech.asterix.InternalMessage;
import com.attech.asterix.cat21.v21.Decryptor;
import com.attech.asterix.cat21.v21.Message;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;
import java.util.Observable;

/**
 * This class handles the message receiving job
 * It uses the Observer pattern
 * The job are :
 * + Listening on UDP port
 * + When the data is coming, It decrypt the message and notify the fusing thread
 */
public class DataReceiver extends Observable implements Runnable {
    
    public static final float ASTERIX_V_2_1 = 2.1f;
    public static final float ASTERIX_V_0_23 = 0.23f;
    
    private int bufferLenght = 1024;
    private int port;
    private int no;
    private int counting;
    private byte[] buffer;
    private float version;
    
    private final IMessageDecryptor decryptor = new Decryptor();
    
    public DataReceiver(int no, int port, int bufferLength, float version) {
        this.no = no;
        this.port = port;
        this.bufferLenght = bufferLength;
        this.counting = 0;
        this.version = version;
    }
    
    public static void main(String [] args) throws InterruptedException {
        DataReceiver data = new DataReceiver(0, 20550, 4096, ASTERIX_V_2_1);
        Thread thread = new Thread(data);
        thread.start();
        
        while (true) {
            Thread.sleep(50000);
        }
    }

    @Override
    public void run() {

        // Logging
        // Logger.info(DataReceiver.class.getSimpleName(), String.format("DataReceiver [%i] is starting", this.no));
        
        // long time = 0;
        List<Message> message;
        DatagramPacket packet;
        DataRecorder recorder = new DataRecorder("D:\\Tmp\\Record", "ALO");

        try {
            
            DatagramSocket socket = new DatagramSocket(this.port);
            
            // Logging
            // Logger.info(DataReceiver.class.getSimpleName(), String.format("DataReceiver is listening on port %i", this.port));
            
            while (true) {
                
                buffer = new byte[bufferLenght];
                packet = new DatagramPacket(buffer, buffer.length);
                
                // Waiting for data
                socket.receive(packet);
                
                // Get incoming data
                int length = packet.getLength();
                byte[] data = new byte[length];
                System.arraycopy(packet.getData(), 0, data, 0, length);
                counting++;
                
                try {
                    message = decryptor.decrypt(data);  // rechange it to 0.23 or using message deccrypting factory
//                    this.setChanged();
//                    this.notifyObservers(message);
                    System.out.println("Rceived : " + message.size() + " messages");
                    for (Message msg : message) {
                        System.out.println(" > " + msg.toString());
                    }
                    
                    recorder.write(data);
                    System.out.println(" > Append to file.");

                    // Update counting
                    // GuiNotifier.getInstance().updateReceiver(no, ReceiverGuiHelper.COLUMN_COUNTING, counting);
                } catch (Exception ex) {
                    // ExceptionHandle.handle(DataReceiver.class.getSimpleName(), ex);
                }
            }
        } catch (IOException ex) {
            // ExceptionHandle.handle(DataReceiver.class.getSimpleName(), ex);
        } finally {
            // Logging
            // Logger.info(DataReceiver.class.getSimpleName(), String.format("DataReceiver [%i] is stoped", this.no));
        }
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
     * @return the counting
     */
    public int getCounting() {
        return counting;
    }

    /**
     * @param counting the counting to set
     */
    public void setCounting(int counting) {
        this.counting = counting;
    }
    
}
