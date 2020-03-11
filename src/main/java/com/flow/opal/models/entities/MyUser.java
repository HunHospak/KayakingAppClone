package com.flow.opal.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "myuser")
public class MyUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String username;
  private String email;
  private String password;
  private boolean isEnabled;

  public MyUser() {
  }

  public MyUser(String username, String email, String password) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public MyUser(String username, String email, String password, boolean isEnabled) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.isEnabled = isEnabled;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }
}
