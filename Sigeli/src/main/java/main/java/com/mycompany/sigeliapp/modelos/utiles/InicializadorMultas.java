
package main.java.com.mycompany.sigeliapp.modelos.utiles;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import main.java.com.mycompany.sigeliapp.dao.*;
import main.java.com.mycompany.sigeliapp.modelos.*;
import main.java.com.mycompany.sigeliapp.vistas.CargaInicio;



public class InicializadorMultas {
    
    private IDaoMulta iDaoMulta;
    private IDaoPersona iDaoPersona;
    private IDaoLibro iDaoLibro;
    private IDaoPrestamos iDaoPrestamos;
    
    private CargaInicio ventanaCarga;
    
    private String mensaje = "";
    private ArrayList<Prestamos> arrayListPrestamos;

    public InicializadorMultas() {
        
        this.iDaoLibro = new DaoLibro();
        this.iDaoPersona = new DaoPersona();
        this.iDaoMulta = new DaoMulta();
        this.iDaoPrestamos = new DaoPrestamos();
        this.ventanaCarga = new CargaInicio();
        
        arrayListPrestamos = iDaoPrestamos.verPrestamos();
        
    }
    
    public void crearMultas(){
        
        int barra =0;
        
        if(arrayListPrestamos.size() != 0){
            barra = 100/arrayListPrestamos.size();
        }
        else{
            barra = 100;
        }
        
        visibleCarga();
        
        for(Prestamos prestamo : arrayListPrestamos){
            ventanaCarga.barraProgreso.setValue(barra);
            for(Libro libro : iDaoLibro.verLibros()){
                for(Persona persona : iDaoPersona.verPersonas()){
                    if(prestamo.getFechaEntrega().before(Date.valueOf(LocalDate.now())) && prestamo.getIdEstado() == 3){
                        if(prestamo.getIdPersona() == persona.getDocumentoPersona()){
                            if(libro.getIsbnLibro().equals(prestamo.getIsbnLibro())){
                                mensaje = "Cordial saludo " + persona.getNombrePersona() + ".\n\n" +
                                        "Le informamos que el prestamo número : " + prestamo.getIdPrestamo() +
                                        ", realizado en la fecha: " + prestamo.getFechaPrestamo() + " y debía ser entregado en la fecha: " + prestamo.getFechaEntrega() +
                                        " ha generado una multa con valor de $30.000 ya que el libro " + libro.getNombreLibro() + " no fue entregado entre las fechas establecidas \n" +
                                        "Si presenta alguna duda sobre la multa o el prestamo realizado, por favor presentarse en la biblioteca de la FESC\n\n" +
                                        "Muchas gracias.";

                                enviarConGMail(persona.getEmailPersona(), ("Multa del libro " + libro.getNombreLibro()), mensaje);

                                Multa multaEnviar = new Multa();

                                multaEnviar.setDocumentoPersona(prestamo.getIdPersona());
                                multaEnviar.setIdPrestamo(prestamo.getIdPrestamo());
                                multaEnviar.setValorMulta(1500);
                                multaEnviar.setFecha(Date.valueOf(LocalDate.now()));
                                multaEnviar.setEstadoMulta(6);

                                prestamo.setIdEstado(4);

                                if(iDaoMulta.hacerMultas(multaEnviar)){
                                    System.out.println("Multa añadida");
                                }
                                libro.setIdEstado(1);
                                if(iDaoLibro.cambioEstadoLibro(libro)){
                                    System.out.println("libro actualizado");
                                }
                                iDaoPrestamos.cambioEstado(prestamo);
                            }
                        }
                    }
                }
            }
            barra += barra;
        }
        
        if(barra == 100){
                ventanaCarga.mnsjInicio.setText("Iniciando aplicación");
                ventanaCarga.barraProgreso.setValue(0);
                for(int i = 0; i< 100; i+= 25){
                    ventanaCarga.barraProgreso.setValue(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(InicializadorMultas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        
        cerrarCarga();
    }
    
    public void enviarConGMail(String destinatario, String asunto, String cuerpo) {
                
        Properties propiedad = new Properties();
        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedad.setProperty("mail.smtp.starttls.enable", "true");
        propiedad.setProperty("mail.smtp.port", "587");
        propiedad.setProperty("mail.smtp.auth", "true");
        
        Session sesion = Session.getDefaultInstance(propiedad);
        
        //Correo
        String correoEnvia = "";
        //Clave
        String contrasena = "";
        
        
        MimeMessage mail = new MimeMessage(sesion);
        
        try {
            mail.setFrom(new InternetAddress (correoEnvia));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            mail.setSubject(asunto);
            mail.setText(cuerpo);
            
            
            Transport transporte = sesion.getTransport("smtp");
            transporte.connect(correoEnvia,contrasena);
            transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));

            
        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void visibleCarga(){
        ventanaCarga.setTitle("Iniciando - Sigeli");
        ventanaCarga.setLocationRelativeTo(null);
        ventanaCarga.setResizable(false);
        ventanaCarga.setVisible(true);
    }
    
    
    public void cerrarCarga(){
        ventanaCarga.setVisible(false);
    }
    
}
