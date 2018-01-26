/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.frms;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import main.entidades.BglTbUsuario;
import main.hilos.hiloVentanaEspera;
import main.hilos.hiloVerificaEmergencia;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class frmMain extends javax.swing.JFrame {

    public EntityManagerFactory factory;
    public BglTbUsuario usuarioSesion;
    public ifrmLogin frmLogin;
    public ifrmEmergencias frmEmergencias;
    public ifrmMantDispositivos frmMantDispositivos;
    public ifrmMantSitios  frmMantSitios;
    public ifrmMantUsuarios  frmMantUsuarios;
    public ifrmDetalleEmergencia frmDetalleEmergencia;
    public ifrmParametros frmParametros;
    public ifrmAbout frmAbout;
    private final static Logger log=Logger.getLogger(ifrmLogin.class);
    public hiloVerificaEmergencia hiloEmergencia;
    public hiloVentanaEspera hiloEspera;
    
    /**
     * Creates new form frmMain
     */
    public frmMain() {
        initComponents();
        
        try{
        //inicializando logger
        PropertyConfigurator.configure("log4j.properties");
        //pintar frame
        //this.setBackground(Color.DARK_GRAY);
        //pintar jdesktop
        this.jdesktop.setBackground(Color.darkGray);
        //seteando img de fondo
        //this.jdesktop.setBorder(new ImagenFondo());
        this.mostrarNotificacion("Iniciando...");
        //maximizando pantalla
        this.setExtendedState(frmMain.MAXIMIZED_BOTH);
        //inicializando hilo de ventana de espera
//        this.hiloEspera=new hiloVentanaEspera(this);
//        this.hiloEspera.start();
        this.jpMensajeEspera.setVisible(false);
        //inicializando factoria de persistencia
        this.factory=Persistence.createEntityManagerFactory("BengalaProyectPU");
        //inicializando la hora
        this.HoraActual();
        //parar el hilo de ventana de espera
//        this.hiloEspera.seguir(false);
//        this.hiloEspera.stop();
        //inicializando hilo de consulta emergencias
        this.hiloEmergencia=new hiloVerificaEmergencia(this);
        this.hiloEmergencia.start();
        }
        catch(Exception ex)
        {
            log.error(ex.getMessage());
            JOptionPane.showMessageDialog(this,"Tenemos un problema:\n"+ex.getMessage());
        }
    }
    
    public void setMensajeEspera(String mensaje)
    {
        this.jlbMensaje.setText(mensaje);
        this.jlbMensaje.repaint();
    }
    
    public void verDatosUsuarioSesion()
    {
        this.txtUsuario.setText(this.usuarioSesion.getUsuNombre());
    }

    @Override
    public Image getIconImage() {
        URL imageURL=frmMain.class.getResource("/main/img/star.png");
        return (new ImageIcon(imageURL,"Bengala Proyect")).getImage();
    }
    
    public void HoraActual(){
        int j=1000;
        Timer timer = new Timer (j, new ActionListener (){@Override
            public void actionPerformed(ActionEvent e){txtFechaHora.setText(MostrarHora());}});
        timer.start();
    }
    
    //este es le metodo para mostrar la fecha-hora
    public String MostrarHora(){
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        Date date=new Date();
        String fecha= sdf.format(date);
        String hora = String.format("%TT", new Date()) ;
        return fecha+" "+hora;
    }
    
    public void configurarPantallas(JInternalFrame frm)
    {
        try
        {
            this.jdesktop.add(frm);
            Dimension desktopSize = this.jdesktop.getSize();
            Dimension FrameSize = frm.getSize();
            frm.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
            frm.setIconifiable(true);
            frm.show();
            
            jpMensajeEspera.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height- FrameSize.height)/2);
        }
        catch(Exception e)
        {
            System.out.println("Error al posicionar el internalFrame");
        }
    }
    
    public void mostrarNotificacion(String mensaje){
    try{
        TrayIcon icono = new TrayIcon(getImagen(),"Open Bengalas",crearMenu());
        SystemTray.getSystemTray().add(icono);
//        Thread.sleep(5000);
        
        icono.displayMessage("Open Bengalas", mensaje, TrayIcon.MessageType.INFO);
    }catch(AWTException ex){log.error(ex.getMessage());}
    }
    
    public static Image getImagen() {
        URL imageURL=frmMain.class.getResource("/main/img/16x16-png-antena.png");
        return (new ImageIcon(imageURL,"OpenBengalas - TrayIcon")).getImage();
    }
    

    public  PopupMenu crearMenu(){
        PopupMenu menu = new PopupMenu();
        MenuItem salir = new MenuItem("Salir");
        salir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        MenuItem monitor = new MenuItem("Monitor Emergencias");
        monitor.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //lo que hace el boton
                if(verificarSesion())
                {
                    if(frmEmergencias==null)
                        instanciaPantallaEmergencia();

                    configurarPantallas(frmEmergencias);

                    frmEmergencias.renderizarLogos();
                }
                        
            }
        });
        menu.add(salir);
        menu.add(monitor);
        return menu;
    }
    
    public void instanciaPantallaEmergencia()
    {
        frmEmergencias=new ifrmEmergencias(this.factory,this);
    }
    
    public void sonarAlarma()
    {
        if(frmEmergencias==null)
            instanciaPantallaEmergencia();
        
        configurarPantallas(frmEmergencias);
        frmEmergencias.renderizarLogos();
        frmEmergencias.iniciarAlarma();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdesktop = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFechaHora = new javax.swing.JTextField();
        jpMensajeEspera = new javax.swing.JPanel();
        jlbMensaje = new javax.swing.JLabel();
        jlbGif = new javax.swing.JLabel();
        jpMarcaAgua = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbNombreApp = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbNombreApp1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mItemLogOn = new javax.swing.JMenuItem();
        mItemLonOff = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mItemSalir = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mItemEmergencias = new javax.swing.JMenuItem();
        mItemNuevaEmergencia = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mItemMantDisp = new javax.swing.JMenuItem();
        mItemMantSitios = new javax.swing.JMenuItem();
        mItemMantUsuarios = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        mItemParametros = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Open Bengalas 1.0.0 [Open Branch Engine Alarm System]");
        setIconImage(getIconImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setText("Usuario");
        jToolBar1.add(jLabel1);

        txtUsuario.setEditable(false);
        txtUsuario.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        txtUsuario.setForeground(java.awt.Color.blue);
        txtUsuario.setOpaque(false);
        jToolBar1.add(txtUsuario);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel2.setText("Fecha:");
        jToolBar1.add(jLabel2);

        txtFechaHora.setEditable(false);
        txtFechaHora.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        txtFechaHora.setForeground(java.awt.Color.blue);
        txtFechaHora.setOpaque(false);
        jToolBar1.add(txtFechaHora);

        jlbMensaje.setText("Cargando datos...");
        jlbMensaje.setFocusable(false);
        jlbMensaje.setRequestFocusEnabled(false);

        jlbGif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/25x25-gif-load.gif"))); // NOI18N

        javax.swing.GroupLayout jpMensajeEsperaLayout = new javax.swing.GroupLayout(jpMensajeEspera);
        jpMensajeEspera.setLayout(jpMensajeEsperaLayout);
        jpMensajeEsperaLayout.setHorizontalGroup(
            jpMensajeEsperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 276, Short.MAX_VALUE)
            .addGroup(jpMensajeEsperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpMensajeEsperaLayout.createSequentialGroup()
                    .addGap(0, 78, Short.MAX_VALUE)
                    .addComponent(jlbGif)
                    .addGap(5, 5, 5)
                    .addComponent(jlbMensaje)
                    .addGap(0, 79, Short.MAX_VALUE)))
        );
        jpMensajeEsperaLayout.setVerticalGroup(
            jpMensajeEsperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
            .addGroup(jpMensajeEsperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jpMensajeEsperaLayout.createSequentialGroup()
                    .addGap(0, 18, Short.MAX_VALUE)
                    .addGroup(jpMensajeEsperaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlbGif)
                        .addGroup(jpMensajeEsperaLayout.createSequentialGroup()
                            .addGap(5, 5, 5)
                            .addComponent(jlbMensaje)))
                    .addGap(0, 19, Short.MAX_VALUE)))
        );

        jpMarcaAgua.setOpaque(false);
        jpMarcaAgua.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/30x30-png-sputnik.png"))); // NOI18N
        jLabel3.setOpaque(true);
        jpMarcaAgua.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 30, 30));

        lbNombreApp.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbNombreApp.setForeground(new java.awt.Color(255, 255, 255));
        lbNombreApp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNombreApp.setText("Open Branch Engine Alarm System");
        jpMarcaAgua.add(lbNombreApp, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/camera-39x30.png"))); // NOI18N
        jLabel4.setOpaque(true);
        jpMarcaAgua.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 40, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/Config.png"))); // NOI18N
        jLabel5.setOpaque(true);
        jpMarcaAgua.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 90, 70));

        lbNombreApp1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lbNombreApp1.setForeground(new java.awt.Color(0, 0, 204));
        lbNombreApp1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNombreApp1.setText("Open Bengalas");
        jpMarcaAgua.add(lbNombreApp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 190, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/40x28_ambulance_by_cycler.gif"))); // NOI18N
        jLabel6.setOpaque(true);
        jpMarcaAgua.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 40, 30));

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Versión 1.0.0");
        jpMarcaAgua.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 100, -1));

        jdesktop.setLayer(jToolBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jdesktop.setLayer(jpMensajeEspera, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jdesktop.setLayer(jpMarcaAgua, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jdesktopLayout = new javax.swing.GroupLayout(jdesktop);
        jdesktop.setLayout(jdesktopLayout);
        jdesktopLayout.setHorizontalGroup(
            jdesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdesktopLayout.createSequentialGroup()
                .addGap(0, 102, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdesktopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpMarcaAgua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jdesktopLayout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jpMensajeEspera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jdesktopLayout.setVerticalGroup(
            jdesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jdesktopLayout.createSequentialGroup()
                .addContainerGap(75, Short.MAX_VALUE)
                .addComponent(jpMarcaAgua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30)
                .addComponent(jpMensajeEspera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jdesktop);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-exit.png"))); // NOI18N
        jMenu1.setText("Inicio");
        jMenu1.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N

        mItemLogOn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK));
        mItemLogOn.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemLogOn.setText("Iniciar Sesión");
        mItemLogOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemLogOnActionPerformed(evt);
            }
        });
        jMenu1.add(mItemLogOn);
        mItemLogOn.getAccessibleContext().setAccessibleDescription("");

        mItemLonOff.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        mItemLonOff.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemLonOff.setText("Cerrar Sesión");
        mItemLonOff.setEnabled(false);
        mItemLonOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemLonOffActionPerformed(evt);
            }
        });
        jMenu1.add(mItemLonOff);
        jMenu1.add(jSeparator2);

        mItemSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        mItemSalir.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemSalir.setText("Salir");
        mItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemSalirActionPerformed(evt);
            }
        });
        jMenu1.add(mItemSalir);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-tools_icon.png"))); // NOI18N
        jMenu2.setText("Menú del Sistema");
        jMenu2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N

        mItemEmergencias.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        mItemEmergencias.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemEmergencias.setText("Monitor de Emergencias");
        mItemEmergencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemEmergenciasActionPerformed(evt);
            }
        });
        jMenu2.add(mItemEmergencias);

        mItemNuevaEmergencia.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        mItemNuevaEmergencia.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemNuevaEmergencia.setText("Crear Nueva Emergencia");
        mItemNuevaEmergencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemNuevaEmergenciaActionPerformed(evt);
            }
        });
        jMenu2.add(mItemNuevaEmergencia);
        jMenu2.add(jSeparator1);

        mItemMantDisp.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK));
        mItemMantDisp.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemMantDisp.setText("Mantenimiento Dispositivos");
        mItemMantDisp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemMantDispActionPerformed(evt);
            }
        });
        jMenu2.add(mItemMantDisp);

        mItemMantSitios.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        mItemMantSitios.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemMantSitios.setText("Mantenimiento Sitios");
        mItemMantSitios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemMantSitiosActionPerformed(evt);
            }
        });
        jMenu2.add(mItemMantSitios);

        mItemMantUsuarios.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK));
        mItemMantUsuarios.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemMantUsuarios.setText("Mantenimiento Usuarios");
        mItemMantUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemMantUsuariosActionPerformed(evt);
            }
        });
        jMenu2.add(mItemMantUsuarios);

        jMenuBar1.add(jMenu2);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-video.png"))); // NOI18N
        jMenu4.setText("MDVR");
        jMenu4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N

        mItemParametros.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        mItemParametros.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemParametros.setText("Parámetros de configuración MDVR");
        mItemParametros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemParametrosActionPerformed(evt);
            }
        });
        jMenu4.add(mItemParametros);

        jMenuBar1.add(jMenu4);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/main/img/16x16-png-help-orb-button.png"))); // NOI18N
        jMenu3.setText("Ayuda");
        jMenu3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N

        mItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        mItemAbout.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        mItemAbout.setText("About BengalaPro");
        mItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItemAboutActionPerformed(evt);
            }
        });
        jMenu3.add(mItemAbout);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mItemLogOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemLogOnActionPerformed
        // TODO add your handling code here:
        if(frmLogin==null)
        {
            frmLogin=new ifrmLogin(this.factory,this);
            configurarPantallas(frmLogin);
        }
        else
        {
            frmLogin.show();
        }

    }//GEN-LAST:event_mItemLogOnActionPerformed

    public void deshabilitarItemIniciarSesion(){
    
        this.mItemLogOn.setEnabled(false);
        
    }
    
    public void habilitarItemCerrarSesion(){
    
        this.mItemLonOff.setEnabled(true);
    }
    
    private void mItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemSalirActionPerformed
        
        System.exit(0);
    }//GEN-LAST:event_mItemSalirActionPerformed

    public boolean verificarSesion(){
    
        boolean result=false;
        
        if(this.usuarioSesion==null)
        {
            JOptionPane.showMessageDialog(this,"Por favor inicie sesión");
        }
        else
            result=true;
        
        return result;
    }
    
    private void mItemEmergenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemEmergenciasActionPerformed
