/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.database;

/**
 *
 * @author Kuro10
 */

import com.mysql.cj.jdbc.Driver;
import java.sql.*;

public class MyDatabase {
    public static void main(String[] args) throws SQLException {
        Connection connexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Le pilote JDBC MySQL a été chargé");
            connexion = DriverManager.getConnection("jdbc:mysql://localhost/DATABASE_NAME", "root", "PASSWORD");
            Statement state = connexion.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM user");
            ResultSetMetaData resultMeta = result.getMetaData();
            for(int i = 1; i <= resultMeta.getColumnCount(); i++)
            System.out.print(resultMeta.getColumnName(i).toUpperCase() + " | ");
            System.out.println();
            while(result.next()){
                for(int i = 1; i <= resultMeta.getColumnCount(); i++)
                System.out.print(result.getObject(i).toString() + " | ");
                System.out.println();
            }
            result.close();
            state.close();
            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
