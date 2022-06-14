package com.studies.financialmanagement.api.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Data
@Embeddable
public class Address {

    @Size(min = 5, max = 100)
    @Column(name = "address_street")
    private String street;

    @Size(min = 1, max = 10)
    @Column(name = "address_number")
    private String number;

    @Size(min = 3, max = 100)
    @Column(name = "address_complement")
    private String complement;

    @Size(min = 3, max = 100)
    @Column(name = "address_district")
    private String district;

    @Size(min = 8, max = 15)
    @Column(name = "address_postalcode")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
