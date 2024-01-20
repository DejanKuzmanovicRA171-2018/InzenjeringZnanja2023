package inzenjering_znanja.api.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.stereotype.Service;

import inzenjering_znanja.api.DTO.RecommendDTO;
import inzenjering_znanja.api.DTO.RecommendResponseDTO;
import inzenjering_znanja.api.Models.CPU;
import inzenjering_znanja.api.Models.Case;
import inzenjering_znanja.api.Models.Cooling;
import inzenjering_znanja.api.Models.GPU;
import inzenjering_znanja.api.Models.Motherboard;
import inzenjering_znanja.api.Models.PC;
import inzenjering_znanja.api.Models.PSU;
import inzenjering_znanja.api.Models.RAM;
import inzenjering_znanja.api.Models.Storage;
import inzenjering_znanja.api.services.RecommendationService;

@Service
public class ExecuteQueryService {
    public String queryTest = "PREFIX ont:<http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#> SELECT ?cpu WHERE { ?cpu a ont:CPU}";
    public String rdfFilePath = "X:/GitRepos/InzenjeringZnanja2023/computer-ontology-classes.rdf";
    public String altPath = "C:/Users/Dejan/Desktop/InzenjeringZnanja2023/computer-ontology-classes.rdf";
    public String queryPrefix = "PREFIX ont: <http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#>\n"
            +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
    public Model model;
    public Reasoner reasoner;
    public Model infModel;

    public ExecuteQueryService() {
        // Initialize the default model
        model = ModelFactory.createDefaultModel();
        // Read RDF data from the file into the model
        try {
            RDFDataMgr.read(model, altPath);
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
        }
        // Create a reasoner
        reasoner = ReasonerRegistry.getRDFSReasoner();
        // Apply the reasoner to the model
        infModel = ModelFactory.createInfModel(reasoner, model);
    }

