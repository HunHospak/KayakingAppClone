package com.flow.opal.repositories;

import com.flow.opal.models.entities.RiverPlace;
import com.flow.opal.models.entities.RiverSection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RiverPlaceRepository extends JpaRepository<RiverPlace, Integer> {
  RiverPlace findByName(String name);

  @Query
  List<RiverPlace> findByRiverSection(RiverSection riverSection);
}
