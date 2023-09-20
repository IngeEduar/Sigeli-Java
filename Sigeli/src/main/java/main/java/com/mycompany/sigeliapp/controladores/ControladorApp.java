
package main.java.com.mycompany.sigeliapp.controladores;

import java.util.ArrayList;
import java.sql.Date;
import java.time.*; // Este paquete contiene LocalDate, LocalTime y LocalDateTime.
import java.time.format.*;  // Este paquete contiene DateTimeFormatter.

import main.java.com.mycompany.sigeliapp.modelos.*;
import main.java.com.mycompany.sigeliapp.dao.*;
import main.java.com.mycompany.sigeliapp.vistas.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.java.com.mycompany.sigeliapp.modelos.utiles.Portapapeles;




public class ControladorApp {
    
    private VistaApp vistaApp;
    private Persona persona;
    private Categoria categoria;
    private CategoriaLibro categoriaLibro;
    private Estado estado;
    private Libro libro;
    private Multa multa;
    private Carrera carrera;
    private Usuario usuario;
    private Prestamos prestamo;
    
    
    private IDaoCarrera iDaoCarrera;
    private IDaoCategoria iDaoCategoria;
    private IDaoCategoriaLibro iDaoCategoriaLibro;
    private IDaoEstado iDaoEstado;
    private IDaoLibro iDaoLibro;
    private IDaoMulta iDaoMulta;
    private IDaoPrestamos iDaoPrestamos;
    private IDaoPersona iDaoPersona;
    private IDaoUsuario iDaoUsuario;
    
    int documentoLogin = 0;
    String claveLogin = "";

    
    

    public ControladorApp() {
        this.vistaApp = new VistaApp();
        this.persona = new Persona();
        this.categoria = new Categoria();
        this.categoriaLibro = new CategoriaLibro();
        this.estado = new Estado();
        this.libro = new Libro();
        this.multa = new Multa();
        this.carrera = new Carrera();
        this.prestamo = new Prestamos();
        this.usuario = new Usuario();
        
        this.iDaoCarrera = new DaoCarrera();
        this.iDaoCategoria = new DaoCategoria();
        this.iDaoCategoriaLibro = new DaoCategoriaLibro();
        this.iDaoEstado = new DaoEstado();
        this.iDaoLibro = new DaoLibro();
        this.iDaoMulta = new DaoMulta();
        this.iDaoPrestamos = new DaoPrestamos();
        this.iDaoPersona = new DaoPersona();
        this.iDaoUsuario = new DaoUsuario();

    }

    public void inicio() {
                               
        crearMultas(iDaoMulta.verMultas(), iDaoPrestamos.verPrestamos());
        
        int opcion = 0;
        
        do{
            switch(vistaApp.login()){
                case 1: //login(iDaoUsuario.verUsuarios());
                    break;
                case 2: //registrarUsuario(persona.arrayListPersonas(), usuario.arrayListUsuario());
                    break;
                case 3: vistaApp.setTexto("Gracias por usar nuestro programa...");
                    opcion = 3;
                break;
            }
        }while(opcion != 3);

    }
    
    public void login(ArrayList<Usuario> arrayListUsuario, int documento, String clave){

        this.documentoLogin = documento;
        this.claveLogin = clave;
        
        for(Usuario usuario : arrayListUsuario){
            if(documentoLogin == usuario.getDocumento() && claveLogin.equals(usuario.getClave())){
                if(usuario.getIdCargo() == 1){
                    panelAdmin();
                }

                else if(usuario.getIdCargo() == 2){
                    panelEstudiante();
                }
            }
        }
        
    }
    
