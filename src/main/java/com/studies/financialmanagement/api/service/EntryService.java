package com.studies.financialmanagement.api.service;

import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.models.Person;
import com.studies.financialmanagement.api.repositories.EntryRepository;
import com.studies.financialmanagement.api.service.exception.InactiveOrInexistentPersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntryService {

    @Autowired
    private PersonService personService;

    @Autowired
    private EntryRepository repository;

    public Entry save(Entry entry) {
        Person person = personService.getPersonByIdOrReturnNull(entry.getPerson().getId());

        if (person == null || person.isInactive())
            throw new InactiveOrInexistentPersonException();

        return repository.save(entry);
    }

}
