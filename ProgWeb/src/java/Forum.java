/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.Comment;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
            
            String action=null;
            String messaggio=null;
            String utente=null;
            String titolo_gruppo=null;
            
            if(!ServletFileUpload.isMultipartContent(request)){
            action=request.getParameter("action");
            utente=request.getParameter("utente");
            titolo_gruppo =request.getParameter("Accedi");
            }else{
                        
            //---------------------Upload eventuale file
            String realPath = getServletContext().getRealPath("/");
            String dirName= realPath+"tmp";
          
            
           MultipartRequest multi = new MultipartRequest(request, dirName, 10*1024*1024, "ISO-8859-1", new DefaultFileRenamePolicy());
        
            action = multi.getParameter("action");
            messaggio = multi.getParameter("messaggio");
            utente = multi.getParameter("utente");
            titolo_gruppo = multi.getParameter("Accedi");
            
          
//             System.out.println("FILES:");
             Enumeration files = multi.getFileNames();
                while (files.hasMoreElements()) {
                String name = (String)files.nextElement();
                String filename = multi.getFilesystemName(name);
                String originalFilename = multi.getOriginalFileName(name);
                String type = multi.getContentType(name);  
                File f = multi.getFile(name);
//                System.out.println("name: " + name);
//                System.out.println("filename: " + filename);
//                System.out.println("originalFilename: " + originalFilename);
//                System.out.println("type: " + type);
                if (f != null) {
//                System.out.println("f.toString(): " + f.toString());
//                System.out.println("f.getName(): " + f.getName());
//                System.out.println("f.exists(): " + f.exists());
//                System.out.println("f.length(): " + f.length());
                }
                }
            //----------FINE UPLOAD-----
            }
            
            if(action!=null){
            if(action.equals("1")){
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
            out.println("<form class=\"form-horizontal well span6 offset2\" name=\"input\" action=\"Forum\" ENCTYPE=\"multipart/form-data\" method=\"post\">");
            out.println("<div class=\"insert_comment\">");
            out.println(Stampa.label("Messaggio","messaggio"));
            out.println("<textarea name=\"messaggio\" id=\"messaggio\" cols=\"50\" rows=\"5\"></textarea>");
            out.println("<input type=\"file\" name=file>");
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
