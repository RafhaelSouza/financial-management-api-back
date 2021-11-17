package com.studies.financialmanagement.api.dto;

import com.studies.financialmanagement.api.models.EntryType;
import com.studies.financialmanagement.api.models.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class EntryStatisticsPerson {

    private EntryType entryType;
    private Person person;
    private BigDecimal total;


}