    public RecommendResponseDTO executeRecommendQuery(String queryString, RecommendDTO constraints) {
        List<PC> allConfigs = executeGetAllConfigsQuery(constraints);

        RecommendResponseDTO response = new RecommendResponseDTO();
        for (PC config : allConfigs) {
            String queryCasePSUString = queryPrefix +
                    "SELECT ?psu ?case ?cooling ?coolingPower ?psuPower ?ramCapacity ?gpuCores ?cpuCores ?gpuVRAM\n" +
                    "WHERE { \n" +
                    "    ?psu      a  ont:PowerSupply .\n" + //
                    "    ?case     a  ont:Case .\n" + //
                    "    ?cooling  a  ont:Cooling .\n" + //
                    "    BIND(ont:" + config.getCpu().getName() + " AS ?cpu) .\n" + //
                    "    BIND(ont:" + config.getGpu().getName() + " AS ?gpu) .\n" + //
                    "    BIND(ont:" + config.getMotherboard().getName() + " AS ?motherboard).\n" + //
                    "    BIND(ont:" + config.getRam().getName() + " AS ?ram).\n" + //
                    "    BIND(ont:" + config.getStorage().getName() + " AS ?storage) .\n" + //
                    "    ?ram ont:hasSizeOfRAM ?ramCapacity .\n" +
                    "    ?gpu ont:hasNumberOfCores ?gpuCores .\n" +
                    "    ?cpu ont:hasNumberOfCores ?cpuCores .\n" +
                    "    ?gpu ont:hasVRAM ?gpuVRAM .\n";

            // COOLING constraints
            if (constraints.minimalThermalPerformance != 0) {
                queryCasePSUString += "?cooling ont:hasPower ?coolerTDP . \n" +
                        "FILTER(?coolerTDP >=" + constraints.minimalThermalPerformance + ") . \n";
            }
            if (constraints.coolingSockets.length > 0) {
                queryCasePSUString += "?cooling ont:hasSocket ?socket .\n" +
                        "FILTER(";
                for (int i = 0; i < constraints.coolingSockets.length; i++) {
                    queryCasePSUString += i > 0 ? " || " : "";
                    queryCasePSUString += "?socket ='" + constraints.coolingSockets[i] + "'";
                }
                queryCasePSUString += "). \n";

            }
            // PSU constraints
            if (constraints.minPSUPower != 0) {
                queryCasePSUString += "?psu ont:hasPower ?psuPower . \n" +
                        "FILTER(?psuPower >=" + constraints.minPSUPower + ") . \n";
            }
            if (constraints.maxPSUPower != 0) {
                queryCasePSUString += "?psu ont:hasPower ?psuPower . \n" +
                        "FILTER(?psuPower <=" + constraints.maxPSUPower + ") . \n";
            }
            queryCasePSUString += "?cpu ont:hasPower ?cpuPower .\n" + //
                    "    ?gpu ont:hasPower ?gpuPower .\n" + //
                    "    ?psu ont:hasPower ?psuPower.\n" + //
                    "    FILTER ( ( ?cpuPower + ?gpuPower ) <= ?psuPower ).\n" + //
                    "    ?cooling ont:hasSocket ?socket. \n" + //
                    "    ?cpu ont:hasSocket ?socket. \n" + //
                    "    ?motherboard ont:hasSocket ?socket. \n" + //
                    "    ?cooling ont:hasPower ?coolingPower.\n" +
                    "    FILTER(?coolingPower >= ?cpuPower).\n" +
                    "    ?case ont:isCompatibleWithPSUType ?psuT1.\n" + //
                    "    ?psu ont:isCompatibleWithPSUType ?psuT2.\n" + //
                    "    FILTER(?psuT1 = ?psuT2).\n" +
                    "    ?case ont:isCompatibleWithMotherboardType ?mbT1.\n" + //
                    "    ?motherboard ont:isCompatibleWithMotherboardType ?mbT2.\n" + //
                    "    FILTER(?mbT1 = ?mbT2).\n" +
                    "}";
            System.out.println(queryCasePSUString);
            Query queryCasePSU = QueryFactory.create(queryCasePSUString);

            try (QueryExecution qe = QueryExecutionFactory.create(queryCasePSU, infModel)) {
                ResultSet results = qe.execSelect();

                if (results.hasNext()) {
                    QuerySolution solution = results.nextSolution();
                    String psu = solution.getResource("psu").getLocalName();
                    String pcCase = solution.getResource("case").getLocalName();
                    String cooling = solution.getResource("cooling").getLocalName();

                    int coolingPower = solution.getLiteral("coolingPower").getInt();
                    int psuPower = solution.getLiteral("psuPower").getInt();

                    PSU newPSU = new PSU(psu, psuPower);
                    Cooling newCooling = new Cooling(cooling, coolingPower);
                    Case newCase = new Case(pcCase);

                    config.setCooling(newCooling);
                    config.setPcCase(newCase);
                    config.setPsu(newPSU);

                    response.recommendedComponents = config;

                    response.usageScores[5] = solution.getLiteral("coolingPower").getDouble();
                    response.usageScores[4] = solution.getLiteral("psuPower").getDouble();
                    response.usageScores[1] = solution.getLiteral("gpuCores").getDouble();
                    response.usageScores[2] = solution.getLiteral("gpuVRAM").getDouble();
                    response.usageScores[0] = solution.getLiteral("cpuCores").getDouble();
                    response.usageScores[3] = solution.getLiteral("ramCapacity").getDouble();

                    break;
                }
            } catch (Exception e) {
                // Log or handle the exception
                e.printStackTrace();
                return null;
            }
        }

        return response;
    }

