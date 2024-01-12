package inzenjering_znanja.api.services;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inzenjering_znanja.api.Models.CPU;

@Service
public class RecommendationService {
    @Autowired
    ExecuteQueryService eqService;
    private String ontUri = "http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#";

    public ResultSet recommendMissingComponents(String CPU, String GPU, String RAM, String Motherboard, String Case,
            String PSU) {
        Resource cpu = eqService.infModel.getResource(ontUri + CPU);
        Resource gpu = eqService.infModel.getResource(ontUri + GPU);
        Resource ram = eqService.infModel.getResource(ontUri + RAM);
        Resource motherboard = eqService.infModel.getResource(ontUri + Motherboard);
        Resource pcCase = eqService.infModel.getResource(ontUri + Case);
        // Start buliding query
        String queryString = "PREFIX <" + ontUri + ">\n" +
                "SELECT ?cpu ?gpu ?ram ?motherboard ?case ?psu \n" +
                "WHERE {";

        // Check if each resource exists
        if (cpu != null && cpu.isResource()) {
            // The CPU resource exists
            System.out.println("CPU resource exists");
            queryString += "  ?cpu owl:sameAs ont:" + cpu.getLocalName() + " .\n";
        }
        if (gpu != null && gpu.isResource()) {
            // The GPU resource exists
            System.out.println("GPU resource exists");
            queryString += "  ?gpu owl:sameAs ont:" + gpu.getLocalName() + " .\n";
        }
        if (ram != null && ram.isResource()) {
            // The RAM resource exists
            System.out.println("RAM resource exists");
            queryString += "  ?ram owl:sameAs ont:" + ram.getLocalName() + " .\n";
        }
        if (motherboard != null && motherboard.isResource()) {
            // The Motherboard resource exists
            System.out.println("Motherboard resource exists");
            queryString += "  ?motherboard owl:sameAs ont:" + motherboard.getLocalName() + " .\n";
        }
        if (pcCase != null && pcCase.isResource()) {
            // The Case resource exists
            System.out.println("Case resource exists");
            queryString += "  ?case owl:sameAs ont:" + pcCase.getLocalName() + " .\n";
        }
        queryString += "?cpu ont:isCompatibleWithMotherboard ?motherboard . \n" +
                "?gpu ont:isCompatibleWithMotherboard ?motherboard . \n" +
                "?ram ont:isCompatibleWithMotherboard ?motherboard . \n" +
                "?ram ont:isCompatibleWithCPU ?cpu";
        return null;
    }

}
