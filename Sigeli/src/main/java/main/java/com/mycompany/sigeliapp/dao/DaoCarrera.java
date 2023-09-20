package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.com.mycompany.sigeliapp.modelos.Carrera;

public class DaoCarrera extends Conexion implements IDaoCarrera{

    @Override
    public ArrayList<Carrera> verCarreras() {
        
        ArrayList<Carrera> arrayLisrCarrera = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_CARRERA + " WHERE " + Constantes.TC_ID + " !=0";

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                Carrera carrera = new Carrera();
                carrera.setIdCarrera(resultSet.getInt(Constantes.TC_ID));
                carrera.setCarrera(resultSet.getString(Constantes.TC_CARREERA));
                
                arrayLisrCarrera.add(carrera);
            }
            
            return arrayLisrCarrera;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayLisrCarrera;
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
