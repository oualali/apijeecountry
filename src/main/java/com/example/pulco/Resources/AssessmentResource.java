package com.example.pulco.Resources;

import com.example.pulco.Repository.AssessmentRepository;
import com.example.pulco.Model.Assessment;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/assessment")
public class AssessmentResource {

    @Inject
    private AssessmentRepository assessmentRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssessments(){
        return Response.ok(assessmentRepository.findAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getAssessment(@PathParam("id") Integer id){
        if(id == null || id <= 0){
            return Response.status(403).build();
        }

        Assessment result =assessmentRepository.findById(id);

        if(result == null){ return Response.status(404).build(); }

        return Response.ok(result).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAssessment(Assessment assessment){
        if(assessment.getName() == null || assessment.getType() == null){
            return Response.status(403).build();
        }

        if(assessmentRepository.add(assessment)){
            return Response.ok().build();
        }

        return Response.status(500).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAssessment(Assessment assessment){

        if(assessment.getId() == 0){
            return Response.status(403).build();
        }

        if(assessmentRepository.update(assessment)){
            return Response.ok().build();
        }

        return Response.status(500).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAssessment(@PathParam("id") Integer id){
        if(id == null || id <= 0){
            return Response.status(403).build();
        }

        if(assessmentRepository.delete(id)){
            return Response.ok().build();
        }

        return Response.status(500).build();
    }
}
