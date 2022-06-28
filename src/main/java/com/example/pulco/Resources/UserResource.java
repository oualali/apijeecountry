package com.example.pulco.Resources;

import com.example.pulco.Filters.JWTNeeded;
import com.example.pulco.Model.Credentials;
import com.example.pulco.Model.User;
import com.example.pulco.Repository.UserRepository;
import com.example.pulco.Utils.JWT;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Path("/users")
public class UserResource {

    @Inject
    private UserRepository userRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JWTNeeded
    public Response getUsers() {
        return Response.ok(userRepository.findAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @JWTNeeded
    public Response getUser(@PathParam("id") Integer id) {
        if (id == null || id <= 0) {
            return Response.status(403).build();
        }

        User user = userRepository.findById(id);

        if (user == null) {
            return Response.status(404).build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Response addUser(User user) {

        if (user.getPassword() == null | user.getEmail() == null) {
            return Response.status(403).build();
        }

        if (userRepository.add(user)) {
            return Response.ok().build();
        }

        return Response.status(406).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JWTNeeded
    public Response updateUser(User user) {
        if (user.getId() == 0) {
            return Response.status(403).build();
        }

        if (userRepository.update(user)) {
            return Response.ok().build();
        }

        return Response.status(406).build();
    }
}
