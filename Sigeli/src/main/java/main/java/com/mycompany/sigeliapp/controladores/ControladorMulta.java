
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
import main.java.com.mycompany.sigeliapp.vistas.*;


public class ControladorMulta implements ActionListener, MouseListener{
    
    //Dao
    private IDaoMulta iDaoMulta;
    private IDaoLibro iDaoLibro;
    private IDaoPersona iDaoPersona;
    private IDaoPrestamos iDaoPrestamos;
    
    //Vistas
    
    private panelAdminMultas panelAdminMultas;
    private PanelVerMulta panelVerMulta;
    
    //Variables
    private int documentoLogin;
    private String nombre, nombreCorto;
    private boolean busqueda = false;
    private int cantMenorMulta = 0, cantMayorMulta = 4, cantMultaPag = 0;
    
    
    public ControladorMulta() {
        //DAO
        this.iDaoMulta = new DaoMulta();
        this.iDaoLibro = new DaoLibro();
        this.iDaoPersona = new DaoPersona();
        this.iDaoPrestamos = new DaoPrestamos();
        
        //Vistas
        this.panelAdminMultas = new panelAdminMultas();
        this.panelVerMulta = new PanelVerMulta();
        
        //Botones vista
        
        this.panelAdminMultas.btnBusqueda.addActionListener(this);
        this.panelAdminMultas.backPagAdmin.addActionListener(this);
        this.panelAdminMultas.nextPagAdmin.addActionListener(this);
        this.panelAdminMultas.btnPegar.addActionListener(this);
        
        this.panelAdminMultas.btnVolverMenu.addMouseListener(this);
        this.panelAdminMultas.btnCerrarSesion.addMouseListener(this);
        this.panelAdminMultas.btnExtenPanel.addMouseListener(this);
        this.panelAdminMultas.btnExtenPanelOff.addMouseListener(this);
        
        this.panelAdminMultas.panel1.addMouseListener(this);
        this.panelAdminMultas.panel2.addMouseListener(this);
        this.panelAdminMultas.panel3.addMouseListener(this);
        this.panelAdminMultas.panel4.addMouseListener(this);
        this.panelAdminMultas.panel5.addMouseListener(this);
        
        this.panelAdminMultas.adminLibros.addMouseListener(this);
        this.panelAdminMultas.adminUsuarios.addMouseListener(this);
        this.panelAdminMultas.addLibros.addMouseListener(this);
        this.panelAdminMultas.prestamos.addMouseListener(this);
        this.panelAdminMultas.multas.addMouseListener(this);
        this.panelAdminMultas.reportes.addMouseListener(this);
        
        //Vista ver multa
        
        this.panelVerMulta.adminLibros.addMouseListener(this);
        this.panelVerMulta.adminUsuarios.addMouseListener(this);
        this.panelVerMulta.addLibros.addMouseListener(this);
        this.panelVerMulta.prestamos.addMouseListener(this);
        this.panelVerMulta.multas.addMouseListener(this);
        this.panelVerMulta.reportes.addMouseListener(this);
        
        this.panelVerMulta.btnVolverMenu.addMouseListener(this);
        this.panelVerMulta.btnCerrarSesion.addMouseListener(this);
        this.panelVerMulta.btnExtenPanel.addMouseListener(this);
        this.panelVerMulta.btnExtenPanelOff.addMouseListener(this);
        
        this.panelVerMulta.txtDocumentoPersona.addMouseListener(this);
        this.panelVerMulta.txtIsbn.addMouseListener(this);
        this.panelVerMulta.btnPagarMulta.addMouseListener(this);
        this.panelVerMulta.btnCondenar.addMouseListener(this);
        
    }
    
    
    
