package com.studies.financialmanagement.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table
public class Entry {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String description;

    @NotNull
    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @NotNull
    @Column
    private BigDecimal price;

    @Column
    private String observation;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type")
    private EntryType entryType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

}
