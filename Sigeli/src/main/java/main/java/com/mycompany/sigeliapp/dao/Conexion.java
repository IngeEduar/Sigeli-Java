package main.java.com.mycompany.sigeliapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    
    public Connection connection=null;
    
    public Connection getConexion(){
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection(Constantes.URL+Constantes.DATABASE, Constantes.USER, Constantes.PASSWORD);
            
           /* if(connection!=null){
                System.out.println("\nConexion establecida");
            }*/
            
        } catch(SQLException e) {
            System.out.println("Error al conectar con la base de datos "+e.getMessage());
        } catch(ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }
}
