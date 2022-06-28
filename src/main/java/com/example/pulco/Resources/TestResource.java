package com.example.pulco.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestResource {

    @GET
    public Response test(){
        return Response.ok("All good").build();
    }

}
