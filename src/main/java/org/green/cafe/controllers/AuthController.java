package org.green.cafe.controllers;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.green.cafe.models.dto.LoginRequest;
import org.green.cafe.models.dto.LoginResponse;
import org.green.cafe.services.AuthService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
    info = @Info(
        title = "Authentication",
        description = "Authentication Application",
        version = "1.0.0",
        license = @License(
            name = "MIT",
            url = "http://localhost:8080/api/auth/login"
        )
    ),
    tags = {
        @Tag(name = "Authentication Resource", description = "Authentication Implementation Controller")
    }
)
public class AuthController {

  @Inject
  AuthService authService;

  @Parameter(
      name = "Login Request",
      required = true
  )
  @Operation(
      operationId = "Post Login",
      summary = "Login User",
      description = "Login to generate token, result user data and user token."
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Op eration Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LoginResponse.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
  })
  @RequestBody(
      description = "Login from User Request",
      required = true,
      content = @Content(schema = @Schema(implementation = LoginRequest.class))
  )
  @Path("/login")
  @POST
  @PermitAll
  public Response login(LoginRequest loginRequest){
    return authService.login(loginRequest);
  }
}
