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
import org.green.cafe.models.Employee;
import org.green.cafe.models.dto.EmployeeResponse;
import org.green.cafe.models.dto.ItemRequest;
import org.green.cafe.models.dto.ItemResponse;
import org.green.cafe.services.ItemService;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/api/item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
    info = @Info(
        title = "Item",
        description = "Item Application",
        version = "1.0.0",
        license = @License(
            name = "MIT",
            url = "http://localhost:8080/api/item"
        )
    ),
    tags = {
        @Tag(name = "Item Resource", description = "Item Implementation Controller")
    }
)
public class ItemController {
  @Inject
  ItemService itemService;

  @Inject
  JsonWebToken jsonWebToken;

  @Parameter(
      name = "Item Request",
      required = true
  )
  @Operation(
      operationId = "Post Item",
      summary = "Create Item",
      description = "Create new Item"
  )
  @POST
  @RolesAllowed("user")
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Map.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = {
          @ExampleObject(name = "message: INVALID_PASSWORD"),
          @ExampleObject(name = "message: LOGIN_NAME_IS_EXISTS"),
          @ExampleObject(name = "message: INVALID_REQUEST")
      }))
  })
  @RequestBody(
      description = "Create from Item Request",
      required = true,
      content = @Content(schema = @Schema(implementation = ItemRequest.class))
  )
  @SecurityRequirement(name = "Bearer JsonWebToken")
  public Response post(ItemRequest itemRequest){
    return itemService.post(jsonWebToken.getClaim("id").toString(), itemRequest);
  }

  @Parameter(
      name = "Item Update Request",
      required = true
  )
  @Operation(
      operationId = "Update Item",
      summary = "Update Item",
      description = "Update new Item"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Map.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, examples = {
          @ExampleObject(name = "message: INVALID_PASSWORD"),
          @ExampleObject(name = "message: LOGIN_NAME_IS_EXISTS"),
          @ExampleObject(name = "message: INVALID_REQUEST")
      }))
  })
  @RequestBody(
      description = "Update from Item Request",
      required = true,
      content = @Content(schema = @Schema(implementation = ItemRequest.class))
  )
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @PUT
  @Path("/{itemId}")
  @RolesAllowed("user")
  public Response put(@PathParam("itemId") String uuid, ItemRequest itemRequest){
    return itemService.put(jsonWebToken.getClaim("id").toString(),uuid,itemRequest);
  }

  @Parameter(
      name = "Item Delete Request",
      required = true
  )
  @Operation(
      operationId = "Delete Item",
      summary = "Delete Item",
      description = "Delete Item By it Token User"
  )
  @RolesAllowed("user")
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Map.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message")),
      @APIResponse(responseCode = "401", description = "Unauthorized Failed")
  })
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") String uuid){
    return itemService.delete(jsonWebToken.getClaim("id").toString(), uuid);
  }

  @Parameter(
      name = "Item List Request",
      required = true
  )
  @Operation(
      operationId = "List Item",
      summary = "List Item",
      description = "List Item By it Token User"
  )
  @RolesAllowed("user")
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ItemResponse.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message")),
      @APIResponse(responseCode = "401", description = "Unauthorized Failed")
  })
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @GET
  public Response list(
      @DefaultValue("1") @QueryParam("page") Integer page,
      @QueryParam("category") String category
  ){
    return itemService.list(page,category);
  }

  @Parameter(
      name = "Item Detail Request",
      required = true
  )
  @Operation(
      operationId = "Item Employee",
      summary = "Item Employee",
      description = "Detail Item By it Token User"
  )
  @RolesAllowed("user")
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ItemRequest.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message")),
      @APIResponse(responseCode = "401", description = "Unauthorized Failed")
  })
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @GET
  @Path("/{itemId}")
  public Response detail(@PathParam("itemId") String uuid){
    return itemService.detailItem(uuid);
  }
}
