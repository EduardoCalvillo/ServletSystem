/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.system.data;

import org.apache.commons.lang3.SerializationUtils;
import java.io.File;
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tranl
 */
public class HistoryDB {

    private static final String defaultName = "history.sqlite";
    private static HistoryDB instance = null;
    private String url = "";

    private HistoryDB() {
        setName(defaultName);
        File f = new File(System.getProperty("user.dir") + File.separator + defaultName);
        if (!f.exists() || f.isDirectory()) {
            createHistoryDB();
            createHistory();
        }
    }

    public static HistoryDB getInstance() {
        if (instance == null) {
            instance = new HistoryDB();
        }
        return instance;
    }

    private Connection connect() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void setName(String name) {
        String current = System.getProperty("user.dir");
        url = "jdbc:sqlite:" + current + File.separator + name;
    }

    public void createHistoryDB() {
        try (Connection c = DriverManager.getConnection(url)) {
            if (c != null) {
                DatabaseMetaData meta = c.getMetaData();
                System.out.println("The driver names" + meta.getDriverName());
                System.out.println("New database for History created.");
            }

        } catch (SQLException e) {
        }
    }

    public void createHistory() {
        String sql
                = "CREATE TABLE IF NOT EXISTS history (\n"
                + "	hostsource VARCHAR(50) NOT NULL,\n"
                + "	hostdest VARCHAR(50) PRIMARY KEY NOT NULL,\n"
                + "	log BLOB\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("ChatLog created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addHistory(MessageLog l) {
        if (!this.existHistory(l)) {
            String sql = "INSERT INTO history(hostsource, hostdest, log) VALUES(?,?,?)";

            try (Connection conn = this.connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, l.getHostSource());
                pstmt.setString(2, l.getHostTarget());
                pstmt.setBytes(3, SerializationUtils.serialize(l));
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateHistory(MessageLog l) {
        String sql = "UPDATE history SET hostsource = ?, hostdest = ?, log = ? WHERE hostsource = ? and hostdest = ?";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, l.getHostSource());
            pstmt.setString(2, l.getHostTarget());
            pstmt.setBytes(3, SerializationUtils.serialize((Serializable) l));
            pstmt.setString(4, l.getHostSource());
            pstmt.setString(5, l.getHostTarget());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MessageLog getMessageLog(String source, String dest) {
        String sql = "SELECT log"
                + " FROM history WHERE hostsource = ? AND hostdest = ?";

        Object result = null;
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, source);
            pstmt.setString(2, dest);
            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                byte[] log = rs.getBytes("log");
                result = SerializationUtils.deserialize(log);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HistoryDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (MessageLog) result;
    }

    public boolean existHistory(MessageLog l) {
        String sql = "SELECT ROWID FROM history WHERE hostsource = ? AND hostdest = ?";

        Object result = null;
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, l.getHostSource());
            pstmt.setString(2, l.getHostTarget());
            //
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return false;
    }
}