    public void registrarUsuario(ArrayList<Persona> arrayListPersona, ArrayList<Usuario> arrayListUsuario){
        String nombre, email, telefono, clave, claveAdmin;
        int documento, idCarrera, idCargo, documentoAdmin;
        
        documentoAdmin =vistaApp.getEntradaInt("Ingrese su documento de administrador: ");
        vistaApp.getEntrada("");
        claveAdmin = vistaApp.getEntrada("Ingrese su clave: ");

        for(Usuario usuario : arrayListUsuario){
            if(documentoAdmin == usuario.getDocumento() && claveAdmin.equals(usuario.getClave())){
                if(usuario.getIdCargo() == 1){
                    documento = vistaApp.getEntradaInt("Ingrese su documento: ");
                    nombre = vistaApp.getEntrada("Ingrese su nombre: ");
                    vistaApp.getEntrada("");
                    email = vistaApp.getEntrada("Ingrese su email: ");
                    telefono = vistaApp.getEntrada("Ingrese su telefono: ");
                    mostrarCarreras(iDaoCarrera.verCarreras());
                    idCarrera = vistaApp.getEntradaInt("Ingrese su carrera: ");
                    idCargo = vistaApp.getEntradaInt("Ingrese su cargo: \n1. Administrador\n2. Estudiante");
                    clave = vistaApp.getEntrada("Ingrese su clave: ");
                    
                    Persona persona = new Persona(documento, nombre, idCarrera, email, telefono);
                    Usuario usuarioE = new Usuario(documento, clave, idCargo);
                    iDaoPersona.addPersona(persona);
                    iDaoUsuario.addUsuario(usuarioE);
                    
                    vistaApp.setTexto("Usuario Ingresado con éxito");
                }
            }
        }
        
    }


    public void panelAdmin() {
        vistaApp.setTexto("Perfil de administrador");
         
        boolean opcion = true;
        
        do{

            switch (vistaApp.menuAdmin()) {
                case "1":
                    adminLibros();
                    break;
                case "2":
                    verUsuarios(iDaoPersona.verPersonas(), iDaoCarrera.verCarreras());
                    break;
                case "3":
                    addLibros(iDaoLibro.verLibros(), iDaoEstado.verEstados());
                    break;
                case "4":
                    prestamoLibros();
                    break;
                case "5":
                    adminMultas();
                    break;
                case "6":
                    reportes();
                    break;
                case "7":
                    opcionesCuenta(iDaoPersona.verPersonas(), iDaoUsuario.verUsuarios(), documentoLogin, iDaoCarrera.verCarreras());
                    break;
                case "8":
                    opcion = false;
                    vistaApp.setTexto("Gracias por usar nuestro programa");
                    break;
            }
            
        }while(opcion == true);
    }

    public void panelEstudiante() {
        vistaApp.setTexto("Perfil de Estudiante");
        
        boolean opcion = true;
        
        do{
        switch (vistaApp.menuEstudiante()) {
            case 1:
                busquedaLibro(iDaoLibro.verLibros(), iDaoEstado.verEstados(), iDaoCategoriaLibro.verCategoriaLibros(), iDaoCategoria.verCategorias());
                break;
            case 2:
                opcionesCuenta(iDaoPersona.verPersonas(), iDaoUsuario.verUsuarios(), documentoLogin, iDaoCarrera.verCarreras());
                break;
             case 3:
                    opcion = false;
                    vistaApp.setTexto("Gracias por usar nuestro programa");
                    break;
            }
            
        }while(opcion == true);
    }

    
    
    //funciones de los paneles
    
    
    //con DAO
    public void adminLibros() {

        switch (vistaApp.menuAdminLibros()) {
            case 1:
                verLibros(iDaoLibro.verLibros(), iDaoEstado.verEstados());
                break;
            case 2:
                modificarLibros(iDaoLibro.verLibros(), iDaoEstado.verEstados());
                break;
            case 3: 
                busquedaLibro(iDaoLibro.verLibros(), iDaoEstado.verEstados(), iDaoCategoriaLibro.verCategoriaLibros(), iDaoCategoria.verCategorias());
                break;
            default: 
                break;
        }

    }

    public void verUsuarios(ArrayList<Persona> arrayListPersonas, ArrayList<Carrera> arrayListCarrera) {

        for(Persona persona : arrayListPersonas){

            Persona personaEnviar = new Persona();
            Carrera carreraEnviar = new Carrera();

            personaEnviar.setNombrePersona(persona.getNombrePersona());
            personaEnviar.setDocumentoPersona(persona.getDocumentoPersona());
            personaEnviar.setEmailPersona(persona.getEmailPersona());

            if(persona.getIdCarrera() == 0){
                carreraEnviar.setCarrera(" ");
            }
            else{
                carreraEnviar.setCarrera("Carrera: " + arrayListCarrera.get(persona.getIdCarrera()-1).getCarrera());
            }
            
            vistaApp.verUsuarios(personaEnviar, carreraEnviar);
            
        }
        switch(vistaApp.adminUsuarios()){
            case "1": administrarUsuario(arrayListPersonas); 
                break;
            case "2": buscarUsuario(arrayListPersonas, iDaoCarrera.verCarreras(), vistaApp.getEntradaInt("Ingrese el documento: "));
                break;
            default: 
                break;
        }
        
    }
    
