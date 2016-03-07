package javaapplication7;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class sqlitePracticeClass {

    public static void main(String[] args) {
        try {
            Connection c;
            Statement stm;
            //register drivers
            Class.forName("org.sqlite.JDBC");
            //open connection
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            //create statement
            stm = c.createStatement();
            //excecute query
            try (ResultSet results = stm.executeQuery("select * from testTable")) {
                System.out.println("ID|NAME");
                System.out.println("_____________");
                //get data
                while (results.next()) {
                    int id = results.getInt("ID");
                    String name = results.getString("NAME");
                    System.out.println(id + "|" + name);
                }
            }
            //CLOSE CONNECTIONS           
            stm.close();
            c.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(JavaApplication7.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
