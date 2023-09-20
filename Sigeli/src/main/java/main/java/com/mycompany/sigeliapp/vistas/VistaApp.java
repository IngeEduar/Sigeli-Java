package main.java.com.mycompany.sigeliapp.vistas;

import java.util.Scanner;
import main.java.com.mycompany.sigeliapp.modelos.*;

public class VistaApp {
    
    private Scanner teclado= new Scanner(System.in);
    
    public String getEntrada(String texto){
        
        System.out.println(texto);
        
        return teclado.nextLine();
    }
    
    public int getEntradaInt(String texto){
        
        System.out.println(texto);
        
        return teclado.nextInt();
    }
    
    public void setTexto(String texto){
        
        System.out.println(texto);
        
    }
    
    public void setTextoError(String texto){
        
        System.err.println(texto);
        
    }
    
    public int login(){
        System.out.println("Ingrese la opcion a ejecutar: \n1. Ingresar\n2. Registrarse\n3. Salir");
        
        return teclado.nextInt();
    }
    
    public String menuAdmin(){
        
        System.out.println("Ingrese la opción de lo que desea hacer: "
                + "\n1. administrar libros\n2. Ver usuarios"
                + "\n3. añadir libros\n4. Prestamo de libros\n5. Administrar multas\n6. Generar reporte\n7. opciones de cuenta\n8. salir");
        
        return teclado.nextLine();
        
    }
    
    public int menuAdminLibros(){
        System.out.println("Ingrese la opción de lo que desea hacer: "
                + "\n1. Ver libros\n2. Modificar libros\n3. Buscar Libros");
        return teclado.nextInt();
    
    }
    
    
    public int menuEstudiante(){
        System.out.println("Ingrese la opción de lo que desea hacer: "
                + "\n1. Buscar libros\n2. Opciones de cuenta\n3. Salir");
        return teclado.nextInt();
    }
    
    public void verLibros(Libro libro, Estado estado){
        System.out.println("Nombre libro: " + libro.getNombreLibro() + "\nEdición libro: " + libro.getEdicionLibro()
                         + "\nAutor: " + libro.getNombreAutor() + "\nISBN libro: " + libro.getIsbnLibro() + "\nUbicación: Estante: " + libro.getEstanteLibro() +
                           " Fila: " + libro.getFilaLibro() + "\nAño del libro: " + libro.getAnoLibro() + "\nEstado del libro: " + estado.getEstado() + "\n");
    }
    

    public void verUsuarios(Persona persona, Carrera carrera){
        System.out.println("Nombre Usuario: " + persona.getNombrePersona() + "\nEmail: " + persona.getEmailPersona() +
                           "\nDocumento:" + persona.getDocumentoPersona() + "\n" + carrera.getCarrera() + "\n");
    }
    
    
    public String adminUsuarios(){
        System.out.println("Ingrese la opcion de lo que desea hacer: " +
                           "\n1. Administrar usuarios\n2. Buscar Usuario\n3. Salir");
        
        return teclado.nextLine();
    }
    
    public int prestamo(){
        System.out.println("Ingrese la opcion de lo que desea hacer: " +
                           "\n1. Ver información de un prestamo\n2. Hacer un prestamo\n3. Validar un prestamo\n4. Salir");
        
        return teclado.nextInt();
    }
    
    public void verPrestamo(Prestamos prestamo, Persona persona, Libro libro, Estado estado){
        System.out.println("\nPrestamo numero: " + prestamo.getIdPrestamo() + 
                           "\nPersona: " + persona.getNombrePersona() + "\nDocumento: "+  persona.getDocumentoPersona() +
                           "\nTelefono: " + persona.getTelefono() + "\nEmail: " + persona.getEmailPersona() +
                           "\nIsbn del libro: " + libro.getIsbnLibro() + "\nNombre del libro: " + libro.getNombreLibro() +
                           "\nFecha de prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega() +
                           "\nEstado del libro: " + estado.getEstado());
    }
    
    public int adminMultas(){
        System.out.println("Ingrse la opcion de lo que desea hacer: \n1. Ver todas las multas\n2. Buscar multas\n3. Pagar multa\n4. Eliminar multa\n5. salir");
        return teclado.nextInt();
    }
    
    public void verMultas(Multa multa,Prestamos prestamo,Persona persona,Libro libro,Estado estado){
        System.out.println("\nMulta numero: " + multa.getIdMulta() + "\nNombre: " + persona.getNombrePersona() +
                           "\nDocumento: " + persona.getDocumentoPersona() + "\nEmail: " + persona.getEmailPersona() +
                           "\nPrestamo: " + prestamo.getIdPrestamo() + "\nFecha del prestamo: " + prestamo.getFechaPrestamo() +
                           "\nFecha de entrega: " + prestamo.getFechaEntrega() + "\nLibro: " + libro.getNombreLibro() +
                           "\nIsbn: " + libro.getIsbnLibro() + "\nValor de la multa: " + multa.getValorMulta() + "\nEstado: " +
                              estado.getEstado() + "\n");
    }
    
    public int opcionesCuenta(){
        System.out.println("Ingrese la opción de lo que desea hacer: \n1. Cambiar clave\n2. Salir");
        return teclado.nextInt();
    }
    
    public int modificarLibro(){
        System.out.println("Ingrese lo que desea hacer: \n1. modificar libro \n2. eliminar libro\n3. Salir");
        
        return teclado.nextInt();
    }
    
    public void vermult(Multa multa){
        System.out.println("\nMulta numero: " + multa.getIdMulta() + "\nPersona: " + multa.getDocumentoPersona() +
                           "\nValor de la multa: " + multa.getValorMulta());
    }
    
}
