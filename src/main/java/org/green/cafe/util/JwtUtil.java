package org.green.cafe.util;

import io.smallrye.jwt.build.Jwt;
import org.green.cafe.models.User;

import javax.inject.Singleton;
import java.time.Duration;
import java.util.Date;


@Singleton
public class JwtUtil {
  public static String generateToken(User user){
    return Jwt.issuer("kawah-edukasi-green-cafe")
        .issuedAt(new Date().toInstant())
        .expiresIn(Duration.ofHours(1))
        .subject(user.getLoginName())
        .claim("fullName", user.getFullName())
        .claim("email", user.getEmail())
        .claim("mobilePhoneNumber", user.getMobilePhoneNumber())
        .claim("workPhoneNumber", user.getWorkPhoneNumber())
        .claim("address", user.getAddress())
        .claim("id", user.getId())
        .groups("user")
        .sign();
  }
}
