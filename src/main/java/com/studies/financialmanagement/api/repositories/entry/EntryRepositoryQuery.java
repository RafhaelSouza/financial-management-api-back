package com.studies.financialmanagement.api.repositories.entry;

import com.studies.financialmanagement.api.dto.EntryStatisticsCategory;
import com.studies.financialmanagement.api.dto.EntryStatisticsDay;
import com.studies.financialmanagement.api.dto.EntryStatisticsPerson;
import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.repositories.filter.EntryFilter;
import com.studies.financialmanagement.api.repositories.projections.EntrySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EntryRepositoryQuery {

    List<EntryStatisticsPerson> byPerson(LocalDate begin, LocalDate end);
    List<EntryStatisticsDay> byDay(LocalDate referenceMonth);
    List<EntryStatisticsCategory> byCategory(LocalDate referenceMonth);

    Page<Entry> toFilter(EntryFilter entryFilter, Pageable pageable);
    Page<EntrySummary> toSummary(EntryFilter entryFilter, Pageable pageable);

}
