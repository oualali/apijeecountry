package com.example.pulco.Filters;

import com.example.pulco.Utils.JWT;

import javax.annotation.Priority;
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
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        try{
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                throw new NotAuthorizedException("Authorization header must be provided");
            }

            String token = authorizationHeader.substring("Bearer".length()).trim();

            JWT tokenObject = new JWT(token);
            System.out.println("Filter works token is : " + tokenObject.toString());
            if (!tokenObject.isValid()){
                System.out.println("Validation Failed !!");
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            System.out.println("Validation approved, you can continue");
        }catch (Exception e){
            System.out.println("something went worng");
            System.out.println(e);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
