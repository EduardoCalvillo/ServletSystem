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
                int port = Integer.parseInt(msg.split(":",4)[2]);
                switch(msg.split(":",3)[0]) {

                case "online":
                    System.out.println("[conn] "+host + " connects");
                    if(!subscribers.contains(host+":"+pseudo)){
                        subscribers.add(host+":"+pseudo);
                        System.out.println("[sub] "+host+" added to list");
                    }
                    
                    for (String subscriber : subscribers){
                        if(!subscriber.equals(host+":"+pseudo)){
                            outMessage=subscriber+":"+port+":"+"OK";
							outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length());
                            outPacket.setAddress(InetAddress.getByName(host));
                            outPacket.setPort(4444);
                            dgramSocket.send(outPacket);
                            System.out.println("[>"+host+":"+pseudo+"] MSG "+outMessage);

							outMessage=host+":"+pseudo+":"+port+":"+"OK";
							outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length());
                            outPacket.setAddress(InetAddress.getByName(subscriber.split(":",2)[0]));
                            outPacket.setPort(4444);
                            dgramSocket.send(outPacket);
                            System.out.println("[>"+subscriber+"] MSG "+outMessage);
                        } 
                    }             
                    break;

                case "offline":
                    System.out.println("[dis] "+host + " disconnects");         
                    subscribers.remove(host+":"+pseudo);
                    for (String subscriber : subscribers){
                            outMessage=host+":"+pseudo+":"+port+":"+"disconnect";
							outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length());
                            outPacket.setAddress(InetAddress.getByName(subscriber.split(":",2)[0]));
                            outPacket.setPort(4444);
                            dgramSocket.send(outPacket);
                            System.out.println("[>"+subscriber.split(":",2)[0]+"] MSG "+outMessage);
                    }                                  
                    break;
                
                case "rename":
                    String oldName = msg.split(":",4)[3];
					System.out.println("[rnm] "+host + " renames from "+oldName+"to " + pseudo);
					subscribers.remove(host+":"+oldName);
					subscribers.add(host+":"+pseudo);
                    for (String subscriber : subscribers){
                        if(!subscriber.equals(host+":"+pseudo)){
                            outMessage=subscriber+":"+port+":"+"rename";
							outPacket = new DatagramPacket(outMessage.getBytes(), outMessage.length());
                            outPacket.setAddress(InetAddress.getByName(subscriber.split(":",2)[0]));
                            outPacket.setPort(4444);
                            dgramSocket.send(outPacket);
                            System.out.println("[>"+subscriber.split(":",2)[0]+"] MSG "+outMessage);
                        }
                    }                   
                    break;

                default : System.out.println("[ERROR] UDP message not in format from: "+host);
                    break; 
                }
            } 
        } catch (IOException e){
            System.out.println("ERROR: Connection failure with: "+inPacket.getAddress().getHostAddress());
			System.out.println(e);
        } catch (Exception ex) {
            System.out.println("ERROR: Connection failure with: "+inPacket.getAddress().getHostAddress());
			System.out.println(ex);
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
