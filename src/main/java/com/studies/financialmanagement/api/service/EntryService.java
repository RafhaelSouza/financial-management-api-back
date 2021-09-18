package com.studies.financialmanagement.api.service;

import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.models.Person;
import com.studies.financialmanagement.api.repositories.EntryRepository;
import com.studies.financialmanagement.api.service.exception.InactiveOrInexistentPersonException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntryService {

    @Autowired
    private PersonService personService;

    @Autowired
    private EntryRepository repository;

    public Entry save(Entry entry) {
        personValidate(entry);

        return repository.save(entry);
    }

    public Entry update(Long id, Entry entry) {
        Entry savedEntry = searchExistentEntry(id);
        if (!entry.getPerson().equals(savedEntry.getPerson()))
            personValidate(entry);

        BeanUtils.copyProperties(entry, savedEntry, "id");

        return repository.save(savedEntry);
    }

    private void personValidate(Entry entry) {
        Person person = null;

        if (entry.getPerson().getId() != null)
            person = personService.getPersonByIdOrReturnNull(entry.getPerson().getId());

        if (person == null || person.isInactive())
            throw new InactiveOrInexistentPersonException();
    }

    private Entry searchExistentEntry(Long id) {
        Optional<Entry> savedEntry = repository.findById(id);

        if (savedEntry.isEmpty())
            throw new IllegalArgumentException();

        return savedEntry.get();
    }
}
