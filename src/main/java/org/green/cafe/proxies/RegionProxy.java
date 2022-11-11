package org.green.cafe.proxies;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.green.cafe.models.dto.DistrictsDTO;
import org.green.cafe.models.dto.ProvincesDTO;
import org.green.cafe.models.dto.RegencyCitiesDTO;
import org.green.cafe.models.dto.VillagesDTO;

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
  List<ProvincesDTO> listProvince();

  @GET
  @Path("/regencies/{provinceId}.json")
  List<RegencyCitiesDTO> listCity(@PathParam("provinceId") String provinceId);

  @GET
  @Path("/districts/{cityId}.json")
  List<DistrictsDTO> listDistrict(@PathParam("cityId") String cityId);

  @GET
  @Path("/villages/{districtId}.json")
  List<VillagesDTO> listVillage(@PathParam("districtId") String districtId);
}
