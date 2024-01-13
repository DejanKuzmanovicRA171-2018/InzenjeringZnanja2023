package inzenjering_znanja.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inzenjering_znanja.api.DTO.RecommendDTO;

@Service
public class RecommendationService {
    @Autowired
    ExecuteQueryService eqService;
    private String ontUri = "http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#";

    public List<String> recommendComponents(RecommendDTO constraints) {

        // Start buliding query
        String queryString = "PREFIX ont:<" + ontUri + ">\n" +
                "SELECT ?cpu ?gpu ?ram ?motherboard ?storage\n" +
                "WHERE {\n" +
                "?cpu a ont:CPU. ?gpu a ont:GPU. ?ram a ont:RAM. ?motherboard a ont:Motherboard. ?storage a ont:Storage.\n"
                +
                "?gpu ont:hasPCI-E ?gc. ?motherboard ont:hasPCI-E ?gc. \n" +
                "?cpu ont:hasSocket ?socket. ?motherboard ont:hasSocket ?socket. \n" +
                "?ram ont:hasRAMSpeedType ?ramSpeed. ?motherboard ont:hasRAMSpeedType ?ramSpeed. ?cpu ont:hasRAMSpeedType ?ramSpeed. ?ram ont:hasNumberOfRAMSlots ?ramSlotsR. ?motherboard ont:hasNumberOfRAMSlots ?ramSlotsM. FILTER(?ramSlotsR <= ?ramSlotsM). \n"
                +
                "?motherboard ont:hasRAMCapacity ?ramCapacityM. ?ram ont:hasSizeOfRAM ?ramCapacityR. \n" +
                "FILTER(?ramCapacityR <= ?ramCapacityM).\n";
        // Append constraints to query
        queryString = appendConstraintsToQueryString(queryString, constraints);
        System.out.println(queryString);
        // Execute query
        List<String> resultList = eqService.executeRecommendQuery(queryString);
        return resultList;
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
        if (constraints.storageRPM != 0) {
            queryString += "?storage ont:hasRPM ?storageRPM . \n" +
                    "FILTER(?storageRPM =" + constraints.storageRPM + ") . \n";
        }
        return queryString += "}";
    }

}
