package org.green.cafe.controllers;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.green.cafe.models.User;
import org.green.cafe.models.dto.requests.UserRequest;
import org.green.cafe.services.UserService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes((MediaType.APPLICATION_JSON))
@OpenAPIDefinition(
    info = @Info(
        title = "User",
        description = "User Application",
        version = "1.0.0",
        license = @License(
            name = "MIT",
            url = "http://localhost:8080/api/user"
        )
    ),
    tags = {
        @Tag(name = "User Resource", description = "User Implementation Controller")
    }
)
public class UserController {

  @Inject
  UserService userService;

  @Inject
  JsonWebToken jsonWebToken;

  @Parameter(
      name = "User Request",
      required = true
  )
  @Operation(
      operationId = "Post User",
      summary = "Create User",
      description = "Create new User"
  )
  @POST
  @PermitAll
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "{}")),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = {
          @ExampleObject(name = "message: INVALID_PASSWORD"),
          @ExampleObject(name = "message: LOGIN_NAME_IS_EXISTS"),
          @ExampleObject(name = "message: INVALID_REQUEST")
      }))
  })
  @RequestBody(
      description = "Create from User Request",
      required = true,
      content = @Content(schema = @Schema(implementation = UserRequest.class))
  )
  public Response post(UserRequest userRequest){
    return userService.post(userRequest);
  }


  @Operation(
      operationId = "Get User",
      summary = "List User",
      description = "Get User Data and Token By it Token User"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = User.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message: INVALID_REQUEST_USER_NOT_FOUND")),
      @APIResponse(responseCode = "401", description = "Unauthorized Failed")
  })
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @GET
  @RolesAllowed("user")
  public Response get(){
    return userService.get(jsonWebToken.getSubject());
  }


  @PUT
  @RolesAllowed("user")
  @Parameter(
      name = "User Update Request",
      required = true
  )
  @Operation(
      operationId = "PUT User",
      summary = "Update User",
      description = "Update By it Token User"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = UserRequest.class))),
      @APIResponse(responseCode = "401", description = "Unauthorized Failed"),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message: USER_ID_NOT_FOUND"))
  })
  @RequestBody(
      description = "Update from User Request",
      required = true,
      content = @Content(schema = @Schema(implementation = UserRequest.class))
  )
  @SecurityRequirement(name = "Bearer JsonWebToken")
  public Response put(UserRequest userRequest){
    return userService.put(jsonWebToken.getClaim("id").toString(), userRequest);
  }


  @Operation(
      operationId = "DELETE",
      summary = "Delete User",
      description = "Delete By it Token User"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "{}")),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message")),
      @APIResponse(responseCode = "401", description = "Unauthorized Failed")
  })
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @DELETE
  @RolesAllowed("user")
  public Response delete(){
    return userService.delete(jsonWebToken.getClaim("id").toString());
  }
}
