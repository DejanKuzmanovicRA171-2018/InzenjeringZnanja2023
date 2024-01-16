package inzenjering_znanja.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import inzenjering_znanja.api.DTO.RecommendDTO;
import inzenjering_znanja.api.DTO.TargetConfigDTO;
import inzenjering_znanja.api.services.RecommendationService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RecommendationController {
    @Autowired
    RecommendationService recService;

    @PostMapping("/recommend")
    public String recommendComponents(@RequestBody RecommendDTO req) {
        return recService.recommendComponents(req);
    }

    @PostMapping("/similar")
    public List<Double> mostSimilarPC(@RequestBody TargetConfigDTO req) {
        List<Double> targetConfig = targetConfigToList(req);
        return recService.mostSimilarPC(targetConfig);
    }

    private List<Double> targetConfigToList(TargetConfigDTO req) {
        List<Double> features = new ArrayList<>();

        // CPU constraints
        features.add((double) req.cpuClock);
        features.add((double) req.cpuCores);
        features.add((double) req.cpuThreads);
        // GPU constraints
        features.add((double) req.gpuClock);
        features.add((double) req.gpuVRAM);
        // RAM constraints
        features.add((double) req.ramClock);
        features.add((double) req.ramSize);
        // STORAGE constraints
        features.add((double) req.storageWriteSpeed);
        features.add((double) req.storageCapacity);
        features.add((double) req.storageRPM);
        // COOLING constraints
        features.add((double) req.thermalPerformance);
        // PSU constraints
        features.add((double) req.psuPower);
        // MOTHERBOARD constraints
        features.add((double) req.numOfRamSlotsMb);
        features.add((double) req.CapacityMb);

        return features;
    }

}
