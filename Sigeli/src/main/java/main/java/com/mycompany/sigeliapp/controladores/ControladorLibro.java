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
import main.java.com.mycompany.sigeliapp.dao.*;
import main.java.com.mycompany.sigeliapp.modelos.*;
import main.java.com.mycompany.sigeliapp.modelos.utiles.Portapapeles;
import main.java.com.mycompany.sigeliapp.validaciones.ValidacionLibros;
import main.java.com.mycompany.sigeliapp.vistas.*;

public class ControladorLibro  implements ActionListener, MouseListener{
    
    private VistaApp vistaApp;
    
    private panelAdminLibros panelAdminLibros;
    private PanelVistaLibro panelVistaLibro;
    private PanelAddLibro panelAddLibro;
    
    
    private IDaoCategoria iDaoCategoria;
    private IDaoCategoriaLibro iDaoCategoriaLibro;
    private IDaoLibro iDaoLibro;
    private IDaoPersona iDaoPersona;
    
    private int documentoLogin;
    private String nombre, nombreCorto;
    private boolean busqueda = false;
    private int cantMenorLibro = 0, cantMayorLibro = 4, cantLibrosPag = 0;

    public ControladorLibro(){
    
        this.panelAdminLibros = new panelAdminLibros();
        this.panelVistaLibro = new PanelVistaLibro();
        this.panelAddLibro = new PanelAddLibro();
        
        this.vistaApp = new VistaApp();

        this.iDaoCategoria = new DaoCategoria();
        this.iDaoCategoriaLibro = new DaoCategoriaLibro();
        this.iDaoLibro = new DaoLibro();
        this.iDaoPersona = new DaoPersona();
        
        //Acciones del panel admin libros
        this.panelAdminLibros.btnPegar.addActionListener(this);
        this.panelAdminLibros.btnBusqueda.addActionListener(this);
        this.panelAdminLibros.nextPagAdmin.addActionListener(this);
        this.panelAdminLibros.backPagAdmin.addActionListener(this);
        
        
        this.panelAdminLibros.btnExtenPanel.addMouseListener(this);
        this.panelAdminLibros.btnExtenPanelOff.addMouseListener(this);
        this.panelAdminLibros.btnVolverMenu.addMouseListener(this);
        this.panelAdminLibros.btnCerrarSesion.addMouseListener(this);
        
        this.panelAdminLibros.panel1.addMouseListener(this);
        this.panelAdminLibros.panel2.addMouseListener(this);
        this.panelAdminLibros.panel3.addMouseListener(this);
        this.panelAdminLibros.panel4.addMouseListener(this);
        this.panelAdminLibros.panel5.addMouseListener(this);
        
        //Acciones del panel de ver libros
        
        this.panelVistaLibro.btnExtenPanel.addMouseListener(this);
        this.panelVistaLibro.btnExtenPanelOff.addMouseListener(this);
        this.panelVistaLibro.btnVolverMenu.addMouseListener(this);
        this.panelVistaLibro.btnCerrarSesion.addMouseListener(this);
        this.panelVistaLibro.btnActualizarLibro.addActionListener(this);
        this.panelVistaLibro.btnEliminarLibro.addActionListener(this);
        this.panelVistaLibro.btnPrestarLibro.addActionListener(this);
        
        
        this.panelVistaLibro.txtISBN.addMouseListener(this);
        
        //panel ADD libro
        
        this.panelAddLibro.btnAddlibro.addActionListener(this);
        this.panelAddLibro.btnCerrarSesion.addMouseListener(this);
        this.panelAddLibro.btnExtenPanel.addMouseListener(this);
        this.panelAddLibro.btnExtenPanelOff.addMouseListener(this);
        this.panelAddLibro.btnVolverMenu.addMouseListener(this);
        this.panelAddLibro.btnCerrarSesion.addMouseListener(this);
        
        //Acciones del panel extendido
        
        this.panelAdminLibros.adminLibros.addMouseListener(this);
        this.panelAdminLibros.adminUsuarios.addMouseListener(this);
        this.panelAdminLibros.addLibro.addMouseListener(this);
        this.panelAdminLibros.prestamos.addMouseListener(this);
        this.panelAdminLibros.multas.addMouseListener(this);
        this.panelAdminLibros.reportes.addMouseListener(this);
        
        
        this.panelVistaLibro.adminLibros.addMouseListener(this);
        this.panelVistaLibro.adminUsuarios.addMouseListener(this);
        this.panelVistaLibro.addLibros.addMouseListener(this);
        this.panelVistaLibro.prestamos.addMouseListener(this);
        this.panelVistaLibro.multas.addMouseListener(this);
        this.panelVistaLibro.reportes.addMouseListener(this);
        
        
        this.panelAddLibro.adminLibros.addMouseListener(this);
        this.panelAddLibro.adminUsuarios.addMouseListener(this);
        this.panelAddLibro.addLibros.addMouseListener(this);
        this.panelAddLibro.prestamos.addMouseListener(this);
        this.panelAddLibro.multas.addMouseListener(this);
        this.panelAddLibro.reportes.addMouseListener(this);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Panel administrador de libros
        if(e.getSource() == panelAdminLibros.btnCerrarSesion || e.getSource() == panelVistaLibro.btnCerrarSesion || e.getSource() == panelAddLibro.btnCerrarSesion){
            ControladorLogin controladorLogin = new ControladorLogin();
            cerrarPanelAddLibro();
            cerrarVerLibro();
            cerrarPanelAdminLibros();
            controladorLogin.visibleLogin();
        }
        else if(e.getSource() == panelAdminLibros.btnVolverMenu || e.getSource() == panelAddLibro.btnVolverMenu){
            cerrarPanelAddLibro();
            cerrarPanelAdminLibros();
            visiblePanelAdmin();
        }

        else if(e.getSource() == panelAdminLibros.btnExtenPanel){
            panelAdminLibros.panelExten.setVisible(true);
            panelAdminLibros.btnVolverMenu.setVisible(false);
        }
        else if(e.getSource() == panelAdminLibros.btnExtenPanelOff){
            panelAdminLibros.btnVolverMenu.setVisible(true);
            panelAdminLibros.panelExten.setVisible(false);
        }
        
        
        //Panel vista
        else if(e.getSource() == panelVistaLibro.btnVolverMenu){
            cerrarVerLibro();
            visiblePanelAdminLibros();
        }
        
        else if(e.getSource() == panelVistaLibro.btnExtenPanel){
            panelVistaLibro.panelExten.setVisible(true);
            panelVistaLibro.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelVistaLibro.btnExtenPanelOff){
            panelVistaLibro.btnVolverMenu.setVisible(true);
            panelVistaLibro.panelExten.setVisible(false);
        }
        
        
        //Portapapeles
        else if(e.getSource() == panelVistaLibro.txtISBN){
            Portapapeles portapapeles = new Portapapeles();
            portapapeles.copiar(panelVistaLibro.txtISBN.getText());
            JOptionPane.showMessageDialog(null, "ISBN copiado al portapapeles");
        }
        
        
        
        //Paneles
        else if (e.getSource() == panelAdminLibros.panel1){
            verLibro(panelAdminLibros.txtNombreLibro1.getText());
            cerrarPanelAdminLibros();
            visibleVerLibro();
        }
        else if (e.getSource() == panelAdminLibros.panel2){
            verLibro(panelAdminLibros.txtNombreLibro2.getText());
            cerrarPanelAdminLibros();
            visibleVerLibro();
        }
        else if (e.getSource() == panelAdminLibros.panel3){
            verLibro(panelAdminLibros.txtNombreLibro3.getText());
            cerrarPanelAdminLibros();
            visibleVerLibro();
        }
        else if (e.getSource() == panelAdminLibros.panel4){
            verLibro(panelAdminLibros.txtNombreLibro4.getText());
            cerrarPanelAdminLibros();
            visibleVerLibro();
        }
        else if (e.getSource() == panelAdminLibros.panel5){
            verLibro(panelAdminLibros.txtNombreLibro5.getText());
            cerrarPanelAdminLibros();
            visibleVerLibro();
        }
        
        //Opciones panel Extendido
        
        else if(e.getSource() == panelVistaLibro.adminLibros || e.getSource() == panelAdminLibros.adminLibros || e.getSource() == panelAddLibro.adminLibros){
            cerrarPanelAddLibro();
            cerrarPanelAdminLibros();
            cerrarVerLibro();
            visiblePanelAdminLibros();
        }
        
        else if(e.getSource() == panelVistaLibro.adminUsuarios || e.getSource() == panelAdminLibros.adminUsuarios || e.getSource() == panelAddLibro.adminUsuarios){
            cerrarPanelAddLibro();
            cerrarPanelAdminLibros();
            cerrarVerLibro();
            
            ControladorPersona controladorPersona = new ControladorPersona();
            controladorPersona.inicio(documentoLogin, nombre);
            
        }
        
        else if(e.getSource() == panelVistaLibro.addLibros || e.getSource() == panelAdminLibros.addLibro || e.getSource() == panelAddLibro.addLibros){
            cerrarPanelAddLibro();
            cerrarPanelAdminLibros();
            cerrarVerLibro();
            inicioAddLibros(documentoLogin, nombre);
        }
        
        //Sin controladores
        
        else if(e.getSource() == panelVistaLibro.prestamos || e.getSource() == panelAdminLibros.prestamos || e.getSource() == panelAddLibro.prestamos){
            cerrarPanelAddLibro();
            cerrarPanelAdminLibros();
            cerrarVerLibro();
            
            ControladorPrestamos controladorPrestamos = new ControladorPrestamos();
            controladorPrestamos.inicio(documentoLogin, nombre);

        }
        
        else if(e.getSource() == panelVistaLibro.multas || e.getSource() == panelAdminLibros.multas || e.getSource() == panelAddLibro.multas){
            cerrarPanelAddLibro();
            cerrarPanelAdminLibros();
            cerrarVerLibro();
            
            ControladorMulta controladorMulta = new ControladorMulta();
            controladorMulta.inicio(documentoLogin, nombre);

        }
        
        else if(e.getSource() == panelVistaLibro.reportes || e.getSource() == panelAdminLibros.reportes || e.getSource() == panelAddLibro.reportes){
            cerrarPanelAddLibro();
            cerrarPanelAdminLibros();
            cerrarVerLibro();
            
            ControladorReportes controladorReportes = new ControladorReportes();
            
            controladorReportes.inicio(documentoLogin, nombre);

        }
        
        
        //opciones panel Add libros
        
        else if(e.getSource() == panelAddLibro.btnExtenPanel){
            panelAddLibro.panelExten.setVisible(true);
            panelAddLibro.btnVolverMenu.setVisible(false);
        }
        else if(e.getSource() == panelAddLibro.btnExtenPanelOff){
            panelAddLibro.btnVolverMenu.setVisible(true);
            panelAddLibro.panelExten.setVisible(false);
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
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == panelAdminLibros.btnBusqueda){
            busquedaLibro(iDaoLibro.verLibros(), iDaoCategoriaLibro.verCategoriaLibros(), iDaoCategoria.verCategorias());
        }
        else if(e.getSource() == panelAdminLibros.btnPegar){
            try {
                panelAdminLibros.txtIngresoBusqueda.setText(Portapapeles.get());
            } catch (Exception ex) {
                Logger.getLogger(ControladorLibro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        else if(e.getSource() == panelAdminLibros.nextPagAdmin){
             cantMenorLibro = cantMayorLibro;
             cantMayorLibro +=4;
             verLibros(iDaoLibro.verLibros());
        }
        else if(e.getSource() == panelAdminLibros.backPagAdmin){
             
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
        
        //Opciones de vista del libro
        else if(e.getSource() == panelVistaLibro.btnEliminarLibro){
            eliminarLibro();
        }
        
        else if(e.getSource() == panelVistaLibro.btnActualizarLibro){
            modificarLibros(iDaoLibro.verLibros());
        }
        
        //opciones de vista Add libro
        
        else if(e.getSource() == panelAddLibro.btnAddlibro){
            addLibro();
            cerrarPanelAddLibro();
            visiblePanelAdmin();
        }
        
        else if(e.getSource() == panelVistaLibro.btnPrestarLibro){
            cerrarVerLibro();
            ControladorPrestamos controladorPrestamos = new ControladorPrestamos();
            controladorPrestamos.inicioCrearPrestamo(documentoLogin, nombre, panelVistaLibro.txtISBN.getText());
        }

    }
    
    
    
    
    
    
    
    

    //funciones de libro
    
    //con DAO
    public void busquedaLibro(ArrayList<Libro> arrayListLibro, ArrayList<CategoriaLibro> arrayListCategoriaLibro, ArrayList<Categoria> arrayListCategoria) {

        ArrayList<Libro> arrayListLibroEnviar = new ArrayList<>();
        String busqueda = panelAdminLibros.txtIngresoBusqueda.getText();

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

    //con DAO
    public void modificarLibros(ArrayList<Libro> arrayListLibro) {

        for (Libro libro : arrayListLibro) {


            if (libro.getIsbnLibro().equals(panelVistaLibro.txtISBN.getText())) {

                    libro.setNombreLibro(panelVistaLibro.txtNombreLibro.getText());
                    libro.setNombreAutor(panelVistaLibro.txtAutor.getText());
                    libro.setEdicionLibro(Integer.parseInt(panelVistaLibro.txtEdicion.getText()));
                    libro.setAnoLibro(Integer.parseInt(panelVistaLibro.txtAno.getText()));
                    libro.setEstanteLibro(panelVistaLibro.txtEstante.getText());
                    libro.setFilaLibro(Integer.parseInt(panelVistaLibro.txtFila.getText()));

                    if(iDaoLibro.modificarLibro(libro, panelVistaLibro.txtISBN.getText())){
                        JOptionPane.showMessageDialog(null, "El libro ha sido actualizado con éxito");
                        cerrarVerLibro();
                        visiblePanelAdmin();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "El libro no se ha podido modificar", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE );
                    }
            }
        }

    }
        
    
    public void eliminarLibro(){
        if(iDaoLibro.eliminarLibro(panelVistaLibro.txtISBN.getText())){
            JOptionPane.showMessageDialog(null, "El libro ha sido eliminado con éxito");
            cerrarVerLibro();
            visiblePanelAdmin();
        }
        else{
            JOptionPane.showMessageDialog(null, "El libro no se ha podido eliminar", "Ha ocurrido un error", JOptionPane.ERROR_MESSAGE );
        }
    }

    public void verLibro(String nombre){
        for(Libro libro : iDaoLibro.verLibros()){
                if(libro.getNombreLibro().equals(nombre)){
                    panelVistaLibro.txtNombreLibro.setText(libro.getNombreLibro());
                    panelVistaLibro.txtISBN.setText(libro.getIsbnLibro());
                    panelVistaLibro.txtAutor.setText(libro.getNombreAutor());
                    panelVistaLibro.txtEstante.setText(libro.getEstanteLibro());
                    panelVistaLibro.txtEdicion.setText(String.valueOf(libro.getEdicionLibro()));
                    panelVistaLibro.txtAno.setText(String.valueOf(libro.getAnoLibro()));
                    panelVistaLibro.txtFila.setText(String.valueOf(libro.getFilaLibro()));
                    if(libro.getIdEstado() == 0){
                        panelVistaLibro.txtDisponibilidad.setForeground(new java.awt.Color(153,0,0));
                        panelVistaLibro.txtDisponibilidad.setText("El libro no está disponible");
                        panelVistaLibro.btnPrestarLibro.setVisible(false);
                    }
                    else{
                        panelVistaLibro.txtDisponibilidad.setForeground(new java.awt.Color(0,204,0));
                        panelVistaLibro.txtDisponibilidad.setText("El libro está disponible");
                        panelVistaLibro.btnPrestarLibro.setVisible(true);
                    }
                }
            }
    }
    
    public void addLibro(){
        
        Libro libro = new Libro();
        
        libro.setNombreLibro(panelAddLibro.txtNombreLibro.getText());
        libro.setIsbnLibro(panelAddLibro.txtIsbn.getText());
        libro.setNombreAutor(panelAddLibro.txtAutor.getText());
        libro.setEdicionLibro(Integer.parseInt(panelAddLibro.txtEdicion.getText()));
        libro.setAnoLibro(Integer.parseInt(panelAddLibro.txtAno.getText()));
        libro.setEstanteLibro(panelAddLibro.txtEstante.getText());
        libro.setFilaLibro(Integer.parseInt(panelAddLibro.txtFila.getText()));
        
        if(ValidacionLibros.crearLibro(libro)){
            if(iDaoLibro.addLibro(libro)){
                JOptionPane.showMessageDialog(null, "Libro añadido con éxito");
            }
            else{
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Verifique los datos");
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
        
        panelAdminLibros.txtNombrePersona.setText(nombreCorto);
        panelAdminLibros.txtNombrePersona1.setText(nombre);
        panelAdminLibros.panelExten.setVisible(false);
        verLibros(iDaoLibro.verLibros());
        
        visiblePanelAdminLibros();
    }
    
    public void inicioAddLibros(int documento, String nombre){
        
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
        
        panelAddLibro.txtNombrePersona.setText(nombreCorto);
        panelAddLibro.txtNombrePersona1.setText(nombre);
        panelAddLibro.panelExten.setVisible(false);
        
        visiblePanelAddLibro();
    }
    
    
    public void setDatosLibro(ArrayList<Libro> arrayListLibro, int cantLibros, String busqueda){
        
        if(this.busqueda){
            panelAdminLibros.nextPagAdmin.setVisible(false);
            panelAdminLibros.backPagAdmin.setVisible(false);
        }
        else{
            panelAdminLibros.nextPagAdmin.setVisible(true);
            panelAdminLibros.backPagAdmin.setVisible(true);
        }
        
        if(arrayListLibro.size() == 0){
           panelAdminLibros.panel1.setVisible(false);
           panelAdminLibros.panel2.setVisible(false);
           panelAdminLibros.panel3.setVisible(false);
           panelAdminLibros.panel4.setVisible(false);
           panelAdminLibros.panel5.setVisible(false);
           busqueda += "\" - No se han encontrado resultados";
        }
        else if(arrayListLibro.size() == 1){
            panelAdminLibros.nextPagAdmin.setVisible(false);
            //paneles
           panelAdminLibros.panel1.setVisible(true);
           panelAdminLibros.panel2.setVisible(false);
           panelAdminLibros.panel3.setVisible(false);
           panelAdminLibros.panel4.setVisible(false);
           panelAdminLibros.panel5.setVisible(false);
           //nombre libro
           panelAdminLibros.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           //nombre Autor
           panelAdminLibros.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           //año del libro
           panelAdminLibros.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           //diponibilidad
           panelAdminLibros.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No dispinoble"));
           //Estante
           panelAdminLibros.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           //Fila
           panelAdminLibros.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
        }
        else if(arrayListLibro.size() == 2){
            panelAdminLibros.nextPagAdmin.setVisible(false);
            //paneles
           panelAdminLibros.panel1.setVisible(true);
           panelAdminLibros.panel2.setVisible(true);
           panelAdminLibros.panel3.setVisible(false);
           panelAdminLibros.panel4.setVisible(false);
           panelAdminLibros.panel5.setVisible(false);
           //nombre libro
           panelAdminLibros.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           panelAdminLibros.txtNombreLibro2.setText(arrayListLibro.get(1).getNombreLibro());
           //nombre Autor
           panelAdminLibros.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           panelAdminLibros.txtNombreAutor2.setText("Autor: " +arrayListLibro.get(1).getNombreAutor());
           //año del libro
           panelAdminLibros.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           panelAdminLibros.txtAnoLibro2.setText("Año: " + String.valueOf(arrayListLibro.get(1).getAnoLibro()));
           //diponibilidad
           panelAdminLibros.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro2.setText("Estado: " + ((arrayListLibro.get(1).getIdEstado() == 1)? "Disponible":"No disponible"));
           //Estante
           panelAdminLibros.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro2.setText("Estante: " + arrayListLibro.get(1).getEstanteLibro());
           //Fila
           panelAdminLibros.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
           panelAdminLibros.txtFilaLibro2.setText("Fila: " + String.valueOf(arrayListLibro.get(1).getFilaLibro()));
        }
        else if(arrayListLibro.size() == 3){
            panelAdminLibros.nextPagAdmin.setVisible(false);
            //paneles
           panelAdminLibros.panel1.setVisible(true);
           panelAdminLibros.panel2.setVisible(true);
           panelAdminLibros.panel3.setVisible(true);
           panelAdminLibros.panel4.setVisible(false);
           panelAdminLibros.panel5.setVisible(false);
           //nombre libro
           panelAdminLibros.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           panelAdminLibros.txtNombreLibro2.setText(arrayListLibro.get(1).getNombreLibro());
           panelAdminLibros.txtNombreLibro3.setText(arrayListLibro.get(2).getNombreLibro());
           //nombre Autor
           panelAdminLibros.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           panelAdminLibros.txtNombreAutor2.setText("Autor: " +arrayListLibro.get(1).getNombreAutor());
           panelAdminLibros.txtNombreAutor3.setText("Autor: " +arrayListLibro.get(2).getNombreAutor());
           //año del libro
           panelAdminLibros.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           panelAdminLibros.txtAnoLibro2.setText("Año: " + String.valueOf(arrayListLibro.get(1).getAnoLibro()));
           panelAdminLibros.txtAnoLibro3.setText("Año: " + String.valueOf(arrayListLibro.get(2).getAnoLibro()));
           //diponibilidad
           panelAdminLibros.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro2.setText("Estado: " + ((arrayListLibro.get(1).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro3.setText("Estado: " + ((arrayListLibro.get(2).getIdEstado() == 1)? "Disponible":"No disponible"));
           //Estante
           panelAdminLibros.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro2.setText("Estante: " + arrayListLibro.get(1).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro3.setText("Estante: " + arrayListLibro.get(2).getEstanteLibro());
           //Fila
           panelAdminLibros.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
           panelAdminLibros.txtFilaLibro2.setText("Fila: " + String.valueOf(arrayListLibro.get(1).getFilaLibro()));
           panelAdminLibros.txtFilaLibro3.setText("Fila: " + String.valueOf(arrayListLibro.get(2).getFilaLibro()));
        }
        else if(arrayListLibro.size() == 4){
            panelAdminLibros.nextPagAdmin.setVisible(false);
            //paneles
           panelAdminLibros.panel1.setVisible(true);
           panelAdminLibros.panel2.setVisible(true);
           panelAdminLibros.panel3.setVisible(true);
           panelAdminLibros.panel4.setVisible(true);
           panelAdminLibros.panel5.setVisible(false);
           //nombre libro
           panelAdminLibros.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           panelAdminLibros.txtNombreLibro2.setText(arrayListLibro.get(1).getNombreLibro());
           panelAdminLibros.txtNombreLibro3.setText(arrayListLibro.get(2).getNombreLibro());
           panelAdminLibros.txtNombreLibro4.setText(arrayListLibro.get(3).getNombreLibro());
           //nombre Autor
           panelAdminLibros.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           panelAdminLibros.txtNombreAutor2.setText("Autor: " +arrayListLibro.get(1).getNombreAutor());
           panelAdminLibros.txtNombreAutor3.setText("Autor: " +arrayListLibro.get(2).getNombreAutor());
           panelAdminLibros.txtNombreAutor4.setText("Autor: " +arrayListLibro.get(3).getNombreAutor());
           //año del libro
           panelAdminLibros.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           panelAdminLibros.txtAnoLibro2.setText("Año: " + String.valueOf(arrayListLibro.get(1).getAnoLibro()));
           panelAdminLibros.txtAnoLibro3.setText("Año: " + String.valueOf(arrayListLibro.get(2).getAnoLibro()));
           panelAdminLibros.txtAnoLibro4.setText("Año: " + String.valueOf(arrayListLibro.get(3).getAnoLibro()));
           //diponibilidad
           panelAdminLibros.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro2.setText("Estado: " + ((arrayListLibro.get(1).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro3.setText("Estado: " + ((arrayListLibro.get(2).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro4.setText("Estado: " + ((arrayListLibro.get(3).getIdEstado() == 1)? "Disponible":"No disponible"));
           //Estante
           panelAdminLibros.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro2.setText("Estante: " + arrayListLibro.get(1).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro3.setText("Estante: " + arrayListLibro.get(2).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro4.setText("Estante: " + arrayListLibro.get(3).getEstanteLibro());
           //Fila
           panelAdminLibros.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
           panelAdminLibros.txtFilaLibro2.setText("Fila: " + String.valueOf(arrayListLibro.get(1).getFilaLibro()));
           panelAdminLibros.txtFilaLibro3.setText("Fila: " + String.valueOf(arrayListLibro.get(2).getFilaLibro()));
           panelAdminLibros.txtFilaLibro4.setText("Fila: " + String.valueOf(arrayListLibro.get(3).getFilaLibro()));
        }
        else if(arrayListLibro.size() == 5){
            panelAdminLibros.nextPagAdmin.setVisible(true);
            if(this.busqueda){
                panelAdminLibros.nextPagAdmin.setVisible(false);
                panelAdminLibros.backPagAdmin.setVisible(false);
            }
            //paneles
           panelAdminLibros.panel1.setVisible(true);
           panelAdminLibros.panel2.setVisible(true);
           panelAdminLibros.panel3.setVisible(true);
           panelAdminLibros.panel4.setVisible(true);
           panelAdminLibros.panel5.setVisible(true);
           //nombre libro
           panelAdminLibros.txtNombreLibro1.setText(arrayListLibro.get(0).getNombreLibro());
           panelAdminLibros.txtNombreLibro2.setText(arrayListLibro.get(1).getNombreLibro());
           panelAdminLibros.txtNombreLibro3.setText(arrayListLibro.get(2).getNombreLibro());
           panelAdminLibros.txtNombreLibro4.setText(arrayListLibro.get(3).getNombreLibro());
           panelAdminLibros.txtNombreLibro5.setText(arrayListLibro.get(4).getNombreLibro());
           //nombre Autor
           panelAdminLibros.txtNombreAutor1.setText("Autor: " +arrayListLibro.get(0).getNombreAutor());
           panelAdminLibros.txtNombreAutor2.setText("Autor: " +arrayListLibro.get(1).getNombreAutor());
           panelAdminLibros.txtNombreAutor3.setText("Autor: " +arrayListLibro.get(2).getNombreAutor());
           panelAdminLibros.txtNombreAutor4.setText("Autor: " +arrayListLibro.get(3).getNombreAutor());
           panelAdminLibros.txtNombreAutor5.setText("Autor: " +arrayListLibro.get(4).getNombreAutor());
           //año del libro
           panelAdminLibros.txtAnoLibro1.setText("Año: " + String.valueOf(arrayListLibro.get(0).getAnoLibro()));
           panelAdminLibros.txtAnoLibro2.setText("Año: " + String.valueOf(arrayListLibro.get(1).getAnoLibro()));
           panelAdminLibros.txtAnoLibro3.setText("Año: " + String.valueOf(arrayListLibro.get(2).getAnoLibro()));
           panelAdminLibros.txtAnoLibro4.setText("Año: " + String.valueOf(arrayListLibro.get(3).getAnoLibro()));
           panelAdminLibros.txtAnoLibro5.setText("Año: " + String.valueOf(arrayListLibro.get(4).getAnoLibro()));
           //diponibilidad
           panelAdminLibros.txtDisponibilidadLibro1.setText("Estado: " + ((arrayListLibro.get(0).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro2.setText("Estado: " + ((arrayListLibro.get(1).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro3.setText("Estado: " + ((arrayListLibro.get(2).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro4.setText("Estado: " + ((arrayListLibro.get(3).getIdEstado() == 1)? "Disponible":"No disponible"));
           panelAdminLibros.txtDisponibilidadLibro5.setText("Estado: " + ((arrayListLibro.get(4).getIdEstado() == 1)? "Disponible":"No disponible"));
           //Estante
           panelAdminLibros.txtEstanteLibro1.setText("Estante: " + arrayListLibro.get(0).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro2.setText("Estante: " + arrayListLibro.get(1).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro3.setText("Estante: " + arrayListLibro.get(2).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro4.setText("Estante: " + arrayListLibro.get(3).getEstanteLibro());
           panelAdminLibros.txtEstanteLibro5.setText("Estante: " + arrayListLibro.get(4).getEstanteLibro());
           //Fila
           panelAdminLibros.txtFilaLibro1.setText("Fila: " + String.valueOf(arrayListLibro.get(0).getFilaLibro()));
           panelAdminLibros.txtFilaLibro2.setText("Fila: " + String.valueOf(arrayListLibro.get(1).getFilaLibro()));
           panelAdminLibros.txtFilaLibro3.setText("Fila: " + String.valueOf(arrayListLibro.get(2).getFilaLibro()));
           panelAdminLibros.txtFilaLibro4.setText("Fila: " + String.valueOf(arrayListLibro.get(3).getFilaLibro()));
           panelAdminLibros.txtFilaLibro5.setText("Fila: " + String.valueOf(arrayListLibro.get(4).getFilaLibro()));
        }
        else{
            panelAdminLibros.txtNombreLibro1.setText("");
            panelAdminLibros.txtNombreLibro2.setText("");
            panelAdminLibros.txtNombreLibro3.setText("");
            panelAdminLibros.txtNombreLibro4.setText("");
            panelAdminLibros.txtNombreLibro5.setText("");
        }
        
        if(!busqueda.equals("")){
            panelAdminLibros.txtBusquedaAdmin.setText("Se muestran resultados para \"" + busqueda + "\"");
        }
        else{
            panelAdminLibros.txtBusquedaAdmin.setText("");
        }
        cantLibrosPag = arrayListLibro.size();
        panelAdminLibros.txtCantLibroAdmin.setText("Se mostraron " + arrayListLibro.size() + " libros de: " + cantLibros);
        
    }
    
    public void cerrarPanelAdminLibros(){
        panelAdminLibros.setVisible(false);
    }

    public void visiblePanelAdminLibros(){
        panelAdminLibros.setTitle("Panel Administrar Libros - Sigeli");
        this.busqueda = false;
        panelAdminLibros.txtBusquedaAdmin.setText("");
        verLibros(iDaoLibro.verLibros());
        panelAdminLibros.setLocationRelativeTo(null);
        panelAdminLibros.setResizable(false);
        panelAdminLibros.setVisible(true);
    }
    
    public void visiblePanelAdmin(){
        ControladorPanelAdmin controladorPanelAdmin = new ControladorPanelAdmin();
        
        controladorPanelAdmin.inicio(documentoLogin, iDaoPersona.verPersonas());
    }
    
    public void visibleVerLibro(){
        panelVistaLibro.txtNombrePersona.setText(nombreCorto);
        panelVistaLibro.txtNombrePersona1.setText(nombre);
        panelVistaLibro.panelExten.setVisible(false);
        panelVistaLibro.setTitle("Panel Administrar Libros - Sigeli");
        panelVistaLibro.setLocationRelativeTo(null);
        panelVistaLibro.setResizable(false);
        panelVistaLibro.setVisible(true);
    }
    
    public void cerrarVerLibro(){
        panelVistaLibro.setVisible(false);
    }
    
    public void visiblePanelAddLibro(){
        
        panelAddLibro.setTitle("Panel añadir Libros - Sigeli");
        panelAddLibro.setLocationRelativeTo(null);
        panelAddLibro.setResizable(false);
        panelAddLibro.setVisible(true);
        
    }
    
    public void cerrarPanelAddLibro(){
        panelAddLibro.setVisible(false);
    }
}
