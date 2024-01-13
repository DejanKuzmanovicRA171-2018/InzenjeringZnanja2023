package inzenjering_znanja.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import inzenjering_znanja.api.DTO.RecommendDTO;
import inzenjering_znanja.api.services.RecommendationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RecommendationController {
    @Autowired
    RecommendationService recService;

    @PostMapping("/recommend")
    public List<String> recommendComponents(@RequestBody RecommendDTO req) {
        return recService.recommendComponents(req);
    }

}
