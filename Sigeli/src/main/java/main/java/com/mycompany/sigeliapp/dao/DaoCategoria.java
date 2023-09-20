package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.mycompany.sigeliapp.modelos.Categoria;


public class DaoCategoria extends Conexion implements IDaoCategoria{

    @Override
    public ArrayList<Categoria> verCategorias() {
        
        ArrayList<Categoria> arrayLisrCategoria = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_CATEGORIA;

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(resultSet.getInt(Constantes.TCAT_ID));
                categoria.setNombreCategoria(resultSet.getString(Constantes.TCAT_CATEGORIA));
                
                arrayLisrCategoria.add(categoria);
            }
            
            return arrayLisrCategoria;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayLisrCategoria;
        }
        finally{
            try {
                getConexion().close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexion "+e);
            }
        }
    }
    
}
