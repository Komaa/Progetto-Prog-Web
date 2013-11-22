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

/**
 *
 * @author pietro
 */
@WebServlet(urlPatterns = {"/Invita"})
public class Invita extends HttpServlet {
Database dbmanager = new Database();
ArrayList<String> utenti=new ArrayList<String>();
ArrayList<String> colums = new ArrayList<String>(Arrays.asList(new String[] {"Utenti", "Inviti"}));
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            

        String titolo_gruppo = request.getParameter("titolo_gruppo");
        String amministratore = request.getParameter("amministratore");
        String invito = request.getParameter("invito");
        
        
        if((titolo_gruppo!=null)&&(amministratore!=null))
        //se è già creato non bisogna entrarci di nuovo! (sennò duplica tutti gli utenti con le stampe quando refresha la pagina)
        if (dbmanager.check_group(titolo_gruppo,amministratore)){
        {
            //Ho già creato il gruppo, quindi gli metto un avviso che lo ha già creato
            out.println(Stampa.alert("danger","Non sei autorizzato oppure il gruppo è già esistente"));
        }}else if(invito!=null){
            //Aggiunge invito nel database all'utente invitato
            out.println(Stampa.alert("info","Hai invitato l'utente nel gruppo!"));
        }else{
            //Creo il database con i dati
            out.println(Stampa.alert("success","Il gruppo è stato creato!"));
        }
            out.println(Stampa.header("Invita utenti nel gruppo!"));
            out.println("<div class=\"jumbotron well span6 offset2\">");      
            out.println("<div class=\"groupdescrition\">");
            out.println("Nome gruppo: " + titolo_gruppo +"</br>");
            out.println("Amministratore gruppo: " + amministratore+"</br></br>");
                       out.println("<div class=\"userlist\">");
            utenti.addAll(dbmanager.listaUtenti());
            Iterator i=utenti.iterator();
            while(i.hasNext()){
            String nome=(String) i.next();
            ArrayList<String> app=new ArrayList<String>(Arrays.asList(new String[] {nome, "&Invita","Invita"}));
            stamptable.add(app);
            }
            out.println(Stampa.table(colums, stamptable));
            out.println(Stampa.div(3));          

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
        processRequest(request, response);
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
        processRequest(request, response);
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
