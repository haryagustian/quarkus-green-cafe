package org.green.cafe.controllers;

import org.green.cafe.models.User;
import org.green.cafe.models.dto.UserDTO;
import org.green.cafe.services.UserService;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes((MediaType.APPLICATION_JSON))
public class UserController {

  private Logger logger  = Logger.getLogger(UserController.class);

  @Inject
  UserService userService;

  @Transactional
  @POST
  public Response post(UserDTO userDTO){
    return userService.post(userDTO);
  }

  @GET
  public Response get(){
    return userService.get();
  }

  @Transactional
  @PUT
  public Response put(@PathParam("id") UUID id, UserDTO userDTO){
    logger.info("PUT METHOD " + id);
    return userService.put(id, userDTO);
  }

  @Transactional
  @DELETE
  public Response delete(@PathParam("id") UUID uuid){
    logger.info("DELETE METHOD " + uuid);
    return userService.delete(uuid);
  }

}
