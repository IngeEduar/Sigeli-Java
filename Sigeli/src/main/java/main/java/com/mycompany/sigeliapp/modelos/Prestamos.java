package main.java.com.mycompany.sigeliapp.modelos;

import java.sql.Date;
import java.util.ArrayList;


public class Prestamos {
     private int idPrestamo;
    private int idPersona;
    private String isbnLibro;
    private Date fechaPrestamo;
    private Date fechaEntrega;
    private int idEstado;

    public Prestamos() {
    }

    public Prestamos(int idPrestamo, int idPersona, String isbnLibro, Date fechaPrestamo, Date fechaEntrega, int idEstado) {
        this.idPrestamo = idPrestamo;
        this.idPersona = idPersona;
        this.isbnLibro = isbnLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEntrega = fechaEntrega;
        this.idEstado = idEstado; 
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    
    
}
