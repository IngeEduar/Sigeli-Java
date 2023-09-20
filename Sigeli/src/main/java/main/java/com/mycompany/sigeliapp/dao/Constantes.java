package main.java.com.mycompany.sigeliapp.dao;


public class Constantes {
    public static final String URL="jdbc:mysql://localhost:3306/";
    public static final String DATABASE="sigeli";
    public static final String USER="root";
    public static final String PASSWORD="";
    
    
    public static final String T_CARRERA = "carrera";
    public static final String TC_ID = "id_carrera";
    public static final String TC_CARREERA = "nombre_carrera";
    
    
    public static final String T_CATEGORIA = "categoria";
    public static final String TCAT_ID = "id_categoria";
    public static final String TCAT_CATEGORIA = "nombre_categoria";
    
    public static final String T_CATLIBRO = "categoria_libro";
    public static final String TCL_ISBN = "isbn_libro";
    public static final String TCL_IDCATEGORIA = "id_categoria";
    
    public static final String T_ESTADO = "estado";
    public static final String TE_ID = "id_estado";
    public static final String TE_ESTADO = "estado";
    
    public static final String T_LIBRO = "libro";
    public static final String TL_IDLIBRO = "id_libro";
    public static final String TL_ISBN = "isbn_libro";
    public static final String TL_NOMBRE = "nombre_libro";
    public static final String TL_AUTOR = "nombre_autor";
    public static final String TL_EDICION = "edicion_libro";
    public static final String TL_ANO = "ano_Libro";
    public static final String TL_ESTANTE = "estante_libro";
    public static final String TL_FILA = "fila_libro";
    public static final String TL_IDESTADO = "id_estado";
    
    public static final String T_MULTA = "multa";
    public static final String TM_ID = "id_multa";
    public static final String TM_DOCUMENTO = "documento_persona";
    public static final String TM_IDPRESTAMO = "id_prestamo";
    public static final String TM_VALOR = "valor_multa";
    public static final String TM_IDESTADO = "id_estado";
    public static final String TM_FECHA = "fecha";
    
    public static final String T_PERSONA = "persona";
    public static final String TP_DOCUMENTO = "documento_persona";
    public static final String TP_NOMBRE = "nombre";
    public static final String TP_IDCARRERA = "id_carrera";
    public static final String TP_EMAIL = "correo_electronico";
    public static final String TP_TELEFONO = "telefono";
    
    public static final String T_PRESTAMO = "prestamos";
    public static final String TPR_ID = "id_prestamo";
    public static final String TPR_DOCUMENTO = "documento_persona";
    public static final String TPR_ISBN = "isbn_libro";
    public static final String TPR_FECHAPRESTAMO = "fecha_prestamo";
    public static final String TPR_FECHAENTREGA = "fecha_entrega";
    public static final String TPR_IDESTADO = "id_estado";
    
    public static final String T_USUARIO = "usuario";
    public static final String TU_DOCUMENTO = "documento_persona";
    public static final String TU_CLAVE = "clave";
    public static final String TU_CARGO = "id_cargo";
    
}
