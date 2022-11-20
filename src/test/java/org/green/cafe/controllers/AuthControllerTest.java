package org.green.cafe.controllers;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(AuthController.class)
class AuthControllerTest {

  @Test
  @Order(1)
  void loginSuccess() {
    when()
        .post()
        .then()
        .assertThat()
        .statusCode(Response.Status.OK.getStatusCode())
        .body("size()", is(0));
  }

  @Test
  void loginFailed() {
  }

  @Test
  void builder() {
  }
}