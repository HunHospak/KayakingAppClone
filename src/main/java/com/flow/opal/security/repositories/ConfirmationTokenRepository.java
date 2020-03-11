package com.flow.opal.security.repositories;

import com.flow.opal.security.models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
  ConfirmationToken findByConfirmationToken(String confirmationToken);
}
