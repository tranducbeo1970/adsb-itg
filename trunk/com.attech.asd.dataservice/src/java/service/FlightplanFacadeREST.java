/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Flightplan;
import java.util.Date;
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

/**
 *
 * @author ANDH
 */
@Stateless
@Path("entities.flightplan")
public class FlightplanFacadeREST extends AbstractFacade<Flightplan> {

    @PersistenceContext(unitName = "com.attech.asd.dataservicePU")
    private EntityManager em;

    public FlightplanFacadeREST() {
        super(Flightplan.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Flightplan entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Flightplan entity) {
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
    public Flightplan find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flightplan> findAll() {
        return super.findAll();
    }

    @GET
    @Path("findbydof/{dof}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flightplan> findByDof(@PathParam("dof") String dof) {
        Query query = getEntityManager().createQuery("SELECT f FROM Flightplan f WHERE f.dof = :dof", Flightplan.class);
        query.setParameter("dof", dof);
        return query.getResultList();
//        return null;
    }
    
    @GET
    @Path("findbytarget/{dof}/{callsign}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Flightplan findByTarget(@PathParam("dof") String dof, @PathParam("callsign") String callsign) {
        
        Query query = getEntityManager().createQuery("SELECT f FROM Flightplan f WHERE f.dof = :dof and f.callSign = :callsign", Flightplan.class);
        query.setParameter("dof", dof);
        query.setParameter("callsign", callsign);
        Flightplan flightPlans = (Flightplan) query.setMaxResults(1).getSingleResult();
        return flightPlans;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Flightplan> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
