package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.repositories.entry.EntryRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long>, EntryRepositoryQuery {

    List<Entry> findByDueDateLessThanEqualAndPaymentDueIsNull(LocalDate data);

}
