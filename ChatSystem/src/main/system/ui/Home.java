/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.system.ui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import main.system.connection.handler.TCPListenerHandler;
import main.system.connection.service.UDPSenderService;
import main.system.data.HistoryDB;
import main.system.model.Node;
import main.system.model.Peer;

/**
 * *
 *
 * @author th_tran
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates attributs
     */
    private Node node;
    DefaultListModel<String> listFriendsOnlineModel;
    static Thread listenTCP = null;
    static TCPListenerHandler runnableTCP = null;
    public static HistoryDB history;

    /**
     * Creates new form Home
     *
     * @param node
     * @param history
     */
    public Home(Node node, HistoryDB history) {
        this.node = node;
        Home.history = HistoryDB.getInstance();
        this.listFriendsOnlineModel = new DefaultListModel<>();
        for (Peer p : node.getOnlinePeers()) {
            listFriendsOnlineModel.addElement(p.getPseudonyme() + ":" + p.getHost() + ":" + p.getPort());
        }
        initComponents();
        this.nicknameLabel.setText("Your nickname : " + node.getPeer().getPseudonyme());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendsList = new javax.swing.JList<>();
        nicknameLabel = new javax.swing.JLabel();
        logOutButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        notiBox = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        renameButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home - ChatSystem");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        titleLabel.setForeground(new java.awt.Color(51, 51, 250));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("HOME");

        friendsList.setModel(listFriendsOnlineModel);
        friendsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        friendsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendsListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                friendsListMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(friendsList);

        nicknameLabel.setText("Nickname : ");

        logOutButton.setText("Log out");
        logOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOutButtonActionPerformed(evt);
            }
        });

        notiBox.setEditable(false);
        notiBox.setColumns(20);
        notiBox.setRows(5);
        jScrollPane2.setViewportView(notiBox);
        notiBox.setWrapStyleWord(true);
        notiBox.setLineWrap(true);

        notiBox.setEditable(false);

        jLabel1.setText("Notifications");

        renameButton.setLabel("Rename");
        renameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(logOutButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(renameButton))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nicknameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(logOutButton)
                            .addComponent(renameButton)))
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void friendsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendsListMouseClicked

        if (!friendsList.isSelectionEmpty()) {
            String friend = friendsList.getSelectedValue();
            String seg[] = friend.split(":");

            /* Find a peer/node when we know his nickname
            ** Then, open the chatwindow with him
             */
            try {
                Node client = new Node(new Peer(seg[0], seg[1]));
                if(seg[0].charAt(0)=="[".charAt(0)){
                   String split[] = seg[0].split("] "); 
                   client.getPeer().setPseudonyme(split[1]);
                }
                
                this.node.updatePeersList(client.getPeer());
                
                this.node.updateHome();
                this.node.getChatWindowForPeer(client.getPeer().getHost()).setTitle(client.getPeer().getPseudonyme()+": Chat");
                this.node.getChatWindowForPeer(client.getPeer().getHost()).display();
            } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_friendsListMouseClicked

    private void logOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logOutButtonActionPerformed

        /* Send a broadcast to inform a disconnection */
        try {
            new UDPSenderService().sendDisconnect(this.node);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Then, close the homepage and back to login window */
        this.node.closeAllChatWindow();
        this.setVisible(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.dispose();
        Login loginWindow = new Login(node);
        loginWindow.setTitle("You have disconnected.");
        loginWindow.display();
    }//GEN-LAST:event_logOutButtonActionPerformed

    private void friendsListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendsListMouseEntered

    }//GEN-LAST:event_friendsListMouseEntered

    private void renameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameButtonActionPerformed

        /* Open a rename window to change the nickname */
        ChangeName changeNameWindow = new ChangeName(this.node, this);
        changeNameWindow.display();
    }//GEN-LAST:event_renameButtonActionPerformed


    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        /* Send a broadcast to inform a disconnection */
        try {
            new UDPSenderService().sendDisconnect(this.node);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* Then, close the homepage and back to login window */
        this.node.closeAllChatWindow();
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Login loginWindow = new Login(node);
        loginWindow.setTitle("You have disconnected.");
        loginWindow.display();
    }//GEN-LAST:event_formWindowClosing

    /**
     * Creates methods
     */
    public void setNicknameLabel(String s) {
        nicknameLabel.setText(s);
    }

    public Node getNode() {
        return this.node;
    }

    public void removeFromList(Peer p) {
        this.listFriendsOnlineModel.removeElement(p.getPseudonyme() + ":" + p.getHost() + ":" + p.getPort());
    }

    public void display() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public DefaultListModel getFriendList() {
        return listFriendsOnlineModel;
    }

    public void writeNoti(String s) throws BadLocationException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm, dd/MM/yyyy] - ");
        notiBox.getDocument().insertString(0, sdf.format(cal.getTime()) + s + System.lineSeparator(), null);
        notiBox.setCaretPosition(0);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> friendsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logOutButton;
    private javax.swing.JLabel nicknameLabel;
    private javax.swing.JTextArea notiBox;
    private javax.swing.JButton renameButton;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