    //con DAO
    public void addLibros(ArrayList<Libro> arrayListLibro, ArrayList<Estado> arrayListEstado) {

        Libro libroAdd = new Libro();
        libroAdd.setNombreLibro(vistaApp.getEntrada("Ingrese el nombre del libro: "));
        libroAdd.setIsbnLibro(vistaApp.getEntrada("Ingrese el ISBN del libro: "));
        libroAdd.setNombreAutor(vistaApp.getEntrada("Ingrese el nombre del Autor: "));
        libroAdd.setEdicionLibro(Integer.parseInt(vistaApp.getEntrada("Ingrese la edición del libro: ")));
        libroAdd.setAnoLibro(Integer.parseInt(vistaApp.getEntrada("Ingrese el año de creación del libro: ")));
        libroAdd.setEstanteLibro(vistaApp.getEntrada("Ingrese el estante del libro: "));
        libroAdd.setFilaLibro(Integer.parseInt(vistaApp.getEntrada("Ingrese la fila del libro: ")));
        libroAdd.setIdEstado(Integer.parseInt(vistaApp.getEntrada("Ingrese el estado del libro: \n1. No disponible\n2.Disponible"))-1);
        
        iDaoLibro.addLibro(libroAdd);

    }

    public void prestamoLibros() {
        
        switch(vistaApp.prestamo()){
            case 1: verPrestamo(iDaoPrestamos.verPrestamos(), iDaoPersona.verPersonas(), iDaoLibro.verLibros(), iDaoEstado.verEstados());
                break;
            case 2: crearPrestamo(iDaoPrestamos.verPrestamos(), iDaoPersona.verPersonas(), iDaoLibro.verLibros(), iDaoMulta.verMultas(), iDaoEstado.verEstados());
                break;
            case 3: validarPrestamo(iDaoPrestamos.verPrestamos(), iDaoPersona.verPersonas(), iDaoLibro.verLibros(), iDaoEstado.verEstados());
                break;
            default :
                break;
            
        }
    }

    public void adminMultas(){
        switch(vistaApp.adminMultas()){
            case 1: multas(iDaoMulta.verMultas(), iDaoPersona.verPersonas(), iDaoPrestamos.verPrestamos(), iDaoLibro.verLibros(), iDaoEstado.verEstados());
                break;
            case 2: buscarMulta(iDaoMulta.verMultas(), iDaoPersona.verPersonas(), iDaoPrestamos.verPrestamos(), iDaoLibro.verLibros(), iDaoEstado.verEstados());
                break;
            case 3: pagoMultas(iDaoMulta.verMultas());
                break;
            case 4: eliminarMulta();
                break;
            default: 
                break;
        }
    }

    public void reportes() {

    }

    public void opcionesCuenta(ArrayList<Persona> arrayListPersona, ArrayList<Usuario> arrayListUsuario,int documento , ArrayList<Carrera> arrayListCarrera) {
        
        String clave1 = "", clave2 = "";
        
        for(Persona persona : arrayListPersona){
            for(Usuario usuario : arrayListUsuario){
                for(Carrera carrera : arrayListCarrera){
                    if(persona.getIdCarrera() == carrera.getIdCarrera()){
                        if(usuario.getDocumento()== documento && usuario.getDocumento() == persona.getDocumentoPersona()){
                            vistaApp.verUsuarios(persona, carrera);
                            
                            switch(vistaApp.opcionesCuenta()){
                                case 1: clave1 = vistaApp.getEntrada("Ingrese la nueva contraseña: ");
                                        vistaApp.getEntrada("");
                                        clave2 = vistaApp.getEntrada("Ingrese nuevamente la contraseña: ");

                                        if(clave1.equals(clave2)){
                                            usuario.setClave(clave2);
                                            iDaoUsuario.cambioClave(usuario);
                                            vistaApp.setTexto("La clave ha sido cambiada con éxito");
                                        }                
                                        else{
                                            vistaApp.setTexto("Las claves no coinciden");
                                        }
                                    break;
                                case 2: vistaApp.setTexto("Será enviado al panel principal...");
                                    break;
                            }
                            
                        }
                    } 
                }
            }
        }
    }
    
    

