/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.frms;

import com.google.gson.Gson;
import java.awt.Image;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import main.controladores.BglTbCatalogoincidentesJpaController;
import main.controladores.BglTbCiudadJpaController;
import main.controladores.BglTbEmergenciaJpaController;
import main.controladores.BglTbEstadoemergenciaJpaController;
import main.controladores.BglTbSectorJpaController;
import main.controladores.BglTbTipoincidenteJpaController;
import main.entidades.BglTbCatalogoincidentes;
import main.entidades.BglTbCiudad;
import main.entidades.BglTbEmergencia;
import main.entidades.BglTbEstadoemergencia;
import main.entidades.BglTbSector;
import main.entidades.BglTbTipoincidente;
import main.entidades.BglTbUsuario;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import wsEcu911.WebServiceControllerService_Impl;
import javax.xml.rpc.Stub;
import javax.xml.rpc.ServiceException;
import wsEcu911.WebServiceControllerPortType;
import wsEcu911.WebServiceControllerPortType_Stub;
import wsEcu911.WebServiceControllerService;


/**
 *
 * @author michael.beltran
 */
public class ifrmDetalleEmergencia extends javax.swing.JInternalFrame {

    public frmMain main;
    public EntityManagerFactory factory;
    private BglTbUsuario usuario;
    private final BglTbEmergenciaJpaController controladorEmergencia;
    private final static Logger log=Logger.getLogger(ifrmLogin.class);
    public BglTbEmergencia emergencia;
    /**
     * Creates new form ifrmDetalleEmergencia
     * @param factory
     * @param frm
     */
    public ifrmDetalleEmergencia(EntityManagerFactory factory,frmMain frm) {
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
        
        mostrarDatosEmergencia();
        
    }

