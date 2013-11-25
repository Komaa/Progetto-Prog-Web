import com.itextpdf.text.Chunk;
import java.io.IOException;
import java.util.Date;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
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
            System.out.println(titolo_gruppo);
            ArrayList<String> utenti = dbmanager.listaUtenti(username, titolo_gruppo);
            utenti.add(username);
            System.out.println("Utenti: " + utenti.size());

            //creo la lista dei post fatti
            int commenti = dbmanager.contaCommenti(titolo_gruppo);
            System.out.println("commenti: " + commenti);
            //creo la data dell'ultimo post

            Date ultimo_post = dbmanager.ultimoPost(titolo_gruppo);
            SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");

            if (ultimo_post != null) {
                String ultimo_post_string = ft.format(ultimo_post);
                System.out.println(ultimo_post);
            }
            //temporanea
            String ultimo_post_string = null;

            String nome_pdf = titolo_gruppo + ".pdf";
            System.out.println(nome_pdf);
           

            // Get the text that will be added to the PDF
            String text = request.getParameter("Genera");
        try {
        response.setContentType("application/pdf");
       
            // step 1
            Document document = new Document();
            // step 2
            PdfWriter.getInstance(document, response.getOutputStream());
            // step 3
            document.open();
            // step 4
            document.add(new Paragraph("Report del gruppo: "+titolo_gruppo));
            document.add(new Paragraph("Gli utenti registrati sono: " ));
            Iterator i = utenti.iterator();
                while (i.hasNext()) {
                   String nome = (String) i.next();
                   document.add(new Chunk(nome+" "));
                } 
            document.add(new Paragraph("Il numero di post nel gruppo è: " + commenti));
            document.add(new Paragraph("La data dell'ultimo post è: " + ultimo_post_string));
            // step 5
            document.close();
        } catch (DocumentException de) {
            throw new IOException(de.getMessage());
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
