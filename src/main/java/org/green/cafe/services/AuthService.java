package org.green.cafe.services;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.green.cafe.models.User;
import org.green.cafe.models.dto.AuthDTO;
import org.green.cafe.util.GenerateJWT;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class AuthService {
  public Response getToken(AuthDTO authDTO){

    Optional<User> userOptional = User.findByLoginName(authDTO.loginName);
    if(userOptional.isEmpty()){
      return Response.status(Response.Status.NOT_FOUND).entity(Map.of("message", "USER NOT FOUND")).build();
    }
    User user = userOptional.get();
    String password = authDTO.password;

    return
        BcryptUtil.matches(password, user.getPassword()) ?
        Response.ok(Map.of("userData", User.findByLoginName(user.getLoginName()),"token", GenerateJWT.generateToken(user))).build() :
            Response.status(Response.Status.BAD_REQUEST).build();
  }
}
