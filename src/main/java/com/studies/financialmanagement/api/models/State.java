package com.studies.financialmanagement.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "state")
public class State {

    @EqualsAndHashCode.Include
    @Id
    private Long id;

    private String name;

}
