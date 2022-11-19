package org.green.cafe.services;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import org.green.cafe.exceptions.ValidationException;
import org.green.cafe.models.User;
import org.green.cafe.models.dto.UserRequest;
import org.green.cafe.util.FormatUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
public class UserService {

  public Response post(UserRequest userRequest){
    if(!User.isEmptyLoginName(userRequest.loginName)){
      throw new ValidationException("LOGIN_NAME_IS_EXISTS");
    }
    if (!FormatUtil.isPassword(userRequest.password)){
      throw new ValidationException("INVALID_PASSWORD");
    }
    if (!FormatUtil.isAlphabet(userRequest.fullName) || !FormatUtil.isPhoneNumber(userRequest.mobilePhoneNumber) || !FormatUtil.isPhoneNumber(userRequest.workPhoneNumber) || !FormatUtil.isEmail(userRequest.email)){
      throw new ValidationException("INVALID_REQUEST");
    }

    userPersist("",userRequest);
    return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();

  }

  @Transactional
  @TransactionConfiguration(timeout = 30)
  public User userPersist(String id, UserRequest userRequest){
    Optional<User> userOptional = User.findByIdOptional(id);
    User newUser;
    if(userOptional.isEmpty()){
      newUser = new User();
    }else {
      newUser = userOptional.get();
    }
    newUser.setFullName(userRequest.fullName);
    newUser.setEmail(userRequest.email);
    newUser.setMobilePhoneNumber(userRequest.mobilePhoneNumber);
    newUser.setWorkPhoneNumber(userRequest.workPhoneNumber);
    newUser.setLoginName(userRequest.loginName);
    newUser.setPassword(BcryptUtil.bcryptHash(userRequest.password));
    newUser.setAddress(userRequest.address);
    newUser.persist();
    return newUser;
  }

  public Response get(String loginName) {
    Optional<User> optionalLogin = User.findByLoginName(loginName);
    if(optionalLogin.isEmpty()){
      throw new ValidationException("INVALID_REQUEST_USER_NOT_FOUND");
    }
    User user = optionalLogin.get();
    return Response.ok(user).build();
  }

  public Response put(String id, UserRequest userRequest) {
    Optional<User>  userUpdate = User.findByIdOptional(id);
    if(userUpdate.isEmpty()){
      throw new ValidationException("USER_NOT_REGISTERED");
    }

    if (!FormatUtil.isPassword(userRequest.password)){
      throw new ValidationException("INVALID_PASSWORD");
    }
    if (!FormatUtil.isAlphabet(userRequest.fullName) || !FormatUtil.isPhoneNumber(userRequest.mobilePhoneNumber) || !FormatUtil.isPhoneNumber(userRequest.workPhoneNumber) || !FormatUtil.isEmail(userRequest.email)){
      throw new ValidationException("INVALID_REQUEST");
    }
    userPersist(id, userRequest);
    return Response.status(Response.Status.ACCEPTED).entity(new HashMap<>()).build();
  }

  @Transactional
  @TransactionConfiguration(timeout = 30)
  public Response delete(String uuid){
    Optional<User> userDeleted = User.findByIdOptional(uuid);
    if(userDeleted.isEmpty()){
      throw new ValidationException("USER_ID_NOT_FOUND");
    }
    User.deleteById(uuid);
    return Response.ok(new HashMap<>()).build();
  }
}
