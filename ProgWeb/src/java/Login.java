/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaoIlMito
 */
@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {

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
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String val_cookie;

        if (Check_User.checkUser(username, password)) {
            HttpSession session = request.getSession(true);//creo la sessione
            session.setAttribute("username", username);
            Cookie[] cks = request.getCookies();
            int i;
            try {
                for (i = 0; true; i++) {
                    if (cks[i].getName().equals(username)) {
                        break;
                    }

                }
                Timestamp timestamp = new Timestamp(Long.parseLong(cks[i].getValue()));
                Date date = new Date(timestamp.getTime());

                // S is the millisecond
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                val_cookie = ("Welcome " + username + " last login: " + simpleDateFormat.format(date));
            } catch (Exception e) {
                val_cookie = ("Welcome " + username + " for the first time!");
            }

            Date date = new Date();
            Cookie ck = new Cookie(username, "" + date.getTime());//creo il cookie
            response.addCookie(ck);                     //lo metto nella risposta per il server

            session.setAttribute("cookie", val_cookie);
            response.sendRedirect("welcome_page");      // reindirizzo l'utente alla pagina principale
        } else {
            out.print("Username or Password not correct");
            RequestDispatcher rs = request.getRequestDispatcher("index.html");

//response.sendRedirect("pippo.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
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
