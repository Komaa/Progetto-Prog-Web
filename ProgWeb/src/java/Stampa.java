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

    public static String header() {
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
        return header;
    }

    public static String aref(String ref, String testo) {

        String href = "<a href=\"";
        href += ref;
        href += "\"> " + testo + "</a>";

        return href;
    }

    public static String sezione() {
        String sezione = aref("Inviti", "Inviti");
        sezione += "</br>";
        sezione += aref("Creazione_gruppi", "Creazione gruppi");
        sezione += "</br>";
        sezione += aref("Gruppi", "Gruppi");
        sezione += "</br>";
        sezione += aref("Logout", "<button type=" + "button" + " class=\"btn btn-sm btn-danger\">Logout</button>");

        return sezione;
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
}
