package inzenjering_znanja.api.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inzenjering_znanja.api.DTO.RecommendDTO;
import inzenjering_znanja.api.Helpers.EuclideanDistanceCalculator;

@Service
public class RecommendationService {
    @Autowired
    ExecuteQueryService eqService;
    private String ontUri = "http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#";

    public String recommendComponents(RecommendDTO constraints) {

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
        String result = eqService.executeRecommendQuery(queryString, constraints);
        return result;
    }

    private String appendConstraintsToQueryString(String queryString, RecommendDTO constraints) {
        // CPU CONSTRAINTS
        if (constraints.cpuClockMin != 0) {
            queryString += "?cpu ont:hasClockSpeed ?cpuClock . \n" +
                    "FILTER(?cpuClock >=" + constraints.cpuClockMin + ") .\n";
        }
        if (constraints.cpuClockMax != 0) {
            queryString += "?cpu ont:hasClockSpeed ?cpuClock . \n" +
                    "FILTER(?cpuClock <=" + constraints.cpuClockMax + ") .\n";
        }
        if (constraints.cpuCoresMin != 0) {
            queryString += "?cpu ont:hasNumberOfCores ?cpuCores . \n" +
                    "FILTER(?cpuCores >=" + constraints.cpuCoresMin + ") .\n";
        }
        if (constraints.cpuCoresMax != 0) {
            queryString += "?cpu ont:hasNumberOfCores ?cpuCores . \n" +
                    "FILTER(?cpuCores <=" + constraints.cpuCoresMax + ") .\n";
        }
        if (constraints.cpuThreadsMin != 0) {
            queryString += "?cpu ont:hasNumberOfThreads ?cpuThreads . \n" +
                    "FILTER(?cpuThreads >=" + constraints.cpuThreadsMin + ") .\n";
        }
        if (constraints.cpuThreadsMax != 0) {
            queryString += "?cpu ont:hasNumberOfThreads ?cpuThreads . \n" +
                    "FILTER(?cpuThreads <=" + constraints.cpuThreadsMax + ") .\n";
        }
        if (constraints.cpuSocket != "") {
            queryString += "?cpu ont:hasSocket ?cpuSocket . \n" +
                    "FILTER(?cpuSocket = '" + constraints.cpuSocket + "') .\n";
        }
        // GPU CONSTRAINTS
        if (constraints.gpuClockMin != 0) {
            queryString += "?gpu ont:hasClockSpeed ?gpuClock . \n" +
                    "FILTER(?gpuClock >=" + constraints.gpuClockMin + ") .\n";
        }
        if (constraints.gpuClockMax != 0) {
            queryString += "?gpu ont:hasClockSpeed ?gpuClock . \n" +
                    "FILTER(?gpuClock <=" + constraints.gpuClockMax + ") .\n";
        }
        if (constraints.gpuVRAMMin != 0) {
            queryString += "?gpu ont:hasVRAM ?gpuVRAM . \n" +
                    "FILTER(?gpuVRAM >=" + constraints.gpuVRAMMin + ") .\n";
        }
        if (constraints.gpuVRAMMax != 0) {
            queryString += "?gpu ont:hasVRAM ?gpuVRAM . \n" +
                    "FILTER(?gpuVRAM <=" + constraints.gpuVRAMMax + ") .\n";
        }
        if (constraints.pciEGPU != "") {
            queryString += "?gpu ont:hasPCI-E ?gpuPciE . \n" +
                    "FILTER(?gpuPciE ='" + constraints.pciEGPU + "') .\n";
        }
        // RAM CONSTRAINTS
        if (constraints.ramClockMin != 0) {
            queryString += "?ram ont:hasClockSpeed ?ramClock . \n" +
                    "FILTER(?ramClock >=" + constraints.ramClockMin + ") .\n";
        }
        if (constraints.ramClockMax != 0) {
            queryString += "?ram ont:hasClockSpeed ?ramClock . \n" +
                    "FILTER(?ramClock <=" + constraints.ramClockMax + ") .\n";
        }
        if (constraints.ramSizeMin != 0) {
            queryString += "?ram ont:hasSizeOfRAM ?ramSize . \n" +
                    "FILTER(?ramSize >=" + constraints.ramSizeMin + ") .\n";
        }
        if (constraints.ramSizeMax != 0) {
            queryString += "?ram ont:hasSizeOfRAM ?ramSize . \n" +
                    "FILTER(?ramSize <=" + constraints.ramSizeMax + ") .\n";
        }
        if (constraints.ramSpeedType != "") {
            queryString += "?ram ont:hasRAMSpeedType ?ramSpeedType . \n" +
                    "FILTER(?ramSpeedType = '" + constraints.ramSpeedType + "') .\n";
        }
        // STORAGE CONSTRAINTS
        if (constraints.storageWriteSpeedMin != 0) {
            queryString += "?storage ont:hasWriteSpeed ?storageWrite . \n" +
                    "FILTER(?storageWrite >=" + constraints.storageWriteSpeedMin + ") .\n";
        }
        if (constraints.storageWriteSpeedMax != 0) {
            queryString += "?storage ont:hasWriteSpeed ?storageWrite . \n" +
                    "FILTER(?storageWrite <=" + constraints.storageWriteSpeedMax + ") .\n";
        }
        if (constraints.storageCapacityMin != 0) {
            queryString += "?storage ont:hasCapacity ?storageSize . \n" +
                    "FILTER(?storageSize >=" + constraints.storageCapacityMin + ") .\n";
        }
        if (constraints.storageCapacityMax != 0) {
            queryString += "?storage ont:hasCapacity ?storageSize . \n" +
                    "FILTER(?storageSize <=" + constraints.storageCapacityMax + ") .\n";
        }
        if (constraints.storageType == "HDD") {
            queryString += "?storage ont:hasRPM ?storageRPM . \n" +
                    "FILTER(?storageRPM =" + constraints.storageRPM + ") . \n";
        }
        // MOTHERBOARD constraints
        if (constraints.minNumOfRamSlotsMb != 0) {
            queryString += "?motherboard ont:hasNumberOfRAMSlots ?ramSlots . \n" +
                    "FILTER(?ramSlots >=" + constraints.minNumOfRamSlotsMb + ") . \n";
        }
        if (constraints.maxNumOfRamSlotsMb != 0) {
            queryString += "?motherboard ont:hasNumberOfRAMSlots ?ramSlots . \n" +
                    "FILTER(?ramSlots <=" + constraints.maxNumOfRamSlotsMb + ") . \n";
        }
        if (constraints.minRamCapacityMb != 0) {
            queryString += "?motherboard ont:hasRAMCapacity ?ramCapacity . \n" +
                    "FILTER(?ramCapacity >=" + constraints.minRamCapacityMb + ") . \n";
        }
        if (constraints.maxNumOfRamSlotsMb != 0) {
            queryString += "?motherboard ont:hasRAMCapacity ?ramCapacity . \n" +
                    "FILTER(?ramCapacity <=" + constraints.maxRamCapacityMb + ") . \n";
        }
        for (int i = 0; i < constraints.pciEMb.length; i++) {
            queryString += "?motherboard ont:hasPCI-E ?pciESlot . \n" +
                    "FILTER(?pciESlot = '" + constraints.pciEMb[i] + "') . \n";
        }
        if (constraints.mbSocket != "") {
            queryString += "?motherboard ont:hasSocket ?mbSocket . \n" +
                    "FILTER(?mbSocket ='" + constraints.mbSocket + "') . \n";
        }
        return queryString += "}";
    }

    public List<Double> mostSimilarPC(List<Double> targetConfig) {
        List<List<Double>> configs = eqService.executeGetAllConfigsQuery();
        List<Double> distances = calculateDistances(targetConfig, configs);
        return getTopConfiguration(distances, configs);
    }

    private static List<Double> calculateDistances(List<Double> targetConfiguration,
            List<List<Double>> configurations) {
        List<Double> distances = new ArrayList<>();

        for (List<Double> configuration : configurations) {
            double distance = EuclideanDistanceCalculator.calculateEuclideanDistance(targetConfiguration,
                    configuration);
            distances.add(distance);
        }

        return distances;
    }

    private static List<Double> getTopConfiguration(List<Double> distances, List<List<Double>> configurations) {
        // Find the index with the smallest distance (top configuration)
        int topIndex = distances.indexOf(Collections.min(distances));

        // Return the top configuration
        return configurations.get(topIndex);
    }

}
