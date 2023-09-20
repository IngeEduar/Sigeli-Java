package main.java.com.mycompany.sigeliapp.dao;

import java.util.ArrayList;
import main.java.com.mycompany.sigeliapp.modelos.Multa;


public interface IDaoMulta {
    
    public ArrayList<Multa> verMultas();
    
    public boolean hacerMultas(Multa multa);
    
    public boolean pagarMulta(int id, Multa multa);
    
    public boolean eliminarMulta(int id);
    
}
