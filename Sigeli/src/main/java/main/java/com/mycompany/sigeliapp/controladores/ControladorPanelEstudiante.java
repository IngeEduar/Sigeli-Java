package main.java.com.mycompany.sigeliapp.controladores;
    
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import main.java.com.mycompany.sigeliapp.dao.DaoCarrera;
import main.java.com.mycompany.sigeliapp.dao.DaoCategoria;
import main.java.com.mycompany.sigeliapp.dao.DaoCategoriaLibro;
import main.java.com.mycompany.sigeliapp.dao.DaoLibro;
import main.java.com.mycompany.sigeliapp.dao.DaoMulta;
import main.java.com.mycompany.sigeliapp.dao.DaoPersona;
import main.java.com.mycompany.sigeliapp.dao.DaoUsuario;
import main.java.com.mycompany.sigeliapp.dao.IDaoCarrera;
import main.java.com.mycompany.sigeliapp.dao.IDaoCategoria;
import main.java.com.mycompany.sigeliapp.dao.IDaoCategoriaLibro;
import main.java.com.mycompany.sigeliapp.dao.IDaoLibro;
import main.java.com.mycompany.sigeliapp.dao.IDaoMulta;
import main.java.com.mycompany.sigeliapp.dao.IDaoPersona;
import main.java.com.mycompany.sigeliapp.dao.IDaoUsuario;
import main.java.com.mycompany.sigeliapp.modelos.*;
import main.java.com.mycompany.sigeliapp.modelos.utiles.Portapapeles;
import main.java.com.mycompany.sigeliapp.vistas.*;

public class ControladorPanelEstudiante implements ActionListener, MouseListener{
    
    private PanelEstudiante panelEstudiante;
    private Configuracion panelConfiguracion;
    
    
    private int documentoLogin;
    private IDaoLibro iDaoLibro;
    private IDaoPersona iDaoPersona;
    private IDaoCarrera iDaoCarrera;
    private IDaoUsuario iDaoUsuario;
    private IDaoMulta iDaoMulta;
    private IDaoCategoria iDaoCategoria;
    private IDaoCategoriaLibro iDaoCategoriaLibro;
    private String nombre;
    private boolean busqueda = false;
    private int cantMenorLibro = 0, cantMayorLibro = 4, cantLibrosPag = 0;

