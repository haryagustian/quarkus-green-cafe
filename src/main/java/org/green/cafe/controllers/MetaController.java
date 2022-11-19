package org.green.cafe.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.green.cafe.models.dto.*;
import org.green.cafe.services.MetaService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;

@Path("/api/meta")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@OpenAPIDefinition(
    info = @Info(
        title = "Meta",
        description = "Meta Application",
        version = "1.0.0",
        license = @License(
            name = "MIT",
            url = "http://localhost:8080/api/meta"
        )
    ),
    tags = {
        @Tag(name = "Meta Resource",description = "Meta Implementation Controller"),
    }
)
public class MetaController {
  @Inject
  MetaService metaService;


  @Operation(
      operationId = "Get Job Position",
      summary = "List Job Position",
      description = "List All Job Position"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = JobPositionResponse.class))),
      @APIResponse(responseCode = "404",description = "Operation Failed", content = @Content(example = "{}"))
  })
  @GET
  @Path("/jobPosition")
  public Response jobPosition(){
    return metaService.jobPosition();
  }

  @Operation(
      operationId = "Get Last Education",
      summary = "List Last Education",
      description = "List All Last Education"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = LastEducationResponse.class))),
      @APIResponse(responseCode = "404",description = "Operation Failed", content = @Content(example = "{}"))
  })
  @GET
  @Path("/lastEducation")
  public Response lastEducation(){
    return metaService.lastEducation();
  }

  @Operation(
      operationId = "Get Payment Type",
      summary = "List Payment Type",
      description = "List All Payment Type"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = PaymentTypeResponse.class))),
      @APIResponse(responseCode = "404",description = "Operation Failed", content = @Content(example = "{}"))
  })
  @GET
  @Path("/paymentType")
  public Response paymentType(){
    return metaService.paymentType();
  }

  @Operation(
      operationId = "Get Gender",
      summary = "List Gender",
      description = "List All Gender"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = GenderResponse.class)))
  })
  @GET
  @Path("/gender")
  public Response listGender(){
    return metaService.listGender();
  }

  @Operation(
      operationId = "Get Item Category",
      summary = "List Item Category",
      description = "List Item Category"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ItemCategoryResponse.class)))
  })
  @GET
  @Path("/itemCategory")
  public Response itemCategory(){
    return metaService.itemCategory();
  }

  @Operation(
      operationId = "Get Province",
      summary = "Province",
      description = "List Province"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ProvinceResponse.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message: BAD_REQUEST"))
  })
  @GET
  @Path("/province")
  public Response listProvince() throws UnirestException {
    return metaService.listProvince();
  }

  @Operation(
      operationId = "Get Regency City",
      summary = "Get Regency City",
      description = "List Get Regency City"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RegencyCitiyResponse.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message: BAD_REQUEST"))
  })
  @GET
  @Path("/city/{provinceId}")
  public Response listCity(@PathParam("provinceId") String provinceId) throws UnirestException {
    return metaService.listCity(provinceId);
  }

  @Operation(
      operationId = "Get Districts",
      summary = "Districts",
      description = "List District"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = DistrictResponse.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message: BAD_REQUEST"))
  })
  @GET
  @Path("/district/{cityId}")
  public Response listDistrict(@PathParam("cityId") String cityId) throws UnirestException {
    return metaService.listDistrict(cityId);
  }

  @Operation(
      operationId = "Get Villages",
      summary = "Villages",
      description = "List Village"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = VillageResponse.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message: BAD_REQUEST"))
  })
  @GET
  @Path("/village/{districtId}")
  public Response listVillage(@PathParam("districtId") String districtId) throws UnirestException {
    return metaService.listVillage(districtId);
  }
@Operation(
    operationId = "Get Bank List",
    summary = "List Bank List",
    description = "List All Bank"
)
@APIResponses({
    @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BankRequest.class)))
})
  @GET
  @Path("/bank")
  public Response listBank(){
    return metaService.listBank();
  }

  @Parameter(
      name = "BankCode Request",
      required = true
  )
  @Operation(
      operationId = "Get Detail Bank List",
      summary = "Bank Detail",
      description = "List All Bank Detail"
  )
  @APIResponses({
      @APIResponse(responseCode = "200",description = "Operation Success", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = BankDetailResponse.class))),
      @APIResponse(responseCode = "400",description = "Operation Failed", content = @Content(mediaType = MediaType.APPLICATION_JSON, example = "message: NOT_FOUND"))
  })
  @GET
  @Path("/bank/{bankCode}")
  public Response detailBank(@PathParam("bankCode") String bankCode) throws JsonProcessingException, FileNotFoundException {
    return metaService.detailBank(bankCode);
  }

}
