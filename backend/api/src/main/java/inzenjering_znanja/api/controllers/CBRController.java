package inzenjering_znanja.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import inzenjering_znanja.api.DTO.TargetConfigDTO;
import inzenjering_znanja.api.Models.CPU;
import inzenjering_znanja.api.Models.GPU;
import inzenjering_znanja.api.Models.Motherboard;
import inzenjering_znanja.api.Models.PC;
import inzenjering_znanja.api.Models.RAM;
import inzenjering_znanja.api.Models.Storage;
import inzenjering_znanja.api.services.ConfigCBR;
import inzenjering_znanja.api.services.ExecuteQueryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CBRController {
    @Autowired
    ConfigCBR configCBRService;
    @Autowired
    ExecuteQueryService eqService;

    @GetMapping("/testCBR")
    public List<PC> getPCs() {
        return eqService.executeGetAllConfigsQuery(null);
    }

    @PostMapping("/similar")
    public List<PC> mostSimilarPC(@RequestBody TargetConfigDTO req) {
        PC targetConfig = targetConfigToPC(req);
        List<PC> similarPCs = configCBRService.Go(targetConfig);
        similarPCs = eqService.completePCs(similarPCs);

        similarPCs = similarPCs.stream()
                .filter(pc -> pc.getPsu() != null && pc.getPcCase() != null && pc.getCooling() != null)
                .collect(Collectors.toList());
        similarPCs.remove(0);
        similarPCs = similarPCs.subList(0, 5);
        return similarPCs;
    }

    private PC targetConfigToPC(TargetConfigDTO req) {

        CPU newCPU = new CPU("", req.cpuClock, req.cpuCores, req.cpuThreads);
        GPU newGPU = new GPU("", req.gpuClock, req.gpuCores, req.gpuVRAM);
        Motherboard newMB = new Motherboard("", req.numOfRamSlotsMb, req.CapacityMb);
        RAM newRAM = new RAM("", req.ramClock, req.ramSize);
        Storage newStorage = new Storage("", req.storageWriteSpeed, req.storageCapacity, req.storageRPM);
        PC newPC = new PC(newCPU, newGPU, newMB, newRAM, newStorage);

        return newPC;
    }
}
