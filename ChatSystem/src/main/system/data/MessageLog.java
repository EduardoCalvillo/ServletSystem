/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.system.data;

import java.io.Serializable;
import java.util.ArrayList;
import main.system.model.*;

/**
 *
 * @author lmtran
 */
public class MessageLog implements Serializable {
    
    private Peer source;
    private Peer target;
    //private int port1;
    private int portTarget;
    private String hostSource;
    private String hostTarget;
    private ArrayList<String> log;
    private int current_index = 0;
    
    public MessageLog(Peer p1, Peer p2) {
        // TODO
        this.source = p1;
        this.target = p2;
        this.hostSource = p1.getHost();
        this.hostTarget = p2.getHost();
        this.log = new ArrayList<>(1000);
    }
    
//    public MessageLog(Peer target) {
//        // TODO
//        this.target = target;
//        this.hostTarget = target.getHost();
//        this.portTarget = target.getPort();
//        this.log = new ArrayList<>(1000);
//    }    
    
//    public MessageLog(int port1, int port2) {
//        this.port1 = port1;
//        this.port2 = port2;
//        this.log = new ArrayList<>(100);
//    }
    
    
    public void addMessage(String message) {
        this.log.add(message);
        current_index++;
    }
    
    public ArrayList<String> getLog() {
        return this.log;
    }
    
    public int getPortTarget() {
        return this.portTarget;
    } 
    
    public String getHostSource() {
        return this.hostSource;
    }    
    
    public String getHostTarget() {
        return this.hostTarget;
    }
    
    
    @Override 
    public String toString() {
        String res = "";
        for (int i = 0; i < current_index; i++) {
            res = res + this.log.get(i) + System.lineSeparator();
        }
        return res;
    }
//    @Override
//    public String toString() {
//        return "[" + this.port1 + " | " + this.port2 + "]";
//    }
    
}
