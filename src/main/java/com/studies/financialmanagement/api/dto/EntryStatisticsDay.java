package com.studies.financialmanagement.api.dto;

import com.studies.financialmanagement.api.models.EntryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class EntryStatisticsDay {

    private EntryType entryType;

    private LocalDate day;

    private BigDecimal total;

}
