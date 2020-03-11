package com.flow.opal;

import com.flow.opal.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlowOpalApplication implements CommandLineRunner {

  @Autowired
  MainService service;

  public static void main(String[] args) {
    SpringApplication.run(FlowOpalApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    service.initialize();
  }

}
