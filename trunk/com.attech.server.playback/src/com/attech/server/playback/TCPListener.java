/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.attech.server.playback;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author root
 */
public class TCPListener {
    public static void main(String [] args) throws IOException {
        String clientSentence;
          String capitalizedSentence;
          ServerSocket welcomeSocket = new ServerSocket(6789);

          while(true)
          {
             Socket connectionSocket = welcomeSocket.accept();
             BufferedReader inFromClient =new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
             DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
             clientSentence = inFromClient.readLine();
             System.out.println("Received: " + clientSentence);
             capitalizedSentence = clientSentence.toUpperCase() + '\n';
             outToClient.writeBytes(capitalizedSentence);
          }
    }
}
