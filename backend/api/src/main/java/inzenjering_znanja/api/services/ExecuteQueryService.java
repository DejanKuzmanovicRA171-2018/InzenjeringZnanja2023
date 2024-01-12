package inzenjering_znanja.api.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.stereotype.Service;

@Service
public class ExecuteQueryService {
    public String queryTest = "PREFIX ont:<http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#> SELECT ?computer WHERE { ?computer a ont:Computer}";
    public String rdfFilePath = "/home/sibin/InzenjeringZnanja2023/computer-ontology-classes.rdf";
    public Model model;
    public Reasoner reasoner;
    public Model infModel;

    public ExecuteQueryService() {
        // Initialize the default model
        model = ModelFactory.createDefaultModel();

        // Read RDF data from the file into the model
        try {
            RDFDataMgr.read(model, rdfFilePath);
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
        }

        // Create a reasoner
        reasoner = ReasonerRegistry.getRDFSReasoner();

        // Apply the reasoner to the model
        infModel = ModelFactory.createInfModel(reasoner, model);
    }

    public ResultSet executeQuery(String queryString) {
        Query query = QueryFactory.create(queryTest);
        // Execute the query on the inferred model
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, infModel)) {
            ResultSet results = queryExecution.execSelect();
            return results;
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
            return null;
        }
    }
}
