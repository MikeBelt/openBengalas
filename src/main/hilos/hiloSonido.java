/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.hilos;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.frms.frmMain;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public class hiloSonido extends Thread{
    
    private Clip sonido;
    private boolean seguir;
    private final static Logger log=Logger.getLogger(hiloSonido.class);       
    public frmMain main;   
 
    public hiloSonido(String rutaArchivo){

          seguir=true;
          try {
              
              PropertyConfigurator.configure("log4j.properties");
              sonido = AudioSystem.getClip();
              sonido.open(AudioSystem.getAudioInputStream(new File(rutaArchivo)));
               
          } catch (UnsupportedAudioFileException ex) {
             
              log.error(ex.getMessage());
              
          } catch (IOException ex) {
              
              log.error(ex.getMessage());
              
          } catch (LineUnavailableException ex) {
              
              log.error(ex.getMessage());
          }
       }
        
      @Override
       public void run(){
             
            if(!sonido.isRunning()){

//          sonido.start();
//          sonido.loop(7);
            sonido.loop(Clip.LOOP_CONTINUOUSLY);
            
            }
          // Espera mientras se esté reproduciendo.
          do{
              try {
              Thread.sleep(500);
              } catch (InterruptedException ex) {
              log.error(ex.getMessage());
              }
               
          }while (seguir && sonido.isActive());
           
          if(sonido.isActive() || sonido.isRunning()){
              sonido.stop();
          }
           
          // Se cierra el clip.
          sonido.close();
         
        
       }
       
      public void parar(){
          seguir=false;
          
          if(sonido.isActive() || sonido.isRunning()){
              sonido.stop();
          }
      }
    
    
}
