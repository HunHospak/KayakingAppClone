package com.flow.opal.services;

import com.flow.opal.models.entities.MyUser;
import com.flow.opal.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainServiceImpl implements MainService {

  private UserService userService;
  private RiverService riverService;
  private HtmlDataService htmlDataService;

  @Autowired
  public MainServiceImpl(UserService userService, RiverService riverService,
      HtmlDataService htmlDataService) {
    this.userService = userService;
    this.riverService = riverService;
    this.htmlDataService = htmlDataService;
  }

  @Override
  public void initialize() {
    riverService.saveAllRivers();
    htmlDataService.parseHtmlAndSaveRiverSections(htmlDataService.getHtml());
    if (userService.findAll().size() == 0) {
      userService.save(new MyUser("admin", "admin@hello.hu", "admin",true));
    }
  }

}
