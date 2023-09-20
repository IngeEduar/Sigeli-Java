package main.java.com.mycompany.sigeliapp.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import main.java.com.mycompany.sigeliapp.vistas.*;
import main.java.com.mycompany.sigeliapp.modelos.*;
import main.java.com.mycompany.sigeliapp.dao.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import main.java.com.mycompany.sigeliapp.modelos.utiles.Portapapeles;


public class ControladorPanelAdmin implements ActionListener, MouseListener{
    
    private panelAdministrador vistaAdministrador;
    private Configuracion panelConfiguracion;
    //private ControladorApp controladorApp;
    
    private ControladorLibro controladorLibro;
    ControladorPersona controladorPersona;
    
    private int documentoLogin;
    private String nombre;
    private boolean panelConfig = false;
    
    private IDaoPersona iDaoPersona;
    private IDaoUsuario iDaoUsuario;
    private IDaoLibro iDaoLibro;
    private IDaoEstado iDaoEstado;
    private IDaoCarrera iDaoCarrera;

    public ControladorPanelAdmin() {
        this.vistaAdministrador = new panelAdministrador();
        this.panelConfiguracion = new Configuracion();
        //this.controladorApp = new ControladorApp();
        
        this.controladorLibro = new ControladorLibro();
        this.controladorPersona = new ControladorPersona();
        
        this.iDaoPersona = new DaoPersona();
        this.iDaoUsuario = new DaoUsuario();
        this.iDaoLibro = new DaoLibro();
        this.iDaoEstado = new DaoEstado();
        this.iDaoCarrera = new DaoCarrera();
        
        this.vistaAdministrador.btnAddLibros.addMouseListener(this);
        this.vistaAdministrador.btnAdminLibros.addMouseListener(this);
        this.vistaAdministrador.btnAdmnUsuario1.addMouseListener(this);
        this.vistaAdministrador.btnDeudas.addMouseListener(this);
        this.vistaAdministrador.btnOpcionesPanelAdmin.addMouseListener(this);
        this.vistaAdministrador.btnPrestamos.addMouseListener(this);
        this.vistaAdministrador.btnReportes.addMouseListener(this);
        this.vistaAdministrador.opcion1Config.addMouseListener(this);
        this.vistaAdministrador.opcion2Config.addMouseListener(this);
        
        this.panelConfiguracion.btnVolver.addMouseListener(this);
        this.panelConfiguracion.btnRegistro.addActionListener(this);
        this.panelConfiguracion.btnCancelar.addActionListener(this);
    }
    
