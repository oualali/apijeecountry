package com.example.pulco.Resources;


import com.example.pulco.Filters.JWTNeeded;
import com.example.pulco.Repository.AssessmentRepository;
import com.example.pulco.Repository.CountryRepository;
import com.example.pulco.Model.Assessment;
import com.example.pulco.Model.Country;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pays")
public class CountryRessource {

    @Inject
    private CountryRepository countryRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JWTNeeded
    public Response getAssessments(){
        return Response.ok(countryRepository.findAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @JWTNeeded
    public Response getAssessment(@PathParam("id") Integer id){
        if(id == null || id <= 0){
            return Response.status(403).build();
        }

        Country result = countryRepository.findById(id);

        if(result == null){ return Response.status(404).build(); }

        return Response.ok(result).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTNeeded
    public Response addAssessment(Country country){
        if(country.getpays() == null || country.getType() == null){
            return Response.status(403).build();
        }

        if(countryRepository.add(country)){
            return Response.ok().build();
        }

        return Response.status(500).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTNeeded
    public Response updateAssessment(Country country){

        if(country.getId() == 0){
            return Response.status(403).build();
        }

        if(countryRepository.update(country)){
            return Response.ok().build();
        }

        return Response.status(500).build();
    }

    @DELETE
    @Path("/{id}")
    @JWTNeeded
    public Response deleteAssessment(@PathParam("id") Integer id){
        if(id == null || id <= 0){
            return Response.status(403).build();
        }

        if(countryRepository.delete(id)){
            return Response.ok().build();
        }

        return Response.status(500).build();
    }
}
