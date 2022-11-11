package org.green.cafe.services;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.green.cafe.models.User;
import org.green.cafe.models.dto.UserDTO;
import org.green.cafe.util.GenerateJWT;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.util.*;

@ApplicationScoped
public class UserService {

  public Response post(UserDTO userDTO){
    User postUser = new User();
    postUser.setFullName(userDTO.fullName);
    postUser.setEmail(userDTO.email);
    postUser.setMobilePhoneNumber(userDTO.mobilePhoneNumber);
    postUser.setWorkPhoneNumber(userDTO.workPhoneNumber);
    postUser.setLoginName(userDTO.loginName);
    postUser.setPassword(BcryptUtil.bcryptHash(userDTO.password));
    postUser.setAddress(userDTO.address);
    postUser.setCreatedAt(new Date());
    postUser.setUpdatedAt(new Date());
    postUser.persist();

    return
        postUser.isPersistent() ?
            Response.ok(Map.of("userData", User.list("id = ?1", postUser.id),"token", GenerateJWT.generateToken(postUser))).build() :
            Response.status(Response.Status.NOT_FOUND).build();
  }

  public Response get() {
    List<User> user = User.listAll();
    return
        user.isEmpty() ?
            Response.status(Response.Status.NOT_FOUND).build() :
            Response.ok(user).build();
  }

  public Response put(String uuid, UserDTO userDTO) {
    Optional<User> userUpdateId = User.findByIdOptional(uuid);
    if (userUpdateId.isEmpty()){
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    User userPut = userUpdateId.get();
    userPut.fullName = userDTO.fullName;
    userPut.email = userDTO.email;
    userPut.mobilePhoneNumber = userDTO.mobilePhoneNumber;
    userPut.workPhoneNumber = userDTO.workPhoneNumber;
    userPut.loginName = userDTO.loginName;
    userPut.password = BcryptUtil.bcryptHash(userDTO.password);
    userPut.address = userDTO.address;
    userPut.updatedAt = new Date();
    User.persist(userPut);
    return Response.accepted().build();
  }

  public Response delete(String uuid){
    Optional<User> user = User.deleteByUUID(uuid);
    if(user.isPresent()){
      user.get().delete();
    }
    return user.isEmpty() ?
        Response.status(Response.Status.BAD_REQUEST).build() :
        Response.noContent().build();
  }
}
