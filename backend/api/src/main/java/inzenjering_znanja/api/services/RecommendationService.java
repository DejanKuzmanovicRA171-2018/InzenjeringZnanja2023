package inzenjering_znanja.api.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inzenjering_znanja.api.DTO.RecommendDTO;
import inzenjering_znanja.api.DTO.RecommendResponseDTO;

@Service
public class RecommendationService {
    @Autowired
    ExecuteFuzzyLogicService efService;
    @Autowired
    ExecuteQueryService eqService;
    private String ontUri = "http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#";

    public RecommendResponseDTO recommendComponents(RecommendDTO constraints) {

        // Start buliding query
        String queryString = "PREFIX ont:<" + ontUri + ">\n" +
                "SELECT ?cpu ?gpu ?ram ?motherboard ?storage\n" +
                "WHERE {\n" +
                "?cpu a ont:CPU. ?gpu a ont:GPU. ?ram a ont:RAM. ?motherboard a ont:Motherboard. ?storage a ont:Storage.\n"
                +
                "?gpu ont:hasPCI-E ?gc. ?motherboard ont:hasPCI-E ?gc. \n" +
                "?cpu ont:hasSocket ?socket. ?motherboard ont:hasSocket ?socket.\n" +
                "?ram ont:hasRAMSpeedType ?ramSpeed. ?motherboard ont:hasRAMSpeedType ?ramSpeed. ?cpu ont:hasRAMSpeedType ?ramSpeed. ?ram ont:hasNumberOfRAMSlots ?ramSlotsR. ?motherboard ont:hasNumberOfRAMSlots ?ramSlotsM. FILTER(?ramSlotsR <= ?ramSlotsM). \n"
                +
                "?motherboard ont:hasRAMCapacity ?ramCapacityM. ?ram ont:hasSizeOfRAM ?ramCapacityR. \n" +
                "FILTER(?ramCapacityR <= ?ramCapacityM).\n";
        // Append constraints to query
        queryString = appendConstraintsToQueryString(queryString, constraints);
        System.out.println(queryString);
        // Execute query
        RecommendResponseDTO result = eqService.executeRecommendQuery(queryString, constraints);
        result.usageScores = efService.executeFuzzyLogic(result.usageScores);
        return result;
    }

