package org.green.cafe.controllers;
import org.green.cafe.models.dto.UserDTO;
import org.green.cafe.services.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes((MediaType.APPLICATION_JSON))
public class UserController {


  @Inject
  UserService userService;

  @Transactional
  @POST
  @PermitAll
  public Response post(UserDTO userDTO){
    return userService.post(userDTO);
  }

  @GET
  @RolesAllowed("user")
  public Response get(){
    return userService.get();
  }

  @Transactional
  @PUT
  @Path("/{id}")
  @RolesAllowed("user")
  public Response put(@PathParam("id") String uuid, UserDTO userDTO){
    return userService.put(uuid, userDTO);
  }

  @Transactional
  @DELETE
  @Path("/{id}")
  @RolesAllowed("user")
  public Response delete(@PathParam("id") String uuid){
    return userService.delete(uuid);
  }
}
