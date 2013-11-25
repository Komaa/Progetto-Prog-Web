/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Models.Comment;
import com.itextpdf.text.Document;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author pietro
 */
@WebServlet(urlPatterns = {"/Invita"})
public class Invita extends HttpServlet {

    Database dbmanager = new Database();
    ArrayList<String> utenti = new ArrayList<String>();
    ArrayList<String> colums = new ArrayList<String>(Arrays.asList(new String[]{"Utenti", "Inviti"}));
    ArrayList<ArrayList<String>> stamptable = new ArrayList<ArrayList<String>>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            HttpSession session = request.getSession(true);//creo la sessione   //mettere le prossime 5 righe al filtro
            String username = (String) session.getAttribute("username");        //nel doFilter
            if (username == null) {
                response.sendRedirect("index.html");
            }

            String titolo_gruppo = request.getParameter("titolo_gruppo");
            String amministratore = request.getParameter("amministratore");
            String action = request.getParameter("action");
            String invito = request.getParameter("invito");

            out.println(Stampa.header("Invita utenti nel gruppo!"));
            out.println("<div class=\"container\">");
            if ((titolo_gruppo != null) && (amministratore != null)) {
                //se è già creato non bisogna entrarci di nuovo! (sennò duplica tutti gli utenti con le stampe quando refresha la pagina)
                if (action.equals("2")) {
                    out.println(Stampa.alert("info", "Hai invitato l'utente nel gruppo!"));
                    dbmanager.inserisci_utente(invito, titolo_gruppo);
                    //Ho già creato il gruppo, quindi gli metto un avviso che lo ha già creato

                }else if (!dbmanager.check_group(titolo_gruppo, amministratore)) {
                out.println(Stampa.alert("danger", "Non sei autorizzato oppure il gruppo è già esistente"));
            } else  if (action.equals("1")){
                //Creo il database con i dati
                dbmanager.inserisci_amministratore(username,titolo_gruppo);
                out.println(Stampa.alert("success", "Il gruppo è stato creato!"));
            } else if (action.equals("3")) {
                out.println(Stampa.alert("success","Hai modificato il nome del gruppo"));
            } else if (action.equals("4")) {
                out.println(Stampa.alert("info","Qui puoi modificare il titolo del gruppo oppure invitare nuovi utenti"));
            }
            }else{
                out.println(Stampa.alert("danger", "Ops, errore accesso"));
       
            } 


            
            out.println("<div class=\"jumbotron well span6 offset2\">");
            out.println("<div class=\"groupdescrition\">");

            //SE sono l'amministratore del gruppo, posso modificargli il nome e generare il pdf 
            if (username.equals(amministratore)) {
                out.println("<form action=\"cambia_titolo\">Nome gruppo: <input id=\"titolo_gruppo\" type=\"text\" name=\"titolo_gruppo_nuovo\" value=\"" + titolo_gruppo + "\">"
                        + "<input type=\"hidden\" name=\"titolo_gruppo_vecchio\" value=\"" + titolo_gruppo + "\"><input type=\"hidden\" name=\"action\" value=\"3\">  " + Stampa.button("titolo", "Cambia Titolo") + "</form></br>");
                out.println("<form action=\"GeneraPdf\">" + Stampa.button(titolo_gruppo,"Genera")+"</form>");out.println("</br>");
                out.println("Amministratore gruppo: " + amministratore + "</br></br>");
                out.println("<div class=\"userlist\">");

                utenti.clear();
                stamptable.clear();

                utenti.addAll(dbmanager.listaUtenti(amministratore, titolo_gruppo));
                System.out.println(utenti.size());
                Iterator i = utenti.iterator();
                while (i.hasNext()) {
                    String nome = (String) i.next();
                    ArrayList<String> app = new ArrayList<String>(Arrays.asList(new String[]{nome, "&Invita", "Invita"}));
                    stamptable.add(app);
                }
                if (stamptable.size() > 0) {
                    out.println(Stampa.table(titolo_gruppo, amministratore, colums, stamptable));
                } else {
                    out.println(Stampa.alert("warning", "Non ci sono utenti da invitare"));
                }

            }

            out.println(Stampa.div(4));

            out.println(Stampa.footer());
        } catch (SQLException ex) {
            Logger.getLogger(Invita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Invita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Invita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
