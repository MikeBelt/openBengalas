/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.frms;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;
import main.controladores.BglTbCiudadJpaController;
import main.controladores.BglTbSectorJpaController;
import main.controladores.BglTbSitioincidenteJpaController;
import main.controladores.BglTbTipositioJpaController;
import main.controladores.exceptions.NonexistentEntityException;
import main.entidades.BglTbCiudad;
import main.entidades.BglTbSector;
import main.entidades.BglTbSitioincidente;
import main.entidades.BglTbTipositio;
import main.entidades.BglTbUsuario;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class ifrmMantSitios extends javax.swing.JInternalFrame {

    
    public frmMain main;
    public EntityManagerFactory factory;
    private BglTbUsuario usuario;
    private final static Logger log=Logger.getLogger(ifrmMantSitios.class);
    private BglTbSitioincidenteJpaController controladorSitios;
    private BglTbSitioincidente miSitio;
    
    /**
     * Creates new form ifrmMantSitios
     * @param factory
     * @param frm
     */
    public ifrmMantSitios(EntityManagerFactory factory,frmMain frm) {
        initComponents();
        PropertyConfigurator.configure("log4j.properties");
        configuraSpinner();
        this.factory=factory;
        this.main=frm;
        llenarComboTipoSitio();
        llenarComboCiudades();
        llenarComboSector();
        
        crearModeloTablaSitios();
        llenarTablaSitios();
        
    }

    public static DefaultTableModel dtmSitios;
    private void crearModeloTablaSitios(){
        try {
        dtmSitios = (new DefaultTableModel(
        null, new String [] {
        "Id","Codigo","Nombre","Latitud","Longitud","Hora Aper","Hora Cierre"}){
        Class[] types = new Class [] {
        BglTbSitioincidente.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class,
        java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
        false,false,false,false,false,false,false
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
        this.tbSitios.setModel(dtmSitios);
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.toString());
        }
}
    
    private void llenarTablaSitios(){

       try
       {
                    
       controladorSitios=new BglTbSitioincidenteJpaController(this.factory);    
       List<BglTbSitioincidente> listaSitios=controladorSitios.findBglTbSitioincidenteEntities();
      
       Object[] row=null;
       
           for(int i=0;i<listaSitios.size();i++)
           {

               dtmSitios.addRow(row);
               dtmSitios.setValueAt(listaSitios.get(i),i,0);
               dtmSitios.setValueAt(listaSitios.get(i).getSitCodigo(), i, 1);
               dtmSitios.setValueAt(listaSitios.get(i).getSitNombre(), i, 2);
               dtmSitios.setValueAt(listaSitios.get(i).getSitLatitud(), i, 3);
               dtmSitios.setValueAt(listaSitios.get(i).getSitLongitud(), i,4);
               dtmSitios.setValueAt(listaSitios.get(i).getSitHoraaper(), i, 5);
               dtmSitios.setValueAt(listaSitios.get(i).getSitHoracierre(), i, 6);

           }
       
       }
       catch(Exception e){
        JOptionPane.showMessageDialog(this,"Ha ocurrido un error al llenar la tabla de Sitios");
        JOptionPane.showMessageDialog(this,e.getMessage());
        log.error(e.getMessage());
       }
       
    }
    
    DefaultComboBoxModel dcmTipoSitio=null;
    private void llenarComboTipoSitio(){
   
        BglTbTipositioJpaController controlerTipo=new BglTbTipositioJpaController(this.factory);
        List<BglTbTipositio> listaTipoSitio;
        listaTipoSitio=controlerTipo.findBglTbTipositioEntities();
        
        dcmTipoSitio=new DefaultComboBoxModel(listaTipoSitio.toArray());
        cbTipoSitio.setModel(dcmTipoSitio);
//        for(int i=0;i<listaTipoSitio.size();i++)
//        {
//            dcmTipoSitio.addElement(listaTipoSitio.get(i).getTsitDescripcion());
//            dcmTipoSitio.insertElementAt(listaTipoSitio.get(i), i);
////            cbTipoSitio.addItem(listaTipoSitio.get(i).toString());
//            
//        }
        
        
    }
    
    DefaultComboBoxModel dcmCiudad;
    private void llenarComboCiudades(){
   
       
       BglTbCiudadJpaController controlerCiudad=new BglTbCiudadJpaController(this.factory);
       List<BglTbCiudad> listaCiudades;
       listaCiudades=controlerCiudad.findBglTbCiudadEntities();
       
       dcmCiudad=new DefaultComboBoxModel(listaCiudades.toArray());
       cbCiudad.setModel(dcmCiudad);
       
//       for(int i=0;i<listaCiudades.size();i++)
//       {
//           dcmCiudad.addElement(listaCiudades.get(i).getCiuNombre());
//       }
       
   
   }
   
    DefaultComboBoxModel dcmSector;
    private void llenarComboSector(){
       
       
       BglTbSectorJpaController controlerSector=new BglTbSectorJpaController(this.factory);
       List<BglTbSector> listaSectores;
       listaSectores=controlerSector.findBglTbSectorEntities();
       
       dcmSector=new DefaultComboBoxModel(listaSectores.toArray());
       cbSector.setModel(dcmSector);
       
//       for(int i=0;i<listaSectores.size();i++)
//       {
//           dcmSector.addElement(listaSectores.get(i).getSecDescripcion());
//       }
       
   }
   
    private void configuraSpinner(){
    
        try{
//            String[] horas = new String[]{"00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"}; 
//            SpinnerListModel spMopdel_1 = new SpinnerListModel(horas);
            SpinnerModel spMopdel_1=new SpinnerDateModel();
            spHoraAper.setModel(spMopdel_1);
            
            SpinnerModel spMopdel_2=new SpinnerDateModel();
//            SpinnerListModel spMopdel_2 = new SpinnerListModel(horas);
            spHoraCierre.setModel(spMopdel_2);
            
            
            spHoraAper.setEditor(new JSpinner.DateEditor(spHoraAper, "HH:mm"));
//            spHoraAper.setValue(new Date().getTime()); 

   
            spHoraCierre.setEditor(new JSpinner.DateEditor(spHoraCierre, "HH:mm"));
//            spHoraCierre.setValue(new Date().getTime()); 
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ocurrio un error al configurar los JSpinners");
            log.error(e.getMessage());
        }
    }
    
    private void crearNuevoSitio(){
    
        String codigo=this.txtCodigo.getText();
        String nombre=this.txtNombre.getText();
        
        String avenida=this.txtAvenida.getText();
        String calle=this.txtCalle.getText();
        String referencia=this.txtReferencia.getText();
        
        

        if(codigo.equals("")||nombre.equals("")||avenida.equals("")||calle.equals("")||referencia.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe llenar todos los campos");
            return;
        }
        
        try{
            int cod=Integer.parseInt(codigo);
            double longitud=Double.parseDouble(this.txtLongitud.getText());
            double latitud=Double.parseDouble(this.txtLatitud.getText());

            java.util.Date utilDate=(java.util.Date) this.spHoraAper.getValue();
            java.sql.Date dHoraAper=new java.sql.Date(utilDate.getTime());
            Time timeAper=new Time(dHoraAper.getTime());
            String horaAper=timeAper.toString();

            utilDate=(java.util.Date) this.spHoraCierre.getValue();
            java.sql.Date dHoraCierre=new java.sql.Date(utilDate.getTime());
            Time timeCierre=new Time(dHoraCierre.getTime());
            String horaCierre=timeCierre.toString();
       
            
            BglTbSitioincidente nuevoSitio= new BglTbSitioincidente();
            
            nuevoSitio.setSitCodigo(cod);
            nuevoSitio.setSitNombre(nombre);
            nuevoSitio.setSitAvenida(avenida);
            nuevoSitio.setSitCalle(calle);
            nuevoSitio.setSitLatitud(latitud);
            nuevoSitio.setSitLongitud(longitud);
            nuevoSitio.setSitReferencia(referencia);
            nuevoSitio.setSitHoraaper(horaAper);
            nuevoSitio.setSitHoracierre(horaCierre);
            
            BglTbCiudad ciudad=(BglTbCiudad)cbCiudad.getSelectedItem();
            nuevoSitio.setSitCiuCodigo(ciudad.getCiuCodigo());
            
            BglTbSector sector=(BglTbSector)cbSector.getSelectedItem();
            nuevoSitio.setSitSector(sector.getSecCodigo());
            
            BglTbTipositio tipoDisp=(BglTbTipositio)this.cbTipoSitio.getSelectedItem();
            nuevoSitio.setSitTsitCodigo(tipoDisp);
            
            this.controladorSitios=new BglTbSitioincidenteJpaController(this.factory);
            this.controladorSitios.create(nuevoSitio);
            
            JOptionPane.showMessageDialog(this,"Sitio de incidente guardado correctamente");
            crearModeloTablaSitios();
            llenarTablaSitios();
            
            btnGuardar.setEnabled(false);
            
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Ha ocurrido un error al crear nuevo sitio de incidente");
            JOptionPane.showMessageDialog(this,ex.getMessage());
            log.error(ex.getMessage());
        }
        
    }
    
    private void elimiarSitioSeleccionado(){
    
        try{
            int resp = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el registro?","Atención",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(resp==1||resp==2)
                return;
            
            this.miSitio=(BglTbSitioincidente)tbSitios.getValueAt(tbSitios.getSelectedRow(), 0);
            controladorSitios.destroy(miSitio.getSitCodigo());
            
             JOptionPane.showMessageDialog(this,"Registro eliminado con éxito");
            
        }
        catch(Exception e){
         JOptionPane.showMessageDialog(this,"Error al eliminar el registro");
         JOptionPane.showMessageDialog(this,e.getMessage());
        log.error(e.getMessage());
        }
    }
    
    private void actualizarSitio(){
    
        String codigo=this.txtCodigo.getText();
        String nombre=this.txtNombre.getText();
        String avenida=this.txtAvenida.getText();
        String calle=this.txtCalle.getText();
        String referencia=this.txtReferencia.getText();
        
        

        if(codigo.equals("")||nombre.equals("")||avenida.equals("")||calle.equals("")||referencia.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe llenar todos los campos");
            return;
        }
        
        
        try{
        
            int cod=Integer.parseInt(codigo);
            double longitud=Double.parseDouble(this.txtLongitud.getText());
            double latitud=Double.parseDouble(this.txtLatitud.getText());

            java.util.Date utilDate=(java.util.Date) this.spHoraAper.getValue();
            java.sql.Date dHoraAper=new java.sql.Date(utilDate.getTime());
            Time timeAper=new Time(dHoraAper.getTime());
            String horaAper=timeAper.toString();

            utilDate=(java.util.Date) this.spHoraCierre.getValue();
            java.sql.Date dHoraCierre=new java.sql.Date(utilDate.getTime());
            Time timeCierre=new Time(dHoraCierre.getTime());
            String horaCierre=timeCierre.toString();
       
            
           
            
             this.miSitio.setSitCodigo(cod);
             this.miSitio.setSitNombre(nombre);
             this.miSitio.setSitAvenida(avenida);
             this.miSitio.setSitCalle(calle);
             this.miSitio.setSitLatitud(latitud);
             this.miSitio.setSitLongitud(longitud);
             this.miSitio.setSitReferencia(referencia);
             this.miSitio.setSitHoraaper(horaAper);
             this.miSitio.setSitHoracierre(horaCierre);
            
            BglTbCiudad ciudad=(BglTbCiudad)cbCiudad.getSelectedItem();
             this.miSitio.setSitCiuCodigo(ciudad.getCiuCodigo());
            
            BglTbSector sector=(BglTbSector)cbSector.getSelectedItem();
             this.miSitio.setSitSector(sector.getSecCodigo());
            
            BglTbTipositio tipoDisp=(BglTbTipositio)this.cbTipoSitio.getSelectedItem();
             this.miSitio.setSitTsitCodigo(tipoDisp);
            
            this.controladorSitios.edit( this.miSitio);
            
            JOptionPane.showMessageDialog(this,"Modificación realizada con éxito");
            crearModeloTablaSitios();
            llenarTablaSitios();
            
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(this,"Error al actualizar el registro");
         JOptionPane.showMessageDialog(this,e.getMessage());
        log.error(e.getMessage());
        }

    }
    
    private void seleccionarSitio(){
    
        if(btnGuardar.isEnabled())
        {
            return;
        }
        
        try{
             int row=tbSitios.getSelectedRow();

            txtCodigo.setText(tbSitios.getValueAt(row, 1).toString());
            txtNombre.setText(tbSitios.getValueAt(row, 2).toString());
            txtLatitud.setText(tbSitios.getValueAt(row, 3).toString());
            txtLongitud.setText(tbSitios.getValueAt(row, 4).toString());
            
            //gestion de seteo del valor del spinner
            String horaAper=tbSitios.getValueAt(row, 5).toString();
            java.util.Date utilDate=new java.util.Date();
            String[] arreglo=horaAper.split(":");
            utilDate.setHours(Integer.parseInt(arreglo[0]));
            utilDate.setMinutes(Integer.parseInt(arreglo[1]));
            utilDate.setSeconds(0);
            
//            java.sql.Date sqlDate=new java.sql.Date(utilDate.getTime());
//            Time timeAper=new Time(dHoraAper.getTime());

            spHoraAper.getModel().setValue(utilDate);
            
            String horaCierre=tbSitios.getValueAt(row, 6).toString();
            arreglo=horaCierre.split(":");
            utilDate.setHours(Integer.parseInt(arreglo[0]));
            utilDate.setMinutes(Integer.parseInt(arreglo[1]));
            utilDate.setSeconds(0);
            
            spHoraCierre.getModel().setValue(utilDate);

            this.miSitio=(BglTbSitioincidente)tbSitios.getValueAt(tbSitios.getSelectedRow(), 0);

            txtAvenida.setText(miSitio.getSitAvenida());
            txtCalle.setText(miSitio.getSitCalle());
            txtReferencia.setText(miSitio.getSitReferencia());

            
            BglTbCiudadJpaController controlerCiudad=new BglTbCiudadJpaController(this.factory);
            BglTbCiudad ciudadSitio;
            ciudadSitio=controlerCiudad.findBglTbCiudad(miSitio.getSitCiuCodigo());
            cbCiudad.setSelectedItem(ciudadSitio);
            
            BglTbSectorJpaController controlerSector=new BglTbSectorJpaController(this.factory);
            BglTbSector sector;
            sector=controlerSector.findBglTbSector(miSitio.getSitSector());
            cbSector.setSelectedItem(sector);
            
//            BglTbTipositioJpaController controlerTipo=new BglTbTipositioJpaController(this.factory);
//            BglTbTipositio tipoSitio;
//            tipoSitio=controlerTipo.findBglTbTipositio(miSitio.getSitTsitCodigo().getTsitCodigo());
            cbTipoSitio.setSelectedItem(miSitio.getSitTsitCodigo());
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
    
    private void nuevoSitio(){
    
        
        
            txtCodigo.setText("");
            txtNombre.setText("");
            txtLatitud.setText("");
            txtLongitud.setText("");
            
            txtAvenida.setText("");
            txtCalle.setText("");
            txtReferencia.setText("");
            
            
            btnGuardar.setEnabled(true);
    
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
        panelUbicacion = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cbCiudad = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbSector = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JTextField();
        txtCalle = new javax.swing.JTextField();
        txtAvenida = new javax.swing.JTextField();
        jlabelMarca = new javax.swing.JLabel();
        txtLatitud = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLongitud = new javax.swing.JTextField();
        panelHorario = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        spHoraAper = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        spHoraCierre = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbTipoSitio = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        panelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSitios = new javax.swing.JTable();

        setBackground(new java.awt.Color(15, 35, 53));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Mantenimiento de Sitios");
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setOpaque(false);

        btnGuardar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnNuevo.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(btnModificar)
                .addGap(5, 5, 5)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addGap(76, 76, 76))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 140, 270));

        panelUbicacion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ubicación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        panelUbicacion.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Ciudad");
        jLabel10.setToolTipText("");

        cbCiudad.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Sector");
        jLabel11.setToolTipText("");

        cbSector.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Avenida");

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Calle");

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Referencia");

        jlabelMarca.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jlabelMarca.setForeground(new java.awt.Color(255, 255, 255));
        jlabelMarca.setText("Latitud");

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Longitud");

        panelHorario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Horario de atención", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        panelHorario.setOpaque(false);

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Hora Apertura");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Hora Cierre");

        javax.swing.GroupLayout panelHorarioLayout = new javax.swing.GroupLayout(panelHorario);
        panelHorario.setLayout(panelHorarioLayout);
        panelHorarioLayout.setHorizontalGroup(
            panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHorarioLayout.createSequentialGroup()
                .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(panelHorarioLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel9)))
                .addGap(18, 18, 18)
                .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spHoraCierre, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(spHoraAper))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelHorarioLayout.setVerticalGroup(
            panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHorarioLayout.createSequentialGroup()
                .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(spHoraAper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelHorarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spHoraCierre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelUbicacionLayout = new javax.swing.GroupLayout(panelUbicacion);
        panelUbicacion.setLayout(panelUbicacionLayout);
        panelUbicacionLayout.setHorizontalGroup(
            panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbicacionLayout.createSequentialGroup()
                .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUbicacionLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbicacionLayout.createSequentialGroup()
                        .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUbicacionLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel10))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUbicacionLayout.createSequentialGroup()
                                .addComponent(cbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbSector, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelUbicacionLayout.createSequentialGroup()
                                .addComponent(txtAvenida, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                                .addComponent(panelHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelUbicacionLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelUbicacionLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jlabelMarca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        panelUbicacionLayout.setVerticalGroup(
            panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUbicacionLayout.createSequentialGroup()
                .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cbSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUbicacionLayout.createSequentialGroup()
                        .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlabelMarca)
                            .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAvenida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(10, 10, 10)
                        .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(10, 10, 10)
                        .addGroup(panelUbicacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addComponent(panelHorario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        getContentPane().add(panelUbicacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 610, 200));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Básicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tipo");

        cbTipoSitio.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Código");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addGap(16, 16, 16)
                        .addComponent(cbTipoSitio, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(11, 11, 11)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(7, 7, 7)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(215, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(cbTipoSitio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 610, 80));

        panelTabla.setLayout(new javax.swing.BoxLayout(panelTabla, javax.swing.BoxLayout.LINE_AXIS));

        tbSitios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbSitios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSitiosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbSitios);

        panelTabla.add(jScrollPane1);

        getContentPane().add(panelTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 770, 150));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
        crearNuevoSitio();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        
        this.main.frmMantSitios=null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void tbSitiosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSitiosMouseClicked
        
        seleccionarSitio();
    }//GEN-LAST:event_tbSitiosMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        
        elimiarSitioSeleccionado();
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        actualizarSitio();
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevoSitio();
    }//GEN-LAST:event_btnNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cbCiudad;
    private javax.swing.JComboBox<String> cbSector;
    private javax.swing.JComboBox<String> cbTipoSitio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlabelMarca;
    private javax.swing.JPanel panelHorario;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JPanel panelUbicacion;
    private javax.swing.JSpinner spHoraAper;
    private javax.swing.JSpinner spHoraCierre;
    private javax.swing.JTable tbSitios;
    private javax.swing.JTextField txtAvenida;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtLatitud;
    private javax.swing.JTextField txtLongitud;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtReferencia;
    // End of variables declaration//GEN-END:variables
}
