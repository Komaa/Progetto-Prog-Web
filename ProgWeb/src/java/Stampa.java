
import Models.Comment;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HaoIlMito
 */
public class Stampa {

    static Database dbmanager = new Database();

    public static String header(String pagina) {
        String header = "<!DOCTYPE html>";
        header += "<html>";
        header += "<head>";
        header += "<title>Servlet Welcome_page</title>";
        header += "<meta charset=" + "utf-8" + "><title>Welcome!</title>";
        header += "<link href=";
        header += "./bootstrap/css/bootstrap.css";
        header += " rel=\"stylesheet\">";
        header += "<link href=";
        header += "./bootstrap/css/bootstrap.min.css";
        header += " rel=\"stylesheet\">";
        header += "</head>";
        header += " <body>";
        header += "<div class=" + "container theme-showcase" + ">";
        header += "<div class=" + "page-header" + ">";
        header += "<h1>" + pagina + "</h1></div>";
        header += aref("welcome_page", button("welcome_page", "Torna in home!")) + "</br></br>";
        return header;
    }

    public static String aref(String ref, String testo) {

        String href = "<a href=\"";
        href += ref;
        href += "\"> " + testo + "</a>";

        return href;
    }
    
      public static String arefnew_ex_page(String ref, String testo) {
        
        String href = "<a href=\"http://";
        href += ref;
        href += "\"  target=\"_blank\"> " + testo + "</a>";

        return href;
    }
      
      public static String arefnew__page(String ref, String testo) {
        
        String href = "<a href=\""+ ref;
        href += "\"  target=\"_blank\">" + testo + "</a>";

        return href;
    }

    public static String sezione() {
        String sezione = "";
        sezione += aref("Creazione_gruppo", "Creazione gruppo");
        sezione += "</br>";
        sezione += aref("Gruppi", "Gruppi");
        sezione += "</br>";
        sezione += aref("Logout", "<button type=" + "button" + " class=\"btn btn-sm btn-danger\">Logout</button>");

        return sezione;
    }

    public static String input(String type, String name) {
        String input = "<input type=\"" + type + "\" name=\"" + name + "\">";
        return input;
    }
    

    public static String div(int num) {
        String div = "</div>";
        for (int i = 0; i < num; i++) {
            div += "</div>";
        }
        return div;
    }

    public static String footer() {
        String footer = "<script src=" + "./bootstrap/js/bootstrap.min.js" + "></script>";
        footer += "</body>";
        footer += "</html>";

        return footer;
    }

    public static String button(String value, String azione) {
        //azione semplicemente è il valore che c'è dentro il bottone: invita, crea, ecc
        String button = "<button class=\"btn btn-success\" type=\"submit\" name=\""+azione+"\" value=\""+value+"\">" + azione + "</button>";

        return button;
    }

    public static String label(String label, String div) {
        String llabel = "<label class=\"control-label\" for=\"" + div + "\">" + label + "</label>";

        return llabel;
    }

    public static String alert(String opzione, String messaggio) {
        //le opzioni sono: success, info, danger, warning
        //il messaggio invece è semplicemente il messaggio da scrivere!
        String allerta = "<div class=\"alert alert-" + opzione + "\"><strong>" + messaggio + "</strong></div>";
        return allerta;
    }

    //Primo arrayList passi le stringhe di colonne, Secondo arrayList passi arrayList di righe
    public static String table(String titolo_gruppo, String amministratore, ArrayList<String> colums, ArrayList<ArrayList<String>> rows) {
        ArrayList<String> riga;
        String table = "<table class=\"table table-striped\">", app, value = null;
        table += "<tr>";
        Iterator iter = colums.iterator();
        while (iter.hasNext()) {
            table += "<th>" + iter.next() + "</th>";
        }
        table += "</tr>";
        Iterator i = rows.iterator();
        while (i.hasNext()) {
            table += "<tr>";
            riga = (ArrayList<String>) i.next();
            Iterator iterator = riga.iterator();
            while (iterator.hasNext()) {
                app = (String) iterator.next();
                if (app.substring(0, 1).equals("&")) {
                    table += "<td><form action=\"" + (String) iterator.next() + "\">";
                    table += button(value, "invito");
                    table += "<input type=\"hidden\" name=\"titolo_gruppo\" value=\"" + titolo_gruppo + "\">";
                    table += "<input type=\"hidden\" name=\"amministratore\" value=\"" + amministratore + "\">";
                    table += "<input id=\"action\" type=\"hidden\" name=\"action\" value=\"2\">";
                    table += "</form></td>";
                } else {
                    table += "<td>" + app + "</td>";
                    value = app;
                }
            }
            table += "</tr>";
        }
        table += "</table>";
        return table;
    }

