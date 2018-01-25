/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.hilos;

import javax.swing.JFrame;
import main.frms.frmMain;
import main.frms.ifrmEspera;
import main.frms.jdialEspera;
import main.frms.jpanelEspera;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class hiloVentanaEspera extends Thread{
    
    private boolean seguir;
    private final static Logger log=Logger.getLogger(hiloVentanaEspera.class);
    public frmMain ventana;
//    jdialEspera ventana;
//    jpanelEspera ventana;
//    ifrmEspera ventana;
    
    public hiloVentanaEspera(frmMain main)
    {
        seguir=true;
        PropertyConfigurator.configure("log4j.properties");
        log.info("Nuevo hiloVentanaEspera");
        this.ventana=main;
    }
    
    
    @Override
    public void run()
    {
        try{
//            ventana=new jdialEspera(this.main,true);
//            ventana= new jpanelEspera();
//            ventana=new ifrmEspera(this.main);
//            this.main.configurarPantallas(ventana);
//            
//            ventana.setAutoRequestFocus(false);
//            ventana.setFocusable(false);
//            ventana.setFocusableWindowState(false);
            
            ventana.jpMensajeEspera.setVisible(true);
            
        do{
            ventana.setMensajeEspera("Cargando datos.");
            hiloVentanaEspera.sleep(2000);
            ventana.setMensajeEspera("Cargando datos..");
            hiloVentanaEspera.sleep(2000);
            ventana.setMensajeEspera("Cargando datos...");
            hiloVentanaEspera.sleep(2000);
            
        }while(seguir);
        
        if(seguir==false && ventana.jpMensajeEspera.isShowing())
        {
            ventana.jpMensajeEspera.setVisible(true);
        }
        
        }catch(InterruptedException ex){log.error(ex.getMessage());}
    }
    
    /**
     *
     * @param val
     */
    public void seguir(boolean val)
    {
        this.seguir=val;
        
        if(seguir==false && ventana.jpMensajeEspera.isShowing())
        {
            ventana.jpMensajeEspera.setVisible(true);
        }
    }
    
}