    //Funciones de las funciones de los paneles
    
    //funciones de libro
    
    //con DAO
    public void busquedaLibro(ArrayList<Libro> arrayListLibro, ArrayList<Estado> arrayListEstado, ArrayList<CategoriaLibro> arrayListCategoriaLibro, ArrayList<Categoria> arrayListCategoria) {
        
        vistaApp.getEntrada("");
        String busqueda = vistaApp.getEntrada("Ingrese el libro a buscar: ");
        int cantLibros = 0;
        Estado estadoEnviar = new Estado();
        
        Pattern pat = Pattern.compile(".*" + busqueda.toLowerCase() + ".*");
        
        vistaApp.setTexto("Resultados para \"" + busqueda + "\"");
        
        for(Libro libro : arrayListLibro){
            for( Categoria categoria: arrayListCategoria){
                Matcher mat5 = pat.matcher(categoria.getNombreCategoria().toLowerCase());
                if(mat5.matches()){
                    for(CategoriaLibro categoriaLibro : arrayListCategoriaLibro){
                        if(libro.getIsbnLibro().equals(categoriaLibro.getIsbnLibro()) && categoriaLibro.getIdCategoria() == categoria.getIdCategoria()){
                            for(Estado estado : arrayListEstado){
                                if(libro.getIdEstado() == estado.getIdEstado()){
                                    estadoEnviar = estado;
                                }
                            }
                            cantLibros += 1;
                            vistaApp.verLibros(libro, estadoEnviar);
                        }
                    }
                }
            }
            Matcher mat = pat.matcher(libro.getNombreLibro().toLowerCase());
            Matcher mat2 = pat.matcher(libro.getNombreAutor().toLowerCase());
            Matcher mat3 = pat.matcher(libro.getIsbnLibro());
            Matcher mat4 = pat.matcher(String.valueOf(libro.getAnoLibro()));
            if(mat.matches() || mat2.matches() || mat3.matches() || mat4.matches()){
                for(Estado estado : arrayListEstado){
                    if(libro.getIdEstado() == estado.getIdEstado()){
                        estadoEnviar = estado;
                    }
                }
                cantLibros += 1;
                vistaApp.verLibros(libro, estadoEnviar);
            }
            
        }
        
        vistaApp.setTexto("Cerca de " + cantLibros + " libros encontrados entre "+ arrayListLibro.size() +"\n");
 
    }

    //con DAO
    public void verLibros(ArrayList<Libro> arrayListLibro, ArrayList<Estado> arrayListEstado) {

        int cantLibros = 0;

        for (Libro libro : arrayListLibro) {

            Libro libroEnviar = new Libro();
            Estado estadoEnviar = new Estado();

            cantLibros += 1;

            libroEnviar.setIsbnLibro(libro.getIsbnLibro());
            libroEnviar.setNombreLibro(libro.getNombreLibro());
            libroEnviar.setNombreAutor(libro.getNombreAutor());
            libroEnviar.setEdicionLibro(libro.getEdicionLibro());
            libroEnviar.setAnoLibro(libro.getAnoLibro());
            libroEnviar.setEstanteLibro(libro.getEstanteLibro());
            libroEnviar.setFilaLibro(libro.getFilaLibro());
            estadoEnviar.setIdEstado(libro.getIdEstado());
            estadoEnviar.setEstado(arrayListEstado.get(libro.getIdEstado()).getEstado());
            
            vistaApp.verLibros(libroEnviar, estadoEnviar);
        }

        vistaApp.setTexto("Se mostraron " + cantLibros + " Libros");
    }

