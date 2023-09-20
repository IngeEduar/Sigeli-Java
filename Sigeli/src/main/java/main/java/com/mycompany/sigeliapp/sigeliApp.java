package main.java.com.mycompany.sigeliapp;

import  main.java.com.mycompany.sigeliapp.controladores.*;
import main.java.com.mycompany.sigeliapp.modelos.utiles.InicializadorMultas;


public class sigeliApp {
    
    public static void main(String[] args) {
        
       /* ControladorApp controlador = new ControladorApp();
        
        controlador.inicio();*/
       
        InicializadorMultas ini = new InicializadorMultas();
        
        ini.crearMultas();
        
        System.out.println("Multas creadas");
       
       
       ControladorLogin controlador = new ControladorLogin();

       controlador.visibleLogin();
        
    }
}
