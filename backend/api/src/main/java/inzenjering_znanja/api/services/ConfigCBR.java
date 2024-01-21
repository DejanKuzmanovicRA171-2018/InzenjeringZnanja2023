package inzenjering_znanja.api.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inzenjering_znanja.api.Models.CPU;
import inzenjering_znanja.api.Models.GPU;
import inzenjering_znanja.api.Models.Motherboard;
import inzenjering_znanja.api.Models.PC;
import inzenjering_znanja.api.Models.RAM;
import inzenjering_znanja.api.Models.Storage;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CBRCaseBase;
import ucm.gaia.jcolibri.cbrcore.CBRQuery;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

@Service
public class ConfigCBR implements StandardCBRApplication {
    private List<PC> result;
    private CBRCaseBase _caseBase;
    private NNConfig _nnConfig;

    @Autowired
    private SparqlConnector sparqlConnector;
    private Connector _connector;

    @Override
    public void configure() throws ExecutionException {
        _connector = sparqlConnector;
        _caseBase = new LinealCaseBase();

        _nnConfig = new NNConfig();
        _nnConfig.setDescriptionSimFunction(new Average());

        _nnConfig.addMapping(new Attribute("cpu", PC.class), new Average());
        _nnConfig.addMapping(new Attribute("gpu", PC.class), new Average());
        _nnConfig.addMapping(new Attribute("storage", PC.class), new Average());
        _nnConfig.addMapping(new Attribute("motherboard", PC.class), new Average());
        _nnConfig.addMapping(new Attribute("ram", PC.class), new Average());

        _nnConfig.addMapping(new Attribute("cpuClockSpeed", CPU.class), new Interval(2000));
        _nnConfig.addMapping(new Attribute("cpuNumOfCores", CPU.class), new Interval(2));
        _nnConfig.addMapping(new Attribute("cpuNumOfThreads", CPU.class), new Interval(4));

        _nnConfig.addMapping(new Attribute("gpuClockSpeed", GPU.class), new Interval(200));
        _nnConfig.addMapping(new Attribute("gpuNumOfCores", GPU.class), new Interval(300));
        _nnConfig.addMapping(new Attribute("gpuVRAM", GPU.class), new Interval(2));

        _nnConfig.addMapping(new Attribute("writeSpeed", Storage.class), new Interval(250));
        _nnConfig.addMapping(new Attribute("storageCapacity", Storage.class), new Interval(300));
        _nnConfig.addMapping(new Attribute("rpm", Storage.class), new Interval(200));

        _nnConfig.addMapping(new Attribute("numOfRAMSlotsM", Motherboard.class), new Interval(2));
        _nnConfig.addMapping(new Attribute("ramCapacityM", Motherboard.class), new Interval(4));

        _nnConfig.addMapping(new Attribute("ramClockSpeed", RAM.class), new Interval(300));
        _nnConfig.addMapping(new Attribute("sizeOfRAM", RAM.class), new Interval(6));
    }

    @Override
    public void cycle(CBRQuery cbrQuery) throws ExecutionException {
        result = new ArrayList<>();
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(_caseBase.getCases(), cbrQuery,
                _nnConfig);
        eval = SelectCases.selectTopKRR(eval, 20);
        System.out.println("Retrieved cases:");
        for (RetrievalResult nse : eval) {
            System.out.println(nse.get_case().getDescription() + " -> " + nse.getEval());
            result.add((PC) nse.get_case().getDescription());
        }
    }

    @Override
    public void postCycle() throws ExecutionException {
        // TODO Auto-generated method stub
    }

    @Override
    public CBRCaseBase preCycle() throws ExecutionException {
        _caseBase.init(_connector);
        return _caseBase;
    }

    public List<PC> Go(PC pc) {
        try {
            configure();
            preCycle();
            CBRQuery cbrQuery = new CBRQuery();
            cbrQuery.setDescription(pc);
            cycle(cbrQuery);
            postCycle();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
