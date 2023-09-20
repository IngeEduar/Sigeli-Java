package main.java.com.mycompany.sigeliapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.java.com.mycompany.sigeliapp.modelos.Multa;
import main.java.com.mycompany.sigeliapp.modelos.Usuario;


public class DaoUsuario extends Conexion implements IDaoUsuario{

    @Override
    public ArrayList<Usuario> verUsuarios() {
        ArrayList<Usuario> arrayListUsuario = new ArrayList<>();
        
        String sql = "SELECT * FROM " + Constantes.T_USUARIO;

        try {
            PreparedStatement ps = getConexion().prepareStatement(sql);
            ResultSet resultSet=ps.executeQuery();
            
            while(resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setDocumento(Integer.parseInt(resultSet.getString(Constantes.TU_DOCUMENTO)));
                usuario.setClave(resultSet.getString(Constantes.TU_CLAVE));
                usuario.setIdCargo(resultSet.getInt(Constantes.TU_CARGO));
                
                arrayListUsuario.add(usuario);
            }
            
            return arrayListUsuario;
            
        } catch (SQLException ex) {
            Logger.getLogger(DaoCarrera.class.getName()).log(Level.SEVERE, null, ex);
            return arrayListUsuario;
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
    public boolean addUsuario(Usuario usuario) {
        String sql = "INSERT INTO "+ Constantes.T_USUARIO+ "(" + Constantes.TU_DOCUMENTO + "," + 
                Constantes.TU_CLAVE + "," + Constantes.TU_CARGO  + ") " + 
                "VALUES (?,?,?)";
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setString(1, String.valueOf(usuario.getDocumento()));
            ps.setString(2, usuario.getClave());
            ps.setInt(3, usuario.getIdCargo());
            
            ps.executeUpdate();
            
            System.out.println("Usuario creado con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al crear el usuario "+e.getMessage());
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
    public boolean cambioClave(Usuario usuario) {
        String sql = "UPDATE " + Constantes.T_USUARIO + " SET " + Constantes.TU_CLAVE + "= ?" +
                    " WHERE " + Constantes.T_USUARIO + "." + Constantes.TU_DOCUMENTO + "=\"" + usuario.getDocumento() + "\"";
        
        try {
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.setString(1, usuario.getClave());
            
            ps.executeUpdate();
            
            System.out.println("El usuario fue actualizado con éxito...");
            
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
    public boolean eliminarUsuario(int documento) {
        String sql = "DELETE FROM " + Constantes.T_USUARIO + " WHERE " + Constantes.TU_DOCUMENTO + " =\"" + documento + "\"";
        
        try {            
            PreparedStatement ps=getConexion().prepareStatement(sql);
            ps.executeUpdate();
            
            System.out.println("Usuario Eliminado con éxito");
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario "+e.getMessage());
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
