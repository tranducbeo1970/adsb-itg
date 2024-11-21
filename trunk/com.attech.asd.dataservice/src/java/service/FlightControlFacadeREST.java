/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.FlightControl;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ANDH
 */
@Stateless
@Path("entities.flightcontrol")
public class FlightControlFacadeREST extends AbstractFacade<FlightControl> {
    
    private static final Logger logger = LogManager.getLogger(FlightControlFacadeREST.class);

    @PersistenceContext(unitName = "com.attech.asd.dataservicePU")
    private EntityManager em;

    public FlightControlFacadeREST() {
	super(FlightControl.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(FlightControl entity) {
        System.out.println("Create target: " + entity.getCallSign() + "- " + entity.getAddress() + " - " + entity.getController());
	super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, FlightControl entity) {
	super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
	super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public FlightControl find(@PathParam("id") String id) {
	return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FlightControl> findAll() {
	return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FlightControl> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
	return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        logger.info("Do counting");
	return String.valueOf(super.count());
    }
    
    @GET
    @Path("getbysector/{sector}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<FlightControl> findBySector(@PathParam("sector") String sector) {
	Query query = getEntityManager().createQuery("SELECT f FROM FlightControl f WHERE f.controller LIKE :sector OR f.targetCotroller Like :sector", FlightControl.class);
	query.setParameter("sector", sector);
	return query.getResultList();
    }
    
    @POST
    @Path("assum")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response assum(FlightControl entity) {
        Query query = getEntityManager().createQuery("SELECT f FROM FlightControl f WHERE f.callSign LIKE :callsign", FlightControl.class);
        query.setParameter("callsign", entity.getCallSign());
        List<FlightControl> controlled = query.getResultList();
        Date currentDate = new Date();

//        FlightControl controlledFlght = super.find(entity.getCallSign());
        if (controlled == null || controlled.isEmpty()) {
            entity.setLastUpdate(currentDate);
            entity.setAssumTime(currentDate);
            entity.setStatus(0);
            entity.setTargetCotroller(null);
            super.create(entity);
//            return entity;
            return Response.ok().entity(entity).build();
        }

        FlightControl currentControl = controlled.get(0);

        if (!entity.getAddress().equalsIgnoreCase(currentControl.getAddress())) {
            entity.setLastUpdate(currentDate);
            entity.setAssumTime(currentDate);
            entity.setStatus(0);
            entity.setTargetCotroller(null);
            super.edit(entity);
//            return entity;
            return Response.ok().entity(entity).build();
        }
        
        if (currentControl.getController().equalsIgnoreCase(entity.getController())) {
            entity.setLastUpdate(currentDate);
            entity.setAssumTime(currentDate);
            entity.setStatus(0);
            entity.setTargetCotroller(null);
            super.edit(entity);
//            return entity;
            return Response.ok().entity(entity).build();
        }

        if (currentControl.getTargetCotroller() != null && currentControl.getTargetCotroller().equalsIgnoreCase(entity.getController())) {
            entity.setLastUpdate(currentDate);
            entity.setAssumTime(currentDate);
            entity.setStatus(0);
            entity.setTargetCotroller(null);
            super.edit(entity);
//            return entity;
            return Response.ok().entity(entity).build();
        }

        long time1 = currentControl.getLastUpdate().getTime();
        long time2 = (new Date()).getTime();
        long diffInMillies = Math.abs(time2 - time1);
        long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if (diff > 7200) {
            entity.setLastUpdate(currentDate);
            entity.setAssumTime(currentDate);
            entity.setStatus(0);
            entity.setTargetCotroller(null);
            super.edit(entity);
            return Response.ok().entity(entity).build();
//	    return entity;
        }

//	return currentControl;
        return Response.ok().entity(currentControl).build();
    }
    
    @POST
    @Path("transfer")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response transfer(FlightControl entity) {
        Query query = getEntityManager().createQuery("SELECT f FROM FlightControl f WHERE f.callSign LIKE :callsign", FlightControl.class);
        query.setParameter("callsign", entity.getCallSign());
        List<FlightControl> controlled = query.getResultList();
        if (controlled == null || controlled.isEmpty()) {
            System.out.println("");
            return Response.status(400).entity("No assum record found").build();
        }

        FlightControl currentControl = controlled.get(0);
        if (currentControl.getTargetCotroller() != null || !currentControl.getController().equalsIgnoreCase(entity.getController())) {
            return Response.ok().entity(currentControl).build();
        }

        currentControl.setTargetCotroller(entity.getTargetCotroller());
        entity.setLastUpdate(new Date());
        super.edit(entity);
        return Response.ok().entity(currentControl).build();
    }
    
    @POST
    @Path("cancel")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response cancel(FlightControl entity) {
        Query query = getEntityManager().createQuery("SELECT f FROM FlightControl f WHERE f.callSign LIKE :callsign", FlightControl.class);
        query.setParameter("callsign", entity.getCallSign());
        List<FlightControl> controlled = query.getResultList();
        if (controlled == null || controlled.isEmpty()) {
            return Response.ok().build();
        }

        FlightControl currentControl = controlled.get(0);
        if (!currentControl.getController().equalsIgnoreCase(entity.getController())) {
            return Response.ok().entity(currentControl).build();
        }

        super.remove(currentControl);
        return Response.ok().build();
    }
    

    @Override
    protected EntityManager getEntityManager() {
	return em;
    }
    
}