    //con DAO
    public void modificarLibros(ArrayList<Libro> arrayListLibro, ArrayList<Estado> arrayListEstado) {

        vistaApp.getEntrada("");
        String isbn = vistaApp.getEntrada("Ingrese el ISBN del libro: ");
        int opcion = vistaApp.modificarLibro();
        
        if(opcion == 1){
            for (Libro libro : arrayListLibro) {

                if (libro.getIsbnLibro().equals(isbn)) {

                        vistaApp.setTexto("***Ingrese los nuevos datos del libro***");
                        libro.setNombreLibro(vistaApp.getEntrada("Ingrese el nuevo nombre del libro: "));
                        libro.setNombreAutor(vistaApp.getEntrada("Ingrese el nuevo nombre del Autor: "));
                        libro.setEdicionLibro(Integer.parseInt(vistaApp.getEntrada("Ingrese la nueva edición del libro: ")));
                        libro.setAnoLibro(Integer.parseInt(vistaApp.getEntrada("Ingrese el año del libro: ")));
                        libro.setEstanteLibro(vistaApp.getEntrada("Ingrese el estante del libro: "));
                        libro.setFilaLibro(Integer.parseInt(vistaApp.getEntrada("Ingrese la fila del libro: ")));
                        libro.setIdEstado(Integer.parseInt(vistaApp.getEntrada("Ingrese el estado del libro: \n1. No disponible\n2. Disponible")) - 1);

                        iDaoLibro.modificarLibro(libro, isbn);

                }

            }

        }
        else if(opcion == 2){                
            
            for(int i=0; i<arrayListLibro.size(); i++){
                
                iDaoLibro.eliminarLibro(isbn);
                
            }

        }
                
        else{
            
        }
        
    }
    
   
    
    //funciones de usuario
    
    public void administrarUsuario(ArrayList<Persona> arrayListPersona){
        
        int documento = Integer.parseInt(vistaApp.getEntrada("Ingrese el documento de la persona: "));
        
        buscarUsuario(arrayListPersona, iDaoCarrera.verCarreras(), documento);
        
        for(Persona persona : arrayListPersona){
            
            if(persona.getDocumentoPersona() == documento){
                persona.setNombrePersona(vistaApp.getEntrada("Ingrese el nuevo nombre de la persona: "));
                persona.setEmailPersona(vistaApp.getEntrada("Ingrese el nuevo email de la persona: "));
            }
            
            iDaoPersona.editarPersona(persona);
            
        }
        
        //buscarUsuario(arrayListPersona, iDaoCarrera.verCarreras(), documento);
        
    }
    
    public void buscarUsuario(ArrayList<Persona> arrayListPersona, ArrayList<Carrera> arrayListCarrera, int documento){
            
        for(Persona persona : arrayListPersona){
            
            if(persona.getDocumentoPersona() == documento){

                Persona personaEnviar = new Persona();
                Carrera carreraEnviar = new Carrera();

                personaEnviar.setNombrePersona(persona.getNombrePersona());
                personaEnviar.setDocumentoPersona(persona.getDocumentoPersona());
                personaEnviar.setEmailPersona(persona.getEmailPersona());

                if(persona.getIdCarrera() == 0){
                    carreraEnviar.setCarrera(" ");
                }
                else{
                    carreraEnviar.setCarrera("Carrera: " + arrayListCarrera.get(persona.getIdCarrera()).getCarrera());
                }

                vistaApp.verUsuarios(personaEnviar, carreraEnviar);

            }
        }
        
    }
            
        
    
    //funciones de prestamo
    
    public void verPrestamo(ArrayList<Prestamos> arrayListPrestamos,ArrayList<Persona> arrayListPersonas,ArrayList<Libro> arrayListLibro, ArrayList<Estado> arrayListEstado){
        
        int documento = vistaApp.getEntradaInt("Ingrese el documento de la persona: ");
        Estado estadoM = new Estado();
        
        for(Persona personaF : arrayListPersonas){
            for(Libro libroF : arrayListLibro){
                for(Prestamos prestamo : arrayListPrestamos){
                    if(libroF.getIsbnLibro().equals(prestamo.getIsbnLibro()) && personaF.getDocumentoPersona() == documento && prestamo.getIdPersona() == documento){
                        vistaApp.setTexto("Persona encontrada");
                        for(Estado estado : arrayListEstado){
                            
                            if(prestamo.getIdEstado() == 2){
                                prestamo.setIdEstado(2);
                            }

                            else if(prestamo.getFechaEntrega().before(Date.valueOf(LocalDate.now()))){
                                prestamo.setIdEstado(4);
                            }

                            else if(prestamo.getFechaEntrega().after(Date.valueOf(LocalDate.now()))){
                                prestamo.setIdEstado(3);
                            }

                            if(prestamo.getIdEstado() == estado.getIdEstado()){
                                estadoM = estado;
                            }

                        }
                        
                        iDaoPrestamos.cambioEstado(prestamo);
                        
                        vistaApp.verPrestamo(prestamo, personaF, libroF, estadoM);
                        vistaApp.setTexto("Libro enviado");
                    }
                }
            }
        }
            
    }
                           
