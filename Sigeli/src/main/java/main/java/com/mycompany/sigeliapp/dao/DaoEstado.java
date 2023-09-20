package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.mycompany.sigeliapp.modelos.Estado;


public class DaoEstado extends Conexion implements IDaoEstado{

    @Override
    public ArrayList<Estado> verEstados() {
        ArrayList<Estado> arrayLisrEstado = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_ESTADO;

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                Estado estado = new Estado();
                estado.setIdEstado(resultSet.getInt(Constantes.TE_ID));
                estado.setEstado(resultSet.getString(Constantes.TE_ESTADO));
                
                arrayLisrEstado.add(estado);
            }
            
            return arrayLisrEstado;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayLisrEstado;
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