    //Funciones de implementacion

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == panelAdminMultas.btnBusqueda){
            buscarMultas(panelAdminMultas.txtIngresoBusqueda.getText());
        }
        
        else if(e.getSource() == panelAdminMultas.nextPagAdmin){
             cantMenorMulta = cantMayorMulta;
             cantMayorMulta +=4;
             verMultas(iDaoMulta.verMultas());
        }
        else if(e.getSource() == panelAdminMultas.backPagAdmin){
             
             cantMayorMulta = cantMenorMulta;
             
            if(cantMayorMulta < 4){
                cantMayorMulta = 4;
            }

            cantMenorMulta -=4;
            
           if(cantMenorMulta < 0 && cantMenorMulta > 4){
                cantMenorMulta = 0;
            }

           verMultas(iDaoMulta.verMultas());
           
        }
        
        else if(e.getSource() == panelAdminMultas.btnPegar){
            try {
                panelAdminMultas.txtIngresoBusqueda.setText(Portapapeles.get());
            } catch (Exception ex) {
                Logger.getLogger(ControladorMulta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        //btn Volver
        if(e.getSource() == panelAdminMultas.btnVolverMenu){
            cerrarPanelAdminMultas();
            visiblePanelAdmin();
        }
        
        else if(e.getSource() == panelVerMulta.btnVolverMenu){
            cerrarPanelVerMulta();
            visiblePanelAdmin();
        }
        
        else if(e.getSource() == panelAdminMultas.btnCerrarSesion || e.getSource() == panelVerMulta.btnCerrarSesion){
            ControladorLogin controladorLogin = new ControladorLogin();
            cerrarPanelAdminMultas();
            cerrarPanelVerMulta();
            controladorLogin.visibleLogin();
        }
        
        //Panel extendido
        else if(e.getSource() == panelAdminMultas.btnExtenPanel){
            panelAdminMultas.panelExten.setVisible(true);
            panelAdminMultas.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelAdminMultas.btnExtenPanelOff){
            panelAdminMultas.btnVolverMenu.setVisible(true);
            panelAdminMultas.panelExten.setVisible(false);
        }
        
        else if(e.getSource() == panelVerMulta.btnExtenPanel){
            panelVerMulta.panelExten.setVisible(true);
            panelVerMulta.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelVerMulta.btnExtenPanelOff){
            panelVerMulta.btnVolverMenu.setVisible(true);
            panelVerMulta.panelExten.setVisible(false);
        }
        
        
        //VistaPanelAdminMultas
        
        else if(e.getSource() == panelAdminMultas.panel1){
            verMulta(Integer.parseInt(panelAdminMultas.txtId1.getText()), Integer.parseInt(panelAdminMultas.txtNombre1.getText()));
            cerrarPanelAdminMultas();
            visiblePanelVerMulta();
        }
        else if(e.getSource() == panelAdminMultas.panel2){
            verMulta(Integer.parseInt(panelAdminMultas.txtId2.getText()), Integer.parseInt(panelAdminMultas.txtNombre2.getText()));
            cerrarPanelAdminMultas();
            visiblePanelVerMulta();
        }
        else if(e.getSource() == panelAdminMultas.panel3){
            verMulta(Integer.parseInt(panelAdminMultas.txtId3.getText()), Integer.parseInt(panelAdminMultas.txtNombre3.getText()));
            cerrarPanelAdminMultas();
            visiblePanelVerMulta();
        }
        else if(e.getSource() == panelAdminMultas.panel4){
            verMulta(Integer.parseInt(panelAdminMultas.txtId4.getText()), Integer.parseInt(panelAdminMultas.txtNombre4.getText()));
            cerrarPanelAdminMultas();
            visiblePanelVerMulta();
        }
        else if(e.getSource() == panelAdminMultas.panel5){
            verMulta(Integer.parseInt(panelAdminMultas.txtId5.getText()), Integer.parseInt(panelAdminMultas.txtNombre5.getText()));
            cerrarPanelAdminMultas();
            visiblePanelVerMulta();
        }
        
        //Panel ver multa
        
        else if(e.getSource() == panelVerMulta.txtDocumentoPersona){
            
            Portapapeles.copiar(panelVerMulta.txtDocumentoPersona.getText());
            JOptionPane.showMessageDialog(null, "Documento copiado con éxito");
        }
        
        else if(e.getSource() == panelVerMulta.txtIsbn){
            
            Portapapeles.copiar(panelVerMulta.txtIsbn.getText());
            JOptionPane.showMessageDialog(null, "ISBN copiado con éxito");
        }
        
        else if(e.getSource() == panelVerMulta.btnPagarMulta){
            pagarMulta(panelVerMulta.txtDocumentoPersona.getText(), panelVerMulta.txtIsbn.getText(), 5);
            cerrarPanelVerMulta();
            visiblePanelAdmin();
        }
        
        else if(e.getSource() == panelVerMulta.btnCondenar){
            pagarMulta(panelVerMulta.txtDocumentoPersona.getText(), panelVerMulta.txtIsbn.getText(), 7);
            cerrarPanelVerMulta();
            visiblePanelAdmin();
        }
        
        
        //OPCIONES PANEL EXTENDIDO
        else if(e.getSource() == panelAdminMultas.adminLibros || e.getSource() == panelVerMulta.adminLibros){
            cerrarPanelAdminMultas();
            ControladorLibro controladorLibro = new ControladorLibro();
            controladorLibro.inicio(documentoLogin, nombre);
        }
        
        else if(e.getSource() == panelAdminMultas.adminUsuarios || e.getSource() == panelVerMulta.adminUsuarios){
            cerrarPanelAdminMultas();
            
            ControladorPersona controladorPersona = new ControladorPersona();
            controladorPersona.inicio(documentoLogin, nombre);
            
        }
        
        else if(e.getSource() == panelAdminMultas.addLibros || e.getSource() == panelVerMulta.addLibros){
            cerrarPanelAdminMultas();

            
            ControladorLibro controladorLibro = new ControladorLibro();
            
            controladorLibro.inicioAddLibros(documentoLogin, nombre);
        }
        
        //Sin controladores
        
        else if(e.getSource() == panelAdminMultas.prestamos || e.getSource() == panelVerMulta.prestamos){
            cerrarPanelAdminMultas();
            
            ControladorPrestamos controladorPrestamos = new ControladorPrestamos();
            controladorPrestamos.inicio(documentoLogin, nombre);

        }
        
        else if(e.getSource() == panelAdminMultas.multas || e.getSource() == panelVerMulta.multas){
            cerrarPanelAdminMultas();
            
            visiblePanelAdminMultas();


        }
        
        else if(e.getSource() == panelAdminMultas.reportes || e.getSource() == panelVerMulta.reportes){
            
            cerrarPanelAdminMultas();
            
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
    
    
    
    
    
    //Funciones de multas
    
    public void verMulta(int idPrestamo, int documento){
        for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
            for(Persona persona : iDaoPersona.verPersonas()){
                for(Multa multa : iDaoMulta.verMultas()){
                   if(prestamo.getIdPrestamo() == idPrestamo && persona.getDocumentoPersona() == documento){
                        panelVerMulta.txtNombre.setText(persona.getNombrePersona());
                        panelVerMulta.txtDocumentoPersona.setText(String.valueOf(persona.getDocumentoPersona()));
                        panelVerMulta.txtIsbn.setText(prestamo.getIsbnLibro());
                        panelVerMulta.txtFechaPrestamo.setText(String.valueOf(prestamo.getFechaPrestamo()));
                        panelVerMulta.txtValorMulta.setText(String.valueOf(multa.getValorMulta()));
                    } 
                }
                
            }
        }
    }
    
    public void verMultas(ArrayList<Multa> arrayListMulta){
        ArrayList<Multa> arrayListMultasEnviar = new ArrayList<>();
        int cant = cantMenorMulta, cantMultas= 0;


        for(Multa multa : arrayListMulta){

            if(multa.getEstadoMulta() == 6){
                if(cantMayorMulta > arrayListMulta.size()){
                    cantMayorMulta = arrayListMulta.size();
                }
                if(cantMenorMulta < 0){
                    cantMenorMulta = 0;
                }
                if(cantMultas <= cantMayorMulta  && cantMultas >= cantMenorMulta){
                    arrayListMultasEnviar.add(multa);
                    cantMenorMulta +=1;
                }
                cantMultas +=1;
            }  

        }
        
        cantMenorMulta = cant;
        
        setDatosMulta(arrayListMultasEnviar, arrayListMultasEnviar.size(), "");
        this.busqueda = false;
    }
    
    public void buscarMultas(String busqueda){

        ArrayList<Multa> arrayListMulta = new ArrayList<>();
        
        if(busqueda.equals("")){
            cantMayorMulta =4;
            cantMenorMulta = 0;
            this.busqueda = false;
            verMultas(iDaoMulta.verMultas());  
        }
        else{
            cantMayorMulta =4;
            cantMenorMulta = 0;
            
            for(Multa multa : iDaoMulta.verMultas()){
                    for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
                        if(prestamo.getIsbnLibro().equals(busqueda) && multa.getIdPrestamo() == prestamo.getIdPrestamo()){
                                if(arrayListMulta.size() <= 4){
                                    arrayListMulta.add(multa);
                                }
                            }
                    }
                    
                    if(String.valueOf(multa.getDocumentoPersona()).equals(String.valueOf(busqueda))){
                        if(arrayListMulta.size() <= 4){
                            arrayListMulta.add(multa);
                        }
                    }

            }
            
            
            
            this.busqueda = true;
            setDatosMulta(arrayListMulta, arrayListMulta.size(), busqueda);
        }
    }
    
    public void pagarMulta(String documento, String isbn, int estado){
        
        
        for(Multa multa : iDaoMulta.verMultas()){
            for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
                if(multa.getIdPrestamo() == prestamo.getIdPrestamo() && multa.getDocumentoPersona() == Integer.parseInt(documento)){
                    if(prestamo.getIsbnLibro().equals(isbn)){
                        multa.setEstadoMulta(estado);
                        if(estado == 7){
                            multa.setValorMulta(0);
                        }
                        multa.setFecha(Date.valueOf(LocalDate.now()));
                        if(iDaoMulta.pagarMulta(multa.getIdMulta(), multa)){
                            JOptionPane.showMessageDialog(null, "La multa ha sido pagada con éxito");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
                        }
                    }
                }
            }
        }
        
    }
    
    
    //Vistas
    
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
        
        panelAdminMultas.txtNombrePersona.setText(this.nombreCorto);
        panelAdminMultas.txtNombrePersona1.setText(this.nombre);
        panelAdminMultas.panelExten.setVisible(false);

        verMultas(iDaoMulta.verMultas());
        visiblePanelAdminMultas();
        
    }
    
    public void visiblePanelAdminMultas(){
        panelAdminMultas.setTitle("Panel Administrar Multas - Sigeli");
        this.busqueda = false;
        panelAdminMultas.txtBusquedaAdmin.setText("");
        panelAdminMultas.setLocationRelativeTo(null);
        panelAdminMultas.setResizable(false);
        panelAdminMultas.setVisible(true);
    }
    
    public void cerrarPanelAdminMultas(){
        panelAdminMultas.setVisible(false);
    }
    
    public void visiblePanelAdmin(){
        ControladorPanelAdmin controladorPanelAdmin = new ControladorPanelAdmin();
        
        controladorPanelAdmin.inicio(documentoLogin, iDaoPersona.verPersonas());
    }
    
    public void visiblePanelVerMulta(){
        panelVerMulta.setTitle("Detalles de la multa - Sigeli");
        panelVerMulta.txtNombrePersona.setText(this.nombreCorto);
        panelVerMulta.txtNombrePersona1.setText(this.nombre);
        panelVerMulta.panelExten.setVisible(false);
        panelVerMulta.setLocationRelativeTo(null);
        panelVerMulta.setResizable(false);
        panelVerMulta.setVisible(true);
    }
    
    public void cerrarPanelVerMulta(){
        panelVerMulta.setVisible(false);
    }
    
    
    public void setDatosMulta(ArrayList<Multa> arrayListMulta, int cantMultas, String busqueda){
        
        if(this.busqueda || arrayListMulta.size() <= 4){
            panelAdminMultas.nextPagAdmin.setVisible(false);
            panelAdminMultas.backPagAdmin.setVisible(false);
        }
        else{
            panelAdminMultas.nextPagAdmin.setVisible(true);
            panelAdminMultas.backPagAdmin.setVisible(true);
        }
        
        if(arrayListMulta.size() == 0){
           panelAdminMultas.panel1.setVisible(false);
           panelAdminMultas.panel2.setVisible(false);
           panelAdminMultas.panel3.setVisible(false);
           panelAdminMultas.panel4.setVisible(false);
           panelAdminMultas.panel5.setVisible(false);
           panelAdminMultas.nextPagAdmin.setVisible(false);
           panelAdminMultas.backPagAdmin.setVisible(false);
           busqueda += "\" - No se han encontrado resultados";
        }
        
        else if(arrayListMulta.size() == 1){
            

            
            panelAdminMultas.nextPagAdmin.setVisible(false);
            if(cantMenorMulta == 0){
                panelAdminMultas.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminMultas.panel1.setVisible(true);
           panelAdminMultas.panel2.setVisible(false);
           panelAdminMultas.panel3.setVisible(false);
           panelAdminMultas.panel4.setVisible(false);
           panelAdminMultas.panel5.setVisible(false);
           //nombre
           panelAdminMultas.txtNombre1.setText(String.valueOf(arrayListMulta.get(0).getDocumentoPersona()));
           //documento
           panelAdminMultas.txtDocumento1.setText((arrayListMulta.get(0).getEstadoMulta() == 5)? "Pago" : "No pago");
           //fecha prestamo
           panelAdminMultas.txtId1.setText("" + String.valueOf(arrayListMulta.get(0).getIdPrestamo()));
           //fecha entrega
           panelAdminMultas.txtPrecio1.setText("Valor: $" + String.valueOf(arrayListMulta.get(0).getValorMulta()));
           
        }
        
        else if(arrayListMulta.size() == 2){
            
            panelAdminMultas.panel1.setVisible(true);
           panelAdminMultas.panel2.setVisible(true);
           panelAdminMultas.panel3.setVisible(false);
           panelAdminMultas.panel4.setVisible(false);
           panelAdminMultas.panel5.setVisible(false);
           //nombre
           panelAdminMultas.txtNombre1.setText(String.valueOf(arrayListMulta.get(0).getDocumentoPersona()));
           panelAdminMultas.txtNombre2.setText(String.valueOf(arrayListMulta.get(1).getDocumentoPersona()));
           //documento
           panelAdminMultas.txtDocumento1.setText((arrayListMulta.get(0).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento2.setText((arrayListMulta.get(1).getEstadoMulta() == 5)? "Pago" : "No pago");
           //fecha prestamo
           panelAdminMultas.txtId1.setText("" + String.valueOf(arrayListMulta.get(0).getIdPrestamo()));
           panelAdminMultas.txtId2.setText("" + String.valueOf(arrayListMulta.get(1).getIdPrestamo()));
           //fecha entrega
           panelAdminMultas.txtPrecio1.setText("Valor: $" + String.valueOf(arrayListMulta.get(0).getValorMulta()));
           panelAdminMultas.txtPrecio2.setText("Valor: $" + String.valueOf(arrayListMulta.get(1).getValorMulta()));
           
        }
        
        else if(arrayListMulta.size() == 3){
            
            panelAdminMultas.panel1.setVisible(true);
           panelAdminMultas.panel2.setVisible(true);
           panelAdminMultas.panel3.setVisible(true);
           panelAdminMultas.panel4.setVisible(false);
           panelAdminMultas.panel5.setVisible(false);
           //nombre
           panelAdminMultas.txtNombre1.setText(String.valueOf(arrayListMulta.get(0).getDocumentoPersona()));
           panelAdminMultas.txtNombre2.setText(String.valueOf(arrayListMulta.get(1).getDocumentoPersona()));
           panelAdminMultas.txtNombre3.setText(String.valueOf(arrayListMulta.get(2).getDocumentoPersona()));
           //documento
           panelAdminMultas.txtDocumento1.setText((arrayListMulta.get(0).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento2.setText((arrayListMulta.get(1).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento3.setText((arrayListMulta.get(2).getEstadoMulta() == 5)? "Pago" : "No pago");
           //fecha prestamo
           panelAdminMultas.txtId1.setText("" + String.valueOf(arrayListMulta.get(0).getIdPrestamo()));
           panelAdminMultas.txtId2.setText("" + String.valueOf(arrayListMulta.get(1).getIdPrestamo()));
           panelAdminMultas.txtId3.setText("" + String.valueOf(arrayListMulta.get(2).getIdPrestamo()));
           //fecha entrega
           panelAdminMultas.txtPrecio1.setText("Valor: $" + String.valueOf(arrayListMulta.get(0).getValorMulta()));
           panelAdminMultas.txtPrecio2.setText("Valor: $" + String.valueOf(arrayListMulta.get(1).getValorMulta()));
           panelAdminMultas.txtPrecio3.setText("Valor: $" + String.valueOf(arrayListMulta.get(2).getValorMulta()));
           
           
        }
        
        else if(arrayListMulta.size() == 4){
            
            panelAdminMultas.panel1.setVisible(true);
           panelAdminMultas.panel2.setVisible(true);
           panelAdminMultas.panel3.setVisible(true);
           panelAdminMultas.panel4.setVisible(true);
           panelAdminMultas.panel5.setVisible(false);
           //nombre
           panelAdminMultas.txtNombre1.setText(String.valueOf(arrayListMulta.get(0).getDocumentoPersona()));
           panelAdminMultas.txtNombre2.setText(String.valueOf(arrayListMulta.get(1).getDocumentoPersona()));
           panelAdminMultas.txtNombre3.setText(String.valueOf(arrayListMulta.get(2).getDocumentoPersona()));
           panelAdminMultas.txtNombre4.setText(String.valueOf(arrayListMulta.get(3).getDocumentoPersona()));
           //documento
           panelAdminMultas.txtDocumento1.setText((arrayListMulta.get(0).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento2.setText((arrayListMulta.get(1).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento3.setText((arrayListMulta.get(2).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento4.setText((arrayListMulta.get(3).getEstadoMulta() == 5)? "Pago" : "No pago");
           //fecha prestamo
           panelAdminMultas.txtId1.setText("" + String.valueOf(arrayListMulta.get(0).getIdPrestamo()));
           panelAdminMultas.txtId2.setText("" + String.valueOf(arrayListMulta.get(1).getIdPrestamo()));
           panelAdminMultas.txtId3.setText("" + String.valueOf(arrayListMulta.get(2).getIdPrestamo()));
           panelAdminMultas.txtId4.setText("" + String.valueOf(arrayListMulta.get(3).getIdPrestamo()));
           //fecha entrega
           panelAdminMultas.txtPrecio1.setText("Valor: $" + String.valueOf(arrayListMulta.get(0).getValorMulta()));
           panelAdminMultas.txtPrecio2.setText("Valor: $" + String.valueOf(arrayListMulta.get(1).getValorMulta()));
           panelAdminMultas.txtPrecio3.setText("Valor: $" + String.valueOf(arrayListMulta.get(2).getValorMulta()));
           panelAdminMultas.txtPrecio4.setText("Valor: $" + String.valueOf(arrayListMulta.get(3).getValorMulta()));
           
        }
        
        else if(arrayListMulta.size() == 5){
            
            panelAdminMultas.panel1.setVisible(true);
           panelAdminMultas.panel2.setVisible(true);
           panelAdminMultas.panel3.setVisible(true);
           panelAdminMultas.panel4.setVisible(true);
           panelAdminMultas.panel5.setVisible(true);
           //nombre
           panelAdminMultas.txtNombre1.setText(String.valueOf(arrayListMulta.get(0).getDocumentoPersona()));
           panelAdminMultas.txtNombre2.setText(String.valueOf(arrayListMulta.get(1).getDocumentoPersona()));
           panelAdminMultas.txtNombre3.setText(String.valueOf(arrayListMulta.get(2).getDocumentoPersona()));
           panelAdminMultas.txtNombre4.setText(String.valueOf(arrayListMulta.get(3).getDocumentoPersona()));
           panelAdminMultas.txtNombre5.setText(String.valueOf(arrayListMulta.get(4).getDocumentoPersona()));
           //documento
           panelAdminMultas.txtDocumento1.setText((arrayListMulta.get(0).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento2.setText((arrayListMulta.get(1).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento3.setText((arrayListMulta.get(2).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento4.setText((arrayListMulta.get(3).getEstadoMulta() == 5)? "Pago" : "No pago");
           panelAdminMultas.txtDocumento5.setText((arrayListMulta.get(4).getEstadoMulta() == 5)? "Pago" : "No pago");
           //fecha prestamo
           panelAdminMultas.txtId1.setText("" + String.valueOf(arrayListMulta.get(0).getIdPrestamo()));
           panelAdminMultas.txtId2.setText("" + String.valueOf(arrayListMulta.get(1).getIdPrestamo()));
           panelAdminMultas.txtId3.setText("" + String.valueOf(arrayListMulta.get(2).getIdPrestamo()));
           panelAdminMultas.txtId4.setText("" + String.valueOf(arrayListMulta.get(3).getIdPrestamo()));
           panelAdminMultas.txtId5.setText("" + String.valueOf(arrayListMulta.get(4).getIdPrestamo()));
           //fecha entrega
           panelAdminMultas.txtPrecio1.setText("Valor: $" + String.valueOf(arrayListMulta.get(0).getValorMulta()));
           panelAdminMultas.txtPrecio2.setText("Valor: $" + String.valueOf(arrayListMulta.get(1).getValorMulta()));
           panelAdminMultas.txtPrecio3.setText("Valor: $" + String.valueOf(arrayListMulta.get(2).getValorMulta()));
           panelAdminMultas.txtPrecio4.setText("Valor: $" + String.valueOf(arrayListMulta.get(3).getValorMulta()));
           panelAdminMultas.txtPrecio5.setText("Valor: $" + String.valueOf(arrayListMulta.get(4).getValorMulta()));
           
        }
        
        
        if(!busqueda.equals("")){
            panelAdminMultas.txtBusquedaAdmin.setText("Se muestran resultados para \"" + busqueda + "\"");
        }
        else{
            panelAdminMultas.txtBusquedaAdmin.setText("");
        }
        cantMultaPag = arrayListMulta.size();
        panelAdminMultas.txtCantLibroAdmin.setText("Se mostraron " + arrayListMulta.size() + " prestamos de: " + cantMultaPag);
        
        
    }
    
    
    
}
