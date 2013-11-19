
import java.sql.*;

public class Check_User {

    public static boolean checkUser(String username, String password) {
        Boolean st = false;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance(); //carica i driver per connettersi al database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3036/test", "root", "root"); //creo connessione al database

            PreparedStatement ps = con.prepareStatement("select * from utenti where username=? and password=?");

            ps.setString(1, username);
            ps.setString(2, password);

            System.out.print(ps.toString());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                st = true;
            }; //inserisce il risultato del next in st (se c'è il next è vero e quindi c'è un record, altrimenti falso perchè non esiste il next)

        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

}
