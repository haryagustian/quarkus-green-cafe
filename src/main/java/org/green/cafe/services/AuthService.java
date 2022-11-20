package org.green.cafe.services;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.green.cafe.exceptions.ValidationException;
import org.green.cafe.models.User;
import org.green.cafe.models.dto.requests.LoginRequest;
import org.green.cafe.models.dto.responses.LoginResponse;
import org.green.cafe.util.FormatUtil;
import org.green.cafe.util.JwtUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.util.Optional;

@ApplicationScoped
public class AuthService {

  public Response login(LoginRequest loginRequest){
    if (!FormatUtil.isPassword(loginRequest.password)){
      throw new ValidationException("INVALID_PASSWORD");
    }

    Optional<User> userOptional = User.findByLoginName(loginRequest.loginName);
    if(userOptional.isEmpty()){
      throw new ValidationException("USER_NOT_FOUND");
    }

    User userLogin = userOptional.get();
    if(!BcryptUtil.matches(loginRequest.password, userLogin.getPassword())){
      throw new ValidationException("WRONG_PASSWORD");
    }

    LoginResponse loginResponse = new LoginResponse();
    loginResponse.user = userLogin;
    loginResponse.token = JwtUtil.generateToken(userLogin);



    return Response.ok(loginResponse).build();
  }

//  public Response getToken(AuthDTO authDTO){
//
//    Optional<User> userOptional = User.findByLoginName(authDTO.loginName);
//    if(userOptional.isEmpty()){
//      return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", "USER NOT FOUND")).build();
//    }
//    User user = userOptional.get();
//    String password = authDTO.password;
//
//    return
//        BcryptUtil.matches(password, user.getPassword()) ?
//        Response.ok(Map.of("userData", User.findByLoginName(user.getLoginName()),"token", GenerateJWT.generateToken(user))).build() :
//            Response.status(Response.Status.BAD_REQUEST).build();
//  }
}
