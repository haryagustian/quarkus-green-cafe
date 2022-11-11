package org.green.cafe.controllers;
import org.green.cafe.services.MetaService;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/meta")
@PermitAll
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MetaController {
  @Inject
  MetaService metaService;


  @GET
  @Path("/jobPosition")
  public Response jobPosition(){
    return metaService.jobPosition();
  }

  @GET
  @Path("/lastEducation")
  public Response lastEducation(){
    return metaService.lastEducation();
  }

  @GET
  @Path("/province")
  public Response listProvince() {
    return metaService.listProvince();
  }

  @GET
  @Path("/city/{provinceId}")
  public Response listCity(@PathParam("provinceId") String provinceId){
    return metaService.listCity(provinceId);
  }

  @GET
  @Path("/district/{cityId}")
  public Response listDistrict(@PathParam("cityId") String cityId){
    return metaService.listDistrict(cityId);
  }

  @GET
  @Path("/village/{districtId}")
  public Response listVillage(@PathParam("districtId") String districtId){
    return metaService.listVillage(districtId);
  }

//  @GET
//  @Path("/gender")
//  public Response listGender(){
//    return metaService.listGender();
//  }
}
