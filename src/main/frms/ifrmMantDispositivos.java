/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.frms;

import java.awt.HeadlessException;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import main.controladores.BglTbTipodispositivoJpaController;
import main.entidades.BglTbTipodispositivo;
import main.entidades.BglTbUsuario;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.controladores.BglTbDispositivoJpaController;
import main.controladores.BglTbSitioincidenteJpaController;
import main.controladores.exceptions.NonexistentEntityException;
import main.entidades.BglTbDispositivo;
import main.entidades.BglTbSitioincidente;

/**
 *
 * @author michael.beltran
 */
public class ifrmMantDispositivos extends javax.swing.JInternalFrame {

    public frmMain main;
    public EntityManagerFactory factory;
    private BglTbUsuario usuario;
    private BglTbDispositivoJpaController controladorDisp;
    private BglTbDispositivo miDispositivo;
    private final static Logger log=Logger.getLogger(ifrmMantDispositivos.class);
    
    /**
     * Creates new form ifrmMantDispositivos
     * @param factory
     * @param frm
     */
    public ifrmMantDispositivos(EntityManagerFactory factory,frmMain frm) {
        initComponents();
        PropertyConfigurator.configure("log4j.properties");
        this.factory=factory;
        this.main=frm;
        crearModeloTablaDisp();
        llenarTablaDisp();
        llenarComboTipoDisp();
        llenarSitiosIncidentes();
    }

