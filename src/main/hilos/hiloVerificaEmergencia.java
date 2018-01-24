/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.hilos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import main.clases.conexionOracle;
import main.frms.frmMain;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author michael.beltran
 */
public final class hiloVerificaEmergencia extends Thread{
    
     private final static Logger log=Logger.getLogger(hiloVerificaEmergencia.class);
     private conexionOracle conexion;
     private boolean seguir=true;
     public int totalEventos;
     public frmMain main;
     
     public hiloVerificaEmergencia(frmMain main)
     {
         try
         {
             this.main=main;
             PropertyConfigurator.configure("log4j.properties");
             conexion=new conexionOracle("RASTRAC", "RASTRAC", "192.168.1.18", "GLTEVRAC",false , true);
             
             conexion.conectar();
             log.info("Conexión a base de datos Oracle exitosa");
             totalEventos=this.consultarEventosEmergencia();
             conexion.desconectar();
         }
         catch(ClassNotFoundException | SQLException ex)
         {
             log.error(ex.getMessage());
         }
         
     }
     
     public int consultarEventosEmergencia()
     {
         int result=0;
         String select="SELECT COUNT(*) FROM RASTRAC.EV_VEHICLESTATE WHERE EVENT=20 AND ALIAS IN('BL0134')";
         Statement st=null;
         ResultSet rs=null;
         try
        {
            st= conexion.getCon().createStatement();
            rs=st.executeQuery(select);
            
            while(rs.next())
            {
                result=rs.getInt(1);
            }
            
            log.debug("Eventos de emergencia encontrados:" + result);
        }
        catch(SQLException ex)
        {
             
             log.error(ex.getMessage());
        }
        
        finally
        {
            
          try
          {
             if(rs!=null)
                rs.close();
          }catch(SQLException se2)
          {
              
              log.error(se2.getMessage());
          } 
        }
         return result;
     }
     
     @Override
       public void run(){

           do{
              try {
                  
                    this.conexion.conectar();
                    int result=this.consultarEventosEmergencia();
                    this.conexion.desconectar();
                    
                    if(result>this.totalEventos)
                    {
                        main.mostrarNotificacion("Se ha generado un nuevo evento de emergencia...");
                        log.info("Se ha generado un nuevo evento de emergencia...");
                        main.sonarAlarma();
                        this.totalEventos=result;
                    }
                  //dormir el hilo por 2 segundos
                    Thread.sleep(2000);
              } catch (InterruptedException ex) {
                    log.error(ex.getMessage());
              } catch (ClassNotFoundException ex) {
                  log.error(ex.getMessage());
               } catch (SQLException ex) {
                  log.error(ex.getMessage());
               }
               
          }while (seguir);
       
       }
       
       public void parar(){
          seguir=false;
       }
    
}
