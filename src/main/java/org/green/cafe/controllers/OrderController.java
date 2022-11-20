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
import org.green.cafe.models.dto.requests.OrderRequest;
import org.green.cafe.services.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/api/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
    info = @Info(
        title = "Order",
        description = "Order Application",
        version = "1.0.0",
        license = @License(
            name = "MIT",
            url = "http://localhost:8080/api/order"
        )
    ),
    tags = {
        @Tag(name = "Order Resource", description = "Order Implementation Controller")
    }
)
public class OrderController {
  @Inject
  OrderService orderService;

  @Inject
  JsonWebToken jsonWebToken;

  @Parameter(
      name = "Order Request",
      required = true
  )
  @Operation(
      operationId = "Post Order",
      summary = "Create Order",
      description = "Create new Order"
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
      description = "Create from Order Request",
      required = true,
      content = @Content(schema = @Schema(implementation = OrderRequest.class))
  )
  @SecurityRequirement(name = "Bearer JsonWebToken")
  public Response post(OrderRequest orderRequest) {
    return orderService.post(jsonWebToken.getClaim("id").toString(), orderRequest);
  }

  @Parameter(
      name = "Month and Page = default 1",
      required = true
  )
  @Operation(
      operationId = "Get Order",
      summary = "List Order",
      description = "Get List Order with specific"
  )
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
      description = "Get List from Order Request",
      required = true,
      content = @Content(schema = @Schema(implementation = OrderRequest.class))
  )
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @GET
  public Response get(@QueryParam("month") String month,@DefaultValue("1") @QueryParam("page") Integer page){
    return orderService.get(jsonWebToken.getClaim("id").toString(), page, month);
  }
}

