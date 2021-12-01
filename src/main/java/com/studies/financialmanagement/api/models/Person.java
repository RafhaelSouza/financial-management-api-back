package com.studies.financialmanagement.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table
public class Person {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column
    private Boolean active;

    @Embedded
    private Address address;

    @Valid
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @JsonIgnore
    @Transient
    public Boolean isInactive() {
        return !this.active;
    }

}
