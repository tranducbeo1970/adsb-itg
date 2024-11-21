/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.asd.amhs.service.socket;

import com.attech.asd.amhs.service.monitor.Command;
import com.attech.asd.amhs.service.monitor.CommandType;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author ANDH
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
	// need host and port, we want to connect to the ServerSocket at port 7777
	Socket socket = new Socket("localhost", 7428);
	System.out.println("Connected!");

	// get the output stream from the socket.
	OutputStream outputStream = socket.getOutputStream();
	// create an object output stream from the output stream so we can send an object through it
	ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

	Command commnad = new Command();
	commnad.setType(CommandType.START);
	commnad.setArgument("Starting");

//	// make a bunch of messages to send.
//	List<Message> messages = new ArrayList<>();
//	messages.add(new Message("Hello from the other side!"));
//	messages.add(new Message("How are you doing?"));
//	messages.add(new Message("What time is it?"));
//	messages.add(new Message("Hi hi hi hi."));
	Thread.sleep(5000);
	System.out.println("Sending messages to the ServerSocket");
//	objectOutputStream.writeObject(messages);
	objectOutputStream.writeObject(commnad);

	Thread.sleep(10000);

	System.out.println("Closing socket and terminating program.");
	socket.close();
    }
}
