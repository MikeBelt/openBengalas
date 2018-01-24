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
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class hiloVentanaEspera extends Thread{
    
    private boolean seguir;
    private final static Logger log=Logger.getLogger(hiloVentanaEspera.class);
    public frmMain main;
    jdialEspera ventana;
    
    public hiloVentanaEspera(frmMain main)
    {
        seguir=true;
        PropertyConfigurator.configure("log4j.properties");
        log.info("Nuevo hiloVentanaEspera");
        this.main=main;
    }
    
    
    @Override
    public void run()
    {
        try{
            ventana=new jdialEspera(this.main,true);
//            ifrmEspera ventana=new ifrmEspera(this.main);
//            this.main.configurarPantallas(ventana);
            ventana.show();
            
        do{
            ventana.setMensaje("Cargando datos.");
            hiloVentanaEspera.sleep(2000);
            ventana.setMensaje("Cargando datos..");
            hiloVentanaEspera.sleep(2000);
            ventana.setMensaje("Cargando datos...");
            hiloVentanaEspera.sleep(2000);
            
        }while(seguir);
        
        if(seguir==false && ventana.isShowing())
        {
            ventana.dispose();
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
        
        if(seguir==false && ventana.isShowing())
        {
            ventana.dispose();
        }
    }
    
}