     public void actionPerformed(ActionEvent e){
         
        if(e.getSource() == panelConfiguracion.btnCancelar){
            cerrarPanelConfiguracion();
            visiblePanelAdmin();
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
                cerrarPanelAdmin();
                visibleLogin();
            }
            else{
                JOptionPane.showMessageDialog(null, "Las claves ingresadas no coinciden");
            }
        }
     }

    @Override
    public void mouseClicked(MouseEvent e) {
        
       if(e.getSource() == vistaAdministrador.btnAdminLibros){
            cerrarPanelAdmin();
            controladorLibro.inicio(documentoLogin, nombre);
        }

        else if(e.getSource() == vistaAdministrador.btnAdmnUsuario1){
            cerrarPanelAdmin();
            //controladorApp.administrarUsuario(iDaoPersona.verPersonas());
            controladorPersona.inicio(documentoLogin, nombre);
        }

        else if(e.getSource() == vistaAdministrador.btnAddLibros){
            cerrarPanelAdmin();
            //controladorApp.addLibros(iDaoLibro.verLibros(), iDaoEstado.verEstados());
            controladorLibro.inicioAddLibros(documentoLogin, nombre);
        }

        else if(e.getSource() == vistaAdministrador.btnPrestamos){
            cerrarPanelAdmin();
            //controladorApp.prestamoLibros();
            ControladorPrestamos controladorPrestamos = new ControladorPrestamos();
            
            controladorPrestamos.inicio(documentoLogin, nombre);
        }

        else if(e.getSource() == vistaAdministrador.btnDeudas){
            cerrarPanelAdmin();
            //controladorApp.adminMultas();
            ControladorMulta controladorMulta = new ControladorMulta();
            controladorMulta.inicio(documentoLogin, nombre);
        }

        else if(e.getSource() == vistaAdministrador.btnReportes){
            cerrarPanelAdmin();
            //controladorApp.reportes();
            ControladorReportes controladorReportes = new ControladorReportes();
            controladorReportes.inicio(documentoLogin, nombre);
        }

        else if(e.getSource() == vistaAdministrador.btnOpcionesPanelAdmin){
            if(panelConfig == true){
                vistaAdministrador.panelConfig.setVisible(false);
                vistaAdministrador.opcion1Config.setVisible(false);
                vistaAdministrador.opcion2Config.setVisible(false);
                vistaAdministrador.separadorCofing.setVisible(false);
                panelConfig = false;
            }
            else if(panelConfig == false){
                vistaAdministrador.panelConfig.setVisible(true);
                vistaAdministrador.opcion1Config.setVisible(true);
                vistaAdministrador.opcion2Config.setVisible(true);
                vistaAdministrador.separadorCofing.setVisible(true);
                panelConfig = true;
            }
        }
        
        else if(e.getSource() == vistaAdministrador.opcion1Config){
            cerrarPanelAdmin();
            //controladorApp.opcionesCuenta(iDaoPersona.verPersonas(), iDaoUsuario.verUsuarios(), documentoLogin, iDaoCarrera.verCarreras());
            setDatosConfig(documentoLogin);
            limpiarDatosConfiguracion();
            vistaAdministrador.panelConfig.setVisible(false);
            visiblePanelConfiguracion();
            
        }
       
        else if(e.getSource() == vistaAdministrador.opcion2Config){
            cerrarPanelAdmin();
            visibleLogin();
        }
       
       
       
        else if(e.getSource() == panelConfiguracion.btnVolver){
            cerrarPanelConfiguracion();
            visiblePanelAdmin();
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
     
    public void inicio(int documento, ArrayList<Persona> arrayListPersona){
        this.documentoLogin = documento;
        for(Persona persona : arrayListPersona){
            if(persona.getDocumentoPersona() == documento){
                this.nombre = persona.getNombrePersona().toUpperCase();
                vistaAdministrador.txtNombrePersona.setText(nombre);
                vistaAdministrador.txtCargo.setText("Administrador");
            }
        }
        
        vistaAdministrador.panelConfig.setVisible(false);
        vistaAdministrador.opcion1Config.setVisible(false);
        vistaAdministrador.opcion2Config.setVisible(false);
        vistaAdministrador.separadorCofing.setVisible(false);
        
        visiblePanelAdmin();
    }
    
    
     public void cerrarPanelAdmin(){
        vistaAdministrador.setVisible(false);
    }

    public void visiblePanelAdmin(){
        vistaAdministrador.setTitle("Panel Administrador - Sigeli");
        vistaAdministrador.setLocationRelativeTo(null);
        vistaAdministrador.setResizable(false);
        vistaAdministrador.setVisible(true);
    }
    
    public void visibleLogin(){
        ControladorLogin controladorLogin = new ControladorLogin();
        controladorLogin.visibleLogin();
    }
    
    public void visiblePanelConfiguracion(){
        panelConfiguracion.setTitle("Panel Configuración - Sigeli");
        panelConfiguracion.setResizable(false);
        panelConfiguracion.setLocationRelativeTo(null);
        panelConfiguracion.setVisible(true);
    }
    
    public void cerrarPanelConfiguracion(){
        panelConfiguracion.setVisible(false);
    }
    
    public void limpiarDatosConfiguracion(){
        panelConfiguracion.txtClaveRegistro.setText("");
        panelConfiguracion.txtClaveRegistro1.setText("");
    }
    
}
