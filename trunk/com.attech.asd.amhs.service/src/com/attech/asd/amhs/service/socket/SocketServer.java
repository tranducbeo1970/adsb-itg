/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.socket;

import com.attech.asd.amhs.database.dao.HibernateUtil;
import com.attech.asd.amhs.service.MLogger;
import com.attech.asd.amhs.service.monitor.Command;
import com.attech.asd.amhs.service.monitor.ThreadStatus;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.xml.DOMConfigurator;

/**
 *
 * @author ANDH
 */
public class SocketServer extends Thread {

    private static final MLogger logger = MLogger.getLogger(ClientSocketHandler.class);

    private int port;
    private String bindIpAddress;
    private ServerSocket serverSocket;
    private List<ClientSocketHandler> socketHandlers;
    private SocketEventHandler socketEventHandler;

    public SocketServer(String bindIpAddress, int port) {
        this.bindIpAddress = bindIpAddress;
        this.port = port;
        this.socketHandlers = new ArrayList<>();
        logger.info("Socket server %s:%s is initialized", this.bindIpAddress, this.port);
    }

    @Override
    public void run() {
        try {

            while (true) {
                listenning();
            }

        } catch (IOException ex) {
            logger.warn("Socket server %s:%s failed to start listenning", this.bindIpAddress, this.port);
            logger.error(ex);
        }

    }

    private void listenning() throws IOException {
        try {
            logger.info("Start listenning");
            this.serverSocket = new ServerSocket();
            this.serverSocket.bind(new InetSocketAddress(this.bindIpAddress, port));
            while (true) {
                Socket socket = serverSocket.accept(); // blocking call, this will wait until a connection is attempted on this port.
                logger.info("New connection from %s %s", socket.getInetAddress().getHostAddress(), socket.getRemoteSocketAddress());
                ClientSocketHandler handler = new ClientSocketHandler(socket);
                handler.setEventHandler(socketEventHandler);
                this.socketHandlers.add(handler);
                logger.info("Connection count %s", this.socketHandlers.size());
                handler.start();
            }

        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.warn("Socket server %s:%s has trouble", this.bindIpAddress, this.port);
            logger.error(ex);
        }
    }

    public void notifyThreadStatus(List<ThreadStatus> status) {
        logger.info("Connection count %s", this.socketHandlers.size());
//        socketHandlers.forEach(handler -> {
//            try {
//                handler.sendStatus(status);
//            } catch (IOException ex) {
//
//            }
//        });
        Iterator<ClientSocketHandler> itr = socketHandlers.iterator();
        while (itr.hasNext()) {
            ClientSocketHandler number = itr.next();
            try {
                number.sendStatus(status); // SEND Thread status 
            } catch (IOException ex) {
                logger.error(ex);
                socketHandlers.remove(number);
                logger.info("Remove connection ID:%s", number.getId());
                logger.info("Connection count %s", this.socketHandlers.size());
            }
        }

    }

    public void removeHandler(ClientSocketHandler handler) {
        this.socketHandlers.remove(handler);
    }

    public void setSocketEventHandler(SocketEventHandler socketEventHandler) {
        this.socketEventHandler = socketEventHandler;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HibernateUtil.buildSessionFactory("config/database.xml");
        DOMConfigurator.configure("config/log.xml");

        SocketEventHandler socketEvtHandler = new SocketEventHandler() {
            @Override
            public void onCommandReceived(Command command) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                System.out.println("Received command from ");
            }

            @Override
            public void onConnectionClosed(ClientSocketHandler obj) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//		socketHandlers.remove(obj);
//		logger.info("client size %s", socketEventHandler.s);
            }
        };

        SocketServer server = new SocketServer("192.168.22.148", 7749);
        server.setSocketEventHandler(socketEvtHandler);
        server.start();

//	// don't need to specify a hostname, it will be the current machine
//	ServerSocket ss = new ServerSocket(7777);
//	System.out.println("ServerSocket awaiting connections...");
//	Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
//	System.out.println("Connection from " + socket + "!");
//
//	// get the input stream from the connected socket
//	InputStream inputStream = socket.getInputStream();
//	// create a DataInputStream so we can read data from it.
//	ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
//
//	// read the list of messages from the socket
//	List<Message> listOfMessages = (List<Message>) objectInputStream.readObject();
//	System.out.println("Received [" + listOfMessages.size() + "] messages from: " + socket);
//	// print out the text of every message
//	System.out.println("All messages:");
//	listOfMessages.forEach((msg) -> System.out.println(msg.getText()));
//
//	System.out.println("Closing sockets.");
//	ss.close();
//	socket.close();
    }
}
