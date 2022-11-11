package org.green.cafe.services;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.green.cafe.models.JobPosition;
import org.green.cafe.models.LastEducation;
import org.green.cafe.models.dto.ProvincesDTO;
import org.green.cafe.proxies.RegionProxy;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class MetaService {

  Logger logger = Logger.getLogger(MetaService.class);

  @Inject
  @RestClient
  RegionProxy regionProxy;

  public Response jobPosition() {
    List<JobPosition> jobPositions = JobPosition.listAll();
    return
        jobPositions.isEmpty() ?
            Response.status(Response.Status.NOT_FOUND).build() :
            Response.ok(jobPositions).build();
  }

  public Response lastEducation() {
    List<LastEducation> lastEducations = LastEducation.listAll();
    return
        lastEducations.isEmpty() ?
            Response.status(Response.Status.NOT_FOUND).build() :
            Response.ok(lastEducations).build();
  }


  public Response listProvince(){
    return regionProxy.listProvince().isEmpty() ?
        Response.status(Response.Status.BAD_REQUEST).build() :
        Response.ok(regionProxy.listProvince()).build();
  }

  public Response listCity(String provinceId){
    return regionProxy.listCity(provinceId).isEmpty() ?
        Response.status(Response.Status.BAD_REQUEST).build() :
        Response.ok(regionProxy.listCity(provinceId)).build();
  }

  public Response listDistrict(String cityId){
    return regionProxy.listDistrict(cityId).isEmpty() ?
        Response.status(Response.Status.BAD_REQUEST).build() :
        Response.ok(regionProxy.listDistrict(cityId)).build();
  }

  public Response listVillage(String districtId){
    return regionProxy.listVillage(districtId).isEmpty() ?
        Response.status(Response.Status.BAD_REQUEST).build() :
        Response.ok(regionProxy.listVillage(districtId)).build();
  }
}
