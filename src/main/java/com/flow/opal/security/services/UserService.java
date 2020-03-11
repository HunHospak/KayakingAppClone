package com.flow.opal.security.services;

import com.flow.opal.models.entities.MyUser;
import com.flow.opal.repositories.MyUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private MyUserRepository myUserRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public void save(MyUser myUser) {
    myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
    myUserRepository.save(myUser);
  }

  public List<MyUser> findAll() {
    return myUserRepository.findAll();
  }

}
