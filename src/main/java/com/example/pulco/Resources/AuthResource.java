package com.example.pulco.Resources;

import com.example.pulco.Filters.JWTNeeded;
import com.example.pulco.Model.Credentials;
import com.example.pulco.Repository.JWTRepositry;
import com.example.pulco.Repository.UserRepository;
import com.example.pulco.Utils.JWT;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("/auth")
public class AuthResource {

    @Inject
    private UserRepository userRepository;

    @Inject
    private JWTRepositry jwtRepositry;

    @GET
    @Path("/logout")
    @JWTNeeded
    public Response logout(@Context HttpServletRequest req){

        String token = req.getHeader(AUTHORIZATION).substring("Bearer".length()).trim();

        try {

            JWT tokenObject = new JWT(token);
            if(jwtRepositry.blackList(token, tokenObject.getExpAsLocalDateTime())){
                return Response.ok().build();
            }

        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }


        return Response.status(424).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials){

        credentials = userRepository.authenticate(credentials);

        if (credentials.isValid()){
            JWT token = new JWT(credentials.getEmail(), credentials.getUserId());
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity(credentials).build();
    }
}