    public ControladorPanelEstudiante() {
        
        this.panelEstudiante = new PanelEstudiante();
        this.panelConfiguracion = new Configuracion();
        
        this.iDaoLibro = new DaoLibro();
        this.iDaoPersona = new DaoPersona();
        this.iDaoCarrera = new DaoCarrera();
        this.iDaoUsuario = new DaoUsuario();
        this.iDaoMulta = new DaoMulta();
        this.iDaoCategoria = new DaoCategoria();
        this.iDaoCategoriaLibro = new DaoCategoriaLibro();
        
        this.panelEstudiante.backPagAdmin.addActionListener(this);
        this.panelEstudiante.nextPagAdmin.addActionListener(this);
        this.panelEstudiante.btnPegar.addActionListener(this);
        this.panelEstudiante.btnBusqueda.addActionListener(this);
        this.panelEstudiante.btnCerrarSesion.addMouseListener(this);
        this.panelEstudiante.btnOpcionesPanelAdmin.addMouseListener(this);

        
        this.panelConfiguracion.btnVolver.addMouseListener(this);
        this.panelConfiguracion.btnRegistro.addActionListener(this);
        this.panelConfiguracion.btnCancelar.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == panelEstudiante.btnBusqueda){
            busquedaLibro(iDaoLibro.verLibros(), iDaoCategoriaLibro.verCategoriaLibros(), iDaoCategoria.verCategorias());
        }
        else if(e.getSource() == panelEstudiante.btnPegar){
            try {
                panelEstudiante.txtIngresoBusqueda.setText(Portapapeles.get());
            } catch (Exception ex) {
                Logger.getLogger(ControladorLibro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(e.getSource() == panelEstudiante.nextPagAdmin){
             cantMenorLibro = cantMayorLibro;
             cantMayorLibro +=4;
             verLibros(iDaoLibro.verLibros());
        }
        else if(e.getSource() == panelEstudiante.backPagAdmin){
             
             cantMayorLibro = cantMenorLibro;
             
            if(cantMayorLibro < 4){
                cantMayorLibro = 4;
            }

            cantMenorLibro -=4;
            
           if(cantMenorLibro < 0 && cantMenorLibro > 4){
                cantMenorLibro = 0;
            }

           verLibros(iDaoLibro.verLibros());
           
        }
        
        else if(e.getSource() == panelConfiguracion.btnCancelar){
            cerrarPanelConfiguracion();
            visiblePanelEstudiante();
        }
         
        else if(e.getSource() == panelConfiguracion.btnRegistro){
            if(panelConfiguracion.txtClaveRegistro.getText().equals(panelConfiguracion.txtClaveRegistro1.getText())){
                Usuario usuario = new Usuario();
                usuario.setDocumento(documentoLogin);
                usuario.setClave(panelConfiguracion.txtClaveRegistro.getText());
                if(iDaoUsuario.cambioClave(usuario)){
                    JOptionPane.showMessageDialog(null, "Clave cambiada con éxito");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                }
                cerrarPanelEstudiante();
                visibleLogin();
            }
            else{
                JOptionPane.showMessageDialog(null, "Las claves ingresadas no coinciden");
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == panelEstudiante.btnOpcionesPanelAdmin){
            cerrarPanelEstudiante();
            //controladorApp.opcionesCuenta(iDaoPersona.verPersonas(), iDaoUsuario.verUsuarios(), documentoLogin, iDaoCarrera.verCarreras());
            setDatosConfig(documentoLogin);
            limpiarDatosConfiguracion();
            visiblePanelConfiguracion();
        }
        
        else if(e.getSource() == panelConfiguracion.btnVolver){
            cerrarPanelConfiguracion();
            visiblePanelEstudiante();
        }
        
        else if(e.getSource() == panelEstudiante.btnCerrarSesion){
            cerrarPanelEstudiante();
            visibleLogin();
        }
        
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
    
    public void busquedaLibro(ArrayList<Libro> arrayListLibro, ArrayList<CategoriaLibro> arrayListCategoriaLibro, ArrayList<Categoria> arrayListCategoria) {

        ArrayList<Libro> arrayListLibroEnviar = new ArrayList<>();
        String busqueda = panelEstudiante.txtIngresoBusqueda.getText();

        Pattern pat = Pattern.compile(".*" + busqueda.toLowerCase() + ".*");
        
        if(busqueda.equals("")){
            cantMayorLibro =4;
            cantMenorLibro = 0;
            this.busqueda = false;
            verLibros(iDaoLibro.verLibros());  
        }
        else{
            cantMayorLibro =4;
            cantMenorLibro = 0;
            for(Libro libro : arrayListLibro){
                for( Categoria categoria: arrayListCategoria){
                    Matcher mat5 = pat.matcher(categoria.getNombreCategoria().toLowerCase());
                    if(mat5.matches()){
                        for(CategoriaLibro categoriaLibro : arrayListCategoriaLibro){
                            if(libro.getIsbnLibro().equals(categoriaLibro.getIsbnLibro()) && categoriaLibro.getIdCategoria() == categoria.getIdCategoria()){
                                if(arrayListLibroEnviar.size() <= 4){
                                    arrayListLibroEnviar.add(libro);
                                }    
                            }
                        }
                    }
                }
                Matcher mat = pat.matcher(libro.getNombreLibro().toLowerCase());
                Matcher mat2 = pat.matcher(libro.getNombreAutor().toLowerCase());
                Matcher mat3 = pat.matcher(libro.getIsbnLibro());
                Matcher mat4 = pat.matcher(String.valueOf(libro.getAnoLibro()));
                if(mat.matches() || mat2.matches() || mat3.matches() || mat4.matches()){
                    if(arrayListLibroEnviar.size() <= 4){
                        arrayListLibroEnviar.add(libro);
                    }  
                }
            }
            this.busqueda = true;
            setDatosLibro(arrayListLibroEnviar, arrayListLibro.size(), busqueda);
        }

    }


    //con DAO
    public void verLibros(ArrayList<Libro> arrayListLibro) {
        
        ArrayList<Libro> arrayListLibroEnviar = new ArrayList<>();
        int cant = cantMenorLibro, cantLibros= 0;


        for(Libro libro : arrayListLibro){

            if(cantMayorLibro > arrayListLibro.size()){
            cantMayorLibro = arrayListLibro.size();
            }
            if(cantMenorLibro < 0){
                cantMenorLibro = 0;
            }
            if(cantLibros <= cantMayorLibro  && cantLibros >= cantMenorLibro){
                arrayListLibroEnviar.add(libro);
                cantMenorLibro +=1;
            }
            cantLibros +=1;

        }
        
        cantMenorLibro = cant;
        
        setDatosLibro(arrayListLibroEnviar, arrayListLibro.size(), "");
        this.busqueda = false;
        

    }
    
    public void multasUsuario(){
        

        for(Multa multa: iDaoMulta.verMultas()){
            if(multa.getDocumentoPersona() == documentoLogin){
                if(multa.getEstadoMulta() == 6){
                    panelEstudiante.txtNotificaciones.setText("Usted tiene una multa \npediente por pagar.\nEsta se generó por \nel prestamo número: " + 
                                                           multa.getIdPrestamo() + " \ny tiene un valor de: " + multa.getValorMulta());
                    
                    panelEstudiante.iconNotificacion.setIcon(new javax.swing.ImageIcon("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\iconos\\iconoNotificacionOn.png"));
                
                }
            }
        }
        
        
    }
    
    

    public void inicio(int documento, ArrayList<Persona> arrayListPersona){
        this.documentoLogin = documento;
        for(Persona persona : arrayListPersona){
            if(persona.getDocumentoPersona() == documento){
                this.nombre = persona.getNombrePersona().toUpperCase();
                panelEstudiante.txtNombrePersona.setText(nombre);
            }
        }
        verLibros(iDaoLibro.verLibros());
        panelEstudiante.txtNotificaciones.setText("No tienes notificaciones");
        multasUsuario();
        visiblePanelEstudiante();
    }
    
    public void visibleLogin(){
        ControladorLogin controladorLogin = new ControladorLogin();
        controladorLogin.visibleLogin();
    }
    
    
    public void visiblePanelEstudiante(){
        panelEstudiante.setTitle("Panel Estudiante - Sigeli");
        panelEstudiante.setLocationRelativeTo(null);
        panelEstudiante.setResizable(false);
        panelEstudiante.setVisible(true);
    }
    
    public void cerrarPanelEstudiante(){
        panelEstudiante.setVisible(false);
    }
    
        
    public void visiblePanelConfiguracion(){
        panelConfiguracion.setTitle("Panel Configuración - Sigeli");
        panelConfiguracion.setLocationRelativeTo(null);
        panelConfiguracion.setResizable(false);
        panelConfiguracion.setVisible(true);
    }
    
    public void cerrarPanelConfiguracion(){
        panelConfiguracion.setVisible(false);
    }
    
    public void limpiarDatosConfiguracion(){
        panelConfiguracion.txtClaveRegistro.setText("");
        panelConfiguracion.txtClaveRegistro1.setText("");
    }
    
    public void setDatosConfig(int documento){
        for(Persona persona : iDaoPersona.verPersonas()){
            if(persona.getDocumentoPersona() == documento){
                panelConfiguracion.txtNombreRegistro.setText(persona.getNombrePersona());
                panelConfiguracion.txtEmailRegistro.setText(persona.getEmailPersona());
                panelConfiguracion.txtTelefonoRegistro.setText(persona.getTelefono());
                panelConfiguracion.txtDocumentoRegistro.setText(String.valueOf(persona.getDocumentoPersona()));
                
                for(Carrera carrera : iDaoCarrera.verCarreras()){
                    if(persona.getIdCarrera() == 0){
                        panelConfiguracion.txtcarrera.setVisible(false);
                        panelConfiguracion.carrera.setVisible(false);
                    }
                    else{
                        panelConfiguracion.txtcarrera.setVisible(true);
                        if(persona.getIdCarrera() == carrera.getIdCarrera()){
                            panelConfiguracion.txtcarrera.setText(carrera.getCarrera());
                        }
                        panelConfiguracion.carrera.setVisible(true);
                    }
                }
            }
        }
    }
    
    public void setDatosLibro(ArrayList<Libro> arrayListLibro, int cantLibros, String busqueda){
        
        if(this.busqueda){
            panelEstudiante.nextPagAdmin.setVisible(false);
            panelEstudiante.backPagAdmin.setVisible(false);
        }
        else{
            panelEstudiante.nextPagAdmin.setVisible(true);
            panelEstudiante.backPagAdmin.setVisible(true);
        }
        
        if(arrayListLibro.size() == 0){
           panelEstudiante.panel1.setVisible(false);
           panelEstudiante.panel2.setVisible(false);
           panelEstudiante.panel3.setVisible(false);
           panelEstudiante.panel4.setVisible(false);
           panelEstudiante.panel5.setVisible(false);
           busqueda += "\" - No se han encontrado resultados";
        }
        else if(arrayListLibro.size() == 1){
            panelEstudiante.nextPagAdmin.setVisible(false);
            panelEstudiante.backPagAdmin.setVisible(false);
            //paneles
           panelEstudiante.panel1.setVisible(true);
           panelEstudiante.panel2.setVisible(false);
           panelEstudiante.panel3.setVisible(false);
           panelEstudiante.panel4.setVisible(false);
           panelEstudiante.panel5.setVisible(false);
           //nombre libro
           panelEstudiante.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           //nombre Autor
           panelEstudiante.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           //año del libro
           panelEstudiante.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           //diponibilidad
           panelEstudiante.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No dispinoble"));
           //Estante
           panelEstudiante.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           //Fila
           panelEstudiante.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
        }
        else if(arrayListLibro.size() == 2){
            panelEstudiante.nextPagAdmin.setVisible(false);
            panelEstudiante.backPagAdmin.setVisible(false);
            //paneles
           panelEstudiante.panel1.setVisible(true);
           panelEstudiante.panel2.setVisible(true);
           panelEstudiante.panel3.setVisible(false);
           panelEstudiante.panel4.setVisible(false);
           panelEstudiante.panel5.setVisible(false);
           //nombre libro
           panelEstudiante.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           panelEstudiante.txtNombreLibro2.setText(arrayListLibro.get(1).getNombreLibro());
           //nombre Autor
           panelEstudiante.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           panelEstudiante.txtNombreAutor2.setText("Autor: " +arrayListLibro.get(1).getNombreAutor());
           //año del libro
           panelEstudiante.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           panelEstudiante.txtAnoLibro2.setText("Año: " + String.valueOf(arrayListLibro.get(1).getAnoLibro()));
           //diponibilidad
           panelEstudiante.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro2.setText("Estado: " + ((arrayListLibro.get(1).getIdEstado() == 1)? "Disponible":"No disponible"));
           //Estante
           panelEstudiante.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           panelEstudiante.txtEstanteLibro2.setText("Estante: " + arrayListLibro.get(1).getEstanteLibro());
           //Fila
           panelEstudiante.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
           panelEstudiante.txtFilaLibro2.setText("Fila: " + String.valueOf(arrayListLibro.get(1).getFilaLibro()));
        }
        else if(arrayListLibro.size() == 3){
            panelEstudiante.nextPagAdmin.setVisible(false);
            panelEstudiante.backPagAdmin.setVisible(false);
            //paneles
           panelEstudiante.panel1.setVisible(true);
           panelEstudiante.panel2.setVisible(true);
           panelEstudiante.panel3.setVisible(true);
           panelEstudiante.panel4.setVisible(false);
           panelEstudiante.panel5.setVisible(false);
           //nombre libro
           panelEstudiante.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           panelEstudiante.txtNombreLibro2.setText(arrayListLibro.get(1).getNombreLibro());
           panelEstudiante.txtNombreLibro3.setText(arrayListLibro.get(2).getNombreLibro());
           //nombre Autor
           panelEstudiante.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           panelEstudiante.txtNombreAutor2.setText("Autor: " +arrayListLibro.get(1).getNombreAutor());
           panelEstudiante.txtNombreAutor3.setText("Autor: " +arrayListLibro.get(2).getNombreAutor());
           //año del libro
           panelEstudiante.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           panelEstudiante.txtAnoLibro2.setText("Año: " + String.valueOf(arrayListLibro.get(1).getAnoLibro()));
           panelEstudiante.txtAnoLibro3.setText("Año: " + String.valueOf(arrayListLibro.get(2).getAnoLibro()));
           //diponibilidad
           panelEstudiante.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro2.setText("Estado: " + ((arrayListLibro.get(1).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro3.setText("Estado: " + ((arrayListLibro.get(2).getIdEstado() == 1)? "Disponible":"No disponible"));
           //Estante
           panelEstudiante.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           panelEstudiante.txtEstanteLibro2.setText("Estante: " + arrayListLibro.get(1).getEstanteLibro());
           panelEstudiante.txtEstanteLibro3.setText("Estante: " + arrayListLibro.get(2).getEstanteLibro());
           //Fila
           panelEstudiante.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
           panelEstudiante.txtFilaLibro2.setText("Fila: " + String.valueOf(arrayListLibro.get(1).getFilaLibro()));
           panelEstudiante.txtFilaLibro3.setText("Fila: " + String.valueOf(arrayListLibro.get(2).getFilaLibro()));
        }
        else if(arrayListLibro.size() == 4){
            panelEstudiante.nextPagAdmin.setVisible(false);
            panelEstudiante.backPagAdmin.setVisible(false);
            //paneles
           panelEstudiante.panel1.setVisible(true);
           panelEstudiante.panel2.setVisible(true);
           panelEstudiante.panel3.setVisible(true);
           panelEstudiante.panel4.setVisible(true);
           panelEstudiante.panel5.setVisible(false);
           //nombre libro
           panelEstudiante.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           panelEstudiante.txtNombreLibro2.setText(arrayListLibro.get(1).getNombreLibro());
           panelEstudiante.txtNombreLibro3.setText(arrayListLibro.get(2).getNombreLibro());
           panelEstudiante.txtNombreLibro4.setText(arrayListLibro.get(3).getNombreLibro());
           //nombre Autor
           panelEstudiante.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           panelEstudiante.txtNombreAutor2.setText("Autor: " +arrayListLibro.get(1).getNombreAutor());
           panelEstudiante.txtNombreAutor3.setText("Autor: " +arrayListLibro.get(2).getNombreAutor());
           panelEstudiante.txtNombreAutor4.setText("Autor: " +arrayListLibro.get(3).getNombreAutor());
           //año del libro
           panelEstudiante.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           panelEstudiante.txtAnoLibro2.setText("Año: " + String.valueOf(arrayListLibro.get(1).getAnoLibro()));
           panelEstudiante.txtAnoLibro3.setText("Año: " + String.valueOf(arrayListLibro.get(2).getAnoLibro()));
           panelEstudiante.txtAnoLibro4.setText("Año: " + String.valueOf(arrayListLibro.get(3).getAnoLibro()));
           //diponibilidad
           panelEstudiante.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro2.setText("Estado: " + ((arrayListLibro.get(1).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro3.setText("Estado: " + ((arrayListLibro.get(2).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro4.setText("Estado: " + ((arrayListLibro.get(3).getIdEstado() == 1)? "Disponible":"No disponible"));
           //Estante
           panelEstudiante.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           panelEstudiante.txtEstanteLibro2.setText("Estante: " + arrayListLibro.get(1).getEstanteLibro());
           panelEstudiante.txtEstanteLibro3.setText("Estante: " + arrayListLibro.get(2).getEstanteLibro());
           panelEstudiante.txtEstanteLibro4.setText("Estante: " + arrayListLibro.get(3).getEstanteLibro());
           //Fila
           panelEstudiante.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
           panelEstudiante.txtFilaLibro2.setText("Fila: " + String.valueOf(arrayListLibro.get(1).getFilaLibro()));
           panelEstudiante.txtFilaLibro3.setText("Fila: " + String.valueOf(arrayListLibro.get(2).getFilaLibro()));
           panelEstudiante.txtFilaLibro4.setText("Fila: " + String.valueOf(arrayListLibro.get(3).getFilaLibro()));
        }
        else if(arrayListLibro.size() == 5){
            panelEstudiante.nextPagAdmin.setVisible(true);
            if(this.busqueda){
                panelEstudiante.nextPagAdmin.setVisible(false);
                panelEstudiante.backPagAdmin.setVisible(false);
            }
            //paneles
           panelEstudiante.panel1.setVisible(true);
           panelEstudiante.panel2.setVisible(true);
           panelEstudiante.panel3.setVisible(true);
           panelEstudiante.panel4.setVisible(true);
           panelEstudiante.panel5.setVisible(true);
           //nombre libro
           panelEstudiante.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           panelEstudiante.txtNombreLibro2.setText(arrayListLibro.get(1).getNombreLibro());
           panelEstudiante.txtNombreLibro3.setText(arrayListLibro.get(2).getNombreLibro());
           panelEstudiante.txtNombreLibro4.setText(arrayListLibro.get(3).getNombreLibro());
           panelEstudiante.txtNombreLibro5.setText(arrayListLibro.get(4).getNombreLibro());
           //nombre Autor
           panelEstudiante.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           panelEstudiante.txtNombreAutor2.setText("Autor: " +arrayListLibro.get(1).getNombreAutor());
           panelEstudiante.txtNombreAutor3.setText("Autor: " +arrayListLibro.get(2).getNombreAutor());
           panelEstudiante.txtNombreAutor4.setText("Autor: " +arrayListLibro.get(3).getNombreAutor());
           panelEstudiante.txtNombreAutor5.setText("Autor: " +arrayListLibro.get(4).getNombreAutor());
           //año del libro
           panelEstudiante.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           panelEstudiante.txtAnoLibro2.setText("Año: " + String.valueOf(arrayListLibro.get(1).getAnoLibro()));
           panelEstudiante.txtAnoLibro3.setText("Año: " + String.valueOf(arrayListLibro.get(2).getAnoLibro()));
           panelEstudiante.txtAnoLibro4.setText("Año: " + String.valueOf(arrayListLibro.get(3).getAnoLibro()));
           panelEstudiante.txtAnoLibro5.setText("Año: " + String.valueOf(arrayListLibro.get(4).getAnoLibro()));
           //diponibilidad
           panelEstudiante.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro2.setText("Estado: " + ((arrayListLibro.get(1).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro3.setText("Estado: " + ((arrayListLibro.get(2).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro4.setText("Estado: " + ((arrayListLibro.get(3).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelEstudiante.txtDisponibilidadLibro5.setText("Estado: " + ((arrayListLibro.get(4).getIdEstado() == 1)? "Disponible":"No disponible"));
           //Estante
           panelEstudiante.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           panelEstudiante.txtEstanteLibro2.setText("Estante: " + arrayListLibro.get(1).getEstanteLibro());
           panelEstudiante.txtEstanteLibro3.setText("Estante: " + arrayListLibro.get(2).getEstanteLibro());
           panelEstudiante.txtEstanteLibro4.setText("Estante: " + arrayListLibro.get(3).getEstanteLibro());
           panelEstudiante.txtEstanteLibro5.setText("Estante: " + arrayListLibro.get(4).getEstanteLibro());
           //Fila
           panelEstudiante.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
           panelEstudiante.txtFilaLibro2.setText("Fila: " + String.valueOf(arrayListLibro.get(1).getFilaLibro()));
           panelEstudiante.txtFilaLibro3.setText("Fila: " + String.valueOf(arrayListLibro.get(2).getFilaLibro()));
           panelEstudiante.txtFilaLibro4.setText("Fila: " + String.valueOf(arrayListLibro.get(3).getFilaLibro()));
           panelEstudiante.txtFilaLibro5.setText("Fila: " + String.valueOf(arrayListLibro.get(4).getFilaLibro()));
        }
        else{
            panelEstudiante.txtNombreLibro1.setText("");
            panelEstudiante.txtNombreLibro2.setText("");
            panelEstudiante.txtNombreLibro3.setText("");
            panelEstudiante.txtNombreLibro4.setText("");
            panelEstudiante.txtNombreLibro5.setText("");
        }
        
        if(!busqueda.equals("")){
            panelEstudiante.txtBusquedaAdmin.setText("Se muestran resultados para \"" + busqueda + "\"");
        }
        else{
            panelEstudiante.txtBusquedaAdmin.setText("");
        }
        cantLibrosPag = arrayListLibro.size();
        panelEstudiante.txtCantLibroAdmin.setText("Se mostraron " + arrayListLibro.size() + " libros de: " + cantLibros);
        
    }
    
    
}

