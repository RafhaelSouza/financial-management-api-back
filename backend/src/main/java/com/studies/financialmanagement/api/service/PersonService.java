package com.studies.financialmanagement.api.service;

import com.studies.financialmanagement.api.models.Person;
import com.studies.financialmanagement.api.repositories.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Person update(Long id, Person person) {
        Person savedPerson = getPersonById(id);

        BeanUtils.copyProperties(person, savedPerson, "id");

        return repository.save(savedPerson);
    }

    public void updateActiveProperty(Long id, Boolean active) {
        Person savedPerson = getPersonById(id);

        savedPerson.setActive(active);

        repository.save(savedPerson);

    }

    public Person getPersonById(Long id) {
        Optional<Person> savedPerson = repository.findById(id);

        if (savedPerson.isEmpty())
            throw new EmptyResultDataAccessException(1);

        return savedPerson.get();
    }

    public Person getPersonByIdOrReturnNull(Long id) {
        Optional<Person> savedPerson = repository.findById(id);

        if (savedPerson.isEmpty())
            return null;

        return savedPerson.get();
    }

}
