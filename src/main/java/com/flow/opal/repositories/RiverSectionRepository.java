package com.flow.opal.repositories;

import com.flow.opal.models.entities.RiverSection;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiverSectionRepository extends JpaRepository<RiverSection,Long> {

  Optional<RiverSection> findByName(String name);
}
