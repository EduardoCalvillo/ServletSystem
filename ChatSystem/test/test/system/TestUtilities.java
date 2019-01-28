package test.system;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import javax.swing.*;
import main.system.model.Node;
import main.system.model.Peer;
import main.system.ui.ChatWindow;
/**
 *
 * @author lmtran
 */
public class TestUtilities {
    
 public static void main(String[] args) throws MalformedURLException, UnknownHostException {
//        ChatWindow f = new ChatWindow();
//        URL url = new URL("http://www.emoticonr.com/design/yahoo/applause.gif");
//        Icon icon = new ImageIcon(url);
//        JLabel label = new JLabel(icon);
//        f.getContentPane().add(label);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.pack();
//        f.setLocationRelativeTo(null);
//        f.setVisible(true);
          Peer p1 = new Peer("p1", "localhost");
          Peer p2 = new Peer("p2", "localhost");
          Peer p3 = new Peer("p3", "localhost");
          Peer p4 = new Peer("p4", "localhost");
          Node n = new Node(p1);
          n.addPeer(p2);
          n.addPeer(p3);
          n.addPeer(p4);
          System.out.println(n.toString());
          System.out.println("and now");
          n.removePeer(new Peer("p3", "localhost"));
          System.out.println(n.toString());
    }   
}
