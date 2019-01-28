/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.system;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.InterfaceAddress;
import java.net.SocketException;
import main.system.model.Node;
import main.system.model.Peer;
import main.system.ui.Login;
import java.util.Enumeration;
/**
 *
 */
public class Chat {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        String ip = getLocalAddress().getHostAddress();
        //String ip = this.getAddressIP();
        Node node = new Node(new Peer(ip));
        
        Login loginWindow = new Login(node);
        loginWindow.display();
    }
    
    private static InetAddress getLocalAddress(){
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while( b.hasMoreElements()){
                for ( InterfaceAddress f : b.nextElement().getInterfaceAddresses())
                    if ( f.getAddress().isSiteLocalAddress())
                        return f.getAddress();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getAddressIP() {
        
        return null;
    }
}
