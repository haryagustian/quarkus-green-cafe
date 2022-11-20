package org.green.cafe.proxies;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.green.cafe.models.dto.requests.BankRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("vnxx/list-of-banks-in-indoneisa-json/master")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface BankProxy {

  @GET
  @Path("/index.json")
  List<BankRequest> listBank();

}
