/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.system.connection.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.system.model.Node;
import main.system.model.Peer;

/**
 *
 * @author Kuro10
 */
public class UDPSenderHandler implements Runnable{

    /*
    * Attributs
    */
    private DatagramSocket dgramSocket;
    private final DatagramPacket outPacket;
    private final String message;
    
    /*
    *   Constructors
    */
    
    public UDPSenderHandler(String host, int port, String message) throws UnknownHostException{
        this.message = message;
        outPacket = new DatagramPacket(message.getBytes(),message.length());
        outPacket.setAddress(InetAddress.getByName(host));
        outPacket.setPort(port);
    }

    public UDPSenderHandler(Node node, String msg) throws UnknownHostException {
        this.message = msg;
//        System.out.println(node.getPeer().getBroadcast().getHostName());
        outPacket = new DatagramPacket(this.message.getBytes(),this.message.length());
//        outPacket.setAddress(node.getPeer().getBroadcast());
        outPacket.setAddress(InetAddress.getByName("255.255.255.255"));
        outPacket.setPort(Peer.PORT_UDP);
    }
    
    @Override
    public void run() {
        try {
            //Request a coonection to the given peer
            this.dgramSocket = new DatagramSocket();
            dgramSocket.send(outPacket);
            dgramSocket.close();
        } catch (SocketException ex) {
            Logger.getLogger(UDPSenderHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPSenderHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
