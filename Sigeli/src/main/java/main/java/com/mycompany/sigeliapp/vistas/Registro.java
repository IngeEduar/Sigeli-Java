/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.mycompany.sigeliapp.vistas;


/**
 *
 * @author Eduar Xavier
 */
public class Registro extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    
    public Registro() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDocumentoRegistro = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombreRegistro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmailRegistro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTelefonoRegistro = new javax.swing.JTextField();
        boxCarreraRegistro = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        boxTipoRegistro = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtClaveRegistro = new javax.swing.JPasswordField();
        btnRegistro = new javax.swing.JButton();
        volverLogin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jRadioButton1.setText("jRadioButton1");

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("REGISTRO");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, -1, -1));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Documento");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        txtDocumentoRegistro.setBackground(new java.awt.Color(153, 153, 153));
        txtDocumentoRegistro.setForeground(new java.awt.Color(51, 51, 51));
        txtDocumentoRegistro.setBorder(null);
        getContentPane().add(txtDocumentoRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 230, 30));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Nombre completo");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        txtNombreRegistro.setBackground(new java.awt.Color(153, 153, 153));
        txtNombreRegistro.setForeground(new java.awt.Color(51, 51, 51));
        txtNombreRegistro.setBorder(null);
        getContentPane().add(txtNombreRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 230, 30));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Email");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, -1));

        txtEmailRegistro.setBackground(new java.awt.Color(153, 153, 153));
        txtEmailRegistro.setForeground(new java.awt.Color(51, 51, 51));
        txtEmailRegistro.setBorder(null);
        getContentPane().add(txtEmailRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 230, 30));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Telefono");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Carrera");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, -1, -1));

        txtTelefonoRegistro.setBackground(new java.awt.Color(153, 153, 153));
        txtTelefonoRegistro.setForeground(new java.awt.Color(51, 51, 51));
        txtTelefonoRegistro.setBorder(null);
        getContentPane().add(txtTelefonoRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 196, 230, 30));

        boxCarreraRegistro.setBackground(new java.awt.Color(153, 153, 153));
        boxCarreraRegistro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boxCarreraRegistro.setBorder(null);
        getContentPane().add(boxCarreraRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 230, 30));

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Tipo de persona");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, -1, -1));

        boxTipoRegistro.setBackground(new java.awt.Color(153, 153, 153));
        boxTipoRegistro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        boxTipoRegistro.setBorder(null);
        getContentPane().add(boxTipoRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 230, 30));

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Contraseña");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, -1, -1));

        txtClaveRegistro.setBackground(new java.awt.Color(153, 153, 153));
        txtClaveRegistro.setForeground(new java.awt.Color(51, 51, 51));
        txtClaveRegistro.setBorder(null);
        getContentPane().add(txtClaveRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 230, 30));

        btnRegistro.setBackground(new java.awt.Color(255, 51, 51));
        btnRegistro.setForeground(new java.awt.Color(204, 204, 204));
        btnRegistro.setText("Registrarse");
        btnRegistro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(btnRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 470, 110, 30));

        volverLogin.setBackground(new java.awt.Color(255, 255, 255));
        volverLogin.setForeground(new java.awt.Color(51, 51, 51));
        volverLogin.setText("Cancelar");
        volverLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        volverLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverLoginActionPerformed(evt);
            }
        });
        getContentPane().add(volverLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 470, 110, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Eduar Xavier\\Desktop\\EduarXavier\\sigeli\\Sigeli\\src\\main\\java\\main\\java\\com\\mycompany\\sigeliapp\\vistas\\imagenes\\interfazRegistro.png")); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void volverLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_volverLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JComboBox<String> boxCarreraRegistro;
    public javax.swing.JComboBox<String> boxTipoRegistro;
    public javax.swing.JButton btnRegistro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JSlider jSlider1;
    public javax.swing.JPasswordField txtClaveRegistro;
    public javax.swing.JTextField txtDocumentoRegistro;
    public javax.swing.JTextField txtEmailRegistro;
    public javax.swing.JTextField txtNombreRegistro;
    public javax.swing.JTextField txtTelefonoRegistro;
    public javax.swing.JButton volverLogin;
    // End of variables declaration//GEN-END:variables
}