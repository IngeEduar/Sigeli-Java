
package main.java.com.mycompany.sigeliapp.modelos;

import java.util.ArrayList;

public class Carrera {
    private int idCarrera;
    private String carrera;

    public Carrera() {
    }

    public Carrera(int idCarrera, String carrera) {
        this.idCarrera = idCarrera;
        this.carrera = carrera;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
        

}
