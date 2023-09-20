
package main.java.com.mycompany.sigeliapp.controladores;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import main.java.com.mycompany.sigeliapp.dao.*;
import main.java.com.mycompany.sigeliapp.vistas.*;
import main.java.com.mycompany.sigeliapp.modelos.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


public class ControladorReportes implements ActionListener, MouseListener{
    
    //DAO
    private IDaoMulta iDaoMulta;
    private IDaoPrestamos iDaoPrestamos;
    private IDaoPersona iDaoPersona;
    
    //Vistas
    
    private PanelReportes panelReportes;
    //Variables
    
    private int documentoLogin;
    private int opcionReporte;
    private IDaoCarrera iDaoCarrera;
    private String nombre, nombreCorto;
    
    private ArrayList<Carrera> arrayListCarrera;

    public ControladorReportes() {
        //DAO
        this.iDaoMulta = new DaoMulta();
        this.iDaoPrestamos = new DaoPrestamos();
        this.iDaoPersona = new DaoPersona();
        this.iDaoCarrera = new DaoCarrera();
        
        this.arrayListCarrera = iDaoCarrera.verCarreras();
        
        //VISTAS
        
        this.panelReportes = new PanelReportes();
        
        //Opciones panel
        
        this.panelReportes.btnPrestamos.addActionListener(this);
        this.panelReportes.btnMultas.addActionListener(this);
        this.panelReportes.btnPagos.addActionListener(this);
        this.panelReportes.btnIncluirTodo.addActionListener(this);
        this.panelReportes.btnCrearReporte.addMouseListener(this);
        this.panelReportes.btnVolverMenu.addMouseListener(this);
        this.panelReportes.btnExtenPanel.addMouseListener(this);
        this.panelReportes.btnExtenPanelOff.addMouseListener(this);
        
        this.panelReportes.btnCerrarSesion.addMouseListener(this);
        this.panelReportes.adminLibros.addMouseListener(this);
        this.panelReportes.adminUsuarios.addMouseListener(this);
        this.panelReportes.addLibros.addMouseListener(this);
        this.panelReportes.prestamos.addMouseListener(this);
        this.panelReportes.multas.addMouseListener(this);
        this.panelReportes.reportes.addMouseListener(this);
         
    }
    
