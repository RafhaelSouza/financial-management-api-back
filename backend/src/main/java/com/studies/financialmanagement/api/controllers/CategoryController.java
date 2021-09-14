package com.studies.financialmanagement.api.controllers;

import com.studies.financialmanagement.api.event.CreatedResourceEvent;
import com.studies.financialmanagement.api.models.Category;
import com.studies.financialmanagement.api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_SEARCH_CATEGORY') and #oauth2.hasScope('read')")
    public List<Category> list() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_SEARCH_CATEGORY') and #oauth2.hasScope('read')")
    public ResponseEntity<Category> findById(@PathVariable Long id) throws Exception {
        Optional<Category> category = repository.findById(id);
        if (category.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(category.get());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SAVE_CATEGORY') and #oauth2.hasScope('write')")
    public ResponseEntity<Category> save(@RequestBody @Valid Category category, HttpServletResponse response) {
        Category savedCategory = repository.save(category);

        publisher.publishEvent(new CreatedResourceEvent(this, response, savedCategory.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

}
