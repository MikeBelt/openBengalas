/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.frms;

import java.awt.Image;
import main.controladores.BglTbCiudadJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.controladores.BglTbCatalogoincidentesJpaController;
import main.controladores.BglTbEmergenciaJpaController;
import main.controladores.BglTbEstadoemergenciaJpaController;
import main.controladores.BglTbSectorJpaController;
import main.controladores.BglTbTipoincidenteJpaController;
import main.controladores.BglTbTrackingJpaController;
import main.entidades.BglTbCatalogoincidentes;
import main.entidades.BglTbCiudad;
import main.entidades.BglTbEmergencia;
import main.entidades.BglTbEstadoemergencia;
import main.entidades.BglTbSector;
import main.entidades.BglTbTipoincidente;
import main.entidades.BglTbTracking;
import main.entidades.BglTbUsuario;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class ifrmEmergencias extends javax.swing.JInternalFrame {

    public frmMain main;
    public EntityManagerFactory factory;
    private BglTbUsuario usuario;
    private BglTbEmergenciaJpaController controladorEmergencia;
    private BglTbEmergencia emergencia;
    private final static Logger log=Logger.getLogger(ifrmLogin.class);
    
    /**
     * Creates new form ifrmEmergencias
     * @param factory
     * @param frm
     */
    public ifrmEmergencias(EntityManagerFactory factory,frmMain frm) {
        initComponents();
        PropertyConfigurator.configure("log4j.properties");
        this.factory=factory;
        this.main=frm;
        controladorEmergencia=new BglTbEmergenciaJpaController(this.factory);
        llenarComboCiudades();
        llenarComboEstado();
        llenarComboSector();
        llenarComboTipoEm();
        llenarComboCatalogo();
        crearModeloEmergencia();
        llenarTablaEmergencias();
        
    }

    public void renderizarLogos()
    {
        try
        {
        
        ImageIcon img=new ImageIcon("src/main/img/sirena-alarma.gif");
        Icon icon=new ImageIcon(img.getImage().getScaledInstance(jlbImagenSirena.getWidth(),jlbImagenSirena.getHeight() , Image.SCALE_DEFAULT));
        jlbImagenSirena.setIcon(icon);
        

        this.repaint();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,"Error al renderizar logos");
            System.out.println("Error al renderizar logos: "+ex.getMessage());
        }
    }
    
   DefaultComboBoxModel dcmCiudad;
    private void llenarComboCiudades(){
   
       
       BglTbCiudadJpaController controlerCiudad=new BglTbCiudadJpaController(this.factory);
       List<BglTbCiudad> listaCiudades;
       listaCiudades=controlerCiudad.findBglTbCiudadEntities();
       
       dcmCiudad=new DefaultComboBoxModel(listaCiudades.toArray());
       cbCiudad.setModel(dcmCiudad);
       

   }
   
   DefaultComboBoxModel dcmSector;
    private void llenarComboSector(){
       
       
       BglTbSectorJpaController controlerSector=new BglTbSectorJpaController(this.factory);
       List<BglTbSector> listaSectores;
       listaSectores=controlerSector.findBglTbSectorEntities();
       
       dcmSector=new DefaultComboBoxModel(listaSectores.toArray());
       cbSector.setModel(dcmSector);
       
       
   }
   
   DefaultComboBoxModel dcmTipoEm=new DefaultComboBoxModel();
   private void llenarComboTipoEm(){
   
       cbTipoEmergencia.setModel(dcmTipoEm);
       BglTbTipoincidenteJpaController controlerTipoincidente=new BglTbTipoincidenteJpaController(this.factory);
       List<BglTbTipoincidente> listaTipos;
       listaTipos=controlerTipoincidente.findBglTbTipoincidenteEntities();
       for(int i=0;i<listaTipos.size();i++)
       {
           dcmTipoEm.addElement(listaTipos.get(i).getTincidDescripcion());
       }
       
   }
   
   DefaultComboBoxModel dcmCatalogo=new DefaultComboBoxModel();
   private void llenarComboCatalogo(){
   
       cbCatalogoEmergencia.setModel(dcmCatalogo);
       BglTbCatalogoincidentesJpaController controlerCatalogo=new BglTbCatalogoincidentesJpaController(this.factory);
        List<BglTbCatalogoincidentes> listaCatalogo;
        listaCatalogo=controlerCatalogo.findBglTbCatalogoincidentesEntities();
        for(int i=0;i<listaCatalogo.size();i++)
        {
            dcmCatalogo.addElement(listaCatalogo.get(i).getCatDescripcion());
        }
        
        
       
   }
   
   DefaultComboBoxModel dcmEstado=new DefaultComboBoxModel();
   private void llenarComboEstado(){
   
       cbEstado.setModel(dcmEstado);
       BglTbEstadoemergenciaJpaController  controlerEstadoEm=new BglTbEstadoemergenciaJpaController(this.factory);
       List<BglTbEstadoemergencia> listaEstadosEm;
       listaEstadosEm=controlerEstadoEm.findBglTbEstadoemergenciaEntities();
       for(int i=0;i<listaEstadosEm.size();i++)
       {
           dcmEstado.addElement(listaEstadosEm.get(i).getEstDescCorta());
       }
       
   }
   
   public static DefaultTableModel modeloEmergencia;
        private void crearModeloEmergencia(){
        try {
        modeloEmergencia = (new DefaultTableModel(
        null, new String [] {
        "Cod Evento","Sitio","Fecha y Hora","Dispositivo","Latitud","Longitud","Ciudad","Observacion"}){
        Class[] types = new Class [] {
        java.lang.Integer.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
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
        this.jtEmergencias.setModel(modeloEmergencia);
        } catch (Exception e) {
        JOptionPane.showMessageDialog(null,e.toString()+"error");
        }
}
   
   private void llenarTablaEmergencias(){
    try
       {
        
       BglTbTrackingJpaController  controladorTracking=new  BglTbTrackingJpaController(this.factory);  
       List<BglTbTracking> listaEmergencias=controladorTracking.findBglTbTrackingEntities();
      
       Object[] row=null;
       
           for(int i=0;i<listaEmergencias.size();i++)
           {

               modeloEmergencia.addRow(row);
               modeloEmergencia.setValueAt(listaEmergencias.get(i).getCodEvento(), i, 0);
               modeloEmergencia.setValueAt(listaEmergencias.get(i).getCodSitio(), i, 1);
               modeloEmergencia.setValueAt(listaEmergencias.get(i).getTrkFecha(), i, 2);
               modeloEmergencia.setValueAt(listaEmergencias.get(i).getAlias(), i, 3);
               modeloEmergencia.setValueAt(listaEmergencias.get(i).getLatitude() , i, 4);
               modeloEmergencia.setValueAt(listaEmergencias.get(i).getLongitude(), i, 5);
               modeloEmergencia.setValueAt(listaEmergencias.get(i).getCity(), i, 6);
               modeloEmergencia.setValueAt(listaEmergencias.get(i).getTrkObservacion(), i, 7);

           }
       
       }
       catch(Exception e){
        JOptionPane.showMessageDialog(this,"Ha ocurrido un error al llenar la tala Usuarios");
       }
   }
   
   
   private void instanciarEmergencia(){
   
       ifrmDetalleEmergencia detalleEm=new ifrmDetalleEmergencia(this.factory, this.main);
       this.main.configurarPantallas(detalleEm);
       detalleEm.emergencia=this.emergencia; 
       detalleEm.mostrarDatosEmergencia();
       
   }
   
   private void seleccionarEmergencia(){
   try{
             int row=jtEmergencias.getSelectedRow();

             this.emergencia=new BglTbEmergencia();
                     
             
            this.emergencia.setEmIdfuente(jtEmergencias.getValueAt(row, 0).toString());
            this.emergencia.setEmDireccion(jtEmergencias.getValueAt(row, 1).toString());
            this.emergencia.setEmTimestamp(jtEmergencias.getValueAt(row, 2).toString());
            this.emergencia.setEmNombre(jtEmergencias.getValueAt(row, 3).toString());
            this.emergencia.setEnLatitud(Double.parseDouble(jtEmergencias.getValueAt(row, 4).toString()));
            this.emergencia.setEmLongitud(Double.parseDouble(jtEmergencias.getValueAt(row, 5).toString()));
            this.emergencia.setEmDireccion(jtEmergencias.getValueAt(row, 6).toString());
            this.emergencia.setEmDescripcion(jtEmergencias.getValueAt(row, 7).toString());
            
            this.emergencia.setEmCedula("999999999");
            this.emergencia.setEmApellido("PRUEBA");
            
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
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BengalaProyectPUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("BengalaProyectPU").createEntityManager();
        bglTbCiudadQuery = java.beans.Beans.isDesignTime() ? null : BengalaProyectPUEntityManager.createQuery("SELECT b FROM BglTbCiudad b");
        bglTbCiudadList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bglTbCiudadQuery.getResultList();
        bglTbSectorQuery = java.beans.Beans.isDesignTime() ? null : BengalaProyectPUEntityManager.createQuery("SELECT b FROM BglTbSector b");
        bglTbSectorList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bglTbSectorQuery.getResultList();
        bglTbTipoincidenteQuery = java.beans.Beans.isDesignTime() ? null : BengalaProyectPUEntityManager.createQuery("SELECT b FROM BglTbTipoincidente b");
        bglTbTipoincidenteList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bglTbTipoincidenteQuery.getResultList();
        bglTbEstadoemergenciaQuery = java.beans.Beans.isDesignTime() ? null : BengalaProyectPUEntityManager.createQuery("SELECT b FROM BglTbEstadoemergencia b");
        bglTbEstadoemergenciaList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bglTbEstadoemergenciaQuery.getResultList();
        jpAcciones = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbCiudad = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbSector = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbTipoEmergencia = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cbEstado = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbCatalogoEmergencia = new javax.swing.JComboBox<>();
        dcFechaInic = new com.toedter.calendar.JDateChooser();
        dcFechaFin = new com.toedter.calendar.JDateChooser();
        jlbImagenSirena = new javax.swing.JLabel();
        jpListadoEmergencias1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtEmergencias = new javax.swing.JTable();
        btnEmergencia = new javax.swing.JButton();
        jpListadoEmergencias = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtActividades = new javax.swing.JTable();

        setBackground(new java.awt.Color(15, 35, 53));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Monitor de Emergencias");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-antena.png"))); // NOI18N

        jpAcciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros de búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jpAcciones.setOpaque(false);
        jpAcciones.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("al");
        jpAcciones.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 20, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ciudad");
        jLabel2.setToolTipText("");
        jpAcciones.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        cbCiudad.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jpAcciones.add(cbCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 150, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fecha Incidente");
        jpAcciones.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Sector");
        jLabel4.setToolTipText("");
        jpAcciones.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, -1, -1));

        cbSector.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jpAcciones.add(cbSector, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 150, -1));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tipo Emergencia");
        jLabel5.setToolTipText("");
        jpAcciones.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        cbTipoEmergencia.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jpAcciones.add(cbTipoEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 190, -1));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Estado de la emergencia:");
        jLabel6.setToolTipText("");
        jpAcciones.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 140, -1));

        cbEstado.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jpAcciones.add(cbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 120, -1));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Catalogo");
        jpAcciones.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jpAcciones.add(cbCatalogoEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 210, -1));

        dcFechaInic.setDateFormatString("yyyy-MMM-dd");
        jpAcciones.add(dcFechaInic, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        dcFechaFin.setDateFormatString("yyyy-MMM-dd");
        jpAcciones.add(dcFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, -1));

        jlbImagenSirena.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/sirena-alarma.gif"))); // NOI18N
        jlbImagenSirena.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpAcciones.add(jlbImagenSirena, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, 250, 90));

        jpListadoEmergencias1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Eventos de emergencias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jpListadoEmergencias1.setOpaque(false);
        jpListadoEmergencias1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtEmergencias.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jtEmergencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtEmergencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtEmergenciasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtEmergencias);

        jpListadoEmergencias1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 20, 660, 160));

        btnEmergencia.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnEmergencia.setText(">>");
        btnEmergencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmergenciaActionPerformed(evt);
            }
        });
        jpListadoEmergencias1.add(btnEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 60, 30));

        jpListadoEmergencias.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actividades de emergencias", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jpListadoEmergencias.setOpaque(false);
        jpListadoEmergencias.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtActividades.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jtActividades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtActividades);

        jpListadoEmergencias.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 20, 660, 120));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpAcciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpListadoEmergencias1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
            .addComponent(jpListadoEmergencias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpAcciones, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpListadoEmergencias1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpListadoEmergencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEmergenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmergenciaActionPerformed
        // TODO add your handling code here:
        instanciarEmergencia();
    }//GEN-LAST:event_btnEmergenciaActionPerformed

    private void jtEmergenciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtEmergenciasMouseClicked
        seleccionarEmergencia();
    }//GEN-LAST:event_jtEmergenciasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.persistence.EntityManager BengalaProyectPUEntityManager;
    private java.util.List<main.entidades.BglTbCiudad> bglTbCiudadList;
    private javax.persistence.Query bglTbCiudadQuery;
    private java.util.List<main.entidades.BglTbEstadoemergencia> bglTbEstadoemergenciaList;
    private javax.persistence.Query bglTbEstadoemergenciaQuery;
    private java.util.List<main.entidades.BglTbSector> bglTbSectorList;
    private javax.persistence.Query bglTbSectorQuery;
    private java.util.List<main.entidades.BglTbTipoincidente> bglTbTipoincidenteList;
    private javax.persistence.Query bglTbTipoincidenteQuery;
    private javax.swing.JButton btnEmergencia;
    private javax.swing.JComboBox<String> cbCatalogoEmergencia;
    private javax.swing.JComboBox<String> cbCiudad;
    private javax.swing.JComboBox<String> cbEstado;
    private javax.swing.JComboBox<String> cbSector;
    private javax.swing.JComboBox<String> cbTipoEmergencia;
    private com.toedter.calendar.JDateChooser dcFechaFin;
    private com.toedter.calendar.JDateChooser dcFechaInic;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlbImagenSirena;
    private javax.swing.JPanel jpAcciones;
    private javax.swing.JPanel jpListadoEmergencias;
    private javax.swing.JPanel jpListadoEmergencias1;
    private javax.swing.JTable jtActividades;
    private javax.swing.JTable jtEmergencias;
    // End of variables declaration//GEN-END:variables
}
