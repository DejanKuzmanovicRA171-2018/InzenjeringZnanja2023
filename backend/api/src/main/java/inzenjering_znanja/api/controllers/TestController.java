package inzenjering_znanja.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import inzenjering_znanja.api.services.TestOntology;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {
    @Autowired
    TestOntology service;

    @GetMapping("/")
    public List<String> testApi() {
        return service.getAllPCs();
    }

}
