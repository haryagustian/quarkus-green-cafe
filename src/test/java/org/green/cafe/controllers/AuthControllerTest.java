package org.green.cafe.controllers;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(AuthController.class)
class AuthControllerTest {

  @Test
  @Order(1)
  @TestSecurity(user = "testUser", roles = "user")
  void loginSuccess() {
    JsonObject jsonObject = Json
        .createObjectBuilder()
        .add("loginName", "haryaugust101")
        .add("password", BcryptUtil.bcryptHash("Areuaoneorazero10"))
        .build();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonObject.toString())
        .when()
        .post()
        .then()
        .assertThat()
        .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
        .body("size()", equalTo(1))
        .body("[0].id", equalTo(1))
        .body("[0].loginName", equalTo(1))
        .body("[0].password", equalTo(BcryptUtil.bcryptHash("Areuaoneorazero10")));
  }

  @Test
  @Order(2)
  void loginFailed() {
    JsonObject jsonObject = Json
        .createObjectBuilder()
        .add("loginName", "haryagustian69")
        .add("password", BcryptUtil.bcryptHash("rahasisa69"))
        .build();

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(jsonObject.toString())
        .when()
        .post()
        .then()
        .assertThat()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("size()", equalTo(1))
        .body("[0].id", equalTo(1))
        .body("[0].loginName", equalTo(1))
        .body("[0].password", equalTo(BcryptUtil.bcryptHash("rahasia69")));
  }
}