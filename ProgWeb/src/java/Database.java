

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

import Models.Comment;
import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

     public ArrayList<Comment> listaCommenti(String id_gruppo) throws SQLException {
        String tmp;
        Comment commento;
        ArrayList<Comment> listaCommenti = new ArrayList<Comment>();
        PreparedStatement stm = con.prepareStatement("select * from comments where id_gruppo=?");
        stm.setString(1, id_gruppo);
        System.out.println(stm);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    commento= new Comment(rs.getString("commenti"),rs.getString("id_utente"),rs.getString("id_gruppo"),rs.getDate("data"));
                    listaCommenti.add(commento);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return listaCommenti;

    }
     
    public ArrayList<String> listaUtenti(String username, String titolo_gruppo) throws SQLException {
        String tmp;
        ArrayList<String> listautenti = new ArrayList<String>();
        PreparedStatement stm = con.prepareStatement("select utenti.username from utenti where username!=? && utenti.username NOT IN (select gruppi_utenti.utente FROM gruppi_utenti WHERE gruppo=?)");
        stm.setString(1, username);
        stm.setString(2, titolo_gruppo);

        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
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

    public ArrayList<String> listaInviti(String utente) throws SQLException {
        String tmp;
        ArrayList<String> listinviti = new ArrayList<String>();
        PreparedStatement stm = con.prepareStatement("select * from gruppi_utenti where utente=? and stato=1");
        stm.setString(1, utente);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    listinviti.add(rs.getString("gruppo"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

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
    
        public boolean inserisci_amministratore(String utente, String titolo_gruppo) throws SQLException {

        boolean val = false;

        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        //bisognerà mettere un campo per vedere se l'utente è in attessa di accettare l'invito o meno nel where
        //al momento ho messo uno stato "1" se sono invitati
        PreparedStatement stm = con.prepareStatement("INSERT INTO gruppi_utenti (utente,gruppo,stato) VALUES (?,?,?)");
        try {
            val = false;

            stm.setString(1, utente);
            stm.setString(2, titolo_gruppo);
            stm.setString(3, "2");
            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();

        }
        val = false;
        return false;
    }

    public boolean inserisci_utente(String utente, String titolo_gruppo) throws SQLException {

        boolean val = false;

        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        //bisognerà mettere un campo per vedere se l'utente è in attessa di accettare l'invito o meno nel where
        //al momento ho messo uno stato "1" se sono invitati
        PreparedStatement stm = con.prepareStatement("INSERT INTO gruppi_utenti (utente,gruppo,stato) VALUES (?,?,?)");
        try {
            stm.setString(1, utente);
            stm.setString(2, titolo_gruppo);
            stm.setString(3, "1");
            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
            val = true;
        } finally {
            stm.close();

        }
        
        return val;
    }

    public void accetto_invito(String titolo_gruppo, String username) throws SQLException {

        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        PreparedStatement stm = con.prepareStatement("UPDATE gruppi_utenti SET stato=? where gruppo=? and utente=? and stato=?");
        try {

            stm.setString(1, "2");
            stm.setString(2, titolo_gruppo);
            stm.setString(3, username);
            stm.setString(4, "1");
            System.out.println(stm);
            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();

        }

    }

    void rifiuto_invito(String titolo_gruppo, String username) throws SQLException {

        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO
        PreparedStatement stm = con.prepareStatement("UPDATE gruppi_utenti SET stato=? where gruppo=? and utente=? and stato=?");
        try {

            stm.setString(1, "3");
            stm.setString(2, titolo_gruppo);
            stm.setString(3, username);
            stm.setString(4, "1");

            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();

        }
    }

    public void uploadTitle(String titolo_gruppo_nuovo, String titolo_gruppo_vecchio) throws SQLException {
        // STATO 1: INVITATO
        // STATO 2: ACCETTO
        // STATO 3: RIFIUTATO

        PreparedStatement stm = con.prepareStatement("UPDATE gruppi SET nome_gruppo=? where nome_gruppo=?");
        try {

            stm.setString(1, titolo_gruppo_nuovo);
            stm.setString(2, titolo_gruppo_vecchio);

            //executeUpdate è per le query di inserimento!
            stm.executeUpdate();
        } finally {
            stm.close();
        }
        PreparedStatement stm2 = con.prepareStatement("UPDATE gruppi_utenti SET gruppo=? where gruppo=?");
        try {

            stm2.setString(1, titolo_gruppo_nuovo);
            stm2.setString(2, titolo_gruppo_vecchio);

            //executeUpdate è per le query di inserimento!
            stm2.executeUpdate();
        } finally {
            stm2.close();
        }
    }

    public boolean controllo_amministratore(String titolo_gruppo_vecchio, String username) throws SQLException {
        boolean val = false;

        PreparedStatement stm = con.prepareStatement("select * from gruppi where amministratore=? and nome_gruppo=?");
        try {
            stm.setString(1, username);
            stm.setString(2, titolo_gruppo_vecchio);

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

    public boolean controllo_gruppo(String titolo_gruppo_nuovo) throws SQLException {

        boolean val = false;

        PreparedStatement stm = con.prepareStatement("select * from gruppi where nome_gruppo=?");
        try {
            stm.setString(1, titolo_gruppo_nuovo);
            ResultSet rs = stm.executeQuery();
            try {
                if (rs.next()) {
                    val = false;
                } else {
                    val = true;
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return val;

    }

    public ArrayList<String> listaGruppi(String utente) throws SQLException {
        String tmp;
        ArrayList<String> listinviti = new ArrayList<String>();
        PreparedStatement stm = con.prepareStatement("select * from gruppi_utenti where utente=? and stato=?");
        stm.setString(1, utente);
        stm.setString(2, "2");
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    listinviti.add(rs.getString("gruppo"));
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return listinviti;

    }

    public int contaCommenti(String titolo_gruppo) throws SQLException {
    
        int commenti = 0;
        PreparedStatement stm = con.prepareStatement("select COUNT(*) from comments where id_gruppo=?");
        stm.setString(1, titolo_gruppo);
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    commenti = rs.getInt(1);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return commenti;
    
    }
    
    public Date ultimoPost(String titolo_gruppo) throws SQLException {

        Date commento = null;

        PreparedStatement stm = con.prepareStatement("SELECT data from comments where id_gruppo=? ORDER BY data DESC LIMIT 1");
        
        stm.setString(1, titolo_gruppo);
        
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    commento= rs.getDate("data");
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return commento;
    }

    public boolean creaGruppo(String titolo_gruppo, String amministratore) throws SQLException {
        
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
                    Date data_creazione = Calendar.getInstance().getTime();        
                    SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd");
                    String creationDate = ft.format(data_creazione);
                    
                    //non esiste e quindi si può creare!
                    PreparedStatement stm2 = con.prepareStatement("INSERT INTO gruppi (nome_gruppo,amministratore,data) VALUES (?,?,?)");
                    try {
                        val = false;

                        stm2.setString(1, titolo_gruppo);
                        stm2.setString(2, amministratore);
                        stm2.setString(3, creationDate);
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

 

}
