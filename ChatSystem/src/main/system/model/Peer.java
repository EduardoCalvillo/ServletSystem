package main.system.model;

import java.io.Serializable;
import java.net.UnknownHostException;

public class Peer implements Serializable {
	
    /**
     * Attributs
     */
    public final static int PORT_TCP = 9999;
    public final static int PORT_UDP = 4444;

    private String pseudonyme;
    private String host;
    private int port;
    private boolean disco;
    private boolean newMsg;

    /**
     * Constructors
     */

    public Peer(String host) throws UnknownHostException{
            this.host = host;
            this.port = PORT_TCP;
            this.disco = false;
            this.newMsg = false;
    }

    public Peer(String pseudo,String host) throws UnknownHostException{
            this.pseudonyme = pseudo;
            this.host = host;
            this.port = PORT_TCP;
            this.disco = false;    
            this.newMsg = false;
    }

    public Peer(String pseudonyme, String host, int port) throws UnknownHostException {
        this.pseudonyme = pseudonyme;
        this.host = host;
        this.port = port;
        this.disco = false;      
        this.newMsg = false;
    }
    
    public Peer(String pseudonyme, String host, boolean newM) throws UnknownHostException {
        this.pseudonyme = pseudonyme;
        this.host = host;
        this.port = PORT_TCP;
        this.disco = false;      
        this.newMsg = newM;
    }

    public Peer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * New methods
     */
    
    public void setPseudonyme(String pseudo) {this.pseudonyme = pseudo;}
    
    public void setDisco(boolean status) {
        this.disco = status;
    }
    
    public boolean getStatusDisconnect() {
        return this.disco;
    }
    
    public void setNewMessage(boolean status) {
        this.newMsg = status;
    }
    
    public boolean getStatusNewMessage() {
        return this.newMsg;
    }
    
    public String getPseudonyme() {
        return pseudonyme;
    }

    public String getHost(){
        return this.host;
    }

    public int getPort(){
        return this.port;
    }

    public void setPort(int localPort) {
       this.port = localPort;
    }
        
    @Override
    public String toString(){
        return this.pseudonyme + " ("+ this.host+ " : "+ this.port+ ")";
    }

}
