package inzenjering_znanja.api.services;

import org.springframework.stereotype.Service;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Rule;

@Service
public class ExecuteFuzzyLogicService {
    public double [] executeFuzzyLogic(double [] inputVariables) {
        // Load the FCL file
        String fileName = "C:/Users/Dejan/Desktop/FuzzySystem.fcl";
        FIS fis = FIS.load(fileName, true);
        for (int i = 0; i < inputVariables.length; i++) {
          if (inputVariables[i] == 0.0) {
              return new double [5];
          }
      }
        fis.setVariable("RAMCapacity", inputVariables[3]);
        fis.setVariable("CPUCores", inputVariables[0]);
        fis.setVariable("GPUCores", inputVariables[1]);
        fis.setVariable("GPUVRAM", inputVariables[2]);
        fis.setVariable("PowerSupply", inputVariables[4]);
        fis.setVariable("CoolerTDP", inputVariables[5]);

        System.out.println(fis.getVariable("RAMCapacity"));
        System.out.println(fis.getVariable("CPUCores"));
        System.out.println(fis.getVariable("GPUCores"));
        System.out.println(fis.getVariable("GPUVRAM"));
        System.out.println(fis.getVariable("PowerSupply"));
        System.out.println(fis.getVariable("CoolerTDP"));

        fis.evaluate();
        double [] usageScores = new double[5];
        // Get output values
        usageScores[0] = fis.getVariable("Gaming").getValue()*10;
        // double workOutput = fis.getVariable("Work").getValue();
        // double homeOutput = fis.getVariable("Home").getValue();
        // double hostingOutput = fis.getVariable("Hosting").getValue();
        // double miningOutput = fis.getVariable("Mining").getValue();
        
        // Print or use the output values as needed
        System.out.println("Gaming Output: " + usageScores[0]);
        for( Rule r : fis.getFunctionBlock("fuzzyLogic").getFuzzyRuleBlock("GamingRules").getRules() )
      System.out.println(r);
      
        return usageScores;
    }
}