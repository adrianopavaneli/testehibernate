/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Usuario;

/**
 *
 * @author Didi
 */
public class UsuarioDAO {
    
    private final EntityManager em;
    
    public UsuarioDAO(EntityManager em){
        this.em = em;
    }
  
    public Usuario insert(Usuario usuario){
        em.persist(usuario);
        return usuario;
        
    }
    public Usuario update(Usuario usuario){
        em.merge(usuario);
        em.persist(usuario);
        return usuario;
        
    }
    public Usuario insertOrUpdate(Usuario usuario){
        if(usuario.getId() > 0){
            return this.update(usuario);
        }
        return this.insert(usuario);
        
    }
    public void delete(Usuario usuario){
        em.merge(usuario);
        em.remove(usuario);
    }
    public Usuario selectPorId(Usuario usuario){
        return em.find(Usuario.class, usuario);
    }
    
    public List<Usuario> selectAll(){
        String jpql = "select u from Usuario as u";
        Query query = em.createQuery(jpql);
        return retornarListaComBaseNaConsulta(query);
         
    }

    private List<Usuario> retornarListaComBaseNaConsulta(Query query) {
        List<Usuario> usuarios;
        try{
            usuarios = query.getResultList();
        }catch(NoResultException e){
            usuarios = new ArrayList<>();
            
        }
        
        return usuarios;
    }
    public boolean existeNoBanco(Usuario usuarioNovo){
        String jpql = "select u from Usuario as u "
                + "Where u.usuario = :pUsuario and u.senha = :pSenha";       
        
        Query query = em.createQuery(jpql);
        query.setParameter("pUsuario", usuarioNovo.getUsuario());
         query.setParameter("pSenha", usuarioNovo.getSenha());
         return !retornarListaComBaseNaConsulta(query).isEmpty();
        
    }
}