    public static DefaultTableModel dtmDisp;
    private void crearModeloTablaDisp(){
        try {
        dtmDisp = (new DefaultTableModel(
        null, new String [] {
        "Id","Etiqueta","Marca","Modelo","Fabricante","Año Fab.","Serial","Costo"}){
        Class[] types = new Class [] {
        BglTbDispositivo.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.Integer.class,
        java.lang.String.class,
        java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
        false,false,false,false,false,false,false,false
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
        this.tbDispositivos.setModel(dtmDisp);
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.toString());
        }
}
    
    private void llenarTablaDisp(){

       try
       {
                    
       controladorDisp=new BglTbDispositivoJpaController(this.factory);    
       List<BglTbDispositivo> listaDisp=controladorDisp.findBglTbDispositivoEntities();
      
       Object[] row=null;
       
           for(int i=0;i<listaDisp.size();i++)
           {

               dtmDisp.addRow(row);
               dtmDisp.setValueAt(listaDisp.get(i),i,0);
               dtmDisp.setValueAt(listaDisp.get(i).getDispEtiqueta(), i, 1);
               dtmDisp.setValueAt(listaDisp.get(i).getDispMarca(), i, 2);
               dtmDisp.setValueAt(listaDisp.get(i).getDispModelo(), i, 3);
               dtmDisp.setValueAt(listaDisp.get(i).getDispFabricante(), i, 4);
               dtmDisp.setValueAt(listaDisp.get(i).getDispAniofab(), i,5);
               dtmDisp.setValueAt(listaDisp.get(i).getDispSerial(), i, 6);
               dtmDisp.setValueAt(listaDisp.get(i).getDispCosto(), i, 7);
               

           }
       
       }
       catch(Exception e){
        JOptionPane.showMessageDialog(this,"Ha ocurrido un error al llenar la tabla de Sitios");
        JOptionPane.showMessageDialog(this,e.getMessage());
        log.error(e.getMessage());
       }
       
    }
    
    DefaultComboBoxModel dcmTipoDisp;
    private void llenarComboTipoDisp(){
    
        
        BglTbTipodispositivoJpaController controler= new BglTbTipodispositivoJpaController(this.factory);
        List<BglTbTipodispositivo> listaDisp;
        listaDisp=controler.findBglTbTipodispositivoEntities();
        
       
//        for(int i=0;i<listaDisp.size();i++)
//        {
//            dcmTipoDisp.addElement(listaDisp.get(i).getTdispDescripcion());
//        }


        dcmTipoDisp=new DefaultComboBoxModel(listaDisp.toArray());
        cbTipoDisp.setModel(dcmTipoDisp);
    }
    
    DefaultComboBoxModel dcmSitioIncidente;
    private void llenarSitiosIncidentes(){
    
        
        BglTbSitioincidenteJpaController controlerSitios=new BglTbSitioincidenteJpaController(this.factory);
        List<BglTbSitioincidente> listaSitios;
        listaSitios=controlerSitios.findBglTbSitioincidenteEntities();
//        for(int i=0;i<listaSitios.size();i++)
//        {
//            dcmSitioIncidente.addElement(listaSitios.get(i).getSitNombre());
//        }
        
        dcmSitioIncidente=new DefaultComboBoxModel(listaSitios.toArray());
        cbSitioIncidente.setModel(dcmSitioIncidente);
    }
    
    private void nuevoDispositivo(){
    
        txtCodigo.setText("");
        txtEtiqueta.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtFabricante.setText("");
        txtSerial.setText("");
        txtCosto.setText("");
        
        btnGuardar.setEnabled(true);
        
    }
    
    private void creaNuevoDispositivo(){
    
        String id=this.txtCodigo.getText();
        String etiqueta=this.txtEtiqueta.getText();
        String fabricante=this.txtFabricante.getText();
        String marca=this.txtMarca.getText();
        String modelo=this.txtModelo.getText();
        String serial=this.txtSerial.getText();
        int anioFab=this.ycAnio.getValue();
        String costo=this.txtCosto.getText();

        if(id.equals("")||etiqueta.equals("")||costo.equals("")||fabricante.equals("")||marca.equals("")||modelo.equals("")||serial.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe llenar todos los campos");
            return;
        }
        
        try{
            
            int codigo=Integer.parseInt(id);
            
            BglTbDispositivo nuevoDispositivo= new BglTbDispositivo();
            nuevoDispositivo.setDispAniofab(anioFab);
            nuevoDispositivo.setDispCodigo(codigo);
            nuevoDispositivo.setDispCosto(Double.parseDouble(costo));
            nuevoDispositivo.setDispEtiqueta(etiqueta);
            nuevoDispositivo.setDispFabricante(fabricante);
            nuevoDispositivo.setDispMarca(marca);
            nuevoDispositivo.setDispModelo(modelo);
            nuevoDispositivo.setDispSerial(serial);
            
            BglTbTipodispositivo tipoDisp=(BglTbTipodispositivo)cbTipoDisp.getSelectedItem();
            nuevoDispositivo.setDispTdispCodigo(tipoDisp);
            
            BglTbSitioincidente sitioIncidente=(BglTbSitioincidente) cbSitioIncidente.getSelectedItem();
            nuevoDispositivo.setDispSitCodigo(sitioIncidente);
            
            this.controladorDisp.create(nuevoDispositivo);
            JOptionPane.showMessageDialog(this,"Dispositivo guardado correctamente");
            
            crearModeloTablaDisp();
            llenarTablaDisp();
            btnGuardar.setEnabled(false);
            
            
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Ha ocurrido un error al crear nuevo dispositivo");
            JOptionPane.showMessageDialog(this, ex);
            log.error(ex);
        }
        
        
    }
    
    private void seleccionarDispositivo(){
    
        if(btnGuardar.isEnabled())
        {
            return;
        }
        
        try{
             
             this.miDispositivo=(BglTbDispositivo)tbDispositivos.getValueAt(tbDispositivos.getSelectedRow(), 0);
        
             txtCodigo.setText(miDispositivo.getDispCodigo().toString());
             txtEtiqueta.setText(miDispositivo.getDispEtiqueta());
             txtFabricante.setText(miDispositivo.getDispFabricante());
             txtModelo.setText(miDispositivo.getDispModelo());
             txtMarca.setText(miDispositivo.getDispMarca());
             txtCosto.setText(miDispositivo.getDispCosto().toString());
             txtSerial.setText(miDispositivo.getDispSerial());
             ycAnio.setValue(miDispositivo.getDispAniofab());

             BglTbTipodispositivo tipo=miDispositivo.getDispTdispCodigo();
             cbTipoDisp.setSelectedItem(tipo);
             
             BglTbSitioincidente sitio=miDispositivo.getDispSitCodigo();
             cbSitioIncidente.setSelectedItem(sitio);
             
             
        }
        catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Error al seleccionar el registro de la tabla");
            JOptionPane.showMessageDialog(this,e.getMessage());
            log.error(e.getMessage());
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"Error al seleccionar el registro de la tabla");
            JOptionPane.showMessageDialog(this,e.getMessage());
            log.error(e.getMessage());
        }
        
    }
    
    private void eliminarSitioSeleccionado(){
    
        try{
            int resp = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el registro?","Atención",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(resp==1||resp==2)
                return;
            
            this.miDispositivo=(BglTbDispositivo)tbDispositivos.getValueAt(tbDispositivos.getSelectedRow(), 0);
            controladorDisp.destroy(miDispositivo.getDispCodigo());
            
             JOptionPane.showMessageDialog(this,"Registro eliminado con éxito");
            
        }
        catch(HeadlessException | NonexistentEntityException e){
         JOptionPane.showMessageDialog(this,"Error al eliminar el registro");
         JOptionPane.showMessageDialog(this,e.getMessage());
        log.error(e.getMessage());
        }
    }
    
