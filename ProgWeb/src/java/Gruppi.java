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
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaoIlMito
 */
public class Gruppi extends HttpServlet {
    
    Database dbmanager = new Database();
    ArrayList<String> gruppi = new ArrayList<String>();
    ArrayList<String> colums = new ArrayList<String>(Arrays.asList(new String[]{"Gruppi", "Entra nel forum", "Gestione gruppo"}));
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
                response.sendRedirect("index");
            }      
            
            out.println(Stampa.header("Lista dei gruppi"));
            out.println(Stampa.section_content("Qui puoi vedere la lista dei gruppi a cui sei iscritto o sei l'amministratore"));
            out.println(Stampa.div(2));
            
            gruppi.clear();
            stamptable.clear();
            
            gruppi.addAll(dbmanager.listaGruppi(username));
            Iterator i = gruppi.iterator();
            System.out.println(gruppi.size());
            while (i.hasNext()) {
                String nome = (String) i.next();
                ArrayList<String> app = new ArrayList<String>(Arrays.asList(new String[]{nome, "&entra", "Forum", "%titolo_gruppo", "Invita"}));
                stamptable.add(app);
            }
            
            out.println("<div class=\"container span4 offset4\"><br>");
            
            if (stamptable.size()== 0) {
                out.println(Stampa.alert("info","Non sei iscritto a nessun gruppo"));
            } else {
                out.println(Stampa.table_gruppi(username, colums, stamptable));
            }
            out.println(Stampa.div(3));
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
            Logger.getLogger(Gruppi.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Gruppi.class.getName()).log(Level.SEVERE, null, ex);
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
