/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.Comment;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.nio.file.Path;
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
                response.sendRedirect("index");
            }

            String action = null;
            String messaggio = null;
            String utente = null;
            String titolo_gruppo = null;
            String namepi = null;
            String filename = null;
            String originalFilename = "noallegato";
            String realPath = getServletContext().getRealPath("/");
            boolean stampa = true;

            if (!ServletFileUpload.isMultipartContent(request)) {
                action = request.getParameter("action");
                utente = request.getParameter("utente");
                titolo_gruppo = request.getParameter("Accedi");
                out.println(Stampa.header("Forum del gruppo: " + titolo_gruppo));
                out.println(Stampa.section_content("Questo è il forum del vostro gruppo, condividete!"));
                out.println(Stampa.div(2));
            } else {

                //---------------------Upload eventuale file
                String dirName = realPath + "tmp";

                MultipartRequest multi = new MultipartRequest(request, dirName, 10 * 1024 * 1024, "ISO-8859-1", new DefaultFileRenamePolicy());

                action = multi.getParameter("action");
                messaggio = multi.getParameter("messaggio");
                utente = multi.getParameter("utente");
                titolo_gruppo = multi.getParameter("Accedi");
                if (messaggio.equals("")) {
                    out.println(Stampa.header("Forum del gruppo: " + titolo_gruppo));
                    out.println(Stampa.section_content("Questo è il forum del vostro gruppo, condividete!"));
                    out.println(Stampa.div(2));
                    out.println(Stampa.alert("danger", "E' obbligatorio inserire un commento!"));

                } else {
//             System.out.println("FILES:");
                    Enumeration files = multi.getFileNames();
                    while (files.hasMoreElements()) {
                        namepi = (String) files.nextElement();
                        filename = multi.getFilesystemName(namepi);
                        originalFilename = multi.getOriginalFileName(namepi);
                        String type = multi.getContentType(namepi);
                        File f = multi.getFile(namepi);
//                System.out.println("name: " + namepi);
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
                    if (originalFilename != null) {
                        String source = realPath + "tmp/" + originalFilename;
//                System.out.println("sourEEEEEEEEEEEEEEEEEE:"+ source);
                        String destination = realPath + "groupsfolder/" + titolo_gruppo + "/" + originalFilename;
//                System.out.println("destinationNNNNNNNNNNNNNNNNNNN:"+ destination);
                        File afile = new File(source);
                        File bfile = new File(destination);
                        if (!(bfile.exists())) {
                            InputStream inStream = null;
                            OutputStream outStream = null;

                            try {

                                inStream = new FileInputStream(afile);
                                outStream = new FileOutputStream(bfile);

                                byte[] buffer = new byte[1024];
                                int length;
                                //copy the file content in bytes 
                                while ((length = inStream.read(buffer)) > 0) {
                                    outStream.write(buffer, 0, length);
                                }
                                inStream.close();
                                outStream.close();

                                //delete the original file
                                afile.delete();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            out.println(Stampa.header("Forum del gruppo: " + titolo_gruppo));
                            out.println(Stampa.section_content("Questo è il forum del vostro gruppo, condividete!"));
                            out.println(Stampa.div(2));
                            out.println(Stampa.alert("danger", "Il file che hai caricato è già presente"));
                            stampa = false;
                            afile.delete();
                        }
                    } else {
                        originalFilename = "noallegato";
                    }
                }
                //----------FINE UPLOAD-----
            }

            if (action != null) {
                if (action.equals("1")) {
                    if (stampa == true && (titolo_gruppo != null) && (messaggio != null) && (!messaggio.equals("")) && (dbmanager.checkusertogroup(username, titolo_gruppo)) && (username.equals(utente))) {
                        String cod_gruppo = dbmanager.take_cod_gruppo(titolo_gruppo);
                        String cod_utente = dbmanager.take_cod_utente(username);
                        dbmanager.addcomment(messaggio, cod_gruppo, cod_utente, originalFilename);
                        out.println(Stampa.header("Forum del gruppo: " + titolo_gruppo));
                        out.println(Stampa.section_content("Questo è il forum del vostro gruppo, condividete!"));
                        out.println(Stampa.div(2));
                    } else if (messaggio.equals("")) {
                    } else if (stampa == false) {

                    } else {
                        out.println(Stampa.header("OPSS!!!"));
                        out.println(Stampa.div(2));
                        out.println(Stampa.alert("danger", "Non fare il furbo"));
                        out.println(Stampa.footer());
                    }
                }
            }

            //stampo il form per inserire i commenti
            out.println("<div class=\"row\" style=\"padding-top: 50px\">");
            out.println("<div class=\"container .col-md-6 .col-md-offset-3\">");
            out.println("<div class=\"panel panel-primary\">");
            out.println("<div class=\"panel-heading\">");
            out.println("<h3 class=\"panel-title\">" + username + " scrivi un post!</h3></div>");
            out.println("<div class=\"panel-body\">");
            out.println("<form class=\"form-horizontal\" name=\"input\" action=\"Forum\" ENCTYPE=\"multipart/form-data\" method=\"post\">");
            out.println("<div class=\"control-group\"");
            out.println("<div class=\"insert_comment\">");
            out.println("<h4>Messaggio</h4><br>");
            out.println("<textarea name=\"messaggio\" id=\"messaggio\" cols=\"100\" rows=\"5\"></textarea>");
            out.println(Stampa.div(1));
            out.println("<div class=\"control-group\">");
            out.println("<br><input type=\"file\" name=file></input><hr>");
            out.println("<input id=\"Accedi\" type=\"hidden\" name=\"Accedi\" value=\"" + titolo_gruppo + "\">");
            out.println("<input id=\"utente\" type=\"hidden\" name=\"utente\" value=\"" + username + "\">");
            out.println("<input id=\"action\" type=\"hidden\" name=\"action\" value=\"1\">");
            out.println(Stampa.div(1));
            out.println("</br>");
            out.println("<div class=\"control-group\">");
            out.println(Stampa.button("", "Commenta!"));
            out.println("<button type=\"reset\" class=\"btn btn-danger\">Cancella!</button>");
            out.println(Stampa.div(1));
            out.println("</form>");

            out.println(Stampa.div(4));
    out.println("<div class=\"container .col-md-6 .col-md-offset-3\">");        
       out.println("<div class=\"panel-group\">");
  out.println("<div class=\"panel panel-primary\">");
   out.println("<div class=\"panel-heading\">");
     out.println("<h4>");
       out.println("Qui sotto ci sono tutti i post del gruppo!");
      out.println("</h4>");
   out.println("</div>");
     out.println("<div id=\"forum\">");
      out.println("<div class=\"panel-body\">");
     

            



            out.println("<div class=\"comments\">");
            String cod_gruppo = dbmanager.take_cod_gruppo(titolo_gruppo);
            //stampo tutti i commenti nella pagina
            ArrayList<Comment> listaCommenti = dbmanager.listaCommenti(cod_gruppo);
            Iterator it = listaCommenti.iterator();
            while (it.hasNext()) {
                String dirpath = realPath + "groupsfolder/" + titolo_gruppo;
                String relativName = "groupsfolder/" + titolo_gruppo;
                out.println(Stampa.stampacommento((Comment) it.next(), dirpath, relativName));
            }
            out.println("</div></div>");
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