    public static String table_inviti(String username, ArrayList<String> colums, ArrayList<ArrayList<String>> rows) {
        ArrayList<String> riga;
        String table = "<table class=\"table table-striped\">", app, value = null;
        table += "<tr>";
        Iterator iter = colums.iterator();
        while (iter.hasNext()) {
            table += "<th>" + iter.next() + "</th>";
        }
        table += "</tr>";
        Iterator i = rows.iterator();
        while (i.hasNext()) {
            table += "<tr>";
            riga = (ArrayList<String>) i.next();
            Iterator iterator = riga.iterator();
            while (iterator.hasNext()) {
                app = (String) iterator.next();
                if (app.substring(0, 1).equals("&")) {
                    table += "<td><form action=\"" + (String) iterator.next() + "\">";
                    table += button("Accetta", "Accetta");
                    table += "  <button class=\"btn btn-danger\" type=\"submit\" name=\"Rifiuta\" value=\"Rifiuta\">Rifiuta</button>";
                    table += "<input type=\"hidden\" name=\"titolo_gruppo\" value=\"" + value + "\">";
                    table += "</form></td>";

                } else {
                    table += "<td>" + app + "</td>";
                    value = app;
                }
            }
            table += "</tr>";
        }
        table += "</table>";
        return table;
    }

    public static String table_gruppi(String nome, ArrayList<String> colums, ArrayList<ArrayList<String>> rows) throws SQLException {
        ArrayList<String> riga;
        String table = "<table class=\"table table-striped\">", app, gruppo = null;
        table += "<tr>";
        Iterator iter = colums.iterator();
        while (iter.hasNext()) {
            table += "<th>" + iter.next() + "</th>";
        }
        table += "</tr>";
        Iterator i = rows.iterator();

        while (i.hasNext()) {
           
            table += "<tr>";
            riga = (ArrayList<String>) i.next();
            Iterator iterator = riga.iterator();
            while (iterator.hasNext()) {
                
                app = (String) iterator.next();

               
                if (app.substring(0, 1).equals("&")) {
                    table += "<td><form action=\"" + (String) iterator.next() + "\">";
                    table += button(gruppo, "Accedi");
                    table += "</form></td>";
                } else if (dbmanager.controllo_amministratore(gruppo, nome) && app.substring(0, 1).equals("%")) {
                    table += "<td><form action=\"" + (String) iterator.next() +"\">";
                    table += "<input id=\""+app.substring(1)+"\" type=\"hidden\" name=\""+app.substring(1)+"\" value=\"" + gruppo + "\">";
                    table += "<input id=\"action\" type=\"hidden\" name=\"action\" value=\"4\">";
                    table += "<input type=\"hidden\" name=\"amministratore\" value=\""+nome+"\">";    
                    table += Stampa.button(gruppo, "Gestisci") + "</form></td>";
                } else if (!dbmanager.controllo_amministratore(gruppo, nome) && app.substring(0, 1).equals("%")) {
                    iterator.next();
                    table += "<td>"+Stampa.alert("info","Non sei l'amministratore")+"</td>";
                } else {
                    table += "<td>" + app + "</td>";
                    gruppo = app;
                }
            }
            table += "</tr>";
        }
        table += "</table>";
        return table;
    }

    static String stampacommento(Comment comment,String dirName,String relativName) throws SQLException {
        String name=dbmanager.take_name_utente(comment.getId_utente());
        String commento="Testo: ";
        String relName= relativName;
          String[] split=comment.getText().split("\\$\\$");
     
                    for(int i=0;i<split.length;i++)
                    {
                        
                        if((i%2)==1){
                         
                 dirName+= "/" + split[i];
                 relativName+= "/" + split[i];
                           
                    File theDir = new File(dirName); 
                    if (theDir.exists()) {  
                        commento+=Stampa.arefnew__page(relativName, split[i]);
                    }else{
                        commento+=Stampa.arefnew_ex_page(split[i], split[i]);
                    }
                        }else{
                        commento+=split[i];
                        }
                    }
       commento+="<br>Autore: " + name + "<br>Data: " + comment.getData()+ "<br>";
       String allegato=comment.getAllegato();
       if(!allegato.equals("noallegato")){
        commento+="Allegato: ";  
        relName+= "/" + allegato;
        commento+=Stampa.arefnew__page(relName, allegato)+"<br><br>";
       }else{
        commento+="Allegato: Nessun allegato aggiunto<br><br>";     
       }
       
       return commento;
    }
}
