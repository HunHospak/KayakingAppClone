package com.flow.opal.heartbeat;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.ApplicationHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Endpoint(id = "heartbeat")
@Component
public class Heartbeat {

  private DataSource dataSource;

  @Autowired
  public Heartbeat(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @ReadOperation
  public ResponseEntity<HeartbeatDTO> heartbeat() {
    ApplicationHealthIndicator appHealth = new ApplicationHealthIndicator();
    DataSourceHealthIndicator dbHealth = new DataSourceHealthIndicator(dataSource);
    Status dbStatus = dbHealth.health().getStatus();
    Status appStatus = appHealth.health().getStatus();
    if (dbStatus.equals(Status.UP) && appStatus.equals(Status.UP)) {
      return ResponseEntity.ok(new HeartbeatDTO("ok", "ok"));
    } else {
      if (dbStatus.equals(Status.UP)) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new HeartbeatDTO("error", "ok"));
      } else if (appStatus.equals(Status.UP)) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new HeartbeatDTO("ok", "error"));
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new HeartbeatDTO("error", "error"));
      }
    }
  }
}
