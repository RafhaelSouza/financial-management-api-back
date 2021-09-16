package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.repositories.entry.EntryRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long>, EntryRepositoryQuery {
}
