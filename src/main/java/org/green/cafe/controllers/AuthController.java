package org.green.cafe.controllers;
import org.green.cafe.models.dto.AuthDTO;
import org.green.cafe.services.AuthService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

  @Inject
  AuthService authService;

  @POST
  @PermitAll
  public Response login(AuthDTO authDTO){
    return authService.getToken(authDTO);
  }
}
