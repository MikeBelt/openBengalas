/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.clases;

import main.frms.frmMain;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class BengalaProyect {

    private final static Logger log=Logger.getLogger(BengalaProyect.class);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PropertyConfigurator.configure("log4j.properties");
        
        frmMain desktop=new frmMain();
        desktop.show();
    }
    
}
