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

@Service
public class ExecuteQueryService {
    public String queryTest = "PREFIX ont:<http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#> SELECT ?cpu WHERE { ?cpu a ont:CPU}";
    public String rdfFilePath = "X:/GitRepos/InzenjeringZnanja2023/computer-ontology-classes.rdf";
    public String altPath = "C:/Users/sibin/Downloads/computer-ontology-classes.rdf";
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

    public String executeRecommendQuery(String queryString) {
        Query query = QueryFactory.create(queryString);
        List<List<String>> allConfigs = new ArrayList<>();
        String result = "";
        // Execute the query on the inferred model
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, infModel)) {
            ResultSet results = queryExecution.execSelect();

            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                String cpu = solution.getResource("cpu").getLocalName();
                String gpu = solution.getResource("gpu").getLocalName();
                String ram = solution.getResource("ram").getLocalName();
                String motherboard = solution.getResource("motherboard").getLocalName();
                String storage = solution.getResource("storage").getLocalName();
                List<String> config = new ArrayList<String>();
                config.add(cpu);
                config.add(gpu);
                config.add(ram);
                config.add(motherboard);
                config.add(storage);
                allConfigs.add(config);
            }
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
            return null;
        }
        for (List<String> config : allConfigs) {
            String queryCasePSUString = queryPrefix +
                    "SELECT ?psu ?case ?cooling\n" +
                    "WHERE { \n" +
                    "    ?psu      a  ont:PowerSupply .\n" + //
                    "    ?case     a  ont:Case .\n" + //
                    "    ?cooling  a  ont:Cooling .\n" + //
                    "    BIND(ont:" + config.get(0) + " AS ?cpu) .\n" + //
                    "    BIND(ont:" + config.get(1) + " AS ?gpu) .\n" + //
                    "    BIND(ont:" + config.get(3) + " AS ?motherboard).\n" + //
                    "    BIND(ont:" + config.get(2) + " AS ?ram).\n" + //
                    "    BIND(ont:" + config.get(4) + " AS ?storage) .\n" + //
                    "    ?cpu ont:hasPower ?cpuPower .\n" + //
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
                    result = config.get(0) + " || " + config.get(1) + " || " + config.get(2) + " || "
                            + config.get(3) + " || " + config.get(4) + " || " + psu + " || " + pcCase + " || "
                            + cooling;
                    break;
                }
            } catch (Exception e) {
                // Log or handle the exception
                e.printStackTrace();
                return null;
            }
        }

        return result;
    }
}
