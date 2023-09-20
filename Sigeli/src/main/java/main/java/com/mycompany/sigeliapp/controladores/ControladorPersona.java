/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import main.java.com.mycompany.sigeliapp.dao.DaoPersona;
import main.java.com.mycompany.sigeliapp.dao.DaoUsuario;
import main.java.com.mycompany.sigeliapp.dao.IDaoCarrera;
import main.java.com.mycompany.sigeliapp.dao.IDaoPersona;
import main.java.com.mycompany.sigeliapp.dao.IDaoUsuario;
import main.java.com.mycompany.sigeliapp.modelos.Carrera;
import main.java.com.mycompany.sigeliapp.modelos.Persona;
import main.java.com.mycompany.sigeliapp.modelos.utiles.Portapapeles;
import main.java.com.mycompany.sigeliapp.vistas.PanelVistaPersona;
import main.java.com.mycompany.sigeliapp.vistas.panelAdminUsuarios;

/**
 *
 * @author Eduar Xavier
 */
public class ControladorPersona implements ActionListener, MouseListener{

    private panelAdminUsuarios panelAdminUsuario;
    private PanelVistaPersona panelVerPersona;

    private IDaoPersona iDaoPersona;
    private IDaoCarrera iDaoCarrera;
    private IDaoUsuario iDaoUsuario;
    
