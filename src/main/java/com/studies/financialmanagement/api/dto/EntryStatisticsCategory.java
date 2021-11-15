package com.studies.financialmanagement.api.dto;

import com.studies.financialmanagement.api.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class EntryStatisticsCategory {

    private Category category;

    private BigDecimal total;

}
