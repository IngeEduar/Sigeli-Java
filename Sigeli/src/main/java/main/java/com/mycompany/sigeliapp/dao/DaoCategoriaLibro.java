package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.mycompany.sigeliapp.modelos.Categoria;
import main.java.com.mycompany.sigeliapp.modelos.CategoriaLibro;


public class DaoCategoriaLibro extends Conexion implements IDaoCategoriaLibro{

    @Override
    public ArrayList<CategoriaLibro> verCategoriaLibros() {
    
        ArrayList<CategoriaLibro> arrayLisrCategoriaLibro = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_CATLIBRO;

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                CategoriaLibro categoriaLibro = new CategoriaLibro();
                categoriaLibro.setIdCategoria(resultSet.getInt(Constantes.TCL_IDCATEGORIA));
                categoriaLibro.setIsbnLibro(resultSet.getString(Constantes.TCL_ISBN));
                
                arrayLisrCategoriaLibro.add(categoriaLibro);
            }
            
            return arrayLisrCategoriaLibro;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayLisrCategoriaLibro;
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
