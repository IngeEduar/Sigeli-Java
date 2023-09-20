package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.mycompany.sigeliapp.modelos.Libro;
import main.java.com.mycompany.sigeliapp.modelos.Multa;

public class DaoMulta extends Conexion implements IDaoMulta{

    @Override
    public ArrayList<Multa> verMultas() {
         ArrayList<Multa> arrayListMulta = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_MULTA;

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                Multa multa = new Multa();
                multa.setIdMulta(resultSet.getInt(Constantes.TM_ID));
                multa.setDocumentoPersona(resultSet.getInt(Constantes.TM_DOCUMENTO));
                multa.setIdPrestamo(resultSet.getInt(Constantes.TM_IDPRESTAMO));
                multa.setValorMulta(resultSet.getInt(Constantes.TM_VALOR));
                multa.setEstadoMulta(resultSet.getInt(Constantes.TM_IDESTADO));
                multa.setFecha(resultSet.getDate(Constantes.TM_FECHA));
                
                arrayListMulta.add(multa);
            }
            
            return arrayListMulta;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayListMulta;
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
    public boolean hacerMultas(Multa multa) {
        String sql = "INSERT INTO "+ Constantes.T_MULTA+ "(" + Constantes.TM_ID + "," + 
                Constantes.TM_DOCUMENTO + "," + Constantes.TM_IDPRESTAMO + "," +
                Constantes.TM_VALOR + "," + Constantes.TM_IDESTADO + "," + Constantes.TM_FECHA + ") " + 
                "VALUES (?,?,?,?,?,?)";
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setInt(1, multa.getIdMulta());
            ps.setInt(2, multa.getDocumentoPersona());
            ps.setInt(3, multa.getIdPrestamo());
            ps.setInt(4, multa.getValorMulta());
            ps.setInt(5, multa.getEstadoMulta());
            ps.setDate(6, multa.getFecha());
            
            ps.executeUpdate();
            
            System.out.println("Multa ingresada con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al Añadir la multa "+e.getMessage());
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
    public boolean pagarMulta(int id, Multa multa) {
        String sql = "UPDATE " + Constantes.T_MULTA + " SET " + Constantes.TM_IDESTADO + "= ?, " + Constantes.TM_FECHA + "= ?" + 
                    " WHERE " + Constantes.T_MULTA + "." + Constantes.TM_ID + " = " + id ;
        
        try {
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setInt(1, multa.getEstadoMulta());
            ps.setDate(2, multa.getFecha());
            
            ps.executeUpdate();
            
            System.out.println("El pago ha sido subido con éxito...");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al hacer el pago "+e.getMessage());
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
    public boolean eliminarMulta(int id) {
        String sql = "DELETE FROM " + Constantes.T_MULTA + " WHERE " + 
                    Constantes.T_MULTA + "." + Constantes.TM_ID + "=" + id;
        
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.executeUpdate();
            
            System.out.println("Multa eliminada con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar la multa "+e.getMessage());
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
