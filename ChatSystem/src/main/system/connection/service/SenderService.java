package main.system.connection.service;

import main.system.model.Peer;

public interface SenderService {

	/** 
	 * This service is used to send message to a given peer
	 * @param peer 		the peer that we want to send to
	 * @param message 	the message is sent to peer 
	 */
	
//	 void sendMessageTo (Peer peer, String message) throws Exception;
         
	 void sendMessageTo (String host, int port, String message) throws Exception;
	
}
