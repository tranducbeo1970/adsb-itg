/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.daemon.client;

import com.attech.asd.daemon.AppContext;
import com.attech.asd.daemon.DataFusion;
import com.attech.asd.daemon.PackageBuilder;
import com.attech.asd.daemon.common.ExceptionHandler;
import com.attech.cat21.v210.Cat21Message;
import com.attech.asd.database.entities.Areas;
import com.attech.asd.database.entities.Circulars;
import com.attech.asd.database.entities.Client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author AnhTH Quan ly viec phat du lieu den cac client
 */
public class ClientBroadcaster implements Observer {

    private int no;
    private String name;
    //private UdpMode mode;
    private String address;
    private int port;
    private String bindingAddress;
    private boolean active = false;
    private IValidation heightValidation;
    private List<IValidation> areaValidations;
    private List<IValidation> cirValidations;
    protected int counting;
    protected DatagramSocket socket;
    protected InetAddress inetAddress;
    protected DatagramPacket dp;
    private boolean isError = false;
    private final PackageBuilder builder;
    public boolean isForwarding;
    public int sicFwd;

    //private final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
    private static final Logger logger = Logger.getLogger(ClientBroadcaster.class);

    public ClientBroadcaster() {
        this.counting = 0;
        this.builder = new PackageBuilder();
        this.areaValidations = new ArrayList<>();
        this.cirValidations = new ArrayList<>();
    }

    public ClientBroadcaster(Client client) {
        this();
        this.no = client.getId();
        this.name = client.getName();
        //this.mode = ("UNICAST".equalsIgnoreCase(client.getForwardMode())) ? UdpMode.UNICAST : UdpMode.MULTICAST;
        this.address = client.getForwardAddress();
        this.port = client.getForwardPort();
        this.bindingAddress = client.getForwardBindIp();

        isForwarding = client.isForwarding();
        sicFwd = (isForwarding) ? client.getSicFwd() : 0;
        
        if (!isForwarding) {
            if (client.isAreaValidate()) {
                for (Areas area : client.getAreas()) {
                    areaValidations.add(new AreaValidation(area.makeArea()));
                }
            }

            if (client.isCircularValidate()) {
                for (Circulars area : client.getCirculars()) {
                    cirValidations.add(new CircularValidation(area));
                }
            }

            if (client.isHeightValidate()) {
                this.heightValidation = new HeightValidation(client.getHeightMin(), client.getHeightMax());
            }
            
            ClientInformationManager.getInstance().update(no, 0, this.active);
        }

    }

    public void initSocket() {
        if (this.active) {
            logger.info(String.format("Client #%d %s has alread initialized", this.getNo(), this.name));
            AppContext.getInstance().setMessage(String.format("Client #%d %s has alread initialized", this.getNo(), this.name));
            return;
        }

        try {
            this.socket = new DatagramSocket(null);
            if (this.bindingAddress != null && !this.bindingAddress.isEmpty()) {
                this.socket.bind(new InetSocketAddress(bindingAddress, 0));
            }
            this.inetAddress = InetAddress.getByName(this.address);
            this.isError = false;
            this.active = true;
            logger.info(String.format("Client #%d %s initialized", this.getNo(), this.name));

        } catch (SocketException | UnknownHostException ex) {
            logger.error(String.format("Client #%d %s cannot initialize cause of some error (%s)", this.getNo(), this.name, ex.getMessage()));
            AppContext.getInstance().setMessage(String.format("Client #%d %s cannot initialize cause of some error (%s)", this.getNo(), this.name, ex.getMessage()));
            logger.error(ex);
            this.isError = true;
            this.active = false;

        } finally {
            ClientInformationManager.getInstance().update(getNo(), 0, this.active);
        }
    }

    public void broadcast(List<Cat21Message> messages) throws IOException {
        if (!this.active || this.isError || messages.size() <=0) {
            return;
        }
        for (Cat21Message msg : messages) {
            // VALIDATE ALT
            if (heightValidation != null && !heightValidation.validate(msg)) {
                continue;
            }
            // VALIDATE REGION
            if (areaValidations.isEmpty() && cirValidations.isEmpty()) {
                builder.append(msg.getBytes());
            } else if (!areaValidations.isEmpty() && cirValidations.isEmpty()) {
                for (IValidation areaValidation : areaValidations) {
                    if (areaValidation != null && areaValidation.validate(msg)) {
                        builder.append(msg.getBytes());
                        break;
                    }
                }
            } else if (areaValidations.isEmpty() && !cirValidations.isEmpty()) {
                for (IValidation validation : cirValidations) {
                    if (validation.validate(msg)) {
                        builder.append(msg.getBytes());
                        break;
                    }
                }
            } else {
                boolean pass = false;
                for (IValidation areaValidation : areaValidations) {
                    if (areaValidation != null && areaValidation.validate(msg)) {
                        builder.append(msg.getBytes());
                        pass = true;
                        break;
                    }
                }
                if (!pass)
                    for (IValidation validation : cirValidations) {
                        if (validation.validate(msg)) {
                            builder.append(msg.getBytes());
                            break;
                        }
                    }
            }
        }
        final byte[] content = builder.toByteArray();
        if (content == null) {
            return;
        }

        final DatagramPacket msgPacket = new DatagramPacket(content, content.length, inetAddress, this.port);
        this.socket.send(msgPacket);

        ClientInformationManager.getInstance().update(getNo(), 1, this.active);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            final List<Cat21Message> messages = (List<Cat21Message>) arg;
            if (messages == null) {
                return;
            }
            this.broadcast(messages);
            //System.out.printf("i:%d\n",i++);
        } catch (Exception e) {
            logger.error(String.format("Client #%d %s cannot broadcast message cause of some error (%s)", this.getNo(), this.name, e.getMessage()));
            AppContext.getInstance().setMessage(String.format("Client #%d %s cannot broadcast message cause of some error (%s)", this.getNo(), this.name, e.getMessage()));
            ExceptionHandler.handle(e);
            ClientInformationManager.getInstance().update(getNo(), 0, this.active);
        }
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean value) {
        this.active = value;
        ClientInformationManager.getInstance().update(no, 0, active);
    }

    public void deactive() {
        if (!this.active || this.socket == null) {
            return;
        }
        DataFusion.getInstance().deleteObserver(this);
        this.socket.close();
        setActive(false);
        ClientInformationManager.getInstance().remove(no);
    }

    public void active() {
        if (this.active) {
            return;
        }
        initSocket();
        DataFusion.getInstance().addObserver(this);
        setActive(true);
    }

    /**
     * @return the no
     */
    public int getNo() {
        return no;
    }
}
