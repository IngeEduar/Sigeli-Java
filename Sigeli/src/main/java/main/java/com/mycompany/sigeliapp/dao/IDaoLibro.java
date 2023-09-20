package main.java.com.mycompany.sigeliapp.dao;

import java.util.ArrayList;

import main.java.com.mycompany.sigeliapp.modelos.Libro;

public interface IDaoLibro {
    
    public ArrayList<Libro> verLibros();
    
    public boolean addLibro(Libro libro);
    
    public boolean modificarLibro(Libro libro, String isbn);
    
    public boolean eliminarLibro(String isbn);
    
    public boolean cambioEstadoLibro(Libro libro);
    
}