    private void actualizarDispositivo(){
    
        String codigo=this.txtCodigo.getText();
        String etiqueta=this.txtEtiqueta.getText();
        String fabricante=this.txtFabricante.getText();
        String marca=this.txtMarca.getText();
        String modelo=this.txtModelo.getText();
        String serial=this.txtSerial.getText();
        int anioFab=ycAnio.getValue();
        String costo=this.txtCosto.getText();
        

        if(codigo.equals("")||etiqueta.equals("")||fabricante.equals("")||marca.equals("")||modelo.equals("")||serial.equals("")||costo.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe llenar todos los campos");
            return;
        }
        
        
        try{
        
            int cod=Integer.parseInt(codigo);
            double dcosto=Double.parseDouble(costo);

           
            
             this.miDispositivo.setDispCodigo(cod);
             this.miDispositivo.setDispEtiqueta(etiqueta);
             this.miDispositivo.setDispAniofab(anioFab);
             this.miDispositivo.setDispCosto(dcosto);
             this.miDispositivo.setDispMarca(marca);
             this.miDispositivo.setDispFabricante(fabricante);
             this.miDispositivo.setDispModelo(modelo);
             this.miDispositivo.setDispSerial(serial);
             this.miDispositivo.setDispTdispCodigo((BglTbTipodispositivo)cbTipoDisp.getSelectedItem());
             this.miDispositivo.setDispSitCodigo((BglTbSitioincidente)cbSitioIncidente.getSelectedItem());
            
            
            
            this.controladorDisp.edit(this.miDispositivo);
            
            JOptionPane.showMessageDialog(this,"Modificación realizada con éxito");
            crearModeloTablaDisp();
            llenarTablaDisp();
            
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

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFabricante = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtSerial = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cbTipoDisp = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtEtiqueta = new javax.swing.JTextField();
        jlabelMarca = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        ycAnio = new com.toedter.calendar.JYearChooser();
        jLabel9 = new javax.swing.JLabel();
        cbSitioIncidente = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDispositivos = new javax.swing.JTable();

        setBackground(new java.awt.Color(15, 35, 53));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Mantenimiento Dispositivos");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-antena.png"))); // NOI18N
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGuardar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 100, -1));

        btnModificar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 100, -1));

        btnEliminar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 100, -1));

        btnNuevo.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 100, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 130, 250));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Costo");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, -1, -1));
        jPanel2.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 60, -1));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Año Fabricacion");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, 20));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fabricante");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));
        jPanel2.add(txtFabricante, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 210, -1));

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Serial");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));
        jPanel2.add(txtSerial, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 190, 210, -1));
        jPanel2.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 120, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Código");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tipo");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        cbTipoDisp.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jPanel2.add(cbTipoDisp, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 191, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Etiqueta");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));
        jPanel2.add(txtEtiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 183, -1));

        jlabelMarca.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jlabelMarca.setForeground(new java.awt.Color(255, 255, 255));
        jlabelMarca.setText("Marca");
        jPanel2.add(jlabelMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));
        jPanel2.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 142, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Modelo");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));
        jPanel2.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 142, -1));
        jPanel2.add(ycAnio, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 50, -1));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Sitio");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, -1, -1));

        cbSitioIncidente.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jPanel2.add(cbSitioIncidente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, 210, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 420, 250));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        tbDispositivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDispositivos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDispositivosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDispositivos);

        jPanel3.add(jScrollPane1);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 570, 120));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
        creaNuevoDispositivo();
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        
        nuevoDispositivo();
        
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        
        this.main.frmMantDispositivos=null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void tbDispositivosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDispositivosMouseClicked
        seleccionarDispositivo();
    }//GEN-LAST:event_tbDispositivosMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        actualizarDispositivo();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarSitioSeleccionado();
    }//GEN-LAST:event_btnEliminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cbSitioIncidente;
    private javax.swing.JComboBox<String> cbTipoDisp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlabelMarca;
    private javax.swing.JTable tbDispositivos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtEtiqueta;
    private javax.swing.JTextField txtFabricante;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtSerial;
    private com.toedter.calendar.JYearChooser ycAnio;
    // End of variables declaration//GEN-END:variables
}
