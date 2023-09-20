package main.java.com.mycompany.sigeliapp.modelos;

import java.util.ArrayList;

public class Usuario {
    private int documento;
    private String clave;
    private int idCargo;

    public Usuario() {
    }

    public Usuario(int documento, String clave, int idCargo) {
        this.documento = documento;
        this.clave = clave;
        this.idCargo = idCargo;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }
    
}
