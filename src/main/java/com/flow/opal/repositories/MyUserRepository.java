package com.flow.opal.repositories;

import com.flow.opal.models.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<MyUser,Integer> {
  MyUser findByUsername(String username);

  MyUser findByEmailIgnoreCase(String email);
}
