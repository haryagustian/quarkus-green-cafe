package org.green.cafe.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.green.cafe.models.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.persistence.Column;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

  public Optional<User> findByUUID(UUID uuid) {
    return find("id", uuid).firstResultOptional();
  }


}
