
import java.io.Serializable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.registry.infomodel.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HaoIlMito
 */
public class Database implements Serializable {

    public transient Connection con;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance(); //carica i driver per connettersi al database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3036/test", "root", "root"); //creo connessione al database   
            this.con = con;
        } catch (Exception e) {
            System.out.println("odjfoaijdofsai");
        }

    }

    public static void shutdown() {
        try {
            DriverManager.getConnection("com:mysql:;shutdown=true");
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws java.sql.SQLException
     */
    public boolean check_user(String username, String password) throws SQLException {

        boolean val = false;
        
        PreparedStatement stm = con.prepareStatement("select * from utenti where username=? and password=?");
        try {
            stm.setString(1, username);
            stm.setString(2, password);

            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    val = true;
                } else {
                    val = false;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return val;

    }
    
    public ArrayList<String> listaUtenti(String username, String password) throws SQLException {

                
        PreparedStatement stm = con.prepareStatement("select * from utenti");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    
                } else {
                    
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return ;

    }
}
