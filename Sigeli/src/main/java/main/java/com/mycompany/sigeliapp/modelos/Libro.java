package main.java.com.mycompany.sigeliapp.modelos;

import java.util.ArrayList;


public class Libro {
    private int idLibro;
    private String isbnLibro;
    private String nombreLibro;
    private String nombreAutor;
    private int edicionLibro;
    private int anoLibro;
    private String estanteLibro;
    private int filaLibro;
    private int idEstado;

    public Libro() {
    }

    public Libro(int idLibro, String isbnLibro, String nombreLibro, String nombreAutor, int edicionLibro, int anoLibro, String estanteLibro, int filaLibro, int idEstado) {
        this.idLibro = idLibro;
        this.isbnLibro = isbnLibro;
        this.nombreLibro = nombreLibro;
        this.nombreAutor = nombreAutor;
        this.edicionLibro = edicionLibro;
        this.anoLibro = anoLibro;
        this.estanteLibro = estanteLibro;
        this.filaLibro = filaLibro;
        this.idEstado = idEstado;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }
    
    

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }


    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public int getEdicionLibro() {
        return edicionLibro;
    }

    public void setEdicionLibro(int edicionLibro) {
        this.edicionLibro = edicionLibro;
    }

    public int getAnoLibro() {
        return anoLibro;
    }

    public void setAnoLibro(int anoLibro) {
        this.anoLibro = anoLibro;
    }

    public String getEstanteLibro() {
        return estanteLibro;
    }

    public void setEstanteLibro(String estanteLibro) {
        this.estanteLibro = estanteLibro;
    }

    public int getFilaLibro() {
        return filaLibro;
    }

    public void setFilaLibro(int filaLibro) {
        this.filaLibro = filaLibro;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    
   /* public ArrayList<Libro> arrayListLibro(){
        ArrayList<Libro> arrayListLibro = new ArrayList<>();
        arrayListLibro.add(new Libro("3456", "El principito", "Eduar", 2, 2002, "B", 3, 1));
        arrayListLibro.add(new Libro("56789", "El principito2", "Eduar2", 3, 2002, "C", 2, 0));
        arrayListLibro.add(new Libro("1010a", "100 años de soledad", "Gabo", 1, 1999, "B", 3, 1));
        arrayListLibro.add(new Libro("Afcv", "Don quijote de la mancha", "Gabo", 1, 2001, "A", 5, 1));
        arrayListLibro.add(new Libro("zzzz", "Java netbeans", "Eduar Avendaño", 2, 2022, "F", 2, 1));
        
        return arrayListLibro;
    }*/
}