    public List<PC> executeGetAllConfigsQuery(RecommendDTO constraints) {
        String queryString = queryPrefix +
                "SELECT ?cpu ?cpuClockSpeed ?cpuCores ?cpuThreads ?gpu ?gpuClockSpeed ?gpuCores ?gpuVRAM ?motherboard ?ramSlotsM ?ramCapacityM ?ram ?ramClock ?ramCapacityR ?storage ?storageSize ?writeSpeed ?rpm\n"
                + //
                "WHERE {\n" + //
                "?cpu a ont:CPU;\n" + //
                "   ont:hasSocket ?socket;\n" + //
                "   ont:hasRAMSpeedType ?ramSpeedC;\n" + //
                "   ont:hasClockSpeed ?cpuClockSpeed;\n" + //
                "   ont:hasNumberOfCores ?cpuCores;\n" + //
                "   ont:hasNumberOfThreads ?cpuThreads.\n" + //
                "?gpu a ont:GPU;\n" + //
                "   ont:hasPCI-E ?gc;\n" + //
                "   ont:hasVRAM ?gpuVRAM;\n" + //
                "   ont:hasClockSpeed ?gpuClockSpeed; ont:hasNumberOfCores ?gpuCores.\n" + //
                "?ram a ont:RAM;\n" + //
                "   ont:hasClockSpeed ?ramClock;\n" + //
                "   ont:hasNumberOfRAMSlots ?ramSlotsR;\n" + //
                "   ont:hasSizeOfRAM ?ramCapacityR;\n" + //
                "   ont:hasRAMSpeedType ?ramSpeedR.\n" + //
                "?motherboard a ont:Motherboard;\n" + //
                "   ont:hasPCI-E ?gc;\n" + //
                "   ont:hasSocket ?socket;\n" + //
                "   ont:isCompatibleWithMotherboardType ?mbT1;\n" + //
                "   ont:hasNumberOfRAMSlots ?ramSlotsM; ont:hasRAMCapacity ?ramCapacityM; ont:hasRAMSpeedType ?ramSpeedM.\n"
                + //
                "?storage a ont:Storage;\n" + //
                "   ont:hasWriteSpeed ?writeSpeed; ont:hasRPM ?rpm; ont:hasCapacity ?storageSize.\n" + //
                "?storage ont:hasRPM ?rpm.\n"; //
                if( constraints != null){
                   queryString = RecommendationService.appendConstraintsToQueryString(queryString, constraints);
                }
                queryString += "FILTER(?ramSlotsR <= ?ramSlotsM && ?ramCapacityR <= ?ramCapacityM && ?ramSpeedC = ?ramSpeedR && ?ramSpeedR = ?ramSpeedM)\n"
                + //
                "}";
        System.out.println(queryString);
        Query query = QueryFactory.create(queryString);
        List<PC> allConfigs = new ArrayList<>();
        // Execute the query on the inferred model
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, infModel)) {
            ResultSet results = queryExecution.execSelect();
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                PC pc = extractFeatures(solution);
                allConfigs.add(pc);
            }
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
            return null;
        }
        return allConfigs;
    }

    public List<PC> completePCs(List<PC> pcs) {
        for (PC pc : pcs) {
            String queryCasePSUString = queryPrefix +
                    "SELECT ?psu ?case ?cooling ?coolingPower ?psuPower ?ramCapacity ?gpuCores" +
                    "?cpuCores ?gpuVRAM\n" +
                    "WHERE { \n" +
                    " ?psu a ont:PowerSupply .\n" + //
                    " ?case a ont:Case .\n" + //
                    " ?cooling a ont:Cooling .\n" + //
                    " BIND(ont:" + pc.getCpu().getName() + " AS ?cpu) .\n" + //
                    " BIND(ont:" + pc.getGpu().getName() + " AS ?gpu) .\n" + //
                    " BIND(ont:" + pc.getMotherboard().getName() + " AS ?motherboard).\n" + //
                    " BIND(ont:" + pc.getRam().getName() + " AS ?ram).\n" + //
                    " BIND(ont:" + pc.getStorage().getName() + " AS ?storage) .\n" + //
                    " ?ram ont:hasSizeOfRAM ?ramCapacity .\n" +
                    " ?gpu ont:hasNumberOfCores ?gpuCores .\n" +
                    " ?cpu ont:hasNumberOfCores ?cpuCores .\n" +
                    " ?gpu ont:hasVRAM ?gpuVRAM .\n" +
                    " ?cpu ont:hasPower ?cpuPower .\n" + //
                    " ?gpu ont:hasPower ?gpuPower .\n" + //
                    " ?psu ont:hasPower ?psuPower.\n" + //
                    " FILTER ( ( ?cpuPower + ?gpuPower ) <= ?psuPower ).\n" + //
                    " ?cooling ont:hasSocket ?socket. \n" + //
                    " ?cpu ont:hasSocket ?socket. \n" + //
                    " ?motherboard ont:hasSocket ?socket. \n" + //
                    " ?cooling ont:hasPower ?coolingPower.\n" +
                    " FILTER(?coolingPower >= ?cpuPower).\n" +
                    " ?case ont:isCompatibleWithPSUType ?psuT1.\n" + //
                    " ?psu ont:isCompatibleWithPSUType ?psuT2.\n" + //
                    " FILTER(?psuT1 = ?psuT2).\n" +
                    " ?case ont:isCompatibleWithMotherboardType ?mbT1.\n" + //
                    " ?motherboard ont:isCompatibleWithMotherboardType ?mbT2.\n" + //
                    " FILTER(?mbT1 = ?mbT2).\n" +
                    "}";
            Query queryCasePSU = QueryFactory.create(queryCasePSUString);

            try (QueryExecution qe = QueryExecutionFactory.create(queryCasePSU, infModel)) {
                ResultSet results = qe.execSelect();

                if (results.hasNext()) {
                    QuerySolution solution = results.nextSolution();
                    String psu = solution.getResource("psu").getLocalName();
                    String pcCase = solution.getResource("case").getLocalName();
                    Case newCase = new Case(pcCase);
                    int psuPower = solution.getLiteral("psuPower").getInt();
                    PSU newPSU = new PSU(psu, psuPower);
                    String cooling = solution.getResource("cooling").getLocalName();
                    int coolingPower = solution.getLiteral("coolingPower").getInt();
                    Cooling newCooling = new Cooling(cooling, coolingPower);
                    pc.setPsu(newPSU);
                    pc.setCooling(newCooling);
                    pc.setPcCase(newCase);
                }
            } catch (Exception e) {
                // Log or handle the exception
                e.printStackTrace();
                return null;
            }
        }

        return pcs;
    }

    private static PC extractFeatures(QuerySolution solution) {

        String cpu = solution.getResource("cpu").getLocalName();
        int cpuClockSpeed = solution.getLiteral("cpuClockSpeed").getInt();
        int cpuCores = solution.getLiteral("cpuCores").getInt();
        int cpuThreads = solution.getLiteral("cpuThreads").getInt();
        CPU newCPU = new CPU(cpu, cpuClockSpeed, cpuCores, cpuThreads);
        String gpu = solution.getResource("gpu").getLocalName();
        int gpuClockSpeed = solution.getLiteral("gpuClockSpeed").getInt();
        int gpuCores = solution.getLiteral("gpuCores").getInt();
        int gpuVRAM = solution.getLiteral("gpuVRAM").getInt();
        GPU newGPU = new GPU(gpu, gpuClockSpeed, gpuCores, gpuVRAM);
        String motherboard = solution.getResource("motherboard").getLocalName();
        int numOfRAMSlots = solution.getLiteral("ramSlotsM").getInt();
        int ramCapacityM = solution.getLiteral("ramCapacityM").getInt();
        Motherboard newMotherboard = new Motherboard(motherboard, numOfRAMSlots, ramCapacityM);
        String ram = solution.getResource("ram").getLocalName();
        // String ramSpeedType = solution.getLiteral("ramSpeed").getString();
        int ramClockSpeed = solution.getLiteral("ramClock").getInt();
        int ramCapacityR = solution.getLiteral("ramCapacityR").getInt();
        RAM newRAM = new RAM(ram, ramClockSpeed, ramCapacityR);
        String storage = solution.getResource("storage").getLocalName();
        int storageSize = solution.getLiteral("storageSize").getInt();
        int writeSpeed = solution.getLiteral("writeSpeed").getInt();
        int rpm = solution.getLiteral("rpm").getInt();
        Storage newStorage = new Storage(storage, writeSpeed, storageSize, rpm);
        PC newPC = new PC(newCPU, newGPU, newMotherboard, newRAM, newStorage);

        return newPC;
    }
}
