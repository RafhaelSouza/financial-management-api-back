package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByStateId(Long stateId);

}
