package inzenjering_znanja.api.Helpers;

import java.util.List;

public class EuclideanDistanceCalculator {

    public static double calculateEuclideanDistance(List<Double> features1, List<Double> features2) {
        if (features1.size() != features2.size()) {
            throw new IllegalArgumentException("Features must have the same dimensionality");
        }

        double sum = 0.0;

        for (int i = 0; i < features1.size(); i++) {
            double diff = features1.get(i) - features2.get(i);
            sum += diff * diff;
        }

        return Math.sqrt(sum);
    }

}
