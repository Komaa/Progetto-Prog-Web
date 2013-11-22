
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
    
    public ArrayList<String> listaUtenti(String username) throws SQLException {
        String tmp;
        ArrayList<String> listautenti= new ArrayList<String>();
        PreparedStatement stm = con.prepareStatement("select * from utenti where username!=?");
        stm.setString(1, username);
        
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while(rs.next()) {
                    listautenti.add(rs.getString("username"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return listautenti;

    }
    
    public ArrayList<String> listaInviti() throws SQLException {
        String tmp;
        ArrayList<String> listinviti= new ArrayList<String>();
//        PreparedStatement stm = con.prepareStatement("select * from utenti");
//        try {
//            ResultSet rs = stm.executeQuery();
//            try {
//                while(rs.next()) {
//                    listinviti.add(rs.getString("username"));
//                }
//            } finally {
//                rs.close();
//            }
//        } finally {
//            stm.close();
//        }

        return listinviti;

    }

    public boolean check_group(String titolo_gruppo, String amministratore) throws SQLException {

        boolean val = false;
        
        PreparedStatement stm = con.prepareStatement("select * from gruppi where nome_gruppo=? and amministratore=?");
        try {
            stm.setString(1, titolo_gruppo);
            stm.setString(2, amministratore);

            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    //quindi il gruppo esiste già e non bisogna crearlo!
                    val = true;
                } else {
                    //non esiste e quindi si può creare!
                    PreparedStatement stm2 = con.prepareStatement("INSERT INTO gruppi (nome_gruppo,amministratore) VALUES (?,?)");
                    try {
                        val = false;
                        
                             stm2.setString(1, titolo_gruppo);
                             stm2.setString(2, amministratore);

                             //executeUpdate è per le query di inserimento!
                             stm2.executeUpdate();
                    } finally {
                        stm2.close();
                    } 
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
    public boolean inserisci_utente(String utente,String titolo_gruppo) throws SQLException {

        boolean val = false;
                    //bisognerà mettere un campo per vedere se l'utente è in attessa di accettare l'invito o meno nel where
                    //al momento ho messo uno stato "1" se sono invitati
                    PreparedStatement stm = con.prepareStatement("INSERT INTO gruppi_utenti (utente,gruppo,stato) VALUES (?,?,?)");
                    try {
                        val = false;
                        
                             stm.setString(1, utente);
                             stm.setString(2, titolo_gruppo);
                             stm.setString(3, "1");
                             //executeUpdate è per le query di inserimento!
                             stm.executeUpdate();
                    } finally {
                        stm.close();
                        
                    } 
                    val = false;
        return false;
       }

  
}
