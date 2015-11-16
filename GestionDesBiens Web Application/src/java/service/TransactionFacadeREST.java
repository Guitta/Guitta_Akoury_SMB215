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
import model.Transaction;

/**
 *
 * @author guitta
 */
@Stateless
@Path("model.transaction")
public class TransactionFacadeREST extends AbstractFacade<Transaction> {
    @PersistenceContext(unitName = "GestionDesBiensPU")
    private EntityManager em;

    public TransactionFacadeREST() {
        super(Transaction.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Transaction entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Transaction entity) {
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
    public Transaction find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("lastid")
    @Produces({"application/json"})
    public List<Transaction> lastid() {
        return em.createQuery("SELECT t FROM Transaction t ORDER BY t.transactionId DESC").setMaxResults(1).getResultList();
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Transaction> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Transaction> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
    @GET
    @Path("usertransaction/{user}")
    @Produces({"application/json"})
    public List<Transaction> usertransaction(@PathParam("user") String username) {

        //return em.createQuery("SELECT t FROM Transaction t WHERE t.username = :username").setParameter("username", username).getResultList();
        return em.createQuery("SELECT t FROM Transaction t WHERE t.username = (SELECT DISTINCT u.username  FROM Users u WHERE u.username = :username )").setParameter("username", username).getResultList();

    }
    
}
