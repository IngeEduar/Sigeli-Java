package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.mycompany.sigeliapp.modelos.Estado;
import main.java.com.mycompany.sigeliapp.modelos.Libro;


public class DaoLibro extends Conexion implements IDaoLibro{

    @Override
    public ArrayList<Libro> verLibros() {
        ArrayList<Libro> arrayListLibro = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_LIBRO + " ORDER BY " + Constantes.T_LIBRO + "." + Constantes.TL_NOMBRE + " ASC";

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                Libro libro = new Libro();
                libro.setIdLibro(resultSet.getInt(Constantes.TL_IDLIBRO)-1);
                libro.setIsbnLibro(resultSet.getString(Constantes.TL_ISBN));
                libro.setNombreLibro(resultSet.getString(Constantes.TL_NOMBRE));
                libro.setNombreAutor(resultSet.getString(Constantes.TL_AUTOR));
                libro.setEdicionLibro(resultSet.getInt(Constantes.TL_EDICION));
                libro.setAnoLibro(resultSet.getInt(Constantes.TL_ANO));
                libro.setEstanteLibro(resultSet.getString(Constantes.TL_ESTANTE));
                libro.setFilaLibro(resultSet.getInt(Constantes.TL_FILA));
                libro.setIdEstado(resultSet.getInt(Constantes.TL_IDESTADO));
                
                arrayListLibro.add(libro);
            }
            
            return arrayListLibro;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayListLibro;
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
    public boolean addLibro(Libro libro) {
        
        String sql = "INSERT INTO "+ Constantes.T_LIBRO + "(" + Constantes.TL_IDLIBRO + "," + Constantes.TL_ISBN + "," + 
                Constantes.TL_NOMBRE + "," + Constantes.TL_AUTOR + "," +
                Constantes.TL_EDICION + "," + Constantes.TL_ANO + "," + 
                Constantes.TL_ESTANTE + "," + Constantes.TL_FILA + "," +
                Constantes.TL_IDESTADO + ") VALUES (?,?,?,?,?,?,?,?,?)";
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setInt(1, verLibros().size()+1);
            ps.setString(2, libro.getIsbnLibro());
            ps.setString(3, libro.getNombreLibro());
            ps.setString(4, libro.getNombreAutor());
            ps.setInt(5, libro.getEdicionLibro());
            ps.setInt(6, libro.getAnoLibro());
            ps.setString(7, libro.getEstanteLibro());
            ps.setInt(8, libro.getFilaLibro());
            ps.setInt(9, libro.getIdEstado());
            
            ps.executeUpdate();
            
            System.out.println("Libro ingresado con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al Añadir el libro "+e.getMessage());
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
    public boolean modificarLibro(Libro libro, String isbn) {
        String sql = "UPDATE " + Constantes.T_LIBRO + " SET " + Constantes.TL_NOMBRE + "= ?," +  
                    Constantes.TL_AUTOR + "= ?," + Constantes.TL_EDICION + "= ?," +
                    Constantes.TL_ANO + "= ?," + Constantes.TL_ESTANTE + "= ?," + 
                    Constantes.TL_FILA + "= ?," + Constantes.TL_IDESTADO + "= ?" +
                    " WHERE " + Constantes.T_LIBRO + "." + Constantes.TL_ISBN + "=\"" + isbn + "\"";
        
        try {
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setString(1, libro.getNombreLibro());
            ps.setString(2, libro.getNombreAutor());
            ps.setInt(3, libro.getEdicionLibro());
            ps.setInt(4, libro.getAnoLibro());
            ps.setString(5, libro.getEstanteLibro());
            ps.setInt(6, libro.getFilaLibro());
            ps.setInt(7, libro.getIdEstado());
            
            ps.executeUpdate();
            
            System.out.println("Libro actualizado con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al Añadir el libro "+e.getMessage());
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
    public boolean eliminarLibro(String isbn) {
        String sql = "DELETE FROM " + Constantes.T_LIBRO + " WHERE " + 
                    Constantes.T_LIBRO + "." + Constantes.TL_ISBN + "=" + "\"" + isbn +"\"";
        
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.executeUpdate();
            
            System.out.println("Libro Eliminado con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al Añadir el libro "+e.getMessage());
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
    public boolean cambioEstadoLibro(Libro libro) {
        String sql = "UPDATE " + Constantes.T_LIBRO + " SET " + Constantes.TL_IDESTADO + "= ?" +
                    " WHERE " + Constantes.T_LIBRO + "." + Constantes.TL_ISBN + "=\"" + libro.getIsbnLibro() + "\"";
        
        try {
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setInt(1, libro.getIdEstado());
            ps.executeUpdate();
            
            System.out.println("Libro actualizado con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el libro "+e.getMessage());
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