    public void renderizarLogos()
    {
        try
        {
        
        ImageIcon img=new ImageIcon("src/main/img/logoecu911.png");
        Icon icon=new ImageIcon(img.getImage().getScaledInstance(jlbLogoEcu.getWidth(),jlbLogoEcu.getHeight() , Image.SCALE_DEFAULT));
        jlbLogoEcu.setIcon(icon);
        

        this.repaint();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this,"Error al renderizar logos");
            System.out.println("Error al renderizar logos: "+ex.getMessage());
        }
    }
    
    private void limpiarControles(){
    
       
        this.txtIdEmergencia.setText("");
        this.txtDireccion.setText("");
        this.txtLongitud.setText("");
        this.txtLatitud.setText("");
        this.txtDispositivo.setText("");
        this.txtObservacion.setText("");
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
   
   DefaultComboBoxModel dcmEstado=new DefaultComboBoxModel();
   private void llenarComboEstado(){
   
       cbEstadoEmergencia.setModel(dcmEstado);
       BglTbEstadoemergenciaJpaController  controlerEstadoEm=new BglTbEstadoemergenciaJpaController(this.factory);
       List<BglTbEstadoemergencia> listaEstadosEm;
       listaEstadosEm=controlerEstadoEm.findBglTbEstadoemergenciaEntities();
       for(int i=0;i<listaEstadosEm.size();i++)
       {
           dcmEstado.addElement(listaEstadosEm.get(i).getEstDescCorta());
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
   
   
   private void crearNuevaEmergencia(){
        
        String id=this.txtIdEmergencia.getText();
        String direccion=this.txtDireccion.getText();
        String cedula=this.txtCedula.getText();
        String nombre=this.txtNombre.getText();
        String apellido = this.txtApellido.getText();
        String dispositivo=this.txtDispositivo.getText();
        String observacion=this.txtObservacion.getText();
        

        if(id.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Ingrese un Id válido");
            return;
        }
        if(cedula.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Ingrese un la cedula");
            return;
        }
        if(apellido.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Ingrese al menos un apellido");
            return;
        }
        if(nombre.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Ingrese al menos un nombre");
            return;
        }
        if(direccion.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe indicar la direción");
            return;
        }
        if(this.txtLatitud.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe indicar la latitud");
            return;
        }
        if(this.txtLongitud.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe indicar la longitud");
            return;
        }
        if(observacion.equals(""))
        {
            JOptionPane.showMessageDialog(this,"Debe indicar la observación o descripción de la emergencia");
            return;
        }
        
        Double latitud=Double.parseDouble(this.txtLatitud.getText());
        Double longitud=Double.parseDouble(this.txtLongitud.getText());
        String timespan = String.format("%TT", new java.util.Date());
        
        try{
            
            BglTbEmergencia nuevaEmergencia=new BglTbEmergencia();
            nuevaEmergencia.setEmIdfuente(id);
            nuevaEmergencia.setEmApellido(apellido);
            nuevaEmergencia.setEmCedula(cedula);
            nuevaEmergencia.setEmCodpais("593");
            nuevaEmergencia.setEmDireccion(direccion);
            nuevaEmergencia.setEmDescripcion(observacion);
            nuevaEmergencia.setEmLongitud(longitud);
            nuevaEmergencia.setEnLatitud(latitud);
            nuevaEmergencia.setEmEstado((BglTbEstadoemergencia)this.cbEstadoEmergencia.getSelectedItem());
            nuevaEmergencia.setEmTincCodigo((BglTbCatalogoincidentes)this.cbCatalogoEmergencia.getSelectedItem());
            nuevaEmergencia.setEmTimestamp(timespan);
            
            this.controladorEmergencia.create(nuevaEmergencia);
            JOptionPane.showMessageDialog(this,"Emergencia guardado correctamente");
            
            
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Ha ocurrido un error al crear la nueva emergencia");
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
       
   }
   
   private void ejecutaExe(){
   
        Runtime aplicacion = Runtime.getRuntime(); 
        try{
//            aplicacion.exec("C:/Windows/System32/NOTEPAD.EXE");
aplicacion.exec("C:/Users/michael.beltran/Documents/Visual Studio 2010/Projects/BengalaProyect-CamVisor/BengalaProyect-CamVisor/bin/Debug/BengalaProyect-CamVisor.exe");
        }
        catch(IOException e){JOptionPane.showMessageDialog(this,e.getMessage());}
       
   }
   
   private String toJson(BglTbEmergencia emergencia ){
   
       String result=null;
       Gson gson=new Gson();
       gson.toJson(emergencia);
       
       
       
       return result; 
   }
   
   private String nuevaEmergencia(String gson){
   
   String newEmergency = null ;    
   WebServiceControllerService ws;
   WebServiceControllerPortType port;
   WebServiceControllerPortType_Stub stub;

   try
   {
       
       ws= new WebServiceControllerService_Impl();
       port= ws.getWebServiceControllerPort();
       
       //autenticación
        stub = (WebServiceControllerPortType_Stub) port;
	stub._setProperty(Stub.USERNAME_PROPERTY, "ecuservices");
	stub._setProperty(Stub.PASSWORD_PROPERTY, "Ecu911S3rv1c3s");
       
//      Call call = (Call) ...;
//	call.setProperty(Call.USERNAME_PROPERTY, "wsuser");
//	call.setProperty(Call.PASSWORD_PROPERTY, "wspwd");

       newEmergency = port.newEmergency(gson);
   }
   catch(RemoteException ex){
       JOptionPane.showMessageDialog(this,"Ha ocurrido un error al generar la nueva emergencia");
       JOptionPane.showMessageDialog(this,ex.getMessage());
   }catch (ServiceException ex) {   
       JOptionPane.showMessageDialog(this,"Ha ocurrido un error al generar la nueva emergencia");
       JOptionPane.showMessageDialog(this,ex.getMessage());
        }
  
   return newEmergency;
   }
   
   private int getStatusEmergencia(String gson){
   
     int status=0;
     WebServiceControllerService ws;
     WebServiceControllerPortType port;
     WebServiceControllerPortType_Stub stub;
     try
   {
       
       ws= new WebServiceControllerService_Impl();
       port= ws.getWebServiceControllerPort();
       
       //autenticación
        stub = (WebServiceControllerPortType_Stub) port;
	stub._setProperty(Stub.USERNAME_PROPERTY, "ecuservices");
	stub._setProperty(Stub.PASSWORD_PROPERTY, "Ecu911S3rv1c3s");
        
       status = port.getStatus(gson);
   }
   catch(RemoteException ex){
       JOptionPane.showMessageDialog(this,"Ha ocurrido un error al solicitar el status de la emergencia");
       JOptionPane.showMessageDialog(this,ex.getMessage());
   }    catch (ServiceException ex) {  
       JOptionPane.showMessageDialog(this,"Ha ocurrido un error al solicitar el status de la emergencia");
       JOptionPane.showMessageDialog(this,ex.getMessage());
        }
     return status;
       
   }
   
   private String setCanceladaEmergencia(String gson){
   
       String result=null;
       WebServiceControllerService ws;
       WebServiceControllerPortType port;
       WebServiceControllerPortType_Stub stub;
       try
       {
        ws=new WebServiceControllerService_Impl();
        port= ws.getWebServiceControllerPort();
       
        //autenticación
        stub = (WebServiceControllerPortType_Stub) port;
	stub._setProperty(Stub.USERNAME_PROPERTY, "ecuservices");
	stub._setProperty(Stub.PASSWORD_PROPERTY, "Ecu911S3rv1c3s");
        
        result =port.setCancelado(gson);
        
        }
        catch(RemoteException ex){
            JOptionPane.showMessageDialog(this,"Ha ocurrido un error al cancelar la emergencia");
            JOptionPane.showMessageDialog(this,ex.getMessage());
        }
        catch (ServiceException ex1) {  
            JOptionPane.showMessageDialog(this,"Ha ocurrido un error al cancelar la emergencia");
            JOptionPane.showMessageDialog(this,ex1.getMessage());
        }
       
       return result;
       
   }
   
   private void enviarNuevaEmergencia(){
        if(this.emergencia!=null)
        {
            String cadenaJson=toJson(this.emergencia);
            
            String result=nuevaEmergencia(cadenaJson);
            
            JOptionPane.showMessageDialog(this,"Resultado: "+result);
            
        }
   }
   
   private void enviarCanceladoEmergencia(){
    
       if(this.emergencia!=null)
        {
            String cadenaJson=toJson(this.emergencia);
            
            String result=setCanceladaEmergencia(cadenaJson);
            
            JOptionPane.showMessageDialog(this,"Resultado: "+result);
            
        }
   
       
   }
   
   private void enviarStatusEmergencia(){
   
    if(this.emergencia!=null)
        {
            String cadenaJson=toJson(this.emergencia);
            
            int result=getStatusEmergencia(cadenaJson);
            
            JOptionPane.showMessageDialog(this,"Resultado: "+result);
            
        }
   }
   
   public void mostrarDatosEmergencia(){
   
       if(this.emergencia!=null)
       {
       
           this.txtIdEmergencia.setText(this.emergencia.getEmIdfuente());
           this.txtCedula.setText(this.emergencia.getEmCedula());
           this.txtApellido.setText(this.emergencia.getEmApellido());
           this.txtNombre.setText(this.emergencia.getEmNombre());
           this.txtDireccion.setText(this.emergencia.getEmDireccion());
           this.txtObservacion.setText(this.emergencia.getEmDescripcion());
           this.txtDispositivo.setText(this.emergencia.getEmNombre());
           this.txtLongitud.setText(String.valueOf(this.emergencia.getEmLongitud()));
           this.txtLatitud.setText(String.valueOf(this.emergencia.getEnLatitud()));
           
           try{
               
              String fecha =this.emergencia.getEmTimestamp();
              SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
              Date fechaDate = formato.parse(fecha);
              
              this.dcFecha.setDate(fechaDate);
               
              } catch (ParseException ex) {
               log.error(ex.getMessage());
              }  
          
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

        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtLatitud = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtLongitud = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDispositivo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbCiudad = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtIdEmergencia = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacion = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbTipoEmergencia = new javax.swing.JComboBox<>();
        cbSector = new javax.swing.JComboBox<>();
        cbEstadoEmergencia = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jlabelNombre = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cbCatalogoEmergencia = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cbEstadoActividad = new javax.swing.JComboBox<>();
        dcFecha = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        btnEnviar = new javax.swing.JButton();
        btnStatus = new javax.swing.JButton();
        btnCancelarEmergencia = new javax.swing.JButton();
        jlbLogoEcu = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnMonitorear = new javax.swing.JButton();

        setBackground(new java.awt.Color(15, 35, 53));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Detalle de la Emergencia");
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Información de la emergencia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 0, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Latitud");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        txtLatitud.setText("000000");
        jPanel2.add(txtLatitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 140, -1));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Longitud");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, -1, -1));

        txtLongitud.setText("00000000");
        jPanel2.add(txtLongitud, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 130, -1));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Apellidos");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, -1, -1));
        jPanel2.add(txtDispositivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 170, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Catalogo");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ciudad");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jPanel2.add(cbCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 140, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fecha");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Id de Emergencia");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));
        jPanel2.add(txtIdEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 80, -1));

        txtObservacion.setColumns(20);
        txtObservacion.setRows(5);
        jScrollPane1.setViewportView(txtObservacion);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, 290, -1));

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Observación");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Sitio/lugar");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));
        jPanel2.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 340, -1));

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Status Actual de Emergencia");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, -1, -1));

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Sector");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

        cbTipoEmergencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoEmergenciaActionPerformed(evt);
            }
        });
        jPanel2.add(cbTipoEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 190, -1));

        jPanel2.add(cbSector, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 140, -1));

        jPanel2.add(cbEstadoEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 140, -1));

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Dispositivo");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Cédula");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 50, -1, -1));

        jlabelNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jlabelNombre.setForeground(new java.awt.Color(255, 255, 255));
        jlabelNombre.setText("Nombres");
        jPanel2.add(jlabelNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, -1, -1));
        jPanel2.add(txtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 50, 110, -1));
        jPanel2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 200, -1));
        jPanel2.add(txtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, 200, -1));

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tipo");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jPanel2.add(cbCatalogoEmergencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 190, -1));

        jLabel15.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Estado del Registro");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        jPanel2.add(cbEstadoActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 140, -1));

        dcFecha.setDateFormatString("yyyy-MMM-dd");
        jPanel2.add(dcFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 140, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Servicio Ecu911", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setOpaque(false);

        btnEnviar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnEnviar.setText("Enviar a Ecu911");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnStatus.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnStatus.setText("Verificar status");
        btnStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatusActionPerformed(evt);
            }
        });

        btnCancelarEmergencia.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnCancelarEmergencia.setText("Cancelar Emergencia");
        btnCancelarEmergencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarEmergenciaActionPerformed(evt);
            }
        });

        jlbLogoEcu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/logoecu911.png"))); // NOI18N
        jlbLogoEcu.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlbLogoEcu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btnCancelarEmergencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jlbLogoEcu, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnviar)
                .addGap(6, 6, 6)
                .addComponent(btnStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarEmergencia)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setOpaque(false);

        btnGuardar.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnMonitorear.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        btnMonitorear.setText("Monitorear");
        btnMonitorear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonitorearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMonitorear, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMonitorear)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(158, 158, 158)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        crearNuevaEmergencia();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbTipoEmergenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoEmergenciaActionPerformed
        //llenar el combo de catálogos por tipo de incidente
    }//GEN-LAST:event_cbTipoEmergenciaActionPerformed

    private void btnMonitorearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonitorearActionPerformed
        // TODO add your handling code here:
        ejecutaExe();
        
    }//GEN-LAST:event_btnMonitorearActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        
        enviarNuevaEmergencia();
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatusActionPerformed
        // TODO add your handling code here:
        enviarStatusEmergencia();
        
    }//GEN-LAST:event_btnStatusActionPerformed

    private void btnCancelarEmergenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarEmergenciaActionPerformed
        // TODO add your handling code here:
        enviarCanceladoEmergencia();
    }//GEN-LAST:event_btnCancelarEmergenciaActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        // TODO add your handling code here:
        this.main.frmDetalleEmergencia=null;
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarEmergencia;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMonitorear;
    private javax.swing.JButton btnStatus;
    private javax.swing.JComboBox<String> cbCatalogoEmergencia;
    private javax.swing.JComboBox<String> cbCiudad;
    private javax.swing.JComboBox<String> cbEstadoActividad;
    private javax.swing.JComboBox<String> cbEstadoEmergencia;
    private javax.swing.JComboBox<String> cbSector;
    private javax.swing.JComboBox<String> cbTipoEmergencia;
    private com.toedter.calendar.JDateChooser dcFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JLabel jlabelNombre;
    private javax.swing.JLabel jlbLogoEcu;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDispositivo;
    private javax.swing.JTextField txtIdEmergencia;
    private javax.swing.JTextField txtLatitud;
    private javax.swing.JTextField txtLongitud;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextArea txtObservacion;
    // End of variables declaration//GEN-END:variables
}
