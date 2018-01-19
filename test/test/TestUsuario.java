/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import main.controladores.BglTbUsuarioJpaController;
import main.entidades.BglTbUsuario;
import org.hibernate.Session;

/**
 *
 * @author michael.beltran
 */
public class TestUsuario {
    
    private static EntityManagerFactory factory;
    private static EntityManager manager;
    
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        
        try
        {
        
        //Crear gestor de persistencia
        factory=Persistence.createEntityManagerFactory("BengalaProyectPU");
//        manager=factory.createEntityManager();
//            
//       
//        manager.getTransaction().begin();
        BglTbUsuarioJpaController dao=new BglTbUsuarioJpaController(factory);
        
        dao.create(new BglTbUsuario(1,"PRUEBA", "12345",null,null,null,null));
        
        
        List<BglTbUsuario> lista=dao.findBglTbUsuarioEntities();
        
//        TypedQuery<BglTbUsuario> query=manager.createQuery("from bgl_tb_usuario",BglTbUsuario.class);
//        List<BglTbUsuario> lista=query.getResultList();
        
            for(BglTbUsuario usuario : lista)
            {
                System.out.println(usuario.getUsuCodigo());
                System.out.println(usuario.getUsuAlias());
                System.out.println(usuario.getUsuNombre());
                System.out.println(usuario.getUsuApellido());
                System.out.println(usuario.getUsuHash());
                System.out.println(usuario.getUsuSalt());
                System.out.println(usuario.getUsuFeculting());
                
            }
       
         Session session = (Session)dao.getEntityManager().getDelegate(); 
         System.out.println("Datos de sesión: "+session.toString());
         
        }
        catch(Exception e)
        {
            System.out.println("Ha ocurrido un error\n"+e.getMessage());
        }
        finally
        {
            manager.close();
        }
        
    }
    
}
