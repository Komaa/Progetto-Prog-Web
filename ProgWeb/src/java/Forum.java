/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.Comment;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class Forum extends HttpServlet {
    
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession(true);//creo la sessione   //mettere le prossime 5 righe al filtro
            String username = (String) session.getAttribute("username");        //nel doFilter
            if (username == null) {
                response.sendRedirect("index.html");
            }
            
            String titolo_gruppo = request.getParameter("Accedi");
            String action = request.getParameter("action");
            String messaggio = request.getParameter("messaggio");
            String utente = request.getParameter("utente");
            if(action!=null){
            if(action.equals("1")){
                System.out.println("eccolo!");
                if((titolo_gruppo!=null)&&(messaggio!=null)&&(dbmanager.checkusertogroup(username,titolo_gruppo))&&(username.equals(utente))){
                    String cod_gruppo= dbmanager.take_cod_gruppo(titolo_gruppo);
                    String cod_utente= dbmanager.take_cod_utente(username);
                    dbmanager.addcomment(messaggio,cod_gruppo,cod_utente);
                }else{
                out.println(Stampa.header("OPSS!!!"));
                out.println(Stampa.alert("danger", "Non fare il furbo"));
                out.println(Stampa.footer());
                }
            }
            }
            
            out.println(Stampa.header("Forum del gruppo: "+titolo_gruppo));
            out.println("<div class=\"jumbotron well span6 offset2\">");
            
            out.println("<div class=\"comments\">");
            String cod_gruppo= dbmanager.take_cod_gruppo(titolo_gruppo);
            //stampo tutti i commenti nella pagina
            ArrayList<Comment> listaCommenti=dbmanager.listaCommenti(cod_gruppo);
           Iterator it= listaCommenti.iterator();
           while(it.hasNext()){
            out.println(Stampa.stampacommento((Comment) it.next())); 
           }
            out.println(Stampa.div(1));
            //stampo il form per inserire i commenti
            out.println("<form class=\"form-horizontal well span6 offset2\" name=\"input\" action=\"Forum\" method=\"get\">");
            out.println("<div class=\"insert_comment\">");
            out.println(Stampa.label("Messaggio","messaggio"));
            out.println("<textarea name=\"messaggio\" id=\"messaggio\" cols=\"50\" rows=\"5\"></textarea>");
            out.println("<input id=\"Accedi\" type=\"hidden\" name=\"Accedi\" value=\""+titolo_gruppo+"\">");
            out.println("<input id=\"utente\" type=\"hidden\" name=\"utente\" value=\""+username+"\">");
            out.println("<input id=\"action\" type=\"hidden\" name=\"action\" value=\"1\">");
            out.println("</br>");
            out.println(Stampa.button("","Commenta!"));
            out.println("</form>");
            
            
  
    
            
            out.println(Stampa.div(2));
            out.println(Stampa.footer());
        } catch (SQLException ex) {
             Logger.getLogger(Forum.class.getName()).log(Level.SEVERE, null, ex);
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
