/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoicemanagement;

//Import packages for MySQL Connection
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author pdeshmukh
 */
public class DbConnection {
    
    // Function to connect with MySQL Database
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/invoice","invadmin","invpassword");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return con;
    }
}
