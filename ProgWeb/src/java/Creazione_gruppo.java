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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HaoIlMito
 */
public class Creazione_gruppo extends HttpServlet {
Database dbmanager = new Database();
ArrayList<String> utenti=new ArrayList<String>();
ArrayList<String> colums = new ArrayList<String>(Arrays.asList(new String[] {"Utenti", "Inviti"}));
ArrayList<ArrayList<String>> stamptable;    /**
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
            out.println(Stampa.header("Crea gruppo!"));
            out.println("<div class=\"jumbotron well span6 offset2\">");
            out.println(Stampa.div(1));
            
            out.println("<div class=\"descriptiongroup\">");
            out.println("<form name=\"input\" action=\"www.google.com\" method=\"get\">");
            out.println(Stampa.input("text", "titolo_gruppo"));
            out.println(Stampa.input("text", "descrizione_gruppo"));
            out.println(Stampa.div(1));
            out.println("</form>");
            
            out.println("<div class=\"userlist\">");
            utenti.addAll(dbmanager.listaUtenti());
            Iterator i=utenti.iterator();
            while(i.hasNext()){
            String nome=(String) i.next();
            ArrayList<String> app=new ArrayList<String>(Arrays.asList(new String[] {nome, "&Invita","www.google.com"}));
            stamptable.add(app);
            }
            out.println(Stampa.table(colums, stamptable));
            out.println(Stampa.div(1));
            out.println(Stampa.footer());
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
        Logger.getLogger(Creazione_gruppo.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(Creazione_gruppo.class.getName()).log(Level.SEVERE, null, ex);
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
