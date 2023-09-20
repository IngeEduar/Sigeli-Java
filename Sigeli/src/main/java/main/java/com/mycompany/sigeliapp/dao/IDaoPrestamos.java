package main.java.com.mycompany.sigeliapp.dao;

import java.util.ArrayList;
import main.java.com.mycompany.sigeliapp.modelos.Prestamos;

public interface IDaoPrestamos {
    
    
    public ArrayList<Prestamos> verPrestamos();
    
    public boolean addPrestamo(Prestamos prestamo);
    
    public boolean validarPrestamo(Prestamos prestamo);
    
    public boolean cambioEstado(Prestamos prestamo);
}
