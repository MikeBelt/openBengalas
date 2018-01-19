/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author michael.beltran
 */
public class conexionMySQL {
    
    private Connection con;
    private String usr;
    private String pass;
    private String server;
    private String puerto;
    private String base;
    private boolean sid;
    private boolean serviceName;
    
    public conexionMySQL(String usr,String pass,String server,String puerto,String base,boolean sid,boolean serviceName){
        con=null;
        setUsr(usr);
        setPass(pass);
        setServer(server);
        setPuerto(puerto);
        setBase(base);
        setSid(sid);
        setServiceName(serviceName);
    }

    public void conectar()throws ClassNotFoundException,SQLException{
         
        String url=null;
        
        Class.forName("com.mysql.jdbc.Driver");
        //en esta parte OJO con el tipo de conexion:
        //si es SID se pone ":" despues del numero de puerto
        //si es SERVICE_NAME se pone "/" despues del numero de puerto
        if(sid)
        { url="jdbc:mysql://@"+server+":"+puerto+":"+base;}
        if(serviceName)
        { url="jdbc:mysql://"+server+":"+puerto+"/"+base;}
        
        setCon(DriverManager.getConnection(url, usr, pass));
        
            
    }

    public void desconectar()throws SQLException{
        
        if( getCon()!=null)
            getCon().close();
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

    /**
     * @param con the con to set
     */
    public void setCon(Connection con) {
        this.con = con;
    }

    /**
     * @return the usr
     */
    public String getUsr() {
        return usr;
    }

    /**
     * @param usr the usr to set
     */
    public void setUsr(String usr) {
        this.usr = usr;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the puerto
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     * @param puerto the base to set
     */
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }
    
    /**
     * @return the base
     */
    public String getBase() {
        return base;
    }

    /**
     * @param base the base to set
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * @return the sid
     */
    public boolean isSid() {
        return sid;
    }

    /**
     * @param sid the sid to set
     */
    public void setSid(boolean sid) {
        this.sid = sid;
    }

    /**
     * @return the serviceName
     */
    public boolean isServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(boolean serviceName) {
        this.serviceName = serviceName;
    }
    
}
