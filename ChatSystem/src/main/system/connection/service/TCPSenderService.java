package main.system.connection.service;

import java.awt.image.BufferedImage;
import java.io.File;
import main.system.connection.handler.TCPSenderHandler;
import main.system.model.Node;
import main.system.model.Peer;

public class TCPSenderService implements SenderService {

//	@Override
//	public void sendMessageTo (Peer peer, String message) throws Exception {
//		Thread t = new Thread( new TCPSenderHandler(peer,message));
//		t.start();
//	}
        
        public void sendMessageTo (Node node, String host, int port, String message) throws Exception {
		Thread t = new Thread( new TCPSenderHandler(node, host,port,message, 1));
		t.start();
	}
        
        public void sendImageTo (Node node, String host, int port, File selectedFile) throws Exception {
            Thread t = new Thread ( new TCPSenderHandler(node, host, port, selectedFile, 2));
            t.start();
        }

    @Override
    public void sendMessageTo(String host, int port, String message) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
