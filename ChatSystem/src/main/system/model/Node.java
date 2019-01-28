package main.system.model;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import main.system.data.ChatHistory;
import main.system.data.HistoryDB;
import main.system.data.MessageLog;
import main.system.ui.ChatWindow;
import main.system.ui.Home;

public class Node {

    private Peer peer;
    private final ArrayList<Peer> onlinePeers;
    private final HashMap<String, ChatWindow> chatWindowForPeer;//String -> ipAddress
    private String msg = "";
    
    private Home home;
//    private static ChatHistory history = new ChatHistory();
    private static final HistoryDB history = null;

    /**
    * Creates construvtors
    */

    public Node(Peer peer) {
            this.peer = peer;
            this.onlinePeers = new ArrayList();
            this.chatWindowForPeer = new HashMap<>();
            this.home = new Home(this, HistoryDB.getInstance());
    }

    /**
     * Creates new methods
     */

    public ArrayList<Peer> getOnlinePeers() {
        return this.onlinePeers;
    }
  
    public Peer getPeer(){
        return this.peer;
    }
    
    public void update (Peer newPeer) {
    	this.peer = newPeer;
    }
    

    public String userName(){
        return this.peer.getPseudonyme();
    }
	
    public void addPeer (Peer peer) {
    	this.onlinePeers.add(peer);
    }
    
    public void removePeer (Peer peer) {
        if (this.onlinePeers != null) {
            for (Peer p : this.onlinePeers) {
                if (p.getHost().equals(peer.getHost()) && p.getPort() == peer.getPort() && p.getPseudonyme().equals(peer.getPseudonyme())) {
                    this.onlinePeers.remove(p);
                }
            }
        }
        //this.onlinePeers.remove(peer);
    }

    /* Update list of Peers when a peer in the list has changed his nickname */
    public void updatePeersList(Peer peer1) {
    	// TODO
        if (peer1.getHost().equals(this.peer.getHost())) {
            return;
        }

        for (Peer peerInList : onlinePeers){
            if (peerInList.getHost().equals(peer1.getHost())){
                System.out.println("[peer] Le peer est deja dans la liste");
                if (peerInList.getPseudonyme().equals(peer1.getPseudonyme())) {
                }
                else{
                    System.out.println(" Name change "+ peerInList.getPseudonyme()+" > "+peer1.getPseudonyme());
                    peerInList.setPseudonyme(peer1.getPseudonyme());
                    this.msg = " Name change "+ peerInList.getPseudonyme()+" > "+peer1.getPseudonyme();
                }
//                if (peerInList.getPort()==peer1.getPort()) {
//                }
//                else{
//                    System.out.println(" Port change "+ peerInList.getPort()+" > "+peer1.getPort());
//                    peerInList.setPort(peer1.getPort());
//                }
                if (peer1.getStatusDisconnect() == false) {
                    System.out.println(" Status : connected ");
                    peerInList.setDisco(false);
                }
                else {
                    System.out.println(" Status : disconnected ");
                    peerInList.setDisco(true);
                }
                if (peer1.getStatusNewMessage() == false) {
                    peerInList.setNewMessage(false);
                }
                else {
                    System.out.println(" New Message Status : new message ");
                    peerInList.setNewMessage(true);
                }
                return;
            }
        }
        System.out.println("[peer] New Peer: Host>"+peer1.getHost()+" Pseudo>"+peer1.getPseudonyme());
        this.addPeer(peer1);
    }
    
    public void setChatWindowForPeer(Peer peer1,ChatWindow chatWindow) {
        this.chatWindowForPeer.put(peer1.getHost(),chatWindow);
    }

    public ChatWindow getChatWindowForPeer(String ipAddress){
        return this.chatWindowForPeer.get(ipAddress);
    }
    
    public boolean existChatWindow(Peer p) {
        //boolean res = false;
        return this.chatWindowForPeer.containsKey(p.getHost());
    }
    
    public void closeAllChatWindow() {
        for (ChatWindow c : chatWindowForPeer.values()) {
            c.closeWindow();
        }
    }
    
    public Home getHome() {
        return this.home;
    }
    
    public String findNicknameByHost (String host) {
    	// TODO
        String res = "";
        for(Peer p : this.getOnlinePeers()) {
            if (p.getHost().equals(host)) {
                res = p.getPseudonyme();
            }
        }
        return res;
    }
     
    public InetAddress getBroadcast() throws UnknownHostException {

        InetAddress myIpAddress = InetAddress.getByName(this.peer.getHost());
        NetworkInterface temp;
        InetAddress iAddr = null;
        try {
            temp = NetworkInterface.getByInetAddress(myIpAddress);
            List<InterfaceAddress> addresses = temp.getInterfaceAddresses();

            for (InterfaceAddress inetAddress : addresses) {
                iAddr = inetAddress.getBroadcast();
            }
            System.out.println("Call in Peer.getBroadcast : " + iAddr);
            return iAddr;

            } catch (SocketException e) {
                e.printStackTrace();
            }
        return null;
    }
    
    public HistoryDB getHistory() {
        return this.history;
    }
    
    public void updateHome() {
        /* Update list friends online */
        this.home.getFriendList().removeAllElements();
        for(Peer p : this.getOnlinePeers()){ 
            if (p.getStatusDisconnect() == false){ //!(this.node.getPeer().getPseudonyme().equals(p.getPseudonyme())) && 
                
                if (p.getStatusNewMessage()) {
                    this.home.getFriendList().addElement("[!] "+p.getPseudonyme()+ ":"+ p.getHost()); 
                } else {
                    this.home.getFriendList().addElement(p.getPseudonyme()+ ":"+ p.getHost());          
                }
                
                 
                if (!this.existChatWindow(p)) {
                    MessageLog l = new MessageLog(this.getPeer(), p);
                if (HistoryDB.getInstance().existHistory(l)) {
                        l = HistoryDB.getInstance().getMessageLog(this.getPeer().getHost(), p.getHost());
                    }
                    else {
                        HistoryDB.getInstance().addHistory(l);
                    }
                    ChatWindow chatWindow = new ChatWindow(this, new Node(p), l);
                    this.setChatWindowForPeer(p, chatWindow);
                }
//                System.out.println(p.getPseudonyme() + p.getStatusDisconnect());
            }
        }
        
    }
    
    public Boolean checkNameUniq() {
        Boolean res = true;
        for (Peer p : this.getOnlinePeers()) {
            System.out.println(p.getPseudonyme());
            if (this.getPeer().getPseudonyme().equals(p.getPseudonyme())) {
                res = false;
            }
        }
        return res;
    }

    
    @Override
    public String toString(){
    String str = "This peer is " + this.peer.toString()+  " and his friends list :\n";
    for (Peer p : onlinePeers) {
        str += "- " + p.toString()+"\n";
    } 
    return str;
    }
    
    
	
}
