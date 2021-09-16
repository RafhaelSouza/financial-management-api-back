package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
