/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Models;

import java.util.Date;

/**
 *
 * @author pietro
 */
public class Comment {
    String text, id_utente, id_gruppo;
    Date data;

    public Comment() {
    }
    
    public Comment(String text, String id_utente, String id_gruppo, Date data){
        this.text=text;
        this.id_utente=id_utente;
        this.id_gruppo=id_gruppo;
        this.data=data;
    }
    
}
