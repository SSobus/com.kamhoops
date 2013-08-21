package com.kamhoops.controllers;

import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.DataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "generate")
public class DataGenerationController {

    @Autowired
    DataGenerationService service;

    @RequestMapping()
    public void generateNews() throws EntityValidationException, EntityNotFoundException {
        service.generateData();
    }
}
