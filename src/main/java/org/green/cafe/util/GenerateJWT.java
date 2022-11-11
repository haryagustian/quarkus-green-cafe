package org.green.cafe.util;

import io.smallrye.jwt.build.Jwt;
import org.green.cafe.models.User;

import javax.inject.Singleton;


@Singleton
public class GenerateJWT {


  public static String generateToken(User user){
    return Jwt.issuer("green-cafe")
        .upn(user.loginName)
        .groups("user")
        .claim("fullName", user.fullName)
        .claim("email", user.email)
        .claim("mobilePhoneNumber", user.mobilePhoneNumber)
        .claim("workPhoneNumber", user.workPhoneNumber)
        .claim("address", user.address)
        .sign();
  }
}