//        this.jpMensajeEspera.setVisible(true);
//        this.repaint();
        
        //verificar usuario en sesión
        if(verificarSesion())
        {
            try {
                Thread.sleep(3000);
//            if(this.hiloEspera.isAlive())
//                this.hiloEspera=new hiloVentanaEspera(this);
//                this.hiloEspera.setPriority(9);
//                this.hiloEspera.seguir(true);
//                this.hiloEspera.start();
                

                if(frmEmergencias==null)
                    frmEmergencias=new ifrmEmergencias(this.factory,this);

                configurarPantallas(frmEmergencias);

                frmEmergencias.renderizarLogos();
                
//                this.hiloEspera.seguir(false);
//                this.hiloEspera.stop();

            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
            
//        this.jpMensajeEspera.setVisible(false);
        
    }//GEN-LAST:event_mItemEmergenciasActionPerformed

    private void mItemMantDispActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemMantDispActionPerformed
        
        //verificar usuario en sesión
        if(verificarSesion())
        {
            if(frmMantDispositivos==null)
                frmMantDispositivos=new ifrmMantDispositivos(this.factory,this);

            configurarPantallas(frmMantDispositivos);
        }
        
    }//GEN-LAST:event_mItemMantDispActionPerformed

    private void mItemMantSitiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemMantSitiosActionPerformed
        // TODO add your handling code here:
        //verificar usuario en sesión
        if(verificarSesion())
        {
            if(frmMantSitios==null)
                frmMantSitios=new ifrmMantSitios(this.factory,this);

            configurarPantallas(frmMantSitios);
        }
        
    }//GEN-LAST:event_mItemMantSitiosActionPerformed

    private void mItemMantUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemMantUsuariosActionPerformed
        // TODO add your handling code here:
        //verificar usuario en sesión
        if(verificarSesion())
        {
            if(frmMantUsuarios==null)
                frmMantUsuarios=new ifrmMantUsuarios(factory,this);

            configurarPantallas(frmMantUsuarios);
        }
        
    }//GEN-LAST:event_mItemMantUsuariosActionPerformed

    private void mItemNuevaEmergenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemNuevaEmergenciaActionPerformed
        // TODO add your handling code here:
        if(verificarSesion())
        {
            if(frmDetalleEmergencia==null)
                frmDetalleEmergencia=new ifrmDetalleEmergencia(factory,this);

            configurarPantallas(frmDetalleEmergencia);
        }
        
    }//GEN-LAST:event_mItemNuevaEmergenciaActionPerformed

    private void mItemParametrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemParametrosActionPerformed
        // TODO add your handling code here:
        
        if(verificarSesion())
        {
            if(frmParametros==null)
                frmParametros=new ifrmParametros(factory,this, this.usuarioSesion);

            configurarPantallas(frmParametros);
        }
        
    }//GEN-LAST:event_mItemParametrosActionPerformed

    private void mItemLonOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemLonOffActionPerformed
        // TODO add your handling code here:
       
        int resp = JOptionPane.showConfirmDialog(this, "Perderá todos los datos no guardados ¿Desea continuar?","Atención",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(resp==1||resp==2)
            return;
        
        
        if(this.mItemLonOff.isEnabled())
        {
            if(frmDetalleEmergencia!=null)
                frmDetalleEmergencia.dispose();
            if(frmEmergencias!=null)
                frmEmergencias.dispose();
            if(frmLogin!=null)
                frmLogin.dispose();
            if(frmMantDispositivos!=null)
                frmMantDispositivos.dispose();
            if(frmMantSitios!=null)
                frmMantSitios.dispose();
            if(frmMantUsuarios!=null)
                frmMantUsuarios.dispose();
            if(frmParametros!=null)
                frmParametros.dispose();

            this.usuarioSesion=null;
            this.mItemLonOff.setEnabled(false);
            this.mItemLogOn.setEnabled(true);
            this.txtUsuario.setText("");
        }
        
    }//GEN-LAST:event_mItemLonOffActionPerformed

    private void mItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItemAboutActionPerformed
        // TODO add your handling code here:
        if(this.frmAbout == null)
        {
             this.frmAbout= new ifrmAbout();
             
        }
        
        this.configurarPantallas(this.frmAbout);
        this.frmAbout.renderizarLogos();
    }//GEN-LAST:event_mItemAboutActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(this.hiloEmergencia.isAlive())
        {
            this.hiloEmergencia.parar();
        }
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    public javax.swing.JDesktopPane jdesktop;
    private javax.swing.JLabel jlbGif;
    private javax.swing.JLabel jlbMensaje;
    private javax.swing.JPanel jpMarcaAgua;
    public javax.swing.JPanel jpMensajeEspera;
    private javax.swing.JLabel lbNombreApp;
    private javax.swing.JLabel lbNombreApp1;
    private javax.swing.JMenuItem mItemAbout;
    private javax.swing.JMenuItem mItemEmergencias;
    private javax.swing.JMenuItem mItemLogOn;
    private javax.swing.JMenuItem mItemLonOff;
    private javax.swing.JMenuItem mItemMantDisp;
    private javax.swing.JMenuItem mItemMantSitios;
    private javax.swing.JMenuItem mItemMantUsuarios;
    private javax.swing.JMenuItem mItemNuevaEmergencia;
    private javax.swing.JMenuItem mItemParametros;
    private javax.swing.JMenuItem mItemSalir;
    private javax.swing.JTextField txtFechaHora;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
