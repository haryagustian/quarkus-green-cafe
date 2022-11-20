package org.green.cafe.proxies;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.green.cafe.models.dto.responses.DistrictResponse;
import org.green.cafe.models.dto.responses.ProvinceResponse;
import org.green.cafe.models.dto.responses.RegencyCitiyResponse;
import org.green.cafe.models.dto.responses.VillageResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api-wilayah-indonesia/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface RegionProxy {

  @GET
  @Path("/provinces.json")
  List<ProvinceResponse> listProvince();

  @GET
  @Path("/regencies/{provinceId}.json")
  List<RegencyCitiyResponse> listCity(@PathParam("provinceId") String provinceId);

  @GET
  @Path("/districts/{cityId}.json")
  List<DistrictResponse> listDistrict(@PathParam("cityId") String cityId);

  @GET
  @Path("/villages/{districtId}.json")
  List<VillageResponse> listVillage(@PathParam("districtId") String districtId);
}
