package com.vehiclemanagement.repository;

import com.vehiclemanagement.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  @EntityGraph(attributePaths = "roles")
  Optional<User> findByEmailAndEnabledTrue(String email);
}
