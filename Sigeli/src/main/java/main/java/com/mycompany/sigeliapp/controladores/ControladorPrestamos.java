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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import main.java.com.mycompany.sigeliapp.dao.*;
import main.java.com.mycompany.sigeliapp.modelos.*;
import main.java.com.mycompany.sigeliapp.modelos.utiles.Portapapeles;
import main.java.com.mycompany.sigeliapp.validaciones.ValidacionPrestamos;
import main.java.com.mycompany.sigeliapp.vistas.*;



public class ControladorPrestamos implements ActionListener, MouseListener{
    
    //vistas
    private panelAdminPrestamos panelAdminPrestamos;
    private PanelVerPrestamo panelVerPrestamo;
    private PanelAddPrestamo panelAddPrestamo;
    
    //Controladores
    
    private ControladorPanelAdmin controladorPanelAdmin;
    
    //DAO
    private IDaoPrestamos iDaoPrestamo;
    private IDaoPersona iDaoPersona;
    private IDaoLibro iDaoLibro;
    
    //Variables del controlador
    
    private int documentoLogin;
    private String nombre, nombreCorto;
    private boolean busqueda = false;
    private int cantMenorPrestamo = 0, cantMayorPrestamo = 4, cantPrestamosPag = 0;

    public ControladorPrestamos() {
        
        //Vistas
        this.panelAdminPrestamos = new panelAdminPrestamos();
        this.panelVerPrestamo = new PanelVerPrestamo();
        this.panelAddPrestamo = new PanelAddPrestamo();
        
        //controlador 
        
        this.controladorPanelAdmin = new ControladorPanelAdmin();
        
        //DAO
        this.iDaoPrestamo = new DaoPrestamos();
        this.iDaoPersona = new DaoPersona();
        this.iDaoLibro = new DaoLibro();
        
        
        //acciones panel admin prestamos
        
        this.panelAdminPrestamos.panel1.addMouseListener(this);
        this.panelAdminPrestamos.panel2.addMouseListener(this);
        this.panelAdminPrestamos.panel3.addMouseListener(this);
        this.panelAdminPrestamos.panel4.addMouseListener(this);
        this.panelAdminPrestamos.panel5.addMouseListener(this);
        this.panelAdminPrestamos.btnBusqueda.addActionListener(this);
        this.panelAdminPrestamos.btnPegar.addActionListener(this);
        this.panelAdminPrestamos.btnCerrarSesion.addMouseListener(this);
        this.panelAdminPrestamos.btnCrearPrestamo.addMouseListener(this);
        this.panelAdminPrestamos.btnVolverMenu.addMouseListener(this);
        this.panelAdminPrestamos.btnExtenPanel.addMouseListener(this);
        this.panelAdminPrestamos.btnExtenPanelOff.addMouseListener(this);
        this.panelAdminPrestamos.btnCrearPrestamo.addActionListener(this);
        
        
        this.panelAdminPrestamos.adminLibros.addMouseListener(this);
        this.panelAdminPrestamos.adminUsuarios.addMouseListener(this);
        this.panelAdminPrestamos.addLibros.addMouseListener(this);
        this.panelAdminPrestamos.prestamos.addMouseListener(this);
        this.panelAdminPrestamos.multas.addMouseListener(this);
        this.panelAdminPrestamos.reportes.addMouseListener(this);
        
        
        //Acciones panel ver prestamo

        this.panelVerPrestamo.btnCerrarSesion.addMouseListener(this);
        this.panelVerPrestamo.btnVolverMenu.addMouseListener(this);
        this.panelVerPrestamo.btnExtenPanel.addMouseListener(this);
        this.panelVerPrestamo.btnExtenPanelOff.addMouseListener(this);
        
        this.panelVerPrestamo.txtDocumentoPersona.addMouseListener(this);
        this.panelVerPrestamo.txtIsbn.addMouseListener(this);
        this.panelVerPrestamo.btnValidarPrestamo.addMouseListener(this);
        
        this.panelVerPrestamo.adminLibros.addMouseListener(this);
        this.panelVerPrestamo.adminUsuarios.addMouseListener(this);
        this.panelVerPrestamo.addLibros.addMouseListener(this);
        this.panelVerPrestamo.prestamos.addMouseListener(this);
        this.panelVerPrestamo.multas.addMouseListener(this);
        this.panelVerPrestamo.reportes.addMouseListener(this);
        
        
        //Panel add Prestamo
        
        this.panelAddPrestamo.btnVolverMenu.addMouseListener(this);
        this.panelAddPrestamo.btnPegarDocumento1.addMouseListener(this);
        this.panelAddPrestamo.btnPegarIsbn.addMouseListener(this);
        this.panelAddPrestamo.btnCrearPrestamo.addActionListener(this);
        
        this.panelAddPrestamo.btnCerrarSesion.addMouseListener(this);
        this.panelAddPrestamo.btnVolverMenu.addMouseListener(this);
        this.panelAddPrestamo.btnExtenPanel.addMouseListener(this);
        this.panelAddPrestamo.btnExtenPanelOff.addMouseListener(this);
        
        this.panelAddPrestamo.adminLibros.addMouseListener(this);
        this.panelAddPrestamo.adminUsuarios.addMouseListener(this);
        this.panelAddPrestamo.addLibros.addMouseListener(this);
        this.panelAddPrestamo.prestamos.addMouseListener(this);
        this.panelAddPrestamo.multas.addMouseListener(this);
        this.panelAddPrestamo.reportes.addMouseListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == panelAdminPrestamos.nextPagAdmin){
             cantMenorPrestamo = cantMayorPrestamo;
             cantMayorPrestamo +=4;
             verPrestamos(iDaoPrestamo.verPrestamos());
        }
        else if(e.getSource() == panelAdminPrestamos.backPagAdmin){
             
             cantMayorPrestamo = cantMenorPrestamo;
             
            if(cantMayorPrestamo < 4){
                cantMayorPrestamo = 4;
            }

            cantMenorPrestamo -=4;
            
           if(cantMenorPrestamo < 0 && cantMenorPrestamo > 4){
                cantMenorPrestamo = 0;
            }

           verPrestamos(iDaoPrestamo.verPrestamos());
           
        }
        
