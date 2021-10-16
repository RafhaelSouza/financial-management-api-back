package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = " select p from Person p where p.name like :name%")
    Page<Person> findByNameContaining(@Param("name") String name, Pageable pageable);

}
