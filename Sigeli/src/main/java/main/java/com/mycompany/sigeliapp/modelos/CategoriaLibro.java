
package main.java.com.mycompany.sigeliapp.modelos;

import java.util.ArrayList;

public class CategoriaLibro {
    
    private String isbnLibro;
    private int idCategoria;

    public CategoriaLibro() {
    }

    public CategoriaLibro(String isbnLibro, int idCategoria) {
        this.isbnLibro = isbnLibro;
        this.idCategoria = idCategoria;
    }

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
}
