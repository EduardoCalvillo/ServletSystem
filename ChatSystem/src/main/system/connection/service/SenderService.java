package main.system.connection.service;

public interface SenderService {

    /**
     * This service is used to send message to a given peer
     *
     * @param host
     * @param port
     * @param message the message is sent to peer
     * @throws java.lang.Exception
     */
//	 void sendMessageTo (Peer peer, String message) throws Exception;
    void sendMessageTo(String host, int port, String message) throws Exception;

}