    public static String appendConstraintsToQueryString(String queryString, RecommendDTO constraints) {
        // CPU CONSTRAINTS
        if (constraints.cpuClockMin != 0) {
            queryString += 
                    "FILTER(?cpuClockSpeed >=" + constraints.cpuClockMin + ") .\n";
        }
        if (constraints.cpuClockMax != 0) {
            queryString += 
                    "FILTER(?cpuClockSpeed <=" + constraints.cpuClockMax + ") .\n";
        }
        if (constraints.cpuCoresMin != 0) {
            queryString +=
                    "FILTER(?cpuCores >=" + constraints.cpuCoresMin + ") .\n";
        }
        if (constraints.cpuCoresMax != 0) {
            queryString +=
                    "FILTER(?cpuCores <=" + constraints.cpuCoresMax + ") .\n";
        }
        if (constraints.cpuThreadsMin != 0) {
            queryString +=
                    "FILTER(?cpuThreads >=" + constraints.cpuThreadsMin + ") .\n";
        }
        if (constraints.cpuThreadsMax != 0) {
            queryString +=
                    "FILTER(?cpuThreads <=" + constraints.cpuThreadsMax + ") .\n";
        }
        if (constraints.cpuSocket != "") {
            queryString += "?cpu ont:hasSocket ?cpuSocket . \n" +
                    "FILTER(?cpuSocket = '" + constraints.cpuSocket + "') .\n";
        }
        // GPU CONSTRAINTS
        if (constraints.gpuClockMin != 0) {
            queryString += 
                    "FILTER(?gpuClockSpeed >=" + constraints.gpuClockMin + ") .\n";
        }
        if (constraints.gpuClockMax != 0) {
            queryString += 
                    "FILTER(?gpuClockSpeed <=" + constraints.gpuClockMax + ") .\n";
        }
        if (constraints.gpuVRAMMin != 0) {
            queryString +=
                    "FILTER(?gpuVRAM >=" + constraints.gpuVRAMMin + ") .\n";
        }
        if (constraints.gpuVRAMMax != 0) {
            queryString +=
                    "FILTER(?gpuVRAM <=" + constraints.gpuVRAMMax + ") .\n";
        }
        if (constraints.pciEGPU != "") {
            queryString += "?gpu ont:hasPCI-E ?gpuPciE . \n" +
                    "FILTER(?gpuPciE ='" + constraints.pciEGPU + "') .\n";
        }
        // RAM CONSTRAINTS
        if (constraints.ramClockMin != 0) {
            queryString += 
                    "FILTER(?ramClock >=" + constraints.ramClockMin + ") .\n";
        }
        if (constraints.ramClockMax != 0) {
            queryString += 
                    "FILTER(?ramClock <=" + constraints.ramClockMax + ") .\n";
        }
        if (constraints.ramSizeMin != 0) {
            queryString += 
                    "FILTER(?ramCapacityR >=" + constraints.ramSizeMin + ") .\n";
        }
        if (constraints.ramSizeMax != 0) {
            queryString += 
                    "FILTER(?ramCapacityR <=" + constraints.ramSizeMax + ") .\n";
        }
        if (constraints.ramSpeedType != "") {
            queryString += "?ram ont:hasRAMSpeedType ?ramSpeedType . \n" +
                    "FILTER(?ramSpeedType = '" + constraints.ramSpeedType + "') .\n";
        }
        // STORAGE CONSTRAINTS
        if (constraints.storageWriteSpeedMin != 0) {
            queryString += 
                    "FILTER(?writeSpeed >=" + constraints.storageWriteSpeedMin + ") .\n";
        }
        if (constraints.storageWriteSpeedMax != 0) {
            queryString += 
                    "FILTER(?writeSpeed <=" + constraints.storageWriteSpeedMax + ") .\n";
        }
        if (constraints.storageCapacityMin != 0) {
            queryString += 
                    "FILTER(?storageSize >=" + constraints.storageCapacityMin + ") .\n";
        }
        if (constraints.storageCapacityMax != 0) {
            queryString += 
                    "FILTER(?storageSize <=" + constraints.storageCapacityMax + ") .\n";
        }
        if (constraints.storageType == "HDD") {
            queryString += 
                    "FILTER(?rpm =" + constraints.storageRPM + ") . \n";
        }
        // MOTHERBOARD constraints
        if (constraints.minNumOfRamSlotsMb != 0) {
            queryString += 
                    "FILTER(?ramSlotsM >=" + constraints.minNumOfRamSlotsMb + ") . \n";
        }
        if (constraints.maxNumOfRamSlotsMb != 0) {
            queryString += 
                    "FILTER(?ramSlotsM <=" + constraints.maxNumOfRamSlotsMb + ") . \n";
        }
        if (constraints.minRamCapacityMb != 0) {
            queryString +=
                    "FILTER(?ramCapacityM >=" + constraints.minRamCapacityMb + ") . \n";
        }
        if (constraints.maxRamCapacityMb != 0) {
            queryString +=
                    "FILTER(?ramCapacityM <=" + constraints.maxRamCapacityMb + ") . \n";
        }
        for (int i = 0; i < constraints.pciEMb.length; i++) {
            queryString += "?motherboard ont:hasPCI-E ?pciESlot . \n" +
                    "FILTER(?pciESlot = '" + constraints.pciEMb[i] + "') . \n";
        }
        if (constraints.mbSocket != "") {
            queryString += "?motherboard ont:hasSocket ?mbSocket . \n" +
                    "FILTER(?mbSocket ='" + constraints.mbSocket + "') . \n";
        }
        return queryString;
    }

}
