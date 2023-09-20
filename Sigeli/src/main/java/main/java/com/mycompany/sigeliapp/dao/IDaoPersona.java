package main.java.com.mycompany.sigeliapp.dao;

import main.java.com.mycompany.sigeliapp.modelos.Persona;

import java.util.ArrayList;


public interface IDaoPersona {
    
    public ArrayList<Persona> verPersonas();
    
    public boolean editarPersona(Persona persona);
    
    public boolean addPersona(Persona persona);
    
    public boolean eliminarPersona(int documento);
    
}
