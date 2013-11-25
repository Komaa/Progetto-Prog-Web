/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class cambia_titolo extends HttpServlet {

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
            throws ServletException, IOException, SQLException, InterruptedException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            HttpSession session = request.getSession(true);//creo la sessione   //mettere le prossime 5 righe al filtro
            String username = (String) session.getAttribute("username");        //nel doFilter
            if (username == null) {
                response.sendRedirect("index.html");
            }

            String titolo_gruppo_nuovo = request.getParameter("titolo_gruppo_nuovo");
            String titolo_gruppo_vecchio = request.getParameter("titolo_gruppo_vecchio");
            String amministratore = request.getParameter("amministratore");

            //controllo di nuovo se il nome non è vuoto/nullo
            //controllo se l'utente che sta cambiando il nome è l'amministratore
            //controllo se il nome non è uguale a prima
            //controllo se c'è già un gruppo che si chiama come il titolo nuovo che vogliono dargli!
            if (!(titolo_gruppo_vecchio.equals(titolo_gruppo_nuovo)) && titolo_gruppo_nuovo != null && dbmanager.controllo_amministratore(titolo_gruppo_vecchio, username) && dbmanager.controllo_gruppo(titolo_gruppo_nuovo)) {
                //scrivo il titolo nuovo nel database
                dbmanager.uploadTitle(titolo_gruppo_nuovo, titolo_gruppo_vecchio);
                response.sendRedirect("Invita?titolo_gruppo="+titolo_gruppo_nuovo+"&amministratore="+amministratore+"&action=3");
            } else {
                out.println(Stampa.header("OPSS!!!"));
                out.println(Stampa.alert("danger", "Nome del gruppo non valido oppure non sei autorizzato a cambiare il titolo!"));
                out.println(Stampa.footer());
            }
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
            Logger.getLogger(cambia_titolo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(cambia_titolo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(cambia_titolo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(cambia_titolo.class.getName()).log(Level.SEVERE, null, ex);
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