    public void crearPrestamo(ArrayList<Prestamos> arrayListPrestamos,ArrayList<Persona> arrayListPersonas,ArrayList<Libro> arrayListLibro, ArrayList<Multa> arrayListMulta, ArrayList<Estado> arrayListEstado){
        Prestamos prestamoAdd = new Prestamos();  
        int idPersona = vistaApp.getEntradaInt("Ingrese el documento de la persona: " );
        int i = 0, cantDocumento =0;
        vistaApp.getEntrada("");
        String isbn = vistaApp.getEntrada("Ingrese el ISBN del libro: ");
        String mensaje = "";
        Date fechaEntrega = Date.valueOf(vistaApp.getEntrada("Ingrese el año:") +"-"+ vistaApp.getEntrada("Ingrese el Mes:") +"-"+ vistaApp.getEntrada("Ingrese el dia:"));
 
        boolean isbnValido = false, fechaValida = false, multa = false, libroDisponible = true;

        for(Prestamos prestamo : arrayListPrestamos){
            if(prestamo.getIdPersona() == idPersona){
                cantDocumento += 1;
                mensaje = "Usted ha pasado el límite de prestamos";
            }
            if(arrayListLibro.get(i).getIsbnLibro().equals(isbn)){
                isbnValido = true;
                
                if(arrayListLibro.get(i).getIdEstado() == 1){
                    mensaje = "El libro no se encuentra disponible";
                    libroDisponible = false;
                }
                
            }
            else{
                mensaje = "El libro ingresado no existe";
            }


            if(fechaEntrega.after(Date.valueOf(LocalDate.now()))){
                fechaValida = true;

            }
            else{
                mensaje = "La fecha de prestamo no puede ser mayor a la fecha de entrega";
            }
            
            i += 1;
                
        }
        
        for(Multa multas : arrayListMulta){
            if(multas.getDocumentoPersona() == idPersona){
                multa = true;
                mensaje = "El Usuario ingresado tiene multas pendientes";
            }
        }

        if(cantDocumento <= 2 && isbnValido == true && fechaValida == true && multa == false && libroDisponible == true){
            prestamoAdd.setIdPrestamo(arrayListPrestamos.size()+1);
            prestamoAdd.setIdPersona(idPersona);
            prestamoAdd.setIsbnLibro(isbn);
            prestamoAdd.setFechaPrestamo(Date.valueOf(LocalDate.now()));
            prestamoAdd.setFechaEntrega(fechaEntrega);
            prestamoAdd.setIdEstado(3);
            

            iDaoPrestamos.addPrestamo(prestamoAdd);

            //vistaApp.setTexto("El prestamo ha sido realizado con éxito");
        }
        else{
            vistaApp.setTextoError("El libro no fue ingresado");
        }

        vistaApp.setTextoError(mensaje);

    }
    
    public void validarPrestamo(ArrayList<Prestamos> arrayListPrestamos, ArrayList<Persona> arrayListPersonas,ArrayList<Libro> arrayListLibro, ArrayList<Estado> arrayListEstado){
        
        int idPrestamo = vistaApp.getEntradaInt("Ingrese el id del prestamo: ");
        vistaApp.getEntrada("");
        if(vistaApp.getEntrada("¿Quiere validar la entrega?\n1. Si\n2. No").equals("1")){
            for(Prestamos prestamo : arrayListPrestamos){
                if((idPrestamo-1) == prestamo.getIdPrestamo()){
                    prestamo.setIdEstado(2);
                    iDaoPrestamos.validarPrestamo(prestamo);
                }
            }
        }
        else{
            vistaApp.setTextoError("No se ha registrado la entrega del libro");
        }
        
    }

    //Funciones de multa
    
