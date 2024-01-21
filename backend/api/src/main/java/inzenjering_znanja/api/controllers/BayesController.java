package inzenjering_znanja.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.INode;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

@RestController
public class BayesController {
    @GetMapping("/malfunction")
    public Map<String, Float> malfunctionSource(@RequestParam String[] evidence) {

        ProbabilisticNetwork net;
        BaseIO io = new NetIO();
        try {
            net = (ProbabilisticNetwork) io.load(
                    new File("X:/GitRepos/InzenjeringZnanja2023/backend/api/unbbayes-4.22.18-dist/bayesnetwork.net"));
        } catch (IOException e) {
            throw new RuntimeException("No such file");
        }
        IInferenceAlgorithm algorithm = new JunctionTreeAlgorithm();

        algorithm.setNetwork(net);
        algorithm.run();

        Set<ProbabilisticNode> causeNodes = new HashSet<>();
        Map<String, Float> response = new HashMap<>();
        // Set all user findings to state: 0 i.e. Yes
        for (String symptom : evidence) {
            ProbabilisticNode targetNode = (ProbabilisticNode) net.getNode(symptom);
            targetNode.addFinding(0);
            List<INode> parents = targetNode.getParentNodes();
            for (INode parNode : parents) {
                ProbabilisticNode a = (ProbabilisticNode) parNode;
                causeNodes.add(a);
                response.put(a.getName(), 0f);
            }
        }

        try {
            net.updateEvidences();
        } catch (Exception e) {
            e.printStackTrace();
        }

        float sum = 0;
        for (ProbabilisticNode probabilisticNode : causeNodes) {
            float newProb = probabilisticNode.getMarginalAt(0) * 100;
            response.put(probabilisticNode.getName(), newProb);
            sum += newProb;
        }
        for (Map.Entry<String, Float> entry : response.entrySet()) {
            Float prob = entry.getValue();
            prob = (prob / sum) * 100;
            prob = Math.round(prob * 100f) / 100f;
            response.put(entry.getKey(), prob);
        }

        return response;
    }

}
