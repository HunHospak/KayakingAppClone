package com.flow.opal.heartbeat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HeartbeatDTO {

  @JsonProperty("application-status")
  private String applicationStatus;
  @JsonProperty("database-status")
  private String databaseStatus;

  public HeartbeatDTO(String applicationStatus, String databaseStatus) {
    this.applicationStatus = applicationStatus;
    this.databaseStatus = databaseStatus;
  }

  public String getApplicationStatus() {
    return applicationStatus;
  }

  public void setApplicationStatus(String applicationStatus) {
    this.applicationStatus = applicationStatus;
  }

  public String getDatabaseStatus() {
    return databaseStatus;
  }

  public void setDatabaseStatus(String databaseStatus) {
    this.databaseStatus = databaseStatus;
  }
}
