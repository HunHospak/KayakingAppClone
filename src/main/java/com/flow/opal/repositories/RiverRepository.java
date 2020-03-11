package com.flow.opal.repositories;

import com.flow.opal.models.entities.River;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RiverRepository extends JpaRepository<River, Long> {

  Optional<River> findByRiverName(String name);

  @Query(value = "select river_name from river", nativeQuery = true)
  List<String> findRiverNames();
}
