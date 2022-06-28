package com.example.pulco.Filters;

import com.example.pulco.Repository.JWTRepositry;
import com.example.pulco.Utils.JWT;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHORIZATION)
@JWTNeeded
public class JWTokenNeededFilter implements ContainerRequestFilter {

    @Inject
    JWTRepositry jwtRepositry;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try{
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new NotAuthorizedException("Authorization header must be provided");
            }

            String token = authorizationHeader.substring("Bearer".length()).trim();

            JWT tokenObject = new JWT(token);

            if (!tokenObject.isValid() || jwtRepositry.exist(token)){
                throw new NotAuthorizedException("Authorization header is not valid");
            }

        }catch (Exception e){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
