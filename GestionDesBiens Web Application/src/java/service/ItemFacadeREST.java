/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import QRCode.QRCode;
import java.io.File;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import model.Item;

/**
 *
 * @author guitta
 */
@Stateless
@Path("model.item")
public class ItemFacadeREST extends AbstractFacade<Item> {

    @PersistenceContext(unitName = "GestionDesBiensPU")
    private EntityManager em;

    public ItemFacadeREST() {
        super(Item.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Item entity) {
        String url = "http://localhost:8080/GestionDesBiens/faces/users/singleitem.html#" + entity.getItemId();

        QRCode.createqr(entity.getItemId(), entity.getItemName(), url);
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Item entity) {
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
    public Item find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("/get{id}{name}")
    @Produces("image/png")
    public Response getFile(@PathParam("id") Integer id, @PathParam("name") String name) {
        String FILE_PATH = "../applications/images/" + name + "_" + id + ".png";
        File file = new File(FILE_PATH);

        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition",
                "attachment; filename=" + name + "_" + id + ".png");
        return response.build();

    }

    @GET
    @Path("lastid")
    @Produces({"application/json"})
    public List<Item> lastid() {
        return em.createQuery("SELECT i FROM Item i ORDER BY i.itemId DESC").setMaxResults(1).getResultList();
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Item> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Item> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
