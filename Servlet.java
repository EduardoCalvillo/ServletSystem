import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.InetAddress;
import java.util.ArrayList;

public class Servlet implements Runnable {

    public final static int PORT_SERVLET = 7777;
    private final DatagramSocket dgramSocket;
    private final DatagramPacket inPacket;
    private DatagramPacket outPacket;
    private final ArrayList<String> subscribers;
    private String outMessage;
    private volatile boolean running = true;
    
    public Servlet() throws SocketException{
        this.dgramSocket = new DatagramSocket(PORT_SERVLET); 
        byte[] buffer = new byte[256];
        this.inPacket = new DatagramPacket(buffer,buffer.length);
        this.subscribers = new ArrayList<String>();
    }
  
    public void terminate() throws IOException {
        running = false;
        dgramSocket.close();
    }
        
    @Override
    public void run() {
        try {     
            System.out.println("Running...");
            while(running){

                this.dgramSocket.receive(this.inPacket);
                String msg = new String(inPacket.getData(),0,inPacket.getLength());
                String host = inPacket.getAddress().getHostAddress();
                String pseudo = msg.split(":",3)[1];
                int port = Integer.parseInt(msg.split(":",3)[2]);
                switch(msg.split(":",3)[0]) {

                case "online":
                    System.out.println("[conn] "+host + " connects");
                    if(!subscribers.contains(host)){
                        subscribers.add(host);
                        System.out.println("[sub] "+host+" added to list");
                    }
                    outMessage=pseudo+":"+port+":"+"OK";
                    for (String subscriber : subscribers){
                        if(!subscriber.equals(host)){
                            outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length());
                            outPacket.setAddress(InetAddress.getByName(host));
                            outPacket.setPort(4444);
                            dgramSocket.send(outPacket);
                            System.out.println("[>] MSG "+outMessage);
                        } 
                    }             
                    break;

                case "offline":
                    System.out.println("[dis] "+host + " disconnects");         
                    outMessage=pseudo+":"+port+":"+"disconnect";
                    for (String subscriber : subscribers){
                        if(!subscriber.equals(host)){
                            outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length());
                            outPacket.setAddress(InetAddress.getByName(host));
                            outPacket.setPort(4444);
                            dgramSocket.send(outPacket);
                            System.out.println("[>] MSG "+outMessage);
                        }
                    }                                  
                    break;
                
                case "rename":
                    System.out.println("[rnm] "+host + " renames to " + pseudo);
                    outMessage=pseudo+":"+port+":"+"rename";
                    for (String subscriber : subscribers){
                        if(!subscriber.equals(host)){
                            outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length());
                            outPacket.setAddress(InetAddress.getByName(host));
                            outPacket.setPort(4444);
                            dgramSocket.send(outPacket);
                            System.out.println("[>] MSG "+outMessage);
                        }
                    }                   
                    break;

                default : System.out.println("[ERROR] UDP message not in format from: "+host);
                    break; 
                }
            } 
        } catch (IOException e){
            System.out.println("ERROR: Connection failure with: "+inPacket.getAddress().getHostAddress());
        } catch (Exception ex) {
            System.out.println("ERROR: Connection failure with: "+inPacket.getAddress().getHostAddress());
        }
    }

    public static void main(String[] args){
        try{
            Servlet runnableServlet = new Servlet();
            Thread listenServlet = new Thread(runnableServlet);
            listenServlet.start();
        } catch(SocketException ex) {
            System.out.println("Exception while creating Servlet");
        }
    }
    
}
