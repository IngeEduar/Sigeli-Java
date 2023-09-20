package main.java.com.mycompany.sigeliapp.dao;

import java.util.ArrayList;
import main.java.com.mycompany.sigeliapp.modelos.Usuario;


public interface IDaoUsuario {
    
    public ArrayList<Usuario> verUsuarios();
    
    public boolean addUsuario(Usuario usuario);
    
    public boolean cambioClave(Usuario usuario);
    
    public boolean eliminarUsuario(int documento);
    
}
