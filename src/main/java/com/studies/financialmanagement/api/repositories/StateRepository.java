package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
