
package main.java.com.mycompany.sigeliapp.validaciones;

import java.sql.Date;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import main.java.com.mycompany.sigeliapp.dao.*;
import main.java.com.mycompany.sigeliapp.modelos.*;


public class ValidacionPrestamos {
    

    public static boolean validarNuevoPrestamo(Prestamos prestamo){
        IDaoPrestamos iDaoPrestamos = new DaoPrestamos();
        IDaoMulta iDaoMulta = new DaoMulta();
        String msj = "";
        boolean multaUsuario = false;
        
        
        for(Multa multa : iDaoMulta.verMultas()){
            if(multa.getDocumentoPersona() == prestamo.getIdPersona()){
                msj += "El usuario tiene multas pendientes";
                multaUsuario = true;
            }
        }
        
        if(prestamo.getFechaEntrega().before(Date.valueOf(LocalDate.now()))){
            if(!msj.equals("")){
                msj += ", ";
            }
            msj += "La fecha ingresada es menor a la fecha actual";
        }
        

        
        if(prestamo.getFechaEntrega().after(Date.valueOf(LocalDate.now())) && multaUsuario == false){
            return true;
        }
            
        else{
            JOptionPane.showMessageDialog(null, msj);
            return false;
        }

    }
}
