/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.system.data;

import java.util.ArrayList;

/**
 *
 * @author lmtran
 */
public class ChatHistory {
    
    private final ArrayList<MessageLog> history;
    private static int current_index = 0;
    
    public ChatHistory() {
        this.history = new ArrayList<>(100);
    }
    
    public void addHistory(MessageLog l) {
        if (!this.existHistory(l)) {
            this.history.add(l);
            current_index++;
        }
    }
    
//    public boolean existHistory(MessageLog l) {
//        boolean res = false;
//        for (int i = 0; i < current_index; i++) {
//            if (this.history.get(i).getPortTarget() == l.getPortTarget()) {
//                res = true;
//                break;
//            }
//        }
//        return res;
//    }
    
     public boolean existHistory(MessageLog l) {
        boolean res = false;
        for (int i = 0; i < current_index; i++) {
            if (this.history.get(i).getHostSource().equals(l.getHostSource()) && this.history.get(i).getHostTarget().equals(l.getHostTarget())) {
                res = true;
                break;
            }
        }
        return res;
    }
    
//    public MessageLog getMessageLog(int target) {
//        MessageLog res = null;
//        for (int i = 0; i < current_index; i++) {
//            if (this.history.get(i).getPortTarget() == target) {
//                res = this.history.get(i);
//                break;
//            }
//        }
//        return res;
//    }
     
     public MessageLog getMessageLog(String source, String target) {
        MessageLog res = null;
        for (int i = 0; i < current_index; i++) {
            if (this.history.get(i).getHostSource().equals(source) && this.history.get(i).getHostTarget().equals(target)) {
                res = this.history.get(i);
                break;
            }
        }
        return res;
    }
    
    
    
    @Override 
    public String toString() {
        String res = "";
        for (int i = 0; i < current_index; i++) {
            res = res + "[" + this.history.get(i).getHostSource() + " | " + this.history.get(i).getHostTarget() + "]" + System.lineSeparator();
        }
        return res;
    }
    
    
//    public boolean existHistory(int port1, int port2) {
//        int i = 0;
//        boolean found = false;
//        while (i < this.history.size() && found == false) {
//            found = (this.history.get(i).getPort1() == port1 && this.history.get(i).getPort2() == port2);
//        }
//        return found;
//    }
    
//    public MessageLog getMessageLog(int port1, int port2) {
//        MessageLog res = null;
//        int i = 0;
//        boolean found = false;
//        while (i < this.history.size() && found == false) {
//            if(this.history.get(i).getPort1() == port1 && this.history.get(i).getPort2() == port2) {
//                res = this.history.get(i);
//                found = true;
//            }
//        }
//        return res;
//    }
    
}
