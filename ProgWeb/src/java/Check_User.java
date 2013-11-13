import java.sql.*;

public class Check_User{
   public static boolean checkUser(String username,String password){
       Boolean st = false;
       try {
          Class.forName("com.mysql.jdbc.Driver");  //carica i driver per connettersi al database
          Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3036/test","root","root"); //creo connessione al database
          
          PreparedStatement ps = con.prepareStatement("select * from utenti where username=? and password=?");
          
          ps.setString(1, username);
          ps.setString(2, password);
          
          ResultSet rs = ps.executeQuery();
          st = rs.next(); //inserisce il risultato del next in st (se c'è il next è vero e quindi c'è un record, altrimenti falso perchè non esiste il next)
                    
       } catch (Exception e) {
          e.printStackTrace();
       } 
       return st;
   }
    
}