    //con DAO
    public void pagoMultas(ArrayList<Multa> arrayListMulta){
        int idMulta = vistaApp.getEntradaInt("Ingrese el id de la multa: ");
        
        for(Multa multa : arrayListMulta){
            if(multa.getIdMulta() == idMulta){
                int opcion =vistaApp.getEntradaInt("La multa tiene un valor de: $" + multa.getValorMulta() + " pesos. ¿Desea confirmar el pago?\n1. Si\n2. No");
                
                if(opcion==1){
                    iDaoMulta.pagarMulta(idMulta, multa);
                }
                
                else{
                    vistaApp.setTexto("El pago no ha sido subido");
                }
            }
        }
    }
    
    //con DAO
    public void multas(ArrayList<Multa> arrayListMulta, ArrayList<Persona> arrayListPersona, ArrayList<Prestamos> arrayListPrestamos, ArrayList<Libro> arrayListLibro, ArrayList<Estado> arrayListEstado){
        
        for(Multa multa : arrayListMulta){
            for(Prestamos prestamo : arrayListPrestamos){
                if(prestamo.getIdPrestamo() == multa.getIdPrestamo()){
                    for(Persona persona : arrayListPersona){
                        if(persona.getDocumentoPersona() == multa.getDocumentoPersona()){
                            for(Libro libro : arrayListLibro){
                                if(libro.getIsbnLibro().equals(prestamo.getIsbnLibro())){                                    
                                    for(Estado estado : arrayListEstado){                                        
                                        if(estado.getIdEstado() == multa.getEstadoMulta()){
                                            multa.setIdMulta(multa.getIdMulta()+1);
                                            vistaApp.verMultas(multa, prestamo, persona, libro, estado);
                                            vistaApp.setTexto("Se motraron " + arrayListMulta.size() + " multas.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } 
        }
    }

    //con DAO
    public void buscarMulta(ArrayList<Multa> arrayListMulta, ArrayList<Persona> arrayListPersona, ArrayList<Prestamos> arrayListPrestamos, ArrayList<Libro> arrayListLibro, ArrayList<Estado> arrayListEstado){
        
        vistaApp.getEntrada("");
        int documento = vistaApp.getEntradaInt("Ingrese el documento: ");
        
        for(Multa multa : arrayListMulta){
            for(Prestamos prestamo : arrayListPrestamos){
                if(prestamo.getIdPrestamo() == multa.getIdPrestamo()){
                    for(Persona persona : arrayListPersona){
                        if(documento == multa.getDocumentoPersona() && documento == persona.getDocumentoPersona()){
                            for(Libro libro : arrayListLibro){
                                if(libro.getIsbnLibro().equals(prestamo.getIsbnLibro())){                                    
                                    for(Estado estado : arrayListEstado){                                        
                                        if(estado.getIdEstado() == multa.getEstadoMulta()){
                                            multa.setIdMulta(multa.getIdMulta()+1);
                                            vistaApp.verMultas(multa, prestamo, persona, libro, estado);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } 
        }
    }

    //con DAO
    public void eliminarMulta(){
        int id = vistaApp.getEntradaInt("Ingrese el id de la multa: ");
        
        iDaoMulta.eliminarMulta(id);
    }
    
    //metodos no funcionales
    
    
    //con DAO
    public void mostrarCarreras(ArrayList<Carrera> arrayListCarrera){
        for(Carrera carrera : arrayListCarrera){
            vistaApp.setTexto(carrera.getIdCarrera() + " " + carrera.getCarrera());
        }
    }
    
    //con DAO
    public void crearMultas(ArrayList<Multa> arrayListMulta, ArrayList<Prestamos> arrayListPrestamos){
        
        ArrayList<Multa> arrayListMultas = arrayListMulta;

        for(Prestamos prestamo : arrayListPrestamos){
            if(prestamo.getIdEstado() == 3 || prestamo.getIdEstado() == 4){
                if(prestamo.getFechaEntrega().before(Date.valueOf(LocalDate.now()))){
                    
                    Multa multa = new Multa();
                    
                    multa.setIdMulta(arrayListMulta.size());
                    multa.setDocumentoPersona(prestamo.getIdPersona());
                    multa.setIdPrestamo(prestamo.getIdPrestamo());
                    multa.setValorMulta(30000);
                    multa.setEstadoMulta(6);

                    iDaoMulta.hacerMultas(multa);
                }
            }
        }
        
        /*for(Multa multa2 : arrayListMulta){
            vistaApp.vermult(multa2);
        }*/
        
    }
    
    
}
