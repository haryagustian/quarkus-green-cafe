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
import org.green.cafe.models.dto.requests.EmployeeRequest;
import org.green.cafe.models.dto.responses.EmployeeResponse;
import org.green.cafe.services.EmployeeService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.Map;


@Path("/api/employee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
    info = @Info(
        title = "Employee",
        description = "Employee Application",
        version = "1.0.0",
        license = @License(
            name = "MIT",
            url = "http://localhost:8080/api/employee"
        )
    ),
    tags = {
        @Tag(name = "Employee Resource", description = "Employee Implementation Controller")
    }
)
public class EmployeeController {
  @Inject
  EmployeeService employeeService;

  @Inject
  JsonWebToken jsonWebToken;

  @Parameter(
      name = "Employee Request",
      required = true
  )
  @Operation(
      operationId = "Post Employee",
      summary = "Create Employee",
      description = "Create new Employee"
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
      description = "Create from Employee Request",
      required = true,
      content = @Content(schema = @Schema(implementation = EmployeeRequest.class))
  )
  @SecurityRequirement(name = "Bearer JsonWebToken")
  public Response post(EmployeeRequest employeeRequest) throws ParseException {
    return employeeService.post(jsonWebToken.getClaim("id").toString(),employeeRequest);
  }

  @Parameter(
      name = "Employee Update Request",
      required = true
  )
  @Operation(
      operationId = "Update Employee",
      summary = "Update Employee",
      description = "Update By it Token User"
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
      description = "Update from Employee Request",
      required = true,
      content = @Content(schema = @Schema(implementation = EmployeeRequest.class))
  )
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @PUT
  @Path("/{employeeId}")
  public Response put(@PathParam("employeeId") String uuid, EmployeeRequest employeeRequest) throws ParseException {
    return employeeService.put(jsonWebToken.getClaim("id").toString(), uuid, employeeRequest);
  }


  @Parameter(
      name = "Employee Delete Request",
      required = true
  )
  @Operation(
      operationId = "Delete Employee",
      summary = "Delete Employee",
      description = "Delete By it Token User"
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
    return employeeService.delete(jsonWebToken.getClaim("id").toString(),uuid);
  }

  @Parameter(
      name = "Employee Detail Request",
      required = true
  )
  @Operation(
      operationId = "Detail Employee",
      summary = "Detail Employee",
      description = "Detail By it Token User"
  )
  @RolesAllowed("user")
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = EmployeeResponse.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message")),
      @APIResponse(responseCode = "401", description = "Unauthorized Failed")
  })
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @GET
  @Path("/{id}")
  public Response detail(@PathParam("id") String uuid){
    return employeeService.detail(uuid);
  }

  @Parameter(
      name = "Employee List Request",
      required = true
  )
  @Operation(
      operationId = "List Employee",
      summary = "List Employee",
      description = "List By it Token User"
  )
  @RolesAllowed("user")
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Employee.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message")),
      @APIResponse(responseCode = "401", description = "Unauthorized Failed")
  })
  @SecurityRequirement(name = "Bearer JsonWebToken")
  @GET
  public Response list(
      @DefaultValue("1") @QueryParam("page") Integer page,
      @QueryParam("jobPosition") String jobPositionId
      ){
    return employeeService.list(page,jobPositionId);
  }

  //  @GET
//  public Response get(@QueryParam("page") Integer page, @QueryParam("jobPosition")String jobPosition){
//    return employeeService.get(page, jobPosition);
//  }
//
//  @GET
//  @Path("/{id}")
//  public Response getById(@PathParam("id") String uuid){
//    return employeeService.getById(uuid);
//  }
}