    //Acciones

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.panelReportes.btnPrestamos){
            this.opcionReporte = 1;
            
            panelReportes.btnPrestamos.setSelected(true);
            panelReportes.btnMultas.setSelected(false);
            panelReportes.btnPagos.setSelected(false);
            panelReportes.btnIncluirTodo.setSelected(false);
        }
        else if(e.getSource() == this.panelReportes.btnMultas){
            this.opcionReporte = 2;
            panelReportes.btnPrestamos.setSelected(false);
            panelReportes.btnMultas.setSelected(true);
            panelReportes.btnPagos.setSelected(false);
            panelReportes.btnIncluirTodo.setSelected(false);
        }
        else if(e.getSource() == this.panelReportes.btnPagos){
            this.opcionReporte = 3;
            panelReportes.btnPrestamos.setSelected(false);
            panelReportes.btnMultas.setSelected(false);
            panelReportes.btnPagos.setSelected(true);
            panelReportes.btnIncluirTodo.setSelected(false);
        }
        else if(e.getSource() == this.panelReportes.btnIncluirTodo){
            this.opcionReporte = 4;
            panelReportes.btnPrestamos.setSelected(false);
            panelReportes.btnMultas.setSelected(false);
            panelReportes.btnPagos.setSelected(false);
            panelReportes.btnIncluirTodo.setSelected(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == panelReportes.btnCrearReporte){
            try {
                generarReporte(opcionReporte);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error " +ex.getMessage());
            } catch (DocumentException ex) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error " +ex.getMessage());
            }
            
            cerrarPanelReportes();
            limpiarDatos();
            visiblePanelAdmin();
        }
        
        else if(e.getSource() == panelReportes.btnVolverMenu){
            cerrarPanelReportes();
            visiblePanelAdmin();
        }
        
        else if(e.getSource() == panelReportes.btnCerrarSesion){
            ControladorLogin controladorLogin = new ControladorLogin();
            cerrarPanelReportes();
            controladorLogin.visibleLogin();
        }
        
        else if(e.getSource() == panelReportes.btnExtenPanel){
            panelReportes.panelExten.setVisible(true);
            panelReportes.btnVolverMenu.setVisible(false);
        }
        
        else if(e.getSource() == panelReportes.btnExtenPanelOff){
            panelReportes.btnVolverMenu.setVisible(true);
            panelReportes.panelExten.setVisible(false);
        }
        
        
        //Opciones panel extendido
        else if(e.getSource() == panelReportes.adminLibros){
            cerrarPanelReportes();
            ControladorLibro controladorLibro = new ControladorLibro();
            controladorLibro.inicio(documentoLogin, nombre);
        }
        
        else if(e.getSource() == panelReportes.adminUsuarios){
            cerrarPanelReportes();
            
            ControladorPersona controladorPersona = new ControladorPersona();
            controladorPersona.inicio(documentoLogin, nombre);
            
        }
        
        else if(e.getSource() == panelReportes.addLibros){
            cerrarPanelReportes();

            
            ControladorLibro controladorLibro = new ControladorLibro();
            
            controladorLibro.inicioAddLibros(documentoLogin, nombre);
        }
        
        //Sin controladores
        
        else if(e.getSource() == panelReportes.prestamos){
            cerrarPanelReportes();
            
            ControladorPrestamos controladorPrestamos = new ControladorPrestamos();
            controladorPrestamos.inicio(documentoLogin, nombre);

        }
        
        else if(e.getSource() == panelReportes.multas){
            cerrarPanelReportes();
            
            ControladorMulta controladorMulta = new ControladorMulta();
            
            controladorMulta.inicio(documentoLogin, nombre);

        }
        
        else if(e.getSource() == panelReportes.reportes){
            
            cerrarPanelReportes();
            visiblePanelReportes();

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
    
    
    
    //metodos
    
    public void generarReporte(int opcion) throws FileNotFoundException, DocumentException{
        
        int carrera0 = 0, carrera1 = 0, carrera2 = 0, carrera3 = 0, carrera4 = 0, carrera5 = 0, carrera6 = 0, carrera7 = 0, cantDinero = 0, cantPagos =0, cantCondenados = 0;
        
        Font fuenteTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Font fuenteSubTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
        
        JFileChooser guardar = new JFileChooser();
        guardar.setSelectedFile(new File("Reporte.pdf"));
        guardar.showSaveDialog(null);
        guardar.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        File pdfFile = new File(guardar.getCurrentDirectory() + "\\" +  Date.valueOf(LocalDate.now() ));
        
        
        Date fechaInicio = Date.valueOf(panelReportes.txtAnoInicio.getText() + "-" + panelReportes.txtMesInicio.getSelectedIndex() + "-" + panelReportes.txtDiaInicio.getText());
        Date fechaFin = Date.valueOf(panelReportes.txtAnoFin.getText() + "-" + panelReportes.txtMesFin.getSelectedIndex() + "-" + panelReportes.txtDiaFin.getText());
        
        
        if(opcion == 1){
            try {
                Document documentoPdf = new Document();
                FileOutputStream ficheroDocumento = new FileOutputStream(pdfFile + " Reporte de prestamos " + " inicio " + String.valueOf(fechaInicio) + " fin " + String.valueOf(fechaFin) + ".pdf");
                PdfWriter.getInstance(documentoPdf, ficheroDocumento).setInitialLeading(10);
                
                documentoPdf.setMargins(30,30, 0, 30);
                documentoPdf.open();
                
                
                Image imagen = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\imagenes\\encabezadoPDF.png");
                
                documentoPdf.add(imagen);
                
                documentoPdf.setMargins(30,30, 30, 30);
                
                documentoPdf.add(new Paragraph("\n"));
                
                documentoPdf.add(new Paragraph("REPORTE DE PRESTAMOS  desde: " + fechaInicio + " hasta: "+ fechaFin , fuenteTitulo));
                documentoPdf.add(new Paragraph("\n"));
                if(!panelReportes.txtObservaciones.getText().equals("")){
                    documentoPdf.add(new Paragraph("Observaciones: ", fuenteSubTitulo));
                    documentoPdf.add(new Paragraph(panelReportes.txtObservaciones.getText()));
                }
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Prestamos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
                    if(prestamo.getFechaPrestamo().after(fechaInicio)  && prestamo.getFechaPrestamo().before(fechaFin)){
                        for(Persona persona : iDaoPersona.verPersonas()){
                            if(prestamo.getIdPersona() == persona.getDocumentoPersona()){
                                documentoPdf.add(new Paragraph("Prestamo número: " + prestamo.getIdPrestamo()));
                                documentoPdf.add(new Paragraph("Persona que pidió el prestamo: " + persona.getNombrePersona() + "\nDocumento: " + prestamo.getIdPersona()));
                                if(persona.getIdCarrera() != 0){
                                    documentoPdf.add(new Paragraph("Programa al que pertenece: " + arrayListCarrera.get(persona.getIdCarrera()).getCarrera()));
                                }
                                else{
                                    documentoPdf.add(new Paragraph("Administrador"));
                                }
                                documentoPdf.add(new Paragraph("ISBN del libro prestado: " + prestamo.getIsbnLibro()));
                                documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "Fecha de entrega: " + prestamo.getFechaEntrega()));
                                documentoPdf.add(new Paragraph("Estado del prestamo: " + ((prestamo.getIdEstado() == 3)? "En deuda":"Entregado")));
                                documentoPdf.add(new Paragraph("\n"));
                                documentoPdf.add(new Paragraph("\n"));
                                
                                if(persona.getIdCarrera() == 0){
                                    carrera0 += 1;
                                }
                                else if(persona.getIdCarrera() == 1){
                                    carrera1 += 1;
                                }
                                else if(persona.getIdCarrera() == 2){
                                    carrera2 += 1;
                                }
                                else if(persona.getIdCarrera() == 3){
                                    carrera3 += 1;
                                }
                                else if(persona.getIdCarrera() == 4){
                                    carrera4 += 1;
                                }
                                else if(persona.getIdCarrera() == 5){
                                    carrera5 += 1;
                                }
                                else if(persona.getIdCarrera() == 6){
                                    carrera6 += 1;
                                }
                                else if(persona.getIdCarrera() == 7){
                                    carrera7 += 1;
                                }
                                
                            }
                        }
                    }
                }
                
                
                PdfPTable tabla = new PdfPTable(2);
                
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Estadisticas por carrera - Prestamos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                documentoPdf.setMargins(30,30, 0, 30);

                //Cabecera
                tabla.addCell("Cargo/carrera");
                tabla.addCell("Cantidad de prestamos");
                //Carrera 0
                tabla.addCell("Administradores");
                tabla.addCell("" + carrera0);
                //Carrera 1
                tabla.addCell("Ingeniería de software");
                tabla.addCell("" + carrera1);
                //Carrera 2
                tabla.addCell("Diseño gráfico");
                tabla.addCell("" + carrera2);
                //Carrera 3
                tabla.addCell("Diseño de modas");
                tabla.addCell("" + carrera3);
                //Carrera 4
                tabla.addCell("Economía y finanzas");
                tabla.addCell("" + carrera4);
                //Carrera 5
                tabla.addCell("Comercio internacional");
                tabla.addCell("" + carrera5);
                //Carrera 6
                tabla.addCell("Hotelería y turísmo");
                tabla.addCell("" + carrera6);
                //Carrera 7
                tabla.addCell("Logística empresarial");
                tabla.addCell("" + carrera7);
                
                //Agregamos la tabla al pdf
                documentoPdf.add(tabla);
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Gráfica de los prestamos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                //Creacion de las graficas
                
                    //Graficas de pastel
                
                DefaultPieDataset graficaPastel = new DefaultPieDataset();
                
                int promPrestamos = 0;

                try {
                   promPrestamos = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7); 
                } catch (Exception e) {
                }
                
                if(promPrestamos != 0){
                    graficaPastel.setValue("Administradores", Double.valueOf((promPrestamos*carrera0)));
                    graficaPastel.setValue("Ingeniería de software", Double.valueOf(promPrestamos*carrera1));
                    graficaPastel.setValue("Diseño gráfico", Double.valueOf(promPrestamos*carrera2));
                    graficaPastel.setValue("Diseño de modas", Double.valueOf(promPrestamos*carrera3));
                    graficaPastel.setValue("Economía y finanzas", Double.valueOf(promPrestamos*carrera4));
                    graficaPastel.setValue("Comercio internacional", Double.valueOf(promPrestamos*carrera5));
                    graficaPastel.setValue("Hotelería y turísmo", Double.valueOf(promPrestamos*carrera6));
                    graficaPastel.setValue("Logística empresarial", Double.valueOf(promPrestamos*carrera7));

                    JFreeChart graficoCahrt = ChartFactory.createPieChart3D("Prestamos", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastel.png"), graficoCahrt, 500, 500);

                    Image image = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastel.png");
                    documentoPdf.add(image);

                        //Graficas de barras

                    DefaultCategoryDataset diagramaBarras = new DefaultCategoryDataset();

                    diagramaBarras.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarras.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarras.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarras.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarras.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarras.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarras.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarras.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChart = ChartFactory.createBarChart3D("Prestamos","Carrera", "Cantidad", diagramaBarras, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarra.png"), graficaBarraChart, 500, 500);

                    Image imagenGraf = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarra.png");
                    documentoPdf.add(imagenGraf);
                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }
            
                documentoPdf.close();
                
                JOptionPane.showMessageDialog(null, "Documento Creado con éxito");
                
                try {
                    File path = new File (pdfFile + " Reporte de prestamos " + " inicio " + String.valueOf(fechaInicio) + " fin " + String.valueOf(fechaFin) + ".pdf");
                    Desktop.getDesktop().open(path);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(null, "No se ha podido crear el documento " + e.getMessage());
            }
        }
        else if(opcion == 2){
            Document documentoPdf = new Document();
            try {
                FileOutputStream ficheroDocumento = new FileOutputStream( pdfFile + " Reporte de Multas " + " inicio " + String.valueOf(fechaInicio) + " fin " + String.valueOf(fechaFin) + ".pdf");
                PdfWriter.getInstance(documentoPdf, ficheroDocumento).setInitialLeading(10);
                
                documentoPdf.setMargins(30,30, 0, 30);
                documentoPdf.open();
                
                Image imagen = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\imagenes\\encabezadoPDF.png");
                
                documentoPdf.add(imagen);
                
                documentoPdf.setMargins(30,30, 30, 30);
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("REPORTE DE MULTAS  desde: " + fechaInicio + " hasta: "+ fechaFin , fuenteTitulo));
                documentoPdf.add(new Paragraph("\n"));
                if(!panelReportes.txtObservaciones.getText().equals("")){
                    documentoPdf.add(new Paragraph("Observaciones: ", fuenteSubTitulo));
                    documentoPdf.add(new Paragraph(panelReportes.txtObservaciones.getText()));
                }
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Multas" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                
                for(Multa multa : iDaoMulta.verMultas()){
                    for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
                        if(multa.getEstadoMulta() == 6 && prestamo.getFechaEntrega().after(fechaInicio) && prestamo.getFechaEntrega().before(fechaFin)){
                            if(prestamo.getIdPrestamo() == multa.getIdPrestamo()){
                                for(Persona persona : iDaoPersona.verPersonas()){
                                    if(prestamo.getIdPersona() == persona.getDocumentoPersona()){
                                        documentoPdf.add(new Paragraph("Multa numero: " + multa.getIdMulta() + "\nID del prestamo multado : " + multa.getIdPrestamo()));
                                        documentoPdf.add(new Paragraph("Persona que pidió el prestamo: " + persona.getNombrePersona() + "\nDocumento: " + persona.getDocumentoPersona()));
                                        documentoPdf.add(new Paragraph("Programa al que pertenece: " + arrayListCarrera.get(persona.getIdCarrera()-1).getCarrera()));
                                        documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("Fecha en la que se creó la multa: " + multa.getFecha()));
                                        documentoPdf.add(new Paragraph("ISBN del libro multado: " + prestamo.getIsbnLibro()));
                                        documentoPdf.add(new Paragraph("Valor de la multa: " + multa.getValorMulta()));
                                        documentoPdf.add(new Paragraph("\n"));
                                        documentoPdf.add(new Paragraph("\n"));
                                        
                                        if(persona.getIdCarrera() == 0){
                                            carrera0 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 1){
                                            carrera1 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 2){
                                            carrera2 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 3){
                                            carrera3 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 4){
                                            carrera4 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 5){
                                            carrera5 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 6){
                                            carrera6 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 7){
                                            carrera7 += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                PdfPTable tabla = new PdfPTable(2);
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Estadisticas por carrera - Multas" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));

                //Cabecera
                tabla.addCell("Cargo/carrera");
                tabla.addCell("Cantidad de multas");
                //Carrera 0
                tabla.addCell("Administradores");
                tabla.addCell("" + carrera0);
                //Carrera 1
                tabla.addCell("Ingeniería de software");
                tabla.addCell("" + carrera1);
                //Carrera 2
                tabla.addCell("Diseño gráfico");
                tabla.addCell("" + carrera2);
                //Carrera 3
                tabla.addCell("Diseño de modas");
                tabla.addCell("" + carrera3);
                //Carrera 4
                tabla.addCell("Economía y finanzas");
                tabla.addCell("" + carrera4);
                //Carrera 5
                tabla.addCell("Comercio internacional");
                tabla.addCell("" + carrera5);
                //Carrera 6
                tabla.addCell("Hotelería y turísmo");
                tabla.addCell("" + carrera6);
                //Carrera 7
                tabla.addCell("Logística empresarial");
                tabla.addCell("" + carrera7);
                
                documentoPdf.add(tabla);
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Gráfica de las multas" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                documentoPdf.setMargins(30,30, 0, 30);
                //Creacion de las graficas
                
                    //Graficas de pastel
                
                DefaultPieDataset graficaPastel = new DefaultPieDataset();
                
                int promMultas = 0;

                try {
                    promMultas = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }
                
                if(promMultas != 0){
                    graficaPastel.setValue("Administradores", Double.valueOf((promMultas*carrera0)));
                    graficaPastel.setValue("Ingeniería de software", Double.valueOf(promMultas*carrera1));
                    graficaPastel.setValue("Diseño gráfico", Double.valueOf(promMultas*carrera2));
                    graficaPastel.setValue("Diseño de modas", Double.valueOf(promMultas*carrera3));
                    graficaPastel.setValue("Economía y finanzas", Double.valueOf(promMultas*carrera4));
                    graficaPastel.setValue("Comercio internacional", Double.valueOf(promMultas*carrera5));
                    graficaPastel.setValue("Hotelería y turísmo", Double.valueOf(promMultas*carrera6));
                    graficaPastel.setValue("Logística empresarial", Double.valueOf(promMultas*carrera7));

                    JFreeChart graficoCahrt = ChartFactory.createPieChart3D("Multas", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastelMultas.png"), graficoCahrt, 500, 500);

                    Image image = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastelMultas.png");
                    documentoPdf.add(image);

                        //Graficas de barras

                    DefaultCategoryDataset diagramaBarras = new DefaultCategoryDataset();

                    diagramaBarras.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarras.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarras.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarras.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarras.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarras.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarras.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarras.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChart = ChartFactory.createBarChart3D("Multas","Carrera", "Cantidad", diagramaBarras, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarraMultas.png"), graficaBarraChart, 500, 500);

                    Image imagenGraf = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarraMultas.png");
                    documentoPdf.add(imagenGraf);
                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }
                
                
                documentoPdf.close();
                
                JOptionPane.showMessageDialog(null, "Documento Creado con éxito");
                
                try {
                    File path = new File (pdfFile + " Reporte de Multas " + " inicio " + String.valueOf(fechaInicio) + " fin " + String.valueOf(fechaFin) + ".pdf");
                    Desktop.getDesktop().open(path);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(null, "No se ha podido crear el documento " + e.getMessage());
            }
                
            
        }
        else if(opcion == 3){
            Document documentoPdf = new Document();
            try {
                FileOutputStream ficheroDocumento = new FileOutputStream(pdfFile + " Reporte de Pagos " + " inicio " + String.valueOf(fechaInicio) + " fin " + String.valueOf(fechaFin) + ".pdf");
                PdfWriter.getInstance(documentoPdf, ficheroDocumento).setInitialLeading(10);
                
                documentoPdf.setMargins(30,30, 0, 30);
                documentoPdf.open();
                
                Image imagen = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\imagenes\\encabezadoPDF.png");
                
                documentoPdf.add(imagen);
                
                documentoPdf.setMargins(30,30, 30, 30);
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("REPORTE DE PAGOS  desde: " + fechaInicio + " hasta: "+ fechaFin , fuenteTitulo));
                documentoPdf.add(new Paragraph("\n"));
                if(!panelReportes.txtObservaciones.getText().equals("")){
                    documentoPdf.add(new Paragraph("Observaciones: ", fuenteSubTitulo));
                    documentoPdf.add(new Paragraph(panelReportes.txtObservaciones.getText()));
                }
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Pagos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                for(Multa multa : iDaoMulta.verMultas()){
                    for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
                        if(multa.getEstadoMulta() == 5 || multa.getEstadoMulta() == 7 && prestamo.getFechaEntrega().after(fechaInicio) && prestamo.getFechaEntrega().before(fechaFin)){
                            if(prestamo.getIdPrestamo() == multa.getIdPrestamo()){
                                for(Persona persona : iDaoPersona.verPersonas()){
                                    if(prestamo.getIdPersona() == persona.getDocumentoPersona()){
                                        documentoPdf.add(new Paragraph("Pago de la multa: " + multa.getIdMulta() + "\nID del prestamo multado : " + multa.getIdPrestamo()));
                                        documentoPdf.add(new Paragraph("Persona que pagó la multa: " + persona.getNombrePersona() + "\nDocumento: " + persona.getDocumentoPersona()));
                                        documentoPdf.add(new Paragraph("Programa al que pertenece: " + arrayListCarrera.get(persona.getIdCarrera()-1).getCarrera()));
                                        documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("Fecha en la que se Pagó la multa: " + multa.getFecha()));
                                        documentoPdf.add(new Paragraph("ISBN del libro multado: " + prestamo.getIsbnLibro()));
                                        documentoPdf.add(new Paragraph("Valor pagado: " + multa.getValorMulta()));
                                        documentoPdf.add(new Paragraph("\n"));
                                        documentoPdf.add(new Paragraph("\n"));
                                        
                                        if(multa.getEstadoMulta() == 5){
                                            cantPagos += 1;
                                            cantDinero += multa.getValorMulta();
                                        }
                                        else if(multa.getEstadoMulta() == 7){
                                            cantCondenados += 1;
                                        }
                                        
                                        
                                        if(persona.getIdCarrera() == 0){
                                            carrera0 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 1){
                                            carrera1 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 2){
                                            carrera2 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 3){
                                            carrera3 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 4){
                                            carrera4 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 5){
                                            carrera5 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 6){
                                            carrera6 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 7){
                                            carrera7 += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                PdfPTable tabla = new PdfPTable(2);
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Estadisticas por carrera - Pagos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                //Cabecera
                tabla.addCell("Cargo/carrera");
                tabla.addCell("Cantidad de Pagos");
                //Carrera 0
                tabla.addCell("Administradores");
                tabla.addCell("" + carrera0);
                //Carrera 1
                tabla.addCell("Ingeniería de software");
                tabla.addCell("" + carrera1);
                //Carrera 2
                tabla.addCell("Diseño gráfico");
                tabla.addCell("" + carrera2);
                //Carrera 3
                tabla.addCell("Diseño de modas");
                tabla.addCell("" + carrera3);
                //Carrera 4
                tabla.addCell("Economía y finanzas");
                tabla.addCell("" + carrera4);
                //Carrera 5
                tabla.addCell("Comercio internacional");
                tabla.addCell("" + carrera5);
                //Carrera 6
                tabla.addCell("Hotelería y turísmo");
                tabla.addCell("" + carrera6);
                //Carrera 7
                tabla.addCell("Logística empresarial");
                tabla.addCell("" + carrera7);
                
                documentoPdf.add(tabla);
                
                PdfPTable reciboPago = new PdfPTable(2);
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Pagos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                reciboPago.addCell("Cantidad de pagos");
                reciboPago.addCell("Dinero en caja");
                
                reciboPago.addCell("Condenados");
                reciboPago.addCell("" + cantCondenados);
                
                reciboPago.addCell("pagos");
                reciboPago.addCell("" + cantPagos);
                
                reciboPago.addCell("Total");
                reciboPago.addCell("" + cantDinero);
                
                documentoPdf.add(reciboPago);
                
                //Creacion de las graficas
                
                    //Graficas de pastel
                
                documentoPdf.setMargins(30,30, 0, 30);
                    
                DefaultPieDataset graficaPastel = new DefaultPieDataset();
                
                int promPagos = 0;

                try {
                    promPagos = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }
                
                
                if(promPagos != 0){
                    graficaPastel.setValue("Administradores", Double.valueOf((promPagos*carrera0)));
                    graficaPastel.setValue("Ingeniería de software", Double.valueOf(promPagos*carrera1));
                    graficaPastel.setValue("Diseño gráfico", Double.valueOf(promPagos*carrera2));
                    graficaPastel.setValue("Diseño de modas", Double.valueOf(promPagos*carrera3));
                    graficaPastel.setValue("Economía y finanzas", Double.valueOf(promPagos*carrera4));
                    graficaPastel.setValue("Comercio internacional", Double.valueOf(promPagos*carrera5));
                    graficaPastel.setValue("Hotelería y turísmo", Double.valueOf(promPagos*carrera6));
                    graficaPastel.setValue("Logística empresarial", Double.valueOf(promPagos*carrera7));

                    JFreeChart graficoCahrt = ChartFactory.createPieChart3D("Multas", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastelPagos.png"), graficoCahrt, 500, 500);

                    Image image = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastelPagos.png");
                    documentoPdf.add(image);

                        //Graficas de barras

                    DefaultCategoryDataset diagramaBarras = new DefaultCategoryDataset();

                    diagramaBarras.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarras.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarras.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarras.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarras.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarras.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarras.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarras.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChart = ChartFactory.createBarChart3D("Multas","Carrera", "Cantidad", diagramaBarras, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarraPagos.png"), graficaBarraChart, 500, 500);

                    Image imagenGraf = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarraPagos.png");
                    documentoPdf.add(imagenGraf);
                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }
                
                documentoPdf.close();
                JOptionPane.showMessageDialog(null, "Documento Creado con éxito");
                
                try {
                    File path = new File (pdfFile + " Reporte de Pagos " + " inicio " + String.valueOf(fechaInicio) + " fin " + String.valueOf(fechaFin) + ".pdf");
                    Desktop.getDesktop().open(path);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                
            } catch (Exception e) {
                
                JOptionPane.showMessageDialog(null, "No se ha podido crear el documento " + e.getMessage());
            }
        }
        else if(opcion == 4){
            Document documentoPdf = new Document();
            Boolean prestamosDoc = false, multasDoc = false, pagosDoc = false;
            try {
                FileOutputStream ficheroDocumento = new FileOutputStream(pdfFile + " Reporte total " + " inicio " + fechaInicio + " fin " + fechaFin + ".pdf");
                PdfWriter.getInstance(documentoPdf, ficheroDocumento).setInitialLeading(10);
                
                documentoPdf.setMargins(30,30, 0, 30);
                documentoPdf.open();
                
                Image imagen = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\imagenes\\encabezadoPDF.png");
                
                documentoPdf.add(imagen);
                
                documentoPdf.setMargins(30,30, 30, 30);
                
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("REPORTE TOTAL  desde: " + fechaInicio + " hasta: "+ fechaFin , fuenteTitulo));
                documentoPdf.add(new Paragraph("\n"));
                if(!panelReportes.txtObservaciones.getText().equals("")){
                    documentoPdf.add(new Paragraph("Observaciones: ", fuenteSubTitulo));
                    documentoPdf.add(new Paragraph(panelReportes.txtObservaciones.getText()));
                }
                documentoPdf.add(new Paragraph("\n"));
                
                documentoPdf.add(new Paragraph("Prestamos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                
                for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
                    if(prestamo.getFechaPrestamo().after(fechaInicio)  && prestamo.getFechaPrestamo().before(fechaFin)){
                        for(Persona persona : iDaoPersona.verPersonas()){
                            if(prestamo.getIdPersona() == persona.getDocumentoPersona()){
                                documentoPdf.add(new Paragraph("Prestamo número: " + prestamo.getIdPrestamo()));
                                documentoPdf.add(new Paragraph("Persona que pidió el prestamo: " + persona.getNombrePersona() + "\nDocumento: " + prestamo.getIdPersona()));
                                if(persona.getIdCarrera() != 0){
                                    documentoPdf.add(new Paragraph("Programa al que pertenece: " + arrayListCarrera.get(persona.getIdCarrera()-1).getCarrera()));
                                }
                                else{
                                    documentoPdf.add(new Paragraph("Administrador"));
                                }
                                documentoPdf.add(new Paragraph("ISBN del libro prestado: " + prestamo.getIsbnLibro()));
                                documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "Fecha de entrega: " + prestamo.getFechaEntrega()));
                                documentoPdf.add(new Paragraph("Estado del prestamo: " + ((prestamo.getIdEstado() == 3)? "En deuda":"Entregado")));
                                documentoPdf.add(new Paragraph("\n"));
                                documentoPdf.add(new Paragraph("\n"));
                            if(persona.getIdCarrera() == 0){
                                    carrera0 += 1;
                                }
                                else if(persona.getIdCarrera() == 1){
                                    carrera1 += 1;
                                }
                                else if(persona.getIdCarrera() == 2){
                                    carrera2 += 1;
                                }
                                else if(persona.getIdCarrera() == 3){
                                    carrera3 += 1;
                                }
                                else if(persona.getIdCarrera() == 4){
                                    carrera4 += 1;
                                }
                                else if(persona.getIdCarrera() == 5){
                                    carrera5 += 1;
                                }
                                else if(persona.getIdCarrera() == 6){
                                    carrera6 += 1;
                                }
                                else if(persona.getIdCarrera() == 7){
                                    carrera7 += 1;
                                }
                                
                            }
                        }
                    }
                }
                
                
                
                //Creacion de las graficas
                
                    //Graficas de pastel
                
                DefaultPieDataset graficaPastel = new DefaultPieDataset();
                
                int promPrestamos = 0;

                try {
                    promPrestamos = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }
                
                if(promPrestamos != 0){
                    
                    prestamosDoc = true;
                    PdfPTable tabla = new PdfPTable(2);

                    documentoPdf.add(new Paragraph("\n"));
                    documentoPdf.add(new Paragraph("Estadisticas por carrera - Prestamos" , fuenteSubTitulo));
                    documentoPdf.add(new Paragraph("\n"));

                    //Cabecera
                    tabla.addCell("Cargo/carrera");
                    tabla.addCell("Cantidad de prestamos");
                    //Carrera 0
                    tabla.addCell("Administradores");
                    tabla.addCell("" + carrera0);
                    //Carrera 1
                    tabla.addCell("Ingeniería de software");
                    tabla.addCell("" + carrera1);
                    //Carrera 2
                    tabla.addCell("Diseño gráfico");
                    tabla.addCell("" + carrera2);
                    //Carrera 3
                    tabla.addCell("Diseño de modas");
                    tabla.addCell("" + carrera3);
                    //Carrera 4
                    tabla.addCell("Economía y finanzas");
                    tabla.addCell("" + carrera4);
                    //Carrera 5
                    tabla.addCell("Comercio internacional");
                    tabla.addCell("" + carrera5);
                    //Carrera 6
                    tabla.addCell("Hotelería y turísmo");
                    tabla.addCell("" + carrera6);
                    //Carrera 7
                    tabla.addCell("Logística empresarial");
                    tabla.addCell("" + carrera7);

                    documentoPdf.add(tabla);

                    
                    graficaPastel.setValue("Administradores", Double.valueOf((promPrestamos*carrera0)));
                    graficaPastel.setValue("Ingeniería de software", Double.valueOf(promPrestamos*carrera1));
                    graficaPastel.setValue("Diseño gráfico", Double.valueOf(promPrestamos*carrera2));
                    graficaPastel.setValue("Diseño de modas", Double.valueOf(promPrestamos*carrera3));
                    graficaPastel.setValue("Economía y finanzas", Double.valueOf(promPrestamos*carrera4));
                    graficaPastel.setValue("Comercio internacional", Double.valueOf(promPrestamos*carrera5));
                    graficaPastel.setValue("Hotelería y turísmo", Double.valueOf(promPrestamos*carrera6));
                    graficaPastel.setValue("Logística empresarial", Double.valueOf(promPrestamos*carrera7));

                    JFreeChart graficoCahrt = ChartFactory.createPieChart3D("Prestamos", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastel.png"), graficoCahrt, 400, 400);

                    

                        //Graficas de barras

                    DefaultCategoryDataset diagramaBarras = new DefaultCategoryDataset();

                    diagramaBarras.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarras.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarras.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarras.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarras.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarras.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarras.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarras.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChart = ChartFactory.createBarChart3D("Prestamos","Carrera", "Cantidad", diagramaBarras, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarra.png"), graficaBarraChart, 400, 400);

                    
                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }
                
                documentoPdf.add(new Paragraph("\n"));
                //Multas
                
                carrera0 = 0;
                carrera1 = 0;
                carrera2 = 0; 
                carrera3 = 0; 
                carrera4 = 0; 
                carrera5 = 0; 
                carrera6 = 0;
                carrera7 = 0;
                documentoPdf.add(new Paragraph("\n"));
                documentoPdf.add(new Paragraph("Multas" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                
                for(Multa multa : iDaoMulta.verMultas()){
                    for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
                        if(multa.getEstadoMulta() == 6 && prestamo.getFechaEntrega().after(fechaInicio) && prestamo.getFechaEntrega().before(fechaFin)){
                            if(prestamo.getIdPrestamo() == multa.getIdPrestamo()){
                                for(Persona persona : iDaoPersona.verPersonas()){
                                    if(prestamo.getIdPersona() == persona.getDocumentoPersona()){
                                        documentoPdf.add(new Paragraph("Multa numero: " + multa.getIdMulta() + "\nID del prestamo multado : " + multa.getIdPrestamo()));
                                        documentoPdf.add(new Paragraph("Persona que pidió el prestamo: " + persona.getNombrePersona() + "\nDocumento: " + persona.getDocumentoPersona()));
                                        documentoPdf.add(new Paragraph("Programa al que pertenece: " + arrayListCarrera.get(persona.getIdCarrera()-1).getCarrera()));
                                        documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("Fecha en la que se creó la multa: " + multa.getFecha()));
                                        documentoPdf.add(new Paragraph("ISBN del libro multado: " + prestamo.getIsbnLibro()));
                                        documentoPdf.add(new Paragraph("Valor de la multa: " + multa.getValorMulta()));
                                        documentoPdf.add(new Paragraph("\n"));
                                        documentoPdf.add(new Paragraph("\n"));
                                    if(persona.getIdCarrera() == 0){
                                            carrera0 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 1){
                                            carrera1 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 2){
                                            carrera2 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 3){
                                            carrera3 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 4){
                                            carrera4 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 5){
                                            carrera5 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 6){
                                            carrera6 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 7){
                                            carrera7 += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                DefaultPieDataset graficaPastelMultas = new DefaultPieDataset();
                
                int promMultas = 0;
                
                try {
                    promMultas = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }
                
                if(promMultas != 0){
                    
                    multasDoc = true;
                    PdfPTable tabla2 = new PdfPTable(2);

                    documentoPdf.add(new Paragraph("\n"));
                    documentoPdf.add(new Paragraph("Estadisticas por carrera - Multas" , fuenteSubTitulo));
                    documentoPdf.add(new Paragraph("\n"));

                    //Cabecera
                    tabla2.addCell("Cargo/carrera");
                    tabla2.addCell("Cantidad de multas");
                    //Carrera 0
                    tabla2.addCell("Administradores");
                    tabla2.addCell("" + carrera0);
                    //Carrera 1
                    tabla2.addCell("Ingeniería de software");
                    tabla2.addCell("" + carrera1);
                    //Carrera 2
                    tabla2.addCell("Diseño gráfico");
                    tabla2.addCell("" + carrera2);
                    //Carrera 3
                    tabla2.addCell("Diseño de modas");
                    tabla2.addCell("" + carrera3);
                    //Carrera 4
                    tabla2.addCell("Economía y finanzas");
                    tabla2.addCell("" + carrera4);
                    //Carrera 5
                    tabla2.addCell("Comercio internacional");
                    tabla2.addCell("" + carrera5);
                    //Carrera 6
                    tabla2.addCell("Hotelería y turísmo");
                    tabla2.addCell("" + carrera6);
                    //Carrera 7
                    tabla2.addCell("Logística empresarial");
                    tabla2.addCell("" + carrera7);

                    documentoPdf.add(tabla2);

                    
                    graficaPastelMultas.setValue("Administradores", Double.valueOf((promMultas*carrera0)));
                    graficaPastelMultas.setValue("Ingeniería de software", Double.valueOf(promMultas*carrera1));
                    graficaPastelMultas.setValue("Diseño gráfico", Double.valueOf(promMultas*carrera2));
                    graficaPastelMultas.setValue("Diseño de modas", Double.valueOf(promMultas*carrera3));
                    graficaPastelMultas.setValue("Economía y finanzas", Double.valueOf(promMultas*carrera4));
                    graficaPastelMultas.setValue("Comercio internacional", Double.valueOf(promMultas*carrera5));
                    graficaPastelMultas.setValue("Hotelería y turísmo", Double.valueOf(promMultas*carrera6));
                    graficaPastelMultas.setValue("Logística empresarial", Double.valueOf(promMultas*carrera7));

                    JFreeChart graficoCahrtMultas = ChartFactory.createPieChart3D("Multas", graficaPastelMultas, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastelMultas.png"), graficoCahrtMultas, 400, 400);

                    

                        //Graficas de barras

                    DefaultCategoryDataset diagramaBarrasMultas = new DefaultCategoryDataset();

                    diagramaBarrasMultas.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarrasMultas.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarrasMultas.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarrasMultas.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarrasMultas.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarrasMultas.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarrasMultas.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarrasMultas.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChartMultas = ChartFactory.createBarChart3D("Multas","Carrera", "Cantidad", diagramaBarrasMultas, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarraMultas.png"), graficaBarraChartMultas, 400, 400);

                    
                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }
                
                documentoPdf.add(new Paragraph("\n"));
                //Pagos
                
                carrera0 = 0;
                carrera1 = 0;
                carrera2 = 0; 
                carrera3 = 0; 
                carrera4 = 0; 
                carrera5 = 0; 
                carrera6 = 0;
                carrera7 = 0;
                
                documentoPdf.add(new Paragraph("Pagos" , fuenteSubTitulo));
                documentoPdf.add(new Paragraph("\n"));
                
                for(Multa multa : iDaoMulta.verMultas()){
                    for(Prestamos prestamo : iDaoPrestamos.verPrestamos()){
                        if(multa.getEstadoMulta() == 5 || multa.getEstadoMulta() == 7 && prestamo.getFechaEntrega().after(fechaInicio) && prestamo.getFechaEntrega().before(fechaFin)){
                            if(prestamo.getIdPrestamo() == multa.getIdPrestamo()){
                                for(Persona persona : iDaoPersona.verPersonas()){
                                    if(prestamo.getIdPersona() == persona.getDocumentoPersona()){
                                        documentoPdf.add(new Paragraph("Pago de la multa: " + multa.getIdMulta() + "\nID del prestamo multado : " + multa.getIdPrestamo()));
                                        documentoPdf.add(new Paragraph("Persona que pagó la multa: " + persona.getNombrePersona() + "\nDocumento: " + persona.getDocumentoPersona()));
                                        documentoPdf.add(new Paragraph("Programa al que pertenece: " + arrayListCarrera.get(persona.getIdCarrera()-1).getCarrera()));
                                        documentoPdf.add(new Paragraph("Fecha del prestamo: " + prestamo.getFechaPrestamo() + "\nFecha de entrega: " + prestamo.getFechaEntrega()));
                                        documentoPdf.add(new Paragraph("Fecha en la que se Pagó la multa: " + multa.getFecha()));
                                        documentoPdf.add(new Paragraph("ISBN del libro multado: " + prestamo.getIsbnLibro()));
                                        documentoPdf.add(new Paragraph("Valor pagado: " + multa.getValorMulta()));
                                        documentoPdf.add(new Paragraph("\n"));
                                        documentoPdf.add(new Paragraph("\n"));
                                        
                                        if(multa.getEstadoMulta() == 5){
                                            cantPagos += 1;
                                            cantDinero += multa.getValorMulta();
                                        }
                                        else if(multa.getEstadoMulta() == 7){
                                            cantCondenados += 1;
                                        }
                                        
                                        if(persona.getIdCarrera() == 0){
                                            carrera0 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 1){
                                            carrera1 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 2){
                                            carrera2 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 3){
                                            carrera3 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 4){
                                            carrera4 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 5){
                                            carrera5 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 6){
                                            carrera6 += 1;
                                        }
                                        else if(persona.getIdCarrera() == 7){
                                            carrera7 += 1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                
                
                //Creacion de las graficas
                
                    //Graficas de pastel
                
                DefaultPieDataset graficaPastelPago = new DefaultPieDataset();
                
                int promPagos = 0;

                try {
                    promPagos = 100/(carrera0 +carrera1 + carrera2 + carrera3 + carrera4 + carrera5 + carrera6 + carrera7);
                } catch (Exception e) {
                }
                

                if(promPagos != 0){
                    pagosDoc = true;
                    PdfPTable tabla3 = new PdfPTable(2);

                    documentoPdf.add(new Paragraph("\n"));
                    documentoPdf.add(new Paragraph("Estadisticas por carrera - Pagos" , fuenteSubTitulo));
                    documentoPdf.add(new Paragraph("\n"));

                    //Cabecera
                    tabla3.addCell("Cargo/carrera");
                    tabla3.addCell("Cantidad de Pagos");
                    //Carrera 0
                    tabla3.addCell("Administradores");
                    tabla3.addCell("" + carrera0);
                    //Carrera 1
                    tabla3.addCell("Ingeniería de software");
                    tabla3.addCell("" + carrera1);
                    //Carrera 2
                    tabla3.addCell("Diseño gráfico");
                    tabla3.addCell("" + carrera2);
                    //Carrera 3
                    tabla3.addCell("Diseño de modas");
                    tabla3.addCell("" + carrera3);
                    //Carrera 4
                    tabla3.addCell("Economía y finanzas");
                    tabla3.addCell("" + carrera4);
                    //Carrera 5
                    tabla3.addCell("Comercio internacional");
                    tabla3.addCell("" + carrera5);
                    //Carrera 6
                    tabla3.addCell("Hotelería y turísmo");
                    tabla3.addCell("" + carrera6);
                    //Carrera 7
                    tabla3.addCell("Logística empresarial");
                    tabla3.addCell("" + carrera7);

                    documentoPdf.add(tabla3);

                    PdfPTable reciboPago = new PdfPTable(2);

                    documentoPdf.add(new Paragraph("\n"));
                    documentoPdf.add(new Paragraph("Pagos" , fuenteSubTitulo));
                    documentoPdf.add(new Paragraph("\n"));

                    reciboPago.addCell("Cantidad de pagos");
                    reciboPago.addCell("Dinero en caja");

                    reciboPago.addCell("Condenados");
                    reciboPago.addCell("" + cantCondenados);

                    reciboPago.addCell("pagos");
                    reciboPago.addCell("" + cantPagos);

                    reciboPago.addCell("Total");
                    reciboPago.addCell("" + cantDinero);

                    documentoPdf.add(reciboPago);
                    
                    graficaPastelPago.setValue("Administradores", Double.valueOf((promPagos*carrera0)));
                    graficaPastelPago.setValue("Ingeniería de software", Double.valueOf(promPagos*carrera1));
                    graficaPastelPago.setValue("Diseño gráfico", Double.valueOf(promPagos*carrera2));
                    graficaPastelPago.setValue("Diseño de modas", Double.valueOf(promPagos*carrera3));
                    graficaPastelPago.setValue("Economía y finanzas", Double.valueOf(promPagos*carrera4));
                    graficaPastelPago.setValue("Comercio internacional", Double.valueOf(promPagos*carrera5));
                    graficaPastelPago.setValue("Hotelería y turísmo", Double.valueOf(promPagos*carrera6));
                    graficaPastelPago.setValue("Logística empresarial", Double.valueOf(promPagos*carrera7));

                    JFreeChart graficoCahrtPago = ChartFactory.createPieChart3D("Pagos", graficaPastel, true, true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastelPago.png"), graficoCahrtPago, 400, 400);

                    

                        //Graficas de barras

                    DefaultCategoryDataset diagramaBarrasPago = new DefaultCategoryDataset();

                    diagramaBarrasPago.setValue(carrera0, "Administrador", "Administrador");
                    diagramaBarrasPago.setValue(carrera1, "Ingeniería de software", "Ingeniería de software");
                    diagramaBarrasPago.setValue(carrera2, "Diseño gráfico", "Diseño gráfico");
                    diagramaBarrasPago.setValue(carrera3, "Diseño de modas", "Diseño de modas");
                    diagramaBarrasPago.setValue(carrera4, "Economía y finanzas", "Economía y finanzas");
                    diagramaBarrasPago.setValue(carrera5, "Comercio internacional", "Comercio internacional");
                    diagramaBarrasPago.setValue(carrera6, "Hotelería y turísmo", "Hotelería y turísmo");
                    diagramaBarrasPago.setValue(carrera7, "Logística empresarial", "Logística empresarial");

                    JFreeChart graficaBarraChartPago = ChartFactory.createBarChart3D("Pagos","Carrera", "Cantidad", diagramaBarrasPago, PlotOrientation.VERTICAL, true,true, false);

                    ChartUtilities.saveChartAsJPEG(new File("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarraPago.png"), graficaBarraChartPago, 400, 400);

                }
                else{
                    documentoPdf.add(new Paragraph("No existe ningún registro"));
                }
                
                documentoPdf.setMargins(30,30, 0, 30);
                
                if(prestamosDoc){
                    Image image = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastel.png");
                    documentoPdf.add(image);

                    Image imagenGraf = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarra.png");
                    documentoPdf.add(imagenGraf);

                }
                if(multasDoc){
                    Image imageMultas = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastelMultas.png");
                    documentoPdf.add(imageMultas);

                    Image imagenGrafMultas = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarraMultas.png");
                    documentoPdf.add(imagenGrafMultas);
                }
                
                if(pagosDoc){
                    Image imagePago = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoPastelPago.png");
                    documentoPdf.add(imagePago);

                    Image imagenGrafPago = Image.getInstance("src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\graficos\\graficoBarraPago.png");
                    documentoPdf.add(imagenGrafPago);
                }
                    
                
                documentoPdf.close();
                JOptionPane.showMessageDialog(null, "Documento Creado con éxito");
                
                try {
                    File path = new File (pdfFile + " Reporte total " + " inicio " + fechaInicio + " fin " + fechaFin + ".pdf");
                    Desktop.getDesktop().open(path);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                
            } catch (Exception e) {
                documentoPdf.close();
                
                JOptionPane.showMessageDialog(null, "No se ha podido crear el documento " + e.getMessage());
            }
        }
        
    }
    
    
    //Metodos de las vistas
    
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
        
        panelReportes.txtNombrePersona.setText(this.nombreCorto);
        panelReportes.txtNombrePersona1.setText(this.nombre);
        panelReportes.panelExten.setVisible(false);
        
        inicioDatos();
        visiblePanelReportes();
        
    }
    
    public void visiblePanelReportes(){
        panelReportes.setTitle("Panel generador de reportes - Sigeli");
        panelReportes.setLocationRelativeTo(null);
        panelReportes.setResizable(false);
        panelReportes.setVisible(true);
    }
    
    public void cerrarPanelReportes(){
        panelReportes.setVisible(false);
    }
    
    public void visiblePanelAdmin(){
        ControladorPanelAdmin controladorPanelAdmin = new ControladorPanelAdmin();
        
        controladorPanelAdmin.inicio(documentoLogin, iDaoPersona.verPersonas());
    }
    
    //Seteo de datos
    
    public void limpiarDatos(){
        panelReportes.txtDiaInicio.setText("");
        panelReportes.txtDiaFin.setText("");
        panelReportes.txtAnoFin.setText("");
        panelReportes.txtAnoInicio.setText("");
        panelReportes.txtObservaciones.setText("");
        panelReportes.txtMesInicio.setSelectedIndex(0);
        panelReportes.txtMesFin.setSelectedIndex(0);
        panelReportes.btnPrestamos.setSelected(false);
        panelReportes.btnMultas.setSelected(false);
        panelReportes.btnPagos.setSelected(false);
        panelReportes.btnIncluirTodo.setSelected(false);
    }
    
    public void inicioDatos(){
        
        panelReportes.txtMesInicio.removeAllItems();
        panelReportes.txtMesInicio.addItem("-mes-");
        panelReportes.txtMesInicio.addItem("Enero");
        panelReportes.txtMesInicio.addItem("Febrero");
        panelReportes.txtMesInicio.addItem("Marzo");
        panelReportes.txtMesInicio.addItem("Abril");
        panelReportes.txtMesInicio.addItem("Mayo");
        panelReportes.txtMesInicio.addItem("Junio");
        panelReportes.txtMesInicio.addItem("Julio");
        panelReportes.txtMesInicio.addItem("Agosto");
        panelReportes.txtMesInicio.addItem("Septiembre");
        panelReportes.txtMesInicio.addItem("Octubre");
        panelReportes.txtMesInicio.addItem("Noviembre");
        panelReportes.txtMesInicio.addItem("Diciembre");
        
        panelReportes.txtMesFin.removeAllItems();
        panelReportes.txtMesFin.addItem("-mes-");
        panelReportes.txtMesFin.addItem("Enero");
        panelReportes.txtMesFin.addItem("Febrero");
        panelReportes.txtMesFin.addItem("Marzo");
        panelReportes.txtMesFin.addItem("Abril");
        panelReportes.txtMesFin.addItem("Mayo");
        panelReportes.txtMesFin.addItem("Junio");
        panelReportes.txtMesFin.addItem("Julio");
        panelReportes.txtMesFin.addItem("Agosto");
        panelReportes.txtMesFin.addItem("Septiembre");
        panelReportes.txtMesFin.addItem("Octubre");
        panelReportes.txtMesFin.addItem("Noviembre");
        panelReportes.txtMesFin.addItem("Diciembre");
        
    }
    
}
