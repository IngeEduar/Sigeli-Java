package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.mycompany.sigeliapp.modelos.Prestamos;


public class DaoPrestamos extends Conexion implements IDaoPrestamos{

    @Override
    public ArrayList<Prestamos> verPrestamos() {
        ArrayList<Prestamos> arrayListPrestamo = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_PRESTAMO;

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                Prestamos prestamo = new Prestamos();
                prestamo.setIdPrestamo(resultSet.getInt(Constantes.TPR_ID));
                prestamo.setIdPersona(Integer.valueOf(resultSet.getString(Constantes.TPR_DOCUMENTO)));
                prestamo.setIsbnLibro(resultSet.getString(Constantes.TPR_ISBN));
                prestamo.setFechaPrestamo(resultSet.getDate(Constantes.TPR_FECHAPRESTAMO));
                prestamo.setFechaEntrega(resultSet.getDate(Constantes.TPR_FECHAENTREGA));
                prestamo.setIdEstado(resultSet.getInt(Constantes.TPR_IDESTADO));
                
                arrayListPrestamo.add(prestamo);
            }
            
            return arrayListPrestamo;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayListPrestamo;
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
    public boolean addPrestamo(Prestamos prestamo) {
        String sql = "INSERT INTO "+ Constantes.T_PRESTAMO+ "(" + Constantes.TPR_ID + "," + 
                Constantes.TPR_DOCUMENTO + "," + Constantes.TPR_ISBN + "," +
                Constantes.TPR_FECHAPRESTAMO + "," + Constantes.TPR_FECHAENTREGA + "," + Constantes.TPR_IDESTADO + ") " + 
                "VALUES (?,?,?,?,?,?)";
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setInt(1, prestamo.getIdPrestamo());
            ps.setString(2, String.valueOf(prestamo.getIdPersona()));
            ps.setString(3, prestamo.getIsbnLibro());
            ps.setDate(4, prestamo.getFechaPrestamo());
            ps.setDate(5, prestamo.getFechaEntrega());
            ps.setInt(6, prestamo.getIdEstado());
            
            ps.executeUpdate();
            
            System.out.println("Prestamo hecho con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al Añadir el prestamo "+e.getMessage());
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
    public boolean validarPrestamo(Prestamos prestamo) {
        String sql = "UPDATE " + Constantes.T_PRESTAMO + " SET " + Constantes.TPR_IDESTADO + "= ?" +
                    " WHERE " + Constantes.T_PRESTAMO + "." + Constantes.TPR_ID + "=" + prestamo.getIdPrestamo();
        
        try {
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setInt(1, prestamo.getIdEstado());
            
            ps.executeUpdate();
            
            System.out.println("El prestamo se ha validado con éxito...");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al validar el prestamo "+e.getMessage());
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
    public boolean cambioEstado(Prestamos prestamo) {
        String sql = "UPDATE " + Constantes.T_PRESTAMO + " SET " + Constantes.TPR_IDESTADO + "= ?" +
                    " WHERE " + Constantes.T_PRESTAMO + "." + Constantes.TPR_ID + "=" + prestamo.getIdPrestamo();
        
        try {
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setInt(1, prestamo.getIdEstado());
            
            ps.executeUpdate();
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al cambiar el estado "+e.getMessage());
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
