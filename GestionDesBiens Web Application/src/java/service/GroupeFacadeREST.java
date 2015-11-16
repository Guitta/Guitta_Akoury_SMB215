/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import model.Groupe;

/**
 *
 * @author guitta
 */
@Stateless
@Path("model.groupe")
public class GroupeFacadeREST extends AbstractFacade<Groupe> {
    @PersistenceContext(unitName = "GestionDesBiensPU")
    private EntityManager em;

    public GroupeFacadeREST() {
        super(Groupe.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Groupe entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Groupe entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Groupe find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("lastid")
    @Produces({"application/json"})
    public List<Groupe> lastid() {
       return em.createQuery("SELECT g FROM Groupe g ORDER BY g.id DESC").setMaxResults(1).getResultList();
    } 
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Groupe> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Groupe> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
