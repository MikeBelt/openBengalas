/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.frms;

import java.awt.HeadlessException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.clases.Blowfish;
import main.clases.HashSalt;
import main.clases.PasswordUtil;
import main.controladores.BglTbUsuarioJpaController;
import main.entidades.BglTbUsuario;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class ifrmMantUsuarios extends javax.swing.JInternalFrame {

    public frmMain main;
    private final EntityManagerFactory factory;
    private final static Logger log=Logger.getLogger(ifrmMantUsuarios.class);
    private final BglTbUsuarioJpaController controladorUsuario;
    
    /**
     * Creates new form ifrmMantUsuarios
     * @param emf
     * @param frm
     */
    public ifrmMantUsuarios(EntityManagerFactory emf,frmMain frm) {
        initComponents();
        PropertyConfigurator.configure("log4j.properties");
        this.main=frm;
        this.factory=emf;
        controladorUsuario=new BglTbUsuarioJpaController(this.factory);
        CrearModeloTablaUsuarios();
        llenarTablaUsuarios();

    }

    public static DefaultTableModel modeloTablaUsuarios;
    private void CrearModeloTablaUsuarios(){
        try {
        modeloTablaUsuarios = (new DefaultTableModel(
        null, new String [] {
        "Id","Alias","Nombre","Apellido","Fec Ult Acceso"}){
        Class[] types = new Class [] {
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        };
        boolean[] canEdit = new boolean [] {
        false,false,false,false,false
        };
        @Override
        public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
        }
        @Override
        public boolean isCellEditable(int rowIndex, int colIndex){
        return canEdit [colIndex];
        }
        });
        this.jtUsuarios.setModel(modeloTablaUsuarios);
        
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null,e.toString()+"error2");
        }
}
    
    private void llenarTablaUsuarios(){

       try
       {
           
       List<BglTbUsuario> listaUsuarios=controladorUsuario.findBglTbUsuarioEntities();
      
       Object[] row=null;
       
           for(int i=0;i<listaUsuarios.size();i++)
           {

               modeloTablaUsuarios.addRow(row);
               modeloTablaUsuarios.setValueAt(listaUsuarios.get(i).getUsuCodigo(), i, 0);
               modeloTablaUsuarios.setValueAt(listaUsuarios.get(i).getUsuAlias(), i, 1);
               modeloTablaUsuarios.setValueAt(listaUsuarios.get(i).getUsuNombre(), i, 2);
               modeloTablaUsuarios.setValueAt(listaUsuarios.get(i).getUsuApellido(), i, 3);
               modeloTablaUsuarios.setValueAt(listaUsuarios.get(i).getUsuFeculting(), i, 4);

           }
       
       }
       catch(Exception e){
        JOptionPane.showMessageDialog(this,"Ha ocurrido un error al llenar la tala Usuarios");
       }
       
    }
    
    private void crearNuevoUsuario(){
    
        String user=this.txtAlias.getText();
        String pass=this.txtPassword.getText();

        if(user.equals("")||pass.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe llenar todos los campos");
            return;
        }
        
        try{
            BglTbUsuario nuevoUsuario=new BglTbUsuario();
            
            this.controladorUsuario.create(nuevoUsuario);
            JOptionPane.showMessageDialog(this,"Usuario guardado correctamente");
            
            
        }
        catch(HeadlessException ex){
            JOptionPane.showMessageDialog(this,"Ha ocurrido un error al crear nuevo usuario");
        }
        
        
        
    }
    
    private void limpiarControles(){
    
        this.txtFechaRegistro.setText("");
        this.txtId.setText("");
        this.txtAlias.setText("");
        this.txtPassword.setText("");
    }
    
    private String encriptarClaveBlowfish(String userPassword){
        String passwordEncriptado = null;
        try {
            passwordEncriptado=Blowfish.encriptar(userPassword, "tesis");
            
        }
        catch (Exception ex) {
        ex.printStackTrace();
        } 
        
        return passwordEncriptado;
    }
    
    private String[] encriptarClavePKSC5(String userPassword){
        String[] passwordEncriptado = null;
        try{
            
        HashSalt hs = PasswordUtil.getHash(userPassword);
//	System.out.println(hs.getHash()); // bWlQYXNzd29yZA==
//	System.out.println(hs.getSalt()); // SMB6x5uRy4kIPYN512ibug==
        
	passwordEncriptado[0]=hs.getHash();
        passwordEncriptado[1]=hs.getSalt();
        
	boolean isValid = PasswordUtil.ValidatePass(userPassword, hs.getHash(), hs.getSalt());
	System.out.println(isValid ? "válida" : "no válida"); // no válida
        
        }
        catch(Exception e){e.printStackTrace();}
        
        return passwordEncriptado;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BengalaProyectPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("BengalaProyectPU").createEntityManager();
        bglTbUsuarioQuery = java.beans.Beans.isDesignTime() ? null : BengalaProyectPUEntityManager.createQuery("SELECT b FROM BglTbUsuario b");
        bglTbUsuarioList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bglTbUsuarioQuery.getResultList();
        bglTbUsuarioQuery1 = java.beans.Beans.isDesignTime() ? null : BengalaProyectPUEntityManager.createQuery("SELECT b FROM BglTbUsuario b");
        bglTbUsuarioList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bglTbUsuarioQuery1.getResultList();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtUsuarios = new javax.swing.JTable();
        panelBotones = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtFechaRegistro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtAlias = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();

        setBackground(new java.awt.Color(15, 35, 53));
        setClosable(true);
        setTitle("Mantenimiento de Usuarios");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-antena.png"))); // NOI18N

        jtUsuarios.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jtUsuarios.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jtUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtUsuarios.setColumnSelectionAllowed(true);
        jtUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtUsuarios);
        jtUsuarios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        panelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelBotones.setOpaque(false);

        btnGuardar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-gif-save.gif"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnEliminar.setText("Eliminar");

        btnModificar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnModificar.setText("Modificar");

        btnNuevo.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBotonesLayout = new javax.swing.GroupLayout(panelBotones);
        panelBotones.setLayout(panelBotonesLayout);
        panelBotonesLayout.setHorizontalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBotonesLayout.setVerticalGroup(
            panelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información de Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Id Usuario");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        txtId.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jPanel2.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 80, -1));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Fecha Registro");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, -1, -1));

        txtFechaRegistro.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jPanel2.add(txtFechaRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 110, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre usuario");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        txtAlias.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jPanel2.add(txtAlias, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 210, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Password");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        txtPassword.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtPassword.setText("jPasswordField1");
        jPanel2.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 120, -1));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nombre");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        txtNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 160, -1));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Apellido");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, -1));

        txtApellido.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jPanel2.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(474, Short.MAX_VALUE)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 630, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(18, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(203, Short.MAX_VALUE)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 12, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(3, 3, 3)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        this.txtId.setText("Automático");
        limpiarControles();
        
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        crearNuevoUsuario();
    }//GEN-LAST:event_btnGuardarActionPerformed

    
    private void jtUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtUsuariosMouseClicked
        // TODO add your handling code here:
//        int row = jtUsuarios.rowAtPoint(evt.getPoint());
        int row=jtUsuarios.getSelectedRow();

       /* row devolvera -1 si se ha clicado fuera de la fila pero dentro de la tabla, si no devolvera el indice de la fila en la que se ha clicado. */

       txtId.setText(jtUsuarios.getValueAt(row, 0).toString());
       txtAlias.setText(jtUsuarios.getValueAt(row, 1).toString());
       txtNombre.setText(jtUsuarios.getValueAt(row, 2).toString());
       txtApellido.setText(jtUsuarios.getValueAt(row, 3).toString());
       txtFechaRegistro.setText(jtUsuarios.getValueAt(row, 4).toString());
        
    }//GEN-LAST:event_jtUsuariosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager BengalaProyectPUEntityManager;
    private java.util.List<main.entidades.BglTbUsuario> bglTbUsuarioList;
    private java.util.List<main.entidades.BglTbUsuario> bglTbUsuarioList1;
    private javax.persistence.Query bglTbUsuarioQuery;
    private javax.persistence.Query bglTbUsuarioQuery1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtUsuarios;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JTextField txtAlias;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtFechaRegistro;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
