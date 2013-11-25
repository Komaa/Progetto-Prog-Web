/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaoIlMito
 */
@WebServlet(urlPatterns = {"/Welcome_page"})
public class Welcome_page extends HttpServlet {

    Database dbmanager = new Database();
    ArrayList<String> inviti = new ArrayList<String>();
    ArrayList<String> colums = new ArrayList<String>(Arrays.asList(new String[]{"Gruppi", "Accetta / Rifiuta"}));
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
            }                                                                   //fino a qui

            out.println(Stampa.header("Welcome page!"));
            out.println("<div class=\"jumbotron well span6 offset2\"><h3>" + session.getAttribute("cookie") + "</h3><hr>");
            out.println(Stampa.aref("", "Inviti"));
            out.println("</br>");

            inviti.clear();
            stamptable.clear();

            inviti.addAll(dbmanager.listaInviti(username));
            Iterator i = inviti.iterator();
            while (i.hasNext()) {
                String nome = (String) i.next();
                ArrayList<String> app = new ArrayList<String>(Arrays.asList(new String[]{nome, "&Invito", "Invito"}));
                stamptable.add(app);
            }
            if (stamptable.size() > 0) {
                out.println(Stampa.table_inviti(username, colums, stamptable));
            } else {
                out.println(Stampa.alert("warning", "Non ci sono inviti"));
            }
            out.println(Stampa.sezione());
            out.println(Stampa.div(2));
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
            Logger.getLogger(Welcome_page.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Welcome_page.class.getName()).log(Level.SEVERE, null, ex);
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
