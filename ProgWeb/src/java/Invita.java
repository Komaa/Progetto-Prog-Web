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
ArrayList<ArrayList<String>> stamptable; 
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
        String descrizione_gruppo = request.getParameter("descrizione_gruppo");
        String invito = request.getParameter("invito");
        if((titolo_gruppo!=null)&&(descrizione_gruppo!=null)){
            //Crea gruppo all'interno del db
            
        }else if(invito!=null){
            //Aggiunge invito nel database all'utente invitato
        }else{
            //accesso non autorizzato alla pagina,redirect
        }
         out.println(Stampa.header("Invita utenti nel gruppo!"));
            out.println("<div class=\"jumbotron well span6 offset2\">");
            out.println(Stampa.div(1));
            
            out.println("<div class=\"groupdescrition\">");
            out.println("Nome gruppo:" + titolo_gruppo);
            out.println("Descrizione gruppo:" + descrizione_gruppo);
            out.println(Stampa.div(1));            
            
           out.println("<div class=\"userlist\">");
            utenti.addAll(dbmanager.listaUtenti());
            Iterator i=utenti.iterator();
            while(i.hasNext()){
            String nome=(String) i.next();
            ArrayList<String> app=new ArrayList<String>(Arrays.asList(new String[] {nome, "&Invita","Invita.java"}));
            stamptable.add(app);
            }
            out.println(Stampa.table(colums, stamptable));
            out.println(Stampa.div(1));
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