        else if(e.getSource() == panelAdminPrestamos.btnCrearPrestamo){
            cerrarPanelAdminPrestamos();
            visiblePanelAddPrestamo();
        }
        
        else if(e.getSource() == panelAddPrestamo.btnCrearPrestamo){
            crearPrestamo();
            cerrarPanelAddPrestamo();
            visiblePanelAdmin();
        }
        else if(e.getSource() == panelAdminPrestamos.btnPegar){
            try {
                panelAdminPrestamos.txtIngresoBusqueda.setText(Portapapeles.get());
            } catch (Exception ex) {
                Logger.getLogger(ControladorPrestamos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(e.getSource() == panelAdminPrestamos.btnBusqueda){
            buscarPrestamo(panelAdminPrestamos.txtIngresoBusqueda.getText());
        }
        
    }

    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getSource() == panelAdminPrestamos.btnCerrarSesion || e.getSource() == panelVerPrestamo.btnCerrarSesion || e.getSource() == panelAddPrestamo.btnCerrarSesion){
            cerrarPanelAddPrestamo();
            cerrarPanelAdminPrestamos();
            cerrarPanelVerPrestamo();
            
            ControladorLogin controladorLogin = new ControladorLogin();
            
            controladorLogin.visibleLogin();
        }
        
        else if(e.getSource() == panelAdminPrestamos.btnVolverMenu){
            cerrarPanelAdminPrestamos();
            visiblePanelAdmin();
        }
        
        else if(e.getSource() == panelVerPrestamo.btnVolverMenu){
            cerrarPanelVerPrestamo();
            visiblePanelAdminPrestamos();
        }
        
        else if(e.getSource() == panelAddPrestamo.btnVolverMenu){
            cerrarPanelAddPrestamo();
            cerrarPanelAdmin();
            visiblePanelAdmin();
        }
        
        else if(e.getSource() == panelAdminPrestamos.panel1){
            verPrestamo(Integer.parseInt(panelAdminPrestamos.txtNombre1.getText()), Integer.parseInt(panelAdminPrestamos.txtDocumento1.getText()));
            cerrarPanelAdminPrestamos();
            visiblePanelVerPrestamo();
        }
        
        else if(e.getSource() == panelAdminPrestamos.panel2){
            verPrestamo(Integer.parseInt(panelAdminPrestamos.txtNombre2.getText()), Integer.parseInt(panelAdminPrestamos.txtDocumento2.getText()));
            cerrarPanelAdminPrestamos();
            visiblePanelVerPrestamo();
        }
        
        else if(e.getSource() == panelAdminPrestamos.panel3){
            verPrestamo(Integer.parseInt(panelAdminPrestamos.txtNombre3.getText()), Integer.parseInt(panelAdminPrestamos.txtDocumento3.getText()));
            cerrarPanelAdminPrestamos();
            visiblePanelVerPrestamo();
        }
        
        else if(e.getSource() == panelAdminPrestamos.panel4){
            verPrestamo(Integer.parseInt(panelAdminPrestamos.txtNombre4.getText()), Integer.parseInt(panelAdminPrestamos.txtDocumento4.getText()));
            cerrarPanelAdminPrestamos();
            visiblePanelVerPrestamo();
        }
        
        else if(e.getSource() == panelAdminPrestamos.panel5){
            verPrestamo(Integer.parseInt(panelAdminPrestamos.txtNombre5.getText()), Integer.parseInt(panelAdminPrestamos.txtDocumento5.getText()));
            cerrarPanelAdminPrestamos();
            visiblePanelVerPrestamo();
        }
        
        else if(e.getSource() == panelVerPrestamo.txtDocumentoPersona){
            Portapapeles.copiar(panelVerPrestamo.txtDocumentoPersona.getText());
            JOptionPane.showMessageDialog(null, "Documento copiado al portapapeles");
        }
        
        else if(e.getSource() == panelVerPrestamo.txtIsbn){
            Portapapeles.copiar(panelVerPrestamo.txtIsbn.getText());
            JOptionPane.showMessageDialog(null, "ISBN copiado al portapapeles");
        }
        
        
        
        else if(e.getSource() == panelVerPrestamo.btnValidarPrestamo){
            
            cerrarPanelVerPrestamo();
            verPrestamos(iDaoPrestamo.verPrestamos());
            validarPrestamo(Integer.parseInt(panelVerPrestamo.idPrestamo.getText()));
            visiblePanelAdminPrestamos(); 
        }
        
        
        //Panel extendido
        
        else if(e.getSource() == panelAdminPrestamos.btnExtenPanel){
            panelAdminPrestamos.panelExten.setVisible(true);
            panelAdminPrestamos.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelAdminPrestamos.btnExtenPanelOff){
            panelAdminPrestamos.btnVolverMenu.setVisible(true);
            panelAdminPrestamos.panelExten.setVisible(false);
            
        }
        
        else if(e.getSource() == panelVerPrestamo.btnExtenPanel){
            panelVerPrestamo.panelExten.setVisible(true);
            panelVerPrestamo.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelVerPrestamo.btnExtenPanelOff){
            panelVerPrestamo.btnVolverMenu.setVisible(true);
            panelVerPrestamo.panelExten.setVisible(false);
            
        }
        
        else if(e.getSource() == panelAddPrestamo.btnExtenPanel){
            panelAddPrestamo.panelExten.setVisible(true);
            panelAddPrestamo.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelAddPrestamo.btnExtenPanelOff){
            panelAddPrestamo.btnVolverMenu.setVisible(true);
            panelAddPrestamo.panelExten.setVisible(false);
            
        }
        
        //panel add prestamo
        
        else if(e.getSource() == panelAddPrestamo.btnPegarDocumento1){
            
            try {
                panelAddPrestamo.txtDocumentoPersona.setText(Portapapeles.get());
            } catch (Exception ex) {
                Logger.getLogger(ControladorPrestamos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        else if(e.getSource() == panelAddPrestamo.btnPegarIsbn){
            
            try {
                panelAddPrestamo.txtIsbn.setText(Portapapeles.get());
            } catch (Exception ex) {
                Logger.getLogger(ControladorPrestamos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        
        
        //Opciones panel Extendido
        
        else if(e.getSource() == panelAdminPrestamos.adminLibros || e.getSource() == panelVerPrestamo.adminLibros || e.getSource() == panelAddPrestamo.adminLibros){
            cerrarPanelAdminPrestamos();
            cerrarPanelVerPrestamo();
            cerrarPanelAddPrestamo();
            ControladorLibro controladorLibro = new ControladorLibro();
            controladorLibro.inicio(documentoLogin, nombre);
        }
        
        else if(e.getSource() == panelAdminPrestamos.adminUsuarios || e.getSource() == panelVerPrestamo.adminUsuarios || e.getSource() == panelAddPrestamo.adminUsuarios){
            cerrarPanelAdminPrestamos();
            cerrarPanelVerPrestamo();
            cerrarPanelAddPrestamo();
            
            ControladorPersona controladorPersona = new ControladorPersona();
            controladorPersona.inicio(documentoLogin, nombre);
            
        }
        
        else if(e.getSource() == panelAdminPrestamos.addLibros || e.getSource() == panelVerPrestamo.addLibros || e.getSource() == panelAddPrestamo.addLibros){
            cerrarPanelAdminPrestamos();
            cerrarPanelVerPrestamo();
            cerrarPanelAddPrestamo();
            
            ControladorLibro controladorLibro = new ControladorLibro();
            
            controladorLibro.inicioAddLibros(documentoLogin, nombre);
        }
        
        //Sin controladores
        
        else if(e.getSource() == panelAdminPrestamos.prestamos || e.getSource() == panelVerPrestamo.prestamos || e.getSource() == panelAddPrestamo.prestamos){
            cerrarPanelAdminPrestamos();
            cerrarPanelVerPrestamo();
            cerrarPanelAddPrestamo();
            
            visiblePanelAdminPrestamos();

        }
        
        else if(e.getSource() == panelAdminPrestamos.multas || e.getSource() == panelVerPrestamo.multas || e.getSource() == panelAddPrestamo.multas){
            cerrarPanelAdminPrestamos();
            cerrarPanelVerPrestamo();
            cerrarPanelAddPrestamo();
            
            ControladorMulta controladorMulta = new ControladorMulta();
            controladorMulta.inicio(documentoLogin, nombre);

        }
        
        else if(e.getSource() == panelAdminPrestamos.reportes || e.getSource() == panelVerPrestamo.reportes|| e.getSource() == panelAddPrestamo.reportes){
            cerrarPanelAdminPrestamos();
            cerrarPanelVerPrestamo();
            cerrarPanelAddPrestamo();

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
    
    
    
    
    
    public void verPrestamos(ArrayList<Prestamos> arrayListPrestamos){
        ArrayList<Prestamos> arrayListPrestamosEnviar = new ArrayList<>();
        int cant = cantMenorPrestamo, cantPrestamos= 0;


        for(Prestamos prestamo : arrayListPrestamos){

            if(prestamo.getIdEstado() == 3){
                if(cantMayorPrestamo > arrayListPrestamos.size()){
                    cantMayorPrestamo = arrayListPrestamos.size();
                }
                if(cantMenorPrestamo < 0){
                    cantMenorPrestamo = 0;
                }
                if(cantPrestamos <= cantMayorPrestamo  && cantPrestamos >= cantMenorPrestamo){
                    if(prestamo.getIdEstado() == 3){
                        arrayListPrestamosEnviar.add(prestamo);
                    }
                    cantMenorPrestamo +=1;
                }
                cantPrestamos +=1;
            }
        }
        
        cantMenorPrestamo = cant;
        
        setDatosPrestamo(arrayListPrestamosEnviar, arrayListPrestamosEnviar.size(), "");
        this.busqueda = false;
        
    }
    
    //Sin necesidad de validación
    public void verPrestamo(int documento, int id){
        
            for(Prestamos prestamo : iDaoPrestamo.verPrestamos()){
                if(id == prestamo.getIdPrestamo()){
                    for(Persona persona : iDaoPersona.verPersonas()){
                        if(persona.getDocumentoPersona() == documento){
                            panelVerPrestamo.txtNombre.setText(persona.getNombrePersona());
                        }
                    }
                    
                    panelVerPrestamo.txtDocumentoPersona.setText(String.valueOf(prestamo.getIdPersona()));
                    panelVerPrestamo.txtIsbn.setText(prestamo.getIsbnLibro());
                    panelVerPrestamo.idPrestamo.setText(String.valueOf(prestamo.getIdPrestamo()));
                    panelVerPrestamo.txtFechaPrestamo.setText(String.valueOf(prestamo.getFechaPrestamo()));
                    panelVerPrestamo.txtfechaEntrega.setText(String.valueOf(prestamo.getFechaEntrega()));
                    panelVerPrestamo.txtEstadoPrestamo.setText((prestamo.getIdEstado() == 3)? "El libro está prestado" : "Este prestamo ya fue validado");
            }
        }
        
    }
    
    //Con validación
    public void crearPrestamo(){
        
        
        for(Libro libro : iDaoLibro.verLibros()){
            if(panelAddPrestamo.txtIsbn.getText().equals(libro.getIsbnLibro())){
                if(libro.getIdEstado() == 1){
                    Prestamos prestamo = new Prestamos();
                    prestamo.setIdPersona(Integer.parseInt(panelAddPrestamo.txtDocumentoPersona.getText()));
                    prestamo.setIdEstado(3);
                    prestamo.setIsbnLibro(panelAddPrestamo.txtIsbn.getText());
                    prestamo.setFechaPrestamo(Date.valueOf(LocalDate.now()));
                    prestamo.setFechaEntrega(Date.valueOf(panelAddPrestamo.txtAno.getText() + "-" + panelAddPrestamo.txtMes.getText() + "-" + panelAddPrestamo.txtDiaPrestamo.getText()));
                    libro.setIdEstado(0);
                    if(ValidacionPrestamos.validarNuevoPrestamo(prestamo)){
                        if(iDaoPrestamo.addPrestamo(prestamo) && iDaoLibro.cambioEstadoLibro(libro)){
                            JOptionPane.showMessageDialog(null, "Prestamo creado con éxito");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "El libro no se encuentra disponible");
                }
            }
        }
        
    }
    
    //Sin necesidad de validación
    public void validarPrestamo(int id){
        Prestamos prestamo = new Prestamos();
        Libro libro = new Libro();
        prestamo.setIdPrestamo(id);
        prestamo.setIdEstado(2);
        if(iDaoPrestamo.cambioEstado(prestamo)){
            libro.setIsbnLibro(prestamo.getIsbnLibro());
            libro.setIdEstado(1);
            if(iDaoLibro.cambioEstadoLibro(libro)){
                JOptionPane.showMessageDialog(null, "Prestamo validado con éxito");
            }

        }
        else{
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
        }
    }
    
    public void buscarPrestamo(String busqueda){
        
        ArrayList<Prestamos> arrayListPrestamos = new ArrayList<>();
        
        if(busqueda.equals("")){
            cantMayorPrestamo =4;
            cantMenorPrestamo = 0;
            this.busqueda = false;
            verPrestamos(iDaoPrestamo.verPrestamos());  
        }
        else{
            cantMayorPrestamo =4;
            cantMenorPrestamo = 0;
        
        
            for(Prestamos prestamo : iDaoPrestamo.verPrestamos()){
                if(String.valueOf(prestamo.getIdPrestamo()).equals(busqueda) || String.valueOf(prestamo.getIdPersona()).equals(busqueda)){
                    if(arrayListPrestamos.size() < 4){
                        if(prestamo.getIdEstado() == 3){
                            arrayListPrestamos.add(prestamo);
                        }
                    }
                }
            }
            
            this.busqueda = true;
            setDatosPrestamo(arrayListPrestamos, arrayListPrestamos.size(), busqueda); 
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
        
        panelAdminPrestamos.txtNombrePersona.setText(nombreCorto);
        panelAdminPrestamos.txtNombrePersona1.setText(nombre);
        panelAdminPrestamos.panelExten.setVisible(false);
        
        verPrestamos(iDaoPrestamo.verPrestamos());
        visiblePanelAdminPrestamos();
        
    }
    
    public void inicioCrearPrestamo(int documento, String nombre, String isbn){
        
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
        
        panelAddPrestamo.txtNombrePersona.setText(nombreCorto);
        panelAddPrestamo.txtNombrePersona1.setText(nombre);
        panelAddPrestamo.panelExten.setVisible(false);
        panelAddPrestamo.txtIsbn.setText(isbn);

        visiblePanelAddPrestamo();
        
    }
    
    public void visiblePanelAdminPrestamos(){
        panelAdminPrestamos.setTitle("Panel Administrar Prestamos - Sigeli");
        this.busqueda = false;
        panelAdminPrestamos.txtBusquedaAdmin.setText("");
        panelAdminPrestamos.setLocationRelativeTo(null);
        panelAdminPrestamos.setResizable(false);
        panelAdminPrestamos.setVisible(true);
    }
    
    public void cerrarPanelAdminPrestamos(){
        panelAdminPrestamos.setVisible(false);
    }
    
    public void visiblePanelAdmin(){

        controladorPanelAdmin.inicio(documentoLogin, iDaoPersona.verPersonas());
    }
    
    public void cerrarPanelAdmin(){
        
        controladorPanelAdmin.cerrarPanelAdmin();
    }
    
    public void visiblePanelVerPrestamo(){
        panelVerPrestamo.setTitle("Detalles del Prestamo - Sigeli");
        panelVerPrestamo.txtNombrePersona.setText(nombreCorto);
        panelVerPrestamo.txtNombrePersona1.setText(nombre);
        panelVerPrestamo.panelExten.setVisible(false);
        panelVerPrestamo.setResizable(false);
        panelVerPrestamo.setLocationRelativeTo(null);
        panelVerPrestamo.setVisible(true);
    }
    
    public void cerrarPanelVerPrestamo(){
        panelVerPrestamo.setVisible(false);
    }
    
    public void visiblePanelAddPrestamo(){
        panelAddPrestamo.setTitle("Creación de un prestamo - Sigeli");
        panelAddPrestamo.txtNombrePersona.setText(nombreCorto);
        panelAddPrestamo.txtNombrePersona1.setText(nombre);
        panelAddPrestamo.panelExten.setVisible(false);
        panelAddPrestamo.setLocationRelativeTo(null);
        panelAddPrestamo.setResizable(false);
        panelAddPrestamo.setVisible(true);
    }
    
    public void cerrarPanelAddPrestamo(){
        panelAddPrestamo.setVisible(false);
    }
    
    public void setDatosPrestamo(ArrayList<Prestamos> arrayListPrestamos, int cantPrestamos, String busqueda){
        
        if(this.busqueda){
            panelAdminPrestamos.nextPagAdmin.setVisible(false);
            panelAdminPrestamos.backPagAdmin.setVisible(false);
        }
        else{
            panelAdminPrestamos.nextPagAdmin.setVisible(true);
            panelAdminPrestamos.backPagAdmin.setVisible(true);
        }
        
        if(arrayListPrestamos.size() == 0){
           panelAdminPrestamos.panel1.setVisible(false);
           panelAdminPrestamos.panel2.setVisible(false);
           panelAdminPrestamos.panel3.setVisible(false);
           panelAdminPrestamos.panel4.setVisible(false);
           panelAdminPrestamos.panel5.setVisible(false);
           panelAdminPrestamos.nextPagAdmin.setVisible(false);
           panelAdminPrestamos.backPagAdmin.setVisible(false);
           busqueda += "\" - No se han encontrado resultados";
        }
        
        else if(arrayListPrestamos.size() == 1){
            

            
            panelAdminPrestamos.nextPagAdmin.setVisible(false);
            if(cantMenorPrestamo == 0){
                panelAdminPrestamos.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminPrestamos.panel1.setVisible(true);
           panelAdminPrestamos.panel2.setVisible(false);
           panelAdminPrestamos.panel3.setVisible(false);
           panelAdminPrestamos.panel4.setVisible(false);
           panelAdminPrestamos.panel5.setVisible(false);
           //nombre
           panelAdminPrestamos.txtNombre1.setText(String.valueOf(arrayListPrestamos.get(0).getIdPersona()));
           //documento
           panelAdminPrestamos.txtDocumento1.setText("" +arrayListPrestamos.get(0).getIdPrestamo());
           //fecha prestamo
           panelAdminPrestamos.fechaPrestamo1.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(0).getFechaPrestamo()));
           //fecha entrega
           panelAdminPrestamos.fechaEntrega1.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(0).getFechaEntrega()));
           
        }
        
        else if(arrayListPrestamos.size() == 2){
            
            String[] nombres = new String[2];
            int pos = 0;
            for(Persona persona : iDaoPersona.verPersonas()){
                for(int i=0; i<arrayListPrestamos.size(); i++){
                    if(persona.getDocumentoPersona() == arrayListPrestamos.get(i).getIdPersona()){
                        nombres[pos] = persona.getNombrePersona();
                        pos+=1;
                    }
                }
            }
            
            panelAdminPrestamos.nextPagAdmin.setVisible(false);
            if(cantMenorPrestamo == 0){
                panelAdminPrestamos.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminPrestamos.panel1.setVisible(true);
           panelAdminPrestamos.panel2.setVisible(true);
           panelAdminPrestamos.panel3.setVisible(false);
           panelAdminPrestamos.panel4.setVisible(false);
           panelAdminPrestamos.panel5.setVisible(false);
           //nombre
           panelAdminPrestamos.txtNombre1.setText(String.valueOf(arrayListPrestamos.get(0).getIdPersona()));
           panelAdminPrestamos.txtNombre2.setText(String.valueOf(arrayListPrestamos.get(1).getIdPersona()));
           //documento
           panelAdminPrestamos.txtDocumento1.setText("" +arrayListPrestamos.get(0).getIdPrestamo());
           panelAdminPrestamos.txtDocumento2.setText("" +arrayListPrestamos.get(1).getIdPrestamo());
           //fecha prestamo
           panelAdminPrestamos.fechaPrestamo1.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(0).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo2.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(1).getFechaPrestamo()));
           //fecha entrega
           panelAdminPrestamos.fechaEntrega1.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(0).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega2.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(1).getFechaEntrega()));
           
        }
        
        else if(arrayListPrestamos.size() == 3){
            
            String[] nombres = new String[3];
            int pos = 0;
            for(Persona persona : iDaoPersona.verPersonas()){
                for(int i=0; i<arrayListPrestamos.size(); i++){
                    if(persona.getDocumentoPersona() == arrayListPrestamos.get(i).getIdPersona()){
                        nombres[pos] = persona.getNombrePersona();
                        pos+=1;
                    }
                }
            }
            
            panelAdminPrestamos.nextPagAdmin.setVisible(false);
            if(cantMenorPrestamo == 0){
                panelAdminPrestamos.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminPrestamos.panel1.setVisible(true);
           panelAdminPrestamos.panel2.setVisible(true);
           panelAdminPrestamos.panel3.setVisible(true);
           panelAdminPrestamos.panel4.setVisible(false);
           panelAdminPrestamos.panel5.setVisible(false);
           //nombre
           panelAdminPrestamos.txtNombre1.setText(String.valueOf(arrayListPrestamos.get(0).getIdPersona()));
           panelAdminPrestamos.txtNombre2.setText(String.valueOf(arrayListPrestamos.get(1).getIdPersona()));
           panelAdminPrestamos.txtNombre3.setText(String.valueOf(arrayListPrestamos.get(2).getIdPersona()));
           //documento
           panelAdminPrestamos.txtDocumento1.setText("" +arrayListPrestamos.get(0).getIdPrestamo());
           panelAdminPrestamos.txtDocumento2.setText("" +arrayListPrestamos.get(1).getIdPrestamo());
           panelAdminPrestamos.txtDocumento3.setText("" +arrayListPrestamos.get(2).getIdPrestamo());
           //fecha prestamo
           panelAdminPrestamos.fechaPrestamo1.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(0).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo2.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(1).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo3.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(2).getFechaPrestamo()));
           //fecha entrega
           panelAdminPrestamos.fechaEntrega1.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(0).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega2.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(1).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega3.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(2).getFechaEntrega()));
           
        }
        
        else if(arrayListPrestamos.size() == 4){
            
            String[] nombres = new String[4];
            int pos = 0;
            for(Persona persona : iDaoPersona.verPersonas()){
                for(int i=0; i<arrayListPrestamos.size(); i++){
                    if(persona.getDocumentoPersona() == arrayListPrestamos.get(i).getIdPersona()){
                        nombres[pos] = persona.getNombrePersona();
                        pos+=1;
                    }
                }
            }
            
            panelAdminPrestamos.nextPagAdmin.setVisible(false);
            if(cantMenorPrestamo == 0){
                panelAdminPrestamos.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminPrestamos.panel1.setVisible(true);
           panelAdminPrestamos.panel2.setVisible(true);
           panelAdminPrestamos.panel3.setVisible(true);
           panelAdminPrestamos.panel4.setVisible(true);
           panelAdminPrestamos.panel5.setVisible(false);
           //nombre
           panelAdminPrestamos.txtNombre1.setText(String.valueOf(arrayListPrestamos.get(0).getIdPersona()));
           panelAdminPrestamos.txtNombre2.setText(String.valueOf(arrayListPrestamos.get(1).getIdPersona()));
           panelAdminPrestamos.txtNombre3.setText(String.valueOf(arrayListPrestamos.get(2).getIdPersona()));
           panelAdminPrestamos.txtNombre4.setText(String.valueOf(arrayListPrestamos.get(3).getIdPersona()));
           //documento
           panelAdminPrestamos.txtDocumento1.setText("" +arrayListPrestamos.get(0).getIdPrestamo());
           panelAdminPrestamos.txtDocumento2.setText("" +arrayListPrestamos.get(1).getIdPrestamo());
           panelAdminPrestamos.txtDocumento3.setText("" +arrayListPrestamos.get(2).getIdPrestamo());
           panelAdminPrestamos.txtDocumento4.setText("" +arrayListPrestamos.get(3).getIdPrestamo());
           //fecha prestamo
           panelAdminPrestamos.fechaPrestamo1.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(0).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo2.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(1).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo3.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(2).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo4.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(3).getFechaPrestamo()));
           //fecha entrega
           panelAdminPrestamos.fechaEntrega1.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(0).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega2.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(1).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega3.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(2).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega4.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(3).getFechaEntrega()));
           
        }
        
        else if(arrayListPrestamos.size() == 5){
            
            String[] nombres = new String[5];
            int pos = 0;
            for(Persona persona : iDaoPersona.verPersonas()){
                for(int i=0; i<arrayListPrestamos.size(); i++){
                    if(persona.getDocumentoPersona() == arrayListPrestamos.get(i).getIdPersona()){
                        nombres[pos] = persona.getNombrePersona();
                        pos+=1;
                    }
                }
            }
            
            panelAdminPrestamos.nextPagAdmin.setVisible(false);
            if(cantMenorPrestamo == 0){
                panelAdminPrestamos.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminPrestamos.panel1.setVisible(true);
           panelAdminPrestamos.panel2.setVisible(true);
           panelAdminPrestamos.panel3.setVisible(true);
           panelAdminPrestamos.panel4.setVisible(true);
           panelAdminPrestamos.panel5.setVisible(false);
           //nombre
           panelAdminPrestamos.txtNombre1.setText(String.valueOf(arrayListPrestamos.get(0).getIdPersona()));
           panelAdminPrestamos.txtNombre2.setText(String.valueOf(arrayListPrestamos.get(1).getIdPersona()));
           panelAdminPrestamos.txtNombre3.setText(String.valueOf(arrayListPrestamos.get(2).getIdPersona()));
           panelAdminPrestamos.txtNombre4.setText(String.valueOf(arrayListPrestamos.get(3).getIdPersona()));
           panelAdminPrestamos.txtNombre5.setText(String.valueOf(arrayListPrestamos.get(4).getIdPersona()));
           //documento
           panelAdminPrestamos.txtDocumento1.setText("" +arrayListPrestamos.get(0).getIdPrestamo());
           panelAdminPrestamos.txtDocumento2.setText("" +arrayListPrestamos.get(1).getIdPrestamo());
           panelAdminPrestamos.txtDocumento3.setText("" +arrayListPrestamos.get(2).getIdPrestamo());
           panelAdminPrestamos.txtDocumento4.setText("" +arrayListPrestamos.get(3).getIdPrestamo());
           panelAdminPrestamos.txtDocumento5.setText("" +arrayListPrestamos.get(4).getIdPrestamo());
           //fecha prestamo
           panelAdminPrestamos.fechaPrestamo1.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(0).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo2.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(1).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo3.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(2).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo4.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(3).getFechaPrestamo()));
           panelAdminPrestamos.fechaPrestamo5.setText("Fecha del prestamo: " + String.valueOf(arrayListPrestamos.get(4).getFechaPrestamo()));
           //fecha entrega
           panelAdminPrestamos.fechaEntrega1.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(0).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega2.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(1).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega3.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(2).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega4.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(3).getFechaEntrega()));
           panelAdminPrestamos.fechaEntrega5.setText("Fecha de entrega: " + String.valueOf(arrayListPrestamos.get(4).getFechaEntrega()));
           
        }
        
        
        if(!busqueda.equals("")){
            panelAdminPrestamos.txtBusquedaAdmin.setText("Se muestran resultados para \"" + busqueda + "\"");
        }
        else{
            panelAdminPrestamos.txtBusquedaAdmin.setText("");
        }
        cantPrestamosPag = arrayListPrestamos.size();
        panelAdminPrestamos.txtCantLibroAdmin.setText("Se mostraron " + arrayListPrestamos.size() + " prestamos de: " + cantPrestamos);
        
        
    }
    
}
