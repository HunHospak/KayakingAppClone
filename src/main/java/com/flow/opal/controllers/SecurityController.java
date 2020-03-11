package com.flow.opal.controllers;

import com.flow.opal.exceptionhandling.customexceptions.UnauthorizedRequestException;
import com.flow.opal.models.entities.MyUser;
import com.flow.opal.repositories.MyUserRepository;
import com.flow.opal.security.models.AuthenticationRequest;
import com.flow.opal.security.models.AuthenticationResponse;
import com.flow.opal.security.models.ConfirmationToken;
import com.flow.opal.security.repositories.ConfirmationTokenRepository;
import com.flow.opal.security.services.EmailSenderService;
import com.flow.opal.security.services.MyUserDetailsService;
import com.flow.opal.security.services.UserService;
import com.flow.opal.security.util.JwtUtil;

import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityController {

  private AuthenticationManager authenticationManager;

  private MyUserDetailsService userDetailsService;

  private JwtUtil jwtTokenUtil;

  private UserService userService;

  private ConfirmationTokenRepository confirmationTokenRepository;

  private EmailSenderService emailSenderService;

  @Autowired
  public SecurityController(AuthenticationManager authenticationManager,
                            MyUserDetailsService userDetailsService,
                            JwtUtil jwtTokenUtil,
                            UserService userService,
                            ConfirmationTokenRepository confirmationTokenRepository,
                            EmailSenderService emailSenderService) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userService = userService;
    this.confirmationTokenRepository = confirmationTokenRepository;
    this.emailSenderService = emailSenderService;
  }

  @Autowired
  private MyUserRepository myUserRepository;

  @PostMapping(value = "/login")
  public ResponseEntity<?> createAuthenticationToken(
          @RequestBody AuthenticationRequest authenticationRequest) {

    MyUser user = myUserRepository.findByUsername(authenticationRequest.getUsername());
    if (user != null && user.isEnabled()) {
      try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
      } catch (BadCredentialsException e) {
        throw new UnauthorizedRequestException("Incorrect username or password");
      }
      final UserDetails userDetails = userDetailsService
              .loadUserByUsername(authenticationRequest.getUsername());
      final String jwt = jwtTokenUtil.generateToken(userDetails);
      return ResponseEntity.ok(new AuthenticationResponse(jwt));
    } else {
      throw new UnauthorizedRequestException("Not validated");
    }
  }

  @PostMapping(value = "/register")
  public ResponseEntity<?> registerNewUser(@RequestBody AuthenticationRequest request) {
    if (userDetailsService.validateAuthenticationRequest(request)) {
      MyUser newUser = new MyUser(request.getUsername(), request.getEmail(), request.getPassword());
      userService.save(newUser);
      ConfirmationToken confirmationToken = new ConfirmationToken(newUser);
      confirmationTokenRepository.save(confirmationToken);
      SimpleMailMessage mailMessage = emailSenderService.createEmail(confirmationToken, newUser);
      emailSenderService.sendEmail(mailMessage);
      return new ResponseEntity(HttpStatus.OK);
    } else {
      throw new BadRequestException("Please provide valid details");
    }
  }

  @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
    ConfirmationToken token = confirmationTokenRepository
            .findByConfirmationToken(confirmationToken);

    if (token != null) {
      MyUser user = myUserRepository.findByUsername(token.getUser().getUsername());
      user.setEnabled(true);
      myUserRepository.save(user);
      return new ResponseEntity(HttpStatus.OK);
    } else {
      throw new BadRequestException("Invalid token");
    }
  }
}
