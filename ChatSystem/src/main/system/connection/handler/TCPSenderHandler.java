package main.system.connection.handler;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import main.system.model.Peer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import javax.imageio.ImageIO;
import main.system.model.Node;

public class TCPSenderHandler implements Runnable {

    /**
     * Attributs
     */
    private final Node node;
    private Socket chatSocket;
    private PrintWriter out;
    private final String message;
    private final String host;
    private final int port;
    private final int type;
    private final File file;

    /**
     *
     * @param node
     * @param host
     * @param port
     * @param message
     * @param type
     * @throws IOException
     */
    //Sending Text
    public TCPSenderHandler(Node node, String host, int port, String message, int type) throws IOException {
        this.node = node;
        this.host = host;
        this.port = port;
        this.message = message;
        this.type = type;
        this.file = null;
    }

    //Sending images
    public TCPSenderHandler(Node node, String host, int port, File selectedFile, int type) throws IOException {
        this.node = node;
        this.host = host;
        this.port = port;
        this.file = selectedFile;
        this.message = selectedFile.getName();
        this.type = type;
    }

    //Encoder for images
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = Base64.getEncoder().encodeToString(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    /**
     * Methods
     */
    @Override
    public void run() {
        try {
            switch (type) {
                case 1: //Text Message

                    /* Request a connection to the given peer  */
                    System.out.println("Connecting to port " + port + " and host " + host);
                    chatSocket = new Socket(host, Peer.PORT_TCP);

                    /* Initialization the output channel */
                    this.out = new PrintWriter(chatSocket.getOutputStream());

                    /* Send the message...*/
                    out.println(message + ":" + this.node.getPeer().getPseudonyme() + ":" + this.node.getPeer().getPort());
                    out.flush();

                    /* Close the socket */
                    chatSocket.close();
                    break;

                case 2: //Image Message

                    /*Get path and extension*/
                    String path = this.file.getAbsoluteFile().toString();
                    String[] split = path.split("[.]");
                    String ext = split[1];

                    /*Request a connection to the given Peer*/
                    chatSocket = new Socket(host, Peer.PORT_TCP);

                    /*Encoding of the image*/
                    BufferedImage bimg = ImageIO.read(this.file);
                    String imgAsString = encodeToString(bimg, ext);

                    /*Initialization of the output channel*/
                    this.out = new PrintWriter(chatSocket.getOutputStream());

                    /*Send the image as text*/
                    out.println(imgAsString + ":" + this.node.getPeer().getPseudonyme() + ":" + ext + ":" + this.file.getName());
                    out.flush();

                    /*Close the socket*/
                    chatSocket.close();
                    break;
                default:
                    System.out.println("Invalid type of message");
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
