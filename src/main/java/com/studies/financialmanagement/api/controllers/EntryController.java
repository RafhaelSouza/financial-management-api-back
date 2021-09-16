package com.studies.financialmanagement.api.controllers;

import com.studies.financialmanagement.api.event.CreatedResourceEvent;
import com.studies.financialmanagement.api.exceptionhandler.ApiExceptionHandler;
import com.studies.financialmanagement.api.models.Entry;
import com.studies.financialmanagement.api.repositories.EntryRepository;
import com.studies.financialmanagement.api.repositories.filter.EntryFilter;
import com.studies.financialmanagement.api.repositories.projections.EntrySummary;
import com.studies.financialmanagement.api.service.EntryService;
import com.studies.financialmanagement.api.service.exception.InactiveOrInexistentPersonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entries")
public class EntryController {

    @Autowired
    private EntryRepository repository;

    @Autowired
    private EntryService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_SEARCH_ENTRY') and #oauth2.hasScope('read')")
    public Page<Entry> search(EntryFilter entryFilter, Pageable pageable) {
        return repository.toFilter(entryFilter, pageable);
    }

    @GetMapping(params = "summary")
    @PreAuthorize("hasAuthority('ROLE_SEARCH_ENTRY') and #oauth2.hasScope('read')")
    public Page<EntrySummary> summary(EntryFilter entryFilter, Pageable pageable) {
        return repository.toSummary(entryFilter, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_SEARCH_ENTRY') and #oauth2.hasScope('read')")
    public ResponseEntity<Entry> findById(@PathVariable Long id) {
        Optional<Entry> entry = repository.findById(id);

        if (entry.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(entry.get());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SAVE_ENTRY') and #oauth2.hasScope('write')")
    public ResponseEntity<Entry> save(@RequestBody @Valid Entry entry, HttpServletResponse response) {
        Entry savedEntry = service.save(entry);

        publisher.publishEvent(new CreatedResourceEvent(this, response, savedEntry.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntry);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_DELETE_ENTRY') and #oauth2.hasScope('write')")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @ExceptionHandler(InactiveOrInexistentPersonException.class)
    public ResponseEntity<Object> handleInactiveOrInexistentPersonException(InactiveOrInexistentPersonException ex) {
        String userMessage = messageSource.getMessage("person.inactive-or-inexistent", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.toString();

        List<ApiExceptionHandler.ErrorMessages> errorMessagesList = Arrays.asList(new ApiExceptionHandler.ErrorMessages(userMessage, developerMessage));

        return ResponseEntity.badRequest().body(errorMessagesList);
    }

}
