/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.frms;

import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import main.controladores.BglTbParametrosJpaController;
import main.entidades.BglTbParametros;
import main.entidades.BglTbParametrosPK;
import main.entidades.BglTbUsuario;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class ifrmParametros extends javax.swing.JInternalFrame {

    public frmMain main;
    public EntityManagerFactory factory;
    public BglTbUsuario usuario;
    private final static Logger log=Logger.getLogger(ifrmParametros.class);
    private BglTbParametros misParametros;
    private BglTbParametrosJpaController controladorParametros;
    
    /**
     * Creates new form ifrmParametros
     * @param factory
     * @param frm
     * @param user
     */
    public ifrmParametros(EntityManagerFactory factory,frmMain frm,BglTbUsuario user) {
        initComponents();
        PropertyConfigurator.configure("log4j.properties");
        this.factory=factory;
        this.main=frm;
        this.usuario=user;
        
        llenarDatos();
        
    }

    
    private void llenarDatos(){
    
        try{
        
            controladorParametros= new BglTbParametrosJpaController(this.factory);
            BglTbParametrosPK paramPK=new BglTbParametrosPK(this.usuario.getUsuCodigo().toString(),"001");
            this.misParametros= controladorParametros.findBglTbParametros(paramPK);
        
            
            if(this.misParametros!=null)
            {
                this.txtUsuarioSesion.setText(this.misParametros.getBglTbParametrosPK().getParamUsuCodigo());
                this.txtIP.setText(this.misParametros.getParamDvrIp());
                this.txtPuerto.setText(String.valueOf(this.misParametros.getParamDvrPuerto()));
                this.txtUsuServ.setText(this.misParametros.getParamDvrUsuario());
                this.jpf_password.setText(this.misParametros.getParamDvrPassword());
                this.spTiempoMin.setValue(this.misParametros.getParamDvrTiempomini());
                this.spTiempoMax.setValue(this.misParametros.getParamDvrTiempomaxi());
            }
        
        }
        catch(Exception e){}
    }
    
    private void actualizarParametros(){
    
        
        String user=this.usuario.getUsuCodigo().toString();
        String ip =this.txtIP.getText();
        String puerto=this.txtPuerto.getText();
        String userDVR=this.txtUsuServ.getText();
        String passDVR=this.jpf_password.getText();
        int tmin=(int)this.spTiempoMin.getValue();
        int tmax=(int)this.spTiempoMax.getValue();
        
        if(user.equals("")||ip.equals("")||puerto.equals("")||userDVR.equals("")||passDVR.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe llenar todos los campos");
            return;
        }
        
        
        try{
        
                        
             this.misParametros.setBglTbParametrosPK(new BglTbParametrosPK(this.usuario.getUsuCodigo().toString(),"001"));
             this.misParametros.setParamAmbiente(1);
             this.misParametros.setParamDvrIp(ip);
             this.misParametros.setParamDvrPuerto(Integer.parseInt(puerto));
             this.misParametros.setParamDvrUsuario(userDVR);
             this.misParametros.setParamDvrPassword(passDVR);
             this.misParametros.setParamDvrTiempomini(tmin);
             this.misParametros.setParamDvrTiempomaxi(tmax);
            
            
            this.controladorParametros.edit(this.misParametros);
            
            JOptionPane.showMessageDialog(this,"Modificación realizada con éxito");
            llenarDatos();
            
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(this,"Error al actualizar el registro");
        JOptionPane.showMessageDialog(this,e.getMessage());
        log.error(e.getMessage());
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtUsuarioSesion = new javax.swing.JTextField();
        lbPuerto = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPuerto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUsuServ = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        spTiempoMin = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        spTiempoMax = new javax.swing.JSpinner();
        jpf_password = new javax.swing.JPasswordField();
        btnModificar = new javax.swing.JButton();

        setBackground(new java.awt.Color(15, 35, 53));
        setClosable(true);
        setIconifiable(true);
        setTitle("Parámetros de Configuración MDVR");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-antena.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Usuario");

        txtUsuarioSesion.setEditable(false);

        lbPuerto.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        lbPuerto.setForeground(new java.awt.Color(255, 255, 255));
        lbPuerto.setText("Puerto");

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("IP");

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Usuario Servidor");

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Contraseña");

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tiempo min.");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Tiempo max.");

        jpf_password.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jpf_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpf_passwordKeyPressed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-edit-undo.png"))); // NOI18N
        btnModificar.setText("Actualizar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(37, 37, 37)
                                .addComponent(txtUsuarioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(lbPuerto)
                                            .addComponent(jLabel5))
                                        .addGap(37, 37, 37))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(38, 38, 38)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsuServ)
                                    .addComponent(txtPuerto, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                    .addComponent(txtIP, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                                    .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel8)
                        .addGap(13, 13, 13)
                        .addComponent(spTiempoMin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel9)
                        .addGap(7, 7, 7)
                        .addComponent(spTiempoMax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnModificar)
                .addGap(162, 162, 162))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuarioSesion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPuerto))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsuServ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jpf_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(spTiempoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(spTiempoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jpf_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpf_passwordKeyPressed
        
        

    }//GEN-LAST:event_jpf_passwordKeyPressed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        actualizarParametros();
    }//GEN-LAST:event_btnModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jpf_password;
    private javax.swing.JLabel lbPuerto;
    private javax.swing.JSpinner spTiempoMax;
    private javax.swing.JSpinner spTiempoMin;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtPuerto;
    private javax.swing.JTextField txtUsuServ;
    private javax.swing.JTextField txtUsuarioSesion;
    // End of variables declaration//GEN-END:variables
}
