package inzenjering_znanja.api.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inzenjering_znanja.api.Models.PC;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;

@Service
public class SparqlConnector implements Connector {
    @Autowired
    ExecuteQueryService eqService;

    @Override
    public void close() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

    @Override
    public void deleteCases(Collection<CBRCase> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCases'");
    }

    @Override
    public void initFromXMLfile(URL arg0) throws InitializingException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initFromXMLfile'");
    }

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        List<PC> configs = eqService.executeGetAllConfigsQuery(null);
        List<CBRCase> ret = new ArrayList<CBRCase>();
        for (PC pc : configs) {
            CBRCase cbrCase = new CBRCase();
            cbrCase.setDescription(pc);
            ret.add(cbrCase);
        }
        return ret;
    }

    @Override
    public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveSomeCases'");
    }

    @Override
    public void storeCases(Collection<CBRCase> arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'storeCases'");
    }

}
