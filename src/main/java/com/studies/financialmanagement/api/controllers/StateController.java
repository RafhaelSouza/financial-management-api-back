package com.studies.financialmanagement.api.controllers;

import com.studies.financialmanagement.api.models.State;
import com.studies.financialmanagement.api.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<State> searchAll() {
        return stateRepository.findAll();
    }

}
