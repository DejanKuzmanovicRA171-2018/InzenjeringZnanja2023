package inzenjering_znanja.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import inzenjering_znanja.api.DTO.RecommendDTO;
import inzenjering_znanja.api.DTO.RecommendResponseDTO;
import inzenjering_znanja.api.DTO.TargetConfigDTO;
import inzenjering_znanja.api.Models.CPU;
import inzenjering_znanja.api.Models.GPU;
import inzenjering_znanja.api.Models.Motherboard;
import inzenjering_znanja.api.Models.PC;
import inzenjering_znanja.api.Models.RAM;
import inzenjering_znanja.api.Models.Storage;
import inzenjering_znanja.api.services.ExecuteFuzzyLogicService;
import inzenjering_znanja.api.services.RecommendationService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class RecommendationController {
    @Autowired
    RecommendationService recService;
    @Autowired
    ExecuteFuzzyLogicService fuzzyLogicService;

    @PostMapping("/recommend")
    public RecommendResponseDTO recommendComponents(@RequestBody RecommendDTO req) {
        return recService.recommendComponents(req);
    }

}
