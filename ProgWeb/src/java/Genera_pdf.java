/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaoIlMito
 */
public class Genera_pdf extends HttpServlet {

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
            
            String titolo_gruppo = request.getParameter("titolo_gruppo");
            
		// step 1: creation of a document-object
		Document document = new Document();
		try {
		// step 2:
		// we create a writer that listens to the document
		PdfWriter.getInstance(document, new FileOutputStream("Paragraphs.pdf"));

		// step 3: we open the document
		document.open();
		// step 4:
                Paragraph p1 = new Paragraph(new Chunk( "This is my first paragraph. ",
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
                p1.add("The leading of this paragraph is calculated automagically. ");
                p1.add("The default leading is 1.5 times the fontsize. ");
                p1.add(new Chunk("You can add chunks "));
                p1.add(new Phrase("or you can add phrases. "));
                p1.add(new Phrase(
                    "Unless you change the leading with the method setLeading, the leading doesn't change if you add text with another leading. This can lead to some problems.",
                    FontFactory.getFont(FontFactory.HELVETICA, 18)));
                document.add(p1);
                Paragraph p2 = new Paragraph(new Phrase("This is my second paragraph. ", FontFactory.getFont(
                                FontFactory.HELVETICA, 12)));
                p2.add("As you can see, it started on a new line.");
                document.add(p2);
                Paragraph p3 = new Paragraph("This is my third paragraph.",
                            FontFactory.getFont(FontFactory.HELVETICA, 12));
               document.add(p3);
		} catch (    DocumentException | IOException de) {
			System.err.println(de.getMessage());
		}

		// step 5: we close the document
		document.close();            
            
            
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
