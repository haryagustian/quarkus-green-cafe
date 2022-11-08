package org.green.cafe.services;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.green.cafe.models.User;
import org.green.cafe.models.dto.UserDTO;
import org.green.cafe.repositories.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {

  @Inject
  UserRepository userRepository;


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
    userRepository.persist(postUser);
    return
        userRepository.isPersistent(postUser) ?
            Response.created(URI.create("api/user" + userDTO.id)).build() :
            Response.status(Response.Status.NOT_FOUND).build();
  }

  public Response get(){
    return
        !userRepository.listAll().isEmpty() ?
            Response.ok(userRepository.listAll()).build() :
            Response.status(Response.Status.NOT_FOUND).build();
  }

  public Response put(UUID uuid, UserDTO userDTO) {
    Optional<User> userUpdateId = userRepository.find("id = ?1",uuid).firstResultOptional();
    if (userUpdateId.isEmpty()){
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
    User userPut = userUpdateId.get();
    userPut.fullName = userDTO.fullName;
    userPut.email = userDTO.email;
    userPut.mobilePhoneNumber = userDTO.mobilePhoneNumber;
    userPut.workPhoneNumber = userDTO.workPhoneNumber;
    userPut.loginName = userDTO.loginName;
    userPut.password = userDTO.password;
    userPut.address = userDTO.address;
    userPut.updatedAt = userDTO.updatedAt;
    userRepository.persist(userPut);
    return Response.accepted().build();
  }

  public Response delete(UUID uuid){
    Optional<User> user = userRepository.findByUUID(uuid);
    System.out.println(user);
    return user.isEmpty() ?
        Response.status(Response.Status.BAD_REQUEST).build() :
        Response.noContent().build();
  }
}
