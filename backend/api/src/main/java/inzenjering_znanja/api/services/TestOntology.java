package inzenjering_znanja.api.services;

import org.apache.jena.ontology.Individual;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFDataMgr;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestOntology {

    public List<String> getAllPCs() {
        // RDF file path
        String rdfFilePath = "/home/sibin/InzenjeringZnanja2023/computer-ontology-classes.rdf";
        // Create an empty Jena model
        Model model = ModelFactory.createDefaultModel();
        // Read RDF data from the file into the model
        try {
            RDFDataMgr.read(model, rdfFilePath);
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
        // Create a reasoner
        Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();

        // Apply the reasoner to the model
        Model infModel = ModelFactory.createInfModel(reasoner, model);

        /// BEEEEEEE
        Resource pc = infModel.getResource(
                "http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#SiboPC");
        Property dataProperty = infModel.getProperty(
                "http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#hasSomething");

        Statement statement = pc.getProperty(dataProperty);
        if (statement != null) {
            RDFNode value = statement.getObject();
            int intValue = value.asLiteral().getInt();
            System.out.println(intValue);
        }
        /// BEEEEEE
        String queryString = "PREFIX ont:<http://www.semanticweb.org/inzenjering-znanja-2023/computer-ontology-classes#> SELECT ?computer WHERE { ?computer a ont:Computer}";
        Query query = QueryFactory.create(queryString);

        List<String> computerInstances = new ArrayList<>();

        // Execute the query on the inferred model
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, infModel)) {
            ResultSet results = queryExecution.execSelect();

            // Process the results
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                Resource computer = solution.getResource("computer");

                // Add computer instance URI to the list
                computerInstances.add(computer.getLocalName());
            }
        } catch (Exception e) {
            // Log or handle the exception
            e.printStackTrace();
        }

        return computerInstances;
    }
}
