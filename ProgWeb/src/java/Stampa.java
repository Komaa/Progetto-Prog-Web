
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
        header += "</head>";
        header += " <body>";
        header += "    <header class=\"navbar navbar-inverse navbar-fixed-top bs-docs-nav\" role=\"banner\">";
        header += " <div class=\"container\">";
        header += "   <div class=\"navbar-header\">";
        header += "    <button class=\"navbar-toggle\" type=\"button\" data-toggle=\"collapse\" data-target=\".bs-navbar-collapse\">";
        header += "     <span class=\"sr-only\">Toggle navigation</span>";
        header += "     <span class=\"icon-bar\"></span>";
        header += "     <span class=\"icon-bar\"></span>";
        header += "      <span class=\"icon-bar\"></span>";
        header += "    </button>";
        header += "   <a href=\"welcome_page\" class=\"navbar-brand\">Progetto Web</a>";
        header += "   </div>";
        header += " <nav class=\"collapse navbar-collapse bs-navbar-collapse\" role=\"navigation\">";
        header += "   <ul class=\"nav navbar-nav\">";
        header += "      <li>";
        header += aref("welcome_page", "Home");
        header += "      </li>";
        header += "     <li>";
        header += aref("Creazione_gruppo", "Creazione gruppo");
        header += "     </li>";
        header += "     <li>";
        header += aref("Gruppi", "Gruppi");
        header += "      </li>";
        header += "   </ul>";
        header += "    <ul class=\"nav navbar-nav navbar-right\">";
        header += "      <li>";
        header += aref("Logout", "<button type=\" + \"button\" + \" class=\"btn btn-sm btn-danger\">Logout</button>");
        header += "      </li>";
        header += "    </ul>";
        header += " </nav>";
        header += "</div>";
        header += "</header></br>";
        header += "<div class=\"section-background\">";
        header += "<div class=\"section-title\">";
        header += "<h1>" + pagina + "</h1>";

        return header;
    }

    public static String section_content(String testo) {
        String content = "";
        content += "<div class=\"section-content clearfix\">" + testo + "</div>";
        return content;
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

        String href = "<a href=\"" + ref;
        href += "\"  target=\"_blank\">" + testo + "</a>";

        return href;
    }

    public static String sezione() {
        String sezione = "";
        sezione
                += sezione += "</br>";
        sezione
                += sezione += "</br>";
        sezione += aref("Logout", "<button type=" + "button" + " class=\"btn btn-sm btn-danger\">Logout</button>");

        return sezione;
    }

    public static String input(String type, String name) {
        String input = "<input type=\"" + type + "\" name=\"" + name + "\">";
        return input;
    }

    public static String div(int num) {
        String div = "</div>";
        if (num == 1) {
            return div;
        } else {
            for (int i = 0; i < num; i++) {
                div += "</div>";
            }
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
        String button = "<button class=\"btn btn-success\" type=\"submit\" name=\"" + azione + "\" value=\"" + value + "\">" + azione + "</button>";

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
                    table += "<td><form method=\"post\" action=\"" + (String) iterator.next() + "\">";
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
                    table += "<td><form method=\"post\" action=\"" + (String) iterator.next() + "\">";
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
                    table += "<td><form action=\"" + (String) iterator.next() + "\" method=\"post\">";
                    table += button(gruppo, "Accedi");
                    table += "</form></td>";
                } else if (dbmanager.controllo_amministratore(gruppo, nome) && app.substring(0, 1).equals("%")) {
                    table += "<td><form action=\"" + (String) iterator.next() + "\" method=\"post\">";
                    table += "<input id=\"" + app.substring(1) + "\" type=\"hidden\" name=\"" + app.substring(1) + "\" value=\"" + gruppo + "\">";
                    table += "<input id=\"action\" type=\"hidden\" name=\"action\" value=\"4\">";
                    table += "<input type=\"hidden\" name=\"amministratore\" value=\"" + nome + "\">";
                    table += Stampa.button(gruppo, "Gestisci") + "</form></td>";
                } else if (!dbmanager.controllo_amministratore(gruppo, nome) && app.substring(0, 1).equals("%")) {
                    iterator.next();
                    table += "<td>" + Stampa.alert("info", "Non sei l'amministratore") + "</td>";
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

    static String stampacommento(Comment comment, String dirName, String relativName) throws SQLException {
        String name = dbmanager.take_name_utente(comment.getId_utente());
        String commento = "";
        
        commento += "<div class=\"panel panel-info\">";
        commento += "<div class=\"panel-heading\">";
        commento += "<h3 class=\"panel-title\">Autore: " + name + " scritto in data: " + comment.getData() + "</div>";
        commento += "<div class=\"panel-body\" style=\"min-height:100px\">";
        
        commento += "<h4>";
        
        String relName = relativName;
        String[] split = comment.getText().split("\\$\\$");

        for (int i = 0; i < split.length; i++) {

            if ((i % 2) == 1) {

                dirName += "/" + split[i];
                relativName += "/" + split[i];

                File theDir = new File(dirName);
                if (theDir.exists()) {
                    commento += Stampa.arefnew__page(relativName, split[i]);
                } else {
                    commento += Stampa.arefnew_ex_page(split[i], split[i]);
                }
            } else {
                commento += split[i];
            }
        }
        
        commento += "</h4></div>";
        commento += "<div class=\"panel-footer\">";
        String allegato = comment.getAllegato();
        if (!allegato.equals("noallegato")) {
            commento += "Allegato: ";
            relName += "/" + allegato;
            commento += Stampa.arefnew__page(relName, allegato) + "<br><br>";
        } else {
            commento += "Allegato: Nessun allegato aggiunto<br><br>";
        }
        commento += "</div></div>";
        return commento;
    }

    public static String index() {
        String index = "";
        index += "<!DOCTYPE html>";
        index += "<html>";
        index += "<head>";
        index += "<meta charset=" + "utf-8" + "><title>Welcome!</title>";
        index += "<link href=";
        index += "./bootstrap/css/bootstrap.css";
        index += " rel=\"stylesheet\">";
        index += "<link href=";
        index += "./bootstrap/css/bootstrap.min.css";
        index += " rel=\"stylesheet\">";
        index += "</head>";
        index += "<body class=\"body_login\">";
        index += "<div class=\"container\">";
        index += "<div class=\"row\">";
        index += "<div class=\"col-md-4 col-md-offset-7\">";
        index += "<div class=\"panel panel-default\">";
        index += "<div class=\"panel-heading\">";
        index += "<span class=\"glyphicon glyphicon-lock\"></span> Login</div>";
        index += "<div class=\"panel-body\">";
        index += "<form class=\"form-horizontal\" method=\"post\" action=\"login\" role=\"form\">";
        index += "<div class=\"form-group\">";
        index += "<label for=\"username\" class=\"col-sm-3 control-label\">";
        index += "Username</label>";
        index += "<div class=\"col-sm-9\">";
        index += "<input type=\"username\" name=\"username\" class=\"form-control\" id=\"username\" placeholder=\"Username\" autofocus required>";
        index += "</div>";
        index += "</div>";
        index += "<div class=\"form-group\">";
        index += "<label for=\"Password\" class=\"col-sm-3 control-label\">";
        index += " Password</label>";
        index += "  <div class=\"col-sm-9\">";
        index += "<input type=\"password\" class=\"form-control\" id=\"Password\" name=\"password\"  placeholder=\"Password\" required>";
        index += "</div>";
        index += "</div>";
        index += "<div class=\"form-group\">";
        index += "</div>";
        index += "<div class=\"form-group last\">";
        index += "  <div class=\"col-sm-offset-3 col-sm-9\">";
        index += "    <button type=\"submit\" class=\"btn btn-success btn-sm\">";
        index += "Sign in</button>";
        index += " <button type=\"reset\" class=\"btn btn-default btn-sm\">";
        index += "     Reset</button>";
        index += " </div>";
        index += "</div>";
        index += " </form>";
        index += "</div>";

        index += "</div>";
        index += "</div>";
        index += "</div>";

        return index;
    }
}
