/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Aircrafts;
import entities.Flightplan;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ANDH
 */
@Stateless
@Path("entities.aircrafts")
public class AircraftsFacadeREST extends AbstractFacade<Aircrafts> {

    @PersistenceContext(unitName = "com.attech.asd.dataservicePU")
    private EntityManager em;

    public AircraftsFacadeREST() {
	super(Aircrafts.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Aircrafts entity) {
	super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Aircrafts entity) {
	super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
	super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Aircrafts find(@PathParam("id") Integer id) {
	return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Aircrafts> findAll() {
	return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Aircrafts> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
	return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("findbyaddress/{address}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Aircrafts findByAddress(@PathParam("address") String address) {
	Query query = getEntityManager().createQuery("SELECT f FROM Aircrafts f WHERE f.icao24Address = :address", Aircrafts.class);
        query.setParameter("address", address);
        return (Aircrafts) query.getSingleResult();
//        if (aircrafts == null || aircrafts.isEmpty()) {
//             return Response.status(400).entity("No aircraft record found").build();
//        }
//           
//        return Response.ok().entity(aircrafts.get(0)).build();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
	return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
	return em;
    }
    
}
