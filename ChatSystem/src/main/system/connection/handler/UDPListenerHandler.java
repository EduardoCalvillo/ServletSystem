/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.system.connection.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.system.connection.service.UDPSenderService;
import main.system.model.Node;
import main.system.model.Peer;
import main.system.ui.WritableUI;

/**
 *
 * @author Kuro10
 */
public class UDPListenerHandler implements Runnable {

    /**
     * Attributs
     */
    private final Node node;
    private final DatagramSocket dgramSocket;
    private final DatagramPacket inPacket;
    private volatile boolean running = true;

    /**
     * Constructor
     *
     * @param node
     * @throws SocketException
     */
    public UDPListenerHandler(Node node) throws SocketException {
        this.node = node;
        this.dgramSocket = new DatagramSocket(Peer.PORT_UDP);  // or node.getPeer().getPort() ???
        byte[] buffer = new byte[256];
        this.inPacket = new DatagramPacket(buffer, buffer.length);
    }

    /**
     * Methods
     */
    public void terminate() throws IOException {
        running = false;
        dgramSocket.close();
    }

    @Override
    public void run() {
        try {
            System.out.println("[UDP] " + node.getPeer().getPseudonyme() + " is listening by UDP at port " + Peer.PORT_UDP + "...");
            while (running) {
                /* Analyse the message broadcast */
                this.dgramSocket.receive(this.inPacket);

                String hostser = inPacket.getAddress().getHostAddress();
                if (!hostser.equals(node.getPeer().getHost())) {
                    String msg = new String(inPacket.getData(), 0, inPacket.getLength());
                    String seg[] = msg.split(":");
                    String host = seg[0];
                    String pseudo = seg[1];
                    int port = Integer.parseInt(seg[2]);
                    msg = seg[3];

                    if (msg.equals("disconnect")) {
                        System.out.println("[dis] " + host + " sends a " + msg);
                        Peer p = new Peer(pseudo, host);
                        p.setDisco(true);
                        this.node.updatePeersList(p);
                        this.node.updateHome();
                    }

                    if (msg.equals("rename")) {
                        System.out.println("[rnm] " + host + " sends a " + msg);
                        String oldName = this.node.findNicknameByHost(host);
                        this.node.updatePeersList(new Peer(pseudo, host));
                        this.node.updateHome();
                        this.node.getChatWindowForPeer(host).setTitle(pseudo + ": Chat");
                        this.node.getHome().writeNoti(oldName + " changed name to " + pseudo);
                    }

                    if (msg.equals("OK")) {
                        System.out.println("[bcst] " + host + " responds " + msg);
                        this.node.updatePeersList(new Peer(pseudo, host));
                        //this.node.getHome().updateHome();    
                        this.node.updateHome();
                    }
                }

            }

        } catch (IOException e) {
            System.out.println("ERROR: Connection failure with: " + inPacket.getAddress().getHostAddress());
            Logger.getLogger(UDPListenerHandler.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception ex) {
            System.out.println("ERROR: Connection failure with: " + inPacket.getAddress().getHostAddress());
            Logger.getLogger(UDPListenerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
