package com.studies.financialmanagement.api.controllers;

import com.studies.financialmanagement.api.models.City;
import com.studies.financialmanagement.api.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<City> search(@RequestParam Long state) {
        return cityRepository.findByStateId(state);
    }

}
