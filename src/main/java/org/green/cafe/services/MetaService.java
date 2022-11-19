package org.green.cafe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.green.cafe.constants.Gender;
import org.green.cafe.constants.ItemCategory;
import org.green.cafe.exceptions.ValidationException;
import org.green.cafe.models.JobPosition;
import org.green.cafe.models.LastEducation;
import org.green.cafe.models.PaymentType;
import org.green.cafe.proxies.BankProxy;
import org.green.cafe.proxies.RegionProxy;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class MetaService {

  @Inject
  @RestClient
  RegionProxy regionProxy;

  @Inject
  @RestClient
  BankProxy bankProxy;

  ObjectMapper objectMapper = new ObjectMapper();

  public Response jobPosition() {
    return JobPosition.listAll().isEmpty() ?
            Response.status(Response.Status.NOT_FOUND).build() :
            Response.ok(JobPosition.listAll()).build();
  }

  public Response lastEducation() {
    return LastEducation.listAll().isEmpty() ?
            Response.status(Response.Status.NOT_FOUND).build() :
            Response.ok(LastEducation.listAll()).build();
  }

  public Response paymentType(){
    return PaymentType.listAll().isEmpty() ?
        Response.status(Response.Status.NOT_FOUND).build() :
        Response.ok(PaymentType.listAll()).build();
  }

    public Response listGender(){
    List<Map<String, String>> genderList = Arrays.stream(Gender.values())
        .map(gender -> Map.of("name", gender.getName(), "code", gender.getCode()))
        .collect(Collectors.toList());

    return Response.ok(genderList).build();
  }

  public Response itemCategory(){
    List<Map<String,String>> itemCategoryList = Arrays.stream(ItemCategory.values())
        .map(itemCategory -> Map.of("name", itemCategory.getName()))
        .collect(Collectors.toList());
    return Response.ok(itemCategoryList).build();
  }

  public Response listProvince() throws UnirestException {
    HttpResponse<String> provinceResponse = Unirest.get("https://emsifa.github.io/api-wilayah-indonesia/api/provinces.json").asString();
    return provinceResponse.getStatus() != 200 || provinceResponse.getStatus() == 500 || provinceResponse.getStatus() == 404  ?
        Response.status(Response.Status.BAD_REQUEST).entity(Map.of("message", "NOT_FOUND_PROVINCE")).build() :
        Response.ok(regionProxy.listProvince()).build();
  }

  public Response listCity(String provinceId) throws UnirestException {
    HttpResponse<String> cityResponse = Unirest.get(
        MessageFormat.format(
            "https://emsifa.github.io/api-wilayah-indonesia/api/regencies/{0}.json", provinceId)
    ).asString();
    return cityResponse.getStatus() != 200 || cityResponse.getStatus() == 500 || cityResponse.getStatus() == 500  ?
        Response.status(Response.Status.BAD_REQUEST).entity(Map.of("message", "NOT_FOUND_CITY")).build() :
        Response.ok(regionProxy.listCity(provinceId)).build();
  }

  public Response listDistrict(String cityId) throws UnirestException {
    HttpResponse<String> districtResponse = Unirest.get(
        MessageFormat.format(
            "https://emsifa.github.io/api-wilayah-indonesia/api/districts/{0}.json", cityId)
    ).asString();
    return districtResponse.getStatus() != 200 || districtResponse.getStatus() == 500 || districtResponse.getStatus() == 500  ?
        Response.status(Response.Status.BAD_REQUEST).entity(Map.of("message", "NOT_FOUND_DISTRICT")).build() :
        Response.ok(regionProxy.listDistrict(cityId)).build();
  }

  public Response listVillage(String districtId) throws UnirestException {
    HttpResponse<String> villageResponse = Unirest.get(
        MessageFormat.format(
            "https://emsifa.github.io/api-wilayah-indonesia/api/villages/{0}.json", districtId)
    ).asString();
    return villageResponse.getStatus() != 200 || villageResponse.getStatus() == 500 || villageResponse.getStatus() == 500  ?
        Response.status(Response.Status.BAD_REQUEST).entity(Map.of("message", "NOT_FOUND_VILLAGE")).build() :
        Response.ok(regionProxy.listVillage(districtId)).build();
  }

  public Response listBank(){
    return bankProxy.listBank().isEmpty() ?
        Response.status(Response.Status.BAD_REQUEST).build() :
        Response.ok(bankProxy.listBank()).build();
  }


  public Response detailBank(String bankCode) throws JsonProcessingException, FileNotFoundException {
    File file = new File("./src/main/resources/bank.json");
    Scanner scanner = new Scanner(file);
    StringBuilder stringBuilder = new StringBuilder();

    while(scanner.hasNextLine()){
      stringBuilder.append(scanner.nextLine());
    }

    List<Map<String,String>> data = objectMapper.readValue(stringBuilder.toString(), List.class);
    List<Map<String, String>> result = data
        .stream()
        .filter(stringStringMap -> stringStringMap.get("bank_code").equals(bankCode)).collect(Collectors.toList());

    if(data.isEmpty() || result.isEmpty()){
      throw new ValidationException("NOT_FOUND");
    }
    scanner.close();

    return Response.ok(result.get(0)).build();
  }
}