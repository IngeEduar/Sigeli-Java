package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.mycompany.sigeliapp.modelos.Persona;


public class DaoPersona extends Conexion implements IDaoPersona{

    @Override
    public ArrayList<Persona> verPersonas() {
        ArrayList<Persona> arrayListPersona = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_PERSONA;

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                Persona persona = new Persona();
                persona.setDocumentoPersona(Integer.parseInt(resultSet.getString(Constantes.TP_DOCUMENTO)));
                persona.setNombrePersona(resultSet.getString(Constantes.TP_NOMBRE));
                persona.setIdCarrera(resultSet.getInt(Constantes.TP_IDCARRERA));
                persona.setEmailPersona(resultSet.getString(Constantes.TP_EMAIL));
                persona.setTelefono(resultSet.getString(Constantes.TP_TELEFONO));
                
                arrayListPersona.add(persona);
            }
            
            return arrayListPersona;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayListPersona;
        }
        finally{
            try {
                getConexion().close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexion "+e);
            }
        }
    }

    @Override
    public boolean editarPersona(Persona persona) {
        String sql = "UPDATE " + Constantes.T_PERSONA+ " SET " + Constantes.TP_NOMBRE + "= ?, " + Constantes.TP_EMAIL + "= ?" +
                    " WHERE " + Constantes.T_PERSONA + "." + Constantes.TP_DOCUMENTO + "=\"" + persona.getDocumentoPersona() +"\"";
        
        try {
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setString(1, persona.getNombrePersona());
            ps.setString(2, persona.getEmailPersona());
            
            ps.executeUpdate();
            
            System.out.println("El usuario ha sido actualizado con éxito...");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario "+e.getMessage());
            return false;
        }finally{
            try {
                getConexion().close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexion "+e);
            }
        }
    }

    @Override
    public boolean addPersona(Persona persona) {
        String sql = "INSERT INTO "+ Constantes.T_PERSONA + "(" + Constantes.TP_DOCUMENTO+ "," + 
                Constantes.TP_NOMBRE + "," + Constantes.TP_IDCARRERA + "," +
                Constantes.TP_EMAIL + "," + Constantes.TP_TELEFONO + ") " + 
                "VALUES (?,?,?,?,?)";
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setString(1, String.valueOf(persona.getDocumentoPersona()));
            ps.setString(2, persona.getNombrePersona());
            ps.setInt(3, persona.getIdCarrera());
            ps.setString(4, persona.getEmailPersona());
            ps.setString(5, persona.getTelefono());
            
            ps.executeUpdate();
            
            System.out.println("Persona agregada con éxito ");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al agregar la persona "+e.getMessage());
            return false;
        }finally{
            try {
                getConexion().close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexion "+e);
            }
        }
    }

    @Override
    public boolean eliminarPersona(int documento) {
        String sql = "DELETE FROM " + Constantes.T_PERSONA + " WHERE " + 
                    Constantes.T_PERSONA + "." + Constantes.TPR_DOCUMENTO + "=" + "\"" + documento +"\"";
        
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.executeUpdate();
            
            System.out.println("Persona Eliminada con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar la persona "+e.getMessage());
            return false;
        }finally{
            try {
                getConexion().close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexion "+e);
            }
        }
    }

    
    
}
