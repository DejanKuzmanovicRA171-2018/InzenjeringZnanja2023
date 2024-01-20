package inzenjering_znanja.api.DTO;

import inzenjering_znanja.api.Models.PC;

public class RecommendResponseDTO {
    public double[] usageScores = new double[6];
    public PC recommendedComponents;
}
