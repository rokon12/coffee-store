package org.eclipse.jakarta.hello;

import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Path("coffees")
public class CoffeeResource {
    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Inject
    private CoffeeRepository coffeeRepository;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Coffee findCoffee(@PathParam("id") Long id) {
        logger.info("Getting coffee by id " + id);
        return coffeeRepository.findById(id)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Coffee> findAll() {
        logger.info("Getting all coffee");
        return coffeeRepository.findAll().toList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Coffee create(Coffee coffee) {
        logger.info("Creating coffee " + coffee.getName());
        try {
            return coffeeRepository.save(coffee);
        } catch (PersistenceException ex) {
            logger.info("Error creating coffee " + coffee.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        logger.info("Deleting coffee by id " + id);
        try {
            Coffee coffee = coffeeRepository.findById(id)
                    .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
            coffeeRepository.delete(coffee);
            return Response.ok("Coffee with ID %s deleted successfully".formatted(id)).build();
        } catch (IllegalArgumentException e) {
            logger.info("Error deleting coffee by id " + id);
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Coffee update(Coffee coffee) {
        logger.info("Updating coffee " + coffee.getName());
        try {
            return coffeeRepository.save(coffee);
        } catch (PersistenceException ex) {
            logger.info("Error updating coffee " + coffee.getName());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}