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
public class Creazione_gruppo extends HttpServlet {

    Database dbmanager = new Database();

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
            out.println(Stampa.header("Crea gruppo!"));
            out.println(Stampa.section_content("Qui puoi creare il tuo gruppo personale, basta inserire il nome nel riquadro sottostante"));
            out.println(Stampa.div(2));
            out.println("<div class=\"row\" style=\"padding-top: 50px\">");
            out.println("<div class=\"container .col-md-6 .col-md-offset-3\">");
            out.println("<div class=\"panel panel-primary\">");
            out.println("<div class=\"panel-heading\">");
            out.println("<h3 class=\"panel-title\">Crea Gruppo</h3></div>");
            out.println("<div class=\"panel-body\">");
            out.println("<form class=\"form-horizontal\" name=\"input\" action=\"Invita\" method=\"post\">");
            out.println("<div class=\"control-group\">");
            out.println(Stampa.label("Nome gruppo", "titolo_gruppo"));
            out.println(Stampa.input("text", "titolo_gruppo"));
            out.println("</br>");
            out.println("<div class=\"control-group\">");
            out.println(Stampa.label("Amministratore", "amministratore_gruppo: "));
            out.println(username);
            out.println("<input id=\"amministratore_gruppo\" type=\"hidden\" name=\"amministratore\" value=\"" + username + "\">");
            out.println("<input id=\"action\" type=\"hidden\" name=\"action\" value=\"1\">");
            out.println(Stampa.div(1));
            out.println("</br>");
            out.println("<hr>");
            out.println(Stampa.button("", "crea!"));
            out.println("</form>");
            out.println(Stampa.div(4));
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
