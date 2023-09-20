
package main.java.com.mycompany.sigeliapp.validaciones;

import javax.swing.JOptionPane;
import main.java.com.mycompany.sigeliapp.modelos.Libro;


public class ValidacionLibros {
    
    public static boolean crearLibro(Libro libro){
        
        if(libro.getNombreLibro().equals("")){
            JOptionPane.showMessageDialog(null, "El nombre del libro no puede estar vacío");
            return false;
        }
        else if(libro.getNombreAutor().equals("")){
            JOptionPane.showMessageDialog(null, "El nombre del Autor no puede estar vacío");
            return false;
        }
        else if(libro.getIsbnLibro().equals("")){
            JOptionPane.showMessageDialog(null, "El ISBN del libro no puede estar vacío");
            return false;
        }
        else if(libro.getEstanteLibro().equals("")){
            JOptionPane.showMessageDialog(null, "El Estante del libro no puede estar vacío");
            return false;
        }
        else if(libro.getFilaLibro() == 0){
            JOptionPane.showMessageDialog(null, "La fila del libro no puede estar vacío");
            return false;
        }
        
        else{
            return true;
        }
       
    }
    
}
