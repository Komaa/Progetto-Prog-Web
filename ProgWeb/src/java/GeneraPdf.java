
import com.google.common.io.FileBackedOutputStream;
import com.itextpdf.text.Chunk;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HaoIlMito
 */
public class GeneraPdf extends HttpServlet {

    Database dbmanager = new Database();
    ArrayList<String> utenti = new ArrayList<String>();
    ArrayList<String> colums = new ArrayList<String>(Arrays.asList(new String[]{"Utenti", "Inviti"}));
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
            throws ServletException, IOException, SQLException, DocumentException {
        response.setContentType("text/html;charset=UTF-8");

        //creo la lista degli utenti
        HttpSession session = request.getSession(true);//creo la sessione   //mettere le prossime 5 righe al filtro
        String username = (String) session.getAttribute("username");        //nel doFilter
        if (username == null) {
            response.sendRedirect("index.html");
        }

        String titolo_gruppo = request.getParameter("Genera");

        String cod_gruppo = dbmanager.take_cod_gruppo(titolo_gruppo);
        String cod_utente = dbmanager.take_cod_utente(username);

        System.out.println(titolo_gruppo);
        ArrayList<String> utenti = dbmanager.listaUtenti(username, titolo_gruppo);
        utenti.add(username);
        System.out.println("Utenti: " + utenti.size());

        //creo la lista dei post fatti
        int commenti = dbmanager.contaCommenti(cod_gruppo);
        System.out.println("commenti: " + commenti);
        //creo la data dell'ultimo post

        Date ultimo_post = dbmanager.ultimoPost(cod_gruppo);
        SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");

        String ultimo_post_string = null;
        if (ultimo_post != null) {
            ultimo_post_string = ft.format(ultimo_post);
        } else {
            ultimo_post_string = Stampa.alert("info", "non ci sono post nel gruppo!");
        }

        String nome_pdf = titolo_gruppo + ".pdf";

        // Get the text that will be added to the PDF
        String text = request.getParameter("Genera");
        try {

            //  PrintWriter out = response.getWriter();
            response.setContentType("application/pdf");

            // step 1
            Document document = new Document();
            // step 2 
            PdfWriter.getInstance(document, response.getOutputStream());
            document.addTitle(nome_pdf);
            document.addAuthor(username);
            document.addCreator("Myself");
            // step 3
            document.open();
            // step 4
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD);
            document.add(new Paragraph("Report del gruppo: " + titolo_gruppo,font));
            
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
            document.add(new Paragraph("Gli utenti registrati al forum sono: ",FontFactory.getFont(FontFactory.COURIER, 12)));
                ListItem l = new ListItem();
                l.setListSymbol(new Chunk("\u2022",FontFactory.getFont(FontFactory.HELVETICA, 12 )));
                Iterator i = utenti.iterator();
                while (i.hasNext()) {
                    String nome = (String) i.next();
                    l.add(new Chunk(nome+" ",FontFactory.getFont(FontFactory.COURIER, 12)));
                }
                document.add(l);
                
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
                
             PdfPTable headerTable;
             
                headerTable = new PdfPTable(2);

                headerTable.addCell(borderlessCell("La data dell'ultimo post è: "));
                headerTable.addCell(borderlessCell(""+ultimo_post_string));
                
                headerTable.addCell(borderlessCell("Il numero di post nel gruppo è"));
                headerTable.addCell(borderlessCell(""+commenti));

                document.add(headerTable);
            // step 5
            document.close();
        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        }
    }

    private PdfPCell borderlessCell(String s) {
        PdfPCell cell = new PdfPCell();
        Font f = new Font();
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.addElement(new Paragraph(s, f));
        return cell;
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
        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(GeneraPdf.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GeneraPdf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(GeneraPdf.class.getName()).log(Level.SEVERE, null, ex);
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
