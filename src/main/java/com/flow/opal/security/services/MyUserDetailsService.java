package com.flow.opal.security.services;

import com.flow.opal.models.entities.MyUser;
import com.flow.opal.repositories.MyUserRepository;
import com.flow.opal.security.models.AuthenticationRequest;
import java.util.ArrayList;
import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private MyUserRepository myUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (myUserRepository.findByUsername(username) != null) {
      MyUser myUserToLogIn = myUserRepository.findByUsername(username);
      User userToLogin = new User(myUserToLogIn.getUsername(), myUserToLogIn.getPassword(),
          new ArrayList<>());
      return userToLogin;
    } else {
      throw new UsernameNotFoundException("Username not found");
    }
  }

  public boolean validateAuthenticationRequest(AuthenticationRequest request) {
    if (request.getUsername() == null || request.getUsername().equals("")) {
      throw new UsernameNotFoundException("Please provide a username ");
    } else if (!validateUsername(request.getUsername())) {
      throw new BadRequestException(
          "This username is already taken. Please select another valid username");
    } else if (request.getPassword() == null || request.getPassword().equals("")) {
      throw new BadRequestException("Please provide a password ");
    } else if (request.getEmail() == null || request.getEmail().equals("")) {
      throw new BadRequestException("Please provide an email address");
    } else {
      return true;
    }
  }

  public boolean validateUsername(String username) {
    return myUserRepository.findByUsername(username) == null;
  }
}