    private int documentoLogin;
    private String nombre, nombreCorto;
    private boolean busqueda = false;
    private int cantMenorPersona = 0, cantMayorPersona = 4, cantLibrosPag = 0;

    
    public ControladorPersona() {
        this.panelAdminUsuario = new panelAdminUsuarios();
        this.panelVerPersona = new PanelVistaPersona();
        

        this.iDaoPersona = new DaoPersona();
        this.iDaoCarrera = new DaoCarrera();
        this.iDaoUsuario = new DaoUsuario();
        
        //Acciones del panel admin Usuarios
        this.panelAdminUsuario.btnPegar.addActionListener(this);
        this.panelAdminUsuario.btnBusqueda.addActionListener(this);
        this.panelAdminUsuario.nextPagAdmin.addActionListener(this);
        this.panelAdminUsuario.backPagAdmin.addActionListener(this);
        
        
        this.panelAdminUsuario.btnExtenPanel.addMouseListener(this);
        this.panelAdminUsuario.btnExtenPanelOff.addMouseListener(this);
        this.panelAdminUsuario.btnVolverMenu.addMouseListener(this);
        this.panelAdminUsuario.btnCerrarSesion.addMouseListener(this);
        
        this.panelAdminUsuario.panel1.addMouseListener(this);
        this.panelAdminUsuario.panel2.addMouseListener(this);
        this.panelAdminUsuario.panel3.addMouseListener(this);
        this.panelAdminUsuario.panel4.addMouseListener(this);
        this.panelAdminUsuario.panel5.addMouseListener(this);
        
        //Acciones del panel ver usuarios
        
        this.panelVerPersona.btnExtenPanel.addMouseListener(this);
        this.panelVerPersona.btnExtenPanelOff.addMouseListener(this);
        this.panelVerPersona.btnVolverMenu.addMouseListener(this);
        this.panelVerPersona.btnActualizarPersona.addMouseListener(this);
        this.panelVerPersona.btnEliminarPersona.addMouseListener(this);
        this.panelVerPersona.btnCerrarSesion.addMouseListener(this);
        this.panelVerPersona.txtDocumentoPersona.addMouseListener(this);
        
        //Acciones del panel extendido
        
        this.panelVerPersona.adminLibros.addMouseListener(this);
        this.panelVerPersona.adminUsuarios.addMouseListener(this);
        this.panelVerPersona.addLibros.addMouseListener(this);
        this.panelVerPersona.prestamos.addMouseListener(this);
        this.panelVerPersona.multas.addMouseListener(this);
        this.panelVerPersona.reportes.addMouseListener(this);
        
        this.panelAdminUsuario.adminLibros.addMouseListener(this);
        this.panelAdminUsuario.adminUsuarios.addMouseListener(this);
        this.panelAdminUsuario.addLibros.addMouseListener(this);
        this.panelAdminUsuario.prestamos.addMouseListener(this);
        this.panelAdminUsuario.multas.addMouseListener(this);
        this.panelAdminUsuario.reportes.addMouseListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == panelAdminUsuario.btnBusqueda){
            
            buscarUsuario(iDaoPersona.verPersonas());
            
        }
        else if(e.getSource() == panelAdminUsuario.btnPegar){
            try {
                panelAdminUsuario.txtIngresoBusqueda.setText(Portapapeles.get());
            } catch (Exception ex) {
                Logger.getLogger(ControladorLibro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(e.getSource() == panelAdminUsuario.nextPagAdmin){
             cantMenorPersona = cantMayorPersona;
             cantMayorPersona +=4;
             verPersonas(iDaoPersona.verPersonas());
        }
        else if(e.getSource() == panelAdminUsuario.backPagAdmin){
             
             cantMayorPersona = cantMenorPersona;
             
            if(cantMayorPersona < 4){
                cantMayorPersona = 4;
            }

            cantMenorPersona -=4;
            
           if(cantMenorPersona < 0 && cantMenorPersona > 4){
                cantMenorPersona = 0;
            }

           verPersonas(iDaoPersona.verPersonas());
           
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getSource() == panelAdminUsuario.btnExtenPanel){
            panelAdminUsuario.panelExten.setVisible(true);
            panelAdminUsuario.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelAdminUsuario.btnExtenPanelOff){
            panelAdminUsuario.btnVolverMenu.setVisible(true);
            panelAdminUsuario.panelExten.setVisible(false);
            
        }
        else if(e.getSource() == panelAdminUsuario.btnVolverMenu){
            cerrarPanelAdminUsuario();
            visiblePanelAdmin();
        }
        
        //Acciones del panel ver usuario
        
        else if(e.getSource() == panelAdminUsuario.panel1){
            verPersona(panelAdminUsuario.txtNombre1.getText());
            cerrarPanelAdminUsuario();
            visiblePanelVerPersona();
        }
        
        else if(e.getSource() == panelAdminUsuario.panel2){
            verPersona(panelAdminUsuario.txtNombre2.getText());
            cerrarPanelAdminUsuario();
            visiblePanelVerPersona();
        }
        
        else if(e.getSource() == panelAdminUsuario.panel3){
            verPersona(panelAdminUsuario.txtNombre3.getText());
            cerrarPanelAdminUsuario();
            visiblePanelVerPersona();
        }
        
        else if(e.getSource() == panelAdminUsuario.panel4){
            verPersona(panelAdminUsuario.txtNombre4.getText());
            cerrarPanelAdminUsuario();
            visiblePanelVerPersona();
        }
        
        else if(e.getSource() == panelAdminUsuario.panel5){
            verPersona(panelAdminUsuario.txtNombre5.getText());
            cerrarPanelAdminUsuario();
            visiblePanelVerPersona();
        }
        
        else if(e.getSource() == panelVerPersona.txtDocumentoPersona){
            Portapapeles.copiar(panelVerPersona.txtDocumentoPersona.getText());
            JOptionPane.showMessageDialog(null, "Documento copiado al portapapeles");
        }
        
        else if(e.getSource() == panelVerPersona.btnExtenPanel){
            panelVerPersona.panelExten.setVisible(true);
            panelVerPersona.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelVerPersona.btnExtenPanelOff){
            panelVerPersona.btnVolverMenu.setVisible(true);
            panelVerPersona.panelExten.setVisible(false);
            
        }
        else if(e.getSource() == panelVerPersona.btnVolverMenu){
            cerrarPanelVerPersona();
            visiblePanelAdminUsuario();
        }
        
        else if(e.getSource() == panelVerPersona.btnActualizarPersona){
            
            modificarPersona();
            
        }
        
        else if(e.getSource() == panelVerPersona.btnEliminarPersona){
            
            eliminarPersona();
                    
        }
        
        else if(e.getSource() == panelVerPersona.btnCerrarSesion || e.getSource() == panelAdminUsuario.btnCerrarSesion){
            ControladorLogin controladorLogin = new ControladorLogin();
            cerrarPanelAdminUsuario();
            cerrarPanelVerPersona();
            controladorLogin.visibleLogin();
        }
        
        
        //Opciones panel Extendido
        
        else if(e.getSource() == panelVerPersona.adminLibros || e.getSource() == panelAdminUsuario.adminLibros){
            cerrarPanelAdminUsuario();
            cerrarPanelVerPersona();
            visiblePanelAdminLibros();
        }
        
        else if(e.getSource() == panelVerPersona.adminUsuarios || e.getSource() == panelAdminUsuario.adminUsuarios){
            cerrarPanelAdminUsuario();
            cerrarPanelVerPersona();
            
            visiblePanelAdminUsuario();
            
        }
        
        else if(e.getSource() == panelVerPersona.addLibros || e.getSource() == panelAdminUsuario.addLibros){
            cerrarPanelAdminUsuario();
            cerrarPanelVerPersona();
            
            ControladorLibro controladorLibro = new ControladorLibro();
            
            controladorLibro.inicioAddLibros(documentoLogin, nombre);
        }
        
        //Sin controladores
        
        else if(e.getSource() == panelVerPersona.prestamos || e.getSource() == panelAdminUsuario.prestamos){
            cerrarPanelAdminUsuario();
            cerrarPanelVerPersona();
            
            ControladorPrestamos controladorPrestamos = new ControladorPrestamos();
            controladorPrestamos.inicio(documentoLogin, nombre);

        }
        
        else if(e.getSource() == panelVerPersona.multas || e.getSource() == panelAdminUsuario.multas){
            cerrarPanelAdminUsuario();
            cerrarPanelVerPersona();

            ControladorMulta controladorMulta = new ControladorMulta();
            controladorMulta.inicio(documentoLogin, nombre);
        }
        
        else if(e.getSource() == panelVerPersona.reportes || e.getSource() == panelAdminUsuario.reportes){
            cerrarPanelAdminUsuario();
            cerrarPanelVerPersona();
            
            ControladorReportes controladorReportes = new ControladorReportes();
            
            controladorReportes.inicio(documentoLogin, nombre);

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
    
    
    
    public void modificarPersona(){
        Persona persona = new Persona();
           
        persona.setDocumentoPersona(Integer.parseInt(panelVerPersona.txtDocumentoPersona.getText()));
        persona.setNombrePersona(panelVerPersona.txtNombre.getText());
        persona.setEmailPersona(panelVerPersona.txtEmail.getText());

        if(iDaoPersona.editarPersona(persona)){
            JOptionPane.showMessageDialog(null, "Persona editada con éxito");
            verPersonas(iDaoPersona.verPersonas());
            cerrarPanelVerPersona();
            visiblePanelAdminUsuario();
        }
        else{
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }
    }
    
    public void eliminarPersona(){
        
        if(iDaoPersona.eliminarPersona(Integer.parseInt(panelVerPersona.txtDocumentoPersona.getText()))){
            
                iDaoUsuario.eliminarUsuario(Integer.parseInt(panelVerPersona.txtDocumentoPersona.getText()));
                JOptionPane.showMessageDialog(null, "Persona eliminada con éxito");
                verPersonas(iDaoPersona.verPersonas());
                cerrarPanelVerPersona();
                visiblePanelAdminUsuario();
            }
            else{
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }
    }
    
    public void verPersonas(ArrayList<Persona> arrayListPersona) {
        
        ArrayList<Persona> arrayListPersonaEnviar = new ArrayList<>();
        int cant = cantMenorPersona, cantPersonas= 0;


        for(Persona persona : arrayListPersona){

            if(cantMayorPersona > arrayListPersona.size()){
            cantMayorPersona = arrayListPersona.size();
            }
            if(cantMenorPersona < 0){
                cantMenorPersona = 0;
            }
            if(cantPersonas <= cantMayorPersona  && cantPersonas >= cantMenorPersona){
                arrayListPersonaEnviar.add(persona);
                cantMenorPersona +=1;
            }
            cantPersonas +=1;

        }
        
        cantMenorPersona = cant;
        
        setDatosPersona(arrayListPersonaEnviar, arrayListPersonaEnviar.size(), "");
        this.busqueda = false;
        

    }
    
    public void buscarUsuario(ArrayList<Persona> arrayListPersona){
            
        String busqueda = panelAdminUsuario.txtIngresoBusqueda.getText();

        Pattern pat = Pattern.compile(".*" + busqueda.toLowerCase() + ".*");
        if(busqueda.equals("")){
            cantMayorPersona =4;
            cantMenorPersona = 0;
            this.busqueda = false;
            verPersonas(iDaoPersona.verPersonas());  
        }
        else{
           ArrayList<Persona> arrayListPersonaEnviar = new ArrayList<>();
        
            for(Persona persona : arrayListPersona){
                Matcher mat = pat.matcher(String.valueOf(persona.getDocumentoPersona()));
                Matcher mat2 = pat.matcher(persona.getNombrePersona().toLowerCase());
                if(mat.matches() || mat2.matches()){

                    if(arrayListPersona.size() <= 4){
                        arrayListPersonaEnviar.add(persona);
                    }

                }
            } 
            
            setDatosPersona(arrayListPersonaEnviar, arrayListPersonaEnviar.size(), busqueda);
        }
        
        
        
    }
    
    public void verPersona(String nombre){
        for(Persona persona : iDaoPersona.verPersonas()){
            if(persona.getNombrePersona().equals(nombre)){
                panelVerPersona.txtNombre.setText(persona.getNombrePersona());
                panelVerPersona.txtDocumentoPersona.setText(String.valueOf(persona.getDocumentoPersona()));
                panelVerPersona.txtTelefono.setText(persona.getTelefono());
                panelVerPersona.txtEmail.setText(persona.getEmailPersona());
                
                if(persona.getIdCarrera() == 0){
                    panelVerPersona.txtCarrera.setVisible(false);
                    panelVerPersona.txtCarreraTxt.setVisible(false);
                    panelVerPersona.txtDisponibilidad.setText("Este usuario es un administrador");
                }
                else{
                    panelVerPersona.txtCarrera.setVisible(true);
                    panelVerPersona.txtCarreraTxt.setVisible(true);
                    
                    for(Carrera carrera : iDaoCarrera.verCarreras()){
                        if(carrera.getIdCarrera() == persona.getIdCarrera()){
                            panelVerPersona.txtCarrera.setText(carrera.getCarrera());
                        }
                    }
                    panelVerPersona.txtDisponibilidad.setText("");
                }
            }
        }
    }
    
    
    
    
    
    
    public void inicio(int documento, String nombre){
        this.documentoLogin = documento;
        this.nombre = nombre;
        
        String nombreCorto = "";
        
        for(int i=0; i <= nombre.length(); i++){
            if(nombre.charAt(i) == ' '){
                i = nombre.length();
            }
            else{
                nombreCorto += nombre.charAt(i);
            }
        }
        this.nombreCorto = nombreCorto;
        
        panelAdminUsuario.txtNombrePersona.setText(nombreCorto);
        panelAdminUsuario.txtNombrePersona1.setText(nombre);
        panelAdminUsuario.panelExten.setVisible(false);
        verPersonas(iDaoPersona.verPersonas());
        
        visiblePanelAdminUsuario();
    }
    
    public void visiblePanelAdminLibros(){
        ControladorLibro controladorLibro = new ControladorLibro();
        
        controladorLibro.inicio(documentoLogin, nombre);
    }
    
    public void visiblePanelAdmin(){
        ControladorPanelAdmin controladorPanelAdmin = new ControladorPanelAdmin();
        
        controladorPanelAdmin.inicio(documentoLogin, iDaoPersona.verPersonas());
    }
    
    public void visiblePanelAdminUsuario(){
        panelAdminUsuario.setTitle("Panel Administrar Usuarios - Sigeli");
        this.busqueda = false;
        panelAdminUsuario.txtBusquedaAdmin.setText("");
        panelAdminUsuario.setLocationRelativeTo(null);
        panelAdminUsuario.setResizable(false);
        panelAdminUsuario.setVisible(true);
    }
    
    public void cerrarPanelAdminUsuario(){
        panelAdminUsuario.setVisible(false);
    }
    
    public void visiblePanelVerPersona(){
        panelVerPersona.setTitle("Detalles de la persona");
        panelVerPersona.txtNombrePersona.setText(nombreCorto);
        panelVerPersona.txtNombrePersona1.setText(nombre);
        panelVerPersona.panelExten.setVisible(false);
        panelVerPersona.setLocationRelativeTo(null);
        panelVerPersona.setResizable(false);
        panelVerPersona.setVisible(true);
    }
    
    
    public void cerrarPanelVerPersona(){
        panelVerPersona.setVisible(false);
    }
    
     public void setDatosPersona(ArrayList<Persona> arrayListPersona, int cantPersonas, String busqueda){
        
        if(this.busqueda){
            panelAdminUsuario.nextPagAdmin.setVisible(false);
            panelAdminUsuario.backPagAdmin.setVisible(false);
        }
        else{
            panelAdminUsuario.nextPagAdmin.setVisible(true);
            panelAdminUsuario.backPagAdmin.setVisible(true);
        }
        
        if(arrayListPersona.size() == 0){
           panelAdminUsuario.panel1.setVisible(false);
           panelAdminUsuario.panel2.setVisible(false);
           panelAdminUsuario.panel3.setVisible(false);
           panelAdminUsuario.panel4.setVisible(false);
           panelAdminUsuario.panel5.setVisible(false);
           busqueda += "\" - No se han encontrado resultados";
        }
        else if(arrayListPersona.size() == 1){
            panelAdminUsuario.nextPagAdmin.setVisible(false);
            if(cantMenorPersona == 0){
                panelAdminUsuario.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminUsuario.panel1.setVisible(true);
           panelAdminUsuario.panel2.setVisible(false);
           panelAdminUsuario.panel3.setVisible(false);
           panelAdminUsuario.panel4.setVisible(false);
           panelAdminUsuario.panel5.setVisible(false);
           //nombre
           panelAdminUsuario.txtNombre1.setText(arrayListPersona.get(0).getNombrePersona());
           //documento
           panelAdminUsuario.txtDocumento1.setText("Documento: " +arrayListPersona.get(0).getDocumentoPersona());
           //email
           panelAdminUsuario.txtEmail1.setText("Email: " + String.valueOf(arrayListPersona.get(0).getEmailPersona()));
           //telefono
           panelAdminUsuario.txtTelefono1.setText("Telefono: " + arrayListPersona.get(0).getTelefono() );
           
        }
        else if(arrayListPersona.size() == 2){
            panelAdminUsuario.nextPagAdmin.setVisible(false);
            if(cantMenorPersona == 0){
                panelAdminUsuario.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminUsuario.panel1.setVisible(true);
           panelAdminUsuario.panel2.setVisible(true);
           panelAdminUsuario.panel3.setVisible(false);
           panelAdminUsuario.panel4.setVisible(false);
           panelAdminUsuario.panel5.setVisible(false);
           //nombre
           panelAdminUsuario.txtNombre1.setText(arrayListPersona.get(0).getNombrePersona());
           panelAdminUsuario.txtNombre2.setText(arrayListPersona.get(1).getNombrePersona());
           //documento
           panelAdminUsuario.txtDocumento1.setText("Documento: " +arrayListPersona.get(0).getDocumentoPersona());
           panelAdminUsuario.txtDocumento2.setText("Documento: " +arrayListPersona.get(1).getDocumentoPersona());
           //email
           panelAdminUsuario.txtEmail1.setText("Email: " + String.valueOf(arrayListPersona.get(0).getEmailPersona()));
           panelAdminUsuario.txtEmail2.setText("Email: " + String.valueOf(arrayListPersona.get(1).getEmailPersona()));
           //telefono
           panelAdminUsuario.txtTelefono1.setText("Telefono: " + arrayListPersona.get(0).getTelefono() );
           panelAdminUsuario.txtTelefono2.setText("Telefono: " + arrayListPersona.get(1).getTelefono() );
        }
        else if(arrayListPersona.size() == 3){
            panelAdminUsuario.nextPagAdmin.setVisible(false);
            if(cantMenorPersona == 0){
                panelAdminUsuario.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminUsuario.panel1.setVisible(true);
           panelAdminUsuario.panel2.setVisible(true);
           panelAdminUsuario.panel3.setVisible(true);
           panelAdminUsuario.panel4.setVisible(false);
           panelAdminUsuario.panel5.setVisible(false);
           //nombre
           panelAdminUsuario.txtNombre1.setText(arrayListPersona.get(0).getNombrePersona());
           panelAdminUsuario.txtNombre2.setText(arrayListPersona.get(1).getNombrePersona());
           panelAdminUsuario.txtNombre3.setText(arrayListPersona.get(2).getNombrePersona());
           //documento
           panelAdminUsuario.txtDocumento1.setText("Documento: " +arrayListPersona.get(0).getDocumentoPersona());
           panelAdminUsuario.txtDocumento2.setText("Documento: " +arrayListPersona.get(1).getDocumentoPersona());
           panelAdminUsuario.txtDocumento3.setText("Documento: " +arrayListPersona.get(2).getDocumentoPersona());
           //email
           panelAdminUsuario.txtEmail1.setText("Email: " + String.valueOf(arrayListPersona.get(0).getEmailPersona()));
           panelAdminUsuario.txtEmail2.setText("Email: " + String.valueOf(arrayListPersona.get(1).getEmailPersona()));
           panelAdminUsuario.txtEmail3.setText("Email: " + String.valueOf(arrayListPersona.get(2).getEmailPersona()));
           //telefono
           panelAdminUsuario.txtTelefono1.setText("Telefono: " + arrayListPersona.get(0).getTelefono() );
           panelAdminUsuario.txtTelefono2.setText("Telefono: " + arrayListPersona.get(1).getTelefono() );
           panelAdminUsuario.txtTelefono3.setText("Telefono: " + arrayListPersona.get(2).getTelefono() );
        }
        else if(arrayListPersona.size() == 4){
            panelAdminUsuario.nextPagAdmin.setVisible(false);
            if(cantMenorPersona == 0){
                panelAdminUsuario.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminUsuario.panel1.setVisible(true);
           panelAdminUsuario.panel2.setVisible(true);
           panelAdminUsuario.panel3.setVisible(true);
           panelAdminUsuario.panel4.setVisible(true);
           panelAdminUsuario.panel5.setVisible(false);
           //nombre
           panelAdminUsuario.txtNombre1.setText(arrayListPersona.get(0).getNombrePersona());
           panelAdminUsuario.txtNombre2.setText(arrayListPersona.get(1).getNombrePersona());
           panelAdminUsuario.txtNombre3.setText(arrayListPersona.get(2).getNombrePersona());
           panelAdminUsuario.txtNombre4.setText(arrayListPersona.get(3).getNombrePersona());
           //documento
           panelAdminUsuario.txtDocumento1.setText("Documento: " +arrayListPersona.get(0).getDocumentoPersona());
           panelAdminUsuario.txtDocumento2.setText("Documento: " +arrayListPersona.get(1).getDocumentoPersona());
           panelAdminUsuario.txtDocumento3.setText("Documento: " +arrayListPersona.get(2).getDocumentoPersona());
           panelAdminUsuario.txtDocumento4.setText("Documento: " +arrayListPersona.get(3).getDocumentoPersona());
           //email
           panelAdminUsuario.txtEmail1.setText("Email: " + String.valueOf(arrayListPersona.get(0).getEmailPersona()));
           panelAdminUsuario.txtEmail2.setText("Email: " + String.valueOf(arrayListPersona.get(1).getEmailPersona()));
           panelAdminUsuario.txtEmail3.setText("Email: " + String.valueOf(arrayListPersona.get(2).getEmailPersona()));
           panelAdminUsuario.txtEmail4.setText("Email: " + String.valueOf(arrayListPersona.get(3).getEmailPersona()));
           //telefono
           panelAdminUsuario.txtTelefono1.setText("Telefono: " + arrayListPersona.get(0).getTelefono() );
           panelAdminUsuario.txtTelefono2.setText("Telefono: " + arrayListPersona.get(1).getTelefono() );
           panelAdminUsuario.txtTelefono3.setText("Telefono: " + arrayListPersona.get(2).getTelefono() );
           panelAdminUsuario.txtTelefono4.setText("Telefono: " + arrayListPersona.get(3).getTelefono() );
        }
        else if(arrayListPersona.size() == 5){
            panelAdminUsuario.nextPagAdmin.setVisible(true);
            if(this.busqueda){
                panelAdminUsuario.nextPagAdmin.setVisible(false);
                panelAdminUsuario.backPagAdmin.setVisible(false);
            }
            if(cantMenorPersona == 0){
                panelAdminUsuario.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminUsuario.panel1.setVisible(true);
           panelAdminUsuario.panel2.setVisible(true);
           panelAdminUsuario.panel3.setVisible(true);
           panelAdminUsuario.panel4.setVisible(true);
           panelAdminUsuario.panel5.setVisible(true);
           //nombre
           panelAdminUsuario.txtNombre1.setText(arrayListPersona.get(0).getNombrePersona());
           panelAdminUsuario.txtNombre2.setText(arrayListPersona.get(1).getNombrePersona());
           panelAdminUsuario.txtNombre3.setText(arrayListPersona.get(2).getNombrePersona());
           panelAdminUsuario.txtNombre4.setText(arrayListPersona.get(3).getNombrePersona());
           panelAdminUsuario.txtNombre5.setText(arrayListPersona.get(4).getNombrePersona());
           //documento
           panelAdminUsuario.txtDocumento1.setText("Documento: " +arrayListPersona.get(0).getDocumentoPersona());
           panelAdminUsuario.txtDocumento2.setText("Documento: " +arrayListPersona.get(1).getDocumentoPersona());
           panelAdminUsuario.txtDocumento3.setText("Documento: " +arrayListPersona.get(2).getDocumentoPersona());
           panelAdminUsuario.txtDocumento4.setText("Documento: " +arrayListPersona.get(3).getDocumentoPersona());
           panelAdminUsuario.txtDocumento5.setText("Documento: " +arrayListPersona.get(4).getDocumentoPersona());
           //email
           panelAdminUsuario.txtEmail1.setText("Email: " + String.valueOf(arrayListPersona.get(0).getEmailPersona()));
           panelAdminUsuario.txtEmail2.setText("Email: " + String.valueOf(arrayListPersona.get(1).getEmailPersona()));
           panelAdminUsuario.txtEmail3.setText("Email: " + String.valueOf(arrayListPersona.get(2).getEmailPersona()));
           panelAdminUsuario.txtEmail4.setText("Email: " + String.valueOf(arrayListPersona.get(3).getEmailPersona()));
           panelAdminUsuario.txtEmail5.setText("Email: " + String.valueOf(arrayListPersona.get(4).getEmailPersona()));
           //telefono
           panelAdminUsuario.txtTelefono1.setText("Telefono: " + arrayListPersona.get(0).getTelefono() );
           panelAdminUsuario.txtTelefono2.setText("Telefono: " + arrayListPersona.get(1).getTelefono() );
           panelAdminUsuario.txtTelefono3.setText("Telefono: " + arrayListPersona.get(2).getTelefono() );
           panelAdminUsuario.txtTelefono4.setText("Telefono: " + arrayListPersona.get(4).getTelefono() );
           panelAdminUsuario.txtTelefono5.setText("Telefono: " + arrayListPersona.get(5).getTelefono() );
        }
        else{
           panelAdminUsuario.panel1.setVisible(false);
           panelAdminUsuario.panel2.setVisible(false);
           panelAdminUsuario.panel3.setVisible(false);
           panelAdminUsuario.panel4.setVisible(false);
           panelAdminUsuario.panel5.setVisible(false);
        }
        
        if(!busqueda.equals("")){
            panelAdminUsuario.txtBusquedaAdmin.setText("Se muestran resultados para \"" + busqueda + "\"");
        }
        else{
            panelAdminUsuario.txtBusquedaAdmin.setText("");
        }
        cantLibrosPag = arrayListPersona.size();
        panelAdminUsuario.txtCantLibroAdmin.setText("Se mostraron " + arrayListPersona.size() + " usuarios de: " + cantPersonas);
        
     }
}
